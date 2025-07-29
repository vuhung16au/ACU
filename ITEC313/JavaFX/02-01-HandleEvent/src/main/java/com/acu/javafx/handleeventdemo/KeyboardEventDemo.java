package com.acu.javafx.handleeventdemo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Demonstrates keyboard event handling with a simple rectangle
 */
public class KeyboardEventDemo extends Application {
    
    private Rectangle rectangle;
    private Label eventLabel;
    private int eventCount = 0;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Keyboard Event Demo");
        
        // Create main container
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        // Create description
        Label description = new Label("Click on the rectangle and press keys to see keyboard events");
        description.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        
        // Create interactive rectangle
        rectangle = new Rectangle(100, 50, 200, 100);
        rectangle.setFill(Color.LIGHTGREEN);
        rectangle.setStroke(Color.DARKGREEN);
        rectangle.setStrokeWidth(2);
        
        // Create event counter
        eventLabel = new Label("Keyboard Events: 0");
        eventLabel.setStyle("-fx-font-size: 12;");
        
        // Setup keyboard event handlers
        setupKeyboardEventHandlers();
        
        root.getChildren().addAll(description, rectangle, eventLabel);
        
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void setupKeyboardEventHandlers() {
        // Key press events
        rectangle.setOnKeyPressed(e -> {
            eventCount++;
            logEvent("Key Press", String.format("Key: %s, Code: %s, Text: '%s'", 
                e.getText(), e.getCode(), e.getText()));
            
            // Change color based on key
            if (e.getCode() == KeyCode.R) {
                rectangle.setFill(Color.RED);
            } else if (e.getCode() == KeyCode.G) {
                rectangle.setFill(Color.GREEN);
            } else if (e.getCode() == KeyCode.B) {
                rectangle.setFill(Color.BLUE);
            } else if (e.getCode() == KeyCode.Y) {
                rectangle.setFill(Color.YELLOW);
            } else if (e.getCode() == KeyCode.P) {
                rectangle.setFill(Color.PURPLE);
            }
        });
        
        // Key release events
        rectangle.setOnKeyReleased(e -> {
            eventCount++;
            logEvent("Key Release", String.format("Key: %s, Code: %s", e.getText(), e.getCode()));
        });
        
        // Key typed events
        rectangle.setOnKeyTyped(e -> {
            eventCount++;
            logEvent("Key Typed", String.format("Character: '%s'", e.getCharacter()));
        });
        
        // Make rectangle focusable and clickable
        rectangle.setFocusTraversable(true);
        rectangle.setOnMouseClicked(e -> rectangle.requestFocus());
    }
    
    private void logEvent(String eventType, String details) {
        System.out.printf("[%s] %s: %s%n", 
            java.time.LocalTime.now().toString(), eventType, details);
        eventLabel.setText("Keyboard Events: " + eventCount);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
} 