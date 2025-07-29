/**
 * Example2.java
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
public class Example2 {
    public static void main(String[] args) {
        System.out.println("=== Example 2: Car Encapsulation ===\n");
        
        // Create a car
        Car myCar = new Car("Toyota", "Camry", 2020);
        
        // Display initial state
        System.out.println("Initial Car State:");
        System.out.println("Make: " + myCar.getMake());
        System.out.println("Model: " + myCar.getModel());
        System.out.println("Year: " + myCar.getYear());
        System.out.println("Fuel Level: " + myCar.getFuelLevel() + "%");
        System.out.println("Mileage: " + myCar.getMileage() + " km");
        System.out.println("Is Running: " + myCar.isRunning());
        
        // Start the car
        System.out.println("\n--- Starting the car ---");
        myCar.start();
        System.out.println("Is Running: " + myCar.isRunning());
        
        // Drive the car
        System.out.println("\n--- Driving the car ---");
        myCar.drive(50);
        System.out.println("Fuel Level: " + myCar.getFuelLevel() + "%");
        System.out.println("Mileage: " + myCar.getMileage() + " km");
        
        // Add fuel
        System.out.println("\n--- Adding fuel ---");
        myCar.addFuel(20);
        System.out.println("Fuel Level: " + myCar.getFuelLevel() + "%");
        
        // Try to drive more
        System.out.println("\n--- Driving more ---");
        myCar.drive(100);
        System.out.println("Fuel Level: " + myCar.getFuelLevel() + "%");
        System.out.println("Mileage: " + myCar.getMileage() + " km");
        
        // Stop the car
        System.out.println("\n--- Stopping the car ---");
        myCar.stop();
        System.out.println("Is Running: " + myCar.isRunning());
        
        // Try to drive while stopped
        System.out.println("\n--- Trying to drive while stopped ---");
        myCar.drive(10);
    }
}

/**
 * Car class demonstrating advanced encapsulation
 */
class Car {
    // Private fields - internal state
    private String make;
    private String model;
    private int year;
    private double fuelLevel;
    private double mileage;
    private boolean isRunning;
    
    // Constants
    private static final double MAX_FUEL = 100.0;
    private static final double FUEL_CONSUMPTION_RATE = 0.1; // liters per km
    
    // Constructor
    public Car(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.fuelLevel = MAX_FUEL;
        this.mileage = 0.0;
        this.isRunning = false;
    }
    
    // Getters for read-only access
    public String getMake() {
        return make;
    }
    
    public String getModel() {
        return model;
    }
    
    public int getYear() {
        return year;
    }
    
    public double getFuelLevel() {
        return fuelLevel;
    }
    
    public double getMileage() {
        return mileage;
    }
    
    public boolean isRunning() {
        return isRunning;
    }
    
    // Business logic methods that encapsulate complex operations
    public void start() {
        if (!isRunning && fuelLevel > 0) {
            isRunning = true;
            System.out.println("Car started successfully");
        } else if (isRunning) {
            System.out.println("Car is already running");
        } else {
            System.out.println("Cannot start car - no fuel");
        }
    }
    
    public void stop() {
        if (isRunning) {
            isRunning = false;
            System.out.println("Car stopped");
        } else {
            System.out.println("Car is already stopped");
        }
    }
    
    public void drive(double distance) {
        if (!isRunning) {
            System.out.println("Cannot drive - car is not running");
            return;
        }
        
        if (distance <= 0) {
            System.out.println("Invalid distance");
            return;
        }
        
        // Calculate fuel needed
        double fuelNeeded = distance * FUEL_CONSUMPTION_RATE;
        
        if (fuelNeeded <= fuelLevel) {
            // Can drive the full distance
            fuelLevel -= fuelNeeded;
            mileage += distance;
            System.out.println("Drove " + distance + " km");
        } else {
            // Can only drive part of the distance
            double maxDistance = fuelLevel / FUEL_CONSUMPTION_RATE;
            fuelLevel = 0;
            mileage += maxDistance;
            System.out.println("Drove " + maxDistance + " km (ran out of fuel)");
            
            // Stop the car if out of fuel
            if (fuelLevel <= 0) {
                isRunning = false;
                System.out.println("Car stopped due to lack of fuel");
            }
        }
    }
    
    public void addFuel(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid fuel amount");
            return;
        }
        
        double newFuelLevel = fuelLevel + amount;
        if (newFuelLevel <= MAX_FUEL) {
            fuelLevel = newFuelLevel;
            System.out.println("Added " + amount + " liters of fuel");
        } else {
            double actualAdded = MAX_FUEL - fuelLevel;
            fuelLevel = MAX_FUEL;
            System.out.println("Added " + actualAdded + " liters of fuel (tank is full)");
        }
    }
    
    // Computed properties
    public double getFuelEfficiency() {
        if (mileage > 0) {
            return (MAX_FUEL - fuelLevel) / mileage; // liters per km
        }
        return 0.0;
    }
    
    public boolean needsMaintenance() {
        return mileage > 10000; // Maintenance every 10,000 km
    }
} 