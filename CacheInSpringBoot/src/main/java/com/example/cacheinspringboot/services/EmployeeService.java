package com.example.cacheinspringboot.services;

import com.example.cacheinspringboot.dto.EmployeeDto;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Optional<EmployeeDto> getEmployeeById(long id);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    EmployeeDto updateEmployee(long id, EmployeeDto employeeDto);
    boolean deleteEmployee(long id);
}
