package com.cs.springbootwebtuts.dto;

import com.cs.springbootwebtuts.annotations.PrimeNumberValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
    private Long id;

    @NotBlank(message = "Required field 'title' in Department")
    private String title;

    @PastOrPresent
    private LocalDate createdAt;

    @JsonProperty("isActive")
    private Boolean isActive;

    @NotNull(message = "Required field 'primeNumber' in Department")
    @PrimeNumberValidation
    private Integer primeNumber;
}
