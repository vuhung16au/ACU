# Spring Boot GraphQL Server

A simple GraphQL service built with Spring Boot and Spring for GraphQL. This project demonstrates how to create a GraphQL API in Java using Spring Boot with full CRUD (Create, Read, Update, Delete) capabilities.

## Technologies Used

- **Spring Boot 3.5.0**: Main framework for building the application
- **Spring for GraphQL**: GraphQL support for Spring Boot
- **Spring Security**: Authentication and authorization framework
- **JWT (JSON Web Tokens)**: Stateless authentication mechanism
- **Spring Data JPA**: Data access layer
- **PostgreSQL**: Primary database (with Docker setup)
- **H2 Database**: In-memory database for testing
- **Java 17**: Programming language
- **Maven**: Build tool and dependency management
- **JUnit 5**: Testing framework
- **GraphiQL**: Interactive GraphQL IDE (enabled for development)

## Project Description

This project implements a comprehensive GraphQL server that provides access to a large collection of books and their authors. The service includes:

- **Authentication & Authorization**: JWT-based authentication with role-based access control
- **1002 authors** with realistic names from various backgrounds
- **2002 books** with diverse titles, genres, and page counts (150-800 pages)
- GraphQL queries to retrieve books by ID with their associated author information
- GraphQL queries with filtering capabilities (search by name, filter by genre, combined filtering)
- GraphQL mutations for full CRUD operations (Create, Read, Update, Delete) with role-based permissions
- RESTful GraphQL endpoint at `/graphql` (requires authentication)
- Authentication endpoint at `/auth/login` for obtaining JWT tokens
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
│   │   ├── User.java                # User JPA entity for authentication
│   │   ├── AuthorRepository.java    # JPA repository for authors
│   │   ├── BookRepository.java      # JPA repository for books
│   │   ├── UserRepository.java      # JPA repository for users
│   │   ├── BookController.java      # GraphQL controller with queries and mutations
│   │   ├── AuthController.java      # Authentication controller for login
│   │   ├── SecurityConfig.java      # Spring Security configuration
│   │   ├── JwtUtil.java             # JWT utility for token management
│   │   ├── JwtAuthenticationFilter.java  # JWT authentication filter
│   │   ├── CustomUserDetailsService.java # Custom user details service
│   │   ├── DataInitializer.java     # Data initializer for default user
│   │   ├── CreateBookInput.java     # Input type for creating books
│   │   ├── CreateAuthorInput.java   # Input type for creating authors
│   │   ├── UpdateBookInput.java     # Input type for updating books
│   │   └── GraphqlServerApplication.java  # Main application class
│   └── resources/
│       ├── application.properties   # Application configuration
│       └── graphql/
│           └── schema.graphqls      # GraphQL schema definition
└── test/
    └── java/com/acu/graphql/
        ├── BookControllerTest.java  # GraphQL controller tests (queries and mutations)
        ├── AuthControllerTest.java  # Authentication controller tests
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

4. **Sample Data**:
   - The database will be automatically populated with **1002 authors** and **2002 books**
   - Sample data includes realistic names, diverse book titles, and varied page counts
   - Data is generated using templates that create believable book titles and author names

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
4. **GraphQL endpoint**: Available at `http://localhost:8081/graphql` (requires authentication)
5. **Authentication endpoint**: Available at `http://localhost:8081/auth/login`

## Authentication

The application uses JWT-based authentication with the following default credentials:

- **Username**: `313@acu.com`
- **Password**: `123456`
- **Role**: `ADMIN`

### Getting Started with Authentication

1. **Login to get a JWT token**:
   ```bash
   curl -X POST http://localhost:8081/auth/login \
     -H "Content-Type: application/json" \
     -d '{
       "username": "313@acu.com",
       "password": "123456"
     }'
   ```

2. **Use the token for GraphQL requests**:
   ```bash
   curl -X POST http://localhost:8081/graphql \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN" \
     -d '{
       "query": "query { bookById(id: \"book-1\") { id name pageCount } }"
     }'
   ```

### Role-Based Access Control

- **ADMIN Role**: Can perform all operations (queries and mutations)
- **USER Role**: Can only perform read operations (queries)
- **Unauthenticated**: Cannot access any GraphQL endpoints

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

### Query books with cursor-based pagination:

```graphql
query {
  books(first: 5) {
    edges {
      cursor
      node {
        id
        name
        pageCount
        genre
        author {
          id
          firstName
          lastName
        }
      }
    }
    pageInfo {
      hasNextPage
      hasPreviousPage
      startCursor
      endCursor
    }
    totalCount
  }
}
```

### Query books with cursor pagination (get next page):

```graphql
query {
  books(first: 5, after: "Ym9vay0x") {
    edges {
      cursor
      node {
        id
        name
        pageCount
        genre
        author {
          id
          firstName
          lastName
        }
      }
    }
    pageInfo {
      hasNextPage
      hasPreviousPage
      startCursor
      endCursor
    }
    totalCount
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
      "genre": "Non-Fiction",
      "author": {
        "id": "author-1",
        "firstName": "Donald",
        "lastName": "Horne"
      }
    }
  }
}
```

### Pagination Response Example:

```json
{
  "data": {
    "books": {
      "edges": [
        {
          "cursor": "Ym9vay0x",
          "node": {
            "id": "book-1",
            "name": "The Lucky Country",
            "pageCount": 300,
            "genre": "Non-Fiction",
            "author": {
              "id": "author-1",
              "firstName": "Donald",
              "lastName": "Horne"
            }
          }
        },
        {
          "cursor": "Ym9vay0y",
          "node": {
            "id": "book-2",
            "name": "The Magic Pudding",
            "pageCount": 250,
            "genre": "Children",
            "author": {
              "id": "author-2",
              "firstName": "Norman",
              "lastName": "Lindsay"
            }
          }
        }
      ],
      "pageInfo": {
        "hasNextPage": true,
        "hasPreviousPage": false,
        "startCursor": "Ym9vay0x",
        "endCursor": "Ym9vay0y"
      },
      "totalCount": 2002
    }
  }
}
```

## GraphQL Queries with Filtering

The project now supports advanced filtering capabilities for book queries:

### Query books with search filter:

```graphql
query {
  books(first: 5, search: "Lucky") {
    edges {
      cursor
      node {
        id
        name
        pageCount
        genre
        author {
          id
          firstName
          lastName
        }
      }
    }
    pageInfo {
      hasNextPage
      hasPreviousPage
      startCursor
      endCursor
    }
    totalCount
  }
}
```

### Query books with genre filter:

```graphql
query {
  books(first: 5, genre: "Non-Fiction") {
    edges {
      cursor
      node {
        id
        name
        pageCount
        genre
        author {
          id
          firstName
          lastName
        }
      }
    }
    pageInfo {
      hasNextPage
      hasPreviousPage
      startCursor
      endCursor
    }
    totalCount
  }
}
```

### Query books with combined search and genre filter:

```graphql
query {
  books(first: 5, search: "Lucky", genre: "Non-Fiction") {
    edges {
      cursor
      node {
        id
        name
        pageCount
        genre
        author {
          id
          firstName
          lastName
        }
      }
    }
    pageInfo {
      hasNextPage
      hasPreviousPage
      startCursor
      endCursor
    }
    totalCount
  }
}
```

## GraphQL Mutations

The project now supports full CRUD operations through GraphQL mutations. **All mutations require ADMIN role authentication**:

### 1. Create Author

```graphql
mutation {
  createAuthor(input: {
    firstName: "Jane"
    lastName: "Austen"
  }) {
    id
    firstName
    lastName
  }
}
```

### 2. Create Book

```graphql
mutation {
  createBook(input: {
    name: "Pride and Prejudice"
    pageCount: 432
    authorId: "author-1"
    genre: "Romance"
  }) {
    id
    name
    pageCount
    genre
    author {
      id
      firstName
      lastName
    }
  }
}
```

### 3. Update Book

```graphql
mutation {
  updateBook(id: "book-1", input: {
    name: "The Lucky Country - Updated Edition"
    pageCount: 320
    genre: "History"
  }) {
    id
    name
    pageCount
    genre
    author {
      id
      firstName
      lastName
    }
  }
}
```

### 4. Delete Book

```graphql
mutation {
  deleteBook(id: "book-1")
}
```

**Response**: Returns `true` if the book was deleted successfully, `false` if the book doesn't exist.

**Note**: All mutations require a valid JWT token with ADMIN role in the Authorization header:
```
Authorization: Bearer YOUR_JWT_TOKEN
```

## Demo Script

Use the provided demo script to test the GraphQL API including authentication and all mutations:

```bash
./script/demo.sh
```

This script includes curl commands and GraphQL queries/mutations to demonstrate:
- JWT authentication and token retrieval
- Querying existing books with authentication
- Creating new authors and books (ADMIN role required)
- Updating book information (ADMIN role required)
- Deleting books (ADMIN role required)
- Error handling for non-existent resources
- Role-based access control

## Available Books

The database now contains **2002 books** written by **1002 authors**, including:

1. **"The Lucky Country"** by Donald Horne (1964)
   - A book about Australia that has become a nickname for the country
   - ID: `book-1`

2. **"The Magic Pudding: Being The Adventures of Bunyip Bluegum and his friends Bill Barnacle and Sam Sawnoff"** by Norman Lindsay (1918)
   - A classic Australian children's book
   - ID: `book-2`

3. **Plus 2000 additional books** with realistic titles such as:
   - "The Secret of Paris" by Mia Taylor
   - "The Dark Castle in Prague" by various authors
   - "Helen and the Bridge" by various authors
   - "Big Ocean" by various authors
   - And many more with diverse themes, locations, and characters

The sample data includes books with page counts ranging from 150 to 800 pages, covering various genres and themes including adventures, mysteries, historical fiction, and contemporary literature.

## Maven Commands

- `mvn clean`: Clean the project
- `mvn compile`: Compile the source code
- `mvn test`: Run tests (including mutation tests)
- `mvn spring-boot:run`: Run the application
- `mvn package`: Create a JAR file

## GraphQL Schema

The GraphQL schema defines the following types:

### Query Type
- **bookById(id: ID)**: Retrieve a book by its ID
- **books(first: Int, after: String, search: String, genre: String)**: Retrieve books with cursor-based pagination and filtering

### Mutation Type
- **createBook(input: CreateBookInput!)**: Create a new book
- **createAuthor(input: CreateAuthorInput!)**: Create a new author
- **updateBook(id: ID!, input: UpdateBookInput!)**: Update an existing book
- **deleteBook(id: ID!)**: Delete a book by ID

### Object Types
- **Book**: Book type with id, name, pageCount, genre, and author fields
- **Author**: Author type with id, firstName, and lastName fields
- **BookConnection**: Paginated connection containing edges, pageInfo, and totalCount
- **BookEdge**: Edge containing cursor and book node
- **PageInfo**: Pagination metadata with hasNextPage, hasPreviousPage, startCursor, and endCursor

### Input Types
- **CreateBookInput**: Input for creating books (name, pageCount, authorId, genre)
- **CreateAuthorInput**: Input for creating authors (firstName, lastName)
- **UpdateBookInput**: Input for updating books (optional name, pageCount, authorId, genre)

## Testing

The project includes comprehensive tests for both queries and mutations:

- **Authentication Tests**: Test JWT login functionality and token validation
- **Query Tests**: Test book retrieval with and without author information (with authentication)
- **Pagination Tests**: Test cursor-based pagination functionality (with authentication)
- **Mutation Tests**: Test all CRUD operations (create, update, delete) with role-based authorization
- **Error Handling**: Test scenarios with non-existent resources and unauthorized access
- **Integration Tests**: Full application context tests with security configuration

All tests use Spring Boot Test framework, Spring Security Test, and Mockito for mocking dependencies.
