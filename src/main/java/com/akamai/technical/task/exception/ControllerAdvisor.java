package com.akamai.technical.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {


    @ExceptionHandler(PropertyNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handlePropertyNotExistException(PropertyNotExistException e) {
        Map<String, Object> body = getExceptionResponseTemplate(e, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(SocialNetworkPostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleUserNotFoundException(SocialNetworkPostNotFoundException e) {
        Map<String, Object> body = getExceptionResponseTemplate(e, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }


    private static Map<String, Object> getExceptionResponseTemplate(Exception ex, HttpStatus httpStatus) {
        Map<String, Object> body = new HashMap<>();
        body.put("title", httpStatus.name());
        body.put("status", httpStatus.value());
        body.put("error", ex.getMessage());
        body.put("timestamp", new Date());
        return body;
    }
}
