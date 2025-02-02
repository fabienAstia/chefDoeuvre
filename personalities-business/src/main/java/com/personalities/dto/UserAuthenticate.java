package com.personalities.dto;

import jakarta.validation.constraints.NotBlank;

public record UserAuthenticate(
        @NotBlank
        String username,

        @NotBlank
        String password
) {
}
