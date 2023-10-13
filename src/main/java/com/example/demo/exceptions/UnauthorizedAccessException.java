package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException(String name) {
        super("This: " + name + ", cannot access to this method" );
    }
}
