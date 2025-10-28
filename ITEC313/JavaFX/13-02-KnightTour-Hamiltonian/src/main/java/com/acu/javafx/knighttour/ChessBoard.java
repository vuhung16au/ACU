package com.acu.javafx.knighttour;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

/**
 * ChessBoard - Interactive 8x8 chessboard for Knight's Tour
 * 
 * This class provides a visual representation of a chessboard with:
 * - Alternating light and dark squares
 * - Interactive square clicking
 * - Knight piece display using Unicode symbol ♘
 * - Path visualization with lines connecting visited squares
 * - Move numbering display
 * 
 * @author ACU JavaFX Team
 * @version 1.0.0
 */
public class ChessBoard extends Canvas {
    
    private static final Color LIGHT_SQUARE = Color.WHITE;
    private static final Color DARK_SQUARE = Color.LIGHTGRAY;
    private static final Color KNIGHT_COLOR = Color.BLACK;
    private static final Color PATH_COLOR = Color.RED;
    private static final Color CYCLE_CLOSE_COLOR = Color.GREEN;
    private static final Color CURRENT_SQUARE = Color.YELLOW;
    
    private final int boardSize;
    private final int cellSize;
    private final int canvasSize;
    
    // Board state
    private int knightRow = -1;
    private int knightCol = -1;
    private boolean[][] visited;
    private List<Position> path;
    private int currentMove = 0; // retained for numbering
    
    // Event handling
    private SquareClickListener squareClickListener;
    
    /**
     * Constructor for ChessBoard
     * 
     * @param boardSize Size of the board (8x8)
     * @param cellSize Size of each cell in pixels
     */
    public ChessBoard(int boardSize, int cellSize) {
        this.boardSize = boardSize;
        this.cellSize = cellSize;
        this.canvasSize = boardSize * cellSize;
        
        // Set canvas size
        setWidth(canvasSize);
        setHeight(canvasSize);
        
        // Initialize board state
        reset();
        
        // Set up mouse event handler
        setOnMouseClicked(this::handleMouseClick);
        
        // Draw initial board
        drawBoard();
    }
    
    /**
     * Resets the board to initial state
     */
    public void reset() {
        knightRow = -1;
        knightCol = -1;
        visited = new boolean[boardSize][boardSize];
        path = new ArrayList<>();
        currentMove = 0;
        drawBoard();
    }
    
    /**
     * Places the knight at the specified position
     * 
     * @param row Row position (0-7)
     * @param col Column position (0-7)
     */
    public void placeKnight(int row, int col) {
        if (isValidPosition(row, col)) {
            knightRow = row;
            knightCol = col;
            visited[row][col] = true;
            path.clear();
            path.add(new Position(row, col));
            currentMove = 1;
            drawBoard();
        }
    }
    
    /**
     * Moves the knight to the specified position and adds it to the path
     * 
     * @param row Row position (0-7)
     * @param col Column position (0-7)
     */
    public void moveKnight(int row, int col) {
        if (isValidPosition(row, col)) {
            knightRow = row;
            knightCol = col;
            visited[row][col] = true;
            path.add(new Position(row, col));
            currentMove++;
            drawBoard();
        }
    }
    
    /**
     * Prepares the board for animation by clearing the path but keeping the starting position
     */
    public void prepareForAnimation() {
        // Clear the path but keep the starting position
        path.clear();
        path.add(new Position(knightRow, knightCol));
        currentMove = 1;
        // Reset visited array but keep starting position
        visited = new boolean[boardSize][boardSize];
        visited[knightRow][knightCol] = true;
        drawBoard();
    }
    
    /**
     * Moves the knight to the specified position during animation
     * 
     * @param row Row position (0-7)
     * @param col Column position (0-7)
     */
    public void animateKnightMove(int row, int col) {
        if (isValidPosition(row, col)) {
            knightRow = row;
            knightCol = col;
            visited[row][col] = true;
            path.add(new Position(row, col));
            currentMove++;
            drawBoard();
        }
    }
    
    /**
     * Highlights the current square being considered
     * 
     * @param row Row position
     * @param col Column position
     */
    public void highlightSquare(int row, int col) {
        if (isValidPosition(row, col)) {
            drawBoard();
            GraphicsContext gc = getGraphicsContext2D();
            gc.setFill(CURRENT_SQUARE);
            gc.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
            drawSquareBorder(gc, row, col);
        }
    }
    
    /**
     * Checks if the knight is placed on the board
     * 
     * @return true if knight is placed, false otherwise
     */
    public boolean hasKnight() {
        return knightRow >= 0 && knightCol >= 0;
    }
    
    /**
     * Gets the current knight position
     * 
     * @return Position object with knight's current row and column, or null if no knight is placed
     */
    public Position getKnightPosition() {
        if (knightRow < 0 || knightCol < 0) {
            return null;
        }
        return new Position(knightRow, knightCol);
    }
    
    /**
     * Gets the current path
     * 
     * @return List of positions in the current path
     */
    public List<Position> getPath() {
        return new ArrayList<>(path);
    }
    
    /**
     * Sets the square click listener
     * 
     * @param listener SquareClickListener to handle square clicks
     */
    public void setOnSquareClicked(SquareClickListener listener) {
        this.squareClickListener = listener;
    }
    
    /**
     * Handles mouse click events on the board
     * 
     * @param event MouseEvent from the canvas
     */
    private void handleMouseClick(MouseEvent event) {
        int col = (int) (event.getX() / cellSize);
        int row = (int) (event.getY() / cellSize);
        
        if (isValidPosition(row, col) && squareClickListener != null) {
            squareClickListener.onSquareClicked(row, col);
        }
    }
    
    /**
     * Draws the entire chessboard
     */
    private void drawBoard() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, canvasSize, canvasSize);
        
        // Draw squares
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                drawSquare(gc, row, col);
            }
        }
        
        // Draw path lines
        drawPath(gc);
        
        // Draw knight
        if (hasKnight()) {
            drawKnight(gc, knightRow, knightCol);
        }
        
        // Draw move numbers
        drawMoveNumbers(gc);
    }
    
    /**
     * Draws a single square
     * 
     * @param gc GraphicsContext for drawing
     * @param row Row position
     * @param col Column position
     */
    private void drawSquare(GraphicsContext gc, int row, int col) {
        Color squareColor = (row + col) % 2 == 0 ? LIGHT_SQUARE : DARK_SQUARE;
        gc.setFill(squareColor);
        gc.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
        drawSquareBorder(gc, row, col);
    }
    
    /**
     * Draws the border of a square
     * 
     * @param gc GraphicsContext for drawing
     * @param row Row position
     * @param col Column position
     */
    private void drawSquareBorder(GraphicsContext gc, int row, int col) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeRect(col * cellSize, row * cellSize, cellSize, cellSize);
    }
    
    /**
     * Draws the knight piece using Unicode symbol
     * 
     * @param gc GraphicsContext for drawing
     * @param row Row position
     * @param col Column position
     */
    private void drawKnight(GraphicsContext gc, int row, int col) {
        gc.setFill(KNIGHT_COLOR);
        gc.setFont(new Font("Arial", cellSize * 0.6));
        gc.setTextAlign(TextAlignment.CENTER);
        
        double x = col * cellSize + cellSize / 2.0;
        double y = row * cellSize + cellSize * 0.7;
        
        gc.fillText("♘", x, y);
    }
    
    /**
     * Draws the path lines connecting visited squares
     * 
     * @param gc GraphicsContext for drawing
     */
    private void drawPath(GraphicsContext gc) {
        if (path.size() < 2) return;
        
        gc.setStroke(PATH_COLOR);
        gc.setLineWidth(2);
        
        for (int i = 1; i < path.size(); i++) {
            Position prev = path.get(i - 1);
            Position curr = path.get(i);
            
            double startX = prev.col * cellSize + cellSize / 2.0;
            double startY = prev.row * cellSize + cellSize / 2.0;
            double endX = curr.col * cellSize + cellSize / 2.0;
            double endY = curr.row * cellSize + cellSize / 2.0;
            
            gc.strokeLine(startX, startY, endX, endY);
        }

        // If the path is a cycle (last equals first), draw closing edge in green
        if (path.size() >= 2) {
            Position first = path.get(0);
            Position last = path.get(path.size() - 1);
            if (last.equals(first)) {
                gc.setStroke(CYCLE_CLOSE_COLOR);
                gc.setLineWidth(3);
                // draw edge from previous to first to emphasize closure
                Position prev = path.get(path.size() - 2);
                double startX = prev.col * cellSize + cellSize / 2.0;
                double startY = prev.row * cellSize + cellSize / 2.0;
                double endX = first.col * cellSize + cellSize / 2.0;
                double endY = first.row * cellSize + cellSize / 2.0;
                gc.strokeLine(startX, startY, endX, endY);
            }
        }
    }
    
    /**
     * Draws move numbers on visited squares
     * 
     * @param gc GraphicsContext for drawing
     */
    private void drawMoveNumbers(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.setFont(new Font("Arial", cellSize * 0.2));
        gc.setTextAlign(TextAlignment.CENTER);
        
        for (int i = 0; i < path.size(); i++) {
            Position pos = path.get(i);
            double x = pos.col * cellSize + cellSize / 2.0;
            double y = pos.row * cellSize + cellSize * 0.3;
            
            gc.fillText(String.valueOf(i + 1), x, y);
        }
    }
    
    /**
     * Checks if the given position is valid on the board
     * 
     * @param row Row position
     * @param col Column position
     * @return true if position is valid, false otherwise
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }
    
    /**
     * Functional interface for handling square clicks
     */
    @FunctionalInterface
    public interface SquareClickListener {
        void onSquareClicked(int row, int col);
    }
    
    /**
     * Position class to represent a square on the board
     */
    public static class Position {
        public final int row;
        public final int col;
        
        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Position position = (Position) obj;
            return row == position.row && col == position.col;
        }
        
        @Override
        public int hashCode() {
            return row * 8 + col;
        }
        
        @Override
        public String toString() {
            return "(" + row + ", " + col + ")";
        }
    }
}
