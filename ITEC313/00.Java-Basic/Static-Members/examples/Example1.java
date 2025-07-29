/**
 * Example1.java
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
public class Example1 {
    
    // Static variable - shared across all instances
    // In Python: class Counter: count = 0
    public static int totalCount = 0;
    
    // Static constant
    public static final int MAX_COUNT = 100;
    
    // Instance variable - each instance has its own copy
    private int instanceCount;
    private String name;
    
    /**
     * Constructor
     * In Python: def __init__(self, name):
     */
    public Example1(String name) {
        this.name = name;
        this.instanceCount = 0;
        System.out.println("Created counter: " + name);
    }
    
    /**
     * Static method - can be called without creating an instance
     * In Python: @staticmethod
     */
    public static void displayTotalCount() {
        System.out.println("📊 Total count across all instances: " + totalCount);
        System.out.println("📈 Max allowed count: " + MAX_COUNT);
        
        if (totalCount > 0) {
            double percentage = (double) totalCount / MAX_COUNT * 100;
            System.out.println("📊 Percentage of max: " + percentage + "%");
        }
        System.out.println();
    }
    
    /**
     * Static method to check if we can increment more
     * In Python: @classmethod
     */
    public static boolean canIncrement() {
        return totalCount < MAX_COUNT;
    }
    
    /**
     * Static method to reset the total count
     * In Python: @classmethod
     */
    public static void resetTotal() {
        System.out.println("🔄 Resetting total count...");
        totalCount = 0;
        System.out.println("✅ Total count reset to 0");
        System.out.println();
    }
    
    /**
     * Instance method - requires an instance to call
     * In Python: def increment(self):
     */
    public void increment() {
        if (canIncrement()) {
            instanceCount++;
            totalCount++;
            System.out.println("➕ " + name + " incremented to " + instanceCount);
            System.out.println("📊 Total count is now: " + totalCount);
        } else {
            System.out.println("❌ Cannot increment - max count reached!");
        }
        System.out.println();
    }
    
    /**
     * Instance method to display instance information
     * In Python: def display_info(self):
     */
    public void displayInfo() {
        System.out.println("👤 Instance: " + name);
        System.out.println("   Instance count: " + instanceCount);
        System.out.println("   Total count: " + totalCount);
        System.out.println("   Can increment: " + canIncrement());
        System.out.println();
    }
    
    /**
     * Main method to demonstrate the example
     */
    public static void main(String[] args) {
        System.out.println("🚀 Example 1: Basic Static Variables and Methods");
        System.out.println("================================================");
        System.out.println();
        
        // Show initial static state
        System.out.println("📋 Initial state:");
        Example1.displayTotalCount();
        
        // Create instances
        System.out.println("🏗️  Creating counters:");
        Example1 counter1 = new Example1("Counter-A");
        Example1 counter2 = new Example1("Counter-B");
        Example1 counter3 = new Example1("Counter-C");
        
        // Demonstrate incrementing
        System.out.println("➕ Incrementing counters:");
        counter1.increment();
        counter1.increment();
        counter2.increment();
        counter3.increment();
        counter3.increment();
        
        // Show current state
        System.out.println("📊 Current state:");
        Example1.displayTotalCount();
        
        // Show instance information
        System.out.println("👤 Instance information:");
        counter1.displayInfo();
        counter2.displayInfo();
        counter3.displayInfo();
        
        // Demonstrate static utility methods
        System.out.println("🔍 Utility methods:");
        System.out.println("Can increment more? " + Example1.canIncrement());
        
        // Demonstrate reset
        System.out.println("🔄 Reset demonstration:");
        Example1.resetTotal();
        Example1.displayTotalCount();
        
        System.out.println("✅ Example 1 complete!");
        System.out.println();
        System.out.println("💡 Key Points:");
        System.out.println("   - Static variables are shared across all instances");
        System.out.println("   - Static methods can be called without objects");
        System.out.println("   - Instance methods can access static members");
        System.out.println("   - Static methods cannot access instance variables");
    }
} 