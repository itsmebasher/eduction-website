package com.education.models;

import java.time.LocalDateTime;

/**
 * Model representing a course in the education platform.
 */
public class Course {
    private String id;
    private String title;
    private String description;
    private String instructor;
    private LocalDateTime createdDate;
    private int enrolledStudents;

    public Course(String id, String title, String description, String instructor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.instructor = instructor;
        this.createdDate = LocalDateTime.now();
        this.enrolledStudents = 0;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public void enrollStudent() {
        this.enrolledStudents++;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", instructor='" + instructor + '\'' +
                ", enrolledStudents=" + enrolledStudents +
                '}';
    }
}
