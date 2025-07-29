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
import java.util.HashMap;

public class Example1 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic HashMap Operations ===\n");
        
        // Create a HashMap to store student grades
        // Python equivalent: grades = {}
        HashMap<String, Integer> grades = new HashMap<>();
        
        // Add student grades
        // Python equivalent: grades['Alice'] = 95
        grades.put("Alice", 95);
        grades.put("Bob", 87);
        grades.put("Charlie", 92);
        grades.put("Diana", 89);
        
        System.out.println("Initial grades: " + grades);
        
        // Get a specific student's grade
        // Python equivalent: grades['Alice']
        int aliceGrade = grades.get("Alice");
        System.out.println("Alice's grade: " + aliceGrade);
        
        // Check if a student exists
        // Python equivalent: 'Eve' in grades
        boolean hasEve = grades.containsKey("Eve");
        System.out.println("Contains 'Eve': " + hasEve);
        
        // Update a grade
        // Python equivalent: grades['Bob'] = 90
        grades.put("Bob", 90);
        System.out.println("After updating Bob's grade: " + grades);
        
        // Remove a student
        // Python equivalent: del grades['Charlie']
        grades.remove("Charlie");
        System.out.println("After removing Charlie: " + grades);
        
        // Get the number of students
        // Python equivalent: len(grades)
        int studentCount = grades.size();
        System.out.println("Number of students: " + studentCount);
        
        // Check if the HashMap is empty
        boolean isEmpty = grades.isEmpty();
        System.out.println("Is empty: " + isEmpty);
        
        System.out.println("\n=== Example 1 completed! ===");
    }
} 