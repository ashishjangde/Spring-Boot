package com.example.unittestingandmockito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private long id;
    private String name;
    private String email;
    private Long salary;
}
