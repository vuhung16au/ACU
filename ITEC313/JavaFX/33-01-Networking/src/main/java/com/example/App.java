package com.example;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App - Modified to launch StudentClient
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Launch the StudentClient instead of the original Hello World app
        StudentClient studentClient = new StudentClient();
        studentClient.start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}