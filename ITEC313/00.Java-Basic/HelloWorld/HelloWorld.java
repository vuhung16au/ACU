/**
 * HelloWorld.java
 * 
 * This program demonstrates basic Java programming concepts:
 * - Class structure and main method
 * - Console output using System.out.println()
 * - String variables and concatenation
 * - Basic commenting practices
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
public class HelloWorld {
    
    /**
     * The main method is the entry point of any Java application.
     * 
     * @param args Command line arguments passed to the program.
     *             In this simple example, we don't use any command line arguments,
     *             but the parameter is required for the main method signature.
     * 
     * Method breakdown:
     * - public: Access modifier that makes this method accessible from anywhere
     * - static: Keyword that allows the method to be called without creating an instance
     * - void: Return type indicating this method doesn't return any value
     * - main: The method name that JVM looks for when starting the application
     * - String[] args: Parameter for command line arguments
     */
    public static void main(String[] args) {
        
        // Print "Hello, World!" to the console
        // System.out.println() is a method that prints text to the standard output
        // followed by a newline character
        System.out.println("Hello, World!");
        
        // Additional demonstration: Print a welcome message
        System.out.println("Welcome to Java programming!");
        
        // Demonstrate string concatenation
        String courseName = "ITEC313";
        String courseTitle = "Object-Oriented Programming";
        System.out.println("Course: " + courseName + " - " + courseTitle);
        
        // Demonstrate basic variable usage and formatting
        int year = 2025;
        System.out.println("Year: " + year);
        
        // Print a separator line for better output formatting
        System.out.println("=====================================");
        System.out.println("This program demonstrates:");
        System.out.println("1. Basic Java class structure");
        System.out.println("2. Main method implementation");
        System.out.println("3. Console output using System.out.println()");
        System.out.println("4. String variables and concatenation");
        System.out.println("5. Basic commenting practices");
        System.out.println("Refer to the github repo for more examples and the learning path: https://github.com/vuhung16au/ACU/tree/main/ITEC313/00.Java-Basic");
        System.out.println("=====================================");
    }
}