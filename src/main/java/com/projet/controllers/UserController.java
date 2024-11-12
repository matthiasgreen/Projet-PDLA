package com.projet.controllers;

import java.sql.SQLException;

import com.projet.database.IncorrectCredentialsException;
import com.projet.database.UserAlreadyExistsException;
import com.projet.models.User;
import com.projet.models.UserRole;
import com.projet.views.ViewManager;
import com.projet.views.LoginView;

public class UserController {
    private LoginView loginView;
    private ViewManager viewManager;

    private PostController postController;
    
    public UserController(LoginView loginView, ViewManager viewManager, PostController postController) {
        this.loginView = loginView;
        this.viewManager = viewManager;
        this.postController = postController;
        loginView.setUserController(this);
    }

    public void signUp(String username, String password, UserRole role) {
        User user = new User(username, password, role);
        try {
            user.toDB();
            postController.setLoggedInUser(user);
            loginView.onSuccess();
            viewManager.showPostListView();
        } catch (SQLException e) {
            loginView.onError("An error occurred while trying to sign up.");
            e.printStackTrace();
        } catch (UserAlreadyExistsException e) {
            loginView.onError("Username already exists.");
        }
    }

    public void login(String username, String password) {
        try {
            User user = User.loginFromDB(username, password);
            loginView.onSuccess();
            postController.setLoggedInUser(user);
            viewManager.showPostListView();
        } catch (SQLException e) {
            loginView.onError("An error occurred while trying to log in.");
            e.printStackTrace();
            return;
        } catch (IncorrectCredentialsException e) {
            loginView.onError("Incorrect username or password.");
            return;
        }
    }
}
