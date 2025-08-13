# 02-core-config — Spring Boot Core Concepts

A comprehensive Spring Boot application demonstrating core concepts including configuration management, annotations, dependency injection, and logging.

## Overview

This project showcases the fundamental Spring Boot concepts:
- **Configuration Management**: Externalized configuration with profiles and `@ConfigurationProperties`
- **Annotations**: Core Spring annotations for dependency injection and component scanning
- **Dependency Injection**: Constructor injection, field injection, and qualifiers
- **Logging**: Structured logging with different levels and profiles
- **Bean Configuration**: Custom bean definitions and configuration classes

## Features

### Core Spring Boot Concepts
- ✅ `@SpringBootApplication` and auto-configuration
- ✅ `@Configuration` and `@Bean` for custom beans
- ✅ `@ConfigurationProperties` with validation
- ✅ `@Service`, `@RestController`, and `@Autowired`
- ✅ Externalized configuration with YAML
- ✅ Profile-based configuration (dev, prod)
- ✅ Structured logging with SLF4J/Logback
- ✅ Actuator endpoints for monitoring

### Application Endpoints
- `GET /api/config` - Get all application configuration
- `GET /api/config/timestamp` - Get current timestamp
- `GET /api/config/timestamp/{format}` - Get timestamp with specific format
- `POST /api/config/log` - Trigger configuration logging
- `GET /api/config/health` - Health check endpoint

### Actuator Endpoints
- `GET /actuator/health` - Application health status
- `GET /actuator/info` - Application information
- `GET /actuator/configprops` - Configuration properties
- `GET /actuator/env` - Environment variables

## Prerequisites

- Java 17 or higher
- Maven 3.9 or higher
- cURL or Postman for testing

## Quick Start

### 1. Build the Application

```bash
cd 02-core-config
mvn clean compile
```

### 2. Run the Application

**Default profile:**
```bash
mvn spring-boot:run
```

**Development profile (enhanced logging, all actuator endpoints):**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

**Production profile (minimal logging, secure actuator):**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### 3. Test the Application

The application will start on `http://localhost:8080`

**Manual testing with curl:**
```bash
# Get all configuration
curl http://localhost:8080/api/config

# Get timestamp
curl http://localhost:8080/api/config/timestamp

# Get timestamp with short format
curl http://localhost:8080/api/config/timestamp/short

# Trigger logging
curl -X POST "http://localhost:8080/api/config/log?level=debug"

# Health check
curl http://localhost:8080/api/config/health

# Actuator endpoints
curl http://localhost:8080/actuator/health
curl http://localhost:8080/actuator/configprops
```

**Automated testing:**
```bash
# Run the test script
./scripts/test_endpoints.sh

# Or with custom base URL
BASE_URL=http://localhost:8080 ./scripts/test_endpoints.sh
```

### 4. Run Tests

```bash
mvn test
```

## Configuration

### Profiles

The application supports multiple profiles for different environments:

#### Default Profile
- Basic configuration
- Standard logging levels
- Limited actuator endpoints

#### Development Profile (`dev`)
- Enhanced debug logging
- All actuator endpoints exposed
- Detailed error messages
- Full environment information

#### Production Profile (`prod`)
- Minimal logging (WARN level)
- Secure actuator configuration
- No sensitive information exposed
- Optimized for production

### Configuration Files

- `application.yml` - Main configuration
- `application-dev.yml` - Development profile overrides
- `application-prod.yml` - Production profile overrides

## Project Structure

```
02-core-config/
├── src/
│   ├── main/
│   │   ├── java/com/acu/coreconfig/
│   │   │   ├── CoreConfigApplication.java     # Main application class
│   │   │   ├── config/
│   │   │   │   ├── AppConfig.java             # Configuration class with @Bean
│   │   │   │   └── AppProperties.java         # @ConfigurationProperties
│   │   │   ├── service/
│   │   │   │   └── ConfigService.java         # Service with @Service
│   │   │   └── controller/
│   │   │       └── ConfigController.java      # REST controller
│   │   └── resources/
│   │       ├── application.yml                # Main configuration
│   │       ├── application-dev.yml            # Dev profile
│   │       └── application-prod.yml           # Prod profile
│   └── test/
│       └── java/com/acu/coreconfig/
│           ├── CoreConfigApplicationTests.java
│           └── controller/
│               └── ConfigControllerTest.java
├── scripts/
│   └── test_endpoints.sh                      # Endpoint testing script
├── pom.xml                                    # Maven configuration
└── README.md                                  # This file
```

## Key Spring Boot Concepts Demonstrated

### 1. @SpringBootApplication
The main class uses `@SpringBootApplication` which combines:
- `@Configuration`: Marks the class as a source of bean definitions
- `@EnableAutoConfiguration`: Tells Spring Boot to start adding beans based on classpath
- `@ComponentScan`: Tells Spring to look for other components in the package

### 2. @Configuration and @Bean
The `AppConfig` class demonstrates:
- `@Configuration`: Marks the class as a configuration class
- `@Bean`: Defines beans that will be managed by Spring
- `@Primary`: Specifies the preferred bean when multiple candidates exist
- `@Qualifier`: Allows selection of specific beans by name

### 3. @ConfigurationProperties
The `AppProperties` class shows:
- `@ConfigurationProperties(prefix = "app")`: Binds external configuration
- `@Validated`: Enables validation of configuration properties
- Validation annotations: `@NotBlank`, `@Min`, `@Max`
- Type-safe configuration binding

### 4. Dependency Injection
Demonstrated through:
- Constructor injection with `@Autowired`
- Field injection with `@Value`
- Qualifier-based injection with `@Qualifier`
- Service layer with `@Service`

### 5. Logging
Configured with:
- SLF4J/Logback for logging
- Different log levels per package
- Profile-based logging configuration
- File and console logging
- Structured log patterns

### 6. REST Controllers
The `ConfigController` demonstrates:
- `@RestController`: Combines `@Controller` and `@ResponseBody`
- `@RequestMapping`: Base path mapping
- `@GetMapping`, `@PostMapping`: HTTP method mapping
- `@PathVariable`, `@RequestParam`: Parameter binding

## Learning Resources

### Official Documentation
- [Spring Boot External Configuration](https://docs.spring.io/spring-boot/reference/features/external-config.html)
- [Spring Boot Logging](https://docs.spring.io/spring-boot/reference/features/logging.html)
- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Spring Boot Maven Plugin](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/)

### Tutorials
- [Tutorialspoint Spring Boot](https://www.tutorialspoint.com/spring_boot/index.htm)
- [GeeksforGeeks Spring Boot Annotations](https://www.geeksforgeeks.org/advance-java/spring-boot/)

## Next Steps

After completing this core concepts tutorial, you can explore:

1. **Day 3**: Build system, web apps with Thymeleaf, and internationalization
2. **Day 4**: Advanced REST services and consuming external APIs
3. **Day 5**: Data JPA and database integration
4. **Day 6**: Kafka messaging, email, and scheduling
5. **Day 7**: Microservices and testing strategies

## Troubleshooting

### Common Issues

1. **Configuration not loading**: Check that `@ConfigurationProperties` class is in component scan
2. **Bean not found**: Ensure `@Component`, `@Service`, or `@Configuration` annotations are present
3. **Logging not working**: Verify logback configuration and log levels
4. **Profile not switching**: Check `spring.profiles.active` property

### Logs

Check the console output and log files for:
- Application startup logs
- Configuration loading logs
- Endpoint access logs
- Error messages

### Health Check

Use the actuator health endpoint to verify the application is running:
```bash
curl http://localhost:8080/actuator/health
```

## Contributing

This is a learning project. Feel free to experiment with:
- Adding new configuration properties
- Creating additional beans
- Modifying logging configurations
- Exploring different actuator endpoints
- Testing different profiles
