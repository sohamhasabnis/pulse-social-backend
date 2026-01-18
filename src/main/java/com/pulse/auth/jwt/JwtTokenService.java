package com.pulse.auth.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtTokenService {

  private final Duration accessTokenExpiry;
  private final JwtKeyProvider keyProvider;
    public JwtTokenService(
            @Value("${jwt.access-token.expiry-minutes}") long accessTokenExpiry, JwtKeyProvider keyProvider) {
        this.accessTokenExpiry = Duration.ofMinutes(accessTokenExpiry);
        this.keyProvider = keyProvider;
    }

    public String generateAccessToken(UUID userId, String email) {
      Instant instant = Instant.now();

      JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
              .subject(userId.toString())
              .claim("email", email)
              .issueTime(Date.from(instant))
              .expirationTime(Date.from(instant.plus(accessTokenExpiry)))
              .build();

      SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);

        try {
            signedJWT.sign(new RSASSASigner(keyProvider.getPrivateKey()));
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public void validateToken(String token)
    {
      try {
        SignedJWT signedJWT = SignedJWT.parse(token);

        boolean valid = signedJWT.verify(new RSASSAVerifier(keyProvider.getPublicKey()));
        if(!valid)
        {
          throw  new IllegalArgumentException("Invalid JWT signature");
        }

        Date expiry = signedJWT.getJWTClaimsSet().getExpirationTime();
        if(expiry.before(new Date()))
        {
          throw new IllegalArgumentException("JWT expired");
        }
      } catch (ParseException | JOSEException e) {
        throw new IllegalArgumentException("Invalid or expired JWT", e);
      }
    }

  public UUID extractUserId(String token) {
    try {
      SignedJWT signedJWT = SignedJWT.parse(token);
      return UUID.fromString(signedJWT.getJWTClaimsSet().getSubject());
    } catch (Exception e) {
      throw new IllegalStateException("Invalid userId in JWT", e);
    }
  }

  public String extractEmail(String token) {
    try {
      SignedJWT signedJWT = SignedJWT.parse(token);
      return signedJWT.getJWTClaimsSet().getStringClaim("email");
    } catch (Exception e) {
      throw new IllegalStateException("Invalid email in JWT", e);
    }
  }
}
