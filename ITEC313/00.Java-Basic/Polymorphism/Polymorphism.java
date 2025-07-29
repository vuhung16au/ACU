/**
 * Polymorphism.java
 * 
 * This program demonstrates polymorphism in Java:
 * - Method overriding
 * - Dynamic method dispatch
 * - Polymorphic behavior
 * - Interface polymorphism
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
// Base class demonstrating inheritance hierarchy
class Animal {
    protected String name;
    protected int age;
    
    // Constructor - similar to Python's __init__
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Base method that will be overridden (polymorphic behavior)
    public void makeSound() {
        System.out.println("Some animal sound");
    }
    
    // Method that will be overridden by subclasses
    public void move() {
        System.out.println("Animal is moving");
    }
    
    // Getter methods
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    // toString method - similar to Python's __str__
    @Override
    public String toString() {
        return "Animal[name=" + name + ", age=" + age + "]";
    }
}

// Subclass demonstrating method overriding (runtime polymorphism)
class Dog extends Animal {
    private String breed;
    
    public Dog(String name, int age, String breed) {
        super(name, age); // Call parent constructor - similar to super().__init__()
        this.breed = breed;
    }
    
    // Method overriding - this is runtime polymorphism
    // In Python, you'd just define the same method name
    @Override
    public void makeSound() {
        System.out.println(name + " says: Woof! Woof!");
    }
    
    @Override
    public void move() {
        System.out.println(name + " is running on four legs");
    }
    
    // Additional method specific to Dog
    public void fetch() {
        System.out.println(name + " is fetching the ball");
    }
    
    public String getBreed() {
        return breed;
    }
    
    @Override
    public String toString() {
        return "Dog[name=" + name + ", age=" + age + ", breed=" + breed + "]";
    }
}

// Another subclass demonstrating polymorphism
class Cat extends Animal {
    private boolean isIndoor;
    
    public Cat(String name, int age, boolean isIndoor) {
        super(name, age);
        this.isIndoor = isIndoor;
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " says: Meow!");
    }
    
    @Override
    public void move() {
        System.out.println(name + " is walking gracefully");
    }
    
    // Additional method specific to Cat
    public void purr() {
        System.out.println(name + " is purring contentedly");
    }
    
    public boolean isIndoor() {
        return isIndoor;
    }
    
    @Override
    public String toString() {
        return "Cat[name=" + name + ", age=" + age + ", indoor=" + isIndoor + "]";
    }
}

// Interface demonstrating interface polymorphism
interface Flyable {
    void fly();
    int getMaxAltitude();
}

// Class implementing interface
class Bird extends Animal implements Flyable {
    private double wingspan;
    
    public Bird(String name, int age, double wingspan) {
        super(name, age);
        this.wingspan = wingspan;
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " says: Tweet! Tweet!");
    }
    
    @Override
    public void move() {
        System.out.println(name + " is flying through the air");
    }
    
    // Interface method implementation
    @Override
    public void fly() {
        System.out.println(name + " is soaring at " + getMaxAltitude() + " meters");
    }
    
    @Override
    public int getMaxAltitude() {
        return (int)(wingspan * 10); // Simple calculation
    }
    
    public double getWingspan() {
        return wingspan;
    }
    
    @Override
    public String toString() {
        return "Bird[name=" + name + ", age=" + age + ", wingspan=" + wingspan + "]";
    }
}

// Abstract class demonstrating abstract class polymorphism
abstract class Vehicle {
    protected String brand;
    protected int year;
    
    public Vehicle(String brand, int year) {
        this.brand = brand;
        this.year = year;
    }
    
    // Abstract method - must be implemented by subclasses
    // In Python, you'd use @abstractmethod decorator
    public abstract void startEngine();
    
    // Concrete method
    public void displayInfo() {
        System.out.println("Brand: " + brand + ", Year: " + year);
    }
    
    public String getBrand() {
        return brand;
    }
    
    public int getYear() {
        return year;
    }
}

// Concrete class extending abstract class
class Car extends Vehicle {
    private int numDoors;
    
    public Car(String brand, int year, int numDoors) {
        super(brand, year);
        this.numDoors = numDoors;
    }
    
    @Override
    public void startEngine() {
        System.out.println(brand + " car engine is starting... Vroom!");
    }
    
    public int getNumDoors() {
        return numDoors;
    }
    
    @Override
    public String toString() {
        return "Car[brand=" + brand + ", year=" + year + ", doors=" + numDoors + "]";
    }
}

// Another concrete class
class Motorcycle extends Vehicle {
    private boolean hasSidecar;
    
    public Motorcycle(String brand, int year, boolean hasSidecar) {
        super(brand, year);
        this.hasSidecar = hasSidecar;
    }
    
    @Override
    public void startEngine() {
        System.out.println(brand + " motorcycle engine is starting... Vroom vroom!");
    }
    
    public boolean hasSidecar() {
        return hasSidecar;
    }
    
    @Override
    public String toString() {
        return "Motorcycle[brand=" + brand + ", year=" + year + ", sidecar=" + hasSidecar + "]";
    }
}

// Main class demonstrating polymorphism concepts
public class Polymorphism {
    
    // Method demonstrating polymorphic behavior
    // This method can accept any Animal subclass
    // In Python, this would be "duck typing"
    public static void demonstrateAnimalBehavior(Animal animal) {
        System.out.println("\n=== Demonstrating polymorphic behavior ===");
        System.out.println("Animal: " + animal);
        animal.makeSound();  // Calls the appropriate version based on actual type
        animal.move();       // Runtime polymorphism in action
        System.out.println();
    }
    
    // Method demonstrating interface polymorphism
    public static void demonstrateFlying(Flyable flyable) {
        System.out.println("=== Demonstrating interface polymorphism ===");
        flyable.fly();
        System.out.println("Max altitude: " + flyable.getMaxAltitude() + " meters");
        System.out.println();
    }
    
    // Method demonstrating abstract class polymorphism
    public static void demonstrateVehicle(Vehicle vehicle) {
        System.out.println("=== Demonstrating abstract class polymorphism ===");
        vehicle.displayInfo();
        vehicle.startEngine();  // Calls the appropriate implementation
        System.out.println();
    }
    
    // Method demonstrating casting
    public static void demonstrateCasting() {
        System.out.println("=== Demonstrating casting ===");
        
        // Upcasting (implicit) - treating subclass as superclass
        Animal myDog = new Dog("Buddy", 3, "Golden Retriever");
        Animal myCat = new Cat("Whiskers", 2, true);
        Animal myBird = new Bird("Tweety", 1, 0.5);
        
        // Downcasting (explicit) - treating superclass as subclass
        // In Python, you'd use isinstance() to check type
        if (myDog instanceof Dog) {
            Dog dog = (Dog) myDog;  // Explicit casting
            dog.fetch();  // Can now call Dog-specific methods
        }
        
        if (myCat instanceof Cat) {
            Cat cat = (Cat) myCat;
            cat.purr();  // Can now call Cat-specific methods
        }
        
        if (myBird instanceof Bird) {
            Bird bird = (Bird) myBird;
            bird.fly();  // Can now call Bird-specific methods
        }
        
        System.out.println();
    }
    
    // Method demonstrating polymorphic collections
    public static void demonstratePolymorphicCollections() {
        System.out.println("=== Demonstrating polymorphic collections ===");
        
        // Array of Animals (can hold any Animal subclass)
        Animal[] animals = {
            new Dog("Rex", 4, "German Shepherd"),
            new Cat("Fluffy", 3, false),
            new Bird("Robin", 2, 0.3),
            new Dog("Max", 5, "Labrador")
        };
        
        // Process all animals polymorphically
        for (Animal animal : animals) {
            demonstrateAnimalBehavior(animal);
        }
        
        // Separate collection for flyable objects
        Flyable[] flyables = {
            new Bird("Eagle", 5, 2.0),
            new Bird("Sparrow", 1, 0.2)
        };
        
        for (Flyable flyable : flyables) {
            demonstrateFlying(flyable);
        }
        
        // Collection for vehicles
        Vehicle[] vehicles = {
            new Car("Toyota", 2020, 4),
            new Motorcycle("Harley", 2019, false),
            new Car("Honda", 2021, 2)
        };
        
        for (Vehicle vehicle : vehicles) {
            demonstrateVehicle(vehicle);
        }
    }
    
    // Method demonstrating method overloading (compile-time polymorphism)
    public static void demonstrateMethodOverloading() {
        System.out.println("=== Demonstrating method overloading (compile-time polymorphism) ===");
        
        // Different method signatures for same method name
        displayInfo("Simple string");
        displayInfo("String with number", 42);
        displayInfo("String with multiple numbers", 10, 20, 30);
        
        System.out.println();
    }
    
    // Overloaded methods - same name, different parameters
    // In Python, you'd use default parameters or *args
    public static void displayInfo(String message) {
        System.out.println("Info: " + message);
    }
    
    public static void displayInfo(String message, int number) {
        System.out.println("Info: " + message + " - Number: " + number);
    }
    
    public static void displayInfo(String message, int... numbers) {
        System.out.print("Info: " + message + " - Numbers: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        System.out.println("🚀 Java Polymorphism Demonstration");
        System.out.println("=====================================");
        System.out.println();
        
        // Demonstrate different types of polymorphism
        demonstrateMethodOverloading();
        demonstrateCasting();
        demonstratePolymorphicCollections();
        
        System.out.println("✅ Polymorphism demonstration completed!");
        System.out.println();
        System.out.println("Key Takeaways:");
        System.out.println("- Runtime polymorphism allows different objects to respond to the same method call");
        System.out.println("- Method overriding enables polymorphic behavior");
        System.out.println("- Casting allows type conversion between related classes");
        System.out.println("- Interfaces and abstract classes provide additional polymorphic capabilities");
        System.out.println("- Collections can hold objects of different types that share a common superclass");
    }
} 