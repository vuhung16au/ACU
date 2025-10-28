package com.acu.connectedcircles;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Model for a circle on the canvas.
 */
public class CircleItem {
    private double x;
    private double y;
    private int radius;
    private Color color;
    private transient Circle view; // JavaFX node (not essential for logic)
    private transient Point2D dragOffset;

    public CircleItem(double x, double y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getRadius() { return radius; }
    public Color getColor() { return color; }
    public Circle getView() { return view; }
    public Point2D getDragOffset() { return dragOffset; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setRadius(int radius) { this.radius = radius; }
    public void setColor(Color color) { this.color = color; }
    public void setView(Circle view) { this.view = view; }
    public void setDragOffset(Point2D dragOffset) { this.dragOffset = dragOffset; }

    /** Returns true if this circle overlaps or touches the other. */
    public boolean overlaps(CircleItem other) {
        double dx = x - other.x;
        double dy = y - other.y;
        double distance = Math.hypot(dx, dy);
        return distance <= (radius + other.radius);
    }
}


