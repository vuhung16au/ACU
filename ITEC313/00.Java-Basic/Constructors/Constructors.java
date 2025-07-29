/**
 * Constructors.java
 * 
 * This program demonstrates constructors in Java:
 * - Default constructors
 * - Parameterized constructors
 * - Constructor overloading
 * - Constructor chaining
 * - Copy constructors
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
public class Constructors {
    
    /**
     * Main method - entry point of the program
     * Demonstrates various constructor types and their usage
     */
    public static void main(String[] args) {
        System.out.println("=== Java Constructors Demonstration ===\n");
        
        // Demonstrate default constructor
        demonstrateDefaultConstructor();
        
        // Demonstrate parameterized constructor
        demonstrateParameterizedConstructor();
        
        // Demonstrate constructor chaining
        demonstrateConstructorChaining();
        
        // Demonstrate copy constructor
        demonstrateCopyConstructor();
        
        // Demonstrate constructor overloading
        demonstrateConstructorOverloading();
        
        System.out.println("\n=== Constructor Demonstration Complete ===");
    }
    
    /**
     * Demonstrates the use of default constructors
     * In Python: This would be like having no __init__ method or a simple __init__(self)
     */
    private static void demonstrateDefaultConstructor() {
        System.out.println("1. Default Constructor Example:");
        System.out.println("   ----------------------------");
        
        // Create objects using default constructor
        Student student1 = new Student();
        Student student2 = new Student();
        
        System.out.println("   Student 1: " + student1);
        System.out.println("   Student 2: " + student2);
        System.out.println();
    }
    
    /**
     * Demonstrates parameterized constructors
     * In Python: This would be like __init__(self, name, age, grade)
     */
    private static void demonstrateParameterizedConstructor() {
        System.out.println("2. Parameterized Constructor Example:");
        System.out.println("   ---------------------------------");
        
        // Create objects with specific values
        Student student1 = new Student("Alice Johnson", 20, 3.8);
        Student student2 = new Student("Bob Smith", 19, 3.5);
        
        System.out.println("   Student 1: " + student1);
        System.out.println("   Student 2: " + student2);
        System.out.println();
    }
    
    /**
     * Demonstrates constructor chaining (one constructor calling another)
     * In Python: This would be like calling self.__init__() from within __init__
     */
    private static void demonstrateConstructorChaining() {
        System.out.println("3. Constructor Chaining Example:");
        System.out.println("   -----------------------------");
        
        // Create object using constructor that chains to another
        Student student = new Student("Charlie Brown", 21);
        
        System.out.println("   Student: " + student);
        System.out.println("   Note: Constructor chained to set default grade");
        System.out.println();
    }
    
    /**
     * Demonstrates copy constructor (creating a new object from an existing one)
     * In Python: This would be like creating a new object with the same attributes
     */
    private static void demonstrateCopyConstructor() {
        System.out.println("4. Copy Constructor Example:");
        System.out.println("   -------------------------");
        
        // Create original student
        Student original = new Student("Diana Prince", 22, 3.9);
        System.out.println("   Original: " + original);
        
        // Create a copy using copy constructor
        Student copy = new Student(original);
        System.out.println("   Copy: " + copy);
        
        // Modify the copy to show they are separate objects
        copy.setGrade(4.0);
        System.out.println("   After modifying copy:");
        System.out.println("   Original: " + original);
        System.out.println("   Copy: " + copy);
        System.out.println();
    }
    
    /**
     * Demonstrates constructor overloading (multiple constructors with different parameters)
     * In Python: This would require using default parameters or *args/**kwargs
     */
    private static void demonstrateConstructorOverloading() {
        System.out.println("5. Constructor Overloading Example:");
        System.out.println("   --------------------------------");
        
        // Create students using different constructors
        Student student1 = new Student("Eve Wilson");
        Student student2 = new Student("Frank Miller", 23);
        Student student3 = new Student("Grace Lee", 24, 3.7);
        
        System.out.println("   Student 1 (name only): " + student1);
        System.out.println("   Student 2 (name, age): " + student2);
        System.out.println("   Student 3 (name, age, grade): " + student3);
        System.out.println();
    }
}

/**
 * Student class demonstrating various constructor types
 * 
 * For Python developers: This class shows how Java constructors work compared to
 * Python's __init__ method. Java constructors can be overloaded (multiple versions
 * with different parameters), which is not possible in Python's __init__.
 */
class Student {
    // Instance variables (fields)
    private String name;
    private int age;
    private double grade;
    
    // Static variable to track student count
    private static int studentCount = 0;
    
    /**
     * Default constructor
     * In Python: This would be like __init__(self) with default values
     */
    public Student() {
        this.name = "Unknown";
        this.age = 18;
        this.grade = 0.0;
        studentCount++;
        System.out.println("   Default constructor called for student #" + studentCount);
    }
    
    /**
     * Parameterized constructor with all fields
     * In Python: This would be like __init__(self, name, age, grade)
     */
    public Student(String name, int age, double grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
        studentCount++;
        System.out.println("   Full parameterized constructor called for student #" + studentCount);
    }
    
    /**
     * Constructor with name and age only (chains to full constructor)
     * In Python: This would be like __init__(self, name, age, grade=0.0)
     */
    public Student(String name, int age) {
        // Constructor chaining - calls the full constructor with default grade
        this(name, age, 0.0);
        System.out.println("   Chained constructor called (name, age) -> (name, age, grade)");
    }
    
    /**
     * Constructor with name only (chains to name and age constructor)
     * In Python: This would be like __init__(self, name, age=18, grade=0.0)
     */
    public Student(String name) {
        // Constructor chaining - calls the name and age constructor
        this(name, 18);
        System.out.println("   Chained constructor called (name) -> (name, age)");
    }
    
    /**
     * Copy constructor - creates a new Student from an existing one
     * In Python: This would be like creating a new object with the same attributes
     */
    public Student(Student other) {
        this.name = other.name;
        this.age = other.age;
        this.grade = other.grade;
        studentCount++;
        System.out.println("   Copy constructor called for student #" + studentCount);
    }
    
    // Getter and setter methods
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public double getGrade() {
        return grade;
    }
    
    public void setGrade(double grade) {
        this.grade = grade;
    }
    
    /**
     * Override toString method for better object representation
     * In Python: This would be like overriding __str__ method
     */
    @Override
    public String toString() {
        return String.format("Student{name='%s', age=%d, grade=%.1f}", name, age, grade);
    }
    
    /**
     * Static method to get total number of students created
     * In Python: This would be like a class method or static method
     */
    public static int getStudentCount() {
        return studentCount;
    }
} 