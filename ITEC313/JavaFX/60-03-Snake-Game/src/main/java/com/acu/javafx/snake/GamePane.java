package com.acu.javafx.snake;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.List;

/**
 * Main game pane that handles the game logic and rendering
 * 
 * This class manages the game state, handles user input, and renders
 * the game using JavaFX Canvas. It implements the game loop and collision detection.
 * 
 * @author ACU JavaFX Course
 * @version 1.0.0
 */
public class GamePane extends BorderPane {
    
    // ACU Color Schema Constants
    private static final Color ACU_PURPLE = Color.web("#3C1053");
    private static final Color ACU_RED = Color.web("#B51825");
    private static final Color ACU_BRIGHT_RED = Color.web("#F2120C");
    private static final Color ACU_WARM_STONE = Color.web("#918B83");
    private static final Color ACU_DEEP_CHARCOAL = Color.web("#302C2A");
    private static final Color ACU_SOFT_IVORY = Color.web("#F2EFEB");
    
    // Game constants
    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 600;
    private static final int CELL_SIZE = 20;
    private static final int INITIAL_SPEED = 150; // milliseconds
    
    // Game components
    private Canvas gameCanvas;
    private GraphicsContext gc;
    private Snake snake;
    private Food food;
    private Timeline gameLoop;
    private AnimationTimer animationTimer;
    
    // UI components
    private Label scoreLabel;
    private Label highScoreLabel;
    private Button startButton;
    private Button pauseButton;
    private Button resetButton;
    private Label gameStatusLabel;
    
    // Game state
    private boolean isGameRunning;
    private boolean isGamePaused;
    private int highScore;
    private int currentScore;
    
    /**
     * Constructor - initializes the game pane
     */
    public GamePane() {
        initializeUI();
        initializeGame();
        setupEventHandlers();
    }
    
    /**
     * Initialize the user interface components
     */
    private void initializeUI() {
        // Create game canvas
        gameCanvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        gc = gameCanvas.getGraphicsContext2D();
        
        // Create UI components
        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        scoreLabel.setTextFill(ACU_PURPLE);
        
        highScoreLabel = new Label("High Score: 0");
        highScoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        highScoreLabel.setTextFill(ACU_RED);
        
        gameStatusLabel = new Label("Press SPACE to start");
        gameStatusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        gameStatusLabel.setTextFill(ACU_PURPLE);
        
        // Create "How to Play" section
        Label howToPlayTitle = new Label("How to Play");
        howToPlayTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        howToPlayTitle.setTextFill(ACU_PURPLE);
        
        Label controlsLabel = new Label("Controls:");
        controlsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        controlsLabel.setTextFill(ACU_DEEP_CHARCOAL);
        
        Label arrowKeysLabel = new Label("↑↓←→ Arrow Keys");
        arrowKeysLabel.setFont(Font.font("Arial", 12));
        arrowKeysLabel.setTextFill(ACU_DEEP_CHARCOAL);
        
        Label wasdLabel = new Label("W A S D Keys");
        wasdLabel.setFont(Font.font("Arial", 12));
        wasdLabel.setTextFill(ACU_DEEP_CHARCOAL);
        
        Label spaceLabel = new Label("SPACE - Start/Pause");
        spaceLabel.setFont(Font.font("Arial", 12));
        spaceLabel.setTextFill(ACU_DEEP_CHARCOAL);
        
        Label rKeyLabel = new Label("R - Reset Game");
        rKeyLabel.setFont(Font.font("Arial", 12));
        rKeyLabel.setTextFill(ACU_DEEP_CHARCOAL);
        
        Label objectiveLabel = new Label("Objective:");
        objectiveLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        objectiveLabel.setTextFill(ACU_DEEP_CHARCOAL);
        
        Label eatFoodLabel = new Label("• Eat food to grow");
        eatFoodLabel.setFont(Font.font("Arial", 12));
        eatFoodLabel.setTextFill(ACU_DEEP_CHARCOAL);
        
        Label avoidCollisionLabel = new Label("• Avoid walls & self");
        avoidCollisionLabel.setFont(Font.font("Arial", 12));
        avoidCollisionLabel.setTextFill(ACU_DEEP_CHARCOAL);
        
        Label scoreLabel2 = new Label("• Score 10 points per food");
        scoreLabel2.setFont(Font.font("Arial", 12));
        scoreLabel2.setTextFill(ACU_DEEP_CHARCOAL);
        
        startButton = new Button("Start Game");
        startButton.setStyle("-fx-background-color: " + toHex(ACU_PURPLE) + "; -fx-text-fill: white; -fx-font-weight: bold;");
        
        pauseButton = new Button("Pause");
        pauseButton.setStyle("-fx-background-color: " + toHex(ACU_RED) + "; -fx-text-fill: white; -fx-font-weight: bold;");
        pauseButton.setDisable(true);
        
        resetButton = new Button("Reset");
        resetButton.setStyle("-fx-background-color: " + toHex(ACU_WARM_STONE) + "; -fx-text-fill: white; -fx-font-weight: bold;");
        
        // Create control panel
        HBox buttonBox = new HBox(10, startButton, pauseButton, resetButton);
        buttonBox.setAlignment(Pos.CENTER);
        
        // Create "How to Play" section
        VBox howToPlaySection = new VBox(5, 
            howToPlayTitle,
            controlsLabel,
            arrowKeysLabel,
            wasdLabel,
            spaceLabel,
            rKeyLabel,
            objectiveLabel,
            eatFoodLabel,
            avoidCollisionLabel,
            scoreLabel2
        );
        howToPlaySection.setAlignment(Pos.TOP_LEFT);
        howToPlaySection.setPadding(new Insets(10, 0, 0, 0));
        
        VBox infoPanel = new VBox(10, scoreLabel, highScoreLabel, gameStatusLabel, buttonBox, howToPlaySection);
        infoPanel.setAlignment(Pos.CENTER);
        infoPanel.setPadding(new Insets(20));
        
        // Set up the layout
        setCenter(gameCanvas);
        setRight(infoPanel);
        
        // Set background color
        setStyle("-fx-background-color: " + toHex(ACU_SOFT_IVORY) + ";");
    }
    
    /**
     * Initialize the game components
     */
    private void initializeGame() {
        // Create snake at center
        int startX = GAME_WIDTH / 2;
        int startY = GAME_HEIGHT / 2;
        snake = new Snake(startX, startY);
        
        // Create food
        food = new Food(GAME_WIDTH, GAME_HEIGHT);
        
        // Initialize game state
        isGameRunning = false;
        isGamePaused = false;
        currentScore = 0;
        highScore = 0;
        
        // Draw initial state
        drawGame();
    }
    
    /**
     * Set up event handlers for user input
     */
    private void setupEventHandlers() {
        // Keyboard input
        setOnKeyPressed(this::handleKeyPress);
        setFocusTraversable(true);
        
        // Button handlers
        startButton.setOnAction(e -> startGame());
        pauseButton.setOnAction(e -> togglePause());
        resetButton.setOnAction(e -> resetGame());
        
        // Create game loop
        gameLoop = new Timeline(new KeyFrame(Duration.millis(INITIAL_SPEED), e -> updateGame()));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
    }
    
    /**
     * Handle keyboard input
     * @param event key event
     */
    private void handleKeyPress(KeyEvent event) {
        // Handle space bar for starting game or toggling pause
        if (event.getCode() == KeyCode.SPACE) {
            if (!isGameRunning) {
                // Start new game if not running
                startGame();
            } else if (isGamePaused) {
                // Resume if paused
                startGame();
            } else {
                // Pause if running
                togglePause();
            }
            return;
        }
        
        // Only handle movement keys if game is running and not paused
        if (!isGameRunning || isGamePaused) {
            return;
        }
        
        switch (event.getCode()) {
            case UP:
            case W:
                snake.setDirection(Snake.Direction.UP);
                break;
            case DOWN:
            case S:
                snake.setDirection(Snake.Direction.DOWN);
                break;
            case LEFT:
            case A:
                snake.setDirection(Snake.Direction.LEFT);
                break;
            case RIGHT:
            case D:
                snake.setDirection(Snake.Direction.RIGHT);
                break;
            case R:
                resetGame();
                break;
        }
    }
    
    /**
     * Start the game
     */
    public void startGame() {
        if (!isGameRunning) {
            // Starting a new game
            isGameRunning = true;
            isGamePaused = false;
            gameStatusLabel.setText("Game Running");
            startButton.setDisable(true);
            pauseButton.setDisable(false);
            pauseButton.setText("Pause");
            gameLoop.play();
        } else if (isGamePaused) {
            // Resuming a paused game
            isGamePaused = false;
            gameStatusLabel.setText("Game Running");
            pauseButton.setText("Pause");
            gameLoop.play();
        }
        // If game is already running and not paused, do nothing
    }
    
    /**
     * Toggle pause state
     */
    private void togglePause() {
        if (!isGameRunning) return;
        
        isGamePaused = !isGamePaused;
        if (isGamePaused) {
            gameStatusLabel.setText("Game Paused");
            pauseButton.setText("Resume");
            gameLoop.pause();
        } else {
            gameStatusLabel.setText("Game Running");
            pauseButton.setText("Pause");
            gameLoop.play();
        }
    }
    
    /**
     * Reset the game to initial state
     */
    private void resetGame() {
        gameLoop.stop();
        isGameRunning = false;
        isGamePaused = false;
        
        // Reset snake
        int startX = GAME_WIDTH / 2;
        int startY = GAME_HEIGHT / 2;
        snake.reset(startX, startY);
        
        // Reset food
        food.generateNewPosition(snake.getBody());
        
        // Reset score
        currentScore = 0;
        updateScoreDisplay();
        
        // Reset UI
        gameStatusLabel.setText("Press SPACE to start");
        startButton.setDisable(false);
        pauseButton.setDisable(true);
        pauseButton.setText("Pause");
        
        // Redraw
        drawGame();
    }
    
    /**
     * Update game logic
     */
    private void updateGame() {
        if (!isGameRunning || isGamePaused) return;
        
        // Move snake
        snake.move(GAME_WIDTH, GAME_HEIGHT);
        
        // Check if snake is still alive
        if (!snake.isAlive()) {
            gameOver();
            return;
        }
        
        // Check for food collision
        if (snake.isHeadAt(food.getX(), food.getY())) {
            snake.grow();
            currentScore = snake.getScore();
            updateScoreDisplay();
            food.generateNewPosition(snake.getBody());
        } else {
            snake.removeTail();
        }
        
        // Draw the game
        drawGame();
    }
    
    /**
     * Handle game over
     */
    private void gameOver() {
        gameLoop.stop();
        isGameRunning = false;
        isGamePaused = false;
        
        // Update high score
        if (currentScore > highScore) {
            highScore = currentScore;
            updateScoreDisplay();
        }
        
        gameStatusLabel.setText("Game Over! Press SPACE to restart");
        startButton.setDisable(false);
        pauseButton.setDisable(true);
        pauseButton.setText("Pause");
    }
    
    /**
     * Update score display
     */
    private void updateScoreDisplay() {
        scoreLabel.setText("Score: " + currentScore);
        highScoreLabel.setText("High Score: " + highScore);
    }
    
    /**
     * Draw the game on the canvas
     */
    private void drawGame() {
        // Clear canvas
        gc.setFill(ACU_SOFT_IVORY);
        gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        
        // Draw game border
        gc.setStroke(ACU_DEEP_CHARCOAL);
        gc.setLineWidth(2);
        gc.strokeRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        
        // Draw grid lines
        drawGrid();
        
        // Draw snake
        drawSnake();
        
        // Draw food
        drawFood();
        
        // Draw game over message if needed
        if (!snake.isAlive() && !isGameRunning) {
            drawGameOverMessage();
        }
    }
    
    /**
     * Draw grid lines on the game board
     */
    private void drawGrid() {
        // Set grid line color and width
        gc.setStroke(ACU_WARM_STONE);
        gc.setLineWidth(0.5);
        
        // Draw vertical grid lines
        for (int x = CELL_SIZE; x < GAME_WIDTH; x += CELL_SIZE) {
            gc.strokeLine(x, 0, x, GAME_HEIGHT);
        }
        
        // Draw horizontal grid lines
        for (int y = CELL_SIZE; y < GAME_HEIGHT; y += CELL_SIZE) {
            gc.strokeLine(0, y, GAME_WIDTH, y);
        }
    }
    
    /**
     * Draw the snake
     */
    private void drawSnake() {
        List<javafx.geometry.Point2D> body = snake.getBody();
        
        for (int i = 0; i < body.size(); i++) {
            javafx.geometry.Point2D segment = body.get(i);
            double x = segment.getX();
            double y = segment.getY();
            
            if (i == 0) {
                // Draw head
                gc.setFill(ACU_PURPLE);
                gc.fillOval(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);
                gc.setStroke(ACU_DEEP_CHARCOAL);
                gc.setLineWidth(1);
                gc.strokeOval(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);
            } else {
                // Draw body
                gc.setFill(ACU_RED);
                gc.fillRect(x + 1, y + 1, CELL_SIZE - 2, CELL_SIZE - 2);
                gc.setStroke(ACU_DEEP_CHARCOAL);
                gc.setLineWidth(1);
                gc.strokeRect(x + 1, y + 1, CELL_SIZE - 2, CELL_SIZE - 2);
            }
        }
    }
    
    /**
     * Draw the food
     */
    private void drawFood() {
        double x = food.getX();
        double y = food.getY();
        
        // Draw food as a circle
        gc.setFill(ACU_BRIGHT_RED);
        gc.fillOval(x + 3, y + 3, CELL_SIZE - 6, CELL_SIZE - 6);
        gc.setStroke(ACU_DEEP_CHARCOAL);
        gc.setLineWidth(2);
        gc.strokeOval(x + 3, y + 3, CELL_SIZE - 6, CELL_SIZE - 6);
        
        // Add a highlight
        gc.setFill(Color.WHITE);
        gc.fillOval(x + 6, y + 6, 4, 4);
    }
    
    /**
     * Draw game over message
     */
    private void drawGameOverMessage() {
        gc.setFill(ACU_PURPLE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gc.fillText("GAME OVER", GAME_WIDTH / 2 - 80, GAME_HEIGHT / 2 - 20);
        
        gc.setFill(ACU_RED);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        gc.fillText("Final Score: " + currentScore, GAME_WIDTH / 2 - 60, GAME_HEIGHT / 2 + 10);
    }
    
    /**
     * Convert Color to hex string
     * @param color the color to convert
     * @return hex string representation
     */
    private String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
