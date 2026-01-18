package com.pulse.auth.controller;

import com.pulse.auth.dto.LoginRequest;
import com.pulse.auth.dto.RegisterRequest;
import com.pulse.auth.dto.RegisterResponse;
import com.pulse.auth.service.UserLoginService;
import com.pulse.auth.service.UserRegistrationService;
import com.pulse.common.exception.AuthenticationFailedException;
import com.pulse.common.exception.GlobalDbException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

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
    public ResponseEntity<RegisterResponse> login(@RequestBody LoginRequest loginRequest) throws GlobalDbException {
        RegisterResponse registerResponse = userLoginService.login(loginRequest);
        return new ResponseEntity<>(registerResponse, HttpStatus.OK);
    }

}
