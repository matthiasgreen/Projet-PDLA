package com.projet.swingComponents;


import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;

import com.projet.models.AbstractUser;

public class UserListCellRenderer extends JPanel implements ListCellRenderer<AbstractUser> {
    private JLabel nameLabel;

    public UserListCellRenderer() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        nameLabel = new JLabel();

        add(nameLabel);

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends AbstractUser> list, AbstractUser value, int index,
            boolean isSelected, boolean cellHasFocus) {
        nameLabel.setText(value.username);
        return this;
    }

}
