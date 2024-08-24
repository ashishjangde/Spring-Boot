package com.example.unittestingandmockito.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class EmployeeValidationException extends RuntimeException {

    private final List<String> errors;
    public EmployeeValidationException(List<String> errors) {
        super("Employee validation failed");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }



}
