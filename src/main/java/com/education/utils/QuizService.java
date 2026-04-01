package com.education.utils;

import com.education.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for quiz management operations.
 */
public class QuizService {
    private static final DatabaseManager dbManager = DatabaseManager.getInstance();

    /**
     * Creates a new quiz.
     */
    public static boolean createQuiz(Quiz quiz) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO quizzes (id, title, description, course_id, instructor_id, passing_score) " +
                     "VALUES (?, ?, ?, ?, ?, ?)")) {
            
            stmt.setString(1, quiz.getId());
            stmt.setString(2, quiz.getTitle());
            stmt.setString(3, quiz.getDescription());
            stmt.setString(4, quiz.getCourseId());
            stmt.setString(5, quiz.getInstructorId());
            stmt.setInt(6, quiz.getPassingScore());
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error creating quiz: " + e.getMessage());
            return false;
        }
    }

    /**
     * Adds a question to a quiz.
     */
    public static boolean addQuestion(Question question) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO questions (id, quiz_id, question_text, question_type, correct_answer) " +
                     "VALUES (?, ?, ?, ?, ?)")) {
            
            stmt.setString(1, question.getId());
            stmt.setString(2, question.getQuizId());
            stmt.setString(3, question.getQuestionText());
            stmt.setString(4, question.getQuestionType());
            stmt.setString(5, question.getCorrectAnswer());
            
            stmt.executeUpdate();

            // Add options
            for (String option : question.getOptions()) {
                try (PreparedStatement optionStmt = conn.prepareStatement(
                        "INSERT INTO question_options (question_id, option_text) VALUES (?, ?)")) {
                    optionStmt.setString(1, question.getId());
                    optionStmt.setString(2, option);
                    optionStmt.executeUpdate();
                }
            }

            return true;
        } catch (SQLException e) {
            System.err.println("Error adding question: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets all questions for a quiz.
     */
    public static List<Question> getQuizQuestions(String quizId) {
        List<Question> questions = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id, question_text, question_type, correct_answer FROM questions WHERE quiz_id = ?")) {
            
            stmt.setString(1, quizId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Question q = new Question(rs.getString("id"), quizId, rs.getString("question_text"), 
                                        rs.getString("question_type"));
                q.setCorrectAnswer(rs.getString("correct_answer"));
                
                // Get options
                try (PreparedStatement optionStmt = conn.prepareStatement(
                        "SELECT option_text FROM question_options WHERE question_id = ?")) {
                    optionStmt.setString(1, q.getId());
                    ResultSet optionRs = optionStmt.executeQuery();
                    while (optionRs.next()) {
                        q.addOption(optionRs.getString("option_text"));
                    }
                }
                questions.add(q);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving questions: " + e.getMessage());
        }
        return questions;
    }

    /**
     * Records a quiz attempt.
     */
    public static boolean submitQuizAttempt(QuizAttempt attempt) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO quiz_attempts (id, quiz_id, student_id, score, total_points, passed, time_taken_seconds) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            
            stmt.setString(1, attempt.getId());
            stmt.setString(2, attempt.getQuizId());
            stmt.setString(3, attempt.getStudentId());
            stmt.setInt(4, attempt.getScore());
            stmt.setInt(5, attempt.getTotalPoints());
            stmt.setBoolean(6, attempt.isPassed());
            stmt.setInt(7, attempt.getTimeTakenSeconds());
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error submitting quiz attempt: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets a quiz by ID.
     */
    public static Quiz getQuizById(String quizId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id, title, description, course_id, instructor_id, total_questions, passing_score " +
                     "FROM quizzes WHERE id = ?")) {
            
            stmt.setString(1, quizId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Quiz quiz = new Quiz(rs.getString("id"), rs.getString("title"), 
                                   rs.getString("description"), rs.getString("course_id"), 
                                   rs.getString("instructor_id"));
                quiz.setTotalQuestions(rs.getInt("total_questions"));
                quiz.setPassingScore(rs.getInt("passing_score"));
                return quiz;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving quiz: " + e.getMessage());
        }
        return null;
    }
}
