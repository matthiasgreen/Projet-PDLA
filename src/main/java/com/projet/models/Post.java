package com.projet.models;

import java.sql.*;
import java.util.List;

import com.projet.database.SqlUtility;

public abstract class Post {
    public static int PAGE_SIZE = 10;

    public Integer id = null;
    public AbstractUser author;
    public String title;
    public String content;
    public String location;
    public Date createdAt;

    protected Post(AbstractUser author, String title, String content, String location) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.location = location;
    }

    protected Post(int id, AbstractUser author, String title, String content, String location, Date createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.location = location;
        this.createdAt = createdAt;
    }

    private static Post resultFunction(ResultSet result) throws SQLException {
        AbstractUser user;
        if (result.getString("role").equals(UserRole.USER_IN_NEED.toString())) {
            user = new UserInNeed(
                result.getInt("user_id"),
                result.getString("username"),
                result.getString("password")
            );
        } else if (result.getString("role").equals(UserRole.VOLUNTEER.toString())) {
            user = new Volunteer(
                result.getInt("user_id"),
                result.getString("username"),
                result.getString("password")
            );
        } else {
            user = new Validator(
                result.getInt("user_id"),
                result.getString("username"),
                result.getString("password")
            );
        }
        if (result.getString("type").equals(PostType.MISSION.toString())) {
            return new Mission(
                result.getInt("id"),
                (UserInNeed)user,
                result.getString("title"),
                result.getString("content"),
                result.getString("location"),
                result.getDate("created_at"),
                MissionStatus.valueOf(result.getString("status").toUpperCase()),
                result.getString("refusal_reason")
            );
        } else {
            return new Offer(
                result.getInt("id"),
                (Volunteer)user,
                result.getString("title"),
                result.getString("content"),
                result.getString("location"),
                result.getDate("created_at")
            );
        }
    }

    public void toDatabase() throws SQLException {
        if (this instanceof Mission) {
            toDatabase(PostType.MISSION, ((Mission) this).status, ((Mission) this).refusalReason);
        } else {
            toDatabase(PostType.OFFER, null, null);
        }
    }

    private void toDatabase(PostType type, MissionStatus status, String refusalReason) throws SQLException {
        ResultSet generatedKeys = SqlUtility.executeUpdate(
            "INSERT INTO posts (user_id, title, content, location, type, status, refusal_reason) VALUES (?, ?, ?, ?, ?, ?, ?)",
            author.id, title, content, location,
            type.toString(),
            status != null ? status.toString() : MissionStatus.VALIDATED.toString(),
            refusalReason != null ? refusalReason : null
        );
        if (!generatedKeys.next()) {
            throw new SQLException("No key generated.");
        }
        id = generatedKeys.getInt(1);
    }

    protected static Post getPost(int id) throws SQLException {
        List<Post> post = SqlUtility.executeQuery(
            "SELECT * FROM posts LEFT JOIN users ON user_id=users.id WHERE posts.id=?",
            Post::resultFunction,
            id
        );
        Post res = post.isEmpty() ? null : post.get(0);
        return res;
    }

    protected static List<? extends Post> getPosts(PostType type, int page) throws SQLException {
        return SqlUtility.executeQuery(
            "SELECT * FROM posts LEFT JOIN users ON user_id=users.id WHERE type=? ORDER BY created_at DESC LIMIT ? OFFSET ?",
            Post::resultFunction,
            type.toString(),
            PAGE_SIZE,
            page * PAGE_SIZE
        );
    }

    protected static List<Post> getMyPosts(PostType type, AbstractUser user, int page) throws SQLException {
        return SqlUtility.executeQuery(
            "SELECT * FROM posts LEFT JOIN users ON user_id=users.id WHERE type=? AND user_id=? ORDER BY created_at DESC LIMIT ? OFFSET ?",
            Post::resultFunction,
            type.toString(),
            user.id,
            PAGE_SIZE,
            page * PAGE_SIZE
        );
    }

    protected static int getNumberOfPages(PostType type) throws SQLException {
        ResultSet result = SqlUtility.executeQuery(
            "SELECT COUNT(*) FROM posts WHERE type=?",
            type.toString()
        );
        if (!result.next()) {
            return 0;
        }
        int count = result.getInt(1);
        return (int) Math.ceil((double) count / PAGE_SIZE);
    }

    protected static int getMyNumberOfPages(PostType type, AbstractUser user) throws SQLException {
        ResultSet result = SqlUtility.executeQuery(
            "SELECT COUNT(*) FROM posts WHERE type=? AND user_id=?",
            type.toString(),
            user.id
        );
        if (!result.next()) {
            return 0;
        }
        int count = result.getInt(1);
        return (int) Math.ceil((double) count / PAGE_SIZE);
    }
}
