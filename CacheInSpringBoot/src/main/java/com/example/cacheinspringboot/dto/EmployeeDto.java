package com.example.cacheinspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto implements Serializable {
    private long employeeId;

    private String employeeName;

    private String username;

    private String employeeEmail;

    private Long salary;
}
