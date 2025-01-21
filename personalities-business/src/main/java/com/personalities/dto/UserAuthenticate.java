package com.personalities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record UserAuthenticate(
        @JsonProperty("username")
        @NotBlank
        String username,

        @NotBlank
        String password
) {
}
