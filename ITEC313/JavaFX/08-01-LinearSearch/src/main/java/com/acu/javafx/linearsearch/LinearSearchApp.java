package com.acu.javafx.linearsearch;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * JavaFX application demonstrating linear search algorithm
 * Provides an interactive UI to visualize the search process
 */
public class LinearSearchApp extends Application {
    
    private TextField arrayInput;
    private TextField searchInput;
    private TextArea resultArea;
    private HBox arrayVisualization;
    private Button searchButton;
    private Button resetButton;
    private Label statusLabel;
    
    // Sample arrays for demonstration
    private final int[][] sampleArrays = {
        {3, 4, 1, 7, 5},
        {10, 20, 30, 40, 50, 60},
        {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
        {100, 200, 300, 400, 500}
    };
    
    private int currentArrayIndex = 2;
    private int[] currentArray;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Linear Search Algorithm Demo");
        
        // Initialize current array
        currentArray = sampleArrays[currentArrayIndex];
        
        // Create main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        
        // Title
        Label titleLabel = new Label("Linear Search Algorithm Visualization");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKBLUE);
        
        // Input section
        VBox inputSection = createInputSection();
        
        // Array visualization
        VBox visualizationSection = createVisualizationSection();
        
        // Control buttons
        HBox buttonSection = createButtonSection();
        
        // Result area
        VBox resultSection = createResultSection();
        
        // Add all sections to main layout
        root.getChildren().addAll(
            titleLabel,
            inputSection,
            visualizationSection,
            buttonSection,
            resultSection
        );
        
        // Create scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Initialize the display
        updateArrayDisplay();
    }
    
    private VBox createInputSection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER);
        
        Label sectionTitle = new Label("Input Parameters");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        // Array input
        HBox arrayInputBox = new HBox(10);
        arrayInputBox.setAlignment(Pos.CENTER);
        Label arrayLabel = new Label("Array:");
        arrayInput = new TextField();
        arrayInput.setPromptText("Enter comma-separated numbers (e.g., 3,4,1,7,5)");
        arrayInput.setPrefWidth(300);
        arrayInput.setText(arrayToString(currentArray));
        arrayInputBox.getChildren().addAll(arrayLabel, arrayInput);
        
        // Search value input
        HBox searchInputBox = new HBox(10);
        searchInputBox.setAlignment(Pos.CENTER);
        Label searchLabel = new Label("Search for:");
        searchInput = new TextField();
        searchInput.setPromptText("Enter number to search");
        searchInput.setPrefWidth(100);
        searchInput.setText("4");
        searchInputBox.getChildren().addAll(searchLabel, searchInput);
        
        section.getChildren().addAll(sectionTitle, arrayInputBox, searchInputBox);
        return section;
    }
    
    private VBox createVisualizationSection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER);
        
        Label sectionTitle = new Label("Array Visualization");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        arrayVisualization = new HBox(5);
        arrayVisualization.setAlignment(Pos.CENTER);
        
        section.getChildren().addAll(sectionTitle, arrayVisualization);
        return section;
    }
    
    private HBox createButtonSection() {
        HBox section = new HBox(20);
        section.setAlignment(Pos.CENTER);
        
        searchButton = new Button("Start Search");
        searchButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 16px;");
        searchButton.setOnAction(e -> performSearch());
        
        resetButton = new Button("Reset");
        resetButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 16px;");
        resetButton.setOnAction(e -> resetDisplay());
        
        Button nextArrayButton = new Button("Next Sample Array");
        nextArrayButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 16px;");
        nextArrayButton.setOnAction(e -> loadNextArray());
        
        section.getChildren().addAll(searchButton, resetButton, nextArrayButton);
        return section;
    }
    
    private VBox createResultSection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER);
        
        Label sectionTitle = new Label("Search Results");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        resultArea = new TextArea();
        resultArea.setPrefRowCount(8);
        resultArea.setPrefColumnCount(60);
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        
        statusLabel = new Label("Ready to search");
        statusLabel.setFont(Font.font("Arial", 12));
        statusLabel.setTextFill(Color.GRAY);
        
        section.getChildren().addAll(sectionTitle, resultArea, statusLabel);
        return section;
    }
    
    private void updateArrayDisplay() {
        arrayVisualization.getChildren().clear();
        
        for (int i = 0; i < currentArray.length; i++) {
            VBox elementBox = new VBox(5);
            elementBox.setAlignment(Pos.CENTER);
            
            Rectangle rect = new Rectangle(60, 60);
            rect.setFill(Color.LIGHTBLUE);
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(2);
            
            Text valueText = new Text(String.valueOf(currentArray[i]));
            valueText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            
            Text indexText = new Text("[" + i + "]");
            indexText.setFont(Font.font("Arial", 12));
            indexText.setFill(Color.GRAY);
            
            elementBox.getChildren().addAll(rect, valueText, indexText);
            arrayVisualization.getChildren().add(elementBox);
        }
    }
    
    private void performSearch() {
        try {
            // Parse search value
            int searchValue = Integer.parseInt(searchInput.getText().trim());
            
            // Parse array from input field
            int[] userArray = parseArrayInput(arrayInput.getText().trim());
            if (userArray == null) {
                resultArea.setText("Error: Please enter a valid array format (e.g., 0,1,2,3,4,5,6,7,8,9,10)");
                statusLabel.setText("Invalid array format");
                statusLabel.setTextFill(Color.RED);
                return;
            }
            
            // Update current array to user's input
            currentArray = userArray;
            updateArrayDisplay();
            
            // Update status
            statusLabel.setText("Searching for " + searchValue + "...");
            statusLabel.setTextFill(Color.ORANGE);
            
            // Disable search button during animation
            searchButton.setDisable(true);
            
            // Perform the search with visualization
            performSearchWithAnimation(searchValue);
            
        } catch (NumberFormatException ex) {
            resultArea.setText("Error: Please enter a valid number to search for.");
            statusLabel.setText("Invalid input");
            statusLabel.setTextFill(Color.RED);
        }
    }
    
    private void performSearchWithAnimation(int searchValue) {
        resultArea.clear();
        resultArea.appendText("Starting linear search for value: " + searchValue + "\n\n");
        
        // Reset array visualization
        updateArrayDisplay();
        
        // Start the recursive search
        performSearchStep(0, searchValue);
    }
    
    private void performSearchStep(int currentIndex, int searchValue) {
        if (currentIndex >= currentArray.length) {
            // Reached end of array - element not found
            resultArea.appendText("\n✗ Element " + searchValue + " not found in the array\n");
            resultArea.appendText("\nTime Complexity: O(n) - Linear time\n");
            resultArea.appendText("Space Complexity: O(1) - Constant space\n");
            
            statusLabel.setText("Element not found");
            statusLabel.setTextFill(Color.RED);
            searchButton.setDisable(false);
            return;
        }
        
        int currentValue = currentArray[currentIndex];
        
        // Highlight current element
        highlightElement(currentIndex, Color.YELLOW);
        
        resultArea.appendText("Checking index [" + currentIndex + "] = " + currentValue + "\n");
        
        if (currentValue == searchValue) {
            // Found!
            highlightElement(currentIndex, Color.GREEN);
            resultArea.appendText("✓ FOUND! Element " + searchValue + " is at index [" + currentIndex + "]\n");
            resultArea.appendText("\nTime Complexity: O(n) - Linear time\n");
            resultArea.appendText("Space Complexity: O(1) - Constant space\n");
            
            statusLabel.setText("Element found at index " + currentIndex);
            statusLabel.setTextFill(Color.GREEN);
            searchButton.setDisable(false);
            // Stop here - don't continue searching
        } else {
            resultArea.appendText("✗ Not found at this index\n");
            
            // Continue to next element after a delay
            PauseTransition nextPause = new PauseTransition(Duration.millis(500));
            nextPause.setOnFinished(event -> {
                // Reset highlight for current element
                highlightElement(currentIndex, Color.LIGHTBLUE);
                
                // Continue to next element
                performSearchStep(currentIndex + 1, searchValue);
            });
            nextPause.play();
        }
    }
    
    private void highlightElement(int index, Color color) {
        if (index >= 0 && index < arrayVisualization.getChildren().size()) {
            VBox elementBox = (VBox) arrayVisualization.getChildren().get(index);
            Rectangle rect = (Rectangle) elementBox.getChildren().get(0);
            rect.setFill(color);
        }
    }
    
    private void resetDisplay() {
        // Reset array visualization
        updateArrayDisplay();
        
        // Clear result area
        resultArea.clear();
        
        // Reset status
        statusLabel.setText("Ready to search");
        statusLabel.setTextFill(Color.GRAY);
        
        // Enable search button
        searchButton.setDisable(false);
        
        // Reset inputs
        arrayInput.setText(arrayToString(currentArray));
        searchInput.setText("4");
    }
    
    private void loadNextArray() {
        currentArrayIndex = (currentArrayIndex + 1) % sampleArrays.length;
        currentArray = sampleArrays[currentArrayIndex];
        
        // Update display
        arrayInput.setText(arrayToString(currentArray));
        updateArrayDisplay();
        
        // Reset result area
        resultArea.clear();
        statusLabel.setText("Ready to search");
        statusLabel.setTextFill(Color.GRAY);
        searchButton.setDisable(false);
    }
    
    private String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(array[i]);
        }
        return sb.toString();
    }
    
    private int[] parseArrayInput(String input) {
        try {
            if (input == null || input.trim().isEmpty()) {
                return null;
            }
            
            String[] parts = input.split(",");
            int[] array = new int[parts.length];
            
            for (int i = 0; i < parts.length; i++) {
                array[i] = Integer.parseInt(parts[i].trim());
            }
            
            return array;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
} 