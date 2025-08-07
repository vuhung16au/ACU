/**
 * InputOutput.java
 * 
 * This program demonstrates basic input and output operations in Java:
 * - Console input using Scanner
 * - Basic data type input
 * - Formatted output
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
import java.util.Scanner;

public class InputOutput {
    
    public static void main(String[] args) {
        System.out.println("=== Java Input and Output Demo ===\n");
        
        // Create a Scanner for user input
        Scanner scanner = new Scanner(System.in);
        
        // Demonstrate basic input
        demonstrateBasicInput(scanner);
        
        // Demonstrate formatted output
        demonstrateFormattedOutput();
        
        // Close the scanner
        scanner.close();
        
        System.out.println("\n=== Input/Output Demo Complete ===");
    }
    
    /**
     * Demonstrate basic input operations with Scanner
     */
    public static void demonstrateBasicInput(Scanner scanner) {
        System.out.println("1. BASIC INPUT WITH SCANNER");
        System.out.println("===========================");
        
        try {
            // String input
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.printf("Hello, %s!%n", name);
            
            // Integer input
            System.out.print("Enter your age: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid integer for age: ");
                scanner.next(); // Consume invalid input
            }
            int age = scanner.nextInt();
            scanner.nextLine(); // Consume the newline
            
            // Double input
            System.out.print("Enter your height (in meters): ");
            while (!scanner.hasNextDouble()) {
                System.out.print("Please enter a valid number: ");
                scanner.next();
            }
            double height = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline
            
            // Display collected information
            System.out.println("\n--- Your Information ---");
            System.out.printf("Name: %s%n", name);
            System.out.printf("Age: %d years old%n", age);
            System.out.printf("Height: %.2f meters%n", height);
            
        } catch (Exception e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrate formatted output capabilities
     */
    public static void demonstrateFormattedOutput() {
        System.out.println("2. FORMATTED OUTPUT");
        System.out.println("===================");
        
        // Basic formatting examples
        String name = "Alice";
        int age = 25;
        double salary = 75000.50;
        
        System.out.println("Basic formatting:");
        System.out.printf("Name: %s%n", name);
        System.out.printf("Age: %d%n", age);
        System.out.printf("Salary: $%.2f%n", salary);
        
        System.out.println("\nAdvanced formatting:");
        System.out.printf("%-15s: %s%n", "Name", name);
        System.out.printf("%-15s: %05d%n", "Age (padded)", age);
        System.out.printf("%-15s: $%,15.2f%n", "Salary", salary);
        
        System.out.println("\nNumeric formatting:");
        int number = 42;
        System.out.printf("Decimal: %d%n", number);
        System.out.printf("Hexadecimal: %x%n", number);
        System.out.printf("Binary: %s%n", Integer.toBinaryString(number));
        
        System.out.println("\nFloating-point formatting:");
        double pi = Math.PI;
        System.out.printf("Default: %f%n", pi);
        System.out.printf("2 decimals: %.2f%n", pi);
        System.out.printf("Scientific: %e%n", pi);
        
        System.out.println();
    }
}
