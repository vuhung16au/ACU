/**
 * Example1.java - Basic Scanner Input Examples
 * 
 * This example demonstrates fundamental input techniques using the Scanner class.
 * It shows how to read different data types and handle basic input validation.
 * 
 * Key differences from Python:
 * - Java requires explicit type conversion (no automatic inference)
 * - Scanner methods are type-specific (nextInt(), nextDouble(), etc.)
 * - Must handle the newline character after reading numbers
 * 
 * Course: ITEC313 - Object-Oriented Programming
 * Institution: Australian Catholic University (ACU)
 * Date: July 11, 2025
 */

import java.util.Scanner;

public class Example1 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic Scanner Input ===\n");
        
        // Create a Scanner object for reading input
        // In Python: input() function
        // In Java: Scanner class with System.in
        Scanner scanner = new Scanner(System.in);
        
        // Example 1: Reading a string
        System.out.println("1. READING STRINGS");
        System.out.println("==================");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine(); // Reads entire line including spaces
        System.out.printf("Hello, %s!%n", name);
        
        // Example 2: Reading an integer
        System.out.println("\n2. READING INTEGERS");
        System.out.println("===================");
        System.out.print("Enter your age: ");
        
        // Input validation - ensure we get a valid integer
        // In Python: try/except with int(input())
        // In Java: hasNextInt() to check before reading
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid integer for age: ");
            scanner.next(); // Consume the invalid input
        }
        int age = scanner.nextInt();
        scanner.nextLine(); // Important: consume the newline character
        System.out.printf("You are %d years old.%n", age);
        
        // Example 3: Reading a double
        System.out.println("\n3. READING DECIMAL NUMBERS");
        System.out.println("==========================");
        System.out.print("Enter your height in meters: ");
        
        while (!scanner.hasNextDouble()) {
            System.out.print("Please enter a valid decimal number: ");
            scanner.next();
        }
        double height = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.printf("Your height is %.2f meters.%n", height);
        
        // Example 4: Reading a boolean
        System.out.println("\n4. READING BOOLEAN VALUES");
        System.out.println("=========================");
        System.out.print("Are you a student? (true/false): ");
        
        while (!scanner.hasNextBoolean()) {
            System.out.print("Please enter true or false: ");
            scanner.next();
        }
        boolean isStudent = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline
        System.out.printf("Student status: %b%n", isStudent);
        
        // Example 5: Reading a single character
        System.out.println("\n5. READING CHARACTERS");
        System.out.println("=====================");
        System.out.print("Enter a single character: ");
        String charInput = scanner.nextLine();
        char character = charInput.length() > 0 ? charInput.charAt(0) : ' ';
        System.out.printf("You entered: '%c' (ASCII: %d)%n", character, (int)character);
        
        // Example 6: Reading multiple values on one line
        System.out.println("\n6. READING MULTIPLE VALUES");
        System.out.println("=========================");
        System.out.print("Enter three numbers separated by spaces: ");
        
        int num1 = 0, num2 = 0, num3 = 0;
        if (scanner.hasNextInt()) {
            num1 = scanner.nextInt();
        }
        if (scanner.hasNextInt()) {
            num2 = scanner.nextInt();
        }
        if (scanner.hasNextInt()) {
            num3 = scanner.nextInt();
        }
        scanner.nextLine(); // Consume any remaining input
        
        System.out.printf("Numbers entered: %d, %d, %d%n", num1, num2, num3);
        System.out.printf("Sum: %d%n", num1 + num2 + num3);
        System.out.printf("Average: %.2f%n", (num1 + num2 + num3) / 3.0);
        
        // Example 7: Input with default values
        System.out.println("\n7. INPUT WITH DEFAULTS");
        System.out.println("======================");
        System.out.print("Enter your favorite color (or press Enter for default): ");
        String color = scanner.nextLine();
        
        // In Python: color = input() or "blue"
        // In Java: check if empty and provide default
        if (color.trim().isEmpty()) {
            color = "blue"; // Default value
            System.out.println("Using default color: blue");
        }
        System.out.printf("Your favorite color is: %s%n", color);
        
        // Example 8: Input validation with range checking
        System.out.println("\n8. INPUT VALIDATION WITH RANGE");
        System.out.println("==============================");
        System.out.print("Enter a number between 1 and 100: ");
        
        int validNumber = getNumberInRange(scanner, 1, 100);
        System.out.printf("Valid number entered: %d%n", validNumber);
        
        // Display summary
        System.out.println("\n=== SUMMARY ===");
        System.out.printf("Name: %s%n", name);
        System.out.printf("Age: %d%n", age);
        System.out.printf("Height: %.2f meters%n", height);
        System.out.printf("Student: %b%n", isStudent);
        System.out.printf("Character: '%c'%n", character);
        System.out.printf("Numbers: %d, %d, %d%n", num1, num2, num3);
        System.out.printf("Color: %s%n", color);
        System.out.printf("Range number: %d%n", validNumber);
        
        // Close the scanner
        scanner.close();
        
        System.out.println("\n=== Example 1 Complete ===");
    }
    
    /**
     * Helper method to get a number within a specified range
     * Demonstrates input validation techniques
     * 
     * @param scanner The Scanner object for input
     * @param min Minimum allowed value
     * @param max Maximum allowed value
     * @return Valid number within the specified range
     */
    public static int getNumberInRange(Scanner scanner, int min, int max) {
        int number;
        do {
            // Ensure we have a valid integer
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid integer: ");
                scanner.next();
            }
            
            number = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            // Check if number is within range
            if (number < min || number > max) {
                System.out.printf("Please enter a number between %d and %d: ", min, max);
            }
        } while (number < min || number > max);
        
        return number;
    }
} 