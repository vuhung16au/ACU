package com.acu.javafx.controlcirclewithouteventhandling;

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
 * Launcher class for the ControlCircleWithoutEventHandling JavaFX application.
 * This class provides a menu to launch the main application.
 */
public class Launcher extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ControlCircleWithoutEventHandling - Launcher");
        
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        // Title
        Label title = new Label("ControlCircleWithoutEventHandling Demo");
        title.setFont(Font.font("Arial", 24));
        
        // Description
        Label description = new Label("This application demonstrates a JavaFX interface with a circle and buttons that have no event handlers.");
        description.setFont(Font.font("Arial", 14));
        description.setWrapText(true);
        description.setMaxWidth(400);
        
        // Launch button
        Button launchButton = new Button("Launch ControlCircleWithoutEventHandling");
        launchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");
        launchButton.setPrefWidth(300);
        launchButton.setPrefHeight(50);
        launchButton.setOnAction(e -> {
            try {
                Stage demoStage = new Stage();
                ControlCircleWithoutEventHandling demoApp = new ControlCircleWithoutEventHandling();
                demoApp.start(demoStage);
            } catch (Exception ex) {
                System.err.println("Error launching demo: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        // Exit button
        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-font-weight: bold;");
        exitButton.setOnAction(e -> Platform.exit());
        
        root.getChildren().addAll(title, description, launchButton, exitButton);
        
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
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