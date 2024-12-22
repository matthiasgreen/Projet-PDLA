package com.projet.views;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.projet.controllers.UserController;
import com.projet.models.user.AbstractUser;
import com.projet.swingComponents.UserListCellRenderer;

public class UserView extends JPanel {
    private JList<AbstractUser> reviewJList;
    private UserController userController;

    public UserView() {
        add(new JLabel("Users"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        reviewJList = new JList<>();
        reviewJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reviewJList.setCellRenderer(new UserListCellRenderer());
        reviewJList.setMinimumSize(new Dimension(200, 200));
        reviewJList.addListSelectionListener(e -> onSelect());
        // reviewJList.setFixedCellHeight(50);

        JScrollPane reviewListScrollPane = new JScrollPane(reviewJList);
        reviewListScrollPane.setPreferredSize(new Dimension(200, 200));
        reviewListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(reviewListScrollPane);
    }

    public void displayUsers(List<? extends AbstractUser> users) {
        reviewJList.setListData(users.toArray(new AbstractUser[0]));
    }

    private void onSelect() {
        userController.selectUser(reviewJList.getSelectedValue());
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }
}
