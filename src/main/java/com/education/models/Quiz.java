package com.education.models;

import java.time.LocalDateTime;

/**
 * Model representing a quiz in the education platform.
 */
public class Quiz {
    private String id;
    private String title;
    private String description;
    private String courseId;
    private String instructorId;
    private LocalDateTime createdDate;
    private int totalQuestions;
    private int passingScore;

    public Quiz(String id, String title, String description, String courseId, String instructorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.courseId = courseId;
        this.instructorId = instructorId;
        this.createdDate = LocalDateTime.now();
        this.totalQuestions = 0;
        this.passingScore = 70;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCourseId() { return courseId; }
    public String getInstructorId() { return instructorId; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
    public int getPassingScore() { return passingScore; }
    public void setPassingScore(int passingScore) { this.passingScore = passingScore; }

    @Override
    public String toString() {
        return "Quiz{" + "id='" + id + '\'' + ", title='" + title + '\'' + 
               ", totalQuestions=" + totalQuestions + ", passingScore=" + passingScore + '}';
    }
}
