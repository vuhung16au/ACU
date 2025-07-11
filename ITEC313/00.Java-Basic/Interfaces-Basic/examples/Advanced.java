/**
 * Advanced.java - Advanced Interface Patterns
 * 
 * This example demonstrates advanced interface concepts:
 * - Nested interfaces (similar to Python's nested classes)
 * - Static methods in interfaces (Java 8+)
 * - Interface inheritance
 * - Complex interface hierarchies
 * - Interface composition patterns
 * 
 * Key concepts:
 * - Nested interfaces
 * - Static methods in interfaces
 * - Interface inheritance
 * - Complex interface design patterns
 */

// Main interface with nested interfaces
interface Vehicle {
    // Interface constants
    String DEFAULT_FUEL_TYPE = "gasoline";
    int MAX_SPEED_LIMIT = 120;
    
    // Abstract methods
    void start();
    void stop();
    void accelerate(double amount);
    double getCurrentSpeed();
    
    // Default method
    default void honk() {
        System.out.println("Beep! Beep!");
    }
    
    // Static method (Java 8+)
    static boolean isValidSpeed(double speed) {
        return speed >= 0 && speed <= MAX_SPEED_LIMIT;
    }
    
    // Nested interface for engine
    interface Engine {
        void startEngine();
        void stopEngine();
        double getEnginePower();
        FuelType getFuelType();
        
        // Nested enum
        enum FuelType {
            GASOLINE, DIESEL, ELECTRIC, HYBRID
        }
    }
    
    // Nested interface for transmission
    interface Transmission {
        void shiftGear(int gear);
        int getCurrentGear();
        int getMaxGears();
        
        enum GearType {
            MANUAL, AUTOMATIC, CVT
        }
    }
}

// Interface inheritance
interface Car extends Vehicle {
    // Additional methods specific to cars
    void turnOnLights();
    void turnOffLights();
    boolean areLightsOn();
    
    // Override default method
    @Override
    default void honk() {
        System.out.println("Car horn: Honk! Honk!");
    }
}

// Interface for electric vehicles
interface ElectricVehicle extends Vehicle {
    // Electric vehicle specific methods
    double getBatteryLevel();
    void charge();
    double getRange();
    
    // Override default method
    @Override
    default void honk() {
        System.out.println("Electric horn: Whirr!");
    }
}

// Interface composition - combining multiple interfaces
interface SmartCar extends Car, ElectricVehicle {
    // Smart car specific methods
    void enableAutopilot();
    void disableAutopilot();
    boolean isAutopilotEnabled();
    
    // Override methods from both parent interfaces
    @Override
    default void honk() {
        System.out.println("Smart car horn: Digital beep!");
    }
}

// Concrete implementation of Engine interface
class GasolineEngine implements Vehicle.Engine {
    private boolean isRunning = false;
    private double power = 200.0; // horsepower
    
    @Override
    public void startEngine() {
        isRunning = true;
        System.out.println("Gasoline engine started");
    }
    
    @Override
    public void stopEngine() {
        isRunning = false;
        System.out.println("Gasoline engine stopped");
    }
    
    @Override
    public double getEnginePower() {
        return power;
    }
    
    @Override
    public FuelType getFuelType() {
        return FuelType.GASOLINE;
    }
}

// Concrete implementation of Transmission interface
class AutomaticTransmission implements Vehicle.Transmission {
    private int currentGear = 1;
    private final int maxGears = 6;
    
    @Override
    public void shiftGear(int gear) {
        if (gear >= 1 && gear <= maxGears) {
            currentGear = gear;
            System.out.println("Shifted to gear " + gear);
        } else {
            System.out.println("Invalid gear: " + gear);
        }
    }
    
    @Override
    public int getCurrentGear() {
        return currentGear;
    }
    
    @Override
    public int getMaxGears() {
        return maxGears;
    }
}

// Concrete implementation of Car interface
class Sedan implements Car {
    private Vehicle.Engine engine;
    private Vehicle.Transmission transmission;
    private double currentSpeed = 0.0;
    private boolean lightsOn = false;
    
    public Sedan() {
        this.engine = new GasolineEngine();
        this.transmission = new AutomaticTransmission();
    }
    
    @Override
    public void start() {
        engine.startEngine();
        System.out.println("Sedan started");
    }
    
    @Override
    public void stop() {
        engine.stopEngine();
        currentSpeed = 0.0;
        System.out.println("Sedan stopped");
    }
    
    @Override
    public void accelerate(double amount) {
        if (Vehicle.isValidSpeed(currentSpeed + amount)) {
            currentSpeed += amount;
            System.out.println("Accelerated to " + currentSpeed + " km/h");
        } else {
            System.out.println("Cannot accelerate beyond speed limit");
        }
    }
    
    @Override
    public double getCurrentSpeed() {
        return currentSpeed;
    }
    
    @Override
    public void turnOnLights() {
        lightsOn = true;
        System.out.println("Car lights turned on");
    }
    
    @Override
    public void turnOffLights() {
        lightsOn = false;
        System.out.println("Car lights turned off");
    }
    
    @Override
    public boolean areLightsOn() {
        return lightsOn;
    }
    
    // Additional methods to access nested interfaces
    public Vehicle.Engine getEngine() {
        return engine;
    }
    
    public Vehicle.Transmission getTransmission() {
        return transmission;
    }
}

// Concrete implementation of SmartCar interface
class TeslaModelS implements SmartCar {
    private double batteryLevel = 100.0;
    private double currentSpeed = 0.0;
    private boolean autopilotEnabled = false;
    private boolean lightsOn = false;
    
    @Override
    public void start() {
        System.out.println("Tesla Model S started silently");
    }
    
    @Override
    public void stop() {
        currentSpeed = 0.0;
        System.out.println("Tesla Model S stopped");
    }
    
    @Override
    public void accelerate(double amount) {
        if (Vehicle.isValidSpeed(currentSpeed + amount)) {
            currentSpeed += amount;
            System.out.println("Tesla accelerated to " + currentSpeed + " km/h");
        } else {
            System.out.println("Cannot accelerate beyond speed limit");
        }
    }
    
    @Override
    public double getCurrentSpeed() {
        return currentSpeed;
    }
    
    @Override
    public void turnOnLights() {
        lightsOn = true;
        System.out.println("Tesla lights turned on");
    }
    
    @Override
    public void turnOffLights() {
        lightsOn = false;
        System.out.println("Tesla lights turned off");
    }
    
    @Override
    public boolean areLightsOn() {
        return lightsOn;
    }
    
    @Override
    public double getBatteryLevel() {
        return batteryLevel;
    }
    
    @Override
    public void charge() {
        batteryLevel = 100.0;
        System.out.println("Tesla fully charged");
    }
    
    @Override
    public double getRange() {
        return batteryLevel * 3.5; // 3.5 km per 1% battery
    }
    
    @Override
    public void enableAutopilot() {
        autopilotEnabled = true;
        System.out.println("Autopilot enabled");
    }
    
    @Override
    public void disableAutopilot() {
        autopilotEnabled = false;
        System.out.println("Autopilot disabled");
    }
    
    @Override
    public boolean isAutopilotEnabled() {
        return autopilotEnabled;
    }
}

// Main class to demonstrate advanced interface patterns
public class Advanced {
    public static void main(String[] args) {
        System.out.println("=== Advanced Interface Patterns Demo ===\n");
        
        // Demonstrate nested interfaces
        demonstrateNestedInterfaces();
        
        // Demonstrate interface inheritance
        demonstrateInterfaceInheritance();
        
        // Demonstrate static methods in interfaces
        demonstrateStaticMethods();
        
        // Demonstrate complex interface composition
        demonstrateInterfaceComposition();
    }
    
    private static void demonstrateNestedInterfaces() {
        System.out.println("1. Nested Interfaces:");
        System.out.println("---------------------");
        
        Sedan sedan = new Sedan();
        
        // Access nested interface methods
        Vehicle.Engine engine = sedan.getEngine();
        Vehicle.Transmission transmission = sedan.getTransmission();
        
        System.out.println("Engine fuel type: " + engine.getFuelType());
        System.out.println("Engine power: " + engine.getEnginePower() + " hp");
        System.out.println("Transmission max gears: " + transmission.getMaxGears());
        
        // Use nested enum
        Vehicle.Engine.FuelType fuelType = engine.getFuelType();
        System.out.println("Fuel type enum: " + fuelType);
        System.out.println();
    }
    
    private static void demonstrateInterfaceInheritance() {
        System.out.println("2. Interface Inheritance:");
        System.out.println("-------------------------");
        
        Car car = new Sedan();
        ElectricVehicle ev = new TeslaModelS();
        
        // Demonstrate inheritance
        car.start();
        car.honk(); // Uses Car's overridden method
        car.turnOnLights();
        
        System.out.println();
        
        ev.start();
        ev.honk(); // Uses ElectricVehicle's overridden method
        System.out.println("Battery level: " + ev.getBatteryLevel() + "%");
        System.out.println("Range: " + ev.getRange() + " km");
        System.out.println();
    }
    
    private static void demonstrateStaticMethods() {
        System.out.println("3. Static Methods in Interfaces:");
        System.out.println("--------------------------------");
        
        // Use static method from interface
        System.out.println("Is 50 km/h valid? " + Vehicle.isValidSpeed(50));
        System.out.println("Is 150 km/h valid? " + Vehicle.isValidSpeed(150));
        System.out.println("Is -10 km/h valid? " + Vehicle.isValidSpeed(-10));
        
        // Demonstrate with vehicle
        Sedan sedan = new Sedan();
        sedan.accelerate(50);
        sedan.accelerate(100); // Should fail due to speed limit
        System.out.println();
    }
    
    private static void demonstrateInterfaceComposition() {
        System.out.println("4. Interface Composition (Multiple Inheritance):");
        System.out.println("-----------------------------------------------");
        
        SmartCar tesla = new TeslaModelS();
        
        // Demonstrate methods from all inherited interfaces
        System.out.println("Tesla Model S Demo:");
        tesla.start();
        tesla.honk(); // Uses SmartCar's overridden method
        tesla.turnOnLights();
        tesla.accelerate(80);
        System.out.println("Speed: " + tesla.getCurrentSpeed() + " km/h");
        System.out.println("Battery: " + tesla.getBatteryLevel() + "%");
        System.out.println("Range: " + tesla.getRange() + " km");
        tesla.enableAutopilot();
        System.out.println("Autopilot enabled: " + tesla.isAutopilotEnabled());
        tesla.stop();
        System.out.println();
        
        // Demonstrate polymorphism with interface arrays
        Vehicle[] vehicles = {new Sedan(), new TeslaModelS()};
        System.out.println("Polymorphic vehicle array:");
        for (Vehicle vehicle : vehicles) {
            vehicle.start();
            vehicle.honk();
            vehicle.accelerate(30);
            System.out.println("Current speed: " + vehicle.getCurrentSpeed() + " km/h");
            vehicle.stop();
            System.out.println();
        }
    }
} 