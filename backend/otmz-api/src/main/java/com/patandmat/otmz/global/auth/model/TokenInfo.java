package com.patandmat.otmz.global.auth.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenInfo {

    private final String userId;
    private final String grantType;
    private final String authorization;
    private final String refreshToken;

    private final Long accessTokenExpirationTime;
    private final Long refreshTokenExpirationTime;

    @Builder
    public TokenInfo(String userId, String grantType, String authorization, String refreshToken, Long accessTokenExpirationTime, Long refreshTokenExpirationTime) {
        this.userId = userId;
        this.grantType = grantType;
        this.authorization = authorization;
        this.refreshToken = refreshToken;
        this.accessTokenExpirationTime = accessTokenExpirationTime;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }
}


