/**
 * Advanced.java - Complex Inheritance Scenarios
 * 
 * This example demonstrates:
 * - Generic classes with inheritance
 * - Nested classes and inheritance
 * - Design patterns with inheritance
 * - Complex method overriding scenarios
 * 
 * Key Differences from Python:
 * - Java generics provide type safety
 * - Java nested classes have different access rules
 * - Java method overloading vs overriding
 */

import java.util.*;

// Generic base class for containers
class Container<T> {
    protected List<T> items;
    protected String name;
    
    public Container(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }
    
    public void add(T item) {
        items.add(item);
        System.out.println("  Added " + item + " to " + name);
    }
    
    public T get(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }
    
    public int size() {
        return items.size();
    }
    
    public void display() {
        System.out.println("  " + name + " contains " + items.size() + " items:");
        for (T item : items) {
            System.out.println("    - " + item);
        }
    }
}

// Specialized container for numbers
class NumberContainer extends Container<Number> {
    private double sum;
    
    public NumberContainer(String name) {
        super(name);
        this.sum = 0.0;
    }
    
    @Override
    public void add(Number item) {
        super.add(item);
        sum += item.doubleValue();
    }
    
    public double getAverage() {
        return items.size() > 0 ? sum / items.size() : 0.0;
    }
    
    @Override
    public void display() {
        super.display();
        System.out.println("  Average: " + getAverage());
    }
}

// Specialized container for strings
class StringContainer extends Container<String> {
    private int totalLength;
    
    public StringContainer(String name) {
        super(name);
        this.totalLength = 0;
    }
    
    @Override
    public void add(String item) {
        super.add(item);
        totalLength += item.length();
    }
    
    public double getAverageLength() {
        return items.size() > 0 ? (double) totalLength / items.size() : 0.0;
    }
    
    @Override
    public void display() {
        super.display();
        System.out.println("  Average length: " + getAverageLength());
    }
}

// Simple Animal classes for factory pattern demonstration
class Animal {
    protected String name;
    protected int age;
    
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public void makeSound() {
        System.out.println("  Animal makes a sound");
    }
    
    public void displayInfo() {
        System.out.println("  Name: " + name + ", Age: " + age);
    }
}

class Dog extends Animal {
    private String breed;
    
    public Dog(String name, int age, String breed) {
        super(name, age);
        this.breed = breed;
    }
    
    @Override
    public void makeSound() {
        System.out.println("  Dog barks: Woof!");
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("  Breed: " + breed);
    }
}

class Cat extends Animal {
    private String breed;
    
    public Cat(String name, int age, String breed) {
        super(name, age);
        this.breed = breed;
    }
    
    @Override
    public void makeSound() {
        System.out.println("  Cat meows: Meow!");
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("  Breed: " + breed);
    }
}

// Abstract factory pattern with inheritance
abstract class AnimalFactory {
    public abstract Animal createAnimal(String name, int age);
    
    public void createAndDisplay(String name, int age) {
        Animal animal = createAnimal(name, age);
        animal.displayInfo();
        animal.makeSound();
    }
}

class DogFactory extends AnimalFactory {
    @Override
    public Animal createAnimal(String name, int age) {
        return new Dog(name, age, "Mixed Breed");
    }
}

class CatFactory extends AnimalFactory {
    @Override
    public Animal createAnimal(String name, int age) {
        return new Cat(name, age, "Domestic Shorthair");
    }
}

// Nested class example with inheritance
class University {
    private String name;
    private List<Department> departments;
    
    public University(String name) {
        this.name = name;
        this.departments = new ArrayList<>();
    }
    
    // Nested class - Department
    public static class Department {
        protected String name;
        protected String head;
        protected int studentCount;
        
        public Department(String name, String head) {
            this.name = name;
            this.head = head;
            this.studentCount = 0;
        }
        
        public void addStudent() {
            studentCount++;
        }
        
        public void displayInfo() {
            System.out.println("    Department: " + name);
            System.out.println("    Head: " + head);
            System.out.println("    Students: " + studentCount);
        }
    }
    
    // Nested class that extends Department
    public static class ComputerScience extends Department {
        private String programmingLanguage;
        
        public ComputerScience(String head, String programmingLanguage) {
            super("Computer Science", head);
            this.programmingLanguage = programmingLanguage;
        }
        
        @Override
        public void displayInfo() {
            super.displayInfo();
            System.out.println("    Primary Language: " + programmingLanguage);
        }
        
        public void teachProgramming() {
            System.out.println("    Teaching " + programmingLanguage + " programming");
        }
    }
    
    // Nested class that extends Department
    public static class Mathematics extends Department {
        private String specialization;
        
        public Mathematics(String head, String specialization) {
            super("Mathematics", head);
            this.specialization = specialization;
        }
        
        @Override
        public void displayInfo() {
            super.displayInfo();
            System.out.println("    Specialization: " + specialization);
        }
        
        public void solveProblem() {
            System.out.println("    Solving complex mathematical problems");
        }
    }
    
    public void addDepartment(Department dept) {
        departments.add(dept);
    }
    
    public void displayAllDepartments() {
        System.out.println("University: " + name);
        for (Department dept : departments) {
            dept.displayInfo();
            System.out.println();
        }
    }
}

// Complex inheritance with method overloading and overriding
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    
    public double add(double a, double b) {
        return a + b;
    }
    
    public int add(int a, int b, int c) {
        return a + b + c;
    }
    
    public void displayResult(String operation, double result) {
        System.out.println("  " + operation + " = " + result);
    }
}

class ScientificCalculator extends Calculator {
    @Override
    public double add(double a, double b) {
        System.out.println("  Using scientific precision");
        return super.add(a, b);
    }
    
    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }
    
    public double sqrt(double number) {
        return Math.sqrt(number);
    }
    
    @Override
    public void displayResult(String operation, double result) {
        System.out.println("  Scientific Calculator Result:");
        super.displayResult(operation, result);
    }
}

// Main class to demonstrate advanced inheritance concepts
public class Advanced {
    public static void main(String[] args) {
        System.out.println("=== Advanced Inheritance Examples ===\n");
        
        // Demonstrate generic inheritance
        demonstrateGenericInheritance();
        
        // Demonstrate factory pattern
        demonstrateFactoryPattern();
        
        // Demonstrate nested class inheritance
        demonstrateNestedInheritance();
        
        // Demonstrate complex method overloading/overriding
        demonstrateMethodOverloading();
        
        System.out.println("=== Advanced Examples Complete ===");
    }
    
    private static void demonstrateGenericInheritance() {
        System.out.println("1. Generic Inheritance:");
        System.out.println("------------------------");
        
        NumberContainer numbers = new NumberContainer("Numbers");
        numbers.add(10);
        numbers.add(20.5);
        numbers.add(30);
        numbers.display();
        
        StringContainer strings = new StringContainer("Strings");
        strings.add("Hello");
        strings.add("World");
        strings.add("Java");
        strings.display();
        
        System.out.println();
    }
    
    private static void demonstrateFactoryPattern() {
        System.out.println("2. Factory Pattern with Inheritance:");
        System.out.println("------------------------------------");
        
        AnimalFactory dogFactory = new DogFactory();
        AnimalFactory catFactory = new CatFactory();
        
        System.out.println("Creating animals with factories:");
        dogFactory.createAndDisplay("Rex", 3);
        catFactory.createAndDisplay("Fluffy", 2);
        
        System.out.println();
    }
    
    private static void demonstrateNestedInheritance() {
        System.out.println("3. Nested Class Inheritance:");
        System.out.println("-----------------------------");
        
        University uni = new University("Tech University");
        
        University.ComputerScience cs = new University.ComputerScience("Dr. Smith", "Java");
        University.Mathematics math = new University.Mathematics("Dr. Johnson", "Algebra");
        
        cs.addStudent();
        cs.addStudent();
        math.addStudent();
        
        uni.addDepartment(cs);
        uni.addDepartment(math);
        
        uni.displayAllDepartments();
        
        System.out.println("Demonstrating specialized methods:");
        cs.teachProgramming();
        math.solveProblem();
        
        System.out.println();
    }
    
    private static void demonstrateMethodOverloading() {
        System.out.println("4. Method Overloading and Overriding:");
        System.out.println("-------------------------------------");
        
        Calculator basicCalc = new Calculator();
        ScientificCalculator sciCalc = new ScientificCalculator();
        
        System.out.println("Basic Calculator:");
        basicCalc.displayResult("2 + 3", basicCalc.add(2, 3));
        basicCalc.displayResult("2.5 + 3.5", basicCalc.add(2.5, 3.5));
        
        System.out.println("\nScientific Calculator:");
        sciCalc.displayResult("2.5 + 3.5", sciCalc.add(2.5, 3.5));
        sciCalc.displayResult("2^3", sciCalc.power(2, 3));
        sciCalc.displayResult("√16", sciCalc.sqrt(16));
        
        System.out.println();
    }
} 