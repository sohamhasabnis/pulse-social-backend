package com.pulse.auth.jwt;

public record AccessTokenRecord(String accessToken, long expiresAt) {
}
