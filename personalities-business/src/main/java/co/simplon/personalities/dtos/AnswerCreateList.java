package co.simplon.personalities.dtos;

import co.simplon.personalities.validators.AnswersSubmitIntegralityPsychPref;
import jakarta.validation.Valid;

import java.util.List;

public record AnswerCreateList(
        @AnswersSubmitIntegralityPsychPref
        List<@Valid AnswerCreate> answers
) {
}
