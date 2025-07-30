package com.acu.javafx.developingefficientalgorithms;

import javafx.application.Platform;
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
 * JavaFX demonstration of Linear Search algorithm.
 * Shows the step-by-step process of linear search with visual feedback.
 * Time Complexity: O(n) - Linear time complexity
 */
public class LinearSearchDemo {
    private Stage stage;
    private TextArea outputArea;
    private TextField searchField;
    private Button searchButton;
    private Button generateButton;
    private int[] array;
    private Label complexityLabel;
    private Label descriptionLabel;

    public void show() {
        stage = new Stage();
        stage.setTitle("Linear Search Algorithm Demo");
        stage.setWidth(800);
        stage.setHeight(600);

        // Initialize array
        array = new int[20];
        generateRandomArray();

        // Create UI components
        createUI();

        Scene scene = new Scene(createMainLayout());
        stage.setScene(scene);
        stage.show();
    }

    private void createUI() {
        // Title
        Label titleLabel = new Label("Linear Search Algorithm");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Description
        descriptionLabel = new Label(
            "Linear Search sequentially checks each element in the array until the target is found.\n" +
            "Time Complexity: O(n) - Linear time complexity\n" +
            "Space Complexity: O(1) - Constant space complexity"
        );
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px;");

        // Complexity information
        complexityLabel = new Label("Best Case: O(1), Worst Case: O(n), Average Case: O(n)");
        complexityLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2E8B57;");

        // Input controls
        HBox inputBox = new HBox(10);
        Label searchLabel = new Label("Search for:");
        searchField = new TextField();
        searchField.setPromptText("Enter a number to search");
        searchField.setPrefWidth(150);
        
        searchButton = new Button("Search");
        searchButton.setOnAction(e -> performLinearSearch());
        
        generateButton = new Button("Generate New Array");
        generateButton.setOnAction(e -> {
            generateRandomArray();
            updateArrayDisplay();
        });

        inputBox.getChildren().addAll(searchLabel, searchField, searchButton, generateButton);

        // Array display
        updateArrayDisplay();

        // Output area
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(15);
        outputArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
    }

    private VBox createMainLayout() {
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));

        // Title and description
        VBox headerBox = new VBox(10);
        headerBox.getChildren().addAll(
            new Label("Linear Search Algorithm"),
            descriptionLabel,
            complexityLabel
        );

        // Array display
        VBox arrayBox = new VBox(10);
        arrayBox.getChildren().addAll(
            new Label("Current Array:"),
            createArrayDisplay()
        );

        // Input controls
        VBox controlBox = new VBox(10);
        controlBox.getChildren().addAll(
            new Label("Search Controls:"),
            createInputControls()
        );

        // Output area
        VBox outputBox = new VBox(10);
        outputBox.getChildren().addAll(
            new Label("Search Process:"),
            outputArea
        );
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        mainLayout.getChildren().addAll(headerBox, arrayBox, controlBox, outputBox);
        return mainLayout;
    }

    private HBox createInputControls() {
        HBox inputBox = new HBox(10);
        Label searchLabel = new Label("Search for:");
        searchField = new TextField();
        searchField.setPromptText("Enter a number to search");
        searchField.setPrefWidth(150);
        
        searchButton = new Button("Search");
        searchButton.setOnAction(e -> performLinearSearch());
        
        generateButton = new Button("Generate New Array");
        generateButton.setOnAction(e -> {
            generateRandomArray();
            updateArrayDisplay();
        });

        inputBox.getChildren().addAll(searchLabel, searchField, searchButton, generateButton);
        return inputBox;
    }

    private HBox createArrayDisplay() {
        HBox arrayDisplay = new HBox(5);
        arrayDisplay.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-border-color: #ccc; -fx-border-width: 1;");
        
        for (int i = 0; i < array.length; i++) {
            Label elementLabel = new Label(String.valueOf(array[i]));
            elementLabel.setStyle("-fx-background-color: white; -fx-padding: 5; -fx-border-color: #999; -fx-border-width: 1; -fx-min-width: 30; -fx-alignment: center;");
            elementLabel.setId("element-" + i);
            arrayDisplay.getChildren().add(elementLabel);
        }
        
        return arrayDisplay;
    }

    private void updateArrayDisplay() {
        // This would update the visual array display
        // For simplicity, we'll just update the output area
        outputArea.clear();
        outputArea.appendText("Array: " + Arrays.toString(array) + "\n");
        outputArea.appendText("Array length: " + array.length + "\n\n");
    }

    private void generateRandomArray() {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(100); // Generate numbers 0-99
        }
    }

    private void performLinearSearch() {
        try {
            int target = Integer.parseInt(searchField.getText());
            outputArea.clear();
            outputArea.appendText("Searching for: " + target + "\n");
            outputArea.appendText("Array: " + Arrays.toString(array) + "\n\n");

            // Perform linear search with step-by-step output
            linearSearchWithSteps(target);

        } catch (NumberFormatException e) {
            outputArea.appendText("Please enter a valid number!\n");
        }
    }

    private void linearSearchWithSteps(int target) {
        int comparisons = 0;
        boolean found = false;
        int foundIndex = -1;

        outputArea.appendText("Starting Linear Search...\n");
        outputArea.appendText("Step-by-step process:\n");

        for (int i = 0; i < array.length; i++) {
            comparisons++;
            outputArea.appendText("Step " + comparisons + ": Checking index " + i + " (value: " + array[i] + ")\n");
            
            if (array[i] == target) {
                found = true;
                foundIndex = i;
                outputArea.appendText("  ✓ FOUND! Target " + target + " is at index " + i + "\n");
                break;
            } else {
                outputArea.appendText("  ✗ Not found at index " + i + "\n");
            }
        }

        outputArea.appendText("\n=== Search Results ===\n");
        if (found) {
            outputArea.appendText("✓ SUCCESS: Found " + target + " at index " + foundIndex + "\n");
        } else {
            outputArea.appendText("✗ NOT FOUND: " + target + " is not in the array\n");
        }
        
        outputArea.appendText("Total comparisons: " + comparisons + "\n");
        outputArea.appendText("Time Complexity: O(n) - Linear time\n");
        outputArea.appendText("Space Complexity: O(1) - Constant space\n");
    }

    /**
     * Standard linear search implementation
     * @param arr the array to search in
     * @param target the value to search for
     * @return the index of the target, or -1 if not found
     */
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }
} 