package com.acu.javafx.fadetransitiondemo;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FadeTransitionDemo - A JavaFX application that demonstrates fade transitions.
 * 
 * This application creates an ellipse that continuously fades in and out.
 * Users can pause the animation by pressing the mouse button and resume it
 * by releasing the mouse button.
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class FadeTransitionDemo extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Place an ellipse to the pane
        Pane pane = new Pane();
        Ellipse ellipse = new Ellipse(10, 10, 100, 50);
        ellipse.setFill(Color.RED);
        ellipse.setStroke(Color.BLACK);
        
        // Bind ellipse properties to pane size for responsive design
        ellipse.centerXProperty().bind(pane.widthProperty().divide(2));
        ellipse.centerYProperty().bind(pane.heightProperty().divide(2));
        ellipse.radiusXProperty().bind(
            pane.widthProperty().multiply(0.4));
        ellipse.radiusYProperty().bind(
            pane.heightProperty().multiply(0.4));
        
        pane.getChildren().add(ellipse);
        
        // Apply a fade transition to ellipse
        FadeTransition ft = 
            new FadeTransition(Duration.millis(3000), ellipse);
        ft.setFromValue(1.0);
        ft.setToValue(0.1);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play(); // Start animation
        
        // Control animation with mouse events
        ellipse.setOnMousePressed(e -> ft.pause());
        ellipse.setOnMouseReleased(e -> ft.play());
        
        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 200, 150);
        primaryStage.setTitle("FadeTransitionDemo"); // Set the stage title
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