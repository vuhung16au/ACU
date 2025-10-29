package com.acu.javafx.sudoku;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.Map;
import java.util.Set;

/**
 * Graph Visualization Utility for Sudoku Constraint Graph
 * 
 * This class provides visualization capabilities for the Sudoku constraint graph,
 * showing how cells are connected through Sudoku rules (row, column, and 3x3 box constraints).
 * 
 * The visualization helps understand:
 * - Constraint relationships between cells
 * - Graph structure and connectivity
 * - Domain reduction during solving
 * - Most constrained variables
 */
public class SudokuGraphVisualizer {
    
    private static final int GRID_SIZE = 9;
    private static final int CELL_SIZE = 50;
    private static final int CANVAS_SIZE = GRID_SIZE * CELL_SIZE;
    
    // ACU Brand Colors
    private static final String ACU_PURPLE = "#3C1053";
    private static final String ACU_RED = "#F2120C";
    private static final String ACU_WHITE = "#FFFFFF";
    private static final String ACU_WARM_STONE = "#918B83";
    private static final String ACU_DEEP_CHARCOAL = "#302C2A";
    private static final String ACU_SOFT_IVORY = "#F2EFEB";
    
    /**
     * Draws the constraint graph on a canvas
     * 
     * @param canvas The canvas to draw on
     * @param constraintGraph The constraint graph to visualize
     * @param domains The current domains for each cell
     * @param highlightCell The cell to highlight (can be null)
     */
    public static void drawConstraintGraph(Canvas canvas, 
                                         Map<SudokuGraphSolver.Cell, Set<SudokuGraphSolver.Cell>> constraintGraph,
                                         Map<SudokuGraphSolver.Cell, Set<Integer>> domains,
                                         SudokuGraphSolver.Cell highlightCell) {
        drawConstraintGraph(canvas, constraintGraph, domains, highlightCell, true);
    }

    /**
     * Draws the constraint graph with an option to include the legend on-canvas
     */
    public static void drawConstraintGraph(Canvas canvas,
                                          Map<SudokuGraphSolver.Cell, Set<SudokuGraphSolver.Cell>> constraintGraph,
                                          Map<SudokuGraphSolver.Cell, Set<Integer>> domains,
                                          SudokuGraphSolver.Cell highlightCell,
                                          boolean includeLegend) {
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Clear canvas
        gc.setFill(Color.web(ACU_SOFT_IVORY));
        gc.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);
        
        // Draw grid lines
        drawGridLines(gc);
        
        // Draw constraint edges
        drawConstraintEdges(gc, constraintGraph);
        
        // Draw cells with domain information
        drawCellsWithDomains(gc, domains, highlightCell);
        
        // Draw legend if requested
        if (includeLegend) {
            drawLegend(gc);
        }
    }
    
    /**
     * Draws the Sudoku grid lines
     */
    private static void drawGridLines(GraphicsContext gc) {
        gc.setStroke(Color.web(ACU_DEEP_CHARCOAL));
        gc.setLineWidth(1);
        
        // Draw all grid lines
        for (int i = 0; i <= GRID_SIZE; i++) {
            double pos = i * CELL_SIZE;
            
            // Thicker lines for 3x3 box boundaries
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
    }
    
    /**
     * Draws constraint edges between cells
     */
    private static void drawConstraintEdges(GraphicsContext gc, 
                                          Map<SudokuGraphSolver.Cell, Set<SudokuGraphSolver.Cell>> constraintGraph) {
        
        gc.setStroke(Color.web(ACU_PURPLE));
        gc.setLineWidth(1);
        
        for (Map.Entry<SudokuGraphSolver.Cell, Set<SudokuGraphSolver.Cell>> entry : constraintGraph.entrySet()) {
            SudokuGraphSolver.Cell cell1 = entry.getKey();
            Set<SudokuGraphSolver.Cell> neighbors = entry.getValue();
            
            double x1 = cell1.col * CELL_SIZE + CELL_SIZE / 2;
            double y1 = cell1.row * CELL_SIZE + CELL_SIZE / 2;
            
            for (SudokuGraphSolver.Cell cell2 : neighbors) {
                // Only draw edge once (avoid duplicates)
                if (cell1.row < cell2.row || (cell1.row == cell2.row && cell1.col < cell2.col)) {
                    double x2 = cell2.col * CELL_SIZE + CELL_SIZE / 2;
                    double y2 = cell2.row * CELL_SIZE + CELL_SIZE / 2;
                    
                    // Draw edge with different colors for different constraint types
                    if (cell1.row == cell2.row) {
                        // Row constraint - red
                        gc.setStroke(Color.web(ACU_RED));
                    } else if (cell1.col == cell2.col) {
                        // Column constraint - blue
                        gc.setStroke(Color.web("#0066CC"));
                    } else {
                        // Box constraint - green
                        gc.setStroke(Color.web("#00AA44"));
                    }
                    
                    gc.strokeLine(x1, y1, x2, y2);
                }
            }
        }
    }
    
    /**
     * Draws cells with their domain information
     */
    private static void drawCellsWithDomains(GraphicsContext gc, 
                                           Map<SudokuGraphSolver.Cell, Set<Integer>> domains,
                                           SudokuGraphSolver.Cell highlightCell) {
        
        gc.setFont(new Font("Arial", 10));
        gc.setTextAlign(TextAlignment.CENTER);
        
        for (Map.Entry<SudokuGraphSolver.Cell, Set<Integer>> entry : domains.entrySet()) {
            SudokuGraphSolver.Cell cell = entry.getKey();
            Set<Integer> domain = entry.getValue();
            
            double x = cell.col * CELL_SIZE + CELL_SIZE / 2;
            double y = cell.row * CELL_SIZE + CELL_SIZE / 2;
            
            // Highlight the selected cell
            if (highlightCell != null && cell.equals(highlightCell)) {
                gc.setFill(Color.web(ACU_RED, 0.3));
                gc.fillRect(cell.col * CELL_SIZE + 2, cell.row * CELL_SIZE + 2, 
                           CELL_SIZE - 4, CELL_SIZE - 4);
            }
            
            // Draw domain size as a number
            if (domain.size() > 1) {
                gc.setFill(Color.web(ACU_PURPLE));
                gc.fillText(String.valueOf(domain.size()), x, y - 5);
            } else if (domain.size() == 1) {
                // Draw the single value
                int value = domain.iterator().next();
                gc.setFill(Color.web(ACU_DEEP_CHARCOAL));
                gc.setFont(new Font("Arial", 16));
                gc.fillText(String.valueOf(value), x, y + 5);
                gc.setFont(new Font("Arial", 10));
            } else {
                // Empty domain - draw X
                gc.setFill(Color.web(ACU_RED));
                gc.fillText("X", x, y);
            }
        }
    }
    
    /**
     * Draws a legend explaining the visualization
     */
    private static void drawLegend(GraphicsContext gc) {
        int legendX = CANVAS_SIZE + 20;
        int legendY = 20;
        
        gc.setFont(new Font("Arial", 12));
        gc.setTextAlign(TextAlignment.LEFT);
        
        // Title
        gc.setFill(Color.web(ACU_PURPLE));
        gc.fillText("Constraint Graph Legend", legendX, legendY);
        
        // Legend items
        legendY += 30;
        gc.setFill(Color.web(ACU_DEEP_CHARCOAL));
        gc.fillText("• Red edges: Row constraints", legendX, legendY);
        
        legendY += 20;
        gc.setFill(Color.web("#0066CC"));
        gc.fillText("• Blue edges: Column constraints", legendX, legendY);
        
        legendY += 20;
        gc.setFill(Color.web("#00AA44"));
        gc.fillText("• Green edges: Box constraints", legendX, legendY);
        
        legendY += 30;
        gc.setFill(Color.web(ACU_DEEP_CHARCOAL));
        gc.fillText("• Numbers: Domain size", legendX, legendY);
        
        legendY += 20;
        gc.setFill(Color.web(ACU_PURPLE));
        gc.fillText("• Large numbers: Assigned values", legendX, legendY);
        
        legendY += 20;
        gc.setFill(Color.web(ACU_RED));
        gc.fillText("• X: Empty domain (no solution)", legendX, legendY);
    }
    
    /**
     * Draws a simplified constraint graph showing only the most important constraints
     */
    public static void drawSimplifiedGraph(Canvas canvas, 
                                         Map<SudokuGraphSolver.Cell, Set<SudokuGraphSolver.Cell>> constraintGraph,
                                         SudokuGraphSolver.Cell focusCell) {
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Clear canvas
        gc.setFill(Color.web(ACU_SOFT_IVORY));
        gc.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);
        
        // Draw grid
        drawGridLines(gc);
        
        if (focusCell != null) {
            // Highlight focus cell
            gc.setFill(Color.web(ACU_RED, 0.5));
            gc.fillRect(focusCell.col * CELL_SIZE + 2, focusCell.row * CELL_SIZE + 2, 
                       CELL_SIZE - 4, CELL_SIZE - 4);
            
            // Draw only constraints for focus cell
            Set<SudokuGraphSolver.Cell> neighbors = constraintGraph.get(focusCell);
            if (neighbors != null) {
                gc.setStroke(Color.web(ACU_PURPLE));
                gc.setLineWidth(3);
                
                double x1 = focusCell.col * CELL_SIZE + CELL_SIZE / 2;
                double y1 = focusCell.row * CELL_SIZE + CELL_SIZE / 2;
                
                for (SudokuGraphSolver.Cell neighbor : neighbors) {
                    double x2 = neighbor.col * CELL_SIZE + CELL_SIZE / 2;
                    double y2 = neighbor.row * CELL_SIZE + CELL_SIZE / 2;
                    
                    gc.strokeLine(x1, y1, x2, y2);
                    
                    // Highlight neighbor
                    gc.setFill(Color.web(ACU_PURPLE, 0.3));
                    gc.fillRect(neighbor.col * CELL_SIZE + 2, neighbor.row * CELL_SIZE + 2, 
                               CELL_SIZE - 4, CELL_SIZE - 4);
                }
            }
        }
        
        // Draw cell coordinates
        gc.setFont(new Font("Arial", 8));
        gc.setFill(Color.web(ACU_DEEP_CHARCOAL));
        gc.setTextAlign(TextAlignment.CENTER);
        
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                double x = col * CELL_SIZE + CELL_SIZE / 2;
                double y = row * CELL_SIZE + CELL_SIZE - 5;
                gc.fillText("(" + row + "," + col + ")", x, y);
            }
        }
    }
    
    /**
     * Calculates the canvas size needed for the visualization
     */
    public static int getCanvasSize() {
        return CANVAS_SIZE;
    }
    
    /**
     * Gets the cell size used in the visualization
     */
    public static int getCellSize() {
        return CELL_SIZE;
    }
}
