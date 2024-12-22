package com.projet.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.projet.controllers.UserController;
import com.projet.swingComponents.CustomTextField;

public class LoginView extends JPanel {
    // This component contains two text fields (username and password)
    // and two buttons: log in and sign up
    private CustomTextField<JTextField> usernameField;
    private CustomTextField<JPasswordField> passwordField;
    private JLabel errorMessage;
    private JButton loginButton;
    

    private UserController userController;

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public LoginView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // add margin to this component
        setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        usernameField = new CustomTextField<>("Username:", new JTextField());
        add(usernameField);

        passwordField = new CustomTextField<>("Password:", new JPasswordField());
        add(passwordField);

        errorMessage = new JLabel();
        errorMessage.setForeground(Color.RED);
        errorMessage.setFont(errorMessage.getFont().deriveFont(16.0f));
        add(errorMessage);
        
        loginButton = new JButton("Log in");
        loginButton.setFont(loginButton.getFont().deriveFont(16.0f));
        

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userController.login(usernameField.getText(), passwordField.getText());
            }
        });

        add(loginButton);
    }

    public void onError(String message) {
        errorMessage.setText(message);
    }

    public void onSuccess() {
        errorMessage.setText("");
        // Login view no longer needed in this case
    }
}
