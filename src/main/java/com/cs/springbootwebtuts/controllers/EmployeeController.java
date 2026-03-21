package com.cs.springbootwebtuts.controllers;

import com.cs.springbootwebtuts.dto.EmployeeDto;
import com.cs.springbootwebtuts.entities.Employee;
import com.cs.springbootwebtuts.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PutMapping(path = {"/{employeeId}"})
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable(name = "employeeId") Long id,
                                                     @RequestBody EmployeeDto employeeDto){
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDto);
        if(updatedEmployee == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedEmployee);
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> partiallyUpdateEmployee(@PathVariable(name = "employeeId") Long id,
                                                              @RequestBody Map<String, Object> updates) {
        EmployeeDto updatedEmployee = employeeService.partiallyUpdateEmployee(id, updates);
        if (updatedEmployee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable(name = "employeeId") Long id) {
        boolean isDeleted = employeeService.deleteEmployeeById(id);
        if (isDeleted) {
            return ResponseEntity.ok(Boolean.TRUE);
        }
        return ResponseEntity.notFound().build();
    }




}
