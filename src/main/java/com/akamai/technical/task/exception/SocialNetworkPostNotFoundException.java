package com.akamai.technical.task.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SocialNetworkPostNotFoundException extends RuntimeException {
    public SocialNetworkPostNotFoundException(String msg) {
        super(msg);
        log.debug(msg);
    }
}
