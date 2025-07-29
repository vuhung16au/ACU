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
        System.out.println("=== Advanced Constructor Examples ===\n");
        
        // Demonstrate copy constructor
        demonstrateCopyConstructor();
        
        // Demonstrate factory methods
        demonstrateFactoryMethods();
        
        // Demonstrate singleton pattern
        demonstrateSingleton();
        
        // Demonstrate builder pattern
        demonstrateBuilderPattern();
    }
    
    /**
     * Demonstrates copy constructor pattern
     */
    private static void demonstrateCopyConstructor() {
        System.out.println("1. Copy Constructor Example:");
        System.out.println("   -------------------------");
        
        // Create original car
        Car originalCar = new Car("Toyota", "Camry", 2020, 25000.0);
        System.out.println("   Original: " + originalCar);
        
        // Create a copy using copy constructor
        Car copyCar = new Car(originalCar);
        System.out.println("   Copy: " + copyCar);
        
        // Modify the copy to show they are separate objects
        copyCar.setPrice(30000.0);
        System.out.println("   After modifying copy:");
        System.out.println("   Original: " + originalCar);
        System.out.println("   Copy: " + copyCar);
        System.out.println();
    }
    
    /**
     * Demonstrates factory method pattern
     */
    private static void demonstrateFactoryMethods() {
        System.out.println("2. Factory Methods Example:");
        System.out.println("   ------------------------");
        
        // Create cars using factory methods
        Car economyCar = Car.createEconomyCar("Honda", "Civic", 2021);
        Car luxuryCar = Car.createLuxuryCar("BMW", "X5", 2021);
        Car electricCar = Car.createElectricCar("Tesla", "Model 3", 2021);
        
        System.out.println("   Economy car: " + economyCar);
        System.out.println("   Luxury car: " + luxuryCar);
        System.out.println("   Electric car: " + electricCar);
        System.out.println();
    }
    
    /**
     * Demonstrates singleton pattern
     */
    private static void demonstrateSingleton() {
        System.out.println("3. Singleton Pattern Example:");
        System.out.println("   -------------------------");
        
        // Get singleton instances
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        
        System.out.println("   Database 1: " + db1);
        System.out.println("   Database 2: " + db2);
        System.out.println("   Are they the same object? " + (db1 == db2));
        System.out.println();
    }
    
    /**
     * Demonstrates builder pattern
     */
    private static void demonstrateBuilderPattern() {
        System.out.println("4. Builder Pattern Example:");
        System.out.println("   ------------------------");
        
        // Create computer using builder pattern
        Computer computer = new Computer.Builder()
            .setCpu("Intel i7")
            .setRam(16)
            .setStorage("512GB SSD")
            .setGpu("NVIDIA RTX 3060")
            .build();
        
        System.out.println("   Built computer: " + computer);
        System.out.println();
    }
}

/**
 * Car class demonstrating copy constructor and factory methods
 */
class Car {
    private String make;
    private String model;
    private int year;
    private double price;
    
    /**
     * Regular constructor
     */
    public Car(String make, String model, int year, double price) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
    }
    
    /**
     * Copy constructor - creates a new Car from an existing one
     * In Python: This would be like creating a new object with the same attributes
     */
    public Car(Car other) {
        this.make = other.make;
        this.model = other.model;
        this.year = other.year;
        this.price = other.price;
    }
    
    // Factory methods for different car types
    /**
     * Factory method for economy cars
     * In Python: This would be like a class method or static method
     */
    public static Car createEconomyCar(String make, String model, int year) {
        return new Car(make, model, year, 20000.0);
    }
    
    /**
     * Factory method for luxury cars
     */
    public static Car createLuxuryCar(String make, String model, int year) {
        return new Car(make, model, year, 60000.0);
    }
    
    /**
     * Factory method for electric cars
     */
    public static Car createElectricCar(String make, String model, int year) {
        return new Car(make, model, year, 45000.0);
    }
    
    // Getter and setter methods
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getPrice() { return price; }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        return String.format("Car{make='%s', model='%s', year=%d, price=$%.2f}", 
                           make, model, year, price);
    }
}

/**
 * DatabaseConnection class demonstrating singleton pattern
 */
class DatabaseConnection {
    private static DatabaseConnection instance;
    private String connectionString;
    private boolean isConnected;
    
    /**
     * Private constructor - prevents external instantiation
     * In Python: This would be like making __init__ private (not really possible)
     */
    private DatabaseConnection() {
        this.connectionString = "jdbc:mysql://localhost:3306/mydb";
        this.isConnected = false;
    }
    
    /**
     * Static method to get singleton instance
     * In Python: This would be like a class method that returns the same instance
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    public void connect() {
        isConnected = true;
        System.out.println("   Connected to database: " + connectionString);
    }
    
    public void disconnect() {
        isConnected = false;
        System.out.println("   Disconnected from database");
    }
    
    @Override
    public String toString() {
        return String.format("DatabaseConnection{connected=%s, url='%s'}", 
                           isConnected, connectionString);
    }
}

/**
 * Computer class demonstrating builder pattern
 */
class Computer {
    private String cpu;
    private int ram;
    private String storage;
    private String gpu;
    
    /**
     * Private constructor for builder pattern
     */
    private Computer() {}
    
    // Getter methods
    public String getCpu() { return cpu; }
    public int getRam() { return ram; }
    public String getStorage() { return storage; }
    public String getGpu() { return gpu; }
    
    @Override
    public String toString() {
        return String.format("Computer{cpu='%s', ram=%dGB, storage='%s', gpu='%s'}", 
                           cpu, ram, storage, gpu);
    }
    
    /**
     * Builder class for Computer
     * In Python: This would be like using a separate builder class or fluent interface
     */
    public static class Builder {
        private Computer computer;
        
        public Builder() {
            computer = new Computer();
        }
        
        public Builder setCpu(String cpu) {
            computer.cpu = cpu;
            return this;
        }
        
        public Builder setRam(int ram) {
            computer.ram = ram;
            return this;
        }
        
        public Builder setStorage(String storage) {
            computer.storage = storage;
            return this;
        }
        
        public Builder setGpu(String gpu) {
            computer.gpu = gpu;
            return this;
        }
        
        public Computer build() {
            return computer;
        }
    }
} 