# Application Properties Configuration

## Overview
The `application.properties` file contains the main configuration for the Vaadin Spring Boot application. This file is located at `src/main/resources/application.properties-sample` and needs to be copied to `application.properties` for the application to work.

## Configuration Sections

### Database Configuration (PostgreSQL)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=postgres
```

**Explanation:**
- `spring.datasource.url`: Connection URL for PostgreSQL database
- `spring.datasource.driver-class-name`: JDBC driver for PostgreSQL
- `spring.datasource.username`: Database username
- `spring.datasource.password`: Database password

### JPA Configuration
```properties
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true
```

**Explanation:**
- `spring.jpa.hibernate.ddl-auto=create`: Automatically creates database tables on startup (drops existing tables)
- `spring.jpa.show-sql=true`: Logs SQL queries to console for debugging
- `spring.jpa.properties.hibernate.dialect`: Specifies PostgreSQL dialect for Hibernate
- `spring.jpa.properties.hibernate.format_sql=true`: Formats SQL output for better readability

### Server Configuration
```properties
server.port=8080
```

**Explanation:**
- `server.port`: Sets the port on which the Spring Boot application will run

### Vaadin Configuration
```properties
vaadin.whitelisted-packages=com.acu.vaadin
```

**Explanation:**
- `vaadin.whitelisted-packages`: Specifies which packages Vaadin should scan for components and routes

## Important Notes

1. **Security**: The `application.properties` file contains sensitive information like database credentials. In production, these should be externalized using environment variables or a secure configuration management system.

2. **Database Setup**: This configuration assumes a PostgreSQL database is running on localhost:5432. Make sure the database is available before starting the application.

3. **DDL Auto**: The `create` setting will drop and recreate all tables on each application startup. For production, consider using `update` or `validate`.

4. **File Setup**: Copy `application.properties-sample` to `application.properties` in the same directory before running the application.
