package com.acu.javafx.selectionsort;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
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
    private Button stopButton;
    private Button resetButton;
    private Slider speedSlider;
    private Label statusLabel;
    private TextArea logTextArea;
    private List<BarElement> bars;
    
    // Animation control
    private boolean isAnimating = false;
    private boolean shouldStop = false;
    private double animationSpeed = 1000; // milliseconds (1 second default)
    private PauseTransition currentPause;
    
    // Selection sort state variables
    private int sortCurrentIndex = 0;
    private int sortCompareIndex = 0;
    private int sortMinIndex = -1;
    
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
        Scene scene = new Scene(root, 1200, 600);
        // Apply styles if available
        try {
            String css = getClass().getResource("/styles.css").toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e) {
            // CSS file not found, continue without styling
            System.out.println("CSS file not found, using default styling");
        }
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
        
        stopButton = new Button("Stop");
        stopButton.setOnAction(e -> stopSorting());
        stopButton.setStyle("-fx-background-color: #DC3545; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        stopButton.setDisable(true);
        
        resetButton = new Button("Reset");
        resetButton.setOnAction(e -> resetVisualization());
        resetButton.setStyle("-fx-background-color: #28A745; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        
        // Speed control (0.5s to 2s)
        speedSlider = new Slider(500, 2000, 1000);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setMajorTickUnit(500);
        speedSlider.setBlockIncrement(250);
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            animationSpeed = newVal.doubleValue();
        });
        
        // Status label
        statusLabel = new Label("Ready to sort");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        // Log text area
        logTextArea = new TextArea();
        logTextArea.setPrefRowCount(20);
        logTextArea.setPrefColumnCount(30);
        logTextArea.setEditable(false);
        logTextArea.setWrapText(true);
        logTextArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
        logTextArea.setText("Selection Sort Log:\n" +
                           "===================\n" +
                           "Ready to start sorting...\n");
        
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
        
        // Center - visualization and log
        HBox centerBox = new HBox(20);
        centerBox.setAlignment(Pos.CENTER);
        
        // Left side - visualization
        VBox visualizationBox = new VBox(10);
        visualizationBox.setAlignment(Pos.CENTER);
        visualizationBox.getChildren().add(visualizationPane);
        
        // Right side - log area
        VBox logBox = new VBox(10);
        logBox.setAlignment(Pos.TOP_CENTER);
        
        Label logLabel = new Label("Sorting Steps Log");
        logLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #6F459E;");
        
        ScrollPane logScrollPane = new ScrollPane(logTextArea);
        logScrollPane.setPrefSize(350, 400);
        logScrollPane.setFitToWidth(true);
        logScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        logScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        logBox.getChildren().addAll(logLabel, logScrollPane);
        
        centerBox.getChildren().addAll(visualizationBox, logBox);
        root.setCenter(centerBox);
        
        // Bottom - controls
        Label speedLabel = new Label("Animation Speed (0.5s - 2s):");
        speedLabel.setStyle("-fx-font-weight: bold;");
        
        HBox speedBox = new HBox(10);
        speedBox.setAlignment(Pos.CENTER);
        speedBox.getChildren().addAll(speedLabel, speedSlider);
        
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(generateButton, sortButton, stopButton, resetButton);
        
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
        stopButton.setDisable(true);
        
        // Clear and update log
        logTextArea.clear();
        logTextArea.appendText("Selection Sort Log:\n");
        logTextArea.appendText("===================\n");
        logTextArea.appendText("New array generated with " + ARRAY_SIZE + " elements.\n");
        logTextArea.appendText("Array values: ");
        for (int i = 0; i < bars.size(); i++) {
            logTextArea.appendText(bars.get(i).getValue() + (i < bars.size() - 1 ? ", " : "\n"));
        }
        logTextArea.appendText("Ready to start sorting...\n\n");
    }
    
    /**
     * Start the selection sort animation
     */
    private void startSelectionSort() {
        if (isAnimating || bars.isEmpty()) return;
        
        isAnimating = true;
        shouldStop = false;
        generateButton.setDisable(true);
        sortButton.setDisable(true);
        stopButton.setDisable(false);
        
        statusLabel.setText("Sorting in progress...");
        logTextArea.appendText("Starting Selection Sort...\n");
        logTextArea.appendText("=======================\n\n");
        
        // Initialize sorting state
        sortCurrentIndex = 0;
        sortCompareIndex = 0;
        sortMinIndex = -1;
        
        // Start the selection sort algorithm with animation
        performNextSortStep();
    }
    
    /**
     * Perform the next step in the selection sort animation
     */
    private void performNextSortStep() {
        if (shouldStop || sortCurrentIndex >= bars.size()) {
            if (shouldStop) {
                statusLabel.setText("Sorting stopped by user.");
                logTextArea.appendText("Sorting stopped by user.\n");
            } else {
                // Sorting complete
                finishSorting();
            }
            return;
        }
        
        if (sortCompareIndex == sortCurrentIndex) {
            // Start of a new iteration
            sortMinIndex = sortCurrentIndex;
            
            // Color the current element
            bars.get(sortCurrentIndex).setColor(CURRENT_COLOR);
            statusLabel.setText("Finding minimum element starting from index " + sortCurrentIndex);
            logTextArea.appendText("Pass " + (sortCurrentIndex + 1) + ": Finding minimum from index " + sortCurrentIndex + "\n");
            logTextArea.appendText("Current minimum candidate: " + bars.get(sortMinIndex).getValue() + " at index " + sortMinIndex + "\n");
            
            // Start comparing from the next element
            sortCompareIndex = sortCurrentIndex + 1;
            
            currentPause = new PauseTransition(Duration.millis(animationSpeed));
            currentPause.setOnFinished(e -> performNextSortStep());
            currentPause.play();
            return;
        }
        
        if (sortCompareIndex >= bars.size()) {
            // Finished comparing for this iteration, perform swap if needed
            if (sortMinIndex != sortCurrentIndex) {
                logTextArea.appendText("Swapping elements: " + bars.get(sortCurrentIndex).getValue() + 
                                     " (index " + sortCurrentIndex + ") with " + bars.get(sortMinIndex).getValue() + 
                                     " (index " + sortMinIndex + ")\n");
                swapElements(sortCurrentIndex, sortMinIndex);
            } else {
                logTextArea.appendText("No swap needed - element " + bars.get(sortCurrentIndex).getValue() + 
                                     " at index " + sortCurrentIndex + " is already in correct position\n");
            }
            
            // Mark current element as sorted
            bars.get(sortCurrentIndex).setColor(SORTED_COLOR);
            logTextArea.appendText("Element " + bars.get(sortCurrentIndex).getValue() + 
                                 " at index " + sortCurrentIndex + " is now in its final sorted position\n\n");
            
            // Reset colors for next iteration
            for (int i = sortCurrentIndex + 1; i < bars.size(); i++) {
                bars.get(i).setColor(DEFAULT_COLOR);
            }
            
            // Move to next iteration
            sortCurrentIndex++;
            sortCompareIndex = sortCurrentIndex;
            
            currentPause = new PauseTransition(Duration.millis(animationSpeed));
            currentPause.setOnFinished(e -> performNextSortStep());
            currentPause.play();
            return;
        }
        
        // Compare current element with minimum
        bars.get(sortCompareIndex).setColor(COMPARING_COLOR);
        
        currentPause = new PauseTransition(Duration.millis(animationSpeed));
        currentPause.setOnFinished(e -> {
            if (bars.get(sortCompareIndex).getValue() < bars.get(sortMinIndex).getValue()) {
                // Found new minimum
                if (sortMinIndex != sortCurrentIndex) {
                    bars.get(sortMinIndex).setColor(DEFAULT_COLOR);
                }
                sortMinIndex = sortCompareIndex;
                bars.get(sortMinIndex).setColor(MINIMUM_COLOR);
                statusLabel.setText("New minimum found at index " + sortMinIndex + " (value: " + bars.get(sortMinIndex).getValue() + ")");
                logTextArea.appendText("New minimum found: " + bars.get(sortMinIndex).getValue() + " at index " + sortMinIndex + "\n");
            } else {
                bars.get(sortCompareIndex).setColor(DEFAULT_COLOR);
                logTextArea.appendText("Comparing: " + bars.get(sortCompareIndex).getValue() + " >= " + 
                                     bars.get(sortMinIndex).getValue() + " (no change in minimum)\n");
            }
            
            // Continue with next comparison
            sortCompareIndex++;
            performNextSortStep();
        });
        currentPause.play();
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
     * Stop the sorting animation
     */
    private void stopSorting() {
        shouldStop = true;
        if (currentPause != null) {
            currentPause.stop();
        }
        
        // Reset UI state
        isAnimating = false;
        generateButton.setDisable(false);
        sortButton.setDisable(false);
        stopButton.setDisable(true);
        
        // Reset colors
        for (BarElement bar : bars) {
            if (bar.getRectangle().getFill() != SORTED_COLOR) {
                bar.setColor(DEFAULT_COLOR);
            }
        }
    }
    
    /**
     * Reset the visualization to initial state
     */
    private void resetVisualization() {
        // Stop any ongoing animation
        stopSorting();
        
        // Reset all bars to default color
        for (BarElement bar : bars) {
            bar.setColor(DEFAULT_COLOR);
        }
        
        // Reset sorting state
        sortCurrentIndex = 0;
        sortCompareIndex = 0;
        sortMinIndex = -1;
        
        statusLabel.setText("Visualization reset. Ready to sort.");
        
        // Clear and reset log
        logTextArea.clear();
        logTextArea.appendText("Selection Sort Log:\n");
        logTextArea.appendText("===================\n");
        logTextArea.appendText("Visualization reset.\n");
        if (!bars.isEmpty()) {
            logTextArea.appendText("Current array: ");
            for (int i = 0; i < bars.size(); i++) {
                logTextArea.appendText(bars.get(i).getValue() + (i < bars.size() - 1 ? ", " : "\n"));
            }
        }
        logTextArea.appendText("Ready to start sorting...\n\n");
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
        logTextArea.appendText("=========================\n");
        logTextArea.appendText("SORTING COMPLETED!\n");
        logTextArea.appendText("=========================\n");
        logTextArea.appendText("Final sorted array: ");
        for (int i = 0; i < bars.size(); i++) {
            logTextArea.appendText(bars.get(i).getValue() + (i < bars.size() - 1 ? ", " : "\n"));
        }
        logTextArea.appendText("All elements are now in ascending order.\n");
        
        isAnimating = false;
        generateButton.setDisable(false);
        sortButton.setDisable(false);
        stopButton.setDisable(true);
    }
    
    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
