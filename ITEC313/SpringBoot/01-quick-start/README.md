# 01-quick-start — Spring Boot Quick Start

A Spring Boot quick-start application demonstrating core features including Web starter, Actuator, and configuration profiles.

## Overview

This project showcases the fundamental Spring Boot features:
- **Auto-configuration**: Spring Boot automatically configures the application based on classpath
- **Embedded Server**: Runs on embedded Tomcat server
- **Actuator**: Production-ready monitoring and management endpoints
- **Profiles**: Environment-specific configurations (dev, prod)
- **REST API**: Simple REST endpoints to demonstrate web functionality

## Features

### Core Spring Boot Features
- ✅ Auto-configuration based on classpath
- ✅ Embedded Tomcat server
- ✅ Spring Boot Actuator for monitoring
- ✅ Externalized configuration with profiles
- ✅ Logging configuration
- ✅ Maven build system

### Application Endpoints
- `GET /hello` - Basic hello message
- `GET /hello/name?name=value` - Personalized hello with timestamp
- `GET /health` - Custom health check endpoint

### Actuator Endpoints
- `GET /actuator/health` - Application health status
- `GET /actuator/info` - Application information
- `GET /actuator/metrics` - Application metrics
- `GET /actuator/env` - Environment variables
- `GET /actuator/configprops` - Configuration properties

## Prerequisites

- Java 17 or higher
- Maven 3.9 or higher
- cURL or Postman for testing

## Quick Start

### 1. Build the Application

```bash
cd 01-quick-start
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
# Basic hello endpoint
curl http://localhost:8080/hello

# Personalized hello
curl "http://localhost:8080/hello/name?name=Spring"

# Health check
curl http://localhost:8080/health

# Actuator health
curl http://localhost:8080/actuator/health
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
- Basic logging
- Limited actuator endpoints
- Standard error handling

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
01-quick-start/
├── src/
│   ├── main/
│   │   ├── java/com/acu/quickstart/
│   │   │   ├── QuickStartApplication.java    # Main application class
│   │   │   └── controller/
│   │   │       └── HelloController.java      # REST controller
│   │   └── resources/
│   │       ├── application.yml               # Main configuration
│   │       ├── application-dev.yml           # Dev profile
│   │       └── application-prod.yml          # Prod profile
│   └── test/
│       └── java/com/acu/quickstart/
│           ├── QuickStartApplicationTests.java
│           └── controller/
│               └── HelloControllerTest.java
├── scripts/
│   └── test_endpoints.sh                     # Endpoint testing script
├── pom.xml                                   # Maven configuration
└── README.md                                 # This file
```

## Key Spring Boot Concepts Demonstrated

### 1. @SpringBootApplication
The main class uses `@SpringBootApplication` which combines:
- `@Configuration`: Marks the class as a source of bean definitions
- `@EnableAutoConfiguration`: Tells Spring Boot to start adding beans based on classpath
- `@ComponentScan`: Tells Spring to look for other components in the `com.acu.quickstart` package

### 2. Auto-configuration
Spring Boot automatically configures:
- Embedded Tomcat server
- Spring MVC for REST endpoints
- Actuator endpoints
- Logging with Logback

### 3. Externalized Configuration
Configuration is externalized in YAML files:
- Environment-specific profiles
- Actuator configuration
- Logging levels
- Server settings

### 4. Actuator
Provides production-ready features:
- Health checks
- Application metrics
- Environment information
- Configuration properties

## Learning Resources

### Official Documentation
- [Spring Boot Quickstart](https://spring.io/quickstart)
- [Spring Boot Project Page](https://spring.io/projects/spring-boot)
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)

### Tutorials
- [Tutorialspoint Spring Boot](https://www.tutorialspoint.com/spring_boot/index.htm)
- [GeeksforGeeks Spring Boot Roadmap](https://www.geeksforgeeks.org/springboot/best-way-to-master-spring-boot-a-complete-roadmap/)

### Tools
- [Spring Tool Suite (STS)](https://spring.io/tools)

## Next Steps

After completing this quick-start, you can explore:

1. **Day 2**: Core concepts, configuration, annotations, and logging
2. **Day 3**: Build system, web apps with Thymeleaf, and internationalization
3. **Day 4**: Advanced REST services and consuming external APIs
4. **Day 5**: Data JPA and database integration
5. **Day 6**: Kafka messaging, email, and scheduling
6. **Day 7**: Microservices and testing strategies

## Troubleshooting

### Common Issues

1. **Port already in use**: Change the port in `application.yml` or kill the process using port 8080
2. **Java version issues**: Ensure you're using Java 17 or higher
3. **Maven issues**: Run `mvn clean` and try again

### Logs

Check the console output for:
- Application startup logs
- Endpoint access logs
- Error messages

### Health Check

Use the actuator health endpoint to verify the application is running:
```bash
curl http://localhost:8080/actuator/health
```

## Contributing

This is a learning project. Feel free to experiment with:
- Adding new endpoints
- Modifying configurations
- Exploring different actuator endpoints
- Testing different profiles
