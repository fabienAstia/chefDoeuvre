package com.personalities.dtos;

import java.util.List;
import java.util.Map;

public record MbtiTypeView(
        String code,
        String name,
        String description,
        double populationPercentage,
        String interestingFact,
        String image,
        List<String> professions,
        Map<String, String> strengthAndWeaknesses
) {
}
