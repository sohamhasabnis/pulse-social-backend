package com.pulse.auth.service.impl;

import com.pulse.auth.dto.LoginRequest;
import com.pulse.auth.dto.LoginResponseRecord;
import com.pulse.auth.dto.LoginTokenResponse;
import com.pulse.auth.jwt.AccessTokenRecord;
import com.pulse.auth.jwt.JwtTokenService;
import com.pulse.auth.refresh.entity.RefreshTokenEntity;
import com.pulse.auth.refresh.service.RefreshTokenService;
import com.pulse.auth.service.UserLoginService;
import com.pulse.common.exception.AuthenticationFailedException;
import com.pulse.common.exception.GlobalDbException;
import com.pulse.user.entity.User;
import com.pulse.user.enums.Status;
import com.pulse.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserLoginServiceImpl implements UserLoginService {

    private static final Logger log = LoggerFactory.getLogger(UserLoginServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public UserLoginServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                JwtTokenService jwtTokenService,
                                RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public LoginResponseRecord login(LoginRequest loginRequest) throws GlobalDbException {

        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if(userOptional.isEmpty())
        {
            throw new AuthenticationFailedException("E002", "No User exists");
        }

        User user = userOptional.get();
        if(Status.DELETED == user.getStatus())
        {
            throw new AuthenticationFailedException("E003", "User is Deleted");
        }

        boolean matches = passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash());
        if (!matches)
        {
            throw new AuthenticationFailedException("E004", "password is wrong");
        }

        AccessTokenRecord accessToken = jwtTokenService.generateAccessToken(user.getId(), user.getEmail());

        RefreshTokenEntity refreshTokenEntity = refreshTokenService.createRefreshToken(user.getId());

        LoginTokenResponse loginTokenResponse = new LoginTokenResponse(accessToken.accessToken(), accessToken.expiresAt());
        return new LoginResponseRecord(loginTokenResponse, refreshTokenEntity);
    }

    @Override
    public LoginResponseRecord refreshAccessToken(String refreshToken) throws AuthenticationFailedException {
        RefreshTokenEntity refreshTokenEntity = refreshTokenService.rotate(refreshToken);

        User user = userRepository.getReferenceById(refreshTokenEntity.getUserId());

        AccessTokenRecord accessTokenRecord = jwtTokenService.generateAccessToken(user.getId(), user.getEmail());

         LoginTokenResponse loginTokenResponse = new LoginTokenResponse(accessTokenRecord.accessToken(), accessTokenRecord.expiresAt());

         return new LoginResponseRecord(loginTokenResponse, refreshTokenEntity);
    }

    @Override
    public void logout(String accessToken)
    {
        RefreshTokenEntity tokenEntity = refreshTokenService.getToken(accessToken);
        if(tokenEntity == null) {
            return;
        }
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(tokenEntity.getId(),
                tokenEntity.getToken(), tokenEntity.getUserId(), tokenEntity.getExpiresAt(), tokenEntity.getCreatedAt(), true);

        RefreshTokenEntity refreshTokenEntity1 = refreshTokenService.setRevokedTrue(refreshTokenEntity);
        log.info("Output : {}", refreshTokenEntity1);

    }
}
