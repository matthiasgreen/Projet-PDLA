package com.projet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LoginComponent extends JPanel {
    // This component contains two text fields (username and password)
    // and two buttons: log in and sign up
    JTextField usernameField;
    JTextField passwordField;
    JButton loginButton;
    JButton signupButton;
    LoginListener loginListener;

    User user;

    private void login(String username, String password) {
        user = User.loginFromDB(username, password);
        loginListener.onLoginSuccess(user);
    }

    LoginComponent(LoginListener loginListener) {
        this.loginListener = loginListener;
        setPreferredSize(new Dimension(350, 400));
        usernameField = new JTextField("Enter username");
        usernameField.setPreferredSize(new Dimension(300, 100));
        usernameField.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField = new JTextField("Enter password");
        passwordField.setPreferredSize(new Dimension(300, 100));
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        loginButton = new JButton("Log in");
        signupButton = new JButton("Sign up");


        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                login(usernameField.getText(), passwordField.getText());
            }
        
        });

        signupButton.addActionListener(new ActionListener(){
                    
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new user
                User user = new User(usernameField.getText(), passwordField.getText());
                user.toDB(user);
                loginListener.onLoginSuccess(user);

            }
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        add(usernameField);
        add(passwordField);
        add(buttonPanel);
    }
}
