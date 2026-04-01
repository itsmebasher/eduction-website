package com.education.models;

import java.time.LocalDateTime;

/**
 * Model representing a quiz submission/attempt by a student.
 */
public class QuizAttempt {
    private String id;
    private String quizId;
    private String studentId;
    private int score;
    private int totalPoints;
    private boolean passed;
    private LocalDateTime attemptDate;
    private int timeTakenSeconds;

    public QuizAttempt(String id, String quizId, String studentId) {
        this.id = id;
        this.quizId = quizId;
        this.studentId = studentId;
        this.attemptDate = LocalDateTime.now();
        this.score = 0;
        this.totalPoints = 0;
        this.passed = false;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getQuizId() { return quizId; }
    public String getStudentId() { return studentId; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public int getTotalPoints() { return totalPoints; }
    public void setTotalPoints(int totalPoints) { this.totalPoints = totalPoints; }
    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) { this.passed = passed; }
    public LocalDateTime getAttemptDate() { return attemptDate; }
    public int getTimeTakenSeconds() { return timeTakenSeconds; }
    public void setTimeTakenSeconds(int timeTakenSeconds) { this.timeTakenSeconds = timeTakenSeconds; }

    public double getPercentage() {
        return totalPoints == 0 ? 0 : (double) score / totalPoints * 100;
    }

    @Override
    public String toString() {
        return "QuizAttempt{" + "id='" + id + '\'' + ", score=" + score + '/' + totalPoints + 
               ", passed=" + passed + ", date=" + attemptDate + '}';
    }
}
