package com.acu.javafx.database;

import java.util.List;

/**
 * Simple test class to verify database functionality
 * This can be run independently to test database operations
 */
public class DatabaseTest {
    
    public static void main(String[] args) {
        System.out.println("=== Database Test ===");
        
        try {
            // Initialize database
            System.out.println("1. Initializing database...");
            DatabaseManager.initializeDatabase();
            
            // Populate sample data
            System.out.println("2. Populating sample data...");
            DatabaseManager.populateSampleData();
            
            // Test retrieving all employees
            System.out.println("3. Retrieving all employees...");
            List<Employee> employees = DatabaseManager.getAllEmployees();
            System.out.println("Found " + employees.size() + " employees:");
            for (Employee emp : employees) {
                System.out.println("  - " + emp.getName() + " (" + emp.getDepartment() + ")");
            }
            
            // Test search functionality
            System.out.println("4. Testing search functionality...");
            List<Employee> searchResults = DatabaseManager.searchEmployeesByName("Vu");
            System.out.println("Search for 'Vu' found " + searchResults.size() + " results:");
            for (Employee emp : searchResults) {
                System.out.println("  - " + emp.getName());
            }
            
            // Test adding a new employee
            System.out.println("5. Testing add functionality...");
            Employee newEmployee = new Employee(0, "Test User", "test@example.com", "Testing", 50000.0);
            boolean added = DatabaseManager.insertEmployee(newEmployee);
            System.out.println("Add employee result: " + (added ? "SUCCESS" : "FAILED"));
            
            // Test updating an employee
            System.out.println("6. Testing update functionality...");
            if (!employees.isEmpty()) {
                Employee toUpdate = employees.get(0);
                toUpdate.setSalary(toUpdate.getSalary() + 1000.0);
                boolean updated = DatabaseManager.updateEmployee(toUpdate);
                System.out.println("Update employee result: " + (updated ? "SUCCESS" : "FAILED"));
            }
            
            // Show final employee count
            System.out.println("7. Final employee count: " + DatabaseManager.getAllEmployees().size());
            
            System.out.println("=== Database Test Completed Successfully ===");
            
        } catch (Exception e) {
            System.err.println("Database test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
