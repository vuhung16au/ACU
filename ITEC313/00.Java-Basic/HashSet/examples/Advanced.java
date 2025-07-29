/**
 * Advanced.java
 * 
 * This program demonstrates advanced in Java:
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
import java.util.Objects;
import java.util.Iterator;

/**
 * Advanced Example: HashSet with Custom Objects and Performance Analysis
 * 
 * This example demonstrates advanced HashSet concepts including:
 * - Using HashSet with custom objects
 * - Importance of equals() and hashCode() methods
 * - Performance characteristics
 * - Real-world applications
 * 
 * Key learning: When using custom objects in HashSet, you MUST override
 * equals() and hashCode() methods for proper behavior.
 */
public class Advanced {
    
    public static void main(String[] args) {
        System.out.println("=== Advanced Example: HashSet with Custom Objects ===\n");
        
        // 1. HashSet with custom objects
        demonstrateCustomObjects();
        
        // 2. Performance analysis
        demonstratePerformance();
        
        // 3. Real-world application: Duplicate removal
        demonstrateDuplicateRemoval();
        
        // 4. Advanced iteration techniques
        demonstrateIteration();
    }
    
    /**
     * Demonstrates HashSet with custom objects
     * Shows why equals() and hashCode() are crucial
     */
    private static void demonstrateCustomObjects() {
        System.out.println("1. HashSet with Custom Objects:");
        System.out.println("-------------------------------");
        
        HashSet<Person> people = new HashSet<>();
        
        // Adding people
        people.add(new Person("Alice", 25));
        people.add(new Person("Bob", 30));
        people.add(new Person("Alice", 25)); // Duplicate - should be ignored
        
        System.out.println("People set: " + people);
        System.out.println("Size: " + people.size());
        
        // Testing contains with custom object
        Person testPerson = new Person("Alice", 25);
        System.out.println("Contains Alice(25): " + people.contains(testPerson));
        
        // Adding person with same name but different age
        people.add(new Person("Alice", 26));
        System.out.println("After adding Alice(26): " + people);
        System.out.println();
    }
    
    /**
     * Demonstrates performance characteristics of HashSet
     * Shows O(1) average time complexity for basic operations
     */
    private static void demonstratePerformance() {
        System.out.println("2. Performance Analysis:");
        System.out.println("----------------------");
        
        int[] sizes = {1000, 10000, 100000};
        
        for (int size : sizes) {
            HashSet<Integer> set = new HashSet<>();
            
            // Measure add performance
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < size; i++) {
                set.add(i);
            }
            long addTime = System.currentTimeMillis() - startTime;
            
            // Measure contains performance
            startTime = System.currentTimeMillis();
            for (int i = 0; i < size; i++) {
                set.contains(i);
            }
            long containsTime = System.currentTimeMillis() - startTime;
            
            System.out.println("Size " + size + ":");
            System.out.println("  Add time: " + addTime + "ms");
            System.out.println("  Contains time: " + containsTime + "ms");
            System.out.println("  Average time per operation: " + 
                              String.format("%.4f", (double)(addTime + containsTime) / (2 * size)) + "ms");
            System.out.println();
        }
    }
    
    /**
     * Real-world application: Removing duplicates from data
     */
    private static void demonstrateDuplicateRemoval() {
        System.out.println("3. Real-world Application: Duplicate Removal");
        System.out.println("--------------------------------------------");
        
        // Simulating data with duplicates
        String[] emails = {
            "alice@example.com", "bob@example.com", "alice@example.com",
            "charlie@example.com", "bob@example.com", "david@example.com",
            "alice@example.com", "eve@example.com"
        };
        
        System.out.println("Original email list:");
        for (String email : emails) {
            System.out.println("  " + email);
        }
        System.out.println("Total emails: " + emails.length);
        
        // Using HashSet to remove duplicates
        HashSet<String> uniqueEmails = new HashSet<>(Arrays.asList(emails));
        
        System.out.println("\nUnique emails:");
        for (String email : uniqueEmails) {
            System.out.println("  " + email);
        }
        System.out.println("Unique count: " + uniqueEmails.size());
        System.out.println("Duplicates removed: " + (emails.length - uniqueEmails.size()));
        System.out.println();
    }
    
    /**
     * Demonstrates different ways to iterate through HashSet
     */
    private static void demonstrateIteration() {
        System.out.println("4. Advanced Iteration Techniques:");
        System.out.println("--------------------------------");
        
        HashSet<String> fruits = new HashSet<>(Arrays.asList(
            "apple", "banana", "orange", "grape", "kiwi"
        ));
        
        System.out.println("Fruits set: " + fruits);
        System.out.println();
        
        // Method 1: Enhanced for loop (recommended)
        System.out.println("Method 1: Enhanced for loop:");
        for (String fruit : fruits) {
            System.out.println("  - " + fruit);
        }
        System.out.println();
        
        // Method 2: Iterator
        System.out.println("Method 2: Iterator:");
        Iterator<String> iterator = fruits.iterator();
        while (iterator.hasNext()) {
            System.out.println("  - " + iterator.next());
        }
        System.out.println();
        
        // Method 3: forEach with lambda (Java 8+)
        System.out.println("Method 3: forEach with lambda:");
        fruits.forEach(fruit -> System.out.println("  - " + fruit));
        System.out.println();
        
        // Method 4: Stream API (Java 8+)
        System.out.println("Method 4: Stream API:");
        fruits.stream()
              .forEach(fruit -> System.out.println("  - " + fruit));
    }
    
    /**
     * Custom Person class to demonstrate HashSet with objects
     * 
     * IMPORTANT: When using custom objects in HashSet, you MUST override:
     * 1. equals() method - defines when two objects are considered equal
     * 2. hashCode() method - must be consistent with equals()
     * 
     * Python equivalent: You would need to implement __eq__ and __hash__ methods
     */
    static class Person {
        private String name;
        private int age;
        
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        // Override equals for HashSet to work correctly
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Person person = (Person) obj;
            return age == person.age && Objects.equals(name, person.name);
        }
        
        // Override hashCode to maintain consistency with equals
        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
        
        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }
} 