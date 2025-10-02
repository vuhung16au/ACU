package com.acu.javafx.heapsort;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HeapSortDemo extends Application {
    private Canvas canvas;
    private GraphicsContext gc;
    private Heap<Integer> heap;
    private List<Integer> originalList;
    private List<Integer> sortedList;
    private TextArea outputArea;
    private Button insertButton;
    private Button removeButton;
    private Button sortButton;
    private Button resetButton;
    private TextField inputField;
    private Label statusLabel;
    
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 400;
    private static final int NODE_RADIUS = 25;
    private static final int VERTICAL_GAP = 60;
    
    @Override
    public void start(Stage primaryStage) {
        heap = new Heap<>();
        originalList = new ArrayList<>();
        sortedList = new ArrayList<>();
        
        // Create UI components
        createUI();
        
        // Set up the scene
        Scene scene = new Scene(createMainLayout(), 1300, 600);
        primaryStage.setTitle("Heap Sort Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Initialize with some sample data
        initializeSampleData();
    }
    
    private void createUI() {
        // Canvas for drawing the heap
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        
        // Control panel
        VBox controlPanel = createControlPanel();
        
        // Output area
        outputArea = new TextArea();
        outputArea.setPrefRowCount(10);
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        
        // Status label
        statusLabel = new Label("Ready");
        statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: blue;");
    }
    
    private VBox createControlPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setAlignment(Pos.TOP_CENTER);
        
        // Input section
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        
        Label inputLabel = new Label("Enter value:");
        inputField = new TextField();
        inputField.setPrefWidth(100);
        inputField.setOnAction(e -> insertValue());
        
        insertButton = new Button("Insert");
        insertButton.setOnAction(e -> insertValue());
        
        removeButton = new Button("Remove Root");
        removeButton.setOnAction(e -> removeRoot());
        
        inputBox.getChildren().addAll(inputLabel, inputField, insertButton, removeButton);
        
        // Action buttons
        HBox actionBox = new HBox(10);
        actionBox.setAlignment(Pos.CENTER);
        
        sortButton = new Button("Heap Sort");
        sortButton.setOnAction(e -> performHeapSort());
        
        resetButton = new Button("Reset");
        resetButton.setOnAction(e -> reset());
        
        actionBox.getChildren().addAll(sortButton, resetButton);
        
        panel.getChildren().addAll(inputBox, actionBox);
        
        return panel;
    }
    
    private VBox createMainLayout() {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        
        // Top section with canvas and controls
        HBox topSection = new HBox(10);
        topSection.getChildren().addAll(canvas, createControlPanel());
        
        // Bottom section with output and status
        VBox bottomSection = new VBox(5);
        bottomSection.getChildren().addAll(statusLabel, outputArea);
        
        mainLayout.getChildren().addAll(topSection, bottomSection);
        
        return mainLayout;
    }
    
    private void initializeSampleData() {
        // Add some sample values to the heap
        Integer[] sampleData = {50, 25, 80, 11, 43, 2, 35, 67, 18, 92};
        for (Integer value : sampleData) {
            heap.add(value);
            originalList.add(value);
        }
        updateDisplay();
        log("Initialized with sample data: " + originalList);
    }
    
    private void insertValue() {
        try {
            String input = inputField.getText().trim();
            if (input.isEmpty()) {
                showAlert("Please enter a value");
                return;
            }
            
            int value = Integer.parseInt(input);
            heap.add(value);
            originalList.add(value);
            inputField.clear();
            updateDisplay();
            log("Inserted: " + value);
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid integer");
        }
    }
    
    private void removeRoot() {
        if (heap.isEmpty()) {
            showAlert("Heap is empty");
            return;
        }
        
        Integer removed = heap.remove();
        originalList.remove(removed);
        updateDisplay();
        log("Removed root: " + removed);
    }
    
    private void performHeapSort() {
        if (originalList.isEmpty()) {
            showAlert("No data to sort");
            return;
        }
        
        sortButton.setDisable(true);
        resetButton.setDisable(true);
        
        // Create a copy of the original list
        List<Integer> listToSort = new ArrayList<>(originalList);
        sortedList.clear();
        
        log("Starting Heap Sort...");
        log("Original array: " + listToSort);
        
        // Perform heap sort
        Integer[] array = listToSort.toArray(new Integer[0]);
        HeapSort.heapSort(array);
        
        // Use a mutable list to avoid UnsupportedOperationException on clear/remove
        sortedList = new ArrayList<>(Arrays.asList(array));
        
        log("Sorted array: " + sortedList);
        log("Heap Sort completed!");
        
        // Rebuild the heap with sorted data for visualization
        heap = new Heap<>();
        for (Integer value : sortedList) {
            heap.add(value);
        }
        
        updateDisplay();
        
        sortButton.setDisable(false);
        resetButton.setDisable(false);
    }
    
    private void reset() {
        heap = new Heap<>();
        // Replace with new mutable lists to be safe even if previous lists were fixed-size
        originalList = new ArrayList<>();
        sortedList = new ArrayList<>();
        updateDisplay();
        log("Reset completed");
    }
    
    private void updateDisplay() {
        // Clear canvas
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        
        if (heap.isEmpty()) {
            gc.setFill(Color.BLACK);
            gc.setFont(Font.font(16));
            gc.fillText("Heap is empty", CANVAS_WIDTH / 2 - 50, CANVAS_HEIGHT / 2);
            return;
        }
        
        // Draw the heap tree
        drawHeapTree();
        
        // Update status
        statusLabel.setText("Heap size: " + heap.getSize() + 
                          (sortedList.isEmpty() ? "" : " | Sorted: " + sortedList));
    }
    
    private void drawHeapTree() {
        List<Integer> heapList = heap.getList();
        if (heapList.isEmpty()) return;
        
        int startX = CANVAS_WIDTH / 2;
        int startY = 50;
        
        drawNode(startX, startY, 0, heapList, CANVAS_WIDTH / 4);
    }
    
    private void drawNode(int x, int y, int index, List<Integer> heapList, int horizontalGap) {
        if (index >= heapList.size()) return;
        
        // Draw node circle
        gc.setFill(Color.LIGHTBLUE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        gc.strokeOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        
        // Draw value
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(14));
        String value = heapList.get(index).toString();
        double textWidth = gc.getFont().getSize() * value.length() * 0.6;
        gc.fillText(value, x - textWidth / 2, y + 5);
        
        // Draw connections to children
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        
        if (leftChildIndex < heapList.size()) {
            int leftX = x - horizontalGap;
            int leftY = y + VERTICAL_GAP;
            
            // Draw line to left child
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeLine(x, y + NODE_RADIUS, leftX, leftY - NODE_RADIUS);
            
            drawNode(leftX, leftY, leftChildIndex, heapList, horizontalGap / 2);
        }
        
        if (rightChildIndex < heapList.size()) {
            int rightX = x + horizontalGap;
            int rightY = y + VERTICAL_GAP;
            
            // Draw line to right child
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeLine(x, y + NODE_RADIUS, rightX, rightY - NODE_RADIUS);
            
            drawNode(rightX, rightY, rightChildIndex, heapList, horizontalGap / 2);
        }
    }
    
    private void log(String message) {
        outputArea.appendText(message + "\n");
        outputArea.setScrollTop(Double.MAX_VALUE);
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
} 