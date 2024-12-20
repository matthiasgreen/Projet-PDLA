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

import com.projet.views.ReviewViewModel;

public class ReviewListCellRenderer extends JPanel implements ListCellRenderer<ReviewViewModel> {
    private JLabel namesLabel;
    private JTextArea contentTextArea;
    private JLabel ratingLabel;

    public ReviewListCellRenderer() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        namesLabel = new JLabel();
        contentTextArea = new JTextArea();
        contentTextArea.setBackground(getBackground());
        ratingLabel = new JLabel();

        add(namesLabel);
        add(contentTextArea);
        add(ratingLabel);

        contentTextArea.setLineWrap(true);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ReviewViewModel> list, ReviewViewModel value, int index,
            boolean isSelected, boolean cellHasFocus) {
        namesLabel.setText(value.authorName() + " -> " + value.targetName());
        contentTextArea.setText(value.content());
        ratingLabel.setText(value.rating() + "/5");
        return this;
    }

}
