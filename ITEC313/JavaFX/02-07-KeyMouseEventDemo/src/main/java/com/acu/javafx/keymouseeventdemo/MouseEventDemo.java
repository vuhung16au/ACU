package com.acu.javafx.keymouseeventdemo;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * MouseEventDemo demonstrates mouse event handling in JavaFX.
 * This class creates a draggable text element that responds to mouse drag events.
 * 
 * Based on the example from: https://liveexample.pearsoncmg.com/html/MouseEventDemo.html
 * 
 * @author ACU JavaFX Team
 * @version 1.0.0
 */
public class MouseEventDemo extends Pane {

    /**
     * Constructor for MouseEventDemo.
     * Initializes the pane with a draggable text element.
     */
    public MouseEventDemo() {
        // Create a pane and set its properties
        Text text = new Text(20, 20, "Programming is fun");
        
        // Add the text to the pane
        getChildren().addAll(text);
        
        // Set up mouse drag event handler
        text.setOnMouseDragged(e -> {
            // Update text position based on mouse coordinates
            text.setX(e.getX());
            text.setY(e.getY());
        });
        
        // Add some styling to make the text more visible
        text.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: #2E86AB;");
        
        // Set the pane background
        setStyle("-fx-background-color: #F7F7F7;");
    }
} 