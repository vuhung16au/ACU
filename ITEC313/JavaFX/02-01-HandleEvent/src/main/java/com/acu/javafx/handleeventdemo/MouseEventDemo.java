package com.acu.javafx.handleeventdemo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Demonstrates mouse event handling with a simple circle
 */
public class MouseEventDemo extends Application {
    
    private Circle circle;
    private Label eventLabel;
    private int eventCount = 0;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mouse Event Demo");
        
        // Create main container
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        // Create description
        Label description = new Label("Click, drag, and move over the circle to see mouse events");
        description.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        
        // Create interactive circle
        circle = new Circle(100, 100, 50);
        circle.setFill(Color.LIGHTBLUE);
        circle.setStroke(Color.DARKBLUE);
        circle.setStrokeWidth(2);
        
        // Create event counter
        eventLabel = new Label("Mouse Events: 0");
        eventLabel.setStyle("-fx-font-size: 12;");
        
        // Setup mouse event handlers
        setupMouseEventHandlers();
        
        root.getChildren().addAll(description, circle, eventLabel);
        
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void setupMouseEventHandlers() {
        // Mouse click events
        circle.setOnMouseClicked(e -> {
            eventCount++;
            logEvent("Mouse Click", String.format("Position: (%.1f, %.1f), Button: %s", 
                e.getX(), e.getY(), e.getButton()));
        });
        
        // Mouse press events
        circle.setOnMousePressed(e -> {
            eventCount++;
            logEvent("Mouse Press", String.format("Position: (%.1f, %.1f), Button: %s", 
                e.getX(), e.getY(), e.getButton()));
        });
        
        // Mouse release events
        circle.setOnMouseReleased(e -> {
            eventCount++;
            logEvent("Mouse Release", String.format("Position: (%.1f, %.1f), Button: %s", 
                e.getX(), e.getY(), e.getButton()));
        });
        
        // Mouse enter events
        circle.setOnMouseEntered(e -> {
            eventCount++;
            logEvent("Mouse Enter", String.format("Position: (%.1f, %.1f)", e.getX(), e.getY()));
            circle.setFill(Color.LIGHTGREEN);
        });
        
        // Mouse exit events
        circle.setOnMouseExited(e -> {
            eventCount++;
            logEvent("Mouse Exit", String.format("Position: (%.1f, %.1f)", e.getX(), e.getY()));
            circle.setFill(Color.LIGHTBLUE);
        });
        
        // Mouse move events
        circle.setOnMouseMoved(e -> {
            eventCount++;
            logEvent("Mouse Move", String.format("Position: (%.1f, %.1f)", e.getX(), e.getY()));
        });
        
        // Mouse drag events
        circle.setOnMouseDragged(e -> {
            eventCount++;
            logEvent("Mouse Drag", String.format("Position: (%.1f, %.1f)", e.getX(), e.getY()));
            
            // Move the circle with the mouse
            circle.setCenterX(e.getX());
            circle.setCenterY(e.getY());
        });
    }
    
    private void logEvent(String eventType, String details) {
        System.out.printf("[%s] %s: %s%n", 
            java.time.LocalTime.now().toString(), eventType, details);
        eventLabel.setText("Mouse Events: " + eventCount);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
} 