package com.acu.javafx.anonymoushandlerdemo;

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
 * Launcher class for the AnonymousHandlerDemo JavaFX application.
 * This class provides a menu to choose which SAM interface demo to run.
 * 
 * Demonstrates:
 * - Single Abstract Method (SAM) interfaces
 * - Anonymous inner classes vs lambda expressions
 * - Event handling patterns in JavaFX
 */
public class Launcher extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SAM Interfaces and Anonymous Handlers Demo");
        
        BorderPane root = new BorderPane();
        
        // Create title
        Label title = new Label("Single Abstract Method (SAM) Interfaces");
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
        Button anonymousHandlerDemo = createDemoButton("Anonymous Handler Demo", 
            "Demonstrates anonymous inner classes for event handling", 
            () -> launchAnonymousHandlerDemo());
        
        Button lambdaDemo = createDemoButton("Lambda Expression Demo", 
            "Demonstrates lambda expressions vs anonymous inner classes", 
            () -> showLambdaDemo());
        
        Button samConceptDemo = createDemoButton("SAM Interface Concept Demo", 
            "Explains Single Abstract Method interfaces", 
            () -> showSAMConceptDemo());
        
        // Exit button
        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-font-weight: bold;");
        exitButton.setOnAction(e -> Platform.exit());
        
        content.getChildren().addAll(
            description,
            anonymousHandlerDemo,
            lambdaDemo,
            samConceptDemo,
            exitButton
        );
        
        root.setCenter(content);
        
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
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
    
    private void showLambdaDemo() {
        try {
            Stage demoStage = new Stage();
            demoStage.setTitle("Lambda Expression Demo");
            
            VBox root = new VBox(20);
            root.setPadding(new Insets(20));
            root.setAlignment(Pos.CENTER);
            
            Label title = new Label("Lambda Expressions vs Anonymous Inner Classes");
            title.setFont(Font.font("Arial", 18));
            
            Label explanation = new Label(
                "This demonstrates the difference between lambda expressions\n" +
                "and anonymous inner classes for SAM interfaces.\n\n" +
                "Both approaches work with functional interfaces that have\n" +
                "exactly one abstract method."
            );
            explanation.setWrapText(true);
            explanation.setMaxWidth(400);
            
            Button lambdaButton = new Button("Lambda Expression");
            lambdaButton.setOnAction(e -> {
                System.out.println("Lambda expression executed!");
                System.out.println("This is equivalent to an anonymous inner class.");
            });
            
            Button anonymousButton = new Button("Anonymous Inner Class");
            anonymousButton.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent event) {
                    System.out.println("Anonymous inner class executed!");
                    System.out.println("This is equivalent to a lambda expression.");
                }
            });
            
            TextArea output = new TextArea();
            output.setPrefRowCount(8);
            output.setEditable(false);
            output.setPromptText("Console output will appear here...");
            
            root.getChildren().addAll(title, explanation, lambdaButton, anonymousButton, output);
            
            Scene scene = new Scene(root, 500, 400);
            demoStage.setScene(scene);
            demoStage.show();
            
        } catch (Exception e) {
            System.err.println("Error launching lambda demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void showSAMConceptDemo() {
        try {
            Stage demoStage = new Stage();
            demoStage.setTitle("SAM Interface Concept Demo");
            
            VBox root = new VBox(20);
            root.setPadding(new Insets(20));
            root.setAlignment(Pos.CENTER);
            
            Label title = new Label("Single Abstract Method (SAM) Interfaces");
            title.setFont(Font.font("Arial", 18));
            
            Label explanation = new Label(
                "A SAM interface is a functional interface that contains\n" +
                "exactly one abstract method. Examples include:\n\n" +
                "• EventHandler<T> - handle(T event)\n" +
                "• Runnable - run()\n" +
                "• Callable<T> - call()\n" +
                "• Comparator<T> - compare(T o1, T o2)\n\n" +
                "These interfaces can be used with:\n" +
                "• Anonymous inner classes\n" +
                "• Lambda expressions\n" +
                "• Method references"
            );
            explanation.setWrapText(true);
            explanation.setMaxWidth(450);
            
            Button testButton = new Button("Test SAM Interface");
            testButton.setOnAction(e -> {
                System.out.println("Testing SAM interface with lambda:");
                Runnable runnable = () -> System.out.println("Hello from lambda!");
                runnable.run();
                
                System.out.println("\nTesting SAM interface with anonymous class:");
                Runnable runnable2 = new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Hello from anonymous inner class!");
                    }
                };
                runnable2.run();
            });
            
            TextArea output = new TextArea();
            output.setPrefRowCount(8);
            output.setEditable(false);
            output.setPromptText("Console output will appear here...");
            
            root.getChildren().addAll(title, explanation, testButton, output);
            
            Scene scene = new Scene(root, 500, 500);
            demoStage.setScene(scene);
            demoStage.show();
            
        } catch (Exception e) {
            System.err.println("Error launching SAM concept demo: " + e.getMessage());
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