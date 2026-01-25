package com.pulse.auth.jwt;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final JwtUserPrincipal jwtUserPrincipal;
    public JwtAuthenticationToken( JwtUserPrincipal jwtUserPrincipal) {
        super(Collections.emptyList());
        this.jwtUserPrincipal = jwtUserPrincipal;
        setAuthenticated(true);
    }

    @Override
    public @Nullable Object getCredentials() {
        return null;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return jwtUserPrincipal;
    }
}
