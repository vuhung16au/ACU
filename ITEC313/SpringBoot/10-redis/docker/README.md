# Redis Server Management

This directory contains the Docker Compose configuration to run a Redis server for the Spring Boot messaging demo.

## Prerequisites

- Docker
- Docker Compose

## Redis Server Commands

### Start Redis Server
```bash
cd docker
docker-compose up -d
```

### Stop Redis Server
```bash
cd docker
docker-compose down
```

### Restart Redis Server
```bash
cd docker
docker-compose restart
```

### View Redis Logs
```bash
cd docker
docker-compose logs -f redis
```

### Stop and Remove All Data
```bash
cd docker
docker-compose down -v
```

## Redis Server Details

- **Image**: redis:latest
- **Port**: 6379 (mapped to host)
- **Container Name**: redis-server
- **Data Persistence**: Enabled with AOF (Append Only File)
- **Volume**: redis_data (persists data between container restarts)

## Connection Details

- **Host**: localhost
- **Port**: 6379
- **No authentication required** (for demo purposes)

## Testing Redis Connection

You can test the Redis connection using the Redis CLI:

```bash
docker exec -it redis-server redis-cli
```

Then try some basic commands:
```
PING
SET test "Hello Redis"
GET test
```
