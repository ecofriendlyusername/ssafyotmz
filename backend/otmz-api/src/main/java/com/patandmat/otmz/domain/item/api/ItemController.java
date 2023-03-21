package com.patandmat.otmz.domain.item.api;

import com.patandmat.otmz.domain.item.dto.ItemDto;
import com.patandmat.otmz.domain.item.application.ItemService;
import com.patandmat.otmz.domain.item.exception.NoSuchMemberException;
import com.patandmat.otmz.domain.item.exception.UnauthorizedException;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.global.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.AttributeNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final String SUCCESS = "SUCCESS";
    private final String FAIL = "FAIL";
    private final ItemService itemService;
    @PostMapping("/item")
    public ResponseEntity<?> saveItem(@RequestPart("imagefile") MultipartFile file, @RequestPart ItemDto item, @RequestParam String category, Authentication authentication) throws IOException {
        // Long member_id = 1L;
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        Member member = userDetails.getMember();
        try {
            itemService.saveItem(file,item,category,member_id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User Does Not Exist", HttpStatus.BAD_REQUEST);
        } catch (AttributeNotFoundException e) {
            return new ResponseEntity<>("Check Attributes of The Item", HttpStatus.BAD_REQUEST);
        } catch (NoSuchMemberException e) {
            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        Map<String,String> hm = new HashMap<>();
        hm.put("hello","world");
        return new ResponseEntity<>(hm,HttpStatus.OK);
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<?> getItem(@PathVariable Long id) {
        // take name, comment
        Long member_id = 1L;
        try {
            ItemDto itemDto = itemService.getItem(member_id,id);
            return new ResponseEntity<>(itemDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("This Item Doesn't Exist", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) throws IOException {
        // take name, comment
        Long member_id = 1L;
        try {
            itemService.deleteItem(member_id,id);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("This Item Doesn't Exist", HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>("Unauthorized Operation", HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/items")
    public ResponseEntity<?> deleteMultipleItems(@RequestBody List<Long> ids) throws IOException {
        // take name, comment
        Long member_id = 1L;
        try {
            itemService.deleteMultipleItems(ids,member_id);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("This Item Doesn't Exist", HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>("Unauthorized Operation", HttpStatus.UNAUTHORIZED);
        } catch (NoSuchMemberException e) {
            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/items/{category}")
    @Operation(summary= "여러 아이템들을 페이지 안에 넣어 돌려줌(사진 파일은 보내지 않음)", description = "각 아이템의 사진을 제외한 정보들을 보내주며 사진을 얻기 위해서는 imageId(사진 아이디)로 api에 요청하면 됨. " +
            "<br><br> 이미지 요청 endpoint -> http://(host)/api/v1/images/{id}" +
            "<br><br> page 관련 정보를 쿼리 파라미터로 보내면 해당 페이지를 보내줌. " +
            "<br><br> ------------ 쿼리파라미터 설명 ------------<br><br> " +
            "size : 한 페이지에 몇개의 item이 들어가는지  <br><br>" +
            "page : 몇번째 페이지인지,  <br><br>" +
            "sort : 어떤 속성으로 정렬할것인지, 올림차순인지 내림차순인지 <br><br>" +
            "요청예시 : http://(host)/api/v1/itempage?page=2&size=3&sort=id,ASC <br><br>" +
            "설명 : 아이디를 기준으로 오름차순으로 정렬하고 두 번쩨 페이지를 가져온다. 한 페이지에는 두개의 아이템이 있다. <br><br>" +
            " 총 페이지 수는 totalPages에서 찾을 수 있음"
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<?> getItemPageByCategory(Pageable pageable, @PathVariable String category) {
        Long member_id = 1L;
        try {
            Page<ItemDto> page = itemService.getItems(pageable, category, member_id);
            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (AttributeNotFoundException e) {
            return new ResponseEntity<>("Wrong Category Value",HttpStatus.BAD_REQUEST);
        } catch (NoSuchMemberException e) {
            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.BAD_REQUEST);
        }
    }
}
