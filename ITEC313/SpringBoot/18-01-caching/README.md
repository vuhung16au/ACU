# Spring Boot Caching Demo

A simple Spring Boot application that demonstrates how to implement caching in Spring applications. This project shows how to enable caching on Spring managed beans to improve application performance.

## Project Description

This project demonstrates the use of Spring Boot's caching capabilities by implementing a simple book repository with caching. The application simulates a slow service (3-second delay) and shows how caching can dramatically improve response times for repeated requests.

### Key Features:
- **Caching Implementation**: Demonstrates `@Cacheable` annotation usage
- **Performance Improvement**: Shows the difference between cached and non-cached responses
- **Simple Book Repository**: Simulates a database with slow query performance
- **Command Line Runner**: Automatically demonstrates caching behavior on startup

## Technologies Used

- **Spring Boot 3.3.0**: Main framework for building the application
- **Spring Cache**: Caching abstraction and annotations
- **Maven**: Build tool and dependency management
- **Java 17**: Programming language
- **SLF4J**: Logging framework

## Main Concepts Used

### 1. **Spring Cache Abstraction**
- `@EnableCaching`: Enables caching support in the application
- `@Cacheable`: Caches method results based on input parameters
- Cache Manager: Manages cache operations and configuration

### 2. **Caching Benefits**
- **Performance**: Faster response times for frequently accessed data
- **Reduced Load**: Decreases load on primary data sources
- **Scalability**: Applications can handle more concurrent users

### 3. **Cache Behavior**
- **Cache Hit**: When requested data is found in cache (fast response)
- **Cache Miss**: When requested data is not in cache (slow response, then cached)
- **Cache Key**: Uses method parameters to generate unique cache keys

## How to Build, Run, and Test the Project

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Building the Project
```bash
# Clean and compile the project
mvn clean compile

# Run tests
mvn test

# Package the application
mvn package
```

### Running the Application
```bash
# Run with Maven
mvn spring-boot:run

# Or run the JAR file
java -jar target/caching-0.0.1-SNAPSHOT.jar
```

### Expected Output
When you run the application, you should see output similar to this:

```
2024-01-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.a.c.AppRunner : .... Fetching books
2024-01-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.a.c.AppRunner : isbn-978-0-14-318069-9 -->Book{isbn='978-0-14-318069-9', title='The Lucky Country'}
2024-01-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.a.c.AppRunner : isbn-978-1-4842-8761-5 -->Book{isbn='978-1-4842-8761-5', title='Spring Boot 3 and Spring Framework 6'}
2024-01-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.a.c.AppRunner : isbn-978-0-13-793510-9 -->Book{isbn='978-0-13-793510-9', title='The Art of Computer Programming, Volume 1, Fascicle 1: MMIX'}
2024-01-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.a.c.AppRunner : isbn-978-0-14-318069-9 -->Book{isbn='978-0-14-318069-9', title='The Lucky Country'}
2024-01-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.a.c.AppRunner : isbn-978-1-4842-8761-5 -->Book{isbn='978-1-4842-8761-5', title='Spring Boot 3 and Spring Framework 6'}
2024-01-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.a.c.AppRunner : isbn-978-0-13-793510-9 -->Book{isbn='978-0-13-793510-9', title='The Art of Computer Programming, Volume 1, Fascicle 1: MMIX'}
```

### Observing Caching Behavior
1. **First requests** (The Lucky Country, Spring Boot 3, Art of Computer Programming): Will take ~3 seconds each (cache miss)
2. **Subsequent requests**: Will be instant (cache hit)
3. **Notice the timing**: The first three requests will be slow, but the rest will be fast

## Project Structure

```
src/
├── main/
│   └── java/
│       └── com/
│           └── acu/
│               └── caching/
│                   ├── CachingApplication.java    # Main Spring Boot application
│                   ├── Book.java                  # Book entity
│                   ├── BookRepository.java        # Repository interface
│                   ├── SimpleBookRepository.java  # Repository implementation with caching
│                   └── AppRunner.java             # Command line runner to demonstrate caching
└── test/
    └── java/
        └── com/
            └── acu/
                └── caching/
                    └── (test files)
```

## Key Components

### 1. **CachingApplication.java**
- Main Spring Boot application class
- Enables caching with `@EnableCaching` annotation

### 2. **Book.java**
- Simple entity class representing a book
- Contains ISBN and title fields

### 3. **BookRepository.java**
- Interface defining the contract for book operations
- Defines `getByIsbn()` method

### 4. **SimpleBookRepository.java**
- Implementation of BookRepository
- Uses `@Cacheable("books")` to cache method results
- Simulates slow service with 3-second delay

### 5. **AppRunner.java**
- Implements `CommandLineRunner` to demonstrate caching
- Makes multiple requests to show cache hits and misses

## Caching Configuration

The application uses Spring Boot's default caching configuration:
- **Cache Provider**: Simple in-memory cache (ConcurrentHashMap)
- **Cache Name**: "books"
- **Cache Key**: Method parameters (ISBN in this case)

## Testing

The project includes basic tests to verify caching functionality:

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=CachingApplicationTests
```

## Customization

You can customize the caching behavior by:

1. **Changing Cache Provider**: Add dependencies for Redis, EhCache, etc.
2. **Configuring Cache Manager**: Create custom cache configuration
3. **Adding Cache Eviction**: Implement cache cleanup strategies
4. **Using Different Cache Keys**: Customize key generation

## Troubleshooting

### Common Issues:
1. **Caching not working**: Ensure `@EnableCaching` is present
2. **Slow responses**: Check if cache is properly configured
3. **Memory issues**: Consider cache size limits and TTL

### Debug Mode:
Add `logging.level.org.springframework.cache=DEBUG` to `application.properties` for detailed cache logging.

## Additional Resources

- [Spring Boot Caching Guide](https://spring.io/guides/gs/caching)
- [Spring Cache Documentation](https://docs.spring.io/spring-framework/reference/integration/cache.html)
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)

## License

This project is for educational purposes and demonstrates Spring Boot caching concepts.
