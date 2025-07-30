package com.acu.javafx.linkedlist;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * JavaFX application demonstrating LinkedList data structure functionality.
 * This application provides a visual interface for interacting with a LinkedList,
 * allowing users to add, remove, and view elements with animated visualizations.
 */
public class LinkedListDemo extends Application {
    
    private MyLinkedList<String> linkedList;
    private VBox visualizationPane;
    private TextArea outputArea;
    private TextField elementField;
    private TextField indexField;
    private Label statusLabel;
    
    @Override
    public void start(Stage primaryStage) {
        linkedList = new MyLinkedList<>();
        
        // Create the main layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        // Create title
        Label titleLabel = new Label("LinkedList Data Structure Demo");
        titleLabel.setFont(Font.font("Arial", 24));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setPadding(new Insets(20));
        
        // Create control panel
        VBox controlPanel = createControlPanel();
        
        // Create visualization pane
        visualizationPane = new VBox(10);
        visualizationPane.setAlignment(Pos.CENTER);
        visualizationPane.setPadding(new Insets(20));
        visualizationPane.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-width: 1;");
        
        // Create output area
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(8);
        outputArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");
        
        // Create status label
        statusLabel = new Label("Ready to demonstrate LinkedList operations");
        statusLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #27ae60;");
        statusLabel.setAlignment(Pos.CENTER);
        statusLabel.setPadding(new Insets(10));
        
        // Layout setup
        VBox topSection = new VBox(10);
        topSection.getChildren().addAll(titleLabel, controlPanel);
        topSection.setPadding(new Insets(20));
        
        VBox centerSection = new VBox(10);
        centerSection.getChildren().addAll(visualizationPane, statusLabel);
        centerSection.setPadding(new Insets(20));
        
        root.setTop(topSection);
        root.setCenter(centerSection);
        root.setBottom(outputArea);
        
        // Create scene
        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("LinkedList Data Structure Demo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        
        // Initialize the visualization
        updateVisualization();
        
        primaryStage.show();
    }
    
    /**
     * Creates the control panel with buttons and input fields.
     */
    private VBox createControlPanel() {
        VBox controlPanel = new VBox(15);
        controlPanel.setAlignment(Pos.CENTER);
        controlPanel.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 15; -fx-border-color: #bdc3c7; -fx-border-width: 1;");
        
        // Input fields
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        
        Label elementLabel = new Label("Element:");
        elementField = new TextField();
        elementField.setPromptText("Enter element value");
        elementField.setPrefWidth(150);
        
        Label indexLabel = new Label("Index:");
        indexField = new TextField();
        indexField.setPromptText("Enter index (optional)");
        indexField.setPrefWidth(100);
        
        inputBox.getChildren().addAll(elementLabel, elementField, indexLabel, indexField);
        
        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button addFirstBtn = new Button("Add First");
        addFirstBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        addFirstBtn.setOnAction(e -> addFirst());
        
        Button addLastBtn = new Button("Add Last");
        addLastBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        addLastBtn.setOnAction(e -> addLast());
        
        Button addAtIndexBtn = new Button("Add at Index");
        addAtIndexBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold;");
        addAtIndexBtn.setOnAction(e -> addAtIndex());
        
        Button removeFirstBtn = new Button("Remove First");
        removeFirstBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        removeFirstBtn.setOnAction(e -> removeFirst());
        
        Button removeLastBtn = new Button("Remove Last");
        removeLastBtn.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-font-weight: bold;");
        removeLastBtn.setOnAction(e -> removeLast());
        
        Button removeAtIndexBtn = new Button("Remove at Index");
        removeAtIndexBtn.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");
        removeAtIndexBtn.setOnAction(e -> removeAtIndex());
        
        Button clearBtn = new Button("Clear List");
        clearBtn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-weight: bold;");
        clearBtn.setOnAction(e -> clearList());
        
        Button testBtn = new Button("Run Test");
        testBtn.setStyle("-fx-background-color: #1abc9c; -fx-text-fill: white; -fx-font-weight: bold;");
        testBtn.setOnAction(e -> runTest());
        
        buttonBox.getChildren().addAll(addFirstBtn, addLastBtn, addAtIndexBtn, 
                                     removeFirstBtn, removeLastBtn, removeAtIndexBtn, clearBtn, testBtn);
        
        controlPanel.getChildren().addAll(inputBox, buttonBox);
        
        return controlPanel;
    }
    
    /**
     * Updates the visual representation of the LinkedList.
     */
    private void updateVisualization() {
        visualizationPane.getChildren().clear();
        
        if (linkedList.size() == 0) {
            Text emptyText = new Text("Empty LinkedList");
            emptyText.setFont(Font.font("Arial", 18));
            emptyText.setFill(Color.GRAY);
            visualizationPane.getChildren().add(emptyText);
            return;
        }
        
        HBox listVisualization = new HBox(20);
        listVisualization.setAlignment(Pos.CENTER);
        
        // Add head pointer
        VBox headPointer = new VBox(5);
        headPointer.setAlignment(Pos.CENTER);
        Text headText = new Text("head");
        headText.setFont(Font.font("Arial", 12));
        Circle headCircle = new Circle(8);
        headCircle.setFill(Color.BLUE);
        headPointer.getChildren().addAll(headText, headCircle);
        
        listVisualization.getChildren().add(headPointer);
        
        // Add nodes
        for (int i = 0; i < linkedList.size(); i++) {
            VBox nodeBox = new VBox(5);
            nodeBox.setAlignment(Pos.CENTER);
            
            // Node circle
            Circle nodeCircle = new Circle(25);
            nodeCircle.setFill(Color.LIGHTBLUE);
            nodeCircle.setStroke(Color.BLACK);
            nodeCircle.setStrokeWidth(2);
            
            // Element text
            Text elementText = new Text(linkedList.get(i));
            elementText.setFont(Font.font("Arial", 12));
            elementText.setFill(Color.BLACK);
            
            // Index text
            Text indexText = new Text("[" + i + "]");
            indexText.setFont(Font.font("Arial", 10));
            indexText.setFill(Color.GRAY);
            
            nodeBox.getChildren().addAll(nodeCircle, elementText, indexText);
            
            // Add arrow if not last node
            if (i < linkedList.size() - 1) {
                Line arrow = new Line(0, 0, 40, 0);
                arrow.setStroke(Color.BLACK);
                arrow.setStrokeWidth(2);
                
                // Arrow head
                Line arrowHead1 = new Line(35, -5, 40, 0);
                Line arrowHead2 = new Line(35, 5, 40, 0);
                arrowHead1.setStroke(Color.BLACK);
                arrowHead2.setStroke(Color.BLACK);
                arrowHead1.setStrokeWidth(2);
                arrowHead2.setStrokeWidth(2);
                
                HBox arrowBox = new HBox();
                arrowBox.setAlignment(Pos.CENTER);
                arrowBox.getChildren().addAll(arrow, arrowHead1, arrowHead2);
                
                HBox nodeWithArrow = new HBox(10);
                nodeWithArrow.setAlignment(Pos.CENTER);
                nodeWithArrow.getChildren().addAll(nodeBox, arrowBox);
                listVisualization.getChildren().add(nodeWithArrow);
            } else {
                listVisualization.getChildren().add(nodeBox);
            }
        }
        
        // Add tail pointer
        VBox tailPointer = new VBox(5);
        tailPointer.setAlignment(Pos.CENTER);
        Text tailText = new Text("tail");
        tailText.setFont(Font.font("Arial", 12));
        Circle tailCircle = new Circle(8);
        tailCircle.setFill(Color.RED);
        tailPointer.getChildren().addAll(tailText, tailCircle);
        
        listVisualization.getChildren().add(tailPointer);
        
        visualizationPane.getChildren().add(listVisualization);
        
        // Add list info
        Text infoText = new Text("Size: " + linkedList.size() + " | Elements: " + linkedList.toString());
        infoText.setFont(Font.font("Arial", 14));
        infoText.setFill(Color.DARKGRAY);
        visualizationPane.getChildren().add(infoText);
    }
    
    /**
     * Adds an element to the beginning of the list.
     */
    private void addFirst() {
        String element = elementField.getText().trim();
        if (element.isEmpty()) {
            showStatus("Please enter an element value", false);
            return;
        }
        
        linkedList.addFirst(element);
        updateVisualization();
        appendOutput("Added '" + element + "' to the beginning of the list");
        showStatus("Element added successfully", true);
        elementField.clear();
    }
    
    /**
     * Adds an element to the end of the list.
     */
    private void addLast() {
        String element = elementField.getText().trim();
        if (element.isEmpty()) {
            showStatus("Please enter an element value", false);
            return;
        }
        
        linkedList.addLast(element);
        updateVisualization();
        appendOutput("Added '" + element + "' to the end of the list");
        showStatus("Element added successfully", true);
        elementField.clear();
    }
    
    /**
     * Adds an element at a specific index.
     */
    private void addAtIndex() {
        String element = elementField.getText().trim();
        String indexStr = indexField.getText().trim();
        
        if (element.isEmpty()) {
            showStatus("Please enter an element value", false);
            return;
        }
        
        if (indexStr.isEmpty()) {
            showStatus("Please enter an index", false);
            return;
        }
        
        try {
            int index = Integer.parseInt(indexStr);
            if (index < 0 || index > linkedList.size()) {
                showStatus("Index out of range. Valid range: 0 to " + linkedList.size(), false);
                return;
            }
            
            linkedList.add(index, element);
            updateVisualization();
            appendOutput("Added '" + element + "' at index " + index);
            showStatus("Element added successfully", true);
            elementField.clear();
            indexField.clear();
        } catch (NumberFormatException e) {
            showStatus("Please enter a valid index number", false);
        }
    }
    
    /**
     * Removes the first element from the list.
     */
    private void removeFirst() {
        if (linkedList.size() == 0) {
            showStatus("List is empty", false);
            return;
        }
        
        String removed = linkedList.removeFirst();
        updateVisualization();
        appendOutput("Removed first element: '" + removed + "'");
        showStatus("Element removed successfully", true);
    }
    
    /**
     * Removes the last element from the list.
     */
    private void removeLast() {
        if (linkedList.size() == 0) {
            showStatus("List is empty", false);
            return;
        }
        
        String removed = linkedList.removeLast();
        updateVisualization();
        appendOutput("Removed last element: '" + removed + "'");
        showStatus("Element removed successfully", true);
    }
    
    /**
     * Removes an element at a specific index.
     */
    private void removeAtIndex() {
        String indexStr = indexField.getText().trim();
        
        if (indexStr.isEmpty()) {
            showStatus("Please enter an index", false);
            return;
        }
        
        try {
            int index = Integer.parseInt(indexStr);
            if (index < 0 || index >= linkedList.size()) {
                showStatus("Index out of range. Valid range: 0 to " + (linkedList.size() - 1), false);
                return;
            }
            
            String removed = linkedList.remove(index);
            updateVisualization();
            appendOutput("Removed element at index " + index + ": '" + removed + "'");
            showStatus("Element removed successfully", true);
            indexField.clear();
        } catch (NumberFormatException e) {
            showStatus("Please enter a valid index number", false);
        }
    }
    
    /**
     * Clears the entire list.
     */
    private void clearList() {
        linkedList.clear();
        updateVisualization();
        appendOutput("Cleared the entire list");
        showStatus("List cleared successfully", true);
    }
    
    /**
     * Runs the test demonstration from TestMyLinkedList.
     */
    private void runTest() {
        appendOutput("=== Running LinkedList Test ===");
        
        // Clear current list
        linkedList.clear();
        updateVisualization();
        
        // Test operations
        linkedList.add("America");
        updateVisualization();
        appendOutput("(1) " + linkedList);
        
        linkedList.add(0, "Canada");
        updateVisualization();
        appendOutput("(2) " + linkedList);
        
        linkedList.add("Russia");
        updateVisualization();
        appendOutput("(3) " + linkedList);
        
        linkedList.addLast("France");
        updateVisualization();
        appendOutput("(4) " + linkedList);
        
        linkedList.add(2, "Germany");
        updateVisualization();
        appendOutput("(5) " + linkedList);
        
        linkedList.add(5, "Norway");
        updateVisualization();
        appendOutput("(6) " + linkedList);
        
        linkedList.add(0, "Poland");
        updateVisualization();
        appendOutput("(7) " + linkedList);
        
        linkedList.remove(0);
        updateVisualization();
        appendOutput("(8) " + linkedList);
        
        linkedList.remove(2);
        updateVisualization();
        appendOutput("(9) " + linkedList);
        
        linkedList.remove(linkedList.size() - 1);
        updateVisualization();
        appendOutput("(10) " + linkedList);
        
        StringBuilder result = new StringBuilder("(11) ");
        for (String s : linkedList) {
            result.append(s.toUpperCase()).append(" ");
        }
        appendOutput(result.toString());
        
        linkedList.clear();
        updateVisualization();
        appendOutput("After clearing the list, the list size is " + linkedList.size());
        
        showStatus("Test completed successfully", true);
    }
    
    /**
     * Appends text to the output area.
     */
    private void appendOutput(String text) {
        outputArea.appendText(text + "\n");
    }
    
    /**
     * Shows status message with color coding.
     */
    private void showStatus(String message, boolean success) {
        statusLabel.setText(message);
        if (success) {
            statusLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #27ae60;");
        } else {
            statusLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #e74c3c;");
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
