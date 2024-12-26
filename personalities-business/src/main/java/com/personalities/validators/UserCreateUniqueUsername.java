package com.personalities.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = UserCreateUniqueUsernameValidator.class)
public @interface UserCreateUniqueUsername {
    String message() default "Cet email existe déjà";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
