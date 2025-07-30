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
import java.util.Random;

/**
 * JavaFX demonstration of Performance Testing for various algorithms.
 * Compares execution times and shows Big O notation in practice.
 */
public class PerformanceTestDemo {
    private Stage stage;
    private TextArea outputArea;
    private Button linearSearchTestButton;
    private Button binarySearchTestButton;
    private Button sortTestButton;
    private Button fibonacciTestButton;
    private Button comprehensiveTestButton;
    private Label complexityLabel;
    private Label descriptionLabel;

    public void show() {
        stage = new Stage();
        stage.setTitle("Performance Test Demo");
        stage.setWidth(1000);
        stage.setHeight(700);

        // Create UI components
        createUI();

        Scene scene = new Scene(createMainLayout());
        stage.setScene(scene);
        stage.show();
    }

    private void createUI() {
        // Title
        Label titleLabel = new Label("Performance Test Demo");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Description
        descriptionLabel = new Label(
            "Performance testing demonstrates how algorithm efficiency affects execution time.\n" +
            "This demo compares different algorithms and shows how time complexity affects performance.\n" +
            "Linear Search: O(n), Binary Search: O(log n), Selection Sort: O(n²), Fibonacci: O(2^n) vs O(n)"
        );
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px;");

        // Complexity information
        complexityLabel = new Label("Performance testing shows Big O notation in practice");
        complexityLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2E8B57;");

        // Test buttons
        linearSearchTestButton = new Button("Linear vs Binary Search");
        linearSearchTestButton.setOnAction(e -> testSearchAlgorithms());
        
        sortTestButton = new Button("Sorting Algorithms");
        sortTestButton.setOnAction(e -> testSortingAlgorithms());
        
        fibonacciTestButton = new Button("Fibonacci Performance");
        fibonacciTestButton.setOnAction(e -> testFibonacciPerformance());
        
        comprehensiveTestButton = new Button("Comprehensive Test");
        comprehensiveTestButton.setOnAction(e -> runComprehensiveTest());

        // Output area
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(30);
        outputArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
    }

    private VBox createMainLayout() {
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));

        // Title and description
        VBox headerBox = new VBox(10);
        headerBox.getChildren().addAll(
            new Label("Performance Test Demo"),
            descriptionLabel,
            complexityLabel
        );

        // Test buttons
        VBox controlBox = new VBox(10);
        controlBox.getChildren().addAll(
            new Label("Performance Tests:"),
            createTestButtons()
        );

        // Output area
        VBox outputBox = new VBox(10);
        outputBox.getChildren().addAll(
            new Label("Test Results:"),
            outputArea
        );
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        mainLayout.getChildren().addAll(headerBox, controlBox, outputBox);
        return mainLayout;
    }

    private HBox createTestButtons() {
        HBox buttonBox = new HBox(10);
        
        linearSearchTestButton = new Button("Linear vs Binary Search");
        linearSearchTestButton.setOnAction(e -> testSearchAlgorithms());
        
        sortTestButton = new Button("Sorting Algorithms");
        sortTestButton.setOnAction(e -> testSortingAlgorithms());
        
        fibonacciTestButton = new Button("Fibonacci Performance");
        fibonacciTestButton.setOnAction(e -> testFibonacciPerformance());
        
        comprehensiveTestButton = new Button("Comprehensive Test");
        comprehensiveTestButton.setOnAction(e -> runComprehensiveTest());

        buttonBox.getChildren().addAll(linearSearchTestButton, sortTestButton, fibonacciTestButton, comprehensiveTestButton);
        return buttonBox;
    }

    private void testSearchAlgorithms() {
        outputArea.clear();
        outputArea.appendText("=== Linear Search vs Binary Search Performance Test ===\n\n");

        int[] sizes = {1000, 10000, 100000};
        
        for (int size : sizes) {
            outputArea.appendText("Testing with array size: " + size + "\n");
            
            // Generate sorted array for binary search
            int[] array = generateSortedArray(size);
            int target = array[size / 2]; // Search for middle element
            
            // Test Linear Search
            long startTime = System.nanoTime();
            int linearResult = LinearSearchDemo.linearSearch(array, target);
            long linearTime = System.nanoTime() - startTime;
            
            // Test Binary Search
            startTime = System.nanoTime();
            int binaryResult = BinarySearchDemo.binarySearch(array, target);
            long binaryTime = System.nanoTime() - startTime;
            
            outputArea.appendText("  Linear Search: " + linearTime / 1000000.0 + " ms (O(n))\n");
            outputArea.appendText("  Binary Search: " + binaryTime / 1000000.0 + " ms (O(log n))\n");
            
            if (linearResult == binaryResult) {
                outputArea.appendText("  ✓ Results match: " + linearResult + "\n");
            }
            
            double speedup = (double) linearTime / binaryTime;
            outputArea.appendText("  Speedup: " + String.format("%.2f", speedup) + "x faster with binary search\n\n");
        }
    }

    private void testSortingAlgorithms() {
        outputArea.clear();
        outputArea.appendText("=== Sorting Algorithms Performance Test ===\n\n");

        int[] sizes = {100, 1000, 5000};
        
        for (int size : sizes) {
            outputArea.appendText("Testing with array size: " + size + "\n");
            
            // Generate random array
            int[] array = generateRandomArray(size);
            int[] arrayCopy = array.clone();
            
            // Test Selection Sort
            long startTime = System.nanoTime();
            SelectionSortDemo.selectionSort(array);
            long selectionTime = System.nanoTime() - startTime;
            
            // Test Arrays.sort (optimized)
            startTime = System.nanoTime();
            Arrays.sort(arrayCopy);
            long arraysSortTime = System.nanoTime() - startTime;
            
            outputArea.appendText("  Selection Sort: " + selectionTime / 1000000.0 + " ms (O(n²))\n");
            outputArea.appendText("  Arrays.sort: " + arraysSortTime / 1000000.0 + " ms (O(n log n))\n");
            
            double speedup = (double) selectionTime / arraysSortTime;
            outputArea.appendText("  Speedup: " + String.format("%.2f", speedup) + "x faster with Arrays.sort\n\n");
        }
    }

    private void testFibonacciPerformance() {
        outputArea.clear();
        outputArea.appendText("=== Fibonacci Performance Test ===\n\n");

        int[] testValues = {20, 30, 35, 40};
        
        for (int n : testValues) {
            outputArea.appendText("Testing F(" + n + "):\n");
            
            // Test Iterative (efficient)
            long startTime = System.nanoTime();
            long iterativeResult = FibonacciDemo.fibonacciIterative(n);
            long iterativeTime = System.nanoTime() - startTime;
            
            outputArea.appendText("  Iterative: " + iterativeResult + " (" + iterativeTime / 1000000.0 + " ms, O(n))\n");
            
            // Test Recursive (inefficient) - only for smaller values
            if (n <= 35) {
                startTime = System.nanoTime();
                long recursiveResult = FibonacciDemo.fibonacciRecursive(n);
                long recursiveTime = System.nanoTime() - startTime;
                
                outputArea.appendText("  Recursive: " + recursiveResult + " (" + recursiveTime / 1000000.0 + " ms, O(2^n))\n");
                
                if (iterativeResult == recursiveResult) {
                    outputArea.appendText("  ✓ Results match\n");
                }
                
                double speedup = (double) recursiveTime / iterativeTime;
                outputArea.appendText("  Speedup: " + String.format("%.2f", speedup) + "x faster with iterative\n");
            } else {
                outputArea.appendText("  Recursive: Skipped (too slow for n=" + n + ")\n");
            }
            
            outputArea.appendText("\n");
        }
    }

    private void runComprehensiveTest() {
        outputArea.clear();
        outputArea.appendText("=== Comprehensive Performance Test ===\n\n");

        outputArea.appendText("1. Search Algorithms Test:\n");
        testSearchAlgorithms();
        
        outputArea.appendText("2. Sorting Algorithms Test:\n");
        testSortingAlgorithms();
        
        outputArea.appendText("3. Fibonacci Performance Test:\n");
        testFibonacciPerformance();
        
        outputArea.appendText("4. Prime Number Test:\n");
        testPrimeNumbers();
        
        outputArea.appendText("=== Summary ===\n");
        outputArea.appendText("• Linear Search: O(n) - Linear time complexity\n");
        outputArea.appendText("• Binary Search: O(log n) - Logarithmic time complexity\n");
        outputArea.appendText("• Selection Sort: O(n²) - Quadratic time complexity\n");
        outputArea.appendText("• Arrays.sort: O(n log n) - Log-linear time complexity\n");
        outputArea.appendText("• Recursive Fibonacci: O(2^n) - Exponential time complexity\n");
        outputArea.appendText("• Iterative Fibonacci: O(n) - Linear time complexity\n");
        outputArea.appendText("• Prime Testing: O(√n) - Square root time complexity\n");
    }

    private void testPrimeNumbers() {
        outputArea.appendText("Testing prime number generation up to 10000:\n");
        
        long startTime = System.nanoTime();
        boolean[] isPrime = PrimeNumbersDemo.sieveOfEratosthenes(10000);
        long sieveTime = System.nanoTime() - startTime;
        
        int primeCount = 0;
        for (int i = 2; i <= 10000; i++) {
            if (isPrime[i]) primeCount++;
        }
        
        outputArea.appendText("  Sieve of Eratosthenes: " + primeCount + " primes found in " + 
                            sieveTime / 1000000.0 + " ms (O(n log log n))\n\n");
    }

    private int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(10000);
        }
        return array;
    }

    private int[] generateSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }
} 