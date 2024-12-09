package com.inserm.user_auth_service.exception;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
} 