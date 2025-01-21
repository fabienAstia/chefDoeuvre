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
    private final String issuer;

    public JwtProvider(Algorithm algorithm, long expiration, String issuer) {
        this.algorithm = algorithm;
        this.expiration = expiration;
        this.issuer = issuer;
    }

    public String create(String subject, String role) {
        Instant issueAt = Instant.now();
        JWTCreator.Builder builder = JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(issueAt)
                .withSubject(subject)
                .withClaim("role", role);
        if (expiration > -1) {
            Instant expiresAt = issueAt.plusSeconds(expiration);
            builder.withExpiresAt(expiresAt);
        }

        System.out.println("Expiration value: " + expiration);
        System.out.println("Issue At: " + issueAt);
        System.out.println("Expires At: " + issueAt.plusSeconds(expiration));
        return builder.sign(algorithm);
    }
}
