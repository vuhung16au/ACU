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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

// Exception factory pattern
class ExceptionFactory {
    public static AdvancedValidationException createValidationException(String field, String value, String reason) {
        return new AdvancedValidationException(String.format("Validation failed for %s='%s': %s", field, value, reason));
    }
    
    public static BusinessException createBusinessException(String operation, String errorCode, int severity) {
        return new BusinessException(operation, errorCode, severity);
    }
    
    public static SystemException createSystemException(String component, String operation, Throwable cause) {
        return new SystemException(component, operation, cause);
    }
}

// Advanced validation exception with field tracking
class AdvancedValidationException extends Exception {
    private String field;
    private String value;
    private String constraint;
    
    public AdvancedValidationException(String message) {
        super(message);
    }
    
    public AdvancedValidationException(String message, String field, String value, String constraint) {
        super(message);
        this.field = field;
        this.value = value;
        this.constraint = constraint;
    }
    
    public String getField() { return field; }
    public String getValue() { return value; }
    public String getConstraint() { return constraint; }
    
    @Override
    public String toString() {
        return String.format("AdvancedValidationException{field='%s', value='%s', constraint='%s', message='%s'}", 
                           field, value, constraint, getMessage());
    }
}

// Business exception with severity and recovery information
class BusinessException extends Exception {
    public enum Severity { INFO, WARNING, ERROR, CRITICAL }
    public enum RecoveryAction { RETRY, SKIP, ABORT, MANUAL_INTERVENTION }
    
    private String operation;
    private String errorCode;
    private Severity severity;
    private RecoveryAction recoveryAction;
    private Map<String, Object> context;
    
    public BusinessException(String operation, String errorCode, int severity) {
        this.operation = operation;
        this.errorCode = errorCode;
        this.severity = Severity.values()[Math.min(severity, Severity.values().length - 1)];
        this.context = new HashMap<>();
    }
    
    public BusinessException(String operation, String errorCode, Severity severity, RecoveryAction recoveryAction) {
        this.operation = operation;
        this.errorCode = errorCode;
        this.severity = severity;
        this.recoveryAction = recoveryAction;
        this.context = new HashMap<>();
    }
    
    public void addContext(String key, Object value) {
        context.put(key, value);
    }
    
    public String getOperation() { return operation; }
    public String getErrorCode() { return errorCode; }
    public Severity getSeverity() { return severity; }
    public RecoveryAction getRecoveryAction() { return recoveryAction; }
    public Map<String, Object> getContext() { return new HashMap<>(context); }
}

// System exception for infrastructure issues
class SystemException extends RuntimeException {
    private String component;
    private String operation;
    private long timestamp;
    private boolean retryable;
    
    public SystemException(String component, String operation, Throwable cause) {
        super(String.format("System error in %s during %s", component, operation), cause);
        this.component = component;
        this.operation = operation;
        this.timestamp = System.currentTimeMillis();
        this.retryable = true;
    }
    
    public SystemException(String component, String operation, String message, boolean retryable) {
        super(String.format("System error in %s during %s: %s", component, operation, message));
        this.component = component;
        this.operation = operation;
        this.timestamp = System.currentTimeMillis();
        this.retryable = retryable;
    }
    
    public String getComponent() { return component; }
    public String getOperation() { return operation; }
    public long getTimestamp() { return timestamp; }
    public boolean isRetryable() { return retryable; }
}

// Exception builder pattern
class ExceptionBuilder {
    private String message;
    private String errorCode;
    private BusinessException.Severity severity = BusinessException.Severity.ERROR;
    private BusinessException.RecoveryAction recoveryAction = BusinessException.RecoveryAction.ABORT;
    private Map<String, Object> context = new HashMap<>();
    
    public ExceptionBuilder message(String message) {
        this.message = message;
        return this;
    }
    
    public ExceptionBuilder errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }
    
    public ExceptionBuilder severity(BusinessException.Severity severity) {
        this.severity = severity;
        return this;
    }
    
    public ExceptionBuilder recoveryAction(BusinessException.RecoveryAction recoveryAction) {
        this.recoveryAction = recoveryAction;
        return this;
    }
    
    public ExceptionBuilder context(String key, Object value) {
        this.context.put(key, value);
        return this;
    }
    
    public BusinessException build() {
        BusinessException exception = new BusinessException("Unknown", errorCode, severity, recoveryAction);
        if (message != null) {
            // Note: This is a simplified approach. In practice, you might need to override the message differently
            exception.addContext("customMessage", message);
        }
        context.forEach(exception::addContext);
        return exception;
    }
}

public class Advanced {
    
    /**
     * Demonstrates exception factory usage
     */
    public static void demonstrateExceptionFactory() {
        System.out.println("\n=== Exception Factory Examples ===");
        
        try {
            // Using factory to create validation exception
            throw ExceptionFactory.createValidationException("email", "invalid-email", "Must contain @ symbol");
        } catch (AdvancedValidationException e) {
            System.out.println("Factory-created exception: " + e.getMessage());
        }
        
        try {
            // Using factory to create business exception
            BusinessException be = ExceptionFactory.createBusinessException("order_processing", "ORD_001", 2);
            be.addContext("orderId", "12345");
            be.addContext("amount", 150.00);
            throw be;
        } catch (BusinessException e) {
            System.out.println("Business exception: " + e.getErrorCode());
            System.out.println("Context: " + e.getContext());
        }
    }
    
    /**
     * Demonstrates exception builder pattern
     */
    public static void demonstrateExceptionBuilder() {
        System.out.println("\n=== Exception Builder Examples ===");
        
        try {
            BusinessException exception = new ExceptionBuilder()
                .message("Payment processing failed")
                .errorCode("PAY_001")
                .severity(BusinessException.Severity.CRITICAL)
                .recoveryAction(BusinessException.RecoveryAction.MANUAL_INTERVENTION)
                .context("paymentId", "pay_123456")
                .context("amount", 500.00)
                .context("currency", "USD")
                .build();
            
            throw exception;
        } catch (BusinessException e) {
            System.out.println("Built exception: " + e.getErrorCode());
            System.out.println("Severity: " + e.getSeverity());
            System.out.println("Recovery: " + e.getRecoveryAction());
            System.out.println("Context: " + e.getContext());
        }
    }
    
    /**
     * Demonstrates system exception handling
     */
    public static void demonstrateSystemExceptions() {
        System.out.println("\n=== System Exception Examples ===");
        
        try {
            // Simulate database connection failure
            throw ExceptionFactory.createSystemException("Database", "connection", 
                new RuntimeException("Connection timeout"));
        } catch (SystemException e) {
            System.out.println("System exception: " + e.getMessage());
            System.out.println("Component: " + e.getComponent());
            System.out.println("Retryable: " + e.isRetryable());
        }
        
        try {
            // Simulate non-retryable system error
            throw new SystemException("FileSystem", "write", "Disk full", false);
        } catch (SystemException e) {
            System.out.println("Non-retryable system error: " + e.getMessage());
            System.out.println("Retryable: " + e.isRetryable());
        }
    }
    
    /**
     * Demonstrates exception recovery strategies
     */
    public static void demonstrateExceptionRecovery() {
        System.out.println("\n=== Exception Recovery Examples ===");
        
        // Example 1: Retry strategy
        int maxRetries = 3;
        int attempt = 0;
        
        while (attempt < maxRetries) {
            try {
                // Simulate operation that might fail
                if (Math.random() < 0.7) {
                    throw new SystemException("Service", "api_call", "Temporary failure", true);
                }
                System.out.println("Operation succeeded on attempt " + (attempt + 1));
                break;
            } catch (SystemException e) {
                attempt++;
                System.out.println("Attempt " + attempt + " failed: " + e.getMessage());
                if (attempt >= maxRetries) {
                    System.out.println("Max retries reached, giving up");
                } else {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100); // Simulate delay
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
        
        // Example 2: Recovery action handling
        try {
            BusinessException exception = new ExceptionBuilder()
                .errorCode("PROC_001")
                .severity(BusinessException.Severity.WARNING)
                .recoveryAction(BusinessException.RecoveryAction.SKIP)
                .build();
            throw exception;
        } catch (BusinessException e) {
            System.out.println("Handling business exception with recovery action: " + e.getRecoveryAction());
            switch (e.getRecoveryAction()) {
                case RETRY:
                    System.out.println("Retrying operation...");
                    break;
                case SKIP:
                    System.out.println("Skipping this item and continuing...");
                    break;
                case ABORT:
                    System.out.println("Aborting entire operation...");
                    break;
                case MANUAL_INTERVENTION:
                    System.out.println("Requires manual intervention...");
                    break;
            }
        }
    }
    
    /**
     * Demonstrates complex exception handling with multiple types
     */
    public static void demonstrateComplexExceptionHandling() {
        System.out.println("\n=== Complex Exception Handling ===");
        
        try {
            // Simulate complex operation that might throw different types of exceptions
            double random = Math.random();
            
            if (random < 0.3) {
                throw ExceptionFactory.createValidationException("user_input", "invalid", "Format error");
            } else if (random < 0.6) {
                throw ExceptionFactory.createBusinessException("processing", "BUS_001", 1);
            } else {
                throw ExceptionFactory.createSystemException("network", "request", 
                    new RuntimeException("Connection lost"));
            }
            
        } catch (AdvancedValidationException e) {
            System.out.println("Handling validation error: " + e.getMessage());
            System.out.println("Field: " + e.getField() + ", Value: " + e.getValue());
            
        } catch (BusinessException e) {
            System.out.println("Handling business error: " + e.getErrorCode());
            System.out.println("Severity: " + e.getSeverity());
            
        } catch (SystemException e) {
            System.out.println("Handling system error: " + e.getMessage());
            System.out.println("Component: " + e.getComponent() + ", Retryable: " + e.isRetryable());
            
        } catch (Exception e) {
            System.out.println("Handling unexpected error: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Advanced Custom Exception Examples ===");
        System.out.println("This example demonstrates:");
        System.out.println("- Exception factories for consistent error creation");
        System.out.println("- Exception builders for complex error construction");
        System.out.println("- System exceptions for infrastructure issues");
        System.out.println("- Exception recovery strategies");
        System.out.println("- Complex exception handling patterns");
        
        // Demonstrate various advanced patterns
        demonstrateExceptionFactory();
        demonstrateExceptionBuilder();
        demonstrateSystemExceptions();
        demonstrateExceptionRecovery();
        demonstrateComplexExceptionHandling();
        
        System.out.println("\n=== Advanced Example Complete ===");
        System.out.println("Key advanced concepts:");
        System.out.println("1. Exception factories ensure consistent error creation");
        System.out.println("2. Exception builders provide fluent API for complex errors");
        System.out.println("3. System exceptions handle infrastructure issues");
        System.out.println("4. Recovery strategies provide graceful error handling");
        System.out.println("5. Complex exception handling manages multiple error types");
    }
} 