package com.projet.views;

import java.awt.CardLayout;

import javax.swing.*;

public class ViewManager extends JPanel {
    private CardLayout cardLayout;

    public ViewManager(
        LoginView loginView,
        PostListView postListView,
        PostCreateView postCreateView,
        MyPostListView myPostListView
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
        add(postViews, "postList");
    }

    public void showLoginView() {
        cardLayout.show(this, "login");
    }

    public void showPostListView() {
        cardLayout.show(this, "postList");
    }

    public void showPostCreateView() {
        cardLayout.show(this, "postCreate");
    }
}
