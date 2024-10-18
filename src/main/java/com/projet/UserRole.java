package com.projet;

public enum UserRole {
    USER,
    VOLUNTEER;

    public static UserRole fromString(String name) {
        return UserRole.valueOf(name.toUpperCase());
    }
}


