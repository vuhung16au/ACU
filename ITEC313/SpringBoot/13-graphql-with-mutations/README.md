# Spring Boot GraphQL Server

A simple GraphQL service built with Spring Boot and Spring for GraphQL. This project demonstrates how to create a GraphQL API in Java using Spring Boot with full CRUD (Create, Read, Update, Delete) capabilities.

## Quick Start

1. **Setup Database**: See [Database Setup](docs/database-setup.md)
2. **Build & Run**: See [Build & Run Guide](docs/build-and-run.md)
3. **Authentication**: See [Authentication Guide](docs/authentication.md)
4. **GraphQL Queries**: See [GraphQL Queries](docs/graphql-queries.md)
5. **GraphQL Mutations**: See [GraphQL Mutations](docs/graphql-mutations.md)

## Technologies Used

- **Spring Boot 3.5.0**: Main framework for building the application
- **Spring for GraphQL**: GraphQL support for Spring Boot
- **Spring Security**: Authentication and authorization framework
- **JWT (JSON Web Tokens)**: Stateless authentication mechanism
- **Spring Data JPA**: Data access layer
- **PostgreSQL**: Primary database (with Docker setup)
- **Maven**: Build tool and dependency management
- **GraphiQL**: Interactive GraphQL IDE (enabled for development)

## Key Features

- **Field-Level Security**: Hide sensitive fields based on user role
- **Authentication & Authorization**: JWT-based authentication with role-based access control
- **Many-to-Many relationship** between books and genres
- **One-to-Many relationship** between books and reviews
- **Full CRUD operations** with role-based permissions
- **Field Selection**: Clients can request only specific fields they need

## Documentation

- [Database Setup](docs/database-setup.md) - Database configuration and sample data
- [Build & Run Guide](docs/build-and-run.md) - How to build and run the application
- [Authentication Guide](docs/authentication.md) - JWT authentication and role-based access
- [GraphQL Queries](docs/graphql-queries.md) - All query examples and field selection
- [GraphQL Mutations](docs/graphql-mutations.md) - All mutation examples and CRUD operations
- [Testing Guide](docs/testing.md) - Testing instructions and examples
- [Project Structure](docs/project-structure.md) - Code organization and file descriptions
- [GraphQL Schema](docs/graphql-schema.md) - Complete schema documentation
- [Advanced Features](docs/advanced-features.md) - Field-level security, relationships, etc.

## Prerequisites

- Java 17
- Maven
- Docker and Docker Compose (for PostgreSQL database)

## Quick Commands

```bash
# Start database
cd docker && docker-compose up -d

# Build and run
mvn clean compile
mvn spring-boot:run

# Run tests
mvn test

# Access GraphiQL
# Open: http://localhost:8081/graphiql
```

## Default Credentials

- **Username**: `313@acu.com`
- **Password**: `123456`
- **Role**: `ADMIN`

For detailed information, see the [Authentication Guide](docs/authentication.md).
