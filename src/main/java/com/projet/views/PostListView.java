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
    protected JButton createPostButton;
    private JButton deleteButton;
    private JButton setDone;
    private JScrollPane postListScrollPane;
    private CustomTextField<JTextField> justificationValidator;
    
    protected JList<Post> postJList;

    public void showPosts(List<? extends Post> posts) {
        DefaultListModel<Post> listModel = new DefaultListModel<>();
        for (Post post : posts) {
            listModel.addElement(post);
        }
        postJList.setModel(listModel);
    }

    public PostListView(PostView selectedPostView) {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridwidth = 2;

        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        c.gridx = 0;
        c.gridy = 0;
        add(errorLabel, c);

        titleLabel = new JLabel("Posts");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        c.gridy++;
        add(titleLabel, c);

        postJList = new JList<>();
        


        postJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        postJList.setCellRenderer(new PostListCellRenderer(true));
        postJList.setMinimumSize(new Dimension(400, 400));
        postJList.setFixedCellHeight(50);
        //validate and refuse button only allowed if a post is selected
        postJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Post selectedPost = postJList.getSelectedValue();
                boolean isMissionSelected = selectedPost instanceof Mission;
                validatingButton.setEnabled(isMissionSelected);
                refusingButton.setEnabled(isMissionSelected);
                justificationValidator.setVisible(isMissionSelected);
            }
        });
        c.gridy++;

        postListScrollPane = new JScrollPane(postJList);
        postListScrollPane.setPreferredSize(new Dimension(400, 400));
        c.gridy++;
        add(postListScrollPane, c);

        c.gridy++;
        add(selectedPostView, c);


        
        pageBackwardButton = new JButton("Previous page");
        pageBackwardButton.addActionListener(e -> previousPage());
        c.gridwidth = 1;
        c.gridy++;
        add(pageBackwardButton, c);

        pageForwardButton = new JButton("Next page");
        pageForwardButton.addActionListener(e -> nextPage());
        c.gridx = 1;
        add(pageForwardButton, c);

        refusingButton = new JButton("Refuse");
        refusingButton.addActionListener(e -> {
            try {
                postController.RefuseButton((Mission) postJList.getSelectedValue(), justificationValidator.getText());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        c.gridx = 4;
        add(refusingButton, c);
        refusingButton.setVisible(false);
        //on ajoute un bouton de validation que seul les valideurs voient
        validatingButton = new JButton("Validate");
        validatingButton.addActionListener(e -> postController.validateMission((Mission) postJList.getSelectedValue()));
        c.gridx = 0;
        c.gridy++;
        add(validatingButton, c);
        validatingButton.setVisible(false);

    

        deleteButton = new JButton("Delete");

        //create a button to set mission as done   
        setDone = new JButton("Set as done");
        setDone.addActionListener(e -> {
            try {
                postController.setFinished((Mission) postJList.getSelectedValue());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        c.gridx = 1;
        c.gridy++;
        add(setDone, c);


    

        deleteButton.addActionListener(
            e -> {
                DefaultListModel<Post> listModel = (DefaultListModel<Post>) postJList.getModel();                
                listModel.remove(postJList.getSelectedIndex());
            
            }
        );
        
        c.gridx = 0;
        c.gridy++;
        add(deleteButton, c);
        //deleteButton.setVisible(false);

        justificationValidator = new CustomTextField<>("Justification", new JTextField());
        c.gridx = 1;
        c.gridy++;
        add(justificationValidator, c);
        justificationValidator.setVisible(false);


        



        createPostButton = new JButton("Create post");
        createPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postController.togglePostCreate();
            }
        });
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy++;
        add(createPostButton, c);
        createPostButton.setVisible(false);

        postJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Post selectedPost = postJList.getSelectedValue();
                postController.selectPost(selectedPost);
            }
        });
    }

    public void showValidationOptions(boolean isValidator){
        validatingButton.setVisible(isValidator);
        refusingButton.setVisible(isValidator);
        justificationValidator.setVisible(isValidator);
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
