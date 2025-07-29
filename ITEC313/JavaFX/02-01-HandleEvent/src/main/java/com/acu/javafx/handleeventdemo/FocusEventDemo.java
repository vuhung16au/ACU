package com.acu.javafx.handleeventdemo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Demonstrates focus event handling with text fields
 */
public class FocusEventDemo extends Application {
    
    private TextField textField1;
    private TextField textField2;
    private Label eventLabel;
    private int eventCount = 0;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Focus Event Demo");
        
        // Create main container
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        // Create description
        Label description = new Label("Click on and away from the text fields to see focus events");
        description.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        
        // Create interactive text fields
        textField1 = new TextField();
        textField1.setPromptText("Click here to gain focus");
        textField1.setPrefWidth(300);
        textField1.setStyle("-fx-font-size: 14;");
        
        textField2 = new TextField();
        textField2.setPromptText("Click here to gain focus");
        textField2.setPrefWidth(300);
        textField2.setStyle("-fx-font-size: 14;");
        
        // Create event counter
        eventLabel = new Label("Focus Events: 0");
        eventLabel.setStyle("-fx-font-size: 12;");
        
        // Setup focus event handlers
        setupFocusEventHandlers();
        
        root.getChildren().addAll(description, textField1, textField2, eventLabel);
        
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void setupFocusEventHandlers() {
        // Focus gained for text field 1
        textField1.focusedProperty().addListener((obs, oldVal, newVal) -> {
            eventCount++;
            if (newVal) {
                logEvent("Focus Gained", "Text Field 1 now has focus");
                textField1.setStyle("-fx-font-size: 14; -fx-background-color: lightgreen;");
            } else {
                logEvent("Focus Lost", "Text Field 1 lost focus");
                textField1.setStyle("-fx-font-size: 14; -fx-background-color: white;");
            }
        });
        
        // Focus gained for text field 2
        textField2.focusedProperty().addListener((obs, oldVal, newVal) -> {
            eventCount++;
            if (newVal) {
                logEvent("Focus Gained", "Text Field 2 now has focus");
                textField2.setStyle("-fx-font-size: 14; -fx-background-color: lightblue;");
            } else {
                logEvent("Focus Lost", "Text Field 2 lost focus");
                textField2.setStyle("-fx-font-size: 14; -fx-background-color: white;");
            }
        });
    }
    
    private void logEvent(String eventType, String details) {
        System.out.printf("[%s] %s: %s%n", 
            java.time.LocalTime.now().toString(), eventType, details);
        eventLabel.setText("Focus Events: " + eventCount);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
} 