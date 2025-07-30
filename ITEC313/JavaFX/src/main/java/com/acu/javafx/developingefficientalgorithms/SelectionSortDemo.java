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
 * JavaFX demonstration of Selection Sort algorithm.
 * Shows the step-by-step process of selection sort with visual feedback.
 * Time Complexity: O(n²) - Quadratic time complexity
 */
public class SelectionSortDemo {
    private Stage stage;
    private TextArea outputArea;
    private Button sortButton;
    private Button generateButton;
    private Button stepButton;
    private int[] array;
    private Label complexityLabel;
    private Label descriptionLabel;
    private boolean isSorted = false;
    private int currentStep = 0;
    private int[] originalArray;

    public void show() {
        stage = new Stage();
        stage.setTitle("Selection Sort Algorithm Demo");
        stage.setWidth(900);
        stage.setHeight(700);

        // Initialize array
        array = new int[15];
        generateRandomArray();

        // Create UI components
        createUI();

        Scene scene = new Scene(createMainLayout());
        stage.setScene(scene);
        stage.show();
    }

    private void createUI() {
        // Title
        Label titleLabel = new Label("Selection Sort Algorithm");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Description
        descriptionLabel = new Label(
            "Selection Sort repeatedly selects the smallest element from the unsorted portion\n" +
            "and places it at the beginning of the sorted portion.\n" +
            "Time Complexity: O(n²) - Quadratic time complexity\n" +
            "Space Complexity: O(1) - Constant space complexity"
        );
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px;");

        // Complexity information
        complexityLabel = new Label("Best Case: O(n²), Worst Case: O(n²), Average Case: O(n²)");
        complexityLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2E8B57;");

        // Output area
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(20);
        outputArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
    }

    private VBox createMainLayout() {
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));

        // Title and description
        VBox headerBox = new VBox(10);
        headerBox.getChildren().addAll(
            new Label("Selection Sort Algorithm"),
            descriptionLabel,
            complexityLabel
        );

        // Array display
        VBox arrayBox = new VBox(10);
        arrayBox.getChildren().addAll(
            new Label("Current Array:"),
            createArrayDisplay()
        );

        // Control buttons
        VBox controlBox = new VBox(10);
        controlBox.getChildren().addAll(
            new Label("Sort Controls:"),
            createControlButtons()
        );

        // Output area
        VBox outputBox = new VBox(10);
        outputBox.getChildren().addAll(
            new Label("Sorting Process:"),
            outputArea
        );
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        mainLayout.getChildren().addAll(headerBox, arrayBox, controlBox, outputBox);
        return mainLayout;
    }

    private HBox createControlButtons() {
        HBox buttonBox = new HBox(10);
        
        generateButton = new Button("Generate New Array");
        generateButton.setOnAction(e -> {
            generateRandomArray();
            resetSort();
        });

        sortButton = new Button("Sort Array");
        sortButton.setOnAction(e -> performSelectionSort());

        stepButton = new Button("Step-by-Step Sort");
        stepButton.setOnAction(e -> performStepByStepSort());

        buttonBox.getChildren().addAll(generateButton, sortButton, stepButton);
        return buttonBox;
    }

    private HBox createArrayDisplay() {
        HBox arrayDisplay = new HBox(5);
        arrayDisplay.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-border-color: #ccc; -fx-border-width: 1;");
        
        for (int i = 0; i < array.length; i++) {
            Label elementLabel = new Label(String.valueOf(array[i]));
            String style = "-fx-background-color: white; -fx-padding: 5; -fx-border-color: #999; -fx-border-width: 1; -fx-min-width: 30; -fx-alignment: center;";
            if (isSorted) {
                style += " -fx-background-color: #e6f3ff;";
            }
            elementLabel.setStyle(style);
            elementLabel.setId("element-" + i);
            arrayDisplay.getChildren().add(elementLabel);
        }
        
        return arrayDisplay;
    }

    private void updateArrayDisplay() {
        outputArea.clear();
        outputArea.appendText("Original Array: " + Arrays.toString(originalArray) + "\n");
        outputArea.appendText("Current Array: " + Arrays.toString(array) + "\n");
        outputArea.appendText("Array length: " + array.length + "\n");
        outputArea.appendText("Sorted: " + (isSorted ? "Yes" : "No") + "\n\n");
    }

    private void generateRandomArray() {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(100); // Generate numbers 0-99
        }
        originalArray = array.clone();
        resetSort();
    }

    private void resetSort() {
        isSorted = false;
        currentStep = 0;
        updateArrayDisplay();
    }

    private void performSelectionSort() {
        outputArea.clear();
        outputArea.appendText("Starting Selection Sort...\n");
        outputArea.appendText("Original Array: " + Arrays.toString(array) + "\n\n");

        int comparisons = 0;
        int swaps = 0;

        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            outputArea.appendText("Pass " + (i + 1) + ": Looking for minimum in unsorted portion\n");
            
            // Find the minimum element in the unsorted portion
            for (int j = i + 1; j < array.length; j++) {
                comparisons++;
                outputArea.appendText("  Comparing " + array[j] + " with current minimum " + array[minIndex] + "\n");
                
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                    outputArea.appendText("    → New minimum found: " + array[minIndex] + " at index " + minIndex + "\n");
                }
            }
            
            // Swap the found minimum element with the first element
            if (minIndex != i) {
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
                swaps++;
                outputArea.appendText("  Swapping " + array[minIndex] + " with " + array[i] + "\n");
            }
            
            outputArea.appendText("  Array after pass " + (i + 1) + ": " + Arrays.toString(array) + "\n\n");
        }

        isSorted = true;
        outputArea.appendText("=== Sort Complete ===\n");
        outputArea.appendText("Final sorted array: " + Arrays.toString(array) + "\n");
        outputArea.appendText("Total comparisons: " + comparisons + "\n");
        outputArea.appendText("Total swaps: " + swaps + "\n");
        outputArea.appendText("Time Complexity: O(n²) - Quadratic time\n");
        outputArea.appendText("Space Complexity: O(1) - Constant space\n");
    }

    private void performStepByStepSort() {
        outputArea.clear();
        outputArea.appendText("Step-by-Step Selection Sort\n");
        outputArea.appendText("Original Array: " + Arrays.toString(array) + "\n\n");

        // Reset array to original state
        array = originalArray.clone();
        isSorted = false;
        currentStep = 0;

        // Perform step-by-step sort
        selectionSortStepByStep();
    }

    private void selectionSortStepByStep() {
        if (currentStep >= array.length - 1) {
            isSorted = true;
            outputArea.appendText("✓ Sorting complete!\n");
            return;
        }

        int minIndex = currentStep;
        outputArea.appendText("Step " + (currentStep + 1) + ": Finding minimum in unsorted portion\n");
        outputArea.appendText("Current array: " + Arrays.toString(array) + "\n");

        // Find minimum in unsorted portion
        for (int j = currentStep + 1; j < array.length; j++) {
            if (array[j] < array[minIndex]) {
                minIndex = j;
            }
        }

        // Swap if necessary
        if (minIndex != currentStep) {
            int temp = array[currentStep];
            array[currentStep] = array[minIndex];
            array[minIndex] = temp;
            outputArea.appendText("Swapped " + array[minIndex] + " with " + array[currentStep] + "\n");
        }

        outputArea.appendText("Array after step " + (currentStep + 1) + ": " + Arrays.toString(array) + "\n\n");
        currentStep++;
    }

    /**
     * Standard selection sort implementation
     * @param arr the array to sort
     */
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        
        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in unsorted array
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            
            // Swap the found minimum element with the first element
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }
} 