# Docker Setup Guide

## What is Docker?

**Docker** = Platform for running applications in isolated containers

**Why use Docker for databases?**
- ✅ No local installation mess
- ✅ Consistent across all dev machines
- ✅ Easy to reset/recreate
- ✅ Multiple versions simultaneously
- ✅ Matches production environment

---

## Prerequisites

### Install Docker Desktop

**macOS:**
```bash
# Download from: https://www.docker.com/products/docker-desktop
# Or use Homebrew:
brew install --cask docker
```

**Windows:**
```bash
# Download from: https://www.docker.com/products/docker-desktop
# Run installer and enable WSL 2
```

**Linux:**
```bash
# Ubuntu/Debian:
sudo apt-get update
sudo apt-get install docker.io docker-compose

# Start Docker service:
sudo systemctl start docker
sudo systemctl enable docker
```

### Verify Installation

```bash
# Check Docker version
docker --version
# Expected: Docker version 20.x or higher

# Check Docker Compose
docker-compose --version
# Expected: Docker Compose version 2.x or higher

# Test Docker
docker run hello-world
```

---

## PostgreSQL with Docker

### Method 1: Docker Compose (Recommended)

**Create `docker-compose.yml`:**

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:16-alpine
    container_name: myapp_postgres
    environment:
      POSTGRES_DB: productdb
      POSTGRES_USER: appuser
      POSTGRES_PASSWORD: devpassword
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U appuser -d productdb"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - app_network

volumes:
  postgres_data:
    driver: local

networks:
  app_network:
    driver: bridge
```

**Start PostgreSQL:**

```bash
# Start container in background
docker-compose up -d

# View logs
docker-compose logs -f postgres

# Wait for healthy status
docker ps
# STATUS column should show "healthy"

# Stop container
docker-compose down

# Stop and remove data
docker-compose down -v
```

### Method 2: Docker CLI

```bash
# Run PostgreSQL container
docker run -d \
  --name myapp_postgres \
  -e POSTGRES_DB=productdb \
  -e POSTGRES_USER=appuser \
  -e POSTGRES_PASSWORD=devpassword \
  -p 5432:5432 \
  -v postgres_data:/var/lib/postgresql/data \
  postgres:16-alpine

# Stop container
docker stop myapp_postgres

# Start existing container
docker start myapp_postgres

# Remove container
docker rm myapp_postgres
```

### Connection String

```json
// appsettings.json
{
  "ConnectionStrings": {
    "DefaultConnection": "Host=localhost;Port=5432;Database=productdb;Username=appuser;Password=devpassword"
  }
}
```

**In Program.cs:**

```csharp
builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseNpgsql(
        builder.Configuration.GetConnectionString("DefaultConnection")));
```

**Install NuGet Package:**

```bash
dotnet add package Npgsql.EntityFrameworkCore.PostgreSQL
```

### Test Connection

```bash
# Using psql (if installed locally)
psql -h localhost -p 5432 -U appuser -d productdb
# Password: devpassword

# Or connect via Docker
docker exec -it myapp_postgres psql -U appuser -d productdb

# SQL commands:
\dt          # List tables
\d Products  # Describe Products table
SELECT * FROM "Products";
\q           # Quit
```

---

## MongoDB with Docker

### Method 1: Docker Compose (Recommended)

**Create `docker-compose.yml`:**

```yaml
version: '3.8'

services:
  mongodb:
    image: mongo:7
    container_name: myapp_mongo
    environment:
      MONGO_INITDB_ROOT_USERNAME: admin
      MONGO_INITDB_ROOT_PASSWORD: admin123
      MONGO_INITDB_DATABASE: blogdb
    ports:
      - "27017:27017"
    volumes:
      - mongodb_data:/data/db
      - mongodb_config:/data/configdb
    healthcheck:
      test: ["CMD", "mongosh", "--eval", "db.adminCommand('ping')"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - app_network

volumes:
  mongodb_data:
    driver: local
  mongodb_config:
    driver: local

networks:
  app_network:
    driver: bridge
```

**Start MongoDB:**

```bash
# Start container
docker-compose up -d

# View logs
docker-compose logs -f mongodb

# Stop container
docker-compose down

# Stop and remove data
docker-compose down -v
```

### Method 2: Docker CLI

```bash
# Run MongoDB container
docker run -d \
  --name myapp_mongo \
  -e MONGO_INITDB_ROOT_USERNAME=admin \
  -e MONGO_INITDB_ROOT_PASSWORD=admin123 \
  -p 27017:27017 \
  -v mongodb_data:/data/db \
  mongo:7

# Stop/start/remove (same as PostgreSQL)
```

### Connection String

```json
// appsettings.json
{
  "MongoDB": {
    "ConnectionString": "mongodb://admin:admin123@localhost:27017",
    "DatabaseName": "blogdb"
  }
}
```

**In Program.cs:**

```csharp
builder.Services.Configure<MongoDBSettings>(
    builder.Configuration.GetSection("MongoDB"));

builder.Services.AddSingleton<IMongoClient>(sp =>
{
    var settings = sp.GetRequiredService<IOptions<MongoDBSettings>>().Value;
    return new MongoClient(settings.ConnectionString);
});
```

**Install NuGet Package:**

```bash
dotnet add package MongoDB.Driver
```

### Test Connection

```bash
# Using mongosh (if installed locally)
mongosh "mongodb://admin:admin123@localhost:27017"

# Or connect via Docker
docker exec -it myapp_mongo mongosh -u admin -p admin123

# MongoDB commands:
show dbs
use blogdb
show collections
db.posts.find()
exit
```

---

## Both PostgreSQL & MongoDB

**Complete `docker-compose.yml`:**

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:16-alpine
    container_name: myapp_postgres
    environment:
      POSTGRES_DB: productdb
      POSTGRES_USER: appuser
      POSTGRES_PASSWORD: devpassword
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U appuser"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - app_network

  mongodb:
    image: mongo:7
    container_name: myapp_mongo
    environment:
      MONGO_INITDB_ROOT_USERNAME: admin
      MONGO_INITDB_ROOT_PASSWORD: admin123
    ports:
      - "27017:27017"
    volumes:
      - mongodb_data:/data/db
    healthcheck:
      test: ["CMD", "mongosh", "--eval", "db.adminCommand('ping')"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - app_network

volumes:
  postgres_data:
  mongodb_data:

networks:
  app_network:
    driver: bridge
```

**Start both:**

```bash
docker-compose up -d
docker-compose ps  # View status
```

---

## Essential Docker Commands

### Container Management

```bash
# List running containers
docker ps

# List all containers (including stopped)
docker ps -a

# Start container
docker start <container-name>

# Stop container
docker stop <container-name>

# Restart container
docker restart <container-name>

# Remove container
docker rm <container-name>

# Force remove running container
docker rm -f <container-name>
```

### Docker Compose Commands

```bash
# Start services
docker-compose up -d

# View logs
docker-compose logs
docker-compose logs -f          # Follow logs
docker-compose logs postgres    # Specific service

# Stop services
docker-compose stop

# Stop and remove containers
docker-compose down

# Stop, remove containers and volumes (deletes data!)
docker-compose down -v

# View running services
docker-compose ps

# Restart specific service
docker-compose restart postgres

# Rebuild and start
docker-compose up -d --build
```

### Volume Management

```bash
# List volumes
docker volume ls

# Inspect volume
docker volume inspect postgres_data

# Remove volume
docker volume rm postgres_data

# Remove all unused volumes
docker volume prune
```

### Logs and Debugging

```bash
# View container logs
docker logs myapp_postgres
docker logs -f myapp_postgres  # Follow

# Execute command in container
docker exec -it myapp_postgres sh
docker exec -it myapp_postgres psql -U appuser -d productdb

# Inspect container
docker inspect myapp_postgres

# View container stats
docker stats
```

---

## Database Management Tools

### PostgreSQL

**Command-line:**
- `psql` - Official CLI client

**GUI Tools:**
- **DBeaver** (Free, cross-platform) - https://dbeaver.io/
- **pgAdmin** (Free) - https://www.pgadmin.org/
- **Azure Data Studio** (Free, Microsoft) - https://docs.microsoft.com/en-us/sql/azure-data-studio/

**Connection Details:**
- Host: `localhost`
- Port: `5432`
- Database: `productdb`
- Username: `appuser`
- Password: `devpassword`

### MongoDB

**Command-line:**
- `mongosh` - MongoDB Shell

**GUI Tools:**
- **MongoDB Compass** (Free, official) - https://www.mongodb.com/products/compass
- **Robo 3T** (Free) - https://robomongo.org/
- **Studio 3T** (Commercial, free trial) - https://studio3t.com/

**Connection Details:**
- URI: `mongodb://admin:admin123@localhost:27017`
- Authentication Database: `admin`

---

## Common Issues & Solutions

### Port Already in Use

```bash
# Find process using port 5432
lsof -i :5432

# Kill process
kill -9 <PID>

# Or change port in docker-compose.yml:
ports:
  - "5433:5432"  # Use 5433 on host
# Connection string: Host=localhost;Port=5433;...
```

### Container Won't Start

```bash
# Check logs
docker logs myapp_postgres

# Common causes:
# 1. Port conflict (see above)
# 2. Volume corruption - remove and recreate:
docker-compose down -v
docker-compose up -d
```

### Can't Connect to Database

```bash
# 1. Check container is running
docker ps

# 2. Check health status
docker ps  # Look for "healthy" in STATUS

# 3. Wait longer (10-20 seconds after start)
sleep 10

# 4. View logs for errors
docker logs myapp_postgres

# 5. Verify credentials match connection string
```

### Permission Denied (Linux)

```bash
# Add user to docker group
sudo usermod -aG docker $USER

# Log out and back in, then test:
docker ps
```

### Out of Disk Space

```bash
# Remove unused containers, images, volumes
docker system prune -a --volumes

# Warning: This removes ALL unused Docker resources!
```

---

## Development Workflow

### Typical Daily Workflow

```bash
# Morning: Start databases
docker-compose up -d

# Work on your app
dotnet run

# Evening: Stop databases
docker-compose stop

# Or leave running (uses minimal resources when idle)
```

### Reset Database

```bash
# Complete reset
docker-compose down -v        # Remove volumes
docker-compose up -d          # Recreate
dotnet ef database update     # Run migrations
```

### Project Handoff

**Include in Git:**
- ✅ `docker-compose.yml`
- ✅ `.env.example` (without real passwords)
- ✅ `README.md` with setup instructions

**Don't include:**
- ❌ `.env` (actual passwords)
- ❌ Volume data

**Teammate setup:**
```bash
git clone repo
cp .env.example .env          # Edit with local values
docker-compose up -d
dotnet ef database update
dotnet run
```

---

## Production Considerations

**Docker is great for development, but for production:**

- Use managed database services (Azure Database, AWS RDS, etc.)
- Or properly secured, backed-up Docker deployments
- Never use development `docker-compose.yml` in production
- Use secrets management (Azure Key Vault, AWS Secrets Manager)
- Configure proper networking and firewall rules

---

## Quick Reference

### PostgreSQL Quick Start

```bash
# Create docker-compose.yml (see example above)
docker-compose up -d
# Wait 10 seconds
dotnet ef database update
dotnet run
```

### MongoDB Quick Start

```bash
# Create docker-compose.yml (see example above)
docker-compose up -d
# Wait 10 seconds
dotnet run
```

### Cleanup

```bash
# Stop everything
docker-compose down

# Remove all data
docker-compose down -v

# Remove all Docker resources
docker system prune -a --volumes
```

---

## Next Steps

- Practice with [02.PostgresDocker](../02.PostgresDocker/)
- Try NoSQL with [04.MongoDBDocker](../04.MongoDBDocker/)
- Secure connections: [Security Best Practices](security-best-practices.md)
- See both databases together: [07.ComprehensiveApp](../07.ComprehensiveApp/)

---

## Key Takeaways

✅ Docker provides isolated, reproducible database environments  
✅ Use `docker-compose.yml` for easy configuration  
✅ PostgreSQL on port 5432, MongoDB on 27017  
✅ Use volumes for data persistence  
✅ Health checks ensure database is ready  
✅ Reset with `docker-compose down -v`  

**Remember:** Docker Desktop must be running to use containers!
