package com.acu.javafx.generics;

import javafx.scene.control.TextArea;

/**
 * Demonstration of the GenericStack class.
 * Shows how generics allow type-safe operations with different data types.
 */
public class GenericStackDemo {
    
    /**
     * Runs the GenericStack demonstration and outputs results to the provided TextArea.
     * @param outputArea the TextArea to display the output
     */
    public static void runDemo(TextArea outputArea) {
        StringBuilder output = new StringBuilder();
        
        output.append("=== Generic Stack Demonstration ===\n\n");
        
        // Demonstrate with String stack
        output.append("1. String Stack Demo:\n");
        GenericStack<String> stringStack = new GenericStack<>();
        stringStack.push("Java");
        stringStack.push("Python");
        stringStack.push("C++");
        
        output.append("Initial stack: ").append(stringStack).append("\n");
        output.append("Stack size: ").append(stringStack.getSize()).append("\n");
        output.append("Top element: ").append(stringStack.peek()).append("\n");
        
        output.append("Popping elements:\n");
        while (!stringStack.isEmpty()) {
            output.append("Popped: ").append(stringStack.pop()).append("\n");
        }
        output.append("Stack after popping all elements: ").append(stringStack).append("\n\n");
        
        // Demonstrate with Integer stack
        output.append("2. Integer Stack Demo:\n");
        GenericStack<Integer> intStack = new GenericStack<>();
        intStack.push(10);
        intStack.push(20);
        intStack.push(30);
        intStack.push(40);
        
        output.append("Initial stack: ").append(intStack).append("\n");
        output.append("Stack size: ").append(intStack.getSize()).append("\n");
        output.append("Top element: ").append(intStack.peek()).append("\n");
        
        output.append("Popping elements:\n");
        while (!intStack.isEmpty()) {
            output.append("Popped: ").append(intStack.pop()).append("\n");
        }
        output.append("Stack after popping all elements: ").append(intStack).append("\n\n");
        
        // Demonstrate with Double stack
        output.append("3. Double Stack Demo:\n");
        GenericStack<Double> doubleStack = new GenericStack<>();
        doubleStack.push(3.14);
        doubleStack.push(2.718);
        doubleStack.push(1.414);
        
        output.append("Initial stack: ").append(doubleStack).append("\n");
        output.append("Stack size: ").append(doubleStack.getSize()).append("\n");
        output.append("Top element: ").append(doubleStack.peek()).append("\n");
        
        output.append("Popping elements:\n");
        while (!doubleStack.isEmpty()) {
            output.append("Popped: ").append(doubleStack.pop()).append("\n");
        }
        output.append("Stack after popping all elements: ").append(doubleStack).append("\n\n");
        
        output.append("=== Key Benefits of Generics ===\n");
        output.append("1. Type Safety: Compile-time type checking\n");
        output.append("2. No Casting: No need for explicit type casting\n");
        output.append("3. Code Reuse: Same implementation works with different types\n");
        output.append("4. Performance: No runtime type checking overhead\n");
        
        outputArea.setText(output.toString());
    }
} 