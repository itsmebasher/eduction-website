package com.education;

import com.education.utils.AuthenticationService;
import com.education.utils.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main entry point for the Education Platform JavaFX application.
 * Supports SDG 4: Quality Education by providing access to learning resources.
 */
public class MainApplication extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        // Initialize database
        DatabaseManager.getInstance();
        
        // Load appropriate view based on authentication status
        FXMLLoader fxmlLoader;
        Scene scene;
        
        if (AuthenticationService.isLoggedIn()) {
            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/education/views/main-view.fxml"));
            scene = new Scene(fxmlLoader.load(), 1200, 800);
        } else {
            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/education/views/auth-view.fxml"));
            scene = new Scene(fxmlLoader.load(), 500, 500);
        }
        
        String css = getClass().getResource("/css/styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        stage.setTitle("Education Platform");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
