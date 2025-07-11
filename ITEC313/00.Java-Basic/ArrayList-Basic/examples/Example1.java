import java.util.ArrayList;
import java.util.Arrays;

/**
 * Example 1: Basic ArrayList Operations
 * 
 * This example shows fundamental ArrayList operations that Python developers
 * need to understand when transitioning to Java.
 * 
 * Python equivalent concepts:
 * - list = [] -> ArrayList<String> list = new ArrayList<>();
 * - list.append(item) -> list.add(item)
 * - list.remove(item) -> list.remove(item)
 * - len(list) -> list.size()
 * - item in list -> list.contains(item)
 */
public class Example1 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic ArrayList Operations ===\n");
        
        // Create an ArrayList of strings
        ArrayList<String> names = new ArrayList<>();
        
        // Add elements to the ArrayList
        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");
        names.add("Diana");
        
        System.out.println("Initial list: " + names);
        System.out.println("Size: " + names.size());
        
        // Access elements by index
        System.out.println("First name: " + names.get(0));
        System.out.println("Last name: " + names.get(names.size() - 1));
        
        // Check if element exists
        System.out.println("Contains 'Bob': " + names.contains("Bob"));
        System.out.println("Contains 'Eve': " + names.contains("Eve"));
        
        // Find index of element
        System.out.println("Index of 'Charlie': " + names.indexOf("Charlie"));
        
        // Insert element at specific position
        names.add(1, "Eve");  // Insert at index 1
        System.out.println("After inserting 'Eve': " + names);
        
        // Replace element at specific position
        names.set(2, "Frank");
        System.out.println("After replacing index 2: " + names);
        
        // Remove element by value
        names.remove("Alice");
        System.out.println("After removing 'Alice': " + names);
        
        // Remove element by index
        String removed = names.remove(0);
        System.out.println("Removed element: " + removed);
        System.out.println("After removing index 0: " + names);
        
        // Clear all elements
        names.clear();
        System.out.println("After clearing: " + names);
        System.out.println("Is empty: " + names.isEmpty());
        
        // Add elements back
        names.add("Grace");
        names.add("Henry");
        System.out.println("After adding new elements: " + names);
        
        // Convert to array
        String[] nameArray = names.toArray(new String[0]);
        System.out.println("As array: " + Arrays.toString(nameArray));
    }
} 