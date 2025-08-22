# Database JDBC Basic Project

This project demonstrates basic JDBC operations with PostgreSQL database using Docker Compose for easy setup and management. The project follows a modular architecture with clear separation of concerns.

## Prerequisites

- Docker
- Docker Compose
- Java 21 or higher
- Maven

## Database Setup with Docker Compose

This project uses Docker Compose to run PostgreSQL and pgAdmin containers. The configuration is defined in `docker-compose.yaml`.

### Database Configuration

- **PostgreSQL Database:**
  - Host: `localhost`
  - Port: `5432`
  - Database: `jdbc_basic`
  - Username: `postgres`
  - Password: `postgres`

- **pgAdmin:**
  - URL: `http://localhost:8888`
  - Email: `313@acu.com`
  - Password: `password`

## Docker Compose Commands

### Starting the Services

To start PostgreSQL and pgAdmin containers:

```bash
docker-compose up -d
```

The `-d` flag runs the containers in detached mode (background).

### Stopping the Services

To stop the running containers:

```bash
docker-compose down
```

This command stops and removes the containers but preserves the data volumes.

### Restarting the Services

To restart the services:

```bash
docker-compose restart
```

Or you can restart specific services:

```bash
docker-compose restart postgres
docker-compose restart pgadmin
```

### Removing Everything (Including Data)

To stop and remove containers, networks, and volumes (this will delete all data):

```bash
docker-compose down -v
```

**Warning:** The `-v` flag removes volumes, which means all database data will be lost.

### Viewing Logs

To view logs from all services:

```bash
docker-compose logs
```

To view logs from a specific service:

```bash
docker-compose logs postgres
docker-compose logs pgadmin
```

To follow logs in real-time:

```bash
docker-compose logs -f
```

### Checking Service Status

To see the status of all services:

```bash
docker-compose ps
```

## Accessing pgAdmin

### Opening pgAdmin in Browser

1. Start the services using `docker-compose up -d`
2. Open your web browser
3. Navigate to: `http://localhost:8888`
4. Login with:
   - **Email:** `313@acu.com`
   - **Password:** `password`

### Connecting to PostgreSQL Database in pgAdmin

1. In pgAdmin, right-click on "Servers" in the left panel
2. Select "Register" → "Server..."
3. In the "General" tab:
   - Name: `PostgreSQL` (or any name you prefer)
4. In the "Connection" tab:
   - Host name/address: `postgres` (use the service name from docker-compose)
   - Port: `5432`
   - Maintenance database: `jdbc_basic`
   - Username: `postgres`
   - Password: `postgres`
5. Click "Save"

**Note:** Use `postgres` as the host name (not `localhost`) when connecting from pgAdmin to PostgreSQL within the Docker network.

## Project Structure

The project follows a modular architecture with clear separation of concerns:

```
src/
├── main/
│   ├── java/
│   │   ├── module-info.java                    # Java module configuration
│   │   └── com/
│   │       └── acu/
│   │           └── jdbc/
│   │               └── basic/
│   │                   ├── JdbcDemoApplication.java      # Main application orchestrator
│   │                   ├── DatabaseConnectionManager.java # Database connection pool management
│   │                   ├── ProductRepository.java        # Product entity operations (CRUD, transactions)
│   │                   ├── JdbcResultSetExamples.java    # ResultSet type demonstrations
│   │                   └── DatabaseUtils.java            # Database utility constants and helpers
│   └── resources/
│       └── logback.xml                        # Logging configuration
├── docker-compose.yaml                        # Docker services configuration
├── pom.xml                                    # Maven project configuration
└── README.md                                  # This file
```

## Architecture Overview

### Class Responsibilities

- **`JdbcDemoApplication`**: Main application class that orchestrates all JDBC demonstrations
- **`DatabaseConnectionManager`**: Manages standard JDBC connection setup and lifecycle
- **`ProductRepository`**: Handles all product-related database operations (CRUD, transactions)
- **`JdbcResultSetExamples`**: Demonstrates various ResultSet types (scrollable, updatable, sensitive)
- **`DatabaseUtils`**: Contains database connection constants and utility methods

### Key Features Demonstrated

- **Database Connections**: Using standard JDBC for database connectivity
- **CRUD Operations**: Create, Read, Update operations on product entities
- **Transaction Management**: Demonstrating ACID properties with manual transaction control
- **ResultSet Types**: Scrollable, updatable, and sensitive ResultSet capabilities
- **Prepared Statements**: SQL injection prevention and performance optimization
- **Batch Operations**: Efficient bulk database operations
- **Resource Management**: Proper cleanup of database resources

## Running the Application

1. Start the database services:
   ```bash
   docker-compose up -d
   ```

2. Run the JavaFX application:
   ```bash
   mvn clean compile javafx:run
   ```

   Or alternatively:
   ```bash
   mvn clean javafx:run
   ```

## JDBC Demonstrations

The application demonstrates the following JDBC concepts:

1. **Database Connection Setup**: Standard JDBC connection configuration
2. **Table Creation**: Dynamic table creation with sample data
3. **Basic CRUD Operations**: 
   - Create: Insert product records
   - Read: Display all products
   - Update: Modify product prices
4. **Transaction Management**: Manual transaction control with commit/rollback
5. **ResultSet Types**:
   - Scrollable ResultSet navigation
   - Updatable ResultSet modifications
   - Sensitive ResultSet for real-time data changes
6. **Resource Cleanup**: Proper connection cleanup

## Dependencies

- **JavaFX**: UI framework for Java applications (required for module system and javafx:run command)
- **PostgreSQL JDBC Driver**: Database connectivity
- **SLF4J + Logback**: Logging framework
