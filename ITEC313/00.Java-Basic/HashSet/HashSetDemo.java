import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * HashSet Demonstration for ITEC313
 * 
 * This program demonstrates the usage of HashSet in Java, which is equivalent
 * to Python's set data structure. HashSet provides O(1) average time complexity
 * for add, remove, and contains operations.
 * 
 * Key differences from Python sets:
 * - Java HashSet is a class, not a built-in type
 * - Must import java.util.HashSet
 * - Uses .add() instead of Python's set literal syntax
 * - Uses .contains() instead of Python's 'in' operator
 * - No set comprehensions in Java (must use loops)
 */
public class HashSetDemo {
    
    public static void main(String[] args) {
        System.out.println("=== HashSet Demonstration ===\n");
        
        // Basic HashSet operations
        demonstrateBasicOperations();
        
        // Set operations (union, intersection, difference)
        demonstrateSetOperations();
        
        // HashSet with custom objects
        demonstrateCustomObjects();
        
        // Performance demonstration
        demonstratePerformance();
        
        // Real-world example: Removing duplicates
        demonstrateDuplicateRemoval();
    }
    
    /**
     * Demonstrates basic HashSet operations
     * Equivalent to Python: set = {1, 2, 3}
     */
    private static void demonstrateBasicOperations() {
        System.out.println("1. Basic HashSet Operations:");
        System.out.println("---------------------------");
        
        // Creating a HashSet (Python: set = set() or set = {1, 2, 3})
        HashSet<String> fruits = new HashSet<>();
        
        // Adding elements (Python: set.add(item))
        fruits.add("apple");
        fruits.add("banana");
        fruits.add("orange");
        fruits.add("apple"); // Duplicate - will be ignored
        
        System.out.println("Fruits set: " + fruits);
        System.out.println("Size: " + fruits.size()); // Python: len(set)
        
        // Checking if element exists (Python: item in set)
        System.out.println("Contains 'apple': " + fruits.contains("apple"));
        System.out.println("Contains 'grape': " + fruits.contains("grape"));
        
        // Removing elements (Python: set.remove(item))
        fruits.remove("banana");
        System.out.println("After removing 'banana': " + fruits);
        
        // Clearing the set (Python: set.clear())
        fruits.clear();
        System.out.println("After clearing: " + fruits);
        System.out.println("Is empty: " + fruits.isEmpty()); // Python: len(set) == 0
        System.out.println();
    }
    
    /**
     * Demonstrates set operations: union, intersection, difference
     * Python equivalents: |, &, -
     */
    private static void demonstrateSetOperations() {
        System.out.println("2. Set Operations:");
        System.out.println("------------------");
        
        // Creating two sets
        HashSet<Integer> setA = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        HashSet<Integer> setB = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));
        
        System.out.println("Set A: " + setA);
        System.out.println("Set B: " + setB);
        
        // Union (Python: setA | setB)
        HashSet<Integer> union = new HashSet<>(setA);
        union.addAll(setB); // Python: union = setA | setB
        System.out.println("Union (A ∪ B): " + union);
        
        // Intersection (Python: setA & setB)
        HashSet<Integer> intersection = new HashSet<>(setA);
        intersection.retainAll(setB); // Python: intersection = setA & setB
        System.out.println("Intersection (A ∩ B): " + intersection);
        
        // Difference (Python: setA - setB)
        HashSet<Integer> difference = new HashSet<>(setA);
        difference.removeAll(setB); // Python: difference = setA - setB
        System.out.println("Difference (A - B): " + difference);
        
        // Symmetric difference (Python: setA ^ setB)
        HashSet<Integer> symmetricDiff = new HashSet<>(union);
        symmetricDiff.removeAll(intersection);
        System.out.println("Symmetric Difference (A △ B): " + symmetricDiff);
        System.out.println();
    }
    
    /**
     * Demonstrates HashSet with custom objects
     * Shows importance of equals() and hashCode() methods
     */
    private static void demonstrateCustomObjects() {
        System.out.println("3. HashSet with Custom Objects:");
        System.out.println("------------------------------");
        
        HashSet<Student> students = new HashSet<>();
        
        // Adding students
        students.add(new Student("Alice", 20));
        students.add(new Student("Bob", 22));
        students.add(new Student("Alice", 20)); // Duplicate - should be ignored
        
        System.out.println("Students: " + students);
        System.out.println("Size: " + students.size());
        
        // Testing contains with custom object
        Student testStudent = new Student("Alice", 20);
        System.out.println("Contains Alice(20): " + students.contains(testStudent));
        System.out.println();
    }
    
    /**
     * Demonstrates performance characteristics of HashSet
     * Shows O(1) average time complexity for basic operations
     */
    private static void demonstratePerformance() {
        System.out.println("4. Performance Demonstration:");
        System.out.println("----------------------------");
        
        HashSet<Integer> largeSet = new HashSet<>();
        int size = 100000;
        
        // Adding elements
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            largeSet.add(i);
        }
        long addTime = System.currentTimeMillis() - startTime;
        
        // Searching for elements
        startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            largeSet.contains(i);
        }
        long searchTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Added " + size + " elements in " + addTime + "ms");
        System.out.println("Searched " + size + " elements in " + searchTime + "ms");
        System.out.println("Average time per operation: " + 
                          (double)(addTime + searchTime) / (2 * size) + "ms");
        System.out.println();
    }
    
    /**
     * Real-world example: Removing duplicates from a list
     * Python equivalent: list(set(original_list))
     */
    private static void demonstrateDuplicateRemoval() {
        System.out.println("5. Real-world Example: Removing Duplicates:");
        System.out.println("-------------------------------------------");
        
        // List with duplicates
        String[] namesWithDuplicates = {
            "Alice", "Bob", "Alice", "Charlie", "Bob", "David", "Alice"
        };
        
        System.out.println("Original list: " + Arrays.toString(namesWithDuplicates));
        
        // Using HashSet to remove duplicates (Python: list(set(names)))
        HashSet<String> uniqueNames = new HashSet<>(Arrays.asList(namesWithDuplicates));
        
        System.out.println("Unique names: " + uniqueNames);
        System.out.println("Count of unique names: " + uniqueNames.size());
        
        // Converting back to array if needed
        String[] uniqueArray = uniqueNames.toArray(new String[0]);
        System.out.println("Unique array: " + Arrays.toString(uniqueArray));
        System.out.println();
    }
    
    /**
     * Custom Student class to demonstrate HashSet with objects
     * Note: Must override equals() and hashCode() for proper HashSet behavior
     */
    static class Student {
        private String name;
        private int age;
        
        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        // Override equals for HashSet to work correctly
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Student student = (Student) obj;
            return age == student.age && name.equals(student.name);
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