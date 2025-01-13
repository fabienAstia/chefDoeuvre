package com.personalities.dto;

import com.personalities.validators.QuestionUpdateUniqueText;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuestionUpdate(
        @NotBlank @Size(max = 50) @QuestionUpdateUniqueText String text,
        @NotBlank @Size(max = 1) String dimension
) {
}
