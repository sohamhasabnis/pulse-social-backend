package com.pulse.auth.service;

import com.pulse.auth.dto.LoginRequest;
import com.pulse.auth.dto.LoginResponseRecord;
import com.pulse.common.exception.GlobalDbException;

public interface UserLoginService {

    public LoginResponseRecord login(LoginRequest loginRequest) throws GlobalDbException;
}
