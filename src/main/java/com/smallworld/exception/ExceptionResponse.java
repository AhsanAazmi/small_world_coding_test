package com.smallworld.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString

public class ExceptionResponse {

    private String message;
    private HttpStatus status;

    public ExceptionResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public ExceptionResponse(String message) {
        this.message = message;
    }

    public static ExceptionResponse of(String message) {
        return new ExceptionResponse(message, HttpStatus.BAD_REQUEST);
    }

    public static ExceptionResponse of(String message, HttpStatus status) {
        return new ExceptionResponse(message, status);
    }

}
