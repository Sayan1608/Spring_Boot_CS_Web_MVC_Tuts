package com.cs.springbootwebtuts.controllers;

import com.cs.springbootwebtuts.dto.DepartmentDto;
import com.cs.springbootwebtuts.exceptions.ResourceNotFoundException;
import com.cs.springbootwebtuts.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable(name = "departmentId") Long id){
        Optional<DepartmentDto> optionalDepartmentDto = departmentService.findDepartmentById(id);
        return optionalDepartmentDto
                .map(ResponseEntity::ok)
                .orElseThrow(()->new ResourceNotFoundException("Department with id " + id + " not found"));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments(){
        return new ResponseEntity<>(departmentService.findAllDepartments(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> createNewDepartment(@RequestBody @Valid DepartmentDto inputDepartment){
        return new ResponseEntity<>(departmentService.createNewDepartment(inputDepartment), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable(name = "departmentId") Long id,
                                                          @RequestBody @Valid DepartmentDto departmentDto) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentDto));
    }

    @DeleteMapping(path = "/{departmentId}")
    public ResponseEntity<Boolean> deleteDepartment(@PathVariable(name = "departmentId") Long id) {
        return ResponseEntity.ok(departmentService.deleteDepartment(id));
    }
}
