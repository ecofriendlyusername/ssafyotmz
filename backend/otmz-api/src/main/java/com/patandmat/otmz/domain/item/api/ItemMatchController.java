package com.patandmat.otmz.domain.item.api;

import com.patandmat.otmz.domain.item.dto.ItemMatchDto;
import com.patandmat.otmz.domain.item.entity.ItemMatch;
import com.patandmat.otmz.domain.item.application.ItemMatchService;
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
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class ItemMatchController {

    private final String SUCCESS = "SUCCESS";
    private final String FAIL = "FAIL";
    private final ItemService itemService;
    private final ItemMatchService itemMatchService;
    @PostMapping("/itemmatch")
    public ResponseEntity<?> saveItemMatch(@RequestPart("imagefile") MultipartFile file, @RequestPart ItemMatch itemMatch, Authentication authentication) throws IOException {
        // take name, comment
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        Member member = userDetails.getMember();
        try {
            itemMatchService.saveItemMatch(file,itemMatch,member.getId());
        } catch (AttributeNotFoundException e) {
            return new ResponseEntity<>("Check Attributes of The Item", HttpStatus.BAD_REQUEST);
        } catch (NoSuchMemberException e) {
            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/itemmatch/{id}")
    public ResponseEntity<?> getItemMatch(@PathVariable Long id, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        Member member = userDetails.getMember();
        try {
            ItemMatchDto itemMatchDto = itemMatchService.getItemMatch(id,member.getId());
            return new ResponseEntity<>(itemMatchDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("This Item Doesn't Exist", HttpStatus.BAD_REQUEST);
        } catch (NoSuchMemberException e) {
            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/itemmatch/{id}")
    public ResponseEntity<?> deleteItemMatch(@PathVariable Long id, Authentication authentication) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        Member member = userDetails.getMember();
        try {
            itemMatchService.deleteItemMatch(id,member.getId());
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("This Item Doesn't Exist", HttpStatus.BAD_REQUEST);
        } catch (NoSuchMemberException e) {
            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/itemmatch")
    public ResponseEntity<?> deleteMultipleItemMatches(@RequestBody List<Long> ids, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        Member member = userDetails.getMember();
        try {
            itemMatchService.deleteMultipleItemMatches(ids,member.getId());
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("This Item Doesn't Exist", HttpStatus.BAD_REQUEST);
        } catch (NoSuchMemberException e) {
            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>("Unauthorized Operation", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/itemmatchpage")
    @Operation(summary= "여러 아이템매치들을 페이지 안에 넣어 돌려줌(사진 파일은 보내지 않음)", description = "각 아이템매치의 사진을 제외한 정보들을 보내주며 사진을 얻기 위해서는 imageId(사진 아이디)로 api에 요청하면 됨. " +
            "<br><br> 이미지 요청 endpoint -> http://(host)/api/v1/images/{id}" +
            "<br><br> page 관련 정보를 쿼리 파라미터로 보내면 해당 페이지를 보내줌. " +
            "<br><br> ------------ 쿼리파라미터 설명 ------------<br><br> " +
            "size : 한 페이지에 몇개의 item match가 들어가는지  <br><br>" +
            "page : 몇번째 페이지인지,  <br><br>" +
            "sort : 어떤 속성으로 정렬할것인지, 올림차순인지 내림차순인지 <br><br>" +
            "요청예시 : http://(host)/api/v1/itemmatchpage?page=2&size=3&sort=id,ASC <br><br>" +
            "설명 : 아이디를 기준으로 오름차순으로 정렬하고 두 번쩨 페이지를 가져온다. 한 페이지에는 두개의 아이템매치가 있다. <br><br>" +
            " 총 페이지 수는 totalPages에서 찾을 수 있음"
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })

    public ResponseEntity<?> getItemMatchPage(Pageable pageable, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        Member member = userDetails.getMember();
        try {
            Page<ItemMatchDto> page = itemMatchService.getItemMatchPage(pageable, member.getId());
            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (NoSuchMemberException e) {
            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.BAD_REQUEST);
        }
    }
}
