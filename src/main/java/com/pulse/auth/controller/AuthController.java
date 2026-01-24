package com.pulse.auth.controller;

import com.pulse.auth.dto.*;
import com.pulse.auth.service.UserLoginService;
import com.pulse.auth.service.UserRegistrationService;
import com.pulse.common.exception.AuthenticationFailedException;
import com.pulse.common.exception.GlobalDbException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserRegistrationService userRegistrationService;
    private final UserLoginService userLoginService;


    public AuthController(UserRegistrationService userRegistrationService, UserLoginService userLoginService) {
        this.userRegistrationService = userRegistrationService;
        this.userLoginService = userLoginService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest request) throws GlobalDbException {
        if(request == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        RegisterResponse response = userRegistrationService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginTokenResponse> login(@RequestBody LoginRequest loginRequest) throws GlobalDbException {
        LoginResponseRecord loginResponseRecord = userLoginService.login(loginRequest);
        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", loginResponseRecord.refreshTokenEntity().getToken())
                .httpOnly(true)
                .secure(false)
                .path("/api/v1/auth/refresh")
                .maxAge(Duration.ofDays(30))
                .sameSite("Lax")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(loginResponseRecord.loginTokenResponse());
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginTokenResponse> refresh(
            @CookieValue(name = "refresh_token", required = false) String refresh
    ) throws AuthenticationFailedException {
        if(refresh == null || refresh.isEmpty())
        {
            new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        LoginTokenResponse loginTokenResponse = userLoginService.refreshAccessToken(refresh);
        return ResponseEntity.ok()
                .body(loginTokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(name = "refresh_token", required = false) String refreshToken
    ) {
        if (refreshToken != null && !refreshToken.isEmpty())
        {
            log.info("Running logout");
            userLoginService.logout(refreshToken);
        }

        ResponseCookie deleteCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(false)
                .path("/api/v1/auth/refresh")
                .maxAge(0)
                .sameSite("Lax")
                .build();
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, "")
                .build();
    }

}
