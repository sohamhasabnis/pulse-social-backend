package com.pulse.auth.refresh.service.impl;

import com.pulse.auth.refresh.entity.RefreshTokenEntity;
import com.pulse.auth.refresh.repository.RefreshTokenRepository;
import com.pulse.auth.refresh.service.RefreshTokenService;
import com.pulse.common.exception.AuthenticationFailedException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public RefreshTokenEntity createRefreshToken(UUID userId) {

        Date now = new Date();
        RefreshTokenEntity refreshTokenEntity =
                new RefreshTokenEntity(UUID.randomUUID().toString(),
                        userId, now.toInstant().plus(Duration.ofDays(30L)), now.toInstant(), false);

        return refreshTokenRepository.save(refreshTokenEntity);
    }

    @Override
    public RefreshTokenEntity isTokenActive(String token) throws AuthenticationFailedException {
        Optional<RefreshTokenEntity> optionalRefreshToken = refreshTokenRepository.findByToken(token);

        if(optionalRefreshToken.isEmpty()) {
            throw new AuthenticationFailedException("E006", "Refresh token expired");
        }

        RefreshTokenEntity refreshTokenEntity = optionalRefreshToken.get();

        if(refreshTokenEntity.isRevoked())
        {
            throw new AuthenticationFailedException("E006", "Refresh token expired");
        }

        Instant date = Instant.now();

         if(date.isAfter(refreshTokenEntity.getExpiresAt()))
        {
            throw new AuthenticationFailedException("E006", "Refresh token expired");
        }
         return refreshTokenEntity;
    }
}
