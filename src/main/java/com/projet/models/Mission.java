package com.projet.models;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.projet.database.SqlUtility;

public class Mission extends Post {
    public MissionStatus status;
    public String refusalReason;

    public Mission(UserInNeed author, String title, String content, String location) {
        super(author, title, content, location);
        this.status = MissionStatus.PENDING;
    }

    Mission(int id, UserInNeed author, String title, String content, String location, Date createdAt, MissionStatus status, String refusalReason) {
        super(id, author, title, content, location, createdAt);
        this.status = status;
        this.refusalReason = refusalReason;
    }

    public static Mission getMission(int id) throws SQLException {
        Post post = getPost(id);
        if (post instanceof Mission) {
            return (Mission) post;
        }
        return null;
    }

    public void toDatabase() throws SQLException {
        super.toDatabase();
    }

    public void validate() throws SQLException {
        status = MissionStatus.VALIDATED;
        SqlUtility.executeUpdate(
            "UPDATE posts SET status = ? WHERE id = ?",
            status.toString(),
            id
        );
    }

    public void refuse(String refusalReason) throws SQLException {
        status = MissionStatus.REFUSED;
        this.refusalReason = refusalReason;

        SqlUtility.executeUpdate(
            "UPDATE posts SET status = ?, refusal_reason = ? WHERE id = ?",
            status.toString(),
            refusalReason,
            id
        );
    }

    @SuppressWarnings("unchecked")
    public static List<Mission> getMissions(int page) throws SQLException {
        List<? extends Post> posts = getPosts(PostType.MISSION, page);
        // All the posts are missions, we just have to cast
        return (List<Mission>) posts;
    }

    @SuppressWarnings("unchecked")
    public static List<Mission> getMyMissions(UserInNeed user, int page) throws SQLException {
        List<? extends Post> posts = getMyPosts(PostType.MISSION, user, page);
        return (List<Mission>) posts;
    }

    public MissionStatus getMissionStatus() {
        return this.status;
    }

    public static int getNumberOfPages() throws SQLException {
        return Post.getNumberOfPages(PostType.MISSION);
    }

    public static int getMyNumberOfPages(UserInNeed user) throws SQLException {
        return getMyNumberOfPages(PostType.MISSION, user);
    }
}
