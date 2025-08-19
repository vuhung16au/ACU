package com.example;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.Random;

/**
 * Single-Player Tic-Tac-Toe Game Implementation
 * 
 * This class implements the single-player mode of the multiplayer Tic-Tac-Toe game using JavaFX controls.
 * The player plays as 'X' against the computer which plays as 'O'.
 * The computer uses a simple AI to make moves.
 * 
 * @author ITEC313 Student
 * @version 3.0.0
 */
public class TicTacToeGame implements TicTacToeConstants {
    private Button[][] buttons = new Button[BOARD_SIZE][BOARD_SIZE];
    private char playerSymbol = 'X';
    private char computerSymbol = 'O';
    private boolean gameOver = false;
    private Label statusLabel;
    private GridPane gameGrid;
    private Random random = new Random();
    
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
        
        statusLabel = new Label("Your turn (X) - Click any square to start!");
        statusLabel.setFont(Font.font(16));
        
        // Create BOARD_SIZE x BOARD_SIZE grid of buttons
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Button button = new Button();
                button.setPrefSize(80, 80);
                button.setFont(Font.font(24));
                button.setStyle("-fx-base: lightblue;");
                
                // Store row and col for the lambda
                final int r = row;
                final int c = col;
                
                button.setOnAction(_ -> handlePlayerMove(r, c));
                
                buttons[row][col] = button;
                gameGrid.add(button, col, row);
            }
        }
    }
    
    /**
     * Handles player moves on the game board
     * @param row the row of the clicked button
     * @param col the column of the clicked button
     */
    private void handlePlayerMove(int row, int col) {
        if (gameOver || !buttons[row][col].getText().isEmpty()) {
            return;
        }
        
        // Player's move
        makeMove(row, col, playerSymbol);
        
        // Check for win or tie after player's move
        if (checkWin(playerSymbol)) {
            statusLabel.setText("Congratulations! You win!");
            gameOver = true;
            showWinAlert("Player");
        } else if (checkTie()) {
            statusLabel.setText("It's a tie!");
            gameOver = true;
            showTieAlert();
        } else {
            // Computer's turn
            statusLabel.setText("Computer is thinking...");
            makeComputerMove();
        }
    }
    
    /**
     * Makes a move on the board
     * @param row the row to place the symbol
     * @param col the column to place the symbol
     * @param symbol the symbol to place ('X' or 'O')
     */
    private void makeMove(int row, int col, char symbol) {
        buttons[row][col].setText(String.valueOf(symbol));
        if (symbol == 'X') {
            buttons[row][col].setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 28px;");
        } else {
            buttons[row][col].setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 28px;");
        }
    }
    
    /**
     * Makes the computer's move using simple AI
     */
    private void makeComputerMove() {
        // Simple AI: try to win, then block player, then random move
        int[] move = findBestMove();
        
        if (move != null) {
            makeMove(move[0], move[1], computerSymbol);
            
            // Check for win or tie after computer's move
            if (checkWin(computerSymbol)) {
                statusLabel.setText("Computer wins! Better luck next time!");
                gameOver = true;
                showWinAlert("Computer");
            } else if (checkTie()) {
                statusLabel.setText("It's a tie!");
                gameOver = true;
                showTieAlert();
            } else {
                statusLabel.setText("Your turn (X) - Click any square!");
            }
        }
    }
    
    /**
     * Finds the best move for the computer
     * @return int array with [row, col] of the best move
     */
    private int[] findBestMove() {
        // First, try to win
        int[] winningMove = findWinningMove(computerSymbol);
        if (winningMove != null) {
            return winningMove;
        }
        
        // Second, try to block player from winning
        int[] blockingMove = findWinningMove(playerSymbol);
        if (blockingMove != null) {
            return blockingMove;
        }
        
        // Third, try to take center
        int center = BOARD_SIZE / 2;
        if (buttons[center][center].getText().isEmpty()) {
            return new int[]{center, center};
        }
        
        // Fourth, try to take corners
        int[][] corners = {{0, 0}, {0, BOARD_SIZE-1}, {BOARD_SIZE-1, 0}, {BOARD_SIZE-1, BOARD_SIZE-1}};
        for (int[] corner : corners) {
            if (buttons[corner[0]][corner[1]].getText().isEmpty()) {
                return corner;
            }
        }
        
        // Finally, take any available space
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return new int[]{row, col};
                }
            }
        }
        
        return null;
    }
    
    /**
     * Finds a winning move for the given symbol
     * @param symbol the symbol to check for winning moves
     * @return int array with [row, col] of the winning move, or null if none found
     */
    private int[] findWinningMove(char symbol) {
        // Check each empty position
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    // Temporarily place the symbol
                    buttons[row][col].setText(String.valueOf(symbol));
                    
                    // Check if this creates a win
                    if (checkWin(symbol)) {
                        // Remove the temporary symbol
                        buttons[row][col].setText("");
                        return new int[]{row, col};
                    }
                    
                    // Remove the temporary symbol
                    buttons[row][col].setText("");
                }
            }
        }
        return null;
    }
    
    /**
     * Checks if the given symbol has won
     * @param symbol the symbol to check for win
     * @return true if the symbol has won, false otherwise
     */
    private boolean checkWin(char symbol) {
        String player = String.valueOf(symbol);
        
        // Check rows
        for (int row = 0; row < BOARD_SIZE; row++) {
            boolean rowWin = true;
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (!player.equals(buttons[row][col].getText())) {
                    rowWin = false;
                    break;
                }
            }
            if (rowWin) return true;
        }
        
        // Check columns
        for (int col = 0; col < BOARD_SIZE; col++) {
            boolean colWin = true;
            for (int row = 0; row < BOARD_SIZE; row++) {
                if (!player.equals(buttons[row][col].getText())) {
                    colWin = false;
                    break;
                }
            }
            if (colWin) return true;
        }
        
        // Check major diagonal
        boolean majorDiagonalWin = true;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (!player.equals(buttons[i][i].getText())) {
                majorDiagonalWin = false;
                break;
            }
        }
        if (majorDiagonalWin) return true;
        
        // Check subdiagonal
        boolean subDiagonalWin = true;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (!player.equals(buttons[i][BOARD_SIZE - 1 - i].getText())) {
                subDiagonalWin = false;
                break;
            }
        }
        if (subDiagonalWin) return true;
        
        return false;
    }
    
    /**
     * Checks if the game is a tie (all buttons filled with no winner)
     * @return true if the game is a tie, false otherwise
     */
    private boolean checkTie() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Shows an alert when a player wins
     * @param winner the name of the winner ("Player" or "Computer")
     */
    private void showWinAlert(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("We have a winner!");
        alert.setContentText(winner + " wins!");
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
        gameOver = false;
        statusLabel.setText("Your turn (X) - Click any square to start!");
        
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
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
        
        Button resetButton = new Button("New Game");
        resetButton.setOnAction(_ -> resetGame());
        resetButton.setStyle("-fx-base: lightyellow;");
        
        gamePane.getChildren().addAll(statusLabel, gameGrid, resetButton);
        return gamePane;
    }
}
