package com.personalities.dto;

import com.personalities.entities.Dimension;

public record QuestionView(
        Long id,
        String text,
        String dimension
) {
}
