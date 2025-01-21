package com.personalities.validators;

import com.personalities.repositories.QuestionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuestionCreateUniqueLabelValidator implements ConstraintValidator<QuestionCreateUniqueLabel, String> {

    private final QuestionRepository questionRepository;

    public QuestionCreateUniqueLabelValidator(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return true;
        }
        return !questionRepository.existsByLabelIgnoreCase(name);
    }
}
