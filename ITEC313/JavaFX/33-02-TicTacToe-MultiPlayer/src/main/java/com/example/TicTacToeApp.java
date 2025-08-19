package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Single-Player Tic-Tac-Toe Game Application
 * 
 * This is the application class for the single-player mode of the multiplayer Tic-Tac-Toe game.
 * It creates a simple, focused interface for playing against the computer.
 * 
 * @author ITEC313 Student
 * @version 3.0.0
 */
public class TicTacToeApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Single-Player Tic-Tac-Toe Game");
        
        // Create main container
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        
        // Create title
        Label titleLabel = new Label("Tic-Tac-Toe");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        
        // Create subtitle
        Label subtitleLabel = new Label("Play against the computer!");
        subtitleLabel.setFont(Font.font("Arial", 16));
        
        // Create the game
        TicTacToeGame game = new TicTacToeGame();
        
        // Add all components to the root
        root.getChildren().addAll(titleLabel, subtitleLabel, game.getGamePane());
        
        // Create scene
        Scene scene = new Scene(root, 400, 500);
        
        // Try to load CSS, but don't fail if it's not found
        try {
            String cssResource = getClass().getResource("/styles.css").toExternalForm();
            scene.getStylesheets().add(cssResource);
        } catch (Exception e) {
            System.out.println("Warning: Could not load CSS stylesheet: " + e.getMessage());
        }
        
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
