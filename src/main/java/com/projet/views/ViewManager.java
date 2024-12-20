package com.projet.views;

import java.awt.CardLayout;

import javax.swing.*;

public class ViewManager extends JPanel {
    private CardLayout cardLayout;

    public ViewManager(
        LoginView loginView,
        PostListView postListView,
        PostCreateView postCreateView,
        MyPostListView myPostListView,
        UserView userView,
        ReviewView reviewView
    ) {        
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Either show:
        // - login view
        // - post list + my post list
        // - post create view
        add(loginView, "login");
        add(postCreateView, "postCreate");

        JPanel postViews = new JPanel();
        postViews.setLayout(new BoxLayout(postViews, BoxLayout.X_AXIS));
        postViews.add(postListView);
        postViews.add(myPostListView);

        JPanel userViews = new JPanel();
        userViews.setLayout(new BoxLayout(userViews, BoxLayout.X_AXIS));
        userViews.add(userView);
        userViews.add(reviewView);

        JPanel homeView = new JPanel();
        homeView.setLayout(new BoxLayout(homeView, BoxLayout.Y_AXIS));
        homeView.add(userViews);
        homeView.add(postViews);
        add(homeView, "homeView");
    }

    public void showLoginView() {
        cardLayout.show(this, "login");
    }

    public void showHomeView() {
        cardLayout.show(this, "homeView");
    }

    public void showPostCreateView() {
        cardLayout.show(this, "postCreate");
    }
}
