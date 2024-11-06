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

    Mission(User author, String title, String content, String location) {
        super(author, title, content, location);
        this.status = MissionStatus.PENDING;
    }

    Mission(int id, User author, String title, String content, String location, Date createdAt, MissionStatus status, String refusalReason) {
        super(id, author, title, content, location, createdAt);
        this.status = status;
        this.refusalReason = refusalReason;
    }

    public void toDatabase() throws SQLException {
        PreparedStatement statement = prepareInsertStatement();
        statement.setString(1, "mission");
        statement.setString(2, status.toString().toLowerCase());
        statement.executeUpdate();
        statement.close();
    }

    public void validate() throws SQLException {
        this.status = MissionStatus.VALIDATED;
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
        ResultSet result = getPosts("mission", page);
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

    public static int getNumberOfPages(String type) throws SQLException {
        return Post.getNumberOfPages("mission");
    }
}
