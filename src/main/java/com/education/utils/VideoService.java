package com.education.utils;

import com.education.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for video lesson management operations.
 */
public class VideoService {
    private static final DatabaseManager dbManager = DatabaseManager.getInstance();

    /**
     * Adds a new video lesson to a course.
     */
    public static boolean addVideoLesson(VideoLesson video) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO video_lessons (id, course_id, title, description, video_url, instructor_id, duration_seconds, thumbnail_url) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            
            stmt.setString(1, video.getId());
            stmt.setString(2, video.getCourseId());
            stmt.setString(3, video.getTitle());
            stmt.setString(4, video.getDescription());
            stmt.setString(5, video.getVideoUrl());
            stmt.setString(6, video.getInstructorId());
            stmt.setLong(7, video.getDurationSeconds());
            stmt.setString(8, video.getThumbnailUrl());
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding video lesson: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets all video lessons for a course.
     */
    public static List<VideoLesson> getCourseVideos(String courseId) {
        List<VideoLesson> videos = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id, title, description, video_url, instructor_id, duration_seconds, view_count, thumbnail_url " +
                     "FROM video_lessons WHERE course_id = ?")) {
            
            stmt.setString(1, courseId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                VideoLesson video = new VideoLesson(rs.getString("id"), courseId, rs.getString("title"), 
                                                   rs.getString("description"), rs.getString("video_url"));
                video.setInstructorId(rs.getString("instructor_id"));
                video.setDurationSeconds(rs.getLong("duration_seconds"));
                video.setThumbnailUrl(rs.getString("thumbnail_url"));
                videos.add(video);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving videos: " + e.getMessage());
        }
        return videos;
    }

    /**
     * Increments view count for a video.
     */
    public static boolean recordVideoView(String videoId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE video_lessons SET view_count = view_count + 1 WHERE id = ?")) {
            
            stmt.setString(1, videoId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error recording video view: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets a video lesson by ID.
     */
    public static VideoLesson getVideoById(String videoId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id, course_id, title, description, video_url, instructor_id, duration_seconds, view_count, thumbnail_url " +
                     "FROM video_lessons WHERE id = ?")) {
            
            stmt.setString(1, videoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                VideoLesson video = new VideoLesson(rs.getString("id"), rs.getString("course_id"), 
                                                   rs.getString("title"), rs.getString("description"), 
                                                   rs.getString("video_url"));
                video.setInstructorId(rs.getString("instructor_id"));
                video.setDurationSeconds(rs.getLong("duration_seconds"));
                video.setThumbnailUrl(rs.getString("thumbnail_url"));
                return video;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving video: " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets the most viewed videos.
     */
    public static List<VideoLesson> getMostViewedVideos(int limit) {
        List<VideoLesson> videos = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id, course_id, title, description, video_url, instructor_id, duration_seconds, view_count, thumbnail_url " +
                     "FROM video_lessons ORDER BY view_count DESC LIMIT ?")) {
            
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                VideoLesson video = new VideoLesson(rs.getString("id"), rs.getString("course_id"), 
                                                   rs.getString("title"), rs.getString("description"), 
                                                   rs.getString("video_url"));
                video.setInstructorId(rs.getString("instructor_id"));
                video.setDurationSeconds(rs.getLong("duration_seconds"));
                video.setThumbnailUrl(rs.getString("thumbnail_url"));
                videos.add(video);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving videos: " + e.getMessage());
        }
        return videos;
    }
}
