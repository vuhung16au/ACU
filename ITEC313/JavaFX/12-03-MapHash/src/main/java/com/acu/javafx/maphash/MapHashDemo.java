package com.acu.javafx.maphash;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;

import java.util.Collection;

public class MapHashDemo extends Application {

    private MyHashMap<String, String> hashMap;
    private MyHashSet<String> hashSet;
    private TextArea outputArea;
    private TextField keyField;
    private TextField valueField;

    @Override
    public void start(Stage primaryStage) {
        // Initialize data structures
        hashMap = new MyHashMap<>();
        hashSet = new MyHashSet<>();

        // Create the main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        // Create title
        Label titleLabel = new Label("Map and Hash Set Demonstration");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");

        // Create tab pane for different demonstrations
        TabPane tabPane = new TabPane();
        tabPane.setPrefWidth(800);
        tabPane.setPrefHeight(600);

        // HashMap Tab
        Tab hashMapTab = new Tab("HashMap Demo");
        hashMapTab.setClosable(false);
        hashMapTab.setContent(createHashMapDemo());

        // HashSet Tab
        Tab hashSetTab = new Tab("HashSet Demo");
        hashSetTab.setClosable(false);
        hashSetTab.setContent(createHashSetDemo());

        // Test Results Tab
        Tab testTab = new Tab("Test Results");
        testTab.setClosable(false);
        testTab.setContent(createTestResults());

        tabPane.getTabs().addAll(hashMapTab, hashSetTab, testTab);

        root.getChildren().addAll(titleLabel, tabPane);

        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setTitle("Map and Hash Set Demonstration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createHashMapDemo() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        // Title
        Label title = new Label("HashMap Operations");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Input fields
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);

        Label keyLabel = new Label("Key:");
        keyField = new TextField();
        keyField.setPromptText("Enter key");
        keyField.setPrefWidth(150);

        Label valueLabel = new Label("Value:");
        valueField = new TextField();
        valueField.setPromptText("Enter value");
        valueField.setPrefWidth(150);

        inputBox.getChildren().addAll(keyLabel, keyField, valueLabel, valueField);

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button putButton = new Button("Put");
        putButton.setOnAction(e -> putToHashMap());

        Button getButton = new Button("Get");
        getButton.setOnAction(e -> getFromHashMap());

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> removeFromHashMap());

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> clearHashMap());

        Button sizeButton = new Button("Size");
        sizeButton.setOnAction(e -> showHashMapSize());

        buttonBox.getChildren().addAll(putButton, getButton, removeButton, clearButton, sizeButton);

        // Output area
        outputArea = new TextArea();
        outputArea.setPrefRowCount(15);
        outputArea.setEditable(false);
        outputArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");

        content.getChildren().addAll(title, inputBox, buttonBox, outputArea);

        return content;
    }

    private VBox createHashSetDemo() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        // Title
        Label title = new Label("HashSet Operations");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Input field
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);

        Label elementLabel = new Label("Element:");
        TextField elementField = new TextField();
        elementField.setPromptText("Enter element");
        elementField.setPrefWidth(200);

        inputBox.getChildren().addAll(elementLabel, elementField);

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addToHashSet(elementField.getText()));

        Button containsButton = new Button("Contains");
        containsButton.setOnAction(e -> checkHashSetContains(elementField.getText()));

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> removeFromHashSet(elementField.getText()));

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> clearHashSet());

        Button sizeButton = new Button("Size");
        sizeButton.setOnAction(e -> showHashSetSize());

        Button iterateButton = new Button("Iterate");
        iterateButton.setOnAction(e -> iterateHashSet());

        buttonBox.getChildren().addAll(addButton, containsButton, removeButton, clearButton, sizeButton, iterateButton);

        // Output area
        TextArea setOutputArea = new TextArea();
        setOutputArea.setPrefRowCount(15);
        setOutputArea.setEditable(false);
        setOutputArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");

        // Store reference to output area for use in button handlers
        this.outputArea = setOutputArea;

        content.getChildren().addAll(title, inputBox, buttonBox, setOutputArea);

        return content;
    }

    private VBox createTestResults() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        // Title
        Label title = new Label("Test Results");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Run test button
        Button runTestButton = new Button("Run All Tests");
        runTestButton.setOnAction(e -> runAllTests());

        // Output area
        TextArea testOutputArea = new TextArea();
        testOutputArea.setPrefRowCount(20);
        testOutputArea.setEditable(false);
        testOutputArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");

        this.outputArea = testOutputArea;

        content.getChildren().addAll(title, runTestButton, testOutputArea);

        return content;
    }

    private void putToHashMap() {
        String key = keyField.getText().trim();
        String value = valueField.getText().trim();
        
        if (key.isEmpty()) {
            outputArea.appendText("Error: Key cannot be empty\n");
            return;
        }

        String oldValue = hashMap.put(key, value);
        if (oldValue != null) {
            outputArea.appendText("Updated: [" + key + ", " + value + "] (old value: " + oldValue + ")\n");
        } else {
            outputArea.appendText("Added: [" + key + ", " + value + "]\n");
        }
        
        outputArea.appendText("HashMap: " + hashMap + "\n\n");
        keyField.clear();
        valueField.clear();
    }

    private void getFromHashMap() {
        String key = keyField.getText().trim();
        
        if (key.isEmpty()) {
            outputArea.appendText("Error: Key cannot be empty\n");
            return;
        }

        String value = hashMap.get(key);
        if (value != null) {
            outputArea.appendText("Get [" + key + "]: " + value + "\n");
        } else {
            outputArea.appendText("Get [" + key + "]: null (key not found)\n");
        }
        
        outputArea.appendText("HashMap: " + hashMap + "\n\n");
    }

    private void removeFromHashMap() {
        String key = keyField.getText().trim();
        
        if (key.isEmpty()) {
            outputArea.appendText("Error: Key cannot be empty\n");
            return;
        }

        hashMap.remove(key);
        outputArea.appendText("Removed key: " + key + "\n");
        outputArea.appendText("HashMap: " + hashMap + "\n\n");
        keyField.clear();
        valueField.clear();
    }

    private void clearHashMap() {
        hashMap.clear();
        outputArea.appendText("HashMap cleared\n");
        outputArea.appendText("HashMap: " + hashMap + "\n\n");
    }

    private void showHashMapSize() {
        outputArea.appendText("HashMap size: " + hashMap.size() + "\n");
        outputArea.appendText("HashMap isEmpty: " + hashMap.isEmpty() + "\n\n");
    }

    private void addToHashSet(String element) {
        if (element == null || element.trim().isEmpty()) {
            outputArea.appendText("Error: Element cannot be empty\n");
            return;
        }

        boolean added = hashSet.add(element.trim());
        if (added) {
            outputArea.appendText("Added: " + element + "\n");
        } else {
            outputArea.appendText("Element already exists: " + element + "\n");
        }
        
        outputArea.appendText("HashSet: " + hashSet + "\n\n");
    }

    private void checkHashSetContains(String element) {
        if (element == null || element.trim().isEmpty()) {
            outputArea.appendText("Error: Element cannot be empty\n");
            return;
        }

        boolean contains = hashSet.contains(element.trim());
        outputArea.appendText("Contains [" + element + "]: " + contains + "\n");
        outputArea.appendText("HashSet: " + hashSet + "\n\n");
    }

    private void removeFromHashSet(String element) {
        if (element == null || element.trim().isEmpty()) {
            outputArea.appendText("Error: Element cannot be empty\n");
            return;
        }

        boolean removed = hashSet.remove(element.trim());
        if (removed) {
            outputArea.appendText("Removed: " + element + "\n");
        } else {
            outputArea.appendText("Element not found: " + element + "\n");
        }
        
        outputArea.appendText("HashSet: " + hashSet + "\n\n");
    }

    private void clearHashSet() {
        hashSet.clear();
        outputArea.appendText("HashSet cleared\n");
        outputArea.appendText("HashSet: " + hashSet + "\n\n");
    }

    private void showHashSetSize() {
        outputArea.appendText("HashSet size: " + hashSet.size() + "\n");
        outputArea.appendText("HashSet isEmpty: " + hashSet.isEmpty() + "\n\n");
    }

    private void iterateHashSet() {
        outputArea.appendText("Iterating HashSet:\n");
        int count = 0;
        for (String element : hashSet) {
            outputArea.appendText("  " + (++count) + ". " + element + "\n");
        }
        if (count == 0) {
            outputArea.appendText("  (empty set)\n");
        }
        outputArea.appendText("\n");
    }

    private void runAllTests() {
        outputArea.clear();
        outputArea.appendText("=== RUNNING ALL TESTS ===\n\n");

        // Test HashMap
        outputArea.appendText("--- HashMap Tests ---\n");
        testHashMap();

        outputArea.appendText("\n--- HashSet Tests ---\n");
        testHashSet();

        outputArea.appendText("\n=== ALL TESTS COMPLETED ===\n");
    }

    private void testHashMap() {
        MyMap<String, Integer> map = new MyHashMap<>();
        
        outputArea.appendText("Creating HashMap...\n");
        
        // Test put operations
        map.put("Perez", 30);
        map.put("Anderson", 31);
        map.put("Lewis", 29);
        map.put("Cook", 29);
        map.put("Perez", 65); // Update existing key
        
        outputArea.appendText("After adding entries: " + map + "\n");
        
        // Test get operations
        outputArea.appendText("Age for Lewis: " + map.get("Lewis") + "\n");
        outputArea.appendText("Age for Perez: " + map.get("Perez") + "\n");
        
        // Test contains operations
        outputArea.appendText("Contains key 'Perez': " + map.containsKey("Perez") + "\n");
        outputArea.appendText("Contains value 33: " + map.containsValue(33) + "\n");
        outputArea.appendText("Contains value 29: " + map.containsValue(29) + "\n");
        
        // Test size
        outputArea.appendText("Map size: " + map.size() + "\n");
        
        // Test remove
        map.remove("Smith"); // Remove non-existent key
        outputArea.appendText("After removing 'Smith': " + map + "\n");
        
        map.remove("Lewis");
        outputArea.appendText("After removing 'Lewis': " + map + "\n");
        
        // Test clear
        map.clear();
        outputArea.appendText("After clear: " + map + "\n");
        outputArea.appendText("Is empty: " + map.isEmpty() + "\n");
    }

    private void testHashSet() {
        Collection<String> set = new MyHashSet<>();
        
        outputArea.appendText("Creating HashSet...\n");
        
        // Test add operations
        set.add("Perez");
        set.add("Anderson");
        set.add("Lewis");
        set.add("Cook");
        set.add("Perez"); // Duplicate element
        
        outputArea.appendText("After adding elements: " + set + "\n");
        outputArea.appendText("Set size: " + set.size() + "\n");
        
        // Test contains
        outputArea.appendText("Contains 'Perez': " + set.contains("Perez") + "\n");
        outputArea.appendText("Contains 'Smith': " + set.contains("Smith") + "\n");
        
        // Test iteration
        outputArea.appendText("Iterating through set:\n");
        for (String s : set) {
            outputArea.appendText("  " + s.toUpperCase() + "\n");
        }
        
        // Test remove
        set.remove("Perez");
        outputArea.appendText("After removing 'Perez': " + set + "\n");
        
        // Test clear
        set.clear();
        outputArea.appendText("After clear: " + set + "\n");
        outputArea.appendText("Is empty: " + set.isEmpty() + "\n");
    }

    public static void main(String[] args) {
        launch(args);
    }
} 