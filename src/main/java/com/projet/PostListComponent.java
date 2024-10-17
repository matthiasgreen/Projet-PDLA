package com.projet;

import java.awt.*;

import javax.swing.*;

public class PostListComponent<E> extends JPanel {
    private Post[] posts;
    private JList<Post> list;
    
    private PostComponent selectedPostComponent;

    private void onPostSelect(Post post) {
        selectedPostComponent.setPost(post);
    }

    public PostListComponent(Post[] posts) {
        this.posts = posts;

        list = new JList<>(posts);
        
        list.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                Post post = (Post) value;
                label.setText(post.title);
                label.setPreferredSize(new Dimension(340, 50));
                return label;
            }
        });
        
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                @SuppressWarnings("unchecked")
                JList<Post> list = (JList<Post>) evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    Post post = list.getModel().getElementAt(index);
                    System.out.println("Double clicked on " + post.title);
                    onPostSelect(post);
                }
            }
        });
        add(list, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(list);
        add(scrollPane, BorderLayout.CENTER);

        selectedPostComponent = new PostComponent(posts[0]);
        add(selectedPostComponent, BorderLayout.SOUTH);
    }
}
