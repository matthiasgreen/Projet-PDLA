package com.projet;

import javax.swing.*;

public class HomeComponent extends JPanel {
    private JLabel label;

    public HomeComponent() {
        label = new JLabel("Welcome to Home Component");
        add(label);

        Post[] posts = {
            new Post("test", "test", null),
            new Post("test2", "test2", null)
        };

        PostListComponent postList = new PostListComponent(posts);

        add(postList);
    }
}
