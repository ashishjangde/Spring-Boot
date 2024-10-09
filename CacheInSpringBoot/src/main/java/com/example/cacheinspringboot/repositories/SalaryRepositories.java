package com.example.cacheinspringboot.repositories;

import com.example.cacheinspringboot.entities.SalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepositories extends JpaRepository<SalaryEntity,Long> {

    SalaryEntity findByEmployeeEmployeeId(long employeeId);
}
