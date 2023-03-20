package com.patandmat.otmz.domain.member.api;


import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.domain.member.application.JwtService;
import com.patandmat.otmz.domain.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/member")
public class MemberController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    // login
    private MemberService memberService;
    private JwtService jwtService;
    @Autowired
    public MemberController(MemberService memberService, JwtService jwtService) {
        this.memberService = memberService;
        this.jwtService = jwtService;
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestParam Long id, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        String token = request.getHeader("refresh_token");
        // logger.debug("token : {}, memberDto : {}", token, memberDto);
        if (jwtService.checkToken(token,id)) {
            if (token.equals(memberService.getRefreshToken(id))) {
                String accessToken = jwtService.createAccessToken("user_id",id);
                resultMap.put("access_token", accessToken);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED;
            }
        } else {
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
    // join


    @GetMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        try {
            memberService.userDelete(id);
            return new ResponseEntity<String>(SUCCESS,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("temp");
            return new ResponseEntity<String>(FAIL,HttpStatus.INTERNAL_SERVER_ERROR);
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
        Member member = memberService.getUserInfoById(id);
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
