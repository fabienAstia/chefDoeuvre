package com.personalities.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = QuestionUpdateExistingDimensionValidator.class)
public @interface QuestionUpdateExistingDimension {
    String message() default "La dimension renseignée doit exister";

    Class<?>[] groups() default {};

    Class<?extends Payload>[] payload() default {};
}
