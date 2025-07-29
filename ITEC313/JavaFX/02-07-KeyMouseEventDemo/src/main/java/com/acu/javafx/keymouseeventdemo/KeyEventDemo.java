package com.acu.javafx.keymouseeventdemo;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * KeyEventDemo demonstrates keyboard event handling in JavaFX.
 * This class creates a text element that can be moved with arrow keys
 * and can have its content changed by typing.
 * 
 * Based on the example from: https://liveexample.pearsoncmg.com/html/KeyEventDemo.html
 * 
 * @author ACU JavaFX Team
 * @version 1.0.0
 */
public class KeyEventDemo extends Pane {

    private Text text;

    /**
     * Constructor for KeyEventDemo.
     * Initializes the pane with a keyboard-controllable text element.
     */
    public KeyEventDemo() {
        // Create a pane and set its properties
        text = new Text(20, 20, "A");
        
        // Add the text to the pane
        getChildren().add(text);
        
        // Set up key pressed event handler for the pane
        setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case DOWN:
                    text.setY(text.getY() + 10);
                    break;
                case UP:
                    text.setY(text.getY() - 10);
                    break;
                case LEFT:
                    text.setX(text.getX() - 10);
                    break;
                case RIGHT:
                    text.setX(text.getX() + 10);
                    break;
                default:
                    // If a printable character is pressed, update the text
                    if (e.getText().length() > 0) {
                        text.setText(e.getText());
                    }
            }
        });
        
        // Add some styling to make the text more visible
        text.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: #E74C3C;");
        
        // Set the pane background
        setStyle("-fx-background-color: #F7F7F7;");
        
        // Make the pane focusable so it can receive keyboard input
        setFocusTraversable(true);
        
        // Request focus for the pane so it can receive key input
        requestFocus();
        
        // Add a mouse click handler to ensure focus when clicked
        setOnMouseClicked(e -> {
            requestFocus();
        });
    }
}
