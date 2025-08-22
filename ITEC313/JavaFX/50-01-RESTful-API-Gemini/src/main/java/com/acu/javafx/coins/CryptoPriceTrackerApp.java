package com.acu.javafx.coins;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main JavaFX application class for the Cryptocurrency Price Tracker
 * Launches the GUI application and loads the main view
 */
public class CryptoPriceTrackerApp extends Application {

    /**
     * Application entry point
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and displays the main application window
     * @param primaryStage the primary stage for the application
     * @throws Exception if FXML loading fails
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML layout for the main view
        Parent root = FXMLLoader.load(getClass().getResource("mainView.fxml"));
        primaryStage.setTitle("Cryptocurrency Price Tracker");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
