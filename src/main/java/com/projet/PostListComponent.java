package com.projet;

import java.awt.*;

import javax.swing.*;

public class PostListComponent extends JPanel {
    private Post[] posts;
    private JList<String> jlist;

    public PostListComponent(Post[] posts) {
        this.posts = posts;

        String[] names = new String[posts.length];
        for (int i = 0; i < posts.length; i++) {
            names[i] = posts[i].name;
        }

        this.jlist = new JList<String>(names);

        add(jlist);

        JScrollPane scrollPane = new JScrollPane(jlist);
        add(scrollPane, BorderLayout.CENTER);
    }
}
