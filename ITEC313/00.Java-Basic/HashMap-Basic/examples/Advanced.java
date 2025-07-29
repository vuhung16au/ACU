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
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Advanced {
    
    // Custom class to demonstrate objects as HashMap values
    static class Student {
        private String name;
        private int age;
        private List<String> courses;
        
        public Student(String name, int age) {
            this.name = name;
            this.age = age;
            this.courses = new ArrayList<>();
        }
        
        public void addCourse(String course) {
            courses.add(course);
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        public List<String> getCourses() { return courses; }
        
        @Override
        public String toString() {
            return "Student{name='" + name + "', age=" + age + ", courses=" + courses + "}";
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Advanced HashMap Examples ===\n");
        
        // Example 1: Nested HashMaps
        nestedHashMapExample();
        
        // Example 2: HashMap with custom objects
        customObjectHashMapExample();
        
        // Example 3: HashMap with complex data structures
        complexDataStructureExample();
        
        // Example 4: Performance considerations
        performanceExample();
        
        System.out.println("\n=== Advanced examples completed! ===");
    }
    
    /**
     * Demonstrates nested HashMaps (HashMap of HashMaps)
     * Python equivalent: nested_dict = {'dept1': {'course1': 'info1', 'course2': 'info2'}}
     */
    public static void nestedHashMapExample() {
        System.out.println("1. Nested HashMaps Example:");
        
        // Create a nested HashMap to store department courses
        HashMap<String, HashMap<String, String>> departmentCourses = new HashMap<>();
        
        // Computer Science department
        HashMap<String, String> csCourses = new HashMap<>();
        csCourses.put("CS101", "Introduction to Programming");
        csCourses.put("CS201", "Data Structures");
        csCourses.put("CS301", "Algorithms");
        departmentCourses.put("Computer Science", csCourses);
        
        // Mathematics department
        HashMap<String, String> mathCourses = new HashMap<>();
        mathCourses.put("MATH101", "Calculus I");
        mathCourses.put("MATH201", "Linear Algebra");
        mathCourses.put("MATH301", "Differential Equations");
        departmentCourses.put("Mathematics", mathCourses);
        
        // Physics department
        HashMap<String, String> physicsCourses = new HashMap<>();
        physicsCourses.put("PHYS101", "Mechanics");
        physicsCourses.put("PHYS201", "Electromagnetism");
        physicsCourses.put("PHYS301", "Quantum Physics");
        departmentCourses.put("Physics", physicsCourses);
        
        System.out.println("   Department courses structure:");
        for (Map.Entry<String, HashMap<String, String>> deptEntry : departmentCourses.entrySet()) {
            String deptName = deptEntry.getKey();
            HashMap<String, String> courses = deptEntry.getValue();
            
            System.out.println("   " + deptName + ":");
            for (Map.Entry<String, String> courseEntry : courses.entrySet()) {
                String courseCode = courseEntry.getKey();
                String courseName = courseEntry.getValue();
                System.out.println("     " + courseCode + ": " + courseName);
            }
        }
        
        // Accessing nested values
        String cs101Name = departmentCourses.get("Computer Science").get("CS101");
        System.out.println("   CS101 course name: " + cs101Name);
        
        // Adding a new course to existing department
        departmentCourses.get("Computer Science").put("CS401", "Software Engineering");
        
        // Adding a new department
        HashMap<String, String> bioCourses = new HashMap<>();
        bioCourses.put("BIO101", "Introduction to Biology");
        bioCourses.put("BIO201", "Cell Biology");
        departmentCourses.put("Biology", bioCourses);
        
        System.out.println("   After modifications:");
        System.out.println("   " + departmentCourses);
        
        System.out.println();
    }
    
    /**
     * Demonstrates HashMap with custom objects as values
     * Python equivalent: students = {'id1': Student('name1', age1), 'id2': Student('name2', age2)}
     */
    public static void customObjectHashMapExample() {
        System.out.println("2. HashMap with Custom Objects:");
        
        // Create HashMap with Student objects as values
        HashMap<String, Student> students = new HashMap<>();
        
        // Create and add students
        Student alice = new Student("Alice", 20);
        alice.addCourse("CS101");
        alice.addCourse("MATH101");
        students.put("S001", alice);
        
        Student bob = new Student("Bob", 22);
        bob.addCourse("CS201");
        bob.addCourse("PHYS101");
        bob.addCourse("MATH201");
        students.put("S002", bob);
        
        Student charlie = new Student("Charlie", 19);
        charlie.addCourse("BIO101");
        charlie.addCourse("CHEM101");
        students.put("S003", charlie);
        
        System.out.println("   Student records:");
        for (Map.Entry<String, Student> entry : students.entrySet()) {
            String studentId = entry.getKey();
            Student student = entry.getValue();
            System.out.println("   " + studentId + ": " + student);
        }
        
        // Find students taking a specific course
        System.out.println("   Students taking CS101:");
        for (Map.Entry<String, Student> entry : students.entrySet()) {
            Student student = entry.getValue();
            if (student.getCourses().contains("CS101")) {
                System.out.println("     " + student.getName() + " (ID: " + entry.getKey() + ")");
            }
        }
        
        // Update student information
        Student aliceUpdated = students.get("S001");
        aliceUpdated.addCourse("CS301");
        students.put("S001", aliceUpdated);
        
        System.out.println("   After updating Alice's courses:");
        System.out.println("   " + students.get("S001"));
        
        System.out.println();
    }
    
    /**
     * Demonstrates complex data structures with HashMaps
     * Shows HashMap with Lists, Sets, and other collections as values
     */
    public static void complexDataStructureExample() {
        System.out.println("3. Complex Data Structures:");
        
        // HashMap with List as values (multiple values per key)
        HashMap<String, List<String>> studentGrades = new HashMap<>();
        
        // Initialize lists for each student
        studentGrades.put("Alice", new ArrayList<>());
        studentGrades.put("Bob", new ArrayList<>());
        studentGrades.put("Charlie", new ArrayList<>());
        
        // Add grades for each student
        studentGrades.get("Alice").add("A");
        studentGrades.get("Alice").add("B+");
        studentGrades.get("Alice").add("A-");
        
        studentGrades.get("Bob").add("B");
        studentGrades.get("Bob").add("C+");
        studentGrades.get("Bob").add("B-");
        
        studentGrades.get("Charlie").add("A");
        studentGrades.get("Charlie").add("A");
        studentGrades.get("Charlie").add("A+");
        
        System.out.println("   Student grades:");
        for (Map.Entry<String, List<String>> entry : studentGrades.entrySet()) {
            String student = entry.getKey();
            List<String> grades = entry.getValue();
            System.out.println("   " + student + ": " + grades);
        }
        
        // Calculate average grade for each student
        System.out.println("   Grade analysis:");
        for (Map.Entry<String, List<String>> entry : studentGrades.entrySet()) {
            String student = entry.getKey();
            List<String> grades = entry.getValue();
            
            double totalPoints = 0;
            for (String grade : grades) {
                switch (grade) {
                    case "A+": totalPoints += 4.3; break;
                    case "A":  totalPoints += 4.0; break;
                    case "A-": totalPoints += 3.7; break;
                    case "B+": totalPoints += 3.3; break;
                    case "B":  totalPoints += 3.0; break;
                    case "B-": totalPoints += 2.7; break;
                    case "C+": totalPoints += 2.3; break;
                    case "C":  totalPoints += 2.0; break;
                    default:   totalPoints += 1.0; break;
                }
            }
            double average = totalPoints / grades.size();
            System.out.println("     " + student + " GPA: " + String.format("%.2f", average));
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates performance considerations and best practices
     * Shows when to use different HashMap operations
     */
    public static void performanceExample() {
        System.out.println("4. Performance Considerations:");
        
        // Create a large HashMap to demonstrate performance
        HashMap<Integer, String> largeMap = new HashMap<>();
        
        // Populate with many entries
        for (int i = 1; i <= 1000; i++) {
            largeMap.put(i, "Value" + i);
        }
        
        System.out.println("   Large HashMap size: " + largeMap.size());
        
        // Performance test: containsKey vs containsValue
        long startTime = System.nanoTime();
        boolean hasKey = largeMap.containsKey(500);
        long keyTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean hasValue = largeMap.containsValue("Value500");
        long valueTime = System.nanoTime() - startTime;
        
        System.out.println("   containsKey(500) time: " + keyTime + " ns");
        System.out.println("   containsValue('Value500') time: " + valueTime + " ns");
        System.out.println("   containsKey is " + (valueTime / keyTime) + "x faster than containsValue");
        
        // Memory usage demonstration
        System.out.println("   Memory considerations:");
        System.out.println("   - HashMap uses more memory than arrays");
        System.out.println("   - Trade-off: speed vs memory");
        System.out.println("   - Good for frequent lookups");
        System.out.println("   - Not ideal for ordered data");
        
        System.out.println();
    }
} 