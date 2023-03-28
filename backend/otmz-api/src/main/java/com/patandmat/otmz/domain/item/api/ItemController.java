package com.patandmat.otmz.domain.item.api;

import com.patandmat.otmz.domain.item.dto.ItemResponseDto;
import com.patandmat.otmz.domain.item.application.ItemService;
import com.patandmat.otmz.domain.item.dto.ItemRequestDto;
import com.patandmat.otmz.domain.member.application.MemberService;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.global.auth.CustomUserDetails;
import com.patandmat.otmz.global.exception.NoSuchMemberException;
import com.patandmat.otmz.global.exception.UnauthorizedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class ItemController {

    private final String SUCCESS = "SUCCESS";
    private final String FAIL = "FAIL";
    private final ItemService itemService;

    private final MemberService memberService;

    @PostMapping("/item")
    @Operation(summary = "아이템 저장", description = "아이템을 저장한다. printVector, fabricVector, categoryVector는 각 vector의 JSON을 스트링화해서 보내주면 됨"
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<?> saveItem(@RequestPart("imagefile") MultipartFile file, @RequestPart ItemRequestDto item, @RequestParam String category, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = customUserDetails.getMember();
        try {
            itemService.saveItem(file, item, category, member.getId());
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User Does Not Exist", HttpStatus.BAD_REQUEST);
        } catch (AttributeNotFoundException e) {
            return new ResponseEntity<>("Check Attributes of The Item", HttpStatus.BAD_REQUEST);
        } catch (NoSuchMemberException e) {
            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            System.out.println("IOException");
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/item/{id}")
    @Operation(summary = "아이템 조회", description = "조회하고자하는 아이템의 아이디를 담아서 넘겨준다."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = ItemResponseDto.class)))
    })
    public ResponseEntity<?> getItem(@PathVariable Long id, Authentication authentication) {
        // take name, comment
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();
        try {
            ItemResponseDto itemResponseDto = itemService.getItem(id, member.getId());
            return new ResponseEntity<>(itemResponseDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("This Item Doesn't Exist", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/item/{id}")
    @Operation(summary = "아이템 삭제", description = "삭제하고 싶은 아이템의 아이디를 담아서 넘겨준다."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<?> deleteItem(@PathVariable Long id, Authentication authentication) throws IOException {
        // take name, comment
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();
        try {
            itemService.deleteItem(id, member.getId());
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("This Item Doesn't Exist", HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>("Unauthorized Operation", HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/items")
    @Operation(summary = "여러개의 아이템 삭제", description = "삭제하고자 하는 아이템들의 아이디들을 ids 배열에 각 코디북들의 아이디를 담아서 넘겨주면 된다."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<?> deleteMultipleItems(@RequestParam List<Long> ids, Authentication authentication) {
        // take name, comment
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();
        try {
            itemService.deleteMultipleItems(ids, member.getId());
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
    @Operation(summary = "여러 아이템들을 페이지 안에 넣어 돌려줌(사진 파일은 보내지 않음)", description = "각 아이템의 사진을 제외한 정보들을 보내주며 사진을 얻기 위해서는 imageId(사진 아이디)로 api에 요청하면 됨. " +
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
    public ResponseEntity<?> getItemPageByCategory(Pageable pageable, @PathVariable String category, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();
        try {
            Page<ItemResponseDto> page = itemService.getItems(pageable, category, member.getId());
            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (AttributeNotFoundException e) {
            return new ResponseEntity<>("Wrong Category Value", HttpStatus.BAD_REQUEST);
        } catch (NoSuchMemberException e) {
            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.BAD_REQUEST);
        }
    }
}
