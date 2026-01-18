//package com.pulse.auth.config;
//
//import com.nimbusds.jose.jwk.JWKSet;
//import com.nimbusds.jose.jwk.RSAKey;
//import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
//import com.nimbusds.jose.jwk.source.JWKSource;
//import com.nimbusds.jose.proc.SecurityContext;
//import com.pulse.auth.jwt.JwtKeyProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
//
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//
//@Configuration
//public class JwtConfig {
//
//    @Bean
//    public JwtEncoder jwtEncoder(JwtKeyProvider keyProvider) {
//        RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) keyProvider.getPublicKey())
//                .privateKey((RSAPrivateKey) keyProvider.getPrivateKey())
//                .build();
//
//        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(rsaKey));
//        return new NimbusJwtEncoder(jwkSource);
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder(JwtKeyProvider keyProvider) {
//
//        return NimbusJwtDecoder.withPublicKey(
//                (RSAPublicKey) keyProvider.getPublicKey()
//        ).build();
//    }
//}
