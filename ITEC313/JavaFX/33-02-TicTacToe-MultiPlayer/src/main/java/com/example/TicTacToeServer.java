package com.example;

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * TicTacToeServer serves multiple clients and creates sessions for pairs of players.
 * Each session handles a game between two players.
 * 
 * @author ITEC313 Student
 * @version 3.0.0
 */
public class TicTacToeServer extends Application implements TicTacToeConstants {
    private int sessionNo = 1; // Number a session
    private TextArea taLog; // TextArea for UI logging

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        taLog = new TextArea();
        
        // Create a scene and place it in the stage
        Scene scene = new Scene(new ScrollPane(taLog), 600, 400);
        primaryStage.setTitle("TicTacToeServer"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        new Thread(() -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(8000);
                logToUI(new Date() + ": Server started at socket 8000");
                logToConsole("Server started at socket 8000");

                // Ready to create a session for every two players
                while (true) {
                    logToUI(new Date() + ": Wait for players to join session " + sessionNo);
                    logToConsole("Wait for players to join session " + sessionNo);

                    // Connect to player 1
                    Socket player1 = serverSocket.accept();

                    logToUI(new Date() + ": Player 1 joined session " + sessionNo);
                    logToUI("Player 1's IP address: " + player1.getInetAddress().getHostAddress());
                    logToConsole("Player 1 joined session " + sessionNo + " from " + player1.getInetAddress().getHostAddress());

                    // Notify that the player is Player 1
                    new DataOutputStream(
                            player1.getOutputStream()).writeInt(PLAYER1);

                    // Connect to player 2
                    Socket player2 = serverSocket.accept();

                    logToUI(new Date() + ": Player 2 joined session " + sessionNo);
                    logToUI("Player 2's IP address: " + player2.getInetAddress().getHostAddress());
                    logToConsole("Player 2 joined session " + sessionNo + " from " + player2.getInetAddress().getHostAddress());

                    // Notify that the player is Player 2
                    new DataOutputStream(
                            player2.getOutputStream()).writeInt(PLAYER2);

                    // Display this session and increment session number
                    logToUI(new Date() + ": Start a thread for session " + sessionNo);
                    logToConsole("Start a thread for session " + sessionNo);

                    // Launch a new thread for this session of two players
                    new Thread(new HandleASession(player1, player2, sessionNo)).start();
                    sessionNo++;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
    
    /**
     * Log message to UI
     */
    private void logToUI(String message) {
        Platform.runLater(() -> taLog.appendText(message + "\n"));
    }
    
    /**
     * Log message to console
     */
    private void logToConsole(String message) {
        System.out.println("[SERVER] " + new Date() + ": " + message);
    }

    // Define the thread class for handling a new session for two players
    class HandleASession implements Runnable, TicTacToeConstants {
        private Socket player1;
        private Socket player2;
        private int sessionId;

        // Create and initialize cells
        private char[][] cell = new char[3][3];

        private DataInputStream fromPlayer1;
        private DataOutputStream toPlayer1;
        private DataInputStream fromPlayer2;
        private DataOutputStream toPlayer2;

        // Continue to play
        private boolean continueToPlay = true;

        /** Construct a thread */
        public HandleASession(Socket player1, Socket player2, int sessionId) {
            this.player1 = player1;
            this.player2 = player2;
            this.sessionId = sessionId;

            // Initialize cells
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    cell[i][j] = ' ';
        }

        /** Implement the run() method for the thread */
        public void run() {
            try {
                // Create data input and output streams
                fromPlayer1 = new DataInputStream(
                        player1.getInputStream());
                toPlayer1 = new DataOutputStream(
                        player1.getOutputStream());
                fromPlayer2 = new DataInputStream(
                        player2.getInputStream());
                toPlayer2 = new DataOutputStream(
                        player2.getOutputStream());

                logToUI("Session " + sessionId + ": Game started");
                logToConsole("Session " + sessionId + ": Game started");

                // Write anything to notify player 1 to start
                // This is just to let player 1 know to start
                toPlayer1.writeInt(1);

                // Continuously serve the players and determine and report
                // the game status to the players
                while (true) {
                    // Receive a move from player 1
                    int row = fromPlayer1.readInt();
                    int column = fromPlayer1.readInt();
                    cell[row][column] = 'X';
                    
                    logToUI("Session " + sessionId + ": Player 1 (X) moved to [" + row + "," + column + "]");
                    logToConsole("Session " + sessionId + ": Player 1 (X) moved to [" + row + "," + column + "]");

                    // Check if Player 1 wins
                    if (isWon('X')) {
                        logToUI("Session " + sessionId + ": Player 1 (X) WINS!");
                        logToConsole("Session " + sessionId + ": Player 1 (X) WINS!");
                        toPlayer1.writeInt(PLAYER1_WON);
                        toPlayer2.writeInt(PLAYER1_WON);
                        sendMove(toPlayer2, row, column);
                        break; // Break the loop
                    } else if (isFull()) { // Check if all cells are filled
                        logToUI("Session " + sessionId + ": Game ended in DRAW!");
                        logToConsole("Session " + sessionId + ": Game ended in DRAW!");
                        toPlayer1.writeInt(DRAW);
                        toPlayer2.writeInt(DRAW);
                        sendMove(toPlayer2, row, column);
                        break;
                    } else {
                        // Notify player 2 to take the turn
                        toPlayer2.writeInt(CONTINUE);
                        // Send player 1's selected row and column to player 2
                        sendMove(toPlayer2, row, column);
                    }

                    // Receive a move from Player 2
                    row = fromPlayer2.readInt();
                    column = fromPlayer2.readInt();
                    cell[row][column] = 'O';
                    
                    logToUI("Session " + sessionId + ": Player 2 (O) moved to [" + row + "," + column + "]");
                    logToConsole("Session " + sessionId + ": Player 2 (O) moved to [" + row + "," + column + "]");

                    // Check if Player 2 wins
                    if (isWon('O')) {
                        logToUI("Session " + sessionId + ": Player 2 (O) WINS!");
                        logToConsole("Session " + sessionId + ": Player 2 (O) WINS!");
                        toPlayer1.writeInt(PLAYER2_WON);
                        toPlayer2.writeInt(PLAYER2_WON);
                        sendMove(toPlayer1, row, column);
                        break;
                    } else {
                        // Notify player 1 to take the turn
                        toPlayer1.writeInt(CONTINUE);

                        // Send player 2's selected row and column to player 1
                        sendMove(toPlayer1, row, column);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        /** Send the move to other player */
        private void sendMove(DataOutputStream out, int row, int column)
                throws IOException {
            out.writeInt(row); // Send row index
            out.writeInt(column); // Send column index
        }
        
        /**
         * Log message to UI
         */
        private void logToUI(String message) {
            Platform.runLater(() -> taLog.appendText(message + "\n"));
        }
        
        /**
         * Log message to console
         */
        private void logToConsole(String message) {
            System.out.println("[SERVER-Session" + sessionId + "] " + new Date() + ": " + message);
        }

        /** Determine if the cells are all occupied */
        private boolean isFull() {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (cell[i][j] == ' ')
                        return false; // At least one cell is not filled

            // All cells are filled
            return true;
        }

        /** Determine if the player with the specified token wins */
        private boolean isWon(char token) {
            // Check all rows
            for (int i = 0; i < 3; i++)
                if ((cell[i][0] == token)
                        && (cell[i][1] == token)
                        && (cell[i][2] == token)) {
                    return true;
                }

            // Check all columns
            for (int j = 0; j < 3; j++)
                if ((cell[0][j] == token)
                        && (cell[1][j] == token)
                        && (cell[2][j] == token)) {
                    return true;
                }

            // Check major diagonal
            if ((cell[0][0] == token)
                    && (cell[1][1] == token)
                    && (cell[2][2] == token)) {
                return true;
            }

            // Check subdiagonal
            if ((cell[0][2] == token)
                    && (cell[1][1] == token)
                    && (cell[2][0] == token)) {
                return true;
            }

            // All checked, but no winner
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
