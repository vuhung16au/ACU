package com.acu.javafx.sudoku;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Sudoku Solver Application
 * 
 * This application provides a graphical interface for solving Sudoku puzzles.
 * Users can input numbers, load sample puzzles, and solve them using a backtracking algorithm.
 * 
 * Features:
 * - Interactive 9x9 grid for input
 * - Sample puzzle loading
 * - Backtracking algorithm for solving
 * - Clear functionality
 * - Visual feedback for invalid inputs
 */
public class SudokuSolver extends Application {
    
    // Constants for the Sudoku grid
    private static final int GRID_SIZE = 9;
    private static final int CELL_SIZE = 50;
    private static final int CANVAS_SIZE = GRID_SIZE * CELL_SIZE;
    
    // ACU Brand Colors
    private static final String ACU_PURPLE = "#582C67";
    private static final String ACU_WHITE = "#FFFFFF";
    private static final String ACU_RED = "#C60C30";
    
    // UI Components
    private Canvas canvas;
    private GraphicsContext gc;
    private Button loadSampleButton;
    private Button solveButton;
    private Button clearButton;
    private Label statusLabel;
    
    // Sudoku grid data
    private int[][] grid = new int[GRID_SIZE][GRID_SIZE];
    private int[][] originalGrid = new int[GRID_SIZE][GRID_SIZE];
    private boolean[][] isOriginal = new boolean[GRID_SIZE][GRID_SIZE];
    
    // Sample puzzle from the exercise
    private final int[][] samplePuzzle = {
        {5, 3, 0, 0, 7, 0, 0, 0, 0},
        {6, 0, 0, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, 0, 0, 2, 0, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };
    
    @Override
    public void start(Stage primaryStage) {
        // Initialize the canvas
        canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
        gc = canvas.getGraphicsContext2D();
        
        // Create the scene first to initialize UI components
        Scene scene = new Scene(createRootPane(), 600, 700);
        
        // Set ACU brand background color
        scene.setFill(Color.web(ACU_PURPLE));
        
        // Set up event handlers after UI components are created
        setupEventHandlers();
        
        // Draw the initial grid
        drawGrid();
        
        primaryStage.setTitle("Exercise22_21 - Sudoku Solver");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    /**
     * Creates the main UI layout
     */
    private BorderPane createRootPane() {
        BorderPane root = new BorderPane();
        
        // Top: Status label with instructions
        statusLabel = new Label("How to enter numbers: Click a cell to add 1, click again to change 1→2→3...→9→0. Click Solve to find solution.");
        statusLabel.setFont(new Font(14));
        statusLabel.setStyle("-fx-text-fill: " + ACU_WHITE + "; -fx-background-color: " + ACU_PURPLE + "; -fx-padding: 10;");
        root.setTop(statusLabel);
        
        // Center: Canvas
        root.setCenter(canvas);
        
        // Bottom: Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));
        
        loadSampleButton = new Button("Load Sample Puzzle");
        solveButton = new Button("Solve");
        clearButton = new Button("Clear");
        
        // Style buttons with ACU brand colors
        String buttonStyle = "-fx-background-color: " + ACU_WHITE + "; " +
                           "-fx-text-fill: " + ACU_PURPLE + "; " +
                           "-fx-font-weight: bold; " +
                           "-fx-padding: 10 20; " +
                           "-fx-border-color: " + ACU_RED + "; " +
                           "-fx-border-width: 2;";
        
        loadSampleButton.setStyle(buttonStyle);
        solveButton.setStyle(buttonStyle);
        clearButton.setStyle(buttonStyle);
        
        buttonBox.getChildren().addAll(loadSampleButton, solveButton, clearButton);
        root.setBottom(buttonBox);
        
        return root;
    }
    
    /**
     * Sets up event handlers for all interactive components
     */
    private void setupEventHandlers() {
        // Canvas click handler for input
        canvas.setOnMouseClicked(this::handleCanvasClick);
        
        // Button handlers
        loadSampleButton.setOnAction(e -> loadSamplePuzzle());
        solveButton.setOnAction(e -> solvePuzzle());
        clearButton.setOnAction(e -> clearPuzzle());
    }
    
    /**
     * Handles mouse clicks on the canvas for number input
     */
    private void handleCanvasClick(MouseEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        
        int col = x / CELL_SIZE;
        int row = y / CELL_SIZE;
        
        if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE) {
            // Cycle through numbers 0-9
            int currentValue = grid[row][col];
            grid[row][col] = (currentValue + 1) % 10; // 0-9 cycle
            
            // Mark as not original if user modified
            if (!isOriginal[row][col]) {
                isOriginal[row][col] = false;
            }
            
            drawGrid();
            updateStatus("Click to cycle: 1→2→3→4→5→6→7→8→9→0 (empty). Click Solve when ready.");
        }
    }
    
    /**
     * Loads the sample puzzle
     */
    private void loadSamplePuzzle() {
        // Copy sample puzzle to grid
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                grid[row][col] = samplePuzzle[row][col];
                originalGrid[row][col] = samplePuzzle[row][col];
                isOriginal[row][col] = (samplePuzzle[row][col] != 0);
            }
        }
        
        drawGrid();
        updateStatus("Sample puzzle loaded. Click Solve to find the solution.");
    }
    
    /**
     * Solves the Sudoku puzzle using backtracking algorithm
     */
    private void solvePuzzle() {
        // Validate input first
        if (!isValidInput()) {
            updateStatus("Invalid Input - Check for duplicate numbers in rows, columns, or 3x3 boxes.");
            return;
        }
        
        // Create a copy for solving
        int[][] workingGrid = new int[GRID_SIZE][GRID_SIZE];
        for (int row = 0; row < GRID_SIZE; row++) {
            System.arraycopy(grid[row], 0, workingGrid[row], 0, GRID_SIZE);
        }
        
        // Solve using backtracking
        if (solveSudoku(workingGrid)) {
            // Copy solution back to display grid
            for (int row = 0; row < GRID_SIZE; row++) {
                System.arraycopy(workingGrid[row], 0, grid[row], 0, GRID_SIZE);
            }
            drawGrid();
            updateStatus("Solution found!");
        } else {
            updateStatus("No solution exists for this puzzle.");
        }
    }
    
    /**
     * Clears the puzzle
     */
    private void clearPuzzle() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                grid[row][col] = 0;
                isOriginal[row][col] = false;
            }
        }
        drawGrid();
        updateStatus("Puzzle cleared. Click cells to enter numbers (1→2→3...→9→0) or load sample puzzle.");
    }
    
    /**
     * Validates the current input for Sudoku rules
     */
    private boolean isValidInput() {
        // Check rows
        for (int row = 0; row < GRID_SIZE; row++) {
            boolean[] used = new boolean[10];
            for (int col = 0; col < GRID_SIZE; col++) {
                int value = grid[row][col];
                if (value != 0) {
                    if (used[value]) {
                        return false; // Duplicate in row
                    }
                    used[value] = true;
                }
            }
        }
        
        // Check columns
        for (int col = 0; col < GRID_SIZE; col++) {
            boolean[] used = new boolean[10];
            for (int row = 0; row < GRID_SIZE; row++) {
                int value = grid[row][col];
                if (value != 0) {
                    if (used[value]) {
                        return false; // Duplicate in column
                    }
                    used[value] = true;
                }
            }
        }
        
        // Check 3x3 boxes
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                boolean[] used = new boolean[10];
                for (int row = boxRow * 3; row < boxRow * 3 + 3; row++) {
                    for (int col = boxCol * 3; col < boxCol * 3 + 3; col++) {
                        int value = grid[row][col];
                        if (value != 0) {
                            if (used[value]) {
                                return false; // Duplicate in 3x3 box
                            }
                            used[value] = true;
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    /**
     * Solves Sudoku using backtracking algorithm
     * Time Complexity: O(9^(n*n)) where n is the number of empty cells
     * Space Complexity: O(n*n) for the recursion stack
     */
    private boolean solveSudoku(int[][] grid) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid[row][col] == 0) {
                    // Try each number 1-9
                    for (int num = 1; num <= 9; num++) {
                        if (isValidPlacement(grid, row, col, num)) {
                            grid[row][col] = num;
                            
                            // Recursively solve the rest
                            if (solveSudoku(grid)) {
                                return true;
                            }
                            
                            // Backtrack if no solution found
                            grid[row][col] = 0;
                        }
                    }
                    return false; // No valid number found
                }
            }
        }
        return true; // All cells filled
    }
    
    /**
     * Checks if placing a number at given position is valid
     */
    private boolean isValidPlacement(int[][] grid, int row, int col, int num) {
        // Check row
        for (int c = 0; c < GRID_SIZE; c++) {
            if (grid[row][c] == num) {
                return false;
            }
        }
        
        // Check column
        for (int r = 0; r < GRID_SIZE; r++) {
            if (grid[r][col] == num) {
                return false;
            }
        }
        
        // Check 3x3 box
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int r = boxRow; r < boxRow + 3; r++) {
            for (int c = boxCol; c < boxCol + 3; c++) {
                if (grid[r][c] == num) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Draws the Sudoku grid on the canvas with ACU brand colors
     */
    private void drawGrid() {
        // Clear canvas with ACU purple background
        gc.setFill(Color.web(ACU_PURPLE));
        gc.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);
        
        // Set up drawing parameters with ACU colors
        gc.setStroke(Color.web(ACU_WHITE));
        gc.setLineWidth(1);
        gc.setFont(new Font("Arial", 16));
        
        // Draw grid lines
        for (int i = 0; i <= GRID_SIZE; i++) {
            double pos = i * CELL_SIZE;
            
            // Thicker lines for 3x3 box boundaries with ACU red color
            if (i % 3 == 0) {
                gc.setLineWidth(3);
                gc.setStroke(Color.web(ACU_RED));
            } else {
                gc.setLineWidth(1);
                gc.setStroke(Color.web(ACU_WHITE));
            }
            
            // Vertical lines
            gc.strokeLine(pos, 0, pos, CANVAS_SIZE);
            // Horizontal lines
            gc.strokeLine(0, pos, CANVAS_SIZE, pos);
        }
        
        // Draw numbers with ACU brand colors
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int value = grid[row][col];
                if (value != 0) {
                    // Different color for original numbers vs user input using ACU colors
                    if (isOriginal[row][col]) {
                        gc.setFill(Color.web(ACU_RED)); // Original numbers in ACU red
                    } else {
                        gc.setFill(Color.web(ACU_WHITE)); // User input in ACU white
                    }
                    
                    // Center the number in the cell
                    double x = col * CELL_SIZE + CELL_SIZE / 2;
                    double y = row * CELL_SIZE + CELL_SIZE / 2;
                    
                    gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER);
                    gc.fillText(String.valueOf(value), x, y);
                }
            }
        }
    }
    
    /**
     * Updates the status label
     */
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
