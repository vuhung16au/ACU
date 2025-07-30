package com.acu.javafx.priorityqueue;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.*;

public class PriorityQueueJavaFXApp extends Application {
    
    private PriorityQueue<String> queue1;
    private PriorityQueue<String> queue2;
    private TextArea outputArea;
    private TextField inputField;
    
    @Override
    public void start(Stage primaryStage) {
        // Initialize the priority queues
        queue1 = new PriorityQueue<>();
        queue2 = new PriorityQueue<>(4, Collections.reverseOrder());
        
        // Create the main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        // Create title
        Label titleLabel = new Label("PriorityQueue Demo");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        // Create input section
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        Label inputLabel = new Label("Enter a string:");
        inputField = new TextField();
        inputField.setPrefWidth(200);
        inputField.setPromptText("e.g., Sydney, Melbourne, Brisbane, Perth");
        
        Button addButton = new Button("Add to Queues");
        addButton.setOnAction(e -> addToQueues());
        
        inputBox.getChildren().addAll(inputLabel, inputField, addButton);
        
        // Create demo buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button demoButton1 = new Button("Demo Comparable Queue");
        demoButton1.setOnAction(e -> demoComparableQueue());
        
        Button demoButton2 = new Button("Demo Comparator Queue");
        demoButton2.setOnAction(e -> demoComparatorQueue());
        
        Button clearButton = new Button("Clear All");
        clearButton.setOnAction(e -> clearQueues());
        
        buttonBox.getChildren().addAll(demoButton1, demoButton2, clearButton);
        
        // Create output area
        outputArea = new TextArea();
        outputArea.setPrefRowCount(15);
        outputArea.setPrefColumnCount(60);
        outputArea.setEditable(false);
        outputArea.setStyle("-fx-font-family: 'Monaco', 'Consolas', monospace; -fx-font-size: 12px;");
        
        // Add all components to root
        root.getChildren().addAll(titleLabel, inputBox, buttonBox, outputArea);
        
        // Create scene and stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("PriorityQueue JavaFX Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Add some initial data
        addInitialData();
    }
    
    private void addInitialData() {
        String[] initialData = {"Sydney", "Melbourne", "Brisbane", "Perth"};
        for (String item : initialData) {
            queue1.offer(item);
            queue2.offer(item);
        }
        updateOutput("Initial data added to both queues:\n" + 
                    "Queue 1 (Comparable): " + Arrays.toString(initialData) + "\n" +
                    "Queue 2 (Reverse Comparator): " + Arrays.toString(initialData) + "\n");
    }
    
    private void addToQueues() {
        String input = inputField.getText().trim();
        if (!input.isEmpty()) {
            queue1.offer(input);
            queue2.offer(input);
            updateOutput("Added '" + input + "' to both queues.\n");
            inputField.clear();
        }
    }
    
    private void demoComparableQueue() {
        updateOutput("Priority queue using Comparable:\n");
        PriorityQueue<String> tempQueue = new PriorityQueue<>(queue1);
        while (!tempQueue.isEmpty()) {
            String item = tempQueue.remove();
            updateOutput(item + " ");
        }
        updateOutput("\n");
    }
    
    private void demoComparatorQueue() {
        updateOutput("Priority queue using Comparator (reverse order):\n");
        PriorityQueue<String> tempQueue = new PriorityQueue<>(queue2);
        while (!tempQueue.isEmpty()) {
            String item = tempQueue.remove();
            updateOutput(item + " ");
        }
        updateOutput("\n");
    }
    
    private void clearQueues() {
        queue1.clear();
        queue2.clear();
        updateOutput("Both queues cleared.\n");
    }
    
    private void updateOutput(String text) {
        outputArea.appendText(text);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
} 