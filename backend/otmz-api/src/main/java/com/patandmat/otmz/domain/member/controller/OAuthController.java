package com.patandmat.otmz.domain.member.controller;

import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.domain.member.service.JwtService;
import com.patandmat.otmz.domain.member.service.MemberService;
import com.patandmat.otmz.domain.member.service.OAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class OAuthController {
    private OAuthService oauthService;
    private JwtService jwtService;
    private MemberService memberService;

    public OAuthController(OAuthService oauthService, MemberService userService, JwtService jwtService) {
        this.jwtService = jwtService;
        this.memberService = userService;
        this.oauthService = oauthService;
    }
    @ResponseBody
    @GetMapping("/kakao")
    public ResponseEntity<Map<String,String>> kakaoCallback(@RequestParam String code) {
        Map<String, String> map = new HashMap<>();
        System.out.println(code);
        Map<String , Object> result = oauthService.getKakaoAccessToken(code);
        System.out.println("출력  " + result.get("access_Token").toString());
        Member member = oauthService.getKakaoUser(result.get("access_Token").toString());
        result.put("member", member);
        oauthService.loginOrJoin(member);

        String accessToken = jwtService.createAccessToken("id", member.getId());
        String refreshToken = jwtService.createRefreshToken("id", member.getId());
        map.put("access_token", accessToken);
        map.put("refresh_token", refreshToken);

        return new ResponseEntity<>(map, HttpStatus.OK);


    }

}
