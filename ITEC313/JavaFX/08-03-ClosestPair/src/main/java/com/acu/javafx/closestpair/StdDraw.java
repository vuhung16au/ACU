package com.acu.javafx.closestpair;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Simplified StdDraw implementation for JavaFX Canvas.
 * This provides basic drawing functionality compatible with the original StdDraw API.
 */
public class StdDraw {
    private static Canvas canvas;
    private static GraphicsContext gc;
    private static double xmin = 0.0, ymin = 0.0, xmax = 1.0, ymax = 1.0;
    private static Color penColor = Color.BLACK;
    private static double penRadius = 0.002;

    /**
     * Set the canvas for drawing.
     */
    public static void setCanvas(Canvas canvas) {
        StdDraw.canvas = canvas;
        StdDraw.gc = canvas.getGraphicsContext2D();
        clear();
    }

    /**
     * Set the drawing scale.
     */
    public static void setXscale(double min, double max) {
        xmin = min;
        xmax = max;
    }

    public static void setYscale(double min, double max) {
        ymin = min;
        ymax = max;
    }

    public static void setScale(double min, double max) {
        setXscale(min, max);
        setYscale(min, max);
    }

    /**
     * Set the pen color.
     */
    public static void setPenColor(Color color) {
        penColor = color;
    }

    /**
     * Set the pen radius.
     */
    public static void setPenRadius(double radius) {
        penRadius = radius;
    }

    /**
     * Clear the canvas.
     */
    public static void clear() {
        if (gc != null) {
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }

    /**
     * Draw a point.
     */
    public static void point(double x, double y) {
        if (gc == null) return;
        
        double screenX = scaleX(x);
        double screenY = scaleY(y);
        
        gc.setFill(penColor);
        gc.fillOval(screenX - penRadius * canvas.getWidth() / 2, 
                    screenY - penRadius * canvas.getHeight() / 2,
                    penRadius * canvas.getWidth(), 
                    penRadius * canvas.getHeight());
    }

    /**
     * Draw a line.
     */
    public static void line(double x0, double y0, double x1, double y1) {
        if (gc == null) return;
        
        double screenX0 = scaleX(x0);
        double screenY0 = scaleY(y0);
        double screenX1 = scaleX(x1);
        double screenY1 = scaleY(y1);
        
        gc.setStroke(penColor);
        gc.setLineWidth(penRadius * canvas.getWidth());
        gc.strokeLine(screenX0, screenY0, screenX1, screenY1);
    }

    /**
     * Draw a circle.
     */
    public static void circle(double x, double y, double radius) {
        if (gc == null) return;
        
        double screenX = scaleX(x);
        double screenY = scaleY(y);
        double screenRadius = radius * canvas.getWidth() / (xmax - xmin);
        
        gc.setStroke(penColor);
        gc.setLineWidth(penRadius * canvas.getWidth());
        gc.strokeOval(screenX - screenRadius, screenY - screenRadius, 
                     2 * screenRadius, 2 * screenRadius);
    }

    /**
     * Draw a filled circle.
     */
    public static void filledCircle(double x, double y, double radius) {
        if (gc == null) return;
        
        double screenX = scaleX(x);
        double screenY = scaleY(y);
        double screenRadius = radius * canvas.getWidth() / (xmax - xmin);
        
        gc.setFill(penColor);
        gc.fillOval(screenX - screenRadius, screenY - screenRadius, 
                    2 * screenRadius, 2 * screenRadius);
    }

    /**
     * Scale x coordinate from world to screen coordinates.
     */
    private static double scaleX(double x) {
        return (x - xmin) / (xmax - xmin) * canvas.getWidth();
    }

    /**
     * Scale y coordinate from world to screen coordinates.
     */
    private static double scaleY(double y) {
        return (1.0 - (y - ymin) / (ymax - ymin)) * canvas.getHeight();
    }

    /**
     * Set canvas size (for compatibility).
     */
    public static void setCanvasSize(int width, int height) {
        // This method is for compatibility but doesn't actually resize in this implementation
        // Canvas size is managed by the JavaFX application
    }

    /**
     * Enable double buffering (for compatibility).
     */
    public static void enableDoubleBuffering() {
        // JavaFX handles double buffering automatically
    }

    /**
     * Show the canvas (for compatibility).
     */
    public static void show() {
        // JavaFX handles rendering automatically
    }

    /**
     * Pause for the specified number of milliseconds.
     */
    public static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Set pen radius with no arguments (resets to default).
     */
    public static void setPenRadius() {
        penRadius = 0.002;
    }

    // Color constants for compatibility
    public static final Color RED = Color.RED;
    public static final Color BLUE = Color.BLUE;
} 