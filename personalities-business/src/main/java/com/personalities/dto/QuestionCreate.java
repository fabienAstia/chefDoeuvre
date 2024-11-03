package com.personalities.dto;

import com.personalities.validators.QuestionCreateUniqueName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuestionCreate(
    @NotBlank @Size(max = 50) @QuestionCreateUniqueName String name,
    @NotBlank @Size(max = 1) String dimension
) {
}
