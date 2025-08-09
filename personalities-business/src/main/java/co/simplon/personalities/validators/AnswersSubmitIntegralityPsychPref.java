package co.simplon.personalities.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = AnswersSubmitIntegralityPsychPrefValidator.class)
public @interface AnswersSubmitIntegralityPsychPref {
    String message() default "Not all psychological preferences are provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
