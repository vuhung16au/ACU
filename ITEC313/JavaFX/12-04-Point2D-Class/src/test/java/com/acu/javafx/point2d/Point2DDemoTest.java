package com.acu.javafx.point2d;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the Point2D demo applications.
 * Tests the CLI demo functionality and ensures proper operation.
 * 
 * @author ACU JavaFX Course
 * @version 1.0
 */
public class Point2DDemoTest {
    
    @Test
    void testPoint2DCLIDemoMainMethod() {
        // Test that the CLI demo can be instantiated and run without errors
        assertDoesNotThrow(() -> {
            // We can't easily test the main method output without capturing System.out,
            // but we can ensure it doesn't throw exceptions
            String[] args = {};
            Point2DCLIDemo.main(args);
        }, "CLI demo should run without throwing exceptions");
    }
    
    @Test
    void testPoint2DDemoApplication() {
        // Test that the JavaFX demo can be instantiated
        assertDoesNotThrow(() -> {
            Point2DDemo demo = new Point2DDemo();
            assertNotNull(demo, "Point2DDemo should be instantiable");
        }, "JavaFX demo should be instantiable without errors");
    }
    
    @Test
    void testDemoClassesExist() {
        // Verify that both demo classes exist and can be loaded
        assertNotNull(Point2DCLIDemo.class, "Point2DCLIDemo class should exist");
        assertNotNull(Point2DDemo.class, "Point2DDemo class should exist");
    }
    
    @Test
    void testDemoClassesHaveMainMethod() {
        // Verify that the CLI demo has a main method
        try {
            Point2DCLIDemo.class.getMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            fail("Point2DCLIDemo should have a main method");
        }
    }
    
    @Test
    void testDemoClassesExtendApplication() {
        // Verify that the JavaFX demo extends Application
        assertTrue(javafx.application.Application.class.isAssignableFrom(Point2DDemo.class),
                "Point2DDemo should extend Application");
    }
}
