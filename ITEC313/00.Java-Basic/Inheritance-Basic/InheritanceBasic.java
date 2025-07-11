/**
 * Inheritance-Basic - Java Inheritance Concepts
 * 
 * This program demonstrates key inheritance concepts in Java:
 * - Extends keyword for class inheritance
 * - Super keyword for accessing parent class members
 * - Method overriding with @Override annotation
 * - Constructor chaining with super()
 * 
 * Target Audience: Python developers learning Java
 * 
 * Key Differences from Python:
 * - Java uses 'extends' instead of Python's parentheses syntax
 * - Java requires explicit 'super()' calls in constructors
 * - Java uses '@Override' annotation for clarity
 * - Java has single inheritance (unlike Python's multiple inheritance)
 */

public class InheritanceBasic {
    
    public static void main(String[] args) {
        System.out.println("=== Java Inheritance Basics ===\n");
        
        // Demonstrate basic inheritance
        demonstrateBasicInheritance();
        
        // Demonstrate constructor chaining
        demonstrateConstructorChaining();
        
        // Demonstrate method overriding
        demonstrateMethodOverriding();
        
        // Demonstrate super keyword usage
        demonstrateSuperKeyword();
        
        // Demonstrate polymorphism
        demonstratePolymorphism();
        
        System.out.println("\n=== Inheritance Demo Complete ===");
    }
    
    /**
     * Demonstrates basic inheritance using the 'extends' keyword
     * 
     * In Python: class Child(Parent):
     * In Java: class Child extends Parent
     */
    private static void demonstrateBasicInheritance() {
        System.out.println("1. Basic Inheritance:");
        System.out.println("----------------------");
        
        // Create instances of different animal types
        Animal genericAnimal = new Animal("Generic Animal", 5);
        Dog myDog = new Dog("Buddy", 3, "Golden Retriever");
        Cat myCat = new Cat("Whiskers", 2, "Persian");
        
        // Demonstrate inherited methods
        System.out.println("Generic Animal:");
        genericAnimal.displayInfo();
        genericAnimal.makeSound();
        
        System.out.println("\nDog (inherits from Animal):");
        myDog.displayInfo();  // Inherited method
        myDog.makeSound();    // Overridden method
        myDog.fetch();        // Dog-specific method
        
        System.out.println("\nCat (inherits from Animal):");
        myCat.displayInfo();  // Inherited method
        myCat.makeSound();    // Overridden method
        myCat.purr();         // Cat-specific method
        
        System.out.println();
    }
    
    /**
     * Demonstrates constructor chaining with super()
     * 
     * In Python: super().__init__(args)
     * In Java: super(args) - must be first statement in constructor
     */
    private static void demonstrateConstructorChaining() {
        System.out.println("2. Constructor Chaining:");
        System.out.println("-------------------------");
        
        // Create vehicles with different constructors
        Vehicle basicVehicle = new Vehicle("Basic Car", 2020);
        Car myCar = new Car("Toyota Camry", 2022, "Sedan");
        ElectricCar tesla = new ElectricCar("Tesla Model 3", 2023, "Sedan", 350);
        
        System.out.println("Basic Vehicle:");
        basicVehicle.displayInfo();
        
        System.out.println("\nCar (inherits from Vehicle):");
        myCar.displayInfo();
        
        System.out.println("\nElectric Car (inherits from Car):");
        tesla.displayInfo();
        
        System.out.println();
    }
    
    /**
     * Demonstrates method overriding with @Override annotation
     * 
     * In Python: Just define the method with same name
     * In Java: Use @Override annotation for clarity and safety
     */
    private static void demonstrateMethodOverriding() {
        System.out.println("3. Method Overriding:");
        System.out.println("----------------------");
        
        // Create different types of employees
        Employee employee = new Employee("John Doe", 50000);
        Manager manager = new Manager("Jane Smith", 80000, "Engineering");
        Developer developer = new Developer("Bob Johnson", 70000, "Java");
        
        System.out.println("Employee:");
        employee.work();
        employee.getSalary();
        
        System.out.println("\nManager (overrides work method):");
        manager.work();
        manager.getSalary();
        
        System.out.println("\nDeveloper (overrides both methods):");
        developer.work();
        developer.getSalary();
        
        System.out.println();
    }
    
    /**
     * Demonstrates super keyword usage
     * 
     * In Python: super().method_name()
     * In Java: super.methodName()
     */
    private static void demonstrateSuperKeyword() {
        System.out.println("4. Super Keyword Usage:");
        System.out.println("------------------------");
        
        // Create a smartphone that extends phone
        Phone basicPhone = new Phone("Nokia 3310", 1999);
        Smartphone smartphone = new Smartphone("iPhone 15", 2023, "iOS");
        
        System.out.println("Basic Phone:");
        basicPhone.displayInfo();
        basicPhone.call("123-456-7890");
        
        System.out.println("\nSmartphone (uses super to call parent methods):");
        smartphone.displayInfo();
        smartphone.call("123-456-7890");  // Uses super.call()
        smartphone.installApp("WhatsApp");
        
        System.out.println();
    }
    
    /**
     * Demonstrates polymorphism - same interface, different implementations
     * 
     * In Python: Duck typing
     * In Java: Polymorphism through inheritance
     */
    private static void demonstratePolymorphism() {
        System.out.println("5. Polymorphism:");
        System.out.println("-----------------");
        
        // Create an array of different shapes
        Shape[] shapes = {
            new Circle("Circle", 5.0),
            new Rectangle("Rectangle", 4.0, 6.0),
            new Triangle("Triangle", 3.0, 4.0, 5.0)
        };
        
        // Demonstrate polymorphic behavior
        for (Shape shape : shapes) {
            System.out.println(shape.getName() + ":");
            System.out.println("  Area: " + shape.calculateArea());
            System.out.println("  Perimeter: " + shape.calculatePerimeter());
            shape.displayInfo();
            System.out.println();
        }
    }
}

/**
 * Base class demonstrating inheritance
 * 
 * In Python: class Animal:
 * In Java: class Animal (no explicit parent, extends Object)
 */
class Animal {
    // Protected fields - accessible to subclasses
    // In Python: No direct equivalent, use _variable for "protected"
    protected String name;
    protected int age;
    
    /**
     * Constructor - called when creating new Animal objects
     * In Python: def __init__(self, name, age):
     */
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    /**
     * Method that can be overridden by subclasses
     * In Python: def make_sound(self):
     */
    public void makeSound() {
        System.out.println("  Animal makes a generic sound");
    }
    
    /**
     * Method that will be inherited by subclasses
     * In Python: def display_info(self):
     */
    public void displayInfo() {
        System.out.println("  Name: " + name + ", Age: " + age);
    }
}

/**
 * Dog class extends Animal - demonstrates single inheritance
 * 
 * In Python: class Dog(Animal):
 * In Java: class Dog extends Animal
 */
class Dog extends Animal {
    // Additional field specific to Dog
    private String breed;
    
    /**
     * Constructor that calls parent constructor using super()
     * In Python: super().__init__(name, age)
     */
    public Dog(String name, int age, String breed) {
        super(name, age);  // Must be first statement
        this.breed = breed;
    }
    
    /**
     * Method overriding - replaces parent's makeSound method
     * In Python: def make_sound(self):
     */
    @Override  // Annotation for clarity and safety
    public void makeSound() {
        System.out.println("  Dog barks: Woof! Woof!");
    }
    
    /**
     * Method overriding with super call
     * In Python: super().display_info()
     */
    @Override
    public void displayInfo() {
        super.displayInfo();  // Call parent method
        System.out.println("  Breed: " + breed);
    }
    
    /**
     * Dog-specific method - not available in parent class
     * In Python: def fetch(self):
     */
    public void fetch() {
        System.out.println("  Dog fetches the ball");
    }
}

/**
 * Cat class extends Animal - another inheritance example
 */
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
    
    public void purr() {
        System.out.println("  Cat purrs contentedly");
    }
}

/**
 * Vehicle hierarchy demonstrating constructor chaining
 */
class Vehicle {
    protected String model;
    protected int year;
    
    public Vehicle(String model, int year) {
        this.model = model;
        this.year = year;
    }
    
    public void displayInfo() {
        System.out.println("  Model: " + model + ", Year: " + year);
    }
}

/**
 * Car extends Vehicle - demonstrates constructor chaining
 */
class Car extends Vehicle {
    private String type;
    
    public Car(String model, int year, String type) {
        super(model, year);  // Call parent constructor
        this.type = type;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("  Type: " + type);
    }
}

/**
 * ElectricCar extends Car - demonstrates multi-level inheritance
 */
class ElectricCar extends Car {
    private int range;
    
    public ElectricCar(String model, int year, String type, int range) {
        super(model, year, type);  // Call parent constructor
        this.range = range;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("  Range: " + range + " miles");
    }
}

/**
 * Employee hierarchy demonstrating method overriding
 */
class Employee {
    protected String name;
    protected double salary;
    
    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
    
    public void work() {
        System.out.println("  Employee works on general tasks");
    }
    
    public void getSalary() {
        System.out.println("  Salary: $" + salary);
    }
}

/**
 * Manager extends Employee - overrides work method
 */
class Manager extends Employee {
    private String department;
    
    public Manager(String name, double salary, String department) {
        super(name, salary);
        this.department = department;
    }
    
    @Override
    public void work() {
        System.out.println("  Manager supervises " + department + " department");
    }
}

/**
 * Developer extends Employee - overrides both methods
 */
class Developer extends Employee {
    private String programmingLanguage;
    
    public Developer(String name, double salary, String programmingLanguage) {
        super(name, salary);
        this.programmingLanguage = programmingLanguage;
    }
    
    @Override
    public void work() {
        System.out.println("  Developer codes in " + programmingLanguage);
    }
    
    @Override
    public void getSalary() {
        System.out.println("  Developer salary: $" + salary + " (includes coding bonus)");
    }
}

/**
 * Phone hierarchy demonstrating super keyword usage
 */
class Phone {
    protected String model;
    protected int year;
    
    public Phone(String model, int year) {
        this.model = model;
        this.year = year;
    }
    
    public void displayInfo() {
        System.out.println("  Model: " + model + ", Year: " + year);
    }
    
    public void call(String number) {
        System.out.println("  Calling " + number + " using basic phone");
    }
}

/**
 * Smartphone extends Phone - uses super to call parent methods
 */
class Smartphone extends Phone {
    private String operatingSystem;
    
    public Smartphone(String model, int year, String operatingSystem) {
        super(model, year);
        this.operatingSystem = operatingSystem;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();  // Call parent method
        System.out.println("  OS: " + operatingSystem);
    }
    
    @Override
    public void call(String number) {
        super.call(number);  // Call parent method first
        System.out.println("  Using smartphone features for better call quality");
    }
    
    public void installApp(String appName) {
        System.out.println("  Installing " + appName + " from app store");
    }
}

/**
 * Shape hierarchy demonstrating polymorphism
 */
abstract class Shape {
    protected String name;
    
    public Shape(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    // Abstract methods - must be implemented by subclasses
    // In Python: def calculate_area(self): raise NotImplementedError
    public abstract double calculateArea();
    public abstract double calculatePerimeter();
    
    public void displayInfo() {
        System.out.println("  Shape: " + name);
    }
}

/**
 * Circle extends Shape - implements abstract methods
 */
class Circle extends Shape {
    private double radius;
    
    public Circle(String name, double radius) {
        super(name);
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
}

/**
 * Rectangle extends Shape - implements abstract methods
 */
class Rectangle extends Shape {
    private double length;
    private double width;
    
    public Rectangle(String name, double length, double width) {
        super(name);
        this.length = length;
        this.width = width;
    }
    
    @Override
    public double calculateArea() {
        return length * width;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * (length + width);
    }
}

/**
 * Triangle extends Shape - implements abstract methods
 */
class Triangle extends Shape {
    private double sideA;
    private double sideB;
    private double sideC;
    
    public Triangle(String name, double sideA, double sideB, double sideC) {
        super(name);
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }
    
    @Override
    public double calculateArea() {
        // Heron's formula
        double s = (sideA + sideB + sideC) / 2;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }
    
    @Override
    public double calculatePerimeter() {
        return sideA + sideB + sideC;
    }
} 