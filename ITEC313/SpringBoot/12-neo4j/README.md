# Spring Boot Neo4j Demo Project

This project demonstrates how to use Spring Boot to connect to Neo4j and perform CRUD (Create, Read, Update, Delete) operations. It's designed as a learning resource to understand the integration between Spring Boot and Neo4j.

## Project Overview

The project implements a simple Person management system with the following features:
- Store Person data in Neo4j database
- Retrieve Person data from Neo4j database
- RESTful API endpoints for CRUD operations
- Comprehensive test coverage using TestContainers

## Technologies Used

- **Spring Boot 3.2.0**: Main framework for building the application
- **Spring Data Neo4j**: For Neo4j database integration
- **Neo4j**: Graph database for data storage
- **Maven**: Build tool and dependency management
- **Docker**: Containerization for Neo4j database
- **TestContainers**: For integration testing with containerized Neo4j
- **JUnit 5**: Testing framework

## Project Structure

```
src/
├── main/
│   ├── java/com/acu/neo4j/
│   │   ├── Neo4jApplication.java      # Main Spring Boot application
│   │   ├── Person.java                # Neo4j entity class
│   │   ├── PersonRepository.java      # Neo4j repository interface
│   │   ├── PersonService.java         # Business logic service
│   │   └── PersonController.java      # REST controller
│   └── resources/
│       └── application.properties     # Application configuration
├── test/
│   └── java/com/acu/neo4j/
│       ├── Neo4jApplicationTests.java # Basic context test
│       └── PersonServiceTest.java     # Comprehensive service tests
docker/
└── docker-compose.yml                 # Neo4j container configuration
scripts/
└── demo.sh                           # Demo script for complete workflow
```

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Docker and Docker Compose

## Getting Started

### Option 1: Quick Demo (Recommended)

For a complete demonstration of the project, run the demo script:

```bash
./scripts/demo.sh
```

This script will:
1. Start Neo4j database using Docker Compose
2. Launch the Spring Boot application
3. Store sample data in Neo4j
4. Retrieve and display data from Neo4j
5. Keep the application running for manual testing
6. Clean up all resources when stopped (Ctrl+C)

### Option 2: Manual Setup

#### 1. Start Neo4j Database

First, start the Neo4j database using Docker Compose:

```bash
cd docker
docker-compose up -d
```

This will start Neo4j on:
- HTTP: http://localhost:7474 (Neo4j Browser)
- Bolt: bolt://localhost:7687 (Application connection)

Default credentials:
- Username: `neo4j`
- Password: `Sydney@9876`

#### 2. Build the Project

```bash
mvn clean compile
```

#### 3. Run Tests

```bash
mvn test
```

The tests use TestContainers to automatically spin up a Neo4j container for testing.

#### 4. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

Once the application is running, you can use the following REST endpoints:

### Create a Person
```bash
POST http://localhost:8080/api/people
Content-Type: application/json

{
  "name": "John Doe",
  "age": 30
}
```

### Get All People
```bash
GET http://localhost:8080/api/people
```

### Get Person by ID
```bash
GET http://localhost:8080/api/people/{id}
```

### Get People by Name
```bash
GET http://localhost:8080/api/people/name/{name}
```

### Get People Older Than Age
```bash
GET http://localhost:8080/api/people/older-than/{age}
```

### Get All People Ordered by Name
```bash
GET http://localhost:8080/api/people/ordered
```

### Delete Person
```bash
DELETE http://localhost:8080/api/people/{id}
```

## Demo Script

The project includes a comprehensive demo script (`scripts/demo.sh`) that demonstrates the complete workflow:

### Features
- **Automated Setup**: Starts Neo4j and Spring Boot application automatically
- **Data Operations**: Stores and retrieves sample data
- **Health Checks**: Ensures services are ready before proceeding
- **Colored Output**: Provides clear, formatted output with status indicators
- **Automatic Cleanup**: Properly shuts down all services when stopped

### What the Demo Shows
1. **Data Storage**: Creates 4 sample people with different attributes
2. **Data Retrieval**: Demonstrates various query operations:
   - Get all people
   - Get people ordered by name
   - Get people older than a specific age
   - Get people by name
   - Get specific person by ID

### Running the Demo
```bash
# Make sure the script is executable
chmod +x scripts/demo.sh

# Run the demo
./scripts/demo.sh
```

### Demo Output
The script provides real-time feedback with colored output:
- 🔵 **Blue**: Information messages
- 🟢 **Green**: Success messages
- 🟡 **Yellow**: Warning messages
- 🔴 **Red**: Error messages

### Stopping the Demo
Press `Ctrl+C` to stop the demo. The script will automatically:
- Stop the Spring Boot application
- Shut down Docker containers
- Clean up all resources

## Testing the Application

### Using curl

1. Create a person:
```bash
curl -X POST http://localhost:8080/api/people \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice Johnson","age":25}'
```

2. Get all people:
```bash
curl http://localhost:8080/api/people
```

3. Get people older than 20:
```bash
curl http://localhost:8080/api/people/older-than/20
```

### Using Neo4j Browser

You can also access the Neo4j Browser at http://localhost:7474 to run Cypher queries directly:

```cypher
// View all Person nodes
MATCH (p:Person) RETURN p

// Find people older than 25
MATCH (p:Person) WHERE p.age > 25 RETURN p

// Count total people
MATCH (p:Person) RETURN count(p)
```

## Development

### Running Tests

The project includes comprehensive tests that:
- Test CRUD operations
- Use TestContainers for isolated testing
- Verify data persistence and retrieval
- Test custom queries

```bash
mvn test
```

### Code Quality

The project follows Spring Boot best practices:
- Layered architecture (Controller → Service → Repository)
- Proper dependency injection
- Comprehensive test coverage
- Clean code structure

## Troubleshooting

### Common Issues

1. **Neo4j connection failed**: Ensure Docker is running and Neo4j container is started
2. **Port conflicts**: Check if ports 7474, 7687, or 8080 are already in use
3. **Test failures**: Ensure Docker has enough resources allocated

### Logs

Check application logs for detailed information:
```bash
mvn spring-boot:run
```

## Cleanup

### If you used the Demo Script
The demo script automatically cleans up when you press `Ctrl+C`. If you need to manually clean up:

```bash
# Stop Spring Boot application (if running)
pkill -f "spring-boot:run"

# Stop Docker containers
cd docker
docker-compose down -v
cd ..
```

### If you used Manual Setup
To stop and remove the Neo4j container:
```bash
cd docker
docker-compose down -v
```
