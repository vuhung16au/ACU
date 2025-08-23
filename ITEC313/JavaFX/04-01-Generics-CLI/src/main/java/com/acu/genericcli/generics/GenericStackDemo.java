package com.acu.genericcli.generics;

/**
 * Demonstration of the GenericStack class.
 * Shows how generics allow type-safe operations with different data types.
 */
public class GenericStackDemo {
    
    /**
     * Runs the GenericStack demonstration and outputs results to console.
     */
    public static void runDemo() {
        System.out.println("=== Generic Stack Demonstration ===\n");
        
        // Demonstrate with String stack
        System.out.println("1. String Stack Demo:");
        GenericStack<String> stringStack = new GenericStack<>();
        stringStack.push("Java");
        stringStack.push("Python");
        stringStack.push("C++");
        
        System.out.println("Initial stack: " + stringStack);
        System.out.println("Stack size: " + stringStack.getSize());
        System.out.println("Top element: " + stringStack.peek());
        
        System.out.println("Popping elements:");
        while (!stringStack.isEmpty()) {
            System.out.println("Popped: " + stringStack.pop());
        }
        System.out.println("Stack after popping all elements: " + stringStack + "\n");
        
        // Demonstrate with Integer stack
        System.out.println("2. Integer Stack Demo:");
        GenericStack<Integer> intStack = new GenericStack<>();
        intStack.push(10);
        intStack.push(20);
        intStack.push(30);
        intStack.push(40);
        
        System.out.println("Initial stack: " + intStack);
        System.out.println("Stack size: " + intStack.getSize());
        System.out.println("Top element: " + intStack.peek());
        
        System.out.println("Popping elements:");
        while (!intStack.isEmpty()) {
            System.out.println("Popped: " + intStack.pop());
        }
        System.out.println("Stack after popping all elements: " + intStack + "\n");
        
        // Demonstrate with Double stack
        System.out.println("3. Double Stack Demo:");
        GenericStack<Double> doubleStack = new GenericStack<>();
        doubleStack.push(3.14);
        doubleStack.push(2.718);
        doubleStack.push(1.414);
        
        System.out.println("Initial stack: " + doubleStack);
        System.out.println("Stack size: " + doubleStack.getSize());
        System.out.println("Top element: " + doubleStack.peek());
        
        System.out.println("Popping elements:");
        while (!doubleStack.isEmpty()) {
            System.out.println("Popped: " + doubleStack.pop());
        }
        System.out.println("Stack after popping all elements: " + doubleStack + "\n");
        
        System.out.println("=== Key Benefits of Generics ===");
        System.out.println("1. Type Safety: Compile-time type checking");
        System.out.println("2. No Casting: No need for explicit type casting");
        System.out.println("3. Code Reuse: Same implementation works with different types");
        System.out.println("4. Performance: No runtime type checking overhead");
    }
} 