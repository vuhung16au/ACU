package com.acu.javafx.textdemo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FontDemo - Demonstrates the Font class usage.
 * Based on the example from: https://liveexample.pearsoncmg.com/html/FontDemo.html
 * 
 * This application shows how to:
 * - Create and configure fonts with different families, weights, and postures
 * - Apply fonts to UI controls
 * - Use color with text styling
 */
public class FontDemo extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {

        // Create a pane to hold the circle 
        Pane pane = new StackPane();
        
        // Create a circle and set its properties
        Circle circle = new Circle();
        circle.setRadius(50);
        circle.setStroke(Color.BLACK); 
        circle.setFill(new Color(0.5, 0.5, 0.5, 0.1));
        pane.getChildren().add(circle); // Add circle to the pane

        // Create a label and set its properties
        Label label = new Label("JavaFX");
        label.setFont(Font.font("Times New Roman", 
          FontWeight.BOLD, FontPosture.ITALIC, 20));
        pane.getChildren().add(label);

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane);
        primaryStage.setTitle("FontDemo"); // Set the stage title
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