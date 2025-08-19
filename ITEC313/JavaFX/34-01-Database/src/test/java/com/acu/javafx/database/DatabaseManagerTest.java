package com.acu.javafx.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

/**
 * Unit tests for DatabaseManager class
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseManagerTest {
    
    private static final String TEST_DB_URL = "jdbc:sqlite:test_employee.sqlite";
    private static final String ORIGINAL_DB_URL = "jdbc:sqlite:employee.sqlite";
    
    @BeforeEach
    void setUp() {
        // Clean up any existing test database
        cleanupTestDatabase();
        
        // Initialize test database
        DatabaseManager.initializeDatabase();
    }
    
    @AfterEach
    void tearDown() {
        // Clean up test database after each test
        cleanupTestDatabase();
    }
    
    private void cleanupTestDatabase() {
        File dbFile = new File("employee.sqlite");
        if (dbFile.exists()) {
            dbFile.delete();
        }
    }
    
    @Test
    @DisplayName("Test database initialization creates table")
    void testInitializeDatabase() {
        // Database should be initialized in setUp()
        List<Employee> employees = DatabaseManager.getAllEmployees();
        assertNotNull(employees);
        assertEquals(0, employees.size());
    }
    
    @Test
    @DisplayName("Test insert employee with valid data")
    void testInsertEmployee_Success() {
        Employee employee = new Employee(0, "John Doe", "john@example.com", "Engineering", 75000.0);
        
        boolean result = DatabaseManager.insertEmployee(employee);
        
        assertTrue(result);
        
        List<Employee> employees = DatabaseManager.getAllEmployees();
        assertEquals(1, employees.size());
        assertEquals("John Doe", employees.get(0).getName());
        assertEquals("john@example.com", employees.get(0).getEmail());
    }
    
    @Test
    @DisplayName("Test insert employee with null values")
    void testInsertEmployee_WithNullValues() {
        Employee employee = new Employee(0, null, null, null, 0.0);
        
        boolean result = DatabaseManager.insertEmployee(employee);
        
        // Should fail because name has NOT NULL constraint
        assertFalse(result);
        
        List<Employee> employees = DatabaseManager.getAllEmployees();
        assertEquals(0, employees.size());
    }
    
    @Test
    @DisplayName("Test insert employee with empty strings")
    void testInsertEmployee_WithEmptyStrings() {
        Employee employee = new Employee(0, "", "", "", 0.0);
        
        boolean result = DatabaseManager.insertEmployee(employee);
        
        assertTrue(result);
        
        List<Employee> employees = DatabaseManager.getAllEmployees();
        assertEquals(1, employees.size());
        assertEquals("", employees.get(0).getName());
        assertEquals("", employees.get(0).getEmail());
        assertEquals("", employees.get(0).getDepartment());
    }
    
    @Test
    @DisplayName("Test insert employee with negative salary")
    void testInsertEmployee_WithNegativeSalary() {
        Employee employee = new Employee(0, "Test User", "test@example.com", "Testing", -50000.0);
        
        boolean result = DatabaseManager.insertEmployee(employee);
        
        assertTrue(result);
        
        List<Employee> employees = DatabaseManager.getAllEmployees();
        assertEquals(1, employees.size());
        assertEquals(-50000.0, employees.get(0).getSalary());
    }
    
    @Test
    @DisplayName("Test insert employee with very large salary")
    void testInsertEmployee_WithVeryLargeSalary() {
        Employee employee = new Employee(0, "Test User", "test@example.com", "Testing", Double.MAX_VALUE);
        
        boolean result = DatabaseManager.insertEmployee(employee);
        
        assertTrue(result);
        
        List<Employee> employees = DatabaseManager.getAllEmployees();
        assertEquals(1, employees.size());
        assertEquals(Double.MAX_VALUE, employees.get(0).getSalary());
    }
    
    @Test
    @DisplayName("Test insert multiple employees")
    void testInsertMultipleEmployees() {
        Employee emp1 = new Employee(0, "John Doe", "john@example.com", "Engineering", 75000.0);
        Employee emp2 = new Employee(0, "Jane Smith", "jane@example.com", "Marketing", 65000.0);
        Employee emp3 = new Employee(0, "Bob Johnson", "bob@example.com", "Sales", 70000.0);
        
        assertTrue(DatabaseManager.insertEmployee(emp1));
        assertTrue(DatabaseManager.insertEmployee(emp2));
        assertTrue(DatabaseManager.insertEmployee(emp3));
        
        List<Employee> employees = DatabaseManager.getAllEmployees();
        assertEquals(3, employees.size());
    }
    
    @Test
    @DisplayName("Test get all employees from empty database")
    void testGetAllEmployees_EmptyDatabase() {
        List<Employee> employees = DatabaseManager.getAllEmployees();
        
        assertNotNull(employees);
        assertEquals(0, employees.size());
    }
    
    @Test
    @DisplayName("Test get all employees with data")
    void testGetAllEmployees_WithData() {
        // Insert test data
        Employee emp1 = new Employee(0, "John Doe", "john@example.com", "Engineering", 75000.0);
        Employee emp2 = new Employee(0, "Jane Smith", "jane@example.com", "Marketing", 65000.0);
        
        DatabaseManager.insertEmployee(emp1);
        DatabaseManager.insertEmployee(emp2);
        
        List<Employee> employees = DatabaseManager.getAllEmployees();
        
        assertEquals(2, employees.size());
        assertTrue(employees.stream().anyMatch(e -> "John Doe".equals(e.getName())));
        assertTrue(employees.stream().anyMatch(e -> "Jane Smith".equals(e.getName())));
    }
    
    @Test
    @DisplayName("Test update employee with valid data")
    void testUpdateEmployee_Success() {
        // Insert an employee first
        Employee original = new Employee(0, "John Doe", "john@example.com", "Engineering", 75000.0);
        DatabaseManager.insertEmployee(original);
        
        List<Employee> employees = DatabaseManager.getAllEmployees();
        Employee toUpdate = employees.get(0);
        
        // Update the employee
        toUpdate.setName("John Updated");
        toUpdate.setSalary(80000.0);
        
        boolean result = DatabaseManager.updateEmployee(toUpdate);
        
        assertTrue(result);
        
        // Verify the update
        List<Employee> updatedEmployees = DatabaseManager.getAllEmployees();
        assertEquals(1, updatedEmployees.size());
        assertEquals("John Updated", updatedEmployees.get(0).getName());
        assertEquals(80000.0, updatedEmployees.get(0).getSalary());
    }
    
    @Test
    @DisplayName("Test update employee with non-existent ID")
    void testUpdateEmployee_NonExistentId() {
        Employee nonExistent = new Employee(999, "Non Existent", "nonexistent@example.com", "Testing", 50000.0);
        
        boolean result = DatabaseManager.updateEmployee(nonExistent);
        
        assertFalse(result);
    }
    
    @Test
    @DisplayName("Test delete employee with valid ID")
    void testDeleteEmployee_Success() {
        // Insert an employee first
        Employee employee = new Employee(0, "John Doe", "john@example.com", "Engineering", 75000.0);
        DatabaseManager.insertEmployee(employee);
        
        List<Employee> employees = DatabaseManager.getAllEmployees();
        assertEquals(1, employees.size());
        
        int employeeId = employees.get(0).getId();
        boolean result = DatabaseManager.deleteEmployee(employeeId);
        
        assertTrue(result);
        
        // Verify deletion
        List<Employee> remainingEmployees = DatabaseManager.getAllEmployees();
        assertEquals(0, remainingEmployees.size());
    }
    
    @Test
    @DisplayName("Test delete employee with non-existent ID")
    void testDeleteEmployee_NonExistentId() {
        boolean result = DatabaseManager.deleteEmployee(999);
        
        assertFalse(result);
    }
    
    @Test
    @DisplayName("Test search employees by name - exact match")
    void testSearchEmployeesByName_ExactMatch() {
        // Insert test data
        Employee emp1 = new Employee(0, "John Doe", "john@example.com", "Engineering", 75000.0);
        Employee emp2 = new Employee(0, "Jane Smith", "jane@example.com", "Marketing", 65000.0);
        Employee emp3 = new Employee(0, "Bob Johnson", "bob@example.com", "Sales", 70000.0);
        
        DatabaseManager.insertEmployee(emp1);
        DatabaseManager.insertEmployee(emp2);
        DatabaseManager.insertEmployee(emp3);
        
        List<Employee> results = DatabaseManager.searchEmployeesByName("John Doe");
        
        assertEquals(1, results.size());
        assertEquals("John Doe", results.get(0).getName());
    }
    
    @Test
    @DisplayName("Test search employees by name - partial match")
    void testSearchEmployeesByName_PartialMatch() {
        // Insert test data
        Employee emp1 = new Employee(0, "John Doe", "john@example.com", "Engineering", 75000.0);
        Employee emp2 = new Employee(0, "Jane Smith", "jane@example.com", "Marketing", 65000.0);
        Employee emp3 = new Employee(0, "Bob Johnson", "bob@example.com", "Sales", 70000.0);
        
        DatabaseManager.insertEmployee(emp1);
        DatabaseManager.insertEmployee(emp2);
        DatabaseManager.insertEmployee(emp3);
        
        List<Employee> results = DatabaseManager.searchEmployeesByName("John");
        
        assertEquals(2, results.size()); // Should find both "John Doe" and "Bob Johnson"
        assertTrue(results.stream().anyMatch(e -> "John Doe".equals(e.getName())));
        assertTrue(results.stream().anyMatch(e -> "Bob Johnson".equals(e.getName())));
    }
    
    @Test
    @DisplayName("Test search employees by name - no match")
    void testSearchEmployeesByName_NoMatch() {
        // Insert test data
        Employee emp1 = new Employee(0, "John Doe", "john@example.com", "Engineering", 75000.0);
        DatabaseManager.insertEmployee(emp1);
        
        List<Employee> results = DatabaseManager.searchEmployeesByName("NonExistent");
        
        assertEquals(0, results.size());
    }
    
    @Test
    @DisplayName("Test search employees by name - empty search term")
    void testSearchEmployeesByName_EmptySearchTerm() {
        // Insert test data
        Employee emp1 = new Employee(0, "John Doe", "john@example.com", "Engineering", 75000.0);
        Employee emp2 = new Employee(0, "Jane Smith", "jane@example.com", "Marketing", 65000.0);
        
        DatabaseManager.insertEmployee(emp1);
        DatabaseManager.insertEmployee(emp2);
        
        List<Employee> results = DatabaseManager.searchEmployeesByName("");
        
        assertEquals(2, results.size());
    }
    
    @Test
    @DisplayName("Test search employees by name - case insensitive")
    void testSearchEmployeesByName_CaseInsensitive() {
        // Insert test data
        Employee emp1 = new Employee(0, "John Doe", "john@example.com", "Engineering", 75000.0);
        DatabaseManager.insertEmployee(emp1);
        
        List<Employee> results = DatabaseManager.searchEmployeesByName("john");
        
        assertEquals(1, results.size());
        assertEquals("John Doe", results.get(0).getName());
    }
    
    @Test
    @DisplayName("Test search employees by name with special characters")
    void testSearchEmployeesByName_SpecialCharacters() {
        // Insert test data with special characters
        Employee emp1 = new Employee(0, "José María", "jose@example.com", "Engineering", 75000.0);
        Employee emp2 = new Employee(0, "O'Connor", "oconnor@example.com", "Marketing", 65000.0);
        
        DatabaseManager.insertEmployee(emp1);
        DatabaseManager.insertEmployee(emp2);
        
        List<Employee> results1 = DatabaseManager.searchEmployeesByName("José");
        List<Employee> results2 = DatabaseManager.searchEmployeesByName("O'Connor");
        
        assertEquals(1, results1.size());
        assertEquals(1, results2.size());
    }
    
    @Test
    @DisplayName("Test populate sample data on empty database")
    void testPopulateSampleData_EmptyDatabase() {
        DatabaseManager.populateSampleData();
        
        List<Employee> employees = DatabaseManager.getAllEmployees();
        assertEquals(3, employees.size());
        
        // Check that sample data contains expected names
        List<String> names = employees.stream().map(Employee::getName).toList();
        assertTrue(names.contains("Vu Nguyen"));
        assertTrue(names.contains("John Doe"));
        assertTrue(names.contains("English Sydney"));
    }
    
    @Test
    @DisplayName("Test populate sample data on existing database")
    void testPopulateSampleData_ExistingData() {
        // Insert some existing data
        Employee existing = new Employee(0, "Existing User", "existing@example.com", "Testing", 50000.0);
        DatabaseManager.insertEmployee(existing);
        
        DatabaseManager.populateSampleData();
        
        List<Employee> employees = DatabaseManager.getAllEmployees();
        assertEquals(1, employees.size()); // Should not add sample data if data exists
        assertEquals("Existing User", employees.get(0).getName());
    }
    
    @Test
    @DisplayName("Test complete employee lifecycle")
    void testCompleteEmployeeLifecycle() {
        // Create
        Employee employee = new Employee(0, "Lifecycle Test", "lifecycle@example.com", "Testing", 60000.0);
        assertTrue(DatabaseManager.insertEmployee(employee));
        
        // Read
        List<Employee> employees = DatabaseManager.getAllEmployees();
        assertEquals(1, employees.size());
        Employee retrieved = employees.get(0);
        assertEquals("Lifecycle Test", retrieved.getName());
        
        // Update
        retrieved.setSalary(65000.0);
        assertTrue(DatabaseManager.updateEmployee(retrieved));
        
        // Verify update
        employees = DatabaseManager.getAllEmployees();
        assertEquals(65000.0, employees.get(0).getSalary());
        
        // Delete
        assertTrue(DatabaseManager.deleteEmployee(retrieved.getId()));
        
        // Verify deletion
        employees = DatabaseManager.getAllEmployees();
        assertEquals(0, employees.size());
    }
    
    @Test
    @DisplayName("Test search with SQL injection attempt")
    void testSearchWithSQLInjectionAttempt() {
        // Insert test data
        Employee emp1 = new Employee(0, "John Doe", "john@example.com", "Engineering", 75000.0);
        DatabaseManager.insertEmployee(emp1);
        
        // Try SQL injection
        String sqlInjection = "'; DROP TABLE employees; --";
        List<Employee> results = DatabaseManager.searchEmployeesByName(sqlInjection);
        
        // Should not crash and should return no results
        assertEquals(0, results.size());
        
        // Verify table still exists
        List<Employee> allEmployees = DatabaseManager.getAllEmployees();
        assertEquals(1, allEmployees.size());
    }
}
