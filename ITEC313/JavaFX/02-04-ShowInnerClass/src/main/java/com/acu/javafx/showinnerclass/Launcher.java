package com.acu.javafx.showinnerclass;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Launcher class for the ShowInnerClass JavaFX application.
 * This class provides a menu to choose which inner class demo to run.
 */
public class Launcher extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inner Classes and Anonymous Inner Classes Demo");
        
        BorderPane root = new BorderPane();
        
        // Create title
        Label title = new Label("JavaFX Inner Classes Demonstration");
        title.setFont(Font.font("Arial", 24));
        title.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);
        
        // Create main content area
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        
        // Description
        Label description = new Label("Choose a demonstration to run:");
        description.setFont(Font.font("Arial", 14));
        
        // Demo buttons
        Button innerClassDemo = createDemoButton("Inner Class Demo", 
            "Demonstrates basic inner class concepts", 
            () -> showInnerClassDemo());
        
        Button anonymousHandlerDemo = createDemoButton("Anonymous Handler Demo", 
            "Demonstrates anonymous inner classes for event handling", 
            () -> launchAnonymousHandlerDemo());
        
        Button consoleDemo = createDemoButton("Console Inner Class Demo", 
            "Demonstrates inner class usage in console", 
            () -> showConsoleDemo());
        
        // Exit button
        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-font-weight: bold;");
        exitButton.setOnAction(e -> Platform.exit());
        
        content.getChildren().addAll(
            description,
            innerClassDemo, 
            anonymousHandlerDemo,
            consoleDemo,
            exitButton
        );
        
        root.setCenter(content);
        
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void showInnerClassDemo() {
        try {
            Stage demoStage = new Stage();
            demoStage.setTitle("Inner Class Demo");
            
            VBox root = new VBox(20);
            root.setPadding(new Insets(20));
            root.setAlignment(Pos.CENTER);
            
            Label title = new Label("Inner Class Demonstration");
            title.setFont(Font.font("Arial", 18));
            
            Label explanation = new Label(
                "This demonstrates the concept of inner classes.\n" +
                "An inner class is a class defined within another class.\n" +
                "Inner classes can access all members of the outer class."
            );
            explanation.setWrapText(true);
            explanation.setMaxWidth(400);
            
            Button testButton = new Button("Test Inner Class");
            testButton.setOnAction(e -> {
                ShowInnerClass demo = new ShowInnerClass();
                demo.setData(10);
                demo.m();
            });
            
            TextArea output = new TextArea();
            output.setPrefRowCount(10);
            output.setEditable(false);
            output.setPromptText("Console output will appear here...");
            
            // Redirect System.out to the text area
            System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
                @Override
                public void write(int b) {
                    Platform.runLater(() -> {
                        output.appendText(String.valueOf((char) b));
                    });
                }
            }));
            
            root.getChildren().addAll(title, explanation, testButton, output);
            
            Scene scene = new Scene(root, 500, 400);
            demoStage.setScene(scene);
            demoStage.show();
            
        } catch (Exception e) {
            System.err.println("Error launching inner class demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void launchAnonymousHandlerDemo() {
        try {
            Stage demoStage = new Stage();
            AnonymousHandlerDemo demoApp = new AnonymousHandlerDemo();
            demoApp.start(demoStage);
        } catch (Exception e) {
            System.err.println("Error launching anonymous handler demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void showConsoleDemo() {
        try {
            Stage demoStage = new Stage();
            demoStage.setTitle("Console Inner Class Demo");
            
            VBox root = new VBox(20);
            root.setPadding(new Insets(20));
            root.setAlignment(Pos.CENTER);
            
            Label title = new Label("Console Inner Class Demo");
            title.setFont(Font.font("Arial", 18));
            
            Label explanation = new Label(
                "This demonstrates inner class usage in a console application.\n" +
                "Click the button to see the inner class in action."
            );
            explanation.setWrapText(true);
            explanation.setMaxWidth(400);
            
            Button testButton = new Button("Run Console Demo");
            testButton.setOnAction(e -> {
                ShowInnerClass demo = new ShowInnerClass();
                demo.setData(5);
                System.out.println("Initial data: " + demo.getData());
                demo.m();
                System.out.println("Final data: " + demo.getData());
            });
            
            TextArea output = new TextArea();
            output.setPrefRowCount(8);
            output.setEditable(false);
            output.setPromptText("Console output will appear here...");
            
            root.getChildren().addAll(title, explanation, testButton, output);
            
            Scene scene = new Scene(root, 500, 400);
            demoStage.setScene(scene);
            demoStage.show();
            
        } catch (Exception e) {
            System.err.println("Error launching console demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private Button createDemoButton(String title, String description, Runnable action) {
        Button button = new Button(title + "\n" + description);
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12;");
        button.setPrefWidth(300);
        button.setPrefHeight(60);
        button.setOnAction(e -> action.run());
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