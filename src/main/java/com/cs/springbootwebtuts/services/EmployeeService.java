package com.cs.springbootwebtuts.services;

import com.cs.springbootwebtuts.dto.EmployeeDto;
import com.cs.springbootwebtuts.entities.Employee;
import com.cs.springbootwebtuts.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<EmployeeDto> findEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.map(employee -> modelMapper.map(employee, EmployeeDto.class));
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
            Object converted = convertValueForField(requiredField, value);
            ReflectionUtils.setField(requiredField, employeeInDb, converted);
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

    private Object convertValueForField(Field field, Object value) {
        if (value == null) {
            return null;
        }
        Class<?> target = field.getType();

        // LocalDate handling (expects ISO date string like "2026-03-21")
        if (LocalDate.class.equals(target)) {
            if (value instanceof LocalDate) {
                return value;
            }
            String s = value.toString();
            return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
        }

        // Integer / int
        if (Integer.class.equals(target) || int.class.equals(target)) {
            if (value instanceof Number) return ((Number) value).intValue();
            return Integer.valueOf(value.toString());
        }

        // Long / long
        if (Long.class.equals(target) || long.class.equals(target)) {
            if (value instanceof Number) return ((Number) value).longValue();
            return Long.valueOf(value.toString());
        }

        // Boolean / boolean
        if (Boolean.class.equals(target) || boolean.class.equals(target)) {
            if (value instanceof Boolean) return value;
            return Boolean.valueOf(value.toString());
        }

        // String
        if (String.class.equals(target)) {
            return value.toString();
        }

        // fallback: attempt direct assignment
        return value;
    }
}
