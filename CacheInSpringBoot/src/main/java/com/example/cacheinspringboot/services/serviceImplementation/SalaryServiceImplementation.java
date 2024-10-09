package com.example.cacheinspringboot.services.serviceImplementation;

import com.example.cacheinspringboot.entities.EmployeeEntity;
import com.example.cacheinspringboot.entities.SalaryEntity;
import com.example.cacheinspringboot.repositories.SalaryRepositories;
import com.example.cacheinspringboot.services.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)  // default set in the transactional method
public class SalaryServiceImplementation implements SalaryService {
    private final SalaryRepositories salaryRepositories;


    @Override
    public void createSalaryAccount(EmployeeEntity employee) {
        SalaryEntity salaryEntity = SalaryEntity.builder()
                .balance(0)
                .employee(employee)
                .build();
        salaryRepositories.save(salaryEntity);
//       throw  new RuntimeException("Some error occurred ");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED ,isolation = Isolation.SERIALIZABLE)
    public SalaryEntity updateSalary(long employeeId) {
        SalaryEntity salaryEntity = salaryRepositories.findByEmployeeEmployeeId(employeeId);
        if (salaryEntity == null) throw  new RuntimeException("Salary account no found");
        double prevBalance = salaryEntity.getBalance();
        double newBalance =  prevBalance + 1L;
        salaryEntity.setBalance(newBalance);
        salaryRepositories.save(salaryEntity);
        return salaryEntity;
    }
}
