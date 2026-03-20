package com.cs.springbootwebtuts.dto;

import java.time.LocalDate;

public class EmployeeDto {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private LocalDate joiningDate;
    private Boolean isActive;

    public EmployeeDto(){

    }

    public EmployeeDto(Long id, String name, String email, Integer age, LocalDate joiningDate, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.joiningDate = joiningDate;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
