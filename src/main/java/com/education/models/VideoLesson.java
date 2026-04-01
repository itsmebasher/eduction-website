package com.education.models;

import java.time.LocalDateTime;

/**
 * Model representing a video lesson in the education platform.
 */
public class VideoLesson {
    private String id;
    private String courseId;
    private String title;
    private String description;
    private String videoUrl;
    private String instructorId;
    private LocalDateTime createdDate;
    private long durationSeconds;
    private int viewCount;
    private String thumbnailUrl;

    public VideoLesson(String id, String courseId, String title, String description, String videoUrl) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.createdDate = LocalDateTime.now();
        this.durationSeconds = 0;
        this.viewCount = 0;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getCourseId() { return courseId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getVideoUrl() { return videoUrl; }
    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public long getDurationSeconds() { return durationSeconds; }
    public void setDurationSeconds(long durationSeconds) { this.durationSeconds = durationSeconds; }
    public int getViewCount() { return viewCount; }
    public void incrementViewCount() { this.viewCount++; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public String getDurationFormatted() {
        long hours = durationSeconds / 3600;
        long minutes = (durationSeconds % 3600) / 60;
        long seconds = durationSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public String toString() {
        return "VideoLesson{" + "id='" + id + '\'' + ", title='" + title + '\'' + 
               ", duration=" + getDurationFormatted() + ", views=" + viewCount + '}';
    }
}
