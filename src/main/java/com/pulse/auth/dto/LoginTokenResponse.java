package com.pulse.auth.dto;

public class LoginTokenResponse {
    private final String accessToken;
    private final long expiresAt;

    public LoginTokenResponse(String accessToken, long expiresAt) {
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getExpiresAt() {
        return expiresAt;
    }
}
