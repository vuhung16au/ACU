package com.acu.javafx.bouncingball;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * BallPane class represents a pane containing a bouncing ball animation.
 * The ball moves within the boundaries of the pane and bounces off the edges.
 * Users can control the animation speed and pause/resume functionality.
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class BallPane extends Pane {
    /** The radius of the ball */
    public final double radius = 20;
    
    /** Current x position of the ball */
    private double x = radius;
    
    /** Current y position of the ball */
    private double y = radius;
    
    /** Horizontal velocity of the ball */
    private double dx = 1;
    
    /** Vertical velocity of the ball */
    private double dy = 1;
    
    /** The circle representing the ball */
    private Circle circle = new Circle(x, y, radius);
    
    /** The animation timeline for the ball movement */
    private Timeline animation;

    /**
     * Constructs a BallPane with a bouncing ball animation.
     * The ball starts in the top-left corner and moves diagonally.
     */
    public BallPane() {
        circle.setFill(Color.GREEN); // Set ball color
        getChildren().add(circle); // Place a ball into this pane

        // Create an animation for moving the ball
        animation = new Timeline(
            new KeyFrame(Duration.millis(50), e -> moveBall()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play(); // Start animation
    }

    /**
     * Starts or resumes the ball animation.
     */
    public void play() {
        animation.play();
    }

    /**
     * Pauses the ball animation.
     */
    public void pause() {
        animation.pause();
    }

    /**
     * Increases the animation speed by 0.1.
     */
    public void increaseSpeed() {
        animation.setRate(animation.getRate() + 0.1);
    }

    /**
     * Decreases the animation speed by 0.1, but not below 0.
     */
    public void decreaseSpeed() {
        animation.setRate(
            animation.getRate() > 0 ? animation.getRate() - 0.1 : 0);
    }

    /**
     * Returns the rate property of the animation for binding.
     * 
     * @return the rate property of the animation
     */
    public DoubleProperty rateProperty() {
        return animation.rateProperty();
    }
    
    /**
     * Moves the ball according to its current velocity and handles boundary collisions.
     * The ball bounces off the edges of the pane by reversing its velocity.
     */
    protected void moveBall() {
        // Check boundaries
        if (x < radius || x > getWidth() - radius) {
            dx *= -1; // Change ball move direction
        }
        if (y < radius || y > getHeight() - radius) {
            dy *= -1; // Change ball move direction
        }

        // Adjust ball position
        x += dx;
        y += dy;
        circle.setCenterX(x);
        circle.setCenterY(y);
    }
} 