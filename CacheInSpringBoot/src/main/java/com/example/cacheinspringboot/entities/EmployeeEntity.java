package com.example.cacheinspringboot.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "EmployeeDetails")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeId;

    private String employeeName;

    private String username;

    private String employeeEmail;

    private String employeePassword;

    private Long salary;

}
