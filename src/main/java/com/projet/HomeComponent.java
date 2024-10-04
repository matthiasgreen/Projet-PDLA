package com.projet;

import javax.swing.*;

public class HomeComponent extends JPanel {
    private JLabel label;

    public HomeComponent() {
        label = new JLabel("Welcome to Home Component");
        add(label);
    }
}
