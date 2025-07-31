package com.acu.javafx.selectionsort;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * JavaFX application that visualizes the Selection Sort algorithm.
 * This application demonstrates how selection sort works by showing
 * bars that represent array elements being sorted in real-time.
 * 
 * Features:
 * - Visual representation of array elements as bars
 * - Step-by-step animation of the sorting process
 * - Color coding to show current operations
 * - Controls to generate new arrays and start sorting
 * - Speed control for animation
 * 
 * @author ACU
 * @version 1.0.0
 */
public class SelectionSortApp extends Application {
    
    // Constants for the visualization
    private static final int ARRAY_SIZE = 20;
    private static final int BAR_WIDTH = 25;
    private static final int BAR_SPACING = 5;
    private static final int MAX_HEIGHT = 300;
    private static final int MIN_VALUE = 5;
    private static final int MAX_VALUE = 100;
    
    // Colors for different states
    private static final Color DEFAULT_COLOR = Color.SKYBLUE;
    private static final Color CURRENT_COLOR = Color.DARKBLUE;
    private static final Color COMPARING_COLOR = Color.RED;
    private static final Color MINIMUM_COLOR = Color.ORANGE;
    private static final Color SORTED_COLOR = Color.LIGHTGREEN;
    
    // UI Components
    private Pane visualizationPane;
    private Button generateButton;
    private Button sortButton;
    private Slider speedSlider;
    private Label statusLabel;
    private List<BarElement> bars;
    
    // Animation control
    private boolean isAnimating = false;
    private double animationSpeed = 500; // milliseconds
    
    /**
     * Inner class representing a visual bar element
     */
    private static class BarElement {
        private Rectangle rectangle;
        private Text valueText;
        private int value;
        
        public BarElement(int value, double x, double y) {
            this.value = value;
            
            // Create rectangle for the bar
            double height = (value / (double) MAX_VALUE) * MAX_HEIGHT;
            this.rectangle = new Rectangle(BAR_WIDTH, height);
            this.rectangle.setFill(DEFAULT_COLOR);
            this.rectangle.setStroke(Color.BLACK);
            this.rectangle.setStrokeWidth(1);
            this.rectangle.setX(x);
            this.rectangle.setY(y - height);
            
            // Create text for the value
            this.valueText = new Text(x + BAR_WIDTH/2 - 10, y - height - 5, String.valueOf(value));
            this.valueText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            this.valueText.setFill(Color.BLACK);
        }
        
        public Rectangle getRectangle() { return rectangle; }
        public Text getValueText() { return valueText; }
        public int getValue() { return value; }
        public void setValue(int value) { this.value = value; }
        
        public void setColor(Color color) {
            rectangle.setFill(color);
        }
        
        public void updatePosition(double x, double y) {
            double height = (value / (double) MAX_VALUE) * MAX_HEIGHT;
            rectangle.setX(x);
            rectangle.setY(y - height);
            rectangle.setHeight(height);
            valueText.setX(x + BAR_WIDTH/2 - 10);
            valueText.setY(y - height - 5);
            valueText.setText(String.valueOf(value));
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Selection Sort Visualizer");
        
        // Initialize components
        initializeComponents();
        
        // Create layout
        BorderPane root = createLayout();
        
        // Generate initial array
        generateNewArray();
        
        // Create and show scene
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    /**
     * Initialize UI components
     */
    private void initializeComponents() {
        // Visualization pane
        visualizationPane = new Pane();
        visualizationPane.setPrefSize(700, 400);
        visualizationPane.setStyle("-fx-background-color: #FFF5E6; -fx-border-color: #6F459E; -fx-border-width: 2;");
        
        // Buttons
        generateButton = new Button("Generate New Array");
        generateButton.setOnAction(e -> generateNewArray());
        generateButton.setStyle("-fx-background-color: #6F459E; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        
        sortButton = new Button("Start Selection Sort");
        sortButton.setOnAction(e -> startSelectionSort());
        sortButton.setStyle("-fx-background-color: #6F459E; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        
        // Speed control
        speedSlider = new Slider(100, 1000, 500);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setMajorTickUnit(200);
        speedSlider.setBlockIncrement(100);
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            animationSpeed = newVal.doubleValue();
        });
        
        // Status label
        statusLabel = new Label("Ready to sort");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        // Initialize bars list
        bars = new ArrayList<>();
    }
    
    /**
     * Create the main layout
     */
    private BorderPane createLayout() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        
        // Title
        Label titleLabel = new Label("Selection Sort Visualizer");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #6F459E;");
        
        VBox topBox = new VBox(10);
        topBox.setAlignment(Pos.CENTER);
        topBox.getChildren().add(titleLabel);
        
        root.setTop(topBox);
        
        // Center - visualization
        root.setCenter(visualizationPane);
        
        // Bottom - controls
        Label speedLabel = new Label("Animation Speed (ms):");
        speedLabel.setStyle("-fx-font-weight: bold;");
        
        HBox speedBox = new HBox(10);
        speedBox.setAlignment(Pos.CENTER);
        speedBox.getChildren().addAll(speedLabel, speedSlider);
        
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(generateButton, sortButton);
        
        VBox bottomBox = new VBox(15);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.getChildren().addAll(statusLabel, speedBox, buttonBox);
        
        root.setBottom(bottomBox);
        
        return root;
    }
    
    /**
     * Generate a new random array and display it
     */
    private void generateNewArray() {
        if (isAnimating) return;
        
        // Clear existing bars
        visualizationPane.getChildren().clear();
        bars.clear();
        
        // Generate random values
        Random random = new Random();
        double startX = 50;
        double baseY = visualizationPane.getPrefHeight() - 50;
        
        for (int i = 0; i < ARRAY_SIZE; i++) {
            int value = random.nextInt(MAX_VALUE - MIN_VALUE + 1) + MIN_VALUE;
            double x = startX + i * (BAR_WIDTH + BAR_SPACING);
            
            BarElement bar = new BarElement(value, x, baseY);
            bars.add(bar);
            
            visualizationPane.getChildren().addAll(bar.getRectangle(), bar.getValueText());
        }
        
        statusLabel.setText("New array generated. Ready to sort.");
        sortButton.setDisable(false);
    }
    
    /**
     * Start the selection sort animation
     */
    private void startSelectionSort() {
        if (isAnimating || bars.isEmpty()) return;
        
        isAnimating = true;
        generateButton.setDisable(true);
        sortButton.setDisable(true);
        
        statusLabel.setText("Sorting in progress...");
        
        // Start the selection sort algorithm with animation
        selectionSortWithAnimation(0, 0, -1);
    }
    
    /**
     * Recursive method to perform selection sort with animation
     */
    private void selectionSortWithAnimation(int currentIndex, int compareIndex, int minIndex) {
        if (currentIndex >= bars.size()) {
            // Sorting complete
            finishSorting();
            return;
        }
        
        if (compareIndex == currentIndex) {
            // Start of a new iteration
            minIndex = currentIndex;
            
            // Color the current element
            bars.get(currentIndex).setColor(CURRENT_COLOR);
            statusLabel.setText("Finding minimum element starting from index " + currentIndex);
            
            // Start comparing from the next element
            PauseTransition pause = new PauseTransition(Duration.millis(animationSpeed));
            pause.setOnFinished(e -> selectionSortWithAnimation(currentIndex, currentIndex + 1, minIndex));
            pause.play();
            return;
        }
        
        if (compareIndex >= bars.size()) {
            // Finished comparing for this iteration, perform swap if needed
            if (minIndex != currentIndex) {
                swapElements(currentIndex, minIndex);
            }
            
            // Mark current element as sorted
            bars.get(currentIndex).setColor(SORTED_COLOR);
            
            // Reset colors for next iteration
            for (int i = currentIndex + 1; i < bars.size(); i++) {
                bars.get(i).setColor(DEFAULT_COLOR);
            }
            
            PauseTransition pause = new PauseTransition(Duration.millis(animationSpeed));
            pause.setOnFinished(e -> selectionSortWithAnimation(currentIndex + 1, currentIndex + 1, -1));
            pause.play();
            return;
        }
        
        // Compare current element with minimum
        bars.get(compareIndex).setColor(COMPARING_COLOR);
        
        PauseTransition pause = new PauseTransition(Duration.millis(animationSpeed));
        pause.setOnFinished(e -> {
            if (bars.get(compareIndex).getValue() < bars.get(minIndex).getValue()) {
                // Found new minimum
                if (minIndex != currentIndex) {
                    bars.get(minIndex).setColor(DEFAULT_COLOR);
                }
                minIndex = compareIndex;
                bars.get(minIndex).setColor(MINIMUM_COLOR);
                statusLabel.setText("New minimum found at index " + minIndex + " (value: " + bars.get(minIndex).getValue() + ")");
            } else {
                bars.get(compareIndex).setColor(DEFAULT_COLOR);
            }
            
            // Continue with next comparison
            selectionSortWithAnimation(currentIndex, compareIndex + 1, minIndex);
        });
        pause.play();
    }
    
    /**
     * Swap two elements in the visualization
     */
    private void swapElements(int index1, int index2) {
        BarElement bar1 = bars.get(index1);
        BarElement bar2 = bars.get(index2);
        
        // Swap values
        int tempValue = bar1.getValue();
        bar1.setValue(bar2.getValue());
        bar2.setValue(tempValue);
        
        // Update visual positions
        double baseY = visualizationPane.getPrefHeight() - 50;
        double startX = 50;
        
        double x1 = startX + index1 * (BAR_WIDTH + BAR_SPACING);
        double x2 = startX + index2 * (BAR_WIDTH + BAR_SPACING);
        
        bar1.updatePosition(x1, baseY);
        bar2.updatePosition(x2, baseY);
        
        statusLabel.setText("Swapped elements at indices " + index1 + " and " + index2);
    }
    
    /**
     * Complete the sorting process
     */
    private void finishSorting() {
        // Color all bars as sorted
        for (BarElement bar : bars) {
            bar.setColor(SORTED_COLOR);
        }
        
        statusLabel.setText("Sorting completed! Array is now sorted.");
        
        isAnimating = false;
        generateButton.setDisable(false);
        sortButton.setDisable(false);
    }
    
    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
