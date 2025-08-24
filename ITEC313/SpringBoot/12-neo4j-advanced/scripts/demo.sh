#!/bin/bash

echo "=== Neo4j Advanced Demo Script ==="
echo "This script demonstrates the use of Spring Boot with Neo4j"
echo ""

# Function to check if a command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Check prerequisites
echo "Checking prerequisites..."
if ! command_exists docker; then
    echo "❌ Docker is not installed. Please install Docker first."
    exit 1
fi

if ! command_exists docker-compose; then
    echo "❌ Docker Compose is not installed. Please install Docker Compose first."
    exit 1
fi

if ! command_exists mvn; then
    echo "❌ Maven is not installed. Please install Maven first."
    exit 1
fi

echo "✅ All prerequisites are met"
echo ""

# Start Neo4j
echo "Starting Neo4j with Docker Compose..."
cd docker
docker-compose up -d
cd ..

# Wait for Neo4j to be ready
echo "Waiting for Neo4j to be ready..."
sleep 10

# Check if Neo4j is running
echo "Checking Neo4j status..."
if curl -s http://localhost:7474 > /dev/null; then
    echo "✅ Neo4j is running at http://localhost:7474"
    echo "   Username: neo4j"
    echo "   Password: Sydney@9876"
else
    echo "❌ Neo4j is not responding. Please check the Docker container."
    exit 1
fi

echo ""

# Build the project
echo "Building the project with Maven..."
mvn clean compile

if [ $? -eq 0 ]; then
    echo "✅ Project built successfully"
else
    echo "❌ Build failed"
    exit 1
fi

echo ""

# Run tests
echo "Running tests..."
mvn test

if [ $? -eq 0 ]; then
    echo "✅ Tests passed"
else
    echo "❌ Tests failed"
    exit 1
fi

echo ""

# Start the Spring Boot application
echo "Starting Spring Boot application..."
echo "The application will populate Neo4j with sample data and start the web server."
echo "Press Ctrl+C to stop the application when you're done."
echo ""

mvn spring-boot:run

echo ""
echo "=== Demo completed ==="
echo ""

# Stop Neo4j
echo "Stopping Neo4j..."
cd docker
docker-compose down
cd ..

echo "✅ Demo completed successfully!"
echo ""
echo "What was demonstrated:"
echo "1. ✅ Neo4j database startup with Docker"
echo "2. ✅ Spring Boot application compilation"
echo "3. ✅ Unit tests execution"
echo "4. ✅ Data population in Neo4j"
echo "5. ✅ Web server startup"
echo ""
echo "You can now explore the Neo4j browser at http://localhost:7474"
echo "to see the populated data and run Cypher queries."
