package com.projet;

public class Post {
    public String name; 
    public String description;
    public User author;

    Post(String name, String description, User author) {
        this.name = name;
        this.description = description;
        this.author = author;
    }
}
