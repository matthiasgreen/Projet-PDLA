package com.projet.models;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.projet.database.DatabaseConnection;

public class Mission extends Post {
    public MissionStatus status;
    public String refusalReason;

    public Mission(User author, String title, String content, String location) {
        super(author, title, content, location);
        this.status = MissionStatus.PENDING;
    }

    Mission(int id, User author, String title, String content, String location, Date createdAt, MissionStatus status, String refusalReason) {
        super(id, author, title, content, location, createdAt);
        this.status = status;
        this.refusalReason = refusalReason;
    }

    public static Mission getMission(int id) throws SQLException {
        ResultSet result = getPost(id);
        if (!result.next()) {
            return null;
        }
        User author = new User(
            result.getInt("user_id"),
            result.getString("username"),
            UserRole.fromString(result.getString("role"))
        );
        return new Mission(
            result.getInt("id"),
            author,
            result.getString("title"),
            result.getString("content"),
            result.getString("location"),
            result.getDate("created_at"),
            MissionStatus.valueOf(result.getString("status").toUpperCase()),
            result.getString("refusal_reason")
        );
    }

    public void toDatabase() throws SQLException {
        PreparedStatement statement = prepareInsertStatement();
        statement.setString(1, "mission");
        statement.setString(2, status.toString().toLowerCase());
        statement.executeUpdate();
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (!generatedKeys.next()) {
            throw new SQLException("Failed to retrieve the generated key");
        }
        id = generatedKeys.getInt(1);
        statement.close();
    }

    public void validate() throws SQLException {
        status = MissionStatus.VALIDATED;
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(
            "UPDATE posts SET status = ? WHERE id = ?"
        );
        statement.setString(1, status.toString().toLowerCase());
        statement.setInt(2, id);
        statement.executeUpdate();
        statement.close();
    }

    public void refuse(String refusalReason) throws SQLException {
        this.status = MissionStatus.REFUSED;
        this.refusalReason = refusalReason;
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(
            "UPDATE posts SET status = ?, refusal_reason = ? WHERE id = ?"
        );
        statement.setString(1, status.toString().toLowerCase());
        statement.setString(2, refusalReason);
        statement.setInt(3, id);
        statement.executeUpdate();
    }

    public static ArrayList<Mission> getMissions(int page) throws SQLException {
        ArrayList<Mission> missions = new ArrayList<>();
        ResultSet result = getPosts(PostType.MISSION, page);
        HashMap<Integer, User> users = new HashMap<>();

        while (result.next()) {
            int userId = result.getInt("user_id");
            User author = users.get(userId);

            if (author == null) {
                author = new User(userId, result.getString("username"), UserRole.fromString(result.getString("role")));
                users.put(userId, author);
            }
            Mission mission = new Mission(
                result.getInt("id"),
                author,
                result.getString("title"),
                result.getString("content"),
                result.getString("location"),
                result.getDate("created_at"),
                MissionStatus.valueOf(result.getString("status").toUpperCase()),
                result.getString("refusal_reason")
            );
            missions.add(mission);
        }
        return missions;
    }

    public static ArrayList<Mission> getMyMissions(User user, int page) throws SQLException {
        ArrayList<Mission> missions = new ArrayList<>();
        ResultSet result = getMyPosts(PostType.MISSION, user, page);
        while (result.next()) {
            Mission mission = new Mission(
                result.getInt("id"),
                user,
                result.getString("title"),
                result.getString("content"),
                result.getString("location"),
                result.getDate("created_at"),
                MissionStatus.valueOf(result.getString("status").toUpperCase()),
                result.getString("refusal_reason")
            );
            missions.add(mission);
        }
        return missions;
    }
    public MissionStatus getMissionStatus() {
        return this.status;
    }
    public void UpdateStatus(MissionStatus status) {
        this.status = status;
    }
    public static int getNumberOfPages() throws SQLException {
        return Post.getNumberOfPages(PostType.MISSION);
    }

    public static int getMyNumberOfPages(User user) throws SQLException {
        return getMyNumberOfPages(PostType.MISSION, user);
    }
}
