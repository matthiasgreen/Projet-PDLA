package com.projet.models.user;

public enum UserRole {
    USER_IN_NEED,
    VOLUNTEER,
    VALIDATOR;

    public static UserRole fromString(String name) {
        return UserRole.valueOf(name.toUpperCase());
    }
}


