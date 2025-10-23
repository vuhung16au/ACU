package com.acu.javafx.snake;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Food class
 * 
 * Tests food position generation, collision detection, and game area updates.
 * 
 * @author ACU JavaFX Course
 * @version 1.0.0
 */
@DisplayName("Food Tests")
class FoodTest {
    
    private Food food;
    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 600;
    
    @BeforeEach
    void setUp() {
        food = new Food(GAME_WIDTH, GAME_HEIGHT);
    }
    
    @Test
    @DisplayName("Food should be created with valid position")
    void testFoodInitialization() {
        Point2D position = food.getPosition();
        
        assertNotNull(position);
        assertTrue(position.getX() >= 0);
        assertTrue(position.getY() >= 0);
        assertTrue(position.getX() < GAME_WIDTH);
        assertTrue(position.getY() < GAME_HEIGHT);
    }
    
    @Test
    @DisplayName("Food should generate new position when requested")
    void testNewPositionGeneration() {
        Point2D originalPosition = food.getPosition();
        List<Point2D> emptySnakeBody = new ArrayList<>();
        
        Point2D newPosition = food.generateNewPosition(emptySnakeBody);
        
        assertNotNull(newPosition);
        assertNotEquals(originalPosition, newPosition);
        assertTrue(newPosition.getX() >= 0);
        assertTrue(newPosition.getY() >= 0);
        assertTrue(newPosition.getX() < GAME_WIDTH);
        assertTrue(newPosition.getY() < GAME_HEIGHT);
    }
    
    @Test
    @DisplayName("Food should avoid snake body when generating new position")
    void testFoodAvoidsSnakeBody() {
        // Create a snake body that occupies some positions
        List<Point2D> snakeBody = new ArrayList<>();
        snakeBody.add(new Point2D(100, 100));
        snakeBody.add(new Point2D(120, 100));
        snakeBody.add(new Point2D(140, 100));
        
        Point2D newPosition = food.generateNewPosition(snakeBody);
        
        assertNotNull(newPosition);
        assertFalse(snakeBody.contains(newPosition));
    }
    
    @Test
    @DisplayName("Food should detect position correctly")
    void testPositionDetection() {
        Point2D position = food.getPosition();
        int x = (int) position.getX();
        int y = (int) position.getY();
        
        assertTrue(food.isAt(x, y));
        assertFalse(food.isAt(x + 20, y));
        assertFalse(food.isAt(x, y + 20));
    }
    
    @Test
    @DisplayName("Food should return correct coordinates")
    void testCoordinateGetters() {
        Point2D position = food.getPosition();
        
        assertEquals((int) position.getX(), food.getX());
        assertEquals((int) position.getY(), food.getY());
    }
    
    @Test
    @DisplayName("Food should update game area dimensions")
    void testGameAreaUpdate() {
        int newWidth = 800;
        int newHeight = 800;
        
        food.updateGameArea(newWidth, newHeight);
        
        // Generate new position to test if it respects new dimensions
        List<Point2D> emptySnakeBody = new ArrayList<>();
        Point2D newPosition = food.generateNewPosition(emptySnakeBody);
        
        assertTrue(newPosition.getX() >= 0);
        assertTrue(newPosition.getY() >= 0);
        assertTrue(newPosition.getX() < newWidth);
        assertTrue(newPosition.getY() < newHeight);
    }
    
    @Test
    @DisplayName("Food should generate multiple different positions")
    void testMultiplePositionGeneration() {
        List<Point2D> positions = new ArrayList<>();
        List<Point2D> emptySnakeBody = new ArrayList<>();
        
        // Generate multiple positions
        for (int i = 0; i < 10; i++) {
            Point2D position = food.generateNewPosition(emptySnakeBody);
            positions.add(position);
        }
        
        // Check that we got different positions (very likely with random generation)
        boolean hasDifferentPositions = false;
        for (int i = 1; i < positions.size(); i++) {
            if (!positions.get(i).equals(positions.get(0))) {
                hasDifferentPositions = true;
                break;
            }
        }
        
        // This test might occasionally fail due to randomness, but it's very unlikely
        assertTrue(hasDifferentPositions, "Generated positions should be different");
    }
    
    @Test
    @DisplayName("Food should handle edge case with full snake body")
    void testFoodWithFullSnakeBody() {
        // Create a snake body that covers most of the game area
        List<Point2D> snakeBody = new ArrayList<>();
        for (int x = 0; x < GAME_WIDTH; x += 20) {
            for (int y = 0; y < GAME_HEIGHT; y += 20) {
                snakeBody.add(new Point2D(x, y));
            }
        }
        
        // Should still generate a position (even if it's not optimal)
        Point2D newPosition = food.generateNewPosition(snakeBody);
        
        assertNotNull(newPosition);
        assertTrue(newPosition.getX() >= 0);
        assertTrue(newPosition.getY() >= 0);
    }
}
