package com.personalities.dtos;

public record UserQuestionView(
        Long id,
        String label,
        Integer questionOrder,
        String psychPref
) {
}
