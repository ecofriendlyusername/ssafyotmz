package com.patandmat.otmz.domain.member.api;


import com.patandmat.otmz.domain.auth.application.JwtService;
import com.patandmat.otmz.domain.look.api.model.LookCount;
import com.patandmat.otmz.domain.member.api.model.MypageDto;
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
    @Operation(summary = "mypage", description = "json형식", responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<MypageDto> getMyPage(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();
        MypageDto result = new MypageDto();
        String nickname = member.getNickname();
        int totalStyleCount = memberService.getTotalStyleCount(member.getId());
        int totalItemCount = memberService.getTotalItemCount(member.getId());
        List<LookCount> list = memberService.getStyleSummary(member.getId());
        List<LookCount> lookCountList = new ArrayList<>();
        int total = 0;

        for (int i = 0; i < list.size(); i++) {
            total += list.get(i).getCount();
        }

        for (int i = 0; i < 3; i++) {
            double cal = list.get(i).getCount() / (double) total * 100;
            int percentage = (int) Math.round(cal);

            LookCount lookCount = new LookCount();
            lookCount.setStyle(list.get(i).getStyle());
            lookCount.setCount(percentage);
            lookCountList.add(lookCount);
        }

        result.setNickname(nickname);
        result.setTotalItemCount(totalItemCount);
        result.setTotalStyleCount(totalStyleCount);
        result.setLookCountList(lookCountList);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/lookdetail")
    @Operation(summary = "lookdetail", description = "json형식", responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<List<LookCount>> getLookDetail(Authentication authentication) {
        //위 코드랑 중복이 많아서 일단 이렇게 커밋하구 빠른 시일 내에 중복 서비스단으로 넘겨서 제거 할 예정입니다!
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();
        List<LookCount> list = memberService.getStyleSummary(member.getId());
        List<LookCount> lookCountList = new ArrayList<>();
        int total = 0;

        for (int i = 0; i < list.size(); i++) {
            total += list.get(i).getCount();
        }

        for (int i = 0; i < list.size(); i++) {
            double cal = list.get(i).getCount() / (double) total * 100;
            int percentage = (int) Math.round(cal);

            LookCount lookCount = new LookCount();
            lookCount.setStyle(list.get(i).getStyle());
            lookCount.setCount(percentage);
            lookCountList.add(lookCount);
        }

        return new ResponseEntity<>(lookCountList, HttpStatus.OK);
    }
}
