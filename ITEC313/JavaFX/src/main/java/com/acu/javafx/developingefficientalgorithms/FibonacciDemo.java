package com.acu.javafx.developingefficientalgorithms;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.Arrays;

/**
 * JavaFX demonstration of Fibonacci algorithms.
 * Shows both recursive (inefficient) and iterative (efficient) approaches.
 * Recursive Time Complexity: O(2^n) - Exponential
 * Iterative Time Complexity: O(n) - Linear
 */
public class FibonacciDemo {
    private Stage stage;
    private TextArea outputArea;
    private TextField inputField;
    private Button calculateButton;
    private Button recursiveButton;
    private Button iterativeButton;
    private Button compareButton;
    private Label complexityLabel;
    private Label descriptionLabel;

    public void show() {
        stage = new Stage();
        stage.setTitle("Fibonacci Algorithm Demo");
        stage.setWidth(900);
        stage.setHeight(700);

        // Create UI components
        createUI();

        Scene scene = new Scene(createMainLayout());
        stage.setScene(scene);
        stage.show();
    }

    private void createUI() {
        // Title
        Label titleLabel = new Label("Fibonacci Algorithm Demo");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Description
        descriptionLabel = new Label(
            "Fibonacci sequence: F(n) = F(n-1) + F(n-2), where F(0) = 0 and F(1) = 1\n" +
            "Recursive approach: O(2^n) - Exponential time complexity (inefficient)\n" +
            "Iterative approach: O(n) - Linear time complexity (efficient)\n" +
            "Dynamic Programming: O(n) - Linear time with memoization"
        );
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px;");

        // Complexity information
        complexityLabel = new Label("Recursive: O(2^n), Iterative: O(n), Dynamic Programming: O(n)");
        complexityLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2E8B57;");

        // Input controls
        HBox inputBox = new HBox(10);
        Label inputLabel = new Label("Calculate F(n) where n =");
        inputField = new TextField();
        inputField.setPromptText("Enter a number (0-40 recommended)");
        inputField.setPrefWidth(200);
        
        calculateButton = new Button("Calculate");
        calculateButton.setOnAction(e -> calculateFibonacci());
        
        recursiveButton = new Button("Recursive Only");
        recursiveButton.setOnAction(e -> calculateRecursiveOnly());
        
        iterativeButton = new Button("Iterative Only");
        iterativeButton.setOnAction(e -> calculateIterativeOnly());
        
        compareButton = new Button("Compare Both");
        compareButton.setOnAction(e -> compareApproaches());

        inputBox.getChildren().addAll(inputLabel, inputField, calculateButton, recursiveButton, iterativeButton, compareButton);

        // Output area
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(25);
        outputArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
    }

    private VBox createMainLayout() {
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));

        // Title and description
        VBox headerBox = new VBox(10);
        headerBox.getChildren().addAll(
            new Label("Fibonacci Algorithm Demo"),
            descriptionLabel,
            complexityLabel
        );

        // Input controls
        VBox controlBox = new VBox(10);
        controlBox.getChildren().addAll(
            new Label("Calculation Controls:"),
            createInputControls()
        );

        // Output area
        VBox outputBox = new VBox(10);
        outputBox.getChildren().addAll(
            new Label("Calculation Results:"),
            outputArea
        );
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        mainLayout.getChildren().addAll(headerBox, controlBox, outputBox);
        return mainLayout;
    }

    private HBox createInputControls() {
        HBox inputBox = new HBox(10);
        Label inputLabel = new Label("Calculate F(n) where n =");
        inputField = new TextField();
        inputField.setPromptText("Enter a number (0-40 recommended)");
        inputField.setPrefWidth(200);
        
        calculateButton = new Button("Calculate");
        calculateButton.setOnAction(e -> calculateFibonacci());
        
        recursiveButton = new Button("Recursive Only");
        recursiveButton.setOnAction(e -> calculateRecursiveOnly());
        
        iterativeButton = new Button("Iterative Only");
        iterativeButton.setOnAction(e -> calculateIterativeOnly());
        
        compareButton = new Button("Compare Both");
        compareButton.setOnAction(e -> compareApproaches());

        inputBox.getChildren().addAll(inputLabel, inputField, calculateButton, recursiveButton, iterativeButton, compareButton);
        return inputBox;
    }

    private void calculateFibonacci() {
        try {
            int n = Integer.parseInt(inputField.getText());
            if (n < 0) {
                outputArea.appendText("Please enter a non-negative number!\n");
                return;
            }
            if (n > 40) {
                outputArea.appendText("Warning: Large numbers may take a long time with recursive approach!\n");
            }

            outputArea.clear();
            outputArea.appendText("=== Fibonacci Calculation for F(" + n + ") ===\n\n");

            // Calculate using both approaches
            long startTime = System.nanoTime();
            long recursiveResult = fibonacciRecursive(n);
            long recursiveTime = System.nanoTime() - startTime;

            startTime = System.nanoTime();
            long iterativeResult = fibonacciIterative(n);
            long iterativeTime = System.nanoTime() - startTime;

            outputArea.appendText("Recursive Result: F(" + n + ") = " + recursiveResult + "\n");
            outputArea.appendText("Recursive Time: " + recursiveTime / 1000000.0 + " ms\n");
            outputArea.appendText("Recursive Complexity: O(2^n)\n\n");

            outputArea.appendText("Iterative Result: F(" + n + ") = " + iterativeResult + "\n");
            outputArea.appendText("Iterative Time: " + iterativeTime / 1000000.0 + " ms\n");
            outputArea.appendText("Iterative Complexity: O(n)\n\n");

            if (recursiveResult == iterativeResult) {
                outputArea.appendText("✓ Results match!\n");
            } else {
                outputArea.appendText("✗ Results don't match!\n");
            }

            double speedup = (double) recursiveTime / iterativeTime;
            outputArea.appendText("Speedup: " + String.format("%.2f", speedup) + "x faster with iterative approach\n");

        } catch (NumberFormatException e) {
            outputArea.appendText("Please enter a valid number!\n");
        }
    }

    private void calculateRecursiveOnly() {
        try {
            int n = Integer.parseInt(inputField.getText());
            if (n < 0) {
                outputArea.appendText("Please enter a non-negative number!\n");
                return;
            }

            outputArea.clear();
            outputArea.appendText("=== Recursive Fibonacci Calculation ===\n");
            outputArea.appendText("Calculating F(" + n + ") using recursive approach...\n\n");

            long startTime = System.nanoTime();
            long result = fibonacciRecursive(n);
            long time = System.nanoTime() - startTime;

            outputArea.appendText("Result: F(" + n + ") = " + result + "\n");
            outputArea.appendText("Time: " + time / 1000000.0 + " ms\n");
            outputArea.appendText("Time Complexity: O(2^n) - Exponential\n");
            outputArea.appendText("Space Complexity: O(n) - Due to call stack\n");

        } catch (NumberFormatException e) {
            outputArea.appendText("Please enter a valid number!\n");
        }
    }

    private void calculateIterativeOnly() {
        try {
            int n = Integer.parseInt(inputField.getText());
            if (n < 0) {
                outputArea.appendText("Please enter a non-negative number!\n");
                return;
            }

            outputArea.clear();
            outputArea.appendText("=== Iterative Fibonacci Calculation ===\n");
            outputArea.appendText("Calculating F(" + n + ") using iterative approach...\n\n");

            long startTime = System.nanoTime();
            long result = fibonacciIterative(n);
            long time = System.nanoTime() - startTime;

            outputArea.appendText("Result: F(" + n + ") = " + result + "\n");
            outputArea.appendText("Time: " + time / 1000000.0 + " ms\n");
            outputArea.appendText("Time Complexity: O(n) - Linear\n");
            outputArea.appendText("Space Complexity: O(1) - Constant\n");

        } catch (NumberFormatException e) {
            outputArea.appendText("Please enter a valid number!\n");
        }
    }

    private void compareApproaches() {
        try {
            int n = Integer.parseInt(inputField.getText());
            if (n < 0) {
                outputArea.appendText("Please enter a non-negative number!\n");
                return;
            }

            outputArea.clear();
            outputArea.appendText("=== Performance Comparison ===\n");
            outputArea.appendText("Comparing recursive vs iterative approaches for F(" + n + ")\n\n");

            // Test with smaller numbers for recursive
            int testN = Math.min(n, 30); // Limit recursive test to avoid long waits
            
            if (testN < n) {
                outputArea.appendText("Note: Recursive test limited to n=" + testN + " for performance\n\n");
            }

            // Recursive test
            long startTime = System.nanoTime();
            long recursiveResult = fibonacciRecursive(testN);
            long recursiveTime = System.nanoTime() - startTime;

            // Iterative test
            startTime = System.nanoTime();
            long iterativeResult = fibonacciIterative(n);
            long iterativeTime = System.nanoTime() - startTime;

            outputArea.appendText("Recursive F(" + testN + ") = " + recursiveResult + "\n");
            outputArea.appendText("Recursive Time: " + recursiveTime / 1000000.0 + " ms\n\n");

            outputArea.appendText("Iterative F(" + n + ") = " + iterativeResult + "\n");
            outputArea.appendText("Iterative Time: " + iterativeTime / 1000000.0 + " ms\n\n");

            if (testN == n && recursiveResult == iterativeResult) {
                outputArea.appendText("✓ Results match!\n");
            }

            double speedup = (double) recursiveTime / iterativeTime;
            outputArea.appendText("Speedup: " + String.format("%.2f", speedup) + "x faster with iterative approach\n");

        } catch (NumberFormatException e) {
            outputArea.appendText("Please enter a valid number!\n");
        }
    }

    /**
     * Recursive Fibonacci implementation (inefficient)
     * Time Complexity: O(2^n) - Exponential
     * @param n the Fibonacci number to calculate
     * @return F(n)
     */
    public static long fibonacciRecursive(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    /**
     * Iterative Fibonacci implementation (efficient)
     * Time Complexity: O(n) - Linear
     * @param n the Fibonacci number to calculate
     * @return F(n)
     */
    public static long fibonacciIterative(int n) {
        if (n <= 1) {
            return n;
        }
        
        long prev = 0;
        long current = 1;
        
        for (int i = 2; i <= n; i++) {
            long next = prev + current;
            prev = current;
            current = next;
        }
        
        return current;
    }

    /**
     * Dynamic Programming Fibonacci implementation
     * Time Complexity: O(n) - Linear
     * @param n the Fibonacci number to calculate
     * @return F(n)
     */
    public static long fibonacciDynamicProgramming(int n) {
        if (n <= 1) {
            return n;
        }
        
        long[] dp = new long[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }
} 