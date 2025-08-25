#!/bin/bash

# Source version management
source scripts/version.sh

echo "Building and deploying Spring Boot application to Kubernetes..."
echo "Using version: $APP_VERSION"
echo "Docker image: $DOCKER_IMAGE"

# Build the JAR file first
echo "Building JAR file..."
./mvnw clean package -DskipTests

# Build the Docker image using Dockerfile with dynamic JAR file
echo "Building Docker image..."
docker build -t $DOCKER_IMAGE --build-arg JAR_FILE=target/kube-$APP_VERSION.jar .

# Push the image to Docker Hub (optional - uncomment if you want to push)
# echo "Pushing Docker image to registry..."
# docker push $DOCKER_IMAGE

# Update Kubernetes deployment with current version
echo "Updating Kubernetes deployment version..."
./scripts/update-k8s-version.sh

# Create namespace
echo "Creating namespace..."
kubectl apply -f k8s/namespace.yaml

# Apply ConfigMap
echo "Applying ConfigMap..."
kubectl apply -f k8s/configmap.yaml

# Apply Deployment
echo "Applying Deployment..."
kubectl apply -f k8s/deployment.yaml

# Apply Service
echo "Applying Service..."
kubectl apply -f k8s/service.yaml

# Apply HPA
echo "Applying HorizontalPodAutoscaler..."
kubectl apply -f k8s/hpa.yaml

echo "Deployment completed!"
echo "Check the status with: kubectl get all -n kube-app-ns"
echo "Access the application: kubectl get service kube-app-service -n kube-app-ns"
