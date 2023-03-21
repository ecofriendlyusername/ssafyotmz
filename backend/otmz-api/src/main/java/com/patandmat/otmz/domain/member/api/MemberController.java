package com.patandmat.otmz.domain.member.api;


import com.patandmat.otmz.domain.auth.application.JwtService;
import com.patandmat.otmz.domain.look.api.model.LookCount;
import com.patandmat.otmz.domain.member.application.MemberService;
import com.patandmat.otmz.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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
    // login
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
    public ResponseEntity<Map<String, Object>> getMypage(@RequestParam Long memberId) {

        Member member = memberService.getMemberById(memberId);
        String nickname = member.getNickname();
        int totalStyleCount = memberService.getTotalStyleCount(memberId);
        int totalItemCount = memberService.getTotalItemCount(memberId);
        Map<String, Object> resultMap = new HashMap<>();
        List<LookCount> list  = memberService.getStyleSummary(memberId);
        List<LookCount> result = new ArrayList<>();
        LookCount lookCount = null;


        int total = 0;
        for (int i=0; i<list.size(); i++){
            total += list.get(i).getCount();
        }


        for (int i=0; i<3; i++){

            double cal = list.get(i).getCount()/(double)total * 100;

            int percentage = (int) Math.round(cal);

            lookCount.setStyle(list.get(i).getStyle());
            lookCount.setCount(percentage);

            result.add(lookCount);
        }
        resultMap.put("nickname", nickname);
        resultMap.put("totalStyleCount", totalStyleCount);
        resultMap.put("totalItemCount", totalItemCount);
        resultMap.put("result", result);



        return new ResponseEntity<>(resultMap, HttpStatus.OK);


    }


}
