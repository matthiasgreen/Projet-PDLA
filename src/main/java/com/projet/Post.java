package com.projet;

public class Post {
    public String title; 
    public String content;
    public User author;

    Post(String title, String description, User author) {
        this.title = title;
        this.content = description;
        this.author = author;
    }
}
