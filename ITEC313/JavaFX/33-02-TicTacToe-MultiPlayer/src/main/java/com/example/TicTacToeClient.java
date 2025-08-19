package com.example;

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * TicTacToeClient models a player in the multiplayer tic-tac-toe game.
 * It provides the user interface and handles communication with the server.
 * 
 * @author ITEC313 Student
 * @version 3.0.0
 */
public class TicTacToeClient extends Application implements TicTacToeConstants {
    // Indicate whether the player has the turn
    private boolean myTurn = false;

    // Indicate the token for the player
    private char myToken = ' ';

    // Indicate the token for the other player
    private char otherToken = ' ';
    
    // Player identifier
    private String playerId = "";

    // Create and initialize cells
    private Cell[][] cell = new Cell[BOARD_SIZE][BOARD_SIZE];

    // Create and initialize a title label
    private Label lblTitle = new Label();

    // Create and initialize a status label
    private Label lblStatus = new Label();

    // Indicate selected row and column by the current move
    private int rowSelected;
    private int columnSelected;

    // Input and output streams from/to server
    private DataInputStream fromServer;
    private DataOutputStream toServer;

    // Continue to play?
    private boolean continueToPlay = true;

    // Wait for the player to mark a cell
    private boolean waiting = true;

    // Host name or ip
    private String host = "localhost";

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Pane to hold cell
        GridPane pane = new GridPane();
        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                pane.add(cell[i][j] = new Cell(i, j), j, i);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(lblTitle);
        borderPane.setCenter(pane);
        borderPane.setBottom(lblStatus);

        // Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 320, 350);
        primaryStage.setTitle("TicTacToeClient"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        
        // Handle window close event to properly close connection
        primaryStage.setOnCloseRequest(event -> {
            logToConsole("Window closing, cleaning up connection");
            continueToPlay = false;
            try {
                if (fromServer != null) fromServer.close();
                if (toServer != null) toServer.close();
            } catch (IOException e) {
                logToConsole("Error closing connection: " + e.getMessage());
            }
        });
        
        primaryStage.show(); // Display the stage

        // Connect to the server
        connectToServer();
    }

    private void connectToServer() {
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket(host, 8000);

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());

            // Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Control the game on a separate thread
        new Thread(() -> {
            try {
                // Get notification from the server
                int player = fromServer.readInt();

                // Am I player 1 or 2?
                if (player == PLAYER1) {
                    myToken = 'X';
                    otherToken = 'O';
                    playerId = "Player 1 (X)";
                    Platform.runLater(() -> {
                        lblTitle.setText("Player 1 with token 'X'");
                        lblStatus.setText("Waiting for player 2 to join");
                    });
                    logToConsole("Connected as Player 1 (X)");

                    // Receive startup notification from the server
                    fromServer.readInt(); // Whatever read is ignored

                    // The other player has joined
                    Platform.runLater(() ->
                            lblStatus.setText("Player 2 has joined. I start first"));
                    logToConsole("Player 2 has joined. Game starting...");

                    // It is my turn
                    myTurn = true;
                } else if (player == PLAYER2) {
                    myToken = 'O';
                    otherToken = 'X';
                    playerId = "Player 2 (O)";
                    Platform.runLater(() -> {
                        lblTitle.setText("Player 2 with token 'O'");
                        lblStatus.setText("Waiting for player 1 to move");
                    });
                    logToConsole("Connected as Player 2 (O)");
                }

                // Continue to play
                while (continueToPlay) {
                    try {
                        if (player == PLAYER1) {
                            waitForPlayerAction(); // Wait for player 1 to move
                            sendMove(); // Send the move to the server
                            receiveInfoFromServer(); // Receive info from the server
                        } else if (player == PLAYER2) {
                            receiveInfoFromServer(); // Receive info from the server
                            waitForPlayerAction(); // Wait for player 2 to move
                            sendMove(); // Send player 2's move to the server
                        }
                    } catch (java.io.EOFException e) {
                        logToConsole("Server disconnected");
                        Platform.runLater(() -> lblStatus.setText("Server disconnected"));
                        break;
                    } catch (IOException e) {
                        logToConsole("Connection error: " + e.getMessage());
                        Platform.runLater(() -> lblStatus.setText("Connection error: " + e.getMessage()));
                        break;
                    }
                }
            } catch (Exception ex) {
                logToConsole("Unexpected error: " + ex.getMessage());
                Platform.runLater(() -> lblStatus.setText("Unexpected error: " + ex.getMessage()));
            }
        }).start();
    }
    
    /**
     * Log message to console
     */
    private void logToConsole(String message) {
        System.out.println("[CLIENT-" + playerId + "] " + new Date() + ": " + message);
    }

    /** Wait for the player to mark a cell */
    private void waitForPlayerAction() throws InterruptedException {
        while (waiting) {
            Thread.sleep(100);
        }
        waiting = true;
    }

    /** Send this player's move to the server */
    private void sendMove() throws IOException {
        toServer.writeInt(rowSelected); // Send the selected row
        toServer.writeInt(columnSelected); // Send the selected column
        logToConsole("Made move to [" + rowSelected + "," + columnSelected + "]");
    }

    /** Receive info from the server */
    private void receiveInfoFromServer() throws IOException {
        // Receive game status
        int status = fromServer.readInt();

        if (status == PLAYER1_WON) {
            // Player 1 won, stop playing
            continueToPlay = false;
            if (myToken == 'X') {
                Platform.runLater(() -> lblStatus.setText("I won! (X)"));
                logToConsole("GAME RESULT: I WON!");
            } else if (myToken == 'O') {
                Platform.runLater(() ->
                        lblStatus.setText("Player 1 (X) has won!"));
                logToConsole("GAME RESULT: Player 1 (X) WON!");
                try {
                    receiveMove();
                } catch (IOException e) {
                    logToConsole("Error receiving final move: " + e.getMessage());
                }
            }
        } else if (status == PLAYER2_WON) {
            // Player 2 won, stop playing
            continueToPlay = false;
            if (myToken == 'O') {
                Platform.runLater(() -> lblStatus.setText("I won! (O)"));
                logToConsole("GAME RESULT: I WON!");
            } else if (myToken == 'X') {
                Platform.runLater(() ->
                        lblStatus.setText("Player 2 (O) has won!"));
                logToConsole("GAME RESULT: Player 2 (O) WON!");
                try {
                    receiveMove();
                } catch (IOException e) {
                    logToConsole("Error receiving final move: " + e.getMessage());
                }
            }
        } else if (status == DRAW) {
            // No winner, game is over
            continueToPlay = false;
            Platform.runLater(() ->
                    lblStatus.setText("Game is over, no winner!"));
            logToConsole("GAME RESULT: DRAW - No winner!");

            if (myToken == 'O') {
                try {
                    receiveMove();
                } catch (IOException e) {
                    logToConsole("Error receiving final move: " + e.getMessage());
                }
            }
        } else {
            try {
                receiveMove();
                Platform.runLater(() -> lblStatus.setText("My turn"));
                myTurn = true; // It is my turn
            } catch (IOException e) {
                logToConsole("Error receiving move: " + e.getMessage());
                throw e; // Re-throw to be handled by the main loop
            }
        }
    }

    private void receiveMove() throws IOException {
        // Get the other player's move
        int row = fromServer.readInt();
        int column = fromServer.readInt();
        Platform.runLater(() -> cell[row][column].setToken(otherToken));
        logToConsole("Received opponent's move to [" + row + "," + column + "]");
    }

    // An inner class for a cell
    public class Cell extends Pane {
        // Indicate the row and column of this cell in the board
        private int row;
        private int column;

        // Token used for this cell
        private char token = ' ';

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
            this.setPrefSize(2000, 2000); // What happens without this?
            setStyle("-fx-border-color: black"); // Set cell's border
            this.setOnMouseClicked(e -> handleMouseClick());
        }

        /** Return token */
        public char getToken() {
            return token;
        }

        /** Set a new token */
        public void setToken(char c) {
            token = c;
            repaint();
        }

        protected void repaint() {
            if (token == 'X') {
                Line line1 = new Line(10, 10,
                        this.getWidth() - 10, this.getHeight() - 10);
                line1.endXProperty().bind(this.widthProperty().subtract(10));
                line1.endYProperty().bind(this.heightProperty().subtract(10));
                line1.setStroke(Color.RED);
                line1.setStrokeWidth(3);
                
                Line line2 = new Line(10, this.getHeight() - 10,
                        this.getWidth() - 10, 10);
                line2.startYProperty().bind(
                        this.heightProperty().subtract(10));
                line2.endXProperty().bind(this.widthProperty().subtract(10));
                line2.setStroke(Color.RED);
                line2.setStrokeWidth(3);

                // Add the lines to the pane
                this.getChildren().addAll(line1, line2);
            } else if (token == 'O') {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2,
                        this.getHeight() / 2, this.getWidth() / 2 - 10,
                        this.getHeight() / 2 - 10);
                ellipse.centerXProperty().bind(
                        this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(
                        this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(
                        this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(
                        this.heightProperty().divide(2).subtract(10));
                ellipse.setStroke(Color.GREEN);
                ellipse.setStrokeWidth(3);
                ellipse.setFill(Color.WHITE);

                getChildren().add(ellipse); // Add the ellipse to the pane
            }
        }

        /* Handle a mouse click event */
        private void handleMouseClick() {
            // If cell is not occupied and the player has the turn
            if (token == ' ' && myTurn) {
                setToken(myToken); // Set the player's token in the cell
                myTurn = false;
                rowSelected = row;
                columnSelected = column;
                lblStatus.setText("Waiting for the other player to move");
                waiting = false; // Just completed a successful move
                logToConsole("Selected cell [" + row + "," + column + "] for move");
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
