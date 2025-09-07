package com.acu.javafx.hilbertcurve;

import java.util.ArrayList;
import java.util.List;

/**
 * HilbertCurveGenerator - Generates points for a Hilbert curve of specified order
 * 
 * The Hilbert curve is a space-filling curve that visits every point in a square grid
 * with a size of 2^n × 2^n, where n is the order of the curve.
 * 
 * This class implements the recursive algorithm to generate the curve points.
 * The algorithm works by recursively dividing the space into four quadrants
 * and connecting them with specific patterns.
 */
public class HilbertCurveGenerator {
    
    /**
     * Represents a point in 2D space
     */
    public static class Point {
        public final double x;
        public final double y;
        
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public String toString() {
            return String.format("(%.2f, %.2f)", x, y);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Point point = (Point) obj;
            return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
        }
        
        @Override
        public int hashCode() {
            return Double.hashCode(x) * 31 + Double.hashCode(y);
        }
    }
    
    /**
     * Generates the Hilbert curve points for the specified order
     * 
     * @param order The order of the Hilbert curve (1-6)
     * @param width The width of the drawing area
     * @param height The height of the drawing area
     * @return List of points representing the Hilbert curve
     * @throws IllegalArgumentException if order is not between 1 and 6
     */
    public static List<Point> generateHilbertCurve(int order, double width, double height) {
        if (order < 1 || order > 6) {
            throw new IllegalArgumentException("Order must be between 1 and 6, got: " + order);
        }
        
        List<Point> points = new ArrayList<>();
        
        // Calculate the grid size (2^order)
        int gridSize = (int) Math.pow(2, order);
        
        // Calculate the step size to fit the curve in the given dimensions
        double stepX = width / gridSize;
        double stepY = height / gridSize;
        
        // Generate the curve using the alternative implementation (more reliable)
        for (int i = 0; i < gridSize * gridSize; i++) {
            int[] coords = hilbertIndexToXY(i, order);
            points.add(new Point(coords[0] * stepX + stepX / 2, coords[1] * stepY + stepY / 2));
        }
        
        return points;
    }
    
    /**
     * Recursive method to generate Hilbert curve points
     * 
     * The algorithm works by:
     * 1. If order is 0, we're at a single point
     * 2. Otherwise, we divide the space into 4 quadrants and recursively
     *    generate curves for each quadrant, connecting them with specific patterns
     * 
     * @param order Current recursion level
     * @param x Current x coordinate
     * @param y Current y coordinate
     * @param dx Current x direction
     * @param dy Current y direction
     * @param direction Current direction (0=right, 1=down, 2=left, 3=up)
     * @param points List to store the generated points
     * @param stepX X step size
     * @param stepY Y step size
     */
    private static void generateHilbertCurveRecursive(int order, int x, int y, 
                                                     int dx, int dy, int direction,
                                                     List<Point> points, 
                                                     double stepX, double stepY) {
        if (order == 0) {
            // Base case: add the current point
            points.add(new Point(x * stepX + stepX / 2, y * stepY + stepY / 2));
            return;
        }
        
        // Recursively generate the four quadrants
        // The pattern depends on the current direction
        switch (direction) {
            case 0: // Moving right
                generateHilbertCurveRecursive(order - 1, x, y, 0, 1, 1, points, stepX, stepY);
                generateHilbertCurveRecursive(order - 1, x, y + (int)Math.pow(2, order - 1), 1, 0, 0, points, stepX, stepY);
                generateHilbertCurveRecursive(order - 1, x + (int)Math.pow(2, order - 1), y + (int)Math.pow(2, order - 1), 1, 0, 0, points, stepX, stepY);
                generateHilbertCurveRecursive(order - 1, x + (int)Math.pow(2, order - 1), y, 0, -1, 3, points, stepX, stepY);
                break;
            case 1: // Moving down
                generateHilbertCurveRecursive(order - 1, x, y, 1, 0, 0, points, stepX, stepY);
                generateHilbertCurveRecursive(order - 1, x + (int)Math.pow(2, order - 1), y, 0, 1, 1, points, stepX, stepY);
                generateHilbertCurveRecursive(order - 1, x + (int)Math.pow(2, order - 1), y + (int)Math.pow(2, order - 1), 0, 1, 1, points, stepX, stepY);
                generateHilbertCurveRecursive(order - 1, x, y + (int)Math.pow(2, order - 1), -1, 0, 2, points, stepX, stepY);
                break;
            case 2: // Moving left
                generateHilbertCurveRecursive(order - 1, x, y, 0, -1, 3, points, stepX, stepY);
                generateHilbertCurveRecursive(order - 1, x, y - (int)Math.pow(2, order - 1), -1, 0, 2, points, stepX, stepY);
                generateHilbertCurveRecursive(order - 1, x - (int)Math.pow(2, order - 1), y - (int)Math.pow(2, order - 1), -1, 0, 2, points, stepX, stepY);
                generateHilbertCurveRecursive(order - 1, x - (int)Math.pow(2, order - 1), y, 0, 1, 1, points, stepX, stepY);
                break;
            case 3: // Moving up
                generateHilbertCurveRecursive(order - 1, x, y, -1, 0, 2, points, stepX, stepY);
                generateHilbertCurveRecursive(order - 1, x - (int)Math.pow(2, order - 1), y, 0, -1, 3, points, stepX, stepY);
                generateHilbertCurveRecursive(order - 1, x - (int)Math.pow(2, order - 1), y - (int)Math.pow(2, order - 1), 0, -1, 3, points, stepX, stepY);
                generateHilbertCurveRecursive(order - 1, x, y - (int)Math.pow(2, order - 1), 1, 0, 0, points, stepX, stepY);
                break;
        }
    }
    
    /**
     * Alternative implementation using a more direct approach
     * This method converts a linear index to 2D coordinates using the Hilbert curve mapping
     * 
     * @param order The order of the Hilbert curve
     * @param width The width of the drawing area
     * @param height The height of the drawing area
     * @return List of points representing the Hilbert curve
     */
    public static List<Point> generateHilbertCurveAlternative(int order, double width, double height) {
        if (order < 1 || order > 6) {
            throw new IllegalArgumentException("Order must be between 1 and 6, got: " + order);
        }
        
        List<Point> points = new ArrayList<>();
        int gridSize = (int) Math.pow(2, order);
        int totalPoints = gridSize * gridSize;
        
        double stepX = width / gridSize;
        double stepY = height / gridSize;
        
        for (int i = 0; i < totalPoints; i++) {
            int[] coords = hilbertIndexToXY(i, order);
            points.add(new Point(coords[0] * stepX + stepX / 2, coords[1] * stepY + stepY / 2));
        }
        
        return points;
    }
    
    /**
     * Converts a linear index to 2D coordinates using Hilbert curve mapping
     * 
     * @param index Linear index (0 to 2^(2*order) - 1)
     * @param order Order of the Hilbert curve
     * @return Array containing [x, y] coordinates
     */
    private static int[] hilbertIndexToXY(int index, int order) {
        int x = 0, y = 0;
        int n = (int) Math.pow(2, order);
        
        for (int s = 1; s < n; s *= 2) {
            int rx = 1 & (index / 2);
            int ry = 1 & (index ^ rx);
            
            if (ry == 0) {
                if (rx == 1) {
                    x = s - 1 - x;
                    y = s - 1 - y;
                }
                // Swap x and y
                int temp = x;
                x = y;
                y = temp;
            }
            
            x += s * rx;
            y += s * ry;
            index /= 4;
        }
        
        return new int[]{x, y};
    }
    
    /**
     * Gets the expected number of points for a given order
     * 
     * @param order The order of the Hilbert curve
     * @return The number of points in the curve
     */
    public static int getPointCount(int order) {
        if (order < 1 || order > 6) {
            throw new IllegalArgumentException("Order must be between 1 and 6, got: " + order);
        }
        return (int) Math.pow(2, 2 * order);
    }
    
    /**
     * Validates that the generated curve has the expected properties
     * 
     * @param points The generated points
     * @param order The order of the curve
     * @return true if the curve is valid, false otherwise
     */
    public static boolean validateHilbertCurve(List<Point> points, int order) {
        if (points == null) {
            return false;
        }
        
        int expectedCount = getPointCount(order);
        if (points.size() != expectedCount) {
            return false;
        }
        
        // Check that all points are unique
        return points.size() == points.stream().distinct().count();
    }
}
