package com.acu.javafx.arraylist;

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
import javafx.scene.control.Alert.AlertType;
import java.util.Iterator;

public class ArrayListDemo extends Application {
    private MyList<String> list;
    private TextArea outputArea;
    private TextField inputField;
    private ComboBox<String> operationComboBox;
    private Spinner<Integer> indexSpinner;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        list = new MyArrayList<>();
        
        // Create the main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        
        // Title
        Label titleLabel = new Label("ArrayList Implementation Demo");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // Input controls
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        
        Label inputLabel = new Label("Element:");
        inputField = new TextField();
        inputField.setPromptText("Enter element to add");
        inputField.setPrefWidth(150);
        
        Label operationLabel = new Label("Operation:");
        operationComboBox = new ComboBox<>();
        operationComboBox.getItems().addAll(
            "Add at end", "Add at index", "Remove by value", 
            "Remove at index", "Get at index", "Set at index",
            "Clear list", "Show size", "Show all elements"
        );
        operationComboBox.setValue("Add at end");
        
        Label indexLabel = new Label("Index:");
        indexSpinner = new Spinner<>(0, 100, 0);
        indexSpinner.setEditable(true);
        indexSpinner.setPrefWidth(80);
        
        Button executeButton = new Button("Execute");
        executeButton.setOnAction(e -> executeOperation());
        
        inputBox.getChildren().addAll(
            inputLabel, inputField, operationLabel, operationComboBox,
            indexLabel, indexSpinner, executeButton
        );
        
        // Output area
        Label outputLabel = new Label("Output:");
        outputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        outputArea = new TextArea();
        outputArea.setPrefRowCount(15);
        outputArea.setEditable(false);
        outputArea.setFont(Font.font("Monospaced", 12));
        
        // Status bar
        statusLabel = new Label("Ready");
        statusLabel.setStyle("-fx-text-fill: green;");
        
        // Add all components to root
        root.getChildren().addAll(
            titleLabel, inputBox, outputLabel, outputArea, statusLabel
        );
        
        // Create scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("ArrayList Implementation Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Add some initial elements
        addInitialElements();
    }
    
    private void addInitialElements() {
        list.add("New Zealand");
        list.add("Australia");
        list.add("Canada");
        updateOutput("Initial list created with 3 elements: " + list.toString());
    }
    
    private void executeOperation() {
        String operation = operationComboBox.getValue();
        String input = inputField.getText().trim();
        int index = indexSpinner.getValue();
        
        try {
            switch (operation) {
                case "Add at end":
                    if (input.isEmpty()) {
                        showError("Please enter an element to add");
                        return;
                    }
                    list.add(input);
                    updateOutput("Added '" + input + "' at end. List: " + list.toString());
                    break;
                    
                case "Add at index":
                    if (input.isEmpty()) {
                        showError("Please enter an element to add");
                        return;
                    }
                    if (index < 0 || index > list.size()) {
                        showError("Index out of bounds. Valid range: 0 to " + list.size());
                        return;
                    }
                    list.add(index, input);
                    updateOutput("Added '" + input + "' at index " + index + ". List: " + list.toString());
                    break;
                    
                case "Remove by value":
                    if (input.isEmpty()) {
                        showError("Please enter an element to remove");
                        return;
                    }
                    boolean removed = list.remove(input);
                    if (removed) {
                        updateOutput("Removed '" + input + "'. List: " + list.toString());
                    } else {
                        updateOutput("Element '" + input + "' not found in list. List: " + list.toString());
                    }
                    break;
                    
                case "Remove at index":
                    if (index < 0 || index >= list.size()) {
                        showError("Index out of bounds. Valid range: 0 to " + (list.size() - 1));
                        return;
                    }
                    String removedElement = list.remove(index);
                    updateOutput("Removed element '" + removedElement + "' at index " + index + ". List: " + list.toString());
                    break;
                    
                case "Get at index":
                    if (index < 0 || index >= list.size()) {
                        showError("Index out of bounds. Valid range: 0 to " + (list.size() - 1));
                        return;
                    }
                    String element = list.get(index);
                    updateOutput("Element at index " + index + ": '" + element + "'");
                    break;
                    
                case "Set at index":
                    if (input.isEmpty()) {
                        showError("Please enter an element to set");
                        return;
                    }
                    if (index < 0 || index >= list.size()) {
                        showError("Index out of bounds. Valid range: 0 to " + (list.size() - 1));
                        return;
                    }
                    String oldElement = list.set(index, input);
                    updateOutput("Replaced '" + oldElement + "' with '" + input + "' at index " + index + ". List: " + list.toString());
                    break;
                    
                case "Clear list":
                    list.clear();
                    updateOutput("List cleared. List: " + list.toString());
                    break;
                    
                case "Show size":
                    updateOutput("List size: " + list.size());
                    break;
                    
                case "Show all elements":
                    StringBuilder sb = new StringBuilder();
                    sb.append("All elements:\n");
                    for (int i = 0; i < list.size(); i++) {
                        sb.append("Index ").append(i).append(": ").append(list.get(i)).append("\n");
                    }
                    updateOutput(sb.toString());
                    break;
            }
            
            // Update status
            statusLabel.setText("Operation completed successfully");
            statusLabel.setStyle("-fx-text-fill: green;");
            
            // Clear input field
            inputField.clear();
            
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        }
    }
    
    private void updateOutput(String message) {
        outputArea.appendText(message + "\n");
        outputArea.setScrollTop(Double.MAX_VALUE); // Auto-scroll to bottom
    }
    
    private void showError(String message) {
        statusLabel.setText("Error: " + message);
        statusLabel.setStyle("-fx-text-fill: red;");
        
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
} 