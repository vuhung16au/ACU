package com.acu.javafx.convexhull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Point class functionality
 */
public class PointTest {

    private Point point1;
    private Point point2;
    private Point point3;

    @BeforeEach
    void setUp() {
        point1 = new Point(0, 0);
        point2 = new Point(3, 4);
        point3 = new Point(1, 1);
    }

    @Test
    void testPointCreation() {
        Point point = new Point(5, 10);
        assertEquals(5, point.getX());
        assertEquals(10, point.getY());
    }

    @Test
    void testSetters() {
        point1.setX(15);
        point1.setY(20);
        assertEquals(15, point1.getX());
        assertEquals(20, point1.getY());
    }

    @Test
    void testDistanceTo() {
        // Distance from (0,0) to (3,4) should be 5
        double distance = point1.distanceTo(point2);
        assertEquals(5.0, distance, 0.001);
    }

    @Test
    void testDistanceToSamePoint() {
        double distance = point1.distanceTo(point1);
        assertEquals(0.0, distance, 0.001);
    }

    @Test
    void testCrossProduct() {
        // Test counter-clockwise turn
        double crossProduct = Point.crossProduct(point1, point3, point2);
        assertTrue(crossProduct > 0, "Should be positive for counter-clockwise turn");
    }

    @Test
    void testCrossProductClockwise() {
        // Test clockwise turn
        double crossProduct = Point.crossProduct(point1, point2, point3);
        assertTrue(crossProduct < 0, "Should be negative for clockwise turn");
    }

    @Test
    void testCrossProductCollinear() {
        // Test collinear points
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);
        Point p3 = new Point(2, 2);
        
        double crossProduct = Point.crossProduct(p1, p2, p3);
        assertEquals(0.0, crossProduct, 0.001, "Should be zero for collinear points");
    }

    @Test
    void testToString() {
        String str = point1.toString();
        assertTrue(str.contains("0.00"));
        assertTrue(str.contains("0.00"));
        assertTrue(str.startsWith("("));
        assertTrue(str.endsWith(")"));
    }

    @Test
    void testEquals() {
        Point samePoint = new Point(0, 0);
        Point differentPoint = new Point(1, 1);
        
        assertTrue(point1.equals(samePoint));
        assertFalse(point1.equals(differentPoint));
        assertTrue(point1.equals(point1));
        assertFalse(point1.equals(null));
        assertFalse(point1.equals("not a point"));
    }

    @Test
    void testHashCode() {
        Point samePoint = new Point(0, 0);
        Point differentPoint = new Point(1, 1);
        
        assertEquals(point1.hashCode(), samePoint.hashCode());
        assertNotEquals(point1.hashCode(), differentPoint.hashCode());
    }

    @Test
    void testHashCodeConsistency() {
        int hashCode1 = point1.hashCode();
        int hashCode2 = point1.hashCode();
        assertEquals(hashCode1, hashCode2);
    }
}
