package com.acu.javafx.kochsnowflake;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.paint.Color;
import java.util.List;

/**
 * Test class for Koch Snowflake Fractal
 * 
 * This class tests the core fractal functionality including:
 * - Fractal generation for different orders
 * - Segment count calculations
 * - Perimeter calculations
 * - Line segment transformations
 * - Edge cases and boundary conditions
 */
public class KochSnowflakeTest {

    private KochSnowflake kochSnowflake;
    private static final double TEST_SIZE = 100.0;
    private static final Color TEST_COLOR = Color.RED;

    @BeforeEach
    void setUp() {
        kochSnowflake = new KochSnowflake(0, TEST_SIZE, TEST_COLOR);
    }

    @Test
    void testDefaultConstructor() {
        KochSnowflake defaultFractal = new KochSnowflake();
        assertNotNull(defaultFractal);
        assertEquals(0, defaultFractal.getOrder());
        assertTrue(defaultFractal.getSize() > 0);
        assertNotNull(defaultFractal.getColor());
    }

    @Test
    void testConstructorWithOrder() {
        KochSnowflake fractal = new KochSnowflake(2);
        assertEquals(2, fractal.getOrder());
        assertTrue(fractal.getSize() > 0);
        assertNotNull(fractal.getColor());
    }

    @Test
    void testConstructorWithAllParameters() {
        KochSnowflake fractal = new KochSnowflake(3, TEST_SIZE, TEST_COLOR);
        assertEquals(3, fractal.getOrder());
        assertEquals(TEST_SIZE, fractal.getSize());
        assertEquals(TEST_COLOR, fractal.getColor());
    }

    @Test
    void testOrderZeroGeneratesTriangle() {
        kochSnowflake.setOrder(0);
        assertEquals(3, kochSnowflake.getSegmentCount());
        
        List<KochSnowflake.LineSegment> segments = kochSnowflake.getSegments();
        assertEquals(3, segments.size());
        
        // Verify all segments are connected (triangle)
        for (KochSnowflake.LineSegment segment : segments) {
            assertNotNull(segment);
            assertTrue(segment.x1 >= 0 && segment.x1 <= TEST_SIZE);
            assertTrue(segment.y1 >= 0 && segment.y1 <= TEST_SIZE);
            assertTrue(segment.x2 >= 0 && segment.x2 <= TEST_SIZE);
            assertTrue(segment.y2 >= 0 && segment.y2 <= TEST_SIZE);
        }
    }

    @Test
    void testOrderOneGeneratesCorrectSegments() {
        kochSnowflake.setOrder(1);
        assertEquals(12, kochSnowflake.getSegmentCount());
        
        List<KochSnowflake.LineSegment> segments = kochSnowflake.getSegments();
        assertEquals(12, segments.size());
        
        // Verify all segments are within reasonable bounds (fractal can extend beyond canvas)
        for (KochSnowflake.LineSegment segment : segments) {
            assertTrue(segment.x1 >= -TEST_SIZE && segment.x1 <= 2 * TEST_SIZE);
            assertTrue(segment.y1 >= -TEST_SIZE && segment.y1 <= 2 * TEST_SIZE);
            assertTrue(segment.x2 >= -TEST_SIZE && segment.x2 <= 2 * TEST_SIZE);
            assertTrue(segment.y2 >= -TEST_SIZE && segment.y2 <= 2 * TEST_SIZE);
        }
    }

    @Test
    void testOrderTwoGeneratesCorrectSegments() {
        kochSnowflake.setOrder(2);
        assertEquals(48, kochSnowflake.getSegmentCount());
    }

    @Test
    void testOrderThreeGeneratesCorrectSegments() {
        kochSnowflake.setOrder(3);
        assertEquals(192, kochSnowflake.getSegmentCount());
    }

    @Test
    void testSegmentCountCalculation() {
        assertEquals(3, KochSnowflake.calculateSegmentCount(0));
        assertEquals(12, KochSnowflake.calculateSegmentCount(1));
        assertEquals(48, KochSnowflake.calculateSegmentCount(2));
        assertEquals(192, KochSnowflake.calculateSegmentCount(3));
        assertEquals(768, KochSnowflake.calculateSegmentCount(4));
    }

    @Test
    void testNegativeOrderHandling() {
        kochSnowflake.setOrder(-1);
        assertEquals(0, kochSnowflake.getOrder());
        assertEquals(3, kochSnowflake.getSegmentCount()); // Should still generate triangle
    }

    @Test
    void testLargeOrderHandling() {
        kochSnowflake.setOrder(10);
        assertEquals(10, kochSnowflake.getOrder());
        assertTrue(kochSnowflake.getSegmentCount() > 0);
    }

    @Test
    void testSizeChange() {
        double newSize = 200.0;
        kochSnowflake.setSize(newSize);
        assertEquals(newSize, kochSnowflake.getSize());
        assertEquals(3, kochSnowflake.getSegmentCount()); // Should still be triangle
    }

    @Test
    void testColorChange() {
        Color newColor = Color.GREEN;
        kochSnowflake.setColor(newColor);
        assertEquals(newColor, kochSnowflake.getColor());
    }

    @Test
    void testPerimeterCalculation() {
        double perimeter0 = KochSnowflake.calculatePerimeter(0, TEST_SIZE);
        double perimeter1 = KochSnowflake.calculatePerimeter(1, TEST_SIZE);
        double perimeter2 = KochSnowflake.calculatePerimeter(2, TEST_SIZE);
        
        assertTrue(perimeter0 > 0);
        assertTrue(perimeter1 > perimeter0); // Perimeter increases with order
        assertTrue(perimeter2 > perimeter1);
    }

    @Test
    void testPerimeterCalculationWithDifferentSizes() {
        double size1 = 100.0;
        double size2 = 200.0;
        
        double perimeter1 = KochSnowflake.calculatePerimeter(1, size1);
        double perimeter2 = KochSnowflake.calculatePerimeter(1, size2);
        
        assertTrue(perimeter2 > perimeter1); // Larger size should have larger perimeter
    }

    @Test
    void testNegativeOrderPerimeterCalculation() {
        double perimeter = KochSnowflake.calculatePerimeter(-1, TEST_SIZE);
        assertEquals(0.0, perimeter);
    }

    @Test
    void testLineSegmentTransformation() {
        // Test the transformSegment method indirectly by checking order 1
        kochSnowflake.setOrder(1);
        List<KochSnowflake.LineSegment> segments = kochSnowflake.getSegments();
        
        // Should have 12 segments (3 original * 4 transformed segments each)
        assertEquals(12, segments.size());
        
        // All segments should be valid
        for (KochSnowflake.LineSegment segment : segments) {
            assertNotNull(segment);
            assertTrue(segment.x1 >= -TEST_SIZE && segment.x1 <= 2 * TEST_SIZE);
            assertTrue(segment.y1 >= -TEST_SIZE && segment.y1 <= 2 * TEST_SIZE);
            assertTrue(segment.x2 >= -TEST_SIZE && segment.x2 <= 2 * TEST_SIZE);
            assertTrue(segment.y2 >= -TEST_SIZE && segment.y2 <= 2 * TEST_SIZE);
        }
    }

    @Test
    void testLineSegmentToString() {
        KochSnowflake.LineSegment segment = new KochSnowflake.LineSegment(0, 0, 10, 10);
        String segmentString = segment.toString();
        assertTrue(segmentString.contains("LineSegment"));
        assertTrue(segmentString.contains("0.00"));
        assertTrue(segmentString.contains("10.00"));
    }

    @Test
    void testKochSnowflakeToString() {
        kochSnowflake.setOrder(2);
        String fractalString = kochSnowflake.toString();
        assertTrue(fractalString.contains("KochSnowflake"));
        assertTrue(fractalString.contains("order=2"));
        assertTrue(fractalString.contains("size=" + TEST_SIZE));
        assertTrue(fractalString.contains("segments=48"));
    }

    @Test
    void testGetSegmentsReturnsCopy() {
        List<KochSnowflake.LineSegment> segments1 = kochSnowflake.getSegments();
        List<KochSnowflake.LineSegment> segments2 = kochSnowflake.getSegments();
        
        // Should be different objects (copies)
        assertNotSame(segments1, segments2);
        
        // But should have same content
        assertEquals(segments1.size(), segments2.size());
    }

    @Test
    void testFractalRegenerationOnOrderChange() {
        // Set initial order
        kochSnowflake.setOrder(1);
        int initialCount = kochSnowflake.getSegmentCount();
        
        // Change order
        kochSnowflake.setOrder(2);
        int newCount = kochSnowflake.getSegmentCount();
        
        // Should be different
        assertNotEquals(initialCount, newCount);
        assertEquals(48, newCount);
    }

    @Test
    void testFractalRegenerationOnSizeChange() {
        // Set initial size
        kochSnowflake.setSize(100.0);
        List<KochSnowflake.LineSegment> initialSegments = kochSnowflake.getSegments();
        
        // Change size
        kochSnowflake.setSize(200.0);
        List<KochSnowflake.LineSegment> newSegments = kochSnowflake.getSegments();
        
        // Should have same number of segments
        assertEquals(initialSegments.size(), newSegments.size());
        
        // But different coordinates (scaled)
        for (int i = 0; i < initialSegments.size(); i++) {
            KochSnowflake.LineSegment initial = initialSegments.get(i);
            KochSnowflake.LineSegment newSegment = newSegments.get(i);
            
            // New coordinates should be approximately double (allowing for centering)
            assertTrue(Math.abs(newSegment.x1 - initial.x1 * 2) < 10);
            assertTrue(Math.abs(newSegment.y1 - initial.y1 * 2) < 10);
        }
    }

    @Test
    void testZeroSizeHandling() {
        kochSnowflake.setSize(0);
        assertEquals(0, kochSnowflake.getSize());
        // Should still generate segments (though they might be at origin)
        assertTrue(kochSnowflake.getSegmentCount() > 0);
    }

    @Test
    void testVerySmallSizeHandling() {
        kochSnowflake.setSize(0.1);
        assertEquals(0.1, kochSnowflake.getSize());
        assertTrue(kochSnowflake.getSegmentCount() > 0);
    }
}
