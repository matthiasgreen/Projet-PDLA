package com.projet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

public class PostListView extends JPanel {
    // Displays a scrollable list of posts with
    // a PostView component to display the selected post at the bottom
    // and page forward and page backward buttons at the bottom

    private JLabel errorLabel;
    private JButton pageBackwardButton;
    private JButton pageForwardButton;
    private JScrollPane postListScrollPane;
    
    private JList<Post> postJList;
    
    private int page = 0;
    private ArrayList<Post> posts;
    
    private PostView selectedPostComponent;

    private boolean isOffers = false;

    private void onPostSelect(Post post) {
        selectedPostComponent.setPost(post);
    }

    public void renderList() {
        DefaultListModel<Post> listModel = new DefaultListModel<>();
        for (Post post : posts) {
            listModel.addElement(post);
        }
        postJList.setModel(listModel);
    }

    public void loadPosts() {
        try {
            posts = new ArrayList<>();
            if (isOffers) {
                ArrayList<Offer> offers = Offer.getOffers(page);
                for (Offer offer : offers) {
                    posts.add(offer);
                }
            } else {
                ArrayList<Mission> missions = Mission.getMissions(page);
                for (Mission mission : missions) {
                    posts.add(mission);
                }
            }
        } catch (SQLException e) {
            errorLabel.setText("Error loading posts: " + e.getMessage());
        }
    }

    public void nextPage() {
        try {
            if (page < Post.getNumberOfPages(isOffers ? "offer" : "mission")) {
                page++;
                loadPosts();
                renderList();
            } else {
                errorLabel.setText("No more pages");
            }
        } catch (SQLException e) {
            errorLabel.setText("Error loading posts: " + e.getMessage());
        }
    }

    public void previousPage() {
        if (page > 0) {
            page--;
            loadPosts();
            renderList();
        } else {
            errorLabel.setText("No more pages");
        }
    }

    public PostListView(TogglePostCreateListener createListener) {
        postJList = new JList<>();
        postJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        postJList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                Post post = (Post) value;
                label.setText(post.title);
                return label;
            }
        });

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

        c.gridx = 0;
        c.gridy = 1;
        add(postJList, c);

        postListScrollPane = new JScrollPane(postJList);
        postListScrollPane.setPreferredSize(new Dimension(800, 500));
        c.gridx = 0;
        c.gridy = 2;
        add(postListScrollPane, c);

        loadPosts();
        renderList();

        selectedPostComponent = new PostView(posts.size() > 0 ? posts.get(0) : null);
        c.gridx = 0;
        c.gridy = 3;
        add(selectedPostComponent, c);
        
        pageBackwardButton = new JButton("Previous page");
        pageBackwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousPage();
            }
        });
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        add(pageBackwardButton, c);

        pageForwardButton = new JButton("Next page");
        pageForwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextPage();
            }
        });
        c.gridx = 1;
        c.gridy = 4;
        add(pageForwardButton, c);

        JButton createPostButton = new JButton("Create post");
        createPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createListener.onTogglePostCreate();
            }
        });
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 5;
        add(createPostButton, c);

        postJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Post selectedPost = postJList.getSelectedValue();
                onPostSelect(selectedPost);
            }
        });
    }

    public void setIsOffers(boolean isOffers) {
        this.isOffers = isOffers;
        loadPosts();
        renderList();
    }
}
