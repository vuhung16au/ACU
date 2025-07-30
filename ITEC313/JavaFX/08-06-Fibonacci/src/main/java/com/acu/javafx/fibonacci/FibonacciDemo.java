package com.acu.javafx.fibonacci;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;

/**
 * FibonacciDemo - JavaFX Application
 * 
 * A JavaFX application that demonstrates both recursive and iterative
 * approaches to computing Fibonacci numbers with a modern, responsive UI.
 * 
 * Features:
 * - Input validation for Fibonacci index
 * - Real-time computation with progress indication
 * - Performance comparison between approaches
 * - Modern, clean UI design
 * - Error handling for invalid inputs
 */
public class FibonacciDemo extends Application {
    
    private TextField indexField;
    private TextArea resultArea;
    private Button computeRecursiveButton;
    private Button computeIterativeButton;
    private Button compareButton;
    private ProgressIndicator progressIndicator;
    private Label statusLabel;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Fibonacci Calculator - JavaFX Demo");
        
        // Create main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        
        // Title
        Label titleLabel = new Label("Fibonacci Calculator");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKBLUE);
        
        // Input section
        VBox inputSection = createInputSection();
        
        // Buttons section
        HBox buttonSection = createButtonSection();
        
        // Progress and status
        HBox progressSection = createProgressSection();
        
        // Results section
        VBox resultSection = createResultSection();
        
        // Add all sections to main layout
        root.getChildren().addAll(
            titleLabel,
            inputSection,
            buttonSection,
            progressSection,
            resultSection
        );
        
        // Create scene
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    
    /**
     * Creates the input section with label and text field
     */
    private VBox createInputSection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER);
        
        Label inputLabel = new Label("Enter Fibonacci Index:");
        inputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        indexField = new TextField();
        indexField.setPromptText("Enter a number (0-50 recommended)");
        indexField.setMaxWidth(300);
        indexField.setPrefHeight(35);
        
        // Add input validation
        indexField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                indexField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        section.getChildren().addAll(inputLabel, indexField);
        return section;
    }
    
    /**
     * Creates the button section with compute buttons
     */
    private HBox createButtonSection() {
        HBox section = new HBox(15);
        section.setAlignment(Pos.CENTER);
        
        computeRecursiveButton = new Button("Compute (Recursive)");
        computeRecursiveButton.setPrefSize(150, 40);
        computeRecursiveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        
        computeIterativeButton = new Button("Compute (Iterative)");
        computeIterativeButton.setPrefSize(150, 40);
        computeIterativeButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        
        compareButton = new Button("Compare Both");
        compareButton.setPrefSize(150, 40);
        compareButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold;");
        
        // Add button event handlers
        computeRecursiveButton.setOnAction(e -> computeRecursive());
        computeIterativeButton.setOnAction(e -> computeIterative());
        compareButton.setOnAction(e -> compareBoth());
        
        section.getChildren().addAll(computeRecursiveButton, computeIterativeButton, compareButton);
        return section;
    }
    
    /**
     * Creates the progress section with progress indicator and status
     */
    private HBox createProgressSection() {
        HBox section = new HBox(15);
        section.setAlignment(Pos.CENTER);
        
        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        progressIndicator.setPrefSize(30, 30);
        
        statusLabel = new Label("Ready");
        statusLabel.setFont(Font.font("Arial", 12));
        statusLabel.setTextFill(Color.GRAY);
        
        section.getChildren().addAll(progressIndicator, statusLabel);
        return section;
    }
    
    /**
     * Creates the result section with text area
     */
    private VBox createResultSection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER);
        
        Label resultLabel = new Label("Results:");
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        resultArea = new TextArea();
        resultArea.setPromptText("Results will appear here...");
        resultArea.setPrefRowCount(8);
        resultArea.setPrefColumnCount(50);
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setStyle("-fx-font-family: 'Monaco', 'Consolas', monospace; -fx-font-size: 12;");
        
        section.getChildren().addAll(resultLabel, resultArea);
        return section;
    }
    
    /**
     * Computes Fibonacci using recursive approach
     */
    private void computeRecursive() {
        if (!validateInput()) return;
        
        int index = Integer.parseInt(indexField.getText());
        setComputing(true);
        
        Task<Long> task = new Task<>() {
            @Override
            protected Long call() throws Exception {
                updateMessage("Computing recursively...");
                long startTime = System.currentTimeMillis();
                long result = ComputeFibonacci.fib(index);
                long endTime = System.currentTimeMillis();
                
                Platform.runLater(() -> {
                    String output = String.format(
                        "=== RECURSIVE FIBONACCI ===\n" +
                        "Index: %d\n" +
                        "Result: %d\n" +
                        "Computation Time: %d ms\n" +
                        "Note: Recursive approach has O(2^n) complexity\n" +
                        "========================\n\n",
                        index, result, (endTime - startTime)
                    );
                    resultArea.appendText(output);
                });
                
                return result;
            }
        };
        
        task.messageProperty().addListener((obs, oldMsg, newMsg) -> 
            statusLabel.setText(newMsg));
        
        task.setOnSucceeded(e -> setComputing(false));
        task.setOnFailed(e -> {
            setComputing(false);
            showError("Computation failed: " + task.getException().getMessage());
        });
        
        new Thread(task).start();
    }
    
    /**
     * Computes Fibonacci using iterative approach
     */
    private void computeIterative() {
        if (!validateInput()) return;
        
        int index = Integer.parseInt(indexField.getText());
        setComputing(true);
        
        Task<Long> task = new Task<>() {
            @Override
            protected Long call() throws Exception {
                updateMessage("Computing iteratively...");
                long startTime = System.currentTimeMillis();
                long result = ImprovedFibonacci.fib(index);
                long endTime = System.currentTimeMillis();
                
                Platform.runLater(() -> {
                    String output = String.format(
                        "=== ITERATIVE FIBONACCI ===\n" +
                        "Index: %d\n" +
                        "Result: %d\n" +
                        "Computation Time: %d ms\n" +
                        "Note: Iterative approach has O(n) complexity\n" +
                        "========================\n\n",
                        index, result, (endTime - startTime)
                    );
                    resultArea.appendText(output);
                });
                
                return result;
            }
        };
        
        task.messageProperty().addListener((obs, oldMsg, newMsg) -> 
            statusLabel.setText(newMsg));
        
        task.setOnSucceeded(e -> setComputing(false));
        task.setOnFailed(e -> {
            setComputing(false);
            showError("Computation failed: " + task.getException().getMessage());
        });
        
        new Thread(task).start();
    }
    
    /**
     * Compares both approaches
     */
    private void compareBoth() {
        if (!validateInput()) return;
        
        int index = Integer.parseInt(indexField.getText());
        setComputing(true);
        
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("Comparing both approaches...");
                
                // Compute recursive
                long startTime = System.currentTimeMillis();
                long recursiveResult = ComputeFibonacci.fib(index);
                long recursiveTime = System.currentTimeMillis() - startTime;
                
                // Compute iterative
                startTime = System.currentTimeMillis();
                long iterativeResult = ImprovedFibonacci.fib(index);
                long iterativeTime = System.currentTimeMillis() - startTime;
                
                Platform.runLater(() -> {
                    String output = String.format(
                        "=== COMPARISON RESULTS ===\n" +
                        "Index: %d\n" +
                        "Recursive Result: %d (Time: %d ms)\n" +
                        "Iterative Result: %d (Time: %d ms)\n" +
                        "Speed Difference: %.2fx faster (iterative)\n" +
                        "========================\n\n",
                        index, recursiveResult, recursiveTime,
                        iterativeResult, iterativeTime,
                        (double) recursiveTime / iterativeTime
                    );
                    resultArea.appendText(output);
                });
                
                return null;
            }
        };
        
        task.messageProperty().addListener((obs, oldMsg, newMsg) -> 
            statusLabel.setText(newMsg));
        
        task.setOnSucceeded(e -> setComputing(false));
        task.setOnFailed(e -> {
            setComputing(false);
            showError("Comparison failed: " + task.getException().getMessage());
        });
        
        new Thread(task).start();
    }
    
    /**
     * Validates user input
     */
    private boolean validateInput() {
        String input = indexField.getText().trim();
        if (input.isEmpty()) {
            showError("Please enter a number.");
            return false;
        }
        
        try {
            int index = Integer.parseInt(input);
            if (index < 0) {
                showError("Please enter a non-negative number.");
                return false;
            }
            if (index > 50) {
                showWarning("Large numbers may take a long time to compute recursively.");
            }
        } catch (NumberFormatException e) {
            showError("Please enter a valid number.");
            return false;
        }
        
        return true;
    }
    
    /**
     * Sets the computing state (enables/disables UI elements)
     */
    private void setComputing(boolean computing) {
        progressIndicator.setVisible(computing);
        computeRecursiveButton.setDisable(computing);
        computeIterativeButton.setDisable(computing);
        compareButton.setDisable(computing);
        indexField.setDisable(computing);
        
        if (!computing) {
            statusLabel.setText("Ready");
        }
    }
    
    /**
     * Shows an error message
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Shows a warning message
     */
    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        launch(args);
    }
} 