package com.cs.springbootwebtuts.services;

import com.cs.springbootwebtuts.dto.EmployeeDto;
import com.cs.springbootwebtuts.entities.Employee;
import com.cs.springbootwebtuts.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDto findEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        return modelMapper.map(employee, EmployeeDto.class);
    }

    public List<EmployeeDto> findAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList
                .stream()
                .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .toList();
    }

    public EmployeeDto createNewEmployee(Employee inputEmployee) {
        Employee savedEmployee = employeeRepository.save(inputEmployee);
        return modelMapper.map(savedEmployee,EmployeeDto.class);
    }
}
