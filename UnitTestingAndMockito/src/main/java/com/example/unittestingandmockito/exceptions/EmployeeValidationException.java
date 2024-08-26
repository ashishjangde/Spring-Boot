package com.example.unittestingandmockito.exceptions;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class EmployeeValidationException extends RuntimeException {

    private final List<Map<String, String>> errors;

    public EmployeeValidationException(List<Map<String, String>> errors) {
        this.errors = errors;
    }





}
