package com.acu.javafx.bouncingball;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;

/**
 * BounceBallControl is the main JavaFX application that demonstrates
 * a bouncing ball animation with user controls for speed and pause/resume.
 * 
 * Features:
 * - Mouse press/release to pause/resume animation
 * - Up/Down arrow keys to control animation speed
 * - Ball bounces off window boundaries
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class BounceBallControl extends Application {
    
    /**
     * The main entry point for the JavaFX application.
     * Sets up the scene, ball pane, and event handlers.
     * 
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        BallPane ballPane = new BallPane(); // Create a ball pane

        // Pause and resume animation
        ballPane.setOnMousePressed(e -> { e.consume(); ballPane.pause(); });
        ballPane.setOnMouseReleased(e -> { e.consume(); ballPane.play(); });

        // Increase and decrease animation speed
        ballPane.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) {
                ballPane.increaseSpeed();
            } 
            else if (e.getCode() == KeyCode.DOWN) {
                ballPane.decreaseSpeed();
            }
        });

        // Instructions label
        Label instructions = new Label(
            "Controls:\n" +
            " - Mouse press/release to pause/resume animation\n" +
            " - Up/Down arrow keys to control animation speed"
        );
        instructions.setStyle("-fx-font-size: 11px; -fx-text-fill: #444;");
        instructions.setPadding(new Insets(4, 8, 6, 8));

        // Layout: ball in center, instructions at bottom
        BorderPane root = new BorderPane();
        root.setCenter(ballPane);
        root.setBottom(instructions);

        // Create a scene and place it in the stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("BounceBallControl"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        
        // Must request focus after the primary stage is displayed
    ballPane.requestFocus();
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
} 