package com.acu.javafx.rotate;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

/**
 * Simple JavaFX application demonstrating basic node styling and rotation.
 * This is the basic implementation as specified in the original requirements.
 */
public class SimpleNodeStyleRotateDemo extends Application {
    
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Create a scene and place a button in the scene
        StackPane pane = new StackPane();
        Button btOK = new Button("OK");
        btOK.setStyle("-fx-border-color: blue;");
        pane.getChildren().add(btOK);    
        
        pane.setRotate(45); // Rotate pane 45 degrees
        pane.setStyle(
          "-fx-border-color: red; -fx-background-color: lightgray;");
        
        Scene scene = new Scene(pane, 200, 250);
        primaryStage.setTitle("NodeStyleRotateDemo"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }
    
    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
} 