# Spring Boot GraphQL Server

A simple GraphQL service built with Spring Boot and Spring for GraphQL. This project demonstrates how to create a GraphQL API in Java using Spring Boot.

## Technologies Used

- **Spring Boot 3.5.0**: Main framework for building the application
- **Spring for GraphQL**: GraphQL support for Spring Boot
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

## Project Structure

```
src/
├── main/
│   ├── java/com/acu/graphql/
│   │   ├── Author.java              # Author record with sample data
│   │   ├── Book.java                # Book record with sample data
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
```

## How to Build

1. **Prerequisites**: Make sure you have Java 17 and Maven installed
2. **Clone the repository**: `git clone <repository-url>`
3. **Navigate to project directory**: `cd 13-graphql`
4. **Build the project**: `mvn clean compile`

## How to Run

1. **Run the application**: `mvn spring-boot:run`
2. **Access GraphiQL interface**: Open your browser and go to `http://localhost:8080/graphiql`
3. **GraphQL endpoint**: Available at `http://localhost:8080/graphql`

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
