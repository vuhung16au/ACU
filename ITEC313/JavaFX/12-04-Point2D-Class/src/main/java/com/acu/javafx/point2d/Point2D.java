package com.acu.javafx.point2d;

/**
 * Point2D class represents a point in 2D space with x and y coordinates.
 * This class demonstrates object-oriented programming concepts including
 * constructors, getters, equals method, and hashCode method.
 * 
 * @author ACU JavaFX Course
 * @version 1.0
 */
public class Point2D {
    
    // Data fields for representing a point
    private double x;  // x-coordinate of the point
    private double y;  // y-coordinate of the point
    
    /**
     * No-argument constructor that constructs a point at origin (0, 0).
     * This is the default constructor that initializes the point to the origin.
     */
    public Point2D() {
        this.x = 0.0;
        this.y = 0.0;
    }
    
    /**
     * Constructor that constructs a point with specified x and y values.
     * 
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Gets the x-coordinate of this point.
     * 
     * @return the x-coordinate
     */
    public double getX() {
        return x;
    }
    
    /**
     * Gets the y-coordinate of this point.
     * 
     * @return the y-coordinate
     */
    public double getY() {
        return y;
    }
    
    /**
     * Sets the x-coordinate of this point.
     * 
     * @param x the new x-coordinate
     */
    public void setX(double x) {
        this.x = x;
    }
    
    /**
     * Sets the y-coordinate of this point.
     * 
     * @param y the new y-coordinate
     */
    public void setY(double y) {
        this.y = y;
    }
    
    /**
     * Override the equals method to compare two Point2D objects.
     * Two points are considered equal if their x and y coordinates are equal.
     * 
     * @param obj the object to compare with this point
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        // Check if the object is the same reference
        if (this == obj) {
            return true;
        }
        
        // Check if the object is null or not an instance of Point2D
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        // Cast the object to Point2D and compare coordinates
        Point2D other = (Point2D) obj;
        
        // Use Double.compare for precise double comparison to handle floating-point precision
        return Double.compare(this.x, other.x) == 0 && Double.compare(this.y, other.y) == 0;
    }
    
    /**
     * Override the hashCode method to provide a hash code for this point.
     * This implementation follows the same pattern as Java's Point2D class,
     * using bit manipulation for efficient hash code generation.
     * 
     * @return a hash code value for this point
     */
    @Override
    public int hashCode() {
        // Use Double.hashCode for each coordinate and combine them
        // This approach is consistent with Java's Point2D implementation
        long bits = Double.doubleToLongBits(x);
        int hash = (int) (bits ^ (bits >>> 32));
        
        bits = Double.doubleToLongBits(y);
        hash = hash * 31 + (int) (bits ^ (bits >>> 32));
        
        return hash;
    }
    
    /**
     * Returns a string representation of this point.
     * 
     * @return a string representation in the format "Point2D(x, y)"
     */
    @Override
    public String toString() {
        return String.format("Point2D(%.2f, %.2f)", x, y);
    }
    
    /**
     * Calculates the distance from this point to another point.
     * 
     * @param other the other point
     * @return the distance between the two points
     */
    public double distance(Point2D other) {
        if (other == null) {
            throw new IllegalArgumentException("Other point cannot be null");
        }
        
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    /**
     * Calculates the distance from this point to the origin (0, 0).
     * 
     * @return the distance from this point to the origin
     */
    public double distanceFromOrigin() {
        return Math.sqrt(x * x + y * y);
    }
}
