package com.acu.javafx.keymouseeventdemo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Launcher class for the JavaFX Key and Mouse Event Demo application.
 * This class provides separate windows for key and mouse event handling demos.
 * 
 * @author ACU JavaFX Team
 * @version 1.0.0
 */
public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Event Demo Launcher");
        
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        // Title
        Label title = new Label("JavaFX Event Handling Demos");
        title.setFont(Font.font("Arial", 24));
        
        // Description
        Label description = new Label("Choose a demo to open in a separate window:");
        description.setFont(Font.font("Arial", 14));
        
        // Create buttons for each demo
        Button mouseEventButton = new Button("Open Mouse Event Demo");
        mouseEventButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        mouseEventButton.setOnAction(e -> openMouseEventDemo());
        
        Button keyEventButton = new Button("Open Key Event Demo");
        keyEventButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        keyEventButton.setOnAction(e -> openKeyEventDemo());
        
        // Add components to the layout
        root.getChildren().addAll(title, description, mouseEventButton, keyEventButton);
        
        // Create the scene
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Opens the Mouse Event Demo in a separate window
     */
    private void openMouseEventDemo() {
        Stage mouseStage = new Stage();
        mouseStage.setTitle("Mouse Event Demo");
        
        MouseEventDemo mouseDemo = new MouseEventDemo();
        Scene scene = new Scene(mouseDemo, 600, 400);
        
        mouseStage.setScene(scene);
        mouseStage.show();
    }
    
    /**
     * Opens the Key Event Demo in a separate window
     */
    private void openKeyEventDemo() {
        Stage keyStage = new Stage();
        keyStage.setTitle("Key Event Demo");
        
        KeyEventDemo keyDemo = new KeyEventDemo();
        Scene scene = new Scene(keyDemo, 600, 400);
        
        keyStage.setScene(scene);
        
        // Ensure the window gets focus when shown
        keyStage.setOnShown(e -> {
            keyDemo.requestFocus();
        });
        
        keyStage.show();
        
        // Also request focus after showing
        keyDemo.requestFocus();
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
} 