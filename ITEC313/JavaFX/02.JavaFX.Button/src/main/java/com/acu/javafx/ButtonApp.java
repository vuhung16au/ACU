package com.acu.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX Button Demo Application
 * 
 * This application demonstrates the basic structure of a JavaFX application:
 * - Application: Entry point that extends Application class
 * - Stage: The top-level container (window)
 * - Scene: The content container that holds all UI elements
 * - Nodes: UI elements like buttons, labels, etc.
 */
public class ButtonApp extends Application {
    
    /**
     * The start() method is the main entry point for all JavaFX applications.
     * It's called after the init() method has returned and after the system
     * is ready for the application to begin running.
     * 
     * @param stage the primary stage for this application, onto which
     * the application scene can be set
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML file that defines our UI layout
        FXMLLoader fxmlLoader = new FXMLLoader(ButtonApp.class.getResource("/fxml/button-view.fxml"));
        
        // Create a Scene with the loaded FXML content
        // Scene represents the content of the stage
        Scene scene = new Scene(fxmlLoader.load(), 600, 800);
        
        // Configure the Stage (window)
        stage.setTitle("JavaFX Button Demo - Stage, Scene & Button Example");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinWidth(350);
        stage.setMinHeight(250);
        
        // Show the stage (make the window visible)
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX applications.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
