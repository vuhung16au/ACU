# MongoDB Docker Setup with Mongo Express

This directory contains Docker configuration for running MongoDB with Mongo Express, a web-based MongoDB admin interface.

## Services

### MongoDB
- **Image**: `mongo:7.0`
- **Port**: `27017` (host) → `27017` (container)
- **Database**: `customerdb` (initialized automatically)
- **Data Persistence**: Data is stored in a Docker volume `mongodb_data`

### Mongo Express
- **Image**: `mongo-express:1.0.0`
- **Port**: `8081` (host) → `8081` (container)
- **Web Interface**: http://localhost:8081
- **Authentication**: 
  - Username: `admin`
  - Password: `admin123`

## Getting Started

### 1. Start the Services

```bash
# Navigate to the docker directory
cd docker

# Start all services
docker-compose up -d
```

### 2. Access Mongo Express

1. Open your web browser
2. Navigate to: http://localhost:8081
3. Login with:
   - **Username**: `admin`
   - **Password**: `admin123`

### 3. Using Mongo Express

Once logged in, you can:

#### View Databases
- The left sidebar shows all databases
- Click on a database name to explore its collections

#### Explore Collections
- Click on a collection name to view its documents
- Use the query interface to filter documents
- View document structure and data types

#### Create/Edit Documents
- Click "Add Document" to create new documents
- Click on any document to edit it
- Use JSON format for document structure

#### Execute Queries
- Use the query interface to run MongoDB queries
- Support for find, aggregate, and other MongoDB operations

#### Database Operations
- Create new databases and collections
- Drop databases and collections
- Export/Import data

### 4. Stop the Services

```bash
# Stop all services
docker-compose down

# Stop and remove volumes (WARNING: This will delete all data)
docker-compose down -v
```

## Configuration

### Environment Variables

The mongo-express service is configured with the following environment variables:

- `ME_CONFIG_MONGODB_SERVER`: MongoDB server hostname (`mongodb`)
- `ME_CONFIG_MONGODB_PORT`: MongoDB port (`27017`)
- `ME_CONFIG_BASICAUTH_USERNAME`: Web interface username (`admin`)
- `ME_CONFIG_BASICAUTH_PASSWORD`: Web interface password (`admin123`)
- `ME_CONFIG_MONGODB_AUTH_DATABASE`: Authentication database (`admin`)

### Security Notes

- The current setup uses basic authentication for the web interface
- MongoDB itself is running without authentication (suitable for development)
- For production use, consider:
  - Enabling MongoDB authentication
  - Using stronger passwords
  - Restricting network access
  - Using HTTPS

## Troubleshooting

### Mongo Express Won't Start
- Ensure MongoDB container is running first
- Check if port 8081 is available on your host
- Verify Docker network connectivity

### Can't Connect to MongoDB
- Check if MongoDB container is healthy: `docker-compose ps`
- View logs: `docker-compose logs mongodb`
- Ensure no other MongoDB instance is running on port 27017

### Web Interface Issues
- Clear browser cache and cookies
- Try accessing from a different browser
- Check if firewall is blocking port 8081

## Useful Commands

```bash
# View running containers
docker-compose ps

# View logs
docker-compose logs mongodb
docker-compose logs mongo-express

# Restart services
docker-compose restart

# Rebuild and start
docker-compose up --build -d
```
