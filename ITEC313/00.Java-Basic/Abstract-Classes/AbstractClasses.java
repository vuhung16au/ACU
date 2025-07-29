/**
 * AbstractClasses.java
 * 
 * This program demonstrates abstract classes and methods in Java:
 * - How to define abstract classes
 * - How to implement abstract methods
 * - Practical examples of abstract classes
 * - Polymorphism with abstract classes
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
import java.util.ArrayList;
import java.util.List;

// Abstract class representing a shape
abstract class Shape {
    protected String color;
    protected double area;
    
    // Constructor
    public Shape(String color) {
        this.color = color;
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract double calculateArea();
    
    // Abstract method for perimeter
    public abstract double calculatePerimeter();
    
    // Concrete method - can be used by all subclasses
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    // Abstract method for displaying shape info
    public abstract void displayInfo();
    
    // Concrete method that uses abstract methods
    public void printDetails() {
        System.out.println("Shape Details:");
        System.out.println("Color: " + color);
        System.out.println("Area: " + calculateArea());
        System.out.println("Perimeter: " + calculatePerimeter());
        displayInfo();
        System.out.println();
    }
}

// Concrete class extending abstract Shape
class Circle extends Shape {
    private double radius;
    
    public Circle(String color, double radius) {
        super(color);
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
    
    @Override
    public void displayInfo() {
        System.out.println("Type: Circle");
        System.out.println("Radius: " + radius);
    }
    
    public double getRadius() {
        return radius;
    }
}

// Concrete class extending abstract Shape
class Rectangle extends Shape {
    private double length;
    private double width;
    
    public Rectangle(String color, double length, double width) {
        super(color);
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
    
    @Override
    public void displayInfo() {
        System.out.println("Type: Rectangle");
        System.out.println("Length: " + length);
        System.out.println("Width: " + width);
    }
    
    public double getLength() {
        return length;
    }
    
    public double getWidth() {
        return width;
    }
}

// Concrete class extending abstract Shape
class Triangle extends Shape {
    private double side1, side2, side3;
    
    public Triangle(String color, double side1, double side2, double side3) {
        super(color);
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }
    
    @Override
    public double calculateArea() {
        // Using Heron's formula
        double s = (side1 + side2 + side3) / 2;
        return Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
    }
    
    @Override
    public double calculatePerimeter() {
        return side1 + side2 + side3;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Type: Triangle");
        System.out.println("Sides: " + side1 + ", " + side2 + ", " + side3);
    }
    
    public double getSide1() { return side1; }
    public double getSide2() { return side2; }
    public double getSide3() { return side3; }
}

// Abstract class for animals
abstract class Animal {
    protected String name;
    protected int age;
    
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Abstract methods
    public abstract String makeSound();
    public abstract String getSpecies();
    
    // Concrete methods
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void displayInfo() {
        System.out.println("Animal: " + name);
        System.out.println("Species: " + getSpecies());
        System.out.println("Age: " + age);
        System.out.println("Sound: " + makeSound());
        System.out.println();
    }
}

// Concrete animal classes
class Dog extends Animal {
    private String breed;
    
    public Dog(String name, int age, String breed) {
        super(name, age);
        this.breed = breed;
    }
    
    @Override
    public String makeSound() {
        return "Woof!";
    }
    
    @Override
    public String getSpecies() {
        return "Dog";
    }
    
    public String getBreed() {
        return breed;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Breed: " + breed);
    }
}

class Cat extends Animal {
    private boolean isIndoor;
    
    public Cat(String name, int age, boolean isIndoor) {
        super(name, age);
        this.isIndoor = isIndoor;
    }
    
    @Override
    public String makeSound() {
        return "Meow!";
    }
    
    @Override
    public String getSpecies() {
        return "Cat";
    }
    
    public boolean isIndoor() {
        return isIndoor;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Indoor: " + isIndoor);
    }
}

// Main class to demonstrate abstract classes
public class AbstractClasses {
    
    public static void main(String[] args) {
        System.out.println("=== Abstract Classes Demonstration ===\n");
        
        // Demonstrate Shape abstract class
        demonstrateShapes();
        
        // Demonstrate Animal abstract class
        demonstrateAnimals();
        
        // Demonstrate polymorphism
        demonstratePolymorphism();
    }
    
    private static void demonstrateShapes() {
        System.out.println("--- Shape Abstract Class Examples ---");
        
        // Create different shapes
        Circle circle = new Circle("Red", 5.0);
        Rectangle rectangle = new Rectangle("Blue", 4.0, 6.0);
        Triangle triangle = new Triangle("Green", 3.0, 4.0, 5.0);
        
        // Use polymorphism - treat all as Shape objects
        List<Shape> shapes = new ArrayList<>();
        shapes.add(circle);
        shapes.add(rectangle);
        shapes.add(triangle);
        
        // Process all shapes uniformly
        for (Shape shape : shapes) {
            shape.printDetails();
        }
    }
    
    private static void demonstrateAnimals() {
        System.out.println("--- Animal Abstract Class Examples ---");
        
        // Create different animals
        Dog dog = new Dog("Buddy", 3, "Golden Retriever");
        Cat cat = new Cat("Whiskers", 2, true);
        
        // Use polymorphism
        List<Animal> animals = new ArrayList<>();
        animals.add(dog);
        animals.add(cat);
        
        // Process all animals uniformly
        for (Animal animal : animals) {
            animal.displayInfo();
        }
    }
    
    private static void demonstratePolymorphism() {
        System.out.println("--- Polymorphism with Abstract Classes ---");
        
        // Create a list of shapes
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Circle("Yellow", 3.0));
        shapes.add(new Rectangle("Purple", 2.0, 8.0));
        shapes.add(new Triangle("Orange", 6.0, 8.0, 10.0));
        
        // Calculate total area
        double totalArea = 0;
        for (Shape shape : shapes) {
            totalArea += shape.calculateArea();
        }
        
        System.out.println("Total area of all shapes: " + String.format("%.2f", totalArea));
        
        // Find shape with largest area
        Shape largestShape = shapes.get(0);
        for (Shape shape : shapes) {
            if (shape.calculateArea() > largestShape.calculateArea()) {
                largestShape = shape;
            }
        }
        
        System.out.println("Shape with largest area:");
        largestShape.printDetails();
    }
} 