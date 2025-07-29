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
// Generic interface for data processing
interface DataProcessor<T> {
    T process(T data);
    boolean isValid(T data);
    String getProcessorType();
}

// Generic interface for data storage
interface DataStorage<T> {
    void store(T data);
    T retrieve(String id);
    boolean contains(String id);
}

// Abstract class for data operations
abstract class DataOperation<T> {
    protected String operationId;
    protected T defaultValue;
    
    public DataOperation(String operationId, T defaultValue) {
        this.operationId = operationId;
        this.defaultValue = defaultValue;
    }
    
    public abstract T execute(T input);
    public abstract boolean canExecute(T input);
    
    public String getOperationId() {
        return operationId;
    }
    
    public T getDefaultValue() {
        return defaultValue;
    }
}

// Concrete class for number processing
class NumberProcessor implements DataProcessor<Integer> {
    private int multiplier;
    
    public NumberProcessor(int multiplier) {
        this.multiplier = multiplier;
    }
    
    @Override
    public Integer process(Integer data) {
        return data * multiplier;
    }
    
    @Override
    public boolean isValid(Integer data) {
        return data != null && data > 0;
    }
    
    @Override
    public String getProcessorType() {
        return "Number Processor (multiplier: " + multiplier + ")";
    }
    
    public int getMultiplier() {
        return multiplier;
    }
}

// Concrete class for string processing
class StringProcessor implements DataProcessor<String> {
    private String prefix;
    private String suffix;
    
    public StringProcessor(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
    
    @Override
    public String process(String data) {
        return prefix + data + suffix;
    }
    
    @Override
    public boolean isValid(String data) {
        return data != null && !data.trim().isEmpty();
    }
    
    @Override
    public String getProcessorType() {
        return "String Processor (prefix: " + prefix + ", suffix: " + suffix + ")";
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public String getSuffix() {
        return suffix;
    }
}

// Concrete class for number storage
class NumberStorage implements DataStorage<Integer> {
    private java.util.Map<String, Integer> storage = new java.util.HashMap<>();
    
    @Override
    public void store(Integer data) {
        String id = "num_" + System.currentTimeMillis();
        storage.put(id, data);
        System.out.println("Stored number with ID: " + id);
    }
    
    @Override
    public Integer retrieve(String id) {
        return storage.getOrDefault(id, 0);
    }
    
    @Override
    public boolean contains(String id) {
        return storage.containsKey(id);
    }
}

// Concrete class for string storage
class StringStorage implements DataStorage<String> {
    private java.util.Map<String, String> storage = new java.util.HashMap<>();
    
    @Override
    public void store(String data) {
        String id = "str_" + System.currentTimeMillis();
        storage.put(id, data);
        System.out.println("Stored string with ID: " + id);
    }
    
    @Override
    public String retrieve(String id) {
        return storage.getOrDefault(id, "");
    }
    
    @Override
    public boolean contains(String id) {
        return storage.containsKey(id);
    }
}

// Concrete class for number operations
class NumberOperation extends DataOperation<Integer> {
    private String operationType;
    
    public NumberOperation(String operationId, String operationType, Integer defaultValue) {
        super(operationId, defaultValue);
        this.operationType = operationType;
    }
    
    @Override
    public Integer execute(Integer input) {
        switch (operationType) {
            case "double":
                return input * 2;
            case "square":
                return input * input;
            case "increment":
                return input + 1;
            default:
                return defaultValue;
        }
    }
    
    @Override
    public boolean canExecute(Integer input) {
        return input != null && input > 0;
    }
    
    public String getOperationType() {
        return operationType;
    }
}

// Concrete class for string operations
class StringOperation extends DataOperation<String> {
    private String operationType;
    
    public StringOperation(String operationId, String operationType, String defaultValue) {
        super(operationId, defaultValue);
        this.operationType = operationType;
    }
    
    @Override
    public String execute(String input) {
        switch (operationType) {
            case "uppercase":
                return input.toUpperCase();
            case "lowercase":
                return input.toLowerCase();
            case "reverse":
                return new StringBuilder(input).reverse().toString();
            default:
                return defaultValue;
        }
    }
    
    @Override
    public boolean canExecute(String input) {
        return input != null && !input.trim().isEmpty();
    }
    
    public String getOperationType() {
        return operationType;
    }
}

// Generic factory class demonstrating factory pattern with polymorphism
class DataFactory {
    
    // Generic method to create processors
    public static <T> DataProcessor<T> createProcessor(String type) {
        switch (type) {
            case "number":
                return (DataProcessor<T>) new NumberProcessor(2);
            case "string":
                return (DataProcessor<T>) new StringProcessor("[", "]");
            default:
                throw new IllegalArgumentException("Unknown processor type: " + type);
        }
    }
    
    // Generic method to create storage
    public static <T> DataStorage<T> createStorage(String type) {
        switch (type) {
            case "number":
                return (DataStorage<T>) new NumberStorage();
            case "string":
                return (DataStorage<T>) new StringStorage();
            default:
                throw new IllegalArgumentException("Unknown storage type: " + type);
        }
    }
    
    // Generic method to create operations
    public static <T> DataOperation<T> createOperation(String operationId, String operationType, T defaultValue) {
        if (defaultValue instanceof Integer) {
            return (DataOperation<T>) new NumberOperation(operationId, operationType, (Integer) defaultValue);
        } else if (defaultValue instanceof String) {
            return (DataOperation<T>) new StringOperation(operationId, operationType, (String) defaultValue);
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }
}

// Generic utility class for processing collections
class DataProcessorUtil {
    
    // Generic method with wildcard
    public static void processAll(DataProcessor<?> processor, Object... data) {
        System.out.println("Processing with: " + processor.getProcessorType());
        for (Object item : data) {
            if (processor.isValid(item)) {
                Object result = processor.process(item);
                System.out.println("Input: " + item + " -> Output: " + result);
            } else {
                System.out.println("Invalid input: " + item);
            }
        }
        System.out.println();
    }
    
    // Generic method with bounded wildcard
    public static <T extends Number> void processNumbers(DataProcessor<T> processor, T... numbers) {
        System.out.println("Processing numbers with: " + processor.getProcessorType());
        for (T number : numbers) {
            if (processor.isValid(number)) {
                T result = processor.process(number);
                System.out.println("Input: " + number + " -> Output: " + result);
            } else {
                System.out.println("Invalid number: " + number);
            }
        }
        System.out.println();
    }
    
    // Generic method for operations
    public static <T> void executeOperation(DataOperation<T> operation, T input) {
        System.out.println("Executing operation: " + operation.getOperationId());
        if (operation.canExecute(input)) {
            T result = operation.execute(input);
            System.out.println("Input: " + input + " -> Output: " + result);
        } else {
            System.out.println("Cannot execute operation on input: " + input);
        }
        System.out.println();
    }
}

// Main class demonstrating advanced polymorphism concepts
public class Advanced {
    
    // Method demonstrating generic polymorphism
    public static void demonstrateGenericPolymorphism() {
        System.out.println("=== Demonstrating Generic Polymorphism ===");
        
        // Create processors using factory
        DataProcessor<Integer> numberProcessor = DataFactory.createProcessor("number");
        DataProcessor<String> stringProcessor = DataFactory.createProcessor("string");
        
        // Process different types of data
        System.out.println("Number processing:");
        DataProcessorUtil.processNumbers(numberProcessor, 5, 10, 15);
        
        System.out.println("String processing:");
        DataProcessorUtil.processAll(stringProcessor, "hello", "world", "java");
        
        System.out.println();
    }
    
    // Method demonstrating storage polymorphism
    public static void demonstrateStoragePolymorphism() {
        System.out.println("=== Demonstrating Storage Polymorphism ===");
        
        // Create storage using factory
        DataStorage<Integer> numberStorage = DataFactory.createStorage("number");
        DataStorage<String> stringStorage = DataFactory.createStorage("string");
        
        // Store different types of data
        numberStorage.store(100);
        numberStorage.store(200);
        stringStorage.store("Hello");
        stringStorage.store("World");
        
        System.out.println();
    }
    
    // Method demonstrating operation polymorphism
    public static void demonstrateOperationPolymorphism() {
        System.out.println("=== Demonstrating Operation Polymorphism ===");
        
        // Create operations using factory
        DataOperation<Integer> doubleOp = DataFactory.createOperation("double", "double", 0);
        DataOperation<Integer> squareOp = DataFactory.createOperation("square", "square", 0);
        DataOperation<String> upperOp = DataFactory.createOperation("upper", "uppercase", "");
        DataOperation<String> reverseOp = DataFactory.createOperation("reverse", "reverse", "");
        
        // Execute operations
        DataProcessorUtil.executeOperation(doubleOp, 5);
        DataProcessorUtil.executeOperation(squareOp, 4);
        DataProcessorUtil.executeOperation(upperOp, "hello");
        DataProcessorUtil.executeOperation(reverseOp, "java");
        
        System.out.println();
    }
    
    // Method demonstrating wildcard polymorphism
    public static void demonstrateWildcardPolymorphism() {
        System.out.println("=== Demonstrating Wildcard Polymorphism ===");
        
        // Create different processors
        DataProcessor<Integer> numberProcessor = new NumberProcessor(3);
        DataProcessor<String> stringProcessor = new StringProcessor("<<", ">>");
        
        // Use wildcard method
        System.out.println("Processing with wildcard method:");
        DataProcessorUtil.processAll(numberProcessor, 1, 2, 3, 4, 5);
        DataProcessorUtil.processAll(stringProcessor, "a", "b", "c");
        
        System.out.println();
    }
    
    // Method demonstrating complex inheritance
    public static void demonstrateComplexInheritance() {
        System.out.println("=== Demonstrating Complex Inheritance ===");
        
        // Create a collection of different operation types
        DataOperation<?>[] operations = {
            new NumberOperation("op1", "double", 0),
            new NumberOperation("op2", "square", 0),
            new StringOperation("op3", "uppercase", ""),
            new StringOperation("op4", "reverse", "")
        };
        
        // Process operations polymorphically
        for (DataOperation<?> operation : operations) {
            System.out.println("Operation: " + operation.getOperationId());
            System.out.println("Default value: " + operation.getDefaultValue());
            System.out.println("Type: " + operation.getClass().getSimpleName());
            System.out.println();
        }
        
        System.out.println();
    }
    
    public static void main(String[] args) {
        System.out.println("🚀 Advanced Polymorphism Concepts");
        System.out.println("==================================");
        System.out.println();
        
        // Demonstrate different advanced polymorphism concepts
        demonstrateGenericPolymorphism();
        demonstrateStoragePolymorphism();
        demonstrateOperationPolymorphism();
        demonstrateWildcardPolymorphism();
        demonstrateComplexInheritance();
        
        System.out.println("✅ Advanced polymorphism example completed!");
        System.out.println();
        System.out.println("Key Advanced Concepts:");
        System.out.println("- Generic polymorphism allows type-safe operations");
        System.out.println("- Wildcards provide flexibility in generic types");
        System.out.println("- Factory pattern enables polymorphic object creation");
        System.out.println("- Complex inheritance hierarchies demonstrate advanced OOP");
        System.out.println("- Strategy pattern can be implemented using polymorphism");
    }
} 