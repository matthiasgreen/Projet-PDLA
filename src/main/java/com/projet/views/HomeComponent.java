package com.projet.views;

import java.awt.CardLayout;

import javax.swing.*;

import com.projet.TogglePostCreateListener;
import com.projet.models.User;
import com.projet.models.UserRole;

public class HomeComponent extends JPanel implements TogglePostCreateListener {
    private JPanel cardPanel;
    private String currentView;
    private PostListView postListView;
    private PostCreateView postCreateView;
    private MyPostListView myPostListView;

    public HomeComponent() {
        CardLayout cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel);

        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.X_AXIS));
        postListView = new PostListView(this);
        postPanel.add(postListView);
        // Ajouter MyPostListView
        myPostListView = new MyPostListView();
        postPanel.add(myPostListView);
        
        cardPanel.add(postPanel, "postList");
        
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

    public void onLogin() {
        // TODO Change login component to be child of this view
    }
}
