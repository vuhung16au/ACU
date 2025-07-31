package com.acu.javafx.sorting;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.Random;

/**
 * JavaFX application demonstrating various sorting algorithms
 */
public class SortingDemo extends Application {
    
    private VBox arrayDisplay;
    private Rectangle[] bars;
    private int[] array;
    private ComboBox<String> algorithmComboBox;
    private Button sortButton;
    private Button resetButton;
    private Button generateButton;
    private Button stopButton;
    private Slider speedSlider;
    private Label statusLabel;
    private TextArea executionLogArea;
    private Timeline animation;
    private int currentStep = 0;
    private int[] originalArray;
    private StringBuilder executionLog;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sorting Algorithm Visualizer");
        
        // Initialize array and execution log
        array = generateRandomArray(15);
        originalArray = Arrays.copyOf(array, array.length);
        executionLog = new StringBuilder();
        
        // Create scene
        Scene scene = new Scene(createMainLayout(), 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private VBox createMainLayout() {
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);
        
        // Title
        Text title = new Text("Sorting Algorithm Visualizer");
        title.setFont(Font.font("Arial", 24));
        title.setFill(Color.DARKBLUE);
        
        // Controls
        HBox controls = createControls();
        
        // Main content area with array display and execution log
        HBox mainContent = new HBox(20);
        mainContent.setAlignment(Pos.TOP_CENTER);
        
        // Left side - Array display
        VBox leftPane = new VBox(10);
        leftPane.setAlignment(Pos.CENTER);
        arrayDisplay = createArrayDisplay();
        leftPane.getChildren().add(arrayDisplay);
        
        // Right side - Execution log
        VBox rightPane = createExecutionLogPane();
        
        mainContent.getChildren().addAll(leftPane, rightPane);
        
        // Status
        statusLabel = new Label("Ready to sort");
        statusLabel.setFont(Font.font("Arial", 14));
        
        mainLayout.getChildren().addAll(title, controls, mainContent, statusLabel);
        
        return mainLayout;
    }
    
    private HBox createControls() {
        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER);
        
        // Algorithm selection
        algorithmComboBox = new ComboBox<>();
        algorithmComboBox.getItems().addAll(
            "Insertion Sort",
            "Bubble Sort", 
            "Merge Sort",
            "Quick Sort",
            "Heap Sort",
            "Radix Sort"
        );
        algorithmComboBox.setValue("Insertion Sort");
        algorithmComboBox.setPrefWidth(150);
        
        // Speed control
        Label speedLabel = new Label("Speed:");
        speedSlider = new Slider(0.5, 2.0, 1.0);
        speedSlider.setPrefWidth(100);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setMajorTickUnit(0.5);
        
        // Buttons
        sortButton = new Button("Sort");
        sortButton.setOnAction(e -> startSorting());
        
        stopButton = new Button("Stop");
        stopButton.setOnAction(e -> stopSorting());
        stopButton.setDisable(true);
        
        resetButton = new Button("Reset");
        resetButton.setOnAction(e -> resetArray());
        
        generateButton = new Button("Generate New");
        generateButton.setOnAction(e -> generateNewArray());
        
        controls.getChildren().addAll(
            new Label("Algorithm:"), algorithmComboBox,
            speedLabel, speedSlider,
            sortButton, stopButton, resetButton, generateButton
        );
        
        return controls;
    }
    
    private VBox createExecutionLogPane() {
        VBox logPane = new VBox(10);
        logPane.setPrefWidth(350);
        logPane.setAlignment(Pos.TOP_LEFT);
        
        // Title for execution log
        Text logTitle = new Text("Sort Execution Log");
        logTitle.setFont(Font.font("Arial", 16));
        logTitle.setFill(Color.DARKGREEN);
        
        // Execution log text area
        executionLogArea = new TextArea();
        executionLogArea.setPrefHeight(400);
        executionLogArea.setPrefWidth(330);
        executionLogArea.setEditable(false);
        executionLogArea.setWrapText(true);
        executionLogArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
        
        // Clear log button
        Button clearLogButton = new Button("Clear Log");
        clearLogButton.setOnAction(e -> clearExecutionLog());
        
        logPane.getChildren().addAll(logTitle, executionLogArea, clearLogButton);
        
        return logPane;
    }
    
    private VBox createArrayDisplay() {
        VBox display = new VBox(10);
        display.setAlignment(Pos.CENTER);
        display.setPadding(new Insets(20));
        display.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 1;");
        
        // Create bars for array visualization
        bars = new Rectangle[array.length];
        HBox barsContainer = new HBox(2);
        barsContainer.setAlignment(Pos.BOTTOM_CENTER);
        
        for (int i = 0; i < array.length; i++) {
            bars[i] = new Rectangle(30, array[i] * 3);
            bars[i].setFill(Color.SKYBLUE);
            bars[i].setStroke(Color.BLACK);
            bars[i].setStrokeWidth(1);
            
            // Add value label
            Text valueText = new Text(String.valueOf(array[i]));
            valueText.setFont(Font.font("Arial", 10));
            
            VBox barWithLabel = new VBox(2);
            barWithLabel.setAlignment(Pos.BOTTOM_CENTER);
            barWithLabel.getChildren().addAll(bars[i], valueText);
            
            barsContainer.getChildren().add(barWithLabel);
        }
        
        display.getChildren().add(barsContainer);
        return display;
    }
    
    private void startSorting() {
        if (animation != null) {
            animation.stop();
        }
        
        sortButton.setDisable(true);
        stopButton.setDisable(false);
        resetButton.setDisable(true);
        generateButton.setDisable(true);
        
        String selectedAlgorithm = algorithmComboBox.getValue();
        statusLabel.setText("Sorting with " + selectedAlgorithm + "...");
        
        // Clear and initialize execution log
        clearExecutionLog();
        logExecution("Starting " + selectedAlgorithm);
        logExecution("Initial array: " + Arrays.toString(array));
        logExecution("----------------------------------------");
        
        // Reset array to original
        array = Arrays.copyOf(originalArray, originalArray.length);
        updateDisplay();
        
        // Start sorting based on selected algorithm
        switch (selectedAlgorithm) {
            case "Insertion Sort":
                startInsertionSort();
                break;
            case "Bubble Sort":
                startBubbleSort();
                break;
            case "Merge Sort":
                startMergeSort();
                break;
            case "Quick Sort":
                startQuickSort();
                break;
            case "Heap Sort":
                startHeapSort();
                break;
            case "Radix Sort":
                startRadixSort();
                break;
        }
    }
    
    private void stopSorting() {
        if (animation != null) {
            animation.stop();
        }
        statusLabel.setText("Sorting stopped by user");
        logExecution("Sorting stopped by user");
        enableControls();
    }
    
    private void startInsertionSort() {
        currentStep = 0;
        int[] steps = new int[array.length * array.length * 2]; // Increased for more steps
        String[] logMessages = new String[array.length * array.length];
        int stepIndex = 0;
        int logIndex = 0;
        
        logExecution("Starting Insertion Sort...");
        
        // Simulate insertion sort steps
        for (int i = 1; i < array.length; i++) {
            int currentElement = array[i];
            logMessages[logIndex++] = "Step " + (i) + ": Inserting element " + currentElement + " at position " + i;
            
            int k;
            for (k = i - 1; k >= 0 && array[k] > currentElement; k--) {
                array[k + 1] = array[k];
                steps[stepIndex++] = k + 1;
                steps[stepIndex++] = array[k];
                logMessages[logIndex++] = "  Moving " + array[k] + " to position " + (k + 1);
            }
            array[k + 1] = currentElement;
            steps[stepIndex++] = k + 1;
            steps[stepIndex++] = currentElement;
            logMessages[logIndex++] = "  Inserted " + currentElement + " at position " + (k + 1);
        }
        
        animateSorting(steps, stepIndex, logMessages, logIndex);
    }
    
    private void startBubbleSort() {
        currentStep = 0;
        int[] steps = new int[array.length * array.length * 2];
        String[] logMessages = new String[array.length * array.length];
        int stepIndex = 0;
        int logIndex = 0;
        
        logExecution("Starting Bubble Sort...");
        
        boolean needNextPass = true;
        for (int k = 1; k < array.length && needNextPass; k++) {
            needNextPass = false;
            logMessages[logIndex++] = "Pass " + k + ": Scanning array";
            
            for (int i = 0; i < array.length - k; i++) {
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    steps[stepIndex++] = i;
                    steps[stepIndex++] = array[i];
                    steps[stepIndex++] = i + 1;
                    steps[stepIndex++] = array[i + 1];
                    logMessages[logIndex++] = "  Swapped " + temp + " and " + array[i] + " at positions " + i + " and " + (i + 1);
                    needNextPass = true;
                }
            }
        }
        
        animateSorting(steps, stepIndex, logMessages, logIndex);
    }
    
    private void startMergeSort() {
        // For simplicity, we'll just sort and show the result
        logExecution("Starting Merge Sort...");
        logExecution("Using divide and conquer approach");
        MergeSort.mergeSort(array);
        updateDisplay();
        logExecution("Final array: " + Arrays.toString(array));
        logExecution("Merge Sort completed!");
        statusLabel.setText("Merge Sort completed!");
        enableControls();
    }
    
    private void startQuickSort() {
        // For simplicity, we'll just sort and show the result
        logExecution("Starting Quick Sort...");
        logExecution("Using pivot-based partitioning");
        QuickSort.quickSort(array);
        updateDisplay();
        logExecution("Final array: " + Arrays.toString(array));
        logExecution("Quick Sort completed!");
        statusLabel.setText("Quick Sort completed!");
        enableControls();
    }
    
    private void startHeapSort() {
        // Convert to Integer array for heap sort
        logExecution("Starting Heap Sort...");
        logExecution("Building max heap and extracting elements");
        Integer[] integerArray = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            integerArray[i] = array[i];
        }
        HeapSort.heapSort(integerArray);
        
        // Convert back to int array
        for (int i = 0; i < array.length; i++) {
            array[i] = integerArray[i];
        }
        
        updateDisplay();
        logExecution("Final array: " + Arrays.toString(array));
        logExecution("Heap Sort completed!");
        statusLabel.setText("Heap Sort completed!");
        enableControls();
    }
    
    private void startRadixSort() {
        // For simplicity, we'll just sort and show the result
        logExecution("Starting Radix Sort...");
        logExecution("Sorting by individual digits");
        RadixSort.radixSort(array);
        updateDisplay();
        logExecution("Final array: " + Arrays.toString(array));
        logExecution("Radix Sort completed!");
        statusLabel.setText("Radix Sort completed!");
        enableControls();
    }
    
    private void animateSorting(int[] steps, int stepCount, String[] logMessages, int logCount) {
        animation = new Timeline(new KeyFrame(
            Duration.millis(1000 / (speedSlider.getValue() * 2)), // Made it faster by multiplying by 2
            event -> {
                if (currentStep < stepCount) {
                    int index = steps[currentStep];
                    int value = steps[currentStep + 1];
                    array[index] = value;
                    updateDisplay();
                    
                    // Log the current step if available
                    int logStepIndex = currentStep / 2;
                    if (logStepIndex < logCount && logMessages[logStepIndex] != null) {
                        logExecution(logMessages[logStepIndex]);
                    }
                    
                    currentStep += 2;
                } else {
                    animation.stop();
                    logExecution("Final array: " + Arrays.toString(array));
                    logExecution("Sorting completed!");
                    statusLabel.setText("Sorting completed!");
                    enableControls();
                }
            }
        ));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }
    
    private void updateDisplay() {
        Platform.runLater(() -> {
            for (int i = 0; i < bars.length; i++) {
                bars[i].setHeight(array[i] * 3);
                bars[i].setFill(Color.SKYBLUE);
                
                // Update the value text
                VBox barContainer = (VBox) bars[i].getParent();
                Text valueText = (Text) barContainer.getChildren().get(1);
                valueText.setText(String.valueOf(array[i]));
            }
        });
    }
    
    private void resetArray() {
        array = Arrays.copyOf(originalArray, originalArray.length);
        updateDisplay();
        statusLabel.setText("Array reset to original");
        enableControls();
    }
    
    private void generateNewArray() {
        array = generateRandomArray(15);
        originalArray = Arrays.copyOf(array, array.length);
        
        // Recreate the display
        arrayDisplay.getChildren().clear();
        HBox barsContainer = new HBox(2);
        barsContainer.setAlignment(Pos.BOTTOM_CENTER);
        
        bars = new Rectangle[array.length];
        for (int i = 0; i < array.length; i++) {
            bars[i] = new Rectangle(30, array[i] * 3);
            bars[i].setFill(Color.SKYBLUE);
            bars[i].setStroke(Color.BLACK);
            bars[i].setStrokeWidth(1);
            
            Text valueText = new Text(String.valueOf(array[i]));
            valueText.setFont(Font.font("Arial", 10));
            
            VBox barWithLabel = new VBox(2);
            barWithLabel.setAlignment(Pos.BOTTOM_CENTER);
            barWithLabel.getChildren().addAll(bars[i], valueText);
            
            barsContainer.getChildren().add(barWithLabel);
        }
        
        arrayDisplay.getChildren().add(barsContainer);
        statusLabel.setText("New array generated");
        enableControls();
    }
    
    private void enableControls() {
        sortButton.setDisable(false);
        stopButton.setDisable(true);
        resetButton.setDisable(false);
        generateButton.setDisable(false);
    }
    
    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(50) + 1; // Values from 1 to 50
        }
        return arr;
    }
    
    private void logExecution(String message) {
        Platform.runLater(() -> {
            executionLog.append(message).append("\n");
            executionLogArea.setText(executionLog.toString());
            executionLogArea.setScrollTop(Double.MAX_VALUE);
        });
    }
    
    private void clearExecutionLog() {
        executionLog = new StringBuilder();
        executionLogArea.clear();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
} 