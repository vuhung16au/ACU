package com.acu.javafx.clockanimation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClockAnimation - A JavaFX application that demonstrates a digital clock with animation.
 * 
 * This application displays a digital clock that updates every second using Timeline animation.
 * The clock shows the current time in HH:mm:ss format and includes start/stop controls.
 * The display has a modern design with a dark background and bright text.
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class ClockAnimation extends Application {
    
    private Timeline animation;
    private Text clockText;
    private Button startButton;
    private Button stopButton;
    private boolean isRunning = false;
    
    @Override
    public void start(Stage primaryStage) {
        // Create the main layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #2c3e50;");
        root.setPadding(new Insets(20));
        
        // Create the clock display
        clockText = new Text();
        clockText.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        clockText.setFill(Color.WHITE);
        clockText.setStroke(Color.CYAN);
        clockText.setStrokeWidth(1);
        
        // Center the clock text
        BorderPane.setAlignment(clockText, Pos.CENTER);
        root.setCenter(clockText);
        
        // Create control buttons
        startButton = new Button("Start");
        startButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        startButton.setPrefWidth(100);
        
        stopButton = new Button("Stop");
        stopButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        stopButton.setPrefWidth(100);
        stopButton.setDisable(true);
        
        // Create button container
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(startButton, stopButton);
        root.setBottom(buttonBox);
        
        // Set up button event handlers
        startButton.setOnAction(e -> startAnimation());
        stopButton.setOnAction(e -> stopAnimation());
        
        // Create the animation timeline
        animation = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateClock()));
        animation.setCycleCount(Timeline.INDEFINITE);
        
        // Initialize clock display
        updateClock();
        
        // Create scene and stage
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setTitle("Clock Animation");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    /**
     * Updates the clock display with the current time.
     */
    private void updateClock() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());
        clockText.setText(currentTime);
    }
    
    /**
     * Starts the clock animation.
     */
    private void startAnimation() {
        if (!isRunning) {
            animation.play();
            isRunning = true;
            startButton.setDisable(true);
            stopButton.setDisable(false);
        }
    }
    
    /**
     * Stops the clock animation.
     */
    private void stopAnimation() {
        if (isRunning) {
            animation.pause();
            isRunning = false;
            startButton.setDisable(false);
            stopButton.setDisable(true);
        }
    }
    
    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
} 