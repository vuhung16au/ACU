package com.acu.javafx.flagrisinganimation;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FlagRisingAnimation - A JavaFX application that demonstrates flag rising animation
 *
 * This application shows a flag (Australian flag image) rising up along a vertical line path
 * using PathTransition animation. The animation repeats 5 times and takes 10 seconds
 * to complete each cycle.
 * 
 * Based on the example from: https://liveexample.pearsoncmg.com/html/FlagRisingAnimation.html
 * 
 * @author ACU JavaFX Team
 * @version 1.0.0
 */
public class FlagRisingAnimation extends Application {
    
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Create a pane
        Pane pane = new Pane();
        
        // Add an image view and add it to pane
        Image image = new Image(getClass().getResourceAsStream("/image/au.gif"));
        ImageView imageView = new ImageView(image);
        pane.getChildren().add(imageView);
        
        // Create a PathTransition animation that moves the ImageView along a straight line.
        // new PathTransition(Duration, Shape path, Node node)
        // Duration.millis(10000): one full traversal of the path takes 10,000 ms (10 seconds).
        // new Line(100, 200, 100, 0): defines the animation path.
        //   (100, 200) -> (100, 0) means: start near the bottom of the pane (y = 200) and move straight up
        //   to the top (y = 0) while keeping the same x position (x = 100). This creates a vertical "rising" effect.
        // imageView: the node (flag image) that will be animated along the path.
        PathTransition pt = new PathTransition(
            Duration.millis(10000),          // Total time for one upward movement
            new Line(100, 200, 100, 0),       // Vertical path line from bottom to top
            imageView                         // Node to animate (the flag)
        );
        pt.setCycleCount(5);                  // Repeat the rise 5 times (after the 5th cycle it stops)

        // Alternative options (not used here):
        //   pt.setAutoReverse(true);  // Would make it go back down after reaching the top
        //   pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        
        pt.play();                             // Start (asynchronously) the animation timeline
        
        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 250, 200);
        primaryStage.setTitle("FlagRisingAnimation"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }
    
    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
} 