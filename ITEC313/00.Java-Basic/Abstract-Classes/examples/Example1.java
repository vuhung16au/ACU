/**
 * Example1.java
 * 
 * Simple example demonstrating abstract classes with vehicle hierarchy
 * Shows basic abstract class concepts
 */

// Abstract class for vehicles
abstract class Vehicle {
    protected String brand;
    protected int year;
    protected double price;
    
    public Vehicle(String brand, int year, double price) {
        this.brand = brand;
        this.year = year;
        this.price = price;
    }
    
    // Abstract methods that must be implemented
    public abstract void startEngine();
    public abstract void stopEngine();
    public abstract String getVehicleType();
    
    // Concrete methods
    public String getBrand() {
        return brand;
    }
    
    public int getYear() {
        return year;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void displayInfo() {
        System.out.println("Vehicle Information:");
        System.out.println("Type: " + getVehicleType());
        System.out.println("Brand: " + brand);
        System.out.println("Year: " + year);
        System.out.println("Price: $" + price);
    }
}

// Concrete class extending Vehicle
class Car extends Vehicle {
    private int numDoors;
    
    public Car(String brand, int year, double price, int numDoors) {
        super(brand, year, price);
        this.numDoors = numDoors;
    }
    
    @Override
    public void startEngine() {
        System.out.println("Car engine started with key ignition");
    }
    
    @Override
    public void stopEngine() {
        System.out.println("Car engine stopped");
    }
    
    @Override
    public String getVehicleType() {
        return "Car";
    }
    
    public int getNumDoors() {
        return numDoors;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Number of doors: " + numDoors);
    }
}

// Concrete class extending Vehicle
class Motorcycle extends Vehicle {
    private boolean hasSidecar;
    
    public Motorcycle(String brand, int year, double price, boolean hasSidecar) {
        super(brand, year, price);
        this.hasSidecar = hasSidecar;
    }
    
    @Override
    public void startEngine() {
        System.out.println("Motorcycle engine started with kick start");
    }
    
    @Override
    public void stopEngine() {
        System.out.println("Motorcycle engine stopped");
    }
    
    @Override
    public String getVehicleType() {
        return "Motorcycle";
    }
    
    public boolean hasSidecar() {
        return hasSidecar;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Has sidecar: " + hasSidecar);
    }
}

// Example usage
public class Example1 {
    public static void main(String[] args) {
        System.out.println("=== Example 1: Vehicle Abstract Class ===\n");
        
        // Create vehicles
        Car car = new Car("Toyota", 2020, 25000.0, 4);
        Motorcycle motorcycle = new Motorcycle("Harley-Davidson", 2019, 15000.0, false);
        
        // Demonstrate abstract class usage
        System.out.println("Car demonstration:");
        car.displayInfo();
        car.startEngine();
        car.stopEngine();
        System.out.println();
        
        System.out.println("Motorcycle demonstration:");
        motorcycle.displayInfo();
        motorcycle.startEngine();
        motorcycle.stopEngine();
        System.out.println();
        
        // Demonstrate polymorphism
        Vehicle[] vehicles = {car, motorcycle};
        System.out.println("Polymorphism demonstration:");
        for (Vehicle vehicle : vehicles) {
            System.out.println("Starting " + vehicle.getVehicleType() + ":");
            vehicle.startEngine();
            System.out.println();
        }
    }
} 