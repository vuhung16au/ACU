package com.acu.javafx.hilbertcurve;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import java.util.List;

/**
 * HilbertCurvePane - A JavaFX pane that displays Hilbert curves
 * 
 * This class extends Pane and uses a Canvas to draw Hilbert curves.
 * It provides methods to set the order and redraw the curve.
 */
public class HilbertCurvePane extends Pane {
    
    private Canvas canvas;
    private GraphicsContext gc;
    private int currentOrder = 1;
    private List<HilbertCurveGenerator.Point> currentPoints;
    
    // Drawing properties
    private static final Color CURVE_COLOR = Color.BLACK;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final double LINE_WIDTH = 2.0;
    private static final double MARGIN = 20.0;
    
    /**
     * Constructor - Creates a new HilbertCurvePane
     */
    public HilbertCurvePane() {
        initializeCanvas();
        setupCanvas();
    }
    
    /**
     * Initialize the canvas and graphics context
     */
    private void initializeCanvas() {
        canvas = new Canvas(550, 500);
        gc = canvas.getGraphicsContext2D();
        
        // Set fixed canvas size
        canvas.setWidth(550);
        canvas.setHeight(500);
        
        getChildren().add(canvas);
    }
    
    /**
     * Setup canvas properties
     */
    private void setupCanvas() {
        gc.setLineWidth(LINE_WIDTH);
        gc.setStroke(CURVE_COLOR);
        gc.setFill(BACKGROUND_COLOR);
    }
    
    /**
     * Set the order of the Hilbert curve and redraw
     * 
     * @param order The order of the Hilbert curve (1-6)
     * @throws IllegalArgumentException if order is not between 1 and 6
     */
    public void setOrder(int order) {
        if (order < 1 || order > 6) {
            throw new IllegalArgumentException("Order must be between 1 and 6, got: " + order);
        }
        this.currentOrder = order;
        redraw();
    }
    
    /**
     * Get the current order of the Hilbert curve
     * 
     * @return The current order
     */
    public int getCurrentOrder() {
        return currentOrder;
    }
    
    /**
     * Redraw the Hilbert curve with the current order
     */
    public void redraw() {
        if (canvas.getWidth() <= 0 || canvas.getHeight() <= 0) {
            return; // Canvas not ready yet
        }
        
        // Clear the canvas
        gc.setFill(BACKGROUND_COLOR);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        // Calculate drawing area (with margins)
        double drawWidth = canvas.getWidth() - 2 * MARGIN;
        double drawHeight = canvas.getHeight() - 2 * MARGIN;
        
        if (drawWidth <= 0 || drawHeight <= 0) {
            return;
        }
        
        try {
            // Generate the Hilbert curve points
            currentPoints = HilbertCurveGenerator.generateHilbertCurve(
                currentOrder, drawWidth, drawHeight);
            
            // Draw the curve
            drawHilbertCurve(currentPoints, MARGIN, MARGIN);
            
        } catch (Exception e) {
            // Draw error message
            gc.setFill(Color.RED);
            gc.fillText("Error drawing curve: " + e.getMessage(), 10, 20);
        }
    }
    
    /**
     * Draw the Hilbert curve using the generated points
     * 
     * @param points List of points representing the curve
     * @param offsetX X offset for drawing
     * @param offsetY Y offset for drawing
     */
    private void drawHilbertCurve(List<HilbertCurveGenerator.Point> points, 
                                 double offsetX, double offsetY) {
        if (points == null || points.isEmpty()) {
            return;
        }
        
        gc.setStroke(CURVE_COLOR);
        gc.setLineWidth(LINE_WIDTH);
        
        // Draw the curve by connecting consecutive points
        for (int i = 0; i < points.size() - 1; i++) {
            HilbertCurveGenerator.Point current = points.get(i);
            HilbertCurveGenerator.Point next = points.get(i + 1);
            
            gc.strokeLine(
                current.x + offsetX, 
                current.y + offsetY,
                next.x + offsetX, 
                next.y + offsetY
            );
        }
        
        // Draw start and end points with different colors
        if (points.size() > 0) {
            // Start point (green)
            gc.setFill(Color.GREEN);
            HilbertCurveGenerator.Point start = points.get(0);
            gc.fillOval(start.x + offsetX - 3, start.y + offsetY - 3, 6, 6);
            
            // End point (red)
            gc.setFill(Color.RED);
            HilbertCurveGenerator.Point end = points.get(points.size() - 1);
            gc.fillOval(end.x + offsetX - 3, end.y + offsetY - 3, 6, 6);
        }
    }
    
    /**
     * Clear the canvas
     */
    public void clear() {
        gc.setFill(BACKGROUND_COLOR);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        currentPoints = null;
    }
    
    /**
     * Get the current points of the Hilbert curve
     * 
     * @return List of points, or null if no curve is drawn
     */
    public List<HilbertCurveGenerator.Point> getCurrentPoints() {
        return currentPoints;
    }
    
    /**
     * Get the number of points in the current curve
     * 
     * @return Number of points, or 0 if no curve is drawn
     */
    public int getPointCount() {
        return currentPoints != null ? currentPoints.size() : 0;
    }
    
    /**
     * Set custom colors for the curve
     * 
     * @param curveColor Color for the curve lines
     * @param backgroundColor Color for the background
     */
    public void setColors(Color curveColor, Color backgroundColor) {
        gc.setStroke(curveColor);
        gc.setFill(backgroundColor);
        redraw();
    }
    
    /**
     * Set the line width for drawing the curve
     * 
     * @param width The line width
     */
    public void setLineWidth(double width) {
        gc.setLineWidth(width);
        redraw();
    }
}
