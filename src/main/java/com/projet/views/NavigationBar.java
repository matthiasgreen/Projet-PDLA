package com.projet.views;

import java.awt.CardLayout;

import javax.swing.*;

public class NavigationBar extends JPanel {
    private static final String LOGIN = "login";
    private static final String SIGN_UP = "signUp";
    private static final String OTHER_POSTS = "otherPosts";
    private static final String MY_POSTS = "myPosts";
    private static final String POST_CREATE = "postCreate";
    private static final String USERS = "users";
    
    private JPanel page;
    private CardLayout cardLayout;

    private JPanel navBar;
    private ButtonGroup navigationButtons;
    private JRadioButton loginButton;
    private JRadioButton signUpButton;
    private JRadioButton otherPostsButton;
    private JRadioButton myPostsButton;
    private JRadioButton postCreateButton;
    private JRadioButton usersButton;

    private void createNavBar() {
        navigationButtons = new ButtonGroup();

        loginButton = new JRadioButton("Login");
        loginButton.setActionCommand(LOGIN);
        loginButton.addActionListener(e -> {
            cardLayout.show(page, LOGIN);
        });

        signUpButton = new JRadioButton("Sign Up");
        signUpButton.setActionCommand(SIGN_UP);
        signUpButton.addActionListener(e -> {
            cardLayout.show(page, SIGN_UP);
        });

        otherPostsButton = new JRadioButton("Other Posts");
        otherPostsButton.setActionCommand(OTHER_POSTS);
        otherPostsButton.addActionListener(e -> {
            cardLayout.show(page, OTHER_POSTS);
        });

        myPostsButton = new JRadioButton("My Posts");
        myPostsButton.setActionCommand(MY_POSTS);
        myPostsButton.addActionListener(e -> {
            cardLayout.show(page, MY_POSTS);
        });

        postCreateButton = new JRadioButton("Create Post");
        postCreateButton.setActionCommand(POST_CREATE);
        postCreateButton.addActionListener(e -> {
            cardLayout.show(page, POST_CREATE);
        });

        usersButton = new JRadioButton("Users");
        usersButton.setActionCommand(USERS);
        usersButton.addActionListener(e -> {
            cardLayout.show(page, USERS);
        });

        navigationButtons.add(loginButton);
        navigationButtons.add(signUpButton);
        navigationButtons.add(otherPostsButton);
        navigationButtons.add(myPostsButton);
        navigationButtons.add(postCreateButton);
        navigationButtons.add(usersButton);
        
        navBar = new JPanel();
        navBar.setLayout(new BoxLayout(navBar, BoxLayout.X_AXIS));
        add(navBar);
    }

    public void showLogin(boolean show) {
        if (!show) {
            otherPostsButton.setSelected(true);
            cardLayout.show(page, OTHER_POSTS);
            navBar.remove(loginButton);
            navBar.remove(signUpButton);
            navBar.add(otherPostsButton);
            navBar.add(myPostsButton);
            navBar.add(postCreateButton);
            navBar.add(usersButton);
        } else {
            loginButton.setSelected(true);
            cardLayout.show(page, LOGIN);
            navBar.add(loginButton);
            navBar.add(signUpButton);
            navBar.remove(otherPostsButton);
            navBar.remove(myPostsButton);
            navBar.remove(postCreateButton);
            navBar.remove(usersButton);
        }

        
    }

    public NavigationBar(
        LoginView loginView,
        SignupView signupView,
        PostListView postListView,
        MyPostListView myPostListView,
        PostCreateView postCreateView,
        UserView userView,
        ReviewView reviewView
    ) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        createNavBar();

        page = new JPanel();

        cardLayout = new CardLayout();
        page.setLayout(cardLayout);

        page.add(loginView, LOGIN);
        page.add(signupView, SIGN_UP);
        page.add(postListView, OTHER_POSTS);
        page.add(myPostListView, MY_POSTS);
        page.add(postCreateView, POST_CREATE);

        JPanel userReviewPanel = new JPanel();
        userReviewPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        userReviewPanel.setLayout(new BoxLayout(userReviewPanel, BoxLayout.X_AXIS));
        userReviewPanel.add(userView);
        userReviewPanel.add(reviewView);

        page.add(userReviewPanel, USERS);

        add(page);
    }
}
