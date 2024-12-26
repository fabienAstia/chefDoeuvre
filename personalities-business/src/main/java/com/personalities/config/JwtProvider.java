package com.personalities.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JwtProvider {

    private final Algorithm algorithm;
    private final long expiration;

    public JwtProvider(Algorithm algorithm, long expiration) {
        this.algorithm = algorithm;
        this.expiration = expiration;
    }

    public String create(String subject) {
        Instant issueAt = Instant.now();
        JWTCreator.Builder builder = JWT.create()
                .withIssuedAt(issueAt)
                .withSubject(subject);
        if (expiration > -1) {
            Instant expiresAt = issueAt.plusSeconds(expiration);
            builder.withExpiresAt(expiresAt);
        }
        return builder.sign(algorithm);
    }
}
