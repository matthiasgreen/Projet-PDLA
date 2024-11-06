package com.projet.controllers;

import com.projet.models.User;
import com.projet.views.MyPostListView;
import com.projet.views.PostCreateView;
import com.projet.views.PostListView;
import com.projet.views.PostView;
import com.projet.views.ViewManager;

public class PostController {
    private User loggedInUser;
    private ViewManager viewManager;

    public PostController(PostView selectedPostView, PostView mySelectedPostView, PostListView postListView,
            MyPostListView myPostListView, PostCreateView postCreateView, ViewManager viewManager) {
        selectedPostView.setPostController(this);
        mySelectedPostView.setPostController(this);
        postListView.setPostController(this);
        myPostListView.setPostController(this);
        postCreateView.setPostController(this);
        this.viewManager = viewManager;
    }

    public void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public void mainListNextPage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mainListNextPage'");
    }

    public void mainListPreviousPage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mainListPreviousPage'");
    }

    public void myListNextPage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mainListNextPage'");
    }

    public void myListPreviousPage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mainListPreviousPage'");
    }

    public void togglePostCreate() {
        viewManager.showPostCreateView();
    }
}
