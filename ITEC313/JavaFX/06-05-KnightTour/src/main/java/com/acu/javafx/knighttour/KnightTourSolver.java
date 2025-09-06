package com.acu.javafx.knighttour;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * KnightTourSolver - Implements the Knight's Tour algorithm with animation
 * 
 * This class provides the core logic for solving the Knight's Tour problem:
 * - Uses backtracking algorithm to find a valid tour
 * - Provides animated visualization of the solution
 * - Supports different solving strategies
 * - Handles animation timing and callbacks
 * 
 * The Knight's Tour is a sequence of moves by a knight on a chessboard such that
 * the knight visits every square exactly once.
 * 
 * @author ACU JavaFX Team
 * @version 1.0.0
 */
public class KnightTourSolver {
    
    // Knight's possible moves (8 directions in L-shape)
    private static final int[][] KNIGHT_MOVES = {
        {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
        {1, -2}, {1, 2}, {2, -1}, {2, 1}
    };
    
    private final ChessBoard chessBoard;
    private final int boardSize;
    private boolean isAnimating = false;
    private List<ChessBoard.Position> solution;
    private int currentMoveIndex = 0;
    private AnimationTimer animationTimer;
    private Runnable onComplete;
    
    /**
     * Constructor for KnightTourSolver
     * 
     * @param chessBoard The chessboard to solve on
     */
    public KnightTourSolver(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        this.boardSize = 8;
    }
    
    /**
     * Solves the Knight's Tour with animation
     * 
     * @param onComplete Callback to execute when animation is complete
     */
    public void solveTour(Runnable onComplete) {
        this.onComplete = onComplete;
        this.isAnimating = true;
        
        // Find solution using backtracking
        CompletableFuture.supplyAsync(() -> findSolution())
            .thenAccept(solution -> {
                if (solution != null && !solution.isEmpty()) {
                    this.solution = solution;
                    startAnimation();
                } else {
                    // No solution found
                    this.isAnimating = false;
                    if (onComplete != null) {
                        onComplete.run();
                    }
                }
            });
    }
    
    /**
     * Finds a solution using backtracking algorithm
     * 
     * @return List of positions representing the tour, or null if no solution
     */
    private List<ChessBoard.Position> findSolution() {
        ChessBoard.Position startPos = chessBoard.getKnightPosition();
        if (startPos == null) {
            return null;
        }
        
        // Initialize board state
        boolean[][] visited = new boolean[boardSize][boardSize];
        List<ChessBoard.Position> path = new ArrayList<>();
        
        // Mark starting position as visited
        visited[startPos.row][startPos.col] = true;
        path.add(startPos);
        
        // Try to find a complete tour
        if (solveRecursive(startPos.row, startPos.col, visited, path, 1)) {
            return new ArrayList<>(path);
        }
        
        return null;
    }
    
    /**
     * Recursive backtracking method to find the Knight's Tour
     * 
     * @param row Current row position
     * @param col Current column position
     * @param visited 2D array tracking visited squares
     * @param path Current path of moves
     * @param moveCount Number of moves made so far
     * @return true if a complete tour is found, false otherwise
     */
    private boolean solveRecursive(int row, int col, boolean[][] visited, 
                                 List<ChessBoard.Position> path, int moveCount) {
        
        // Base case: if we've visited all squares, we have a solution
        if (moveCount == boardSize * boardSize) {
            return true;
        }
        
        // Try all possible knight moves
        for (int[] move : KNIGHT_MOVES) {
            int newRow = row + move[0];
            int newCol = col + move[1];
            
            // Check if the move is valid and the square hasn't been visited
            if (isValidMove(newRow, newCol, visited)) {
                // Make the move
                visited[newRow][newCol] = true;
                path.add(new ChessBoard.Position(newRow, newCol));
                
                // Recursively try to complete the tour from this position
                if (solveRecursive(newRow, newCol, visited, path, moveCount + 1)) {
                    return true;
                }
                
                // Backtrack: undo the move
                visited[newRow][newCol] = false;
                path.remove(path.size() - 1);
            }
        }
        
        return false;
    }
    
    /**
     * Checks if a move is valid
     * 
     * @param row Row position to check
     * @param col Column position to check
     * @param visited 2D array tracking visited squares
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidMove(int row, int col, boolean[][] visited) {
        return row >= 0 && row < boardSize && 
               col >= 0 && col < boardSize && 
               !visited[row][col];
    }
    
    /**
     * Starts the animation of the solution
     */
    private void startAnimation() {
        // Prepare the board for animation
        chessBoard.prepareForAnimation();
        
        currentMoveIndex = 1; // Start from index 1 since index 0 is the starting position
        
        animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;
            private final long ANIMATION_INTERVAL = 500_000_000; // 500ms in nanoseconds
            
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= ANIMATION_INTERVAL) {
                    if (currentMoveIndex < solution.size()) {
                        ChessBoard.Position pos = solution.get(currentMoveIndex);
                        chessBoard.animateKnightMove(pos.row, pos.col);
                        currentMoveIndex++;
                    } else {
                        // Animation complete
                        stop();
                        isAnimating = false;
                        if (onComplete != null) {
                            onComplete.run();
                        }
                    }
                    lastUpdate = now;
                }
            }
        };
        
        animationTimer.start();
    }
    
    /**
     * Resets the solver to initial state
     */
    public void reset() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        isAnimating = false;
        solution = null;
        currentMoveIndex = 0;
        onComplete = null;
    }
    
    /**
     * Checks if the solver is currently animating
     * 
     * @return true if animating, false otherwise
     */
    public boolean isAnimating() {
        return isAnimating;
    }
    
    /**
     * Gets the current solution path
     * 
     * @return List of positions in the solution, or null if no solution
     */
    public List<ChessBoard.Position> getSolution() {
        return solution != null ? new ArrayList<>(solution) : null;
    }
    
    /**
     * Gets the number of moves in the current solution
     * 
     * @return Number of moves, or 0 if no solution
     */
    public int getSolutionLength() {
        return solution != null ? solution.size() : 0;
    }
    
    /**
     * Checks if a position is reachable by a knight from another position
     * 
     * @param from Starting position
     * @param to Target position
     * @return true if the move is valid for a knight, false otherwise
     */
    public static boolean isValidKnightMove(ChessBoard.Position from, ChessBoard.Position to) {
        int rowDiff = Math.abs(to.row - from.row);
        int colDiff = Math.abs(to.col - from.col);
        
        // Knight moves in L-shape: 2 squares in one direction, 1 in perpendicular
        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }
    
    /**
     * Gets all possible knight moves from a given position
     * 
     * @param position Starting position
     * @return List of valid positions the knight can move to
     */
    public static List<ChessBoard.Position> getPossibleMoves(ChessBoard.Position position) {
        List<ChessBoard.Position> moves = new ArrayList<>();
        
        for (int[] move : KNIGHT_MOVES) {
            int newRow = position.row + move[0];
            int newCol = position.col + move[1];
            
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                moves.add(new ChessBoard.Position(newRow, newCol));
            }
        }
        
        return moves;
    }
}
