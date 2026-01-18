package com.pulse.auth.service.impl;

import com.pulse.auth.dto.LoginRequest;
import com.pulse.auth.dto.RegisterResponse;
import com.pulse.auth.service.UserLoginService;
import com.pulse.common.exception.AuthenticationFailedException;
import com.pulse.common.exception.GlobalDbException;
import com.pulse.user.entity.User;
import com.pulse.user.enums.Status;
import com.pulse.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserLoginServiceImpl implements UserLoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserLoginServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponse login(LoginRequest loginRequest) throws GlobalDbException {

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

        return new RegisterResponse(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt());
    }
}
