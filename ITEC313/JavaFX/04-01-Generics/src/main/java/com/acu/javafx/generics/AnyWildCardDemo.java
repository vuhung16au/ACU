package com.acu.javafx.generics;

import javafx.scene.control.TextArea;
import java.util.ArrayList;
import java.util.List;

/**
 * Demonstration of the unbounded wildcard (?).
 * Shows how to work with any type in a generic context.
 */
public class AnyWildCardDemo {
    
    /**
     * Runs the unbounded wildcard demonstration and outputs results to the provided TextArea.
     * @param outputArea the TextArea to display the output
     */
    public static void runDemo(TextArea outputArea) {
        StringBuilder output = new StringBuilder();
        
        output.append("=== Unbounded Wildcard (?) Demonstration ===\n\n");
        
        // Create lists of different types
        List<Integer> intList = new ArrayList<>();
        intList.add(1);
        intList.add(2);
        intList.add(3);
        
        List<String> stringList = new ArrayList<>();
        stringList.add("Hello");
        stringList.add("World");
        stringList.add("Java");
        
        List<Double> doubleList = new ArrayList<>();
        doubleList.add(1.1);
        doubleList.add(2.2);
        doubleList.add(3.3);
        
        output.append("Created lists:\n");
        output.append("Integer list: ").append(intList).append("\n");
        output.append("String list: ").append(stringList).append("\n");
        output.append("Double list: ").append(doubleList).append("\n\n");
        
        // Demonstrate unbounded wildcard methods
        output.append("=== Unbounded Wildcard Methods ===\n");
        
        output.append("1. printList(List<?> list) - prints any list:\n");
        printList(intList);
        printList(stringList);
        printList(doubleList);
        
        output.append("\n2. getSize(List<?> list) - gets size of any list:\n");
        output.append("Size of Integer list: ").append(getSize(intList)).append("\n");
        output.append("Size of String list: ").append(getSize(stringList)).append("\n");
        output.append("Size of Double list: ").append(getSize(doubleList)).append("\n\n");
        
        output.append("3. isEmpty(List<?> list) - checks if any list is empty:\n");
        output.append("Integer list empty: ").append(isEmpty(intList)).append("\n");
        output.append("String list empty: ").append(isEmpty(stringList)).append("\n");
        output.append("Double list empty: ").append(isEmpty(doubleList)).append("\n\n");
        
        // Demonstrate limitations
        output.append("=== Limitations of Unbounded Wildcard ===\n");
        output.append("1. Cannot add elements (except null):\n");
        output.append("   - list.add(new Integer(1)); // Compile error\n");
        output.append("   - list.add(null); // OK\n\n");
        
        output.append("2. Can only read as Object:\n");
        output.append("   - Object obj = list.get(0); // OK\n");
        output.append("   - Integer i = list.get(0); // Compile error\n\n");
        
        // Demonstrate practical usage
        output.append("=== Practical Usage Examples ===\n");
        
        output.append("1. Utility methods that work with any collection:\n");
        output.append("   - isEmpty()\n");
        output.append("   - size()\n");
        output.append("   - contains()\n\n");
        
        output.append("2. Debugging and logging:\n");
        output.append("   - printCollection()\n");
        output.append("   - logCollection()\n\n");
        
        output.append("3. Collection operations that don't modify:\n");
        output.append("   - copy()\n");
        output.append("   - clear()\n\n");
        
        // Demonstrate with actual method calls
        output.append("=== Method Demonstrations ===\n");
        
        List<Object> objectList = new ArrayList<>();
        objectList.add("String");
        objectList.add(42);
        objectList.add(3.14);
        
        output.append("Mixed Object list: ").append(objectList).append("\n");
        output.append("Size: ").append(getSize(objectList)).append("\n");
        output.append("Empty: ").append(isEmpty(objectList)).append("\n\n");
        
        // Demonstrate clear method
        output.append("Clearing the mixed list...\n");
        clearList(objectList);
        output.append("After clearing - Empty: ").append(isEmpty(objectList)).append("\n\n");
        
        output.append("=== Key Points ===\n");
        output.append("1. ? represents any type\n");
        output.append("2. Most restrictive wildcard\n");
        output.append("3. Can only read as Object\n");
        output.append("4. Cannot add elements (except null)\n");
        output.append("5. Useful for utility methods that don't modify the collection\n");
        output.append("6. Provides maximum flexibility for reading operations\n");
        
        outputArea.setText(output.toString());
    }
    
    /**
     * Prints any list using unbounded wildcard.
     * @param list the list to print
     */
    private static void printList(List<?> list) {
        System.out.println("List: " + list);
    }
    
    /**
     * Gets the size of any list using unbounded wildcard.
     * @param list the list to get size of
     * @return the size of the list
     */
    private static int getSize(List<?> list) {
        return list.size();
    }
    
    /**
     * Checks if any list is empty using unbounded wildcard.
     * @param list the list to check
     * @return true if the list is empty
     */
    private static boolean isEmpty(List<?> list) {
        return list.isEmpty();
    }
    
    /**
     * Clears any list using unbounded wildcard.
     * @param list the list to clear
     */
    private static void clearList(List<?> list) {
        list.clear();
    }
} 