package com.pulse.auth.dto;

import com.pulse.auth.refresh.entity.RefreshTokenEntity;

public record LoginResponseRecord(LoginTokenResponse loginTokenResponse, RefreshTokenEntity refreshTokenEntity) {



}
