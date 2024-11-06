package com.projet.models;

public enum UserRole {
    USER,
    VOLUNTEER,
    VALIDATOR;

    public static UserRole fromString(String name) {
        return UserRole.valueOf(name.toUpperCase());
    }
}


