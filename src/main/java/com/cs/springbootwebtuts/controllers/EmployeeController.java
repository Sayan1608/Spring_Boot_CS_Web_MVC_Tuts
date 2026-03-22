package com.cs.springbootwebtuts.controllers;

import com.cs.springbootwebtuts.dto.EmployeeDto;
import com.cs.springbootwebtuts.exceptions.ResourceNotFoundException;
import com.cs.springbootwebtuts.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable(name = "employeeId") Long id){
        Optional<EmployeeDto> optionalEmployeeDto = employeeService.findEmployeeById(id);
        return optionalEmployeeDto
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new ResourceNotFoundException("Employee with id " + id + " not found"));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.findAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createNewEmployee(@RequestBody @Valid EmployeeDto inputEmployee){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.createNewEmployee(inputEmployee)) ;
    }

    @PutMapping(path = {"/{employeeId}"})
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable(name = "employeeId") Long id,
                                                     @RequestBody @Valid EmployeeDto employeeDto){
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
