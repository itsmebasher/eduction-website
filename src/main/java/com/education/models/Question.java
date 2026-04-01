package com.education.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Model representing a quiz question in the education platform.
 */
public class Question {
    private String id;
    private String quizId;
    private String questionText;
    private String questionType; // MULTIPLE_CHOICE, TRUE_FALSE, SHORT_ANSWER
    private List<String> options;
    private String correctAnswer;
    private LocalDateTime createdDate;

    public Question(String id, String quizId, String questionText, String questionType) {
        this.id = id;
        this.quizId = quizId;
        this.questionText = questionText;
        this.questionType = questionType;
        this.options = new ArrayList<>();
        this.createdDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getQuizId() { return quizId; }
    public String getQuestionText() { return questionText; }
    public String getQuestionType() { return questionType; }
    public List<String> getOptions() { return options; }
    public void addOption(String option) { this.options.add(option); }
    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
    public LocalDateTime getCreatedDate() { return createdDate; }

    @Override
    public String toString() {
        return "Question{" + "id='" + id + '\'' + ", questionText='" + questionText + '\'' + 
               ", questionType='" + questionType + '\'' + ", options=" + options + '}';
    }
}
