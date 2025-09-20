package com.acu.javafx.convexhull;

/**
 * Represents a point in 2D space with x and y coordinates.
 * This class is used for the convex hull algorithm.
 */
public class Point {
    private double x;
    private double y;

    /**
     * Creates a new point with the specified coordinates.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Point(double x, double y) {
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
     * Calculates the distance between this point and another point.
     * 
     * @param other the other point
     * @return the distance between the two points
     */
    public double distanceTo(Point other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Calculates the cross product of three points.
     * Used to determine the orientation of three points.
     * 
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     * @return positive if counter-clockwise, negative if clockwise, 0 if collinear
     */
    public static double crossProduct(Point p1, Point p2, Point p3) {
        return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
    }

    /**
     * Returns a string representation of this point.
     * 
     * @return a string in the format "(x, y)"
     */
    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }

    /**
     * Checks if this point is equal to another point.
     * Two points are equal if they have the same coordinates.
     * 
     * @param obj the object to compare with
     * @return true if the points are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    /**
     * Returns a hash code for this point.
     * 
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Double.hashCode(x) * 31 + Double.hashCode(y);
    }
}
