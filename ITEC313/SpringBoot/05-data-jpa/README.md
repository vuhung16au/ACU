# 05-data-jpa — Spring Boot Data JPA and Database Integration

A comprehensive Spring Boot application demonstrating JPA (Java Persistence API) with database integration, entity relationships, repository patterns, and database migrations.

## Overview

This project showcases enterprise-level data persistence with Spring Boot:
- **JPA/Hibernate Integration**: Complete ORM (Object-Relational Mapping) setup
- **Database Integration**: PostgreSQL database support
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
- ✅ PostgreSQL database configuration
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
- ✅ PostgreSQL database
- ✅ Flyway migrations for schema management
- ✅ Indexes for performance optimization
- ✅ Foreign key constraints
- ✅ Data validation with Bean Validation
- ✅ PostgreSQL triggers for automatic timestamp updates

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
  - **Database**: PostgreSQL (console connects to PostgreSQL)

### Monitoring
- `GET /actuator/health` - Application health
- `GET /actuator/info` - Application information
- `GET /actuator/metrics` - Application metrics

## Prerequisites

- Java 17 or higher
- Maven 3.9 or higher
- PostgreSQL (via Docker Compose)
- Internet connection for dependencies

## Quick Start

### 1. Build the Application

```bash
cd 05-data-jpa
mvn clean compile
```

### 2. Run the Application

```bash
# First, start PostgreSQL using Docker Compose
cd postgresql-pgadmin
docker-compose up -d

# Then run the application
cd ..
mvn spring-boot:run
```

### 3. Access the Application

The application will start on `http://localhost:8080`

**Key URLs:**
- **pgAdmin**: http://localhost:5050 (email: 313@acu.com, password: postgres)
- **H2 Console**: http://localhost:8080/h2-console
  - **Database**: PostgreSQL
  - **JDBC URL**: `jdbc:postgresql://localhost:5432/postgres`
  - **Username**: `postgres`, **Password**: `postgres`
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

### Database Configuration
- **Database**: PostgreSQL
- **DDL**: validate (validates schema)
- **SQL Logging**: Enabled for debugging
- **Migrations**: Flyway enabled

### JPA Configuration
- **Hibernate**: Configured for PostgreSQL
- **Auditing**: Enabled with @EnableJpaAuditing
- **Validation**: Bean Validation enabled
- **Lazy Loading**: Configured for relationships

## PostgreSQL Migration

This project has been migrated from MySQL to PostgreSQL for improved performance, reliability, and advanced features.

### Migration Highlights
- **Database Driver**: Switched from MySQL Connector/J to PostgreSQL JDBC driver
- **Flyway Support**: Updated to use Flyway PostgreSQL support
- **Schema Migration**: Converted MySQL-specific syntax to PostgreSQL compatibility
- **Triggers**: Implemented PostgreSQL triggers for automatic timestamp updates
- **Data Types**: Updated to use PostgreSQL-specific data types (BIGSERIAL, BOOLEAN)

### PostgreSQL Features
- **Automatic ID Generation**: Using `BIGSERIAL` for auto-incrementing primary keys
- **Timestamp Triggers**: PostgreSQL triggers automatically update `updated_at` columns
- **Advanced Indexing**: PostgreSQL's sophisticated indexing capabilities
- **ACID Compliance**: Full ACID transaction support
- **JSON Support**: Native JSON data type support (future enhancement)

For detailed migration information, see [POSTGRESQL_MIGRATION.md](POSTGRESQL_MIGRATION.md).

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
│   │       ├── data.sql                          # Sample data
│   │       └── db/migration/
│   │           └── V1__Create_tables.sql         # Flyway migration
│   └── test/
│       └── java/com/acu/datajpa/
│           └── DataJpaApplicationTests.java      # Application tests
├── postgresql-pgadmin/
│   ├── compose.yaml                              # PostgreSQL & pgAdmin setup
│   └── README.md                                 # PostgreSQL setup guide
├── scripts/
│   └── test_endpoints.sh                         # Endpoint testing script
├── pom.xml                                       # Maven configuration
├── POSTGRESQL_MIGRATION.md                       # Migration documentation
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
- [Flyway Migrations](https://www.red-gate.com/products/flyway/community/)

### Tutorials
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa)
- [Relational Data Access](https://spring.io/guides/gs/relational-data-access)

## Next Steps

After completing this JPA tutorial, you can explore:

1. **Day 6**: Kafka messaging, email, and scheduling
2. **Day 7**: Microservices and testing strategies
