package com.projet;

import java.sql.*;

public class Post {
    public static int PAGE_SIZE = 10;

    public int id;
    public User author;
    public String title; 
    public String content;
    public String location;
    public Date createdAt;

    Post(User author, String title, String content, String location) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.location = location;
    }

    Post(int id, User author, String title, String content, String location, Date createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.location = location;
        this.createdAt = createdAt;
    }

    public PreparedStatement prepareInsertStatement() throws SQLException {
        // Returns PreparedStatement with everything ready except type (index 1) and status (index 2)
        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO posts (type, status, user_id, title, content, location)"
            + "VALUES (?, ?, ?, ?, ?, ?)"
        );
        statement.setInt(3, author.id);
        statement.setString(4, title);
        statement.setString(5, content);
        statement.setString(6, location);
        return statement;
    }

    public static ResultSet getPosts(String type, int page) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM posts LEFT JOIN users ON user_id=users.id WHERE type=? ORDER BY created_at DESC LIMIT ? OFFSET ?"
        );
        statement.setString(1, type);
        statement.setInt(2, PAGE_SIZE);
        statement.setInt(3, page * PAGE_SIZE);
        return statement.executeQuery();
    }

    public static ResultSet getMyPosts(String type, User user, int page) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM posts LEFT JOIN users ON user_id=users.id WHERE type=? AND user_id=? ORDER BY created_at DESC LIMIT ? OFFSET ?"
        );
        statement.setString(1, type);
        statement.setInt(2, user.id);
        statement.setInt(3, PAGE_SIZE);
        statement.setInt(4, page * PAGE_SIZE);
        return statement.executeQuery();
    }

    public static int getNumberOfPages(String type) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT COUNT(*) FROM posts WHERE type=?"
        );
        statement.setString(1, type);
        ResultSet result = statement.executeQuery();
        result.next();
        int count = result.getInt(1);
        return (int) Math.ceil((double) count / PAGE_SIZE) - 1;
    }
}
