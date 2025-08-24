# Spring Boot GraphQL Server

A simple GraphQL service built with Spring Boot and Spring for GraphQL. This project demonstrates how to create a GraphQL API in Java using Spring Boot.

## Technologies Used

- **Spring Boot 3.5.0**: Main framework for building the application
- **Spring for GraphQL**: GraphQL support for Spring Boot
- **Spring Data JPA**: Data access layer
- **PostgreSQL**: Primary database (with Docker setup)
- **H2 Database**: In-memory database for testing
- **Java 17**: Programming language
- **Maven**: Build tool and dependency management
- **JUnit 5**: Testing framework
- **GraphiQL**: Interactive GraphQL IDE (enabled for development)

## Project Description

This project implements a simple GraphQL server that provides access to Australian books and their authors. The service includes:

- Two Australian books: "The Lucky Country" by Donald Horne and "The Magic Pudding" by Norman Lindsay
- GraphQL queries to retrieve books by ID with their associated author information
- RESTful GraphQL endpoint at `/graphql`
- Interactive GraphiQL interface at `/graphiql` (when enabled)
- PostgreSQL database with Docker setup for data persistence
- JPA entities for data modeling

## Project Structure

```
src/
├── main/
│   ├── java/com/acu/graphql/
│   │   ├── Author.java              # Author JPA entity
│   │   ├── Book.java                # Book JPA entity
│   │   ├── AuthorRepository.java    # JPA repository for authors
│   │   ├── BookRepository.java      # JPA repository for books
│   │   ├── BookController.java      # GraphQL controller
│   │   └── GraphqlServerApplication.java  # Main application class
│   └── resources/
│       ├── application.properties   # Application configuration
│       └── graphql/
│           └── schema.graphqls      # GraphQL schema definition
└── test/
    └── java/com/acu/graphql/
        ├── BookControllerTest.java  # GraphQL controller tests
        └── GraphqlServerApplicationTests.java  # Integration tests
docker/
├── docker-compose.yml               # Docker setup for PostgreSQL and pgAdmin
├── init.sql                         # Database initialization script
└── README.md                        # Docker setup instructions
```

## Prerequisites

- Java 17
- Maven
- Docker and Docker Compose (for PostgreSQL database)

## Database Setup

1. **Start PostgreSQL and pgAdmin**:
   ```bash
   cd docker
   docker-compose up -d
   ```

2. **Verify database is running**:
   - PostgreSQL: `localhost:5432`
   - pgAdmin: `http://localhost:8080` (313@acu.com / password)

3. **Database credentials**:
   - Database: `graphql_db`
   - Username: `postgres`
   - Password: `postgres`

## How to Build

1. **Clone the repository**: `git clone <repository-url>`
2. **Navigate to project directory**: `cd 13-graphql-with-mutations`
3. **Build the project**: `mvn clean compile`

## How to Run

1. **Start the database** (if not already running):
   ```bash
   cd docker
   docker-compose up -d
   ```

2. **Run the application**: `mvn spring-boot:run`
3. **Access GraphiQL interface**: Open your browser and go to `http://localhost:8081/graphiql`
4. **GraphQL endpoint**: Available at `http://localhost:8081/graphql`

## How to Test

1. **Run all tests**: `mvn test`
2. **Run specific test class**: `mvn test -Dtest=BookControllerTest`
3. **Run integration tests**: `mvn test -Dtest=GraphqlServerApplicationTests`

## GraphQL Queries

### Query a book by ID with author information:

```graphql
query {
  bookById(id: "book-1") {
    id
    name
    pageCount
    author {
      id
      firstName
      lastName
    }
  }
}
```

### Expected Response:

```json
{
  "data": {
    "bookById": {
      "id": "book-1",
      "name": "The Lucky Country",
      "pageCount": 300,
      "author": {
        "id": "author-1",
        "firstName": "Donald",
        "lastName": "Horne"
      }
    }
  }
}
```

## Demo Script

Use the provided demo script to test the GraphQL API:

```bash
./script/demo.sh
```

This script includes curl commands and GraphQL queries to demonstrate the functionality.

## Available Books

1. **"The Lucky Country"** by Donald Horne (1964)
   - A book about Australia that has become a nickname for the country
   - ID: `book-1`

2. **"The Magic Pudding: Being The Adventures of Bunyip Bluegum and his friends Bill Barnacle and Sam Sawnoff"** by Norman Lindsay (1918)
   - A classic Australian children's book
   - ID: `book-2`

## Maven Commands

- `mvn clean`: Clean the project
- `mvn compile`: Compile the source code
- `mvn test`: Run tests
- `mvn spring-boot:run`: Run the application
- `mvn package`: Create a JAR file

## GraphQL Schema

The GraphQL schema defines the following types:

- **Query**: Root query type with `bookById` field
- **Book**: Book type with id, name, pageCount, and author fields
- **Author**: Author type with id, firstName, and lastName fields
