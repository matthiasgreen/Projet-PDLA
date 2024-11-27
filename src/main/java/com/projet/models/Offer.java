package com.projet.models;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class Offer extends Post {
    public Offer(User author, String title, String content, String location) {
        super(author, title, content, location);
    }

    Offer(int id, User author, String title, String content, String location, Date createdAt) {
        super(id, author, title, content, location, createdAt);
    }

    public void toDatabase() throws SQLException {
        PreparedStatement statement = prepareInsertStatement();
        statement.setString(1, "offer");
        statement.setString(2, "validated");
        statement.executeUpdate();
        statement.close();
    }

    public static ArrayList<Offer> getOffers(int page) throws SQLException {
        ArrayList<Offer> offers = new ArrayList<>();
        ResultSet result = getPosts(PostType.OFFER, page);
        HashMap<Integer, User> users = new HashMap<>();

        while (result.next()) {
            int id = result.getInt("id");
            int userId = result.getInt("user_id");
            User author = users.get(userId);
            if (author == null) {
                author = new User(userId, result.getString("username"), UserRole.fromString(result.getString("role")));
                users.put(userId, author);
            }
            String title = result.getString("title");
            String content = result.getString("content");
            String location = result.getString("location");
            Date createdAt = result.getDate("created_at");
            offers.add(new Offer(id, author, title, content, location, createdAt));
        }

        return offers;
    }

    public static ArrayList<Offer> getMyOffers(User user, int page) throws SQLException {
        ArrayList<Offer> offers = new ArrayList<>();
        ResultSet result = getMyPosts(PostType.OFFER, user, page);

        while (result.next()) {
            int id = result.getInt("id");
            String title = result.getString("title");
            String content = result.getString("content");
            String location = result.getString("location");
            Date createdAt = result.getDate("created_at");
            offers.add(new Offer(id, user, title, content, location, createdAt));
        }

        return offers;
    }

    public static int getNumberOfPages() throws SQLException {
        return Post.getNumberOfPages(PostType.OFFER);
    }

    public static int getMyNumberOfPages(User user) throws SQLException {
        return Post.getMyNumberOfPages(PostType.OFFER, user);
    }
}
