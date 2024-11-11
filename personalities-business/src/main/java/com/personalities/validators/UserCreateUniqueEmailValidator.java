package com.personalities.validators;

import com.personalities.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserCreateUniqueEmailValidator implements ConstraintValidator<UserCreateUniqueEmail, String> {

    private final UserRepository userRepository;

    public UserCreateUniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if(email == null){
            return true;
        }
        return !userRepository.existsByEmailIgnoreCase(email);
    }
}
