package com.projet;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.*;

public class Main implements LoginListener {
    HomeComponent homeComponent;
    LoginComponent loginComponent;
    JPanel cardPanel;


    Main() {
        // Create and set up the window.
        JFrame frame = new JFrame("Projet");
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
        System.out.println(user.username + user.password);
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
        cardLayout.show(cardPanel, "home");
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