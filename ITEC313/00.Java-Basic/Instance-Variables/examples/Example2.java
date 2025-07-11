/**
 * Example 2: Advanced Instance Variables
 * 
 * This example demonstrates advanced instance variable concepts:
 * - Arrays as instance variables
 * - Object references as instance variables
 * - Complex initialization patterns
 * - Instance variable scope and shadowing
 * 
 * For Python developers: This shows how Java handles object references
 * and arrays as instance variables, similar to Python's object attributes.
 */

public class Example2 {
    
    // Instance variables with arrays
    private int[] scores;              // Default: null
    private String[] courses;          // Default: null
    private double[] grades;           // Default: null
    
    // Instance variables with object references
    private String name;               // Default: null
    private Address address;           // Default: null
    private Course[] enrolledCourses;  // Default: null
    
    // Instance variables with explicit initialization
    private int maxCourses = 5;
    private boolean isFullTime = true;
    
    // Static instance variable (shared across all instances)
    private static int totalStudents = 0;
    
    /**
     * Inner class to demonstrate object references as instance variables
     */
    public static class Address {
        private String street;
        private String city;
        private String state;
        private String zipCode;
        
        public Address(String street, String city, String state, String zipCode) {
            this.street = street;
            this.city = city;
            this.state = state;
            this.zipCode = zipCode;
        }
        
        public String toString() {
            return street + ", " + city + ", " + state + " " + zipCode;
        }
    }
    
    /**
     * Inner class to demonstrate object references as instance variables
     */
    public static class Course {
        private String courseCode;
        private String courseName;
        private int credits;
        private double grade;
        
        public Course(String code, String name, int credits) {
            this.courseCode = code;
            this.courseName = name;
            this.credits = credits;
            this.grade = 0.0;
        }
        
        public void setGrade(double grade) {
            if (grade >= 0.0 && grade <= 4.0) {
                this.grade = grade;
            }
        }
        
        public String toString() {
            return courseCode + " - " + courseName + " (" + credits + " credits, Grade: " + grade + ")";
        }
    }
    
    /**
     * Constructor with complex initialization
     */
    public Example2(String name, String street, String city, String state, String zipCode) {
        this.name = name;
        this.address = new Address(street, city, state, zipCode);
        
        // Initialize arrays
        this.scores = new int[10];
        this.courses = new String[maxCourses];
        this.grades = new double[maxCourses];
        this.enrolledCourses = new Course[maxCourses];
        
        // Initialize arrays with default values
        for (int i = 0; i < scores.length; i++) {
            scores[i] = 0;
        }
        
        totalStudents++;
    }
    
    /**
     * Default constructor
     */
    public Example2() {
        // Initialize with default values
        this.name = "Unknown";
        this.address = new Address("Unknown", "Unknown", "Unknown", "00000");
        
        // Initialize arrays
        this.scores = new int[5];
        this.courses = new String[maxCourses];
        this.grades = new double[maxCourses];
        this.enrolledCourses = new Course[maxCourses];
        
        totalStudents++;
    }
    
    // Getter methods
    public String getName() {
        return name;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public int[] getScores() {
        return scores;
    }
    
    public String[] getCourses() {
        return courses;
    }
    
    public double[] getGrades() {
        return grades;
    }
    
    public Course[] getEnrolledCourses() {
        return enrolledCourses;
    }
    
    public int getMaxCourses() {
        return maxCourses;
    }
    
    public boolean isFullTime() {
        return isFullTime;
    }
    
    public static int getTotalStudents() {
        return totalStudents;
    }
    
    // Setter methods
    public void setName(String name) {
        this.name = name;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public void setScores(int[] scores) {
        if (scores != null && scores.length <= this.scores.length) {
            this.scores = scores;
        }
    }
    
    public void setFullTime(boolean fullTime) {
        isFullTime = fullTime;
    }
    
    /**
     * Method to add a course
     */
    public boolean addCourse(String courseCode, String courseName, int credits) {
        for (int i = 0; i < enrolledCourses.length; i++) {
            if (enrolledCourses[i] == null) {
                enrolledCourses[i] = new Course(courseCode, courseName, credits);
                courses[i] = courseCode;
                return true;
            }
        }
        System.out.println("Cannot add course: maximum courses reached");
        return false;
    }
    
    /**
     * Method to set grade for a course
     */
    public void setCourseGrade(String courseCode, double grade) {
        for (int i = 0; i < enrolledCourses.length; i++) {
            if (enrolledCourses[i] != null && enrolledCourses[i].courseCode.equals(courseCode)) {
                enrolledCourses[i].setGrade(grade);
                grades[i] = grade;
                return;
            }
        }
        System.out.println("Course not found: " + courseCode);
    }
    
    /**
     * Method to calculate GPA
     */
    public double calculateGPA() {
        double totalGradePoints = 0.0;
        int totalCredits = 0;
        
        for (int i = 0; i < enrolledCourses.length; i++) {
            if (enrolledCourses[i] != null) {
                totalGradePoints += grades[i] * enrolledCourses[i].credits;
                totalCredits += enrolledCourses[i].credits;
            }
        }
        
        return totalCredits > 0 ? totalGradePoints / totalCredits : 0.0;
    }
    
    /**
     * Method to display student information
     */
    public void displayInfo() {
        System.out.println("Student Information:");
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Full Time: " + isFullTime);
        System.out.println("GPA: " + String.format("%.2f", calculateGPA()));
        System.out.println("Enrolled Courses:");
        
        for (int i = 0; i < enrolledCourses.length; i++) {
            if (enrolledCourses[i] != null) {
                System.out.println("  " + enrolledCourses[i]);
            }
        }
        System.out.println();
    }
    
    /**
     * Method demonstrating instance variable scope
     */
    public void demonstrateScope() {
        System.out.println("Demonstrating instance variable scope...");
        
        // Local variable shadows instance variable
        String name = "Local Name";
        System.out.println("Local name: " + name);
        System.out.println("Instance name: " + this.name);
        
        // Modify instance variable
        this.name = "Updated Name";
        System.out.println("Updated instance name: " + this.name);
    }
    
    /**
     * Main method to demonstrate Example2
     */
    public static void main(String[] args) {
        System.out.println("=== Example 2: Advanced Instance Variables ===\n");
        
        // Create student with constructor
        System.out.println("Creating student with constructor...");
        Example2 student1 = new Example2("Alice Johnson", "123 Main St", "Boston", "MA", "02101");
        student1.addCourse("CS101", "Introduction to Programming", 3);
        student1.addCourse("MATH201", "Calculus I", 4);
        student1.setCourseGrade("CS101", 3.8);
        student1.setCourseGrade("MATH201", 3.5);
        student1.displayInfo();
        
        // Create student with default constructor
        System.out.println("Creating student with default constructor...");
        Example2 student2 = new Example2();
        student2.setName("Bob Smith");
        student2.setAddress(new Address("456 Oak Ave", "Chicago", "IL", "60601"));
        student2.addCourse("ENG101", "English Composition", 3);
        student2.setCourseGrade("ENG101", 3.2);
        student2.displayInfo();
        
        // Demonstrate scope
        student1.demonstrateScope();
        
        // Demonstrate static variable
        System.out.println("\nTotal students created: " + Example2.getTotalStudents());
        
        // Demonstrate array operations
        System.out.println("\nDemonstrating array operations...");
        int[] newScores = {85, 92, 78, 95, 88};
        student1.setScores(newScores);
        System.out.println("Updated scores for " + student1.getName());
    }
} 