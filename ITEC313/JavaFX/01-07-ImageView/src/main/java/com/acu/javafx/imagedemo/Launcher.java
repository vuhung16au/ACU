package com.acu.javafx.imagedemo;

import javafx.application.Application;

/**
 * Launcher class for the JavaFX Image and ImageView Demo application.
 * This class serves as the entry point for the application.
 * 
 * @author ITEC313 Student
 * @version 1.0.0
 */
public class Launcher {
    
    /**
     * Main method that launches the JavaFX application.
     * This method is called by the JavaFX runtime system.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {

        // Uncomment the line below to run the ShowImage example directly
        Application.launch(ShowImage.class, args);

        // Uncomment the line below to run the ImageDemo example directly
        // Application.launch(ImageDemo.class, args);
    }
} 