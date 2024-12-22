package com.projet.views;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.projet.controllers.UserController;
import com.projet.models.user.UserRole;
import com.projet.swingComponents.CustomTextField;

public class SignupView extends JPanel {
    private CustomTextField<JTextField> usernameField;
    private CustomTextField<JPasswordField> passwordField;
    private JButton signupButton;

    private JLabel errorMessage;

    private ButtonGroup roleGroup;
    private JRadioButton userButton;
    private JRadioButton volunteerButton;
    private JRadioButton validatorButton;

    private UserController userController;

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public SignupView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        usernameField = new CustomTextField<>("Username:", new JTextField());
        // usernameField.setPreferredSize(new Dimension(300, 100));
        add(usernameField);

        passwordField = new CustomTextField<>("Password:", new JPasswordField());
        // passwordField.setPreferredSize(new Dimension(300, 100));
        add(passwordField);

        roleGroup = new ButtonGroup();
        userButton = new JRadioButton("User");
        userButton.setActionCommand("user_in_need");
        userButton.setSelected(true);
        roleGroup.add(userButton);
        add(userButton);

        volunteerButton = new JRadioButton("Volunteer");
        volunteerButton.setActionCommand("volunteer");
        roleGroup.add(volunteerButton);
        add(volunteerButton);

        validatorButton = new JRadioButton("Validator");
        validatorButton.setActionCommand("validator");
        roleGroup.add(validatorButton);
        add(validatorButton);

        signupButton = new JButton("Sign up");
        // signupButton.setPreferredSize(new Dimension(200, 100));
        signupButton.setFont(signupButton.getFont().deriveFont(16.0f));
        signupButton.addActionListener(e -> {
            onSignUpButtonClicked();
        });
        add(signupButton);

        errorMessage = new JLabel();
        errorMessage.setForeground(java.awt.Color.RED);
        errorMessage.setFont(errorMessage.getFont().deriveFont(16.0f));
        add(errorMessage);
    }

    private void onSignUpButtonClicked() {
        userController.signUp(
            usernameField.getText(),
            passwordField.getText(),
            UserRole.fromString(
                roleGroup.getSelection().getActionCommand()
            )
        );
    }
    
    public void showError(String message) {
        errorMessage.setText(message);
    }
}
