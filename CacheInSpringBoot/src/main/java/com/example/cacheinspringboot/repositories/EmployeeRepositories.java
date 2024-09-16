package com.example.cacheinspringboot.repositories;

import com.example.cacheinspringboot.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepositories extends JpaRepository<EmployeeEntity,Long> {
}
