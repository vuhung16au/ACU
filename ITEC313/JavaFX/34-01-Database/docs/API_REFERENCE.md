# API Reference Documentation

This document provides comprehensive API documentation for the JavaFX Employee Database Management System.

## Table of Contents

- [Package Overview](#package-overview)
- [Employee Class](#employee-class)
- [DatabaseManager Class](#databasemanager-class)
- [EmployeeDatabaseApp Class](#employeedatabaseapp-class)
- [Database Schema](#database-schema)
- [Error Handling](#error-handling)
- [Examples](#examples)

## Package Overview

**Package**: `com.acu.javafx.database`

The main package contains four core classes:
- `Employee`: Data model class with JavaFX properties
- `DatabaseManager`: Static utility class for database operations
- `EmployeeDatabaseApp`: Main JavaFX application class
- `DatabaseTest`: Utility class for database testing

## Employee Class

### Class Overview
```java
public class Employee
```
Represents an employee entity with JavaFX properties for reactive UI binding.

### Constructors

#### Default Constructor
```java
public Employee()
```
Creates a new Employee instance with default values:
- `id`: 0
- `name`: null
- `email`: null
- `department`: null
- `salary`: 0.0

#### Parameterized Constructor
```java
public Employee(int id, String name, String email, String department, double salary)
```
Creates a new Employee instance with specified values.

**Parameters:**
- `id` (int): Employee ID
- `name` (String): Employee name
- `email` (String): Employee email address
- `department` (String): Employee department
- `salary` (double): Employee salary

### Properties

#### ID Property
```java
public int getId()
public void setId(int id)
public IntegerProperty idProperty()
```
- **Type**: `IntegerProperty`
- **Default**: 0
- **Description**: Unique identifier for the employee

#### Name Property
```java
public String getName()
public void setName(String name)
public StringProperty nameProperty()
```
- **Type**: `StringProperty`
- **Default**: null
- **Description**: Employee's full name
- **Validation**: Should not be empty for database operations

#### Email Property
```java
public String getEmail()
public void setEmail(String email)
public StringProperty emailProperty()
```
- **Type**: `StringProperty`
- **Default**: null
- **Description**: Employee's email address
- **Database Constraint**: UNIQUE

#### Department Property
```java
public String getDepartment()
public void setDepartment(String department)
public StringProperty departmentProperty()
```
- **Type**: `StringProperty`
- **Default**: null
- **Description**: Employee's department

#### Salary Property
```java
public double getSalary()
public void setSalary(double salary)
public DoubleProperty salaryProperty()
```
- **Type**: `DoubleProperty`
- **Default**: 0.0
- **Description**: Employee's salary amount
- **Format**: Currency format in UI ($XX.XX)

### Methods

#### toString()
```java
@Override
public String toString()
```
Returns a string representation of the Employee object.

**Format**: `Employee{id=X, name='Name', email='email', department='dept', salary=XX.XX}`

**Example**:
```java
Employee emp = new Employee(1, "John Doe", "john@example.com", "Engineering", 75000.0);
System.out.println(emp); // Employee{id=1, name='John Doe', email='john@example.com', department='Engineering', salary=75000.00}
```

## DatabaseManager Class

### Class Overview
```java
public class DatabaseManager
```
Static utility class providing database operations for the Employee entity.

### Database Configuration
- **Database Type**: SQLite
- **Database File**: `employee.sqlite`
- **Connection URL**: `jdbc:sqlite:employee.sqlite`

### Static Methods

#### initializeDatabase()
```java
public static void initializeDatabase()
```
Initializes the database and creates the employees table if it doesn't exist.

**SQL Schema**:
```sql
CREATE TABLE IF NOT EXISTS employees (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT UNIQUE,
    department TEXT,
    salary REAL DEFAULT 0.0
)
```

**Behavior**:
- Creates database file if it doesn't exist
- Creates employees table with proper schema
- Prints success/error messages to console
- Throws no exceptions (handles SQLException internally)

#### insertEmployee(Employee employee)
```java
public static boolean insertEmployee(Employee employee)
```
Inserts a new employee into the database.

**Parameters:**
- `employee` (Employee): Employee object to insert

**Returns:**
- `boolean`: true if insertion successful, false otherwise

**SQL Statement**:
```sql
INSERT INTO employees (name, email, department, salary) VALUES (?, ?, ?, ?)
```

**Validation**:
- Employee name must not be null or empty
- Email uniqueness is enforced by database constraint

**Example**:
```java
Employee emp = new Employee(0, "Jane Smith", "jane@company.com", "Marketing", 65000.0);
boolean success = DatabaseManager.insertEmployee(emp);
if (success) {
    System.out.println("Employee added successfully");
}
```

#### getAllEmployees()
```java
public static List<Employee> getAllEmployees()
```
Retrieves all employees from the database.

**Returns:**
- `List<Employee>`: List of all employees, ordered by ID

**SQL Statement**:
```sql
SELECT * FROM employees ORDER BY id
```

**Behavior**:
- Returns empty list if no employees exist
- Handles SQLException internally
- Employee objects are created with database values

#### updateEmployee(Employee employee)
```java
public static boolean updateEmployee(Employee employee)
```
Updates an existing employee in the database.

**Parameters:**
- `employee` (Employee): Employee object with updated values

**Returns:**
- `boolean`: true if update successful, false otherwise

**SQL Statement**:
```sql
UPDATE employees SET name=?, email=?, department=?, salary=? WHERE id=?
```

**Validation**:
- Employee ID must exist in database
- Employee name must not be null or empty

#### deleteEmployee(int id)
```java
public static boolean deleteEmployee(int id)
```
Deletes an employee from the database by ID.

**Parameters:**
- `id` (int): Employee ID to delete

**Returns:**
- `boolean`: true if deletion successful, false otherwise

**SQL Statement**:
```sql
DELETE FROM employees WHERE id=?
```

#### searchEmployeesByName(String searchTerm)
```java
public static List<Employee> searchEmployeesByName(String searchTerm)
```
Searches for employees by name using LIKE pattern matching.

**Parameters:**
- `searchTerm` (String): Search term for employee names

**Returns:**
- `List<Employee>`: List of matching employees

**SQL Statement**:
```sql
SELECT * FROM employees WHERE name LIKE ? ORDER BY id
```

**Search Pattern**: `%searchTerm%` (matches anywhere in name)

**Example**:
```java
List<Employee> results = DatabaseManager.searchEmployeesByName("John");
// Returns employees with "John" anywhere in their name
```

#### populateSampleData()
```java
public static void populateSampleData()
```
Populates the database with sample employee data.

**Behavior**:
- Only populates if database is empty
- Adds three sample employees
- Prints status messages to console

**Sample Data**:
1. Vu Nguyen - Engineering - $75,000
2. John Doe - Marketing - $65,000
3. English Sydney - Sales - $70,000

## EmployeeDatabaseApp Class

### Class Overview
```java
public class EmployeeDatabaseApp extends Application
```
Main JavaFX application class providing the user interface for employee management.

### Application Lifecycle

#### start(Stage primaryStage)
```java
@Override
public void start(Stage primaryStage)
```
Main entry point for the JavaFX application.

**Parameters:**
- `primaryStage` (Stage): Primary application window

**Initialization Steps**:
1. Initialize database
2. Populate sample data
3. Create UI components
4. Set up scene and show window

**Window Configuration**:
- Title: "Employee Database Management System"
- Size: 900x600 pixels
- Layout: VBox with centered alignment

### UI Components

#### Table View
- **Component**: `TableView<Employee>`
- **Columns**: ID, Name, Email, Department, Salary
- **Features**: Sortable columns, row selection
- **Salary Format**: Currency format ($XX.XX)

#### Form Fields
- **Name Field**: TextField for employee name
- **Email Field**: TextField for email address
- **Department Field**: TextField for department
- **Salary Field**: TextField for salary amount

#### Buttons
- **Add Employee**: Creates new employee
- **Update Employee**: Updates selected employee
- **Delete Employee**: Deletes selected employee with confirmation
- **Clear Form**: Clears all form fields
- **Refresh List**: Reloads employee data

#### Search Functionality
- **Search Field**: TextField for name search
- **Search Button**: Triggers search operation
- **Clear Button**: Clears search and shows all employees

### Event Handlers

#### addEmployee()
```java
private void addEmployee()
```
Handles adding a new employee.

**Validation**:
- Name is required
- Salary must be a valid number
- Shows error alerts for validation failures

**Success Flow**:
1. Create Employee object from form data
2. Insert into database
3. Refresh table view
4. Clear form fields
5. Show success message

#### updateEmployee()
```java
private void updateEmployee()
```
Handles updating an existing employee.

**Validation**:
- Employee must be selected
- Name is required
- Salary must be a valid number

**Success Flow**:
1. Update Employee object with form data
2. Update database record
3. Refresh table view
4. Clear form fields
5. Show success message

#### deleteEmployee()
```java
private void deleteEmployee()
```
Handles deleting an employee.

**Validation**:
- Employee must be selected
- Confirmation dialog required

**Success Flow**:
1. Show confirmation dialog
2. Delete from database if confirmed
3. Refresh table view
4. Clear form fields
5. Show success message

#### searchEmployees()
```java
private void searchEmployees()
```
Handles employee search functionality.

**Behavior**:
- Searches by name using LIKE pattern
- Updates table view with results
- Shows all employees if search term is empty

### Utility Methods

#### showAlert(String title, String content)
```java
private void showAlert(String title, String content)
```
Displays an information alert dialog.

**Parameters**:
- `title` (String): Alert window title
- `content` (String): Alert message content

#### refreshEmployeeList()
```java
private void refreshEmployeeList()
```
Reloads employee data from database and updates table view.

#### clearFields()
```java
private void clearFields()
```
Clears all form fields and deselects table row.

#### populateFields(Employee employee)
```java
private void populateFields(Employee employee)
```
Populates form fields with employee data.

**Parameters**:
- `employee` (Employee): Employee object to display

## Database Schema

### Employees Table
```sql
CREATE TABLE employees (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT UNIQUE,
    department TEXT,
    salary REAL DEFAULT 0.0
);
```

### Column Descriptions
- **id**: Auto-incrementing primary key
- **name**: Employee name (required)
- **email**: Email address (unique constraint)
- **department**: Department name
- **salary**: Salary amount (default: 0.0)

### Constraints
- **Primary Key**: `id` (auto-increment)
- **NOT NULL**: `name`
- **UNIQUE**: `email`
- **DEFAULT**: `salary = 0.0`

## Error Handling

### Database Errors
- All database operations handle SQLException internally
- Error messages are printed to console
- Methods return boolean/false on failure
- No exceptions are thrown to calling code

### UI Validation
- Form validation prevents invalid data submission
- User-friendly error messages via alert dialogs
- Confirmation dialogs for destructive operations

### Common Error Scenarios
1. **Database Connection Failure**: Check file permissions
2. **Unique Constraint Violation**: Email already exists
3. **Invalid Salary Format**: Non-numeric input
4. **Missing Required Fields**: Name is empty

## Examples

### Creating and Saving an Employee
```java
// Create employee
Employee emp = new Employee();
emp.setName("John Doe");
emp.setEmail("john@company.com");
emp.setDepartment("Engineering");
emp.setSalary(75000.0);

// Save to database
boolean success = DatabaseManager.insertEmployee(emp);
if (success) {
    System.out.println("Employee saved successfully");
}
```

### Searching for Employees
```java
// Search by name
List<Employee> results = DatabaseManager.searchEmployeesByName("John");
for (Employee emp : results) {
    System.out.println(emp.getName() + " - " + emp.getDepartment());
}
```

### Updating Employee Data
```java
// Get employee by ID (assuming ID 1 exists)
List<Employee> allEmployees = DatabaseManager.getAllEmployees();
Employee emp = allEmployees.stream()
    .filter(e -> e.getId() == 1)
    .findFirst()
    .orElse(null);

if (emp != null) {
    emp.setSalary(80000.0);
    boolean success = DatabaseManager.updateEmployee(emp);
    System.out.println("Update successful: " + success);
}
```

### Property Binding Example
```java
Employee emp = new Employee();
StringProperty nameProperty = emp.nameProperty();

// Bind property to UI component
TextField nameField = new TextField();
nameField.textProperty().bindBidirectional(nameProperty);

// Changes to text field automatically update employee name
nameField.setText("Jane Smith");
System.out.println(emp.getName()); // Outputs: Jane Smith
```

---

**Note**: This API reference is based on version 1.0 of the application. For the most up-to-date information, refer to the source code and JavaDoc comments.
