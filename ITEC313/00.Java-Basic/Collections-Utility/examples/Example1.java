import java.util.*;

/**
 * Example1 - Basic Collections Utility Methods
 * 
 * This example demonstrates fundamental Collections utility methods
 * that are commonly used in Java programming.
 * 
 * Python developers note:
 * - Java's Collections class provides static methods (like Python's built-in functions)
 * - Most methods modify the original collection (unlike Python's sorted() which returns new list)
 * - Java requires explicit type declarations for collections
 */
public class Example1 {
    
    public static void main(String[] args) {
        System.out.println("=== Basic Collections Utility Methods ===\n");
        
        demonstrateBasicSorting();
        demonstrateBasicSearching();
        demonstrateBasicShuffling();
    }
    
    /**
     * Demonstrates basic sorting operations
     * Python equivalent: list.sort() or sorted(list)
     */
    public static void demonstrateBasicSorting() {
        System.out.println("1. BASIC SORTING");
        System.out.println("================");
        
        // Sort a list of integers
        List<Integer> numbers = new ArrayList<>();
        numbers.add(5);
        numbers.add(2);
        numbers.add(8);
        numbers.add(1);
        numbers.add(9);
        
        System.out.println("Original numbers: " + numbers);
        Collections.sort(numbers);
        System.out.println("After sorting: " + numbers);
        
        // Sort a list of strings
        List<String> names = new ArrayList<>();
        names.add("Charlie");
        names.add("Alice");
        names.add("Bob");
        names.add("David");
        
        System.out.println("\nOriginal names: " + names);
        Collections.sort(names);
        System.out.println("After sorting: " + names);
        
        System.out.println();
    }
    
    /**
     * Demonstrates basic searching operations
     * Python equivalent: list.index(item) or 'item in list'
     */
    public static void demonstrateBasicSearching() {
        System.out.println("2. BASIC SEARCHING");
        System.out.println("==================");
        
        // Create a sorted list for binary search
        List<Integer> sortedNumbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            sortedNumbers.add(i * 10);
        }
        
        System.out.println("Sorted numbers: " + sortedNumbers);
        
        // Search for existing element
        int searchValue = 50;
        int index = Collections.binarySearch(sortedNumbers, searchValue);
        System.out.println("Index of " + searchValue + ": " + index);
        
        // Search for non-existing element
        int nonExistingValue = 35;
        int nonExistingIndex = Collections.binarySearch(sortedNumbers, nonExistingValue);
        System.out.println("Index of " + nonExistingValue + ": " + nonExistingIndex + " (not found)");
        
        System.out.println();
    }
    
    /**
     * Demonstrates basic shuffling operations
     * Python equivalent: random.shuffle(list)
     */
    public static void demonstrateBasicShuffling() {
        System.out.println("3. BASIC SHUFFLING");
        System.out.println("==================");
        
        // Create a list to shuffle
        List<String> cards = new ArrayList<>();
        cards.add("Ace");
        cards.add("King");
        cards.add("Queen");
        cards.add("Jack");
        cards.add("10");
        
        System.out.println("Original cards: " + cards);
        
        // Shuffle the cards
        Collections.shuffle(cards);
        System.out.println("After shuffling: " + cards);
        
        // Shuffle again
        Collections.shuffle(cards);
        System.out.println("After second shuffle: " + cards);
        
        System.out.println();
    }
} 