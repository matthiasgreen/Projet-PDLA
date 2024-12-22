package com.projet.views;

import com.projet.models.post.PostType;

public class MyPostListView extends PostListView {
    public MyPostListView(PostView selectedPostView) {
        super(selectedPostView);
        createPostButton.setVisible(true);
    }
    
    @Override
    protected void nextPage() {
        postController.myListNextPage();
    }

    @Override
    protected void previousPage() {
        postController.myListPreviousPage();
    }

    @Override
    protected void selectPost() {
        postController.selectMyPost(postJList.getSelectedValue());
    }

    @Override
    public void setPostType(PostType postType) {
        
    }
}
