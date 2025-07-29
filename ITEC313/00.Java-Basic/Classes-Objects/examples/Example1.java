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
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic Class and Object Creation ===\n");
        
        // Create objects using default constructor
        // In Python: person1 = Person()
        Person person1 = new Person();
        
        // Create objects using parameterized constructor
        // In Python: person2 = Person("Alice", 25)
        Person person2 = new Person("Alice", 25);
        Person person3 = new Person("Bob", 30);
        
        // Demonstrate object methods
        person1.displayInfo();
        person2.displayInfo();
        person3.displayInfo();
        
        // Demonstrate method calls
        person2.haveBirthday();
        person2.displayInfo();
        
        person3.setAge(35);
        person3.setName("Robert");
        person3.displayInfo();
        
        System.out.println("\n=== Example 1 Complete ===");
    }
}

/**
 * Simple Person class demonstrating basic OOP concepts
 * 
 * In Python, this would be:
 * class Person:
 *     def __init__(self, name="Unknown", age=0):
 *         self.name = name
 *         self.age = age
 */
class Person {
    // Instance variables (fields)
    // In Python: these would be defined in __init__
    private String name;
    private int age;
    
    // Default constructor
    // In Python: def __init__(self, name="Unknown", age=0):
    public Person() {
        this.name = "Unknown";
        this.age = 0;
        System.out.println("Default constructor called - Person created with default values");
    }
    
    // Parameterized constructor
    // In Python: def __init__(self, name, age):
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("Parameterized constructor called - Person created: " + name);
    }
    
    // Getter methods (accessors)
    // In Python: @property def name(self): return self.name
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    // Setter methods (mutators)
    // In Python: def set_name(self, name): self.name = name
    public void setName(String name) {
        this.name = name;
    }
    
    public void setAge(int age) {
        if (age >= 0 && age <= 150) {
            this.age = age;
        } else {
            System.out.println("Invalid age: " + age + " (must be 0-150)");
        }
    }
    
    // Business methods
    // In Python: def have_birthday(self): self.age += 1
    public void haveBirthday() {
        this.age++;
        System.out.println(name + " had a birthday! Age is now " + age);
    }
    
    // Method to display object information
    // In Python: def display_info(self): print(f"Name: {self.name}, Age: {self.age}")
    public void displayInfo() {
        System.out.println("Person Info:");
        System.out.println("  Name: " + name);
        System.out.println("  Age: " + age);
        System.out.println();
    }
} 