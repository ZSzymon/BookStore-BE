package com.assigment.bookstore.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AlreadyPayedAdvice {

    @ResponseBody
    @ExceptionHandler(AlreadyPayedException.class)
    @ResponseStatus(HttpStatus.FOUND)
    String notFoundHandler(AlreadyPayedException ex) {
        return ex.getMessage();
    }
}
