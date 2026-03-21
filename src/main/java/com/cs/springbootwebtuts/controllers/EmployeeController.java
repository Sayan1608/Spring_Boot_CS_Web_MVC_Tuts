package com.cs.springbootwebtuts.controllers;

import com.cs.springbootwebtuts.dto.EmployeeDto;
import com.cs.springbootwebtuts.entities.Employee;
import com.cs.springbootwebtuts.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/test")
    public String testEmployeeController(){
        return "Hello Employee Controller";
    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeDto getEmployee(@PathVariable(name = "employeeId") Long id){
        return employeeService.findEmployeeById(id);
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees(){
        return employeeService.findAllEmployees();
    }

    @PostMapping(path = "/create")
    public EmployeeDto createNewEmployee(@RequestBody Employee inputEmployee){
        return employeeService.createNewEmployee(inputEmployee);
    }


}
