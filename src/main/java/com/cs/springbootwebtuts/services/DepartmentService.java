package com.cs.springbootwebtuts.services;

import com.cs.springbootwebtuts.dto.DepartmentDto;
import com.cs.springbootwebtuts.entities.Department;
import com.cs.springbootwebtuts.exceptions.ResourceNotFoundException;
import com.cs.springbootwebtuts.repositories.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    @Autowired
    private ModelMapper modelMapper;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    public Optional<DepartmentDto> findDepartmentById(Long id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment
                .map(department->
                        modelMapper.map(department, DepartmentDto.class));
    }

    public List<DepartmentDto> findAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments
                .stream()
                .map(department -> modelMapper.map(department, DepartmentDto.class))
                .toList();
    }

    public DepartmentDto createNewDepartment(DepartmentDto inputDepartment) {
        Department department = modelMapper.map(inputDepartment, Department.class);
        Department savedDepartment = departmentRepository.save(department);
        return modelMapper.map(savedDepartment, DepartmentDto.class);
    }

    public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto) {
        isExistsDepartmentById(id);
        Department department = modelMapper.map(departmentDto, Department.class);
        department.setId(id);
        Department updatedDepartment = departmentRepository.save(department);
        return modelMapper.map(updatedDepartment, DepartmentDto.class);
    }

    public void isExistsDepartmentById(Long id){
        boolean existsById = departmentRepository.existsById(id);
        if(!existsById) throw new ResourceNotFoundException("Department with id " + id + " not found");
    }

    public Boolean deleteDepartment(Long id) {
        isExistsDepartmentById(id);
        departmentRepository.deleteById(id);
        return true;
    }
}
