package com.projet.views;

import java.awt.BorderLayout;

import javax.swing.*;

import com.projet.controllers.PostController;
import com.projet.models.Post;

public class PostView extends JPanel {
    private PostController postController;

    JLabel titleLabel;
    JLabel contentLabel;
    JLabel authorDateLabel;

    public PostView() {
        setLayout(new BorderLayout());
        
        titleLabel = new JLabel();
        titleLabel.setFont(titleLabel.getFont().deriveFont(20.0f));
        add(titleLabel, BorderLayout.NORTH);

        contentLabel = new JLabel();
        add(contentLabel, BorderLayout.CENTER);
        
        authorDateLabel = new JLabel(
        );
        add(authorDateLabel, BorderLayout.SOUTH);
    }

    public void showPost(Post post) {
        titleLabel.setText(post.title);
        contentLabel.setText(post.content);
        authorDateLabel.setText(
            "Created by " + post.author.username + " at " + post.createdAt.toString()
        );
    }
    

    public void setPostController(PostController postController) {
        this.postController = postController;
    }
}
