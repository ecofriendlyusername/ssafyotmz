package com.patandmat.otmz.domain.auth.api;

import com.patandmat.otmz.domain.auth.api.model.TokenResponse;
import com.patandmat.otmz.domain.auth.application.JwtService;
import com.patandmat.otmz.domain.auth.application.OAuthService;
import com.patandmat.otmz.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    private final OAuthService oauthService;
    private final JwtService jwtService;

    @ResponseBody
    @GetMapping("/kakao")
    public ResponseEntity<TokenResponse> kakaoCallback(@RequestParam String code) {
        Map<String , Object> result = oauthService.getKakaoAccessToken(code);

        Member member = oauthService.getKakaoUser(result.get("access_Token").toString());
        result.put("member", member);

        oauthService.loginOrJoin(member);

        String accessToken = jwtService.createAccessToken("id", member.getId());
        String refreshToken = jwtService.createRefreshToken("id", member.getId());

        return new ResponseEntity<>(new TokenResponse(accessToken, refreshToken), HttpStatus.OK);
    }
}
