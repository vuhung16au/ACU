package com.acu.javafx.convexhull;

import java.util.*;

/**
 * Implements the Graham Scan algorithm to find the convex hull of a set of points.
 * The convex hull is the smallest convex polygon that contains all the points.
 */
public class ConvexHullAlgorithm {

    /**
     * Finds the convex hull of a set of points using the Graham Scan algorithm.
     * 
     * @param points the list of points
     * @return a list of points that form the convex hull in counter-clockwise order
     * @throws IllegalArgumentException if points is null or has less than 3 points
     */
    public static List<Point> findConvexHull(List<Point> points) {
        if (points == null) {
            throw new IllegalArgumentException("Points list cannot be null");
        }
        
        if (points.size() < 3) {
            throw new IllegalArgumentException("At least 3 points are required to form a convex hull");
        }

        // Remove duplicate points
        List<Point> uniquePoints = removeDuplicates(points);
        
        if (uniquePoints.size() < 3) {
            throw new IllegalArgumentException("At least 3 unique points are required to form a convex hull");
        }

        // Find the bottom-most point (and leftmost in case of tie)
        Point bottomPoint = findBottomPoint(uniquePoints);
        
        // Sort points by polar angle with respect to the bottom point
        List<Point> sortedPoints = sortByPolarAngle(uniquePoints, bottomPoint);
        
        // Apply Graham Scan algorithm
        return grahamScan(sortedPoints);
    }

    /**
     * Removes duplicate points from the list.
     * 
     * @param points the list of points
     * @return a new list with duplicate points removed
     */
    private static List<Point> removeDuplicates(List<Point> points) {
        Set<Point> uniquePoints = new LinkedHashSet<>(points);
        return new ArrayList<>(uniquePoints);
    }

    /**
     * Finds the bottom-most point (and leftmost in case of tie).
     * 
     * @param points the list of points
     * @return the bottom-most point
     */
    private static Point findBottomPoint(List<Point> points) {
        Point bottom = points.get(0);
        
        for (Point point : points) {
            if (point.getY() < bottom.getY() || 
                (point.getY() == bottom.getY() && point.getX() < bottom.getX())) {
                bottom = point;
            }
        }
        
        return bottom;
    }

    /**
     * Sorts points by polar angle with respect to the reference point.
     * Points with the same polar angle are sorted by distance from the reference point.
     * 
     * @param points the list of points to sort
     * @param reference the reference point
     * @return a new list of points sorted by polar angle
     */
    private static List<Point> sortByPolarAngle(List<Point> points, Point reference) {
        List<Point> sortedPoints = new ArrayList<>(points);
        
        // Remove the reference point from the list to sort
        sortedPoints.remove(reference);
        
        // Sort by polar angle
        sortedPoints.sort((p1, p2) -> {
            double angle1 = polarAngle(reference, p1);
            double angle2 = polarAngle(reference, p2);
            
            // Compare angles
            int angleComparison = Double.compare(angle1, angle2);
            if (angleComparison != 0) {
                return angleComparison;
            }
            
            // If angles are equal, sort by distance from reference point
            double dist1 = reference.distanceTo(p1);
            double dist2 = reference.distanceTo(p2);
            return Double.compare(dist1, dist2);
        });
        
        // Add reference point at the beginning
        sortedPoints.add(0, reference);
        
        return sortedPoints;
    }

    /**
     * Calculates the polar angle of a point with respect to a reference point.
     * 
     * @param reference the reference point
     * @param point the point to calculate angle for
     * @return the polar angle in radians
     */
    private static double polarAngle(Point reference, Point point) {
        return Math.atan2(point.getY() - reference.getY(), point.getX() - reference.getX());
    }

    /**
     * Applies the Graham Scan algorithm to find the convex hull.
     * 
     * @param sortedPoints points sorted by polar angle
     * @return the convex hull as a list of points
     */
    private static List<Point> grahamScan(List<Point> sortedPoints) {
        Stack<Point> hull = new Stack<>();
        
        // Add first two points to the stack
        hull.push(sortedPoints.get(0));
        hull.push(sortedPoints.get(1));
        
        // Process remaining points
        for (int i = 2; i < sortedPoints.size(); i++) {
            Point current = sortedPoints.get(i);
            
            // Remove points that make a right turn
            while (hull.size() > 1 && 
                   Point.crossProduct(hull.get(hull.size() - 2), hull.peek(), current) <= 0) {
                hull.pop();
            }
            
            hull.push(current);
        }
        
        // Convert stack to list
        return new ArrayList<>(hull);
    }

    /**
     * Checks if a point is inside the convex hull.
     * 
     * @param point the point to check
     * @param hull the convex hull points
     * @return true if the point is inside or on the boundary of the hull
     */
    public static boolean isPointInsideHull(Point point, List<Point> hull) {
        if (hull.size() < 3) {
            return false;
        }
        
        // Check if point is on the same side of all edges
        boolean allNegative = true;
        boolean allPositive = true;
        
        for (int i = 0; i < hull.size(); i++) {
            Point p1 = hull.get(i);
            Point p2 = hull.get((i + 1) % hull.size());
            
            // Calculate cross product to determine which side of the edge the point is on
            double crossProduct = Point.crossProduct(p1, p2, point);
            
            if (crossProduct > 0) {
                allNegative = false;
            } else if (crossProduct < 0) {
                allPositive = false;
            }
        }
        
        // Point is inside if all cross products have the same sign (or are zero)
        return allNegative || allPositive;
    }

    /**
     * Calculates the area of the convex hull.
     * 
     * @param hull the convex hull points
     * @return the area of the convex hull
     */
    public static double calculateHullArea(List<Point> hull) {
        if (hull.size() < 3) {
            return 0.0;
        }
        
        double area = 0.0;
        int n = hull.size();
        
        // Use the shoelace formula
        for (int i = 0; i < n; i++) {
            Point current = hull.get(i);
            Point next = hull.get((i + 1) % n);
            area += current.getX() * next.getY() - next.getX() * current.getY();
        }
        
        return Math.abs(area) / 2.0;
    }

    /**
     * Calculates the perimeter of the convex hull.
     * 
     * @param hull the convex hull points
     * @return the perimeter of the convex hull
     */
    public static double calculateHullPerimeter(List<Point> hull) {
        if (hull.size() < 2) {
            return 0.0;
        }
        
        double perimeter = 0.0;
        int n = hull.size();
        
        for (int i = 0; i < n; i++) {
            Point current = hull.get(i);
            Point next = hull.get((i + 1) % n);
            perimeter += current.distanceTo(next);
        }
        
        return perimeter;
    }
}
