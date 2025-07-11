/**
 * Input and Output Demo
 * 
 * This program demonstrates various methods of input and output in Java:
 * - Console input using Scanner class
 * - BufferedReader for efficient input
 * - Command-line arguments processing
 * - Formatted output with System.out.printf
 * - Error handling for input operations
 * 
 * Course: ITEC313 - Object-Oriented Programming
 * Institution: Australian Catholic University (ACU)
 * Date: July 11, 2025
 */

import java.io.*;
import java.util.Scanner;

public class InputOutput {
    
    public static void main(String[] args) {
        System.out.println("=== Java Input and Output Demo ===\n");
        
        // Process command-line arguments first
        processCommandLineArguments(args);
        
        // Create a Scanner for user input
        Scanner scanner = new Scanner(System.in);
        
        // Demonstrate different input methods
        demonstrateBasicInput(scanner);
        demonstrateDataTypeInput(scanner);
        demonstrateFormattedOutput();
        demonstrateBufferedReader();
        demonstrateErrorHandling(scanner);
        
        // Close the scanner
        scanner.close();
        
        System.out.println("\n=== Input/Output Demo Complete ===");
    }
    
    /**
     * Process and display command-line arguments
     */
    public static void processCommandLineArguments(String[] args) {
        System.out.println("1. COMMAND-LINE ARGUMENTS");
        System.out.println("=========================");
        
        if (args.length == 0) {
            System.out.println("No command-line arguments provided.");
            System.out.println("Try running: java InputOutput arg1 arg2 arg3");
        } else {
            System.out.printf("Number of arguments: %d%n", args.length);
            System.out.println("Arguments received:");
            for (int i = 0; i < args.length; i++) {
                System.out.printf("  args[%d] = \"%s\"%n", i, args[i]);
            }
            
            // Demonstrate argument processing
            System.out.println("\nArgument processing examples:");
            for (String arg : args) {
                if (isNumeric(arg)) {
                    try {
                        double value = Double.parseDouble(arg);
                        System.out.printf("  \"%s\" is a number: %.2f%n", arg, value);
                    } catch (NumberFormatException e) {
                        System.out.printf("  \"%s\" looks numeric but failed to parse%n", arg);
                    }
                } else {
                    System.out.printf("  \"%s\" is text (length: %d)%n", arg, arg.length());
                }
            }
        }
        System.out.println();
    }
    
    /**
     * Demonstrate basic input operations with Scanner
     */
    public static void demonstrateBasicInput(Scanner scanner) {
        System.out.println("2. BASIC INPUT WITH SCANNER");
        System.out.println("===========================");
        
        try {
            // String input
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.printf("Hello, %s!%n", name);
            
            // Age input with validation
            System.out.print("Enter your age: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid integer for age: ");
                scanner.next(); // Consume invalid input
            }
            int age = scanner.nextInt();
            scanner.nextLine(); // Consume the newline
            
            // City input
            System.out.print("Enter your city: ");
            String city = scanner.nextLine();
            
            // Display collected information
            System.out.println("\n--- Your Information ---");
            System.out.printf("Name: %s%n", name);
            System.out.printf("Age: %d years old%n", age);
            System.out.printf("City: %s%n", city);
            
            // Calculate birth year (approximate)
            int currentYear = 2025;
            int birthYear = currentYear - age;
            System.out.printf("Approximate birth year: %d%n", birthYear);
            
        } catch (Exception e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrate input for different data types
     */
    public static void demonstrateDataTypeInput(Scanner scanner) {
        System.out.println("3. DATA TYPE INPUT");
        System.out.println("==================");
        
        try {
            // Integer input
            System.out.print("Enter an integer: ");
            int intValue = getValidInt(scanner);
            
            // Double input
            System.out.print("Enter a decimal number: ");
            double doubleValue = getValidDouble(scanner);
            
            // Boolean input
            System.out.print("Enter true or false: ");
            boolean boolValue = getValidBoolean(scanner);
            
            // Character input
            System.out.print("Enter a single character: ");
            char charValue = getValidChar(scanner);
            
            // Display results
            System.out.println("\n--- Data Type Results ---");
            System.out.printf("Integer: %d (binary: %s)%n", 
                            intValue, Integer.toBinaryString(intValue));
            System.out.printf("Double: %.6f (scientific: %.2e)%n", 
                            doubleValue, doubleValue);
            System.out.printf("Boolean: %b (opposite: %b)%n", 
                            boolValue, !boolValue);
            System.out.printf("Character: '%c' (ASCII: %d)%n", 
                            charValue, (int)charValue);
            
            // Perform some calculations
            System.out.println("\n--- Calculations ---");
            System.out.printf("Integer × 2 = %d%n", intValue * 2);
            System.out.printf("Double ÷ 2 = %.3f%n", doubleValue / 2);
            System.out.printf("Character + 1 = '%c'%n", (char)(charValue + 1));
            
        } catch (Exception e) {
            System.err.println("Error processing data types: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrate formatted output capabilities
     */
    public static void demonstrateFormattedOutput() {
        System.out.println("4. FORMATTED OUTPUT");
        System.out.println("===================");
        
        // Various printf formatting examples
        String name = "Alice";
        int age = 25;
        double salary = 75000.50;
        boolean isEmployed = true;
        
        System.out.println("Basic formatting:");
        System.out.printf("Name: %s%n", name);
        System.out.printf("Age: %d%n", age);
        System.out.printf("Salary: $%.2f%n", salary);
        System.out.printf("Employed: %b%n", isEmployed);
        
        System.out.println("\nAdvanced formatting:");
        System.out.printf("%-15s: %s%n", "Name", name);
        System.out.printf("%-15s: %05d%n", "Age (padded)", age);
        System.out.printf("%-15s: $%,15.2f%n", "Salary", salary);
        System.out.printf("%-15s: %B%n", "Employed (upper)", isEmployed);
        
        System.out.println("\nNumeric formatting:");
        int number = 42;
        System.out.printf("Decimal: %d%n", number);
        System.out.printf("Hexadecimal: %x%n", number);
        System.out.printf("Octal: %o%n", number);
        System.out.printf("Binary: %s%n", Integer.toBinaryString(number));
        
        System.out.println("\nFloating-point formatting:");
        double pi = Math.PI;
        System.out.printf("Default: %f%n", pi);
        System.out.printf("2 decimals: %.2f%n", pi);
        System.out.printf("Scientific: %e%n", pi);
        System.out.printf("General: %g%n", pi);
        System.out.printf("Shortest: %g%n", 1234.0);
        
        System.out.println("\nDate and time formatting:");
        java.util.Date now = new java.util.Date();
        System.out.printf("Current date: %tF%n", now);
        System.out.printf("Current time: %tT%n", now);
        System.out.printf("Day of week: %tA%n", now);
        System.out.printf("Month: %tB%n", now);
        
        System.out.println();
    }
    
    /**
     * Demonstrate BufferedReader for efficient input
     */
    public static void demonstrateBufferedReader() {
        System.out.println("5. BUFFERED READER INPUT");
        System.out.println("========================");
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            System.out.print("Enter a sentence (BufferedReader): ");
            String sentence = reader.readLine();
            
            if (sentence != null && !sentence.trim().isEmpty()) {
                System.out.println("\n--- Sentence Analysis ---");
                System.out.printf("Length: %d characters%n", sentence.length());
                System.out.printf("Words: %d%n", sentence.trim().split("\\s+").length);
                System.out.printf("Uppercase: %s%n", sentence.toUpperCase());
                System.out.printf("Lowercase: %s%n", sentence.toLowerCase());
                
                // Count vowels
                int vowels = 0;
                String vowelChars = "aeiouAEIOU";
                for (char c : sentence.toCharArray()) {
                    if (vowelChars.indexOf(c) != -1) {
                        vowels++;
                    }
                }
                System.out.printf("Vowels: %d%n", vowels);
            } else {
                System.out.println("No sentence entered.");
            }
            
        } catch (IOException e) {
            System.err.println("Error reading with BufferedReader: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrate error handling for input operations
     */
    public static void demonstrateErrorHandling(Scanner scanner) {
        System.out.println("6. INPUT ERROR HANDLING");
        System.out.println("=======================");
        
        System.out.println("Testing input validation and error recovery...");
        
        // Test integer input with error handling
        System.out.print("Enter a number between 1 and 100: ");
        int validNumber = getNumberInRange(scanner, 1, 100);
        System.out.printf("Valid number entered: %d%n", validNumber);
        
        // Test division by zero handling
        System.out.print("Enter a divisor (try entering 0): ");
        int divisor = getValidInt(scanner);
        
        try {
            int dividend = 42;
            if (divisor == 0) {
                throw new ArithmeticException("Division by zero");
            }
            double result = (double) dividend / divisor;
            System.out.printf("42 ÷ %d = %.3f%n", divisor, result);
        } catch (ArithmeticException e) {
            System.err.println("Error: " + e.getMessage());
            System.out.println("Division by zero is not allowed!");
        }
        
        System.out.println();
    }
    
    // Helper methods for input validation
    
    public static int getValidInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter an integer: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return value;
    }
    
    public static double getValidDouble(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        return value;
    }
    
    public static boolean getValidBoolean(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().toLowerCase().trim();
            if (input.equals("true") || input.equals("t") || input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("false") || input.equals("f") || input.equals("no") || input.equals("n")) {
                return false;
            } else {
                System.out.print("Please enter true/false (or t/f, yes/no, y/n): ");
            }
        }
    }
    
    public static char getValidChar(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            if (input.length() == 1) {
                return input.charAt(0);
            } else {
                System.out.print("Please enter exactly one character: ");
            }
        }
    }
    
    public static int getNumberInRange(Scanner scanner, int min, int max) {
        while (true) {
            int value = getValidInt(scanner);
            if (value >= min && value <= max) {
                return value;
            } else {
                System.out.printf("Number must be between %d and %d. Try again: ", min, max);
            }
        }
    }
    
    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
