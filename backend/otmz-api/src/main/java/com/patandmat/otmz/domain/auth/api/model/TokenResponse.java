package com.patandmat.otmz.domain.auth.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenResponse {

    private final String accessToken;

    private final String refreshToken;
}
