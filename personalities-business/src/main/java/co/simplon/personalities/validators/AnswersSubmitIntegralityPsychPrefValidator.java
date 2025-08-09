package co.simplon.personalities.validators;

import co.simplon.personalities.dtos.AnswerCreate;
import co.simplon.personalities.entities.PsychPreference;
import co.simplon.personalities.exceptions.QuestionNotFoundException;
import co.simplon.personalities.repositories.PsychPreferenceRepository;
import co.simplon.personalities.repositories.QuestionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AnswersSubmitIntegralityPsychPrefValidator implements ConstraintValidator<AnswersSubmitIntegralityPsychPref, List<AnswerCreate>> {

    private final QuestionRepository questionRepository;
    private final PsychPreferenceRepository psychPreferenceRepository;

    public AnswersSubmitIntegralityPsychPrefValidator(QuestionRepository questionRepository, PsychPreferenceRepository psychPreferenceRepository) {
        this.questionRepository = questionRepository;
        this.psychPreferenceRepository = psychPreferenceRepository;
    }

    @Override
    public boolean isValid(List<AnswerCreate> answers, ConstraintValidatorContext context) {
        List<String> dbCodes = psychPreferenceRepository.findAll()
                .stream()
                .map(PsychPreference::getCode)
                .toList();

        Set<String> answerCodes = answers.stream()
                .map(answerCreate -> questionRepository.findById(answerCreate.questionId())
                        .orElseThrow(() -> new QuestionNotFoundException(answerCreate.questionId()))
                        .getPsychPreference().getCode())
                .collect(Collectors.toSet());

        return answerCodes.containsAll(dbCodes);
    }
}
