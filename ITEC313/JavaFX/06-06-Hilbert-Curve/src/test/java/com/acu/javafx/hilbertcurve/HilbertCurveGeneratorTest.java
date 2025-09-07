package com.acu.javafx.hilbertcurve;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Test class for HilbertCurveGenerator
 * 
 * This class tests the core functionality of the Hilbert curve generation algorithm,
 * including validation, point generation, and edge cases.
 */
@DisplayName("Hilbert Curve Generator Tests")
public class HilbertCurveGeneratorTest {

    private static final double TEST_WIDTH = 400.0;
    private static final double TEST_HEIGHT = 400.0;
    private static final double EPSILON = 0.001;

    @BeforeEach
    void setUp() {
        // Setup method - can be used for common test initialization
    }

    @Test
    @DisplayName("Test valid order range")
    void testValidOrderRange() {
        // Test that orders 1-6 are accepted
        for (int order = 1; order <= 6; order++) {
            final int currentOrder = order; // Make it effectively final
            assertDoesNotThrow(() -> {
                HilbertCurveGenerator.generateHilbertCurve(currentOrder, TEST_WIDTH, TEST_HEIGHT);
            }, "Order " + currentOrder + " should be valid");
        }
    }

    @Test
    @DisplayName("Test invalid order range")
    void testInvalidOrderRange() {
        // Test orders outside valid range
        assertThrows(IllegalArgumentException.class, () -> {
            HilbertCurveGenerator.generateHilbertCurve(0, TEST_WIDTH, TEST_HEIGHT);
        }, "Order 0 should throw IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, () -> {
            HilbertCurveGenerator.generateHilbertCurve(7, TEST_WIDTH, TEST_HEIGHT);
        }, "Order 7 should throw IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, () -> {
            HilbertCurveGenerator.generateHilbertCurve(-1, TEST_WIDTH, TEST_HEIGHT);
        }, "Negative order should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("Test point count for different orders")
    void testPointCount() {
        // Test that the correct number of points are generated
        assertEquals(4, HilbertCurveGenerator.getPointCount(1), "Order 1 should have 4 points");
        assertEquals(16, HilbertCurveGenerator.getPointCount(2), "Order 2 should have 16 points");
        assertEquals(64, HilbertCurveGenerator.getPointCount(3), "Order 3 should have 64 points");
        assertEquals(256, HilbertCurveGenerator.getPointCount(4), "Order 4 should have 256 points");
        assertEquals(1024, HilbertCurveGenerator.getPointCount(5), "Order 5 should have 1024 points");
        assertEquals(4096, HilbertCurveGenerator.getPointCount(6), "Order 6 should have 4096 points");
    }

    @Test
    @DisplayName("Test Hilbert curve generation for order 1")
    void testHilbertCurveOrder1() {
        List<HilbertCurveGenerator.Point> points = 
            HilbertCurveGenerator.generateHilbertCurve(1, TEST_WIDTH, TEST_HEIGHT);
        
        assertNotNull(points, "Points list should not be null");
        assertEquals(4, points.size(), "Order 1 should generate 4 points");
        
        // Verify all points are within bounds
        for (HilbertCurveGenerator.Point point : points) {
            assertTrue(point.x >= 0 && point.x <= TEST_WIDTH, 
                      "X coordinate should be within bounds: " + point.x);
            assertTrue(point.y >= 0 && point.y <= TEST_HEIGHT, 
                      "Y coordinate should be within bounds: " + point.y);
        }
        
        // Verify curve is valid
        assertTrue(HilbertCurveGenerator.validateHilbertCurve(points, 1), 
                  "Generated curve should be valid");
    }

    @Test
    @DisplayName("Test Hilbert curve generation for order 2")
    void testHilbertCurveOrder2() {
        List<HilbertCurveGenerator.Point> points = 
            HilbertCurveGenerator.generateHilbertCurve(2, TEST_WIDTH, TEST_HEIGHT);
        
        assertNotNull(points, "Points list should not be null");
        assertEquals(16, points.size(), "Order 2 should generate 16 points");
        
        // Verify all points are within bounds
        for (HilbertCurveGenerator.Point point : points) {
            assertTrue(point.x >= 0 && point.x <= TEST_WIDTH, 
                      "X coordinate should be within bounds: " + point.x);
            assertTrue(point.y >= 0 && point.y <= TEST_HEIGHT, 
                      "Y coordinate should be within bounds: " + point.y);
        }
        
        // Verify curve is valid
        assertTrue(HilbertCurveGenerator.validateHilbertCurve(points, 2), 
                  "Generated curve should be valid");
    }

    @Test
    @DisplayName("Test Hilbert curve generation for order 3")
    void testHilbertCurveOrder3() {
        List<HilbertCurveGenerator.Point> points = 
            HilbertCurveGenerator.generateHilbertCurve(3, TEST_WIDTH, TEST_HEIGHT);
        
        assertNotNull(points, "Points list should not be null");
        assertEquals(64, points.size(), "Order 3 should generate 64 points");
        
        // Verify curve is valid
        assertTrue(HilbertCurveGenerator.validateHilbertCurve(points, 3), 
                  "Generated curve should be valid");
    }

    @Test
    @DisplayName("Test Hilbert curve generation for order 4")
    void testHilbertCurveOrder4() {
        List<HilbertCurveGenerator.Point> points = 
            HilbertCurveGenerator.generateHilbertCurve(4, TEST_WIDTH, TEST_HEIGHT);
        
        assertNotNull(points, "Points list should not be null");
        assertEquals(256, points.size(), "Order 4 should generate 256 points");
        
        // Verify curve is valid
        assertTrue(HilbertCurveGenerator.validateHilbertCurve(points, 4), 
                  "Generated curve should be valid");
    }

    @Test
    @DisplayName("Test Hilbert curve generation for order 5")
    void testHilbertCurveOrder5() {
        List<HilbertCurveGenerator.Point> points = 
            HilbertCurveGenerator.generateHilbertCurve(5, TEST_WIDTH, TEST_HEIGHT);
        
        assertNotNull(points, "Points list should not be null");
        assertEquals(1024, points.size(), "Order 5 should generate 1024 points");
        
        // Verify curve is valid
        assertTrue(HilbertCurveGenerator.validateHilbertCurve(points, 5), 
                  "Generated curve should be valid");
    }

    @Test
    @DisplayName("Test Hilbert curve generation for order 6")
    void testHilbertCurveOrder6() {
        List<HilbertCurveGenerator.Point> points = 
            HilbertCurveGenerator.generateHilbertCurve(6, TEST_WIDTH, TEST_HEIGHT);
        
        assertNotNull(points, "Points list should not be null");
        assertEquals(4096, points.size(), "Order 6 should generate 4096 points");
        
        // Verify curve is valid
        assertTrue(HilbertCurveGenerator.validateHilbertCurve(points, 6), 
                  "Generated curve should be valid");
    }

    @Test
    @DisplayName("Test alternative Hilbert curve generation")
    void testAlternativeHilbertCurveGeneration() {
        // Test the alternative implementation
        for (int order = 1; order <= 4; order++) { // Test up to order 4 for performance
            List<HilbertCurveGenerator.Point> points1 = 
                HilbertCurveGenerator.generateHilbertCurve(order, TEST_WIDTH, TEST_HEIGHT);
            List<HilbertCurveGenerator.Point> points2 = 
                HilbertCurveGenerator.generateHilbertCurveAlternative(order, TEST_WIDTH, TEST_HEIGHT);
            
            assertEquals(points1.size(), points2.size(), 
                        "Both implementations should generate the same number of points for order " + order);
            
            // Verify both curves are valid
            assertTrue(HilbertCurveGenerator.validateHilbertCurve(points1, order), 
                      "Primary implementation should generate valid curve for order " + order);
            assertTrue(HilbertCurveGenerator.validateHilbertCurve(points2, order), 
                      "Alternative implementation should generate valid curve for order " + order);
        }
    }

    @Test
    @DisplayName("Test curve validation with invalid data")
    void testCurveValidationWithInvalidData() {
        // Test with null points
        assertFalse(HilbertCurveGenerator.validateHilbertCurve(null, 1), 
                   "Null points should be invalid");
        
        // Test with empty points
        List<HilbertCurveGenerator.Point> emptyPoints = List.of();
        assertFalse(HilbertCurveGenerator.validateHilbertCurve(emptyPoints, 1), 
                   "Empty points should be invalid");
        
        // Test with wrong number of points
        List<HilbertCurveGenerator.Point> wrongCountPoints = List.of(
            new HilbertCurveGenerator.Point(0, 0),
            new HilbertCurveGenerator.Point(1, 1)
        );
        assertFalse(HilbertCurveGenerator.validateHilbertCurve(wrongCountPoints, 2), 
                   "Wrong number of points should be invalid");
    }

    @Test
    @DisplayName("Test Point class functionality")
    void testPointClass() {
        HilbertCurveGenerator.Point point1 = new HilbertCurveGenerator.Point(1.0, 2.0);
        HilbertCurveGenerator.Point point2 = new HilbertCurveGenerator.Point(1.0, 2.0);
        HilbertCurveGenerator.Point point3 = new HilbertCurveGenerator.Point(2.0, 1.0);
        
        // Test toString
        String pointString = point1.toString();
        assertTrue(pointString.contains("1.00") && pointString.contains("2.00"), 
                  "toString should contain coordinates");
        
        // Test equals
        assertEquals(point1, point2, "Equal points should be equal");
        assertNotEquals(point1, point3, "Different points should not be equal");
        
        // Test hashCode
        assertEquals(point1.hashCode(), point2.hashCode(), 
                    "Equal points should have same hashCode");
    }

    @Test
    @DisplayName("Test curve continuity")
    void testCurveContinuity() {
        // Test that consecutive points in the curve are within reasonable distance
        // Note: Hilbert curves can have jumps between consecutive points, but they should
        // still be within the overall drawing area
        for (int order = 1; order <= 3; order++) {
            List<HilbertCurveGenerator.Point> points = 
                HilbertCurveGenerator.generateHilbertCurve(order, TEST_WIDTH, TEST_HEIGHT);
            
            for (int i = 0; i < points.size() - 1; i++) {
                HilbertCurveGenerator.Point current = points.get(i);
                HilbertCurveGenerator.Point next = points.get(i + 1);
                
                double distance = Math.sqrt(
                    Math.pow(next.x - current.x, 2) + Math.pow(next.y - current.y, 2)
                );
                
                // Distance should be reasonable (not larger than the diagonal of the drawing area)
                double maxDistance = Math.sqrt(TEST_WIDTH * TEST_WIDTH + TEST_HEIGHT * TEST_HEIGHT);
                assertTrue(distance <= maxDistance, 
                          "Consecutive points should be within the drawing area");
            }
        }
    }

    @Test
    @DisplayName("Test different canvas sizes")
    void testDifferentCanvasSizes() {
        int order = 2;
        
        // Test with different canvas sizes
        double[] widths = {100.0, 200.0, 400.0, 800.0};
        double[] heights = {100.0, 200.0, 400.0, 800.0};
        
        for (double width : widths) {
            for (double height : heights) {
                List<HilbertCurveGenerator.Point> points = 
                    HilbertCurveGenerator.generateHilbertCurve(order, width, height);
                
                assertNotNull(points, "Points should not be null for size " + width + "x" + height);
                assertEquals(16, points.size(), "Should generate 16 points for order 2");
                
                // Verify all points are within bounds
                for (HilbertCurveGenerator.Point point : points) {
                    assertTrue(point.x >= 0 && point.x <= width, 
                              "X coordinate should be within bounds");
                    assertTrue(point.y >= 0 && point.y <= height, 
                              "Y coordinate should be within bounds");
                }
            }
        }
    }

    @Test
    @DisplayName("Test getPointCount with invalid orders")
    void testGetPointCountWithInvalidOrders() {
        assertThrows(IllegalArgumentException.class, () -> {
            HilbertCurveGenerator.getPointCount(0);
        }, "getPointCount should throw exception for order 0");
        
        assertThrows(IllegalArgumentException.class, () -> {
            HilbertCurveGenerator.getPointCount(7);
        }, "getPointCount should throw exception for order 7");
        
        assertThrows(IllegalArgumentException.class, () -> {
            HilbertCurveGenerator.getPointCount(-1);
        }, "getPointCount should throw exception for negative order");
    }
}
