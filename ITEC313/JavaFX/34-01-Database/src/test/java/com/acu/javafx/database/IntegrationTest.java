package com.acu.javafx.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Integration tests for the complete database system
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntegrationTest {
    
    @BeforeEach
    void setUp() {
        cleanupTestDatabase();
        DatabaseManager.initializeDatabase();
    }
    
    @AfterEach
    void tearDown() {
        cleanupTestDatabase();
    }
    
    private void cleanupTestDatabase() {
        File dbFile = new File("employee.sqlite");
        if (dbFile.exists()) {
            dbFile.delete();
        }
    }
    
    @Test
    @DisplayName("Test complete application workflow")
    void testCompleteApplicationWorkflow() {
        // Step 1: Initialize database
        DatabaseManager.initializeDatabase();
        List<Employee> employees = DatabaseManager.getAllEmployees();
        assertEquals(0, employees.size());
        
        // Step 2: Populate sample data
        DatabaseManager.populateSampleData();
        employees = DatabaseManager.getAllEmployees();
        assertEquals(3, employees.size());
        
        // Step 3: Add new employee
        Employee newEmployee = new Employee(0, "Integration Test", "integration@test.com", "Testing", 55000.0);
        assertTrue(DatabaseManager.insertEmployee(newEmployee));
        employees = DatabaseManager.getAllEmployees();
        assertEquals(4, employees.size());
        
        // Step 4: Search for employee
        List<Employee> searchResults = DatabaseManager.searchEmployeesByName("Integration");
        assertEquals(1, searchResults.size());
        assertEquals("Integration Test", searchResults.get(0).getName());
        
        // Step 5: Update employee
        Employee toUpdate = searchResults.get(0);
        toUpdate.setSalary(60000.0);
        assertTrue(DatabaseManager.updateEmployee(toUpdate));
        
        // Step 6: Verify update
        employees = DatabaseManager.getAllEmployees();
        Employee updated = employees.stream()
                .filter(e -> "Integration Test".equals(e.getName()))
                .findFirst()
                .orElse(null);
        assertNotNull(updated);
        assertEquals(60000.0, updated.getSalary());
        
        // Step 7: Delete employee
        assertTrue(DatabaseManager.deleteEmployee(updated.getId()));
        employees = DatabaseManager.getAllEmployees();
        assertEquals(3, employees.size());
    }
    
    @Test
    @DisplayName("Test concurrent database access")
    void testConcurrentDatabaseAccess() throws InterruptedException {
        int threadCount = 5;
        int operationsPerThread = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        // Each thread will insert, read, update, and delete employees
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < operationsPerThread; j++) {
                        String name = "Thread-" + threadId + "-Employee-" + j;
                        String email = "thread" + threadId + ".employee" + j + "@test.com";
                        
                        // Insert
                        Employee emp = new Employee(0, name, email, "Concurrent", 50000.0 + j);
                        DatabaseManager.insertEmployee(emp);
                        
                        // Read
                        List<Employee> employees = DatabaseManager.getAllEmployees();
                        assertFalse(employees.isEmpty());
                        
                        // Search
                        List<Employee> searchResults = DatabaseManager.searchEmployeesByName(name);
                        assertEquals(1, searchResults.size());
                        
                        // Update
                        Employee found = searchResults.get(0);
                        found.setSalary(found.getSalary() + 1000.0);
                        DatabaseManager.updateEmployee(found);
                        
                        // Delete
                        DatabaseManager.deleteEmployee(found.getId());
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        // Wait for all threads to complete
        boolean completed = latch.await(30, TimeUnit.SECONDS);
        assertTrue(completed, "Concurrent test did not complete within timeout");
        
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        
        // Verify final state
        List<Employee> finalEmployees = DatabaseManager.getAllEmployees();
        assertEquals(0, finalEmployees.size());
    }
    
    @Test
    @DisplayName("Test large dataset operations")
    void testLargeDatasetOperations() {
        int employeeCount = 100;
        
        // Insert large dataset
        for (int i = 0; i < employeeCount; i++) {
            Employee emp = new Employee(0, 
                "Employee-" + i, 
                "employee" + i + "@company.com", 
                "Department-" + (i % 5), 
                50000.0 + (i * 100));
            assertTrue(DatabaseManager.insertEmployee(emp));
        }
        
        // Verify all employees were inserted
        List<Employee> employees = DatabaseManager.getAllEmployees();
        assertEquals(employeeCount, employees.size());
        
        // Test search performance
        long startTime = System.currentTimeMillis();
        List<Employee> searchResults = DatabaseManager.searchEmployeesByName("Employee-50");
        long endTime = System.currentTimeMillis();
        
        assertEquals(1, searchResults.size());
        assertTrue((endTime - startTime) < 1000, "Search took too long: " + (endTime - startTime) + "ms");
        
        // Test update performance
        startTime = System.currentTimeMillis();
        Employee toUpdate = employees.get(50);
        toUpdate.setSalary(100000.0);
        assertTrue(DatabaseManager.updateEmployee(toUpdate));
        endTime = System.currentTimeMillis();
        
        assertTrue((endTime - startTime) < 1000, "Update took too long: " + (endTime - startTime) + "ms");
        
        // Test delete performance
        startTime = System.currentTimeMillis();
        assertTrue(DatabaseManager.deleteEmployee(toUpdate.getId()));
        endTime = System.currentTimeMillis();
        
        assertTrue((endTime - startTime) < 1000, "Delete took too long: " + (endTime - startTime) + "ms");
        
        // Verify final count
        employees = DatabaseManager.getAllEmployees();
        assertEquals(employeeCount - 1, employees.size());
    }
    
    @Test
    @DisplayName("Test data consistency across operations")
    void testDataConsistency() {
        // Insert test data
        Employee emp1 = new Employee(0, "Consistency Test 1", "consistency1@test.com", "Testing", 50000.0);
        Employee emp2 = new Employee(0, "Consistency Test 2", "consistency2@test.com", "Testing", 60000.0);
        
        DatabaseManager.insertEmployee(emp1);
        DatabaseManager.insertEmployee(emp2);
        
        // Verify initial state
        List<Employee> employees = DatabaseManager.getAllEmployees();
        assertEquals(2, employees.size());
        
        // Perform multiple operations and verify consistency
        for (int i = 0; i < 10; i++) {
            // Read all employees
            employees = DatabaseManager.getAllEmployees();
            assertEquals(2, employees.size());
            
            // Search for specific employees
            List<Employee> search1 = DatabaseManager.searchEmployeesByName("Consistency Test 1");
            List<Employee> search2 = DatabaseManager.searchEmployeesByName("Consistency Test 2");
            
            assertEquals(1, search1.size());
            assertEquals(1, search2.size());
            
            // Update employees
            Employee emp1Updated = search1.get(0);
            Employee emp2Updated = search2.get(0);
            
            emp1Updated.setSalary(emp1Updated.getSalary() + 1000.0);
            emp2Updated.setSalary(emp2Updated.getSalary() + 1000.0);
            
            DatabaseManager.updateEmployee(emp1Updated);
            DatabaseManager.updateEmployee(emp2Updated);
            
            // Verify updates
            employees = DatabaseManager.getAllEmployees();
            assertEquals(2, employees.size());
            
            double totalSalary = employees.stream().mapToDouble(Employee::getSalary).sum();
            assertTrue(totalSalary > 110000.0); // Should be increasing
        }
    }
    
    @Test
    @DisplayName("Test error recovery scenarios")
    void testErrorRecovery() {
        // Test recovery after database corruption simulation
        // (This is a simplified test - in real scenarios you'd need more sophisticated testing)
        
        // Insert some data
        Employee emp = new Employee(0, "Recovery Test", "recovery@test.com", "Testing", 50000.0);
        DatabaseManager.insertEmployee(emp);
        
        // Verify data exists
        List<Employee> employees = DatabaseManager.getAllEmployees();
        assertEquals(1, employees.size());
        
        // Simulate database reset by deleting and reinitializing
        cleanupTestDatabase();
        DatabaseManager.initializeDatabase();
        
        // Verify database is still functional
        employees = DatabaseManager.getAllEmployees();
        assertEquals(0, employees.size()); // Should be empty after reinitialization
        
        // Test that we can still insert new data
        Employee newEmp = new Employee(0, "New Recovery Test", "newrecovery@test.com", "Testing", 60000.0);
        assertTrue(DatabaseManager.insertEmployee(newEmp));
        
        employees = DatabaseManager.getAllEmployees();
        assertEquals(1, employees.size());
    }
    
    @Test
    @DisplayName("Test boundary conditions")
    void testBoundaryConditions() {
        // Test with maximum values
        Employee maxEmp = new Employee(0, 
            "A".repeat(1000), // Very long name
            "test@" + "a".repeat(1000) + ".com", // Very long email
            "B".repeat(500), // Very long department
            Double.MAX_VALUE); // Maximum salary
        
        assertTrue(DatabaseManager.insertEmployee(maxEmp));
        
        List<Employee> employees = DatabaseManager.getAllEmployees();
        assertEquals(1, employees.size());
        
        Employee retrieved = employees.get(0);
        assertEquals("A".repeat(1000), retrieved.getName());
        assertEquals("test@" + "a".repeat(1000) + ".com", retrieved.getEmail());
        assertEquals("B".repeat(500), retrieved.getDepartment());
        assertEquals(Double.MAX_VALUE, retrieved.getSalary());
        
        // Test with minimum values
        Employee minEmp = new Employee(0, "", "", "", Double.MIN_VALUE);
        assertTrue(DatabaseManager.insertEmployee(minEmp));
        
        employees = DatabaseManager.getAllEmployees();
        assertEquals(2, employees.size());
    }
}
