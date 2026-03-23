package com.cs.springbootwebtuts.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrimeNumberValidator implements ConstraintValidator<PrimeNumberValidation,Integer> {
    @Override
    public void initialize(PrimeNumberValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
      if (integer == null) return true;
      if(integer == 0 || integer == 1){
          setMessage(constraintValidatorContext,integer + " is not a prime number");
          return false;
      }
      for(int i = 2; i <= Math.sqrt(integer); i++) {
          if (integer % i == 0) {
              setMessage(constraintValidatorContext,integer + " is not a prime number");
              return false;
          }
      }
        return true;
    }

    private void setMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}
