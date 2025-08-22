/**
 * Module configuration for the Cryptocurrency Price Tracker application
 * Defines required dependencies for JavaFX, HTTP client, and JSON parsing
 */
open module com.acu.javafx.coins {
    requires javafx.controls;      // JavaFX UI controls
    requires javafx.fxml;          // JavaFX FXML support
    requires okhttp3;              // HTTP client for API calls
    requires com.fasterxml.jackson.databind;  // JSON parsing library
}