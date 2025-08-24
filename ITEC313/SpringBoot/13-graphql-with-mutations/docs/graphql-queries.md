# GraphQL Queries

This guide covers all available GraphQL queries with examples and field selection techniques.

## Overview

The GraphQL API provides powerful querying capabilities with:
- **Field Selection**: Request only the fields you need
- **Nested Field Selection**: Access related object fields
- **Pagination**: Cursor-based pagination for large datasets
- **Filtering**: Search by name, filter by genre
- **Sorting**: Sort by name, page count, genre
- **Combined Operations**: Filter and sort together

## Field Selection

One of the key features of GraphQL is **field selection**, which allows clients to request only the specific fields they need. This reduces over-fetching and under-fetching of data.

### Basic Field Selection

Query a book with only specific fields:

```graphql
query {
  bookById(id: "book-1") {
    name
    genre
  }
}
```

**Response**:
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

### Nested Field Selection

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

**Response**:
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

### Minimal Field Selection

Query with only the book name:

```graphql
query {
  bookById(id: "book-1") {
    name
  }
}
```

**Response**:
```json
{
  "data": {
    "bookById": {
      "name": "The Lucky Country"
    }
  }
}
```

## Basic Queries

### Query Book by ID

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

**Response**:
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

### Query All Books with Pagination

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

**Response**:
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

### Query Next Page

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

## Filtering Queries

### Search Books by Name

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
      totalCount
    }
  }
}
```

### Filter Books by Genre

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
      totalCount
    }
  }
}
```

### Combined Search and Genre Filter

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
      totalCount
    }
  }
}
```

## Sorting Queries

### Sort Books by Name

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
      totalCount
    }
  }
}
```

### Sort Books by Page Count

```graphql
query {
  books(first: 5, orderBy: PAGE_COUNT) {
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
      totalCount
    }
  }
}
```

### Sort Books by Genre

```graphql
query {
  books(first: 5, orderBy: GENRE) {
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
      totalCount
    }
  }
}
```

## Combined Filtering and Sorting

### Search, Filter, and Sort

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
      totalCount
    }
  }
}
```

## Genre Queries

### Query All Genres

```graphql
query {
  genres {
    id
    name
    description
  }
}
```

**Response**:
```json
{
  "data": {
    "genres": [
      {
        "id": "genre-1",
        "name": "Fiction",
        "description": "Imaginative literature"
      },
      {
        "id": "genre-2",
        "name": "Non-Fiction",
        "description": "Factual literature"
      }
    ]
  }
}
```

### Query Genre by ID with Books

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

### Query Books by Specific Genre

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

## Book with Multiple Genres

### Query Book with Genre Relationships

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

**Response**:
```json
{
  "data": {
    "bookById": {
      "id": "book-1",
      "name": "The Lucky Country",
      "pageCount": 300,
      "genre": "Non-Fiction",
      "genres": [
        {
          "id": "genre-2",
          "name": "Non-Fiction",
          "description": "Factual literature"
        },
        {
          "id": "genre-3",
          "name": "History",
          "description": "Historical literature"
        }
      ],
      "author": {
        "firstName": "Donald",
        "lastName": "Horne"
      }
    }
  }
}
```

## Review Queries

### Query Book with Reviews (ADMIN Only)

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

**Note**: The `reviews` field is only visible to ADMIN users due to field-level security.

### Query Reviews for a Specific Book

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

### Query Reviews by a Specific User

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

## Performance Optimization

### Field Selection Benefits

1. **Reduced Network Traffic**: Only requested fields are sent
2. **Faster Response Times**: Less data processing
3. **Bandwidth Savings**: Especially important for mobile clients
4. **Flexible Queries**: Same endpoint for different use cases

### Best Practices

1. **Request Only Needed Fields**: Don't over-fetch data
2. **Use Nested Selection**: Get related data in one query
3. **Leverage Pagination**: For large datasets
4. **Combine Filters**: Use multiple filters together
5. **Cache Results**: On client side when appropriate

## Error Handling

### Common Query Errors

**Book Not Found**:
```json
{
  "errors": [
    {
      "message": "Book not found with id: book-999",
      "path": ["bookById"]
    }
  ]
}
```

**Invalid ID Format**:
```json
{
  "errors": [
    {
      "message": "Invalid book ID format",
      "path": ["bookById"]
    }
  ]
}
```

**Authentication Required**:
```json
{
  "errors": [
    {
      "message": "Access denied",
      "path": ["books"]
    }
  ]
}
```

## Testing Queries

### Using GraphiQL

1. **Access GraphiQL**: `http://localhost:8081/graphiql`
2. **Add Authorization Header**:
   ```json
   {
     "Authorization": "Bearer YOUR_JWT_TOKEN"
   }
   ```
3. **Test Queries**: Copy and paste queries from this guide
4. **Explore Schema**: Use the documentation explorer

### Using curl

```bash
# Get JWT token first
TOKEN=$(curl -s -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "313@acu.com", "password": "123456"}' | jq -r '.token')

# Test query
curl -X POST http://localhost:8081/graphql \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"query": "query { bookById(id: \"book-1\") { id name } }"}'
```
```

```markdown:docs/graphql-mutations.md
# GraphQL Mutations

This guide covers all available GraphQL mutations for CRUD operations. **All mutations require ADMIN role authentication**.

## Overview

The GraphQL API provides comprehensive mutation capabilities for:
- **Book Management**: Create, update, delete books
- **Author Management**: Create new authors
- **Genre Management**: Create, update, delete genres
- **Genre Relationships**: Add/remove genres from books
- **Review Management**: Create, update, delete reviews

## Authentication

All mutations require a valid JWT token with ADMIN role in the Authorization header:

```
Authorization: Bearer YOUR_JWT_TOKEN
```

## Book Mutations

### 1. Create Book

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

**Response**:
```json
{
  "data": {
    "createBook": {
      "id": "book-2003",
      "name": "Pride and Prejudice",
      "pageCount": 432,
      "genre": "Romance",
      "author": {
        "id": "author-1",
        "firstName": "Donald",
        "lastName": "Horne"
      }
    }
  }
}
```

### 2. Update Book

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

**Response**:
```json
{
  "data": {
    "updateBook": {
      "id": "book-1",
      "name": "The Lucky Country - Updated Edition",
      "pageCount": 320,
      "genre": "History",
      "author": {
        "id": "author-1",
        "firstName": "Donald",
        "lastName": "Horne"
      }
    }
  }
}
```

### 3. Delete Book

```graphql
mutation {
  deleteBook(id: "book-1")
}
```

**Response**:
```json
{
  "data": {
    "deleteBook": true
  }
}
```

**Note**: Returns `true` if the book was deleted successfully, `false` if the book doesn't exist.

## Author Mutations

### Create Author

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

**Response**:
```json
{
  "data": {
    "createAuthor": {
      "id": "author-1003",
      "firstName": "Jane",
      "lastName": "Austen"
    }
  }
}
```

## Genre Mutations

### 1. Create Genre

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

**Response**:
```json
{
  "data": {
    "createGenre": {
      "id": "genre-16",
      "name": "Young Adult",
      "description": "Literature written for young adults"
    }
  }
}
```

### 2. Update Genre

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

**Response**:
```json
{
  "data": {
    "updateGenre": {
      "id": "genre-1",
      "name": "Contemporary Fiction",
      "description": "Modern fiction set in contemporary times"
    }
  }
}
```

### 3. Delete Genre

```graphql
mutation {
  deleteGenre(id: "genre-1")
}
```

**Response**:
```json
{
  "data": {
    "deleteGenre": true
  }
}
```

**Note**: Returns `true` if the genre was deleted successfully, `false` if the genre doesn't exist.

## Genre Relationship Mutations

### 1. Add Genre to Book

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

**Response**:
```json
{
  "data": {
    "addGenreToBook": {
      "id": "book-1",
      "name": "The Lucky Country",
      "genres": [
        {
          "id": "genre-1",
          "name": "Fiction"
        },
        {
          "id": "genre-2",
          "name": "Non-Fiction"
        }
      ]
    }
  }
}
```

### 2. Remove Genre from Book

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

**Response**:
```json
{
  "data": {
    "removeGenreFromBook": {
      "id": "book-1",
      "name": "The Lucky Country",
      "genres": [
        {
          "id": "genre-2",
          "name": "Non-Fiction"
        }
      ]
    }
  }
}
```

## Review Mutations

### 1. Create Review

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

**Response**:
```json
{
  "data": {
    "createReview": {
      "id": "review-1001",
      "bookId": "book-1",
      "userId": 1,
      "rating": 5,
      "comment": "Excellent book! Highly recommended.",
      "createdAt": "2024-01-15T10:30:00Z"
    }
  }
}
```

### 2. Update Review

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

**Response**:
```json
{
  "data": {
    "updateReview": {
      "id": "review-1",
      "rating": 4,
      "comment": "Updated comment: Great book with some minor issues.",
      "createdAt": "2024-01-15T10:30:00Z"
    }
  }
}
```

### 3. Delete Review

```graphql
mutation {
  deleteReview(id: "review-1")
}
```

**Response**:
```json
{
  "data": {
    "deleteReview": true
  }
}
```

**Note**: 
- Book, Author, and Genre mutations require ADMIN role
- Review mutations require any authenticated user (users can only update/delete their own reviews)

## Input Types

### CreateBookInput

```graphql
input CreateBookInput {
  name: String!
  pageCount: Int
  authorId: String!
  genre: String
}
```

### UpdateBookInput

```graphql
input UpdateBookInput {
  name: String
  pageCount: Int
  authorId: String
  genre: String
}
```

### CreateAuthorInput

```graphql
input CreateAuthorInput {
  firstName: String!
  lastName: String!
}
```

### CreateGenreInput

```graphql
input CreateGenreInput {
  name: String!
  description: String
}
```

### UpdateGenreInput

```graphql
input UpdateGenreInput {
  name: String
  description: String
}
```

### CreateReviewInput

```graphql
input CreateReviewInput {
  bookId: String!
  rating: Int!
  comment: String
}
```

### UpdateReviewInput

```graphql
input UpdateReviewInput {
  rating: Int
  comment: String
}
```

## Error Handling

### Common Mutation Errors

**Book Not Found**:
```json
{
  "errors": [
    {
      "message": "Book not found with id: book-999",
      "path": ["updateBook"]
    }
  ]
}
```

**Author Not Found**:
```json
{
  "errors": [
    {
      "message": "Author not found with id: author-999",
      "path": ["createBook"]
    }
  ]
}
```

**Genre Already Exists**:
```json
{
  "errors": [
    {
      "message": "Genre with name 'Fiction' already exists",
      "path": ["createGenre"]
    }
  ]
}
```

**Invalid Rating**:
```json
{
  "errors": [
    {
      "message": "Rating must be between 1 and 5",
      "path": ["createReview"]
    }
  ]
}
```

**Unauthorized Access**:
```json
{
  "errors": [
    {
      "message": "Access denied. ADMIN role required.",
      "path": ["createBook"]
    }
  ]
}
```

**Review Ownership**:
```json
{
  "errors": [
    {
      "message": "You can only update your own reviews",
      "path": ["updateReview"]
    }
  ]
}
```

## Testing Mutations

### Using GraphiQL

1. **Access GraphiQL**: `http://localhost:8081/graphiql`
2. **Add Authorization Header**:
   ```json
   {
     "Authorization": "Bearer YOUR_JWT_TOKEN"
   }
   ```
3. **Test Mutations**: Copy and paste mutations from this guide
4. **Verify Results**: Check the response and query the updated data

### Using curl

```bash
# Get JWT token first
TOKEN=$(curl -s -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "313@acu.com", "password": "123456"}' | jq -r '.token')

# Test mutation
curl -X POST http://localhost:8081/graphql \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"query": "mutation { createBook(input: { name: \"New Book\", pageCount: 300, authorId: \"author-1\", genre: \"Fiction\" }) { id name pageCount genre author { id firstName lastName } } }"}'
```
```

