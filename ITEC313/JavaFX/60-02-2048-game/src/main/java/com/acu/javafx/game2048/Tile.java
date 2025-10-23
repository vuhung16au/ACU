package com.acu.javafx.game2048;

/**
 * Represents a single tile in the 2048 game.
 * Each tile has a value and position on the game board.
 * 
 * @author ACU JavaFX Course
 * @version 1.0
 */
public class Tile {
    
    private int value;      // The numeric value of the tile (0 for empty)
    private int row;         // Current row position on the board
    private int col;         // Current column position on the board
    
    /**
     * Creates a new tile with the specified value.
     * 
     * @param value The numeric value of the tile
     */
    public Tile(int value) {
        this.value = value;
    }
    
    /**
     * Creates a new tile with the specified value and position.
     * 
     * @param value The numeric value of the tile
     * @param row The row position on the board
     * @param col The column position on the board
     */
    public Tile(int value, int row, int col) {
        this.value = value;
        this.row = row;
        this.col = col;
    }
    
    /**
     * Creates an empty tile (value = 0).
     */
    public Tile() {
        this(0);
    }
    
    /**
     * Gets the value of this tile.
     * 
     * @return The tile's value
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Sets the value of this tile.
     * 
     * @param value The new value
     */
    public void setValue(int value) {
        this.value = value;
    }
    
    /**
     * Sets the position of this tile on the board.
     * 
     * @param row The row position
     * @param col The column position
     */
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    /**
     * Merges this tile with another tile of the same value.
     * Doubles the tile's value and returns the new value.
     * 
     * @return The new merged value
     */
    public int merging() {
        this.value += this.value;  // Double the value
        return this.value;
    }
    
    /**
     * Checks if this tile has moved to a new position.
     * 
     * @param newRow The new row position
     * @param newCol The new column position
     * @return true if the tile has moved, false otherwise
     */
    public boolean hasMoved(int newRow, int newCol) {
        return (!isEmpty() && ((this.row != newRow) || (this.col != newCol)));
    }
    
    /**
     * Checks if this tile is empty (value = 0).
     * 
     * @return true if the tile is empty, false otherwise
     */
    public boolean isEmpty() {
        return (value == 0);
    }
    
    /**
     * Gets the current row position.
     * 
     * @return The row position
     */
    public int getRow() {
        return row;
    }
    
    /**
     * Gets the current column position.
     * 
     * @return The column position
     */
    public int getCol() {
        return col;
    }
    
    @Override
    public String toString() {
        return "Tile [value=" + value + ", row=" + row + ", col=" + col + "]";
    }
}
