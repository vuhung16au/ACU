package com.acu.javafx.database;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Employee class
 */
class EmployeeTest {
    
    private Employee employee;
    
    @BeforeEach
    void setUp() {
        employee = new Employee();
    }
    
    @Test
    @DisplayName("Test default constructor creates employee with default values")
    void testDefaultConstructor() {
        assertEquals(0, employee.getId());
        assertNull(employee.getName());
        assertNull(employee.getEmail());
        assertNull(employee.getDepartment());
        assertEquals(0.0, employee.getSalary());
    }
    
    @Test
    @DisplayName("Test parameterized constructor sets all values correctly")
    void testParameterizedConstructor() {
        Employee emp = new Employee(1, "John Doe", "john@example.com", "Engineering", 75000.0);
        
        assertEquals(1, emp.getId());
        assertEquals("John Doe", emp.getName());
        assertEquals("john@example.com", emp.getEmail());
        assertEquals("Engineering", emp.getDepartment());
        assertEquals(75000.0, emp.getSalary());
    }
    
    @Test
    @DisplayName("Test ID property getter and setter")
    void testIdProperty() {
        employee.setId(123);
        assertEquals(123, employee.getId());
        
        IntegerProperty idProperty = employee.idProperty();
        assertNotNull(idProperty);
        assertEquals(123, idProperty.get());
    }
    
    @Test
    @DisplayName("Test name property getter and setter")
    void testNameProperty() {
        employee.setName("Jane Smith");
        assertEquals("Jane Smith", employee.getName());
        
        StringProperty nameProperty = employee.nameProperty();
        assertNotNull(nameProperty);
        assertEquals("Jane Smith", nameProperty.get());
    }
    
    @Test
    @DisplayName("Test email property getter and setter")
    void testEmailProperty() {
        employee.setEmail("jane.smith@company.com");
        assertEquals("jane.smith@company.com", employee.getEmail());
        
        StringProperty emailProperty = employee.emailProperty();
        assertNotNull(emailProperty);
        assertEquals("jane.smith@company.com", emailProperty.get());
    }
    
    @Test
    @DisplayName("Test department property getter and setter")
    void testDepartmentProperty() {
        employee.setDepartment("Marketing");
        assertEquals("Marketing", employee.getDepartment());
        
        StringProperty deptProperty = employee.departmentProperty();
        assertNotNull(deptProperty);
        assertEquals("Marketing", deptProperty.get());
    }
    
    @Test
    @DisplayName("Test salary property getter and setter")
    void testSalaryProperty() {
        employee.setSalary(85000.0);
        assertEquals(85000.0, employee.getSalary());
        
        DoubleProperty salaryProperty = employee.salaryProperty();
        assertNotNull(salaryProperty);
        assertEquals(85000.0, salaryProperty.get());
    }
    
    @Test
    @DisplayName("Test property bindings work correctly")
    void testPropertyBindings() {
        StringProperty nameProperty = employee.nameProperty();
        nameProperty.set("Test Name");
        assertEquals("Test Name", employee.getName());
        
        DoubleProperty salaryProperty = employee.salaryProperty();
        salaryProperty.set(100000.0);
        assertEquals(100000.0, employee.getSalary());
    }
    
    @Test
    @DisplayName("Test toString method returns correct format")
    void testToString() {
        Employee emp = new Employee(1, "John Doe", "john@example.com", "Engineering", 75000.0);
        String result = emp.toString();
        
        assertTrue(result.contains("Employee{id=1"));
        assertTrue(result.contains("name='John Doe'"));
        assertTrue(result.contains("email='john@example.com'"));
        assertTrue(result.contains("department='Engineering'"));
        assertTrue(result.contains("salary=75000.00"));
    }
    
    @Test
    @DisplayName("Test toString with zero values")
    void testToStringWithZeroValues() {
        String result = employee.toString();
        
        assertTrue(result.contains("Employee{id=0"));
        assertTrue(result.contains("name='null'"));
        assertTrue(result.contains("email='null'"));
        assertTrue(result.contains("department='null'"));
        assertTrue(result.contains("salary=0.00"));
    }
    
    @Test
    @DisplayName("Test setting null values")
    void testNullValues() {
        employee.setName(null);
        employee.setEmail(null);
        employee.setDepartment(null);
        
        assertNull(employee.getName());
        assertNull(employee.getEmail());
        assertNull(employee.getDepartment());
    }
    
    @Test
    @DisplayName("Test setting empty strings")
    void testEmptyStrings() {
        employee.setName("");
        employee.setEmail("");
        employee.setDepartment("");
        
        assertEquals("", employee.getName());
        assertEquals("", employee.getEmail());
        assertEquals("", employee.getDepartment());
    }
    
    @Test
    @DisplayName("Test setting very long strings")
    void testVeryLongStrings() {
        String longName = "A".repeat(1000);
        String longEmail = "test@" + "a".repeat(1000) + ".com";
        String longDept = "B".repeat(500);
        
        employee.setName(longName);
        employee.setEmail(longEmail);
        employee.setDepartment(longDept);
        
        assertEquals(longName, employee.getName());
        assertEquals(longEmail, employee.getEmail());
        assertEquals(longDept, employee.getDepartment());
    }
    
    @Test
    @DisplayName("Test negative salary")
    void testNegativeSalary() {
        employee.setSalary(-50000.0);
        assertEquals(-50000.0, employee.getSalary());
    }
    
    @Test
    @DisplayName("Test very large salary")
    void testVeryLargeSalary() {
        employee.setSalary(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, employee.getSalary());
    }
    
    @Test
    @DisplayName("Test salary with decimal places")
    void testSalaryWithDecimals() {
        employee.setSalary(75000.50);
        assertEquals(75000.50, employee.getSalary());
    }
    
    @Test
    @DisplayName("Test property change notifications")
    void testPropertyChangeNotifications() {
        StringProperty nameProperty = employee.nameProperty();
        StringProperty emailProperty = employee.emailProperty();
        
        // Test that properties are observable
        assertNotNull(nameProperty);
        assertNotNull(emailProperty);
        
        // Test that setting values through properties works
        nameProperty.set("New Name");
        emailProperty.set("new@email.com");
        
        assertEquals("New Name", employee.getName());
        assertEquals("new@email.com", employee.getEmail());
    }
}
