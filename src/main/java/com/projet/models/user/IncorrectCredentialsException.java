package com.projet.models.user;

public class IncorrectCredentialsException extends Exception {
    public IncorrectCredentialsException(String message) {
        super(message);
    }
}
