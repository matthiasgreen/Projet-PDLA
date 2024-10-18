package com.projet;

import java.awt.BorderLayout;

import javax.swing.*;

public class PostView extends JPanel {
    private Post post;

    JLabel titleLabel;
    JLabel contentLabel;
    JLabel authorDateLabel;

    public PostView(Post post) {
        setLayout(new BorderLayout());
        
        titleLabel = new JLabel();
        titleLabel.setFont(titleLabel.getFont().deriveFont(20.0f));
        add(titleLabel, BorderLayout.NORTH);

        contentLabel = new JLabel();
        add(contentLabel, BorderLayout.CENTER);
        
        authorDateLabel = new JLabel(
        );
        add(authorDateLabel, BorderLayout.SOUTH);

        if (post != null) {
            setPost(post);
        }
    }

    public void setPost(Post post) {
        this.post = post;
        if (post != null) {
            titleLabel.setText(post.title);
            contentLabel.setText(post.content);
            authorDateLabel.setText(
                "Created by " + post.author.username + " at " + post.createdAt.toString()
            );
        }
    }
}
