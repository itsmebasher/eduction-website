package com.education.utils;

import java.sql.*;

/**
 * Database connection manager using SQLite.
 * Handles all database operations for the education platform.
 */
public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:education_platform.db";
    private static DatabaseManager instance;

    private DatabaseManager() {
        initializeDatabase();
    }

    /**
     * Gets the singleton instance of DatabaseManager.
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Gets a database connection.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    /**
     * Initializes the database with required tables.
     */
    private void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Users table
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id TEXT PRIMARY KEY," +
                    "name TEXT NOT NULL," +
                    "email TEXT UNIQUE NOT NULL," +
                    "password_hash TEXT NOT NULL," +
                    "role TEXT NOT NULL," +
                    "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            // Courses table
            stmt.execute("CREATE TABLE IF NOT EXISTS courses (" +
                    "id TEXT PRIMARY KEY," +
                    "title TEXT NOT NULL," +
                    "description TEXT," +
                    "instructor_id TEXT NOT NULL," +
                    "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "enrolled_students INTEGER DEFAULT 0," +
                    "FOREIGN KEY(instructor_id) REFERENCES users(id))");

            // Quizzes table
            stmt.execute("CREATE TABLE IF NOT EXISTS quizzes (" +
                    "id TEXT PRIMARY KEY," +
                    "title TEXT NOT NULL," +
                    "description TEXT," +
                    "course_id TEXT NOT NULL," +
                    "instructor_id TEXT NOT NULL," +
                    "total_questions INTEGER DEFAULT 0," +
                    "passing_score INTEGER DEFAULT 70," +
                    "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY(course_id) REFERENCES courses(id)," +
                    "FOREIGN KEY(instructor_id) REFERENCES users(id))");

            // Questions table
            stmt.execute("CREATE TABLE IF NOT EXISTS questions (" +
                    "id TEXT PRIMARY KEY," +
                    "quiz_id TEXT NOT NULL," +
                    "question_text TEXT NOT NULL," +
                    "question_type TEXT NOT NULL," +
                    "correct_answer TEXT NOT NULL," +
                    "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY(quiz_id) REFERENCES quizzes(id))");

            // Question options table
            stmt.execute("CREATE TABLE IF NOT EXISTS question_options (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "question_id TEXT NOT NULL," +
                    "option_text TEXT NOT NULL," +
                    "FOREIGN KEY(question_id) REFERENCES questions(id))");

            // Quiz attempts table
            stmt.execute("CREATE TABLE IF NOT EXISTS quiz_attempts (" +
                    "id TEXT PRIMARY KEY," +
                    "quiz_id TEXT NOT NULL," +
                    "student_id TEXT NOT NULL," +
                    "score INTEGER," +
                    "total_points INTEGER," +
                    "passed BOOLEAN," +
                    "time_taken_seconds INTEGER," +
                    "attempt_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY(quiz_id) REFERENCES quizzes(id)," +
                    "FOREIGN KEY(student_id) REFERENCES users(id))");

            // Forums table
            stmt.execute("CREATE TABLE IF NOT EXISTS forums (" +
                    "id TEXT PRIMARY KEY," +
                    "course_id TEXT NOT NULL," +
                    "title TEXT NOT NULL," +
                    "description TEXT," +
                    "creator_id TEXT NOT NULL," +
                    "view_count INTEGER DEFAULT 0," +
                    "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY(course_id) REFERENCES courses(id)," +
                    "FOREIGN KEY(creator_id) REFERENCES users(id))");

            // Forum posts table
            stmt.execute("CREATE TABLE IF NOT EXISTS forum_posts (" +
                    "id TEXT PRIMARY KEY," +
                    "forum_id TEXT NOT NULL," +
                    "author_id TEXT NOT NULL," +
                    "title TEXT NOT NULL," +
                    "content TEXT NOT NULL," +
                    "likes INTEGER DEFAULT 0," +
                    "reply_count INTEGER DEFAULT 0," +
                    "is_question BOOLEAN DEFAULT false," +
                    "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY(forum_id) REFERENCES forums(id)," +
                    "FOREIGN KEY(author_id) REFERENCES users(id))");

            // Video lessons table
            stmt.execute("CREATE TABLE IF NOT EXISTS video_lessons (" +
                    "id TEXT PRIMARY KEY," +
                    "course_id TEXT NOT NULL," +
                    "title TEXT NOT NULL," +
                    "description TEXT," +
                    "video_url TEXT NOT NULL," +
                    "instructor_id TEXT NOT NULL," +
                    "duration_seconds LONG DEFAULT 0," +
                    "view_count INTEGER DEFAULT 0," +
                    "thumbnail_url TEXT," +
                    "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY(course_id) REFERENCES courses(id)," +
                    "FOREIGN KEY(instructor_id) REFERENCES users(id))");

            // Course enrollments table
            stmt.execute("CREATE TABLE IF NOT EXISTS course_enrollments (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "student_id TEXT NOT NULL," +
                    "course_id TEXT NOT NULL," +
                    "enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "UNIQUE(student_id, course_id)," +
                    "FOREIGN KEY(student_id) REFERENCES users(id)," +
                    "FOREIGN KEY(course_id) REFERENCES courses(id))");

            System.out.println("Database initialized successfully");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    /**
     * Tests the database connection.
     */
    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn.isValid(2);
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Closes all database resources.
     */
    public void close() {
        // SQLite connections typically close automatically
    }
}
