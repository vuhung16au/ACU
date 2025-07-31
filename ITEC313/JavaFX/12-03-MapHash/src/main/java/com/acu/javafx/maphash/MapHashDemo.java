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
    private TextArea hashMapOutputArea;
    private TextArea hashSetOutputArea;
    private TextArea testOutputArea;
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

        // Output area with title
        Label outputLabel = new Label("Output:");
        outputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        hashMapOutputArea = new TextArea();
        hashMapOutputArea.setPrefRowCount(30);
        hashMapOutputArea.setEditable(false);
        hashMapOutputArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");

        VBox outputBox = new VBox(3);
        outputBox.setAlignment(Pos.TOP_LEFT);
        outputBox.getChildren().addAll(outputLabel, hashMapOutputArea);

        content.getChildren().addAll(title, inputBox, buttonBox, outputBox);

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

        // Output area with title
        Label outputLabel = new Label("Output:");
        outputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        hashSetOutputArea = new TextArea();
        hashSetOutputArea.setPrefRowCount(15);
        hashSetOutputArea.setEditable(false);
        hashSetOutputArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");

        VBox outputBox = new VBox(3);
        outputBox.setAlignment(Pos.TOP_LEFT);
        outputBox.getChildren().addAll(outputLabel, hashSetOutputArea);

        content.getChildren().addAll(title, inputBox, buttonBox, outputBox);

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
        testOutputArea = new TextArea();
        testOutputArea.setPrefRowCount(20);
        testOutputArea.setEditable(false);
        testOutputArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");

        content.getChildren().addAll(title, runTestButton, testOutputArea);

        return content;
    }

    private void putToHashMap() {
        String key = keyField.getText().trim();
        String value = valueField.getText().trim();

        if (key.isEmpty()) {
            hashMapOutputArea.appendText("Error: Key cannot be empty\n");
            return;
        }

        String oldValue = hashMap.put(key, value);
        String timestamp = java.time.LocalDateTime.now().withNano(0).toString();
        if (oldValue != null) {
            hashMapOutputArea.appendText(timestamp + ": Updated: Key = \"" + key + "\", Value = \"" + value + "\" (old value: " + oldValue + ")\n");
        } else {
            hashMapOutputArea.appendText(timestamp + ": Inserted: Key = \"" + key + "\", Value = \"" + value + "\"\n");
        }

        hashMapOutputArea.appendText("HashMap: " + hashMap + "\n\n");
        keyField.clear();
        valueField.clear();
    }

    private void getFromHashMap() {
        String key = keyField.getText().trim();

        if (key.isEmpty()) {
            hashMapOutputArea.appendText("Error: Key cannot be empty\n");
            return;
        }

        String value = hashMap.get(key);
        if (value != null) {
            hashMapOutputArea.appendText("Get [" + key + "]: " + value + "\n");
        } else {
            hashMapOutputArea.appendText("Get [" + key + "]: null (key not found)\n");
        }

        hashMapOutputArea.appendText("HashMap: " + hashMap + "\n\n");
    }

    private void removeFromHashMap() {
        String key = keyField.getText().trim();

        if (key.isEmpty()) {
            hashMapOutputArea.appendText("Error: Key cannot be empty\n");
            return;
        }

        hashMap.remove(key);
        hashMapOutputArea.appendText("Removed key: " + key + "\n");
        hashMapOutputArea.appendText("HashMap: " + hashMap + "\n\n");
        keyField.clear();
        valueField.clear();
    }

    private void clearHashMap() {
        hashMap.clear();
        hashMapOutputArea.appendText("HashMap cleared\n");
        hashMapOutputArea.appendText("HashMap: " + hashMap + "\n\n");
    }

    private void showHashMapSize() {
        hashMapOutputArea.appendText("HashMap size: " + hashMap.size() + "\n");
        hashMapOutputArea.appendText("HashMap isEmpty: " + hashMap.isEmpty() + "\n\n");
    }

    private void addToHashSet(String element) {
        if (element == null || element.trim().isEmpty()) {
            hashSetOutputArea.appendText("Error: Element cannot be empty\n");
            return;
        }

        boolean added = hashSet.add(element.trim());
        if (added) {
            hashSetOutputArea.appendText("Added: " + element + "\n");
        } else {
            hashSetOutputArea.appendText("Element already exists: " + element + "\n");
        }

        hashSetOutputArea.appendText("HashSet: " + hashSet + "\n\n");
    }

    private void checkHashSetContains(String element) {
        if (element == null || element.trim().isEmpty()) {
            hashSetOutputArea.appendText("Error: Element cannot be empty\n");
            return;
        }

        boolean contains = hashSet.contains(element.trim());
        hashSetOutputArea.appendText("Contains [" + element + "]: " + contains + "\n");
        hashSetOutputArea.appendText("HashSet: " + hashSet + "\n\n");
    }

    private void removeFromHashSet(String element) {
        if (element == null || element.trim().isEmpty()) {
            hashSetOutputArea.appendText("Error: Element cannot be empty\n");
            return;
        }

        boolean removed = hashSet.remove(element.trim());
        if (removed) {
            hashSetOutputArea.appendText("Removed: " + element + "\n");
        } else {
            hashSetOutputArea.appendText("Element not found: " + element + "\n");
        }

        hashSetOutputArea.appendText("HashSet: " + hashSet + "\n\n");
    }

    private void clearHashSet() {
        hashSet.clear();
        hashSetOutputArea.appendText("HashSet cleared\n");
        hashSetOutputArea.appendText("HashSet: " + hashSet + "\n\n");
    }

    private void showHashSetSize() {
        hashSetOutputArea.appendText("HashSet size: " + hashSet.size() + "\n");
        hashSetOutputArea.appendText("HashSet isEmpty: " + hashSet.isEmpty() + "\n\n");
    }

    private void iterateHashSet() {
        hashSetOutputArea.appendText("Iterating HashSet:\n");
        int count = 0;
        for (String element : hashSet) {
            hashSetOutputArea.appendText("  " + (++count) + ". " + element + "\n");
        }
        if (count == 0) {
            hashSetOutputArea.appendText("  (empty set)\n");
        }
        hashSetOutputArea.appendText("\n");
    }

    private void runAllTests() {
        testOutputArea.clear();
        testOutputArea.appendText("=== RUNNING ALL TESTS ===\n\n");

        // Test HashMap
        testOutputArea.appendText("--- HashMap Tests ---\n");
        testHashMap();

        testOutputArea.appendText("\n--- HashSet Tests ---\n");
        testHashSet();

        testOutputArea.appendText("\n=== ALL TESTS COMPLETED ===\n");
    }

    private void testHashMap() {
        MyMap<String, Integer> map = new MyHashMap<>();

        testOutputArea.appendText("Creating HashMap...\n");

        // Test put operations
        map.put("Perez", 30);
        map.put("Anderson", 31);
        map.put("Lewis", 29);
        map.put("Cook", 29);
        map.put("Perez", 65); // Update existing key

        testOutputArea.appendText("After adding entries: " + map + "\n");

        // Test get operations
        testOutputArea.appendText("Age for Lewis: " + map.get("Lewis") + "\n");
        testOutputArea.appendText("Age for Perez: " + map.get("Perez") + "\n");

        // Test contains operations
        testOutputArea.appendText("Contains key 'Perez': " + map.containsKey("Perez") + "\n");
        testOutputArea.appendText("Contains value 33: " + map.containsValue(33) + "\n");
        testOutputArea.appendText("Contains value 29: " + map.containsValue(29) + "\n");

        // Test size
        testOutputArea.appendText("Map size: " + map.size() + "\n");

        // Test remove
        map.remove("Smith"); // Remove non-existent key
        testOutputArea.appendText("After removing 'Smith': " + map + "\n");

        map.remove("Lewis");
        testOutputArea.appendText("After removing 'Lewis': " + map + "\n");

        // Test clear
        map.clear();
        testOutputArea.appendText("After clear: " + map + "\n");
        testOutputArea.appendText("Is empty: " + map.isEmpty() + "\n");
    }

    private void testHashSet() {
        Collection<String> set = new MyHashSet<>();

        testOutputArea.appendText("Creating HashSet...\n");

        // Test add operations
        set.add("Perez");
        set.add("Anderson");
        set.add("Lewis");
        set.add("Cook");
        set.add("Perez"); // Duplicate element

        testOutputArea.appendText("After adding elements: " + set + "\n");
        testOutputArea.appendText("Set size: " + set.size() + "\n");

        // Test contains
        testOutputArea.appendText("Contains 'Perez': " + set.contains("Perez") + "\n");
        testOutputArea.appendText("Contains 'Smith': " + set.contains("Smith") + "\n");

        // Test iteration
        testOutputArea.appendText("Iterating through set:\n");
        for (String s : set) {
            testOutputArea.appendText("  " + s.toUpperCase() + "\n");
        }

        // Test remove
        set.remove("Perez");
        testOutputArea.appendText("After removing 'Perez': " + set + "\n");

        // Test clear
        set.clear();
        testOutputArea.appendText("After clear: " + set + "\n");
        testOutputArea.appendText("Is empty: " + set.isEmpty() + "\n");
    }

    public static void main(String[] args) {
        launch(args);
    }
} 