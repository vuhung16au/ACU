package com.acu.javafx.clockpanesdemo;

import javafx.application.Application;

/**
 * Launcher class for the ClockPane JavaFX application.
 * This class serves as the main entry point for the application.
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class Launcher {
    
    /**
     * Main method that launches the JavaFX application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Application.launch(DisplayClock.class, args);
    }
} 