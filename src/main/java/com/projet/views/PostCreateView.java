package com.projet.views;


import javax.swing.*;

import com.projet.controllers.PostController;
import com.projet.models.post.PostType;
import com.projet.models.user.UserInNeed;
import com.projet.swingComponents.CustomTextField;

public class PostCreateView extends JPanel {
    private JLabel titleLabel;
    private CustomTextField<JTextField> titleField;
    private CustomTextField<JTextField> descriptionField;
    private CustomTextField<JTextField> locationField;
    private JLabel errorLabel;
    private JButton submitButton;
    private PostController postController;

    public UserInNeed loggedInUser;

    public PostCreateView(boolean isOffers) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        errorLabel = new JLabel();
        titleLabel = new JLabel("Create a new post");
        titleLabel.setFont(titleLabel.getFont().deriveFont(24.0f));
        
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

        add(titleLabel);
        add(titleField);
        add(descriptionField);
        add(locationField);
        add(submitButton);
        add(errorLabel);
    }

    public void setPostType(PostType postType) {
        titleLabel.setText(
            "Create a new " + postType.toString().toLowerCase() + "."
        );
    }

    public void setPostController(PostController postController) {
        this.postController = postController;
    }

    public void setError(String string) {
        errorLabel.setText(string);
    }
}
