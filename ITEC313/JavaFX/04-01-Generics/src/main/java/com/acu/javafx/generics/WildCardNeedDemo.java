package com.acu.javafx.generics;

import javafx.scene.control.TextArea;
import java.util.ArrayList;
import java.util.List;

/**
 * Demonstration of why wildcards are needed in generic programming.
 * Shows the limitations of generic types without wildcards.
 */
public class WildCardNeedDemo {
    
    /**
     * Runs the wildcard need demonstration and outputs results to the provided TextArea.
     * @param outputArea the TextArea to display the output
     */
    public static void runDemo(TextArea outputArea) {
        StringBuilder output = new StringBuilder();
        
        output.append("=== Wildcard Need Demonstration ===\n\n");
        
        // Create lists of different types
        List<Integer> intList = new ArrayList<>();
        intList.add(1);
        intList.add(2);
        intList.add(3);
        
        List<Double> doubleList = new ArrayList<>();
        doubleList.add(1.1);
        doubleList.add(2.2);
        doubleList.add(3.3);
        
        List<String> stringList = new ArrayList<>();
        stringList.add("Hello");
        stringList.add("World");
        stringList.add("Java");
        
        output.append("Created lists:\n");
        output.append("Integer list: ").append(intList).append("\n");
        output.append("Double list: ").append(doubleList).append("\n");
        output.append("String list: ").append(stringList).append("\n\n");
        
        // Demonstrate the problem without wildcards
        output.append("=== Problem: Without Wildcards ===\n");
        output.append("If we have a method that takes List<Number>:\n");
        output.append("public static double sum(List<Number> list) { ... }\n\n");
        
        output.append("This method CANNOT accept:\n");
        output.append("- List<Integer> (even though Integer extends Number)\n");
        output.append("- List<Double> (even though Double extends Number)\n");
        output.append("- List<String> (String does not extend Number)\n\n");
        
        output.append("This is because generics are INVARIANT by default.\n");
        output.append("List<Integer> is NOT a subtype of List<Number>\n\n");
        
        // Demonstrate the solution with wildcards
        output.append("=== Solution: With Wildcards ===\n");
        output.append("Using wildcards allows flexibility:\n");
        output.append("public static double sum(List<? extends Number> list) { ... }\n\n");
        
        output.append("This method CAN accept:\n");
        output.append("- List<Integer> (Integer extends Number)\n");
        output.append("- List<Double> (Double extends Number)\n");
        output.append("- List<Number> (Number is Number)\n\n");
        
        // Demonstrate with actual method calls
        output.append("=== Demonstration with Methods ===\n");
        
        // Method that works with wildcards
        double intSum = sumWithWildcard(intList);
        double doubleSum = sumWithWildcard(doubleList);
        
        output.append("Sum of Integer list: ").append(intSum).append("\n");
        output.append("Sum of Double list: ").append(doubleSum).append("\n\n");
        
        // Demonstrate upper bounded wildcard
        output.append("=== Upper Bounded Wildcard (? extends T) ===\n");
        output.append("Allows reading from the list but not writing:\n");
        output.append("- Can read elements (they are guaranteed to be T or subtype)\n");
        output.append("- Cannot add elements (don't know exact type)\n\n");
        
        // Demonstrate lower bounded wildcard
        output.append("=== Lower Bounded Wildcard (? super T) ===\n");
        output.append("Allows writing to the list but limited reading:\n");
        output.append("- Can add elements (they are guaranteed to be T or supertype)\n");
        output.append("- Can only read as Object (not guaranteed to be T)\n\n");
        
        // Demonstrate unbounded wildcard
        output.append("=== Unbounded Wildcard (?) ===\n");
        output.append("Most restrictive - only allows null operations:\n");
        output.append("- Cannot add elements (except null)\n");
        output.append("- Can only read as Object\n\n");
        
        output.append("=== Key Points ===\n");
        output.append("1. Wildcards provide flexibility in generic type parameters\n");
        output.append("2. ? extends T: Read-only access, elements are T or subtype\n");
        output.append("3. ? super T: Write access, can add T or supertype\n");
        output.append("4. ?: Most restrictive, only null operations\n");
        output.append("5. Wildcards help maintain type safety while providing flexibility\n");
        
        outputArea.setText(output.toString());
    }
    
    /**
     * Method that demonstrates wildcard usage.
     * This method can accept any List of Number or its subtypes.
     * @param list the list to sum
     * @return the sum of all numbers in the list
     */
    private static double sumWithWildcard(List<? extends Number> list) {
        double sum = 0.0;
        for (Number number : list) {
            sum += number.doubleValue();
        }
        return sum;
    }
} 