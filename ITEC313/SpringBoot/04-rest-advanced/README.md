# 04-rest-advanced — Spring Boot Advanced REST Services and External API Consumption

A comprehensive Spring Boot application demonstrating advanced REST API development with validation, HATEOAS, OpenAPI documentation, and external API consumption.

## Overview

This project showcases enterprise-level REST API development with Spring Boot:
- **Advanced REST Patterns**: HATEOAS, validation, error handling, API versioning
- **OpenAPI Documentation**: Interactive Swagger UI with comprehensive API documentation
- **External API Integration**: WebClient-based external service consumption
- **Global Exception Handling**: Centralized error handling with custom error responses
- **Data Transfer Objects**: Clean separation between API and domain models

## Features

### Advanced REST API Features
- ✅ HATEOAS (Hypermedia) support with automatic link generation
- ✅ Comprehensive validation with custom error messages
- ✅ Global exception handling with standardized error responses
- ✅ API versioning (v1) with clear endpoint structure
- ✅ Advanced HTTP methods (GET, POST, PUT, DELETE, PATCH)
- ✅ Query parameter filtering and search capabilities
- ✅ Pagination and sorting support (framework ready)

### OpenAPI/Swagger Integration
- ✅ Interactive API documentation at `/swagger-ui.html`
- ✅ OpenAPI 3.0 specification at `/api-docs`
- ✅ Comprehensive endpoint documentation
- ✅ Request/response schema documentation
- ✅ Example requests and responses

### External API Consumption
- ✅ WebClient for reactive HTTP requests
- ✅ JSONPlaceholder API integration
- ✅ Error handling for external service failures
- ✅ Reactive programming with Mono/Flux

### Data Management
- ✅ In-memory data storage with sample data
- ✅ DTO pattern for API requests/responses
- ✅ Entity validation with Bean Validation
- ✅ Business logic separation in service layer

## API Endpoints

### Product Management API (`/api/v1/products`)

#### Core CRUD Operations
- `GET /api/v1/products` - Get all products with HATEOAS links
- `GET /api/v1/products/{id}` - Get product by ID
- `POST /api/v1/products` - Create a new product
- `PUT /api/v1/products/{id}` - Update an existing product
- `DELETE /api/v1/products/{id}` - Delete a product

#### Advanced Operations
- `PATCH /api/v1/products/{id}/stock` - Update product stock
- `GET /api/v1/products/category/{category}` - Get products by category
- `GET /api/v1/products/search?name={term}` - Search products by name
- `GET /api/v1/products/statistics` - Get product statistics
- `GET /api/v1/products/categories` - Get available categories

#### Query Parameters
- `category` - Filter by product category
- `minPrice` - Minimum price filter
- `maxPrice` - Maximum price filter

### External API Integration (`/api/v1/external`)

#### JSONPlaceholder API
- `GET /api/v1/external/posts` - Get all posts
- `GET /api/v1/external/posts/{id}` - Get specific post
- `POST /api/v1/external/posts` - Create a post
- `GET /api/v1/external/users/{id}` - Get user information
- `GET /api/v1/external/posts/{postId}/comments` - Get post comments
- `GET /api/v1/external/posts/search?title={term}` - Search posts by title

### Documentation and Monitoring
- `GET /swagger-ui.html` - Interactive API documentation
- `GET /api-docs` - OpenAPI specification
- `GET /actuator/health` - Application health
- `GET /actuator/info` - Application information
- `GET /actuator/metrics` - Application metrics

## Prerequisites

- Java 17 or higher
- Maven 3.9 or higher
- Internet connection (for external API calls)
- cURL or Postman for testing

## Quick Start

### 1. Build the Application

```bash
cd 04-rest-advanced
mvn clean compile
```

### 2. Run the Application

```bash
mvn spring-boot:run
```

### 3. Access the Application

The application will start on `http://localhost:8080`

**Key URLs:**
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI Spec**: http://localhost:8080/api-docs
- **Health Check**: http://localhost:8080/actuator/health

### 4. Test the Application

**Automated testing:**
```bash
# Run the test script
./scripts/test_endpoints.sh

# Or with custom base URL
BASE_URL=http://localhost:8080 ./scripts/test_endpoints.sh
```

**Manual testing with curl:**
```bash
# Get all products with HATEOAS
curl http://localhost:8080/api/v1/products

# Create a product
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Product",
    "description": "A test product",
    "price": 99.99,
    "category": "ELECTRONICS",
    "stockQuantity": 50
  }'

# Get external posts
curl http://localhost:8080/api/v1/external/posts
```

### 5. Run Tests

```bash
mvn test
```

## Configuration

### Application Configuration
- **Server Port**: 8080
- **Context Path**: /
- **Jackson Configuration**: Optimized for API responses
- **OpenAPI**: Enabled with custom metadata

### External API Configuration
- **Base URL**: https://jsonplaceholder.typicode.com
- **Timeout**: Default WebClient timeout
- **Error Handling**: Custom error responses for external failures

## Project Structure

```
04-rest-advanced/
├── src/
│   ├── main/
│   │   ├── java/com/acu/restadvanced/
│   │   │   ├── RestAdvancedApplication.java      # Main application class
│   │   │   ├── model/
│   │   │   │   ├── Product.java                  # Product entity with validation
│   │   │   │   └── ProductCategory.java          # Product category enum
│   │   │   ├── dto/
│   │   │   │   └── ProductDto.java               # Data transfer object
│   │   │   ├── service/
│   │   │   │   ├── ProductService.java           # Business logic service
│   │   │   │   └── ExternalApiService.java       # External API service
│   │   │   ├── controller/
│   │   │   │   ├── ProductController.java        # Product REST controller
│   │   │   │   └── ExternalApiController.java    # External API controller
│   │   │   └── exception/
│   │   │       ├── GlobalExceptionHandler.java   # Global exception handler
│   │   │       ├── ErrorResponse.java            # Error response model
│   │   │       ├── ResourceNotFoundException.java # Not found exception
│   │   │       └── BusinessException.java        # Business logic exception
│   │   └── resources/
│   │       └── application.yml                   # Application configuration
│   └── test/
│       └── java/com/acu/restadvanced/
│           ├── RestAdvancedApplicationTests.java
│           └── controller/
│               └── ProductControllerTest.java    # Controller tests
├── scripts/
│   └── test_endpoints.sh                         # Endpoint testing script
├── pom.xml                                       # Maven configuration
└── README.md                                     # This file
```

## Key Spring Boot Concepts Demonstrated

### 1. HATEOAS (Hypermedia)
- **RepresentationModel**: Products extend RepresentationModel for HATEOAS support
- **Link Generation**: Automatic link generation with WebMvcLinkBuilder
- **Navigation**: Self, category, and collection links in responses

### 2. Validation and Error Handling
- **Bean Validation**: @Valid, @NotBlank, @Size, @DecimalMin, @DecimalMax
- **Global Exception Handler**: @RestControllerAdvice for centralized error handling
- **Custom Error Responses**: Standardized error format with details

### 3. OpenAPI Documentation
- **@Tag**: API grouping and organization
- **@Operation**: Endpoint descriptions and metadata
- **@ApiResponses**: Response code documentation
- **@Parameter**: Parameter descriptions and validation

### 4. External API Integration
- **WebClient**: Reactive HTTP client for external calls
- **Mono/Flux**: Reactive programming patterns
- **Error Handling**: Custom error handling for external failures
- **JSON Processing**: Automatic JSON serialization/deserialization

### 5. Advanced REST Patterns
- **API Versioning**: URL-based versioning (/api/v1/)
- **HTTP Methods**: Full CRUD with appropriate HTTP methods
- **Query Parameters**: Filtering, searching, and pagination support
- **Status Codes**: Proper HTTP status code usage

## Learning Resources

### Official Documentation
- [Spring HATEOAS](https://spring.io/projects/spring-hateoas)
- [Spring Boot Validation](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.validation)
- [Spring WebClient](https://docs.spring.io/spring-framework/reference/web/webflux-webclient.html)
- [OpenAPI with SpringDoc](https://springdoc.org/)

### Tutorials
- [Building RESTful Web Services](https://spring.io/guides/gs/rest-service/)
- [Consuming RESTful Web Services](https://spring.io/guides/gs/consuming-rest/)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

## Next Steps

After completing this advanced REST tutorial, you can explore:

1. **Day 5**: Data JPA and database integration
2. **Day 6**: Kafka messaging, email, and scheduling
3. **Day 7**: Microservices and testing strategies
