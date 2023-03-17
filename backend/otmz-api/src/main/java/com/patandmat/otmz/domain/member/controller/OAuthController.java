package com.patandmat.otmz.domain.member.controller;

import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.domain.member.service.JwtService;
import com.patandmat.otmz.domain.member.service.OAuthService;
import com.patandmat.otmz.domain.member.service.MemberService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
public class OAuthController {
    private OAuthService oauthService;
    private JwtService jwtService;
    private MemberService memberService;

    @Autowired
    public OAuthController(OAuthService oauthService, MemberService userService, JwtService jwtService) {
        this.jwtService = jwtService;
        this.memberService = userService;
        this.oauthService = oauthService;
    }
    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) {
        System.out.println(code);
        Map<String , Object> map = oauthService.getKakaoAccessToken(code);
        Member member = oauthService.getKakaoUser(map.get("access_Token").toString());
        map.put("member", member);
        oauthService.loginOrJoin(member);

    }

}
