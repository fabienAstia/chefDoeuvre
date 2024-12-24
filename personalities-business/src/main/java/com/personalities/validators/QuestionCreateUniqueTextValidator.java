package com.personalities.validators;

import com.personalities.repositories.QuestionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuestionCreateUniqueTextValidator implements ConstraintValidator<QuestionCreateUniqueText, String> {

    private final QuestionRepository questionRepository;

    public QuestionCreateUniqueTextValidator(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return true;
        }
        return !questionRepository.existsByTextIgnoreCase(name);
    }
}
