# Docker and Containerization

## What is Containerization?

Containerization is a lightweight virtualization technology that packages an application and its dependencies into a standardized unit called a container. Unlike traditional virtual machines, containers share the host operating system kernel and isolate applications at the process level, making them more efficient and portable.

**Key benefits:**
- Consistent environments across development, testing, and production
- Lightweight and fast startup times
- Better resource utilization
- Easy deployment and scaling

## What is Docker?

Docker is the most popular containerization platform that enables developers to build, ship, and run applications in containers. It provides a complete ecosystem for container management.

**Core components:**
- **Docker Engine**: The runtime that builds and runs containers
- **Docker Images**: Read-only templates used to create containers
- **Docker Containers**: Running instances of Docker images
- **Docker Hub**: Registry for sharing and distributing Docker images

**Basic Docker commands:**
```bash
# Build an image
docker build -t myapp .

# Run a container
docker run -p 8080:8080 myapp

# List running containers
docker ps

# Stop a container
docker stop <container_id>
```

## What is Docker Compose?

Docker Compose is a tool for defining and running multi-container Docker applications. It uses a YAML file to configure application services, networks, and volumes.

**Key features:**
- Define multi-container applications in a single file
- Manage complex application stacks
- Easy service orchestration
- Environment-specific configurations

**Example docker-compose.yml:**
```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - db
  db:
    image: postgres:13
    environment:
      POSTGRES_DB: myapp
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
```

**Docker Compose commands:**
```bash
# Start services
docker-compose up

# Start services in background
docker-compose up -d

# Stop services
docker-compose down

# View logs
docker-compose logs
```

## Spring Boot with Docker

For Spring Boot applications, Docker provides an excellent way to package and deploy your application. The [Spring Boot Docker guide](https://spring.io/guides/gs/spring-boot-docker) demonstrates how to:

1. Create a Dockerfile for your Spring Boot application
2. Build and run the containerized application
3. Use Docker Compose for multi-service applications
4. Optimize Docker images for production use

This approach ensures your Spring Boot application runs consistently across different environments and can be easily deployed to various cloud platforms.
