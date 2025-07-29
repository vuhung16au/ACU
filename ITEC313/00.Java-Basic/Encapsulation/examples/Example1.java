/**
 * Example1.java
 * 
 * This program demonstrates example in Java:
 * - Core concepts and principles
 * - Implementation techniques
 * - Best practices and patterns
 * - Practical examples and usage
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
public class Example1 {
    public static void main(String[] args) {
        System.out.println("=== Example 1: Rectangle Encapsulation ===\n");
        
        // Create a rectangle
        Rectangle rect = new Rectangle();
        
        // Set dimensions using public methods
        rect.setWidth(10);
        rect.setHeight(5);
        
        // Get dimensions using public methods
        System.out.println("Rectangle Dimensions:");
        System.out.println("Width: " + rect.getWidth());
        System.out.println("Height: " + rect.getHeight());
        System.out.println("Area: " + rect.getArea());
        System.out.println("Perimeter: " + rect.getPerimeter());
        
        // Try to set invalid values
        rect.setWidth(-5);  // Should be ignored
        rect.setHeight(0);  // Should be ignored
        
        System.out.println("\nAfter invalid values:");
        System.out.println("Width: " + rect.getWidth());
        System.out.println("Height: " + rect.getHeight());
    }
}

/**
 * Rectangle class demonstrating basic encapsulation
 */
class Rectangle {
    // Private fields - cannot be accessed directly from outside
    private double width;
    private double height;
    
    // Constructor
    public Rectangle() {
        this.width = 0;
        this.height = 0;
    }
    
    // Public getter methods
    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
    
    // Public setter methods with validation
    public void setWidth(double width) {
        if (width > 0) {
            this.width = width;
        } else {
            System.out.println("Error: Width must be positive");
        }
    }
    
    public void setHeight(double height) {
        if (height > 0) {
            this.height = height;
        } else {
            System.out.println("Error: Height must be positive");
        }
    }
    
    // Computed properties (like @property in Python)
    public double getArea() {
        return width * height;
    }
    
    public double getPerimeter() {
        return 2 * (width + height);
    }
} 