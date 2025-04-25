package com.guisebastiao.api.exceptions;

public class RequiredAuthenticationException extends RuntimeException {
    public RequiredAuthenticationException(String message) {
        super(message);
    }
}