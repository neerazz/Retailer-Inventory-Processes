package com.hackerrank.sample.excpetion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(NoSuchResourceFoundException.class)
    public ResponseEntity handle(NoSuchResourceFoundException exc){
        return new ResponseEntity(exc.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadResourceRequestException.class)
    public ResponseEntity handle(BadResourceRequestException exc){
        return new ResponseEntity(exc.getMessage(), HttpStatus.NOT_FOUND);
    }
}
