package com.hackerrank.sample.excpetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchResourceFoundException extends RuntimeException {
    public NoSuchResourceFoundException(String msg) {
        super(msg);
    }
}