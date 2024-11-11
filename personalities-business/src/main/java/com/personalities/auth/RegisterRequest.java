package com.personalities.auth;

import com.personalities.validators.UserCreateUniqueEmail;

public record RegisterRequest(
        String username,
        @UserCreateUniqueEmail String email,
        String password
){}

