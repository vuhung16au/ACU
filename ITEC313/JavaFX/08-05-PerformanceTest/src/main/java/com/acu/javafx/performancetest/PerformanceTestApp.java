package com.acu.javafx.performancetest;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * JavaFX application for demonstrating performance testing with different algorithms.
 * This application provides a graphical interface to run performance tests and
 * compare execution times for different input sizes.
 */
public class PerformanceTestApp extends Application {
    
    private TextArea outputArea;
    private Button runTestButton;
    private Button clearButton;
    private ProgressBar progressBar;
    private Label statusLabel;
    private ExecutorService executor;
    
    @Override
    public void start(Stage primaryStage) {
        executor = Executors.newSingleThreadExecutor();
        
        // Create the main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        
        // Create title
        Label titleLabel = new Label("Performance Test Application");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // Create description
        Label descriptionLabel = new Label(
            "This application demonstrates performance testing with different algorithms.\n" +
            "Click 'Run Performance Test' to execute tests with various input sizes."
        );
        descriptionLabel.setWrapText(true);
        descriptionLabel.setAlignment(Pos.CENTER);
        
        // Create controls
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        runTestButton = new Button("Run Performance Test");
        runTestButton.setPrefWidth(200);
        runTestButton.setOnAction(e -> runPerformanceTest());
        
        clearButton = new Button("Clear Output");
        clearButton.setPrefWidth(150);
        clearButton.setOnAction(e -> clearOutput());
        
        buttonBox.getChildren().addAll(runTestButton, clearButton);
        
        // Create progress bar
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(400);
        progressBar.setVisible(false);
        
        // Create status label
        statusLabel = new Label("Ready to run performance tests");
        statusLabel.setAlignment(Pos.CENTER);
        
        // Create output area
        outputArea = new TextArea();
        outputArea.setPrefRowCount(15);
        outputArea.setPrefColumnCount(80);
        outputArea.setEditable(false);
        outputArea.setFont(Font.font("Monospaced", 12));
        outputArea.setWrapText(true);
        
        // Add scroll pane for output
        ScrollPane scrollPane = new ScrollPane(outputArea);
        scrollPane.setPrefViewportWidth(600);
        scrollPane.setPrefViewportHeight(400);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        // Add all components to root
        root.getChildren().addAll(
            titleLabel,
            descriptionLabel,
            buttonBox,
            progressBar,
            statusLabel,
            scrollPane
        );
        
        // Create scene and stage
        Scene scene = new Scene(root, 800, 700);
        primaryStage.setTitle("JavaFX Performance Test Application");
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
     * Runs the performance test in a background thread to avoid blocking the UI.
     */
    private void runPerformanceTest() {
        runTestButton.setDisable(true);
        clearButton.setDisable(true);
        progressBar.setVisible(true);
        statusLabel.setText("Running performance tests...");
        
        executor.submit(() -> {
            try {
                // Test values
                long[] testValues = {1000000, 10000000, 100000000, 1000000000, 10000000000L};
                
                Platform.runLater(() -> {
                    outputArea.appendText("=== Performance Test Results ===\n");
                    outputArea.appendText("Testing execution time for different input sizes\n\n");
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
                    long startTime = System.currentTimeMillis();
                    long k = 0;
                    for (long j = 1; j <= n; j++) {
                        k = k + 5;
                    }
                    long endTime = System.currentTimeMillis();
                    long executionTime = endTime - startTime;
                    
                    // Update output
                    final String result = String.format(
                        "n = %,d: %d milliseconds\n", n, executionTime
                    );
                    Platform.runLater(() -> outputArea.appendText(result));
                    
                    // Small delay to show progress
                    Thread.sleep(100);
                }
                
                Platform.runLater(() -> {
                    outputArea.appendText("\n=== Test Complete ===\n");
                    outputArea.appendText("All performance tests have been completed.\n");
                    statusLabel.setText("Performance tests completed successfully");
                    progressBar.setVisible(false);
                    runTestButton.setDisable(false);
                    clearButton.setDisable(false);
                });
                
            } catch (Exception e) {
                Platform.runLater(() -> {
                    outputArea.appendText("Error during performance test: " + e.getMessage() + "\n");
                    statusLabel.setText("Error occurred during testing");
                    progressBar.setVisible(false);
                    runTestButton.setDisable(false);
                    clearButton.setDisable(false);
                });
            }
        });
    }
    
    /**
     * Clears the output area.
     */
    private void clearOutput() {
        outputArea.clear();
        statusLabel.setText("Output cleared");
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