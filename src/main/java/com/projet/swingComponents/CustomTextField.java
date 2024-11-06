package com.projet.swingComponents;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public class CustomTextField<E extends JTextComponent> extends JPanel {
    // This component is a text field with a label
    // that can be used in forms
    JLabel label;
    E textField;

    public CustomTextField(String labelText, E textField) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        label = new JLabel(labelText);
        this.textField = textField;
        label.setFont(label.getFont().deriveFont(16.0f));
        textField.setFont(textField.getFont().deriveFont(16.0f));
        add(label);
        add(textField);
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public E getTextField() {
        return textField;
    }
}
