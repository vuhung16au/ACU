/**
 * Example2.java - Advanced Custom Exception Examples
 * 
 * This example demonstrates more advanced custom exception concepts:
 * - Exception hierarchies
 * - Exception chaining
 * - Custom exception with additional data
 * - Multiple exception handling
 */

// Base exception for all application errors
abstract class ApplicationException extends Exception {
    private String errorCode;
    
    public ApplicationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}

// Specific exception for user-related errors
class UserException extends ApplicationException {
    private String userId;
    
    public UserException(String message, String errorCode, String userId) {
        super(message, errorCode);
        this.userId = userId;
    }
    
    public UserException(String message, String errorCode, String userId, Throwable cause) {
        super(message, errorCode);
        this.userId = userId;
        initCause(cause);
    }
    
    public String getUserId() {
        return userId;
    }
    
    @Override
    public String toString() {
        return "UserException{" +
               "message='" + getMessage() + '\'' +
               ", errorCode='" + getErrorCode() + '\'' +
               ", userId='" + userId + '\'' +
               '}';
    }
}

// Specific exception for data-related errors
class DataException extends ApplicationException {
    private String dataType;
    
    public DataException(String message, String errorCode, String dataType) {
        super(message, errorCode);
        this.dataType = dataType;
    }
    
    public String getDataType() {
        return dataType;
    }
}

// Custom exception with severity levels
class CriticalException extends RuntimeException {
    public enum Severity { LOW, MEDIUM, HIGH, CRITICAL }
    
    private Severity severity;
    private long timestamp;
    
    public CriticalException(String message, Severity severity) {
        super(message);
        this.severity = severity;
        this.timestamp = System.currentTimeMillis();
    }
    
    public Severity getSeverity() {
        return severity;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
}

public class Example2 {
    
    /**
     * Demonstrates exception hierarchy usage
     */
    public static void processUser(String userId, String userData) throws UserException, DataException {
        // Validate user ID
        if (userId == null || userId.trim().isEmpty()) {
            throw new UserException("Invalid user ID", "USER_001", userId);
        }
        
        // Validate user data
        if (userData == null) {
            throw new DataException("User data is null", "DATA_001", "userData");
        }
        
        // Simulate processing
        if (userData.length() < 5) {
            throw new UserException("User data too short", "USER_002", userId);
        }
        
        System.out.println("User " + userId + " processed successfully");
    }
    
    /**
     * Demonstrates exception chaining
     */
    public static void processUserData(String userId, String dataStr) throws UserException {
        try {
            // Try to parse data as integer
            int data = Integer.parseInt(dataStr);
            
            // Validate the parsed data
            if (data < 0) {
                throw new UserException("Data cannot be negative", "USER_003", userId);
            }
            
            System.out.println("User " + userId + " data processed: " + data);
            
        } catch (NumberFormatException e) {
            // Chain the exception - preserve the original cause
            throw new UserException("Invalid data format for user: " + userId, "USER_004", userId, e);
        }
    }
    
    /**
     * Demonstrates critical exception handling
     */
    public static void performCriticalOperation(String operation) {
        if (operation == null) {
            throw new CriticalException("Operation cannot be null", CriticalException.Severity.HIGH);
        }
        
        if (operation.equals("dangerous")) {
            throw new CriticalException("Dangerous operation detected", CriticalException.Severity.CRITICAL);
        }
        
        System.out.println("Critical operation '" + operation + "' completed successfully");
    }
    
    /**
     * Demonstrates multiple exception handling with hierarchy
     */
    public static void demonstrateExceptionHierarchy() {
        System.out.println("\n=== Exception Hierarchy Examples ===");
        
        // Example 1: User exception
        try {
            processUser("", "valid data");
        } catch (UserException e) {
            System.out.println("User Error: " + e.getMessage());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("User ID: " + e.getUserId());
        } catch (DataException e) {
            System.out.println("Data Error: " + e.getMessage());
            System.out.println("Data Type: " + e.getDataType());
        }
        
        // Example 2: Data exception
        try {
            processUser("user123", null);
        } catch (UserException e) {
            System.out.println("User Error: " + e.getMessage());
        } catch (DataException e) {
            System.out.println("Data Error: " + e.getMessage());
            System.out.println("Data Type: " + e.getDataType());
        }
        
        // Example 3: Exception chaining
        try {
            processUserData("user456", "abc");
        } catch (UserException e) {
            System.out.println("Chained Exception: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("Original Cause: " + e.getCause().getMessage());
            }
        }
        
        // Example 4: Critical exceptions
        try {
            performCriticalOperation("dangerous");
        } catch (CriticalException e) {
            System.out.println("Critical Error: " + e.getMessage());
            System.out.println("Severity: " + e.getSeverity());
            System.out.println("Timestamp: " + e.getTimestamp());
        }
    }
    
    /**
     * Demonstrates catching base exception types
     */
    public static void demonstrateBaseExceptionCatching() {
        System.out.println("\n=== Base Exception Catching ===");
        
        try {
            processUser("user789", "short");
        } catch (ApplicationException e) {
            // This catches both UserException and DataException
            System.out.println("Application Error: " + e.getMessage());
            System.out.println("Error Code: " + e.getErrorCode());
            
            // Check specific type
            if (e instanceof UserException) {
                UserException ue = (UserException) e;
                System.out.println("User ID: " + ue.getUserId());
            } else if (e instanceof DataException) {
                DataException de = (DataException) e;
                System.out.println("Data Type: " + de.getDataType());
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Example 2: Advanced Custom Exceptions ===");
        System.out.println("This example demonstrates:");
        System.out.println("- Exception hierarchies");
        System.out.println("- Exception chaining");
        System.out.println("- Custom exceptions with additional data");
        System.out.println("- Multiple exception handling");
        
        // Demonstrate exception hierarchy
        demonstrateExceptionHierarchy();
        
        // Demonstrate base exception catching
        demonstrateBaseExceptionCatching();
        
        System.out.println("\n=== Example 2 Complete ===");
        System.out.println("Key takeaways:");
        System.out.println("1. Exception hierarchies provide organized error handling");
        System.out.println("2. Exception chaining preserves original error context");
        System.out.println("3. Custom exceptions can carry additional data");
        System.out.println("4. Base exception types allow flexible error handling");
    }
} 