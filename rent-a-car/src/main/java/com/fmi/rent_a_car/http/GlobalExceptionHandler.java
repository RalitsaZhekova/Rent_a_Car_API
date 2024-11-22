package com.fmi.rent_a_car.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {

        return AppResponse.error()
                .withMessage(ex.getMessage())
                .withCode(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {

        return AppResponse.error()
                .withMessage("An unexpected error occurred: " + ex.getMessage())
                .withCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

}
