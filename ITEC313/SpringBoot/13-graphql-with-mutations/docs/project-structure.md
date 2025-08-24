# Project Structure

This document provides a detailed overview of the project's code organization, file structure, and architectural decisions.

## Directory Structure

```
13-graphql-with-mutations/
├── docker/                          # Docker configuration and database setup
│   ├── docker-compose.yml          # PostgreSQL and database services
│   ├── init.sql                    # Database initialization script
│   ├── sample_data.sql             # Sample data for testing
│   ├── users.sql                   # User data initialization
│   ├── add_reviews_table.sql       # Reviews table schema
│   └── add_cursor_column.sql       # Cursor-based pagination support
├── docs/                           # Documentation files
│   ├── authentication.md           # JWT authentication guide
│   ├── build-and-run.md            # Build and deployment instructions
│   ├── database-setup.md           # Database configuration
│   ├── graphql-queries.md          # Query examples and usage
│   ├── graphql-mutations.md        # Mutation examples and CRUD operations
│   ├── testing.md                  # Testing guide and examples
│   ├── project-structure.md        # This file
│   ├── graphql-schema.md           # Schema documentation
│   └── advanced-features.md        # Advanced features guide
├── src/
│   ├── main/
│   │   ├── java/com/acu/graphql/   # Main application code
│   │   │   ├── GraphqlServerApplication.java  # Spring Boot main class
│   │   │   ├── Controllers/        # GraphQL resolvers and REST controllers
│   │   │   │   ├── BookController.java
│   │   │   │   ├── AuthController.java
│   │   │   │   └── ReviewController.java
│   │   │   ├── Models/             # Entity classes
│   │   │   │   ├── Book.java
│   │   │   │   ├── Author.java
│   │   │   │   ├── Genre.java
│   │   │   │   ├── Review.java
│   │   │   │   └── User.java
│   │   │   ├── Repositories/       # Data access layer
│   │   │   │   ├── BookRepository.java
│   │   │   │   ├── AuthorRepository.java
│   │   │   │   ├── GenreRepository.java
│   │   │   │   ├── ReviewRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── Security/           # Authentication and authorization
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   ├── JwtUtil.java
│   │   │   │   └── CustomUserDetailsService.java
│   │   │   ├── GraphQL/            # GraphQL-specific components
│   │   │   │   ├── GraphQLErrorHandler.java
│   │   │   │   ├── BookConnection.java
│   │   │   │   ├── BookEdge.java
│   │   │   │   ├── BookOrderBy.java
│   │   │   │   └── PageInfo.java
│   │   │   ├── Input/              # GraphQL input types
│   │   │   │   ├── CreateBookInput.java
│   │   │   │   ├── UpdateBookInput.java
│   │   │   │   ├── CreateAuthorInput.java
│   │   │   │   ├── CreateGenreInput.java
│   │   │   │   ├── UpdateGenreInput.java
│   │   │   │   ├── CreateReviewInput.java
│   │   │   │   └── UpdateReviewInput.java
│   │   │   └── DataInitializer.java # Sample data initialization
│   │   └── resources/
│   │       ├── application.properties  # Application configuration
│   │       └── graphql/
│   │           └── schema.graphqls     # GraphQL schema definition
│   └── test/
│       ├── java/com/acu/graphql/   # Test classes
│       │   ├── GraphqlServerApplicationTests.java
│       │   ├── BookControllerTest.java
│       │   ├── AuthControllerTest.java
│       │   └── ReviewControllerTest.java
│       └── resources/
│           └── graphql/
│               └── schema.graphqls     # Test schema
├── pom.xml                         # Maven project configuration
├── README.md                       # Main project documentation
└── script/
    ├── demo.sh                     # Demo script for testing
    └── generate_sample_data.py     # Python script for data generation
```

## Core Components

### 1. Application Entry Point

**`GraphqlServerApplication.java`**
- Spring Boot main class
- Configures application context
- Enables GraphQL and security features

### 2. Controllers (GraphQL Resolvers)

**`BookController.java`**
- Handles all book-related GraphQL operations
- Implements field-level security
- Manages book-genre relationships
- Provides pagination support

**`AuthController.java`**
- JWT authentication endpoints
- User login/logout functionality
- Token validation and refresh

**`ReviewController.java`**
- Review CRUD operations
- Role-based access control
- User review management

### 3. Entity Models

**`Book.java`**
```java
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private Integer pageCount;
    private String genre;
    
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
    
    @ManyToMany
    @JoinTable(name = "book_genres")
    private Set<Genre> genres = new HashSet<>();
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
}
```

**`Author.java`**
- Simple entity with firstName and lastName
- One-to-many relationship with books

**`Genre.java`**
- Genre information with name and description
- Many-to-many relationship with books

**`Review.java`**
- User reviews with rating and comments
- Many-to-one relationships with books and users

**`User.java`**
- User authentication and role information
- One-to-many relationship with reviews

### 4. Repositories

All repositories extend `JpaRepository` for basic CRUD operations:

- **`BookRepository`**: Custom queries for search, filtering, and pagination
- **`AuthorRepository`**: Author management operations
- **`GenreRepository`**: Genre CRUD and book relationship queries
- **`ReviewRepository`**: Review queries with user and book filtering
- **`UserRepository`**: User authentication and role queries

### 5. Security Configuration

**`SecurityConfig.java`**
- Configures Spring Security
- Defines authentication and authorization rules
- Sets up JWT filter chain

**`JwtAuthenticationFilter.java`**
- Intercepts requests to validate JWT tokens
- Extracts user information from tokens
- Sets up security context

**`JwtUtil.java`**
- JWT token generation and validation
- Token expiration handling
- User information extraction

### 6. GraphQL Components

**`GraphQLErrorHandler.java`**
- Custom error handling for GraphQL operations
- Provides meaningful error messages
- Handles authentication and authorization errors

**Pagination Support**
- **`BookConnection.java`**: Connection wrapper for pagination
- **`BookEdge.java`**: Edge wrapper with cursor
- **`PageInfo.java`**: Pagination metadata
- **`BookOrderBy.java`**: Sorting options enum

### 7. Input Types

All GraphQL input types are defined as separate classes:

- **`CreateBookInput`**: Book creation parameters
- **`UpdateBookInput`**: Book update parameters
- **`CreateAuthorInput`**: Author creation parameters
- **`CreateGenreInput`**: Genre creation parameters
- **`UpdateGenreInput`**: Genre update parameters
- **`CreateReviewInput`**: Review creation parameters
- **`UpdateReviewInput`**: Review update parameters

## Configuration Files

### `application.properties`
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/graphql_db
spring.datasource.username=postgres
spring.datasource.password=password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

# GraphQL Configuration
spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/graphiql

# Security Configuration
jwt.secret=your-secret-key
jwt.expiration=86400000

# Server Configuration
server.port=8081
```

### `schema.graphqls`
- Defines the complete GraphQL schema
- Includes all types, queries, and mutations
- Specifies input types and relationships

## Database Schema

### Tables
1. **`books`**: Book information and metadata
2. **`authors`**: Author information
3. **`genres`**: Genre categories
4. **`book_genres`**: Many-to-many relationship table
5. **`reviews`**: User reviews and ratings
6. **`users`**: User accounts and roles

### Relationships
- **Books ↔ Authors**: Many-to-one
- **Books ↔ Genres**: Many-to-many (via book_genres)
- **Books ↔ Reviews**: One-to-many
- **Users ↔ Reviews**: One-to-many

## Architectural Patterns

### 1. Layered Architecture
- **Controller Layer**: GraphQL resolvers and REST endpoints
- **Service Layer**: Business logic (implicit in controllers)
- **Repository Layer**: Data access and persistence
- **Entity Layer**: Domain models

### 2. Security Patterns
- **JWT Authentication**: Stateless token-based authentication
- **Role-Based Access Control**: ADMIN and USER roles
- **Field-Level Security**: Dynamic field visibility based on user role

### 3. GraphQL Patterns
- **Resolver Pattern**: Controllers act as GraphQL resolvers
- **Connection Pattern**: Cursor-based pagination
- **Input Validation**: Separate input types for mutations

### 4. Data Access Patterns
- **Repository Pattern**: Abstract data access layer
- **JPA/Hibernate**: Object-relational mapping
- **Custom Queries**: Optimized database queries

## Key Design Decisions

### 1. GraphQL Over REST
- **Flexibility**: Clients can request exactly what they need
- **Single Endpoint**: Reduces API complexity
- **Strong Typing**: Schema-driven development

### 2. JWT Authentication
- **Stateless**: No server-side session storage
- **Scalable**: Works well with microservices
- **Secure**: Token-based with expiration

### 3. Field-Level Security
- **Dynamic**: Fields shown based on user role
- **Granular**: Fine-grained access control
- **Transparent**: Security handled at resolver level

### 4. Cursor-Based Pagination
- **Performance**: Efficient for large datasets
- **Consistency**: Handles concurrent updates
- **GraphQL Standard**: Follows Relay specification

## Development Workflow

### 1. Schema-First Development
1. Define GraphQL schema in `schema.graphqls`
2. Generate or create corresponding Java types
3. Implement resolvers in controllers
4. Add business logic and security

### 2. Testing Strategy
1. Unit tests for individual components
2. Integration tests for GraphQL operations
3. Security tests for authentication and authorization
4. Performance tests for pagination and queries

### 3. Deployment
1. Database setup with Docker
2. Application build with Maven
3. Configuration for different environments
4. Monitoring and logging setup

## Best Practices

### 1. Code Organization
- Separate concerns into appropriate packages
- Use meaningful class and method names
- Follow Java naming conventions
- Document complex business logic

### 2. Security
- Validate all inputs
- Implement proper authentication
- Use role-based access control
- Secure sensitive data

### 3. Performance
- Use pagination for large datasets
- Optimize database queries
- Implement caching where appropriate
- Monitor application performance

### 4. Testing
- Write comprehensive unit tests
- Test security scenarios
- Validate GraphQL schema
- Test error conditions

## Future Enhancements

### 1. Additional Features
- Real-time subscriptions
- File upload support
- Advanced search and filtering
- Bulk operations

### 2. Performance Improvements
- Query optimization
- Database indexing
- Caching strategies
- Connection pooling

### 3. Security Enhancements
- Rate limiting
- Input sanitization
- Audit logging
- Advanced authorization

### 4. Monitoring and Observability
- Application metrics
- Query performance monitoring
- Error tracking
- Health checks
