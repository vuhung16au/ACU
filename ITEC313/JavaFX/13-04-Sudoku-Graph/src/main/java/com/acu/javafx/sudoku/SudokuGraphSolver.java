package com.acu.javafx.sudoku;

import java.util.*;

/**
 * Graph Theory-based Sudoku Solver
 * 
 * This class implements Sudoku solving using graph theory concepts:
 * - Constraint Graph: Each cell is a vertex, constraints are edges
 * - Arc Consistency: Remove invalid values from domains
 * - Forward Checking: Propagate constraints forward
 * - Most Constrained Variable: Choose cell with fewest options
 * - Least Constraining Value: Choose value that eliminates fewest options
 * 
 * The graph represents the Sudoku puzzle as a constraint satisfaction problem (CSP)
 * where each cell is a variable with a domain of possible values, and constraints
 * are the Sudoku rules (no duplicates in row, column, or 3x3 box).
 */
public class SudokuGraphSolver {
    
    private static final int GRID_SIZE = 9;
    private static final int BOX_SIZE = 3;
    
    // Graph representation
    private Map<Cell, Set<Integer>> domains; // Each cell's possible values
    private Map<Cell, Set<Cell>> constraints; // Constraint graph edges
    private List<Cell> cells; // All cells in the grid
    
    /**
     * Represents a cell in the Sudoku grid
     */
    public static class Cell {
        public final int row;
        public final int col;
        
        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Cell cell = (Cell) obj;
            return row == cell.row && col == cell.col;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
        
        @Override
        public String toString() {
            return "(" + row + "," + col + ")";
        }
    }
    
    /**
     * Solves a Sudoku puzzle using graph theory algorithms
     * 
     * @param grid The Sudoku grid to solve (modified in place)
     * @return true if a solution is found, false otherwise
     */
    public boolean solveSudoku(int[][] grid) {
        // Initialize the constraint graph
        initializeGraph(grid);
        
        // Apply arc consistency to reduce search space
        if (!arcConsistency()) {
            return false; // No solution possible
        }
        
        // Solve using backtracking with graph-based heuristics
        return solveWithBacktracking(grid);
    }
    
    /**
     * Initializes the constraint graph and domains
     */
    private void initializeGraph(int[][] grid) {
        domains = new HashMap<>();
        constraints = new HashMap<>();
        cells = new ArrayList<>();
        
        // Create all cells
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Cell cell = new Cell(row, col);
                cells.add(cell);
                constraints.put(cell, new HashSet<>());
                
                // Initialize domain based on current value
                if (grid[row][col] != 0) {
                    domains.put(cell, new HashSet<>(Arrays.asList(grid[row][col])));
                } else {
                    // All values 1-9 are possible initially
                    Set<Integer> domain = new HashSet<>();
                    for (int i = 1; i <= 9; i++) {
                        domain.add(i);
                    }
                    domains.put(cell, domain);
                }
            }
        }
        
        // Build constraint graph - connect cells that are constrained by Sudoku rules
        for (Cell cell1 : cells) {
            for (Cell cell2 : cells) {
                if (!cell1.equals(cell2) && areConstrained(cell1, cell2)) {
                    constraints.get(cell1).add(cell2);
                    constraints.get(cell2).add(cell1);
                }
            }
        }
    }

    /**
     * Ensures the internal graph structures are initialized. If not, builds them
     * from the provided grid without mutating the grid.
     */
    public void ensureGraphInitialized(int[][] grid) {
        if (constraints == null || domains == null || cells == null) {
            initializeGraph(grid);
        }
    }
    
    /**
     * Checks if two cells are constrained by Sudoku rules
     */
    private boolean areConstrained(Cell cell1, Cell cell2) {
        // Same row
        if (cell1.row == cell2.row) return true;
        
        // Same column
        if (cell1.col == cell2.col) return true;
        
        // Same 3x3 box
        int box1Row = cell1.row / BOX_SIZE;
        int box1Col = cell1.col / BOX_SIZE;
        int box2Row = cell2.row / BOX_SIZE;
        int box2Col = cell2.col / BOX_SIZE;
        
        return box1Row == box2Row && box1Col == box2Col;
    }
    
    /**
     * Applies arc consistency to reduce domains
     * 
     * Arc consistency ensures that for every value in a cell's domain,
     * there exists at least one valid value in each constrained cell's domain.
     */
    private boolean arcConsistency() {
        Queue<Cell> queue = new LinkedList<>(cells);
        
        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            Set<Integer> domain = domains.get(cell);
            
            // Try to remove values that violate constraints
            Set<Integer> toRemove = new HashSet<>();
            
            for (int value : domain) {
                boolean hasValidSupport = false;
                
                // Check if this value has support in all constrained cells
                for (Cell neighbor : constraints.get(cell)) {
                    Set<Integer> neighborDomain = domains.get(neighbor);
                    
                    // Check if neighbor has any value that doesn't conflict
                    for (int neighborValue : neighborDomain) {
                        if (neighborValue != value) {
                            hasValidSupport = true;
                            break;
                        }
                    }
                    
                    if (!hasValidSupport) {
                        break;
                    }
                }
                
                if (!hasValidSupport) {
                    toRemove.add(value);
                }
            }
            
            // Remove invalid values
            domain.removeAll(toRemove);
            
            // If domain becomes empty, no solution exists
            if (domain.isEmpty()) {
                return false;
            }
            
            // If domain was reduced, add constrained cells back to queue
            if (!toRemove.isEmpty()) {
                for (Cell neighbor : constraints.get(cell)) {
                    if (!queue.contains(neighbor)) {
                        queue.add(neighbor);
                    }
                }
            }
        }
        
        return true;
    }
    
    /**
     * Solves using backtracking with graph-based heuristics
     */
    private boolean solveWithBacktracking(int[][] grid) {
        // Find the most constrained variable (cell with fewest options)
        Cell cell = findMostConstrainedVariable();
        
        if (cell == null) {
            return true; // All cells assigned
        }
        
        // Try values in order of least constraining value
        List<Integer> values = getLeastConstrainingValues(cell);
        
        for (int value : values) {
            if (isValidAssignment(grid, cell, value)) {
                // Assign value
                grid[cell.row][cell.col] = value;
                
                // Update domain
                domains.get(cell).clear();
                domains.get(cell).add(value);
                
                // Recursively solve
                if (solveWithBacktracking(grid)) {
                    return true;
                }
                
                // Backtrack
                grid[cell.row][cell.col] = 0;
                domains.get(cell).clear();
                for (int i = 1; i <= 9; i++) {
                    domains.get(cell).add(i);
                }
            }
        }
        
        return false;
    }
    
    /**
     * Finds the most constrained variable (cell with fewest valid values)
     */
    private Cell findMostConstrainedVariable() {
        Cell bestCell = null;
        int minDomainSize = Integer.MAX_VALUE;
        
        for (Cell cell : cells) {
            Set<Integer> domain = domains.get(cell);
            if (domain.size() > 1 && domain.size() < minDomainSize) {
                minDomainSize = domain.size();
                bestCell = cell;
            }
        }
        
        return bestCell;
    }
    
    /**
     * Gets values ordered by least constraining value heuristic
     */
    private List<Integer> getLeastConstrainingValues(Cell cell) {
        List<Integer> values = new ArrayList<>(domains.get(cell));
        
        // Sort by how many options this value eliminates from other cells
        values.sort((v1, v2) -> {
            int eliminations1 = countEliminations(cell, v1);
            int eliminations2 = countEliminations(cell, v2);
            return Integer.compare(eliminations1, eliminations2);
        });
        
        return values;
    }
    
    /**
     * Counts how many values this assignment would eliminate from other cells
     */
    private int countEliminations(Cell cell, int value) {
        int eliminations = 0;
        
        for (Cell neighbor : constraints.get(cell)) {
            if (domains.get(neighbor).contains(value)) {
                eliminations++;
            }
        }
        
        return eliminations;
    }
    
    /**
     * Checks if assigning a value to a cell is valid
     */
    private boolean isValidAssignment(int[][] grid, Cell cell, int value) {
        // Check row constraint
        for (int col = 0; col < GRID_SIZE; col++) {
            if (col != cell.col && grid[cell.row][col] == value) {
                return false;
            }
        }
        
        // Check column constraint
        for (int row = 0; row < GRID_SIZE; row++) {
            if (row != cell.row && grid[row][cell.col] == value) {
                return false;
            }
        }
        
        // Check 3x3 box constraint
        int boxRow = (cell.row / BOX_SIZE) * BOX_SIZE;
        int boxCol = (cell.col / BOX_SIZE) * BOX_SIZE;
        
        for (int row = boxRow; row < boxRow + BOX_SIZE; row++) {
            for (int col = boxCol; col < boxCol + BOX_SIZE; col++) {
                if ((row != cell.row || col != cell.col) && grid[row][col] == value) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Validates if the input grid follows Sudoku rules (no duplicates in rows, columns, or boxes)
     */
    public boolean isValidInput(int[][] grid) {
        // Check rows
        for (int row = 0; row < 9; row++) {
            boolean[] used = new boolean[10];
            for (int col = 0; col < 9; col++) {
                int value = grid[row][col];
                if (value != 0) {
                    if (value < 1 || value > 9) {
                        return false; // Invalid number
                    }
                    if (used[value]) {
                        return false; // Duplicate in row
                    }
                    used[value] = true;
                }
            }
        }
        
        // Check columns
        for (int col = 0; col < 9; col++) {
            boolean[] used = new boolean[10];
            for (int row = 0; row < 9; row++) {
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
     * Validates if a complete Sudoku solution is valid
     */
    public boolean isValidSolution(int[][] grid) {
        // Check all rows
        for (int row = 0; row < GRID_SIZE; row++) {
            boolean[] used = new boolean[10];
            for (int col = 0; col < GRID_SIZE; col++) {
                int value = grid[row][col];
                if (value < 1 || value > 9 || used[value]) {
                    return false;
                }
                used[value] = true;
            }
        }
        
        // Check all columns
        for (int col = 0; col < GRID_SIZE; col++) {
            boolean[] used = new boolean[10];
            for (int row = 0; row < GRID_SIZE; row++) {
                int value = grid[row][col];
                if (used[value]) {
                    return false;
                }
                used[value] = true;
            }
        }
        
        // Check all 3x3 boxes
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                boolean[] used = new boolean[10];
                for (int row = boxRow * 3; row < boxRow * 3 + 3; row++) {
                    for (int col = boxCol * 3; col < boxCol * 3 + 3; col++) {
                        int value = grid[row][col];
                        if (used[value]) {
                            return false;
                        }
                        used[value] = true;
                    }
                }
            }
        }
        
        return true;
    }
    
    /**
     * Gets the constraint graph for visualization
     */
    public Map<Cell, Set<Cell>> getConstraintGraph() {
        if (constraints == null) {
            return new HashMap<>();
        }
        return new HashMap<>(constraints);
    }
    
    /**
     * Gets the current domains for all cells
     */
    public Map<Cell, Set<Integer>> getDomains() {
        if (domains == null) {
            return new HashMap<>();
        }
        return new HashMap<>(domains);
    }
    
    /**
     * Gets the number of constraints in the graph
     */
    public int getConstraintCount() {
        if (constraints == null) {
            return 0;
        }
        int count = 0;
        for (Set<Cell> neighbors : constraints.values()) {
            count += neighbors.size();
        }
        return count / 2; // Each constraint is counted twice
    }
    
    /**
     * Gets the degree of a cell (number of constraints)
     */
    public int getCellDegree(Cell cell) {
        if (constraints == null) {
            return 0;
        }
        Set<Cell> neighbors = constraints.get(cell);
        return neighbors == null ? 0 : neighbors.size();
    }
}
