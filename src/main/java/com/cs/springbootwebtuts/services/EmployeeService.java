package com.cs.springbootwebtuts.services;

import com.cs.springbootwebtuts.dto.EmployeeDto;
import com.cs.springbootwebtuts.entities.Employee;
import com.cs.springbootwebtuts.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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

    public boolean isExistsEmployeeById(Long id){
        return employeeRepository.existsById(id);
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        boolean existsEmployeeById = isExistsEmployeeById(id);
        if(!existsEmployeeById) return null;
        Employee employeeInDb = modelMapper.map(employeeDto, Employee.class);
        employeeInDb.setId(id);
        Employee updatedEmployee = employeeRepository.save(employeeInDb);
        return modelMapper.map(updatedEmployee, EmployeeDto.class);
    }

    public EmployeeDto partiallyUpdateEmployee(Long id, Map<String, Object> updates) {
        boolean existsEmployeeById = isExistsEmployeeById(id);
        if(!existsEmployeeById) return null;
        Employee employeeInDb = employeeRepository.findById(id).get();
        updates.forEach((field, value)->{
            Field requiredField = ReflectionUtils.getRequiredField(Employee.class, field);
            requiredField.setAccessible(true);
            ReflectionUtils.setField(requiredField, employeeInDb, value);
        });
        Employee updatedEmployee = employeeRepository.save(employeeInDb);
        return modelMapper.map(updatedEmployee, EmployeeDto.class);
    }

    public boolean deleteEmployeeById(Long id) {
        boolean existsEmployeeById = isExistsEmployeeById(id);
        if(!existsEmployeeById) return false;
        employeeRepository.deleteById(id);
        return true;
    }
}
