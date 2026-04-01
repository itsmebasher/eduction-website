package com.education.controllers;

import com.education.models.Forum;
import com.education.models.ForumPost;
import com.education.utils.ForumService;
import com.education.utils.AuthenticationService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Controller for the Forum view.
 */
public class ForumViewController {

    @FXML
    private ListView<Forum> forumListView;

    @FXML
    private ListView<ForumPost> postListView;

    @FXML
    private Label forumTitleLabel;

    @FXML
    private TextArea postContentArea;

    @FXML
    private TextField postTitleField;

    private Forum currentForum;

    @FXML
    public void initialize() {
        setupForumList();
    }

    private void setupForumList() {
        ObservableList<Forum> forums = FXCollections.observableArrayList();
        // Placeholder - will be populated from database
        forums.add(new Forum("F1", "COURSE-1", "Java Q&A", "Ask and answer Java questions", "INSTR-1"));
        forums.add(new Forum("F2", "COURSE-1", "JavaScript Discussion", "Discuss JavaScript topics", "INSTR-1"));
        
        forumListView.setItems(forums);
        forumListView.setOnMouseClicked(e -> loadForum());
    }

    private void loadForum() {
        Forum selected = forumListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            currentForum = selected;
            forumTitleLabel.setText(selected.getTitle());
            loadForumPosts();
        }
    }

    private void loadForumPosts() {
        if (currentForum != null) {
            ObservableList<ForumPost> posts = FXCollections.observableArrayList();
            // Fetch from ForumService.getForumPosts(currentForum.getId());
            posts.add(new ForumPost("P1", currentForum.getId(), "USER-1", "How to learn Java?", 
                                   "I'm a beginner. What's the best way to start learning Java?"));
            posts.add(new ForumPost("P2", currentForum.getId(), "USER-2", "Lambda Expressions", 
                                   "Can someone explain lambda expressions in Java 8?"));
            
            postListView.setItems(posts);
        }
    }

    @FXML
    private void handlePostMessage() {
        if (currentForum == null) {
            showAlert("Error", "Please select a forum first");
            return;
        }

        String title = postTitleField.getText();
        String content = postContentArea.getText();

        if (title.isEmpty() || content.isEmpty()) {
            showAlert("Error", "Please fill in title and content");
            return;
        }

        String postId = "POST_" + System.currentTimeMillis();
        ForumPost post = new ForumPost(postId, currentForum.getId(), 
                                      AuthenticationService.getCurrentUser().getId(), 
                                      title, content);

        if (ForumService.postMessage(post)) {
            postTitleField.clear();
            postContentArea.clear();
            loadForumPosts();
            showAlert("Success", "Post published successfully");
        } else {
            showAlert("Error", "Failed to post message");
        }
    }

    @FXML
    private void handleLikePost() {
        ForumPost selected = postListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            ForumService.likePost(selected.getId());
            loadForumPosts();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
