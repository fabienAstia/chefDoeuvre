package co.simplon.personalities.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = QuestionCreateUniqueLabelValidator.class)
public @interface QuestionCreateUniqueLabel {
    String message() default "This question already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
