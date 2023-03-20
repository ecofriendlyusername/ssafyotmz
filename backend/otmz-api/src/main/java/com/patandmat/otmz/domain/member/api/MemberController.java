package com.patandmat.otmz.domain.member.api;


import com.patandmat.otmz.domain.auth.application.JwtService;
import com.patandmat.otmz.domain.member.application.MemberService;
import com.patandmat.otmz.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

        if (jwtService.checkToken(token,id)) {
            if (token.equals(memberService.getRefreshToken(id))) {
                String accessToken = jwtService.createAccessToken("member_id",id);
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
            return new ResponseEntity<>(SUCCESS,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("temp");
            return new ResponseEntity<>(FAIL,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // logout
    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam Long id) {
        memberService.logout(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/info")
    @Operation(summary= "유저 정보 얻기.", description = "json형식으로 감. 실제 어떻게 오는지는 response 참고. 키값 userInfo를 통해 유저 정보(객체) 접근 가능. 닉네임(nickname), 비밀번호(password), 돌생일(birthday), 가입일자(joinDate), 아이디(id), 이메일(email)", responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<Map<String, Object>> getMyInfo(@RequestParam Long id) {
        Member member = memberService.getMemberById(id);

        Map<String, Object> resultMap = new HashMap<>();
        if (member == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            resultMap.put("userInfo", member);
            resultMap.put("message", SUCCESS);

            return new ResponseEntity<>(resultMap,HttpStatus.OK);
        }
    }









}
