package com.projet;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class PostListCellRenderer extends JPanel implements ListCellRenderer<Post> {
    private JLabel titleLabel;
    private JLabel statusLabel;
    private boolean showStatus;

    public PostListCellRenderer(boolean showStatus) {
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
        return this;
    }
}
