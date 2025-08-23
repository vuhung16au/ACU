package com.acu.genericcli.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstration of the unbounded wildcard (?).
 * Shows how to work with any type in a generic context.
 */
public class AnyWildCardDemo {
    
    /**
     * Runs the unbounded wildcard demonstration and outputs results to console.
     */
    public static void runDemo() {
        System.out.println("=== Unbounded Wildcard (?) Demonstration ===\n");
        
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
        
        System.out.println("Created lists:");
        System.out.println("Integer list: " + intList);
        System.out.println("String list: " + stringList);
        System.out.println("Double list: " + doubleList + "\n");
        
        // Demonstrate unbounded wildcard methods
        System.out.println("=== Unbounded Wildcard Methods ===");
        
        System.out.println("1. printList(List<?> list) - prints any list:");
        printList(intList);
        printList(stringList);
        printList(doubleList);
        
        System.out.println("\n2. getSize(List<?> list) - gets size of any list:");
        System.out.println("Size of Integer list: " + getSize(intList));
        System.out.println("Size of String list: " + getSize(stringList));
        System.out.println("Size of Double list: " + getSize(doubleList) + "\n");
        
        System.out.println("3. isEmpty(List<?> list) - checks if any list is empty:");
        System.out.println("Integer list empty: " + isEmpty(intList));
        System.out.println("String list empty: " + isEmpty(stringList));
        System.out.println("Double list empty: " + isEmpty(doubleList) + "\n");
        
        // Demonstrate limitations
        System.out.println("=== Limitations of Unbounded Wildcard ===");
        System.out.println("1. Cannot add elements (except null):");
        System.out.println("   - list.add(new Integer(1)); // Compile error");
        System.out.println("   - list.add(null); // OK\n");
        
        System.out.println("2. Can only read as Object:");
        System.out.println("   - Object obj = list.get(0); // OK");
        System.out.println("   - Integer i = list.get(0); // Compile error\n");
        
        // Demonstrate practical usage
        System.out.println("=== Practical Usage Examples ===");
        
        System.out.println("1. Utility methods that work with any collection:");
        System.out.println("   - isEmpty()");
        System.out.println("   - size()");
        System.out.println("   - contains()\n");
        
        System.out.println("2. Debugging and logging:");
        System.out.println("   - printCollection()");
        System.out.println("   - logCollection()\n");
        
        System.out.println("3. Collection operations that don't modify:");
        System.out.println("   - copy()");
        System.out.println("   - clear()\n");
        
        // Demonstrate with actual method calls
        System.out.println("=== Method Demonstrations ===");
        
        List<Object> objectList = new ArrayList<>();
        objectList.add("String");
        objectList.add(42);
        objectList.add(3.14);
        
        System.out.println("Mixed Object list: " + objectList);
        System.out.println("Size: " + getSize(objectList));
        System.out.println("Empty: " + isEmpty(objectList) + "\n");
        
        // Demonstrate clear method
        System.out.println("Clearing the mixed list...");
        clearList(objectList);
        System.out.println("After clearing - Empty: " + isEmpty(objectList) + "\n");
        
        System.out.println("=== Key Points ===");
        System.out.println("1. ? represents any type");
        System.out.println("2. Most restrictive wildcard");
        System.out.println("3. Can only read as Object");
        System.out.println("4. Cannot add elements (except null)");
        System.out.println("5. Useful for utility methods that don't modify the collection");
        System.out.println("6. Provides maximum flexibility for reading operations");
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