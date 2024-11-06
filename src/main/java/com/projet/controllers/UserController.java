package com.projet.controllers;

import com.projet.models.User;
import com.projet.models.UserRole;
import com.projet.views.HomeComponent;
import com.projet.views.LoginComponent;

public class UserController {
    private LoginComponent loginView;
    private HomeComponent mainView;

    private PostController postController;
    
    public UserController(LoginComponent loginView, HomeComponent homeView) {
        this.loginView = loginView;
        this.mainView = homeView;
    }

    public void signUp(String username, String password, UserRole role) {
        User user = new User(username, password, role);
        try {
            user.toDB();
            loginView.onSuccess();
            postController.setLoggedInUser(user);
            mainView.onLogin();
        } catch (Exception e) {
            loginView.onError("Something went wrong while signing you up :" + e.getMessage());
        }
    }

    public void login(String username, String password) {
        try {
            User user = User.loginFromDB(username, password);
            loginView.onSuccess();
            postController.setLoggedInUser(user);
            mainView.onLogin();
        } catch (Exception e) {
            loginView.onError("Something went wrong while logging you in :" + e.getMessage());
        }
    }
}
