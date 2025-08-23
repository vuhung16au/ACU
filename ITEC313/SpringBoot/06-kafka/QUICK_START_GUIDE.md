# 🚀 Kafka Demo - Quick Start Guide

Get up and running with the Kafka Demo in under 5 minutes!

## 🎯 What You'll Get

- **Apache Kafka 3.9.1** running in KRaft mode (no ZooKeeper!)
- **Spring Boot** application with Kafka integration
- **Kafka UI** for visual monitoring and management
- **REST API** for sending and receiving messages
- **Real-time message processing** with consumer/producer patterns

## ⚡ One-Command Setup

```bash
./scripts/quick-start.sh
```

This single command will:
1. ✅ Check prerequisites (Java, Maven, Docker)
2. 🏗️ Build the Spring Boot application
3. 🐳 Start Kafka infrastructure with Docker
4. 🚀 Launch the Spring Boot application
5. 🧪 Run a live demonstration
6. 📊 Show you how to access everything

## 📋 Prerequisites

Before running the quick start, make sure you have:

### Required Tools
- **Java 17+** - [Download from Adoptium](https://adoptium.net/)
- **Maven 3.9+** - [Installation Guide](https://maven.apache.org/install.html)
- **Docker** - [Get Docker](https://www.docker.com/get-started)
- **Docker Compose** - Usually included with Docker Desktop
- **curl** - For API testing (usually pre-installed)

### Optional Tools
- **jq** - For pretty JSON formatting (`brew install jq` or `apt install jq`)

### Check Your Setup
```bash
# Check Java version (should be 17+)
java -version

# Check Maven
mvn -version

# Check Docker
docker --version
docker-compose --version

# Check curl
curl --version
```

## 🎮 Step-by-Step Manual Setup

If you prefer to run each step manually:

### Step 1: Start Kafka Infrastructure
```bash
# Start Kafka and Kafka UI
./scripts/docker-setup.sh start

# Or manually with Docker Compose
docker-compose up -d
```

### Step 2: Build and Run Application
```bash
# Build the application
mvn clean compile

# Start the Spring Boot application
mvn spring-boot:run
```

### Step 3: Test the Setup
```bash
# Run the demo script
./scripts/demo.sh

# Or run comprehensive tests
./scripts/dev-test.sh
```

## 🌐 Access Points

Once everything is running, you can access:

| Service | URL | Description |
|---------|-----|-------------|
| **Kafka UI** | http://localhost:8080 | Visual Kafka management interface |
| **API Base** | http://localhost:8086/api/messages | REST API endpoints |
| **Health Check** | http://localhost:8086/api/messages/health | Application health status |
| **Kafka Broker** | localhost:9092 | Direct Kafka connection |

## 🧪 Testing the API

### Quick Test Commands

```bash
# Health check
curl http://localhost:8086/api/messages/health

# Send a message
curl -X POST http://localhost:8086/api/messages \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Hello Kafka!",
    "sender": "QuickStart",
    "type": "INFO"
  }'

# Get all messages
curl http://localhost:8086/api/messages

# Get statistics
curl http://localhost:8086/api/messages/stats
```

### Interactive Testing

Run the interactive test mode:
```bash
./scripts/dev-test.sh interactive
```

This provides a menu-driven interface to test all API endpoints.

## 📊 Monitoring and Management

### Kafka UI Features
Navigate to http://localhost:8080 to:
- 📈 View cluster health and metrics
- 📋 Browse topics and partitions
- 💬 Send and consume messages
- 👥 Monitor consumer groups
- ⚙️ Manage Kafka configuration

### Application Logs
```bash
# View real-time application logs
tail -f application.log

# View Docker service logs
./scripts/docker-setup.sh logs

# View specific service logs
docker-compose logs -f kafka
```

## 🛠️ Development Workflow

### Typical Development Session

1. **Start Infrastructure**
   ```bash
   ./scripts/docker-setup.sh start
   ```

2. **Start Application**
   ```bash
   mvn spring-boot:run
   ```

3. **Test Changes**
   ```bash
   ./scripts/dev-test.sh smoke  # Quick test
   ./scripts/dev-test.sh        # Full test suite
   ```

4. **Monitor Activity**
   - Open http://localhost:8080 for Kafka UI
   - Watch logs: `tail -f application.log`

5. **Stop Everything**
   ```bash
   # Stop application (Ctrl+C)
   ./scripts/docker-setup.sh stop
   ```

### Making Changes

1. **Code Changes**: Edit Java files in `src/main/java/`
2. **Restart Application**: Stop with Ctrl+C, then `mvn spring-boot:run`
3. **Test Changes**: Use `./scripts/dev-test.sh`
4. **View Results**: Check Kafka UI or application logs

## 🔧 Useful Commands

### Docker Management
```bash
./scripts/docker-setup.sh start     # Start all services
./scripts/docker-setup.sh stop      # Stop all services
./scripts/docker-setup.sh status    # Check service health
./scripts/docker-setup.sh logs      # View all logs
./scripts/docker-setup.sh restart   # Restart services
./scripts/docker-setup.sh cleanup   # Remove all data
```

### Testing Scripts
```bash
./scripts/quick-start.sh             # Complete setup and demo
./scripts/demo.sh                    # API demonstration
./scripts/dev-test.sh smoke          # Quick smoke test
./scripts/dev-test.sh                # Comprehensive tests
./scripts/dev-test.sh interactive    # Interactive testing
```

### Maven Commands
```bash
mvn clean compile                    # Build application
mvn test                            # Run unit tests
mvn spring-boot:run                 # Start application
mvn clean package                   # Create JAR file
```

## 🔍 API Examples

### Send Different Message Types

**INFO Message**
```bash
curl -X POST http://localhost:8086/api/messages \
  -H "Content-Type: application/json" \
  -d '{
    "content": "System started successfully",
    "sender": "System",
    "type": "INFO"
  }'
```

**WARNING Message**
```bash
curl -X POST http://localhost:8086/api/messages \
  -H "Content-Type: application/json" \
  -d '{
    "content": "High memory usage detected",
    "sender": "Monitor",
    "type": "WARNING"
  }'
```

**ERROR Message**
```bash
curl -X POST http://localhost:8086/api/messages \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Database connection failed",
    "sender": "Database",
    "type": "ERROR"
  }'
```

### Query Messages

**Get All Messages**
```bash
curl http://localhost:8086/api/messages | jq '.'
```

**Filter by Sender**
```bash
curl http://localhost:8086/api/messages/sender/System | jq '.'
```

**Filter by Type**
```bash
curl http://localhost:8086/api/messages/type/ERROR | jq '.'
```

**Get Statistics**
```bash
curl http://localhost:8086/api/messages/stats | jq '.'
```

## 🚨 Troubleshooting

### Common Issues

**Port Already in Use**
```bash
# Check what's using port 8086
lsof -i :8086

# Or use a different port
export SERVER_PORT=8087
mvn spring-boot:run
```

**Docker Services Not Starting**
```bash
# Check Docker status
docker-compose ps

# View service logs
./scripts/docker-setup.sh logs kafka

# Restart services
./scripts/docker-setup.sh restart
```

**Application Won't Connect to Kafka**
```bash
# Verify Kafka is healthy
./scripts/docker-setup.sh status

# Check Kafka logs
docker-compose logs kafka

# Test Kafka connectivity
./scripts/dev-test.sh health
```

**Maven Build Issues**
```bash
# Clean and rebuild
mvn clean compile

# Update dependencies
mvn dependency:resolve

# Skip tests if needed
mvn spring-boot:run -Dmaven.test.skip=true
```

### Getting Help

1. **Check Logs**: Always start with `tail -f application.log`
2. **Service Status**: Use `./scripts/docker-setup.sh status`
3. **Run Tests**: Use `./scripts/dev-test.sh` to verify setup
4. **Health Check**: Visit http://localhost:8086/api/messages/health

### Reset Everything

If things get messed up, you can reset completely:

```bash
# Stop everything
./scripts/docker-setup.sh stop

# Clean up all data (WARNING: This removes all messages!)
./scripts/docker-setup.sh cleanup

# Start fresh
./scripts/quick-start.sh
```

## 📚 Next Steps

Once you have the demo running:

1. **Explore the Code**
   - Check out `src/main/java/com/acu/kafka/`
   - Look at the controller, service, and model classes

2. **Read Documentation**
   - `API_DOCUMENTATION.md` - Complete API reference
   - `DOCKER_README.md` - Docker setup details
   - `README.md` - Project overview

3. **Experiment**
   - Send different types of messages
   - Monitor them in Kafka UI
   - Try the interactive test mode

4. **Extend**
   - Add new message types
   - Implement message filtering
   - Add persistence or external integrations

## 🎉 You're Ready!

That's it! You now have a fully functional Kafka development environment. The system is designed to be:

- **Easy to start**: One command setup
- **Easy to test**: Multiple testing scripts
- **Easy to monitor**: Visual Kafka UI
- **Easy to extend**: Clean, simple codebase

Happy coding! 🚀
