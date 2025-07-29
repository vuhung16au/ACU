package com.acu.javafx.handleeventdemo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Launcher class for the HandleEvent JavaFX application.
 * This class provides a menu to choose which event demo to run.
 */
public class Launcher extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Event Handling Demo Launcher");
        
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        // Title
        Label title = new Label("JavaFX Event Handling Demos");
        title.setFont(Font.font("Arial", 24));
        
        // Description
        Label description = new Label("Choose a demo to run:");
        description.setFont(Font.font("Arial", 14));
        
        // Demo buttons
        Button basicDemo = createDemoButton("Basic Event Demo", "Simple OK/Cancel buttons", 
            () -> launchDemo(new HandleEvent()));
        
        Button mouseDemo = createDemoButton("Mouse Event Demo", "Circle with mouse interactions", 
            () -> launchDemo(new MouseEventDemo()));
        
        Button keyboardDemo = createDemoButton("Keyboard Event Demo", "Rectangle with keyboard interactions", 
            () -> launchDemo(new KeyboardEventDemo()));
        
        Button dragDropDemo = createDemoButton("Drag & Drop Demo", "Draggable rectangle", 
            () -> launchDemo(new DragDropEventDemo()));
        
        Button wheelDemo = createDemoButton("Wheel Event Demo", "Rectangle that scales on scroll", 
            () -> launchDemo(new WheelEventDemo()));
        
        Button focusDemo = createDemoButton("Focus Event Demo", "Text fields with focus events", 
            () -> launchDemo(new FocusEventDemo()));
        
        Button comprehensiveDemo = createDemoButton("Comprehensive Demo", "All event types in tabs", 
            () -> launchDemo(new ComprehensiveEventDemo()));
        
        // Exit button
        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-font-weight: bold;");
        exitButton.setOnAction(e -> Platform.exit());
        
        root.getChildren().addAll(
            title, description,
            basicDemo, mouseDemo, keyboardDemo, dragDropDemo, wheelDemo, focusDemo, comprehensiveDemo,
            exitButton
        );
        
        Scene scene = new Scene(root, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void launchDemo(Application demoApp) {
        try {
            Stage demoStage = new Stage();
            demoApp.start(demoStage);
        } catch (Exception e) {
            System.err.println("Error launching demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private Button createDemoButton(String title, String description, Runnable action) {
        Button button = new Button(title + "\n" + description);
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12;");
        button.setPrefWidth(300);
        button.setPrefHeight(60);
        button.setOnAction(e -> {
            // Launch the selected demo
            action.run();
        });
        return button;
    }
    
    /**
     * Main method that launches the JavaFX application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
} 