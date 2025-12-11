package com.example.carrentalproject.dto;

public class TokensDTO {
    public final String accessToken;
    public final String refreshToken;

    public TokensDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
