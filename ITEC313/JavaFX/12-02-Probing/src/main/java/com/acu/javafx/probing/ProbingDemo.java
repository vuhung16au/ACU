package com.acu.javafx.probing;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * JavaFX application demonstrating Linear Probing, Quadratic Probing, and Separate Chaining.
 * This application provides an interactive interface to visualize and compare different
 * hash table collision resolution techniques.
 */
public class ProbingDemo extends Application {

    private HashSetUsingLinearProbing linearProbing;
    private HashSetUsingQuadraticProbing quadraticProbing;
    private MyHashSet separateChaining;

    private TextArea linearProbingArea;
    private TextArea quadraticProbingArea;
    private TextArea separateChainingArea;

    private TextField inputField;
    private Label statusLabel;

    private static final int BOX_WIDTH = 40;
    private static final int BOX_HEIGHT = 30;
    private static final int CELL_SPACING = 5;

    @Override
    public void start(Stage primaryStage) {
        // Initialize hash tables
        linearProbing = new HashSetUsingLinearProbing();
        quadraticProbing = new HashSetUsingQuadraticProbing();
        separateChaining = new MyHashSet();

        // Add some initial data
        addInitialData();

        // Create the main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Create title
        Label titleLabel = new Label("Hash Table Probing Techniques Demonstration");
        titleLabel.setFont(Font.font("Arial", 24));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Create input controls
        HBox inputBox = createInputControls();

        // Create visualization areas
        HBox visualizationBox = createVisualizationAreas();

        // Create status area
        statusLabel = new Label("Ready to demonstrate hash table operations");
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");

        // Add all components to root
        root.getChildren().addAll(titleLabel, inputBox, visualizationBox, statusLabel);

        // Create scene
        Scene scene = new Scene(root, 1200, 1100); // Increased height for more content
        primaryStage.setTitle("Hash Table Probing Techniques - JavaFX Demo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);

        // Update visualizations
        updateAllVisualizations();

        primaryStage.show();
    }

    /**
     * Create input controls for the application.
     */
    private HBox createInputControls() {
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(10));

        // Input field
        Label inputLabel = new Label("Enter a number:");
        inputField = new TextField();
        inputField.setPrefWidth(100);
        inputField.setPromptText("0-99");

        // Buttons
        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
        addButton.setOnAction(e -> addElement());

        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        removeButton.setOnAction(e -> removeElement());

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        searchButton.setOnAction(e -> searchElement());

        Button clearButton = new Button("Clear All");
        clearButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
        clearButton.setOnAction(e -> clearAll());

        // Load factor controls
        Label loadFactorLabel = new Label("Load Factor:");
        TextField loadFactorField = new TextField("0.75");
        loadFactorField.setPrefWidth(60);

        Button setLoadFactorButton = new Button("Set");
        setLoadFactorButton.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white;");
        setLoadFactorButton.setOnAction(e -> {
            try {
                double loadFactor = Double.parseDouble(loadFactorField.getText());
                setLoadFactor(loadFactor);
            } catch (NumberFormatException ex) {
                showAlert("Invalid load factor. Please enter a number between 0 and 1.");
            }
        });

        inputBox.getChildren().addAll(
                inputLabel, inputField, addButton, removeButton, searchButton, clearButton,
                new Separator(), loadFactorLabel, loadFactorField, setLoadFactorButton
        );

        return inputBox;
    }

    /**
     * Create visualization areas for the three probing techniques.
     */
    private HBox createVisualizationAreas() {
        HBox visualizationBox = new HBox(20);
        visualizationBox.setAlignment(Pos.TOP_CENTER);

        // Linear Probing
        VBox linearProbingBox = createProbingBox("Linear Probing", "linear");
        linearProbingArea = (TextArea) linearProbingBox.getChildren().get(1);

        // Quadratic Probing
        VBox quadraticProbingBox = createProbingBox("Quadratic Probing", "quadratic");
        quadraticProbingArea = (TextArea) quadraticProbingBox.getChildren().get(1);

        // Separate Chaining
        VBox separateChainingBox = createProbingBox("Separate Chaining", "separate");
        separateChainingArea = (TextArea) separateChainingBox.getChildren().get(1);

        visualizationBox.getChildren().addAll(linearProbingBox, quadraticProbingBox, separateChainingBox);
        return visualizationBox;
    }

    /**
     * Create a box for displaying a probing technique.
     */
    private VBox createProbingBox(String title, String type) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");
        box.setPrefWidth(350);
        box.setPrefHeight(650); // Increased height for more content

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", 16));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(32); // Increased row count for more visible text
        textArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");

        box.getChildren().addAll(titleLabel, textArea);
        return box;
    }

    /**
     * Add initial data to all hash tables.
     */
    private void addInitialData() {
        int[] initialData = {44, 4, 16, 28, 21};
        for (int data : initialData) {
            linearProbing.add(data);
            quadraticProbing.add(data);
            separateChaining.add(data);
        }
    }

    /**
     * Add an element to all hash tables.
     */
    private void addElement() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            if (value < 0 || value > 99) {
                showAlert("Please enter a number between 0 and 99.");
                return;
            }

            boolean linearAdded = linearProbing.add(value);
            boolean quadraticAdded = quadraticProbing.add(value);
            boolean separateAdded = separateChaining.add(value);

            if (linearAdded && quadraticAdded && separateAdded) {
                statusLabel.setText("Added " + value + " to all hash tables successfully.");
            } else {
                statusLabel.setText("Element " + value + " already exists in one or more hash tables.");
            }

            updateAllVisualizations();
            inputField.clear();

        } catch (NumberFormatException e) {
            showAlert("Please enter a valid integer.");
        }
    }

    /**
     * Remove an element from all hash tables.
     */
    private void removeElement() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            if (value < 0 || value > 99) {
                showAlert("Please enter a number between 0 and 99.");
                return;
            }

            boolean linearRemoved = linearProbing.remove(value);
            boolean quadraticRemoved = quadraticProbing.remove(value);
            boolean separateRemoved = separateChaining.remove(value);

            if (linearRemoved && quadraticRemoved && separateRemoved) {
                statusLabel.setText("Removed " + value + " from all hash tables successfully.");
            } else {
                statusLabel.setText("Element " + value + " not found in one or more hash tables.");
            }

            updateAllVisualizations();
            inputField.clear();

        } catch (NumberFormatException e) {
            showAlert("Please enter a valid integer.");
        }
    }

    /**
     * Search for an element in all hash tables.
     */
    private void searchElement() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            if (value < 0 || value > 99) {
                showAlert("Please enter a number between 0 and 99.");
                return;
            }

            boolean linearFound = linearProbing.contains(value);
            boolean quadraticFound = quadraticProbing.contains(value);
            boolean separateFound = separateChaining.contains(value);

            if (linearFound && quadraticFound && separateFound) {
                statusLabel.setText("Element " + value + " found in all hash tables.");
            } else {
                statusLabel.setText("Element " + value + " not found in one or more hash tables.");
            }

            inputField.clear();

        } catch (NumberFormatException e) {
            showAlert("Please enter a valid integer.");
        }
    }

    /**
     * Clear all hash tables.
     */
    private void clearAll() {
        linearProbing.clear();
        quadraticProbing.clear();
        separateChaining.clear();
        
        // Add initial data back
        addInitialData();
        
        statusLabel.setText("Cleared all hash tables and restored initial data.");
        updateAllVisualizations();
    }

    /**
     * Set load factor for all hash tables.
     */
    private void setLoadFactor(double loadFactor) {
        try {
            linearProbing.setLoadFactorThreshold(loadFactor);
            quadraticProbing.setLoadFactorThreshold(loadFactor);
            separateChaining.setLoadFactorThreshold(loadFactor);
            
            statusLabel.setText("Load factor set to " + loadFactor + " for all hash tables.");
            updateAllVisualizations();
        } catch (IllegalArgumentException e) {
            showAlert(e.getMessage());
        }
    }

    /**
     * Update all visualization areas.
     */
    private void updateAllVisualizations() {
        updateLinearProbingVisualization();
        updateQuadraticProbingVisualization();
        updateSeparateChainingVisualization();
    }

    /**
     * Update the linear probing visualization.
     */
    private void updateLinearProbingVisualization() {
        StringBuilder sb = new StringBuilder();
        sb.append("Linear Probing Hash Table\n");
        sb.append("========================\n");
        sb.append("Capacity: ").append(linearProbing.getCapacity()).append("\n");
        sb.append("Size: ").append(linearProbing.size()).append("\n");
        sb.append("Load Factor: ").append(String.format("%.2f", linearProbing.getCurrentLoadFactor())).append("\n");
        sb.append("Threshold: ").append(String.format("%.2f", linearProbing.getLoadFactorThreshold())).append("\n\n");

        Integer[] table = linearProbing.getTable();
        sb.append("Index | Value\n");
        sb.append("------|-------\n");
        for (int i = 0; i < table.length; i++) {
            String value = table[i] == null ? "null" : 
                          table[i] == Integer.MIN_VALUE ? "DEL" : table[i].toString();
            sb.append(String.format("%5d | %s\n", i, value));
        }

        sb.append("\nElements: ").append(linearProbing.toString());
        linearProbingArea.setText(sb.toString());
    }

    /**
     * Update the quadratic probing visualization.
     */
    private void updateQuadraticProbingVisualization() {
        StringBuilder sb = new StringBuilder();
        sb.append("Quadratic Probing Hash Table\n");
        sb.append("===========================\n");
        sb.append("Capacity: ").append(quadraticProbing.getCapacity()).append("\n");
        sb.append("Size: ").append(quadraticProbing.size()).append("\n");
        sb.append("Load Factor: ").append(String.format("%.2f", quadraticProbing.getCurrentLoadFactor())).append("\n");
        sb.append("Threshold: ").append(String.format("%.2f", quadraticProbing.getLoadFactorThreshold())).append("\n\n");

        Integer[] table = quadraticProbing.getTable();
        sb.append("Index | Value\n");
        sb.append("------|-------\n");
        for (int i = 0; i < table.length; i++) {
            String value = table[i] == null ? "null" : 
                          table[i] == Integer.MIN_VALUE ? "DEL" : table[i].toString();
            sb.append(String.format("%5d | %s\n", i, value));
        }

        sb.append("\nElements: ").append(quadraticProbing.toString());
        quadraticProbingArea.setText(sb.toString());
    }

    /**
     * Update the separate chaining visualization.
     */
    private void updateSeparateChainingVisualization() {
        StringBuilder sb = new StringBuilder();
        sb.append("Separate Chaining Hash Table\n");
        sb.append("===========================\n");
        sb.append("Capacity: ").append(separateChaining.getCapacity()).append("\n");
        sb.append("Size: ").append(separateChaining.size()).append("\n");
        sb.append("Load Factor: ").append(String.format("%.2f", separateChaining.getCurrentLoadFactor())).append("\n");
        sb.append("Threshold: ").append(String.format("%.2f", separateChaining.getLoadFactorThreshold())).append("\n\n");

        LinkedList<Integer>[] table = separateChaining.getTable();
        sb.append("Index | Chain\n");
        sb.append("------|-------\n");
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null || table[i].isEmpty()) {
                sb.append(String.format("%5d | null\n", i));
            } else {
                sb.append(String.format("%5d | %s\n", i, table[i].toString()));
            }
        }

        sb.append("\nElements: ").append(separateChaining.toString());
        separateChainingArea.setText(sb.toString());
    }

    /**
     * Show an alert dialog.
     */
    private void showAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    /**
     * Main method to launch the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}