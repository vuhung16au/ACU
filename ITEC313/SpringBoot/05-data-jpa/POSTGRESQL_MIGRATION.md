# PostgreSQL Migration Guide

This document outlines the migration from MySQL to PostgreSQL for the Spring Boot Data JPA application.

## Changes Made

### 1. Dependencies Updated (`pom.xml`)

**Removed:**
- `mysql-connector-j` - MySQL JDBC driver
- `flyway-mysql` - Flyway MySQL support
- `testcontainers:mysql` - MySQL test container

**Added:**
- `postgresql` - PostgreSQL JDBC driver
- `flyway-database-postgresql` - Flyway PostgreSQL support
- `testcontainers:postgresql` - PostgreSQL test container

### 2. Database Configuration (`application-prod.yml`)

**Updated:**
- Database URL: `jdbc:postgresql://localhost:5432/postgres`
- Driver class: `org.postgresql.Driver`
- Hibernate dialect: `org.hibernate.dialect.PostgreSQLDialect`
- Default credentials: `yourUser` / `changeit`

### 3. Database Migration (`V1__Create_tables.sql`)

**Key Changes:**
- Replaced `AUTO_INCREMENT` with `BIGSERIAL` for primary keys
- Removed `ON UPDATE CURRENT_TIMESTAMP` (not supported in PostgreSQL)
- Added PostgreSQL triggers and functions for automatic `updated_at` timestamp updates
- Maintained all indexes and foreign key constraints

**New PostgreSQL Features:**
- Automatic timestamp updates via triggers
- `BIGSERIAL` for auto-incrementing primary keys
- PostgreSQL-compatible data types

## PostgreSQL Setup

### Environment Variables
The PostgreSQL configuration uses these environment variables (defined in `postgresql-pgadmin/.env`):

```
POSTGRES_USER=yourUser
POSTGRES_PW=changeit
POSTGRES_DB=postgres
PGADMIN_MAIL=your@email.com
PGADMIN_PW=changeit
```

### Starting PostgreSQL
```bash
cd postgresql-pgadmin
docker-compose up -d
```

### Accessing pgAdmin
- URL: http://localhost:5050
- Email: your@email.com
- Password: changeit

## Running the Application

### Development (H2 Database)
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Production (PostgreSQL)
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

### With Environment Variables
```bash
DB_USERNAME=yourUser DB_PASSWORD=changeit ./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

## Database Migration

The application uses Flyway for database migrations. When you start the application:

1. Flyway will automatically detect and run pending migrations
2. The `V1__Create_tables.sql` migration will create all necessary tables
3. PostgreSQL triggers will be created for automatic timestamp updates

## Testing

### Integration Tests
The test configuration uses Testcontainers with PostgreSQL:
- Tests will automatically start a PostgreSQL container
- Database is cleaned between test runs
- No manual database setup required

### Manual Testing
Use the provided test script:
```bash
./scripts/test_endpoints.sh
```

## Key Differences from MySQL

1. **Auto-increment**: Uses `BIGSERIAL` instead of `AUTO_INCREMENT`
2. **Timestamp Updates**: Uses triggers instead of `ON UPDATE CURRENT_TIMESTAMP`
3. **Data Types**: PostgreSQL-specific data types (e.g., `BOOLEAN` vs `TINYINT`)
4. **Case Sensitivity**: PostgreSQL is case-sensitive for identifiers
5. **String Functions**: Different string manipulation functions

## Troubleshooting

### Common Issues

1. **Connection Refused**: Ensure PostgreSQL container is running
2. **Authentication Failed**: Check username/password in environment variables
3. **Migration Errors**: Drop and recreate database if migration conflicts occur

### Useful Commands

```bash
# Check PostgreSQL container status
docker ps | grep postgres

# View PostgreSQL logs
docker logs postgres

# Connect to PostgreSQL directly
docker exec -it postgres psql -U yourUser -d postgres

# Reset database (if needed)
docker-compose down -v
docker-compose up -d
```

## Performance Considerations

1. **Indexes**: All necessary indexes are created by the migration
2. **Connection Pooling**: Spring Boot automatically configures HikariCP
3. **Query Optimization**: PostgreSQL query planner is generally more sophisticated than MySQL

## Security Notes

1. **Credentials**: Store database credentials in environment variables, not in code
2. **Network Access**: PostgreSQL container is exposed on localhost only
3. **pgAdmin**: Access is restricted to localhost:5050

## Next Steps

1. Test the application with PostgreSQL
2. Update any application-specific queries if needed
3. Monitor performance and adjust indexes as necessary
4. Consider setting up database backups
5. Update deployment scripts for PostgreSQL
