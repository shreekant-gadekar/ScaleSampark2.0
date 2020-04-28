package com.scalesampark.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Regex annotation is used to validate the string field as per given regex 
 * string with it and will return the specified message.
 *
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = RegexValidator.class)
@Documented
public @interface Regex {
	String message() default "Field should have alphabets only.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    String value() default "";
    
}
