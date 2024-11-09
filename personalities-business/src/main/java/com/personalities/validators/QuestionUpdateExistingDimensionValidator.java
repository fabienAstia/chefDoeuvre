package com.personalities.validators;

import com.personalities.repositories.DimensionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuestionUpdateExistingDimensionValidator implements ConstraintValidator <QuestionUpdateExistingDimension, String>{

    private final DimensionRepository dimensionRepository;

    public QuestionUpdateExistingDimensionValidator(DimensionRepository dimensionRepository) {
        this.dimensionRepository = dimensionRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if(name == null){
            return true;
        }
        return dimensionRepository.existsByNameIgnoreCase(name);
    }
}
