package com.projet.controllers;

import java.sql.SQLException;
import java.util.List;

import com.projet.models.post.Review;
import com.projet.models.user.AbstractUser;
import com.projet.views.ReviewView;
import com.projet.views.ReviewViewModel;

public class ReviewController {
    private ReviewView reviewView;

    private AbstractUser loggedInUser;
    private AbstractUser targetUser;

    public void setLoggedInUser(AbstractUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public ReviewController(ReviewView reviewView) {
        this.reviewView = reviewView;
        reviewView.setReviewController(this);
    }

    public void displayReviews(AbstractUser target) {
        this.targetUser = target;
        // Fetch reviews from the database
        try {
            List<Review> reviews = Review.getByTarget(target.id);
            List<ReviewViewModel> reviewViewModels = reviews.stream().map(
                review -> {
                    try {
                        return new ReviewViewModel(
                            AbstractUser.getFromDatabase(review.authorId).username,
                            AbstractUser.getFromDatabase(review.targetId).username,
                            review.content,
                            review.rating
                        );
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;
                    }
                }
            ).toList();
            reviewView.displayReviews(reviewViewModels);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void addReview(String content, int rating) {
        Review review = new Review(loggedInUser.id, targetUser.id, content, rating);
        try {
            review.toDatabase();
            displayReviews(targetUser);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
