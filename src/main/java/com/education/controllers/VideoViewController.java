package com.education.controllers;

import com.education.models.VideoLesson;
import com.education.utils.VideoService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Controller for the Video Lesson view.
 */
public class VideoViewController {

    @FXML
    private ListView<VideoLesson> videoListView;

    @FXML
    private Label videoTitleLabel;

    @FXML
    private Label videoDurationLabel;

    @FXML
    private Label videoViewsLabel;

    @FXML
    private TextArea videoDescriptionArea;

    @FXML
    private WebView videoPlayer;

    private VideoLesson currentVideo;

    @FXML
    public void initialize() {
        setupVideoList();
    }

    private void setupVideoList() {
        ObservableList<VideoLesson> videos = FXCollections.observableArrayList();
        // Placeholder - will be populated from database
        VideoLesson v1 = new VideoLesson("V1", "COURSE-1", "Introduction to Java", 
                                        "Learn the basics of Java programming", 
                                        "https://example.com/videos/java-intro.mp4");
        v1.setDurationSeconds(3600);
        videos.add(v1);

        VideoLesson v2 = new VideoLesson("V2", "COURSE-1", "Object-Oriented Programming", 
                                        "Understand OOP concepts", 
                                        "https://example.com/videos/oop.mp4");
        v2.setDurationSeconds(4500);
        videos.add(v2);
        
        videoListView.setItems(videos);
        videoListView.setOnMouseClicked(e -> loadVideo());
    }

    private void loadVideo() {
        VideoLesson selected = videoListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            currentVideo = selected;
            VideoService.recordVideoView(selected.getId());
            
            videoTitleLabel.setText(selected.getTitle());
            videoDurationLabel.setText("Duration: " + selected.getDurationFormatted());
            videoViewsLabel.setText("Views: " + selected.getViewCount());
            videoDescriptionArea.setText(selected.getDescription());
            
            // Load video in player (placeholder - actual implementation would use a video player library)
            loadVideoPlayer(selected.getVideoUrl());
        }
    }

    private void loadVideoPlayer(String videoUrl) {
        // Placeholder for video player loading
        System.out.println("Loading video: " + videoUrl);
    }

    @FXML
    private void handlePlayVideo() {
        if (currentVideo != null) {
            System.out.println("Playing: " + currentVideo.getTitle());
            // Actual video playback would be implemented here
        }
    }

    @FXML
    private void handlePauseVideo() {
        System.out.println("Video paused");
    }

    @FXML
    private void handleDownloadVideo() {
        if (currentVideo != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Download");
            alert.setContentText("Downloading: " + currentVideo.getTitle());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleViewAllVideos() {
        // Show all videos for current course
    }
}
