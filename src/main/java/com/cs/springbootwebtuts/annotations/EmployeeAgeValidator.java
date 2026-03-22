package com.cs.springbootwebtuts.annotations;

import jakarta.validation.ConstraintValidator;

public class EmployeeAgeValidator implements ConstraintValidator<EmployeeAgeValidation, Integer> {
    @Override
    public boolean isValid(Integer age, jakarta.validation.ConstraintValidatorContext context) {
        if (age == null) return true;
        return age >= 18 && age <= 65;
    }
}
