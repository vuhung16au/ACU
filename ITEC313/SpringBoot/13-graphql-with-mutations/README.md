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

This project implements a comprehensive GraphQL server that provides access to a large collection of books and their authors, with a many-to-many relationship between books and genres, and a one-to-many relationship between books and reviews. The service includes:

### Key Features

- **Field-Level Security**: Hide sensitive fields based on user role
  - **ADMIN users**: Can see all fields including book reviews
  - **Non-admin users**: Reviews field is hidden (returns null)
  - **Implementation**: Uses Spring Security context to check user roles at field resolution time
- **Field Selection**: Clients can request only the specific fields they need, reducing over-fetching and under-fetching of data
- **Nested Field Selection**: Support for selecting fields from related objects (e.g., author information within books)
- **Performance Optimization**: Field selection helps optimize network usage and response times

- **Authentication & Authorization**: JWT-based authentication with role-based access control
- **1002 authors** with realistic names from various backgrounds
- **2002 books** with diverse titles, genres, and page counts (150-800 pages)
- **15 genres** with descriptions covering various literary categories
- **Many-to-Many relationship** between books and genres (a book can belong to multiple genres)
- **One-to-Many relationship** between books and reviews (a book can have multiple reviews)
- GraphQL queries to retrieve books by ID with their associated author, genre, and review information
- GraphQL queries with filtering capabilities (search by name, filter by genre, combined filtering)
- GraphQL queries with sorting capabilities (sort by name, page count, genre)
- GraphQL queries with combined filtering and sorting
- GraphQL mutations for full CRUD operations (Create, Read, Update, Delete) with role-based permissions
- Genre management with full CRUD operations
- Book-Genre relationship management (add/remove genres from books)
- Review system with 1-5 star ratings and comments
- User-based review management (users can only modify their own reviews)
- Automatic calculation of average ratings and review counts for books
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
│   │   ├── Book.java                # Book JPA entity (with many-to-many genres)
│   │   ├── Genre.java               # Genre JPA entity
│   │   ├── User.java                # User JPA entity for authentication
│   │   ├── AuthorRepository.java    # JPA repository for authors
│   │   ├── BookRepository.java      # JPA repository for books
│   │   ├── GenreRepository.java     # JPA repository for genres
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
│   │   ├── CreateGenreInput.java    # Input type for creating genres
│   │   ├── CreateReviewInput.java   # Input type for creating reviews
│   │   ├── UpdateBookInput.java     # Input type for updating books
│   │   ├── UpdateGenreInput.java    # Input type for updating genres
│   │   ├── UpdateReviewInput.java   # Input type for updating reviews
│   │   ├── Review.java              # Review JPA entity
│   │   ├── ReviewRepository.java    # JPA repository for reviews
│   │   ├── ReviewController.java    # GraphQL controller for reviews
│   │   └── GraphqlServerApplication.java  # Main application class
│   └── resources/
│       ├── application.properties   # Application configuration
│       └── graphql/
│           └── schema.graphqls      # GraphQL schema definition
└── test/
    └── java/com/acu/graphql/
        ├── BookControllerTest.java  # GraphQL controller tests (queries and mutations)
        ├── AuthControllerTest.java  # Authentication controller tests
        ├── ReviewControllerTest.java # Review controller tests
        └── GraphqlServerApplicationTests.java  # Integration tests
docker/
├── docker-compose.yml               # Docker setup for PostgreSQL and pgAdmin
├── init.sql                         # Database initialization script
├── add_cursor_column.sql            # Migration script for genres and book_genres
├── add_reviews_table.sql            # Migration script for reviews table
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
   - The database will be automatically populated with **1002 authors**, **2002 books**, **15 genres**, and **sample reviews**
   - Sample data includes realistic names, diverse book titles, varied page counts, and genre descriptions
   - Data is generated using templates that create believable book titles and author names
   - Books are automatically linked to genres based on their existing genre field
   - Sample reviews are included to demonstrate the review system functionality

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

## Many-to-Many Genre Relationship

This project now supports a many-to-many relationship between books and genres, allowing:

- **Multiple Genres per Book**: A book can belong to multiple genres (e.g., "Science Fiction" + "Adventure")
- **Genre Management**: Full CRUD operations for genres with descriptions
- **Flexible Categorization**: Books can be categorized more accurately and flexibly
- **Backward Compatibility**: The existing `genre` string field is maintained for compatibility

## One-to-Many Review Relationship

This project now supports a one-to-many relationship between books and reviews, allowing:

- **Multiple Reviews per Book**: A book can have multiple reviews from different users
- **User-Based Reviews**: Each review is associated with a specific user
- **Rating System**: 1-5 star rating system with optional comments
- **Review Management**: Full CRUD operations for reviews with user ownership validation
- **Automatic Calculations**: Average ratings and review counts are automatically calculated
- **Data Integrity**: Foreign key constraints ensure data consistency

### Database Schema

```sql
-- Genres table
CREATE TABLE genres (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT
);

-- Junction table for many-to-many relationship
CREATE TABLE book_genres (
    book_id VARCHAR(50) NOT NULL,
    genre_id VARCHAR(50) NOT NULL,
    PRIMARY KEY (book_id, genre_id),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

-- Reviews table for one-to-many relationship
CREATE TABLE reviews (
    id VARCHAR(50) PRIMARY KEY,
    book_id VARCHAR(50) NOT NULL,
    user_id BIGINT NOT NULL,
    rating INTEGER CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

### Sample Genres

The system includes 15 predefined genres:
- Fiction, Non-Fiction, Science Fiction, Fantasy, Mystery
- Thriller, Romance, Adventure, Historical Fiction, Biography
- Children, Horror, Poetry, Drama, Comedy

## Field-Level Security

This project implements **field-level security** to hide sensitive fields based on user roles. This is a powerful security feature that allows fine-grained control over what data users can access.

### Implementation

Field-level security is implemented using Spring Security's `SecurityContextHolder` to check user roles at field resolution time. The `reviews` field in the `Book` type is protected:

```java
@SchemaMapping
public List<Review> reviews(Book book) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
    // Check if user is authenticated and has ADMIN role
    if (authentication != null && 
        authentication.isAuthenticated() && 
        authentication.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
        return new ArrayList<>(book.getReviews());
    }
    
    // Return null for non-admin users (field will be hidden)
    return null;
}
```

### Security Rules

- **ADMIN Role**: Can see all fields including book reviews
- **USER Role**: Cannot see book reviews (field returns null)
- **Unauthenticated**: Cannot see book reviews (field returns null)

### Example Queries

#### ADMIN User - Can See Reviews
```graphql
query {
  bookById(id: "book-1") {
    id
    name
    reviews {
      id
      rating
      comment
    }
  }
}
```

**Response for ADMIN user:**
```json
{
  "data": {
    "bookById": {
      "id": "book-1",
      "name": "The Lucky Country",
      "reviews": [
        {
          "id": "review-1",
          "rating": 5,
          "comment": "Excellent book!"
        }
      ]
    }
  }
}
```

#### Non-ADMIN User - Reviews Hidden
```graphql
query {
  bookById(id: "book-1") {
    id
    name
    reviews {
      id
      rating
      comment
    }
  }
}
```

**Response for non-ADMIN user:**
```json
{
  "data": {
    "bookById": {
      "id": "book-1",
      "name": "The Lucky Country",
      "reviews": null
    }
  }
}
```

### Benefits

1. **Fine-grained Security**: Control access at the field level, not just the object level
2. **Transparent to Clients**: Non-admin users simply see `null` for protected fields
3. **No Data Leakage**: Sensitive data is never sent to unauthorized users
4. **GraphQL Native**: Leverages GraphQL's field resolution mechanism
5. **Performance**: No additional database queries for security checks

## GraphQL Queries

### Field Selection

One of the key features of GraphQL is **field selection**, which allows clients to request only the specific fields they need. This reduces over-fetching and under-fetching of data, improving performance and network efficiency.

#### Basic Field Selection

Query a book with only specific fields:

```graphql
query {
  bookById(id: "book-1") {
    name
    genre
  }
}
```

#### Nested Field Selection

Query a book with nested author information:

```graphql
query {
  bookById(id: "book-1") {
    name
    genre
    author {
      firstName
      lastName
    }
  }
}
```

#### Minimal Field Selection

Query with only the book name:

```graphql
query {
  bookById(id: "book-1") {
    name
  }
}
```

#### Field Selection with Pagination

Query books with pagination, selecting only specific fields:

```graphql
query {
  books(first: 5) {
    edges {
      cursor
      node {
        name
        genre
        author {
          firstName
          lastName
        }
      }
    }
    pageInfo {
      hasNextPage
    }
    totalCount
  }
}
```

### Query a book by ID with all fields:

```graphql
query {
  bookById(id: "book-1") {
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

### Expected Response (Full Fields):

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

### Field Selection Response Examples:

#### Basic Field Selection Response:
```json
{
  "data": {
    "bookById": {
      "name": "The Lucky Country",
      "genre": "Non-Fiction"
    }
  }
}
```

#### Nested Field Selection Response:
```json
{
  "data": {
    "bookById": {
      "name": "The Lucky Country",
      "genre": "Non-Fiction",
      "author": {
        "firstName": "Donald",
        "lastName": "Horne"
      }
    }
  }
}
```

#### Minimal Field Selection Response:
```json
{
  "data": {
    "bookById": {
      "name": "The Lucky Country"
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

## GraphQL Queries with Filtering and Sorting

The project now supports advanced filtering and sorting capabilities for book queries:

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

### Query books with sorting:

```graphql
query {
  books(first: 5, orderBy: NAME) {
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

### Query books with search and sorting:

```graphql
query {
  books(first: 5, search: "Lucky", orderBy: PAGE_COUNT) {
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

### Query books with genre filter and sorting:

```graphql
query {
  books(first: 5, genre: "Non-Fiction", orderBy: GENRE) {
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

### Query books with combined filtering and sorting:

```graphql
query {
  books(first: 5, search: "Lucky", genre: "Non-Fiction", orderBy: NAME) {
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

### Query all genres:

```graphql
query {
  genres {
    id
    name
    description
  }
}
```

### Query genre by ID with books:

```graphql
query {
  genreById(id: "genre-1") {
    id
    name
    description
    books {
      id
      name
      pageCount
      author {
        firstName
        lastName
      }
    }
  }
}
```

### Query books by specific genre:

```graphql
query {
  booksByGenre(genreId: "genre-2", first: 5) {
    edges {
      cursor
      node {
        id
        name
        pageCount
        author {
          firstName
          lastName
        }
      }
    }
    pageInfo {
      hasNextPage
      totalCount
    }
  }
}
```

### Query book with multiple genres:

```graphql
query {
  bookById(id: "book-1") {
    id
    name
    pageCount
    genre
    genres {
      id
      name
      description
    }
    author {
      firstName
      lastName
    }
  }
}
```

### Query book with reviews and ratings:

```graphql
query {
  bookById(id: "book-1") {
    id
    name
    pageCount
    averageRating
    reviewCount
    reviews {
      id
      rating
      comment
      createdAt
      user {
        username
      }
    }
  }
}
```

### Query reviews for a specific book:

```graphql
query {
  reviewsByBook(bookId: "book-1") {
    id
    bookId
    userId
    rating
    comment
    createdAt
    user {
      username
    }
  }
}
```

### Query reviews by a specific user:

```graphql
query {
  reviewsByUser(userId: "1") {
    id
    bookId
    rating
    comment
    createdAt
    book {
      name
      author {
        firstName
        lastName
      }
    }
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

### 5. Create Genre

```graphql
mutation {
  createGenre(input: {
    name: "Young Adult"
    description: "Literature written for young adults"
  }) {
    id
    name
    description
  }
}
```

### 6. Update Genre

```graphql
mutation {
  updateGenre(id: "genre-1", input: {
    name: "Contemporary Fiction"
    description: "Modern fiction set in contemporary times"
  }) {
    id
    name
    description
  }
}
```

### 7. Delete Genre

```graphql
mutation {
  deleteGenre(id: "genre-1")
}
```

**Response**: Returns `true` if the genre was deleted successfully, `false` if the genre doesn't exist.

### 8. Add Genre to Book

```graphql
mutation {
  addGenreToBook(bookId: "book-1", genreId: "genre-1") {
    id
    name
    genres {
      id
      name
    }
  }
}
```

### 9. Remove Genre from Book

```graphql
mutation {
  removeGenreFromBook(bookId: "book-1", genreId: "genre-1") {
    id
    name
    genres {
      id
      name
    }
  }
}
```

### 10. Create Review

```graphql
mutation {
  createReview(input: {
    bookId: "book-1"
    rating: 5
    comment: "Excellent book! Highly recommended."
  }) {
    id
    bookId
    userId
    rating
    comment
    createdAt
  }
}
```

### 11. Update Review

```graphql
mutation {
  updateReview(id: "review-1", input: {
    rating: 4
    comment: "Updated comment: Great book with some minor issues."
  }) {
    id
    rating
    comment
    createdAt
  }
}
```

### 12. Delete Review

```graphql
mutation {
  deleteReview(id: "review-1")
}
```

**Note**: 
- Book, Author, and Genre mutations require a valid JWT token with ADMIN role in the Authorization header
- Review mutations require a valid JWT token (any authenticated user can create reviews, but users can only update/delete their own reviews)

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
- **books(first: Int, after: String, search: String, genre: String, orderBy: BookOrderBy)**: Retrieve books with cursor-based pagination, filtering, and sorting
- **genres**: Retrieve all genres
- **genreById(id: ID)**: Retrieve a genre by its ID
- **booksByGenre(genreId: ID!, first: Int, after: String)**: Retrieve books by specific genre with pagination
- **reviewsByBook(bookId: ID!)**: Retrieve all reviews for a specific book
- **reviewsByUser(userId: ID!)**: Retrieve all reviews by a specific user
- **reviewById(id: ID!)**: Retrieve a review by its ID

### Mutation Type
- **createBook(input: CreateBookInput!)**: Create a new book
- **createAuthor(input: CreateAuthorInput!)**: Create a new author
- **createGenre(input: CreateGenreInput!)**: Create a new genre
- **updateBook(id: ID!, input: UpdateBookInput!)**: Update an existing book
- **updateGenre(id: ID!, input: UpdateGenreInput!)**: Update an existing genre
- **deleteBook(id: ID!)**: Delete a book by ID
- **deleteGenre(id: ID!)**: Delete a genre by ID
- **addGenreToBook(bookId: ID!, genreId: ID!)**: Add a genre to a book
- **removeGenreFromBook(bookId: ID!, genreId: ID!)**: Remove a genre from a book
- **createReview(input: CreateReviewInput!)**: Create a new review
- **updateReview(id: ID!, input: UpdateReviewInput!)**: Update an existing review
- **deleteReview(id: ID!)**: Delete a review by ID

### Object Types
- **Book**: Book type with id, name, pageCount, genre, genres, author, reviews, averageRating, and reviewCount fields
- **Author**: Author type with id, firstName, and lastName fields
- **Genre**: Genre type with id, name, description, and books fields
- **Review**: Review type with id, bookId, userId, rating, comment, createdAt, book, and user fields
- **User**: User type with id, username, role, and reviews fields
- **BookConnection**: Paginated connection containing edges, pageInfo, and totalCount
- **BookEdge**: Edge containing cursor and book node
- **PageInfo**: Pagination metadata with hasNextPage, hasPreviousPage, startCursor, and endCursor

### Enum Types
- **BookOrderBy**: Sorting options for books (NAME, PAGE_COUNT, GENRE)

### Input Types
- **CreateBookInput**: Input for creating books (name, pageCount, authorId, genre)
- **CreateAuthorInput**: Input for creating authors (firstName, lastName)
- **CreateGenreInput**: Input for creating genres (name, description)
- **CreateReviewInput**: Input for creating reviews (bookId, rating, comment)
- **UpdateBookInput**: Input for updating books (optional name, pageCount, authorId, genre)
- **UpdateGenreInput**: Input for updating genres (optional name, description)
- **UpdateReviewInput**: Input for updating reviews (optional rating, comment)

## Testing

The project includes comprehensive tests for both queries and mutations:

- **Authentication Tests**: Test JWT login functionality and token validation
- **Query Tests**: Test book retrieval with and without author information (with authentication)
- **Pagination Tests**: Test cursor-based pagination functionality (with authentication)
- **Mutation Tests**: Test all CRUD operations (create, update, delete) with role-based authorization
- **Error Handling**: Test scenarios with non-existent resources and unauthorized access
- **Integration Tests**: Full application context tests with security configuration

All tests use Spring Boot Test framework, Spring Security Test, and Mockito for mocking dependencies.
