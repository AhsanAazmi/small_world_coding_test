package com.smallworld.exception;

public class ExceptionDecorator {

    private ExceptionDecorator() {
    }

    public static ExceptionResponse create(Exception e) {
        return new ExceptionResponse(e.getMessage());
    }

}
