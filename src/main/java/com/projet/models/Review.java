package com.projet.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.projet.database.SqlUtility;

public class Review {
    public int authorId;
    public int targetId;
    public String content;
    public int rating;

    public Review(int authorId, int targetId, String content, int rating) {
        this.authorId = authorId;
        this.targetId = targetId;
        this.content = content;
        this.rating = rating;
    }

    private static Review resultFunction(ResultSet result) throws SQLException {
        return new Review(
            result.getInt("author_id"),
            result.getInt("target_id"),
            result.getString("content"),
            result.getInt("rating")
        );
    }

    public void toDatabase() throws SQLException {
        String query = "INSERT INTO reviews (author_id, target_id, content, rating) VALUES (?, ?, ?, ?)";
        SqlUtility.executeUpdate(query, this.authorId, this.targetId, this.content, this.rating);
    }

    public static List<Review> getByAuthor(int authorId) throws SQLException {
        String query = "SELECT * FROM reviews WHERE author_id = ?";
        return SqlUtility.executeQuery(query, Review::resultFunction, authorId);
    }

    public static List<Review> getByTarget(int targetId) throws SQLException {
        String query = "SELECT * FROM reviews WHERE target_id = ?";
        return SqlUtility.executeQuery(query, Review::resultFunction, targetId);
    }

    public void delete() throws SQLException {
        String query = "DELETE FROM reviews WHERE author_id = ? AND target_id = ?";
        SqlUtility.executeUpdate(query, this.authorId, this.targetId);
    }
}
