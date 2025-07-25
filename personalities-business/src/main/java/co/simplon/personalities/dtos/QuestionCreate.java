package co.simplon.personalities.dtos;

import co.simplon.personalities.validators.QuestionCreateUniqueLabel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuestionCreate(
        @NotBlank @Size(max = 50) @QuestionCreateUniqueLabel String label,
        @NotBlank @Size(max = 1) String psychPref
) {
}
