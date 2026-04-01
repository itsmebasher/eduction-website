package com.education.models;

import java.time.LocalDateTime;

/**
 * Model representing a forum post/discussion message.
 */
public class ForumPost {
    private String id;
    private String forumId;
    private String authorId;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private int likes;
    private int replyCount;
    private boolean isQuestionPost;

    public ForumPost(String id, String forumId, String authorId, String title, String content) {
        this.id = id;
        this.forumId = forumId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
        this.likes = 0;
        this.replyCount = 0;
        this.isQuestionPost = false;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getForumId() { return forumId; }
    public String getAuthorId() { return authorId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; this.lastModifiedDate = LocalDateTime.now(); }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getLastModifiedDate() { return lastModifiedDate; }
    public int getLikes() { return likes; }
    public void incrementLikes() { this.likes++; }
    public int getReplyCount() { return replyCount; }
    public void setReplyCount(int replyCount) { this.replyCount = replyCount; }
    public boolean isQuestionPost() { return isQuestionPost; }
    public void setQuestionPost(boolean questionPost) { this.isQuestionPost = questionPost; }

    @Override
    public String toString() {
        return "ForumPost{" + "id='" + id + '\'' + ", title='" + title + '\'' + 
               ", likes=" + likes + ", replies=" + replyCount + '}';
    }
}
