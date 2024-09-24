package com.example.cacheinspringboot.services.serviceImplementation;

import com.example.cacheinspringboot.entities.EmployeeEntity;
import com.example.cacheinspringboot.entities.SalaryEntity;
import com.example.cacheinspringboot.repositories.SalaryRepositories;
import com.example.cacheinspringboot.services.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalaryServiceImplementation implements SalaryService {
    private final SalaryRepositories salaryRepositories;


    @Override
    public void createSalaryAccount(EmployeeEntity employee) {
        SalaryEntity salaryEntity = SalaryEntity.builder()
                .balance(0)
                .employee(employee)
                .build();
        salaryRepositories.save(salaryEntity);
    }
}
