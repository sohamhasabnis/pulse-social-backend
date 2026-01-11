package com.pulse.auth.dto;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class RegisterResponse {
    private final UUID id;
    private final String username;
    private final String email;
    private final Instant createdAt;

    public RegisterResponse(UUID id, String username, String email, Instant createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
    }
}
