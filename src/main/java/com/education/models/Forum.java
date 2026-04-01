package com.education.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Model representing a discussion forum thread.
 */
public class Forum {
    private String id;
    private String courseId;
    private String title;
    private String description;
    private String creatorId;
    private LocalDateTime createdDate;
    private List<String> postIds;
    private int viewCount;

    public Forum(String id, String courseId, String title, String description, String creatorId) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
        this.createdDate = LocalDateTime.now();
        this.postIds = new ArrayList<>();
        this.viewCount = 0;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getCourseId() { return courseId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCreatorId() { return creatorId; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public List<String> getPostIds() { return postIds; }
    public void addPostId(String postId) { this.postIds.add(postId); }
    public int getViewCount() { return viewCount; }
    public void incrementViewCount() { this.viewCount++; }
    public int getPostCount() { return postIds.size(); }

    @Override
    public String toString() {
        return "Forum{" + "id='" + id + '\'' + ", title='" + title + '\'' + 
               ", posts=" + postIds.size() + ", views=" + viewCount + '}';
    }
}
