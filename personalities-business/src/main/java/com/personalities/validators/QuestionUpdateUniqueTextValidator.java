package com.personalities.validators;

import com.personalities.repositories.QuestionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuestionUpdateUniqueTextValidator implements ConstraintValidator<QuestionUpdateUniqueText, String> {

    private final QuestionRepository questionRepository;

    public QuestionUpdateUniqueTextValidator(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return true;
        }
        Long id = ValidationUtils.pathVariableAsLong("id");
        return !questionRepository.existsByTextIgnoreCaseAndIdNot(name, id);
    }
}
