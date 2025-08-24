# GraphQL Mutations

This document provides comprehensive examples and usage patterns for all GraphQL mutations in the application, including CRUD operations, relationship management, and authentication.

## Overview

Mutations in GraphQL are used for write operations - creating, updating, and deleting data. The application provides mutations for managing books, authors, genres, reviews, and user relationships.

## Authentication Mutations

### Login
Authenticate a user and receive a JWT token.

```graphql
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

**Response:**
```json
{
  "data": {
    "login": {
      "token": "eyJhbGciOiJIUzUxMiJ9...",
      "user": {
        "id": "1",
        "username": "313@acu.com",
        "role": "ADMIN"
      }
    }
  }
}
```

**Usage:**
- Include the returned token in the `Authorization` header for subsequent requests
- Format: `Authorization: Bearer <token>`

## Book Mutations

### Create Book
Create a new book with author and genre information.

```graphql
mutation {
  createBook(input: {
    name: "The Great Gatsby"
    pageCount: 180
    authorId: "1"
    genre: "Fiction"
  }) {
    id
    name
    pageCount
    author {
      id
      firstName
      lastName
    }
    genres {
      id
      name
    }
  }
}
```

**Input Fields:**
- `name`: Book title (required)
- `pageCount`: Number of pages (required)
- `authorId`: ID of the author (required)
- `genre`: Genre name (required)

**Response:**
```json
{
  "data": {
    "createBook": {
      "id": "5",
      "name": "The Great Gatsby",
      "pageCount": 180,
      "author": {
        "id": "1",
        "firstName": "F. Scott",
        "lastName": "Fitzgerald"
      },
      "genres": [
        {
          "id": "1",
          "name": "Fiction"
        }
      ]
    }
  }
}
```

### Update Book
Update an existing book's information.

```graphql
mutation {
  updateBook(id: "1", input: {
    name: "Updated Book Title"
    pageCount: 250
    authorId: "2"
    genre: "Science Fiction"
  }) {
    id
    name
    pageCount
    author {
      firstName
      lastName
    }
    genres {
      name
    }
  }
}
```

**Input Fields:** All fields are optional for updates.

**Response:**
```json
{
  "data": {
    "updateBook": {
      "id": "1",
      "name": "Updated Book Title",
      "pageCount": 250,
      "author": {
        "firstName": "Isaac",
        "lastName": "Asimov"
      },
      "genres": [
        {
          "name": "Science Fiction"
        }
      ]
    }
  }
}
```

### Delete Book
Delete a book (ADMIN only).

```graphql
mutation {
  deleteBook(id: "1")
}
```

**Response:**
```json
{
  "data": {
    "deleteBook": true
  }
}
```

**Note:** This operation is restricted to ADMIN users only.

## Author Mutations

### Create Author
Create a new author.

```graphql
mutation {
  createAuthor(input: {
    firstName: "J.K."
    lastName: "Rowling"
  }) {
    id
    firstName
    lastName
  }
}
```

**Input Fields:**
- `firstName`: Author's first name (required)
- `lastName`: Author's last name (required)

**Response:**
```json
{
  "data": {
    "createAuthor": {
      "id": "3",
      "firstName": "J.K.",
      "lastName": "Rowling"
    }
  }
}
```

## Genre Mutations

### Create Genre
Create a new genre category.

```graphql
mutation {
  createGenre(input: {
    name: "Mystery"
    description: "Detective and crime fiction novels"
  }) {
    id
    name
    description
  }
}
```

**Input Fields:**
- `name`: Genre name (required)
- `description`: Genre description (optional)

**Response:**
```json
{
  "data": {
    "createGenre": {
      "id": "4",
      "name": "Mystery",
      "description": "Detective and crime fiction novels"
    }
  }
}
```

### Update Genre
Update an existing genre.

```graphql
mutation {
  updateGenre(id: "1", input: {
    name: "Updated Genre Name"
    description: "Updated genre description"
  }) {
    id
    name
    description
  }
}
```

**Response:**
```json
{
  "data": {
    "updateGenre": {
      "id": "1",
      "name": "Updated Genre Name",
      "description": "Updated genre description"
    }
  }
}
```

### Delete Genre
Delete a genre (ADMIN only).

```graphql
mutation {
  deleteGenre(id: "1")
}
```

**Response:**
```json
{
  "data": {
    "deleteGenre": true
  }
}
```

## Relationship Mutations

### Add Genre to Book
Add a genre to a book (many-to-many relationship).

```graphql
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
```

**Arguments:**
- `bookId`: The ID of the book
- `genreId`: The ID of the genre to add

**Response:**
```json
{
  "data": {
    "addGenreToBook": {
      "id": "1",
      "name": "Harry Potter and the Philosopher's Stone",
      "genres": [
        {
          "id": "1",
          "name": "Fantasy",
          "description": "Fantasy literature"
        },
        {
          "id": "2",
          "name": "Adventure",
          "description": "Adventure stories"
        }
      ]
    }
  }
}
```

### Remove Genre from Book
Remove a genre from a book.

```graphql
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

**Response:**
```json
{
  "data": {
    "removeGenreFromBook": {
      "id": "1",
      "name": "Harry Potter and the Philosopher's Stone",
      "genres": [
        {
          "id": "1",
          "name": "Fantasy"
        }
      ]
    }
  }
}
```

## Review Mutations

### Create Review
Create a new review for a book.

```graphql
mutation {
  createReview(input: {
    bookId: "1"
    rating: 5
    comment: "Excellent book! Highly recommended for all ages."
  }) {
    id
    rating
    comment
    createdAt
    book {
      id
      name
    }
    user {
      id
      username
    }
  }
}
```

**Input Fields:**
- `bookId`: ID of the book being reviewed (required)
- `rating`: Rating from 1 to 5 stars (required)
- `comment`: Review text (optional)

**Response:**
```json
{
  "data": {
    "createReview": {
      "id": "3",
      "rating": 5,
      "comment": "Excellent book! Highly recommended for all ages.",
      "createdAt": "2024-01-15T10:30:00Z",
      "book": {
        "id": "1",
        "name": "Harry Potter and the Philosopher's Stone"
      },
      "user": {
        "id": "1",
        "username": "313@acu.com"
      }
    }
  }
}
```

### Update Review
Update an existing review.

```graphql
mutation {
  updateReview(id: "1", input: {
    rating: 4
    comment: "Updated review comment with more details."
  }) {
    id
    rating
    comment
    createdAt
  }
}
```

**Input Fields:** All fields are optional for updates.

**Response:**
```json
{
  "data": {
    "updateReview": {
      "id": "1",
      "rating": 4,
      "comment": "Updated review comment with more details.",
      "createdAt": "2024-01-10T14:20:00Z"
    }
  }
}
```

### Delete Review
Delete a review.

```graphql
mutation {
  deleteReview(id: "1")
}
```

**Response:**
```json
{
  "data": {
    "deleteReview": true
  }
}
```

## Complex Mutation Examples

### Create Book with Multiple Genres
Create a book and then add multiple genres to it.

```graphql
mutation CreateBookWithGenres {
  # Step 1: Create the book
  book: createBook(input: {
    name: "Dune"
    pageCount: 688
    authorId: "2"
    genre: "Science Fiction"
  }) {
    id
    name
    author {
      firstName
      lastName
    }
  }
  
  # Step 2: Add additional genres
  addSciFi: addGenreToBook(bookId: "5", genreId: "3") {
    id
    genres {
      name
    }
  }
  
  addAdventure: addGenreToBook(bookId: "5", genreId: "4") {
    id
    genres {
      name
    }
  }
}
```

### Batch Operations
Perform multiple operations in a single request.

```graphql
mutation BatchOperations {
  # Create multiple genres
  genre1: createGenre(input: {
    name: "Thriller"
    description: "Suspenseful and exciting stories"
  }) {
    id
    name
  }
  
  genre2: createGenre(input: {
    name: "Romance"
    description: "Love stories and romantic fiction"
  }) {
    id
    name
  }
  
  # Create a book
  book: createBook(input: {
    name: "The Notebook"
    pageCount: 214
    authorId: "3"
    genre: "Romance"
  }) {
    id
    name
  }
  
  # Add both genres to the book
  addThriller: addGenreToBook(bookId: "6", genreId: "5") {
    id
    genres {
      name
    }
  }
  
  addRomance: addGenreToBook(bookId: "6", genreId: "6") {
    id
    genres {
      name
    }
  }
}
```

## Error Handling

### Validation Errors
When input validation fails:

```graphql
mutation {
  createBook(input: {
    name: ""  # Empty name will cause validation error
    pageCount: -1  # Negative page count will cause validation error
    authorId: "999"  # Non-existent author ID
    genre: "Invalid Genre"
  }) {
    id
    name
  }
}
```

**Error Response:**
```json
{
  "errors": [
    {
      "message": "Book name is required",
      "path": ["createBook"],
      "extensions": {
        "errorType": "VALIDATION_ERROR"
      }
    },
    {
      "message": "Page count must be at least 1",
      "path": ["createBook"],
      "extensions": {
        "errorType": "VALIDATION_ERROR"
      }
    }
  ],
  "data": {
    "createBook": null
  }
}
```

### Authorization Errors
When user lacks permission:

```graphql
mutation {
  deleteBook(id: "1")  # Non-admin user trying to delete
}
```

**Error Response:**
```json
{
  "errors": [
    {
      "message": "Access denied. ADMIN role required.",
      "path": ["deleteBook"],
      "extensions": {
        "errorType": "UNAUTHORIZED"
      }
    }
  ],
  "data": {
    "deleteBook": null
  }
}
```

### Not Found Errors
When referenced entity doesn't exist:

```graphql
mutation {
  updateBook(id: "999", input: {
    name: "Updated Title"
  }) {
    id
    name
  }
}
```

**Error Response:**
```json
{
  "errors": [
    {
      "message": "Book not found with ID: 999",
      "path": ["updateBook"],
      "extensions": {
        "errorType": "NOT_FOUND"
      }
    }
  ],
  "data": {
    "updateBook": null
  }
}
```

## Best Practices

### 1. Input Validation
- Always validate input data on both client and server
- Use meaningful error messages
- Implement proper constraint checking

### 2. Authorization
- Check user permissions before performing operations
- Use role-based access control
- Implement field-level security where needed

### 3. Error Handling
- Provide clear and actionable error messages
- Use appropriate HTTP status codes
- Log errors for debugging

### 4. Performance
- Use transactions for related operations
- Implement proper database constraints
- Consider bulk operations for large datasets

### 5. Security
- Sanitize all input data
- Use parameterized queries
- Implement rate limiting
- Validate file uploads

## Testing Mutations

### Using GraphiQL
1. Open GraphiQL at `http://localhost:8081/graphiql`
2. Authenticate first using the login mutation
3. Copy the token and add it to the HTTP Headers:
   ```json
   {
     "Authorization": "Bearer <your-token>"
   }
   ```
4. Test mutations with various inputs

### Using cURL
```bash
# Login and get token
curl -X POST http://localhost:8081/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "mutation { login(username: \"313@acu.com\", password: \"123456\") { token } }"
  }'

# Use token for authenticated requests
curl -X POST http://localhost:8081/graphql \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "query": "mutation { createBook(input: { name: \"Test Book\", pageCount: 100, authorId: \"1\", genre: \"Fiction\" }) { id name } }"
  }'
```

## Common Patterns

### 1. Create and Update Pattern
```graphql
mutation CreateOrUpdate {
  # Create if doesn't exist, update if exists
  result: createBook(input: $input) {
    id
    name
    # ... other fields
  }
}
```

### 2. Conditional Operations
```graphql
mutation ConditionalOps($shouldDelete: Boolean!) {
  deleteBook(id: "1") @include(if: $shouldDelete)
}
```

### 3. Batch Processing
```graphql
mutation BatchCreate($books: [CreateBookInput!]!) {
  books: createBooks(input: $books) {
    id
    name
  }
}
```

## Future Enhancements

### Planned Mutations
- `bulkCreateBooks`: Create multiple books at once
- `uploadBookCover`: Upload book cover images
- `mergeGenres`: Merge duplicate genres
- `importBooks`: Import books from external sources
- `exportBooks`: Export books to various formats
