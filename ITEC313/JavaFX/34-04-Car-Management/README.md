# Car Management System

A JavaFX-based desktop application for managing car inventory and components with PostgreSQL database persistence.

## Overview

The Car Management System is a comprehensive desktop application built with JavaFX that allows users to manage a fleet of cars and their associated components. The application provides a modern, intuitive interface for tracking car details, managing components, and maintaining inventory records.

### Key Features

- **User Authentication**: Secure login system with role-based access
- **Car Management**: Add, edit, delete, and search cars by make or model
- **Component Management**: Track car components with cost information
- **Database Persistence**: PostgreSQL database with automatic table creation

- **Real-time Search**: Filter cars by make or model in real-time
- **Editable Tables**: In-place editing of car and component information

## How to Use

### Login
1. Launch the application
2. Use the following credentials:
   - **Username**: `admin` | **Password**: `admin`
   - **Username**: `user` | **Password**: `user`
3. Click "OK" to proceed to the main application

### Adding a Car
1. Click the "+" button in the cars toolbar
2. Fill in the car details:
   - **Make**: Car manufacturer (e.g., Toyota, Honda)
   - **Model**: Car model (e.g., Camry, CR-V)
   - **Year**: Manufacturing year
   - **Color**: Car color
   - **Mileage**: Current mileage
   - **Category**: Car type (Sedan, SUV, Hatchback, etc.)
3. Click "Apply" to save the car

### Adding Components
1. Select a car from the cars table
2. Click the "+" button in the components toolbar
3. Fill in the component details:
   - **Component Code**: Unique identifier (auto-generated)
   - **Description**: Component description
   - **Cost**: Component cost in dollars
4. Click "Apply" to save the component

### Editing Data
- **Cars**: Double-click any cell in the cars table to edit in-place
- **Components**: Double-click any cell in the components table to edit in-place
- Changes are automatically saved to the database

### Searching
- Use the search field at the top to filter cars by make or model
- Search is performed in real-time as you type



### Removing Items
- **Remove Car**: Select a car and click the "-" button in the cars toolbar
- **Remove Component**: Select a component and click the "-" button in the components toolbar

## Database Setup with Docker Compose

### Prerequisites
- Docker and Docker Compose installed on your system

### Setup Steps
1. Navigate to the project directory
2. Start the PostgreSQL database:
   ```bash
   docker-compose up -d
   ```
3. The database will be available at:
   - **Host**: localhost
   - **Port**: 5432
   - **Database**: car_management
   - **Username**: postgres
   - **Password**: postgres

### Database Configuration
The application automatically creates the required tables on first run. The database connection is configured in `CarManagementApp.java`:

```java
private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/car_management?user=postgres&password=postgres&ssl=false";
```

## Database Structure

### Tables

#### `users` Table
| Column | Type | Description |
|--------|------|-------------|
| id | SERIAL | Primary key |
| username | VARCHAR(20) | User login name |
| password | VARCHAR(20) | Encrypted password |

#### `cars` Table
| Column | Type | Description |
|--------|------|-------------|
| id | SERIAL | Primary key |
| make | VARCHAR(50) | Car manufacturer |
| model | VARCHAR(50) | Car model |
| year | INTEGER | Manufacturing year |
| color | VARCHAR(50) | Car color |
| mileage | DOUBLE PRECISION | Current mileage |
| category | VARCHAR(50) | Car type (Sedan, SUV, etc.) |

#### `components` Table
| Column | Type | Description |
|--------|------|-------------|
| id | SERIAL | Primary key |
| carid | BIGINT | Foreign key to cars.id |
| componentcode | VARCHAR(50) | Component identifier |
| description | VARCHAR(50) | Component description |
| cost | DOUBLE PRECISION | Component cost |

### Relationships
- `components.carid` → `cars.id` (Many-to-One)
- Each car can have multiple components
- Components are automatically deleted when their associated car is deleted

## How to Compile and Run

### Prerequisites
- Java 21 or higher
- Maven 3.6 or higher
- PostgreSQL database (via Docker Compose)

### Build and Run
1. **Start the database**:
   ```bash
   docker-compose up -d
   ```

2. **Compile and run the application**:
   ```bash
   mvn clean compile javafx:run
   ```

### Alternative Commands
- **Compile only**: `mvn clean compile`
- **Run only**: `mvn javafx:run`
- **Package**: `mvn clean package`

## Dependencies

This project uses Maven for dependency management. All dependencies are defined in `pom.xml` and automatically downloaded during the build process.

### Core Dependencies

#### JavaFX Framework
- **javafx-controls (21)**: Core JavaFX UI controls and components
- **javafx-fxml (21)**: FXML support for declarative UI definition

#### Database & Persistence
- **postgresql (42.7.1)**: PostgreSQL JDBC driver for database connectivity
- **HikariCP (5.1.0)**: High-performance JDBC connection pool for optimal database performance

#### Logging
- **logback-classic (1.4.14)**: Logging framework with SLF4J integration for application logging

### Build Dependencies

#### Maven Plugins
- **maven-compiler-plugin (3.11.0)**: Java compilation with Java 21 support
- **javafx-maven-plugin (0.0.8)**: JavaFX application packaging and execution

### Dependency Details

#### JavaFX Dependencies
```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>21</version>
</dependency>
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-fxml</artifactId>
    <version>21</version>
</dependency>
```
- **Purpose**: Provides the core JavaFX UI framework and FXML support
- **Usage**: Used for creating the desktop application interface and loading FXML layouts

#### Database Dependencies
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.1</version>
</dependency>
<dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
    <version>5.1.0</version>
</dependency>
```
- **Purpose**: PostgreSQL connectivity and connection pooling
- **Usage**: Database operations and connection management

#### Logging Dependency
```xml
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.4.14</version>
</dependency>
```
- **Purpose**: Application logging and debugging
- **Usage**: Logging database operations, errors, and application events

### Technology Stack

#### Backend
- **Java 21**: Modern Java with latest features
- **JavaFX 21**: Desktop application framework
- **PostgreSQL**: Relational database for data persistence
- **HikariCP**: High-performance JDBC connection pool

#### Database & Persistence
- **PostgreSQL**: Primary database
- **HikariCP**: Connection pooling for optimal performance
- **JDBC**: Database connectivity
- **Custom Repository Pattern**: Data access layer

#### Frontend
- **JavaFX**: Modern UI framework
- **FXML**: Declarative UI definition
- **CSS**: Styling (if needed)
- **Scene Builder**: UI design tool (optional)

#### Build Tools
- **Maven**: Dependency management and build automation
- **JavaFX Maven Plugin**: JavaFX application packaging

## Project Structure

```
34-04-Car-Management/
├── docker-compose.yaml          # Database setup
├── pom.xml                      # Maven configuration
├── README.md                    # This file
├── src/
│   └── main/
│       ├── java/
│       │   ├── module-info.java # Java module definition
│       │   └── com/acu/car/management/
│       │       ├── CarManagementApp.java     # Main application class
│       │       ├── controller/  # UI controllers
│       │       │   ├── LoginController.java
│       │       │   ├── OverviewController.java
│       │       │   ├── AddCarDialog.java
│       │       │   └── AddComponentDialog.java
│       │       └── persistence/ # Data layer
│       │           ├── model/   # Domain models
│       │           │   ├── User.java
│       │           │   ├── Car.java
│       │           │   └── Component.java
│       │           └── repository/ # Data access
│       │               ├── Repository.java
│       │               ├── UserRepository.java
│       │               └── CarRepository.java
│       └── resources/
│           ├── cars.json        # Sample data
│           ├── logback.xml      # Logging configuration
│           └── com/acu/car/management/
│               └── controller/  # FXML files
│                   ├── login-view.fxml
│                   ├── cars-view.fxml
│                   ├── add-car-view.fxml
│                   └── add-component-view.fxml
└── target/                      # Compiled classes (generated)
```

## Sample Data

The application includes sample car data in `src/main/resources/cars.json`:

```json
[
  {
    "make": "Toyota",
    "model": "Camry",
    "year": 2020,
    "color": "Silver",
    "mileage": 25000.0,
    "category": "Sedan",
    "components": [
      {
        "componentCode": "ENG001",
        "description": "Engine Oil Filter",
        "cost": 25.0
      }
    ]
  }
]
```

## Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Ensure Docker Compose is running: `docker-compose up -d`
   - Check if PostgreSQL is accessible on port 5432

2. **Application Won't Start**
   - Verify Java 21 is installed: `java -version`
   - Check Maven installation: `mvn -version`
   - Ensure all dependencies are downloaded: `mvn clean compile`

3. **UI Not Loading**
   - Check FXML file paths in the controller classes
   - Verify all FXML files are in the correct resources directory

### Logs
The application uses Logback for logging. Check the console output for detailed error messages and database operation logs.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is part of the ACU JavaFX course materials.
