package co.simplon.personalities.validators;

import co.simplon.personalities.repositories.QuestionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuestionUpdateUniqueLabelValidator implements ConstraintValidator<QuestionUpdateUniqueLabel, String> {

    private final QuestionRepository questionRepository;

    public QuestionUpdateUniqueLabelValidator(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return true;
        }
        Long id = ValidationUtils.pathVariableAsLong("id");
        return !questionRepository.existsByLabelIgnoreCaseAndIdNot(name, id);
    }
}
