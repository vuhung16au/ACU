package com.acu.javafx.anagram;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Anagram Checker Demo - JavaFX Application
 * 
 * This application demonstrates recursive anagram checking using JavaFX.
 * Two words are anagrams of each other if they contain the same letters
 * that are arranged in different orders.
 * 
 * Features:
 * - Text fields for input words
 * - Check Anagram button
 * - Result display
 * - Clear button
 * - Exit button
 * 
 * @author ACU JavaFX Team
 * @version 1.0.0
 */
public class AnagramCheckerDemo extends Application {
    
    // UI Components
    private TextField word1Field;
    private TextField word2Field;
    private Button checkAnagramBtn;
    private Button clearBtn;
    private Button exitBtn;
    private Label resultLabel;
    private TextArea outputArea;
    
    // Anagram checker utility
    private AnagramChecker anagramChecker;
    
    @Override
    public void start(Stage primaryStage) {
        anagramChecker = new AnagramChecker();
        
        // Create the main layout
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        // Create title
        Label titleLabel = new Label("Anagram Checker");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create description
        Label descriptionLabel = new Label(
            "Enter two words to check if they are anagrams of each other.\n" +
            "Anagrams contain the same letters arranged in different orders."
        );
        descriptionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
        descriptionLabel.setAlignment(Pos.CENTER);
        descriptionLabel.setWrapText(true);
        
        // Create input section
        VBox inputSection = createInputSection();
        
        // Create button section
        HBox buttonSection = createButtonSection();
        
        // Create result section
        VBox resultSection = createResultSection();
        
        // Create output area for detailed information
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(8);
        outputArea.setStyle("-fx-font-family: 'Monaco', 'Consolas', monospace; -fx-font-size: 12px;");
        outputArea.setPromptText("Detailed analysis will appear here...");
        
        // Add all components to root
        root.getChildren().addAll(
            titleLabel,
            descriptionLabel,
            inputSection,
            buttonSection,
            resultSection,
            outputArea
        );
        
        // Create scene
        Scene scene = new Scene(root, 600, 700);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        primaryStage.setTitle("Anagram Checker Demo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        // Add initial message
        appendOutput("Anagram Checker Ready!\n" +
                    "Enter two words and click 'Check Anagram' to see if they are anagrams.\n" +
                    "==========================================\n");
    }
    
    /**
     * Creates the input section with text fields for the two words
     */
    private VBox createInputSection() {
        VBox inputSection = new VBox(10);
        inputSection.setAlignment(Pos.CENTER);
        
        // Word 1 input
        Label word1Label = new Label("Word 1:");
        word1Label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        word1Field = new TextField();
        word1Field.setPromptText("Enter first word...");
        word1Field.setPrefWidth(300);
        word1Field.setStyle("-fx-font-size: 14px;");
        
        // Word 2 input
        Label word2Label = new Label("Word 2:");
        word2Label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        word2Field = new TextField();
        word2Field.setPromptText("Enter second word...");
        word2Field.setPrefWidth(300);
        word2Field.setStyle("-fx-font-size: 14px;");
        
        // Add event handlers for Enter key
        word1Field.setOnAction(e -> word2Field.requestFocus());
        word2Field.setOnAction(e -> checkAnagram());
        
        inputSection.getChildren().addAll(word1Label, word1Field, word2Label, word2Field);
        return inputSection;
    }
    
    /**
     * Creates the button section with all action buttons
     */
    private HBox createButtonSection() {
        HBox buttonSection = new HBox(15);
        buttonSection.setAlignment(Pos.CENTER);
        
        // Check Anagram button
        checkAnagramBtn = new Button("Check Anagram");
        checkAnagramBtn.setPrefWidth(120);
        checkAnagramBtn.setPrefHeight(40);
        checkAnagramBtn.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #3498db; -fx-text-fill: white;");
        checkAnagramBtn.setOnAction(e -> checkAnagram());
        
        // Clear button
        clearBtn = new Button("Clear");
        clearBtn.setPrefWidth(80);
        clearBtn.setPrefHeight(40);
        clearBtn.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #95a5a6; -fx-text-fill: white;");
        clearBtn.setOnAction(e -> clearAll());
        
        // Exit button
        exitBtn = new Button("Exit");
        exitBtn.setPrefWidth(80);
        exitBtn.setPrefHeight(40);
        exitBtn.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #e74c3c; -fx-text-fill: white;");
        exitBtn.setOnAction(e -> Platform.exit());
        
        buttonSection.getChildren().addAll(checkAnagramBtn, clearBtn, exitBtn);
        return buttonSection;
    }
    
    /**
     * Creates the result section to display the anagram check result
     */
    private VBox createResultSection() {
        VBox resultSection = new VBox(10);
        resultSection.setAlignment(Pos.CENTER);
        
        Label resultTitleLabel = new Label("Result:");
        resultTitleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        resultLabel = new Label("Enter words to check...");
        resultLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #7f8c8d;");
        resultLabel.setAlignment(Pos.CENTER);
        resultLabel.setWrapText(true);
        resultLabel.setPrefWidth(400);
        
        resultSection.getChildren().addAll(resultTitleLabel, resultLabel);
        return resultSection;
    }
    
    /**
     * Checks if the two input words are anagrams
     */
    private void checkAnagram() {
        String word1 = word1Field.getText().trim();
        String word2 = word2Field.getText().trim();
        
        // Validate input
        if (word1.isEmpty() || word2.isEmpty()) {
            showResult("Please enter both words!", false);
            appendOutput("Error: Both words must be provided.\n");
            return;
        }
        
        // Check for anagrams
        boolean areAnagrams = anagramChecker.areAnagrams(word1, word2);
        
        // Display result
        if (areAnagrams) {
            showResult("✓ YES! These words are anagrams!", true);
            appendOutput(String.format("✓ '%s' and '%s' are anagrams!\n", word1, word2));
        } else {
            showResult("✗ NO! These words are not anagrams.", false);
            appendOutput(String.format("✗ '%s' and '%s' are not anagrams.\n", word1, word2));
        }
        
        // Show detailed analysis
        showDetailedAnalysis(word1, word2);
    }
    
    /**
     * Shows the result with appropriate styling
     */
    private void showResult(String message, boolean isAnagram) {
        resultLabel.setText(message);
        if (isAnagram) {
            resultLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");
        } else {
            resultLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
        }
    }
    
    /**
     * Shows detailed analysis of the anagram check
     */
    private void showDetailedAnalysis(String word1, String word2) {
        appendOutput("\n--- Detailed Analysis ---\n");
        appendOutput(String.format("Word 1: '%s' (length: %d)\n", word1, word1.length()));
        appendOutput(String.format("Word 2: '%s' (length: %d)\n", word2, word2.length()));
        
        // Show character frequency analysis
        String normalized1 = anagramChecker.normalizeWord(word1);
        String normalized2 = anagramChecker.normalizeWord(word2);
        
        appendOutput(String.format("Normalized Word 1: '%s'\n", normalized1));
        appendOutput(String.format("Normalized Word 2: '%s'\n", normalized2));
        
        // Show character counts
        appendOutput("\nCharacter frequency analysis:\n");
        appendOutput(String.format("Word 1 characters: %s\n", anagramChecker.getCharacterCounts(normalized1)));
        appendOutput(String.format("Word 2 characters: %s\n", anagramChecker.getCharacterCounts(normalized2)));
        
        appendOutput("==========================================\n");
    }
    
    /**
     * Clears all input fields and results
     */
    private void clearAll() {
        word1Field.clear();
        word2Field.clear();
        resultLabel.setText("Enter words to check...");
        resultLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #7f8c8d;");
        outputArea.clear();
        word1Field.requestFocus();
        
        appendOutput("Cleared all fields. Ready for new input.\n");
        appendOutput("==========================================\n");
    }
    
    /**
     * Appends text to the output area
     */
    private void appendOutput(String text) {
        Platform.runLater(() -> {
            outputArea.appendText(text);
            outputArea.setScrollTop(Double.MAX_VALUE);
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
