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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Advanced: Advanced ArrayList Concepts
 * 
 * This example demonstrates advanced ArrayList concepts including:
 * - Nested ArrayLists (2D lists)
 * - ArrayList of custom objects
 * - Complex filtering and mapping operations
 * - Performance considerations
 * 
 * Python equivalent concepts:
 * - nested_list = [[1, 2], [3, 4]] -> ArrayList<ArrayList<Integer>>
 * - list comprehension -> manual loops or streams
 * - filter/map operations -> manual loops or streams
 */
public class Advanced {
    
    // Custom class to demonstrate ArrayList of objects
    static class Student {
        private String name;
        private int age;
        private double gpa;
        
        public Student(String name, int age, double gpa) {
            this.name = name;
            this.age = age;
            this.gpa = gpa;
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        public double getGpa() { return gpa; }
        
        @Override
        public String toString() {
            return name + " (Age: " + age + ", GPA: " + gpa + ")";
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Advanced ArrayList Concepts ===\n");
        
        // 1. Nested ArrayLists (2D ArrayList)
        nestedArrayLists();
        
        // 2. ArrayList of custom objects
        arrayListOfObjects();
        
        // 3. Complex operations
        complexOperations();
        
        // 4. Performance considerations
        performanceConsiderations();
    }
    
    /**
     * Demonstrates nested ArrayLists (2D lists)
     * Python equivalent: matrix = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
     */
    public static void nestedArrayLists() {
        System.out.println("1. Nested ArrayLists (2D Lists):");
        System.out.println("================================");
        
        // Create a 2D ArrayList
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        
        // Add rows to the matrix
        ArrayList<Integer> row1 = new ArrayList<>();
        row1.add(1);
        row1.add(2);
        row1.add(3);
        matrix.add(row1);
        
        ArrayList<Integer> row2 = new ArrayList<>();
        row2.add(4);
        row2.add(5);
        row2.add(6);
        matrix.add(row2);
        
        ArrayList<Integer> row3 = new ArrayList<>();
        row3.add(7);
        row3.add(8);
        row3.add(9);
        matrix.add(row3);
        
        System.out.println("2D ArrayList (Matrix):");
        for (ArrayList<Integer> row : matrix) {
            System.out.println(row);
        }
        
        // Access element at row 1, column 2
        int element = matrix.get(1).get(2);
        System.out.println("Element at [1][2]: " + element);
        
        // Calculate sum of all elements
        int sum = 0;
        for (ArrayList<Integer> row : matrix) {
            for (int num : row) {
                sum += num;
            }
        }
        System.out.println("Sum of all elements: " + sum);
        
        // Transpose the matrix
        ArrayList<ArrayList<Integer>> transpose = new ArrayList<>();
        int cols = matrix.get(0).size();
        int rows = matrix.size();
        
        for (int i = 0; i < cols; i++) {
            ArrayList<Integer> newRow = new ArrayList<>();
            for (int j = 0; j < rows; j++) {
                newRow.add(matrix.get(j).get(i));
            }
            transpose.add(newRow);
        }
        
        System.out.println("Transposed matrix:");
        for (ArrayList<Integer> row : transpose) {
            System.out.println(row);
        }
        System.out.println();
    }
    
    /**
     * Demonstrates ArrayList of custom objects
     * Python equivalent: students = [Student("Alice", 20, 3.8), ...]
     */
    public static void arrayListOfObjects() {
        System.out.println("2. ArrayList of Custom Objects:");
        System.out.println("==============================");
        
        // Create ArrayList of Student objects
        ArrayList<Student> students = new ArrayList<>();
        
        students.add(new Student("Alice", 20, 3.8));
        students.add(new Student("Bob", 22, 3.5));
        students.add(new Student("Charlie", 19, 3.9));
        students.add(new Student("Diana", 21, 3.7));
        students.add(new Student("Eve", 23, 3.6));
        
        System.out.println("All students:");
        for (Student student : students) {
            System.out.println(student);
        }
        
        // Find students with GPA > 3.7
        ArrayList<Student> highGpaStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getGpa() > 3.7) {
                highGpaStudents.add(student);
            }
        }
        
        System.out.println("\nStudents with GPA > 3.7:");
        for (Student student : highGpaStudents) {
            System.out.println(student);
        }
        
        // Find average GPA
        double totalGpa = 0;
        for (Student student : students) {
            totalGpa += student.getGpa();
        }
        double averageGpa = totalGpa / students.size();
        System.out.println("\nAverage GPA: " + averageGpa);
        
        // Find student with highest GPA
        Student topStudent = students.get(0);
        for (Student student : students) {
            if (student.getGpa() > topStudent.getGpa()) {
                topStudent = student;
            }
        }
        System.out.println("Student with highest GPA: " + topStudent);
        System.out.println();
    }
    
    /**
     * Demonstrates complex operations on ArrayLists
     * Shows filtering, mapping, and other advanced operations
     */
    public static void complexOperations() {
        System.out.println("3. Complex Operations:");
        System.out.println("====================");
        
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            numbers.add(i);
        }
        
        System.out.println("Original numbers: " + numbers);
        
        // Filter even numbers (Python: [x for x in numbers if x % 2 == 0])
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        for (int num : numbers) {
            if (num % 2 == 0) {
                evenNumbers.add(num);
            }
        }
        System.out.println("Even numbers: " + evenNumbers);
        
        // Square all numbers (Python: [x**2 for x in numbers])
        ArrayList<Integer> squaredNumbers = new ArrayList<>();
        for (int num : numbers) {
            squaredNumbers.add(num * num);
        }
        System.out.println("Squared numbers: " + squaredNumbers);
        
        // Find prime numbers
        ArrayList<Integer> primeNumbers = new ArrayList<>();
        for (int num : numbers) {
            if (isPrime(num)) {
                primeNumbers.add(num);
            }
        }
        System.out.println("Prime numbers: " + primeNumbers);
        
        // Group numbers by range
        ArrayList<ArrayList<Integer>> groupedNumbers = new ArrayList<>();
        ArrayList<Integer> small = new ArrayList<>();
        ArrayList<Integer> medium = new ArrayList<>();
        ArrayList<Integer> large = new ArrayList<>();
        
        for (int num : numbers) {
            if (num <= 7) {
                small.add(num);
            } else if (num <= 14) {
                medium.add(num);
            } else {
                large.add(num);
            }
        }
        
        groupedNumbers.add(small);
        groupedNumbers.add(medium);
        groupedNumbers.add(large);
        
        System.out.println("Grouped numbers:");
        System.out.println("Small (1-7): " + small);
        System.out.println("Medium (8-14): " + medium);
        System.out.println("Large (15-20): " + large);
        System.out.println();
    }
    
    /**
     * Demonstrates performance considerations with ArrayLists
     * Shows when to use ArrayList vs other data structures
     */
    public static void performanceConsiderations() {
        System.out.println("4. Performance Considerations:");
        System.out.println("============================");
        
        // Demonstrate initial capacity
        ArrayList<Integer> listWithCapacity = new ArrayList<>(1000);
        System.out.println("Created ArrayList with initial capacity of 1000");
        
        // Adding elements at the end (fast)
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            listWithCapacity.add(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time to add 10,000 elements at end: " + (endTime - startTime) + "ms");
        
        // Adding elements at the beginning (slow)
        ArrayList<Integer> listForBeginning = new ArrayList<>();
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            listForBeginning.add(0, i);  // Insert at beginning
        }
        endTime = System.currentTimeMillis();
        System.out.println("Time to add 1,000 elements at beginning: " + (endTime - startTime) + "ms");
        
        // Searching in ArrayList
        startTime = System.currentTimeMillis();
        boolean found = listWithCapacity.contains(5000);
        endTime = System.currentTimeMillis();
        System.out.println("Time to search for element: " + (endTime - startTime) + "ms");
        System.out.println("Found 5000: " + found);
        
        // Memory usage consideration
        System.out.println("\nMemory considerations:");
        System.out.println("- ArrayList uses more memory than arrays");
        System.out.println("- Each element is an object (even primitives are boxed)");
        System.out.println("- Good for frequent insertions/deletions");
        System.out.println("- Use arrays for fixed-size, performance-critical data");
        System.out.println();
    }
    
    /**
     * Helper method to check if a number is prime
     */
    private static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
} 