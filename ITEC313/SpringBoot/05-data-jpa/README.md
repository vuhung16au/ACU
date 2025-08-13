# TODO

Add a Postgres database and make it work with the application.
```
mvn spring-boot:run
```


# 05-data-jpa — Spring Boot Data JPA and Database Integration

A comprehensive Spring Boot application demonstrating JPA (Java Persistence API) with database integration, entity relationships, repository patterns, and database migrations.

## Overview

This project showcases enterprise-level data persistence with Spring Boot:
- **JPA/Hibernate Integration**: Complete ORM (Object-Relational Mapping) setup
- **Database Integration**: H2 (development) and MySQL (production) support
- **Entity Relationships**: One-to-many, many-to-one, and complex associations
- **Repository Pattern**: Spring Data JPA with custom query methods
- **Database Migrations**: Flyway for schema versioning and management
- **Transaction Management**: ACID compliance with @Transactional
- **Auditing**: Automatic timestamp management with @CreatedDate and @LastModifiedDate

## Features

### JPA and Database Features
- ✅ Complete JPA entity model with relationships
- ✅ Spring Data JPA repositories with custom query methods
- ✅ Database migrations with Flyway
- ✅ Profile-based database configuration (H2 dev, MySQL prod)
- ✅ Transaction management with @Transactional
- ✅ Entity auditing with automatic timestamps
- ✅ Pagination and sorting support
- ✅ Custom JPQL and native SQL queries

### Entity Relationships
- ✅ Customer (1) → (N) Order (one-to-many)
- ✅ Order (N) → (1) Customer (many-to-one)
- ✅ Order (1) → (N) OrderItem (one-to-many)
- ✅ OrderItem (N) → (1) Product (many-to-one)
- ✅ Cascade operations and orphan removal

### Repository Features
- ✅ Basic CRUD operations
- ✅ Custom query methods (findBy, countBy, etc.)
- ✅ JPQL queries with @Query annotation
- ✅ Native SQL queries
- ✅ Aggregation queries (COUNT, AVG, SUM)
- ✅ Complex joins and relationships
- ✅ Pagination and sorting

### Database Features
- ✅ H2 in-memory database for development
- ✅ MySQL support for production
- ✅ Flyway migrations for schema management
- ✅ Indexes for performance optimization
- ✅ Foreign key constraints
- ✅ Data validation with Bean Validation

## Database Schema

### Entities and Relationships

```
Customer (1) ←→ (N) Order (1) ←→ (N) OrderItem (N) ←→ (1) Product
```

#### Customer Entity
- **Fields**: id, firstName, lastName, email, phone, status, createdAt, updatedAt
- **Relationships**: One-to-many with Order
- **Validation**: Email uniqueness, required fields

#### Order Entity
- **Fields**: id, orderNumber, customer, status, totalAmount, notes, createdAt, updatedAt
- **Relationships**: Many-to-one with Customer, one-to-many with OrderItem
- **Features**: Calculated total amount, status tracking

#### OrderItem Entity
- **Fields**: id, order, product, productName, quantity, price, notes, createdAt, updatedAt
- **Relationships**: Many-to-one with Order and Product
- **Features**: Subtotal calculation, product name caching

#### Product Entity
- **Fields**: id, name, description, price, category, stockQuantity, sku, imageUrl, isActive, createdAt, updatedAt
- **Features**: Stock management, category classification

## API Endpoints

### Customer Management (`/api/customers`)

#### Core Operations
- `GET /api/customers` - Get all customers with pagination
- `GET /api/customers/{id}` - Get customer by ID
- `GET /api/customers/email/{email}` - Get customer by email
- `POST /api/customers` - Create a new customer
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer

#### Advanced Operations
- `GET /api/customers/status/{status}` - Get customers by status
- `GET /api/customers/search?firstName={name}&lastName={name}` - Search customers by name
- `GET /api/customers/domain/{domain}` - Get customers by email domain
- `PATCH /api/customers/{id}/status?status={status}` - Update customer status
- `GET /api/customers/statistics` - Get customer statistics
- `GET /api/customers/statistics/average-orders` - Get average orders per customer

#### Query Parameters
- `page` - Page number (default: 0)
- `size` - Page size (default: 10)
- `sortBy` - Sort field (default: id)
- `sortDir` - Sort direction (asc/desc, default: asc)

### Database Console
- `GET /h2-console` - H2 Database Console (development only)

### Monitoring
- `GET /actuator/health` - Application health
- `GET /actuator/info` - Application information
- `GET /actuator/metrics` - Application metrics

## Prerequisites

- Java 17 or higher
- Maven 3.9 or higher
- MySQL (optional, for production profile)
- Internet connection for dependencies

## Quick Start

### 1. Build the Application

```bash
cd 05-data-jpa
mvn clean compile
```

### 2. Run the Application

**Development (H2 in-memory database):**
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

**Production (MySQL):**
```bash
# First, set up MySQL database
mysql -u root -p -e "CREATE DATABASE datajpa_prod;"

# Then run with production profile
mvn spring-boot:run -Dspring.profiles.active=prod
```

### 3. Access the Application

The application will start on `http://localhost:8080`

**Key URLs:**
- **H2 Console**: http://localhost:8080/h2-console (dev profile only)
- **Health Check**: http://localhost:8080/actuator/health
- **Customer API**: http://localhost:8080/api/customers

### 4. Test the Application

**Automated testing:**
```bash
# Run the test script
./scripts/test_endpoints.sh

# Or with custom base URL
BASE_URL=http://localhost:8080 ./scripts/test_endpoints.sh
```

**Manual testing with curl:**
```bash
# Get all customers with pagination
curl "http://localhost:8080/api/customers?page=0&size=5"

# Create a customer
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "+1234567890",
    "status": "ACTIVE"
  }'

# Get customer by ID
curl http://localhost:8080/api/customers/1
```

### 5. Run Tests

```bash
mvn test
```

## Configuration

### Development Profile (H2)
- **Database**: H2 in-memory
- **Console**: Enabled at /h2-console
- **DDL**: create-drop (recreates schema on startup)
- **SQL Logging**: Enabled for debugging

### Production Profile (MySQL)
- **Database**: MySQL
- **DDL**: validate (validates schema)
- **SQL Logging**: Disabled for performance
- **Migrations**: Flyway enabled

### JPA Configuration
- **Hibernate**: Configured for both H2 and MySQL
- **Auditing**: Enabled with @EnableJpaAuditing
- **Validation**: Bean Validation enabled
- **Lazy Loading**: Configured for relationships

## Project Structure

```
05-data-jpa/
├── src/
│   ├── main/
│   │   ├── java/com/acu/datajpa/
│   │   │   ├── DataJpaApplication.java           # Main application class
│   │   │   ├── entity/
│   │   │   │   ├── Customer.java                 # Customer entity
│   │   │   │   ├── CustomerStatus.java           # Customer status enum
│   │   │   │   ├── Order.java                    # Order entity
│   │   │   │   ├── OrderStatus.java              # Order status enum
│   │   │   │   ├── OrderItem.java                # OrderItem entity
│   │   │   │   ├── Product.java                  # Product entity
│   │   │   │   └── ProductCategory.java          # Product category enum
│   │   │   ├── repository/
│   │   │   │   ├── CustomerRepository.java       # Customer repository
│   │   │   │   ├── OrderRepository.java          # Order repository
│   │   │   │   └── ProductRepository.java        # Product repository
│   │   │   ├── service/
│   │   │   │   └── CustomerService.java          # Customer service
│   │   │   ├── controller/
│   │   │   │   └── CustomerController.java       # Customer controller
│   │   │   └── exception/
│   │   │       └── ResourceNotFoundException.java # Exception handler
│   │   └── resources/
│   │       ├── application.yml                   # Main configuration
│   │       ├── application-dev.yml               # Development profile
│   │       ├── application-prod.yml              # Production profile
│   │       ├── data.sql                          # Sample data
│   │       └── db/migration/
│   │           └── V1__Create_tables.sql         # Flyway migration
│   └── test/
│       └── java/com/acu/datajpa/
│           └── DataJpaApplicationTests.java      # Application tests
├── scripts/
│   └── test_endpoints.sh                         # Endpoint testing script
├── pom.xml                                       # Maven configuration
└── README.md                                     # This file
```

## Key Spring Boot Concepts Demonstrated

### 1. JPA Entity Mapping
- **@Entity**: Marks classes as JPA entities
- **@Table**: Specifies table name and constraints
- **@Id**: Primary key identification
- **@GeneratedValue**: Auto-generation strategies
- **@Column**: Column mapping and constraints
- **@Enumerated**: Enum mapping strategies

### 2. Entity Relationships
- **@OneToMany**: One-to-many relationships
- **@ManyToOne**: Many-to-one relationships
- **@JoinColumn**: Foreign key specification
- **@MappedBy**: Relationship ownership
- **Cascade**: Cascade operations
- **FetchType**: Loading strategies (LAZY/EAGER)

### 3. Spring Data JPA
- **JpaRepository**: Base repository interface
- **Query Methods**: Method name-based queries
- **@Query**: Custom JPQL queries
- **@Param**: Parameter binding
- **Pageable**: Pagination support
- **Sort**: Sorting capabilities

### 4. Transaction Management
- **@Transactional**: Transaction boundaries
- **@Transactional(readOnly = true)**: Read-only transactions
- **ACID Properties**: Atomicity, Consistency, Isolation, Durability
- **Rollback**: Automatic rollback on exceptions

### 5. Auditing
- **@CreatedDate**: Automatic creation timestamp
- **@LastModifiedDate**: Automatic update timestamp
- **@EntityListeners**: Auditing listener configuration
- **@EnableJpaAuditing**: Auditing configuration

### 6. Database Migration
- **Flyway**: Database migration tool
- **Migration Files**: Versioned SQL scripts
- **Baseline**: Migration baseline configuration
- **Validation**: Migration validation

## Learning Resources

### Official Documentation
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Hibernate ORM](https://hibernate.org/orm/)
- [Flyway Migrations](https://flywaydb.org/)
- [Spring Boot Data Access](https://docs.spring.io/spring-boot/docs/current/reference/html/data.html)

### Tutorials
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa)
- [Relational Data Access](https://spring.io/guides/gs/relational-data-access)
- [Accessing Data with MySQL](https://spring.io/guides/gs/accessing-data-mysql)

## Next Steps

After completing this JPA tutorial, you can explore:

1. **Day 6**: Kafka messaging, email, and scheduling
2. **Day 7**: Microservices and testing strategies

## Troubleshooting

### Common Issues

1. **H2 Console not accessible**: Ensure dev profile is active
2. **Migration failures**: Check Flyway configuration and SQL syntax
3. **Lazy loading exceptions**: Use @Transactional or fetch joins
4. **Database connection issues**: Verify database credentials and connectivity

### Development Tips

1. **Database Inspection**: Use H2 console to inspect data and schema
2. **SQL Logging**: Enable SQL logging in dev profile for debugging
3. **Migration Testing**: Test migrations on clean database
4. **Performance**: Monitor query performance with SQL logging

### Logs

Check the console output for:
- Hibernate SQL queries
- Flyway migration logs
- JPA entity lifecycle events
- Transaction boundaries
