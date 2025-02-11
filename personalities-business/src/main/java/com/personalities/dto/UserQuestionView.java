package com.personalities.dto;

public record UserQuestionView(
        Long id,
        String label,
        Integer questionOrder,
        String psychPref
) {
}
