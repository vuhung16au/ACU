# JavaFX Database Programming Demo

This project demonstrates how to integrate JavaFX with SQLite database for CRUD (Create, Read, Update, Delete) operations.

## Features

- **SQLite Database Integration**: Uses SQLite JDBC driver for database operations
- **CRUD Operations**: Complete Create, Read, Update, Delete functionality
- **JavaFX UI**: Modern JavaFX interface with TableView and form controls
- **Search Functionality**: Search employees by name
- **Data Validation**: Input validation and error handling
- **Sample Data**: Pre-populated with sample employees

## Sample Employees

The application comes with three sample employees:
- Vu Nguyen (Engineering, $75,000)
- John Doe (Marketing, $65,000)
- English Sydney (Sales, $70,000)

## Database Schema

```sql
CREATE TABLE employees (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT UNIQUE,
    department TEXT,
    salary REAL DEFAULT 0.0
);
```

## Project Structure

```
33-01-Database/
├── src/main/java/com/acu/javafx/database/
│   ├── Employee.java              # Employee model class
│   ├── DatabaseManager.java       # Database operations
│   ├── EmployeeDatabaseApp.java   # Main JavaFX application
│   └── module-info.java          # Module configuration
├── pom.xml                       # Maven configuration
├── run.sh                        # Unix/Linux run script
├── run.bat                       # Windows run script
└── README.md                     # This file
```

## Dependencies

- **JavaFX**: 24.0.2
- **SQLite JDBC**: 3.45.1.0
- **Java**: 21+

## How to Run

### Prerequisites
- Java 21 or higher
- Maven 3.6 or higher

### Running the Application

#### On Unix/Linux/macOS:
```bash
cd 33-01-Database
./run.sh
```

#### On Windows:
```cmd
cd 33-01-Database
run.bat
```

#### Using Maven directly:
```bash
cd 33-01-Database
mvn clean javafx:run
```

## Application Features

### 1. View Employees
- All employees are displayed in a table view
- Columns: ID, Name, Email, Department, Salary
- Salary is formatted with currency symbol

### 2. Add Employee
- Fill in the form fields (Name is required)
- Click "Add Employee" button
- Employee is added to the database and table

### 3. Update Employee
- Select an employee from the table
- Modify the form fields
- Click "Update Employee" button
- Changes are saved to the database

### 4. Delete Employee
- Select an employee from the table
- Click "Delete Employee" button
- Confirm deletion in the dialog
- Employee is removed from the database

### 5. Search Employees
- Enter a name in the search field
- Click "Search" button
- Results are filtered by name (partial match)
- Click "Clear" to show all employees

### 6. Form Management
- "Clear Form" button clears all form fields
- "Refresh List" button reloads data from database

## Database File

The application creates a `employee.sqlite` file in the project directory. This file contains:
- Database schema
- Sample employee data
- All CRUD operations are performed on this file

## Technical Details

### Database Operations
- **Connection Management**: Uses try-with-resources for proper connection handling
- **Prepared Statements**: Prevents SQL injection
- **Error Handling**: Comprehensive exception handling with user-friendly messages
- **Data Validation**: Input validation before database operations

### JavaFX Features
- **Property Binding**: Uses JavaFX properties for reactive UI updates
- **TableView**: Displays employee data in a sortable table
- **Form Controls**: Text fields for data input
- **Event Handling**: Button click events and table selection events
- **Alerts**: User-friendly confirmation and error dialogs

### Architecture
- **Model-View-Controller**: Clean separation of concerns
- **Database Abstraction**: DatabaseManager class handles all database operations
- **JavaFX Properties**: Reactive data binding between model and view

## Troubleshooting

### Common Issues

1. **JavaFX not found**: Ensure JavaFX is properly installed and configured
2. **SQLite driver not found**: Maven should automatically download the SQLite JDBC driver
3. **Permission denied**: Make sure run.sh has execute permissions (`chmod +x run.sh`)
4. **Database locked**: Close any other applications that might be using the database file

### Error Messages

- **"Name is required"**: Employee name field cannot be empty
- **"Please enter a valid salary amount"**: Salary must be a valid number
- **"Failed to add/update/delete employee"**: Database operation failed (check console for details)

## Learning Objectives

This project demonstrates:
1. JavaFX application development
2. SQLite database integration
3. CRUD operations with Java
4. JavaFX properties and data binding
5. Event handling in JavaFX
6. Form validation and error handling
7. Modern Java features (text blocks, pattern matching)

## Next Steps

Possible enhancements:
## Add more search criteria (department, salary range)

- Users can search by selecting one or more departments
- Users can search by selecting a salary range (min and max)
- Users can search by selecting a name (partial match)

## Implement data export (CSV, Excel)

## Add data visualization (charts, graphs)

