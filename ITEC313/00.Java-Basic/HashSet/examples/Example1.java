/**
 * Example1.java
 * 
 * This program demonstrates example in Java:
 * - Core concepts and principles
 * - Implementation techniques
 * - Best practices and patterns
 * - Practical examples and usage
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
import java.util.HashSet;
import java.util.Arrays;

/**
 * Example 1: Basic HashSet Operations
 * 
 * This example demonstrates fundamental HashSet operations including
 * creation, adding elements, checking membership, and removing elements.
 * 
 * Python equivalent concepts:
 * - Creating: set() or {1, 2, 3}
 * - Adding: set.add(item)
 * - Checking: item in set
 * - Removing: set.remove(item)
 */
public class Example1 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic HashSet Operations ===\n");
        
        // Creating an empty HashSet (Python: set = set())
        HashSet<String> colors = new HashSet<>();
        System.out.println("Created empty HashSet: " + colors);
        
        // Adding elements one by one (Python: set.add(item))
        colors.add("red");
        colors.add("green");
        colors.add("blue");
        colors.add("red"); // Duplicate - will be ignored
        
        System.out.println("After adding colors: " + colors);
        System.out.println("Size: " + colors.size()); // Python: len(set)
        
        // Creating HashSet from existing collection (Python: set(list))
        String[] colorArray = {"yellow", "purple", "orange"};
        HashSet<String> moreColors = new HashSet<>(Arrays.asList(colorArray));
        System.out.println("HashSet from array: " + moreColors);
        
        // Checking if element exists (Python: item in set)
        System.out.println("Contains 'red': " + colors.contains("red"));
        System.out.println("Contains 'yellow': " + colors.contains("yellow"));
        
        // Removing elements (Python: set.remove(item))
        colors.remove("green");
        System.out.println("After removing 'green': " + colors);
        
        // Checking if set is empty (Python: len(set) == 0)
        System.out.println("Is empty: " + colors.isEmpty());
        
        // Clearing all elements (Python: set.clear())
        colors.clear();
        System.out.println("After clearing: " + colors);
        System.out.println("Is empty: " + colors.isEmpty());
        
        // Adding elements again
        colors.add("cyan");
        colors.add("magenta");
        System.out.println("After adding new colors: " + colors);
        
        // Iterating through HashSet (Python: for item in set:)
        System.out.println("Iterating through colors:");
        for (String color : colors) {
            System.out.println("  - " + color);
        }
    }
} 