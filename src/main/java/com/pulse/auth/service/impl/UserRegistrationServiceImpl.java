package com.pulse.auth.service.impl;

import com.pulse.auth.dto.RegisterRequest;
import com.pulse.auth.dto.RegisterResponse;
import com.pulse.auth.service.UserRegistrationService;
import com.pulse.common.exception.ConflictException;
import com.pulse.common.exception.GlobalDbException;
import com.pulse.user.entity.User;
import com.pulse.user.enums.Status;
import com.pulse.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserRegistrationServiceImpl(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public RegisterResponse createUser(RegisterRequest registerRequest) throws GlobalDbException {

        if(userRepository.existsByEmail(registerRequest.getEmail()) ||
        userRepository.existsByUsername(registerRequest.getUsername()))
        {
            throw new ConflictException("E0001", "User exists");
        }

        String passwordHash = bCryptPasswordEncoder.encode(registerRequest.getPassword());

        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), passwordHash, Status.ACTIVE, Instant.now());
        userRepository.save(user);

        return new RegisterResponse(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt());
    }
}
