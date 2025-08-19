# Testing Guide

This guide provides comprehensive information about testing strategies, best practices, and implementation for the JavaFX Employee Database Management System.

## Table of Contents

- [Testing Overview](#testing-overview)
- [Testing Strategy](#testing-strategy)
- [Unit Testing](#unit-testing)
- [Integration Testing](#integration-testing)
- [UI Testing](#ui-testing)
- [Database Testing](#database-testing)
- [Test Data Management](#test-data-management)
- [Test Coverage](#test-coverage)
- [Continuous Integration](#continuous-integration)
- [Troubleshooting](#troubleshooting)

## Testing Overview

### Testing Pyramid

Our testing approach follows the testing pyramid:

```
    /\
   /  \     E2E Tests (Few)
  /____\    Integration Tests (Some)
 /______\   Unit Tests (Many)
```

- **Unit Tests**: Fast, isolated tests for individual components
- **Integration Tests**: Tests for component interactions
- **End-to-End Tests**: Full application workflow tests

### Testing Goals

- **Reliability**: Ensure application works correctly
- **Maintainability**: Easy to update and extend tests
- **Performance**: Fast test execution
- **Coverage**: Comprehensive test coverage (>80%)

## Testing Strategy

### Test Categories

#### 1. Unit Tests
- **Purpose**: Test individual components in isolation
- **Scope**: Single class or method
- **Speed**: Fast execution (< 1 second per test)
- **Dependencies**: Mocked or stubbed

#### 2. Integration Tests
- **Purpose**: Test component interactions
- **Scope**: Multiple classes working together
- **Speed**: Medium execution (1-10 seconds per test)
- **Dependencies**: Real database, in-memory or test containers

#### 3. End-to-End Tests
- **Purpose**: Test complete user workflows
- **Scope**: Full application
- **Speed**: Slow execution (10+ seconds per test)
- **Dependencies**: Full application stack

### Test Naming Conventions

```java
// Pattern: test[MethodName]_[Scenario]_[ExpectedResult]
@Test
void testInsertEmployee_WithValidData_ShouldReturnTrue() { }

@Test
void testInsertEmployee_WithNullName_ShouldReturnFalse() { }

@Test
void testSearchEmployees_WithEmptyTerm_ShouldReturnAllEmployees() { }

// For complex scenarios, use @DisplayName
@Test
@DisplayName("Should handle database connection failure gracefully")
void testDatabaseConnectionFailure() { }
```

### Test Structure (AAA Pattern)

```java
@Test
void testExample() {
    // Arrange (Given)
    Employee employee = new Employee(1, "John", "john@test.com", "IT", 50000.0);
    
    // Act (When)
    boolean result = DatabaseManager.insertEmployee(employee);
    
    // Assert (Then)
    assertTrue(result);
}
```

## Unit Testing

### Employee Model Tests

#### Test Setup
```java
@DisplayName("Employee Model Tests")
class EmployeeTest {
    
    private Employee employee;
    
    @BeforeEach
    void setUp() {
        employee = new Employee();
    }
    
    @AfterEach
    void tearDown() {
        employee = null;
    }
}
```

#### Constructor Tests
```java
@Test
@DisplayName("Default constructor should create employee with default values")
void testDefaultConstructor() {
    // Given: Employee created with default constructor
    
    // When: No additional setup
    
    // Then: Default values should be set
    assertEquals(0, employee.getId());
    assertNull(employee.getName());
    assertNull(employee.getEmail());
    assertNull(employee.getDepartment());
    assertEquals(0.0, employee.getSalary());
}

@Test
@DisplayName("Parameterized constructor should set all values correctly")
void testParameterizedConstructor() {
    // Given: Employee data
    int id = 1;
    String name = "John Doe";
    String email = "john@example.com";
    String department = "Engineering";
    double salary = 75000.0;
    
    // When: Creating employee with parameters
    Employee emp = new Employee(id, name, email, department, salary);
    
    // Then: All values should be set correctly
    assertEquals(id, emp.getId());
    assertEquals(name, emp.getName());
    assertEquals(email, emp.getEmail());
    assertEquals(department, emp.getDepartment());
    assertEquals(salary, emp.getSalary());
}
```

#### Property Tests
```java
@Test
@DisplayName("Name property should be set and retrieved correctly")
void testNameProperty() {
    // Given: Employee instance
    String expectedName = "Jane Smith";
    
    // When: Setting name
    employee.setName(expectedName);
    
    // Then: Name should be set correctly
    assertEquals(expectedName, employee.getName());
    assertEquals(expectedName, employee.nameProperty().get());
}

@Test
@DisplayName("Salary property should handle decimal values")
void testSalaryPropertyWithDecimals() {
    // Given: Employee instance
    double expectedSalary = 75000.50;
    
    // When: Setting salary with decimals
    employee.setSalary(expectedSalary);
    
    // Then: Salary should be set correctly
    assertEquals(expectedSalary, employee.getSalary());
}

@Test
@DisplayName("Properties should be observable")
void testPropertyObservability() {
    // Given: Employee instance
    StringProperty nameProperty = employee.nameProperty();
    DoubleProperty salaryProperty = employee.salaryProperty();
    
    // When: Properties are accessed
    
    // Then: Properties should not be null
    assertNotNull(nameProperty);
    assertNotNull(salaryProperty);
    
    // And: Properties should be observable
    assertTrue(nameProperty instanceof Observable);
    assertTrue(salaryProperty instanceof Observable);
}
```

#### Edge Case Tests
```java
@Test
@DisplayName("Should handle null values")
void testNullValues() {
    // Given: Employee instance
    
    // When: Setting null values
    employee.setName(null);
    employee.setEmail(null);
    employee.setDepartment(null);
    
    // Then: Values should be set to null
    assertNull(employee.getName());
    assertNull(employee.getEmail());
    assertNull(employee.getDepartment());
}

@Test
@DisplayName("Should handle empty strings")
void testEmptyStrings() {
    // Given: Employee instance
    
    // When: Setting empty strings
    employee.setName("");
    employee.setEmail("");
    employee.setDepartment("");
    
    // Then: Values should be set to empty strings
    assertEquals("", employee.getName());
    assertEquals("", employee.getEmail());
    assertEquals("", employee.getDepartment());
}

@Test
@DisplayName("Should handle very long strings")
void testVeryLongStrings() {
    // Given: Employee instance and long strings
    String longName = "A".repeat(1000);
    String longEmail = "test@" + "a".repeat(1000) + ".com";
    String longDept = "B".repeat(500);
    
    // When: Setting very long strings
    employee.setName(longName);
    employee.setEmail(longEmail);
    employee.setDepartment(longDept);
    
    // Then: Values should be set correctly
    assertEquals(longName, employee.getName());
    assertEquals(longEmail, employee.getEmail());
    assertEquals(longDept, employee.getDepartment());
}

@Test
@DisplayName("Should handle negative salary")
void testNegativeSalary() {
    // Given: Employee instance
    double negativeSalary = -50000.0;
    
    // When: Setting negative salary
    employee.setSalary(negativeSalary);
    
    // Then: Salary should be set correctly
    assertEquals(negativeSalary, employee.getSalary());
}

@Test
@DisplayName("Should handle maximum salary value")
void testMaximumSalary() {
    // Given: Employee instance
    double maxSalary = Double.MAX_VALUE;
    
    // When: Setting maximum salary
    employee.setSalary(maxSalary);
    
    // Then: Salary should be set correctly
    assertEquals(maxSalary, employee.getSalary());
}
```

#### toString() Tests
```java
@Test
@DisplayName("toString should return correct format")
void testToString() {
    // Given: Employee with all values set
    Employee emp = new Employee(1, "John Doe", "john@example.com", "Engineering", 75000.0);
    
    // When: Calling toString
    String result = emp.toString();
    
    // Then: Should contain all employee information
    assertTrue(result.contains("Employee{id=1"));
    assertTrue(result.contains("name='John Doe'"));
    assertTrue(result.contains("email='john@example.com'"));
    assertTrue(result.contains("department='Engineering'"));
    assertTrue(result.contains("salary=75000.00"));
}

@Test
@DisplayName("toString should handle null values")
void testToStringWithNullValues() {
    // Given: Employee with default values (nulls)
    
    // When: Calling toString
    String result = employee.toString();
    
    // Then: Should handle null values gracefully
    assertTrue(result.contains("Employee{id=0"));
    assertTrue(result.contains("name='null'"));
    assertTrue(result.contains("email='null'"));
    assertTrue(result.contains("department='null'"));
    assertTrue(result.contains("salary=0.00"));
}
```

### Database Manager Tests

#### Mocking Database Dependencies
```java
@ExtendWith(MockitoExtension.class)
@DisplayName("Database Manager Tests")
class DatabaseManagerTest {
    
    @Mock
    private Connection mockConnection;
    
    @Mock
    private PreparedStatement mockPreparedStatement;
    
    @Mock
    private ResultSet mockResultSet;
    
    @BeforeEach
    void setUp() throws SQLException {
        // Setup common mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }
}
```

#### Insert Employee Tests
```java
@Test
@DisplayName("Should insert employee successfully")
void testInsertEmployee_Success() throws SQLException {
    // Given: Valid employee and mocked database
    Employee employee = new Employee(0, "John Doe", "john@test.com", "IT", 50000.0);
    when(mockPreparedStatement.executeUpdate()).thenReturn(1);
    
    // When: Inserting employee
    boolean result = DatabaseManager.insertEmployee(employee);
    
    // Then: Should return true
    assertTrue(result);
    
    // And: Should set parameters correctly
    verify(mockPreparedStatement).setString(1, "John Doe");
    verify(mockPreparedStatement).setString(2, "john@test.com");
    verify(mockPreparedStatement).setString(3, "IT");
    verify(mockPreparedStatement).setDouble(4, 50000.0);
    verify(mockPreparedStatement).executeUpdate();
}

@Test
@DisplayName("Should return false when insert fails")
void testInsertEmployee_Failure() throws SQLException {
    // Given: Valid employee and mocked database failure
    Employee employee = new Employee(0, "John Doe", "john@test.com", "IT", 50000.0);
    when(mockPreparedStatement.executeUpdate()).thenReturn(0);
    
    // When: Inserting employee
    boolean result = DatabaseManager.insertEmployee(employee);
    
    // Then: Should return false
    assertFalse(result);
}

@Test
@DisplayName("Should handle SQL exception during insert")
void testInsertEmployee_SQLException() throws SQLException {
    // Given: Valid employee and SQL exception
    Employee employee = new Employee(0, "John Doe", "john@test.com", "IT", 50000.0);
    when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Database error"));
    
    // When: Inserting employee
    boolean result = DatabaseManager.insertEmployee(employee);
    
    // Then: Should return false
    assertFalse(result);
}
```

#### Get All Employees Tests
```java
@Test
@DisplayName("Should retrieve all employees successfully")
void testGetAllEmployees_Success() throws SQLException {
    // Given: Mocked result set with employee data
    when(mockConnection.createStatement()).thenReturn(mockStatement);
    when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true, true, false); // Two employees
    when(mockResultSet.getInt("id")).thenReturn(1, 2);
    when(mockResultSet.getString("name")).thenReturn("John", "Jane");
    when(mockResultSet.getString("email")).thenReturn("john@test.com", "jane@test.com");
    when(mockResultSet.getString("department")).thenReturn("IT", "HR");
    when(mockResultSet.getDouble("salary")).thenReturn(50000.0, 60000.0);
    
    // When: Getting all employees
    List<Employee> employees = DatabaseManager.getAllEmployees();
    
    // Then: Should return list with employees
    assertEquals(2, employees.size());
    assertEquals("John", employees.get(0).getName());
    assertEquals("Jane", employees.get(1).getName());
}

@Test
@DisplayName("Should return empty list when no employees exist")
void testGetAllEmployees_EmptyResult() throws SQLException {
    // Given: Mocked result set with no data
    when(mockConnection.createStatement()).thenReturn(mockStatement);
    when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(false);
    
    // When: Getting all employees
    List<Employee> employees = DatabaseManager.getAllEmployees();
    
    // Then: Should return empty list
    assertTrue(employees.isEmpty());
}
```

#### Search Employees Tests
```java
@Test
@DisplayName("Should search employees by name successfully")
void testSearchEmployeesByName_Success() throws SQLException {
    // Given: Mocked result set with search results
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true, false); // One result
    when(mockResultSet.getInt("id")).thenReturn(1);
    when(mockResultSet.getString("name")).thenReturn("John Doe");
    when(mockResultSet.getString("email")).thenReturn("john@test.com");
    when(mockResultSet.getString("department")).thenReturn("IT");
    when(mockResultSet.getDouble("salary")).thenReturn(50000.0);
    
    // When: Searching for employees
    List<Employee> results = DatabaseManager.searchEmployeesByName("John");
    
    // Then: Should return matching employees
    assertEquals(1, results.size());
    assertEquals("John Doe", results.get(0).getName());
    
    // And: Should use correct search pattern
    verify(mockPreparedStatement).setString(1, "%John%");
}

@Test
@DisplayName("Should return all employees when search term is empty")
void testSearchEmployeesByName_EmptyTerm() throws SQLException {
    // Given: Mocked result set
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true, true, false); // Two results
    // ... setup result set data
    
    // When: Searching with empty term
    List<Employee> results = DatabaseManager.searchEmployeesByName("");
    
    // Then: Should return all employees
    assertEquals(2, results.size());
    
    // And: Should use wildcard pattern
    verify(mockPreparedStatement).setString(1, "%%");
}
```

## Integration Testing

### Database Integration Tests

#### Test Containers Setup
```java
@Testcontainers
@DisplayName("Database Integration Tests")
class DatabaseIntegrationTest {
    
    @Container
    static SQLiteContainer<?> sqlite = new SQLiteContainer<>("sqlite:3.45.1.0")
        .withDatabaseName("test_employee")
        .withUsername("test")
        .withPassword("test");
    
    @BeforeAll
    static void setUpDatabase() {
        // Initialize test database
        DatabaseManager.initializeDatabase();
    }
    
    @BeforeEach
    void setUp() {
        // Clear test data before each test
        clearTestData();
    }
    
    @AfterEach
    void tearDown() {
        // Clean up after each test
        clearTestData();
    }
    
    private void clearTestData() {
        // Clear all test data
        try (Connection conn = DriverManager.getConnection(sqlite.getJdbcUrl());
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM employees");
        } catch (SQLException e) {
            // Handle exception
        }
    }
}
```

#### CRUD Operation Tests
```java
@Test
@DisplayName("Should perform full CRUD lifecycle")
void testFullEmployeeLifecycle() {
    // Given: Employee data
    Employee employee = new Employee(0, "Test User", "test@example.com", "Test", 50000.0);
    
    // When: Insert employee
    boolean insertResult = DatabaseManager.insertEmployee(employee);
    
    // Then: Insert should be successful
    assertTrue(insertResult);
    
    // When: Retrieve all employees
    List<Employee> employees = DatabaseManager.getAllEmployees();
    
    // Then: Should contain the inserted employee
    assertFalse(employees.isEmpty());
    Employee retrieved = employees.get(0);
    assertEquals("Test User", retrieved.getName());
    assertEquals("test@example.com", retrieved.getEmail());
    assertEquals("Test", retrieved.getDepartment());
    assertEquals(50000.0, retrieved.getSalary());
    
    // When: Update employee
    retrieved.setSalary(60000.0);
    retrieved.setDepartment("Updated");
    boolean updateResult = DatabaseManager.updateEmployee(retrieved);
    
    // Then: Update should be successful
    assertTrue(updateResult);
    
    // When: Retrieve updated employee
    List<Employee> updatedEmployees = DatabaseManager.getAllEmployees();
    Employee updated = updatedEmployees.get(0);
    
    // Then: Should reflect updates
    assertEquals(60000.0, updated.getSalary());
    assertEquals("Updated", updated.getDepartment());
    
    // When: Delete employee
    boolean deleteResult = DatabaseManager.deleteEmployee(updated.getId());
    
    // Then: Delete should be successful
    assertTrue(deleteResult);
    
    // When: Retrieve all employees after delete
    List<Employee> finalEmployees = DatabaseManager.getAllEmployees();
    
    // Then: Should be empty
    assertTrue(finalEmployees.isEmpty());
}
```

#### Search Functionality Tests
```java
@Test
@DisplayName("Should search employees by name")
void testSearchEmployeesByName() {
    // Given: Multiple employees in database
    Employee emp1 = new Employee(0, "John Doe", "john@test.com", "IT", 50000.0);
    Employee emp2 = new Employee(0, "Jane Smith", "jane@test.com", "HR", 60000.0);
    Employee emp3 = new Employee(0, "Bob Johnson", "bob@test.com", "IT", 55000.0);
    
    DatabaseManager.insertEmployee(emp1);
    DatabaseManager.insertEmployee(emp2);
    DatabaseManager.insertEmployee(emp3);
    
    // When: Searching for "John"
    List<Employee> johnResults = DatabaseManager.searchEmployeesByName("John");
    
    // Then: Should return John Doe
    assertEquals(1, johnResults.size());
    assertEquals("John Doe", johnResults.get(0).getName());
    
    // When: Searching for "IT"
    List<Employee> itResults = DatabaseManager.searchEmployeesByName("IT");
    
    // Then: Should return employees in IT department
    assertEquals(2, itResults.size());
    assertTrue(itResults.stream().allMatch(emp -> "IT".equals(emp.getDepartment())));
    
    // When: Searching with empty term
    List<Employee> allResults = DatabaseManager.searchEmployeesByName("");
    
    // Then: Should return all employees
    assertEquals(3, allResults.size());
}
```

#### Data Validation Tests
```java
@Test
@DisplayName("Should handle unique email constraint")
void testUniqueEmailConstraint() {
    // Given: Employee with email
    Employee emp1 = new Employee(0, "John Doe", "john@test.com", "IT", 50000.0);
    Employee emp2 = new Employee(0, "Jane Smith", "john@test.com", "HR", 60000.0);
    
    // When: Inserting first employee
    boolean firstInsert = DatabaseManager.insertEmployee(emp1);
    
    // Then: Should be successful
    assertTrue(firstInsert);
    
    // When: Inserting second employee with same email
    boolean secondInsert = DatabaseManager.insertEmployee(emp2);
    
    // Then: Should fail due to unique constraint
    assertFalse(secondInsert);
}

@Test
@DisplayName("Should handle null name constraint")
void testNullNameConstraint() {
    // Given: Employee with null name
    Employee employee = new Employee();
    employee.setName(null);
    employee.setEmail("test@test.com");
    employee.setDepartment("IT");
    employee.setSalary(50000.0);
    
    // When: Attempting to insert
    boolean result = DatabaseManager.insertEmployee(employee);
    
    // Then: Should fail due to NOT NULL constraint
    assertFalse(result);
}
```

## UI Testing

### JavaFX Application Tests

#### Application Startup Tests
```java
@DisplayName("JavaFX Application Tests")
class JavaFXApplicationTest {
    
    @Test
    @DisplayName("Should start application successfully")
    void testApplicationStartup() {
        // Given: JavaFX application
        
        // When: Starting application
        assertDoesNotThrow(() -> {
            // This would typically be tested with TestFX
            // For now, we test that the application class can be instantiated
            EmployeeDatabaseApp app = new EmployeeDatabaseApp();
            assertNotNull(app);
        });
    }
}
```

#### UI Component Tests
```java
@Test
@DisplayName("Should create UI components correctly")
void testUIComponentCreation() {
    // Given: Application instance
    EmployeeDatabaseApp app = new EmployeeDatabaseApp();
    
    // When: Creating UI components
    // Note: This would require TestFX for actual UI testing
    
    // Then: Components should be created
    // This is a placeholder for actual UI testing
    assertTrue(true);
}
```

## Database Testing

### Test Data Management

#### Test Data Factory
```java
public class TestDataFactory {
    
    public static Employee createValidEmployee() {
        return new Employee(0, "Test User", "test@example.com", "Test Department", 50000.0);
    }
    
    public static Employee createEmployeeWithName(String name) {
        return new Employee(0, name, name.toLowerCase().replace(" ", ".") + "@test.com", "Test", 50000.0);
    }
    
    public static Employee createEmployeeWithSalary(double salary) {
        return new Employee(0, "Test User", "test@example.com", "Test", salary);
    }
    
    public static List<Employee> createMultipleEmployees(int count) {
        List<Employee> employees = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            employees.add(new Employee(0, "User " + i, "user" + i + "@test.com", "Department " + i, 50000.0 + i * 1000));
        }
        return employees;
    }
}
```

#### Database State Management
```java
public class DatabaseTestHelper {
    
    public static void clearDatabase() {
        try (Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM employees");
        } catch (SQLException e) {
            System.err.println("Error clearing database: " + e.getMessage());
        }
    }
    
    public static void insertTestData(List<Employee> employees) {
        for (Employee emp : employees) {
            DatabaseManager.insertEmployee(emp);
        }
    }
    
    public static int getEmployeeCount() {
        List<Employee> employees = DatabaseManager.getAllEmployees();
        return employees.size();
    }
    
    public static boolean employeeExists(String name) {
        List<Employee> employees = DatabaseManager.searchEmployeesByName(name);
        return !employees.isEmpty();
    }
}
```

## Test Coverage

### Coverage Goals

- **Line Coverage**: >90%
- **Branch Coverage**: >80%
- **Method Coverage**: >95%

### Coverage Reporting

#### Maven Configuration
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
        <execution>
            <id>check</id>
            <goals>
                <goal>check</goal>
            </goals>
            <configuration>
                <rules>
                    <rule>
                        <element>BUNDLE</element>
                        <limits>
                            <limit>
                                <counter>LINE</counter>
                                <value>COVEREDRATIO</value>
                                <minimum>0.90</minimum>
                            </limit>
                            <limit>
                                <counter>BRANCH</counter>
                                <value>COVEREDRATIO</value>
                                <minimum>0.80</minimum>
                            </limit>
                        </limits>
                    </rule>
                </rules>
            </configuration>
        </execution>
    </executions>
</plugin>
```

#### Running Coverage
```bash
# Generate coverage report
mvn clean test jacoco:report

# Check coverage thresholds
mvn clean test jacoco:check

# View coverage report
open target/site/jacoco/index.html
```

## Continuous Integration

### GitHub Actions Workflow
```yaml
name: Java CI with Maven

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 21
      uses: actions/setup-java@v3
      with:
        java-version: '21'
        distribution: 'temurin'
        cache: maven
    
    - name: Cache Maven packages
      uses: actions/cache@v3
      with:
        path: ~/.m2
        key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
        restore-keys: ${{ runner.os }}-m2
    
    - name: Run tests
      run: mvn clean test
    
    - name: Generate coverage report
      run: mvn jacoco:report
    
    - name: Upload coverage to Codecov
      uses: codecov/codecov-action@v3
      with:
        file: ./target/site/jacoco/jacoco.xml
        flags: unittests
        name: codecov-umbrella
```

### Test Execution Commands

#### Local Development
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=EmployeeTest

# Run specific test method
mvn test -Dtest=EmployeeTest#testNameProperty

# Run tests with coverage
mvn clean test jacoco:report

# Run tests in parallel
mvn test -T 1C
```

#### CI/CD Pipeline
```bash
# Clean build and test
mvn clean verify

# Run tests with coverage check
mvn clean test jacoco:check

# Run integration tests only
mvn test -Dtest=*IntegrationTest

# Run unit tests only
mvn test -Dtest=*Test -Dtest=!*IntegrationTest
```

## Troubleshooting

### Common Test Issues

#### Database Connection Issues
```java
// Problem: Database file locked
// Solution: Ensure proper cleanup
@AfterEach
void tearDown() {
    // Close all connections
    // Clear test data
    DatabaseTestHelper.clearDatabase();
}

// Problem: Concurrent test execution
// Solution: Use unique database files
@Test
void testWithUniqueDatabase() {
    String uniqueDbUrl = "jdbc:sqlite:test_" + System.currentTimeMillis() + ".sqlite";
    // Use unique database for this test
}
```

#### JavaFX Thread Issues
```java
// Problem: JavaFX components not initialized
// Solution: Use TestFX or mock UI components
@Test
void testUIComponent() {
    // Use TestFX for actual UI testing
    // Or mock UI components for unit testing
    TextField mockField = mock(TextField.class);
    when(mockField.getText()).thenReturn("Test");
    
    // Test with mocked component
}
```

#### Test Data Cleanup
```java
// Problem: Test data persists between tests
// Solution: Proper cleanup in @AfterEach
@AfterEach
void tearDown() {
    // Clear all test data
    DatabaseTestHelper.clearDatabase();
    
    // Reset any static state
    // Close any open resources
}
```

### Performance Issues

#### Slow Test Execution
```java
// Problem: Tests running slowly
// Solution: Optimize test setup

// Use @BeforeAll for expensive setup
@BeforeAll
static void setUpOnce() {
    // Expensive setup that can be shared
}

// Use in-memory database for tests
@Test
void testWithInMemoryDatabase() {
    // Use H2 or SQLite in-memory for faster tests
    String inMemoryUrl = "jdbc:h2:mem:testdb";
}
```

#### Memory Leaks
```java
// Problem: Memory leaks in tests
// Solution: Proper resource cleanup
@AfterEach
void tearDown() {
    // Clear collections
    if (employeeList != null) {
        employeeList.clear();
    }
    
    // Close connections
    // Reset static state
}
```

### Debugging Tests

#### Test Debugging
```java
// Add debug logging to tests
@Test
void testWithDebugging() {
    System.out.println("Starting test...");
    
    // Test logic
    
    System.out.println("Test completed successfully");
}

// Use assertions with messages
@Test
void testWithDetailedAssertions() {
    Employee employee = new Employee();
    employee.setName("Test");
    
    assertEquals("Test", employee.getName(), 
        "Employee name should be set to 'Test'");
}
```

#### Database Debugging
```java
// Enable SQL logging
public static void enableSQLLogging() {
    // Set system property for SQL logging
    System.setProperty("java.util.logging.config.file", "logging.properties");
}

// Check database state
public static void printDatabaseState() {
    List<Employee> employees = DatabaseManager.getAllEmployees();
    System.out.println("Database contains " + employees.size() + " employees:");
    employees.forEach(emp -> System.out.println("  " + emp));
}
```

---

This testing guide should be updated as the project evolves. For specific testing questions or issues, refer to the test examples in the source code or contact the development team.
