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
    
    // Enum as constants - type-safe and more powerful than simple constants
    public enum Status {
        ACTIVE("Active"),
        INACTIVE("Inactive"),
        PENDING("Pending"),
        COMPLETED("Completed");
        
        private final String displayName;
        
        Status(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constants for configuration
    public static final int MAX_RETRY_ATTEMPTS = 3;
    public static final double INTEREST_RATE = 0.05;
    public static final String DEFAULT_CURRENCY = "USD";
    
    // Final instance variables
    private final String accountId;
    private final Status accountStatus;
    private final double balance;
    
    public Advanced(String accountId, Status status, double balance) {
        this.accountId = accountId;
        this.accountStatus = status;
        this.balance = balance;
    }
    
    /**
     * Demonstrates enum constants
     */
    public void demonstrateEnumConstants() {
        System.out.println("=== Enum Constants ===");
        
        System.out.println("Available statuses:");
        for (Status status : Status.values()) {
            System.out.println("- " + status.name() + ": " + status.getDisplayName());
        }
        
        // Using enum constants
        Status currentStatus = Status.ACTIVE;
        System.out.println("Current status: " + currentStatus.getDisplayName());
        
        // Switch statement with enums (type-safe)
        switch (currentStatus) {
            case ACTIVE:
                System.out.println("Account is active and operational");
                break;
            case INACTIVE:
                System.out.println("Account is inactive");
                break;
            case PENDING:
                System.out.println("Account is pending approval");
                break;
            case COMPLETED:
                System.out.println("Account process completed");
                break;
        }
        
        System.out.println("✓ Enums provide type-safe constants");
        System.out.println("✓ Enums can have methods and properties");
    }
    
    /**
     * Demonstrates final variables in complex calculations
     */
    public void demonstrateComplexCalculations() {
        System.out.println("\n=== Complex Calculations with Final Variables ===");
        
        // Constants for financial calculations
        final double MONTHLY_INTEREST_RATE = INTEREST_RATE / 12;
        final int COMPOUNDING_PERIODS = 12;
        final double MINIMUM_BALANCE = 100.0;
        final double OVERDRAFT_FEE = 25.0;
        
        // Simulate compound interest calculation
        double principal = balance;
        double monthlyRate = MONTHLY_INTEREST_RATE;
        int periods = COMPOUNDING_PERIODS;
        
        double futureValue = principal * Math.pow(1 + monthlyRate, periods);
        double interestEarned = futureValue - principal;
        
        System.out.println("Financial Calculations:");
        System.out.println("Principal: $" + principal);
        System.out.println("Monthly Interest Rate: " + (monthlyRate * 100) + "%");
        System.out.println("Compounding Periods: " + periods);
        System.out.println("Future Value: $" + futureValue);
        System.out.println("Interest Earned: $" + interestEarned);
        
        // Check for minimum balance
        if (balance < MINIMUM_BALANCE) {
            System.out.println("Warning: Balance below minimum of $" + MINIMUM_BALANCE);
            System.out.println("Overdraft fee would be: $" + OVERDRAFT_FEE);
        }
    }
    
    /**
     * Demonstrates final variables in error handling
     */
    public void demonstrateErrorHandling() {
        System.out.println("\n=== Error Handling with Final Variables ===");
        
        // Constants for error handling
        final int MAX_RETRIES = MAX_RETRY_ATTEMPTS;
        final String ERROR_PREFIX = "ERROR:";
        final String SUCCESS_PREFIX = "SUCCESS:";
        
        // Simulate retry logic
        int attemptCount = 0;
        boolean operationSuccessful = false;
        
        while (attemptCount < MAX_RETRIES && !operationSuccessful) {
            attemptCount++;
            System.out.println("Attempt " + attemptCount + " of " + MAX_RETRIES);
            
            // Simulate operation (randomly fail)
            if (Math.random() > 0.5) {
                operationSuccessful = true;
                System.out.println(SUCCESS_PREFIX + " Operation completed successfully");
            } else {
                System.out.println(ERROR_PREFIX + " Operation failed, retrying...");
                
                if (attemptCount == MAX_RETRIES) {
                    System.out.println(ERROR_PREFIX + " Maximum retries exceeded");
                }
            }
        }
        
        System.out.println("✓ Final variables ensure consistent error handling");
    }
    
    /**
     * Demonstrates final variables in data validation
     */
    public void demonstrateDataValidation() {
        System.out.println("\n=== Data Validation with Final Variables ===");
        
        // Constants for validation
        final int MIN_ACCOUNT_LENGTH = 8;
        final int MAX_ACCOUNT_LENGTH = 16;
        final double MIN_BALANCE = 0.0;
        final double MAX_BALANCE = 1000000.0;
        
        // Test data
        String[] testAccountIds = {"123", "12345678", "1234567890123456", "12345678901234567"};
        double[] testBalances = {-100.0, 0.0, 50000.0, 2000000.0};
        
        System.out.println("Validating account IDs:");
        for (String accountId : testAccountIds) {
            boolean isValid = accountId.length() >= MIN_ACCOUNT_LENGTH && 
                            accountId.length() <= MAX_ACCOUNT_LENGTH;
            System.out.println("Account ID '" + accountId + "': " + 
                             (isValid ? "VALID" : "INVALID"));
        }
        
        System.out.println("\nValidating balances:");
        for (double balance : testBalances) {
            boolean isValid = balance >= MIN_BALANCE && balance <= MAX_BALANCE;
            System.out.println("Balance $" + balance + ": " + 
                             (isValid ? "VALID" : "INVALID"));
        }
        
        System.out.println("✓ Final variables ensure consistent validation rules");
    }
    
    /**
     * Demonstrates final variables in configuration management
     */
    public void demonstrateConfigurationManagement() {
        System.out.println("\n=== Configuration Management ===");
        
        // Configuration constants
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/bankdb";
        final int CONNECTION_TIMEOUT = 30;
        final int QUERY_TIMEOUT = 60;
        final String LOG_LEVEL = "INFO";
        final boolean ENABLE_CACHING = true;
        final int CACHE_SIZE = 1000;
        
        // Simulate configuration usage
        System.out.println("Database Configuration:");
        System.out.println("- URL: " + DATABASE_URL);
        System.out.println("- Connection Timeout: " + CONNECTION_TIMEOUT + " seconds");
        System.out.println("- Query Timeout: " + QUERY_TIMEOUT + " seconds");
        
        System.out.println("\nApplication Configuration:");
        System.out.println("- Log Level: " + LOG_LEVEL);
        System.out.println("- Caching Enabled: " + ENABLE_CACHING);
        if (ENABLE_CACHING) {
            System.out.println("- Cache Size: " + CACHE_SIZE + " entries");
        }
        
        // Simulate configuration-based behavior
        if (LOG_LEVEL.equals("DEBUG")) {
            System.out.println("Debug logging enabled");
        } else if (LOG_LEVEL.equals("INFO")) {
            System.out.println("Info logging enabled");
        }
        
        System.out.println("✓ Final variables centralize configuration");
    }
    
    /**
     * Demonstrates final variables in performance optimization
     */
    public void demonstratePerformanceOptimization() {
        System.out.println("\n=== Performance Optimization with Final Variables ===");
        
        // Performance-related constants
        final int BUFFER_SIZE = 8192;
        final int THREAD_POOL_SIZE = 4;
        final long CACHE_TTL = 3600000; // 1 hour in milliseconds
        final int MAX_CONNECTIONS = 10;
        
        // Simulate performance calculations
        long startTime = System.currentTimeMillis();
        
        // Simulate work with constants
        for (int i = 0; i < BUFFER_SIZE; i++) {
            // Simulate processing
            if (i % 1000 == 0) {
                System.out.println("Processed " + i + " items");
            }
        }
        
        long endTime = System.currentTimeMillis();
        long processingTime = endTime - startTime;
        
        System.out.println("Performance Results:");
        System.out.println("- Buffer Size: " + BUFFER_SIZE + " bytes");
        System.out.println("- Thread Pool Size: " + THREAD_POOL_SIZE + " threads");
        System.out.println("- Cache TTL: " + (CACHE_TTL / 1000) + " seconds");
        System.out.println("- Max Connections: " + MAX_CONNECTIONS);
        System.out.println("- Processing Time: " + processingTime + " ms");
        
        System.out.println("✓ Final variables optimize performance tuning");
    }
    
    /**
     * Main method to run the advanced example
     */
    public static void main(String[] args) {
        System.out.println("Advanced: Final Classes, Enums, and Complex Scenarios");
        System.out.println("=====================================================\n");
        
        Advanced advanced = new Advanced("ACC123456", Status.ACTIVE, 5000.0);
        
        advanced.demonstrateEnumConstants();
        advanced.demonstrateComplexCalculations();
        advanced.demonstrateErrorHandling();
        advanced.demonstrateDataValidation();
        advanced.demonstrateConfigurationManagement();
        advanced.demonstratePerformanceOptimization();
        
        System.out.println("\n=== Advanced Key Takeaways ===");
        System.out.println("✓ Enums provide type-safe, powerful constants");
        System.out.println("✓ Final variables ensure consistency in complex scenarios");
        System.out.println("✓ Constants improve code maintainability and readability");
        System.out.println("✓ Final variables optimize performance and configuration");
        System.out.println("✓ Java's final provides compile-time guarantees");
    }
} 