package com.acu.javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

/**
 * Controller class for the Button Demo
 * 
 * This class handles the events and logic for our JavaFX application.
 * The @FXML annotation links this controller to the FXML file.
 */
public class ButtonController {
    
    // These fields are automatically injected from the FXML file
    // The fx:id in FXML must match the field name here
    @FXML
    private Button clickMeButton;
    
    @FXML
    private Button resetButton;
    
    @FXML
    private Label statusLabel;
    
    @FXML
    private Label clickCountLabel;
    
    // Counter to track button clicks
    private int clickCount = 0;
    
    /**
     * This method is automatically called after the FXML file has been loaded.
     * It's useful for initializing the controller after all FXML elements have been injected.
     */
    @FXML
    private void initialize() {
        // Initialize the labels
        statusLabel.setText("Welcome to JavaFX Button Demo!");
        clickCountLabel.setText("Click count: 0");
        
        // You can also set button properties programmatically
        clickMeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        resetButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
    }
    
    /**
     * Event handler for the "Click Me!" button
     * This method is called when the button is clicked
     * 
     * @param event the action event triggered by the button click
     */
    @FXML
    private void onClickMeButtonClick(ActionEvent event) {
        clickCount++;
        statusLabel.setText("Button was clicked! 🎉");
        clickCountLabel.setText("Click count: " + clickCount);
        
        // Change button text based on click count
        if (clickCount == 1) {
            clickMeButton.setText("Click Me Again!");
        } else if (clickCount >= 5) {
            clickMeButton.setText("You're on fire! 🔥");
            statusLabel.setText("Wow! You've clicked " + clickCount + " times! 🚀");
        }
        
        System.out.println("Button clicked! Count: " + clickCount);
    }
    
    /**
     * Event handler for the "Reset" button
     * This method resets the application to its initial state
     * 
     * @param event the action event triggered by the button click
     */
    @FXML
    private void onResetButtonClick(ActionEvent event) {
        clickCount = 0;
        statusLabel.setText("Counter has been reset!");
        clickCountLabel.setText("Click count: 0");
        clickMeButton.setText("Click Me!");
        
        System.out.println("Counter reset!");
    }
}
