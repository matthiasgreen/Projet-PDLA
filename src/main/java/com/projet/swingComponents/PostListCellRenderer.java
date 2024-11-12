package com.projet.swingComponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import com.projet.models.Mission;
import com.projet.models.Post;

public class PostListCellRenderer extends JPanel implements ListCellRenderer<Post> {
    private JLabel titleLabel;
    private JLabel statusLabel;
    private boolean showStatus;

    public PostListCellRenderer(boolean showStatus) {
        // Add border
        setBorder(new LineBorder(Color.BLACK, 2));
        setMinimumSize(new Dimension(200, 200));
        this.showStatus = showStatus;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        titleLabel = new JLabel();
        add(titleLabel);
        if (showStatus) {
            statusLabel = new JLabel();
            add(statusLabel);
        }
    }

    @Override
    public Component getListCellRendererComponent(
        JList<? extends Post> list,
        Post value,
        int index,
        boolean isSelected,
        boolean cellHasFocus
    ) {
        titleLabel.setText(value.title);
        if (showStatus) {
            if (value instanceof Mission) {
                Mission mission = (Mission) value;
                statusLabel.setText(mission.status.toString());
            } else {
                statusLabel.setText("");
            }
        }
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;
    }
}
