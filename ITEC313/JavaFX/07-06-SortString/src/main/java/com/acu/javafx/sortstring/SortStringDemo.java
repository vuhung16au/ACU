package com.acu.javafx.sortstring;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.List;

/**
 * JavaFX application demonstrating string sorting by length and case-insensitive sorting.
 * This application provides an interactive interface to demonstrate both sorting methods
 * from the original SortStringByLength and SortStringIgnoreCase classes.
 */
public class SortStringDemo extends Application {

    private TextArea outputArea;
    private TextField inputField;
    private Button sortByLengthButton;
    private Button sortIgnoreCaseButton;
    private Button clearButton;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("String Sorting Demo");

        // Create the main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        // Title
        Label titleLabel = new Label("String Sorting Demonstration");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");

        // Description
        Label descriptionLabel = new Label("Enter strings separated by commas, then choose a sorting method:");
        descriptionLabel.setFont(Font.font("Arial", 14));
        descriptionLabel.setStyle("-fx-text-fill: #34495e;");

        // Input section
        VBox inputSection = new VBox(10);
        inputSection.setAlignment(Pos.CENTER);

        Label inputLabel = new Label("Input Strings:");
        inputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        inputField = new TextField();
        inputField.setPromptText("Enter strings separated by commas (e.g., Atlanta, Savannah, New York, Dallas)");
        inputField.setPrefWidth(500);
        inputField.setPrefHeight(40);

        // Set default text
        inputField.setText("Atlanta, Savannah, New York, Dallas");

        inputSection.getChildren().addAll(inputLabel, inputField);

        // Buttons section
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        sortByLengthButton = new Button("Sort by Length");
        sortByLengthButton.setPrefSize(150, 40);
        sortByLengthButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");

        sortIgnoreCaseButton = new Button("Sort Ignore Case");
        sortIgnoreCaseButton.setPrefSize(150, 40);
        sortIgnoreCaseButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");

        clearButton = new Button("Clear");
        clearButton.setPrefSize(100, 40);
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold;");

        buttonBox.getChildren().addAll(sortByLengthButton, sortIgnoreCaseButton, clearButton);

        // Output section
        VBox outputSection = new VBox(10);
        outputSection.setAlignment(Pos.CENTER);

        Label outputLabel = new Label("Output:");
        outputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        outputArea = new TextArea();
        outputArea.setPrefSize(500, 200);
        outputArea.setEditable(false);
        outputArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");

        outputSection.getChildren().addAll(outputLabel, outputArea);

        // Add all sections to main layout
        root.getChildren().addAll(titleLabel, descriptionLabel, inputSection, buttonBox, outputSection);

        // Set up event handlers
        setupEventHandlers();

        // Create scene
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void setupEventHandlers() {
        sortByLengthButton.setOnAction(e -> sortByLength());
        sortIgnoreCaseButton.setOnAction(e -> sortIgnoreCase());
        clearButton.setOnAction(e -> clearOutput());
    }

    private void sortByLength() {
        try {
            String input = inputField.getText().trim();
            if (input.isEmpty()) {
                showError("Please enter some strings to sort.");
                return;
            }

            String[] strings = input.split(",");
            for (int i = 0; i < strings.length; i++) {
                strings[i] = strings[i].trim();
            }

            // Use the same logic as SortStringByLength
            Arrays.sort(strings, new SortStringByLength.MyComparator());

            StringBuilder result = new StringBuilder();
            result.append("Sorting by length:\n");
            result.append("Original: ").append(input).append("\n");
            result.append("Sorted:   ");
            for (String s : strings) {
                result.append(s).append(" ");
            }
            result.append("\n\nLengths: ");
            for (String s : strings) {
                result.append(s.length()).append(" ");
            }

            outputArea.setText(result.toString());
        } catch (Exception ex) {
            showError("Error sorting by length: " + ex.getMessage());
        }
    }

    private void sortIgnoreCase() {
        try {
            String input = inputField.getText().trim();
            if (input.isEmpty()) {
                showError("Please enter some strings to sort.");
                return;
            }

            String[] strings = input.split(",");
            for (int i = 0; i < strings.length; i++) {
                strings[i] = strings[i].trim();
            }

            // Use the same logic as SortStringIgnoreCase
            List<String> stringList = Arrays.asList(strings);
            stringList.sort((s1, s2) -> s1.compareToIgnoreCase(s2));

            StringBuilder result = new StringBuilder();
            result.append("Case-insensitive sorting:\n");
            result.append("Original: ").append(input).append("\n");
            result.append("Sorted:   ");
            for (String s : stringList) {
                result.append(s).append(" ");
            }

            outputArea.setText(result.toString());
        } catch (Exception ex) {
            showError("Error sorting ignore case: " + ex.getMessage());
        }
    }

    private void clearOutput() {
        outputArea.clear();
        inputField.setText("Atlanta, Savannah, New York, Dallas");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
} 