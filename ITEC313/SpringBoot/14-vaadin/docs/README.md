# Project Documentation

This directory contains comprehensive documentation for the Vaadin Spring Boot CRUD application.

## Documentation Index

### Configuration Files
- **[Application Properties](application-properties.md)** - Main application configuration
- **[Application Test Properties](application-test-properties.md)** - Test environment configuration
- **[Docker Compose](docker-compose.md)** - Infrastructure and database setup

### Framework Documentation
- **[Vaadin Overview](vaadin-overview.md)** - Complete guide to Vaadin and Spring Boot integration

## Quick Start Guide

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose

### Setup Steps
1. **Start the database infrastructure:**
   ```bash
   cd docker
   docker-compose up -d
   ```

2. **Configure the application:**
   ```bash
   cd src/main/resources
   cp application.properties-sample application.properties
   cp application-test.properties-sample application-test.properties
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application:**
   - Main application: http://localhost:8080
   - Database admin (pgAdmin): http://localhost:8081

## Project Overview

This is a Customer Management System built with:
- **Spring Boot 3.2.0** - Backend framework
- **Vaadin 24.3.0** - UI framework
- **PostgreSQL** - Database
- **Spring Data JPA** - Data access layer

The application provides a complete CRUD (Create, Read, Update, Delete) interface for managing customer records with features like:
- Real-time filtering
- Inline editing
- Responsive design
- Type-safe development

## Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Vaadin UI     │    │  Spring Boot    │    │   PostgreSQL    │
│   Components    │◄──►│   Application   │◄──►│    Database     │
│                 │    │                 │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## Key Features

- **Server-side Java development** - No HTML/CSS/JavaScript required
- **Component-based architecture** - Reusable UI components
- **Automatic client-server communication** - AJAX handled automatically
- **Type safety** - Full Java type safety throughout
- **Responsive design** - Works on desktop and mobile
- **Database integration** - Seamless Spring Data JPA integration

## Development Workflow

1. **Database First**: Start PostgreSQL via Docker Compose
2. **Configure**: Set up application properties
3. **Develop**: Write Java code for UI and business logic
4. **Test**: Use provided test configuration
5. **Deploy**: Package as JAR or WAR file

## Troubleshooting

### Common Issues
1. **Database Connection**: Ensure PostgreSQL is running via Docker
2. **Port Conflicts**: Check if ports 8080, 8081, or 5432 are available
3. **Configuration**: Verify application.properties is properly set up
4. **Dependencies**: Ensure all Maven dependencies are resolved

### Useful Commands
```bash
# Check Docker containers
docker-compose ps

# View application logs
mvn spring-boot:run

# Clean and rebuild
mvn clean install

# Run tests
mvn test
```
