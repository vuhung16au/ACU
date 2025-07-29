/**
 * Example2.java
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
public class Example2 {
    
    // Constants for configuration
    public static final int ARRAY_SIZE = 5;
    public static final String DEFAULT_NAME = "Unknown";
    public static final double DISCOUNT_RATE = 0.10;
    
    // Final instance variables
    private final int[] scores;
    private final String[] names;
    private final StringBuilder log;
    
    public Example2() {
        // Initialize final arrays
        this.scores = new int[ARRAY_SIZE];
        this.names = new String[ARRAY_SIZE];
        this.log = new StringBuilder();
        
        // Initialize arrays with default values
        for (int i = 0; i < ARRAY_SIZE; i++) {
            scores[i] = 0;
            names[i] = DEFAULT_NAME;
        }
    }
    
    /**
     * Demonstrates final arrays behavior
     * Note: You can modify array contents but not reassign the array reference
     */
    public void demonstrateFinalArrays() {
        System.out.println("=== Final Arrays Behavior ===");
        
        // Show initial state
        System.out.println("Initial scores:");
        for (int i = 0; i < scores.length; i++) {
            System.out.print(scores[i] + " ");
        }
        System.out.println();
        
        // Modify array contents (this is allowed)
        scores[0] = 95;
        scores[1] = 87;
        scores[2] = 92;
        scores[3] = 78;
        scores[4] = 89;
        
        System.out.println("Modified scores:");
        for (int i = 0; i < scores.length; i++) {
            System.out.print(scores[i] + " ");
        }
        System.out.println();
        
        // This would cause compilation error:
        // scores = new int[10]; // Error: cannot assign to final variable
        
        // Modify names array
        names[0] = "Alice";
        names[1] = "Bob";
        names[2] = "Charlie";
        names[3] = "Diana";
        names[4] = "Eve";
        
        System.out.println("Names:");
        for (String name : names) {
            System.out.print(name + " ");
        }
        System.out.println();
        
        System.out.println("✓ Final arrays can have contents modified");
        System.out.println("✓ Final arrays cannot be reassigned");
    }
    
    /**
     * Demonstrates final objects behavior
     * Note: You can modify object state but not reassign the reference
     */
    public void demonstrateFinalObjects() {
        System.out.println("\n=== Final Objects Behavior ===");
        
        // Final StringBuilder - can modify content but not reassign
        System.out.println("Initial log: '" + log.toString() + "'");
        
        // Modify the StringBuilder content (allowed)
        log.append("Application started\n");
        log.append("Processing data...\n");
        log.append("Operation completed\n");
        
        System.out.println("Modified log:");
        System.out.println(log.toString());
        
        // This would cause compilation error:
        // log = new StringBuilder(); // Error: cannot assign to final variable
        
        // But you can still modify the object's state
        log.setLength(0);  // Clear the StringBuilder
        log.append("New log entry\n");
        
        System.out.println("After clearing and adding new entry:");
        System.out.println(log.toString());
        
        System.out.println("✓ Final objects can have state modified");
        System.out.println("✓ Final objects cannot be reassigned");
    }
    
    /**
     * Demonstrates final methods
     * Final methods cannot be overridden in subclasses
     */
    public final void demonstrateFinalMethods() {
        System.out.println("\n=== Final Methods ===");
        System.out.println("This method cannot be overridden in subclasses");
        
        // Calculate average score
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }
        double average = (double) sum / scores.length;
        
        System.out.println("Average score: " + average);
        System.out.println("✓ Final methods provide compile-time guarantees");
    }
    
    /**
     * Demonstrates final parameters with complex objects
     */
    public void processStudentData(final String[] studentNames, final int[] studentScores) {
        System.out.println("\n=== Final Parameters with Arrays ===");
        
        System.out.println("Processing student data:");
        for (int i = 0; i < studentNames.length; i++) {
            System.out.println("Student: " + studentNames[i] + ", Score: " + studentScores[i]);
        }
        
        // You can modify the contents of final array parameters
        studentScores[0] = 100;  // This is allowed
        studentNames[0] = "Updated Name";  // This is allowed
        
        System.out.println("After modification:");
        for (int i = 0; i < studentNames.length; i++) {
            System.out.println("Student: " + studentNames[i] + ", Score: " + studentScores[i]);
        }
        
        // But you cannot reassign the array references
        // studentNames = new String[5];  // Error: cannot assign to final parameter
        // studentScores = new int[5];    // Error: cannot assign to final parameter
    }
    
    /**
     * Demonstrates constants in complex calculations
     */
    public void demonstrateComplexCalculations() {
        System.out.println("\n=== Complex Calculations with Constants ===");
        
        // Constants for calculations
        final double TAX_RATE = 0.15;
        final double SHIPPING_COST = 5.99;
        final double MINIMUM_ORDER = 25.0;
        
        // Simulate order processing
        double[] orderAmounts = {15.0, 30.0, 50.0, 75.0, 100.0};
        
        System.out.println("Order Processing with Constants:");
        for (int i = 0; i < orderAmounts.length; i++) {
            double subtotal = orderAmounts[i];
            double tax = subtotal * TAX_RATE;
            double shipping = (subtotal >= MINIMUM_ORDER) ? 0.0 : SHIPPING_COST;
            double total = subtotal + tax + shipping;
            
            System.out.printf("Order %d: Subtotal=$%.2f, Tax=$%.2f, Shipping=$%.2f, Total=$%.2f%n",
                            i + 1, subtotal, tax, shipping, total);
        }
    }
    
    /**
     * Demonstrates final variables in loops and conditionals
     */
    public void demonstrateFinalInControlStructures() {
        System.out.println("\n=== Final Variables in Control Structures ===");
        
        // Final variables in loops
        final int MAX_ITERATIONS = 3;
        final String SEPARATOR = "-------------------";
        
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            System.out.println("Iteration " + (i + 1));
            System.out.println(SEPARATOR);
            
            // Final variable inside loop
            final String message = "Processing step " + (i + 1);
            System.out.println(message);
            
            // This would cause compilation error:
            // message = "Modified message"; // Error: cannot assign to final variable
        }
        
        // Final variables in conditionals
        final boolean DEBUG_MODE = true;
        final String LOG_LEVEL = DEBUG_MODE ? "DEBUG" : "INFO";
        
        System.out.println("Log level: " + LOG_LEVEL);
        
        if (DEBUG_MODE) {
            final String debugMessage = "Debug information available";
            System.out.println(debugMessage);
        }
    }
    
    /**
     * Main method to run the example
     */
    public static void main(String[] args) {
        System.out.println("Example 2: Advanced Final Variables and Objects");
        System.out.println("===============================================\n");
        
        Example2 example = new Example2();
        
        example.demonstrateFinalArrays();
        example.demonstrateFinalObjects();
        example.demonstrateFinalMethods();
        example.demonstrateComplexCalculations();
        example.demonstrateFinalInControlStructures();
        
        // Demonstrate final parameters
        String[] testNames = {"John", "Jane", "Mike"};
        int[] testScores = {85, 92, 78};
        example.processStudentData(testNames, testScores);
        
        System.out.println("\n=== Advanced Key Takeaways ===");
        System.out.println("✓ Final arrays can have contents modified but not reassigned");
        System.out.println("✓ Final objects can have state modified but not reassigned");
        System.out.println("✓ Final methods cannot be overridden in subclasses");
        System.out.println("✓ Final parameters provide compile-time guarantees");
        System.out.println("✓ Constants make code more maintainable and readable");
    }
} 