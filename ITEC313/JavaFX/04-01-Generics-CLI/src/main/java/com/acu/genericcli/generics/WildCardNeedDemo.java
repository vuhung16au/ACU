package com.acu.genericcli.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstration of why wildcards are needed in generic programming.
 * Shows the limitations of generic types without wildcards.
 */
public class WildCardNeedDemo {
    
    /**
     * Runs the wildcard need demonstration and outputs results to console.
     */
    public static void runDemo() {
        System.out.println("=== Wildcard Need Demonstration ===\n");
        
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
        
        System.out.println("Created lists:");
        System.out.println("Integer list: " + intList);
        System.out.println("Double list: " + doubleList);
        System.out.println("String list: " + stringList + "\n");
        
        // Demonstrate the problem without wildcards
        System.out.println("=== Problem: Without Wildcards ===");
        System.out.println("If we have a method that takes List<Number>:");
        System.out.println("public static double sum(List<Number> list) { ... }\n");
        
        System.out.println("This method CANNOT accept:");
        System.out.println("- List<Integer> (even though Integer extends Number)");
        System.out.println("- List<Double> (even though Double extends Number)");
        System.out.println("- List<String> (String does not extend Number)\n");
        
        System.out.println("This is because generics are INVARIANT by default.");
        System.out.println("List<Integer> is NOT a subtype of List<Number>\n");
        
        // Demonstrate the solution with wildcards
        System.out.println("=== Solution: With Wildcards ===");
        System.out.println("Using wildcards allows flexibility:");
        System.out.println("public static double sum(List<? extends Number> list) { ... }\n");
        
        System.out.println("This method CAN accept:");
        System.out.println("- List<Integer> (Integer extends Number)");
        System.out.println("- List<Double> (Double extends Number)");
        System.out.println("- List<Number> (Number is Number)\n");
        
        // Demonstrate with actual method calls
        System.out.println("=== Demonstration with Methods ===");
        
        // Method that works with wildcards
        double intSum = sumWithWildcard(intList);
        double doubleSum = sumWithWildcard(doubleList);
        
        System.out.println("Sum of Integer list: " + intSum);
        System.out.println("Sum of Double list: " + doubleSum + "\n");
        
        // Demonstrate upper bounded wildcard
        System.out.println("=== Upper Bounded Wildcard (? extends T) ===");
        System.out.println("Allows reading from the list but not writing:");
        System.out.println("- Can read elements (they are guaranteed to be T or subtype)");
        System.out.println("- Cannot add elements (don't know exact type)\n");
        
        // Demonstrate lower bounded wildcard
        System.out.println("=== Lower Bounded Wildcard (? super T) ===");
        System.out.println("Allows writing to the list but limited reading:");
        System.out.println("- Can add elements (they are guaranteed to be T or supertype)");
        System.out.println("- Can only read as Object (not guaranteed to be T)\n");
        
        // Demonstrate unbounded wildcard
        System.out.println("=== Unbounded Wildcard (?) ===");
        System.out.println("Most restrictive - only allows null operations:");
        System.out.println("- Cannot add elements (except null)");
        System.out.println("- Can only read as Object\n");
        
        System.out.println("=== Key Points ===");
        System.out.println("1. Wildcards provide flexibility in generic type parameters");
        System.out.println("2. ? extends T: Read-only access, elements are T or subtype");
        System.out.println("3. ? super T: Write access, can add T or supertype");
        System.out.println("4. ?: Most restrictive, only null operations");
        System.out.println("5. Wildcards help maintain type safety while providing flexibility");
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