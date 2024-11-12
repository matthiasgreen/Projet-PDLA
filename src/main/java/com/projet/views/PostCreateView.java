package com.projet.views;


import javax.swing.*;

import com.projet.controllers.PostController;
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
    private PostController postController;

    public User loggedInUser;

    public PostCreateView(boolean isOffers) {
        this.isOffers = isOffers;
        errorLabel = new JLabel();
        titleField = new CustomTextField<>("Title:", new JTextField());
        descriptionField = new CustomTextField<>("Description:", new JTextField());
        locationField = new CustomTextField<>("Location:", new JTextField());

        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> 
            postController.createPost(
                titleField.getText(),
                descriptionField.getText(),
                locationField.getText()
            )
        );

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            postController.togglePostCreate();
            titleField.setText("");
            descriptionField.setText("");
            locationField.setText("");
        });

        add(titleField);
        add(descriptionField);
        add(locationField);
        add(submitButton);
        add(cancelButton);
        add(errorLabel);
    }

    public void setIsOffers(boolean isOffers) {
        this.isOffers = isOffers;
    }

    public void setPostController(PostController postController) {
        this.postController = postController;
    }

    public void setError(String string) {
        errorLabel.setText(string);
    }
}
