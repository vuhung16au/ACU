/**
 * HelloWorld.java
 * A simple Java program that demonstrates basic Java syntax
 * 
 * @author Your Name
 * @version 1.0
 * @since 2025-07-11
 */
public class HelloWorld {
    
    /**
     * Main method - entry point of the Java application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Print a welcome message
        System.out.println("Hello, World!");
        System.out.println("Welcome to Java Programming!");
        
        // Demonstrate variables
        String name = "Java Developer";
        int year = 2025;
        double version = 21.0;
        
        // Print formatted output
        System.out.println("Hello, " + name + "!");
        System.out.println("Current year: " + year);
        System.out.println("Java version: " + version);
        
        // Demonstrate basic operations
        int a = 10;
        int b = 20;
        int sum = a + b;
        
        System.out.println(a + " + " + b + " = " + sum);
        
        // Demonstrate conditional statements
        if (sum > 25) {
            System.out.println("Sum is greater than 25");
        } else {
            System.out.println("Sum is 25 or less");
        }
        
        // Demonstrate loops
        System.out.println("Counting from 1 to 5:");
        for (int i = 1; i <= 5; i++) {
            System.out.println("Count: " + i);
        }
        
        System.out.println("Program execution completed successfully!");
    }
}
