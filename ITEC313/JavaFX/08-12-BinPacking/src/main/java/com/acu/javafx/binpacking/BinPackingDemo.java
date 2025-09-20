package com.acu.javafx.binpacking;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main JavaFX application for the Bin Packing Problem Solver
 * 
 * Features:
 * - Interactive input for items and bin capacity
 * - Two algorithms: First Fit and Best Fit
 * - Visual representation of solutions
 * - Sample data loading
 * - Performance comparison
 */
public class BinPackingDemo extends Application {
    
    // UI Components
    private TextField numItemsField;
    private TextField numBinsField;
    private TextField binCapacityField;
    private TextArea itemsTextArea;
    private Button loadSampleButton;
    private Button findSolutionButton;
    private Button clearButton;
    private TabPane algorithmTabs;
    private Canvas visualizationCanvas;
    private TextArea resultTextArea;
    
    // Algorithm instances
    private final FirstFitAlgorithm firstFitAlgorithm = new FirstFitAlgorithm();
    private final BestFitAlgorithm bestFitAlgorithm = new BestFitAlgorithm();
    private final FirstFitDecreasingAlgorithm firstFitDecreasingAlgorithm = new FirstFitDecreasingAlgorithm();
    
    // Visualizer
    private BinPackingVisualizer visualizer;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bin Packing Problem Solver");
        
        // Create main layout with proper spacing
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(5));
        
        // Create left side panel (input, algorithms, results)
        VBox leftPanel = createLeftPanel();
        
        // Create right side panel (visualization)
        VBox rightPanel = createRightPanel();
        
        // Layout setup
        HBox mainPanel = new HBox(5);
        mainPanel.getChildren().addAll(leftPanel, rightPanel);
        mainPanel.setAlignment(Pos.TOP_LEFT);
        
        root.setCenter(mainPanel);
        
        // Set up scene with exact size as specified
        Scene scene = new Scene(root, 1280, 720);
        scene.setFill(Color.LIGHTGRAY);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Initialize visualizer
        visualizer = new BinPackingVisualizer(visualizationCanvas);
        visualizer.drawWelcome();
    }
    
    /**
     * Create the left panel with input, algorithm selection, and results
     */
    private VBox createLeftPanel() {
        VBox leftPanel = new VBox(5);
        leftPanel.setPrefWidth(200);
        leftPanel.setMaxWidth(200);
        leftPanel.setMinWidth(200);
        
        // Create input panel
        VBox inputPanel = createInputPanel();
        
        // Create algorithm selection panel
        VBox algorithmPanel = createAlgorithmSelectionPanel();
        
        // Create result panel
        VBox resultPanel = createResultPanel();
        
        leftPanel.getChildren().addAll(inputPanel, algorithmPanel, resultPanel);
        
        return leftPanel;
    }
    
    /**
     * Create the right panel with visualization
     */
    private VBox createRightPanel() {
        VBox rightPanel = new VBox(5);
        rightPanel.setPrefWidth(1075); // 1280 - 200 - 5 (spacing)
        rightPanel.setMaxWidth(1075);
        rightPanel.setMinWidth(1075);
        
        // Create visualization panel
        VBox visualizationPanel = createVisualizationPanel();
        
        rightPanel.getChildren().addAll(visualizationPanel);
        
        return rightPanel;
    }
    
    /**
     * Create the input panel with controls for entering data
     */
    private VBox createInputPanel() {
        VBox panel = new VBox(3);
        panel.setPadding(new Insets(5));
        panel.setPrefWidth(190);
        panel.setMaxWidth(190);
        panel.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-width: 1; -fx-border-radius: 3;");
        
        Label titleLabel = new Label("Bin Packing Input");
        titleLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        
        // Number of items input
        VBox numItemsBox = new VBox(2);
        Label numItemsLabel = new Label("Items:");
        numItemsLabel.setStyle("-fx-font-size: 10px;");
        numItemsField = new TextField("6");
        numItemsField.setPrefWidth(180);
        numItemsField.setMaxWidth(180);
        numItemsBox.getChildren().addAll(numItemsLabel, numItemsField);
        
        // Number of bins input
        VBox numBinsBox = new VBox(2);
        Label numBinsLabel = new Label("Bins:");
        numBinsLabel.setStyle("-fx-font-size: 10px;");
        numBinsField = new TextField("5");
        numBinsField.setPrefWidth(180);
        numBinsField.setMaxWidth(180);
        numBinsBox.getChildren().addAll(numBinsLabel, numBinsField);
        
        // Bin capacity input
        VBox binCapacityBox = new VBox(2);
        Label binCapacityLabel = new Label("Capacity:");
        binCapacityLabel.setStyle("-fx-font-size: 10px;");
        binCapacityField = new TextField("10");
        binCapacityField.setPrefWidth(180);
        binCapacityField.setMaxWidth(180);
        binCapacityBox.getChildren().addAll(binCapacityLabel, binCapacityField);
        
        // Items input
        Label itemsLabel = new Label("Weights:");
        itemsLabel.setStyle("-fx-font-size: 10px;");
        itemsTextArea = new TextArea("7 5 2 3 5 8");
        itemsTextArea.setPrefRowCount(3);
        itemsTextArea.setPrefWidth(180);
        itemsTextArea.setMaxWidth(180);
        itemsTextArea.setStyle("-fx-font-size: 9px;");
        
        // Buttons with proper spacing
        VBox buttonBox = new VBox(3);
        loadSampleButton = new Button("Load Sample");
        findSolutionButton = new Button("Find Solution");
        clearButton = new Button("Clear");
        
        // Set button widths and styles
        loadSampleButton.setPrefWidth(180);
        loadSampleButton.setMaxWidth(180);
        loadSampleButton.setStyle("-fx-font-size: 9px;");
        
        findSolutionButton.setPrefWidth(180);
        findSolutionButton.setMaxWidth(180);
        findSolutionButton.setStyle("-fx-font-size: 9px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        
        clearButton.setPrefWidth(180);
        clearButton.setMaxWidth(180);
        clearButton.setStyle("-fx-font-size: 9px;");
        
        loadSampleButton.setOnAction(e -> loadSampleData());
        findSolutionButton.setOnAction(e -> findSolution());
        clearButton.setOnAction(e -> clearAll());
        
        buttonBox.getChildren().addAll(loadSampleButton, findSolutionButton, clearButton);
        
        panel.getChildren().addAll(
            titleLabel, numItemsBox, numBinsBox, binCapacityBox, itemsLabel, 
            itemsTextArea, buttonBox
        );
        
        return panel;
    }
    
    /**
     * Create the visualization panel with canvas
     */
    private VBox createVisualizationPanel() {
        VBox panel = new VBox(5);
        panel.setPadding(new Insets(5));
        panel.setPrefWidth(1070);
        panel.setMaxWidth(1070);
        panel.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-width: 1; -fx-border-radius: 3;");
        
        Label titleLabel = new Label("Visualization");
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        // Create a scroll pane for the canvas to handle overflow
        ScrollPane canvasScrollPane = new ScrollPane();
        canvasScrollPane.setPrefSize(1060, 700);
        canvasScrollPane.setMaxSize(1060, 700);
        canvasScrollPane.setStyle("-fx-background-color: white;");
        
        visualizationCanvas = new Canvas(1040, 680);
        visualizationCanvas.setStyle("-fx-border-color: #ddd; -fx-border-width: 1;");
        
        canvasScrollPane.setContent(visualizationCanvas);
        canvasScrollPane.setFitToWidth(true);
        canvasScrollPane.setFitToHeight(true);
        
        panel.getChildren().addAll(titleLabel, canvasScrollPane);
        
        return panel;
    }
    
    /**
     * Create the result panel with text area
     */
    private VBox createResultPanel() {
        VBox panel = new VBox(3);
        panel.setPadding(new Insets(5));
        panel.setPrefWidth(190);
        panel.setMaxWidth(190);
        panel.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #ccc; -fx-border-width: 1; -fx-border-radius: 3;");
        
        Label titleLabel = new Label("Results");
        titleLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        
        resultTextArea = new TextArea();
        resultTextArea.setPrefRowCount(20);
        resultTextArea.setPrefWidth(180);
        resultTextArea.setMaxWidth(180);
        resultTextArea.setEditable(false);
        resultTextArea.setStyle("-fx-font-family: monospace; -fx-font-size: 8px;");
        
        panel.getChildren().addAll(titleLabel, resultTextArea);
        
        return panel;
    }
    
    /**
     * Create algorithm selection panel
     */
    private VBox createAlgorithmSelectionPanel() {
        VBox panel = new VBox(3);
        panel.setPadding(new Insets(5));
        panel.setPrefWidth(190);
        panel.setMaxWidth(190);
        panel.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #ccc; -fx-border-width: 1; -fx-border-radius: 3;");
        
        Label titleLabel = new Label("Algorithm Selection");
        titleLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        
        // Create algorithm buttons
        VBox buttonBox = new VBox(3);
        
        Button firstFitButton = new Button("First Fit");
        firstFitButton.setPrefWidth(180);
        firstFitButton.setMaxWidth(180);
        firstFitButton.setStyle("-fx-font-size: 9px;");
        firstFitButton.setOnAction(e -> selectAlgorithm("First Fit"));
        
        Button bestFitButton = new Button("Best Fit");
        bestFitButton.setPrefWidth(180);
        bestFitButton.setMaxWidth(180);
        bestFitButton.setStyle("-fx-font-size: 9px;");
        bestFitButton.setOnAction(e -> selectAlgorithm("Best Fit"));
        
        Button firstFitDecreasingButton = new Button("First Fit Decreasing");
        firstFitDecreasingButton.setPrefWidth(180);
        firstFitDecreasingButton.setMaxWidth(180);
        firstFitDecreasingButton.setStyle("-fx-font-size: 9px;");
        firstFitDecreasingButton.setOnAction(e -> selectAlgorithm("First Fit Decreasing"));
        
        buttonBox.getChildren().addAll(firstFitButton, bestFitButton, firstFitDecreasingButton);
        
        panel.getChildren().addAll(titleLabel, buttonBox);
        
        return panel;
    }
    
    /**
     * Select algorithm
     */
    private void selectAlgorithm(String algorithmName) {
        // This method can be used to highlight the selected algorithm
        // For now, we'll just store the selection for use in findSolution()
        // The actual algorithm selection is handled in the findSolution method
    }
    
    /**
     * Create algorithm tabs (kept for compatibility)
     */
    private TabPane createAlgorithmTabs() {
        TabPane tabPane = new TabPane();
        tabPane.setPrefWidth(400);
        tabPane.setMaxWidth(400);
        tabPane.setPrefHeight(300);
        tabPane.setMaxHeight(300);
        tabPane.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #ccc; -fx-border-width: 1; -fx-border-radius: 5;");
        
        // First Fit tab
        Tab firstFitTab = new Tab("First Fit");
        VBox firstFitContent = new VBox(10);
        firstFitContent.setPadding(new Insets(15));
        
        Label firstFitLabel = new Label("First Fit Algorithm");
        firstFitLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        TextArea firstFitDescription = new TextArea(
            "Places each item into the first bin that has enough capacity.\n" +
            "Time Complexity: O(n * m)\n" +
            "Guarantee: Uses at most 2 * OPT bins"
        );
        firstFitDescription.setPrefRowCount(5);
        firstFitDescription.setPrefWidth(370);
        firstFitDescription.setMaxWidth(370);
        firstFitDescription.setEditable(false);
        firstFitDescription.setStyle("-fx-font-size: 11px;");
        
        firstFitContent.getChildren().addAll(firstFitLabel, firstFitDescription);
        firstFitTab.setContent(firstFitContent);
        
        // Best Fit tab
        Tab bestFitTab = new Tab("Best Fit");
        VBox bestFitContent = new VBox(10);
        bestFitContent.setPadding(new Insets(15));
        
        Label bestFitLabel = new Label("Best Fit Algorithm");
        bestFitLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        TextArea bestFitDescription = new TextArea(
            "Places each item into the bin with the least remaining capacity.\n" +
            "Time Complexity: O(n * m)\n" +
            "Guarantee: Uses at most 1.7 * OPT bins"
        );
        bestFitDescription.setPrefRowCount(5);
        bestFitDescription.setPrefWidth(370);
        bestFitDescription.setMaxWidth(370);
        bestFitDescription.setEditable(false);
        bestFitDescription.setStyle("-fx-font-size: 11px;");
        
        bestFitContent.getChildren().addAll(bestFitLabel, bestFitDescription);
        bestFitTab.setContent(bestFitContent);
        
        // First Fit Decreasing tab
        Tab firstFitDecreasingTab = new Tab("First Fit Decreasing");
        VBox firstFitDecreasingContent = new VBox(10);
        firstFitDecreasingContent.setPadding(new Insets(15));
        
        Label firstFitDecreasingLabel = new Label("First Fit Decreasing Algorithm");
        firstFitDecreasingLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        TextArea firstFitDecreasingDescription = new TextArea(
            "Sorts items by weight (decreasing), then applies First Fit.\n" +
            "Time Complexity: O(n log n + n * m)\n" +
            "Guarantee: Uses at most (11/9) * OPT + 6/9 bins"
        );
        firstFitDecreasingDescription.setPrefRowCount(5);
        firstFitDecreasingDescription.setPrefWidth(370);
        firstFitDecreasingDescription.setMaxWidth(370);
        firstFitDecreasingDescription.setEditable(false);
        firstFitDecreasingDescription.setStyle("-fx-font-size: 11px;");
        
        firstFitDecreasingContent.getChildren().addAll(firstFitDecreasingLabel, firstFitDecreasingDescription);
        firstFitDecreasingTab.setContent(firstFitDecreasingContent);
        
        tabPane.getTabs().addAll(firstFitTab, bestFitTab, firstFitDecreasingTab);
        
        return tabPane;
    }
    
    /**
     * Load sample data for demonstration
     */
    private void loadSampleData() {
        Random random = new Random();
        int numItems = 10;
        int numBins = 5;
        int binCapacity = 10;
        
        numItemsField.setText(String.valueOf(numItems));
        numBinsField.setText(String.valueOf(numBins));
        binCapacityField.setText(String.valueOf(binCapacity));
        
        // Generate random weights between 1 and 10
        StringBuilder weights = new StringBuilder();
        for (int i = 0; i < numItems; i++) {
            if (i > 0) weights.append(" ");
            weights.append(random.nextInt(10) + 1);
        }
        
        itemsTextArea.setText(weights.toString());
    }
    
    /**
     * Find solution using the selected algorithm
     */
    private void findSolution() {
        try {
            // Parse input
            int numItems = Integer.parseInt(numItemsField.getText());
            int numBins = Integer.parseInt(numBinsField.getText());
            int binCapacity = Integer.parseInt(binCapacityField.getText());
            String[] weightStrings = itemsTextArea.getText().trim().split("\\s+");
            
            if (weightStrings.length != numItems) {
                showError("Number of weights must match the number of items.");
                return;
            }
            
            if (numBins <= 0) {
                showError("Number of bins must be greater than 0.");
                return;
            }
            
            if (binCapacity <= 0) {
                showError("Bin capacity must be greater than 0.");
                return;
            }
            
            // Create items
            List<BinPackingAlgorithm.Item> items = new ArrayList<>();
            for (int i = 0; i < numItems; i++) {
                int weight = Integer.parseInt(weightStrings[i]);
                items.add(new BinPackingAlgorithm.Item(i + 1, weight));
            }
            
            // Get selected algorithm (default to First Fit for now)
            BinPackingAlgorithm algorithm = firstFitAlgorithm; // Default to First Fit
            
            // Solve the problem
            BinPackingAlgorithm.BinPackingResult result = algorithm.solve(items, binCapacity);
            
            // Check if solution uses more bins than allowed
            if (result.getNumberOfBins() > numBins) {
                showError("Solution requires " + result.getNumberOfBins() + " bins, but only " + numBins + " bins are available.");
                return;
            }
            
            // Display results
            displayResults(result);
            
            // Visualize solution
            visualizer.drawSolution(result);
            
        } catch (NumberFormatException e) {
            showError("Please enter valid numbers for all fields.");
        } catch (Exception e) {
            showError("An error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Display the results in the text area
     */
    private void displayResults(BinPackingAlgorithm.BinPackingResult result) {
        StringBuilder output = new StringBuilder();
        
        output.append("=== BIN PACKING SOLUTION ===\n");
        output.append("Algorithm: ").append(result.getAlgorithmName()).append("\n");
        output.append("Number of bins used: ").append(result.getNumberOfBins()).append("\n");
        output.append("Efficiency: ").append(String.format("%.1f%%", result.getEfficiency() * 100)).append("\n");
        output.append("Execution time: ").append(result.getExecutionTimeMs()).append(" ms\n\n");
        
        // Display each bin
        List<BinPackingAlgorithm.Bin> bins = result.getBins();
        for (int i = 0; i < bins.size(); i++) {
            BinPackingAlgorithm.Bin bin = bins.get(i);
            output.append("Bin ").append(i + 1).append(": ");
            output.append("Capacity ").append(bin.getCapacity());
            output.append(", Used ").append(bin.getCurrentWeight());
            output.append(", Remaining ").append(bin.getRemainingCapacity());
            output.append("\n");
            
            List<BinPackingAlgorithm.Item> items = bin.getItems();
            if (items.isEmpty()) {
                output.append("  (empty)\n");
            } else {
                output.append("  Items: ");
                for (int j = 0; j < items.size(); j++) {
                    if (j > 0) output.append(", ");
                    output.append("Item ").append(items.get(j).getId())
                          .append("(weight=").append(items.get(j).getWeight()).append(")");
                }
                output.append("\n");
            }
            output.append("\n");
        }
        
        // Display unplaced items
        if (!result.getUnplacedItems().isEmpty()) {
            output.append("Unplaced items:\n");
            for (BinPackingAlgorithm.Item item : result.getUnplacedItems()) {
                output.append("  Item ").append(item.getId())
                      .append(" (weight=").append(item.getWeight()).append(")\n");
            }
        }
        
        resultTextArea.setText(output.toString());
    }
    
    /**
     * Clear all input and results
     */
    private void clearAll() {
        numItemsField.clear();
        numBinsField.clear();
        binCapacityField.clear();
        itemsTextArea.clear();
        resultTextArea.clear();
        visualizer.drawWelcome();
    }
    
    /**
     * Show error message
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
