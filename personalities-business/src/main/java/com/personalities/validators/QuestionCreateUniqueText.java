package com.personalities.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = QuestionCreateUniqueTextValidator.class)
public @interface QuestionCreateUniqueText {
    String message() default "Cette question doit être unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
