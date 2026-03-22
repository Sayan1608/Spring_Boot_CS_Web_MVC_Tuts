package com.cs.springbootwebtuts.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EmployeeAgeValidator.class})
public @interface EmployeeAgeValidation {
    String message() default "Employee age must be between 18 and 65";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
