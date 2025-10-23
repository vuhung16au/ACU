package com.acu.javafx.game2048;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Main JavaFX application for the 2048 game.
 * Creates the game window, handles user input, and manages the game state.
 * 
 * @author ACU JavaFX Course
 * @version 1.0
 */
public class Game2048App extends Application {
    
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 600;
    private static final int GRID_SIZE = 4;
    private static final int TILE_SIZE = 100;
    private static final int TILE_SPACING = 10;
    
    private Board gameBoard;
    private GameGrid gameGrid;
    private Label scoreLabel;
    private Label gameStatusLabel;
    private Button newGameButton;
    private Button helpButton;
    private Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) {
        // Store reference to primary stage
        this.primaryStage = primaryStage;
        
        // Initialize game board
        gameBoard = new Board(GRID_SIZE);
        
        // Create the main layout
        VBox root = createMainLayout();
        
        // Create the scene
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setFill(ACUColorScheme.getGameBackground());
        
        // Set up keyboard controls
        setupKeyboardControls(scene);
        
        // Configure the stage
        primaryStage.setTitle("2048 Game - ACU JavaFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        // Initial game grid update
        updateGameDisplay();
    }
    
    /**
     * Creates the main layout of the game.
     * 
     * @return The main VBox layout
     */
    private VBox createMainLayout() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(
            ACUColorScheme.getGameBackground(), null, null)));
        
        // Title
        Label titleLabel = createTitleLabel();
        
        // Score and controls section
        HBox topSection = createTopSection();
        
        // Game grid
        gameGrid = new GameGrid(gameBoard, TILE_SIZE, TILE_SPACING);
        
        // Game status and new game button
        VBox bottomSection = createBottomSection();
        
        root.getChildren().addAll(titleLabel, topSection, gameGrid, bottomSection);
        
        return root;
    }
    
    /**
     * Creates the title label.
     * 
     * @return The title label
     */
    private Label createTitleLabel() {
        Label titleLabel = new Label("2048");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        titleLabel.setTextFill(ACUColorScheme.getTextDark());
        return titleLabel;
    }
    
    /**
     * Creates the top section with score and controls.
     * 
     * @return The top section HBox
     */
    private HBox createTopSection() {
        HBox topSection = new HBox(20);
        topSection.setAlignment(Pos.CENTER);
        
        // Score display
        VBox scoreSection = new VBox(5);
        scoreSection.setAlignment(Pos.CENTER);
        
        Label scoreTitleLabel = new Label("SCORE");
        scoreTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        scoreTitleLabel.setTextFill(ACUColorScheme.getTextDark());
        
        scoreLabel = new Label("0");
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        scoreLabel.setTextFill(ACUColorScheme.getTextDark());
        
        scoreSection.getChildren().addAll(scoreTitleLabel, scoreLabel);
        
        // New Game button
        newGameButton = new Button("NEW GAME");
        newGameButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        newGameButton.setTextFill(ACUColorScheme.getTextLight());
        newGameButton.setBackground(new Background(new BackgroundFill(
            ACUColorScheme.ACU_PURPLE, new CornerRadii(5), null)));
        newGameButton.setPadding(new Insets(10, 20, 10, 20));
        newGameButton.setOnAction(e -> startNewGame());
        
        // Help button
        helpButton = new Button("?");
        helpButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        helpButton.setTextFill(ACUColorScheme.getTextLight());
        helpButton.setBackground(new Background(new BackgroundFill(
            ACUColorScheme.ACU_RED, new CornerRadii(5), null)));
        helpButton.setPadding(new Insets(10, 15, 10, 15));
        helpButton.setMinWidth(50);
        helpButton.setOnAction(e -> showHelp());
        
        topSection.getChildren().addAll(scoreSection, newGameButton, helpButton);
        
        return topSection;
    }
    
    /**
     * Creates the bottom section with game status.
     * 
     * @return The bottom section VBox
     */
    private VBox createBottomSection() {
        VBox bottomSection = new VBox(10);
        bottomSection.setAlignment(Pos.CENTER);
        
        gameStatusLabel = new Label("");
        gameStatusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gameStatusLabel.setTextFill(ACUColorScheme.ACU_RED);
        
        bottomSection.getChildren().add(gameStatusLabel);
        
        return bottomSection;
    }
    
    /**
     * Sets up keyboard controls for the game.
     * 
     * @param scene The scene to add controls to
     */
    private void setupKeyboardControls(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            
            switch (keyCode) {
                case UP:
                case W:
                    gameBoard.moveUp();
                    break;
                case DOWN:
                case S:
                    gameBoard.moveDown();
                    break;
                case LEFT:
                case A:
                    gameBoard.moveLeft();
                    break;
                case RIGHT:
                case D:
                    gameBoard.moveRight();
                    break;
                case R:
                    startNewGame();
                    break;
                case ESCAPE:
                    System.exit(0);
                    break;
                case SLASH:
                    if (event.isShiftDown()) {
                        showHelp();
                    }
                    break;
                default:
                    break;
            }
            
            updateGameDisplay();
        });
    }
    
    /**
     * Updates the game display with current board state.
     */
    private void updateGameDisplay() {
        // Update score
        scoreLabel.setText(String.valueOf(gameBoard.getScore()));
        
        // Update game status
        String status = gameBoard.getGameStatus();
        if (status != null) {
            if ("WON".equals(status)) {
                gameStatusLabel.setText("You WON! 🎉");
                gameStatusLabel.setTextFill(ACUColorScheme.ACU_PURPLE);
            } else if ("LOST".equals(status)) {
                gameStatusLabel.setText("Game Over! 😢");
                gameStatusLabel.setTextFill(ACUColorScheme.ACU_RED);
            }
        } else {
            gameStatusLabel.setText("");
        }
        
        // Update game grid
        gameGrid.updateDisplay();
    }
    
    /**
     * Starts a new game.
     */
    private void startNewGame() {
        gameBoard.resetGame();
        gameStatusLabel.setText("");
        updateGameDisplay();
    }
    
    /**
     * Shows the help dialog with game instructions.
     */
    private void showHelp() {
        HelpDialog helpDialog = new HelpDialog();
        helpDialog.showHelp(primaryStage);
    }
    
    /**
     * Main method to launch the application.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
