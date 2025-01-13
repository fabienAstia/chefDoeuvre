package com.personalities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record UserAuthenticate(
        @JsonProperty("email")
        @NotBlank
        String username,

        @NotBlank
        String password
) {
}
