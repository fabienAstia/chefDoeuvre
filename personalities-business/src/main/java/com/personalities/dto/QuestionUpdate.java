package com.personalities.dto;

import com.personalities.validators.QuestionCreateUniqueName;
import com.personalities.validators.QuestionUpdateExistingDimension;
import com.personalities.validators.QuestionUpdateUniqueName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuestionUpdate(Long id,
     @NotBlank @Size(max = 50) @QuestionUpdateUniqueName String name,
     @NotBlank @Size(max = 1) @QuestionUpdateExistingDimension String dimension
) {
}
