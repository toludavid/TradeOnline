package com.stanbic.redbox.fincale.online.rest.service.exception;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super(message);
    }
}