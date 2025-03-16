package com.personalities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personalities.validators.UserCreateUniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreate(

        @JsonProperty("username")
        @NotBlank
        @UserCreateUniqueUsername
        @Pattern(regexp = "^(?=.{1,64}@)[\\w-]+([.-]?[\\w-]+)*@(?=.{4,252}$)[\\w-]+([.-]?[\\w-]+)*(\\.[a-zA-Z]{2,4})+$",
                message = "The email address must be valid")
        String username,

        @Size(min = 8, max = 16)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s])\\S{8,16}$",
                message = "The password must be valid")
        String password
) {
    @Override
    public String toString() {
        return String.format("{username=%s, password= [PROTECTED]}", username);
    }
}
