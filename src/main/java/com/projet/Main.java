package com.projet;

import java.awt.Dimension;

import javax.swing.*;

import com.projet.controllers.PostController;
import com.projet.controllers.UserController;
import com.projet.views.MyPostListView;
import com.projet.views.PostCreateView;
import com.projet.views.PostListView;
import com.projet.views.PostView;
import com.projet.views.ViewManager;
import com.projet.views.LoginView;

public class Main {

    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("Projet");
        frame.setPreferredSize(new Dimension(1000, 800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create the views
        LoginView loginView = new LoginView();
        PostView selectedPostView = new PostView();
        PostView mySelectedPostView = new PostView();
        PostListView postListView = new PostListView(selectedPostView);
        PostCreateView postCreateView = new PostCreateView(false);
        MyPostListView myPostListView = new MyPostListView(mySelectedPostView);
        ViewManager viewManager = new ViewManager(
            loginView,
            postListView,
            postCreateView,
            myPostListView
        );
        frame.add(viewManager);

        // Create the controllers
        PostController postController = new PostController(
            selectedPostView,
            mySelectedPostView,
            postListView,
            myPostListView,
            postCreateView,
            viewManager
        );
        UserController userController = new UserController(
            loginView,
            viewManager,
            postController
        );

        viewManager.showLoginView();
        
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
