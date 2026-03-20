package com.cs.springbootwebtuts.controllers;

import com.cs.springbootwebtuts.dto.EmployeeDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    @GetMapping(path = "/test")
    public String testEmployeeController(){
        return "Hello Employee Controller";
    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeDto getEmployee(@PathVariable(name = "employeeId") Long id){
        return new
                EmployeeDto(id, "John Doe", "johndoe@gmail.com", 30,
                null, true);
    }

    @GetMapping
    public String getRequestParamTest(@RequestParam(required = false) String name, @RequestParam(required = false) Integer age){
        return "Name: " + name + ", Age: " + age;
    }


}
