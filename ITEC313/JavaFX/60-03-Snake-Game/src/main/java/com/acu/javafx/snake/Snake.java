package com.acu.javafx.snake;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Snake class representing the player's snake in the game
 * 
 * This class manages the snake's position, movement, and growth.
 * It uses a list of Point2D objects to represent snake segments.
 * 
 * @author ACU JavaFX Course
 * @version 1.0.0
 */
public class Snake {
    
    // ACU Color Schema Constants
    private static final String SNAKE_HEAD_COLOR = "#3C1053"; // Purple
    private static final String SNAKE_BODY_COLOR = "#B51825"; // Red
    private static final String SNAKE_ACCENT_COLOR = "#F2120C"; // Bright Red
    
    // Game constants
    private static final int INITIAL_LENGTH = 3;
    private static final int CELL_SIZE = 20;
    
    // Snake properties
    private List<Point2D> body;
    private Direction direction;
    private Direction nextDirection;
    private boolean isAlive;
    private int score;
    
    /**
     * Enumeration for snake movement directions
     */
    public enum Direction {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0);
        
        private final int deltaX;
        private final int deltaY;
        
        Direction(int deltaX, int deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }
        
        public int getDeltaX() { return deltaX; }
        public int getDeltaY() { return deltaY; }
    }
    
    /**
     * Constructor - creates a new snake at the center of the game area
     * @param startX initial X position
     * @param startY initial Y position
     */
    public Snake(int startX, int startY) {
        this.body = new ArrayList<>();
        this.direction = Direction.RIGHT;
        this.nextDirection = Direction.RIGHT;
        this.isAlive = true;
        this.score = 0;
        
        // Initialize snake body with initial length
        for (int i = 0; i < INITIAL_LENGTH; i++) {
            body.add(new Point2D(startX - i * CELL_SIZE, startY));
        }
    }
    
    /**
     * Move the snake in the current direction
     * @param gameWidth width of the game area
     * @param gameHeight height of the game area
     */
    public void move(int gameWidth, int gameHeight) {
        if (!isAlive) return;
        
        // Update direction
        direction = nextDirection;
        
        // Get current head position
        Point2D head = body.get(0);
        
        // Calculate new head position
        int newX = (int) (head.getX() + direction.getDeltaX() * CELL_SIZE);
        int newY = (int) (head.getY() + direction.getDeltaY() * CELL_SIZE);
        
        // Check for wall collisions
        if (newX < 0 || newX >= gameWidth || newY < 0 || newY >= gameHeight) {
            isAlive = false;
            return;
        }
        
        // Check for self-collision
        Point2D newHead = new Point2D(newX, newY);
        if (body.contains(newHead)) {
            isAlive = false;
            return;
        }
        
        // Add new head
        body.add(0, newHead);
        
        // Remove tail (this will be handled by the game logic when food is eaten)
    }
    
    /**
     * Grow the snake by one segment
     */
    public void grow() {
        // Snake grows by not removing the tail in the next move
        score += 10;
        // The actual growth happens by not calling removeTail() in the next move
    }
    
    /**
     * Remove the tail segment (called when snake moves without eating food)
     */
    public void removeTail() {
        if (body.size() > INITIAL_LENGTH) {
            body.remove(body.size() - 1);
        }
    }
    
    /**
     * Change the snake's direction
     * @param newDirection the new direction to move
     */
    public void setDirection(Direction newDirection) {
        // Prevent the snake from moving backwards into itself
        if (direction == Direction.UP && newDirection == Direction.DOWN) return;
        if (direction == Direction.DOWN && newDirection == Direction.UP) return;
        if (direction == Direction.LEFT && newDirection == Direction.RIGHT) return;
        if (direction == Direction.RIGHT && newDirection == Direction.LEFT) return;
        
        this.nextDirection = newDirection;
    }
    
    /**
     * Check if the snake's head is at the given position
     * @param x X coordinate
     * @param y Y coordinate
     * @return true if head is at the position
     */
    public boolean isHeadAt(int x, int y) {
        if (body.isEmpty()) return false;
        Point2D head = body.get(0);
        return (int) head.getX() == x && (int) head.getY() == y;
    }
    
    /**
     * Check if the snake's body contains the given position
     * @param x X coordinate
     * @param y Y coordinate
     * @return true if body contains the position
     */
    public boolean contains(int x, int y) {
        Point2D point = new Point2D(x, y);
        return body.contains(point);
    }
    
    // Getters
    public List<Point2D> getBody() { return new ArrayList<>(body); }
    public Direction getDirection() { return direction; }
    public boolean isAlive() { return isAlive; }
    public int getScore() { return score; }
    public int getLength() { return body.size(); }
    
    /**
     * Get the head position
     * @return Point2D representing the head position
     */
    public Point2D getHead() {
        return body.isEmpty() ? new Point2D(0, 0) : body.get(0);
    }
    
    /**
     * Reset the snake to initial state
     * @param startX initial X position
     * @param startY initial Y position
     */
    public void reset(int startX, int startY) {
        this.body.clear();
        this.direction = Direction.RIGHT;
        this.nextDirection = Direction.RIGHT;
        this.isAlive = true;
        this.score = 0;
        
        // Initialize snake body with initial length
        for (int i = 0; i < INITIAL_LENGTH; i++) {
            body.add(new Point2D(startX - i * CELL_SIZE, startY));
        }
    }
}
