package com.acu.javafx.game2048;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Board class.
 * Tests game board initialization, tile movement, merging, and game state.
 * 
 * @author ACU JavaFX Course
 * @version 1.0
 */
public class BoardTest {
    
    private Board board;
    
    @BeforeEach
    public void setUp() {
        board = new Board(4);
    }
    
    @Test
    public void testBoardInitialization() {
        assertEquals(4, board.getSize());
        assertEquals(0, board.getScore());
        assertNull(board.getGameStatus());
        
        // Check that board has initial tiles
        int nonEmptyTiles = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (!board.getTileAt(row, col).isEmpty()) {
                    nonEmptyTiles++;
                }
            }
        }
        assertEquals(2, nonEmptyTiles); // Should start with 2 tiles
    }
    
    @Test
    public void testGetTileAt() {
        Tile tile = board.getTileAt(0, 0);
        assertNotNull(tile);
        assertTrue(tile instanceof Tile);
    }
    
    @Test
    public void testSetTileAt() {
        Tile newTile = new Tile(8, 1, 1);
        board.setTileAt(1, 1, newTile);
        
        Tile retrievedTile = board.getTileAt(1, 1);
        assertEquals(8, retrievedTile.getValue());
        assertEquals(1, retrievedTile.getRow());
        assertEquals(1, retrievedTile.getCol());
    }
    
    @Test
    public void testScoreTracking() {
        int initialScore = board.getScore();
        
        // Manually set up tiles for merging
        board.setTileAt(0, 0, new Tile(4, 0, 0));
        board.setTileAt(0, 1, new Tile(4, 0, 1));
        
        // Move left to trigger merge
        board.moveLeft();
        
        // Score should have increased
        assertTrue(board.getScore() > initialScore);
    }
    
    @Test
    public void testGameStatus() {
        assertNull(board.getGameStatus());
        
        board.setGameStatus("WON");
        assertEquals("WON", board.getGameStatus());
        
        board.setGameStatus("LOST");
        assertEquals("LOST", board.getGameStatus());
    }
    
    @Test
    public void testResetGame() {
        // Modify the board state
        board.setTileAt(0, 0, new Tile(2048, 0, 0));
        board.setGameStatus("WON");
        
        // Reset the game
        board.resetGame();
        
        // Check that game is reset
        assertEquals(0, board.getScore());
        assertNull(board.getGameStatus());
        
        // Check that board has initial tiles again
        int nonEmptyTiles = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (!board.getTileAt(row, col).isEmpty()) {
                    nonEmptyTiles++;
                }
            }
        }
        assertEquals(2, nonEmptyTiles);
    }
    
    @Test
    public void testMoveOperations() {
        // Test that move operations don't throw exceptions
        assertDoesNotThrow(() -> board.moveUp());
        assertDoesNotThrow(() -> board.moveDown());
        assertDoesNotThrow(() -> board.moveLeft());
        assertDoesNotThrow(() -> board.moveRight());
    }
    
    @Test
    public void testTileMerging() {
        // Set up two adjacent tiles with same value
        board.setTileAt(0, 0, new Tile(4, 0, 0));
        board.setTileAt(0, 1, new Tile(4, 0, 1));
        
        int initialScore = board.getScore();
        
        // Move left to merge tiles
        board.moveLeft();
        
        // Check that tiles were merged
        Tile leftTile = board.getTileAt(0, 0);
        Tile rightTile = board.getTileAt(0, 1);
        
        // One tile should have value 8, the other should be empty
        boolean foundMergedTile = false;
        if (leftTile.getValue() == 8 || rightTile.getValue() == 8) {
            foundMergedTile = true;
        }
        
        assertTrue(foundMergedTile);
        assertTrue(board.getScore() > initialScore);
    }
    
    @Test
    public void testBoardSize() {
        assertEquals(4, board.getSize());
        
        // Test that we can access all positions
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                assertNotNull(board.getTileAt(row, col));
            }
        }
    }
    
    @Test
    public void testGetTiles() {
        var tiles = board.getTiles();
        assertNotNull(tiles);
        assertEquals(4, tiles.size());
        assertEquals(4, tiles.get(0).size());
    }
}
