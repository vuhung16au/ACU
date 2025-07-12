package com.example;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Tic-Tac-Toe Game Implementation
 * 
 * This class implements a simple two-player Tic-Tac-Toe game using JavaFX controls.
 * Players alternate between 'X' and 'O' until someone wins or the game is a tie.
 * 
 * @author ITEC313 Student
 * @version 1.0.0
 */
public class TicTacToeGame {
    private Button[][] buttons = new Button[3][3];
    private char currentPlayer = 'X';
    private boolean gameOver = false;
    private Label statusLabel;
    private GridPane gameGrid;
    
    /**
     * Constructor that initializes the game board
     */
    public TicTacToeGame() {
        initializeGame();
    }
    
    /**
     * Initializes the game board and controls
     */
    private void initializeGame() {
        gameGrid = new GridPane();
        gameGrid.setAlignment(Pos.CENTER);
        gameGrid.setHgap(5);
        gameGrid.setVgap(5);
        
        statusLabel = new Label("Player X's turn");
        statusLabel.setFont(Font.font(16));
        
        // Create 3x3 grid of buttons
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button();
                button.setPrefSize(80, 80);
                button.setFont(Font.font(24));
                button.setStyle("-fx-base: lightblue;");
                
                // Store row and col for the lambda
                final int r = row;
                final int c = col;
                
                button.setOnAction(_ -> handleButtonClick(r, c));
                
                buttons[row][col] = button;
                gameGrid.add(button, col, row);
            }
        }
    }
    
    /**
     * Handles button clicks on the game board
     * @param row the row of the clicked button
     * @param col the column of the clicked button
     */
    private void handleButtonClick(int row, int col) {
        if (gameOver || !buttons[row][col].getText().isEmpty()) {
            return;
        }
        
        // Set the button text to current player's symbol
        buttons[row][col].setText(String.valueOf(currentPlayer));
        buttons[row][col].setStyle("-fx-base: " + (currentPlayer == 'X' ? "lightcoral" : "lightgreen"));
        
        // Check for win or tie
        if (checkWin()) {
            statusLabel.setText("Player " + currentPlayer + " wins!");
            gameOver = true;
            showWinAlert();
        } else if (checkTie()) {
            statusLabel.setText("It's a tie!");
            gameOver = true;
            showTieAlert();
        } else {
            // Switch players
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            statusLabel.setText("Player " + currentPlayer + "'s turn");
        }
    }
    
    /**
     * Checks if the current player has won
     * @return true if the current player has won, false otherwise
     */
    private boolean checkWin() {
        String player = String.valueOf(currentPlayer);
        
        // Check rows
        for (int row = 0; row < 3; row++) {
            if (player.equals(buttons[row][0].getText()) &&
                player.equals(buttons[row][1].getText()) &&
                player.equals(buttons[row][2].getText())) {
                return true;
            }
        }
        
        // Check columns
        for (int col = 0; col < 3; col++) {
            if (player.equals(buttons[0][col].getText()) &&
                player.equals(buttons[1][col].getText()) &&
                player.equals(buttons[2][col].getText())) {
                return true;
            }
        }
        
        // Check diagonals
        if (player.equals(buttons[0][0].getText()) &&
            player.equals(buttons[1][1].getText()) &&
            player.equals(buttons[2][2].getText())) {
            return true;
        }
        
        if (player.equals(buttons[0][2].getText()) &&
            player.equals(buttons[1][1].getText()) &&
            player.equals(buttons[2][0].getText())) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Checks if the game is a tie (all buttons filled with no winner)
     * @return true if the game is a tie, false otherwise
     */
    private boolean checkTie() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Shows an alert when a player wins
     */
    private void showWinAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("We have a winner!");
        alert.setContentText("Player " + currentPlayer + " wins!");
        alert.showAndWait();
    }
    
    /**
     * Shows an alert when the game is a tie
     */
    private void showTieAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("It's a tie!");
        alert.setContentText("Good game! Try again?");
        alert.showAndWait();
    }
    
    /**
     * Resets the game to initial state
     */
    public void resetGame() {
        currentPlayer = 'X';
        gameOver = false;
        statusLabel.setText("Player X's turn");
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setStyle("-fx-base: lightblue;");
            }
        }
    }
    
    /**
     * Returns the game pane containing the game board and controls
     * @return VBox containing the complete game interface
     */
    public VBox getGamePane() {
        VBox gamePane = new VBox(20);
        gamePane.setAlignment(Pos.CENTER);
        
        Button resetButton = new Button("Reset Game");
        resetButton.setOnAction(_ -> resetGame());
        resetButton.setStyle("-fx-base: lightyellow;");
        
        gamePane.getChildren().addAll(statusLabel, gameGrid, resetButton);
        return gamePane;
    }
}
