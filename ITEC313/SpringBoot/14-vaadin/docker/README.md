# Docker Setup for Vaadin Project

This directory contains Docker Compose configuration for running the Vaadin application with PostgreSQL database and pgAdmin.

## Services

The Docker Compose setup includes the following services:

### PostgreSQL Database
- **Image**: `postgres:17-alpine`
- **Container Name**: `vaadin-postgres`
- **Port**: `5432` (host) → `5432` (container)
- **Credentials**:
  - Username: `postgres`
  - Password: `postgres`
  - Database: `postgres`

### pgAdmin (Database Management)
- **Image**: `dpage/pgadmin4:latest`
- **Container Name**: `vaadin-pgadmin`
- **Port**: `8081` (host) → `80` (container)
- **Credentials**:
  - Email: `313@acu.com`
  - Password: `password`

## Quick Start

### 1. Start Services
```bash
# Navigate to the docker directory
cd docker

# Start all services
docker-compose up -d
```

### 2. Access Services
- **pgAdmin**: http://localhost:8081
  - Login with: `313@acu.com` / `password`
  - Add PostgreSQL server with:
    - Host: `postgres` (container name)
    - Port: `5432`
    - Username: `postgres`
    - Password: `postgres`

### 3. Stop Services
```bash
# Stop all services
docker-compose down

# Stop and remove volumes (WARNING: This will delete all data)
docker-compose down -v
```

## Useful Commands

```bash
# View running containers
docker-compose ps

# View logs
docker-compose logs

# View logs for specific service
docker-compose logs postgres
docker-compose logs pgadmin

# Restart services
docker-compose restart

# Rebuild and start services
docker-compose up -d --build
```

## Configuration

### Environment Variables
The services use the following environment variables:

**PostgreSQL:**
- `POSTGRES_USER`: Database username
- `POSTGRES_PASSWORD`: Database password
- `POSTGRES_DB`: Database name

**pgAdmin:**
- `PGADMIN_DEFAULT_EMAIL`: Admin email
- `PGADMIN_DEFAULT_PASSWORD`: Admin password

### Volumes
- `postgres_data`: Persistent storage for PostgreSQL data

## Application Configuration

Make sure your Vaadin application's `application.properties` is configured to connect to the PostgreSQL database:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## Troubleshooting

### Port Conflicts
If you get port conflicts, you can modify the port mappings in `docker-compose.yml`:
- Change `"5432:5432"` for PostgreSQL
- Change `"8081:80"` for pgAdmin

### Data Persistence
Database data is persisted in a Docker volume. To completely reset:
```bash
docker-compose down -v
docker volume rm docker_postgres_data
```

### Container Issues
```bash
# Check container status
docker-compose ps

# View detailed logs
docker-compose logs -f

# Restart specific service
docker-compose restart postgres
```
