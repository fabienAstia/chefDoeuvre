package com.personalities.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AnswerUpdate(
        @NotNull Long questionId,
        @NotNull @Min(-3) @Max(3) Integer rating
) {
}
