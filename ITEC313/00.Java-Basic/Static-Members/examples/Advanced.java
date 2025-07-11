/**
 * Advanced: Complex Static Concepts and Patterns
 * 
 * This example demonstrates advanced static concepts including:
 * - Static nested classes
 * - Static initialization patterns
 * - Static utility methods
 * - Advanced static patterns
 * 
 * Key concepts:
 * - Static nested classes for organization
 * - Complex static initialization
 * - Static utility patterns
 * - Static vs instance method design
 * 
 * Python equivalent concepts:
 * - Nested classes
 * - Module-level utilities
 * - Factory patterns
 */
public class Advanced {
    
    // Static constants for the application
    public static final int DEFAULT_TIMEOUT = 30;
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8080;
    
    // Static variables for tracking
    private static int connectionCount = 0;
    private static double totalResponseTime = 0.0;
    private static boolean isInitialized = false;
    
    // Static block for complex initialization
    static {
        System.out.println("🔧 Advanced static initialization...");
        
        // Simulate complex initialization process
        try {
            // Simulate loading configuration
            Thread.sleep(100); // Simulate I/O operation
            
            // Initialize static variables
            connectionCount = 0;
            totalResponseTime = 0.0;
            isInitialized = true;
            
            System.out.println("✅ Advanced initialization complete");
            System.out.println("   Default timeout: " + DEFAULT_TIMEOUT + "s");
            System.out.println("   Default host: " + DEFAULT_HOST);
            System.out.println("   Default port: " + DEFAULT_PORT);
            System.out.println();
        } catch (InterruptedException e) {
            System.err.println("❌ Initialization failed: " + e.getMessage());
            isInitialized = false;
        }
    }
    
    /**
     * Static nested class for connection management
     * In Python: nested class or separate utility class
     */
    public static class ConnectionManager {
        
        // Static method to create a new connection
        public static Connection createConnection(String host, int port) {
            if (!isInitialized) {
                throw new IllegalStateException("System not initialized");
            }
            
            connectionCount++;
            return new Connection(host, port, connectionCount);
        }
        
        // Static method to create connection with default settings
        public static Connection createDefaultConnection() {
            return createConnection(DEFAULT_HOST, DEFAULT_PORT);
        }
        
        // Static method to get connection statistics
        public static String getStatistics() {
            double avgResponseTime = connectionCount > 0 ? totalResponseTime / connectionCount : 0.0;
            return String.format("Connections: %d, Avg Response: %.2fms", 
                               connectionCount, avgResponseTime);
        }
        
        // Static method to reset all statistics
        public static void resetStatistics() {
            System.out.println("🔄 Resetting connection statistics...");
            connectionCount = 0;
            totalResponseTime = 0.0;
            System.out.println("✅ Statistics reset complete");
            System.out.println();
        }
    }
    
    /**
     * Static nested class for performance monitoring
     */
    public static class PerformanceMonitor {
        
        // Static method to record response time
        public static void recordResponseTime(double responseTime) {
            totalResponseTime += responseTime;
        }
        
        // Static method to get performance metrics
        public static String getPerformanceMetrics() {
            if (connectionCount == 0) {
                return "No connections recorded";
            }
            
            double avgResponseTime = totalResponseTime / connectionCount;
            double maxResponseTime = Math.max(avgResponseTime * 2, 100.0); // Simulated max
            
            return String.format("Avg: %.2fms, Max: %.2fms, Total: %.2fms", 
                               avgResponseTime, maxResponseTime, totalResponseTime);
        }
        
        // Static method to check if performance is acceptable
        public static boolean isPerformanceAcceptable() {
            if (connectionCount == 0) return true;
            
            double avgResponseTime = totalResponseTime / connectionCount;
            return avgResponseTime < 50.0; // Acceptable threshold
        }
    }
    
    /**
     * Static nested class for configuration validation
     */
    public static class ConfigValidator {
        
        // Static method to validate host configuration
        public static boolean isValidHost(String host) {
            return host != null && !host.trim().isEmpty() && host.length() <= 255;
        }
        
        // Static method to validate port configuration
        public static boolean isValidPort(int port) {
            return port > 0 && port <= 65535;
        }
        
        // Static method to validate timeout configuration
        public static boolean isValidTimeout(int timeout) {
            return timeout > 0 && timeout <= 300; // Max 5 minutes
        }
        
        // Static method to get validation summary
        public static String getValidationSummary(String host, int port, int timeout) {
            boolean hostValid = isValidHost(host);
            boolean portValid = isValidPort(port);
            boolean timeoutValid = isValidTimeout(timeout);
            
            return String.format("Host: %s, Port: %s, Timeout: %s", 
                               hostValid ? "✓" : "✗",
                               portValid ? "✓" : "✗",
                               timeoutValid ? "✓" : "✗");
        }
    }
    
    /**
     * Connection class to represent a connection instance
     */
    public static class Connection {
        private String host;
        private int port;
        private int connectionId;
        private long startTime;
        
        public Connection(String host, int port, int connectionId) {
            this.host = host;
            this.port = port;
            this.connectionId = connectionId;
            this.startTime = System.currentTimeMillis();
            
            System.out.println("🔗 Created connection #" + connectionId + 
                             " to " + host + ":" + port);
        }
        
        // Instance method to simulate a request
        public void makeRequest() {
            if (!isInitialized) {
                System.out.println("❌ System not initialized - cannot make request");
                return;
            }
            
            // Simulate request processing time
            long requestStart = System.currentTimeMillis();
            
            try {
                // Simulate network delay
                Thread.sleep((long) (Math.random() * 100) + 10);
            } catch (InterruptedException e) {
                System.err.println("Request interrupted");
                return;
            }
            
            long requestEnd = System.currentTimeMillis();
            double responseTime = requestEnd - requestStart;
            
            // Record performance metrics
            PerformanceMonitor.recordResponseTime(responseTime);
            
            System.out.println("📡 Connection #" + connectionId + " request completed in " + 
                             String.format("%.2f", responseTime) + "ms");
        }
        
        // Instance method to get connection info
        public String getConnectionInfo() {
            long uptime = System.currentTimeMillis() - startTime;
            return String.format("Connection #%d: %s:%d (Uptime: %dms)", 
                               connectionId, host, port, uptime);
        }
    }
    
    /**
     * Main method to demonstrate advanced static concepts
     */
    public static void main(String[] args) {
        System.out.println("🚀 Advanced: Complex Static Concepts and Patterns");
        System.out.println("================================================");
        System.out.println();
        
        // Check initialization status
        System.out.println("📋 System status:");
        System.out.println("   Initialized: " + isInitialized);
        System.out.println("   Default timeout: " + DEFAULT_TIMEOUT + "s");
        System.out.println("   Default host: " + DEFAULT_HOST);
        System.out.println("   Default port: " + DEFAULT_PORT);
        System.out.println();
        
        // Demonstrate configuration validation
        System.out.println("🔍 Configuration validation:");
        String testHost = "example.com";
        int testPort = 9000;
        int testTimeout = 60;
        
        System.out.println("   Testing: " + testHost + ":" + testPort + " (timeout: " + testTimeout + "s)");
        System.out.println("   Validation: " + ConfigValidator.getValidationSummary(testHost, testPort, testTimeout));
        System.out.println();
        
        // Create connections using static factory methods
        System.out.println("🏭 Creating connections:");
        Connection conn1 = ConnectionManager.createDefaultConnection();
        Connection conn2 = ConnectionManager.createConnection("api.example.com", 443);
        Connection conn3 = ConnectionManager.createConnection("db.example.com", 5432);
        
        // Make requests to demonstrate performance monitoring
        System.out.println("📡 Making requests:");
        conn1.makeRequest();
        conn2.makeRequest();
        conn3.makeRequest();
        conn1.makeRequest();
        conn2.makeRequest();
        
        // Show statistics
        System.out.println("📊 Statistics:");
        System.out.println("   " + ConnectionManager.getStatistics());
        System.out.println("   Performance: " + PerformanceMonitor.getPerformanceMetrics());
        System.out.println("   Performance acceptable: " + PerformanceMonitor.isPerformanceAcceptable());
        System.out.println();
        
        // Show connection information
        System.out.println("👤 Connection information:");
        System.out.println("   " + conn1.getConnectionInfo());
        System.out.println("   " + conn2.getConnectionInfo());
        System.out.println("   " + conn3.getConnectionInfo());
        System.out.println();
        
        // Demonstrate reset functionality
        System.out.println("🔄 Reset demonstration:");
        ConnectionManager.resetStatistics();
        System.out.println("   " + ConnectionManager.getStatistics());
        System.out.println();
        
        System.out.println("✅ Advanced example complete!");
        System.out.println();
        System.out.println("💡 Advanced Key Points:");
        System.out.println("   - Static nested classes provide organization");
        System.out.println("   - Static blocks handle complex initialization");
        System.out.println("   - Static utility methods offer reusable functionality");
        System.out.println("   - Static factory methods create instances");
        System.out.println("   - Static vs instance methods serve different purposes");
        System.out.println("   - Static constants provide shared configuration");
    }
} 