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
import java.io.*;
import java.util.Date;

/**
 * Example1.java - Simple Object Serialization
 * 
 * This example demonstrates basic object serialization using a Student class.
 * Shows how to serialize and deserialize a simple object with various data types.
 * 
 * Key differences from Python:
 * - Java requires implementing Serializable interface
 * - Java uses ObjectOutputStream/ObjectInputStream
 * - Java handles primitive types and objects differently
 * 
 * @author ITEC313 - Object-Oriented Programming
 */
public class Example1 {
    
    /**
     * Student class demonstrating serialization
     * In Python, you'd use dataclasses or regular classes with pickle
     */
    public static class Student implements Serializable {
        private static final long serialVersionUID = 1L; // Version control for serialization
        
        private String name;
        private int studentId;
        private double gpa;
        private Date enrollmentDate;
        private transient String temporaryNotes; // Won't be serialized
        
        public Student(String name, int studentId, double gpa) {
            this.name = name;
            this.studentId = studentId;
            this.gpa = gpa;
            this.enrollmentDate = new Date();
            this.temporaryNotes = "Temporary notes";
        }
        
        // Getters
        public String getName() { return name; }
        public int getStudentId() { return studentId; }
        public double getGpa() { return gpa; }
        public Date getEnrollmentDate() { return enrollmentDate; }
        public String getTemporaryNotes() { return temporaryNotes; }
        
        @Override
        public String toString() {
            return String.format("Student{name='%s', id=%d, gpa=%.2f, enrolled=%s, notes='%s'}", 
                               name, studentId, gpa, enrollmentDate, temporaryNotes);
        }
    }
    
    /**
     * Demonstrates serializing a single Student object
     */
    public static void serializeStudent() {
        System.out.println("=== Serializing Student Object ===");
        
        Student student = new Student("John Doe", 12345, 3.85);
        System.out.println("Original student: " + student);
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("data/student.ser"))) {
            
            oos.writeObject(student);
            System.out.println("✓ Student serialized successfully");
            
        } catch (IOException e) {
            System.err.println("✗ Serialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates deserializing a Student object
     */
    public static void deserializeStudent() {
        System.out.println("\n=== Deserializing Student Object ===");
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("data/student.ser"))) {
            
            Student student = (Student) ois.readObject();
            System.out.println("✓ Student deserialized: " + student);
            System.out.println("  Note: temporaryNotes is null (transient field)");
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Deserialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates serialization with different data types
     */
    public static void demonstrateDataTypes() {
        System.out.println("\n=== Serialization with Different Data Types ===");
        
        // Create an array of students with different data types
        Student[] students = {
            new Student("Alice", 1001, 4.0),
            new Student("Bob", 1002, 3.2),
            new Student("Charlie", 1003, 3.8)
        };
        
        System.out.println("Original students:");
        for (Student s : students) {
            System.out.println("  " + s);
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("data/students_array.ser"))) {
            
            oos.writeObject(students);
            System.out.println("✓ Students array serialized");
            
        } catch (IOException e) {
            System.err.println("✗ Array serialization failed: " + e.getMessage());
        }
        
        // Deserialize the array
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("data/students_array.ser"))) {
            
            Student[] restoredStudents = (Student[]) ois.readObject();
            System.out.println("✓ Students array deserialized:");
            for (Student s : restoredStudents) {
                System.out.println("  " + s);
            }
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Array deserialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Main method to run the example
     */
    public static void main(String[] args) {
        System.out.println("📚 Example 1: Simple Object Serialization");
        System.out.println("==========================================");
        
        // Create data directory if it doesn't exist
        new File("data").mkdirs();
        
        serializeStudent();
        deserializeStudent();
        demonstrateDataTypes();
        
        System.out.println("\n✅ Example 1 complete!");
    }
} 