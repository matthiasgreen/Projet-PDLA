package com.projet;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.*;

import com.projet.models.User;
import com.projet.views.HomeComponent;
import com.projet.views.LoginComponent;

public class Main implements LoginListener {
    HomeComponent homeComponent;
    LoginComponent loginComponent;
    JPanel cardPanel;
    JFrame frame;

    Main() {
        // Create and set up the window.
        frame = new JFrame("Projet");
        frame.setPreferredSize(new Dimension(1000, 800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        cardPanel = new JPanel(new CardLayout());
        homeComponent = new HomeComponent();
        loginComponent = new LoginComponent(this);

        cardPanel.add(loginComponent, "login");
        cardPanel.add(homeComponent, "home");
        
        frame.getContentPane().add(cardPanel);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void onLoginSuccess(User user) {
        homeComponent.setLoggedInUser(user);
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
        cardLayout.show(cardPanel, "home");
        frame.pack();
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
