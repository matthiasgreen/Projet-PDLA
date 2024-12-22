package com.projet.controllers;

import java.sql.SQLException;

import com.projet.models.user.AbstractUser;
import com.projet.models.user.IncorrectCredentialsException;
import com.projet.models.user.UserAlreadyExistsException;
import com.projet.models.user.UserInNeed;
import com.projet.models.user.UserRole;
import com.projet.models.user.Validator;
import com.projet.models.user.Volunteer;
import com.projet.views.NavigationBar;
import com.projet.views.SignupView;
import com.projet.views.LoginView;
import com.projet.views.UserView;

public class UserController {
    private LoginView loginView;
    private SignupView signupView;
    private UserView userView;
    private NavigationBar navBar;

    private PostController postController;
    private ReviewController reviewController;
    
    public UserController(LoginView loginView, SignupView signupView, UserView userView, NavigationBar navBar, PostController postController, ReviewController reviewController) {
        this.loginView = loginView;
        this.signupView = signupView;
        this.navBar = navBar;
        this.postController = postController;
        this.reviewController = reviewController;
        this.userView = userView;
        loginView.setUserController(this);
        signupView.setUserController(this);
        userView.setUserController(this);
        navBar.showLogin(true);
    }

    private void afterLoginOrSignUp(AbstractUser user) {
        postController.setLoggedInUser(user);
        reviewController.setLoggedInUser(user);
        navBar.showLogin(false);
        displayUsers();
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
            afterLoginOrSignUp(user);
            signupView.showError("");
        } catch (SQLException e) {
            signupView.showError("An error occurred while trying to sign up.");
            e.printStackTrace();
        } catch (UserAlreadyExistsException e) {
            signupView.showError("Username already exists.");
        }
    }

    public void login(String username, String password) {
        try {
            AbstractUser user = AbstractUser.loginFromDB(username, password);
            afterLoginOrSignUp(user);
            loginView.onError("");
        } catch (SQLException e) {
            loginView.onError("An error occurred while trying to log in.");
            e.printStackTrace();
            return;
        } catch (IncorrectCredentialsException e) {
            loginView.onError("Incorrect username or password.");
            return;
        }
    }

    public void selectUser(AbstractUser user) {
        reviewController.displayReviews(user);
    }

    public void displayUsers() {
        try {
            userView.displayUsers(AbstractUser.getAllFromDatabase());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
