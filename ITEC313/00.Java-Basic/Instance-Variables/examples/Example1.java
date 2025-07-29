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
public class Example1 {
    
    // Instance variables with different data types
    private String studentName;    // Default: null
    private int studentId;         // Default: 0
    private double gpa;           // Default: 0.0
    private boolean isEnrolled;   // Default: false
    
    // Instance variable with explicit initialization
    private String major = "Computer Science";
    
    /**
     * Constructor to initialize instance variables
     * In Python: def __init__(self, name, id, gpa):
     */
    public Example1(String name, int id, double gpa) {
        this.studentName = name;
        this.studentId = id;
        this.gpa = gpa;
        this.isEnrolled = true;
    }
    
    /**
     * Default constructor - shows default initialization
     */
    public Example1() {
        // All instance variables get default values automatically
        System.out.println("Default values:");
        System.out.println("studentName: " + studentName);
        System.out.println("studentId: " + studentId);
        System.out.println("gpa: " + gpa);
        System.out.println("isEnrolled: " + isEnrolled);
        System.out.println("major: " + major);
    }
    
    // Getter methods
    public String getStudentName() {
        return studentName;
    }
    
    public int getStudentId() {
        return studentId;
    }
    
    public double getGpa() {
        return gpa;
    }
    
    public boolean isEnrolled() {
        return isEnrolled;
    }
    
    public String getMajor() {
        return major;
    }
    
    // Setter methods
    public void setStudentName(String name) {
        this.studentName = name;
    }
    
    public void setStudentId(int id) {
        this.studentId = id;
    }
    
    public void setGpa(double gpa) {
        if (gpa >= 0.0 && gpa <= 4.0) {
            this.gpa = gpa;
        } else {
            System.out.println("Invalid GPA: " + gpa);
        }
    }
    
    public void setEnrolled(boolean enrolled) {
        isEnrolled = enrolled;
    }
    
    public void setMajor(String major) {
        this.major = major;
    }
    
    /**
     * Method to display student information
     */
    public void displayInfo() {
        System.out.println("Student Information:");
        System.out.println("Name: " + studentName);
        System.out.println("ID: " + studentId);
        System.out.println("GPA: " + gpa);
        System.out.println("Major: " + major);
        System.out.println("Enrolled: " + isEnrolled);
        System.out.println();
    }
    
    /**
     * Method to update GPA
     */
    public void updateGpa(double newGpa) {
        if (newGpa >= 0.0 && newGpa <= 4.0) {
            double oldGpa = this.gpa;
            this.gpa = newGpa;
            System.out.println("GPA updated from " + oldGpa + " to " + newGpa);
        } else {
            System.out.println("Invalid GPA: " + newGpa);
        }
    }
    
    /**
     * Main method to demonstrate Example1
     */
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic Instance Variables ===\n");
        
        // Create student with constructor
        System.out.println("Creating student with constructor...");
        Example1 student1 = new Example1("John Doe", 12345, 3.8);
        student1.displayInfo();
        
        // Create student with default constructor
        System.out.println("Creating student with default constructor...");
        Example1 student2 = new Example1();
        
        // Set values using setters
        student2.setStudentName("Jane Smith");
        student2.setStudentId(67890);
        student2.setGpa(3.9);
        student2.setMajor("Mathematics");
        student2.displayInfo();
        
        // Demonstrate instance variable modifications
        System.out.println("Demonstrating modifications...");
        student1.updateGpa(3.9);
        student1.setEnrolled(false);
        student1.displayInfo();
        
        // Demonstrate validation
        System.out.println("Demonstrating validation...");
        student2.setGpa(5.0);  // Invalid GPA
        student2.setGpa(-1.0); // Invalid GPA
    }
} 