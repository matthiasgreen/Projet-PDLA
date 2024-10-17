package com.projet;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Post {
    public User author;
    public String title; 
    public String content;
    public PostStatus status;
    public String refusalReason;
    public String location;
    public Date createdAt;

    Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.location = "placeholder";
    }

    public void toDB(boolean isOffer) throws SQLException {
        String type;
        String status;
        if (isOffer) {
            type = "offer";
            status = "validated";
        } else {
            type = "mission";
            status = "pending";
        }

        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO posts (title, content, type, status, location, user_id)"
            + "VALUES (?, ?, ?, ?, ?, ?)"
        );
        statement.setString(1, title);
        statement.setString(2, content);
        statement.setString(3, type);
        statement.setString(4, status);
        statement.setString(5, location);
        statement.setInt(6, author.id);

        statement.executeUpdate();
    }

    public static ArrayList<Post> getAllFromDB(boolean offers) throws SQLException {
        String type;
        if (offers) {
            type = "offer";
        } else {
            type = "mission";
        }

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM posts WHERE type=(?)"
        );
        statement.setString(1, type);
        ResultSet results = statement.executeQuery();

        ArrayList<Post> posts = new ArrayList<>();
        while (results.next()) {
            Post post = new Post(
                results.getString("title"),
                results.getString("content"),
                User.getFromDB(results.getInt("user_id"))
            );
            posts.add(post);
        }
        return posts;
    }

    public static void main(String[] args) {
        User user = new User(1, "test", "test");
        Post post = new Post("title", "content", user);
        try {
            post.toDB(false);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
