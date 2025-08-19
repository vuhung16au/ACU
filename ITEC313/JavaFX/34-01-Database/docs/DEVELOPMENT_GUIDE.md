# Development Guide

This guide provides comprehensive information for developers working on the JavaFX Employee Database Management System.

## Table of Contents

- [Development Environment Setup](#development-environment-setup)
- [Project Structure](#project-structure)
- [Coding Standards](#coding-standards)
- [Database Development](#database-development)
- [UI Development](#ui-development)
- [Testing Guidelines](#testing-guidelines)
- [Debugging](#debugging)
- [Performance Considerations](#performance-considerations)
- [Security Best Practices](#security-best-practices)
- [Deployment](#deployment)

## Development Environment Setup

### Prerequisites

#### Required Software
- **Java Development Kit (JDK)**: Version 21 or higher
- **Maven**: Version 3.6 or higher
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code
- **Git**: For version control

#### Recommended IDE Setup

##### IntelliJ IDEA
1. **Install JavaFX Plugin**
   - Go to `File → Settings → Plugins`
   - Search for "JavaFX" and install the plugin

2. **Configure Project SDK**
   - Go to `File → Project Structure`
   - Set Project SDK to Java 21
   - Set Project language level to 21

3. **Import Maven Project**
   - Go to `File → Open`
   - Select the `pom.xml` file
   - Let Maven download dependencies

4. **Configure Run Configuration**
   - Go to `Run → Edit Configurations`
   - Add new Application configuration
   - Set Main class to `com.acu.javafx.database.EmployeeDatabaseApp`
   - Set VM options: `--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml`

##### Eclipse
1. **Install e(fx)clipse Plugin**
   - Go to `Help → Eclipse Marketplace`
   - Search for "e(fx)clipse" and install

2. **Import Maven Project**
   - Go to `File → Import → Maven → Existing Maven Projects`
   - Select the project directory

3. **Configure JavaFX**
   - Right-click project → Properties
   - JavaFX → Configure JavaFX
   - Set JavaFX SDK path

### Environment Variables

Set the following environment variables:

```bash
# Unix/Linux/macOS
export JAVA_HOME=/path/to/jdk-21
export PATH=$JAVA_HOME/bin:$PATH

# Windows
set JAVA_HOME=C:\path\to\jdk-21
set PATH=%JAVA_HOME%\bin;%PATH%
```

### Verify Installation

Run these commands to verify your setup:

```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Test project build
mvn clean compile

# Run tests
mvn test
```

## Project Structure

### Directory Layout
```
34-01-Database/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/acu/javafx/database/
│   │   │       ├── EmployeeDatabaseApp.java    # Main application
│   │   │       ├── DatabaseManager.java        # Database operations
│   │   │       ├── Employee.java               # Data model
│   │   │       └── DatabaseTest.java           # Test utilities
│   │   └── resources/                          # Application resources
│   └── test/
│       └── java/
│           └── com/acu/javafx/database/
│               ├── EmployeeTest.java           # Model tests
│               ├── DatabaseManagerTest.java    # Database tests
│               └── IntegrationTest.java        # Integration tests
├── docs/                                       # Documentation
├── target/                                     # Build output
├── pom.xml                                     # Maven configuration
├── Makefile                                    # Build automation
├── run.sh                                      # Unix run script
└── run.bat                                     # Windows run script
```

### Package Organization

#### Main Package: `com.acu.javafx.database`
- **Application Layer**: `EmployeeDatabaseApp.java`
- **Data Access Layer**: `DatabaseManager.java`
- **Model Layer**: `Employee.java`
- **Test Utilities**: `DatabaseTest.java`

#### Test Package: `com.acu.javafx.database`
- **Unit Tests**: Individual component tests
- **Integration Tests**: End-to-end functionality tests

## Coding Standards

### Java Code Style

#### Naming Conventions
```java
// Classes: PascalCase
public class EmployeeDatabaseApp { }

// Methods: camelCase
public void addEmployee() { }

// Variables: camelCase
private String employeeName;

// Constants: UPPER_SNAKE_CASE
private static final String DB_URL = "jdbc:sqlite:employee.sqlite";

// Packages: lowercase
package com.acu.javafx.database;
```

#### Code Formatting
```java
// Indentation: 4 spaces (no tabs)
public class Example {
    private String field;
    
    public void method() {
        if (condition) {
            // code here
        }
    }
}

// Line length: Maximum 120 characters
// Line breaks: Break at logical points
String longString = "This is a very long string that should be " +
                   "broken across multiple lines for readability";
```

#### Documentation Standards

##### JavaDoc Comments
```java
/**
 * Represents an employee in the system.
 * 
 * <p>This class provides JavaFX properties for reactive UI binding
 * and includes validation for database operations.</p>
 * 
 * @author Developer Name
 * @version 1.0
 * @since 2024-01-01
 */
public class Employee {
    
    /**
     * Creates a new employee with default values.
     * 
     * <p>The default values are:</p>
     * <ul>
     *   <li>id: 0</li>
     *   <li>name: null</li>
     *   <li>email: null</li>
     *   <li>department: null</li>
     *   <li>salary: 0.0</li>
     * </ul>
     */
    public Employee() {
        // implementation
    }
    
    /**
     * Gets the employee's name.
     * 
     * @return the employee name, or null if not set
     */
    public String getName() {
        return name.get();
    }
    
    /**
     * Sets the employee's name.
     * 
     * @param name the new name, cannot be null for database operations
     * @throws IllegalArgumentException if name is null or empty
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name.set(name);
    }
}
```

##### Inline Comments
```java
// Use comments to explain complex logic
// Avoid obvious comments
String name = employee.getName(); // Get employee name - BAD
String name = employee.getName(); // Extract name for validation - GOOD

// Use TODO comments for future work
// TODO: Add email validation regex
public void setEmail(String email) {
    this.email.set(email);
}
```

### Error Handling

#### Exception Handling Patterns
```java
// Database operations
public static boolean insertEmployee(Employee employee) {
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        // Set parameters
        pstmt.setString(1, employee.getName());
        
        int affectedRows = pstmt.executeUpdate();
        return affectedRows > 0;
        
    } catch (SQLException e) {
        // Log error and return false
        System.err.println("Error inserting employee: " + e.getMessage());
        return false;
    }
}

// UI operations
private void addEmployee() {
    try {
        // Validate input
        if (nameField.getText().trim().isEmpty()) {
            showAlert("Error", "Name is required!");
            return;
        }
        
        // Parse numeric input
        double salary = Double.parseDouble(salaryField.getText().trim());
        
        // Process data
        Employee employee = new Employee();
        employee.setName(nameField.getText().trim());
        employee.setSalary(salary);
        
        if (DatabaseManager.insertEmployee(employee)) {
            showAlert("Success", "Employee added successfully!");
            refreshEmployeeList();
        } else {
            showAlert("Error", "Failed to add employee!");
        }
        
    } catch (NumberFormatException ex) {
        showAlert("Error", "Please enter a valid salary amount!");
    }
}
```

## Database Development

### SQLite Best Practices

#### Connection Management
```java
// Always use try-with-resources
try (Connection conn = DriverManager.getConnection(DB_URL);
     PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
    // Database operations here
    
} catch (SQLException e) {
    // Handle exceptions
}
```

#### Prepared Statements
```java
// Use prepared statements to prevent SQL injection
String sql = "INSERT INTO employees (name, email, department, salary) VALUES (?, ?, ?, ?)";

try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    pstmt.setString(1, employee.getName());
    pstmt.setString(2, employee.getEmail());
    pstmt.setString(3, employee.getDepartment());
    pstmt.setDouble(4, employee.getSalary());
    
    int affectedRows = pstmt.executeUpdate();
    return affectedRows > 0;
}
```

#### Transaction Management
```java
// For operations that modify multiple records
public static boolean updateMultipleEmployees(List<Employee> employees) {
    try (Connection conn = DriverManager.getConnection(DB_URL)) {
        conn.setAutoCommit(false);
        
        try {
            for (Employee emp : employees) {
                if (!updateEmployee(emp, conn)) {
                    conn.rollback();
                    return false;
                }
            }
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
        
    } catch (SQLException e) {
        System.err.println("Error updating employees: " + e.getMessage());
        return false;
    }
}
```

### Database Schema Management

#### Version Control
```sql
-- Always version your schema changes
-- Create migration scripts for schema updates

-- Version 1.0
CREATE TABLE IF NOT EXISTS employees (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT UNIQUE,
    department TEXT,
    salary REAL DEFAULT 0.0
);

-- Version 1.1 (future)
ALTER TABLE employees ADD COLUMN hire_date TEXT;
```

#### Data Validation
```java
// Validate data before database operations
public static boolean validateEmployee(Employee employee) {
    if (employee == null) {
        return false;
    }
    
    // Required fields
    if (employee.getName() == null || employee.getName().trim().isEmpty()) {
        return false;
    }
    
    // Email format validation
    if (employee.getEmail() != null && !employee.getEmail().contains("@")) {
        return false;
    }
    
    // Salary validation
    if (employee.getSalary() < 0) {
        return false;
    }
    
    return true;
}
```

## UI Development

### JavaFX Best Practices

#### Property Binding
```java
// Use property binding for reactive UI
public class EmployeeController {
    private Employee employee;
    private TextField nameField;
    private Label nameLabel;
    
    public void initialize() {
        // Bidirectional binding
        nameField.textProperty().bindBidirectional(employee.nameProperty());
        
        // Unidirectional binding
        nameLabel.textProperty().bind(employee.nameProperty());
        
        // Conditional binding
        nameLabel.visibleProperty().bind(
            employee.nameProperty().isNotNull().and(
                employee.nameProperty().isNotEmpty()
            )
        );
    }
}
```

#### Event Handling
```java
// Use lambda expressions for event handlers
Button addButton = new Button("Add Employee");
addButton.setOnAction(event -> addEmployee());

// For complex event handling, use separate methods
addButton.setOnAction(this::handleAddEmployee);

private void handleAddEmployee(ActionEvent event) {
    // Complex logic here
}
```

#### UI Thread Management
```java
// Always update UI on JavaFX Application Thread
Platform.runLater(() -> {
    tableView.getItems().clear();
    tableView.getItems().addAll(employees);
});

// For background operations
Task<List<Employee>> task = new Task<>() {
    @Override
    protected List<Employee> call() throws Exception {
        return DatabaseManager.getAllEmployees();
    }
};

task.setOnSucceeded(event -> {
    List<Employee> employees = task.getValue();
    tableView.getItems().setAll(employees);
});

new Thread(task).start();
```

### Responsive Design

#### Layout Management
```java
// Use responsive layouts
VBox mainLayout = new VBox(20);
mainLayout.setPadding(new Insets(20));
mainLayout.setAlignment(Pos.TOP_CENTER);

// Use HBox for horizontal layouts
HBox buttonBox = new HBox(10);
buttonBox.setAlignment(Pos.CENTER);

// Use GridPane for form layouts
GridPane formGrid = new GridPane();
formGrid.setHgap(10);
formGrid.setVgap(10);
formGrid.setPadding(new Insets(10));
```

#### Styling
```java
// Use CSS for styling
scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

// CSS file (styles.css)
.button {
    -fx-background-color: #4CAF50;
    -fx-text-fill: white;
    -fx-font-size: 14px;
    -fx-padding: 8px 16px;
}

.button:hover {
    -fx-background-color: #45a049;
}

.table-view {
    -fx-background-color: white;
    -fx-border-color: #ddd;
}
```

## Testing Guidelines

### Unit Testing

#### Test Structure
```java
@DisplayName("Employee Model Tests")
class EmployeeTest {
    
    private Employee employee;
    
    @BeforeEach
    void setUp() {
        employee = new Employee();
    }
    
    @Test
    @DisplayName("Should create employee with default values")
    void testDefaultConstructor() {
        // Given: Employee created with default constructor
        
        // When: No additional setup
        
        // Then: Default values should be set
        assertEquals(0, employee.getId());
        assertNull(employee.getName());
        assertEquals(0.0, employee.getSalary());
    }
    
    @Test
    @DisplayName("Should set and get name correctly")
    void testNameProperty() {
        // Given: Employee instance
        
        // When: Setting name
        String expectedName = "John Doe";
        employee.setName(expectedName);
        
        // Then: Name should be set correctly
        assertEquals(expectedName, employee.getName());
        assertEquals(expectedName, employee.nameProperty().get());
    }
}
```

#### Test Naming Conventions
```java
// Use descriptive test names
@Test
void testInsertEmployee_WithValidData_ShouldReturnTrue() { }

@Test
void testInsertEmployee_WithNullName_ShouldReturnFalse() { }

@Test
void testSearchEmployees_WithEmptyTerm_ShouldReturnAllEmployees() { }
```

#### Mocking
```java
@ExtendWith(MockitoExtension.class)
class DatabaseManagerTest {
    
    @Mock
    private Connection mockConnection;
    
    @Mock
    private PreparedStatement mockStatement;
    
    @Test
    void testInsertEmployee_WithMockDatabase() throws SQLException {
        // Given
        Employee employee = new Employee(0, "John", "john@test.com", "IT", 50000.0);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        
        // When
        boolean result = DatabaseManager.insertEmployee(employee);
        
        // Then
        assertTrue(result);
        verify(mockStatement).setString(1, "John");
        verify(mockStatement).executeUpdate();
    }
}
```

### Integration Testing

#### Database Integration Tests
```java
@Testcontainers
class DatabaseIntegrationTest {
    
    @Container
    static SQLiteContainer<?> sqlite = new SQLiteContainer<>("sqlite:3.45.1.0");
    
    @Test
    void testFullEmployeeLifecycle() {
        // Given
        Employee employee = new Employee(0, "Test User", "test@example.com", "Test", 50000.0);
        
        // When: Insert
        boolean insertResult = DatabaseManager.insertEmployee(employee);
        
        // Then: Should be successful
        assertTrue(insertResult);
        
        // When: Retrieve
        List<Employee> employees = DatabaseManager.getAllEmployees();
        
        // Then: Should contain the employee
        assertFalse(employees.isEmpty());
        Employee retrieved = employees.get(0);
        assertEquals("Test User", retrieved.getName());
        
        // When: Update
        retrieved.setSalary(60000.0);
        boolean updateResult = DatabaseManager.updateEmployee(retrieved);
        
        // Then: Should be successful
        assertTrue(updateResult);
        
        // When: Delete
        boolean deleteResult = DatabaseManager.deleteEmployee(retrieved.getId());
        
        // Then: Should be successful
        assertTrue(deleteResult);
    }
}
```

### Test Coverage

#### Coverage Goals
- **Line Coverage**: >90%
- **Branch Coverage**: >80%
- **Method Coverage**: >95%

#### Coverage Reporting
```xml
<!-- Add to pom.xml -->
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
    </executions>
</plugin>
```

## Debugging

### Logging

#### Logging Configuration
```java
import java.util.logging.Logger;
import java.util.logging.Level;

public class DatabaseManager {
    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());
    
    public static boolean insertEmployee(Employee employee) {
        LOGGER.info("Attempting to insert employee: " + employee.getName());
        
        try {
            // Database operation
            boolean result = performInsert(employee);
            
            if (result) {
                LOGGER.info("Successfully inserted employee: " + employee.getName());
            } else {
                LOGGER.warning("Failed to insert employee: " + employee.getName());
            }
            
            return result;
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error inserting employee: " + employee.getName(), e);
            return false;
        }
    }
}
```

#### Debug Mode
```java
public class DebugConfig {
    public static final boolean DEBUG_MODE = true;
    
    public static void debugLog(String message) {
        if (DEBUG_MODE) {
            System.out.println("[DEBUG] " + message);
        }
    }
}
```

### Common Debugging Scenarios

#### Database Connection Issues
```java
// Check database file permissions
File dbFile = new File("employee.sqlite");
if (!dbFile.exists()) {
    System.err.println("Database file does not exist");
} else if (!dbFile.canRead()) {
    System.err.println("Cannot read database file");
} else if (!dbFile.canWrite()) {
    System.err.println("Cannot write to database file");
}
```

#### UI Thread Issues
```java
// Check if running on JavaFX thread
if (Platform.isFxApplicationThread()) {
    // Safe to update UI
    updateUI();
} else {
    // Schedule on JavaFX thread
    Platform.runLater(this::updateUI);
}
```

#### Memory Leaks
```java
// Properly dispose of resources
@Override
public void stop() {
    // Clean up resources
    if (tableView != null) {
        tableView.getItems().clear();
    }
    
    // Close database connections
    // (if using connection pooling)
    
    super.stop();
}
```

## Performance Considerations

### Database Performance

#### Connection Pooling
```java
// For production applications, use connection pooling
public class ConnectionPool {
    private static final int MAX_POOL_SIZE = 10;
    private static final Queue<Connection> connectionPool = new ConcurrentLinkedQueue<>();
    
    public static Connection getConnection() throws SQLException {
        Connection connection = connectionPool.poll();
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }
    
    public static void returnConnection(Connection connection) {
        if (connection != null && !connection.isClosed()) {
            connectionPool.offer(connection);
        }
    }
}
```

#### Query Optimization
```java
// Use indexes for frequently searched columns
public static void createIndexes() {
    String createNameIndex = "CREATE INDEX IF NOT EXISTS idx_employees_name ON employees(name)";
    String createEmailIndex = "CREATE INDEX IF NOT EXISTS idx_employees_email ON employees(email)";
    
    try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement()) {
        stmt.execute(createNameIndex);
        stmt.execute(createEmailIndex);
    } catch (SQLException e) {
        System.err.println("Error creating indexes: " + e.getMessage());
    }
}
```

### UI Performance

#### Lazy Loading
```java
// Load data in background
private void loadEmployeesAsync() {
    Task<List<Employee>> task = new Task<>() {
        @Override
        protected List<Employee> call() throws Exception {
            updateMessage("Loading employees...");
            return DatabaseManager.getAllEmployees();
        }
    };
    
    task.setOnSucceeded(event -> {
        List<Employee> employees = task.getValue();
        tableView.getItems().setAll(employees);
    });
    
    new Thread(task).start();
}
```

#### Virtual Scrolling
```java
// For large datasets, use virtual scrolling
TableView<Employee> tableView = new TableView<>();
tableView.setFixedCellSize(25); // Fixed row height for better performance
```

## Security Best Practices

### Input Validation

#### SQL Injection Prevention
```java
// Always use prepared statements (already implemented)
String sql = "SELECT * FROM employees WHERE name LIKE ?";
try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    pstmt.setString(1, "%" + searchTerm + "%");
    // Safe from SQL injection
}
```

#### Input Sanitization
```java
public static String sanitizeInput(String input) {
    if (input == null) {
        return null;
    }
    
    // Remove potentially dangerous characters
    return input.replaceAll("[<>\"']", "");
}

public static boolean isValidEmail(String email) {
    if (email == null) {
        return false;
    }
    
    // Basic email validation
    String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
    return email.matches(emailRegex);
}
```

### Data Protection

#### Sensitive Data Handling
```java
// Don't log sensitive information
public static boolean insertEmployee(Employee employee) {
    LOGGER.info("Inserting employee: " + employee.getName()); // OK
    LOGGER.info("Inserting employee with email: " + employee.getEmail()); // Avoid in production
    
    // Use placeholder for sensitive data
    LOGGER.info("Inserting employee: " + employee.getName() + " (email: ***)");
}
```

## Deployment

### Build Configuration

#### Maven Assembly
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-assembly-plugin</artifactId>
    <version>3.6.0</version>
    <configuration>
        <descriptorRefs>
            <descriptorRef>jar-with-dependencies</descriptorRef>
        </descriptorRefs>
        <archive>
            <manifest>
                <mainClass>com.acu.javafx.database.EmployeeDatabaseApp</mainClass>
            </manifest>
        </archive>
    </configuration>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>single</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

#### JLink for Custom Runtime
```bash
# Create custom runtime image
jlink --module-path $JAVA_HOME/jmods --add-modules java.base,java.sql,javafx.controls,javafx.fxml --output custom-runtime

# Run application with custom runtime
./custom-runtime/bin/java -jar target/JavaFX-Database-1.0.jar
```

### Distribution

#### Create Distribution Package
```bash
# Build fat JAR
mvn clean package

# Create distribution directory
mkdir dist
cp target/JavaFX-Database-1.0-fat.jar dist/
cp run.sh dist/
cp run.bat dist/
cp README.md dist/

# Create ZIP archive
zip -r JavaFX-Database-1.0.zip dist/
```

#### Installation Script
```bash
#!/bin/bash
# install.sh

echo "Installing JavaFX Employee Database Management System..."

# Check Java installation
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed"
    exit 1
fi

# Create application directory
sudo mkdir -p /opt/employeedb
sudo cp JavaFX-Database-1.0-fat.jar /opt/employeedb/
sudo cp run.sh /opt/employeedb/

# Make executable
sudo chmod +x /opt/employeedb/run.sh

# Create desktop shortcut
cat > ~/Desktop/EmployeeDB.desktop << EOF
[Desktop Entry]
Version=1.0
Type=Application
Name=Employee Database
Comment=Employee Database Management System
Exec=/opt/employeedb/run.sh
Icon=applications-office
Terminal=false
Categories=Office;
EOF

chmod +x ~/Desktop/EmployeeDB.desktop

echo "Installation complete!"
```

---

This development guide should be updated as the project evolves. For specific questions or clarifications, refer to the API documentation or contact the development team.
