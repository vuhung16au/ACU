package com.acu.javafx.timelinedemo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * TimelineDemo - A JavaFX application that demonstrates Timeline animations.
 * 
 * This application creates a circle that moves across the screen using
 * Timeline animation. The circle bounces back and forth horizontally.
 * Users can pause the animation by pressing the mouse button and resume it
 * by releasing the mouse button.
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class TimelineDemo extends Application {
    
    private Timeline animation;
    private double dx = 1; // Direction of movement
    private Circle circle;
    
    @Override
    public void start(Stage primaryStage) {
        // Create a pane for the circle
        Pane pane = new Pane();
        
        // Create a circle
        circle = new Circle(10, 10, 20);
        circle.setFill(Color.RED);
        circle.setStroke(Color.BLACK);
        
        // Set initial position
        circle.setCenterX(20);
        circle.setCenterY(100);
        
        pane.getChildren().add(circle);
        
        // Create a timeline animation
        animation = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            // Move the circle
            double x = circle.getCenterX();
            
            // Check boundaries and reverse direction
            if (x <= 20 && dx < 0 || x >= pane.getWidth() - 20 && dx > 0) {
                dx = -dx;
            }
            
            // Update position
            circle.setCenterX(x + dx);
        }));
        
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play(); // Start animation
        
        // Control animation with mouse events
        pane.setOnMousePressed(e -> animation.pause());
        pane.setOnMouseReleased(e -> animation.play());
        
        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 400, 200);
        primaryStage.setTitle("TimelineDemo"); // Set the stage title
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