package com.patandmat.otmz.domain.auth.api;

import com.patandmat.otmz.domain.auth.api.model.LoginResponse;
import com.patandmat.otmz.domain.auth.api.model.MemberInfoFromKakao;
import com.patandmat.otmz.domain.auth.api.model.TokenResponse;
import com.patandmat.otmz.domain.auth.application.OAuthService;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.global.auth.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    private final OAuthService oauthService;

    private final JwtUtil jwtUtil;

    @GetMapping("/kakao")
    public ResponseEntity<LoginResponse> kakaoCallback(@RequestParam String code, @RequestParam(name = "redirect_uri", required = false)String redirectUri) {
        TokenResponse tokens = oauthService.getKakaoTokens(code, redirectUri);
        MemberInfoFromKakao memberInfoFromKakao = oauthService.getKakaoUser(tokens.getAccessToken());

        Member member = oauthService.loginOrJoin(memberInfoFromKakao);

        String accessToken = jwtUtil.createAccessToken(String.valueOf(member.getId()));
        String refreshToken = jwtUtil.createRefreshToken(String.valueOf(member.getId()));

        return new ResponseEntity<>(new LoginResponse(accessToken, refreshToken, member.getId(), member.getNickname(), member.getProfileImagePath()), HttpStatus.OK);
    }
}
