# Day 9 - Hibernate & ORM Basics

## Goals
- Understand ORM and Hibernate as a JPA provider; when and why to use ORM
- Map entities and relationships (`@OneToMany`, `@ManyToOne`, `@ManyToMany`), cascading, and fetch strategies
- Write queries via Spring Data derived methods, JPQL, and pagination/sorting
- Manage transactions and avoid common pitfalls (N+1 selects, LazyInitializationException)
- Optional: database migrations with Flyway

## Features
- Entity relationships (Author-Book, Book-Tag)
- Spring Data JPA repositories with derived queries
- JPQL queries and custom repository methods
- Pagination and sorting
- Lazy vs eager fetching demonstration
- N+1 problem solutions with @EntityGraph
- Database migrations with Flyway
- Profile-based database configuration

## Prerequisites
- Java 17+
- Maven 3.9+
- Docker and Docker Compose

## Quick Start

### 1. Build the Project
```bash
# Clean and compile the project
mvn clean compile

# Run tests
mvn test

# Package the application (creates JAR file)
mvn package
```

### 2. Start the Database
```bash
# Start PostgreSQL using Docker Compose
cd docker
docker-compose up postgres -d

# Verify database is running
docker-compose ps
```

### 3. Run the Application
```bash
# Option 1: Using Maven Spring Boot plugin
mvn spring-boot:run

# Option 2: Using the packaged JAR
java -jar target/hibernate-orm-0.0.1-SNAPSHOT.jar

# Option 3: Using Maven with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### 4. Test the Application
```bash
# Run the test script to verify all endpoints
chmod +x scripts/test_endpoints.sh
./scripts/test_endpoints.sh

# Or test manually using curl
curl http://localhost:8090/actuator/health
```

## Detailed Instructions

### Building the Project

#### Maven Commands
```bash
# Clean previous builds
mvn clean

# Compile source code
mvn compile

# Run unit tests
mvn test

# Package application (creates executable JAR)
mvn package

# Install to local Maven repository
mvn install

# Skip tests during build
mvn package -DskipTests

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

#### Build Output
- Compiled classes: `target/classes/`
- Test classes: `target/test-classes/`
- Executable JAR: `target/hibernate-orm-0.0.1-SNAPSHOT.jar`
- Test reports: `target/surefire-reports/`

### Running the Application

#### Database Setup
```bash
# Start PostgreSQL database
cd docker
docker-compose up postgres -d

# Optional: Start with pgAdmin for database management
docker-compose --profile tools up -d

# Check database status
docker-compose ps

# View database logs
docker-compose logs postgres
```

#### Application Startup
```bash
# Development mode (with hot reload)
mvn spring-boot:run

# Production mode
mvn spring-boot:run -Dspring-boot.run.profiles=prod

# With custom JVM options
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx512m -Xms256m"

# Using packaged JAR
java -jar target/hibernate-orm-0.0.1-SNAPSHOT.jar

# With custom properties
java -jar target/hibernate-orm-0.0.1-SNAPSHOT.jar --server.port=9090
```

#### Application URLs
- **REST API**: http://localhost:8090
- **Actuator Health**: http://localhost:8090/actuator/health
- **Actuator Info**: http://localhost:8090/actuator/info
- **Actuator Metrics**: http://localhost:8090/actuator/metrics
- **pgAdmin** (if using tools profile): http://localhost:8080

### Testing the Application

#### Automated Testing
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=AuthorServiceTest

# Run tests with coverage
mvn test jacoco:report

# Run integration tests
mvn verify

# Run tests in parallel
mvn test -Dparallel=methods -DthreadCount=4
```

#### Manual API Testing
```bash
# Make the test script executable
chmod +x scripts/test_endpoints.sh

# Run the comprehensive test script
./scripts/test_endpoints.sh

# Test individual endpoints manually
curl -X GET http://localhost:8090/api/authors
curl -X GET http://localhost:8090/api/books
curl -X GET http://localhost:8090/api/tags
```

#### Health Check
```bash
# Check application health
curl http://localhost:8090/actuator/health

# Check database connectivity
curl http://localhost:8090/actuator/health/db
```

#### Performance Testing
```bash
# Test with Apache Bench (if installed)
ab -n 1000 -c 10 http://localhost:8090/api/authors

# Test with curl timing
curl -w "@curl-format.txt" -o /dev/null -s http://localhost:8090/api/authors
```

### Development Tools

#### Database Management
```bash
# Start pgAdmin for visual database management
cd docker
docker-compose --profile tools up -d

# Access pgAdmin at http://localhost:8080
# Email: admin@example.com
# Password: admin
```

#### Hot Reload
The application includes Spring Boot DevTools for automatic restart during development:
```bash
# Enable hot reload (enabled by default in development)
mvn spring-boot:run

# Disable hot reload
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.devtools.restart.enabled=false"
```

### Troubleshooting

#### Common Issues

**Database Connection Issues**
```bash
# Check if PostgreSQL is running
docker-compose ps

# Restart PostgreSQL
docker-compose restart postgres

# Check database logs
docker-compose logs postgres
```

**Port Conflicts**
```bash
# Check what's using port 8090
lsof -i :8090

# Kill process using the port
kill -9 <PID>

# Or use a different port
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=9090"
```

**Memory Issues**
```bash
# Increase heap size
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx1g -Xms512m"
```

#### Logs and Debugging
```bash
# View application logs
tail -f logs/application.log

# Enable debug logging
mvn spring-boot:run -Dlogging.level.com.acu.hibernate=DEBUG

# Enable SQL logging
mvn spring-boot:run -Dlogging.level.org.hibernate.SQL=DEBUG
```

### Stopping Services
```bash
# Stop the application
# Press Ctrl+C in the terminal running the application

# Stop PostgreSQL
cd docker
docker-compose down

# Stop all services including pgAdmin
docker-compose --profile tools down

# Stop and remove volumes (WARNING: This will delete all data)
docker-compose down -v
```

## Entity Relationships

### Author (1) — Book (N)
- One author can have many books
- Each book belongs to one author
- Cascade operations for book management

### Book (N) — Tag (M)
- Many-to-many relationship
- Books can have multiple tags
- Tags can be associated with multiple books

## Endpoints

### Authors
- `GET /api/authors` - Get all authors with pagination
- `GET /api/authors/{id}` - Get author by ID
- `POST /api/authors` - Create new author
- `PUT /api/authors/{id}` - Update author
- `DELETE /api/authors/{id}` - Delete author
- `GET /api/authors/{id}/books` - Get author's books

### Books
- `GET /api/books` - Get all books with pagination
- `GET /api/books/{id}` - Get book by ID
- `POST /api/books` - Create new book
- `PUT /api/books/{id}` - Update book
- `DELETE /api/books/{id}` - Delete book
- `GET /api/books/search?title={title}` - Search books by title
- `GET /api/books/author/{authorId}` - Get books by author

### Tags
- `GET /api/tags` - Get all tags
- `GET /api/tags/{id}` - Get tag by ID
- `POST /api/tags` - Create new tag
- `GET /api/tags/{id}/books` - Get books with specific tag

## Query Examples

### Derived Queries
- `findByTitleContainingIgnoreCase`
- `findByAuthorName`
- `findByPublicationYearBetween`

### JPQL Queries
- `@Query("SELECT b FROM Book b WHERE b.title LIKE %:keyword%")`
- `@Query("SELECT a FROM Author a JOIN FETCH a.books")`

### Native Queries
- `@Query(value = "SELECT * FROM books WHERE publication_year > :year", nativeQuery = true)`

## Performance Features

### Fetch Strategies
- Lazy loading (default)
- Eager loading with @EntityGraph
- Fetch joins in JPQL

### N+1 Problem Solutions
- @EntityGraph for eager fetching
- JOIN FETCH in JPQL
- Batch fetching configuration

## Links
- [Hibernate Tutorial (GeeksforGeeks)](https://www.geeksforgeeks.org/java/hibernate-tutorial/)
- [Accessing Data with JPA (Guide)](https://spring.io/guides/gs/accessing-data-jpa)
- [Hibernate ORM Documentation](https://hibernate.org/orm/documentation/)
- [Spring Data JPA Reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
