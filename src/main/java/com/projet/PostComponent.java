package com.projet;

import java.awt.Dimension;

import javax.swing.*;

public class PostComponent extends JPanel {
    public Post post;

    JLabel titleLabel;
    JLabel contentLabel;

    public PostComponent(Post post) {
        this.post = post;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // set bg to red
        setBackground(java.awt.Color.red);

        // Add title, description, etc... to the panel
        titleLabel = new JLabel(post.title);
        add(titleLabel);

        contentLabel = new JLabel(post.content);
        add(contentLabel);
    }

    public void setPost(Post post) {
        this.post = post;
        titleLabel.setText(post.title);
        contentLabel.setText(post.content);
    }

}
