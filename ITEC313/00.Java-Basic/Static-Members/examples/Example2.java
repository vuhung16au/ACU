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
    
    // Static variables for configuration
    public static String appName;
    public static int maxConnections;
    public static double timeout;
    public static boolean debugMode;
    
    // Static block - runs when class is loaded
    // Python equivalent: module-level code or class-level initialization
    static {
        System.out.println("🔧 Loading configuration...");
        
        // Simulate loading configuration from external source
        appName = "StaticConfigDemo";
        maxConnections = 50;
        timeout = 30.0;
        debugMode = true;
        
        System.out.println("✅ Configuration loaded successfully");
        System.out.println("   App Name: " + appName);
        System.out.println("   Max Connections: " + maxConnections);
        System.out.println("   Timeout: " + timeout + " seconds");
        System.out.println("   Debug Mode: " + debugMode);
        System.out.println();
    }
    
    // Static utility class for configuration management
    public static class ConfigManager {
        
        // Static method to validate configuration
        public static boolean isValidConfig() {
            return appName != null && 
                   maxConnections > 0 && 
                   timeout > 0 && 
                   appName.length() > 0;
        }
        
        // Static method to get configuration summary
        public static String getConfigSummary() {
            return String.format("App: %s, Connections: %d, Timeout: %.1fs, Debug: %s",
                               appName, maxConnections, timeout, debugMode);
        }
        
        // Static method to update configuration
        public static void updateConfig(String name, int connections, double time, boolean debug) {
            System.out.println("🔄 Updating configuration...");
            appName = name;
            maxConnections = connections;
            timeout = time;
            debugMode = debug;
            System.out.println("✅ Configuration updated");
            System.out.println();
        }
    }
    
    // Static factory method - creates instances with default settings
    // In Python: @classmethod
    public static Example2 createDefault() {
        return new Example2("Default Instance");
    }
    
    // Static factory method with custom settings
    public static Example2 createWithSettings(String name, int priority) {
        Example2 instance = new Example2(name);
        instance.priority = priority;
        return instance;
    }
    
    // Instance variables
    private String name;
    private int priority;
    private static int instanceCounter = 0;
    
    /**
     * Constructor
     * In Python: def __init__(self, name):
     */
    public Example2(String name) {
        this.name = name;
        this.priority = 1;
        instanceCounter++;
        
        System.out.println("🏗️  Created instance: " + name + " (Priority: " + priority + ")");
        System.out.println("📊 Total instances: " + instanceCounter);
        System.out.println();
    }
    
    /**
     * Instance method that uses static configuration
     * In Python: def connect(self):
     */
    public void connect() {
        if (ConfigManager.isValidConfig()) {
            System.out.println("🔗 " + name + " connecting to " + appName);
            System.out.println("   Using " + maxConnections + " max connections");
            System.out.println("   Timeout set to " + timeout + " seconds");
            System.out.println("   Debug mode: " + debugMode);
        } else {
            System.out.println("❌ Invalid configuration - cannot connect");
        }
        System.out.println();
    }
    
    /**
     * Instance method to display instance information
     * In Python: def display_info(self):
     */
    public void displayInfo() {
        System.out.println("👤 Instance: " + name);
        System.out.println("   Priority: " + priority);
        System.out.println("   Instance #" + instanceCounter);
        System.out.println("   Config: " + ConfigManager.getConfigSummary());
        System.out.println();
    }
    
    /**
     * Static method to get instance statistics
     * In Python: @classmethod
     */
    public static void displayInstanceStats() {
        System.out.println("📊 Instance Statistics:");
        System.out.println("   Total instances created: " + instanceCounter);
        System.out.println("   Current configuration: " + ConfigManager.getConfigSummary());
        System.out.println("   Configuration valid: " + ConfigManager.isValidConfig());
        System.out.println();
    }
    
    /**
     * Main method to demonstrate the example
     */
    public static void main(String[] args) {
        System.out.println("🚀 Example 2: Static Blocks and Advanced Concepts");
        System.out.println("================================================");
        System.out.println();
        
        // Show initial configuration (loaded by static block)
        System.out.println("📋 Initial configuration:");
        System.out.println(ConfigManager.getConfigSummary());
        System.out.println();
        
        // Demonstrate static factory methods
        System.out.println("🏭 Creating instances with factory methods:");
        Example2 defaultInstance = Example2.createDefault();
        Example2 customInstance = Example2.createWithSettings("Custom-Instance", 5);
        
        // Show instance information
        System.out.println("👤 Instance information:");
        defaultInstance.displayInfo();
        customInstance.displayInfo();
        
        // Demonstrate instance methods using static configuration
        System.out.println("🔗 Testing connections:");
        defaultInstance.connect();
        customInstance.connect();
        
        // Show instance statistics
        System.out.println("📊 Statistics:");
        Example2.displayInstanceStats();
        
        // Demonstrate configuration updates
        System.out.println("🔄 Updating configuration:");
        ConfigManager.updateConfig("UpdatedApp", 100, 60.0, false);
        
        // Test connections with new configuration
        System.out.println("🔗 Testing connections with new config:");
        defaultInstance.connect();
        customInstance.connect();
        
        // Show final statistics
        System.out.println("📊 Final statistics:");
        Example2.displayInstanceStats();
        
        System.out.println("✅ Example 2 complete!");
        System.out.println();
        System.out.println("💡 Key Points:");
        System.out.println("   - Static blocks run when class is loaded");
        System.out.println("   - Static utility classes provide organization");
        System.out.println("   - Static factory methods create instances");
        System.out.println("   - Instance methods can access static configuration");
        System.out.println("   - Static methods provide utility functions");
    }
} 