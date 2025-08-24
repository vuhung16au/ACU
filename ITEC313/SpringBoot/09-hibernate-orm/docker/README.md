# Docker Setup for Hibernate ORM Demonstration

This directory contains Docker Compose configuration for running the Hibernate ORM demonstration project with PostgreSQL database.

## Prerequisites

- Docker and Docker Compose installed on your system
- Port 5432 available for PostgreSQL
- Port 8080 available for pgAdmin (optional)

## Quick Start

### 1. Start PostgreSQL Database Only

```bash
# Navigate to the docker directory
cd docker

# Start PostgreSQL database
docker-compose up postgres -d

# Check if the container is running
docker-compose ps
```

### 2. Start PostgreSQL with pgAdmin (Database Management Tool)

```bash
# Start both PostgreSQL and pgAdmin
docker-compose --profile tools up -d

# Access pgAdmin at: http://localhost:8080
# Email: 313@acu.edu.au
# Password: password
```

### 3. View Logs

```bash
# View PostgreSQL logs
docker-compose logs postgres

# View pgAdmin logs (if using tools profile)
docker-compose logs pgAdmin

# Follow logs in real-time
docker-compose logs -f postgres
```

### 4. Stop Services

```bash
# Stop all services
docker-compose down

# Stop and remove volumes (WARNING: This will delete all data)
docker-compose down -v
```

## Database Connection Details

- **Host**: localhost
- **Port**: 5432
- **Database**: hibernate_demo
- **Username**: postgres
- **Password**: password
- **URL**: `jdbc:postgresql://localhost:5432/hibernate_demo`

## Spring Boot Application Configuration

The application is already configured to connect to the PostgreSQL container. The `application.yml` contains:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hibernate_demo
    username: postgres
    password: password
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
        use_sql_comments: true
```

## pgAdmin Setup (Optional)

If you started with the `tools` profile, you can access pgAdmin:

1. Open http://localhost:8080 in your browser
2. Login with:
   - Email: `313@acu.edu.au`
   - Password: `password`
3. Add a new server connection:
   - Host: `postgres` (container name)
   - Port: `5432`
   - Database: `hibernate_demo`
   - Username: `postgres`
   - Password: `password`

## Data Persistence

- PostgreSQL data is persisted in a Docker volume named `postgres_data`
- pgAdmin data is persisted in a Docker volume named `pgadmin_data`
- Data survives container restarts and removals

## Troubleshooting

### Port Already in Use
If port 5432 is already in use, modify the port mapping in `docker-compose.yml`:
```yaml
ports:
  - "5433:5432"  # Use port 5433 on host
```

### Container Won't Start
Check if the container name is already in use:
```bash
docker ps -a | grep postgres-hibernate
docker rm postgres-hibernate  # Remove if exists
```

### Database Connection Issues
Ensure the database is ready before starting your Spring Boot application:
```bash
# Wait for database to be healthy
docker-compose up postgres -d
sleep 10  # Give it time to start
```

## Development Workflow

1. Start the database: `docker-compose up postgres -d`
2. Run your Spring Boot application: `mvn spring-boot:run`
3. Watch the console output for Hibernate demonstrations
4. Use pgAdmin (optional) to inspect the database at http://localhost:8080
   - Email: `313@acu.edu.au`
   - Password: `password`
5. Stop when done: `docker-compose down`
