package com.personalities.validators;

import com.personalities.repositories.QuestionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuestionUpdateUniqueNameValidator implements ConstraintValidator<QuestionUpdateUniqueName, String> {

    private final QuestionRepository questionRepository;

    public QuestionUpdateUniqueNameValidator(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context){
        if(name == null){
            return true;
        }
        Long id = ValidationUtils.pathVariableAsLong("id");
        return !questionRepository.existsByNameIgnoreCaseAndIdNot(name, id);
    }
}
