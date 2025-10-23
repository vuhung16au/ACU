package com.acu.javafx.game2048;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Represents the visual grid of the 2048 game.
 * Displays the game board with tiles and handles the visual updates.
 * 
 * @author ACU JavaFX Course
 * @version 1.0
 */
public class GameGrid extends GridPane {
    
    private Board gameBoard;
    private int tileSize;
    private int tileSpacing;
    private Label[][] tileLabels;
    
    /**
     * Creates a new game grid.
     * 
     * @param gameBoard The game board to display
     * @param tileSize The size of each tile in pixels
     * @param tileSpacing The spacing between tiles in pixels
     */
    public GameGrid(Board gameBoard, int tileSize, int tileSpacing) {
        this.gameBoard = gameBoard;
        this.tileSize = tileSize;
        this.tileSpacing = tileSpacing;
        this.tileLabels = new Label[gameBoard.getSize()][gameBoard.getSize()];
        
        initializeGrid();
        updateDisplay();
    }
    
    /**
     * Initializes the grid layout and creates tile labels.
     */
    private void initializeGrid() {
        setHgap(tileSpacing);
        setVgap(tileSpacing);
        setPadding(new Insets(tileSpacing));
        
        // Set grid background
        setBackground(new Background(new BackgroundFill(
            ACUColorScheme.getGridBackground(), 
            new CornerRadii(10), 
            null)));
        
        // Create tile labels
        for (int row = 0; row < gameBoard.getSize(); row++) {
            for (int col = 0; col < gameBoard.getSize(); col++) {
                Label tileLabel = createTileLabel();
                tileLabels[row][col] = tileLabel;
                add(tileLabel, col, row);
            }
        }
    }
    
    /**
     * Creates a tile label with default styling.
     * 
     * @return A styled tile label
     */
    private Label createTileLabel() {
        Label label = new Label();
        label.setMinSize(tileSize, tileSize);
        label.setMaxSize(tileSize, tileSize);
        label.setPrefSize(tileSize, tileSize);
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        return label;
    }
    
    /**
     * Updates the display to reflect the current game board state.
     */
    public void updateDisplay() {
        for (int row = 0; row < gameBoard.getSize(); row++) {
            for (int col = 0; col < gameBoard.getSize(); col++) {
                Tile tile = gameBoard.getTileAt(row, col);
                updateTileLabel(tileLabels[row][col], tile);
            }
        }
    }
    
    /**
     * Updates a specific tile label with the tile's value and styling.
     * 
     * @param label The label to update
     * @param tile The tile containing the value and state
     */
    private void updateTileLabel(Label label, Tile tile) {
        int value = tile.getValue();
        
        if (value == 0) {
            // Empty tile
            label.setText("");
            label.setBackground(new Background(new BackgroundFill(
                ACUColorScheme.getTileBackground(0), 
                new CornerRadii(5), 
                null)));
        } else {
            // Tile with value
            label.setText(String.valueOf(value));
            label.setTextFill(ACUColorScheme.getTileTextColor(value));
            label.setBackground(new Background(new BackgroundFill(
                ACUColorScheme.getTileBackground(value), 
                new CornerRadii(5), 
                null)));
            
            // Adjust font size based on value
            if (value < 100) {
                label.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            } else if (value < 1000) {
                label.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            } else {
                label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            }
        }
    }
}
