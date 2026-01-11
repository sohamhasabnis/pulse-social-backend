package com.pulse.auth.controller;

import com.pulse.auth.dto.RegisterRequest;
import com.pulse.auth.dto.RegisterResponse;
import com.pulse.auth.service.UserRegistrationService;
import com.pulse.common.exception.GlobalDbException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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


    public AuthController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest request) throws GlobalDbException {
        if(request == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        RegisterResponse response = userRegistrationService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
