#!/bin/bash

# Source version management
source scripts/version.sh

echo "Building and pushing Docker image to vuhunghn registry..."
echo "Using version: $APP_VERSION"
echo "Docker image: $DOCKER_IMAGE"

# Build the JAR file first
echo "Building JAR file..."
./mvnw clean package -DskipTests

# Build the Docker image using Dockerfile with dynamic JAR file
echo "Building Docker image..."
docker build -t $DOCKER_IMAGE --build-arg JAR_FILE=target/kube-$APP_VERSION.jar .

# Tag the image for pushing (optional - if you want a different tag)
echo "Tagging image..."
docker tag $DOCKER_IMAGE vuhunghn/kube:latest

# Push the image to Docker Hub
echo "Pushing Docker image to registry..."
docker push $DOCKER_IMAGE
docker push vuhunghn/kube:latest

# Update Kubernetes deployment with current version
echo "Updating Kubernetes deployment version..."
./scripts/update-k8s-version.sh

echo "Image build and push completed!"
echo "Image available at: $DOCKER_IMAGE"
echo "Image available at: vuhunghn/kube:latest"
