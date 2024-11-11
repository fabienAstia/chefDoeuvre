package com.personalities.auth;

public record AuthenticationRequest(
        String email,
        String password
) {
}

