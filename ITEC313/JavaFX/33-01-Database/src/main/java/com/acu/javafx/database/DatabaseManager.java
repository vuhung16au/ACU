package com.acu.javafx.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Database manager class for SQLite operations
 */
public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:employee.sqlite";
    
    /**
     * Initialize the database and create tables
     */
    public static void initializeDatabase() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS employees (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT UNIQUE,
                department TEXT,
                salary REAL DEFAULT 0.0
            )
        """;
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Database initialized successfully");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
    
    /**
     * Insert a new employee
     */
    public static boolean insertEmployee(Employee employee) {
        String sql = "INSERT INTO employees (name, email, department, salary) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getEmail());
            pstmt.setString(3, employee.getDepartment());
            pstmt.setDouble(4, employee.getSalary());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting employee: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get all employees
     */
    public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY id";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Employee employee = new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("department"),
                    rs.getDouble("salary")
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
        }
        
        return employees;
    }
    
    /**
     * Update an employee
     */
    public static boolean updateEmployee(Employee employee) {
        String sql = "UPDATE employees SET name=?, email=?, department=?, salary=? WHERE id=?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getEmail());
            pstmt.setString(3, employee.getDepartment());
            pstmt.setDouble(4, employee.getSalary());
            pstmt.setInt(5, employee.getId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete an employee by ID
     */
    public static boolean deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id=?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Search employees by name
     */
    public static List<Employee> searchEmployeesByName(String searchTerm) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE name LIKE ? ORDER BY id";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Employee employee = new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("department"),
                    rs.getDouble("salary")
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.err.println("Error searching employees: " + e.getMessage());
        }
        
        return employees;
    }
    
    /**
     * Populate database with sample data
     */
    public static void populateSampleData() {
        // Check if data already exists
        List<Employee> existing = getAllEmployees();
        if (!existing.isEmpty()) {
            System.out.println("Database already contains data, skipping population");
            return;
        }
        
        Employee[] sampleEmployees = {
            new Employee(0, "Vu Nguyen", "vu.nguyen@company.com", "Engineering", 75000.0),
            new Employee(0, "John Doe", "john.doe@company.com", "Marketing", 65000.0),
            new Employee(0, "English Sydney", "english.sydney@company.com", "Sales", 70000.0)
        };
        
        for (Employee emp : sampleEmployees) {
            if (insertEmployee(emp)) {
                System.out.println("Added sample employee: " + emp.getName());
            }
        }
    }
}
