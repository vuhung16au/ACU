/**
 * CollectionsUtility.java
 * 
 * This program demonstrates Java Collections utility methods:
 * - Collection manipulation utilities
 * - Sorting and searching collections
 * - Collection synchronization
 * - Collection performance optimization
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
import java.util.*;

/**
 * CollectionsUtility - Demonstrates Java Collections Utility Methods
 * 
 * This program showcases the powerful utility methods provided by Java's Collections class.
 * These methods provide common operations for working with collections like sorting, 
 * searching, and manipulating data structures.
 * 
 * Key differences from Python:
 * - Java uses static methods in Collections class (vs Python's built-in functions)
 * - Collections work with specific interfaces (List, Set, Map)
 * - Java requires explicit type declarations
 * - Collections methods modify the original collection (unlike Python's sorted() which returns new list)
 */
public class CollectionsUtility {
    
    public static void main(String[] args) {
        System.out.println("=== Java Collections Utility Methods Demo ===\n");
        
        // Demonstrate different aspects of Collections utility methods
        demonstrateSorting();
        demonstrateSearching();
        demonstrateShuffling();
        demonstrateFrequencyAndOccurrences();
        demonstrateMinMaxOperations();
        demonstrateReverseAndRotate();
        demonstrateFillAndCopy();
        demonstrateSwapAndReplace();
    }
    
    /**
     * Demonstrates sorting operations using Collections.sort()
     * 
     * Python equivalent: list.sort() or sorted(list)
     * Key difference: Java's sort() modifies the original list
     */
    public static void demonstrateSorting() {
        System.out.println("1. SORTING OPERATIONS");
        System.out.println("======================");
        
        // Create a list of integers
        List<Integer> numbers = new ArrayList<>(Arrays.asList(64, 34, 25, 12, 22, 11, 90));
        System.out.println("Original list: " + numbers);
        
        // Sort in ascending order (natural ordering)
        Collections.sort(numbers);
        System.out.println("After sorting: " + numbers);
        
        // Sort in descending order using Comparator
        Collections.sort(numbers, Collections.reverseOrder());
        System.out.println("After reverse sorting: " + numbers);
        
        // Sort strings
        List<String> names = new ArrayList<>(Arrays.asList("Alice", "Charlie", "Bob", "David"));
        System.out.println("\nOriginal names: " + names);
        Collections.sort(names);
        System.out.println("After sorting names: " + names);
        
        // Custom sorting with Comparator (sort by length)
        Collections.sort(names, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
        System.out.println("After sorting by length: " + names);
        
        System.out.println();
    }
    
    /**
     * Demonstrates searching operations using Collections.binarySearch()
     * 
     * Python equivalent: list.index() or 'in' operator
     * Important: List must be sorted for binarySearch to work correctly
     */
    public static void demonstrateSearching() {
        System.out.println("2. SEARCHING OPERATIONS");
        System.out.println("=======================");
        
        // Create a sorted list for binary search
        List<Integer> sortedNumbers = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50, 60, 70));
        System.out.println("Sorted list: " + sortedNumbers);
        
        // Binary search for existing element
        int searchValue = 40;
        int index = Collections.binarySearch(sortedNumbers, searchValue);
        System.out.println("Index of " + searchValue + ": " + index);
        
        // Binary search for non-existing element
        int nonExistingValue = 35;
        int nonExistingIndex = Collections.binarySearch(sortedNumbers, nonExistingValue);
        System.out.println("Index of " + nonExistingValue + ": " + nonExistingIndex + " (not found)");
        
        // Search with custom comparator
        List<String> sortedNames = new ArrayList<>(Arrays.asList("Alice", "Bob", "Charlie", "David"));
        int nameIndex = Collections.binarySearch(sortedNames, "Charlie", String::compareTo);
        System.out.println("Index of 'Charlie': " + nameIndex);
        
        System.out.println();
    }
    
    /**
     * Demonstrates shuffling operations using Collections.shuffle()
     * 
     * Python equivalent: random.shuffle(list)
     * Note: Java's shuffle modifies the original list
     */
    public static void demonstrateShuffling() {
        System.out.println("3. SHUFFLING OPERATIONS");
        System.out.println("========================");
        
        // Create a list to shuffle
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        System.out.println("Original list: " + numbers);
        
        // Shuffle the list
        Collections.shuffle(numbers);
        System.out.println("After shuffling: " + numbers);
        
        // Shuffle again (different result)
        Collections.shuffle(numbers);
        System.out.println("After second shuffle: " + numbers);
        
        // Shuffle with specific Random seed for reproducible results
        Collections.shuffle(numbers, new Random(42));
        System.out.println("After seeded shuffle: " + numbers);
        
        System.out.println();
    }
    
    /**
     * Demonstrates frequency counting and occurrence operations
     * 
     * Python equivalent: list.count(item)
     * Java's frequency() works with any Collection, not just Lists
     */
    public static void demonstrateFrequencyAndOccurrences() {
        System.out.println("4. FREQUENCY AND OCCURRENCES");
        System.out.println("=============================");
        
        // Create a list with duplicate elements
        List<String> fruits = new ArrayList<>(Arrays.asList("apple", "banana", "apple", "orange", "banana", "apple"));
        System.out.println("Fruits list: " + fruits);
        
        // Count frequency of elements
        System.out.println("Frequency of 'apple': " + Collections.frequency(fruits, "apple"));
        System.out.println("Frequency of 'banana': " + Collections.frequency(fruits, "banana"));
        System.out.println("Frequency of 'orange': " + Collections.frequency(fruits, "orange"));
        System.out.println("Frequency of 'grape': " + Collections.frequency(fruits, "grape"));
        
        // Check if two collections are disjoint (no common elements)
        List<String> list1 = Arrays.asList("a", "b", "c");
        List<String> list2 = Arrays.asList("d", "e", "f");
        List<String> list3 = Arrays.asList("a", "d", "e");
        
        System.out.println("\nList1: " + list1);
        System.out.println("List2: " + list2);
        System.out.println("List3: " + list3);
        System.out.println("List1 and List2 are disjoint: " + Collections.disjoint(list1, list2));
        System.out.println("List1 and List3 are disjoint: " + Collections.disjoint(list1, list3));
        
        System.out.println();
    }
    
    /**
     * Demonstrates min/max operations using Collections.min() and Collections.max()
     * 
     * Python equivalent: min(list), max(list)
     * Java requires the collection to be non-empty
     */
    public static void demonstrateMinMaxOperations() {
        System.out.println("5. MIN/MAX OPERATIONS");
        System.out.println("======================");
        
        // Find min and max in a list of integers
        List<Integer> numbers = Arrays.asList(34, 12, 67, 89, 23, 45, 1, 99);
        System.out.println("Numbers: " + numbers);
        System.out.println("Minimum: " + Collections.min(numbers));
        System.out.println("Maximum: " + Collections.max(numbers));
        
        // Find min and max in a list of strings
        List<String> words = Arrays.asList("zebra", "apple", "banana", "cat", "dog");
        System.out.println("\nWords: " + words);
        System.out.println("Minimum (alphabetical): " + Collections.min(words));
        System.out.println("Maximum (alphabetical): " + Collections.max(words));
        
        // Find min and max with custom comparator (by length)
        System.out.println("Minimum (by length): " + Collections.min(words, (s1, s2) -> Integer.compare(s1.length(), s2.length())));
        System.out.println("Maximum (by length): " + Collections.max(words, (s1, s2) -> Integer.compare(s1.length(), s2.length())));
        
        System.out.println();
    }
    
    /**
     * Demonstrates reverse and rotate operations
     * 
     * Python equivalent: list.reverse(), list slicing for rotation
     * Java's reverse() modifies the original list
     */
    public static void demonstrateReverseAndRotate() {
        System.out.println("6. REVERSE AND ROTATE OPERATIONS");
        System.out.println("=================================");
        
        // Reverse a list
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println("Original list: " + numbers);
        Collections.reverse(numbers);
        System.out.println("After reverse: " + numbers);
        
        // Rotate a list (shift elements)
        List<Integer> rotateNumbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println("\nOriginal list: " + rotateNumbers);
        Collections.rotate(rotateNumbers, 2);
        System.out.println("After rotating by 2: " + rotateNumbers);
        Collections.rotate(rotateNumbers, -1);
        System.out.println("After rotating by -1: " + rotateNumbers);
        
        System.out.println();
    }
    
    /**
     * Demonstrates fill and copy operations
     * 
     * Python equivalent: list * n for fill, list.copy() for copy
     * Java's fill() modifies the original list
     */
    public static void demonstrateFillAndCopy() {
        System.out.println("7. FILL AND COPY OPERATIONS");
        System.out.println("============================");
        
        // Fill a list with a specific value
        List<String> fillList = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e"));
        System.out.println("Original list: " + fillList);
        Collections.fill(fillList, "X");
        System.out.println("After filling with 'X': " + fillList);
        
        // Copy elements from one list to another
        List<String> source = Arrays.asList("apple", "banana", "orange");
        List<String> destination = new ArrayList<>(Arrays.asList("x", "y", "z", "w"));
        System.out.println("\nSource: " + source);
        System.out.println("Destination before copy: " + destination);
        Collections.copy(destination, source);
        System.out.println("Destination after copy: " + destination);
        
        System.out.println();
    }
    
    /**
     * Demonstrates swap and replace operations
     * 
     * Python equivalent: list[i], list[j] = list[j], list[i] for swap
     * Java provides utility methods for these operations
     */
    public static void demonstrateSwapAndReplace() {
        System.out.println("8. SWAP AND REPLACE OPERATIONS");
        System.out.println("===============================");
        
        // Swap elements in a list
        List<Integer> swapList = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50));
        System.out.println("Original list: " + swapList);
        Collections.swap(swapList, 1, 3);
        System.out.println("After swapping indices 1 and 3: " + swapList);
        
        // Replace all occurrences of an element
        List<String> replaceList = new ArrayList<>(Arrays.asList("old", "new", "old", "current", "old"));
        System.out.println("\nOriginal list: " + replaceList);
        Collections.replaceAll(replaceList, "old", "updated");
        System.out.println("After replacing 'old' with 'updated': " + replaceList);
        
        System.out.println();
    }
} 