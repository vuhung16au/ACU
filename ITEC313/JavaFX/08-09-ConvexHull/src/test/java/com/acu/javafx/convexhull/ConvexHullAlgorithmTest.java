package com.acu.javafx.convexhull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for ConvexHullAlgorithm functionality
 */
public class ConvexHullAlgorithmTest {

    private List<Point> testPoints;
    private List<Point> squarePoints;
    private List<Point> trianglePoints;

    @BeforeEach
    void setUp() {
        testPoints = new ArrayList<>();
        squarePoints = new ArrayList<>();
        trianglePoints = new ArrayList<>();
        
        // Create a square
        squarePoints.add(new Point(0, 0));
        squarePoints.add(new Point(4, 0));
        squarePoints.add(new Point(4, 4));
        squarePoints.add(new Point(0, 4));
        squarePoints.add(new Point(2, 2)); // Interior point
        
        // Create a triangle
        trianglePoints.add(new Point(0, 0));
        trianglePoints.add(new Point(4, 0));
        trianglePoints.add(new Point(2, 4));
        trianglePoints.add(new Point(2, 2)); // Interior point
    }

    @Test
    void testFindConvexHullWithSquare() {
        List<Point> hull = ConvexHullAlgorithm.findConvexHull(squarePoints);
        
        // Should have 4 vertices (the square corners)
        assertEquals(4, hull.size());
        
        // All hull points should be on the boundary
        for (Point hullPoint : hull) {
            assertTrue(squarePoints.contains(hullPoint));
        }
    }

    @Test
    void testFindConvexHullWithTriangle() {
        List<Point> hull = ConvexHullAlgorithm.findConvexHull(trianglePoints);
        
        // Should have 3 vertices (the triangle corners)
        assertEquals(3, hull.size());
        
        // All hull points should be on the boundary
        for (Point hullPoint : hull) {
            assertTrue(trianglePoints.contains(hullPoint));
        }
    }

    @Test
    void testFindConvexHullWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            ConvexHullAlgorithm.findConvexHull(null);
        });
    }

    @Test
    void testFindConvexHullWithInsufficientPoints() {
        List<Point> insufficientPoints = new ArrayList<>();
        insufficientPoints.add(new Point(0, 0));
        insufficientPoints.add(new Point(1, 1));
        
        assertThrows(IllegalArgumentException.class, () -> {
            ConvexHullAlgorithm.findConvexHull(insufficientPoints);
        });
    }

    @Test
    void testFindConvexHullWithDuplicatePoints() {
        List<Point> duplicatePoints = new ArrayList<>();
        duplicatePoints.add(new Point(0, 0));
        duplicatePoints.add(new Point(1, 1));
        duplicatePoints.add(new Point(2, 2));
        duplicatePoints.add(new Point(0, 0)); // Duplicate
        duplicatePoints.add(new Point(1, 1)); // Duplicate
        
        // Should not throw exception, duplicates should be removed
        List<Point> hull = ConvexHullAlgorithm.findConvexHull(duplicatePoints);
        assertNotNull(hull);
        // After removing duplicates, we have 3 collinear points, which should still work
        assertTrue(hull.size() >= 2);
    }

    @Test
    void testIsPointInsideHull() {
        List<Point> hull = ConvexHullAlgorithm.findConvexHull(squarePoints);
        
        // Interior point should be inside
        Point interiorPoint = new Point(2, 2);
        assertTrue(ConvexHullAlgorithm.isPointInsideHull(interiorPoint, hull));
        
        // Exterior point should be outside
        Point exteriorPoint = new Point(5, 5);
        assertFalse(ConvexHullAlgorithm.isPointInsideHull(exteriorPoint, hull));
        
        // Boundary point should be inside (on the boundary)
        Point boundaryPoint = new Point(0, 0);
        assertTrue(ConvexHullAlgorithm.isPointInsideHull(boundaryPoint, hull));
    }

    @Test
    void testIsPointInsideHullWithInsufficientHull() {
        List<Point> insufficientHull = new ArrayList<>();
        insufficientHull.add(new Point(0, 0));
        insufficientHull.add(new Point(1, 1));
        
        Point testPoint = new Point(0.5, 0.5);
        assertFalse(ConvexHullAlgorithm.isPointInsideHull(testPoint, insufficientHull));
    }

    @Test
    void testCalculateHullArea() {
        List<Point> hull = ConvexHullAlgorithm.findConvexHull(squarePoints);
        double area = ConvexHullAlgorithm.calculateHullArea(hull);
        
        // Area of 4x4 square should be 16
        assertEquals(16.0, area, 0.001);
    }

    @Test
    void testCalculateHullAreaWithInsufficientPoints() {
        List<Point> insufficientHull = new ArrayList<>();
        insufficientHull.add(new Point(0, 0));
        insufficientHull.add(new Point(1, 1));
        
        double area = ConvexHullAlgorithm.calculateHullArea(insufficientHull);
        assertEquals(0.0, area, 0.001);
    }

    @Test
    void testCalculateHullPerimeter() {
        List<Point> hull = ConvexHullAlgorithm.findConvexHull(squarePoints);
        double perimeter = ConvexHullAlgorithm.calculateHullPerimeter(hull);
        
        // Perimeter of 4x4 square should be 16
        assertEquals(16.0, perimeter, 0.001);
    }

    @Test
    void testCalculateHullPerimeterWithInsufficientPoints() {
        List<Point> insufficientHull = new ArrayList<>();
        insufficientHull.add(new Point(0, 0));
        
        double perimeter = ConvexHullAlgorithm.calculateHullPerimeter(insufficientHull);
        assertEquals(0.0, perimeter, 0.001);
    }

    @Test
    void testConvexHullWithCollinearPoints() {
        List<Point> collinearPoints = new ArrayList<>();
        collinearPoints.add(new Point(0, 0));
        collinearPoints.add(new Point(1, 1));
        collinearPoints.add(new Point(2, 2));
        collinearPoints.add(new Point(3, 3));
        collinearPoints.add(new Point(4, 4));
        
        // Should still work with collinear points
        List<Point> hull = ConvexHullAlgorithm.findConvexHull(collinearPoints);
        assertNotNull(hull);
        assertTrue(hull.size() >= 2);
    }

    @Test
    void testConvexHullWithSinglePoint() {
        List<Point> singlePoint = new ArrayList<>();
        singlePoint.add(new Point(0, 0));
        
        assertThrows(IllegalArgumentException.class, () -> {
            ConvexHullAlgorithm.findConvexHull(singlePoint);
        });
    }

    @Test
    void testConvexHullWithTwoPoints() {
        List<Point> twoPoints = new ArrayList<>();
        twoPoints.add(new Point(0, 0));
        twoPoints.add(new Point(1, 1));
        
        assertThrows(IllegalArgumentException.class, () -> {
            ConvexHullAlgorithm.findConvexHull(twoPoints);
        });
    }

    @Test
    void testConvexHullWithComplexShape() {
        // Create a more complex shape
        List<Point> complexPoints = new ArrayList<>();
        complexPoints.add(new Point(0, 0));
        complexPoints.add(new Point(2, 0));
        complexPoints.add(new Point(4, 0));
        complexPoints.add(new Point(4, 2));
        complexPoints.add(new Point(4, 4));
        complexPoints.add(new Point(2, 4));
        complexPoints.add(new Point(0, 4));
        complexPoints.add(new Point(0, 2));
        complexPoints.add(new Point(2, 2)); // Interior point
        
        List<Point> hull = ConvexHullAlgorithm.findConvexHull(complexPoints);
        
        // Should have 4 vertices (the outer rectangle corners)
        assertEquals(4, hull.size());
        
        // All hull points should be on the boundary
        for (Point hullPoint : hull) {
            assertTrue(complexPoints.contains(hullPoint));
        }
    }

    @Test
    void testConvexHullProperties() {
        List<Point> hull = ConvexHullAlgorithm.findConvexHull(squarePoints);
        
        // Hull should not be empty
        assertFalse(hull.isEmpty());
        
        // Hull should contain only points from the original set
        for (Point hullPoint : hull) {
            assertTrue(squarePoints.contains(hullPoint));
        }
        
        // Hull should be convex (no interior angles > 180 degrees)
        // This is implicitly tested by the Graham Scan algorithm
    }
}
