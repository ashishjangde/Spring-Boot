package com.example.unittestingandmockito.services;

import com.example.unittestingandmockito.dto.EmployeeDto;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeeDto> getAllEmployees();
    Optional<EmployeeDto> getEmployeeById(long employeeId);
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    EmployeeDto updateEmployee(long employeeId, EmployeeDto employeeDto);
    EmployeeDto updatePartialEmployeeById(long employeeId, Map<String,Object> employee);
    boolean deleteEmployeeById(long employeeId);
}
