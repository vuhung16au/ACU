package com.acu.javafx.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * JavaFX application entry point for the calculator.
 *
 * Loads the normal calculator view on startup and configures the primary stage.
 */
public class App extends Application {
    /**
     * Launches the JavaFX application.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and shows the primary stage with the normal calculator view.
     * The window is centered, non-resizable, and titled "Calculator".
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Load the initial FXML (normal calculator UI)
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("controllers/normal-view.fxml")));
        Scene scene = new Scene(view);
        stage.setTitle("Calculator");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}


