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
public class Advanced {
    
    public static void main(String[] args) {
        System.out.println("=== Advanced: Complex Object-Oriented Concepts ===\n");
        
        // Demonstrate composition
        demonstrateComposition();
        
        // Demonstrate factory pattern
        demonstrateFactoryPattern();
        
        // Demonstrate builder pattern
        demonstrateBuilderPattern();
        
        // Demonstrate object lifecycle
        demonstrateObjectLifecycle();
        
        System.out.println("\n=== Advanced Example Complete ===");
    }
    
    public static void demonstrateComposition() {
        System.out.println("1. COMPOSITION RELATIONSHIPS");
        System.out.println("============================");
        
        // Create a university with departments
        University acu = new University("XYZ");
        
        // Add departments (composition - departments belong to university)
        Department csDept = new Department("Computer Science", "Dr. Smith");
        Department mathDept = new Department("Mathematics", "Dr. Johnson");
        
        acu.addDepartment(csDept);
        acu.addDepartment(mathDept);
        
        // Add courses to departments
        Course javaCourse = new Course("ITEC313", "Object-Oriented Programming", 3);
        Course pythonCourse = new Course("ITEC314", "Python Programming", 3);
        Course calcCourse = new Course("MATH101", "Calculus", 4);
        
        csDept.addCourse(javaCourse);
        csDept.addCourse(pythonCourse);
        mathDept.addCourse(calcCourse);
        
        // Display university structure
        acu.displayStructure();
        
        System.out.println();
    }
    
    public static void demonstrateFactoryPattern() {
        System.out.println("2. FACTORY PATTERN");
        System.out.println("==================");
        
        // Create different types of vehicles using factory
        VehicleFactory factory = new VehicleFactory();
        
        Vehicle car = factory.createVehicle("car", "Toyota Camry", 2020);
        Vehicle motorcycle = factory.createVehicle("motorcycle", "Honda CBR", 2019);
        Vehicle truck = factory.createVehicle("truck", "Ford F-150", 2021);
        
        // Demonstrate different vehicle behaviors
        car.start();
        car.drive();
        car.stop();
        
        motorcycle.start();
        motorcycle.drive();
        motorcycle.stop();
        
        truck.start();
        truck.drive();
        truck.stop();
        
        System.out.println();
    }
    
    public static void demonstrateBuilderPattern() {
        System.out.println("3. BUILDER PATTERN");
        System.out.println("==================");
        
        // Create a complex object using builder pattern
        Computer gamingPC = new Computer.ComputerBuilder()
            .setProcessor("Intel i7")
            .setMemory("16GB")
            .setStorage("1TB SSD")
            .setGraphicsCard("RTX 3080")
            .setPrice(1500.0)
            .build();
        
        Computer officePC = new Computer.ComputerBuilder()
            .setProcessor("Intel i5")
            .setMemory("8GB")
            .setStorage("500GB SSD")
            .setPrice(800.0)
            .build();
        
        // Display computer specifications
        gamingPC.displaySpecs();
        officePC.displaySpecs();
        
        System.out.println();
    }
    
    public static void demonstrateObjectLifecycle() {
        System.out.println("4. OBJECT LIFECYCLE MANAGEMENT");
        System.out.println("===============================");
        
        // Demonstrate object creation, usage, and cleanup
        ResourceManager manager = new ResourceManager();
        
        // Create and use resources
        Resource resource1 = manager.createResource("Database Connection");
        Resource resource2 = manager.createResource("File Handle");
        
        resource1.use();
        resource2.use();
        
        // Cleanup resources
        manager.cleanupResource(resource1);
        manager.cleanupResource(resource2);
        
        System.out.println();
    }
}

/**
 * University class demonstrating composition
 * University "has-a" Department (composition relationship)
 */
class University {
    private String name;
    private Department[] departments;
    private int departmentCount;
    private int maxDepartments;
    
    public University(String name) {
        this.name = name;
        this.maxDepartments = 10;
        this.departments = new Department[maxDepartments];
        this.departmentCount = 0;
    }
    
    public void addDepartment(Department department) {
        if (departmentCount < maxDepartments) {
            departments[departmentCount] = department;
            departmentCount++;
        }
    }
    
    public void displayStructure() {
        System.out.println("University: " + name);
        System.out.println("Departments:");
        for (int i = 0; i < departmentCount; i++) {
            departments[i].displayInfo();
        }
    }
}

/**
 * Department class - part of University (composition)
 */
class Department {
    private String name;
    private String head;
    private Course[] courses;
    private int courseCount;
    private int maxCourses;
    
    public Department(String name, String head) {
        this.name = name;
        this.head = head;
        this.maxCourses = 20;
        this.courses = new Course[maxCourses];
        this.courseCount = 0;
    }
    
    public void addCourse(Course course) {
        if (courseCount < maxCourses) {
            courses[courseCount] = course;
            courseCount++;
        }
    }
    
    public void displayInfo() {
        System.out.println("  Department: " + name + " (Head: " + head + ")");
        System.out.println("  Courses:");
        for (int i = 0; i < courseCount; i++) {
            System.out.println("    - " + courses[i].getCourseName());
        }
    }
}

/**
 * Course class - part of Department (composition)
 */
class Course {
    private String courseCode;
    private String courseName;
    private int creditHours;
    
    public Course(String courseCode, String courseName, int creditHours) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.creditHours = creditHours;
    }
    
    public String getCourseName() {
        return courseName;
    }
}

/**
 * Vehicle hierarchy demonstrating inheritance and polymorphism
 */
abstract class Vehicle {
    protected String model;
    protected int year;
    
    public Vehicle(String model, int year) {
        this.model = model;
        this.year = year;
    }
    
    public abstract void start();
    public abstract void drive();
    public abstract void stop();
}

class Car extends Vehicle {
    public Car(String model, int year) {
        super(model, year);
    }
    
    @Override
    public void start() {
        System.out.println("Car " + model + " starting with key ignition");
    }
    
    @Override
    public void drive() {
        System.out.println("Car " + model + " driving on roads");
    }
    
    @Override
    public void stop() {
        System.out.println("Car " + model + " stopping with brakes");
    }
}

class Motorcycle extends Vehicle {
    public Motorcycle(String model, int year) {
        super(model, year);
    }
    
    @Override
    public void start() {
        System.out.println("Motorcycle " + model + " starting with kick start");
    }
    
    @Override
    public void drive() {
        System.out.println("Motorcycle " + model + " driving on roads");
    }
    
    @Override
    public void stop() {
        System.out.println("Motorcycle " + model + " stopping with brakes");
    }
}

class Truck extends Vehicle {
    public Truck(String model, int year) {
        super(model, year);
    }
    
    @Override
    public void start() {
        System.out.println("Truck " + model + " starting with air brakes");
    }
    
    @Override
    public void drive() {
        System.out.println("Truck " + model + " driving on highways");
    }
    
    @Override
    public void stop() {
        System.out.println("Truck " + model + " stopping with air brakes");
    }
}

/**
 * Factory pattern for creating different types of vehicles
 */
class VehicleFactory {
    public Vehicle createVehicle(String type, String model, int year) {
        switch (type.toLowerCase()) {
            case "car":
                return new Car(model, year);
            case "motorcycle":
                return new Motorcycle(model, year);
            case "truck":
                return new Truck(model, year);
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
}

/**
 * Computer class demonstrating builder pattern
 */
class Computer {
    private String processor;
    private String memory;
    private String storage;
    private String graphicsCard;
    private double price;
    
    private Computer() {} // Private constructor
    
    public void displaySpecs() {
        System.out.println("Computer Specifications:");
        System.out.println("  Processor: " + processor);
        System.out.println("  Memory: " + memory);
        System.out.println("  Storage: " + storage);
        if (graphicsCard != null) {
            System.out.println("  Graphics: " + graphicsCard);
        }
        System.out.println("  Price: $" + price);
        System.out.println();
    }
    
    // Static inner class for builder pattern
    public static class ComputerBuilder {
        private Computer computer;
        
        public ComputerBuilder() {
            computer = new Computer();
        }
        
        public ComputerBuilder setProcessor(String processor) {
            computer.processor = processor;
            return this;
        }
        
        public ComputerBuilder setMemory(String memory) {
            computer.memory = memory;
            return this;
        }
        
        public ComputerBuilder setStorage(String storage) {
            computer.storage = storage;
            return this;
        }
        
        public ComputerBuilder setGraphicsCard(String graphicsCard) {
            computer.graphicsCard = graphicsCard;
            return this;
        }
        
        public ComputerBuilder setPrice(double price) {
            computer.price = price;
            return this;
        }
        
        public Computer build() {
            return computer;
        }
    }
}

/**
 * Resource management demonstrating object lifecycle
 */
class Resource {
    private String name;
    private boolean isActive;
    
    public Resource(String name) {
        this.name = name;
        this.isActive = true;
        System.out.println("Resource created: " + name);
    }
    
    public void use() {
        if (isActive) {
            System.out.println("Using resource: " + name);
        } else {
            System.out.println("Resource " + name + " is not active");
        }
    }
    
    public void cleanup() {
        if (isActive) {
            isActive = false;
            System.out.println("Resource cleaned up: " + name);
        }
    }
    
    public boolean isActive() {
        return isActive;
    }
}

class ResourceManager {
    public Resource createResource(String name) {
        return new Resource(name);
    }
    
    public void cleanupResource(Resource resource) {
        resource.cleanup();
    }
} 