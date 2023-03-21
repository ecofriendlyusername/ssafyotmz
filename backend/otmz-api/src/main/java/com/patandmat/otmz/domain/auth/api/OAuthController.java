package com.patandmat.otmz.domain.auth.api;

import com.patandmat.otmz.domain.auth.api.model.MemberInfoFromKakao;
import com.patandmat.otmz.domain.auth.api.model.TokenResponse;
import com.patandmat.otmz.domain.auth.application.OAuthService;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.global.auth.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    private final OAuthService oauthService;

    private final JwtUtil jwtUtil;

    @ResponseBody
    @GetMapping("/kakao")
    public ResponseEntity<TokenResponse> kakaoCallback(@RequestParam String code) throws IOException {
        TokenResponse tokens = oauthService.getKakaoTokens(code);
        MemberInfoFromKakao memberInfoFromKakao = oauthService.getKakaoUser(tokens.getAccessToken());

        Member member = oauthService.loginOrJoin(memberInfoFromKakao);

        String accessToken = jwtUtil.createAccessToken(String.valueOf(member.getId()));
        String refreshToken = jwtUtil.createRefreshToken(String.valueOf(member.getId()));

        return new ResponseEntity<>(new TokenResponse(accessToken, refreshToken), HttpStatus.OK);
    }
}
