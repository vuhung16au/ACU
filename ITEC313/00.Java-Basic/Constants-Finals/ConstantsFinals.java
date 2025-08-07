/**
 * ConstantsFinals.java
 * 
 * This program demonstrates constants and final variables in Java:
 * - Final variable declaration
 * - Constant definition and usage
 * - Static final variables
 * - Immutable object creation (cannot be changed after creation)
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
public class ConstantsFinals {
    
    // Constants - typically declared as public static final
    // In Python, you'd use UPPER_CASE naming convention, but Java enforces it with 'final'
    public static final double PI = 3.14159;
    public static final String APP_NAME = "Constants-Finals Demo";
    public static final int MAX_ATTEMPTS = 3;
    public static final boolean DEBUG_MODE = false;
    
    // Final instance variables - must be initialized in constructor
    private final String applicationId;
    private final int versionNumber;
    
    // Final method parameters - cannot be modified within the method
    public ConstantsFinals(String appId, int version) {
        this.applicationId = appId;  // Can only be set once in constructor
        this.versionNumber = version;
        
        // Demonstrate that final variables cannot be reassigned
        // this.applicationId = "newId"; // This would cause compilation error
    }
    
    /**
     * Demonstrates final method parameters
     * In Python, you can reassign parameters, but in Java 'final' prevents this
     */
    public void processData(final String data, final int count) {
        System.out.println("Processing data: " + data);
        System.out.println("Count: " + count);
        
        // Attempting to modify final parameters would cause compilation error:
        // data = "modified"; // Error: cannot assign to final parameter
        // count = 10;       // Error: cannot assign to final parameter
        
        // However, you can use the parameters normally
        for (int i = 0; i < count; i++) {
            System.out.println("Processing item " + (i + 1));
        }
    }
    
    /**
     * Demonstrates final local variables
     * Once assigned, they cannot be changed
     */
    public void demonstrateFinalLocals() {
        System.out.println("\n=== Final Local Variables ===");
        
        final String message = "Hello from final local variable";
        final int maxSize = 100;
        final double taxRate = 0.08;
        
        System.out.println("Message: " + message);
        System.out.println("Max size: " + maxSize);
        System.out.println("Tax rate: " + taxRate);
        
        // These assignments would cause compilation errors:
        // message = "Modified message"; // Error
        // maxSize = 200;               // Error
        // taxRate = 0.10;              // Error
    }
    
    /**
     * Demonstrates constants in calculations
     */
    public void demonstrateConstants() {
        System.out.println("\n=== Constants in Calculations ===");
        
        double radius = 5.0;
        double area = PI * radius * radius;  // Using the PI constant
        double circumference = 2 * PI * radius;
        
        System.out.println("Radius: " + radius);
        System.out.println("Area: " + area);
        System.out.println("Circumference: " + circumference);
        
        // Using other constants
        System.out.println("App name: " + APP_NAME);
        System.out.println("Max attempts: " + MAX_ATTEMPTS);
        System.out.println("Debug mode: " + DEBUG_MODE);
    }
    
    /**
     * Demonstrates final arrays and objects
     * Note: final arrays can have their contents modified, but not reassigned
     */
    public void demonstrateFinalArrays() {
        System.out.println("\n=== Final Arrays ===");
        
        final int[] numbers = {1, 2, 3, 4, 5};
        final String[] names = {"Alice", "Bob", "Charlie"};
        
        System.out.println("Original numbers array:");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        // You can modify the contents of a final array
        numbers[0] = 10;  // This is allowed
        numbers[1] = 20;  // This is allowed
        
        System.out.println("Modified numbers array:");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        // But you cannot reassign the array reference
        // numbers = new int[]{6, 7, 8}; // This would cause compilation error
        
        System.out.println("Names array:");
        for (String name : names) {
            System.out.print(name + " ");
        }
        System.out.println();
    }
    
    /**
     * Demonstrates naming conventions for constants
     */
    public void demonstrateNamingConventions() {
        System.out.println("\n=== Naming Conventions ===");
        
        // Constants should be UPPER_CASE with underscores
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/mydb";
        final int TIMEOUT_SECONDS = 30;
        final double GRAVITY_ACCELERATION = 9.81;
        
        // Class constants (static final)
        System.out.println("Database URL: " + DATABASE_URL);
        System.out.println("Timeout: " + TIMEOUT_SECONDS + " seconds");
        System.out.println("Gravity: " + GRAVITY_ACCELERATION + " m/s²");
        
        // Method-level constants
        final int BUFFER_SIZE = 1024;
        final String DEFAULT_ENCODING = "UTF-8";
        
        System.out.println("Buffer size: " + BUFFER_SIZE);
        System.out.println("Default encoding: " + DEFAULT_ENCODING);
    }
    
    /**
     * Demonstrates final methods and classes
     */
    public final void demonstrateFinalMethods() {
        System.out.println("\n=== Final Methods ===");
        System.out.println("This method cannot be overridden in subclasses");
        System.out.println("Application ID: " + applicationId);
        System.out.println("Version: " + versionNumber);
    }
    
    /**
     * Demonstrates practical use of constants in a real-world scenario
     */
    public void demonstratePracticalUse() {
        System.out.println("\n=== Practical Example: Shopping Cart ===");
        
        // Constants for a shopping cart system
        final double TAX_RATE = 0.10;
        final double SHIPPING_THRESHOLD = 50.0;
        final double SHIPPING_COST = 5.99;
        final int MAX_ITEMS = 100;
        
        double subtotal = 75.50;
        int itemCount = 3;
        
        // Calculate tax
        double tax = subtotal * TAX_RATE;
        
        // Calculate shipping
        double shipping = (subtotal >= SHIPPING_THRESHOLD) ? 0.0 : SHIPPING_COST;
        
        // Calculate total
        double total = subtotal + tax + shipping;
        
        System.out.println("Subtotal: $" + subtotal);
        System.out.println("Tax (" + (TAX_RATE * 100) + "%): $" + tax);
        System.out.println("Shipping: $" + shipping);
        System.out.println("Total: $" + total);
        System.out.println("Items: " + itemCount + "/" + MAX_ITEMS);
    }
    
    /**
     * Main method to demonstrate all concepts
     */
    public static void main(String[] args) {
        System.out.println("=== Constants and Final Variables in Java ===");
        System.out.println("Demonstrating final variables, constants, and naming conventions\n");
        
        // Create an instance
        ConstantsFinals demo = new ConstantsFinals("CONSTANTS_DEMO", 1);
        
        // Demonstrate various concepts
        demo.demonstrateFinalLocals();
        demo.demonstrateConstants();
        demo.demonstrateFinalArrays();
        demo.demonstrateNamingConventions();
        demo.demonstrateFinalMethods();
        demo.demonstratePracticalUse();
        
        // Demonstrate final method parameters
        demo.processData("Sample data", 3);
        
        System.out.println("\n=== Summary ===");
        System.out.println("✓ Final variables cannot be reassigned after initialization");
        System.out.println("✓ Constants are typically declared as public static final");
        System.out.println("✓ Use UPPER_CASE naming convention for constants");
        System.out.println("✓ Final arrays can have contents modified but not reassigned");
        System.out.println("✓ Final methods cannot be overridden in subclasses");
        System.out.println("✓ Final parameters cannot be modified within the method");
    }
} 