package com.acu.javafx.snake;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Snake class
 * 
 * Tests the snake's movement, growth, collision detection, and state management.
 * 
 * @author ACU JavaFX Course
 * @version 1.0.0
 */
@DisplayName("Snake Tests")
class SnakeTest {
    
    private Snake snake;
    private static final int START_X = 100;
    private static final int START_Y = 100;
    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 600;
    
    @BeforeEach
    void setUp() {
        snake = new Snake(START_X, START_Y);
    }
    
    @Test
    @DisplayName("Snake should be created with initial length")
    void testSnakeInitialization() {
        assertEquals(3, snake.getLength());
        assertTrue(snake.isAlive());
        assertEquals(0, snake.getScore());
        assertEquals(Snake.Direction.RIGHT, snake.getDirection());
    }
    
    @Test
    @DisplayName("Snake should move in the correct direction")
    void testSnakeMovement() {
        // Move right
        snake.move(GAME_WIDTH, GAME_HEIGHT);
        Point2D head = snake.getHead();
        assertEquals(START_X + 20, head.getX(), 0.1);
        assertEquals(START_Y, head.getY(), 0.1);
        
        // Change direction to down
        snake.setDirection(Snake.Direction.DOWN);
        snake.move(GAME_WIDTH, GAME_HEIGHT);
        head = snake.getHead();
        assertEquals(START_X + 20, head.getX(), 0.1);
        assertEquals(START_Y + 20, head.getY(), 0.1);
    }
    
    @Test
    @DisplayName("Snake should not move backwards into itself")
    void testSnakeDirectionChange() {
        // Move right first
        snake.move(GAME_WIDTH, GAME_HEIGHT);
        
        // Try to move left (should be ignored)
        snake.setDirection(Snake.Direction.LEFT);
        snake.move(GAME_WIDTH, GAME_HEIGHT);
        
        // Should still be moving right
        assertEquals(Snake.Direction.RIGHT, snake.getDirection());
    }
    
    @Test
    @DisplayName("Snake should grow when food is eaten")
    void testSnakeGrowth() {
        int initialLength = snake.getLength();
        int initialScore = snake.getScore();
        
        snake.grow();
        
        // The grow() method only increases score, actual growth happens on next move
        assertEquals(initialLength, snake.getLength());
        assertEquals(initialScore + 10, snake.getScore());
    }
    
    @Test
    @DisplayName("Snake should detect wall collisions")
    void testWallCollision() {
        // Move snake to the right edge
        for (int i = 0; i < 30; i++) {
            snake.move(GAME_WIDTH, GAME_HEIGHT);
        }
        
        assertFalse(snake.isAlive());
    }
    
    @Test
    @DisplayName("Snake should detect self-collision")
    void testSelfCollision() {
        // Create a scenario where snake collides with itself
        snake.move(GAME_WIDTH, GAME_HEIGHT); // Move right
        snake.setDirection(Snake.Direction.DOWN);
        snake.move(GAME_WIDTH, GAME_HEIGHT); // Move down
        snake.setDirection(Snake.Direction.LEFT);
        snake.move(GAME_WIDTH, GAME_HEIGHT); // Move left
        snake.setDirection(Snake.Direction.UP);
        snake.move(GAME_WIDTH, GAME_HEIGHT); // Move up (collision)
        
        assertFalse(snake.isAlive());
    }
    
    @Test
    @DisplayName("Snake should detect head position correctly")
    void testHeadPositionDetection() {
        assertTrue(snake.isHeadAt(START_X, START_Y));
        assertFalse(snake.isHeadAt(START_X + 20, START_Y));
        
        snake.move(GAME_WIDTH, GAME_HEIGHT);
        assertTrue(snake.isHeadAt(START_X + 20, START_Y));
    }
    
    @Test
    @DisplayName("Snake should detect body position correctly")
    void testBodyPositionDetection() {
        // Check initial body positions
        assertTrue(snake.contains(START_X, START_Y));
        assertTrue(snake.contains(START_X - 20, START_Y));
        assertTrue(snake.contains(START_X - 40, START_Y));
        
        // Move and check new positions
        snake.move(GAME_WIDTH, GAME_HEIGHT);
        assertTrue(snake.contains(START_X + 20, START_Y));
    }
    
    @Test
    @DisplayName("Snake should reset to initial state")
    void testSnakeReset() {
        // Move snake and change state
        snake.move(GAME_WIDTH, GAME_HEIGHT);
        snake.grow();
        snake.setDirection(Snake.Direction.DOWN);
        
        // Reset
        snake.reset(START_X, START_Y);
        
        assertEquals(3, snake.getLength());
        assertTrue(snake.isAlive());
        assertEquals(0, snake.getScore());
        assertEquals(Snake.Direction.RIGHT, snake.getDirection());
        assertTrue(snake.isHeadAt(START_X, START_Y));
    }
    
    @Test
    @DisplayName("Snake should remove tail when moving without eating")
    void testTailRemoval() {
        int initialLength = snake.getLength();
        
        // Move without growing
        snake.move(GAME_WIDTH, GAME_HEIGHT);
        snake.removeTail();
        
        assertEquals(initialLength, snake.getLength());
    }
    
    @Test
    @DisplayName("Snake should maintain body integrity during movement")
    void testBodyIntegrity() {
        List<Point2D> initialBody = snake.getBody();
        snake.move(GAME_WIDTH, GAME_HEIGHT);
        List<Point2D> newBody = snake.getBody();
        
        // Head should be in new position
        assertNotEquals(initialBody.get(0), newBody.get(0));
        
        // Body segments should follow
        for (int i = 1; i < newBody.size(); i++) {
            assertEquals(initialBody.get(i - 1), newBody.get(i));
        }
    }
}
