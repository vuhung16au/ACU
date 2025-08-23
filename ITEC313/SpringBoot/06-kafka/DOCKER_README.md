# Docker Development Environment

This Docker Compose setup provides a complete local development environment for the Kafka Demo Application with Apache Kafka running in KRaft mode.

## Services Included

### 1. Apache Kafka (KRaft Mode)
- **Image**: `apache/kafka:3.9.1`
- **Ports**: 
  - `9092` (external access)
  - `29092` (internal cluster communication)
  - `9101` (JMX monitoring)
- **Features**:
  - Single-node Kafka cluster using KRaft (no ZooKeeper required)
  - Auto topic creation enabled
  - JMX monitoring on port `9101`
  - Health checks configured
  - Compatible with Spring Boot Kafka starter
  - KRaft mode for improved performance and simplified deployment

### 2. Kafka UI - Web-based Kafka Management Interface
- **Image**: `provectuslabs/kafka-ui:latest`
- **Port**: `8080`
- **Features**:
  - Web-based Kafka management interface
  - Topic management and creation
  - Message browsing and production
  - Consumer group monitoring
  - Cluster health monitoring
  - Real-time message streaming

## Quick Start

### 1. Start All Services
```bash
# Start all services in detached mode
docker-compose up -d

# Or use the management script
./scripts/docker-setup.sh start

# Or start with logs
docker-compose up
```

### 2. Check Service Status
```bash
# Check if all services are running
docker-compose ps

# Check service logs
docker-compose logs -f [service-name]
```

### 3. Access Services

| Service | URL | Description |
|---------|-----|-------------|
| **Kafka UI** | http://localhost:8080 | Web-based Kafka management interface |
| **Kafka** | localhost:9092 | Kafka broker for external connections |
| **Kafka JMX** | localhost:9101 | JMX monitoring port |

### 4. Stop Services
```bash
# Stop all services
docker-compose down

# Or use the management script
./scripts/docker-setup.sh stop

# Stop and remove volumes (WARNING: This will delete all data)
docker-compose down -v

# Or use the management script for cleanup
./scripts/docker-setup.sh cleanup
```

## Kafka Configuration

### KRaft Mode Benefits
- **No ZooKeeper dependency**: Simplified architecture
- **Better performance**: Reduced latency and improved throughput
- **Easier deployment**: Single process to manage
- **Enhanced security**: Built-in security features

### Key Configuration Parameters
- **KAFKA_PROCESS_ROLES**: `broker,controller` (KRaft mode)
- **KAFKA_CONTROLLER_QUORUM_VOTERS**: `1@kafka:29093` (single node)
- **KAFKA_LISTENERS**: Configured for both internal and external access
- **KAFKA_ADVERTISED_LISTENERS**: Proper listener configuration for container networking

### Environment Variables
Copy `docker.env` to `.env` and modify as needed:
```bash
cp docker.env .env
```

### Docker Compose Version
This setup uses Docker Compose without the `version` attribute (compatible with Docker Compose v2+).

### Customizing Ports
You can change the exposed ports by modifying the `docker-compose.yml` file or setting environment variables.

### Persistent Data
All data is stored in Docker volumes:
- `kafka-data`: Kafka data and logs

## Development Workflow

### 1. Start the Infrastructure
```bash
docker-compose up -d
```

### 2. Run the Spring Boot Application
```bash
mvn spring-boot:run
```

### 3. Test the Application
```bash
# Test Kafka endpoints
curl -X POST http://localhost:8086/api/messages \
  -H "Content-Type: application/json" \
  -d '{"content": "Hello Kafka!", "sender": "test", "type": "INFO"}'

# Or use the provided test script
./scripts/test_endpoints.sh
```

### 4. Monitor with Kafka UI
- Open http://localhost:8080
- Browse topics, messages, and consumer groups
- Monitor cluster health
- Create and manage topics
- Produce and consume messages interactively

## Troubleshooting

### Common Issues

#### 1. Port Already in Use
```bash
# Check what's using the port
lsof -i :9092

# Stop conflicting services or change ports in docker-compose.yml
```

#### 2. Services Not Starting
```bash
# Check service logs
docker-compose logs [service-name]

# Check service health
docker-compose ps
```

#### 3. Kafka Connection Issues
- Ensure Kafka is healthy: `docker-compose ps kafka`
- Check logs: `docker-compose logs kafka`
- Verify bootstrap servers configuration in your application

#### 4. KRaft Mode Issues
- Verify KAFKA_PROCESS_ROLES is set to `broker,controller`
- Check KAFKA_CONTROLLER_QUORUM_VOTERS configuration
- Ensure proper listener configuration

### Useful Commands

```bash
# View all logs
docker-compose logs -f

# Or use the management script
./scripts/docker-setup.sh logs

# Restart a specific service
docker-compose restart [service-name]

# Execute commands in running containers
docker-compose exec kafka kafka-topics --bootstrap-server localhost:9092 --list
docker-compose exec kafka kafka-console-producer --bootstrap-server localhost:9092 --topic test-topic
docker-compose exec kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic test-topic --from-beginning

# Check service status
./scripts/docker-setup.sh status

# Clean up everything
docker-compose down -v --remove-orphans
docker system prune -f

# Or use the management script
./scripts/docker-setup.sh cleanup
```

## Security Notes

⚠️ **Important**: This setup is for development only!

- No authentication configured for Kafka
- No SSL/TLS encryption configured
- Services are exposed on localhost only
- Do not use in production without proper security configuration

## Performance Tuning

### For Development
The current configuration is optimized for development with:
- Single Kafka broker in KRaft mode
- Minimal resource usage
- Fast startup times
- Compatible with Spring Boot Kafka starter

### For Testing
For more realistic testing, consider:
- Increasing Kafka partitions
- Adding more Kafka brokers
- Configuring proper retention policies
- Setting up monitoring and alerting

## Management Script

A convenient management script is provided at `scripts/docker-setup.sh` with the following commands:

```bash
./scripts/docker-setup.sh start     # Start all services
./scripts/docker-setup.sh stop      # Stop all services
./scripts/docker-setup.sh restart   # Restart all services
./scripts/docker-setup.sh status    # Show service status
./scripts/docker-setup.sh logs      # Show logs (all or specific service)
./scripts/docker-setup.sh cleanup   # Stop and remove all data
./scripts/docker-setup.sh help      # Show help
```

## Next Steps

1. **Integrate with Spring Boot**: Configure your Spring Boot application to connect to Kafka
2. **Add Message Processing**: Implement Kafka producers and consumers
3. **Add Error Handling**: Implement proper error handling and retry mechanisms
4. **Set up Monitoring**: Add Prometheus and Grafana for metrics
5. **Add Security**: Implement proper authentication and authorization
6. **Scale the Cluster**: Add more Kafka brokers for production use

## Troubleshooting Notes

### Recent Changes Applied
- **Kafka Version**: Updated to `apache/kafka:3.9.1` for latest features
- **KRaft Mode**: Migrated from ZooKeeper-based to KRaft mode for simplified architecture
- **Removed Services**: PostgreSQL and pgAdmin removed to focus on Kafka functionality
- **Health Checks**: All services have proper health checks configured
- **Docker Compose**: Removed obsolete `version` attribute for modern Docker Compose compatibility

### Common Issues Resolved
- **ZooKeeper dependency**: Eliminated by using KRaft mode
- **Version attribute warning**: Removed obsolete version field
- **Service dependencies**: Proper health check-based dependencies configured
- **Simplified architecture**: Reduced complexity with KRaft mode

### KRaft Mode Benefits
- **Simplified deployment**: No separate ZooKeeper cluster needed
- **Better performance**: Reduced latency and improved throughput
- **Enhanced security**: Built-in security features
- **Easier maintenance**: Single process to manage
