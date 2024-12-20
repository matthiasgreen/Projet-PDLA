package com.projet.views;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import com.projet.controllers.ReviewController;
import com.projet.swingComponents.ReviewListCellRenderer;

public class ReviewView extends JPanel {
    private ReviewController reviewController;

    private JList<ReviewViewModel> reviewJList;

    public ReviewView() {
        add(new JLabel("Reviews"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        reviewJList = new JList<>();
        reviewJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reviewJList.setCellRenderer(new ReviewListCellRenderer());
        reviewJList.setMinimumSize(new Dimension(200, 200));
        // reviewJList.setFixedCellHeight(50);

        JScrollPane reviewListScrollPane = new JScrollPane(reviewJList);
        reviewListScrollPane.setPreferredSize(new Dimension(400, 400));
        reviewListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JTextField reviewContentField = new JTextField("Enter review here");
        JSlider ratingSlider = new JSlider(0, 5, 0);
        JButton addReviewButton = new JButton("Add review");
        addReviewButton.addActionListener(e -> {
            reviewController.addReview(reviewContentField.getText(), ratingSlider.getValue());
        });
        add(reviewListScrollPane);

        add(reviewContentField);
        add(ratingSlider);
        add(addReviewButton);
    }

    public void displayReviews(List<ReviewViewModel> reviews) {
        reviewJList.setListData(reviews.toArray(new ReviewViewModel[0]));
    }

    public void setReviewController(ReviewController reviewController) {
        this.reviewController = reviewController;
    }
}
