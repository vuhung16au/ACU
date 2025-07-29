package com.acu.javafx.controlcirclewithmouseandkey;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * CirclePane class that manages a circle with enlarge and shrink functionality.
 * The circle is centered in the pane and can be resized using the enlarge() and shrink() methods.
 */
public class CirclePane extends Pane {
    private Circle circle;
    private static final double MIN_RADIUS = 5;
    private static final double MAX_RADIUS = 50;
    private static final double RADIUS_CHANGE = 2;

    /**
     * Constructor that creates a circle pane with a default circle.
     */
    public CirclePane() {
        // Create a circle with initial properties
        circle = new Circle();
        circle.setRadius(20);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
        
        // Add the circle to the pane
        getChildren().add(circle);
        
        // Center the circle in the pane
        centerCircle();
    }

    /**
     * Enlarges the circle by RADIUS_CHANGE units, up to MAX_RADIUS.
     */
    public void enlarge() {
        if (circle.getRadius() < MAX_RADIUS) {
            circle.setRadius(circle.getRadius() + RADIUS_CHANGE);
            centerCircle();
        }
    }

    /**
     * Shrinks the circle by RADIUS_CHANGE units, down to MIN_RADIUS.
     */
    public void shrink() {
        if (circle.getRadius() > MIN_RADIUS) {
            circle.setRadius(circle.getRadius() - RADIUS_CHANGE);
            centerCircle();
        }
    }

    /**
     * Centers the circle in the pane.
     */
    private void centerCircle() {
        circle.setCenterX(getWidth() / 2);
        circle.setCenterY(getHeight() / 2);
    }

    /**
     * Override the setWidth method to center the circle when the pane is resized.
     */
    @Override
    public void setWidth(double width) {
        super.setWidth(width);
        centerCircle();
    }

    /**
     * Override the setHeight method to center the circle when the pane is resized.
     */
    @Override
    public void setHeight(double height) {
        super.setHeight(height);
        centerCircle();
    }

    /**
     * Gets the current radius of the circle.
     * @return the current radius
     */
    public double getRadius() {
        return circle.getRadius();
    }

    /**
     * Sets the radius of the circle.
     * @param radius the new radius (will be clamped between MIN_RADIUS and MAX_RADIUS)
     */
    public void setRadius(double radius) {
        radius = Math.max(MIN_RADIUS, Math.min(MAX_RADIUS, radius));
        circle.setRadius(radius);
        centerCircle();
    }
} 