package com.personalities.dto;

import com.personalities.validators.UniqueName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuestionCreate(
    @NotBlank @Size(max = 50) @UniqueName String name,
    @NotBlank @Size(max = 1) String dimension
) {
}
