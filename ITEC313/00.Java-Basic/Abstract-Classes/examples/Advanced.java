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
import java.util.ArrayList;
import java.util.List;

// Abstract base class for database operations
abstract class DatabaseOperation {
    protected String tableName;
    protected List<String> columns;
    
    public DatabaseOperation(String tableName) {
        this.tableName = tableName;
        this.columns = new ArrayList<>();
    }
    
    // Template method pattern - defines algorithm structure
    public final void executeOperation() {
        validateConnection();
        prepareStatement();
        executeQuery();
        processResults();
        cleanup();
    }
    
    // Abstract methods that must be implemented
    protected abstract void prepareStatement();
    protected abstract void executeQuery();
    protected abstract void processResults();
    
    // Concrete methods with default implementations
    protected void validateConnection() {
        System.out.println("Validating database connection...");
        System.out.println("Connection established successfully");
    }
    
    protected void cleanup() {
        System.out.println("Cleaning up resources...");
        System.out.println("Operation completed successfully");
    }
    
    public void addColumn(String columnName) {
        columns.add(columnName);
    }
    
    public String getTableName() {
        return tableName;
    }
}

// Concrete implementation for SELECT operations
class SelectOperation extends DatabaseOperation {
    private String whereClause;
    private List<String> results;
    
    public SelectOperation(String tableName, String whereClause) {
        super(tableName);
        this.whereClause = whereClause;
        this.results = new ArrayList<>();
    }
    
    @Override
    protected void prepareStatement() {
        System.out.println("Preparing SELECT statement for table: " + tableName);
        System.out.println("WHERE clause: " + whereClause);
    }
    
    @Override
    protected void executeQuery() {
        System.out.println("Executing SELECT query...");
        // Simulate query execution
        results.add("Row 1: Data 1");
        results.add("Row 2: Data 2");
        results.add("Row 3: Data 3");
    }
    
    @Override
    protected void processResults() {
        System.out.println("Processing SELECT results:");
        for (String result : results) {
            System.out.println("  " + result);
        }
    }
}

// Concrete implementation for INSERT operations
class InsertOperation extends DatabaseOperation {
    private List<String> values;
    
    public InsertOperation(String tableName) {
        super(tableName);
        this.values = new ArrayList<>();
    }
    
    public void addValue(String value) {
        values.add(value);
    }
    
    @Override
    protected void prepareStatement() {
        System.out.println("Preparing INSERT statement for table: " + tableName);
        System.out.println("Values to insert: " + values);
    }
    
    @Override
    protected void executeQuery() {
        System.out.println("Executing INSERT query...");
        System.out.println("Inserted " + values.size() + " rows");
    }
    
    @Override
    protected void processResults() {
        System.out.println("Processing INSERT results:");
        System.out.println("  Rows affected: " + values.size());
    }
}

// Abstract class for data processors
abstract class DataProcessor {
    protected String dataSource;
    protected boolean isProcessed;
    
    public DataProcessor(String dataSource) {
        this.dataSource = dataSource;
        this.isProcessed = false;
    }
    
    // Template method for data processing
    public final void processData() {
        validateData();
        transformData();
        saveData();
        generateReport();
    }
    
    // Abstract methods
    protected abstract void transformData();
    protected abstract void saveData();
    protected abstract void generateReport();
    
    // Concrete method
    protected void validateData() {
        System.out.println("Validating data from: " + dataSource);
        System.out.println("Data validation completed");
    }
    
    public boolean isProcessed() {
        return isProcessed;
    }
}

// Concrete data processor for CSV files
class CSVProcessor extends DataProcessor {
    private List<String> csvData;
    
    public CSVProcessor(String dataSource) {
        super(dataSource);
        this.csvData = new ArrayList<>();
    }
    
    @Override
    protected void transformData() {
        System.out.println("Transforming CSV data...");
        // Simulate CSV processing
        csvData.add("Header: Name,Age,City");
        csvData.add("Data: John,25,New York");
        csvData.add("Data: Jane,30,Los Angeles");
    }
    
    @Override
    protected void saveData() {
        System.out.println("Saving processed CSV data...");
        System.out.println("Data saved to database");
    }
    
    @Override
    protected void generateReport() {
        System.out.println("Generating CSV processing report:");
        System.out.println("  Rows processed: " + csvData.size());
        System.out.println("  Source: " + dataSource);
    }
}

// Concrete data processor for JSON files
class JSONProcessor extends DataProcessor {
    private String jsonStructure;
    
    public JSONProcessor(String dataSource) {
        super(dataSource);
        this.jsonStructure = "";
    }
    
    @Override
    protected void transformData() {
        System.out.println("Transforming JSON data...");
        jsonStructure = "{\"name\":\"John\",\"age\":25,\"city\":\"New York\"}";
    }
    
    @Override
    protected void saveData() {
        System.out.println("Saving processed JSON data...");
        System.out.println("JSON structure: " + jsonStructure);
    }
    
    @Override
    protected void generateReport() {
        System.out.println("Generating JSON processing report:");
        System.out.println("  JSON structure processed");
        System.out.println("  Source: " + dataSource);
    }
}

// Abstract factory for creating database operations
abstract class DatabaseOperationFactory {
    public abstract DatabaseOperation createOperation(String tableName, String operationType);
    
    public void executeOperation(String tableName, String operationType) {
        DatabaseOperation operation = createOperation(tableName, operationType);
        operation.executeOperation();
    }
}

// Concrete factory implementation
class ConcreteDatabaseFactory extends DatabaseOperationFactory {
    @Override
    public DatabaseOperation createOperation(String tableName, String operationType) {
        switch (operationType.toLowerCase()) {
            case "select":
                return new SelectOperation(tableName, "id > 0");
            case "insert":
                InsertOperation insertOp = new InsertOperation(tableName);
                insertOp.addValue("Value1");
                insertOp.addValue("Value2");
                return insertOp;
            default:
                throw new IllegalArgumentException("Unknown operation type: " + operationType);
        }
    }
}

// Advanced example usage
public class Advanced {
    public static void main(String[] args) {
        System.out.println("=== Advanced Abstract Classes Example ===\n");
        
        // Demonstrate template method pattern with database operations
        System.out.println("--- Database Operations (Template Method Pattern) ---");
        ConcreteDatabaseFactory factory = new ConcreteDatabaseFactory();
        
        System.out.println("Executing SELECT operation:");
        factory.executeOperation("users", "select");
        System.out.println();
        
        System.out.println("Executing INSERT operation:");
        factory.executeOperation("users", "insert");
        System.out.println();
        
        // Demonstrate data processors
        System.out.println("--- Data Processors (Template Method Pattern) ---");
        
        CSVProcessor csvProcessor = new CSVProcessor("data.csv");
        System.out.println("Processing CSV data:");
        csvProcessor.processData();
        System.out.println();
        
        JSONProcessor jsonProcessor = new JSONProcessor("data.json");
        System.out.println("Processing JSON data:");
        jsonProcessor.processData();
        System.out.println();
        
        // Demonstrate polymorphism with processors
        List<DataProcessor> processors = new ArrayList<>();
        processors.add(csvProcessor);
        processors.add(jsonProcessor);
        
        System.out.println("--- Batch Processing ---");
        for (DataProcessor processor : processors) {
            System.out.println("Processing: " + processor.dataSource);
            processor.processData();
            System.out.println();
        }
    }
} 