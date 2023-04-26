package com.akamai.technical.task.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertyNotExistException extends RuntimeException {
    public PropertyNotExistException(String msg) {
        super(msg);
        log.debug(msg);
    }
}
