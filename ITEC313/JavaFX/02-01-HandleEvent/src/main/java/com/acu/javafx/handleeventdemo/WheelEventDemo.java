package com.acu.javafx.handleeventdemo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Demonstrates wheel event handling with a rectangle that scales on scroll
 */
public class WheelEventDemo extends Application {
    
    private Rectangle rectangle;
    private Label eventLabel;
    private int eventCount = 0;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Wheel Event Demo");
        
        // Create main container
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        // Create description
        Label description = new Label("Scroll over the rectangle to see wheel events and scaling");
        description.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        
        // Create interactive rectangle
        rectangle = new Rectangle(100, 50, 200, 100);
        rectangle.setFill(Color.LIGHTGRAY);
        rectangle.setStroke(Color.DARKGRAY);
        rectangle.setStrokeWidth(2);
        
        // Create event counter
        eventLabel = new Label("Wheel Events: 0");
        eventLabel.setStyle("-fx-font-size: 12;");
        
        // Setup wheel event handlers
        setupWheelEventHandlers();
        
        root.getChildren().addAll(description, rectangle, eventLabel);
        
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void setupWheelEventHandlers() {
        // Wheel events
        rectangle.setOnScroll(e -> {
            eventCount++;
            logEvent("Wheel Scroll", String.format("Delta: (%.1f, %.1f), Text Delta: %.1f", 
                e.getDeltaX(), e.getDeltaY(), e.getTextDeltaY()));
            
            // Scale the rectangle based on scroll
            double scaleX = rectangle.getScaleX();
            double scaleY = rectangle.getScaleY();
            
            if (e.getDeltaY() > 0) {
                // Scroll up - zoom in
                rectangle.setScaleX(scaleX * 1.1);
                rectangle.setScaleY(scaleY * 1.1);
            } else {
                // Scroll down - zoom out
                rectangle.setScaleX(scaleX * 0.9);
                rectangle.setScaleY(scaleY * 0.9);
            }
        });
    }
    
    private void logEvent(String eventType, String details) {
        System.out.printf("[%s] %s: %s%n", 
            java.time.LocalTime.now().toString(), eventType, details);
        eventLabel.setText("Wheel Events: " + eventCount);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
} 