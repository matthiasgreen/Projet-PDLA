package com.projet.controllers;

import java.sql.SQLException;

import com.projet.database.IncorrectCredentialsException;
import com.projet.database.UserAlreadyExistsException;
import com.projet.models.AbstractUser;
import com.projet.models.UserInNeed;
import com.projet.models.UserRole;
import com.projet.models.Validator;
import com.projet.models.Volunteer;
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
        AbstractUser user;
        if (role == UserRole.USER_IN_NEED) {
            user = new UserInNeed(username, password);
        } else if (role == UserRole.VOLUNTEER) {
            user = new Volunteer(username, password);
        } else {
            user = new Validator(username, password);
        }
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
            AbstractUser user = AbstractUser.loginFromDB(username, password);
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
