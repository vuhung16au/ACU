package com.acu.javafx.game2048;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Tile class.
 * Tests tile creation, value setting, merging, and position tracking.
 * 
 * @author ACU JavaFX Course
 * @version 1.0
 */
public class TileTest {
    
    private Tile tile;
    
    @BeforeEach
    public void setUp() {
        tile = new Tile();
    }
    
    @Test
    public void testDefaultTileCreation() {
        assertEquals(0, tile.getValue());
        assertTrue(tile.isEmpty());
    }
    
    @Test
    public void testTileCreationWithValue() {
        Tile tileWithValue = new Tile(4);
        assertEquals(4, tileWithValue.getValue());
        assertFalse(tileWithValue.isEmpty());
    }
    
    @Test
    public void testTileCreationWithValueAndPosition() {
        Tile positionedTile = new Tile(8, 2, 3);
        assertEquals(8, positionedTile.getValue());
        assertEquals(2, positionedTile.getRow());
        assertEquals(3, positionedTile.getCol());
    }
    
    @Test
    public void testSetValue() {
        tile.setValue(16);
        assertEquals(16, tile.getValue());
        assertFalse(tile.isEmpty());
    }
    
    @Test
    public void testSetPosition() {
        tile.setPosition(1, 2);
        assertEquals(1, tile.getRow());
        assertEquals(2, tile.getCol());
    }
    
    @Test
    public void testMerging() {
        tile.setValue(4);
        int mergedValue = tile.merging();
        assertEquals(8, mergedValue);
        assertEquals(8, tile.getValue());
    }
    
    @Test
    public void testHasMoved() {
        tile.setPosition(0, 0);
        tile.setValue(2);
        
        // Same position - should not have moved
        assertFalse(tile.hasMoved(0, 0));
        
        // Different position - should have moved
        assertTrue(tile.hasMoved(1, 1));
    }
    
    @Test
    public void testHasMovedWithEmptyTile() {
        // Empty tile should not be considered as moved
        assertFalse(tile.hasMoved(1, 1));
    }
    
    @Test
    public void testIsEmpty() {
        assertTrue(tile.isEmpty());
        
        tile.setValue(2);
        assertFalse(tile.isEmpty());
        
        tile.setValue(0);
        assertTrue(tile.isEmpty());
    }
    
    @Test
    public void testToString() {
        tile.setValue(4);
        tile.setPosition(1, 2);
        String result = tile.toString();
        assertTrue(result.contains("value=4"));
        assertTrue(result.contains("row=1"));
        assertTrue(result.contains("col=2"));
    }
}
