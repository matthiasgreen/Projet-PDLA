package com.projet;

import java.awt.CardLayout;

import javax.swing.*;

public class HomeComponent extends JPanel implements TogglePostCreateListener {
    private JPanel cardPanel;
    private String currentView;
    private PostListView postListView;
    private PostCreateView postCreateView;

    public HomeComponent() {
        CardLayout cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel);

        postListView = new PostListView(this);
        cardPanel.add(postListView, "postList");
        
        postCreateView = new PostCreateView(this,true);
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
        postCreateView.loggedInUser = user;
        if (user.role.equals(UserRole.VOLUNTEER)) {
            postListView.setIsOffers(false);
            postCreateView.setOffers(true);
        } else {
            postListView.setIsOffers(true);
            postCreateView.setOffers(false);
        }
    }
}
