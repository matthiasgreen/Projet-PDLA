package com.projet.controllers;

import com.projet.models.User;

public class PostController {
    private User loggedInUser;

    public void setLoggedInUser(User user) {
        loggedInUser = user;
    }
}
