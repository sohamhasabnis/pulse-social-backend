package com.pulse.auth.service;

import com.pulse.auth.dto.LoginRequest;
import com.pulse.auth.dto.RegisterResponse;
import com.pulse.common.exception.AuthenticationFailedException;
import com.pulse.common.exception.GlobalDbException;

public interface UserLoginService {

    public RegisterResponse login(LoginRequest loginRequest) throws GlobalDbException;
}
