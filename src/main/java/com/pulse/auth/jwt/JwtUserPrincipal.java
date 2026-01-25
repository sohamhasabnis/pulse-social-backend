package com.pulse.auth.jwt;

import java.util.UUID;

public record JwtUserPrincipal(UUID userId, String email) {

}
