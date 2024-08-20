package com.personalities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuestionCreate(
    @NotBlank @Size(max = 50) @JsonProperty("nameQuestion") String name
) {
}
