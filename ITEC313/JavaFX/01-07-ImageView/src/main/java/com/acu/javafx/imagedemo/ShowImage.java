package com.acu.javafx.imagedemo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * ShowImage - Demonstrates the Image and ImageView classes.
 * Based on the example from: https://liveexample.pearsoncmg.com/html/ShowImage.html
 * 
 * This application shows how to:
 * - Load images using the Image class
 * - Display images using ImageView
 * - Manipulate image properties (size, rotation)
 * - Create multiple views of the same image
 */
public class ShowImage extends Application {
    
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Create a pane to hold the image views
        Pane pane = new HBox(10);
        pane.setPadding(new Insets(5, 5, 5, 5));
        
        // Load the image from resources
        Image image = new Image(getClass().getResource("/image/au.gif").toExternalForm());
        
        // Create first ImageView with original size
        pane.getChildren().add(new ImageView(image));
        
        // Create second ImageView with custom size
        ImageView imageView2 = new ImageView(image);
        imageView2.setFitHeight(100);
        imageView2.setFitWidth(100);
        pane.getChildren().add(imageView2);
        
        // Create third ImageView with rotation
        ImageView imageView3 = new ImageView(image);
        imageView3.setRotate(90);
        pane.getChildren().add(imageView3);
        
        // Create a scene and place it in the stage
        Scene scene = new Scene(pane);
        primaryStage.setTitle("ShowImage"); // Set the stage title
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