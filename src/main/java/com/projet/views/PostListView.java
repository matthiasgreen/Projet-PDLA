package com.projet.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.projet.controllers.PostController;
import com.projet.models.Mission;
import com.projet.models.Post;
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
    private JButton ValidatingButton;
    protected JButton createPostButton; 
    private JScrollPane postListScrollPane;
    
    protected JList<Post> postJList;

    protected boolean isOffers;

    public void showPosts(java.util.List<Post> posts) {
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


        //on ajoute un bouton de valisation que seul les valideurs voient 
        ValidatingButton = new JButton("Validate");
        ValidatingButton.addActionListener(e -> postController.ValidateMission((Mission) postJList.getSelectedValue()));
        c.gridx = 0;
        c.gridy++;
        add(ValidatingButton, c);


        createPostButton = new JButton("Create post");
        createPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postController.togglePostCreate();
            }
        });
        c.gridwidth = 2;
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

    public void setIsOffers(boolean isOffers) {
        this.isOffers = isOffers;
        titleLabel.setText(isOffers ? "Offers" : "Missions");
    }

    public void setPostController(PostController postController) {
        this.postController = postController;
    }

    public void setError(String string) {
        errorLabel.setText(string);
    }
}
