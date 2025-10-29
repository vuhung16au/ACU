package com.acu.javafx.sudoku;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Map;
import java.util.Set;

/**
 * Sudoku Solver Application with Graph Theory Implementation
 * 
 * This application provides a graphical interface for solving Sudoku puzzles using
 * both traditional backtracking and advanced graph theory algorithms.
 * 
 * Features:
 * - Interactive 9x9 grid for input with color-coded numbers
 * - Sample puzzle loading
 * - Traditional backtracking algorithm
 * - Graph theory-based solving with constraint satisfaction
 * - Constraint graph visualization
 * - Help, About, Algorithm, and Strategy information dialogs
 * - Check solution functionality
 * - Clear functionality
 * - Visual feedback for invalid inputs
 * - ACU brand color scheme
 */
public class SudokuSolver extends Application {
    
    // Constants for the Sudoku grid
    private static final int GRID_SIZE = 9;
    private static final int CELL_SIZE = 50;
    private static final int CANVAS_SIZE = GRID_SIZE * CELL_SIZE;
    
    // ACU Brand Colors
    private static final String ACU_PURPLE = "#3C1053";
    private static final String ACU_WHITE = "#FFFFFF";
    private static final String ACU_RED = "#F2120C";
    private static final String ACU_WARM_STONE = "#918B83";
    private static final String ACU_DEEP_CHARCOAL = "#302C2A";
    private static final String ACU_SOFT_IVORY = "#F2EFEB";
    
    // Number colors for different values 1-9
    private static final String[] NUMBER_COLORS = {
        "#3C1053", // 1 - Purple
        "#F2120C", // 2 - Red
        "#0066CC", // 3 - Blue
        "#00AA44", // 4 - Green
        "#FF6600", // 5 - Orange
        "#9900CC", // 6 - Violet
        "#CC6600", // 7 - Brown
        "#006699", // 8 - Teal
        "#CC0066"  // 9 - Pink
    };
    
    // UI Components
    private Canvas canvas;
    private GraphicsContext gc;
    private Button loadSampleButton;
    private Button solveButton;
    private Button clearButton;
    private Button checkButton;
    private Button helpButton;
    private Button aboutButton;
    private Button algorithmButton;
    private Button strategyButton;
    private Button graphButton;
    private Label statusLabel;
    
    // Graph Theory solver
    private SudokuGraphSolver graphSolver;
    
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
        // Initialize Graph Theory solver
        graphSolver = new SudokuGraphSolver();
        
        // Initialize the canvas
        canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
        gc = canvas.getGraphicsContext2D();
        
        // Create the scene first to initialize UI components
        Scene scene = new Scene(createRootPane(), 800, 900);
        
        // Set ACU brand background color
        scene.setFill(Color.web(ACU_SOFT_IVORY));
        
        // Set up event handlers after UI components are created
        setupEventHandlers();
        
        // Draw the initial grid
        drawGrid();
        
        primaryStage.setTitle("Sudoku Solver - Graph Theory Implementation");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    
    /**
     * Creates the main UI layout
     */
    private BorderPane createRootPane() {
        BorderPane root = new BorderPane();
        
        // Top: Status label with instructions
        statusLabel = new Label("How to enter numbers: Click a cell to add 1, click again to change 1→2→3...→9→0. Use Graph Theory solver for advanced solving.");
        statusLabel.setFont(new Font(14));
        statusLabel.setStyle("-fx-text-fill: " + ACU_WHITE + "; -fx-background-color: " + ACU_PURPLE + "; -fx-padding: 10;");
        statusLabel.setWrapText(true);
        root.setTop(statusLabel);
        
        // Center: Canvas
        root.setCenter(canvas);
        
        // Bottom: Buttons in multiple rows
        VBox buttonContainer = new VBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10));
        
        // Main action buttons
        HBox mainButtons = new HBox(10);
        mainButtons.setAlignment(Pos.CENTER);
        
        loadSampleButton = new Button("Load Sample");
        solveButton = new Button("Solve (Graph Theory)");
        clearButton = new Button("Clear");
        checkButton = new Button("Check Solution");
        
        // Information buttons
        HBox infoButtons = new HBox(10);
        infoButtons.setAlignment(Pos.CENTER);
        
        helpButton = new Button("Help");
        aboutButton = new Button("About");
        algorithmButton = new Button("Algorithm");
        strategyButton = new Button("Strategy");
        graphButton = new Button("Show Graph");
        
        // Style buttons with ACU brand colors
        String buttonStyle = "-fx-background-color: " + ACU_WHITE + "; " +
                           "-fx-text-fill: " + ACU_PURPLE + "; " +
                           "-fx-font-weight: bold; " +
                           "-fx-padding: 8 16; " +
                           "-fx-border-color: " + ACU_RED + "; " +
                           "-fx-border-width: 2; " +
                           "-fx-border-radius: 5; " +
                           "-fx-background-radius: 5;";
        
        String infoButtonStyle = "-fx-background-color: " + ACU_PURPLE + "; " +
                               "-fx-text-fill: " + ACU_WHITE + "; " +
                               "-fx-font-weight: bold; " +
                               "-fx-padding: 6 12; " +
                               "-fx-border-color: " + ACU_RED + "; " +
                               "-fx-border-width: 1; " +
                               "-fx-border-radius: 5; " +
                               "-fx-background-radius: 5;";
        
        // Apply styles
        loadSampleButton.setStyle(buttonStyle);
        solveButton.setStyle(buttonStyle);
        clearButton.setStyle(buttonStyle);
        checkButton.setStyle(buttonStyle);
        
        helpButton.setStyle(infoButtonStyle);
        aboutButton.setStyle(infoButtonStyle);
        algorithmButton.setStyle(infoButtonStyle);
        strategyButton.setStyle(infoButtonStyle);
        graphButton.setStyle(infoButtonStyle);
        
        mainButtons.getChildren().addAll(loadSampleButton, solveButton, clearButton, checkButton);
        infoButtons.getChildren().addAll(helpButton, aboutButton, algorithmButton, strategyButton, graphButton);
        
        buttonContainer.getChildren().addAll(mainButtons, infoButtons);
        root.setBottom(buttonContainer);
        
        return root;
    }
    
    /**
     * Sets up event handlers for all interactive components
     */
    private void setupEventHandlers() {
        // Canvas click handler for input
        canvas.setOnMouseClicked(this::handleCanvasClick);
        
        // Main action button handlers
        loadSampleButton.setOnAction(e -> loadSamplePuzzle());
        solveButton.setOnAction(e -> solvePuzzle());
        clearButton.setOnAction(e -> clearPuzzle());
        checkButton.setOnAction(e -> checkSolution());
        
        // Information button handlers
        helpButton.setOnAction(e -> showHelpDialog());
        aboutButton.setOnAction(e -> showAboutDialog());
        algorithmButton.setOnAction(e -> showAlgorithmDialog());
        strategyButton.setOnAction(e -> showStrategyDialog());
        graphButton.setOnAction(e -> showGraphDialog());
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
     * Solves the Sudoku puzzle using Graph Theory algorithm
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
        
        // Solve using graph theory algorithm
        if (graphSolver.solveSudoku(workingGrid)) {
            // Copy solution back to display grid
            for (int row = 0; row < GRID_SIZE; row++) {
                System.arraycopy(workingGrid[row], 0, grid[row], 0, GRID_SIZE);
            }
            drawGrid();
            updateStatus("Solution found using Graph Theory! Constraint graph has " + graphSolver.getConstraintCount() + " constraints.");
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
     * Draws the Sudoku grid on the canvas with ACU brand colors and color-coded numbers
     */
    private void drawGrid() {
        // Clear canvas with ACU soft ivory background
        gc.setFill(Color.web(ACU_SOFT_IVORY));
        gc.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);
        
        // Set up drawing parameters with ACU colors
        gc.setStroke(Color.web(ACU_DEEP_CHARCOAL));
        gc.setLineWidth(1);
        gc.setFont(new Font("Arial", 18));
        
        // Draw grid lines
        for (int i = 0; i <= GRID_SIZE; i++) {
            double pos = i * CELL_SIZE;
            
            // Thicker lines for 3x3 box boundaries with ACU red color
            if (i % 3 == 0) {
                gc.setLineWidth(3);
                gc.setStroke(Color.web(ACU_RED));
            } else {
                gc.setLineWidth(1);
                gc.setStroke(Color.web(ACU_DEEP_CHARCOAL));
            }
            
            // Vertical lines
            gc.strokeLine(pos, 0, pos, CANVAS_SIZE);
            // Horizontal lines
            gc.strokeLine(0, pos, CANVAS_SIZE, pos);
        }
        
        // Draw numbers with color-coded values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int value = grid[row][col];
                if (value != 0) {
                    // Color-code numbers 1-9 with different colors
                    String numberColor;
                    if (isOriginal[row][col]) {
                        // Original numbers in bold with darker colors
                        numberColor = NUMBER_COLORS[value - 1];
                        gc.setFont(new Font("Arial Bold", 20));
                    } else {
                        // User input in lighter colors
                        numberColor = NUMBER_COLORS[value - 1];
                        gc.setFont(new Font("Arial", 18));
                    }
                    
                    gc.setFill(Color.web(numberColor));
                    
                    // Center the number in the cell
                    double x = col * CELL_SIZE + CELL_SIZE / 2;
                    double y = row * CELL_SIZE + CELL_SIZE / 2 + 6; // Adjust for centering
                    
                    gc.setTextAlign(TextAlignment.CENTER);
                    gc.fillText(String.valueOf(value), x, y);
                } else {
                    // Draw empty cell with subtle background
                    gc.setFill(Color.web(ACU_WARM_STONE, 0.1));
                    gc.fillRect(col * CELL_SIZE + 2, row * CELL_SIZE + 2, 
                               CELL_SIZE - 4, CELL_SIZE - 4);
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
    
    /**
     * Checks if the current puzzle is solved correctly
     */
    private void checkSolution() {
        if (graphSolver.isValidSolution(grid)) {
            updateStatus("✓ Congratulations! The Sudoku is solved correctly!");
        } else {
            updateStatus("✗ The Sudoku is not solved correctly. Check for errors.");
        }
    }
    
    /**
     * Shows help dialog
     */
    private void showHelpDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help - How to Use Sudoku Solver");
        alert.setHeaderText("How to Use the Sudoku Solver");
        
        String helpText = "HOW TO USE:\n\n" +
                "1. INPUT NUMBERS:\n" +
                "   • Click any empty cell to cycle through numbers 1→2→3→4→5→6→7→8→9→0\n" +
                "   • Each number has a unique color for easy identification\n\n" +
                "2. SOLVE PUZZLE:\n" +
                "   • Click 'Solve (Graph Theory)' to solve using the CSP + Graph Theory algorithm\n" +
                "   • The solver uses Graph Theory with constraint satisfaction\n\n" +
                "3. CHECK SOLUTION:\n" +
                "   • Click 'Check Solution' to verify if your puzzle is correct\n\n" +
                "4. VISUALIZE GRAPH:\n" +
                "   • Click 'Show Graph' to see the constraint graph\n" +
                "   • Shows how cells are connected by Sudoku rules\n\n" +
                "5. GET INFORMATION:\n" +
                "   • Algorithm: Learn about the solving algorithm\n" +
                "   • Strategy: Understand the solving strategy\n" +
                "   • About: Learn about the application\n\n" +
                "SUDOKU RULES:\n" +
                "• Each row must contain numbers 1-9 without repetition\n" +
                "• Each column must contain numbers 1-9 without repetition\n" +
                "• Each 3×3 box must contain numbers 1-9 without repetition";
        
        TextArea area = new TextArea(helpText);
        area.setWrapText(true);
        area.setEditable(false);
        area.setPrefSize(600, 450);
        area.setStyle("-fx-control-inner-background: " + ACU_SOFT_IVORY + "; -fx-text-fill: " + ACU_DEEP_CHARCOAL + ";");
        alert.getDialogPane().setContent(area);
        alert.getDialogPane().setPrefSize(650, 500);
        alert.showAndWait();
    }
    
    /**
     * Shows about dialog
     */
    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Sudoku Solver");
        alert.setHeaderText("Sudoku Solver - Graph Theory Implementation");
        
        String aboutText = "ABOUT THIS APPLICATION:\n\n" +
                "This Sudoku Solver demonstrates advanced computer science concepts:\n\n" +
                "GRAPH THEORY:\n" +
                "• Constraint Satisfaction Problem (CSP)\n" +
                "• Constraint graph with 81 vertices (cells)\n" +
                "• Arc consistency for domain reduction\n" +
                "• Most Constrained Variable heuristic\n" +
                "• Least Constraining Value heuristic\n\n" +
                "ALGORITHMS:\n" +
                "• Graph-based constraint propagation\n" +
                "• Forward checking\n" +
                "• Constraint graph visualization\n\n" +
                "EDUCATIONAL VALUE:\n" +
                "• Learn graph theory concepts\n" +
                "• Understand constraint satisfaction\n" +
                "• See algorithm visualization\n" +
                "• Interactive learning experience\n\n" +
                "DEVELOPED FOR:\n" +
                "Australian Catholic University (ACU)\n" +
                "ITEC313 - Advanced Programming Concepts\n\n" +
                "Version: 1.0.0\n" +
                "Built with JavaFX and Graph Theory";
        
        TextArea area = new TextArea(aboutText);
        area.setWrapText(true);
        area.setEditable(false);
        area.setPrefSize(600, 500);
        area.setStyle("-fx-control-inner-background: " + ACU_SOFT_IVORY + "; -fx-text-fill: " + ACU_DEEP_CHARCOAL + ";");
        alert.getDialogPane().setContent(area);
        alert.getDialogPane().setPrefSize(650, 540);
        alert.showAndWait();
    }
    
    /**
     * Shows algorithm dialog
     */
    private void showAlgorithmDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Algorithm Information");
        alert.setHeaderText("Graph Theory Algorithm for Sudoku Solving");
        
        String algorithmText = "GRAPH THEORY ALGORITHM:\n\n" +
                "1. CONSTRAINT GRAPH CONSTRUCTION:\n" +
                "   • Each cell is a vertex in the graph\n" +
                "   • Edges connect cells that share constraints\n" +
                "   • Three types of constraints: row, column, 3×3 box\n\n" +
                "2. DOMAIN INITIALIZATION:\n" +
                "   • Each cell has a domain of possible values (1-9)\n" +
                "   • Pre-filled cells have single-value domains\n" +
                "   • Empty cells start with full domain\n\n" +
                "3. ARC CONSISTENCY:\n" +
                "   • Remove values that violate constraints\n" +
                "   • Propagate constraints through the graph\n" +
                "   • Reduce search space before solving\n\n" +
                "4. BACKTRACKING WITH HEURISTICS:\n" +
                "   • Most Constrained Variable: Choose cell with fewest options\n" +
                "   • Least Constraining Value: Choose value that eliminates fewest options\n" +
                "   • Forward checking: Update domains of affected cells\n\n" +
                "5. CONSTRAINT PROPAGATION:\n" +
                "   • When a value is assigned, update all constrained cells\n" +
                "   • Remove conflicting values from domains\n" +
                "   • Detect dead ends early\n\n" +
                "COMPLEXITY:\n" +
                "• Time: O(9^(empty_cells)) in worst case\n" +
                "• Space: O(81) for constraint graph\n" +
                "• Much better than naive backtracking due to heuristics";
        
        TextArea area = new TextArea(algorithmText);
        area.setWrapText(true);
        area.setEditable(false);
        area.setPrefSize(700, 520);
        area.setStyle("-fx-control-inner-background: " + ACU_SOFT_IVORY + "; -fx-text-fill: " + ACU_DEEP_CHARCOAL + ";");
        alert.getDialogPane().setContent(area);
        alert.getDialogPane().setPrefSize(740, 560);
        alert.showAndWait();
    }
    
    /**
     * Shows strategy dialog
     */
    private void showStrategyDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Solving Strategy");
        alert.setHeaderText("Graph Theory Solving Strategy");
        
        String strategyText = "SOLVING STRATEGY:\n\n" +
                "1. CONSTRAINT ANALYSIS:\n" +
                "   • Build constraint graph connecting related cells\n" +
                "   • Identify most constrained variables\n" +
                "   • Analyze constraint propagation effects\n\n" +
                "2. DOMAIN REDUCTION:\n" +
                "   • Apply arc consistency to eliminate invalid values\n" +
                "   • Use forward checking to update domains\n" +
                "   • Detect impossible assignments early\n\n" +
                "3. HEURISTIC SELECTION:\n" +
                "   • Most Constrained Variable (MCV):\n" +
                "     - Choose cell with fewest remaining options\n" +
                "     - Reduces branching factor\n" +
                "   • Least Constraining Value (LCV):\n" +
                "     - Choose value that eliminates fewest options\n" +
                "     - Preserves future choices\n\n" +
                "4. CONSTRAINT PROPAGATION:\n" +
                "   • When assigning a value, update all affected cells\n" +
                "   • Remove conflicting values from domains\n" +
                "   • Maintain arc consistency throughout search\n\n" +
                "5. BACKTRACKING:\n" +
                "   • If no valid assignment found, backtrack\n" +
                "   • Restore previous state and try next option\n" +
                "   • Use constraint graph to guide search\n\n" +
                "ADVANTAGES OVER NAIVE BACKTRACKING:\n" +
                "• Faster solving through constraint propagation\n" +
                "• Better variable ordering with MCV heuristic\n" +
                "• Early detection of dead ends\n" +
                "• Reduced search space through domain reduction";
        
        TextArea area = new TextArea(strategyText);
        area.setWrapText(true);
        area.setEditable(false);
        area.setPrefSize(700, 520);
        area.setStyle("-fx-control-inner-background: " + ACU_SOFT_IVORY + "; -fx-text-fill: " + ACU_DEEP_CHARCOAL + ";");
        alert.getDialogPane().setContent(area);
        alert.getDialogPane().setPrefSize(740, 560);
        alert.showAndWait();
    }
    
    /**
     * Shows graph visualization dialog
     */
    private void showGraphDialog() {
        Stage graphStage = new Stage();
        graphStage.setTitle("Sudoku Constraint Graph Visualization");
        graphStage.initModality(Modality.WINDOW_MODAL);
        
        // Create canvas for graph visualization
        Canvas graphCanvas = new Canvas(600, 600);
        GraphicsContext graphGc = graphCanvas.getGraphicsContext2D();
        
        // Ensure the graph is initialized from the current grid, then get data
        graphSolver.ensureGraphInitialized(grid);
        Map<SudokuGraphSolver.Cell, Set<SudokuGraphSolver.Cell>> constraintGraph = graphSolver.getConstraintGraph();
        Map<SudokuGraphSolver.Cell, Set<Integer>> domains = graphSolver.getDomains();
        
        // Draw the constraint graph without legend (we'll show a readable legend at the side)
        SudokuGraphVisualizer.drawConstraintGraph(graphCanvas, constraintGraph, domains, null, false);
        
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: " + ACU_SOFT_IVORY + ";");
        
        Label titleLabel = new Label("Sudoku Constraint Graph");
        titleLabel.setFont(new Font("Arial Bold", 16));
        titleLabel.setStyle("-fx-text-fill: " + ACU_PURPLE + ";");
        
        // Legend and explanation on the right in a scrollable, non-editable text area
        String legendText = "Constraint Graph Legend\n\n" +
                "• Red edges: Row constraints\n" +
                "• Blue edges: Column constraints\n" +
                "• Green edges: Box constraints\n\n" +
                "• Numbers: Domain size\n" +
                "• Large numbers: Assigned values\n" +
                "• X: Empty domain (no solution)\n\n" +
                "Notes:\n" +
                "- The graph shows 81 vertices (cells).\n" +
                "- Edges connect cells that cannot share the same value.\n" +
                "- Domains are reduced via arc consistency and forward checking.";
        TextArea legendArea = new TextArea(legendText);
        legendArea.setWrapText(true);
        legendArea.setEditable(false);
        legendArea.setPrefWidth(280);
        legendArea.setStyle("-fx-control-inner-background: " + ACU_SOFT_IVORY + "; -fx-text-fill: " + ACU_DEEP_CHARCOAL + ";");
        
        Label infoLabel = new Label("This graph shows how Sudoku cells are connected through constraints.\n" +
                "Red edges = row constraints, Blue edges = column constraints, Green edges = box constraints");
        infoLabel.setFont(new Font("Arial", 12));
        infoLabel.setStyle("-fx-text-fill: " + ACU_DEEP_CHARCOAL + ";");
        infoLabel.setWrapText(true);
        
        HBox content = new HBox(12);
        content.getChildren().addAll(graphCanvas, legendArea);
        
        root.getChildren().addAll(titleLabel, content, infoLabel);
        
        ScrollPane scroller = new ScrollPane(root);
        scroller.setFitToWidth(true);
        scroller.setPannable(true);
        
        Scene scene = new Scene(scroller, 1000, 700);
        graphStage.setScene(scene);
        graphStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
