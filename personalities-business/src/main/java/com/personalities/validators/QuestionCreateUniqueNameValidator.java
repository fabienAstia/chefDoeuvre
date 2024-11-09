package com.personalities.validators;

import com.personalities.repositories.QuestionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuestionCreateUniqueNameValidator implements ConstraintValidator<QuestionCreateUniqueName, String> {

    private final QuestionRepository questionRepository;

    public QuestionCreateUniqueNameValidator(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if(name == null){
            return true;
        }
        return !questionRepository.existsByNameIgnoreCase(name);
    }
}
