package com.pulse.auth.model;

import java.util.UUID;

public record JwtUserPrincipal(
        UUID userId,
        String email
) { }
