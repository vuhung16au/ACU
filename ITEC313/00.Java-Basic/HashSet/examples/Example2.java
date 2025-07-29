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
import java.util.HashSet;
import java.util.Arrays;

/**
 * Example 2: HashSet Set Operations
 * 
 * This example demonstrates set operations including union, intersection,
 * difference, and symmetric difference using HashSet.
 * 
 * Python equivalent operations:
 * - Union: setA | setB
 * - Intersection: setA & setB
 * - Difference: setA - setB
 * - Symmetric Difference: setA ^ setB
 */
public class Example2 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 2: HashSet Set Operations ===\n");
        
        // Creating two sets for demonstration
        HashSet<Integer> setA = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        HashSet<Integer> setB = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));
        
        System.out.println("Set A: " + setA);
        System.out.println("Set B: " + setB);
        System.out.println();
        
        // 1. Union Operation (Python: setA | setB)
        System.out.println("1. Union Operation (A ∪ B):");
        HashSet<Integer> union = new HashSet<>(setA);
        union.addAll(setB); // Python: union = setA | setB
        System.out.println("   Union: " + union);
        System.out.println();
        
        // 2. Intersection Operation (Python: setA & setB)
        System.out.println("2. Intersection Operation (A ∩ B):");
        HashSet<Integer> intersection = new HashSet<>(setA);
        intersection.retainAll(setB); // Python: intersection = setA & setB
        System.out.println("   Intersection: " + intersection);
        System.out.println();
        
        // 3. Difference Operation (Python: setA - setB)
        System.out.println("3. Difference Operation (A - B):");
        HashSet<Integer> difference = new HashSet<>(setA);
        difference.removeAll(setB); // Python: difference = setA - setB
        System.out.println("   Difference: " + difference);
        System.out.println();
        
        // 4. Symmetric Difference (Python: setA ^ setB)
        System.out.println("4. Symmetric Difference (A △ B):");
        HashSet<Integer> symmetricDiff = new HashSet<>(union);
        symmetricDiff.removeAll(intersection);
        System.out.println("   Symmetric Difference: " + symmetricDiff);
        System.out.println();
        
        // 5. Subset and Superset checks
        System.out.println("5. Subset and Superset Operations:");
        HashSet<Integer> subset = new HashSet<>(Arrays.asList(1, 2));
        HashSet<Integer> superset = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        
        System.out.println("   Subset: " + subset);
        System.out.println("   Superset: " + superset);
        System.out.println("   Is subset of A: " + setA.containsAll(subset)); // Python: subset.issubset(setA)
        System.out.println("   Is superset of A: " + superset.containsAll(setA)); // Python: superset.issuperset(setA)
        System.out.println();
        
        // 6. Practical example: Finding common elements
        System.out.println("6. Practical Example: Finding Common Elements");
        HashSet<String> studentsMath = new HashSet<>(Arrays.asList("Alice", "Bob", "Charlie", "David"));
        HashSet<String> studentsPhysics = new HashSet<>(Arrays.asList("Bob", "David", "Eve", "Frank"));
        
        System.out.println("   Math students: " + studentsMath);
        System.out.println("   Physics students: " + studentsPhysics);
        
        // Students taking both subjects
        HashSet<String> bothSubjects = new HashSet<>(studentsMath);
        bothSubjects.retainAll(studentsPhysics);
        System.out.println("   Students taking both: " + bothSubjects);
        
        // Students taking only math
        HashSet<String> onlyMath = new HashSet<>(studentsMath);
        onlyMath.removeAll(studentsPhysics);
        System.out.println("   Students taking only math: " + onlyMath);
        
        // Students taking only physics
        HashSet<String> onlyPhysics = new HashSet<>(studentsPhysics);
        onlyPhysics.removeAll(studentsMath);
        System.out.println("   Students taking only physics: " + onlyPhysics);
        
        // All unique students
        HashSet<String> allStudents = new HashSet<>(studentsMath);
        allStudents.addAll(studentsPhysics);
        System.out.println("   All unique students: " + allStudents);
    }
} 