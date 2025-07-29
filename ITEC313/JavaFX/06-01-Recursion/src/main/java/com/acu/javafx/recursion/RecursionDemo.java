package com.acu.javafx.recursion;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.File;
import java.util.Arrays;

public class RecursionDemo extends Application {
    
    private TextArea outputArea;
    private TextField inputField;
    private ComboBox<String> algorithmComboBox;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Recursion Demonstrations");
        
        // Create main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        
        // Title
        Label titleLabel = new Label("Recursion Algorithm Demonstrations");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // Algorithm selection
        HBox selectionBox = new HBox(10);
        selectionBox.setAlignment(Pos.CENTER);
        
        Label algorithmLabel = new Label("Select Algorithm:");
        algorithmComboBox = new ComboBox<>();
        algorithmComboBox.getItems().addAll(
            "Compute Factorial",
            "Compute Fibonacci",
            "Recursive Selection Sort",
            "Recursive Binary Search",
            "Directory Size",
            "Tower of Hanoi",
            "Factorial (Tail Recursion)"
        );
        algorithmComboBox.setValue("Compute Factorial");
        
        selectionBox.getChildren().addAll(algorithmLabel, algorithmComboBox);
        
        // Input area
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        
        Label inputLabel = new Label("Input:");
        inputField = new TextField();
        inputField.setPromptText("Enter a number or path");
        inputField.setPrefWidth(300);
        
        Button runButton = new Button("Run Algorithm");
        runButton.setOnAction(e -> runSelectedAlgorithm());
        
        inputBox.getChildren().addAll(inputLabel, inputField, runButton);
        
        // Output area
        Label outputLabel = new Label("Output:");
        outputArea = new TextArea();
        outputArea.setPrefRowCount(15);
        outputArea.setPrefColumnCount(60);
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        
        // Add all components to root
        root.getChildren().addAll(titleLabel, selectionBox, inputBox, outputLabel, outputArea);
        
        // Set up scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Set up event handlers
        algorithmComboBox.setOnAction(e -> updateInputPrompt());
        updateInputPrompt();
    }
    
    private void updateInputPrompt() {
        String selected = algorithmComboBox.getValue();
        switch (selected) {
            case "Compute Factorial":
            case "Factorial (Tail Recursion)":
                inputField.setPromptText("Enter a non-negative integer (e.g., 5)");
                break;
            case "Compute Fibonacci":
                inputField.setPromptText("Enter Fibonacci index (e.g., 10)");
                break;
            case "Recursive Selection Sort":
                inputField.setPromptText("Enter numbers separated by spaces (e.g., 5 2 8 1 9)");
                break;
            case "Recursive Binary Search":
                inputField.setPromptText("Enter a number to search (e.g., 7)");
                break;
            case "Directory Size":
                inputField.setPromptText("Enter directory path (e.g., .)");
                break;
            case "Tower of Hanoi":
                inputField.setPromptText("Enter number of disks (e.g., 3)");
                break;
        }
    }
    
    private void runSelectedAlgorithm() {
        String selected = algorithmComboBox.getValue();
        String input = inputField.getText().trim();
        
        if (input.isEmpty()) {
            outputArea.setText("Please enter input data.");
            return;
        }
        
        try {
            switch (selected) {
                case "Compute Factorial":
                    runFactorial(input);
                    break;
                case "Compute Fibonacci":
                    runFibonacci(input);
                    break;
                case "Recursive Selection Sort":
                    runSelectionSort(input);
                    break;
                case "Recursive Binary Search":
                    runBinarySearch(input);
                    break;
                case "Directory Size":
                    runDirectorySize(input);
                    break;
                case "Tower of Hanoi":
                    runTowerOfHanoi(input);
                    break;
                case "Factorial (Tail Recursion)":
                    runFactorialTailRecursion(input);
                    break;
            }
        } catch (Exception ex) {
            outputArea.setText("Error: " + ex.getMessage());
        }
    }
    
    private void runFactorial(String input) {
        int n = Integer.parseInt(input);
        if (n < 0) {
            outputArea.setText("Error: Please enter a non-negative integer.");
            return;
        }
        
        long result = ComputeFactorial.factorial(n);
        outputArea.setText("Factorial of " + n + " is " + result);
    }
    
    private void runFibonacci(String input) {
        int index = Integer.parseInt(input);
        if (index < 0) {
            outputArea.setText("Error: Please enter a non-negative integer.");
            return;
        }
        
        long result = ComputeFibonacci.fib(index);
        outputArea.setText("Fibonacci number at index " + index + " is " + result);
    }
    
    private void runSelectionSort(String input) {
        String[] parts = input.split("\\s+");
        double[] numbers = new double[parts.length];
        
        for (int i = 0; i < parts.length; i++) {
            numbers[i] = Double.parseDouble(parts[i]);
        }
        
        outputArea.setText("Original array: " + Arrays.toString(numbers) + "\n");
        RecursiveSelectionSort.sort(numbers);
        outputArea.appendText("Sorted array: " + Arrays.toString(numbers));
    }
    
    private void runBinarySearch(String input) {
        int key = Integer.parseInt(input);
        int[] list = {3, 5, 7, 8, 12, 17, 24, 29};
        
        outputArea.setText("Searching for " + key + " in array: " + Arrays.toString(list) + "\n");
        int result = RecursiveBinarySearch.binarySearch(list, key);
        
        if (result >= 0) {
            outputArea.appendText("Found at index: " + result);
        } else {
            outputArea.appendText("Not found. Should be inserted at index: " + (-result - 1));
        }
    }
    
    private void runDirectorySize(String input) {
        File file = new File(input);
        if (!file.exists()) {
            outputArea.setText("Error: File or directory does not exist.");
            return;
        }
        
        long size = DirectorySize.getSize(file);
        outputArea.setText("Size of " + input + ": " + size + " bytes");
    }
    
    private void runTowerOfHanoi(String input) {
        int n = Integer.parseInt(input);
        if (n <= 0) {
            outputArea.setText("Error: Please enter a positive integer.");
            return;
        }
        
        outputArea.setText("Tower of Hanoi solution for " + n + " disks:\n");
        // Capture the output by redirecting System.out
        StringBuilder output = new StringBuilder();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(new java.io.ByteArrayOutputStream()) {
            @Override
            public void println(String x) {
                output.append(x).append("\n");
            }
        });
        
        TowerOfHanoi.moveDisks(n, 'A', 'B', 'C');
        System.setOut(originalOut);
        
        outputArea.appendText(output.toString());
    }
    
    private void runFactorialTailRecursion(String input) {
        int n = Integer.parseInt(input);
        if (n < 0) {
            outputArea.setText("Error: Please enter a non-negative integer.");
            return;
        }
        
        long result = ComputeFactorialTailRecursion.factorial(n);
        outputArea.setText("Factorial of " + n + " (tail recursion) is " + result);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
} 