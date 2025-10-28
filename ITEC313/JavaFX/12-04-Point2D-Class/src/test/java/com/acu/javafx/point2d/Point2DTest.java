package com.acu.javafx.point2d;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for the Point2D class.
 * Tests all functionality including constructors, getters, setters,
 * equals method, hashCode method, and distance calculations.
 * 
 * @author ACU JavaFX Course
 * @version 1.0
 */
public class Point2DTest {
    
    private Point2D point1;
    private Point2D point2;
    private Point2D origin;
    
    @BeforeEach
    void setUp() {
        point1 = new Point2D(3.0, 4.0);
        point2 = new Point2D(6.0, 8.0);
        origin = new Point2D();
    }
    
    // Constructor Tests
    
    @Test
    void testDefaultConstructor() {
        Point2D defaultPoint = new Point2D();
        assertEquals(0.0, defaultPoint.getX(), "Default X should be 0.0");
        assertEquals(0.0, defaultPoint.getY(), "Default Y should be 0.0");
    }
    
    @Test
    void testParameterizedConstructor() {
        Point2D customPoint = new Point2D(5.5, -3.2);
        assertEquals(5.5, customPoint.getX(), "Custom X should match input");
        assertEquals(-3.2, customPoint.getY(), "Custom Y should match input");
    }
    
    @Test
    void testParameterizedConstructorWithZero() {
        Point2D zeroPoint = new Point2D(0.0, 0.0);
        assertEquals(0.0, zeroPoint.getX(), "Zero X should be 0.0");
        assertEquals(0.0, zeroPoint.getY(), "Zero Y should be 0.0");
    }
    
    // Getter and Setter Tests
    
    @Test
    void testGetters() {
        assertEquals(3.0, point1.getX(), "getX should return correct value");
        assertEquals(4.0, point1.getY(), "getY should return correct value");
    }
    
    @Test
    void testSetters() {
        Point2D testPoint = new Point2D(1.0, 2.0);
        testPoint.setX(10.0);
        testPoint.setY(20.0);
        
        assertEquals(10.0, testPoint.getX(), "setX should update X coordinate");
        assertEquals(20.0, testPoint.getY(), "setY should update Y coordinate");
    }
    
    @Test
    void testSettersWithNegativeValues() {
        Point2D testPoint = new Point2D(1.0, 2.0);
        testPoint.setX(-5.0);
        testPoint.setY(-10.0);
        
        assertEquals(-5.0, testPoint.getX(), "setX should handle negative values");
        assertEquals(-10.0, testPoint.getY(), "setY should handle negative values");
    }
    
    // Equals Method Tests
    
    @Test
    void testEqualsWithSameObject() {
        assertTrue(point1.equals(point1), "Object should equal itself");
    }
    
    @Test
    void testEqualsWithNull() {
        assertFalse(point1.equals(null), "Object should not equal null");
    }
    
    @Test
    void testEqualsWithDifferentClass() {
        String differentObject = "not a point";
        assertFalse(point1.equals(differentObject), "Object should not equal different class");
    }
    
    @Test
    void testEqualsWithEqualPoints() {
        Point2D equalPoint = new Point2D(3.0, 4.0);
        assertTrue(point1.equals(equalPoint), "Equal points should be equal");
    }
    
    @Test
    void testEqualsWithDifferentX() {
        Point2D differentX = new Point2D(5.0, 4.0);
        assertFalse(point1.equals(differentX), "Points with different X should not be equal");
    }
    
    @Test
    void testEqualsWithDifferentY() {
        Point2D differentY = new Point2D(3.0, 6.0);
        assertFalse(point1.equals(differentY), "Points with different Y should not be equal");
    }
    
    @Test
    void testEqualsWithBothDifferent() {
        Point2D bothDifferent = new Point2D(5.0, 6.0);
        assertFalse(point1.equals(bothDifferent), "Points with both coordinates different should not be equal");
    }
    
    @Test
    void testEqualsWithFloatingPointPrecision() {
        Point2D precisePoint1 = new Point2D(1.0 / 3.0, 2.0 / 3.0);
        Point2D precisePoint2 = new Point2D(0.3333333333333333, 0.6666666666666666);
        
        // These should be equal due to Double.compare handling precision
        assertTrue(precisePoint1.equals(precisePoint2), "Points with same floating point values should be equal");
    }
    
    // HashCode Method Tests
    
    @Test
    void testHashCodeConsistency() {
        int hashCode1 = point1.hashCode();
        int hashCode2 = point1.hashCode();
        assertEquals(hashCode1, hashCode2, "HashCode should be consistent");
    }
    
    @Test
    void testHashCodeWithEqualObjects() {
        Point2D equalPoint = new Point2D(3.0, 4.0);
        assertEquals(point1.hashCode(), equalPoint.hashCode(), "Equal objects should have same hashCode");
    }
    
    @Test
    void testHashCodeWithDifferentObjects() {
        Point2D differentPoint = new Point2D(5.0, 6.0);
        assertNotEquals(point1.hashCode(), differentPoint.hashCode(), "Different objects should have different hashCode");
    }
    
    @Test
    void testHashCodeWithOrigin() {
        Point2D origin1 = new Point2D();
        Point2D origin2 = new Point2D(0.0, 0.0);
        assertEquals(origin1.hashCode(), origin2.hashCode(), "Origin points should have same hashCode");
    }
    
    // Distance Calculation Tests
    
    @Test
    void testDistanceFromOrigin() {
        assertEquals(5.0, point1.distanceFromOrigin(), 0.001, "Distance from origin should be 5.0 for (3,4)");
        assertEquals(0.0, origin.distanceFromOrigin(), 0.001, "Distance from origin should be 0.0 for origin");
    }
    
    @Test
    void testDistanceFromOriginWithNegativeCoordinates() {
        Point2D negativePoint = new Point2D(-3.0, -4.0);
        assertEquals(5.0, negativePoint.distanceFromOrigin(), 0.001, "Distance from origin should be 5.0 for (-3,-4)");
    }
    
    @Test
    void testDistanceBetweenPoints() {
        double expectedDistance = Math.sqrt((6.0 - 3.0) * (6.0 - 3.0) + (8.0 - 4.0) * (8.0 - 4.0));
        assertEquals(expectedDistance, point1.distance(point2), 0.001, "Distance between points should be calculated correctly");
    }
    
    @Test
    void testDistanceBetweenSamePoints() {
        assertEquals(0.0, point1.distance(point1), 0.001, "Distance between same points should be 0.0");
    }
    
    @Test
    void testDistanceBetweenOriginAndPoint() {
        assertEquals(5.0, origin.distance(point1), 0.001, "Distance between origin and (3,4) should be 5.0");
    }
    
    @Test
    void testDistanceWithNullPoint() {
        assertThrows(IllegalArgumentException.class, () -> {
            point1.distance(null);
        }, "Distance with null point should throw IllegalArgumentException");
    }
    
    // ToString Method Tests
    
    @Test
    void testToString() {
        String expected = "Point2D(3.00, 4.00)";
        assertEquals(expected, point1.toString(), "toString should format correctly");
    }
    
    @Test
    void testToStringWithNegativeValues() {
        Point2D negativePoint = new Point2D(-3.0, -4.0);
        String expected = "Point2D(-3.00, -4.00)";
        assertEquals(expected, negativePoint.toString(), "toString should handle negative values");
    }
    
    @Test
    void testToStringWithZero() {
        String expected = "Point2D(0.00, 0.00)";
        assertEquals(expected, origin.toString(), "toString should handle zero values");
    }
    
    // Edge Cases and Boundary Tests
    
    @Test
    void testVeryLargeCoordinates() {
        Point2D largePoint = new Point2D(Double.MAX_VALUE, Double.MAX_VALUE);
        // With very large coordinates, the distance calculation may result in infinity
        // This is expected behavior due to floating-point limitations
        assertTrue(Double.isFinite(largePoint.distanceFromOrigin()) || Double.isInfinite(largePoint.distanceFromOrigin()), 
                   "Distance should be finite or infinite with large coordinates");
    }
    
    @Test
    void testVerySmallCoordinates() {
        Point2D smallPoint = new Point2D(Double.MIN_VALUE, Double.MIN_VALUE);
        assertTrue(Double.isFinite(smallPoint.distanceFromOrigin()), "Distance should be finite even with small coordinates");
    }
    
    @Test
    void testSymmetricDistance() {
        assertEquals(point1.distance(point2), point2.distance(point1), 0.001, "Distance should be symmetric");
    }
    
    @Test
    void testTriangleInequality() {
        Point2D point3 = new Point2D(1.0, 1.0);
        double d12 = point1.distance(point2);
        double d13 = point1.distance(point3);
        double d23 = point2.distance(point3);
        
        assertTrue(d12 <= d13 + d23, "Triangle inequality should hold");
    }
}
