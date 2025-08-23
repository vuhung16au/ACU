package com.acu.genericcli.generics;

/**
 * Main launcher class for the CLI Generics Demo application.
 * This class serves as the entry point for the CLI application.
 */
public class Launcher {

    public static void main(String[] args) {
        System.out.println("=== Java Generics CLI Demo ===\n");
        
        System.out.println("Running all demonstrations automatically...\n");
        
        // Run all demos in sequence
        runAllDemos();
        
        System.out.println("\n=== All demonstrations completed ===");
    }
    
    private static void runAllDemos() {
        System.out.println("1. Generic Stack Demo:");
        System.out.println("=".repeat(50));
        GenericStackDemo.runDemo();
        
        System.out.println("\n2. Wildcard Need Demo:");
        System.out.println("=".repeat(50));
        WildCardNeedDemo.runDemo();
        
        System.out.println("\n3. Any Wildcard Demo:");
        System.out.println("=".repeat(50));
        AnyWildCardDemo.runDemo();
        
        System.out.println("\n4. Super Wildcard Demo:");
        System.out.println("=".repeat(50));
        SuperWildCardDemo.runDemo();
        
        System.out.println("\n5. ArrayList with Generics Demo:");
        System.out.println("=".repeat(50));
        TestArrayListNew.runDemo();
        
        System.out.println("\n6. Integer Matrix Demo:");
        System.out.println("=".repeat(50));
        TestIntegerMatrix.runDemo();
        
        System.out.println("\n7. Rational Matrix Demo:");
        System.out.println("=".repeat(50));
        TestRationalMatrix.runDemo();
    }
} 