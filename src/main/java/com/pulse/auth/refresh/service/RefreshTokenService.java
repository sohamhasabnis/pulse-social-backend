package com.pulse.auth.refresh.service;

import com.pulse.auth.refresh.entity.RefreshTokenEntity;
import com.pulse.common.exception.AuthenticationFailedException;

import java.util.UUID;

public interface RefreshTokenService {

    RefreshTokenEntity createRefreshToken(UUID userId);

    RefreshTokenEntity isTokenActive(String token) throws AuthenticationFailedException;

    RefreshTokenEntity getToken(String token);

    RefreshTokenEntity setRevokedTrue(RefreshTokenEntity refreshTokenEntity);

    RefreshTokenEntity rotate(String token) throws AuthenticationFailedException;
}
