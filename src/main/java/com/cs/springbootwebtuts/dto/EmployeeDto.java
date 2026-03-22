package com.cs.springbootwebtuts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Long id;

    @NotBlank(message = "Required field 'name' in Employee")
    @Size(min = 2, max = 50, message = "Please enter a valid 'name' in the range of 2 to 50 characters.")
    private String name;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@infosys\\.com$", message = "Please enter a valid email address")
    private String email;

    @Max(value = 60, message = "Age cannot be greater than 60")
    @Min(value = 18, message = "Age cannot be less than 18")
    private Integer age;

    @PastOrPresent(message = "Joining date cannot be in future")
    private LocalDate joiningDate;

    @JsonProperty("isActive")
    private Boolean isActive;
}
