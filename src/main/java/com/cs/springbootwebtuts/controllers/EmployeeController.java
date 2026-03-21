package com.cs.springbootwebtuts.controllers;

import com.cs.springbootwebtuts.entities.Employee;
import com.cs.springbootwebtuts.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(path = "/test")
    public String testEmployeeController(){
        return "Hello Employee Controller";
    }

    @GetMapping(path = "/{employeeId}")
    public Employee getEmployee(@PathVariable(name = "employeeId") Long id){
        return employeeRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @PostMapping(path = "/create")
    public Employee createNewEmployee(@RequestBody Employee inputEmployee){
        return employeeRepository.save(inputEmployee);
    }


}
