package com.akamai.technical.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ControllerAdvisor {


    @ExceptionHandler(PropertyNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handlePropertyNotExistException(PropertyNotExistException e) {
        return getExceptionResponseTemplate(
                "Property Not Found",
                Collections.singletonList(e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleDefaultException(Exception e) {
        return getExceptionResponseTemplate(
                "Internal Server Error",
                Collections.singletonList(e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(SocialNetworkPostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleSocialNetworkPostNotFoundException(SocialNetworkPostNotFoundException e) {
        return getExceptionResponseTemplate(
                "Post Not Found",
                Collections.singletonList(e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    /* Version with @ControllerAdvice -> There is no @ResponseBody annotation so there should be ResponseEntity wrapper
     @ExceptionHandler(SocialNetworkPostNotFoundException.class)
     public ResponseEntity<Object> handleUserNotFoundException(SocialNetworkPostNotFoundException e) {
        Map<String, Object> body = getExceptionResponseTemplate(e, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getFieldErrors()
                .stream()
                .map(el -> String.format("Invalid '%s' field: %s.", el.getField(), el.getDefaultMessage()))
                .toList();
        return getExceptionResponseTemplate(
                "Invalid Request Body",
                errorMessages,
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleHttpMessageConversionException(final HttpMessageConversionException ex) {
        return getExceptionResponseTemplate(
                "Unable to process request data.",
                Collections.singletonList(ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    private static Map<String, Object> getExceptionResponseTemplate(
            String title,
            List<String> errorMessages,
            HttpStatus httpStatus
    ) {
        Map<String, Object> body = new HashMap<>();
        body.put("title", title);
        body.put("status", httpStatus.value());
        body.put("errors", errorMessages);
        body.put("timestamp", new Date());
        return body;
    }
}
