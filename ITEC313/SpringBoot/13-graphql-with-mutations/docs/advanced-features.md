# Advanced Features

This document covers the advanced features and capabilities of the GraphQL application, including field-level security, relationships, pagination, and performance optimizations.

## Field-Level Security

The application implements sophisticated field-level security that dynamically shows or hides fields based on user roles and permissions.

### Implementation

Field-level security is implemented in the GraphQL resolvers using Spring Security context:

```java
@Component
public class BookController {
    
    @SchemaMapping(typeName = "Book", field = "reviews")
    public List<Review> getReviews(Book book, GraphQLContext context) {
        // Check if user is ADMIN
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return reviewRepository.findByBookId(book.getId().toString());
        }
        // Return null for non-admin users (field will be hidden)
        return null;
    }
}
```

### Protected Fields

| Field | Access Level | Description |
|-------|-------------|-------------|
| `Book.reviews` | ADMIN only | Detailed review information |
| `Book.averageRating` | All authenticated users | Calculated average rating |
| `Book.reviewCount` | All authenticated users | Total number of reviews |

### Security Context

The security context is maintained through JWT tokens and includes:

- **User ID**: Unique identifier for the user
- **Username**: User's email/username
- **Role**: ADMIN or USER role
- **Permissions**: Granular permissions for specific operations

## Relationship Management

### Many-to-Many Relationships

#### Books and Genres
Books can have multiple genres, and genres can be associated with multiple books:

```graphql
# Add a genre to a book
mutation {
  addGenreToBook(bookId: "1", genreId: "2") {
    id
    name
    genres {
      id
      name
      description
    }
  }
}

# Remove a genre from a book
mutation {
  removeGenreFromBook(bookId: "1", genreId: "2") {
    id
    name
    genres {
      id
      name
    }
  }
}
```

#### Implementation
```java
@MutationMapping
public Book addGenreToBook(@Argument String bookId, @Argument String genreId) {
    Book book = bookRepository.findById(Long.valueOf(bookId))
        .orElseThrow(() -> new RuntimeException("Book not found"));
    
    Genre genre = genreRepository.findById(Long.valueOf(genreId))
        .orElseThrow(() -> new RuntimeException("Genre not found"));
    
    book.getGenres().add(genre);
    return bookRepository.save(book);
}
```

### One-to-Many Relationships

#### Books and Reviews
Each book can have multiple reviews, and each review belongs to one book:

```graphql
# Get book with all reviews (ADMIN only)
query {
  bookById(id: "1") {
    id
    name
    reviews {
      id
      rating
      comment
      user {
        username
      }
    }
  }
}
```

#### Books and Authors
Each book has one author, and authors can have multiple books:

```graphql
# Get author with all books
query {
  bookById(id: "1") {
    id
    name
    author {
      id
      firstName
      lastName
    }
  }
}
```

## Cursor-Based Pagination

The application implements cursor-based pagination following the Relay specification for efficient and consistent pagination.

### Pagination Structure

```graphql
type BookConnection {
    edges: [BookEdge!]!
    pageInfo: PageInfo!
    totalCount: Int!
}

type BookEdge {
    cursor: String!
    node: Book!
}

type PageInfo {
    hasNextPage: Boolean!
    hasPreviousPage: Boolean!
    startCursor: String
    endCursor: String
}
```

### Implementation

```java
@QueryMapping
public BookConnection books(
    @Argument Integer first,
    @Argument String after,
    @Argument String search,
    @Argument String genre,
    @Argument BookOrderBy orderBy
) {
    // Parse cursor for pagination
    String cursor = after != null ? decodeCursor(after) : null;
    
    // Build query with pagination
    Pageable pageable = PageRequest.of(0, first != null ? first : 10);
    
    // Execute query with filters
    Page<Book> bookPage = bookRepository.findBooksWithFilters(
        search, genre, orderBy, pageable, cursor);
    
    // Build connection response
    return buildBookConnection(bookPage);
}
```

### Usage Examples

```graphql
# First page of books
query {
  books(first: 5) {
    edges {
      node {
        id
        name
        pageCount
      }
      cursor
    }
    pageInfo {
      hasNextPage
      hasPreviousPage
      totalCount
    }
  }
}

# Next page using cursor
query {
  books(first: 5, after: "eyJpZCI6NX0=") {
    edges {
      node {
        id
        name
        pageCount
      }
      cursor
    }
    pageInfo {
      hasNextPage
      hasPreviousPage
    }
  }
}
```

## Search and Filtering

### Text Search

The application supports full-text search across book names and descriptions:

```graphql
# Search for books containing "Harry Potter"
query {
  books(search: "Harry Potter", first: 10) {
    edges {
      node {
        id
        name
        author {
          firstName
          lastName
        }
      }
    }
    pageInfo {
      totalCount
    }
  }
}
```

### Genre Filtering

Filter books by specific genres:

```graphql
# Get books in the "Fiction" genre
query {
  books(genre: "Fiction", first: 10) {
    edges {
      node {
        id
        name
        pageCount
      }
    }
  }
}
```

### Combined Filters

Combine multiple filters for precise queries:

```graphql
# Search for science fiction books with pagination
query {
  books(
    search: "space"
    genre: "Science Fiction"
    orderBy: PAGE_COUNT
    first: 5
  ) {
    edges {
      node {
        id
        name
        pageCount
        averageRating
      }
    }
    pageInfo {
      totalCount
      hasNextPage
    }
  }
}
```

## Sorting and Ordering

### Available Sort Options

```graphql
enum BookOrderBy {
    NAME        # Alphabetical by book name
    PAGE_COUNT  # By number of pages
    GENRE       # By genre name
}
```

### Implementation

```java
public enum BookOrderBy {
    NAME("b.name"),
    PAGE_COUNT("b.pageCount"),
    GENRE("g.name");
    
    private final String sortField;
    
    BookOrderBy(String sortField) {
        this.sortField = sortField;
    }
    
    public String getSortField() {
        return sortField;
    }
}
```

### Usage

```graphql
# Sort books by page count (ascending)
query {
  books(orderBy: PAGE_COUNT, first: 10) {
    edges {
      node {
        id
        name
        pageCount
      }
    }
  }
}
```

## Performance Optimizations

### Database Query Optimization

#### N+1 Query Prevention
The application uses JPA's `@EntityGraph` and custom queries to prevent N+1 query problems:

```java
@EntityGraph(attributePaths = {"author", "genres"})
@Query("SELECT b FROM Book b WHERE " +
       "(:search IS NULL OR LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
       "(:genre IS NULL OR EXISTS (SELECT 1 FROM b.genres g WHERE LOWER(g.name) = LOWER(:genre)))")
Page<Book> findBooksWithFilters(
    @Param("search") String search,
    @Param("genre") String genre,
    Pageable pageable);
```

#### Indexing Strategy
Database indexes are created for optimal query performance:

```sql
-- Index for book search
CREATE INDEX idx_books_name ON books(LOWER(name));

-- Index for genre filtering
CREATE INDEX idx_book_genres ON book_genres(book_id, genre_id);

-- Index for pagination
CREATE INDEX idx_books_id ON books(id);
```

### Caching Strategy

#### Application-Level Caching
```java
@Cacheable("genres")
@QueryMapping
public List<Genre> genres() {
    return genreRepository.findAll();
}

@CacheEvict("genres")
@MutationMapping
public Genre createGenre(@Argument CreateGenreInput input) {
    // Create genre logic
}
```

#### HTTP Response Caching
```java
@RestController
public class BookController {
    
    @GetMapping("/api/books/{id}")
    @CacheControl(maxAge = 300) // Cache for 5 minutes
    public Book getBook(@PathVariable String id) {
        return bookService.findById(id);
    }
}
```

## Error Handling and Validation

### GraphQL Error Handling

Custom error handler provides meaningful error messages:

```java
@Component
public class GraphQLErrorHandler implements DataFetcherExceptionResolver {
    
    @Override
    public List<GraphQLError> resolveExceptions(DataFetchingEnvironment env, Exception exception) {
        if (exception instanceof BookNotFoundException) {
            return List.of(
                GraphqlErrorBuilder.newError()
                    .errorType(ErrorType.NOT_FOUND)
                    .message("Book not found with ID: " + exception.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .build()
            );
        }
        
        // Handle other exceptions...
        return null;
    }
}
```

### Input Validation

Comprehensive input validation using Bean Validation:

```java
public class CreateBookInput {
    @NotBlank(message = "Book name is required")
    @Size(min = 1, max = 255, message = "Book name must be between 1 and 255 characters")
    private String name;
    
    @NotNull(message = "Page count is required")
    @Min(value = 1, message = "Page count must be at least 1")
    @Max(value = 10000, message = "Page count cannot exceed 10000")
    private Integer pageCount;
    
    @NotBlank(message = "Author ID is required")
    private String authorId;
    
    @NotBlank(message = "Genre is required")
    private String genre;
}
```

## Monitoring and Observability

### Query Performance Monitoring

Track GraphQL query performance:

```java
@Component
public class GraphQLPerformanceInterceptor implements DataFetcherInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(GraphQLPerformanceInterceptor.class);
    
    @Override
    public CompletableFuture<Object> intercept(DataFetchingEnvironment env, DataFetcherInterceptorContext context) {
        long startTime = System.currentTimeMillis();
        
        return context.invokeNext().whenComplete((result, throwable) -> {
            long duration = System.currentTimeMillis() - startTime;
            String fieldName = env.getField().getName();
            
            logger.info("GraphQL field '{}' executed in {}ms", fieldName, duration);
            
            if (duration > 1000) {
                logger.warn("Slow GraphQL query detected: {} took {}ms", fieldName, duration);
            }
        });
    }
}
```

### Health Checks

Application health monitoring:

```java
@Component
public class GraphQLHealthIndicator implements HealthIndicator {
    
    @Override
    public Health health() {
        try {
            // Check database connectivity
            // Check GraphQL schema validity
            // Check authentication service
            
            return Health.up()
                .withDetail("graphql", "operational")
                .withDetail("database", "connected")
                .build();
        } catch (Exception e) {
            return Health.down()
                .withDetail("error", e.getMessage())
                .build();
        }
    }
}
```

## Security Features

### JWT Token Management

Advanced JWT token handling with refresh capabilities:

```java
@Component
public class JwtUtil {
    
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()));
        
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
            .compact();
    }
    
    public String refreshToken(String token) {
        // Implement token refresh logic
    }
}
```

### Rate Limiting

Protect against abuse with rate limiting:

```java
@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    
    private final RateLimiter rateLimiter = RateLimiter.create(100.0); // 100 requests per second
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!rateLimiter.tryAcquire()) {
            response.setStatus(429); // Too Many Requests
            return false;
        }
        return true;
    }
}
```

## Future Enhancements

### Real-time Subscriptions

Planned GraphQL subscription support for real-time updates:

```graphql
subscription {
  bookUpdated(bookId: "1") {
    id
    name
    averageRating
    reviewCount
  }
}
```

### File Upload Support

GraphQL file upload capabilities:

```graphql
mutation {
  uploadBookCover(bookId: "1", file: "cover.jpg") {
    id
    name
    coverUrl
  }
}
```

### Advanced Search

Full-text search with Elasticsearch integration:

```graphql
query {
  searchBooks(query: "space exploration", filters: {
    genres: ["Science Fiction", "Non-Fiction"]
    minRating: 4.0
    publishedAfter: "2020-01-01"
  }) {
    edges {
      node {
        id
        name
        relevanceScore
      }
    }
  }
}
```

### Bulk Operations

Efficient bulk operations for large datasets:

```graphql
mutation {
  bulkCreateBooks(input: [
    { name: "Book 1", pageCount: 200, authorId: "1" },
    { name: "Book 2", pageCount: 300, authorId: "2" }
  ]) {
    createdCount
    errors {
      index
      message
    }
  }
}
```

## Best Practices

### 1. Performance
- Use pagination for large datasets
- Implement proper database indexing
- Cache frequently accessed data
- Monitor query performance

### 2. Security
- Validate all inputs
- Implement proper authentication
- Use field-level security
- Rate limit API requests

### 3. Maintainability
- Follow consistent naming conventions
- Document complex business logic
- Write comprehensive tests
- Use meaningful error messages

### 4. Scalability
- Design for horizontal scaling
- Use connection pooling
- Implement caching strategies
- Monitor resource usage
