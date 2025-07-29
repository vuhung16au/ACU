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
// Base class for all shapes
class Shape {
    protected String color;
    
    public Shape(String color) {
        this.color = color;
    }
    
    // Method that will be overridden by subclasses
    public double getArea() {
        return 0.0; // Default implementation
    }
    
    // Method that will be overridden by subclasses
    public double getPerimeter() {
        return 0.0; // Default implementation
    }
    
    // Method that will be overridden by subclasses
    public void displayInfo() {
        System.out.println("Shape - Color: " + color);
    }
    
    public String getColor() {
        return color;
    }
}

// Subclass for Circle
class Circle extends Shape {
    private double radius;
    
    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }
    
    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Circle - Color: " + color + ", Radius: " + radius);
        System.out.println("  Area: " + String.format("%.2f", getArea()));
        System.out.println("  Perimeter: " + String.format("%.2f", getPerimeter()));
    }
    
    public double getRadius() {
        return radius;
    }
}

// Subclass for Rectangle
class Rectangle extends Shape {
    private double width;
    private double height;
    
    public Rectangle(String color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double getArea() {
        return width * height;
    }
    
    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Rectangle - Color: " + color + ", Width: " + width + ", Height: " + height);
        System.out.println("  Area: " + String.format("%.2f", getArea()));
        System.out.println("  Perimeter: " + String.format("%.2f", getPerimeter()));
    }
    
    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
}

// Subclass for Triangle
class Triangle extends Shape {
    private double sideA;
    private double sideB;
    private double sideC;
    
    public Triangle(String color, double sideA, double sideB, double sideC) {
        super(color);
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }
    
    @Override
    public double getArea() {
        // Using Heron's formula
        double s = (sideA + sideB + sideC) / 2;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }
    
    @Override
    public double getPerimeter() {
        return sideA + sideB + sideC;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Triangle - Color: " + color + ", Sides: " + sideA + ", " + sideB + ", " + sideC);
        System.out.println("  Area: " + String.format("%.2f", getArea()));
        System.out.println("  Perimeter: " + String.format("%.2f", getPerimeter()));
    }
    
    public double getSideA() {
        return sideA;
    }
    
    public double getSideB() {
        return sideB;
    }
    
    public double getSideC() {
        return sideC;
    }
}

// Main class demonstrating basic polymorphism
public class Example1 {
    
    // Method that demonstrates polymorphic behavior
    // This method can work with any Shape subclass
    public static void processShape(Shape shape) {
        System.out.println("Processing shape:");
        shape.displayInfo(); // Calls the appropriate version based on actual type
        System.out.println();
    }
    
    // Method demonstrating casting
    public static void demonstrateCasting() {
        System.out.println("=== Demonstrating Casting ===");
        
        // Upcasting - treating subclasses as superclass
        Shape circleShape = new Circle("Red", 5.0);
        Shape rectShape = new Rectangle("Blue", 4.0, 6.0);
        Shape triangleShape = new Triangle("Green", 3.0, 4.0, 5.0);
        
        // Downcasting - treating superclass as subclass
        if (circleShape instanceof Circle) {
            Circle circle = (Circle) circleShape;
            System.out.println("Circle radius: " + circle.getRadius());
        }
        
        if (rectShape instanceof Rectangle) {
            Rectangle rect = (Rectangle) rectShape;
            System.out.println("Rectangle dimensions: " + rect.getWidth() + " x " + rect.getHeight());
        }
        
        if (triangleShape instanceof Triangle) {
            Triangle triangle = (Triangle) triangleShape;
            System.out.println("Triangle sides: " + triangle.getSideA() + ", " + triangle.getSideB() + ", " + triangle.getSideC());
        }
        
        System.out.println();
    }
    
    // Method demonstrating polymorphic collections
    public static void demonstratePolymorphicCollections() {
        System.out.println("=== Demonstrating Polymorphic Collections ===");
        
        // Array of Shapes (can hold any Shape subclass)
        Shape[] shapes = {
            new Circle("Red", 3.0),
            new Rectangle("Blue", 5.0, 3.0),
            new Triangle("Green", 6.0, 8.0, 10.0),
            new Circle("Yellow", 2.5),
            new Rectangle("Purple", 4.0, 4.0)
        };
        
        // Process all shapes polymorphically
        for (Shape shape : shapes) {
            processShape(shape);
        }
        
        // Calculate total area of all shapes
        double totalArea = 0.0;
        for (Shape shape : shapes) {
            totalArea += shape.getArea();
        }
        
        System.out.println("Total area of all shapes: " + String.format("%.2f", totalArea));
        System.out.println();
    }
    
    public static void main(String[] args) {
        System.out.println("📐 Basic Polymorphism Example - Shapes");
        System.out.println("=======================================");
        System.out.println();
        
        // Demonstrate different aspects of polymorphism
        demonstrateCasting();
        demonstratePolymorphicCollections();
        
        System.out.println("✅ Basic polymorphism example completed!");
        System.out.println();
        System.out.println("Key Points:");
        System.out.println("- Different shapes respond to the same method calls");
        System.out.println("- Method overriding enables polymorphic behavior");
        System.out.println("- Collections can hold objects of different types");
        System.out.println("- Casting allows access to subclass-specific methods");
    }
} 