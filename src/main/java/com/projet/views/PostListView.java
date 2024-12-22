package com.projet.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

import java.util.List;

import com.projet.controllers.PostController;
import com.projet.models.post.Mission;
import com.projet.models.post.Post;
import com.projet.models.post.PostType;
import com.projet.models.user.AbstractUser;
import com.projet.swingComponents.CustomTextField;
import com.projet.swingComponents.PostListCellRenderer;

public class PostListView extends JPanel {
    // Displays a scrollable list of posts with
    // a PostView component to display the selected post at the bottom
    // and page forward and page backward buttons at the bottom
    protected PostController postController;
    protected JLabel titleLabel;
    private JLabel errorLabel;
    private JButton pageBackwardButton;
    private JButton pageForwardButton;
    private JButton validatingButton;
    private JButton refusingButton;
    protected JButton deleteButton;
    protected JButton setDone;
    private JScrollPane postListScrollPane;
    private CustomTextField<JTextField> refusalReasonTextField;
    
    protected JList<Post> postJList;

    public void showPosts(List<? extends Post> posts) {
        DefaultListModel<Post> listModel = new DefaultListModel<>();
        for (Post post : posts) {
            listModel.addElement(post);
        }
        postJList.setModel(listModel);
    }

    public PostListView(PostView selectedPostView) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        add(errorLabel);

        titleLabel = new JLabel("Posts");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel);

        postJList = new JList<>();
        postJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        postJList.setCellRenderer(new PostListCellRenderer(true));
        postJList.setFixedCellHeight(50);
        //validate and refuse button only allowed if a post is selected
        postJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Post selectedPost = postJList.getSelectedValue();
                boolean isMissionSelected = selectedPost instanceof Mission && selectedPost != null;
                validatingButton.setEnabled(isMissionSelected);
                refusingButton.setEnabled(isMissionSelected);
                refusalReasonTextField.setEnabled(isMissionSelected);
                selectPost();
            }
        });

        postListScrollPane = new JScrollPane(postJList);
        postListScrollPane.setPreferredSize(new Dimension(400, 400));
        add(postListScrollPane);

        add(selectedPostView);

        pageBackwardButton = new JButton("Previous page");
        pageBackwardButton.addActionListener(e -> previousPage());
        add(pageBackwardButton);

        pageForwardButton = new JButton("Next page");
        pageForwardButton.addActionListener(e -> nextPage());
        add(pageForwardButton);

        refusingButton = new JButton("Refuse");
        refusingButton.addActionListener(e -> {
            try {
                postController.refuseMission((Mission) postJList.getSelectedValue(), refusalReasonTextField.getText());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        add(refusingButton);
        refusingButton.setVisible(false);
        //on ajoute un bouton de validation que seul les valideurs voient
        validatingButton = new JButton("Validate");
        validatingButton.addActionListener(e -> postController.validateMission((Mission) postJList.getSelectedValue()));
        add(validatingButton);
        validatingButton.setVisible(false);

        deleteButton = new JButton("Delete");
        deleteButton.setVisible(false);
        
        deleteButton.addActionListener(
            e -> {
                DefaultListModel<Post> listModel = (DefaultListModel<Post>) postJList.getModel();                
                listModel.remove(postJList.getSelectedIndex());
                
            }
        );
        add(deleteButton);
        //deleteButton.setVisible(false);
    
        //create a button to set mission as done
        setDone = new JButton("Set as done");
        setDone.addActionListener(e -> {
            try {
                postController.setFinished((Mission) postJList.getSelectedValue());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        add(setDone);
        setDone.setVisible(false);

        refusalReasonTextField = new CustomTextField<>("Refusal reason", new JTextField());
        add(refusalReasonTextField);
        refusalReasonTextField.setVisible(false);
    }

    public void showValidationOptions(boolean isValidator){
        validatingButton.setVisible(isValidator);
        refusingButton.setVisible(isValidator);
        refusalReasonTextField.setVisible(isValidator);
    }

    protected void nextPage() {
        // Should be overriden by myPostListView
        postController.mainListNextPage();
    }

    protected void previousPage() {
        // Should be overriden by myPostListView
        postController.mainListPreviousPage();
    }

    protected void selectPost() {
        // Should be overriden by myPostListView
        postController.selectPost(postJList.getSelectedValue());
    }

    public void setPostType(PostType postType) {
        titleLabel.setText(
            postType.toString().substring(0, 1) + postType.toString().substring(1).toLowerCase() + "s"
        );
    }

    public void setPostController(PostController postController) {
        this.postController = postController;
    }

    public void setError(String string) {
        errorLabel.setText(string);
    }
}
