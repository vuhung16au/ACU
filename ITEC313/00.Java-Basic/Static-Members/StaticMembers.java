/**
 * StaticMembers.java
 * 
 * This program demonstrates static members in Java:
 * - Static variables and methods
 * - Static initialization blocks
 * - Static vs instance members
 * - Static utility classes
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
public class StaticMembers {
    
    // Static variable - shared across all instances
    // In Python, this would be a class variable: class_var = 0
    public static int instanceCount = 0;
    
    // Static constant - final means it can't be changed
    // In Python: CLASS_CONSTANT = 100
    public static final int MAX_INSTANCES = 10;
    
    // Static variable to track total value across all instances
    public static double totalValue = 0.0;
    
    // Instance variables (non-static)
    private String name;
    private double value;
    
    // Static block - runs when class is loaded (before any instances created)
    // Python doesn't have this concept - you'd use module-level code
    static {
        System.out.println("🔧 Static block executed - class is being loaded");
        System.out.println("📊 Initializing static variables...");
        instanceCount = 0;
        totalValue = 0.0;
        System.out.println("✅ Static initialization complete");
        System.out.println();
    }
    
    /**
     * Constructor - creates a new instance
     * In Python: def __init__(self, name, value):
     */
    public StaticMembers(String name, double value) {
        this.name = name;
        this.value = value;
        
        // Increment static counter
        instanceCount++;
        totalValue += value;
        
        System.out.println("🏗️  Created instance: " + name + " (value: " + value + ")");
        System.out.println("📈 Total instances: " + instanceCount);
        System.out.println("💰 Total value: " + totalValue);
        System.out.println();
    }
    
    /**
     * Static method - can be called without creating an instance
     * In Python: @staticmethod or @classmethod
     */
    public static void displayStaticInfo() {
        System.out.println("📊 STATIC INFORMATION:");
        System.out.println("   Instance count: " + instanceCount);
        System.out.println("   Total value: " + totalValue);
        System.out.println("   Max instances: " + MAX_INSTANCES);
        
        if (instanceCount > 0) {
            double average = totalValue / instanceCount;
            System.out.println("   Average value: " + average);
        }
        System.out.println();
    }
    
    /**
     * Static method to check if we can create more instances
     * In Python: @classmethod
     */
    public static boolean canCreateMore() {
        return instanceCount < MAX_INSTANCES;
    }
    
    /**
     * Static method to reset all static data
     * In Python: @classmethod
     */
    public static void resetAll() {
        System.out.println("🔄 Resetting all static data...");
        instanceCount = 0;
        totalValue = 0.0;
        System.out.println("✅ Reset complete");
        System.out.println();
    }
    
    /**
     * Instance method - requires an instance to call
     * In Python: def instance_method(self):
     */
    public void displayInstanceInfo() {
        System.out.println("👤 INSTANCE INFORMATION:");
        System.out.println("   Name: " + name);
        System.out.println("   Value: " + value);
        System.out.println("   Instance #" + instanceCount + " of " + MAX_INSTANCES);
        System.out.println();
    }
    
    /**
     * Instance method that uses static data
     * In Python: def method_using_class_var(self):
     */
    public void updateValue(double newValue) {
        // Remove old value from total
        totalValue -= this.value;
        
        // Update instance value
        this.value = newValue;
        
        // Add new value to total
        totalValue += this.value;
        
        System.out.println("🔄 Updated " + name + " value to: " + newValue);
        System.out.println("💰 New total value: " + totalValue);
        System.out.println();
    }
    
    /**
     * Main method - entry point of the program
     * In Python: if __name__ == "__main__":
     */
    public static void main(String[] args) {
        System.out.println("🚀 STATIC MEMBERS DEMONSTRATION");
        System.out.println("=================================");
        System.out.println();
        
        // Demonstrate static method call without creating instances
        System.out.println("📋 Initial static information:");
        StaticMembers.displayStaticInfo();
        
        // Create instances to demonstrate static vs instance behavior
        System.out.println("🏗️  CREATING INSTANCES:");
        System.out.println("------------------------");
        
        StaticMembers obj1 = new StaticMembers("Object-1", 100.0);
        StaticMembers obj2 = new StaticMembers("Object-2", 250.0);
        StaticMembers obj3 = new StaticMembers("Object-3", 75.5);
        
        // Show static information after creating instances
        System.out.println("📊 Static info after creating instances:");
        StaticMembers.displayStaticInfo();
        
        // Demonstrate instance methods
        System.out.println("👤 INSTANCE METHODS:");
        System.out.println("-------------------");
        obj1.displayInstanceInfo();
        obj2.displayInstanceInfo();
        obj3.displayInstanceInfo();
        
        // Demonstrate updating values
        System.out.println("🔄 UPDATING VALUES:");
        System.out.println("------------------");
        obj1.updateValue(150.0);
        obj2.updateValue(300.0);
        
        // Show final static state
        System.out.println("📊 Final static information:");
        StaticMembers.displayStaticInfo();
        
        // Demonstrate static utility methods
        System.out.println("🔍 UTILITY METHODS:");
        System.out.println("------------------");
        System.out.println("Can create more instances? " + StaticMembers.canCreateMore());
        
        // Demonstrate reset functionality
        System.out.println("🔄 RESET DEMONSTRATION:");
        System.out.println("----------------------");
        StaticMembers.resetAll();
        StaticMembers.displayStaticInfo();
        
        System.out.println("✅ Demonstration complete!");
        System.out.println();
        System.out.println("💡 KEY TAKEAWAYS:");
        System.out.println("   - Static members belong to the class, not instances");
        System.out.println("   - Static methods can be called without creating objects");
        System.out.println("   - Static variables are shared across all instances");
        System.out.println("   - Static blocks run when class is loaded");
        System.out.println("   - Instance methods can access static members");
        System.out.println("   - Static methods cannot access instance variables");
    }
} 