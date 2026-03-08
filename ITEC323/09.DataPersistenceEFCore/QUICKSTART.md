# Quick Start Guide
## Data Persistence & Entity Framework Core

### Prerequisites

**Required:**
- ✅ .NET SDK 10.0 or later (includes C# 14)
- ✅ Visual Studio Code (primary) or Visual Studio 2026 (optional)
- ✅ Docker Desktop (for PostgreSQL and MongoDB projects)
- ✅ Modern web browser (Chrome, Edge, Firefox, Safari)
- ✅ Completed Week 1-8 modules

**Recommended:**
- ✅ Database management tool (DBeaver, Azure Data Studio, pgAdmin)
- ✅ MongoDB Compass (GUI for MongoDB)
- ✅ Basic SQL knowledge

### Installation Checks

```bash
# Check .NET version
dotnet --version
# Should show 10.0.x or later

# Check Docker
docker --version
# Should show 20.x or higher

docker-compose --version
# Should show 2.x or higher

# Check EF Core tools
dotnet ef --version
# If not installed, run:
dotnet tool install --global dotnet-ef
```

### Setup Steps

#### 1. Navigate to Module Folder

```bash
cd /path/to/ITEC323/09.DataPersistenceEFCore
```

#### 2. Install EF Core CLI Tools

```bash
# Install globally (one-time)
dotnet tool install --global dotnet-ef

# Verify installation
dotnet ef --version
# Should show 8.x or 9.x
```

#### 3. Install Scaffolding Tools (Optional)

```bash
# For project 05.ScaffoldingCRUD
dotnet tool install --global dotnet-aspnet-codegenerator

# Verify
dotnet aspnet-codegenerator --help
```

#### 4. Start Docker Desktop

Ensure Docker Desktop is running before starting projects 02, 04, or 07.

```bash
# Test Docker
docker ps
# Should show running containers (if any)
```

#### 5. Choose Your Starting Point

**Beginners:** Start with `01.BasicEFCore`  
**Have Docker experience:** Jump to `02.PostgresDocker`  
**Want to see comparison:** Try `04.MongoDBDocker`  
**Need full example:** Go to `07.ComprehensiveApp`

### Running Projects

#### Standard ASP.NET Projects (01, 03, 05, 06)

```bash
# Navigate to project folder
cd 01.BasicEFCore

# Restore dependencies
dotnet restore

# Apply database migrations
dotnet ef database update

# Build project
dotnet build

# Run application
dotnet run

# Open browser to:
https://localhost:5001
```

#### Docker-Based Projects (02, 04, 07)

```bash
# Navigate to project folder
cd 02.PostgresDocker

# Start database containers
docker-compose up -d

# Wait 10 seconds for database to initialize
sleep 10

# Apply migrations
dotnet ef database update

# Run application
dotnet run

# When done, stop containers
docker-compose down
```

### Common Commands

#### Entity Framework Core

```bash
# Create a new migration
dotnet ef migrations add <MigrationName>

# Apply migrations to database
dotnet ef database update

# Remove last migration (if not applied)
dotnet ef migrations remove

# View migration SQL
dotnet ef migrations script

# Drop database (careful!)
dotnet ef database drop
```

#### Docker Commands

```bash
# Start containers
docker-compose up -d

# View logs
docker-compose logs -f

# Stop containers
docker-compose down

# Stop and remove volumes (deletes data!)
docker-compose down -v

# View running containers
docker ps
```

#### User Secrets (Project 06)

```bash
# Initialize user secrets
dotnet user-secrets init

# Set a secret
dotnet user-secrets set "ConnectionStrings:DefaultConnection" "your-connection-string"

# List all secrets
dotnet user-secrets list

# Remove a secret
dotnet user-secrets remove "ConnectionStrings:DefaultConnection"

# Clear all secrets
dotnet user-secrets clear
```

### Troubleshooting

#### EF Core Tools Not Found

```bash
# Update PATH
export PATH="$PATH:$HOME/.dotnet/tools"

# Or reinstall
dotnet tool uninstall --global dotnet-ef
dotnet tool install --global dotnet-ef
```

#### Migration Errors

```bash
# Clean and rebuild
dotnet clean
dotnet build

# Remove problematic migration
dotnet ef migrations remove

# Try again
dotnet ef migrations add <Name>
```

#### Docker Connection Issues

```bash
# Check if container is running
docker ps

# View container logs
docker logs <container-name>

# Restart containers
docker-compose restart

# Ensure ports aren't in use
lsof -i :5432  # PostgreSQL
lsof -i :27017 # MongoDB
```

#### Port Already in Use

```bash
# Kill process on port 5001 (macOS/Linux)
lsof -ti:5001 | xargs kill -9

# Or run on different port
dotnet run --urls "https://localhost:5002"
```

#### Database Connection Failed

1. Check connection string in `appsettings.json`
2. Verify Docker containers are running: `docker ps`
3. Wait 10-20 seconds after `docker-compose up` for DB initialization
4. Check credentials match between Docker Compose and connection string

#### Cannot Drop Database

```bash
# Close all connections first
# Then drop
dotnet ef database drop --force
```

### Testing Database Connections

#### PostgreSQL

```bash
# Using psql (if installed)
psql -h localhost -U student -d productdb

# Or use Docker
docker exec -it <postgres-container-name> psql -U student -d productdb
```

#### MongoDB

```bash
# Using mongosh (if installed)
mongosh "mongodb://admin:admin123@localhost:27017"

# Or use Docker
docker exec -it <mongo-container-name> mongosh -u admin -p admin123
```

### Project-Specific Notes

- **01.BasicEFCore:** Uses SQLite (no Docker needed)
- **02.PostgresDocker:** Requires Docker, port 5432
- **03.LinqQueries:** Uses in-memory database (fastest)
- **04.MongoDBDocker:** Requires Docker, port 27017
- **05.ScaffoldingCRUD:** Needs `dotnet-aspnet-codegenerator` tool
- **06.SecureConnections:** Focus on security, uses user secrets
- **07.ComprehensiveApp:** Uses both PostgreSQL and MongoDB

### Performance Tips

- Use `.AsNoTracking()` for read-only queries
- Apply `.ToListAsync()` appropriately (don't over-fetch)
- Use indexes on frequently queried columns
- Use `docker-compose down -v` to reset databases during development

### Next Steps

1. Review the [README.md](README.md) for module overview
2. Read [docs/orm-basics.md](docs/orm-basics.md) for ORM concepts
3. Start with [01.BasicEFCore](01.BasicEFCore/)
4. Progress through projects sequentially
5. Complete [07.ComprehensiveApp](07.ComprehensiveApp/) for full integration

### Getting Help

- Check project-specific README.md files
- Review docs/ folder for detailed guides
- Consult EF Core documentation: https://learn.microsoft.com/en-us/ef/core/
- Ask in class or tutorials

---

**Ready?** Start with `cd 01.BasicEFCore` and follow the project's README!
