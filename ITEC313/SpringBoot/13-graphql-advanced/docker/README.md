# Docker Setup for PostgreSQL and pgAdmin

This directory contains Docker configuration to run PostgreSQL and pgAdmin for the GraphQL project.

## Prerequisites

- Docker
- Docker Compose

## Quick Start

1. Navigate to the docker directory:
   ```bash
   cd docker
   ```

2. Start the services:
   ```bash
   docker-compose up -d
   ```

3. The services will be available at:
   - PostgreSQL: `localhost:5432`
   - pgAdmin: `http://localhost:8080`

## Database Configuration

- **Database Name**: `graphql_db`
- **Username**: `postgres`
- **Password**: `postgres`
- **Host**: `localhost`
- **Port**: `5432`

## pgAdmin Access

- **URL**: http://localhost:8080
- **Email**: 313@acu.com
- **Password**: password

### Connecting to PostgreSQL in pgAdmin

1. Open pgAdmin in your browser
2. Login with the credentials above
3. Right-click on "Servers" → "Register" → "Server"
4. In the "General" tab, enter a name (e.g., "GraphQL PostgreSQL")
5. In the "Connection" tab, enter:
   - Host: `postgres` (use the service name from docker-compose)
   - Port: `5432`
   - Database: `graphql_db`
   - Username: `postgres`
   - Password: `postgres`

## Stopping the Services

```bash
docker-compose down
```

To remove all data (volumes):
```bash
docker-compose down -v
```

## Container Names

- PostgreSQL: `acu-graphql-postgres`
- pgAdmin: `acu-graphql-pgadmin`

## Data Persistence

The PostgreSQL data is persisted in a Docker volume named `postgres_data`. This ensures your data survives container restarts.
