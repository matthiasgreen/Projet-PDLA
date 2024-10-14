package com.projet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

public class LoginComponent extends JPanel {
    // This component contains two text fields (username and password)
    // and two buttons: log in and sign up
    JTextField usernameField;
    JTextField passwordField;
    JLabel errorMessage;
    JButton loginButton;
    JButton signupButton;
    LoginListener loginListener;

    private void login(String username, String password) {
        try {
            User user = User.loginFromDB(username, password);
            loginListener.onLoginSuccess(user);
        } catch (SQLException e) {
            errorMessage.setText("An error occurred while trying to log in.");
            e.printStackTrace();
            return;
        } catch (IncorrectCredentialsException e) {
            errorMessage.setText("Incorrect username or password.");
            return;
        }
    }

    private void signUp(String username, String password) {
        try {
            User user = new User(username, password);
            user.toDB();
            loginListener.onLoginSuccess(user);
        } catch (SQLException e) {
            errorMessage.setText("An error occurred while trying to sign up.");
            e.printStackTrace();
        } catch (UserAlreadyExistsException e) {
            errorMessage.setText("Username already exists.");
        }
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
        errorMessage = new JLabel();
        errorMessage.setPreferredSize(new Dimension(300, 100));
        errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
        errorMessage.setForeground(Color.RED);
        add(errorMessage);

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
                signUp(usernameField.getText(), passwordField.getText());
            }
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        add(usernameField);
        add(passwordField);
        add(buttonPanel);
    }
}
