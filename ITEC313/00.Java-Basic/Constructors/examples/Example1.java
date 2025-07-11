/**
 * Example1.java - Basic Constructor Examples
 * 
 * This example demonstrates fundamental constructor concepts using a Rectangle class.
 * Shows default and parameterized constructors.
 * 
 * For Python developers: This shows how Java constructors work compared to
 * Python's __init__ method.
 */

public class Example1 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic Constructors ===\n");
        
        // Create rectangles using different constructors
        Rectangle rect1 = new Rectangle();  // Default constructor
        Rectangle rect2 = new Rectangle(5, 10);  // Parameterized constructor
        
        System.out.println("Rectangle 1 (default): " + rect1);
        System.out.println("Rectangle 2 (5x10): " + rect2);
        
        // Calculate areas
        System.out.println("Area of Rectangle 1: " + rect1.getArea());
        System.out.println("Area of Rectangle 2: " + rect2.getArea());
    }
}

/**
 * Rectangle class demonstrating basic constructors
 */
class Rectangle {
    private double width;
    private double height;
    
    /**
     * Default constructor - sets default values
     * In Python: This would be like __init__(self, width=1, height=1)
     */
    public Rectangle() {
        this.width = 1.0;
        this.height = 1.0;
        System.out.println("   Default constructor called - creating 1x1 rectangle");
    }
    
    /**
     * Parameterized constructor
     * In Python: This would be like __init__(self, width, height)
     */
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
        System.out.println("   Parameterized constructor called - creating " + width + "x" + height + " rectangle");
    }
    
    // Getter methods
    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
    
    public double getArea() {
        return width * height;
    }
    
    @Override
    public String toString() {
        return String.format("Rectangle{width=%.1f, height=%.1f}", width, height);
    }
} 