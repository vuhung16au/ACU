# Scripts Directory

This directory contains utility scripts for the Redis messaging demo project.

## Available Scripts

### `demo.sh`

A comprehensive demo script that demonstrates the Redis messaging functionality.

#### Features:
- **Automatic Redis Management**: Checks if Redis is running and starts it if needed
- **Project Building**: Automatically builds the project before running
- **Interactive Menu**: Provides multiple demo options
- **Real-time Monitoring**: Can monitor Redis messages in real-time
- **Custom Messages**: Allows sending custom messages via Redis CLI
- **Colored Output**: Uses colored output for better readability
- **Error Handling**: Comprehensive error handling and cleanup

#### Usage:
```bash
./scripts/demo.sh
```

#### Demo Flow:
1. **Redis Setup**: Automatically checks and starts Redis if needed
2. **Project Build**: Builds the project with Maven
3. **Redis Monitoring**: Starts monitoring Redis messages in background
4. **Custom Message**: Sends a custom message via Redis CLI
5. **Application Demo**: Runs the Spring Boot application (sends/receives 5 messages)
6. **Results Display**: Shows Redis activity and demo results

#### Prerequisites:
- Docker and Docker Compose installed and running
- Maven installed
- Java 17 or higher

#### What the Demo Shows:
1. **Redis Connection**: The script ensures Redis is running and accessible
2. **Message Sending**: The Spring Boot application sends messages to Redis
3. **Message Receiving**: The application receives and processes messages
4. **Real-time Communication**: Demonstrates pub/sub messaging pattern
5. **Monitoring**: Shows how to monitor Redis activity

#### Example Output:
```
==========================================
    Redis Messaging Demo Script
==========================================

[INFO] Checking if Redis server is running...
[SUCCESS] Redis server is running
[INFO] Redis server information:
# Server
redis_version:8.2.1
redis_git_sha1:00000000
redis_git_dirty:1
redis_build_id:1317a6026586c3b6
redis_mode:standalone
os:Linux 6.10.14-linuxkit aarch64
arch_bits:64
monotonic_clock:POSIX clock_gettime
multiplexing_api:epoll

[INFO] Building the project...
[SUCCESS] Project built successfully

[INFO] Running comprehensive demo - all tests in one go...

[INFO] Starting Redis monitoring in background...

[INFO] Step 1: Sending custom message via Redis CLI...
[INFO] Sending custom message via Redis CLI: 'Hello from demo script!'
[SUCCESS] Message sent successfully

[INFO] Step 2: Running Spring Boot application...
[INFO] The application will send 5 messages and receive them back

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

Received <Hello from Redis!>
Sending message...
Received <Hello from Redis!>
Sending message...
Received <Hello from Redis!>
Sending message...
Received <Hello from Redis!>
Sending message...
Received <Hello from Redis!>

[INFO] Stopping Redis monitoring...

[INFO] Redis activity during demo:
1703456789.123456 [0 172.17.0.1:12345] "PUBLISH" "chat" "Hello from demo script!"
1703456790.234567 [0 172.17.0.1:12346] "PUBLISH" "chat" "Hello from Redis!"
1703456791.345678 [0 172.17.0.1:12346] "PUBLISH" "chat" "Hello from Redis!"
1703456792.456789 [0 172.17.0.1:12346] "PUBLISH" "chat" "Hello from Redis!"
1703456793.567890 [0 172.17.0.1:12346] "PUBLISH" "chat" "Hello from Redis!"
1703456794.678901 [0 172.17.0.1:12346] "PUBLISH" "chat" "Hello from Redis!"

[SUCCESS] Demo completed! All tests executed successfully.
```

## Troubleshooting

### Common Issues:

1. **Docker not running**: Make sure Docker Desktop is started
2. **Redis connection failed**: The script will automatically start Redis if needed
3. **Maven build failed**: Ensure all dependencies are available
4. **Permission denied**: Make sure the script is executable (`chmod +x scripts/demo.sh`)

### Manual Steps:

If the script fails, you can run the steps manually:

1. Start Redis:
   ```bash
   cd docker && docker-compose up -d
   ```

2. Build the project:
   ```bash
   mvn clean compile
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Monitor Redis (in another terminal):
   ```bash
   docker exec redis-server redis-cli monitor
   ```
