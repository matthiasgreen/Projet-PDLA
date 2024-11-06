package com.projet.views;

import java.sql.SQLException;

import javax.swing.*;

import com.projet.TogglePostCreateListener;
import com.projet.models.Mission;
import com.projet.models.Offer;
import com.projet.models.User;
import com.projet.swingComponents.CustomTextField;

public class PostCreateView extends JPanel {
    private boolean isOffers;
    private CustomTextField<JTextField> titleField;
    private CustomTextField<JTextField> descriptionField;
    private CustomTextField<JTextField> locationField;
    private JLabel errorLabel;
    private JButton submitButton;
    private JButton cancelButton;

    public User loggedInUser;
    private TogglePostCreateListener createListener;

    public PostCreateView(TogglePostCreateListener createListener, boolean isOffers) {
        this.createListener = createListener;
        this.isOffers = isOffers;
        errorLabel = new JLabel();
        titleField = new CustomTextField<>("Title:", new JTextField());
        descriptionField = new CustomTextField<>("Description:", new JTextField());
        locationField = new CustomTextField<>("Location:", new JTextField());

        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> onSubmit());

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> onCancel());

        add(titleField);
        add(descriptionField);
        add(locationField);
        add(submitButton);
        add(cancelButton);
        add(errorLabel);
    }

    private void onSubmit() {
        // Create a new post with the data from the fields
        // and save it to the database
        if (isOffers) {
            Offer offer = new Offer(loggedInUser, titleField.getText(), descriptionField.getText(), locationField.getText());
            try {
                offer.toDatabase();
            } catch (SQLException e) {
                errorLabel.setText("Error saving offer: " + e.getMessage());
            }
        } else {
            Mission mission = new Mission(loggedInUser, titleField.getText(), descriptionField.getText(), locationField.getText());
            try {
                mission.toDatabase();
            } catch (SQLException e) {
                errorLabel.setText("Error saving mission: " + e.getMessage());
            }
        }
        JOptionPane.showMessageDialog(this, "Post created successfully");
        createListener.onTogglePostCreate();
    }

    private void onCancel() {
        createListener.onTogglePostCreate();
    }

    public void setOffers(boolean isOffers) {
        this.isOffers = isOffers;
    }
}
