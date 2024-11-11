package com.personalities.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = UserCreateUniqueEmailValidator.class)
public @interface UserCreateUniqueEmail {
    String message() default "Cet email existe déjà";

    Class<?>[] groups() default {};

    Class<?extends Payload>[] payload() default {};
}
