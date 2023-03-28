package com.patandmat.otmz.domain.member.api;


import com.patandmat.otmz.domain.auth.application.JwtService;
import com.patandmat.otmz.domain.look.api.model.StyleByCountResponse;
import com.patandmat.otmz.domain.look.api.model.StyleByPercentResponse;
import com.patandmat.otmz.domain.member.api.model.MypageResponse;
import com.patandmat.otmz.domain.member.application.MemberService;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.global.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final MemberService memberService;
    private final JwtService jwtService;

    @GetMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestParam Long id, HttpServletRequest request) {

        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        String token = request.getHeader("refresh_token");

        if (jwtService.checkToken(token, id)) {
            if (token.equals(memberService.getRefreshToken(id))) {
                String accessToken = jwtService.createAccessToken("member_id", id);
                resultMap.put("access_token", accessToken);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED;
            }
        } else {
            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        try {
            memberService.deleteMember(id);
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("temp");

            return new ResponseEntity<>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // logout
    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam Long id) {
        memberService.logout(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/mypage")
    @Operation(summary = "mypage", description = "json형식 내이름, 내정보, 그리고 스타일 상위 3개 퍼센테이지랑 보여줌.", responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<MypageResponse> getMyPage(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        String nickname = member.getNickname();
        int totalStyleCount = memberService.getTotalStyleCount(member.getId());
        int totalItemCount = memberService.getTotalItemCount(member.getId());

        List<StyleByCountResponse> styleSummaries = memberService.getStyleSummary(member.getId());
        final long total = styleSummaries.stream()
                .mapToLong(StyleByCountResponse::getCount)
                .sum();

        List<StyleByPercentResponse> styleByCountResponse = styleSummaries
                .stream()
                .map(summary -> {
                    double cal = summary.getCount() / (double) total * 100;
                    double percentage = Math.round(cal);

                    return new StyleByPercentResponse(summary.getStyle(), percentage);
                })
                .collect(Collectors.toList());

        MypageResponse response = MypageResponse.builder()
                .nickname(nickname)
                .totalStyleCount(totalStyleCount)
                .totalItemCount(totalItemCount)
                .styleByPercentResponseList(styleByCountResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/lookdetail")
    @Operation(summary = "lookdetail", description = "json형식, 전체 스타일에 관한 퍼센테이지를 보여줌.", responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<List<StyleByCountResponse>> getLookDetail(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        List<StyleByCountResponse> styleSummaries = memberService.getStyleSummary(member.getId());

        return new ResponseEntity<>(styleSummaries, HttpStatus.OK);
    }
}
