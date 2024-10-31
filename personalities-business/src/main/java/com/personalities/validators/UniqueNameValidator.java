package com.personalities.validators;

import com.personalities.services.PersonalitiesService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {

    private final PersonalitiesService service;

    public UniqueNameValidator(PersonalitiesService service) {this.service = service;}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println("value=" + value);
        if(null == value){
            return true;
        }
        return !service.existsByNameIgnoreCase(value);
    }
}
