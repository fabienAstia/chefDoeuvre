package com.personalities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserAuthenticate(
        @JsonProperty("email") String username,
        String password
) {
}
