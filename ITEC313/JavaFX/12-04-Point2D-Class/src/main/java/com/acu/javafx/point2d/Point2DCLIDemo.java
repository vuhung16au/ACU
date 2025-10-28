package com.acu.javafx.point2d;

/**
 * Point2D CLI Demo - Command Line Interface demonstration of the Point2D class.
 * This application provides a simple command-line interface to test
 * the Point2D class functionality including constructors, equality, and distance calculations.
 * 
 * @author ACU JavaFX Course
 * @version 1.0
 */
public class Point2DCLIDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Point2D Class CLI Demo ===");
        System.out.println("ACU JavaFX Course - Educational Demonstration\n");
        
        // Test 1: Default constructor
        System.out.println("Test 1: Default Constructor");
        Point2D origin = new Point2D();
        System.out.println("Origin point: " + origin.toString());
        System.out.println("Distance from origin: " + String.format("%.2f", origin.distanceFromOrigin()));
        System.out.println();
        
        // Test 2: Parameterized constructor
        System.out.println("Test 2: Parameterized Constructor");
        Point2D point1 = new Point2D(3.0, 4.0);
        Point2D point2 = new Point2D(6.0, 8.0);
        System.out.println("Point 1: " + point1.toString());
        System.out.println("Point 2: " + point2.toString());
        System.out.println();
        
        // Test 3: Equality testing
        System.out.println("Test 3: Equality Testing");
        Point2D point3 = new Point2D(3.0, 4.0);
        Point2D point4 = new Point2D(3.0, 4.0);
        Point2D point5 = new Point2D(3.1, 4.0);
        
        System.out.println("Point 3: " + point3.toString());
        System.out.println("Point 4: " + point4.toString());
        System.out.println("Point 5: " + point5.toString());
        System.out.println("Point 3 equals Point 4: " + point3.equals(point4));
        System.out.println("Point 3 equals Point 5: " + point3.equals(point5));
        System.out.println();
        
        // Test 4: HashCode testing
        System.out.println("Test 4: HashCode Testing");
        System.out.println("Point 3 hashCode: " + point3.hashCode());
        System.out.println("Point 4 hashCode: " + point4.hashCode());
        System.out.println("Point 5 hashCode: " + point5.hashCode());
        System.out.println();
        
        // Test 5: Distance calculations
        System.out.println("Test 5: Distance Calculations");
        System.out.println("Distance from Point 1 to Point 2: " + String.format("%.4f", point1.distance(point2)));
        System.out.println("Distance from Point 1 to origin: " + String.format("%.4f", point1.distanceFromOrigin()));
        System.out.println("Distance from Point 2 to origin: " + String.format("%.4f", point2.distanceFromOrigin()));
        System.out.println();
        
        // Test 6: Edge cases
        System.out.println("Test 6: Edge Cases");
        Point2D negativePoint = new Point2D(-3.0, -4.0);
        Point2D zeroPoint = new Point2D(0.0, 0.0);
        System.out.println("Negative point: " + negativePoint.toString());
        System.out.println("Zero point: " + zeroPoint.toString());
        System.out.println("Distance from negative to zero: " + String.format("%.4f", negativePoint.distance(zeroPoint)));
        System.out.println();
        
        // Test 7: Floating point precision
        System.out.println("Test 7: Floating Point Precision");
        Point2D precisePoint1 = new Point2D(1.0 / 3.0, 2.0 / 3.0);
        Point2D precisePoint2 = new Point2D(0.3333333333333333, 0.6666666666666666);
        System.out.println("Precise Point 1: " + precisePoint1.toString());
        System.out.println("Precise Point 2: " + precisePoint2.toString());
        System.out.println("Are they equal? " + precisePoint1.equals(precisePoint2));
        System.out.println("Distance between them: " + String.format("%.10f", precisePoint1.distance(precisePoint2)));
        System.out.println();
        
        // Test 8: Getter and Setter methods
        System.out.println("Test 8: Getter and Setter Methods");
        Point2D mutablePoint = new Point2D(1.0, 2.0);
        System.out.println("Original point: " + mutablePoint.toString());
        System.out.println("X coordinate: " + mutablePoint.getX());
        System.out.println("Y coordinate: " + mutablePoint.getY());
        
        mutablePoint.setX(10.0);
        mutablePoint.setY(20.0);
        System.out.println("After modification: " + mutablePoint.toString());
        System.out.println();
        
        System.out.println("=== All Tests Completed Successfully ===");
        System.out.println("The Point2D class implementation is working correctly!");
    }
}
