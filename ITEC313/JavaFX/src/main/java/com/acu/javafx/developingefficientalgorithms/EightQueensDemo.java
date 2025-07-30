package com.acu.javafx.developingefficientalgorithms;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX demonstration of Eight Queens puzzle.
 * Shows backtracking algorithm to find valid queen placements.
 * Time Complexity: O(n!) - Factorial time complexity
 */
public class EightQueensDemo {
    private Stage stage;
    private TextArea outputArea;
    private Button solveButton;
    private Button findAllButton;
    private Button stepButton;
    private Label complexityLabel;
    private Label descriptionLabel;
    private int[] queens;
    private int currentSolution = 0;
    private List<int[]> allSolutions;

    public void show() {
        stage = new Stage();
        stage.setTitle("Eight Queens Puzzle Demo");
        stage.setWidth(900);
        stage.setHeight(700);

        // Initialize
        queens = new int[8];
        allSolutions = new ArrayList<>();

        // Create UI components
        createUI();

        Scene scene = new Scene(createMainLayout());
        stage.setScene(scene);
        stage.show();
    }

    private void createUI() {
        // Title
        Label titleLabel = new Label("Eight Queens Puzzle Demo");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Description
        descriptionLabel = new Label(
            "Eight Queens puzzle: Place 8 queens on an 8x8 chessboard so that no two queens\n" +
            "threaten each other (no two queens share the same row, column, or diagonal).\n" +
            "Uses backtracking algorithm: O(n!) time complexity\n" +
            "Space Complexity: O(n) - Linear space complexity"
        );
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px;");

        // Complexity information
        complexityLabel = new Label("Time Complexity: O(n!), Space Complexity: O(n)");
        complexityLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2E8B57;");

        // Control buttons
        solveButton = new Button("Find First Solution");
        solveButton.setOnAction(e -> findFirstSolution());
        
        findAllButton = new Button("Find All Solutions");
        findAllButton.setOnAction(e -> findAllSolutions());
        
        stepButton = new Button("Step-by-Step");
        stepButton.setOnAction(e -> solveStepByStep());

        // Output area
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(25);
        outputArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
    }

    private VBox createMainLayout() {
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));

        // Title and description
        VBox headerBox = new VBox(10);
        headerBox.getChildren().addAll(
            new Label("Eight Queens Puzzle Demo"),
            descriptionLabel,
            complexityLabel
        );

        // Control buttons
        VBox controlBox = new VBox(10);
        controlBox.getChildren().addAll(
            new Label("Algorithm Controls:"),
            createControlButtons()
        );

        // Board display
        VBox boardBox = new VBox(10);
        boardBox.getChildren().addAll(
            new Label("Current Board:"),
            createBoardDisplay()
        );

        // Output area
        VBox outputBox = new VBox(10);
        outputBox.getChildren().addAll(
            new Label("Results:"),
            outputArea
        );
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        mainLayout.getChildren().addAll(headerBox, controlBox, boardBox, outputBox);
        return mainLayout;
    }

    private HBox createControlButtons() {
        HBox buttonBox = new HBox(10);
        
        solveButton = new Button("Find First Solution");
        solveButton.setOnAction(e -> findFirstSolution());
        
        findAllButton = new Button("Find All Solutions");
        findAllButton.setOnAction(e -> findAllSolutions());
        
        stepButton = new Button("Step-by-Step");
        stepButton.setOnAction(e -> solveStepByStep());

        buttonBox.getChildren().addAll(solveButton, findAllButton, stepButton);
        return buttonBox;
    }

    private GridPane createBoardDisplay() {
        GridPane board = new GridPane();
        board.setHgap(2);
        board.setVgap(2);
        board.setStyle("-fx-background-color: #8B4513; -fx-padding: 5;");
        
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Rectangle cell = new Rectangle(40, 40);
                cell.setFill((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
                cell.setStroke(Color.BLACK);
                cell.setStrokeWidth(1);
                
                // Add queen if placed
                if (queens[row] == col) {
                    Label queen = new Label("♕");
                    queen.setStyle("-fx-font-size: 20px; -fx-text-fill: #8B0000;");
                    StackPane stack = new StackPane();
                    stack.getChildren().addAll(cell, queen);
                    board.add(stack, col, row);
                } else {
                    board.add(cell, col, row);
                }
            }
        }
        
        return board;
    }

    private void findFirstSolution() {
        outputArea.clear();
        outputArea.appendText("=== Finding First Solution ===\n\n");
        
        outputArea.appendText("Algorithm: Backtracking\n");
        outputArea.appendText("1. Place queens one by one in each row\n");
        outputArea.appendText("2. For each placement, check if it's safe\n");
        outputArea.appendText("3. If safe, move to next row; if not, backtrack\n");
        outputArea.appendText("4. Continue until all queens are placed\n\n");

        long startTime = System.nanoTime();
        boolean found = solveEightQueens(0);
        long time = System.nanoTime() - startTime;

        if (found) {
            outputArea.appendText("✓ Solution found!\n");
            outputArea.appendText("Queen positions (row, col):\n");
            for (int i = 0; i < 8; i++) {
                outputArea.appendText("Row " + i + ": Queen at column " + queens[i] + "\n");
            }
            outputArea.appendText("\nTime: " + time / 1000000.0 + " ms\n");
            outputArea.appendText("Time Complexity: O(n!)\n");
            outputArea.appendText("Space Complexity: O(n)\n");
            
            // Display board
            displayBoard();
        } else {
            outputArea.appendText("✗ No solution found (should not happen for 8 queens)\n");
        }
    }

    private void findAllSolutions() {
        outputArea.clear();
        outputArea.appendText("=== Finding All Solutions ===\n\n");
        
        outputArea.appendText("This will find all possible valid solutions...\n");
        outputArea.appendText("(There are 92 unique solutions for 8 queens)\n\n");

        allSolutions.clear();
        long startTime = System.nanoTime();
        findAllSolutions(0);
        long time = System.nanoTime() - startTime;

        outputArea.appendText("✓ Found " + allSolutions.size() + " solutions!\n");
        outputArea.appendText("Time: " + time / 1000000.0 + " ms\n");
        outputArea.appendText("Time Complexity: O(n!)\n");
        outputArea.appendText("Space Complexity: O(n)\n\n");

        // Show first few solutions
        int showCount = Math.min(5, allSolutions.size());
        outputArea.appendText("First " + showCount + " solutions:\n");
        for (int i = 0; i < showCount; i++) {
            outputArea.appendText("Solution " + (i + 1) + ": ");
            int[] solution = allSolutions.get(i);
            for (int j = 0; j < 8; j++) {
                outputArea.appendText(solution[j] + " ");
            }
            outputArea.appendText("\n");
        }
        
        if (allSolutions.size() > showCount) {
            outputArea.appendText("... and " + (allSolutions.size() - showCount) + " more solutions\n");
        }
    }

    private void solveStepByStep() {
        outputArea.clear();
        outputArea.appendText("=== Step-by-Step Solution ===\n\n");
        
        outputArea.appendText("Solving step by step...\n");
        outputArea.appendText("(This is a simplified demonstration)\n\n");

        // Reset queens
        for (int i = 0; i < 8; i++) {
            queens[i] = -1;
        }

        // Show first few steps
        outputArea.appendText("Step 1: Try placing queen in row 0, column 0\n");
        queens[0] = 0;
        outputArea.appendText("✓ Safe placement\n\n");

        outputArea.appendText("Step 2: Try placing queen in row 1, column 2\n");
        queens[1] = 2;
        outputArea.appendText("✓ Safe placement\n\n");

        outputArea.appendText("Step 3: Try placing queen in row 2, column 4\n");
        queens[2] = 4;
        outputArea.appendText("✓ Safe placement\n\n");

        outputArea.appendText("... continuing with backtracking algorithm\n");
        outputArea.appendText("(Full step-by-step would be very long)\n\n");

        // Complete the solution
        solveEightQueens(0);
        outputArea.appendText("Final solution found!\n");
        displayBoard();
    }

    private void displayBoard() {
        outputArea.appendText("\nBoard representation:\n");
        outputArea.appendText("  ");
        for (int col = 0; col < 8; col++) {
            outputArea.appendText(col + " ");
        }
        outputArea.appendText("\n");
        
        for (int row = 0; row < 8; row++) {
            outputArea.appendText(row + " ");
            for (int col = 0; col < 8; col++) {
                if (queens[row] == col) {
                    outputArea.appendText("Q ");
                } else {
                    outputArea.appendText(". ");
                }
            }
            outputArea.appendText("\n");
        }
    }

    /**
     * Solve Eight Queens using backtracking
     * @param row current row to place queen
     * @return true if solution found, false otherwise
     */
    private boolean solveEightQueens(int row) {
        if (row >= 8) {
            return true; // All queens placed successfully
        }

        for (int col = 0; col < 8; col++) {
            if (isSafe(row, col)) {
                queens[row] = col;
                
                if (solveEightQueens(row + 1)) {
                    return true;
                }
                
                queens[row] = -1; // Backtrack
            }
        }
        
        return false;
    }

    /**
     * Find all solutions using backtracking
     * @param row current row to place queen
     */
    private void findAllSolutions(int row) {
        if (row >= 8) {
            allSolutions.add(queens.clone());
            return;
        }

        for (int col = 0; col < 8; col++) {
            if (isSafe(row, col)) {
                queens[row] = col;
                findAllSolutions(row + 1);
                queens[row] = -1; // Backtrack
            }
        }
    }

    /**
     * Check if placing a queen at (row, col) is safe
     * @param row row to check
     * @param col column to check
     * @return true if safe, false otherwise
     */
    private boolean isSafe(int row, int col) {
        // Check column
        for (int i = 0; i < row; i++) {
            if (queens[i] == col) {
                return false;
            }
        }
        
        // Check diagonal (top-left to bottom-right)
        for (int i = 0; i < row; i++) {
            if (queens[i] - i == col - row) {
                return false;
            }
        }
        
        // Check diagonal (top-right to bottom-left)
        for (int i = 0; i < row; i++) {
            if (queens[i] + i == col + row) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Static method to solve Eight Queens
     * @return solution array, or null if no solution
     */
    public static int[] solveEightQueens() {
        int[] queens = new int[8];
        if (solveEightQueensRecursive(queens, 0)) {
            return queens;
        }
        return null;
    }

    private static boolean solveEightQueensRecursive(int[] queens, int row) {
        if (row >= 8) {
            return true;
        }

        for (int col = 0; col < 8; col++) {
            if (isSafePosition(queens, row, col)) {
                queens[row] = col;
                
                if (solveEightQueensRecursive(queens, row + 1)) {
                    return true;
                }
                
                queens[row] = -1;
            }
        }
        
        return false;
    }

    private static boolean isSafePosition(int[] queens, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (queens[i] == col || 
                queens[i] - i == col - row || 
                queens[i] + i == col + row) {
                return false;
            }
        }
        return true;
    }
} 