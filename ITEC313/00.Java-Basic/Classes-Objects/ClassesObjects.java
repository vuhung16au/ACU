/**
 * ClassesObjects.java
 * 
 * This program demonstrates classes and objects in Java:
 * - Class definition and structure
 * - Object creation and instantiation
 * - Instance variables and methods
 * - Constructor usage
 * - Object-oriented principles
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
public class ClassesObjects {
    
    public static void main(String[] args) {
        System.out.println("=== Classes and Objects Demo ===\n");
        
        // Demonstrate basic object creation and usage
        demonstrateStudentClass();
        
        // Demonstrate multiple objects
        demonstrateMultipleObjects();
        
        // Demonstrate object interaction
        demonstrateObjectInteraction();
        
        System.out.println("\n=== Demo Complete ===");
    }
    
    public static void demonstrateStudentClass() {
        System.out.println("1. BASIC OBJECT CREATION");
        System.out.println("========================");
        
        // Create a Student object
        Student student1 = new Student();
        
        // Set properties using methods
        student1.setName("Alice Johnson");
        student1.setAge(20);
        student1.setStudentId("S12345");
        student1.setMajor("Computer Science");
        
        // Display student information
        System.out.println("Student created:");
        student1.displayInfo();
        
        // Use some methods
        student1.attendClass("Java Programming");
        student1.submitAssignment("OOP Assignment 1");
        
        System.out.printf("GPA: %.2f%n", student1.getGPA());
        System.out.println();
    }
    
    public static void demonstrateMultipleObjects() {
        System.out.println("2. MULTIPLE OBJECTS");
        System.out.println("===================");
        
        // Create multiple Student objects
        Student[] students = {
            new Student("Bob Smith", 19, "S12346", "Mathematics"),
            new Student("Carol Davis", 21, "S12347", "Physics"),
            new Student("David Wilson", 20, "S12348", "Chemistry")
        };
        
        System.out.println("Multiple students created:");
        for (int i = 0; i < students.length; i++) {
            System.out.printf("\nStudent %d:%n", i + 1);
            students[i].displayInfo();
        }
        
        // Demonstrate different states
        students[0].addGrade(85.5);
        students[0].addGrade(92.0);
        students[1].addGrade(78.5);
        students[2].addGrade(95.0);
        students[2].addGrade(88.5);
        
        System.out.println("\nAfter adding grades:");
        for (Student student : students) {
            System.out.printf("%s: GPA = %.2f%n", 
                            student.getName(), student.getGPA());
        }
        
        System.out.println();
    }
    
    public static void demonstrateObjectInteraction() {
        System.out.println("3. OBJECT INTERACTION");
        System.out.println("=====================");
        
        // Create a Course object
        Course javaCourse = new Course("ITEC313", "Advanced Programming Concepts", 3);
        
        // Create students
        Student student1 = new Student("Vu Nguyen", 19, "S16111", "IT");
        Student student2 = new Student("Frank Miller", 20, "S12350", "IT");
        
        // Enroll students in course
        javaCourse.enrollStudent(student1);
        javaCourse.enrollStudent(student2);
        
        // Display course information
        javaCourse.displayCourseInfo();
        
        // Students attend classes
        student1.attendClass(javaCourse.getCourseName());
        student2.attendClass(javaCourse.getCourseName());
        
        System.out.println();
    }
}

/**
 * Student class demonstrating basic OOP concepts
 */
class Student {
    // Instance variables (private for encapsulation)
    private String name;
    private int age;
    private String studentId;
    private String major;
    private double[] grades;
    private int gradeCount;
    private int maxGrades;
    
    // Default constructor
    public Student() {
        this.name = "Unknown";
        this.age = 0;
        this.studentId = "Unknown";
        this.major = "Undeclared";
        this.maxGrades = 10;
        this.grades = new double[maxGrades];
        this.gradeCount = 0;
    }
    
    // Parameterized constructor
    public Student(String name, int age, String studentId, String major) {
        this.name = name;
        this.age = age;
        this.studentId = studentId;
        this.major = major;
        this.maxGrades = 10;
        this.grades = new double[maxGrades];
        this.gradeCount = 0;
    }
    
    // Getter methods
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public String getMajor() {
        return major;
    }
    
    // Setter methods
    public void setName(String name) {
        this.name = name;
    }
    
    public void setAge(int age) {
        if (age >= 0 && age <= 150) {
            this.age = age;
        }
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public void setMajor(String major) {
        this.major = major;
    }
    
    // Business methods
    public void addGrade(double grade) {
        if (gradeCount < maxGrades && grade >= 0 && grade <= 100) {
            grades[gradeCount] = grade;
            gradeCount++;
            System.out.printf("%s received grade: %.1f%n", name, grade);
        }
    }
    
    public double getGPA() {
        if (gradeCount == 0) {
            return 0.0;
        }
        
        double sum = 0;
        for (int i = 0; i < gradeCount; i++) {
            sum += grades[i];
        }
        return sum / gradeCount;
    }
    
    public void attendClass(String className) {
        System.out.printf("%s attended %s class%n", name, className);
    }
    
    public void submitAssignment(String assignmentName) {
        System.out.printf("%s submitted %s%n", name, assignmentName);
    }
    
    public void displayInfo() {
        System.out.printf("Name: %s%n", name);
        System.out.printf("Age: %d%n", age);
        System.out.printf("Student ID: %s%n", studentId);
        System.out.printf("Major: %s%n", major);
        System.out.printf("Current GPA: %.2f%n", getGPA());
        System.out.printf("Grades recorded: %d%n", gradeCount);
    }
}

/**
 * Course class to demonstrate object relationships
 */
class Course {
    private String courseCode;
    private String courseName;
    private int creditHours;
    private Student[] enrolledStudents;
    private int studentCount;
    private int maxStudents;
    
    public Course(String courseCode, String courseName, int creditHours) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.creditHours = creditHours;
        this.maxStudents = 30;
        this.enrolledStudents = new Student[maxStudents];
        this.studentCount = 0;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public void enrollStudent(Student student) {
        if (studentCount < maxStudents) {
            enrolledStudents[studentCount] = student;
            studentCount++;
            System.out.printf("%s enrolled in %s%n", 
                            student.getName(), courseName);
        } else {
            System.out.println("Course is full!");
        }
    }
    
    public void displayCourseInfo() {
        System.out.printf("Course: %s - %s%n", courseCode, courseName);
        System.out.printf("Credit Hours: %d%n", creditHours);
        System.out.printf("Enrolled Students: %d/%d%n", studentCount, maxStudents);
        
        if (studentCount > 0) {
            System.out.println("Student List:");
            for (int i = 0; i < studentCount; i++) {
                System.out.printf("  %d. %s (%s)%n", 
                                i + 1, 
                                enrolledStudents[i].getName(),
                                enrolledStudents[i].getStudentId());
            }
        }
    }
}
