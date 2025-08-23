# Spring Boot JPA with PostgreSQL

This project demonstrates how to use Spring Boot with Spring Data JPA to access a PostgreSQL database. It's based on the [Spring Boot Accessing Data with JPA guide](https://spring.io/guides/gs/accessing-data-jpa).

## Build Tool

This project uses **Maven** for dependency management and build automation. The project includes:

- ✅ `pom.xml` - Maven configuration file
- ✅ Maven wrapper files (`mvnw`, `mvnw.cmd`, `.mvn/` directory) for consistent builds
- ✅ Spring Boot Maven plugin for easy application management
- ✅ All build commands use Maven syntax

## Project Overview

This application demonstrates:
- Spring Boot with Spring Data JPA
- PostgreSQL database integration
- Entity mapping with JPA annotations
- Repository pattern implementation
- Custom query methods
- Database operations (CRUD)

## Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Maven (or use the included Maven wrapper)

## Database Setup

The project uses PostgreSQL running in Docker. The database configuration is defined in `docker-compose.yaml` at the project root.

### Starting the Database

```bash
# From the project root directory
docker-compose up -d
```

This will start:
- PostgreSQL database on port 5432
- pgAdmin web interface on port 8888

### Database Credentials

- **Database**: `spring_jpa_db`
- **Username**: `postgres`
- **Password**: `postgres`
- **Host**: `localhost`
- **Port**: `5432`

## Running the Application

### Build the Project

```bash
./mvnw clean compile
```

### Run the Application

```bash
./mvnw spring-boot:run
```

The application will:
1. Connect to the PostgreSQL database
2. Create the `customer` table automatically
3. Insert sample customer data
4. Demonstrate various database operations:
   - Finding all customers
   - Finding a customer by ID
   - Finding customers by last name

### Run Tests

```bash
./mvnw test
```

Tests use an in-memory H2 database for faster execution.

## Project Structure

```
50-03-Spring-JPA/
├── pom.xml                    # Maven configuration
├── mvnw                       # Maven wrapper (Unix)
├── mvnw.cmd                   # Maven wrapper (Windows)
├── .mvn/                      # Maven wrapper configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/accessingdatajpa/
│   │   │       ├── AccessingDataJpaApplication.java  # Main application class
│   │   │       ├── Customer.java                     # JPA entity
│   │   │       └── CustomerRepository.java           # Repository interface
│   │   └── resources/
│   │       └── application.properties               # Database configuration
│   └── test/
│       ├── java/
│       │   └── com/example/accessingdatajpa/
│       │       └── CustomerRepositoryTests.java     # Unit tests
│       └── resources/
│           └── application.properties               # Test configuration
├── docker-compose.yaml        # Database configuration
└── README.md                  # Project documentation
```

## Key Components

### Customer Entity (`Customer.java`)

```java
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    // ... getters, setters, constructors
}
```

### Customer Repository (`CustomerRepository.java`)

```java
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByLastName(String lastName);
    Customer findById(long id);
}
```

### Application Configuration

The application uses two different database configurations:

1. **Production** (`src/main/resources/application.properties`): PostgreSQL
2. **Test** (`src/test/resources/application.properties`): H2 in-memory

## Database Operations Demonstrated

When you run the application, it will:

1. **Save customers** to the database
2. **Find all customers** using `findAll()`
3. **Find a specific customer** by ID using `findById(1L)`
4. **Find customers by last name** using `findByLastName("Bauer")`

## Expected Output

When running the application, you should see output similar to:

```
Customers found with findAll():
-------------------------------
Customer[id=1, firstName='Jack', lastName='Bauer']
Customer[id=2, firstName='Chloe', lastName='O'Brian']
Customer[id=3, firstName='Kim', lastName='Bauer']
Customer[id=4, firstName='David', lastName='Palmer']
Customer[id=5, firstName='Michelle', lastName='Dessler']

Customer found with findById(1L):
--------------------------------
Customer[id=1, firstName='Jack', lastName='Bauer']

Customer found with findByLastName('Bauer'):
--------------------------------------------
Customer[id=1, firstName='Jack', lastName='Bauer']
Customer[id=3, firstName='Kim', lastName='Bauer']
```

## Database Management

You can access the PostgreSQL database using:

- **pgAdmin**: http://localhost:8888
  - Email: `313@acu.com`
  - Password: `password`

- **Direct connection**: Use any PostgreSQL client with the credentials above

### pgAdmin Configuration

When configuring the PostgreSQL server in pgAdmin, use these settings:

**For connections from within Docker network (if pgAdmin is also containerized):**
```
Host name/address: postgres (use the container name)
Port: 5432
Maintenance database: postgres
Username: postgres
Password: postgres
```

**For connections from your host machine:**
```
Host name/address: localhost
Port: 5432
Maintenance database: postgres
Username: postgres
Password: postgres
```

> **Note**: If you encounter connection issues, ensure the Docker containers are running with `docker-compose up -d` before attempting to connect.