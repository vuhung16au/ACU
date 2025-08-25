# Spring Boot Docker Demo

This project demonstrates how to containerize a Spring Boot 3.5.5 application using Docker. It's a simple web application that returns "Hello Docker World" when accessed.

## Technologies Used

- **Spring Boot 3.5.5**: A framework for building production-ready applications with Spring
- **Java 21**: The programming language used for the application
- **Maven**: Build tool and dependency management
- **Docker**: Containerization platform for packaging and running the application
- **OpenJDK 21 Slim**: Lightweight base image for the Docker container

## Project Structure

```
17-01-docker/
├── src/main/java/hello/
│   └── Application.java          # Main Spring Boot application
├── .mvn/wrapper/
│   └── maven-wrapper.properties  # Maven wrapper configuration
├── pom.xml                       # Maven project configuration
├── Dockerfile                    # Docker image definition
├── mvnw                          # Maven wrapper script
├── .gitignore                    # Git ignore rules
└── README.md                     # This file
```

## Prerequisites

- Java 21 or higher
- Docker (optional, for containerization)
- Git (for version control)

## Building the Project

### Using Maven Wrapper (Recommended)

```bash
# Make the Maven wrapper executable (first time only)
chmod +x mvnw

# Build the project
./mvnw clean package
```

### Using Maven (if installed globally)

```bash
mvn clean package
```

The build process will:
1. Compile the Java source code
2. Run tests (if any)
3. Create a JAR file in the `target/` directory

## Running the Application

### Option 1: Run as JAR file

```bash
# Build and run in one command
./mvnw package && java -jar target/gs-spring-boot-docker-0.1.0.jar
```

### Option 2: Run with Maven

```bash
./mvnw spring-boot:run
```

The application will start on port 8080 by default.

## Testing the Application

Once the application is running, you can test it by:

1. **Using curl:**
   ```bash
   curl http://localhost:8080/
   ```
   Expected output: `Hello Docker World`

2. **Using a web browser:**
   Navigate to `http://localhost:8080/`

3. **Using wget:**
   ```bash
   wget -qO- http://localhost:8080/
   ```

## Docker Containerization

### Building the Docker Image

```bash
# Build the JAR file first
./mvnw package

# Build the Docker image
docker build -t spring-boot-docker .
```

### Running the Docker Container

```bash
# Run the container
docker run -p 8080:8080 spring-boot-docker
```

### Testing the Docker Container

```bash
# Test the containerized application
curl http://localhost:8080/
```

Expected output: `Hello Docker World`

## Docker Commands Reference

```bash
# List running containers
docker ps

# Stop a running container
docker stop <container_id>

# Remove a container
docker rm <container_id>

# Remove an image
docker rmi spring-boot-docker

# View container logs
docker logs <container_id>
```

## Alternative Dockerfile Examples

The project includes three different Dockerfile approaches:

1. **Simple approach** (current): Uses a single JAR file
2. **Security-focused**: Runs as non-root user
3. **Layered approach**: Separates dependencies for better caching

To use alternative Dockerfiles, rename them and rebuild:

```bash
# Example for security-focused approach
cp Dockerfile Dockerfile.secure
# Edit Dockerfile.secure to use the second sample
docker build -f Dockerfile.secure -t spring-boot-docker-secure .
```

## Development

### Adding Dependencies

Edit `pom.xml` to add new dependencies:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

### Running Tests

```bash
./mvnw test
```

### Code Formatting

```bash
./mvnw spring-javaformat:apply
```

## Troubleshooting

### Common Issues

1. **Port already in use:**
   ```bash
   # Find process using port 8080
   lsof -i :8080
   # Kill the process
   kill -9 <PID>
   ```

2. **Docker permission issues:**
   ```bash
   # Add user to docker group (Linux)
   sudo usermod -aG docker $USER
   # Log out and back in
   ```

3. **Maven wrapper not executable:**
   ```bash
   chmod +x mvnw
   ```

### Logs

- **Application logs**: Check console output when running the JAR
- **Docker logs**: `docker logs <container_id>`
- **Maven logs**: Add `-X` flag for debug output

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is for educational purposes and follows the Spring Boot license.
