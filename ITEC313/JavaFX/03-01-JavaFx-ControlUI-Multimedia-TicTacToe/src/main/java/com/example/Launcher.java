package com.example;

/**
 * Application launcher for JavaFX Control UI Multimedia Demo.
 * 
 * This class serves as the entry point for the JavaFX application.
 * It delegates to the main application class to avoid module-path issues
 * when JavaFX is not on the module path.
 * 
 * @author ITEC313 Student
 * @version 1.0.0
 */
public class Launcher {
    /**
     * Main method that launches the JavaFX application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Launch the main JavaFX application
        JavaFXControlsDemo.main(args);
    }
}
