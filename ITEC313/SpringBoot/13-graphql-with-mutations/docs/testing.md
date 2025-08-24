# Testing Guide

This guide covers testing the GraphQL application, including unit tests, integration tests, and manual testing procedures.

## Test Structure

The project includes comprehensive tests located in `src/test/java/com/acu/graphql/`:

- `AuthControllerTest.java` - Authentication and authorization tests
- `BookControllerTest.java` - Book-related GraphQL operations tests
- `ReviewControllerTest.java` - Review-related GraphQL operations tests
- `GraphqlServerApplicationTests.java` - Application context tests

## Running Tests

### All Tests
```bash
mvn test
```

### Specific Test Class
```bash
mvn test -Dtest=BookControllerTest
```

### Specific Test Method
```bash
mvn test -Dtest=BookControllerTest#testCreateBook
```

## Test Categories

### 1. Unit Tests
Unit tests focus on individual components in isolation:

```java
@Test
void testCreateBook() {
    // Test book creation with valid input
    CreateBookInput input = new CreateBookInput();
    input.setName("Test Book");
    input.setPageCount(300);
    input.setAuthorId("1");
    input.setGenre("Fiction");
    
    Book result = bookController.createBook(input);
    assertNotNull(result);
    assertEquals("Test Book", result.getName());
}
```

### 2. Integration Tests
Integration tests verify component interactions:

```java
@Test
void testBookWithReviews() {
    // Test that books can be fetched with reviews (ADMIN only)
    String query = """
        query {
            bookById(id: "1") {
                id
                name
                reviews {
                    id
                    rating
                    comment
                }
            }
        }
        """;
    
    // Test with different user roles
    // ADMIN should see reviews, USER should not
}
```

### 3. Authentication Tests
Test JWT authentication and role-based access:

```java
@Test
void testAdminAccess() {
    // Test that ADMIN users can access all fields
    String token = generateAdminToken();
    // Verify access to sensitive fields like reviews
}

@Test
void testUserAccess() {
    // Test that regular users cannot access sensitive fields
    String token = generateUserToken();
    // Verify reviews field is hidden
}
```

## Manual Testing

### 1. GraphiQL Interface
Access the interactive GraphQL IDE at `http://localhost:8081/graphiql`

#### Authentication
```graphql
# First, authenticate to get a token
mutation {
  login(username: "313@acu.com", password: "123456") {
    token
    user {
      id
      username
      role
    }
  }
}
```

#### Test Queries
```graphql
# Query books with pagination
query {
  books(first: 5) {
    edges {
      node {
        id
        name
        pageCount
        genres {
          name
        }
      }
    }
    pageInfo {
      hasNextPage
      totalCount
    }
  }
}

# Query with search
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
  }
}
```

#### Test Mutations
```graphql
# Create a new book
mutation {
  createBook(input: {
    name: "Test Book"
    pageCount: 250
    authorId: "1"
    genre: "Fiction"
  }) {
    id
    name
    pageCount
  }
}

# Create a review
mutation {
  createReview(input: {
    bookId: "1"
    rating: 5
    comment: "Excellent book!"
  }) {
    id
    rating
    comment
    createdAt
  }
}
```

### 2. Role-Based Access Testing

#### Admin User (313@acu.com)
- Can see all fields including reviews
- Can perform all CRUD operations
- Can manage genres and relationships

#### Regular User
- Cannot see review details
- Limited CRUD operations
- Cannot delete books or genres

### 3. Error Handling Tests

#### Invalid Input
```graphql
# Test with invalid book ID
query {
  bookById(id: "999999") {
    id
    name
  }
}
```

#### Unauthorized Access
```graphql
# Test without authentication token
query {
  books {
    edges {
      node {
        reviews {
          id
          rating
        }
      }
    }
  }
}
```

## Test Data

The application includes sample data for testing:

- **Books**: Various books with different genres and authors
- **Users**: Admin and regular user accounts
- **Reviews**: Sample reviews for testing
- **Genres**: Different book genres

## Performance Testing

### Load Testing
Use tools like Apache JMeter or Artillery to test:

- Concurrent user requests
- Query performance with large datasets
- Mutation throughput

### Example JMeter Test Plan
1. Create a thread group with multiple users
2. Add HTTP requests for GraphQL queries
3. Include authentication headers
4. Monitor response times and throughput

## Continuous Integration

The project includes GitHub Actions workflow for automated testing:

```yaml
name: Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '17'
      - run: mvn test
```

## Best Practices

1. **Test Coverage**: Aim for >80% code coverage
2. **Test Isolation**: Each test should be independent
3. **Meaningful Assertions**: Test business logic, not just syntax
4. **Error Scenarios**: Test both success and failure cases
5. **Performance**: Include performance tests for critical paths
6. **Security**: Test authentication and authorization thoroughly

## Troubleshooting

### Common Issues

1. **Database Connection**: Ensure PostgreSQL is running
2. **Authentication**: Verify JWT token is valid and not expired
3. **CORS**: Check if requests are coming from allowed origins
4. **Memory**: Monitor heap usage during large query tests

### Debug Mode
Enable debug logging in `application.properties`:

```properties
logging.level.com.acu.graphql=DEBUG
logging.level.org.springframework.graphql=DEBUG
```

## Next Steps

- Add more comprehensive integration tests
- Implement performance benchmarks
- Add API contract testing
- Set up automated security scanning
