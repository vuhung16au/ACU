# Spring Boot + Vaadin CRUD Application

A demonstration project showing how to build a CRUD (Create, Read, Update, Delete) application using Spring Boot and Vaadin.

## Technologies Used

- **Spring Boot 3.2.0** - Main application framework
- **Vaadin 24.3.0** - Web UI framework
- **Spring Data JPA** - Data access layer
- **PostgreSQL 17** - Primary database (via Docker)

- **Maven** - Build tool
- **Docker & Docker Compose** - Containerization for PostgreSQL and pgAdmin

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose (for PostgreSQL database setup)

## Quick Start

### 1. Start the Database

The application uses PostgreSQL as the default database. Start the PostgreSQL database using Docker Compose:

```bash
cd docker
docker-compose up -d
```

This will start:
- **PostgreSQL 17** on port 5432
- **pgAdmin** on port 8081 (for database management)

### 2. Build and Run the Application

```bash
# Clean and compile
mvn clean compile

# Run the application
mvn spring-boot:run
```

The application will be available at: http://localhost:8080

### 3. Run Tests

```bash
# Run all tests
mvn test

# Run tests with coverage
mvn test jacoco:report
```

## Database Access

### PostgreSQL (Default Database)

The application connects to PostgreSQL with these settings:
- **Host**: localhost
- **Port**: 5432
- **Database**: postgres
- **Username**: postgres
- **Password**: postgres

### pgAdmin (Database Management)

Access pgAdmin to manage the PostgreSQL database:
- **URL**: http://localhost:8081
- **Email**: 313@acu.com
- **Password**: password

To connect to the PostgreSQL server in pgAdmin:
- **Host**: postgres (or localhost)
- **Port**: 5432
- **Database**: postgres
- **Username**: postgres
- **Password**: postgres



## Project Structure

```
src/
├── main/
│   ├── java/com/acu/vaadin/
│   │   ├── CrudWithVaadinApplication.java    # Main application class
│   │   ├── Customer.java                     # JPA entity
│   │   ├── CustomerRepository.java           # Spring Data repository
│   │   ├── CustomerEditor.java               # Vaadin editor component
│   │   └── MainView.java                     # Main Vaadin view
│   └── resources/
│       ├── application.properties            # Main configuration
│       └── application-test.properties       # Test configuration
├── test/
│   └── java/com/acu/vaadin/
│       ├── CrudWithVaadinApplicationTests.java
│       ├── MainViewTests.java
│       └── CustomerEditorTests.java
docker/
└── docker-compose.yml                        # PostgreSQL and pgAdmin setup
```

## Features

- **Customer Management**: Add, edit, delete, and view customers
- **Data Validation**: Email validation and required field validation
- **Search/Filter**: Filter customers by last name
- **Responsive UI**: Modern Vaadin-based user interface
- **Database Integration**: Full CRUD operations with PostgreSQL
- **Test Coverage**: Comprehensive test suite


