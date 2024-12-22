package com.projet;

import java.awt.Dimension;

import javax.swing.*;

import com.projet.controllers.PostController;
import com.projet.controllers.ReviewController;
import com.projet.controllers.UserController;
import com.projet.views.MyPostListView;
import com.projet.views.PostCreateView;
import com.projet.views.PostListView;
import com.projet.views.PostView;
import com.projet.views.ReviewView;
import com.projet.views.SignupView;
import com.projet.views.UserView;
import com.projet.views.NavigationBar;
import com.projet.views.LoginView;

public class Main {

    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("Projet");
        frame.setPreferredSize(new Dimension(1000, 800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create the views
        LoginView loginView = new LoginView();
        SignupView signupView = new SignupView();
        PostView selectedPostView = new PostView();
        PostView mySelectedPostView = new PostView();
        PostListView postListView = new PostListView(selectedPostView);
        PostCreateView postCreateView = new PostCreateView(false);
        MyPostListView myPostListView = new MyPostListView(mySelectedPostView);
        UserView userView = new UserView();
        ReviewView reviewView = new ReviewView();
        NavigationBar navBar = new NavigationBar(
            loginView,
            signupView,
            postListView,
            myPostListView,
            postCreateView,
            userView,
            reviewView
        );
        frame.add(navBar);

        // Create the controllers
        ReviewController reviewController = new ReviewController(reviewView);
        PostController postController = new PostController(
            selectedPostView,
            mySelectedPostView,
            postListView,
            myPostListView,
            postCreateView
        );
        UserController userController = new UserController(
            loginView,
            signupView,
            userView,
            navBar,
            postController,
            reviewController
        );

        navBar.showLogin(true);
        
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
