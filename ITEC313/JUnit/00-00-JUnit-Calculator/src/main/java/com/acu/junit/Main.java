package com.acu.junit;

/**
 * Main class to demonstrate the Calculator functionality.
 */
public class Main {
    
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        
        System.out.println("=== JUnit 5 Calculator Demo ===");
        System.out.println();
        
        // Demonstrate addition
        System.out.println("Addition:");
        System.out.println("2 + 3 = " + calculator.add(2, 3));
        System.out.println("5 + (-3) = " + calculator.add(5, -3));
        System.out.println();
        
        // Demonstrate subtraction
        System.out.println("Subtraction:");
        System.out.println("10 - 4 = " + calculator.subtract(10, 4));
        System.out.println("3 - 7 = " + calculator.subtract(3, 7));
        System.out.println();
        
        // Demonstrate multiplication
        System.out.println("Multiplication:");
        System.out.println("6 * 7 = " + calculator.multiply(6, 7));
        System.out.println("(-4) * 5 = " + calculator.multiply(-4, 5));
        System.out.println();
        
        // Demonstrate division
        System.out.println("Division:");
        System.out.println("15 / 3 = " + calculator.divide(15, 3));
        System.out.println("10 / 4 = " + calculator.divide(10, 4));
        System.out.println();
        
        // Demonstrate division by zero exception
        System.out.println("Division by zero:");
        try {
            calculator.divide(5, 0);
        } catch (ArithmeticException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
        
        System.out.println();
        System.out.println("Run 'mvn test' to execute the JUnit 5 tests!");
    }
}
