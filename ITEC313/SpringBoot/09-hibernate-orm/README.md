# Hibernate ORM Demonstration Project

A comprehensive Spring Boot project demonstrating Hibernate ORM concepts for beginners and students. This project focuses on core Hibernate functionality without the complexity of web APIs.

## ⚡ Quick Start (3 Steps)

```bash
# 1. Start database services
./scripts/start-services.sh

# 2. Run the Hibernate demo
./scripts/run-demo.sh

# 3. Stop services when done
./scripts/stop-services.sh
```

**Optional**: Access pgAdmin at http://localhost:8080 (Email: `313@acu.edu.au`, Password: `password`)

## 🎯 Learning Objectives

This project demonstrates the following Hibernate ORM concepts:

1. **Entity Creation and Persistence**
   - Basic entity mapping with annotations
   - Primary key generation strategies
   - Field validation and constraints

2. **Entity Relationships**
   - **One-to-Many**: Author → Books
   - **Many-to-One**: Books → Author  
   - **Many-to-Many**: Books ↔ Tags

3. **Hibernate Annotations**
   - `@Entity`, `@Table`, `@Column`
   - `@OneToMany`, `@ManyToOne`, `@ManyToMany`
   - `@JoinColumn`, `@JoinTable`
   - `@PrePersist`, `@PreUpdate`

4. **CRUD Operations**
   - Create, Read, Update, Delete operations
   - Batch operations and transactions

5. **Query Methods**
   - Derived query methods (findBy, countBy, etc.)
   - Custom JPQL queries
   - Native SQL queries

6. **Advanced Features**
   - Lazy vs Eager loading
   - Cascade operations
   - Entity lifecycle callbacks

## 🏗️ Project Structure

```
├── src/main/java/com/acu/hibernate/
│   ├── entity/                 # JPA Entities
│   │   ├── Author.java        # Author entity with One-to-Many relationship
│   │   ├── Book.java          # Book entity with Many-to-One and Many-to-Many
│   │   └── Tag.java           # Tag entity with Many-to-Many relationship
│   ├── repository/            # Spring Data JPA Repositories
│   │   ├── AuthorRepository.java
│   │   ├── BookRepository.java
│   │   └── TagRepository.java
│   ├── demo/                  # Console-based demonstrations
│   │   └── HibernateDemo.java # Main demonstration class
│   └── HibernateOrmApplication.java
├── docker/                   # Docker configuration
│   ├── docker-compose.yml    # PostgreSQL and pgAdmin services
│   └── README.md            # Docker setup instructions
├── scripts/                  # Utility scripts
│   ├── start-services.sh    # Start database services
│   ├── run-demo.sh          # Run Hibernate demo
│   └── stop-services.sh     # Stop database services
└── README.md                # Project documentation
```

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose (recommended)
- PostgreSQL 15+ (if not using Docker)

### Using Docker (Recommended)

#### Option 1: Using Scripts (Easiest)
```bash
# Start services (PostgreSQL + optional pgAdmin)
./scripts/start-services.sh

# Run the application
./scripts/run-demo.sh

# Stop services when done
./scripts/stop-services.sh
```

#### Option 2: Manual Commands
1. **Start PostgreSQL database:**
   ```bash
   cd docker
   docker-compose up -d postgres
   ```

2. **Optional: Start with pgAdmin for database management:**
   ```bash
   cd docker
   docker-compose --profile tools up -d
   # Access pgAdmin at: http://localhost:8080
   # Email: 313@acu.edu.au
   # Password: password
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

### Manual Setup

1. **Create PostgreSQL database:**
   ```sql
   CREATE DATABASE hibernate_demo;
   CREATE USER postgres WITH PASSWORD 'password';
   GRANT ALL PRIVILEGES ON DATABASE hibernate_demo TO postgres;
   ```

2. **Update database configuration** in `src/main/resources/application.yml`

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

## 📚 Demonstrations

When you run the application, you'll see console output demonstrating:

### 1. Entity Creation and Persistence
```java
// Creating and saving entities
Author author = new Author();
author.setName("J.K. Rowling");
author.setEmail("jk.rowling@example.com");
Author savedAuthor = authorRepository.save(author);
```

### 2. One-to-Many Relationship
```java
// Author -> Books relationship
Author author = authorRepository.findByName("J.K. Rowling").orElse(null);
Book book = new Book("Harry Potter and the Philosopher's Stone", ...);
author.addBook(book); // Establishes the relationship
authorRepository.save(author); // Saves both author and book
```

### 3. Many-to-Many Relationship
```java
// Books <-> Tags relationship
Book book = bookRepository.findAll().get(0);
Tag fantasyTag = tagRepository.findByName("Fantasy").orElse(null);
book.addTag(fantasyTag); // Establishes the relationship
bookRepository.save(book);
```

### 4. CRUD Operations
```java
// CREATE
Author newAuthor = authorRepository.save(author);

// READ
Optional<Author> foundAuthor = authorRepository.findById(id);

// UPDATE
author.setBiography("Updated biography");
Author updatedAuthor = authorRepository.save(author);

// DELETE
authorRepository.deleteById(id);
```

### 5. Query Methods
```java
// Derived queries
Optional<Author> author = authorRepository.findByName("J.K. Rowling");
List<Book> books1997 = bookRepository.findByPublicationYear(1997);
List<Book> expensiveBooks = bookRepository.findByPriceGreaterThan(new BigDecimal("30.00"));

// Custom JPQL queries
List<Author> authorsWithBooks = authorRepository.findAuthorsWithBooks();
```

### 6. Lazy Loading
```java
// Books are loaded lazily when accessed
Author author = authorRepository.findByName("J.K. Rowling").orElse(null);
System.out.println("Books count: " + author.getBooks().size()); // Triggers lazy loading
```

### 7. Cascade Operations
```java
// Saving author automatically saves associated books
Author author = new Author("Stephen King", ...);
Book book = new Book("The Shining", ...);
author.addBook(book);
authorRepository.save(author); // Both author and book are saved
```

## 🗄️ Database Schema

The project uses Flyway for database migrations:

### Tables Created:
- `authors` - Author information
- `books` - Book information with foreign key to authors
- `tags` - Tag information
- `book_tags` - Junction table for Many-to-Many relationship

### Sample Data:
- Authors: J.K. Rowling, Stephen King
- Books: Harry Potter series, The Shining
- Tags: Fantasy, Adventure, Young Adult

### Database Management:
- **pgAdmin**: Available at http://localhost:8080 (when using tools profile)
- **Credentials**: Email: `313@acu.edu.au`, Password: `password`
- **Database**: `hibernate_demo` on PostgreSQL

## 🧪 Testing

Run the tests to verify entity functionality:

```bash
mvn test
```

Tests cover:
- Entity creation and validation
- Relationship management
- CRUD operations
- Entity lifecycle

## 🛠️ Utility Scripts

The project includes helpful scripts for managing services:

### Service Management Scripts
- **`scripts/start-services.sh`** - Start PostgreSQL and optionally pgAdmin
- **`scripts/run-demo.sh`** - Run the Hibernate demonstration application
- **`scripts/stop-services.sh`** - Stop all services with option to remove data

### Usage
```bash
# Start services interactively
./scripts/start-services.sh

# Run the demo
./scripts/run-demo.sh

# Stop services
./scripts/stop-services.sh
```

These scripts provide an interactive way to manage Docker services with helpful prompts and status information.

## 📖 Key Hibernate Concepts Demonstrated

### Entity Annotations
```java
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();
}
```

### Relationship Mappings
```java
// One-to-Many (Author -> Books)
@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private List<Book> books;

// Many-to-One (Books -> Author)
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "author_id", nullable = false)
private Author author;

// Many-to-Many (Books <-> Tags)
@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
@JoinTable(
    name = "book_tags",
    joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id")
)
private Set<Tag> tags;
```

### Repository Methods
```java
// Derived queries
Optional<Author> findByName(String name);
List<Book> findByPublicationYear(Integer publicationYear);
List<Book> findByPriceGreaterThan(BigDecimal price);

// Custom JPQL queries
@Query("SELECT a FROM Author a WHERE SIZE(a.books) > 0")
List<Author> findAuthorsWithBooks();
```

## 🔧 Configuration

### Application Properties
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hibernate_demo
    username: postgres
    password: password
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        use_sql_comments: true
```

### Logging
The application logs SQL queries and Hibernate operations for educational purposes.

## 🎓 Educational Benefits

This project is designed for:

- **Beginners** learning Hibernate ORM
- **Students** studying JPA and database relationships
- **Developers** wanting to understand entity mapping
- **Anyone** interested in Spring Data JPA

### What You'll Learn:
- How to map Java objects to database tables
- How to establish and manage relationships between entities
- How to perform database operations using repositories
- How to use Hibernate annotations effectively
- How to write custom queries
- How to handle entity lifecycle events

## 🤝 Contributing

Feel free to contribute by:
- Adding more demonstration scenarios
- Improving documentation
- Adding more complex relationship examples
- Creating additional test cases

## 📄 License

This project is for educational purposes. Feel free to use and modify as needed.

---

**Happy Learning! 🚀**
