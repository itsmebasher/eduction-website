package com.education.controllers;

import com.education.utils.AuthenticationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main application view.
 */
public class MainViewController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private VBox mainContainer;

    @FXML
    private Label titleLabel;

    @FXML
    private Label userLabel;

    @FXML
    public void initialize() {
        titleLabel.setText("Welcome to Education Platform");
        updateUserInfo();
    }

    private void updateUserInfo() {
        if (AuthenticationService.isLoggedIn()) {
            userLabel.setText("Logged in as: " + AuthenticationService.getCurrentUser().getName());
        }
    }

    @FXML
    private void handleViewCourses() {
        System.out.println("Viewing courses...");
    }

    @FXML
    private void handleViewQuizzes() {
        loadView("/com/education/views/quiz-view.fxml");
    }

    @FXML
    private void handleAccessForum() {
        loadView("/com/education/views/forum-view.fxml");
    }

    @FXML
    private void handleViewVideos() {
        loadView("/com/education/views/video-view.fxml");
    }

    @FXML
    private void handleLogout() {
        AuthenticationService.logout();
        System.out.println("User logged out");
        // Navigate back to login screen
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            mainBorderPane.setCenter(loader.load());
        } catch (Exception e) {
            System.err.println("Error loading view: " + e.getMessage());
        }
    }
}
