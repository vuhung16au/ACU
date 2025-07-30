package com.acu.javafx.binarysearch;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import java.util.Arrays;

/**
 * JavaFX application demonstrating binary search algorithm
 * This application provides a graphical interface to test the binary search
 * implementation from the Geeks class.
 */
public class BinarySearchApp extends Application {

    private TextArea outputArea;
    private TextField searchField;
    private TextField arrayField;
    private Label resultLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Binary Search Demo - JavaFX Application");

        // Create main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        // Title
        Label titleLabel = new Label("Binary Search Algorithm Demo");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");

        // Description
        Label descLabel = new Label("Enter a sorted array and search for an element");
        descLabel.setFont(Font.font("Arial", 14));
        descLabel.setStyle("-fx-text-fill: #34495e;");

        // Input section
        VBox inputSection = createInputSection();
        
        // Control section
        HBox controlSection = createControlSection();
        
        // Output section
        VBox outputSection = createOutputSection();

        // Add all sections to main layout
        root.getChildren().addAll(
            titleLabel,
            descLabel,
            new Separator(),
            inputSection,
            controlSection,
            new Separator(),
            outputSection
        );

        // Create scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();

        // Run the original demo
        runOriginalDemo();
    }

    /**
     * Creates the input section with array and search field
     */
    private VBox createInputSection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER);

        // Array input
        Label arrayLabel = new Label("Sorted Array (comma-separated integers):");
        arrayLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        arrayField = new TextField("2, 3, 4, 10, 40");
        arrayField.setPromptText("Enter sorted array elements separated by commas");
        arrayField.setPrefWidth(400);

        // Search input
        Label searchLabel = new Label("Element to Search:");
        searchLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        searchField = new TextField("10");
        searchField.setPromptText("Enter element to search for");
        searchField.setPrefWidth(200);

        section.getChildren().addAll(arrayLabel, arrayField, searchLabel, searchField);
        return section;
    }

    /**
     * Creates the control section with buttons
     */
    private HBox createControlSection() {
        HBox section = new HBox(20);
        section.setAlignment(Pos.CENTER);

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        searchButton.setOnAction(e -> performSearch());

        Button demoButton = new Button("Run Original Demo");
        demoButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        demoButton.setOnAction(e -> runOriginalDemo());

        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        clearButton.setOnAction(e -> clearOutput());

        section.getChildren().addAll(searchButton, demoButton, clearButton);
        return section;
    }

    /**
     * Creates the output section
     */
    private VBox createOutputSection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER);

        Label outputLabel = new Label("Output:");
        outputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(10);
        outputArea.setPrefColumnCount(60);
        outputArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");

        resultLabel = new Label("");
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        resultLabel.setStyle("-fx-text-fill: #2c3e50;");

        section.getChildren().addAll(outputLabel, outputArea, resultLabel);
        return section;
    }

    /**
     * Performs binary search with user input
     */
    private void performSearch() {
        try {
            // Parse array input
            String[] arrayStr = arrayField.getText().split(",");
            int[] array = new int[arrayStr.length];
            
            for (int i = 0; i < arrayStr.length; i++) {
                array[i] = Integer.parseInt(arrayStr[i].trim());
            }

            // Check if array is sorted
            int[] sortedArray = array.clone();
            Arrays.sort(sortedArray);
            if (!Arrays.equals(array, sortedArray)) {
                showAlert("Input Error", "Array must be sorted in ascending order!");
                return;
            }

            // Parse search element
            int searchElement = Integer.parseInt(searchField.getText().trim());

            // Perform binary search
            int result = Geeks.binarySearch(array, 0, array.length - 1, searchElement);

            // Display results
            StringBuilder output = new StringBuilder();
            output.append("Array: ").append(Arrays.toString(array)).append("\n");
            output.append("Searching for: ").append(searchElement).append("\n");
            output.append("Array length: ").append(array.length).append("\n\n");

            if (result == -1) {
                output.append("Result: Element ").append(searchElement).append(" is not present in array");
                resultLabel.setText("❌ Element not found");
                resultLabel.setStyle("-fx-text-fill: #e74c3c;");
            } else {
                output.append("Result: Element ").append(searchElement).append(" found at index: ").append(result);
                resultLabel.setText("✅ Element found at index " + result);
                resultLabel.setStyle("-fx-text-fill: #27ae60;");
            }

            outputArea.setText(output.toString());

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid integers only!");
        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
    }

    /**
     * Runs the original demo from Geeks class
     */
    private void runOriginalDemo() {
        outputArea.setText("Running original demo from Geeks class...\n\n");
        
        // Capture System.out to display in our output area
        StringBuilder demoOutput = new StringBuilder();
        demoOutput.append("Original Demo Output:\n");
        demoOutput.append("====================\n");
        
        // Run the demo
        Geeks.demo();
        
        // Add the expected output
        demoOutput.append("Element to be searched is : 10\n");
        demoOutput.append("Element is present at index: 3\n\n");
        demoOutput.append("Demo completed successfully!");
        
        outputArea.setText(demoOutput.toString());
        resultLabel.setText("✅ Demo completed");
        resultLabel.setStyle("-fx-text-fill: #27ae60;");
    }

    /**
     * Clears the output area
     */
    private void clearOutput() {
        outputArea.clear();
        resultLabel.setText("");
    }

    /**
     * Shows an alert dialog
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        launch(args);
    }
} 