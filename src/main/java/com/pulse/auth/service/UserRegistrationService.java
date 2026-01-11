package com.pulse.auth.service;

import com.pulse.auth.dto.RegisterRequest;
import com.pulse.auth.dto.RegisterResponse;
import com.pulse.common.exception.ConflictException;
import com.pulse.common.exception.GlobalDbException;

public interface UserRegistrationService {

    RegisterResponse createUser(RegisterRequest registerRequest) throws GlobalDbException;
}
