package com.projet.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.projet.controllers.UserController;
import com.projet.models.UserRole;
import com.projet.swingComponents.CustomTextField;

public class LoginView extends JPanel {
    // This component contains two text fields (username and password)
    // and two buttons: log in and sign up
    private CustomTextField<JTextField> usernameField;
    private CustomTextField<JPasswordField> passwordField;
    private JLabel errorMessage;
    private JButton loginButton;
    private JButton signupButton;

    private UserController userController;

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public LoginView() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);

        usernameField = new CustomTextField<>("Username:", new JTextField());
        usernameField.setPreferredSize(new Dimension(300, 100));
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        add(usernameField, c);

        passwordField = new CustomTextField<>("Password:", new JPasswordField());
        passwordField.setPreferredSize(new Dimension(300, 100));
        c.gridx = 0;
        c.gridy = 1;
        add(passwordField, c);

        errorMessage = new JLabel();
        errorMessage.setForeground(Color.RED);
        errorMessage.setFont(errorMessage.getFont().deriveFont(16.0f));
        c.gridx = 0;
        c.gridy = 2;
        add(errorMessage, c);

        //add a checkbox for wheter or no the person signing up is a volunteer
        JCheckBox iSvolunteer = new JCheckBox("Volunteer");
        c.gridx = 2;
        c.gridy = 2;
        add(iSvolunteer, c);

        JCheckBox iSvalidator = new JCheckBox("Validator");
        c.gridx = 1;
        c.gridy = 2;
        add(iSvalidator, c);

        
        loginButton = new JButton("Log in");
        loginButton.setPreferredSize(new Dimension(200, 100));
        loginButton.setFont(loginButton.getFont().deriveFont(16.0f));
        signupButton = new JButton("Sign up");
        signupButton.setPreferredSize(new Dimension(200, 100));
        signupButton.setFont(signupButton.getFont().deriveFont(16.0f));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userController.login(usernameField.getText(), passwordField.getText());
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserRole role;
                if (iSvolunteer.isSelected() && iSvalidator.isSelected()) {
                    onError("You can't be both a volunteer and a validator.");
                    return;
                } else if (iSvolunteer.isSelected()) {
                    role = UserRole.VOLUNTEER;
                } else if (iSvalidator.isSelected()) {
                    role = UserRole.VALIDATOR;
                } else {
                    role = UserRole.USER;
                }
                userController.signUp(usernameField.getText(), passwordField.getText(), role);
            }
        });        
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 3;
        add(loginButton, c);

        c.gridx = 1;
        c.gridy = 3;
        add(signupButton, c);
    }

    public void onError(String message) {
        errorMessage.setText(message);
    }

    public void onSuccess() {
        errorMessage.setText("");
        // Login view no longer needed in this case
    }
}
