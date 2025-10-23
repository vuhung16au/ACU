package com.acu.javafx.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main Snake Game Application
 * 
 * This is the entry point for the Snake game built with JavaFX.
 * The game features a modern UI with ACU color schema and smooth gameplay.
 * 
 * @author ACU JavaFX Course
 * @version 1.0.0
 */
public class SnakeGame extends Application {
    
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 700;
    private static final String GAME_TITLE = "Snake Game - ACU JavaFX";
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Create the main game pane
            GamePane gamePane = new GamePane();
            
            // Create the main scene
            Scene scene = new Scene(gamePane, WINDOW_WIDTH, WINDOW_HEIGHT);
            
            // Set up the stage
            primaryStage.setTitle(GAME_TITLE);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            
            // Start the game
            gamePane.startGame();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Main method to launch the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
