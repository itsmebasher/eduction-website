package com.education.controllers;

import com.education.models.Quiz;
import com.education.models.Question;
import com.education.models.QuizAttempt;
import com.education.utils.QuizService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Controller for the Quiz view.
 */
public class QuizViewController {

    @FXML
    private ListView<Quiz> quizListView;

    @FXML
    private Label quizTitleLabel;

    @FXML
    private Label quizDescriptionLabel;

    @FXML
    private VBox questionContainer;

    @FXML
    private Button submitButton;

    private Quiz currentQuiz;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @FXML
    public void initialize() {
        setupQuizList();
    }

    private void setupQuizList() {
        ObservableList<Quiz> quizzes = FXCollections.observableArrayList();
        // Placeholder - will be populated from database
        quizzes.add(new Quiz("1", "Java Basics", "Test your Java knowledge", "COURSE-1", "INSTR-1"));
        quizzes.add(new Quiz("2", "Advanced OOP", "Object-oriented programming concepts", "COURSE-1", "INSTR-1"));
        
        quizListView.setItems(quizzes);
        quizListView.setOnMouseClicked(e -> loadQuiz());
    }

    private void loadQuiz() {
        Quiz selected = quizListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            currentQuiz = selected;
            quizTitleLabel.setText(selected.getTitle());
            quizDescriptionLabel.setText(selected.getDescription());
            currentQuestionIndex = 0;
            score = 0;
            displayNextQuestion();
        }
    }

    private void displayNextQuestion() {
        questionContainer.getChildren().clear();
        
        if (currentQuiz == null) {
            Label label = new Label("Select a quiz to start");
            questionContainer.getChildren().add(label);
            return;
        }

        Question question = getQuestion(currentQuestionIndex);
        if (question != null) {
            Label questionLabel = new Label("Question " + (currentQuestionIndex + 1) + ": " + question.getQuestionText());
            questionLabel.setWrapText(true);
            questionLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
            questionContainer.getChildren().add(questionLabel);

            ToggleGroup group = new ToggleGroup();
            for (String option : question.getOptions()) {
                RadioButton rb = new RadioButton(option);
                rb.setToggleGroup(group);
                questionContainer.getChildren().add(rb);
            }
        } else {
            submitButton.setText("Quiz Complete");
            Label resultLabel = new Label("Quiz completed! Your score: " + score);
            questionContainer.getChildren().add(resultLabel);
        }
    }

    private Question getQuestion(int index) {
        // Placeholder - will fetch from QuizService
        return null;
    }

    @FXML
    private void handleSubmitAnswer() {
        currentQuestionIndex++;
        displayNextQuestion();
    }

    @FXML
    private void handleViewResults() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz Results");
        alert.setHeaderText("Your Results");
        alert.setContentText("Score: " + score + "\nPassed: " + (score >= 70 ? "Yes" : "No"));
        alert.showAndWait();
    }
}
