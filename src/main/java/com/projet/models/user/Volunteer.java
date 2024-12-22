package com.projet.models.user;

import com.projet.models.post.PostType;

public class Volunteer extends AbstractUser {

    public Volunteer(int id, String username, String password) {
        super(id, username, password, UserRole.VOLUNTEER);
    }

    public Volunteer(String username, String password) {
        super(username, password, UserRole.VOLUNTEER);
    }

    @Override
    public PostType getMainListPostType() {
        return PostType.MISSION;
    }

    @Override
    public PostType getCreatePostType() {
        return PostType.OFFER;
    }

    @Override
    public boolean canValidateMissions() {
        return false;
    }

}
