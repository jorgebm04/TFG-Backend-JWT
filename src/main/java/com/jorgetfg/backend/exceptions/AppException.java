package com.jorgetfg.backend.exceptions;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException{

    public AppException(String messages) {
        super(messages);
    }

}
