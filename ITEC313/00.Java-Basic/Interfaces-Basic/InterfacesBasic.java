/**
 * InterfacesBasic.java
 * 
 * This program demonstrates Java interfaces, which are similar to Python's abstract base classes
 * but with some key differences. Interfaces in Java provide a way to define a contract that
 * implementing classes must follow.
 * 
 * Key differences from Python:
 * - Java interfaces can only contain method signatures and constants (no implementation)
 * - Java 8+ introduced default methods in interfaces (similar to Python's mixins)
 * - Java interfaces support multiple inheritance (unlike Python's single inheritance)
 * - Java interfaces are more restrictive than Python's duck typing
 * 
 * @author ITEC313 - Object-Oriented Programming
 * @version 1.0
 */

public class InterfacesBasic {
    
    /**
     * Main method to demonstrate interface concepts
     * Unlike Python's if __name__ == "__main__", Java uses public static void main
     */
    public static void main(String[] args) {
        System.out.println("=== Java Interfaces Basic Demo ===\n");
        
        // Demonstrate basic interface implementation
        demonstrateBasicInterface();
        
        // Demonstrate interface with default methods
        demonstrateDefaultMethods();
        
        // Demonstrate multiple interface implementation
        demonstrateMultipleInterfaces();
        
        // Demonstrate interface as type
        demonstrateInterfaceAsType();
        
        System.out.println("\n=== Demo Complete ===");
    }
    
    /**
     * Demonstrates basic interface implementation
     * In Python, this would be similar to implementing an abstract base class
     */
    private static void demonstrateBasicInterface() {
        System.out.println("1. Basic Interface Implementation:");
        System.out.println("--------------------------------");
        
        // Create instances of classes that implement interfaces
        Drawable circle = new Circle(5.0);
        Drawable rectangle = new Rectangle(4.0, 6.0);
        
        // Call interface methods (polymorphism)
        circle.draw();  // In Python: circle.draw()
        rectangle.draw(); // In Python: rectangle.draw()
        
        // Get information about the shapes
        System.out.println("Circle area: " + circle.getArea());
        System.out.println("Rectangle area: " + rectangle.getArea());
        System.out.println();
    }
    
    /**
     * Demonstrates default methods in interfaces (Java 8+ feature)
     * This is similar to Python's mixins or trait-like behavior
     */
    private static void demonstrateDefaultMethods() {
        System.out.println("2. Default Methods in Interfaces:");
        System.out.println("--------------------------------");
        
        // Create instances that use default methods
        Loggable logger = new SimpleLogger();
        Loggable advancedLogger = new AdvancedLogger();
        
        // Call default method (inherited from interface)
        logger.log("Basic log message"); // Uses default implementation
        advancedLogger.log("Advanced log message"); // Uses overridden implementation
        
        // Call specific methods
        logger.info("Info message");
        advancedLogger.info("Advanced info message");
        System.out.println();
    }
    
    /**
     * Demonstrates implementing multiple interfaces
     * In Python, this would be similar to multiple inheritance with mixins
     */
    private static void demonstrateMultipleInterfaces() {
        System.out.println("3. Multiple Interface Implementation:");
        System.out.println("-----------------------------------");
        
        // Create an object that implements multiple interfaces
        SmartPhone smartPhone = new SmartPhone();
        
        // Call methods from different interfaces
        smartPhone.turnOn();    // From Powerable interface
        smartPhone.connect();    // From Connectable interface
        smartPhone.process();    // From Processable interface
        
        // Demonstrate interface-specific behavior
        System.out.println("Device status: " + smartPhone.getStatus());
        System.out.println();
    }
    
    /**
     * Demonstrates using interfaces as types (polymorphism)
     * This is similar to Python's duck typing but more explicit
     */
    private static void demonstrateInterfaceAsType() {
        System.out.println("4. Interface as Type (Polymorphism):");
        System.out.println("------------------------------------");
        
        // Create an array of different objects that implement the same interface
        Drawable[] shapes = {
            new Circle(3.0),
            new Rectangle(2.0, 4.0),
            new Circle(1.5)
        };
        
        // Process all shapes through the interface
        double totalArea = 0.0;
        for (Drawable shape : shapes) {
            shape.draw();
            totalArea += shape.getArea();
        }
        
        System.out.println("Total area of all shapes: " + totalArea);
        System.out.println();
    }
}

/**
 * Basic interface example - similar to Python's abstract base class
 * In Python, this would be:
 * from abc import ABC, abstractmethod
 * class Drawable(ABC):
 *     @abstractmethod
 *     def draw(self): pass
 */
interface Drawable {
    // Interface constants (public static final by default)
    String DEFAULT_COLOR = "black";
    
    // Abstract method (must be implemented by classes)
    void draw();
    
    // Abstract method for getting area
    double getArea();
}

/**
 * Interface with default methods (Java 8+ feature)
 * Similar to Python's mixins or trait-like behavior
 */
interface Loggable {
    // Default method - provides implementation
    // In Python, this would be a mixin with concrete methods
    default void log(String message) {
        System.out.println("[LOG] " + message);
    }
    
    // Abstract method that must be implemented
    void info(String message);
}

/**
 * Multiple interfaces example
 * In Python, this would be multiple inheritance with mixins
 */
interface Powerable {
    void turnOn();
    void turnOff();
    String getStatus();
}

interface Connectable {
    void connect();
    void disconnect();
}

interface Processable {
    void process();
}

/**
 * Concrete class implementing Drawable interface
 * Similar to implementing an abstract base class in Python
 */
class Circle implements Drawable {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a circle with radius " + radius);
    }
    
    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
}

/**
 * Another concrete class implementing Drawable interface
 */
class Rectangle implements Drawable {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a rectangle " + width + "x" + height);
    }
    
    @Override
    public double getArea() {
        return width * height;
    }
}

/**
 * Class implementing interface with default methods
 * Demonstrates how to override default methods
 */
class SimpleLogger implements Loggable {
    @Override
    public void info(String message) {
        System.out.println("[INFO] " + message);
    }
    // Uses default implementation of log() method
}

/**
 * Class that overrides the default method
 */
class AdvancedLogger implements Loggable {
    @Override
    public void log(String message) {
        System.out.println("[ADVANCED LOG] " + message + " (timestamp: " + System.currentTimeMillis() + ")");
    }
    
    @Override
    public void info(String message) {
        System.out.println("[ADVANCED INFO] " + message);
    }
}

/**
 * Class implementing multiple interfaces
 * In Python, this would be: class SmartPhone(Powerable, Connectable, Processable)
 */
class SmartPhone implements Powerable, Connectable, Processable {
    private boolean isOn = false;
    private boolean isConnected = false;
    
    @Override
    public void turnOn() {
        isOn = true;
        System.out.println("Smartphone turned on");
    }
    
    @Override
    public void turnOff() {
        isOn = false;
        System.out.println("Smartphone turned off");
    }
    
    @Override
    public String getStatus() {
        return "Power: " + (isOn ? "ON" : "OFF") + ", Connection: " + (isConnected ? "CONNECTED" : "DISCONNECTED");
    }
    
    @Override
    public void connect() {
        if (isOn) {
            isConnected = true;
            System.out.println("Smartphone connected to network");
        } else {
            System.out.println("Cannot connect - device is off");
        }
    }
    
    @Override
    public void disconnect() {
        isConnected = false;
        System.out.println("Smartphone disconnected from network");
    }
    
    @Override
    public void process() {
        if (isOn && isConnected) {
            System.out.println("Smartphone processing data");
        } else {
            System.out.println("Cannot process - device not ready");
        }
    }
} 