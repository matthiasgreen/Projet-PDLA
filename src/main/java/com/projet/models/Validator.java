package com.projet.models;

public class Validator extends AbstractUser {
    public Validator(String username, String password) {
        super(username, password, UserRole.VALIDATOR);
    }

    public Validator(int id, String username, String password) {
        super(id, username, password, UserRole.VALIDATOR);
    }

    @Override
    public PostType getMainListPostType() {
        return PostType.MISSION;
    }

    @Override
    public PostType getCreatePostType() {
        return null;
    }

    @Override
    public boolean canValidateMissions() {
        return true;
    }

    
}
