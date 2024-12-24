package com.personalities.dto;

import com.personalities.validators.QuestionCreateUniqueText;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuestionCreate(
        @NotBlank @Size(max = 50) @QuestionCreateUniqueText String text,
        @NotBlank @Size(max = 1) String dimension
) {
}
