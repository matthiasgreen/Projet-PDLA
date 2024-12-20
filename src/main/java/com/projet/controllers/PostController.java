package com.projet.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import com.projet.database.SqlUtility;
import com.projet.models.AbstractUser;
import com.projet.models.Mission;
import com.projet.models.MissionStatus;
import com.projet.models.Offer;
import com.projet.models.Post;
import com.projet.models.PostType;
import com.projet.models.UserInNeed;
import com.projet.models.Volunteer;
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
    
    private AbstractUser loggedInUser;
    private int mainPage = 0;
    private String currentView = "list";

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

    public void setLoggedInUser(AbstractUser user) {
        loggedInUser = user;
        postListView.setPostType(user.getMainListPostType());
        if (user.getCreatePostType() != null) {
            myPostListView.setPostType(user.getCreatePostType());
            postCreateView.setPostType(user.getCreatePostType());
        } else {
            myPostListView.setVisible(false);
            postCreateView.setVisible(false);
        }
        postListView.showValidationOptions(user.canValidateMissions());
        

        try {
            mainListSetPosts();
            myListSetPosts();
        } catch (SQLException e) {
            postListView.setError("Error loading posts: " + e.getMessage());
            myPostListView.setError("Error loading posts: " + e.getMessage());
        }
    }
    private boolean isMainPageOk(int newPage) throws SQLException {
        return newPage >= 0 && newPage < (loggedInUser.getMainListPostType() == PostType.OFFER ? Offer.getNumberOfPages() : Mission.getNumberOfPages());
    }

    private boolean isMyPageOk(int newPage) throws SQLException {
        return newPage >= 0 && newPage < (loggedInUser.getCreatePostType() == PostType.MISSION ? Mission.getMyNumberOfPages((UserInNeed)loggedInUser) : Offer.getMyNumberOfPages((Volunteer)loggedInUser));
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

        List<? extends Post> posts = (
            loggedInUser.getMainListPostType() == PostType.OFFER ?
            Offer.getOffers(mainPage)
            : Mission.getMissions(mainPage)
        );
        postListView.showPosts(posts);
    }

    private void myListSetPosts() throws SQLException {
        if (loggedInUser.getCreatePostType() == null) {
            return;
        }
        List<? extends Post> posts = (
            loggedInUser.getCreatePostType() == PostType.MISSION ?
            Mission.getMyMissions((UserInNeed)loggedInUser, mainPage)
            : Offer.getMyOffers((Volunteer)loggedInUser, mainPage)
        );
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
            if (loggedInUser.getCreatePostType() == PostType.OFFER) {
                new Offer((Volunteer)loggedInUser, title, content, location).toDatabase();
            } else {
                new Mission((UserInNeed)loggedInUser, title, content, location).toDatabase();
            }
            viewManager.showPostListView();
            myListSetPosts();
        } catch (SQLException e) {
            postCreateView.setError("Error creating post: " + e.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Post created successfully");
    }

    public void validateMission(Mission mission){
        try {
            mission.validate();
            mainListSetPosts();
        } catch (SQLException e) {
            myPostListView.setError("Error validating mission: " + e.getMessage());
        }
    };


    public void setFinished(Mission mission) throws SQLException{
        mission.status = MissionStatus.DONE;
        SqlUtility.executeQuery(
        "UPDATE posts SET status = ?, WHERE id = ?",
        mission.status.toString(),
        mission.id
        );
        myListSetPosts();
    }

    //a function to send the justification of the refusal to the database
    public void RefuseButton(Mission mission, String refusalReason) throws SQLException{
        mission.status = MissionStatus.REFUSED;
        mission.refusalReason = refusalReason;
        SqlUtility.executeUpdate(
        "UPDATE posts SET status = ?, refusal_reason = ? WHERE id = ?",
        mission.status.toString(),
        refusalReason,
        mission.id
        );
        myListSetPosts();
    }   

    

}