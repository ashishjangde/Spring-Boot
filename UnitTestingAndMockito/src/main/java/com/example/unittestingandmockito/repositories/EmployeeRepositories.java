package com.example.unittestingandmockito.repositories;

import com.example.unittestingandmockito.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepositories extends JpaRepository<EmployeeEntity,Long> {
    EmployeeEntity findByEmail(String email);

}
