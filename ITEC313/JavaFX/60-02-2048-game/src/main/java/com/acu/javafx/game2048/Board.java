package com.acu.javafx.game2048;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game board for the 2048 game.
 * Manages the 4x4 grid of tiles and game logic.
 * 
 * @author ACU JavaFX Course
 * @version 1.0
 */
public class Board {
    
    private int size;                        // Size of the grid (always 4 for 2048)
    private int score;                       // Current game score
    private int emptyTiles;                  // Number of empty tiles on the board
    private int initTiles = 2;               // Number of tiles to start with
    private boolean gameOver = false;        // Game over flag
    private String gameStatus;               // "WON", "LOST", or null
    private boolean shouldGenerateNewTile = false;  // Flag to generate new tile
    private List<List<Tile>> tiles;          // 2D list representing the board
    
    /**
     * Creates a new game board with the specified size.
     * 
     * @param size The size of the board (typically 4 for 2048)
     */
    public Board(int size) {
        this.size = size;
        this.emptyTiles = this.size * this.size;
        this.tiles = new ArrayList<>();
        this.score = 0;
        this.gameStatus = null;
        
        initializeBoard();
        generateInitialTiles();
    }
    
    /**
     * Initializes the board with empty tiles.
     */
    private void initializeBoard() {
        for (int row = 0; row < this.size; row++) {
            tiles.add(new ArrayList<Tile>());
            for (int col = 0; col < this.size; col++) {
                tiles.get(row).add(new Tile());
            }
        }
    }
    
    /**
     * Generates the initial tiles for the game start.
     */
    private void generateInitialTiles() {
        for (int i = 0; i < initTiles; i++) {
            shouldGenerateNewTile = true;
            generateNewRandomTile();
        }
    }
    
    /**
     * Gets the tile at the specified position.
     * 
     * @param row The row index
     * @param col The column index
     * @return The tile at the specified position
     */
    public Tile getTileAt(int row, int col) {
        return tiles.get(row).get(col);
    }
    
    /**
     * Sets a tile at the specified position.
     * 
     * @param row The row index
     * @param col The column index
     * @param tile The tile to place
     */
    public void setTileAt(int row, int col, Tile tile) {
        tiles.get(row).set(col, tile);
    }
    
    /**
     * Gets the current score.
     * 
     * @return The current score
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Gets the game status.
     * 
     * @return "WON", "LOST", or null if game continues
     */
    public String getGameStatus() {
        return gameStatus;
    }
    
    /**
     * Sets the game status.
     * 
     * @param status The new game status
     */
    public void setGameStatus(String status) {
        this.gameStatus = status;
    }
    
    /**
     * Gets the board size.
     * 
     * @return The board size
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Gets all tiles on the board.
     * 
     * @return The 2D list of tiles
     */
    public List<List<Tile>> getTiles() {
        return tiles;
    }
    
    /**
     * Moves all tiles up and merges adjacent tiles with the same value.
     */
    public void moveUp() {
        for (int col = 0; col < size; col++) {
            List<Tile> columnTiles = getColumnTiles(col);
            List<Tile> movedTiles = removeEmptyTiles(columnTiles);
            movedTiles = mergeTiles(movedTiles);
            movedTiles = addEmptyTilesAtEnd(movedTiles);
            setColumnTiles(col, movedTiles);
        }
        checkGameStatus();
    }
    
    /**
     * Moves all tiles down and merges adjacent tiles with the same value.
     */
    public void moveDown() {
        for (int col = 0; col < size; col++) {
            List<Tile> columnTiles = getColumnTiles(col);
            List<Tile> movedTiles = removeEmptyTiles(columnTiles);
            movedTiles = mergeTiles(movedTiles);
            movedTiles = addEmptyTilesAtStart(movedTiles);
            setColumnTiles(col, movedTiles);
        }
        checkGameStatus();
    }
    
    /**
     * Moves all tiles left and merges adjacent tiles with the same value.
     */
    public void moveLeft() {
        for (int row = 0; row < size; row++) {
            List<Tile> rowTiles = getRowTiles(row);
            List<Tile> movedTiles = removeEmptyTiles(rowTiles);
            movedTiles = mergeTiles(movedTiles);
            movedTiles = addEmptyTilesAtEnd(movedTiles);
            setRowTiles(row, movedTiles);
        }
        checkGameStatus();
    }
    
    /**
     * Moves all tiles right and merges adjacent tiles with the same value.
     */
    public void moveRight() {
        for (int row = 0; row < size; row++) {
            List<Tile> rowTiles = getRowTiles(row);
            List<Tile> movedTiles = removeEmptyTiles(rowTiles);
            movedTiles = mergeTiles(movedTiles);
            movedTiles = addEmptyTilesAtStart(movedTiles);
            setRowTiles(row, movedTiles);
        }
        checkGameStatus();
    }
    
    /**
     * Gets all tiles in a specific row.
     * 
     * @param row The row index
     * @return List of tiles in the row
     */
    private List<Tile> getRowTiles(int row) {
        List<Tile> rowTiles = new ArrayList<>();
        for (int col = 0; col < size; col++) {
            rowTiles.add(getTileAt(row, col));
        }
        return rowTiles;
    }
    
    /**
     * Gets all tiles in a specific column.
     * 
     * @param col The column index
     * @return List of tiles in the column
     */
    private List<Tile> getColumnTiles(int col) {
        List<Tile> columnTiles = new ArrayList<>();
        for (int row = 0; row < size; row++) {
            columnTiles.add(getTileAt(row, col));
        }
        return columnTiles;
    }
    
    /**
     * Sets tiles in a specific row.
     * 
     * @param row The row index
     * @param tiles The tiles to set
     */
    private void setRowTiles(int row, List<Tile> tiles) {
        for (int col = 0; col < size; col++) {
            Tile tile = tiles.get(col);
            if (tile.hasMoved(row, col)) {
                shouldGenerateNewTile = true;
            }
            setTileAt(row, col, tile);
        }
    }
    
    /**
     * Sets tiles in a specific column.
     * 
     * @param col The column index
     * @param tiles The tiles to set
     */
    private void setColumnTiles(int col, List<Tile> tiles) {
        for (int row = 0; row < size; row++) {
            Tile tile = tiles.get(row);
            if (tile.hasMoved(row, col)) {
                shouldGenerateNewTile = true;
            }
            setTileAt(row, col, tile);
        }
    }
    
    /**
     * Removes empty tiles from a list, keeping only non-empty tiles.
     * 
     * @param tiles The list of tiles to filter
     * @return List containing only non-empty tiles
     */
    private List<Tile> removeEmptyTiles(List<Tile> tiles) {
        List<Tile> nonEmptyTiles = new ArrayList<>();
        for (Tile tile : tiles) {
            if (!tile.isEmpty()) {
                nonEmptyTiles.add(tile);
            }
        }
        return nonEmptyTiles;
    }
    
    /**
     * Merges adjacent tiles with the same value.
     * 
     * @param tiles The list of tiles to merge
     * @return The merged list of tiles
     */
    private List<Tile> mergeTiles(List<Tile> tiles) {
        for (int i = 0; i < tiles.size() - 1; i++) {
            if (tiles.get(i).getValue() == tiles.get(i + 1).getValue()) {
                int mergedValue = tiles.get(i).merging();
                if (mergedValue == 2048) {
                    gameOver = true;
                }
                score += mergedValue;
                tiles.remove(i + 1);
                shouldGenerateNewTile = true;
                emptyTiles++;
            }
        }
        return tiles;
    }
    
    /**
     * Adds empty tiles at the end of the list to maintain board size.
     * 
     * @param tiles The list of tiles
     * @return The list with empty tiles added at the end
     */
    private List<Tile> addEmptyTilesAtEnd(List<Tile> tiles) {
        while (tiles.size() < size) {
            tiles.add(new Tile());
        }
        return tiles;
    }
    
    /**
     * Adds empty tiles at the beginning of the list to maintain board size.
     * 
     * @param tiles The list of tiles
     * @return The list with empty tiles added at the beginning
     */
    private List<Tile> addEmptyTilesAtStart(List<Tile> tiles) {
        while (tiles.size() < size) {
            tiles.add(0, new Tile());
        }
        return tiles;
    }
    
    /**
     * Checks the current game status and generates new tiles if needed.
     */
    private void checkGameStatus() {
        if (gameOver) {
            setGameStatus("WON");
        } else if (isBoardFull()) {
            if (!isMovePossible()) {
                setGameStatus("LOST");
            } else {
                generateNewRandomTile();
            }
        } else {
            generateNewRandomTile();
        }
    }
    
    /**
     * Checks if the board is full (no empty tiles).
     * 
     * @return true if the board is full, false otherwise
     */
    private boolean isBoardFull() {
        return emptyTiles == 0;
    }
    
    /**
     * Checks if any moves are possible on the current board.
     * 
     * @return true if moves are possible, false otherwise
     */
    private boolean isMovePossible() {
        // Check horizontal adjacent tiles
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size - 1; col++) {
                if (getTileAt(row, col).getValue() == getTileAt(row, col + 1).getValue()) {
                    return true;
                }
            }
        }
        
        // Check vertical adjacent tiles
        for (int row = 0; row < size - 1; row++) {
            for (int col = 0; col < size; col++) {
                if (getTileAt(row, col).getValue() == getTileAt(row + 1, col).getValue()) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Generates a new random tile on the board.
     */
    private void generateNewRandomTile() {
        if (shouldGenerateNewTile && !isBoardFull()) {
            int row, col;
            int value = Math.random() < 0.9 ? 2 : 4;  // 90% chance for 2, 10% for 4
            
            do {
                row = (int) (Math.random() * size);
                col = (int) (Math.random() * size);
            } while (!getTileAt(row, col).isEmpty());
            
            setTileAt(row, col, new Tile(value, row, col));
            emptyTiles--;
            shouldGenerateNewTile = false;
        }
    }
    
    /**
     * Resets the game to initial state.
     */
    public void resetGame() {
        this.score = 0;
        this.gameOver = false;
        this.gameStatus = null;
        this.emptyTiles = size * size;
        this.shouldGenerateNewTile = false;
        
        // Clear all tiles
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                setTileAt(row, col, new Tile());
            }
        }
        
        generateInitialTiles();
    }
}
