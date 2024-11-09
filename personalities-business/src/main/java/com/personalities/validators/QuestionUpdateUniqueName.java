package com.personalities.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = QuestionUpdateUniqueNameValidator.class)
public @interface QuestionUpdateUniqueName {
    String message() default "Cette question doit être unique";

    Class<?>[] groups() default {};

    Class<?extends Payload>[] payload() default {};
}
