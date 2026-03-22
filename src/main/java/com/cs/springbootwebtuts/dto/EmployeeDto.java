package com.cs.springbootwebtuts.dto;

import com.cs.springbootwebtuts.annotations.EmployeeAgeValidation;
import com.cs.springbootwebtuts.annotations.EmployeeSalaryValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EmployeeSalaryValidation
public class EmployeeDto {
    private Long id;

    @NotBlank(message = "Required field 'name' in Employee")
    @Size(min = 2, max = 50, message = "Please enter a valid 'name' in the range of 2 to 50 characters.")
    private String name;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@infosys\\.com$", message = "Please enter a valid email address")
    private String email;

    @EmployeeAgeValidation
    private Integer age;

    @PastOrPresent(message = "Joining date cannot be in future")
    private LocalDate joiningDate;

    @JsonProperty("isActive")
    private Boolean isActive;

    @NotNull(message = "Job level is required")
    @Max(value = 3, message = "Job level cannot be greater than 3")
    @Min(value = 1, message = "Job level cannot be less than 1")
    private Integer jobLevel;

    @Positive
    @NotNull
    private BigDecimal salary;
}
