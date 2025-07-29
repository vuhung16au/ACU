package com.acu.javafx.controlcirclewithmouseandkey;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * JavaFX application that demonstrates circle control using mouse and keyboard events.
 * 
 * Features:
 * - Two buttons (Enlarge/Shrink) for manual control
 * - Mouse click events (left click to enlarge, right click to shrink)
 * - Keyboard events (UP arrow to enlarge, DOWN arrow to shrink)
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class ControlCircleWithMouseAndKey extends Application {
    
    private CirclePane circlePane = new CirclePane();

    /**
     * The main entry point for the JavaFX application.
     * 
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        // Create the UI components
        setupUI(primaryStage);
        
        // Set up event handlers
        setupEventHandlers();
        
        // Display the stage
        primaryStage.show();
    }

    /**
     * Sets up the user interface components.
     * 
     * @param primaryStage the primary stage
     */
    private void setupUI(Stage primaryStage) {
        // Create buttons in an HBox
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        
        Button btEnlarge = new Button("Enlarge");
        Button btShrink = new Button("Shrink");
        
        hBox.getChildren().addAll(btEnlarge, btShrink);
        
        // Create and register button event handlers
        btEnlarge.setOnAction(e -> circlePane.enlarge());
        btShrink.setOnAction(e -> circlePane.shrink());
        
        // Create the main layout
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(circlePane);
        borderPane.setBottom(hBox);
        BorderPane.setAlignment(hBox, Pos.CENTER);
        
        // Create the scene
        Scene scene = new Scene(borderPane, 400, 300);
        primaryStage.setTitle("Control Circle with Mouse and Key");
        primaryStage.setScene(scene);
    }

    /**
     * Sets up event handlers for mouse and keyboard events.
     */
    private void setupEventHandlers() {
        // Mouse click event handler
        circlePane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                // Left click to enlarge
                circlePane.enlarge();
            } else if (e.getButton() == MouseButton.SECONDARY) {
                // Right click to shrink
                circlePane.shrink();
            }
        });
        
        // Keyboard event handler
        circlePane.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) {
                // UP arrow key to enlarge
                circlePane.enlarge();
            } else if (e.getCode() == KeyCode.DOWN) {
                // DOWN arrow key to shrink
                circlePane.shrink();
            }
        });
        
        // Make the circle pane focusable for keyboard events
        circlePane.setFocusTraversable(true);
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