package com.projet.models;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;



public class Offer extends Post {
    public Offer(Volunteer author, String title, String content, String location) {
        super(author, title, content, location);
    }

    Offer(int id, Volunteer author, String title, String content, String location, Date createdAt) {
        super(id, author, title, content, location, createdAt);
    }

    public static Offer getOffer(int id) throws SQLException {
        Post post = getPost(id);
        if (post instanceof Offer) {
            return (Offer) post;
        }
        return null;
    }

    public void toDatabase() throws SQLException {
        super.toDatabase();
    }

    @SuppressWarnings("unchecked")
    public static List<Offer> getOffers(int page) throws SQLException {
        List<? extends Post> posts = getPosts(PostType.OFFER, page);
        // All the posts are offers, we just have to cast
        return (List<Offer>) posts;
    }

    @SuppressWarnings("unchecked")
    public static List<Offer> getMyOffers(Volunteer user, int page) throws SQLException {
        List<? extends Post> posts = getMyPosts(PostType.OFFER, user, page);
        return (List<Offer>) posts;
    }

    public static int getNumberOfPages() throws SQLException {
        return Post.getNumberOfPages(PostType.OFFER);
    }

    public static int getMyNumberOfPages(Volunteer user) throws SQLException {
        return Post.getMyNumberOfPages(PostType.OFFER, user);
    }
}
