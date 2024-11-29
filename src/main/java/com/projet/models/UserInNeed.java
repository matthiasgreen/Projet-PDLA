package com.projet.models;

public class UserInNeed extends AbstractUser {
    public UserInNeed(String username, String password) {
        super(username, password, UserRole.USER_IN_NEED);
    }

    public UserInNeed(int id, String username, String password) {
        super(id, username, password, UserRole.USER_IN_NEED);
    }

    @Override
    public PostType getMainListPostType() {
        return PostType.OFFER;
    }

    @Override
    public PostType getCreatePostType() {
        return PostType.MISSION;
    }

    @Override
    public boolean canValidateMissions() {
        return false;
    }
    
}
