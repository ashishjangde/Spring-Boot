package com.example.cacheinspringboot.services;

import com.example.cacheinspringboot.entities.EmployeeEntity;
import org.springframework.stereotype.Service;

@Service
public interface SalaryService {

    void createSalaryAccount(EmployeeEntity employeeId);
}
