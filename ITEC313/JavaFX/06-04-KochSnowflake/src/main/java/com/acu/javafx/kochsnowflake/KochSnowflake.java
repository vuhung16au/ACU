package com.acu.javafx.kochsnowflake;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Koch Snowflake Fractal Generator
 * 
 * This class implements the Koch snowflake fractal algorithm.
 * The Koch snowflake is created by recursively dividing line segments
 * and adding equilateral triangles to create a fractal pattern.
 * 
 * Algorithm:
 * 1. Start with an equilateral triangle (order 0)
 * 2. For each line segment, divide it into three equal parts
 * 3. Replace the middle third with two sides of an equilateral triangle
 * 4. Repeat for higher orders
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class KochSnowflake {
    
    private static final Color DEFAULT_COLOR = Color.BLUE;
    private static final double DEFAULT_SIZE = 500.0;
    
    private int order;
    private double size;
    private Color color;
    private List<LineSegment> segments;
    
    /**
     * Represents a line segment with start and end points
     */
    public static class LineSegment {
        public final double x1, y1, x2, y2;
        
        public LineSegment(double x1, double y1, double x2, double y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
        
        @Override
        public String toString() {
            return String.format("LineSegment(%.2f,%.2f -> %.2f,%.2f)", x1, y1, x2, y2);
        }
    }
    
    /**
     * Constructs a Koch snowflake with default parameters
     */
    public KochSnowflake() {
        this(0, DEFAULT_SIZE, DEFAULT_COLOR);
    }
    
    /**
     * Constructs a Koch snowflake with specified order
     * 
     * @param order the fractal order (0 = triangle, 1+ = fractal iterations)
     */
    public KochSnowflake(int order) {
        this(order, DEFAULT_SIZE, DEFAULT_COLOR);
    }
    
    /**
     * Constructs a Koch snowflake with specified parameters
     * 
     * @param order the fractal order
     * @param size the size of the fractal
     * @param color the color of the fractal
     */
    public KochSnowflake(int order, double size, Color color) {
        this.order = Math.max(0, order);
        this.size = size;
        this.color = color;
        this.segments = new ArrayList<>();
        generateFractal();
    }
    
    /**
     * Generates the Koch snowflake fractal based on the current order
     */
    private void generateFractal() {
        segments.clear();
        
        if (order == 0) {
            // Order 0: Simple equilateral triangle
            generateTriangle();
        } else {
            // Higher orders: Apply fractal transformation
            generateFractalRecursive();
        }
    }
    
    /**
     * Generates the base equilateral triangle (order 0)
     */
    private void generateTriangle() {
        // Calculate triangle vertices
        // Center the triangle in the canvas
        double centerX = size / 2.0;
        double centerY = size / 2.0;
        
        // Calculate triangle height and base
        double triangleHeight = size * 0.8; // Use 80% of canvas size
        double triangleBase = triangleHeight * 2.0 / Math.sqrt(3.0);
        
        // Calculate vertices
        double topX = centerX;
        double topY = centerY - triangleHeight / 2.0;
        
        double leftX = centerX - triangleBase / 2.0;
        double leftY = centerY + triangleHeight / 2.0;
        
        double rightX = centerX + triangleBase / 2.0;
        double rightY = centerY + triangleHeight / 2.0;
        
        // Create the three sides of the triangle
        segments.add(new LineSegment(topX, topY, leftX, leftY));
        segments.add(new LineSegment(leftX, leftY, rightX, rightY));
        segments.add(new LineSegment(rightX, rightY, topX, topY));
    }
    
    /**
     * Generates the fractal recursively by applying the Koch transformation
     */
    private void generateFractalRecursive() {
        // Start with the base triangle
        generateTriangle();
        
        // Apply the Koch transformation for each order
        for (int i = 0; i < order; i++) {
            List<LineSegment> newSegments = new ArrayList<>();
            
            for (LineSegment segment : segments) {
                // Apply Koch transformation to each segment
                List<LineSegment> transformed = transformSegment(segment);
                newSegments.addAll(transformed);
            }
            
            segments = newSegments;
        }
    }
    
    /**
     * Applies the Koch transformation to a single line segment
     * 
     * @param segment the original line segment
     * @return list of new segments after transformation
     */
    private List<LineSegment> transformSegment(LineSegment segment) {
        List<LineSegment> result = new ArrayList<>();
        
        double x1 = segment.x1;
        double y1 = segment.y1;
        double x2 = segment.x2;
        double y2 = segment.y2;
        
        // Calculate the three division points
        double dx = x2 - x1;
        double dy = y2 - y1;
        
        // First third point
        double x3 = x1 + dx / 3.0;
        double y3 = y1 + dy / 3.0;
        
        // Second third point
        double x4 = x1 + 2.0 * dx / 3.0;
        double y4 = y1 + 2.0 * dy / 3.0;
        
        // Calculate the peak of the equilateral triangle
        // The peak is perpendicular to the line segment at distance sqrt(3)/6 * length
        double length = Math.sqrt(dx * dx + dy * dy);
        double height = length * Math.sqrt(3.0) / 6.0;
        
        // Calculate perpendicular vector (rotated 90 degrees)
        double perpX = -dy / length;
        double perpY = dx / length;
        
        // Peak point
        double peakX = (x3 + x4) / 2.0 + perpX * height;
        double peakY = (y3 + y4) / 2.0 + perpY * height;
        
        // Create the four new segments
        result.add(new LineSegment(x1, y1, x3, y3));           // First segment
        result.add(new LineSegment(x3, y3, peakX, peakY));     // Left side of triangle
        result.add(new LineSegment(peakX, peakY, x4, y4));     // Right side of triangle
        result.add(new LineSegment(x4, y4, x2, y2));           // Last segment
        
        return result;
    }
    
    /**
     * Draws the Koch snowflake on the given canvas
     * 
     * @param canvas the canvas to draw on
     */
    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Clear the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        // Set drawing properties
        gc.setStroke(color);
        gc.setLineWidth(1.0);
        
        // Draw all segments
        for (LineSegment segment : segments) {
            gc.strokeLine(segment.x1, segment.y1, segment.x2, segment.y2);
        }
    }
    
    /**
     * Gets the current fractal order
     * 
     * @return the order
     */
    public int getOrder() {
        return order;
    }
    
    /**
     * Sets the fractal order and regenerates the fractal
     * 
     * @param order the new order
     */
    public void setOrder(int order) {
        this.order = Math.max(0, order);
        generateFractal();
    }
    
    /**
     * Gets the fractal size
     * 
     * @return the size
     */
    public double getSize() {
        return size;
    }
    
    /**
     * Sets the fractal size and regenerates the fractal
     * 
     * @param size the new size
     */
    public void setSize(double size) {
        this.size = size;
        generateFractal();
    }
    
    /**
     * Gets the fractal color
     * 
     * @return the color
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Sets the fractal color
     * 
     * @param color the new color
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    /**
     * Gets the number of line segments in the fractal
     * 
     * @return the number of segments
     */
    public int getSegmentCount() {
        return segments.size();
    }
    
    /**
     * Gets a copy of all line segments
     * 
     * @return list of line segments
     */
    public List<LineSegment> getSegments() {
        return new ArrayList<>(segments);
    }
    
    /**
     * Calculates the theoretical number of segments for a given order
     * 
     * @param order the fractal order
     * @return the number of segments
     */
    public static int calculateSegmentCount(int order) {
        if (order < 0) return 0;
        if (order == 0) return 3;
        return 3 * (int) Math.pow(4, order);
    }
    
    /**
     * Calculates the theoretical perimeter for a given order and size
     * 
     * @param order the fractal order
     * @param size the fractal size
     * @return the perimeter
     */
    public static double calculatePerimeter(int order, double size) {
        if (order < 0) return 0;
        double baseLength = size * 0.8 * 2.0 / Math.sqrt(3.0);
        return 3 * baseLength * Math.pow(4.0 / 3.0, order);
    }
    
    @Override
    public String toString() {
        return String.format("KochSnowflake{order=%d, size=%.2f, segments=%d}", 
                           order, size, segments.size());
    }
}
