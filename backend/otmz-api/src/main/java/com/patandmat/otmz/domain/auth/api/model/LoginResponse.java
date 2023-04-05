package com.patandmat.otmz.domain.auth.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponse {

    private final String accessToken;

    private final String refreshToken;

    private final Long memberId;

    private final String nickname;

    private final String profileImagePath;

    private final boolean isFirst;
}