package co.simplon.personalities.dtos;

import co.simplon.personalities.validators.QuestionUpdateUniqueLabel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuestionUpdate(
        @NotBlank @Size(max = 50) @QuestionUpdateUniqueLabel String label,
        @NotBlank @Size(max = 1) String psychPref
) {
}
