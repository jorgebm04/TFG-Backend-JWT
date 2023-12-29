package com.jorgetfg.backend.exceptions;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException{

    private final HttpStatus code;

    public AppException(String messages, HttpStatus code) {
        super(messages);
        this.code = code;
    }

    public HttpStatus getCode() {
        return code;
    }
}
