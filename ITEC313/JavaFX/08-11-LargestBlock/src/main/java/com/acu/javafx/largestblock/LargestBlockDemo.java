package com.acu.javafx.largestblock;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * JavaFX application for the Largest Block Problem.
 * 
 * This application provides an interactive interface for:
 * - Displaying a binary matrix (0s and 1s)
 * - Allowing users to toggle cell values by clicking
 * - Finding the largest square block of 1s
 * - Highlighting the solution
 * - Changing board size
 * - Loading sample data
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class LargestBlockDemo extends Application {
    
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 20;
    private static final int MIN_SIZE = 3;
    
    private int currentSize = DEFAULT_SIZE;
    private int[][] matrix;
    private TextField[][] cellFields;
    private GridPane boardGrid;
    private Label resultLabel;
    private LargestBlockSolver.BlockResult currentResult;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Largest Block Problem - Exercise 22.19");
        
        // Initialize the matrix
        matrix = new int[currentSize][currentSize];
        initializeMatrix();
        
        // Create the main layout
        VBox root = createMainLayout();
        
        Scene scene = new Scene(root, 800, 700);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    
    /**
     * Creates the main layout of the application.
     */
    private VBox createMainLayout() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        
        // Title
        Label titleLabel = new Label("Largest Block Problem");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        root.getChildren().add(titleLabel);
        
        // Control panel
        HBox controlPanel = createControlPanel();
        root.getChildren().add(controlPanel);
        
        // Board size control
        HBox sizePanel = createSizePanel();
        root.getChildren().add(sizePanel);
        
        // Board display
        boardGrid = createBoardGrid();
        root.getChildren().add(boardGrid);
        
        // Result display
        resultLabel = new Label("Click 'Find Largest Block' to solve the puzzle");
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        root.getChildren().add(resultLabel);
        
        return root;
    }
    
    /**
     * Creates the control panel with main buttons.
     */
    private HBox createControlPanel() {
        HBox panel = new HBox(10);
        panel.setAlignment(Pos.CENTER);
        
        Button loadSampleButton = new Button("Load Sample Board");
        loadSampleButton.setOnAction(e -> loadSampleBoard());
        
        Button findSolutionButton = new Button("Find Largest Block");
        findSolutionButton.setOnAction(e -> findLargestBlock());
        
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> clearBoard());
        
        Button randomButton = new Button("Random Board");
        randomButton.setOnAction(e -> generateRandomBoard());
        
        panel.getChildren().addAll(loadSampleButton, findSolutionButton, clearButton, randomButton);
        
        return panel;
    }
    
    /**
     * Creates the board size control panel.
     */
    private HBox createSizePanel() {
        HBox panel = new HBox(10);
        panel.setAlignment(Pos.CENTER);
        
        Label sizeLabel = new Label("Board Size:");
        TextField sizeField = new TextField(String.valueOf(currentSize));
        sizeField.setPrefWidth(50);
        
        Button changeSizeButton = new Button("Change Board Size");
        changeSizeButton.setOnAction(e -> {
            try {
                int newSize = Integer.parseInt(sizeField.getText());
                if (newSize >= MIN_SIZE && newSize <= MAX_SIZE) {
                    changeBoardSize(newSize);
                } else {
                    showAlert("Invalid size", "Board size must be between " + MIN_SIZE + " and " + MAX_SIZE);
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid input", "Please enter a valid number");
            }
        });
        
        panel.getChildren().addAll(sizeLabel, sizeField, changeSizeButton);
        
        return panel;
    }
    
    /**
     * Creates the board grid for displaying the matrix.
     */
    private GridPane createBoardGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(2);
        grid.setVgap(2);
        grid.setPadding(new Insets(10));
        
        // Create cell fields
        cellFields = new TextField[currentSize][currentSize];
        
        for (int i = 0; i < currentSize; i++) {
            for (int j = 0; j < currentSize; j++) {
                TextField cell = new TextField(String.valueOf(matrix[i][j]));
                cell.setPrefWidth(30);
                cell.setPrefHeight(30);
                cell.setAlignment(Pos.CENTER);
                cell.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                
                // Add click handler to toggle value
                final int row = i;
                final int col = j;
                cell.setOnMouseClicked(e -> toggleCell(row, col));
                
                // Add text change handler
                cell.textProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal.equals("0") || newVal.equals("1")) {
                        matrix[row][col] = Integer.parseInt(newVal);
                    } else if (!newVal.isEmpty()) {
                        // Reset to old value if invalid
                        cell.setText(oldVal);
                    }
                });
                
                cellFields[i][j] = cell;
                grid.add(cell, j, i);
            }
        }
        
        return grid;
    }
    
    /**
     * Toggles the value of a cell between 0 and 1.
     */
    private void toggleCell(int row, int col) {
        matrix[row][col] = (matrix[row][col] == 0) ? 1 : 0;
        cellFields[row][col].setText(String.valueOf(matrix[row][col]));
    }
    
    /**
     * Initializes the matrix with zeros.
     */
    private void initializeMatrix() {
        for (int i = 0; i < currentSize; i++) {
            for (int j = 0; j < currentSize; j++) {
                matrix[i][j] = 0;
            }
        }
    }
    
    /**
     * Loads the sample board.
     */
    private void loadSampleBoard() {
        matrix = LargestBlockSolver.createSampleMatrix();
        currentSize = matrix.length;
        refreshBoard();
    }
    
    /**
     * Generates a random board.
     */
    private void generateRandomBoard() {
        matrix = LargestBlockSolver.createRandomMatrix(currentSize, 0.3); // 30% probability of 1
        refreshBoard();
    }
    
    /**
     * Clears the board (sets all cells to 0).
     */
    private void clearBoard() {
        initializeMatrix();
        refreshBoard();
        resultLabel.setText("Board cleared. Click 'Find Largest Block' to solve.");
    }
    
    /**
     * Changes the board size and recreates the grid.
     */
    private void changeBoardSize(int newSize) {
        currentSize = newSize;
        matrix = new int[currentSize][currentSize];
        initializeMatrix();
        refreshBoard();
        resultLabel.setText("Board size changed to " + currentSize + "x" + currentSize);
    }
    
    /**
     * Refreshes the board display.
     */
    private void refreshBoard() {
        // Get the parent container
        VBox parent = (VBox) boardGrid.getParent();
        
        // Remove old grid from parent
        if (parent != null) {
            parent.getChildren().remove(boardGrid);
        }
        
        // Create new grid
        boardGrid = createBoardGrid();
        
        // Add new grid to parent at the correct position
        if (parent != null) {
            parent.getChildren().add(3, boardGrid); // Insert at position 3
        }
    }
    
    /**
     * Finds the largest block and highlights it.
     */
    private void findLargestBlock() {
        if (!LargestBlockSolver.isValidMatrix(matrix)) {
            showAlert("Invalid Matrix", "Matrix contains invalid values. Please use only 0s and 1s.");
            return;
        }
        
        currentResult = LargestBlockSolver.findLargestBlock(matrix);
        
        if (currentResult.getSize() == 0) {
            resultLabel.setText("No block of 1s found!");
            clearHighlights();
        } else {
            resultLabel.setText(String.format("Largest block: size %d at position (%d, %d)", 
                currentResult.getSize(), currentResult.getRow(), currentResult.getCol()));
            highlightLargestBlock();
        }
    }
    
    /**
     * Highlights the largest block in the grid.
     */
    private void highlightLargestBlock() {
        clearHighlights();
        
        if (currentResult.getSize() == 0) return;
        
        int size = currentResult.getSize();
        int startRow = currentResult.getRow() - size + 1;
        int startCol = currentResult.getCol() - size + 1;
        
        // Highlight the cells in the largest block
        for (int i = startRow; i <= currentResult.getRow(); i++) {
            for (int j = startCol; j <= currentResult.getCol(); j++) {
                if (i >= 0 && i < currentSize && j >= 0 && j < currentSize) {
                    cellFields[i][j].setStyle("-fx-background-color: #FFD700; -fx-border-color: #FFA500;");
                }
            }
        }
    }
    
    /**
     * Clears all highlights from the grid.
     */
    private void clearHighlights() {
        for (int i = 0; i < currentSize; i++) {
            for (int j = 0; j < currentSize; j++) {
                cellFields[i][j].setStyle("");
            }
        }
    }
    
    /**
     * Shows an alert dialog.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Main method to launch the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
