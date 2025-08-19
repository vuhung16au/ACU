# JavaFX Employee Database Management System

A comprehensive JavaFX application demonstrating CRUD operations with SQLite database integration. This project showcases modern Java development practices including JavaFX UI, database operations, unit testing, and Maven build management.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Quick Start](#quick-start)
- [Development Guide](#development-guide)
- [Testing](#testing)
- [Build & Deployment](#build--deployment)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)

## 🎯 Overview

This application provides a complete employee management system with a modern JavaFX user interface. It demonstrates:

- **CRUD Operations**: Create, Read, Update, Delete employee records
- **Database Integration**: SQLite database with JDBC
- **Modern UI**: JavaFX with responsive design
- **Data Validation**: Input validation and error handling
- **Search Functionality**: Real-time search by employee name
- **Comprehensive Testing**: Unit tests with JUnit 5 and Mockito

## ✨ Features

### Core Functionality
- ✅ Add new employees with validation
- ✅ View all employees in a sortable table
- ✅ Update existing employee information
- ✅ Delete employees with confirmation
- ✅ Search employees by name
- ✅ Clear form and refresh data

### User Interface
- 🎨 Modern JavaFX interface with responsive design
- 📊 Table view with sortable columns
- 🔍 Real-time search functionality
- 📝 Form-based data entry with validation
- ⚠️ User-friendly error messages and confirmations

### Data Management
- 💾 SQLite database for persistent storage
- 🔄 Automatic database initialization
- 📊 Sample data population
- 🛡️ SQL injection prevention with prepared statements

## 🛠 Technology Stack

| Component | Technology | Version |
|-----------|------------|---------|
| **Language** | Java | 21 |
| **UI Framework** | JavaFX | 24.0.2 |
| **Database** | SQLite | 3.45.1.0 |
| **Build Tool** | Maven | 3.x |
| **Testing** | JUnit 5 | 5.10.1 |
| **Mocking** | Mockito | 5.8.0 |
| **Test Containers** | TestContainers | 1.19.3 |

## 📁 Project Structure

```
34-01-Database/
├── src/
│   ├── main/java/com/acu/javafx/database/
│   │   ├── EmployeeDatabaseApp.java    # Main JavaFX application
│   │   ├── DatabaseManager.java        # Database operations
│   │   ├── Employee.java               # Employee model class
│   │   └── DatabaseTest.java           # Database testing utility
│   ├── main/resources/                 # Application resources
│   └── test/java/com/acu/javafx/database/
│       ├── EmployeeTest.java           # Employee model tests
│       ├── DatabaseManagerTest.java    # Database operation tests
│       └── IntegrationTest.java        # Integration tests
├── docs/                               # Documentation
├── pom.xml                             # Maven configuration
├── Makefile                            # Build automation
├── run.sh                              # Unix run script
└── run.bat                             # Windows run script
```

## 🚀 Quick Start

### Prerequisites
- Java 21 or higher
- Maven 3.6 or higher
- Git

### Installation & Running

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd 34-01-Database
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   # Using Maven
   mvn javafx:run
   
   # Using scripts
   ./run.sh          # Unix/Linux/macOS
   run.bat           # Windows
   
   # Using Makefile
   make run
   ```

4. **Run tests**
   ```bash
   mvn test
   ```

## 👨‍💻 Development Guide

### Setting Up Development Environment

1. **IDE Setup**
   - Recommended: IntelliJ IDEA or Eclipse with JavaFX support
   - Ensure Java 21 is configured as project SDK
   - Install Maven integration plugin

2. **Database Setup**
   - SQLite database is automatically created on first run
   - Database file: `employee.sqlite` (created in project root)
   - Sample data is automatically populated

3. **Code Style**
   - Follow Java naming conventions
   - Use meaningful variable and method names
   - Add comprehensive JavaDoc comments
   - Maintain consistent indentation (4 spaces)

### Development Workflow

1. **Feature Development**
   ```bash
   # Create feature branch
   git checkout -b feature/new-feature
   
   # Make changes and test
   mvn test
   
   # Commit changes
   git add .
   git commit -m "Add new feature"
   ```

2. **Testing**
   - Write unit tests for new functionality
   - Ensure all tests pass before committing
   - Run integration tests for database operations

3. **Code Review**
   - Review code for best practices
   - Check for potential security issues
   - Verify UI/UX consistency

## 🧪 Testing

### Test Structure
- **Unit Tests**: Test individual components in isolation
- **Integration Tests**: Test database operations and UI interactions
- **Test Coverage**: Aim for >80% code coverage

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=EmployeeTest

# Run with coverage report
mvn test jacoco:report
```

### Test Categories
- **Model Tests**: `EmployeeTest.java` - Tests Employee class properties and methods
- **Database Tests**: `DatabaseManagerTest.java` - Tests database operations
- **Integration Tests**: `IntegrationTest.java` - Tests end-to-end functionality

## 📦 Build & Deployment

### Building Executable JAR
```bash
# Create fat JAR with dependencies
mvn clean package

# JAR file location: target/JavaFX-Database-1.0-fat.jar
```

### Distribution
```bash
# Create distribution package
make package

# Run distributed application
java -jar target/JavaFX-Database-1.0-fat.jar
```

## 📚 API Documentation

### Core Classes

#### `EmployeeDatabaseApp`
Main JavaFX application class handling UI and user interactions.

**Key Methods:**
- `start(Stage primaryStage)`: Application entry point
- `addEmployee()`: Add new employee to database
- `updateEmployee()`: Update existing employee
- `deleteEmployee()`: Delete employee with confirmation
- `searchEmployees()`: Search employees by name

#### `DatabaseManager`
Static utility class for database operations.

**Key Methods:**
- `initializeDatabase()`: Create database and tables
- `insertEmployee(Employee employee)`: Insert new employee
- `getAllEmployees()`: Retrieve all employees
- `updateEmployee(Employee employee)`: Update employee
- `deleteEmployee(int id)`: Delete employee by ID
- `searchEmployeesByName(String searchTerm)`: Search by name

#### `Employee`
Model class representing an employee with JavaFX properties.

**Properties:**
- `id`: Employee ID (IntegerProperty)
- `name`: Employee name (StringProperty)
- `email`: Employee email (StringProperty)
- `department`: Department (StringProperty)
- `salary`: Salary amount (DoubleProperty)

## 🤝 Contributing

### Contribution Guidelines
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add/update tests
5. Ensure all tests pass
6. Submit a pull request

### Code Standards
- Follow Java coding conventions
- Add comprehensive comments
- Write unit tests for new features
- Update documentation as needed

## 📄 License

This project is part of the ACU JavaFX Database course materials.

## 🆘 Support

For questions or issues:
1. Check the [documentation](docs/)
2. Review existing issues
3. Create a new issue with detailed description

---

**Last Updated**: December 2024  
**Version**: 1.0  
**Java Version**: 21  
**JavaFX Version**: 24.0.2
