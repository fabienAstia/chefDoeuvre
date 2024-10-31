package com.personalities.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = UniqueNameValidator.class)
public @interface UniqueName {
    String message() default "Hey, cette question doit être unique";

    Class<?>[] groups() default {};

    Class<?extends Payload>[] payload() default {};
}
