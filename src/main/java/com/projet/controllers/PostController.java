package com.projet.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.projet.models.Mission;
import com.projet.models.MissionStatus;
import com.projet.models.Offer;
import com.projet.models.Post;
import com.projet.models.User;
import com.projet.models.UserRole;
import com.projet.views.MyPostListView;
import com.projet.views.PostCreateView;
import com.projet.views.PostListView;
import com.projet.views.PostView;
import com.projet.views.ViewManager;

public class PostController {
    private PostView selectedPostView;
    private PostView mySelectedPostView;
    private PostListView postListView;
    private MyPostListView myPostListView;
    private PostCreateView postCreateView;
    private ViewManager viewManager;
    
    private User loggedInUser;
    private int mainPage = 0;
    private String currentView = "list";
    private boolean showOffers;

    public PostController(PostView selectedPostView, PostView mySelectedPostView, PostListView postListView,
            MyPostListView myPostListView, PostCreateView postCreateView, ViewManager viewManager) {
        this.selectedPostView = selectedPostView;
        this.mySelectedPostView = mySelectedPostView;
        this.postListView = postListView;
        this.myPostListView = myPostListView;
        this.postCreateView = postCreateView;

        selectedPostView.setPostController(this);
        mySelectedPostView.setPostController(this);
        postListView.setPostController(this);
        myPostListView.setPostController(this);
        postCreateView.setPostController(this);
        this.viewManager = viewManager;
    }

    public void setLoggedInUser(User user) {
        loggedInUser = user;
        showOffers = user.getRole() == UserRole.USER;
        postListView.setIsOffers(showOffers);
        myPostListView.setIsOffers(!showOffers);
        postCreateView.setIsOffers(!showOffers);
        try {
            mainListSetPosts();
            myListSetPosts();
        } catch (SQLException e) {
            postListView.setError("Error loading posts: " + e.getMessage());
            myPostListView.setError("Error loading posts: " + e.getMessage());
        }
    }

    private boolean isMainPageOk(int newPage) throws SQLException {
        return newPage >= 0 && newPage < (showOffers ? Offer.getNumberOfPages() : Mission.getNumberOfPages());
    }

    private boolean isMyPageOk(int newPage) throws SQLException {
        return newPage >= 0 && newPage < (showOffers ? Mission.getMyNumberOfPages(loggedInUser) : Offer.getMyNumberOfPages(loggedInUser));
    }

    public void mainListNextPage() {
        try {
            if (isMainPageOk(mainPage + 1)) {
                mainPage++;
                mainListSetPosts();
            } else {
                postListView.setError("No more pages");
            }
        } catch (SQLException e) {
            postListView.setError("Error loading posts: " + e.getMessage());
        }
    }

    public void mainListPreviousPage() {
        try {
            if (isMainPageOk(mainPage - 1)) {
                mainPage--;
                mainListSetPosts();
            } else {
                postListView.setError("No more pages");
            }
        } catch (SQLException e) {
            postListView.setError("Error loading posts: " + e.getMessage());
        }
    }

    public void myListNextPage() {
        try {
            if (isMyPageOk(mainPage + 1)) {
                mainPage++;
                myListSetPosts();
            } else {
                myPostListView.setError("No more pages");
            }
        } catch (SQLException e) {
            myPostListView.setError("Error loading posts: " + e.getMessage());
        }
    }

    public void myListPreviousPage() {
        try {
            if (isMyPageOk(mainPage - 1)) {
                mainPage--;
                myListSetPosts();
            } else {
                myPostListView.setError("No more pages");
            }
        } catch (SQLException e) {
            myPostListView.setError("Error loading posts: " + e.getMessage());
        }
    }

    public void togglePostCreate() {
        if (currentView.equals("list")) {
            viewManager.showPostCreateView();
            currentView = "create";
        } else {
            viewManager.showPostListView();
            currentView = "list";
        }
    }

    private void mainListSetPosts() throws SQLException {
        List<Post> posts = new ArrayList<>();
        if (showOffers) {
            ArrayList<Offer> offers = Offer.getOffers(mainPage);
            for (Offer offer : offers) {
                posts.add(offer);
            }
        } else {
            ArrayList<Mission> missions = Mission.getMissions(mainPage);
            for (Mission mission : missions) {
                posts.add(mission);
            }
        }
        postListView.showPosts(posts);
    }

    private void myListSetPosts() throws SQLException {
        List<Post> posts = new ArrayList<>();
        if (!showOffers) {
            ArrayList<Offer> offers = Offer.getMyOffers(loggedInUser, mainPage);
            for (Offer offer : offers) {
                posts.add(offer);
            }
        } else {
            ArrayList<Mission> missions = Mission.getMyMissions(loggedInUser, mainPage);
            for (Mission mission : missions) {
                posts.add(mission);
            }
        }
        myPostListView.showPosts(posts);
    }

    public void selectPost(Post post) {
        if (post == null) {
            return;
        }
        selectedPostView.showPost(post);
    }

    public void selectMyPost(Post post) {
        if (post == null) {
            return;
        }
        mySelectedPostView.showPost(post);
    }

    public void createPost(String title, String content, String location) {
        try {
            if (!showOffers) {
                new Offer(loggedInUser, title, content, location).toDatabase();
            } else {
                new Mission(loggedInUser, title, content, location).toDatabase();
            }
            viewManager.showPostListView();
            myListSetPosts();
        } catch (SQLException e) {
            postCreateView.setError("Error creating post: " + e.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Post created successfully");
    }

    public void ValidateMission(Mission mission){
        mission.UpdateStatus(MissionStatus.VALIDATED);
    };
}
