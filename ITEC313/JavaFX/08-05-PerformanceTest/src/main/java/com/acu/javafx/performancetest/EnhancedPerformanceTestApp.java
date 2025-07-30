package com.acu.javafx.performancetest;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Enhanced JavaFX application for demonstrating algorithm performance comparison.
 * This application provides a graphical interface to compare different algorithms
 * and visualize their performance characteristics.
 */
public class EnhancedPerformanceTestApp extends Application {
    
    private TextArea outputArea;
    private Button runAlgorithmTestButton;
    private Button runSimpleTestButton;
    private Button clearButton;
    private ProgressBar progressBar;
    private Label statusLabel;
    private LineChart<Number, Number> performanceChart;
    private ExecutorService executor;
    
    @Override
    public void start(Stage primaryStage) {
        executor = Executors.newSingleThreadExecutor();
        
        // Create the main layout
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        
        // Create title
        Label titleLabel = new Label("Enhanced Performance Test Application");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // Create description
        Label descriptionLabel = new Label(
            "This application demonstrates performance testing with different algorithms.\n" +
            "Compare linear search vs binary search, bubble sort vs quick sort, and more."
        );
        descriptionLabel.setWrapText(true);
        descriptionLabel.setAlignment(Pos.CENTER);
        
        // Create controls
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        runAlgorithmTestButton = new Button("Run Algorithm Comparison");
        runAlgorithmTestButton.setPrefWidth(200);
        runAlgorithmTestButton.setOnAction(e -> runAlgorithmComparison());
        
        runSimpleTestButton = new Button("Run Simple Test");
        runSimpleTestButton.setPrefWidth(150);
        runSimpleTestButton.setOnAction(e -> runSimpleTest());
        
        clearButton = new Button("Clear Output");
        clearButton.setPrefWidth(150);
        clearButton.setOnAction(e -> clearOutput());
        
        buttonBox.getChildren().addAll(runAlgorithmTestButton, runSimpleTestButton, clearButton);
        
        // Create progress bar
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(400);
        progressBar.setVisible(false);
        
        // Create status label
        statusLabel = new Label("Ready to run performance tests");
        statusLabel.setAlignment(Pos.CENTER);
        
        // Create performance chart
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Input Size");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Execution Time (ms)");
        
        performanceChart = new LineChart<>(xAxis, yAxis);
        performanceChart.setTitle("Algorithm Performance Comparison");
        performanceChart.setPrefHeight(300);
        performanceChart.setCreateSymbols(false);
        
        // Create output area
        outputArea = new TextArea();
        outputArea.setPrefRowCount(10);
        outputArea.setPrefColumnCount(80);
        outputArea.setEditable(false);
        outputArea.setFont(Font.font("Monospaced", 11));
        outputArea.setWrapText(true);
        
        // Add scroll pane for output
        ScrollPane scrollPane = new ScrollPane(outputArea);
        scrollPane.setPrefViewportWidth(600);
        scrollPane.setPrefViewportHeight(200);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        // Create split pane for chart and output
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(performanceChart, scrollPane);
        splitPane.setDividerPositions(0.6);
        
        // Add all components to root
        root.getChildren().addAll(
            titleLabel,
            descriptionLabel,
            buttonBox,
            progressBar,
            statusLabel,
            splitPane
        );
        
        // Create scene and stage
        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setTitle("Enhanced JavaFX Performance Test Application");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
        
        // Handle window close
        primaryStage.setOnCloseRequest(e -> {
            if (executor != null && !executor.isShutdown()) {
                executor.shutdown();
            }
        });
    }
    
    /**
     * Runs algorithm performance comparison tests.
     */
    private void runAlgorithmComparison() {
        runAlgorithmTestButton.setDisable(true);
        runSimpleTestButton.setDisable(true);
        clearButton.setDisable(true);
        progressBar.setVisible(true);
        statusLabel.setText("Running algorithm comparison tests...");
        
        executor.submit(() -> {
            try {
                // Test sizes for algorithms
                int[] testSizes = {1000, 5000, 10000, 50000, 100000};
                
                Platform.runLater(() -> {
                    outputArea.appendText("=== Algorithm Performance Comparison ===\n");
                    outputArea.appendText("Testing different algorithms with various input sizes\n\n");
                    performanceChart.getData().clear();
                });
                
                // Create chart series
                XYChart.Series<Number, Number> linearSearchSeries = new XYChart.Series<>();
                linearSearchSeries.setName("Linear Search");
                
                XYChart.Series<Number, Number> binarySearchSeries = new XYChart.Series<>();
                binarySearchSeries.setName("Binary Search");
                
                XYChart.Series<Number, Number> bubbleSortSeries = new XYChart.Series<>();
                bubbleSortSeries.setName("Bubble Sort");
                
                XYChart.Series<Number, Number> quickSortSeries = new XYChart.Series<>();
                quickSortSeries.setName("Quick Sort");
                
                for (int i = 0; i < testSizes.length; i++) {
                    final int size = testSizes[i];
                    final int progress = i + 1;
                    
                    // Update progress
                    Platform.runLater(() -> {
                        progressBar.setProgress((double) progress / testSizes.length);
                        statusLabel.setText("Testing with size = " + size);
                    });
                    
                    // Test algorithms
                    long linearTime = AlgorithmPerformanceTest.testLinearSearch(size);
                    long binaryTime = AlgorithmPerformanceTest.testBinarySearch(size);
                    long bubbleTime = AlgorithmPerformanceTest.testBubbleSort(size);
                    long quickTime = AlgorithmPerformanceTest.testQuickSort(size);
                    
                    // Add data to chart series
                    final int finalI = i;
                    Platform.runLater(() -> {
                        linearSearchSeries.getData().add(new XYChart.Data<>(size, linearTime));
                        binarySearchSeries.getData().add(new XYChart.Data<>(size, binaryTime));
                        bubbleSortSeries.getData().add(new XYChart.Data<>(size, bubbleTime));
                        quickSortSeries.getData().add(new XYChart.Data<>(size, quickTime));
                        
                        // Update chart
                        if (finalI == 0) {
                            performanceChart.getData().addAll(
                                linearSearchSeries, binarySearchSeries, 
                                bubbleSortSeries, quickSortSeries
                            );
                        }
                    });
                    
                    // Update output
                    final String result = String.format(
                        "Size %,d: Linear=%dms, Binary=%dms, Bubble=%dms, Quick=%dms\n",
                        size, linearTime, binaryTime, bubbleTime, quickTime
                    );
                    Platform.runLater(() -> outputArea.appendText(result));
                    
                    // Small delay to show progress
                    Thread.sleep(200);
                }
                
                Platform.runLater(() -> {
                    outputArea.appendText("\n=== Algorithm Comparison Complete ===\n");
                    outputArea.appendText("Chart shows performance comparison across different input sizes.\n");
                    statusLabel.setText("Algorithm comparison completed successfully");
                    progressBar.setVisible(false);
                    runAlgorithmTestButton.setDisable(false);
                    runSimpleTestButton.setDisable(false);
                    clearButton.setDisable(false);
                });
                
            } catch (Exception e) {
                Platform.runLater(() -> {
                    outputArea.appendText("Error during algorithm comparison: " + e.getMessage() + "\n");
                    statusLabel.setText("Error occurred during testing");
                    progressBar.setVisible(false);
                    runAlgorithmTestButton.setDisable(false);
                    runSimpleTestButton.setDisable(false);
                    clearButton.setDisable(false);
                });
            }
        });
    }
    
    /**
     * Runs the simple performance test (original test).
     */
    private void runSimpleTest() {
        runAlgorithmTestButton.setDisable(true);
        runSimpleTestButton.setDisable(true);
        clearButton.setDisable(true);
        progressBar.setVisible(true);
        statusLabel.setText("Running simple performance test...");
        
        executor.submit(() -> {
            try {
                // Test values (same as original)
                long[] testValues = {1000000, 10000000, 100000000, 1000000000, 10000000000L};
                
                Platform.runLater(() -> {
                    outputArea.appendText("=== Simple Performance Test Results ===\n");
                    outputArea.appendText("Testing execution time for different loop iterations\n\n");
                });
                
                for (int i = 0; i < testValues.length; i++) {
                    final long n = testValues[i];
                    final int progress = i + 1;
                    
                    // Update progress
                    Platform.runLater(() -> {
                        progressBar.setProgress((double) progress / testValues.length);
                        statusLabel.setText("Testing with n = " + n);
                    });
                    
                    // Run the test
                    long executionTime = AlgorithmPerformanceTest.testSimpleLoop(n);
                    
                    // Update output
                    final String result = String.format(
                        "n = %,d: %d milliseconds\n", n, executionTime
                    );
                    Platform.runLater(() -> outputArea.appendText(result));
                    
                    // Small delay to show progress
                    Thread.sleep(100);
                }
                
                Platform.runLater(() -> {
                    outputArea.appendText("\n=== Simple Test Complete ===\n");
                    outputArea.appendText("All simple performance tests have been completed.\n");
                    statusLabel.setText("Simple performance test completed successfully");
                    progressBar.setVisible(false);
                    runAlgorithmTestButton.setDisable(false);
                    runSimpleTestButton.setDisable(false);
                    clearButton.setDisable(false);
                });
                
            } catch (Exception e) {
                Platform.runLater(() -> {
                    outputArea.appendText("Error during simple test: " + e.getMessage() + "\n");
                    statusLabel.setText("Error occurred during testing");
                    progressBar.setVisible(false);
                    runAlgorithmTestButton.setDisable(false);
                    runSimpleTestButton.setDisable(false);
                    clearButton.setDisable(false);
                });
            }
        });
    }
    
    /**
     * Clears the output area and chart.
     */
    private void clearOutput() {
        outputArea.clear();
        performanceChart.getData().clear();
        statusLabel.setText("Output and chart cleared");
    }
    
    @Override
    public void stop() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }
    
    /**
     * Main method to launch the application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
} 