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

    public ExceptionResponse(String message) {
        this.message = message;
    }
}
