package com.acu.javafx.knighttour;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Knight's Tour Demo - JavaFX Application
 * 
 * This application demonstrates the Knight's Tour problem where a knight
 * must visit every square on a chessboard exactly once.
 * 
 * Features:
 * - Interactive 8x8 chessboard
 * - Click to place knight on any square
 * - Animated knight movement along the tour path
 * - Visual path display
 * - Solve button to start the tour animation
 * 
 * @author ACU JavaFX Team
 * @version 1.0.0
 */
public class KnightTourDemo extends Application {
    
    private static final int BOARD_SIZE = 8;
    private static final int CELL_SIZE = 60;
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 700;
    
    private ChessBoard chessBoard;
    private KnightTourSolver solver;
    private Button solveButton;
    private Button resetButton;
    private Label statusLabel;
    
    @Override
    public void start(Stage primaryStage) {
        // Initialize components
        chessBoard = new ChessBoard(BOARD_SIZE, CELL_SIZE);
        solver = new KnightTourSolver(chessBoard);
        
        // Create UI components
        createUI();
        
        // Set up event handlers
        setupEventHandlers();
        
        // Create main layout
        BorderPane root = new BorderPane();
        root.setCenter(chessBoard);
        root.setBottom(createControlPanel());
        
        // Create scene and show stage
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Knight's Tour Demo - Exercise18_33");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        // Update status
        updateStatus("Click on any square to place the knight, then click Solve to start the tour.");
    }
    
    /**
     * Creates the main UI components
     */
    private void createUI() {
        // Create control buttons
        solveButton = new Button("Solve");
        solveButton.setPrefWidth(100);
        solveButton.setDisable(true);
        
        resetButton = new Button("Reset");
        resetButton.setPrefWidth(100);
        
        // Create status label
        statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #333;");
    }
    
    /**
     * Creates the control panel at the bottom of the window
     */
    private VBox createControlPanel() {
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setAlignment(Pos.CENTER);
        
        // Button panel
        HBox buttonPanel = new HBox(20);
        buttonPanel.setAlignment(Pos.CENTER);
        buttonPanel.getChildren().addAll(solveButton, resetButton);
        
        // Status panel
        HBox statusPanel = new HBox();
        statusPanel.setAlignment(Pos.CENTER);
        statusPanel.getChildren().add(statusLabel);
        
        controlPanel.getChildren().addAll(buttonPanel, statusPanel);
        return controlPanel;
    }
    
    /**
     * Sets up event handlers for buttons and chessboard
     */
    private void setupEventHandlers() {
        // Solve button handler
        solveButton.setOnAction(e -> {
            if (chessBoard.hasKnight()) {
                solveButton.setDisable(true);
                resetButton.setDisable(true);
                updateStatus("Solving Knight's Tour...");
                
                // Start the tour animation
                solver.solveTour(() -> {
                    // Animation completed callback
                    updateStatus("Knight's Tour completed! Click Reset to try again.");
                    solveButton.setDisable(false);
                    resetButton.setDisable(false);
                });
            }
        });
        
        // Reset button handler
        resetButton.setOnAction(e -> {
            chessBoard.reset();
            solver.reset();
            solveButton.setDisable(true);
            updateStatus("Click on any square to place the knight, then click Solve to start the tour.");
        });
        
        // Chessboard click handler
        chessBoard.setOnSquareClicked((row, col) -> {
            if (!solver.isAnimating()) {
                chessBoard.placeKnight(row, col);
                solveButton.setDisable(false);
                updateStatus("Knight placed at (" + row + ", " + col + "). Click Solve to start the tour.");
            }
        });
    }
    
    /**
     * Updates the status label with the given message
     */
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }
    
    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
