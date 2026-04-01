package com.education.utils;

import com.education.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for forum management operations.
 */
public class ForumService {
    private static final DatabaseManager dbManager = DatabaseManager.getInstance();

    /**
     * Creates a new forum thread.
     */
    public static boolean createForum(Forum forum) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO forums (id, course_id, title, description, creator_id) " +
                     "VALUES (?, ?, ?, ?, ?)")) {
            
            stmt.setString(1, forum.getId());
            stmt.setString(2, forum.getCourseId());
            stmt.setString(3, forum.getTitle());
            stmt.setString(4, forum.getDescription());
            stmt.setString(5, forum.getCreatorId());
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error creating forum: " + e.getMessage());
            return false;
        }
    }

    /**
     * Posts a new message to a forum.
     */
    public static boolean postMessage(ForumPost post) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO forum_posts (id, forum_id, author_id, title, content, is_question) " +
                     "VALUES (?, ?, ?, ?, ?, ?)")) {
            
            stmt.setString(1, post.getId());
            stmt.setString(2, post.getForumId());
            stmt.setString(3, post.getAuthorId());
            stmt.setString(4, post.getTitle());
            stmt.setString(5, post.getContent());
            stmt.setBoolean(6, post.isQuestionPost());
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error posting message: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets all posts in a forum.
     */
    public static List<ForumPost> getForumPosts(String forumId) {
        List<ForumPost> posts = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id, author_id, title, content, likes, reply_count, is_question, created_date " +
                     "FROM forum_posts WHERE forum_id = ? ORDER BY created_date DESC")) {
            
            stmt.setString(1, forumId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ForumPost post = new ForumPost(rs.getString("id"), forumId, rs.getString("author_id"), 
                                             rs.getString("title"), rs.getString("content"));
                post.setQuestionPost(rs.getBoolean("is_question"));
                posts.add(post);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving forum posts: " + e.getMessage());
        }
        return posts;
    }

    /**
     * Likes a forum post.
     */
    public static boolean likePost(String postId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE forum_posts SET likes = likes + 1 WHERE id = ?")) {
            
            stmt.setString(1, postId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error liking post: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets all forums for a course.
     */
    public static List<Forum> getCourseFora(String courseId) {
        List<Forum> forums = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id, title, description, creator_id, view_count FROM forums WHERE course_id = ?")) {
            
            stmt.setString(1, courseId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Forum forum = new Forum(rs.getString("id"), courseId, rs.getString("title"), 
                                      rs.getString("description"), rs.getString("creator_id"));
                forums.add(forum);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving forums: " + e.getMessage());
        }
        return forums;
    }

    /**
     * Updates a forum post.
     */
    public static boolean updatePost(ForumPost post) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE forum_posts SET content = ? WHERE id = ?")) {
            
            stmt.setString(1, post.getContent());
            stmt.setString(2, post.getId());
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error updating post: " + e.getMessage());
            return false;
        }
    }
}
