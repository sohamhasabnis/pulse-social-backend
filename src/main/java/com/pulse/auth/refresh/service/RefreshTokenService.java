package com.pulse.auth.refresh.service;

import com.pulse.auth.refresh.entity.RefreshTokenEntity;

import java.util.UUID;

public interface RefreshTokenService {

    RefreshTokenEntity createRefreshToken(UUID userId);

    boolean isTokenActive(String token);
}
