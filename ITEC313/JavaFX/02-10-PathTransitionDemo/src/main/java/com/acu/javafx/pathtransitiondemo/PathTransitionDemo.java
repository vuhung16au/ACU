package com.acu.javafx.pathtransitiondemo;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

// TODO: Fix rectangle position bug. Should move around the circle.

/**
 * JavaFX application demonstrating PathTransition animations.
 * 
 * This application shows a rectangle moving along a circular path with
 * animation controls (play, pause, stop) and demonstrates the use of
 * PathTransition for creating smooth object movement along defined paths.
 * 
 * @author ACU JavaFX Team
 * @version 1.0.0
 */
public class PathTransitionDemo extends Application {

    private PathTransition pathTransition;
    private Rectangle rectangle;
    private Circle path;

    /**
     * The main entry point for the JavaFX application.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and starts the JavaFX application.
     * 
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        // Set up the main window
        primaryStage.setTitle("JavaFX PathTransition Demo");
        
        // Create the main container
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f0f0f0;");

        // Create the animation area
        VBox animationArea = createAnimationArea();
        
        // Create the control buttons
        HBox controls = createControls();
        
        // Add components to the main container
        root.getChildren().addAll(animationArea, controls);
        
        // Create and set the scene
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        
        // Show the stage
        primaryStage.show();
    }

    /**
     * Creates the animation area with the path and moving rectangle.
     * 
     * @return VBox containing the animation area
     */
    private VBox createAnimationArea() {
        VBox animationArea = new VBox(10);
        animationArea.setAlignment(Pos.CENTER);
        animationArea.setStyle("-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-width: 2;");
        animationArea.setPadding(new Insets(20));
        animationArea.setPrefSize(500, 300);

        // Create a container for the path and rectangle with relative positioning
        javafx.scene.layout.StackPane pathContainer = new javafx.scene.layout.StackPane();
        pathContainer.setAlignment(Pos.CENTER);

        // Create the circular path
        path = new Circle(100);
        path.setFill(null);
        path.setStroke(Color.BLUE);
        path.setStrokeWidth(3);
        path.setStyle("-fx-stroke-dash-array: 10 5;"); // Dashed line

        // Create the rectangle that will move along the path
        rectangle = new Rectangle(20, 20);
        rectangle.setFill(Color.RED);
        rectangle.setStroke(Color.DARKRED);
        rectangle.setStrokeWidth(2);

        // The rectangle will be positioned by the PathTransition
        // No manual positioning needed - PathTransition handles the movement

        // Create the path transition
        pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(4));
        pathTransition.setPath(path);
        pathTransition.setNode(rectangle);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(false);

        // Add components to the path container
        pathContainer.getChildren().addAll(path, rectangle);

        // Add the path container to the animation area
        animationArea.getChildren().add(pathContainer);

        return animationArea;
    }

    /**
     * Creates the control buttons for the animation.
     * 
     * @return HBox containing the control buttons
     */
    private HBox createControls() {
        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10));

        // Create control buttons
        Button playButton = createButton("Play", "#4CAF50", () -> {
            if (pathTransition.getStatus() == javafx.animation.Animation.Status.RUNNING) {
                pathTransition.pause();
            } else {
                pathTransition.play();
            }
        });

        Button pauseButton = createButton("Pause", "#FF9800", () -> pathTransition.pause());
        Button stopButton = createButton("Stop", "#F44336", () -> pathTransition.stop());
        Button reverseButton = createButton("Reverse", "#9C27B0", () -> {
            pathTransition.setAutoReverse(!pathTransition.isAutoReverse());
        });

        controls.getChildren().addAll(playButton, pauseButton, stopButton, reverseButton);
        return controls;
    }

    /**
     * Creates a styled button with the given text, color, and action.
     * 
     * @param text the button text
     * @param color the button color in hex format
     * @param action the action to perform when clicked
     * @return the created Button
     */
    private Button createButton(String text, String color, Runnable action) {
        Button button = new Button(text);
        button.setPrefSize(100, 40);
        button.setStyle(String.format(
            "-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; " +
            "-fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;",
            color
        ));
        
        // Add hover effect
        button.setOnMouseEntered(e -> button.setStyle(String.format(
            "-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; " +
            "-fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand; -fx-opacity: 0.8;",
            color
        )));
        
        button.setOnMouseExited(e -> button.setStyle(String.format(
            "-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; " +
            "-fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;",
            color
        )));
        
        button.setOnAction(e -> action.run());
        return button;
    }

    /**
     * Called when the application is stopped.
     * Cleans up resources and stops animations.
     */
    @Override
    public void stop() {
        if (pathTransition != null) {
            pathTransition.stop();
        }
    }
} 