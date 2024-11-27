package com.projet.models;

import java.sql.*;

import com.projet.database.DatabaseConnection;

public class Post {
    public static int PAGE_SIZE = 10;

    public Integer id = null;
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
            + "VALUES (?, ?, ?, ?, ?, ?)",
            PreparedStatement.RETURN_GENERATED_KEYS
        );
        statement.setInt(3, author.id);
        statement.setString(4, title);
        statement.setString(5, content);
        statement.setString(6, location);
        return statement;
    }

    public static ResultSet getPost(int id) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM posts LEFT JOIN users ON user_id=users.id WHERE posts.id=?"
        );
        statement.setInt(1, id);
        return statement.executeQuery();
    }

    public static ResultSet getPosts(PostType type, int page) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM posts LEFT JOIN users ON user_id=users.id WHERE type=? ORDER BY created_at DESC LIMIT ? OFFSET ?"
        );
        statement.setString(1, type.toString().toLowerCase());
        statement.setInt(2, PAGE_SIZE);
        statement.setInt(3, page * PAGE_SIZE);
        return statement.executeQuery();
    }

    public static ResultSet getMyPosts(PostType type, User user, int page) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM posts LEFT JOIN users ON user_id=users.id WHERE type=? AND user_id=? ORDER BY created_at DESC LIMIT ? OFFSET ?"
        );
        statement.setString(1, type.toString().toLowerCase());
        statement.setInt(2, user.id);
        statement.setInt(3, PAGE_SIZE);
        statement.setInt(4, page * PAGE_SIZE);
        return statement.executeQuery();
    }

    public static int getNumberOfPages(PostType type) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT COUNT(*) FROM posts WHERE type=?"
        );
        statement.setString(1, type.toString().toLowerCase());
        ResultSet result = statement.executeQuery();
        result.next();
        int count = result.getInt(1);
        return (int) Math.ceil((double) count / PAGE_SIZE);
    }

    public static int getMyNumberOfPages(PostType type, User user) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT COUNT(*) FROM posts WHERE type=? AND user_id=?"
        );
        statement.setString(1, type.toString());
        statement.setInt(2, user.id);
        ResultSet result = statement.executeQuery();
        result.next();
        int count = result.getInt(1);
        return (int) Math.ceil((double) count / PAGE_SIZE);
    }
}
