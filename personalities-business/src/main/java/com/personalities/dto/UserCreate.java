package com.personalities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personalities.validators.UserCreateUniqueUsername;
import jakarta.validation.constraints.Size;

public record UserCreate(
        @UserCreateUniqueUsername @JsonProperty("email") String username,
        @Size(min = 8, max = 16) String password
) {
    @Override
    public String toString() {
        return String.format("{username=%s, password= [PROTECTED]}", username);
    }
}
