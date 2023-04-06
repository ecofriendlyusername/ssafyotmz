package com.patandmat.otmz.domain.look.api;

import com.patandmat.otmz.domain.item.dto.ItemResponseDto;
import com.patandmat.otmz.domain.look.api.model.LookResponse;
import com.patandmat.otmz.domain.look.api.model.LookSaveResponse;
import com.patandmat.otmz.domain.look.api.model.RecommendedLookResponse;
import com.patandmat.otmz.domain.look.application.LookRecommendService;
import com.patandmat.otmz.domain.look.application.LookService;
import com.patandmat.otmz.domain.look.dto.LookResponseDto;
import com.patandmat.otmz.domain.look.entity.Look;
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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/looks")
public class LookController {

    private final LookService lookService;
    private final MemberService memberService;
    private final LookRecommendService lookRecommendService;

    @PostMapping("")
    @Operation(summary = "룩 저장", description = "룩을 저장한다. mutipart/form-data 타입으로 요청보내야 함. styleVector의 경우 json을 stringify하여 전송"
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<?> saveLook(@RequestPart("imageFile") MultipartFile file,
                                      @RequestPart("styleVector") String styleVector,
                                      @RequestPart("style") String style,
                                      Authentication authentication) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();
        Look look;

        try {
            look = lookService.saveLook(file, styleVector, style, member);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User Does Not Exist", HttpStatus.BAD_REQUEST);
        } catch (NoSuchMemberException e) {
            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.BAD_REQUEST);
        }

        memberService.updateStyleStat(member, styleVector);

        return ResponseEntity.ok().body(new LookSaveResponse(look.getId(), look.getImage().getId()));
    }

    @GetMapping("/recommended")
    @Operation(summary = "추천 스타일 요청", description = "로그인 유저의 스타일스탯(styleStat)을 이용하여 추천 룩을 리스트 형태로 응답함. 쿼리로 size(int), reversed(boolean)를 추가 작성할 수 있음"
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<List<RecommendedLookResponse>> recommendLooks(Authentication authentication,
                                                                        @RequestParam(required = false, defaultValue = "3") Integer size,
                                                                        @RequestParam(required = false, defaultValue = "false") boolean reversed) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        List<RecommendedLookResponse> looks = lookRecommendService.getRecommendedLooks(member, size, reversed);

        return ResponseEntity.ok(looks);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "룩 삭제", description = "삭제하고 싶은 룩의 아이디를 담아서 넘겨준다."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<?> deleteLook(@PathVariable Long id, Authentication authentication) throws IOException {
        // take name, comment
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();
        try {
            lookService.deleteLook(id, member.getId());
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("This Item Doesn't Exist", HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>("Unauthorized Operation", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "룩 조회", description = "조회하고자하는 룩의 아이디를 담아서 넘겨준다."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = LookResponseDto.class)))
    })
    public ResponseEntity<?> getLook(@PathVariable Long id) {
        try {
            LookResponseDto lookResponseDto = lookService.getLook(id);
            return new ResponseEntity<>(lookResponseDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("This Item Doesn't Exist", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getLookPage(@RequestParam(name = "style") String style, Pageable pageable) {
        try {
            Page<Look> looks = lookService.getLooksWithPageable(style, pageable);
            List<LookResponse> response = looks.stream()
                                       .map(look ->
                                               LookResponse.builder()
                                                           .id(look.getId())
                                                           .imageId(look.getImage().getId())
                                                           .memberId(look.getMember().getId())
                                                           .ownerName(look.getMember().getNickname())
                                                           .style(look.getStyle().getKey())
                                                           .build())
                                       .collect(Collectors.toList());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (AttributeNotFoundException e) {

            return new ResponseEntity<>("Wrong Style Value", HttpStatus.BAD_REQUEST);
        } catch (NoSuchMemberException e) {

            return new ResponseEntity<>("User Doesn't Exist", HttpStatus.BAD_REQUEST);
        }
    }
}
