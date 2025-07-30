package com.acu.javafx.setsandmaps;

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
import javafx.stage.FileChooser;
import java.io.*;
import java.util.*;

public class SetsAndMapsDemo extends Application {
    private TextArea outputArea;
    private Button testHashSetBtn, testLinkedHashSetBtn, testTreeSetBtn, 
                  testTreeSetWithComparatorBtn, performanceTestBtn, 
                  countKeywordsBtn, testMapBtn, countWordsBtn;
    private FileChooser fileChooser;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Sets and Maps Demo");

        // Create the main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        // Create title
        Label titleLabel = new Label("Sets and Maps Demonstration");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        root.getChildren().add(titleLabel);

        // Create button grid
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);
        buttonGrid.setAlignment(Pos.CENTER);

        // Create buttons
        testHashSetBtn = new Button("Test HashSet");
        testLinkedHashSetBtn = new Button("Test LinkedHashSet");
        testTreeSetBtn = new Button("Test TreeSet");
        testTreeSetWithComparatorBtn = new Button("Test TreeSet with Comparator");
        performanceTestBtn = new Button("Performance Test");
        countKeywordsBtn = new Button("Count Keywords");
        testMapBtn = new Button("Test Map");
        countWordsBtn = new Button("Count Word Occurrences");

        // Add buttons to grid
        buttonGrid.add(testHashSetBtn, 0, 0);
        buttonGrid.add(testLinkedHashSetBtn, 1, 0);
        buttonGrid.add(testTreeSetBtn, 0, 1);
        buttonGrid.add(testTreeSetWithComparatorBtn, 1, 1);
        buttonGrid.add(performanceTestBtn, 0, 2);
        buttonGrid.add(countKeywordsBtn, 1, 2);
        buttonGrid.add(testMapBtn, 0, 3);
        buttonGrid.add(countWordsBtn, 1, 3);

        root.getChildren().add(buttonGrid);

        // Create output area
        outputArea = new TextArea();
        outputArea.setPrefRowCount(20);
        outputArea.setPrefColumnCount(80);
        outputArea.setEditable(false);
        outputArea.setFont(Font.font("Monospaced", 12));
        outputArea.setWrapText(true);

        ScrollPane scrollPane = new ScrollPane(outputArea);
        scrollPane.setPrefViewportHeight(400);
        root.getChildren().add(scrollPane);

        // Set up event handlers
        setupEventHandlers();

        // Create file chooser
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select Java Source File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Java Files", "*.java")
        );

        Scene scene = new Scene(root, 800, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupEventHandlers() {
        testHashSetBtn.setOnAction(e -> runTestHashSet());
        testLinkedHashSetBtn.setOnAction(e -> runTestLinkedHashSet());
        testTreeSetBtn.setOnAction(e -> runTestTreeSet());
        testTreeSetWithComparatorBtn.setOnAction(e -> runTestTreeSetWithComparator());
        performanceTestBtn.setOnAction(e -> runPerformanceTest());
        countKeywordsBtn.setOnAction(e -> runCountKeywords());
        testMapBtn.setOnAction(e -> runTestMap());
        countWordsBtn.setOnAction(e -> runCountOccurrenceOfWords());
    }

    private void runTestHashSet() {
        outputArea.clear();
        outputArea.appendText("=== TestHashSet Demo ===\n\n");
        
        // Capture System.out
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        try {
            TestHashSet.main(new String[0]);
        } catch (Exception ex) {
            outputArea.appendText("Error: " + ex.getMessage() + "\n");
        }

        // Restore System.out and get output
        System.setOut(originalOut);
        String output = baos.toString();
        outputArea.appendText(output);
    }

    private void runTestLinkedHashSet() {
        outputArea.clear();
        outputArea.appendText("=== TestLinkedHashSet Demo ===\n\n");
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        try {
            TestLinkedHashSet.main(new String[0]);
        } catch (Exception ex) {
            outputArea.appendText("Error: " + ex.getMessage() + "\n");
        }

        System.setOut(originalOut);
        String output = baos.toString();
        outputArea.appendText(output);
    }

    private void runTestTreeSet() {
        outputArea.clear();
        outputArea.appendText("=== TestTreeSet Demo ===\n\n");
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        try {
            TestTreeSet.main(new String[0]);
        } catch (Exception ex) {
            outputArea.appendText("Error: " + ex.getMessage() + "\n");
        }

        System.setOut(originalOut);
        String output = baos.toString();
        outputArea.appendText(output);
    }

    private void runTestTreeSetWithComparator() {
        outputArea.clear();
        outputArea.appendText("=== TestTreeSetWithComparator Demo ===\n\n");
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        try {
            TestTreeSetWithComparator.main(new String[0]);
        } catch (Exception ex) {
            outputArea.appendText("Error: " + ex.getMessage() + "\n");
        }

        System.setOut(originalOut);
        String output = baos.toString();
        outputArea.appendText(output);
    }

    private void runPerformanceTest() {
        outputArea.clear();
        outputArea.appendText("=== SetListPerformanceTest Demo ===\n\n");
        outputArea.appendText("Running performance tests... This may take a moment.\n\n");
        
        // Run in background thread to avoid blocking UI
        new Thread(() -> {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            PrintStream ps = new PrintStream(baos);
            System.setOut(ps);

            try {
                SetListPerformanceTest.main(new String[0]);
            } catch (Exception ex) {
                outputArea.appendText("Error: " + ex.getMessage() + "\n");
            }

            System.setOut(originalOut);
            String output = baos.toString();
            
            Platform.runLater(() -> outputArea.appendText(output));
        }).start();
    }

    private void runCountKeywords() {
        outputArea.clear();
        outputArea.appendText("=== CountKeywords Demo ===\n\n");
        
        // Show file chooser
        File file = fileChooser.showOpenDialog(outputArea.getScene().getWindow());
        if (file != null) {
            outputArea.appendText("Selected file: " + file.getAbsolutePath() + "\n\n");
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            PrintStream ps = new PrintStream(baos);
            System.setOut(ps);

            try {
                // Create a temporary input stream with the filename
                String[] args = {file.getAbsolutePath()};
                // We need to modify the CountKeywords class to accept command line args
                // For now, let's just call the countKeywords method directly
                int count = CountKeywords.countKeywords(file);
                System.out.println("The number of keywords in " + file.getName() + " is " + count);
            } catch (Exception ex) {
                outputArea.appendText("Error: " + ex.getMessage() + "\n");
            }

            System.setOut(originalOut);
            String output = baos.toString();
            outputArea.appendText(output);
        }
    }

    private void runTestMap() {
        outputArea.clear();
        outputArea.appendText("=== TestMap Demo ===\n\n");
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        try {
            TestMap.main(new String[0]);
        } catch (Exception ex) {
            outputArea.appendText("Error: " + ex.getMessage() + "\n");
        }

        System.setOut(originalOut);
        String output = baos.toString();
        outputArea.appendText(output);
    }

    private void runCountOccurrenceOfWords() {
        outputArea.clear();
        outputArea.appendText("=== CountOccurrenceOfWords Demo ===\n\n");
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        try {
            CountOccurrenceOfWords.main(new String[0]);
        } catch (Exception ex) {
            outputArea.appendText("Error: " + ex.getMessage() + "\n");
        }

        System.setOut(originalOut);
        String output = baos.toString();
        outputArea.appendText(output);
    }

    public static void main(String[] args) {
        launch(args);
    }
} 