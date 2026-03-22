package com.cs.springbootwebtuts.annotations;

import com.cs.springbootwebtuts.dto.EmployeeDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.util.Locale;

public class EmployeeSalaryValidator implements ConstraintValidator<EmployeeSalaryValidation, EmployeeDto> {

    private String jobLevelField;


    @Override
    public void initialize(EmployeeSalaryValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(EmployeeDto employee, ConstraintValidatorContext context) {

        if (employee == null) return true;

        BigDecimal salary = employee.getSalary();
        Object jobLevelObj = employee.getJobLevel();

        if (salary == null || jobLevelObj == null) {
            return true;
        }

        Integer level = toLevel(jobLevelObj);
        if (level == null) return false;

        boolean valid;
        switch (level) {
            case 1:
                valid = salary.compareTo(new BigDecimal("30000")) >= 0
                        && salary.compareTo(new BigDecimal("60000")) < 0;
                break;
            case 2:
                valid = salary.compareTo(new BigDecimal("60000")) >= 0
                        && salary.compareTo(new BigDecimal("100000")) < 0;
                break;
            case 3:
                valid = salary.compareTo(new BigDecimal("100000")) >= 0;
                break;
            default:
                valid = false;
        }

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid salary for job level")
                    .addPropertyNode("salary")
                    .addConstraintViolation();
        }

        return valid;
    }

    private Integer toLevel(Object jobLevelObj) {
        if (jobLevelObj instanceof Number) return ((Number) jobLevelObj).intValue();
        String s = jobLevelObj.toString().trim().toUpperCase(Locale.ROOT);
        switch (s) {
            case "1": case "JUNIOR": case "JR": case "J": return 1;
            case "2": case "MID": case "M": case "INTERMEDIATE": return 2;
            case "3": case "SENIOR": case "SR": case "S": return 3;
            default:
                try { return Integer.parseInt(s); } catch (NumberFormatException ex) { return null; }
        }
    }
}
