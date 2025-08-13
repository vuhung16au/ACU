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

## How to Run

### Prerequisites
- Java 17+
- Maven 3.9+
- PostgreSQL (for prod profile) or H2 (for dev profile)

### Run with H2 (Development)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Run with PostgreSQL (Production)
```bash
# Start PostgreSQL
docker run --name postgres-hibernate -e POSTGRES_PASSWORD=password -e POSTGRES_DB=hibernate_demo -p 5432:5432 -d postgres:15

# Run application
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Access the Application
- REST API: http://localhost:8090
- H2 Console (dev): http://localhost:8090/h2-console
- Actuator: http://localhost:8090/actuator

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
