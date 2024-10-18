package com.projet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

public class LoginComponent extends JPanel {
    // This component contains two text fields (username and password)
    // and two buttons: log in and sign up
    CustomTextField<JTextField> usernameField;
    CustomTextField<JPasswordField> passwordField;
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

    private void signUp(String username, String password, boolean isVolunteer) {
        UserRole role;
        if (isVolunteer==true) {
            role = UserRole.VOLUNTEER;
        } else {
            role = UserRole.USER;
        }
        try {
            User user = new User(username, password, role);
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

        
        loginButton = new JButton("Log in");
        loginButton.setPreferredSize(new Dimension(200, 100));
        loginButton.setFont(loginButton.getFont().deriveFont(16.0f));
        signupButton = new JButton("Sign up");
        signupButton.setPreferredSize(new Dimension(200, 100));
        signupButton.setFont(signupButton.getFont().deriveFont(16.0f));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(usernameField.getText(), passwordField.getText());
            }
        });

        signupButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                signUp(usernameField.getText(), passwordField.getText(), iSvolunteer.isSelected());
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
}
