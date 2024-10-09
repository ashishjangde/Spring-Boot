package com.example.cacheinspringboot.services;

import com.example.cacheinspringboot.entities.EmployeeEntity;
import com.example.cacheinspringboot.entities.SalaryEntity;
import org.springframework.stereotype.Service;

@Service
public interface SalaryService {

    void createSalaryAccount(EmployeeEntity employeeId);

    SalaryEntity updateSalary(long employeeId);
}
