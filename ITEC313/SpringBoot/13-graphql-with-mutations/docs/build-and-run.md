# Build & Run Guide

This guide covers how to build and run the Spring Boot GraphQL application.

## Prerequisites

- Java 17
- Maven
- Docker and Docker Compose (for PostgreSQL database)

## Step-by-Step Setup

### 1. Clone and Navigate

```bash
git clone <repository-url>
cd 13-graphql-with-mutations
```

### 2. Start the Database

```bash
cd docker
docker-compose up -d
```

**Verify database is running**:
- PostgreSQL: `localhost:5432`
- pgAdmin: `http://localhost:8080` (313@acu.com / password)

### 3. Build the Project

```bash
mvn clean compile
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

### 5. Access the Application

- **GraphiQL Interface**: `http://localhost:8081/graphiql`
- **GraphQL Endpoint**: `http://localhost:8081/graphql` (requires authentication)
- **Authentication Endpoint**: `http://localhost:8081/auth/login`

## Maven Commands

### Build Commands

```bash
# Clean the project
mvn clean

# Compile the source code
mvn compile

# Create a JAR file
mvn package

# Clean and compile
mvn clean compile
```

### Run Commands

```bash
# Run the application
mvn spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Test Commands

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=BookControllerTest

# Run integration tests
mvn test -Dtest=GraphqlServerApplicationTests

# Run tests with coverage
mvn test jacoco:report
```

## Application Configuration

### Default Settings

- **Port**: 8081
- **Database**: PostgreSQL (localhost:5432)
- **GraphQL Endpoint**: `/graphql`
- **GraphiQL**: `/graphiql` (enabled in development)
- **Authentication**: `/auth/login`

### Environment Variables

You can override default settings using environment variables:

```bash
# Set custom port
export SERVER_PORT=8080

# Set custom database URL
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/graphql_db

# Run with custom settings
mvn spring-boot:run
```

### Application Properties

Key configuration in `src/main/resources/application.properties`:

```properties
# Server configuration
server.port=8081

# Database configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/graphql_db
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# GraphQL configuration
spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/graphiql

# JWT configuration
jwt.secret=your-secret-key
jwt.expiration=86400000
```

## Troubleshooting

### Common Issues

1. **Database Connection Error**
   ```bash
   # Check if database is running
   docker ps
   
   # Restart database
   cd docker && docker-compose restart
   ```

2. **Port Already in Use**
   ```bash
   # Find process using port 8081
   lsof -i :8081
   
   # Kill process or change port
   export SERVER_PORT=8080
   ```

3. **Build Errors**
   ```bash
   # Clean and rebuild
   mvn clean compile
   
   # Check Java version
   java -version
   ```

### Logs

Check application logs for debugging:

```bash
# Run with debug logging
mvn spring-boot:run -Dlogging.level.com.acu.graphql=DEBUG

# View logs in real-time
tail -f logs/application.log
```

## Development Workflow

1. **Start database**: `cd docker && docker-compose up -d`
2. **Build project**: `mvn clean compile`
3. **Run application**: `mvn spring-boot:run`
4. **Access GraphiQL**: `http://localhost:8081/graphiql`
5. **Make changes** and restart application
6. **Run tests**: `mvn test`

## Production Deployment

For production deployment:

1. **Build JAR**: `mvn clean package`
2. **Run JAR**: `java -jar target/graphql-server-0.0.1-SNAPSHOT.jar`
3. **Configure environment variables** for production settings
4. **Set up production database** (not Docker)
5. **Configure reverse proxy** (nginx, etc.)

## Demo Script

Use the provided demo script to test the GraphQL API:

```bash
script/demo.sh
```

This script includes curl commands and GraphQL queries/mutations to demonstrate:
- JWT authentication and token retrieval
- Querying existing books with authentication
- Creating new authors and books (ADMIN role required)
- Updating book information (ADMIN role required)
- Deleting books (ADMIN role required)
- Error handling for non-existent resources
- Role-based access control
