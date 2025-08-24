# Authentication Guide

This guide covers JWT-based authentication and role-based access control in the GraphQL application.

## Overview

The application uses JWT (JSON Web Tokens) for stateless authentication with role-based access control:

- **ADMIN Role**: Can perform all operations (queries and mutations)
- **USER Role**: Can only perform read operations (queries)
- **Unauthenticated**: Cannot access any GraphQL endpoints

## Default Credentials

- **Username**: `313@acu.com`
- **Password**: `123456`
- **Role**: `ADMIN`

## Getting Started

### 1. Login to Get JWT Token

```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "313@acu.com",
    "password": "123456"
  }'
```

**Response**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "313@acu.com",
  "role": "ADMIN"
}
```

### 2. Use Token for GraphQL Requests

```bash
curl -X POST http://localhost:8081/graphql \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "query": "query { bookById(id: \"book-1\") { id name pageCount } }"
  }'
```

## Authentication Endpoints

### Login Endpoint

- **URL**: `POST /auth/login`
- **Content-Type**: `application/json`
- **Body**:
  ```json
  {
    "username": "313@acu.com",
    "password": "123456"
  }
  ```

### GraphQL Endpoint

- **URL**: `POST /graphql`
- **Headers**:
  - `Content-Type: application/json`
  - `Authorization: Bearer YOUR_JWT_TOKEN`
- **Body**: GraphQL query/mutation

## Role-Based Access Control

### ADMIN Role

**Capabilities**:
- ✅ All GraphQL queries
- ✅ All GraphQL mutations
- ✅ Access to sensitive fields (e.g., book reviews)
- ✅ Full CRUD operations on books, authors, genres
- ✅ Review management

**Example Queries**:
```graphql
# Can see all fields including reviews
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

# Can perform mutations
mutation {
  createBook(input: {
    name: "New Book"
    pageCount: 300
    authorId: "author-1"
    genre: "Fiction"
  }) {
    id
    name
  }
}
```

### USER Role

**Capabilities**:
- ✅ All GraphQL queries
- ❌ No GraphQL mutations
- ❌ Cannot access sensitive fields (reviews return null)
- ❌ Cannot perform CRUD operations

**Example Queries**:
```graphql
# Can query books but reviews are hidden
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

# Response: reviews field returns null
```

### Unauthenticated

**Capabilities**:
- ❌ Cannot access any GraphQL endpoints
- ❌ Cannot perform any operations

**Response**: 401 Unauthorized

## Field-Level Security

The application implements **field-level security** to hide sensitive fields based on user roles.

### Implementation

Field-level security is implemented using Spring Security's `SecurityContextHolder` to check user roles at field resolution time:

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

### Example: Reviews Field Access

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

**Response for ADMIN user**:
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

**Response for non-ADMIN user**:
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

## JWT Configuration

### Token Properties

- **Secret Key**: Configured in `application.properties`
- **Expiration**: 24 hours (86400000 milliseconds)
- **Algorithm**: HS256

### Configuration

```properties
# JWT configuration
jwt.secret=your-secret-key
jwt.expiration=86400000
```

## Security Components

### Key Classes

- `SecurityConfig.java` - Spring Security configuration
- `JwtUtil.java` - JWT utility for token management
- `JwtAuthenticationFilter.java` - JWT authentication filter
- `CustomUserDetailsService.java` - Custom user details service
- `AuthController.java` - Authentication controller for login

### Security Flow

1. **Login Request** → `AuthController.login()`
2. **Validate Credentials** → `CustomUserDetailsService`
3. **Generate JWT Token** → `JwtUtil.generateToken()`
4. **Return Token** → Client stores token
5. **GraphQL Request** → `JwtAuthenticationFilter`
6. **Validate Token** → `JwtUtil.validateToken()`
7. **Set Security Context** → Spring Security context
8. **Process Request** → GraphQL resolver with role-based access

## Error Handling

### Authentication Errors

**Invalid Credentials**:
```json
{
  "error": "Invalid username or password"
}
```

**Invalid Token**:
```json
{
  "error": "Invalid JWT token"
}
```

**Expired Token**:
```json
{
  "error": "JWT token has expired"
}
```

**Insufficient Permissions**:
```json
{
  "error": "Access denied. ADMIN role required."
}
```

## Testing Authentication

### Manual Testing

1. **Test Login**:
   ```bash
   curl -X POST http://localhost:8081/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username": "313@acu.com", "password": "123456"}'
   ```

2. **Test GraphQL with Token**:
   ```bash
   curl -X POST http://localhost:8081/graphql \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer YOUR_TOKEN" \
     -d '{"query": "query { bookById(id: \"book-1\") { id name } }"}'
   ```

3. **Test Without To
