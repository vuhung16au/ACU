/**
 * Example2.java
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
import java.util.*;

/**
 * Example2 - Intermediate Collections Utility Methods
 * 
 * This example demonstrates intermediate-level Collections utility methods
 * including custom comparators, frequency counting, and collection operations.
 * 
 * Python developers note:
 * - Java uses Comparator objects for custom sorting (vs Python's key function)
 * - Collections.frequency() is like Python's list.count() but works with any Collection
 * - Java's disjoint() method checks if two collections have no common elements
 */
public class Example2 {
    
    public static void main(String[] args) {
        System.out.println("=== Intermediate Collections Utility Methods ===\n");
        
        demonstrateCustomSorting();
        demonstrateFrequencyOperations();
        demonstrateMinMaxOperations();
        demonstrateCollectionOperations();
    }
    
    /**
     * Demonstrates custom sorting with Comparators
     * Python equivalent: sorted(list, key=function)
     */
    public static void demonstrateCustomSorting() {
        System.out.println("1. CUSTOM SORTING WITH COMPARATORS");
        System.out.println("===================================");
        
        // Create a list of students with name and age
        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", 20));
        students.add(new Student("Bob", 18));
        students.add(new Student("Charlie", 22));
        students.add(new Student("David", 19));
        students.add(new Student("Eve", 21));
        
        System.out.println("Original students: " + students);
        
        // Sort by age (ascending)
        Collections.sort(students, (s1, s2) -> Integer.compare(s1.getAge(), s2.getAge()));
        System.out.println("Sorted by age (ascending): " + students);
        
        // Sort by age (descending)
        Collections.sort(students, (s1, s2) -> Integer.compare(s2.getAge(), s1.getAge()));
        System.out.println("Sorted by age (descending): " + students);
        
        // Sort by name (alphabetical)
        Collections.sort(students, (s1, s2) -> s1.getName().compareTo(s2.getName()));
        System.out.println("Sorted by name: " + students);
        
        System.out.println();
    }
    
    /**
     * Demonstrates frequency counting operations
     * Python equivalent: list.count(item)
     */
    public static void demonstrateFrequencyOperations() {
        System.out.println("2. FREQUENCY OPERATIONS");
        System.out.println("========================");
        
        // Create a list with duplicate elements
        List<String> fruits = new ArrayList<>();
        fruits.add("apple");
        fruits.add("banana");
        fruits.add("apple");
        fruits.add("orange");
        fruits.add("banana");
        fruits.add("apple");
        fruits.add("grape");
        fruits.add("apple");
        
        System.out.println("Fruits list: " + fruits);
        
        // Count frequency of each fruit
        System.out.println("Frequency of 'apple': " + Collections.frequency(fruits, "apple"));
        System.out.println("Frequency of 'banana': " + Collections.frequency(fruits, "banana"));
        System.out.println("Frequency of 'orange': " + Collections.frequency(fruits, "orange"));
        System.out.println("Frequency of 'grape': " + Collections.frequency(fruits, "grape"));
        System.out.println("Frequency of 'mango': " + Collections.frequency(fruits, "mango"));
        
        // Find the most frequent fruit
        String mostFrequent = null;
        int maxFrequency = 0;
        
        Set<String> uniqueFruits = new HashSet<>(fruits);
        for (String fruit : uniqueFruits) {
            int frequency = Collections.frequency(fruits, fruit);
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
                mostFrequent = fruit;
            }
        }
        
        System.out.println("Most frequent fruit: " + mostFrequent + " (appears " + maxFrequency + " times)");
        
        System.out.println();
    }
    
    /**
     * Demonstrates min/max operations with custom comparators
     * Python equivalent: min(list, key=function), max(list, key=function)
     */
    public static void demonstrateMinMaxOperations() {
        System.out.println("3. MIN/MAX OPERATIONS");
        System.out.println("======================");
        
        // Find min and max in a list of strings by length
        List<String> words = Arrays.asList("cat", "elephant", "dog", "ant", "hippopotamus");
        System.out.println("Words: " + words);
        
        // Find shortest and longest words
        String shortest = Collections.min(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
        String longest = Collections.max(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
        
        System.out.println("Shortest word: " + shortest + " (length: " + shortest.length() + ")");
        System.out.println("Longest word: " + longest + " (length: " + longest.length() + ")");
        
        // Find min and max in a list of numbers
        List<Integer> numbers = Arrays.asList(34, 12, 67, 89, 23, 45, 1, 99);
        System.out.println("\nNumbers: " + numbers);
        System.out.println("Minimum: " + Collections.min(numbers));
        System.out.println("Maximum: " + Collections.max(numbers));
        
        System.out.println();
    }
    
    /**
     * Demonstrates collection operations like disjoint and reverse
     * Python equivalent: set operations for disjoint, list.reverse() for reverse
     */
    public static void demonstrateCollectionOperations() {
        System.out.println("4. COLLECTION OPERATIONS");
        System.out.println("=========================");
        
        // Check if two collections are disjoint (no common elements)
        List<String> list1 = Arrays.asList("a", "b", "c");
        List<String> list2 = Arrays.asList("d", "e", "f");
        List<String> list3 = Arrays.asList("a", "d", "e");
        
        System.out.println("List1: " + list1);
        System.out.println("List2: " + list2);
        System.out.println("List3: " + list3);
        
        System.out.println("List1 and List2 are disjoint: " + Collections.disjoint(list1, list2));
        System.out.println("List1 and List3 are disjoint: " + Collections.disjoint(list1, list3));
        
        // Reverse a list
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println("\nOriginal numbers: " + numbers);
        Collections.reverse(numbers);
        System.out.println("After reverse: " + numbers);
        
        System.out.println();
    }
    
    /**
     * Simple Student class for demonstration
     */
    static class Student {
        private String name;
        private int age;
        
        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        
        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }
} 