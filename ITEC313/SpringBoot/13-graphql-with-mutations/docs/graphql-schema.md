# GraphQL Schema Documentation

This document provides a complete reference for the GraphQL schema, including all types, queries, mutations, and their usage.

## Schema Overview

The GraphQL schema defines a book management system with the following main entities:
- **Books**: Core entity with metadata and relationships
- **Authors**: Book authors with basic information
- **Genres**: Book categories and classifications
- **Reviews**: User reviews and ratings
- **Users**: User accounts with authentication and roles

## Root Types

### Query
The root query type provides read-only access to all entities.

### Mutation
The root mutation type provides write operations for creating, updating, and deleting entities.

## Type Definitions

### Book
```graphql
type Book {
    id: ID
    name: String
    pageCount: Int
    genre: String
    genres: [Genre!]!
    author: Author
    reviews: [Review] # Field-level security: Only ADMIN users can see reviews
    averageRating: Float
    reviewCount: Int
}
```

**Fields:**
- `id`: Unique identifier for the book
- `name`: Title of the book
- `pageCount`: Number of pages in the book
- `genre`: Legacy genre field (deprecated, use `genres` instead)
- `genres`: Array of associated genres (many-to-many relationship)
- `author`: Book author (many-to-one relationship)
- `reviews`: User reviews (field-level security: ADMIN only)
- `averageRating`: Calculated average rating from all reviews
- `reviewCount`: Total number of reviews for the book

### Author
```graphql
type Author {
    id: ID
    firstName: String
    lastName: String
}
```

**Fields:**
- `id`: Unique identifier for the author
- `firstName`: Author's first name
- `lastName`: Author's last name

### Genre
```graphql
type Genre {
    id: ID
    name: String
    description: String
    books: [Book!]!
}
```

**Fields:**
- `id`: Unique identifier for the genre
- `name`: Genre name (e.g., "Fiction", "Science Fiction")
- `description`: Detailed description of the genre
- `books`: Array of books in this genre (many-to-many relationship)

### Review
```graphql
type Review {
    id: ID!
    bookId: String!
    userId: String!
    rating: Int!
    comment: String
    createdAt: String!
    book: Book
    user: User
}
```

**Fields:**
- `id`: Unique identifier for the review
- `bookId`: ID of the reviewed book
- `userId`: ID of the user who wrote the review
- `rating`: Rating from 1 to 5 stars
- `comment`: Optional review text
- `createdAt`: ISO timestamp when review was created
- `book`: Associated book object
- `user`: User who wrote the review

### User
```graphql
type User {
    id: ID!
    username: String!
    role: String!
    reviews: [Review!]!
}
```

**Fields:**
- `id`: Unique identifier for the user
- `username`: User's email/username
- `role`: User role ("ADMIN" or "USER")
- `reviews`: Array of reviews written by this user

## Query Operations

### bookById
```graphql
bookById(id: ID): Book
```

**Purpose:** Retrieve a single book by its ID.

**Arguments:**
- `id`: The unique identifier of the book

**Example:**
```graphql
query {
  bookById(id: "1") {
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

### books
```graphql
books(first: Int, after: String, search: String, genre: String, orderBy: BookOrderBy): BookConnection
```

**Purpose:** Retrieve a paginated list of books with optional filtering and sorting.

**Arguments:**
- `first`: Number of books to return (pagination limit)
- `after`: Cursor for pagination (get books after this cursor)
- `search`: Search term to filter books by name
- `genre`: Filter books by genre name
- `orderBy`: Sort order for the results

**Example:**
```graphql
query {
  books(first: 10, search: "Harry Potter", orderBy: NAME) {
    edges {
      node {
        id
        name
        pageCount
        averageRating
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
```

### genres
```graphql
genres: [Genre!]!
```

**Purpose:** Retrieve all available genres.

**Example:**
```graphql
query {
  genres {
    id
    name
    description
    books {
      id
      name
    }
  }
}
```

### genreById
```graphql
genreById(id: ID): Genre
```

**Purpose:** Retrieve a single genre by its ID.

**Arguments:**
- `id`: The unique identifier of the genre

**Example:**
```graphql
query {
  genreById(id: "1") {
    id
    name
    description
    books {
      id
      name
      author {
        firstName
        lastName
      }
    }
  }
}
```

### booksByGenre
```graphql
booksByGenre(genreId: ID!, first: Int, after: String): BookConnection
```

**Purpose:** Retrieve books filtered by a specific genre with pagination.

**Arguments:**
- `genreId`: The ID of the genre to filter by
- `first`: Number of books to return
- `after`: Cursor for pagination

**Example:**
```graphql
query {
  booksByGenre(genreId: "1", first: 5) {
    edges {
      node {
        id
        name
        pageCount
      }
    }
    pageInfo {
      hasNextPage
      totalCount
    }
  }
}
```

### reviewsByBook
```graphql
reviewsByBook(bookId: ID!): [Review!]!
```

**Purpose:** Retrieve all reviews for a specific book.

**Arguments:**
- `bookId`: The ID of the book

**Example:**
```graphql
query {
  reviewsByBook(bookId: "1") {
    id
    rating
    comment
    createdAt
    user {
      username
    }
  }
}
```

### reviewsByUser
```graphql
reviewsByUser(userId: ID!): [Review!]!
```

**Purpose:** Retrieve all reviews written by a specific user.

**Arguments:**
- `userId`: The ID of the user

**Example:**
```graphql
query {
  reviewsByUser(userId: "1") {
    id
    rating
    comment
    book {
      id
      name
    }
  }
}
```

### reviewById
```graphql
reviewById(id: ID!): Review
```

**Purpose:** Retrieve a single review by its ID.

**Arguments:**
- `id`: The unique identifier of the review

**Example:**
```graphql
query {
  reviewById(id: "1") {
    id
    rating
    comment
    book {
      name
    }
    user {
      username
    }
  }
}
```

## Mutation Operations

### createBook
```graphql
createBook(input: CreateBookInput!): Book
```

**Purpose:** Create a new book.

**Arguments:**
- `input`: Book creation data

**Example:**
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
      firstName
      lastName
    }
  }
}
```

### createAuthor
```graphql
createAuthor(input: CreateAuthorInput!): Author
```

**Purpose:** Create a new author.

**Arguments:**
- `input`: Author creation data

**Example:**
```graphql
mutation {
  createAuthor(input: {
    firstName: "F. Scott"
    lastName: "Fitzgerald"
  }) {
    id
    firstName
    lastName
  }
}
```

### createGenre
```graphql
createGenre(input: CreateGenreInput!): Genre
```

**Purpose:** Create a new genre.

**Arguments:**
- `input`: Genre creation data

**Example:**
```graphql
mutation {
  createGenre(input: {
    name: "Mystery"
    description: "Detective and crime fiction"
  }) {
    id
    name
    description
  }
}
```

### updateBook
```graphql
updateBook(id: ID!, input: UpdateBookInput!): Book
```

**Purpose:** Update an existing book.

**Arguments:**
- `id`: The ID of the book to update
- `input`: Book update data

**Example:**
```graphql
mutation {
  updateBook(id: "1", input: {
    name: "Updated Book Title"
    pageCount: 200
  }) {
    id
    name
    pageCount
  }
}
```

### updateGenre
```graphql
updateGenre(id: ID!, input: UpdateGenreInput!): Genre
```

**Purpose:** Update an existing genre.

**Arguments:**
- `id`: The ID of the genre to update
- `input`: Genre update data

**Example:**
```graphql
mutation {
  updateGenre(id: "1", input: {
    name: "Updated Genre"
    description: "Updated description"
  }) {
    id
    name
    description
  }
}
```

### deleteBook
```graphql
deleteBook(id: ID!): Boolean
```

**Purpose:** Delete a book.

**Arguments:**
- `id`: The ID of the book to delete

**Returns:** `true` if successful, `false` otherwise

**Example:**
```graphql
mutation {
  deleteBook(id: "1")
}
```

### deleteGenre
```graphql
deleteGenre(id: ID!): Boolean
```

**Purpose:** Delete a genre.

**Arguments:**
- `id`: The ID of the genre to delete

**Returns:** `true` if successful, `false` otherwise

**Example:**
```graphql
mutation {
  deleteGenre(id: "1")
}
```

### addGenreToBook
```graphql
addGenreToBook(bookId: ID!, genreId: ID!): Book
```

**Purpose:** Add a genre to a book (many-to-many relationship).

**Arguments:**
- `bookId`: The ID of the book
- `genreId`: The ID of the genre to add

**Example:**
```graphql
mutation {
  addGenreToBook(bookId: "1", genreId: "2") {
    id
    name
    genres {
      id
      name
    }
  }
}
```

### removeGenreFromBook
```graphql
removeGenreFromBook(bookId: ID!, genreId: ID!): Book
```

**Purpose:** Remove a genre from a book.

**Arguments:**
- `bookId`: The ID of the book
- `genreId`: The ID of the genre to remove

**Example:**
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

### createReview
```graphql
createReview(input: CreateReviewInput!): Review
```

**Purpose:** Create a new review for a book.

**Arguments:**
- `input`: Review creation data

**Example:**
```graphql
mutation {
  createReview(input: {
    bookId: "1"
    rating: 5
    comment: "Excellent book! Highly recommended."
  }) {
    id
    rating
    comment
    createdAt
    book {
      name
    }
  }
}
```

### updateReview
```graphql
updateReview(id: ID!, input: UpdateReviewInput!): Review
```

**Purpose:** Update an existing review.

**Arguments:**
- `id`: The ID of the review to update
- `input`: Review update data

**Example:**
```graphql
mutation {
  updateReview(id: "1", input: {
    rating: 4
    comment: "Updated review comment"
  }) {
    id
    rating
    comment
  }
}
```

### deleteReview
```graphql
deleteReview(id: ID!): Boolean
```

**Purpose:** Delete a review.

**Arguments:**
- `id`: The ID of the review to delete

**Returns:** `true` if successful, `false` otherwise

**Example:**
```graphql
mutation {
  deleteReview(id: "1")
}
```

## Input Types

### CreateBookInput
```graphql
input CreateBookInput {
    name: String!
    pageCount: Int!
    authorId: String!
    genre: String!
}
```

**Fields:**
- `name`: Book title (required)
- `pageCount`: Number of pages (required)
- `authorId`: ID of the author (required)
- `genre`: Genre name (required)

### UpdateBookInput
```graphql
input UpdateBookInput {
    name: String
    pageCount: Int
    authorId: String
    genre: String
}
```

**Fields:** All fields are optional for updates.

### CreateAuthorInput
```graphql
input CreateAuthorInput {
    firstName: String!
    lastName: String!
}
```

**Fields:**
- `firstName`: Author's first name (required)
- `lastName`: Author's last name (required)

### CreateGenreInput
```graphql
input CreateGenreInput {
    name: String!
    description: String
}
```

**Fields:**
- `name`: Genre name (required)
- `description`: Genre description (optional)

### UpdateGenreInput
```graphql
input UpdateGenreInput {
    name: String
    description: String
}
```

**Fields:** All fields are optional for updates.

### CreateReviewInput
```graphql
input CreateReviewInput {
    bookId: String!
    rating: Int!
    comment: String
}
```

**Fields:**
- `bookId`: ID of the book being reviewed (required)
- `rating`: Rating from 1 to 5 (required)
- `comment`: Review text (optional)

### UpdateReviewInput
```graphql
input UpdateReviewInput {
    rating: Int
    comment: String
}
```

**Fields:** All fields are optional for updates.

## Pagination Types

### BookConnection
```graphql
type BookConnection {
    edges: [BookEdge!]!
    pageInfo: PageInfo!
    totalCount: Int!
}
```

**Fields:**
- `edges`: Array of book edges with cursors
- `pageInfo`: Pagination metadata
- `totalCount`: Total number of books matching the query

### BookEdge
```graphql
type BookEdge {
    cursor: String!
    node: Book!
}
```

**Fields:**
- `cursor`: Opaque cursor for pagination
- `node`: The book object

### PageInfo
```graphql
type PageInfo {
    hasNextPage: Boolean!
    hasPreviousPage: Boolean!
    startCursor: String
    endCursor: String
}
```

**Fields:**
- `hasNextPage`: Whether there are more pages after this one
- `hasPreviousPage`: Whether there are pages before this one
- `startCursor`: Cursor for the first item in this page
- `endCursor`: Cursor for the last item in this page

## Enums

### BookOrderBy
```graphql
enum BookOrderBy {
    NAME
    PAGE_COUNT
    GENRE
}
```

**Values:**
- `NAME`: Sort by book name (alphabetical)
- `PAGE_COUNT`: Sort by number of pages
- `GENRE`: Sort by genre name

## Field-Level Security

The schema implements field-level security where certain fields are only visible to users with appropriate roles:

- **`Book.reviews`**: Only visible to ADMIN users
- **`Book.averageRating`**: Visible to all authenticated users
- **`Book.reviewCount`**: Visible to all authenticated users

## Error Handling

The schema includes comprehensive error handling:

- **Validation Errors**: Invalid input data
- **Authentication Errors**: Missing or invalid JWT tokens
- **Authorization Errors**: Insufficient permissions
- **Not Found Errors**: Requested resources don't exist
- **Database Errors**: Connection or constraint violations

## Best Practices

### 1. Query Optimization
- Use field selection to request only needed data
- Implement pagination for large datasets
- Use search and filtering to reduce result sets

### 2. Security
- Always authenticate before accessing protected fields
- Validate input data on both client and server
- Use role-based access control for sensitive operations

### 3. Performance
- Use cursor-based pagination for consistent results
- Implement caching for frequently accessed data
- Optimize database queries for complex relationships

### 4. Schema Design
- Use descriptive field names
- Provide meaningful descriptions for complex fields
- Follow GraphQL naming conventions
- Use appropriate scalar types

## Schema Evolution

When modifying the schema:

1. **Additive Changes**: New fields and types can be added safely
2. **Breaking Changes**: Deprecate fields before removing them
3. **Versioning**: Consider schema versioning for major changes
4. **Documentation**: Update this documentation for all changes

## Tools and IDE Support

- **GraphiQL**: Interactive GraphQL IDE at `/graphiql`
- **Schema Introspection**: Available at `/graphql` endpoint
- **Code Generation**: Use tools like GraphQL Code Generator
- **Validation**: Built-in GraphQL validation and error reporting
