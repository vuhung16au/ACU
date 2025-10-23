package com.acu.javafx.snake;

import javafx.geometry.Point2D;
import java.util.Random;

/**
 * Food class representing the food items that the snake can eat
 * 
 * This class manages the food's position and provides methods to generate
 * new food positions that don't conflict with the snake's body.
 * 
 * @author ACU JavaFX Course
 * @version 1.0.0
 */
public class Food {
    
    // ACU Color Schema Constants
    private static final String FOOD_COLOR = "#F2120C"; // Bright Red
    private static final String FOOD_ACCENT_COLOR = "#B51825"; // Red
    
    // Game constants
    private static final int CELL_SIZE = 20;
    
    // Food properties
    private Point2D position;
    private Random random;
    private int gameWidth;
    private int gameHeight;
    
    /**
     * Constructor - creates food at a random position
     * @param gameWidth width of the game area
     * @param gameHeight height of the game area
     */
    public Food(int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.random = new Random();
        this.position = generateRandomPosition();
    }
    
    /**
     * Generate a new random position for the food
     * @return Point2D representing the new food position
     */
    private Point2D generateRandomPosition() {
        // Calculate grid dimensions
        int gridWidth = gameWidth / CELL_SIZE;
        int gridHeight = gameHeight / CELL_SIZE;
        
        // Generate random grid coordinates
        int gridX = random.nextInt(gridWidth);
        int gridY = random.nextInt(gridHeight);
        
        // Convert to pixel coordinates
        int pixelX = gridX * CELL_SIZE;
        int pixelY = gridY * CELL_SIZE;
        
        return new Point2D(pixelX, pixelY);
    }
    
    /**
     * Generate a new food position that doesn't conflict with the snake
     * @param snakeBody list of snake body positions
     * @return Point2D representing the new food position
     */
    public Point2D generateNewPosition(java.util.List<javafx.geometry.Point2D> snakeBody) {
        Point2D newPosition;
        int attempts = 0;
        int maxAttempts = 100; // Prevent infinite loop
        
        do {
            newPosition = generateRandomPosition();
            attempts++;
        } while (snakeBody.contains(newPosition) && attempts < maxAttempts);
        
        // If we couldn't find a valid position, place it at a safe distance
        if (attempts >= maxAttempts) {
            // Find a position that's at least 2 cells away from the snake head
            Point2D head = snakeBody.get(0);
            int safeX = (int) head.getX() + CELL_SIZE * 3;
            int safeY = (int) head.getY() + CELL_SIZE * 3;
            
            // Ensure it's within bounds
            safeX = Math.min(safeX, gameWidth - CELL_SIZE);
            safeY = Math.min(safeY, gameHeight - CELL_SIZE);
            
            newPosition = new Point2D(safeX, safeY);
        }
        
        this.position = newPosition;
        return newPosition;
    }
    
    /**
     * Check if the food is at the given position
     * @param x X coordinate
     * @param y Y coordinate
     * @return true if food is at the position
     */
    public boolean isAt(int x, int y) {
        return (int) position.getX() == x && (int) position.getY() == y;
    }
    
    /**
     * Get the current food position
     * @return Point2D representing the food position
     */
    public Point2D getPosition() {
        return position;
    }
    
    /**
     * Get the food's X coordinate
     * @return X coordinate
     */
    public int getX() {
        return (int) position.getX();
    }
    
    /**
     * Get the food's Y coordinate
     * @return Y coordinate
     */
    public int getY() {
        return (int) position.getY();
    }
    
    /**
     * Update the game area dimensions
     * @param gameWidth new game width
     * @param gameHeight new game height
     */
    public void updateGameArea(int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
    }
}
