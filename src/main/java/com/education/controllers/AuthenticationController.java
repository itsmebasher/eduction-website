package com.education.controllers;

import com.education.utils.AuthenticationService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controller for the Login/Register view.
 */
public class AuthenticationController {

    @FXML
    private TabPane authTabPane;

    @FXML
    private TextField loginEmailField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private TextField registerNameField;

    @FXML
    private TextField registerEmailField;

    @FXML
    private PasswordField registerPasswordField;

    @FXML
    private PasswordField registerConfirmPasswordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    public void initialize() {
        setupRoleComboBox();
    }

    private void setupRoleComboBox() {
        roleComboBox.getItems().addAll("STUDENT", "INSTRUCTOR", "ADMIN");
        roleComboBox.setValue("STUDENT");
    }

    @FXML
    private void handleLogin() {
        String email = loginEmailField.getText();
        String password = loginPasswordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter email and password");
            return;
        }

        if (AuthenticationService.login(email, password)) {
            showAlert("Success", "Login successful!");
            // Navigate to main application view
            closeWindow();
        } else {
            showAlert("Error", "Invalid email or password");
        }
    }

    @FXML
    private void handleRegister() {
        String name = registerNameField.getText();
        String email = registerEmailField.getText();
        String password = registerPasswordField.getText();
        String confirmPassword = registerConfirmPasswordField.getText();
        String role = roleComboBox.getValue();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in all fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match");
            return;
        }

        if (password.length() < 6) {
            showAlert("Error", "Password must be at least 6 characters");
            return;
        }

        if (AuthenticationService.emailExists(email)) {
            showAlert("Error", "Email already registered");
            return;
        }

        if (AuthenticationService.registerUser(name, email, password, role)) {
            showAlert("Success", "Registration successful! You can now login.");
            clearRegistrationFields();
            authTabPane.getSelectionModel().selectFirst();
        } else {
            showAlert("Error", "Registration failed");
        }
    }

    @FXML
    private void handleForgotPassword() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Reset Password");
        dialog.setHeaderText("Enter your email to reset password");
        dialog.setContentText("Email:");
        
        dialog.showAndWait().ifPresent(email -> {
            showAlert("Info", "Password reset link sent to: " + email);
        });
    }

    private void clearRegistrationFields() {
        registerNameField.clear();
        registerEmailField.clear();
        registerPasswordField.clear();
        registerConfirmPasswordField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) loginEmailField.getScene().getWindow();
        stage.close();
    }
}
