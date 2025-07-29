package com.acu.javafx.controlcirclewithouteventhandling;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * ControlCircleWithoutEventHandling - JavaFX Application
 * 
 * This application demonstrates a simple JavaFX interface with a circle
 * and two buttons (Enlarge and Shrink) that are NOT connected to any
 * event handlers. This shows the basic structure of a JavaFX application
 * without event handling functionality.
 * 
 * Based on the example from: https://liveexample.pearsoncmg.com/html/ControlCircleWithoutEventHandling.html
 * 
 * Key Concepts Demonstrated:
 * - JavaFX Application lifecycle
 * - Scene Graph structure
 * - Layout containers (StackPane, HBox, BorderPane)
 * - Shape creation and styling
 * - Button creation without event handlers
 * - Stage and Scene management
 * 
 * @author ITEC313 Student
 * @version 1.0.0
 */
public class ControlCircleWithoutEventHandling extends Application {
    
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Create a StackPane to hold the circle
        StackPane pane = new StackPane();
        
        // Create a circle and set its properties
        Circle circle = new Circle(50);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
        pane.getChildren().add(circle);
        
        // Create an HBox to hold the buttons
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        
        // Create buttons (without event handlers)
        Button btEnlarge = new Button("Enlarge");
        Button btShrink = new Button("Shrink");
        
        // Add buttons to the HBox
        hBox.getChildren().add(btEnlarge);
        hBox.getChildren().add(btShrink);

        // Create a BorderPane to organize the layout
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(hBox);
        BorderPane.setAlignment(hBox, Pos.CENTER);
        
        // Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 200, 150);
        primaryStage.setTitle("ControlCircle"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
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