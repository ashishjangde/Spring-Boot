package com.example.unittestingandmockito.exceptions;

public class ExistingResourceFoundException extends RuntimeException {
    public ExistingResourceFoundException(String message) {
        super(message);
    }
}
