package com.jorgetfg.backend.configuration;

import com.jorgetfg.backend.dto.ErrorDto;
import com.jorgetfg.backend.exceptions.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException ex){
        HttpStatusCode code = null;
        switch (ex.getMessage()) {
            case "Unknown user":
                code = HttpStatus.NOT_FOUND;
                break;
            case "Email already exists", "Invalid password", "No user with that id", "No folder with that id",
                    "No subscription with that id":
                code = HttpStatus.BAD_REQUEST;
                break;
        }
        return ResponseEntity.status(code)
                .body(ErrorDto.builder().message(ex.getMessage()).build());
    }

}
