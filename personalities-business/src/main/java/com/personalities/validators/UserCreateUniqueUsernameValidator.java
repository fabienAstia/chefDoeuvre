package com.personalities.validators;

import com.personalities.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserCreateUniqueUsernameValidator implements ConstraintValidator<UserCreateUniqueUsername, String> {

    private final UserRepository userRepository;

    public UserCreateUniqueUsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) {
            return true;
        }
        return !userRepository.existsByUsernameIgnoreCase(username);
    }
}
