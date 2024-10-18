package com.projet;

import java.awt.CardLayout;

import javax.swing.*;

public class HomeComponent extends JPanel implements TogglePostCreateListener {
    private User loggedInUser;
    private JPanel cardPanel;
    private String currentView;
    private PostListView postListView;
    private PostCreateView postCreateView;

    public HomeComponent() {
        CardLayout cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel);

        postListView = new PostListView(this, false);
        cardPanel.add(postListView, "postList");
        
        postCreateView = new PostCreateView(this, false);
        cardPanel.add(postCreateView, "postCreate");
        
        currentView = "postList";
        cardLayout.show(cardPanel, currentView);
    }

    @Override
    public void onTogglePostCreate() {
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
        if (currentView.equals("postList")) {
            currentView = "postCreate";
            
        } else {
            currentView = "postList";
            // When switching to postlist, reload the posts
            postListView.loadPosts();
            postListView.renderList();
        }
        cardLayout.show(cardPanel, currentView);
    }

    public void setLoggedInUser(User user) {
        loggedInUser = user;
        postCreateView.loggedInUser = user;
    }
}
