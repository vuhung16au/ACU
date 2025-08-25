# Quick Start Guide

This guide provides step-by-step commands to clean, build, run, and register the Spring Boot Maven project to Docker.

## Prerequisites

- Java 21
- Maven 3.9+
- Docker
- Kubernetes cluster (minikube, Docker Desktop, or cloud provider)

## Step 1: Clean the Project

```bash
# Clean Maven project (removes target directory)
./mvnw clean

# Clean Docker images (optional - removes old images)
docker rmi vuhunghn/kube:0.0.1-SNAPSHOT 2>/dev/null || true
docker rmi vuhunghn/kube:latest 2>/dev/null || true
```

## Step 2: Build the Project

```bash
# Build JAR file (skip tests for faster build)
./mvnw clean package -DskipTests

# Verify JAR was created
ls -la target/kube-0.0.1-SNAPSHOT.jar
```

## Step 3: Build Docker Image

```bash
# Build Docker image with custom registry
docker build -t vuhunghn/kube:0.0.1-SNAPSHOT .

# Tag with latest version (optional)
docker tag vuhunghn/kube:0.0.1-SNAPSHOT vuhunghn/kube:latest

# Verify image was created
docker images | grep vuhunghn/kube
```

## Step 4: Test Docker Image Locally

```bash
# Run container in background
docker run -d -p 8080:8080 --name kube-app-test vuhunghn/kube:0.0.1-SNAPSHOT

# Wait for application to start
sleep 10

# Test health endpoint
curl http://localhost:8080/actuator/health

# Check container logs
docker logs kube-app-test

# Stop and remove test container
docker stop kube-app-test && docker rm kube-app-test
```

## Step 5: Login to Docker Hub

```bash
# Login to Docker Hub (required for pushing images)
docker login

# Enter your Docker Hub username and password when prompted
```

## Step 6: Push Docker Image to Registry

```bash
# Push specific version
docker push vuhunghn/kube:0.0.1-SNAPSHOT

# Push latest version
docker push vuhunghn/kube:latest

# Verify images are available
docker search vuhunghn/kube
```

## Step 7: Deploy to Kubernetes

### Option A: Using Scripts (Recommended)

```bash
# Make scripts executable (if not already)
chmod +x scripts/*.sh

# Deploy to Kubernetes (builds and deploys in one step)
./scripts/deploy.sh
```

### Option B: Manual Deployment

```bash
# Create namespace
kubectl apply -f k8s/namespace.yaml

# Apply ConfigMap
kubectl apply -f k8s/configmap.yaml

# Apply Deployment
kubectl apply -f k8s/deployment.yaml

# Apply Service
kubectl apply -f k8s/service.yaml

# Apply HPA
kubectl apply -f k8s/hpa.yaml
```

## Step 8: Verify Deployment

```bash
# Check all resources
kubectl get all -n kube-app-ns

# Check pod status
kubectl get pods -n kube-app-ns

# Check service
kubectl get svc -n kube-app-ns

# View logs
kubectl logs -f deployment/kube-app -n kube-app-ns
```

## Step 9: Access the Application

### Local Kubernetes (minikube/Docker Desktop)
```bash
# Get service URL
minikube service kube-app-service -n kube-app-ns

# Or port forward
kubectl port-forward svc/kube-app-service 8080:80 -n kube-app-ns
```

### Cloud Kubernetes
```bash
# Get external IP
kubectl get svc kube-app-service -n kube-app-ns

# Access via external IP
curl http://<EXTERNAL-IP>/actuator/health
```

## One-Command Build and Deploy

For quick development cycles, use the automated script:

```bash
# Build, push, and deploy in one command
./scripts/build-and-push.sh && ./scripts/deploy.sh
```

## Troubleshooting

### Common Issues

1. **Docker login failed**
   ```bash
   # Check Docker Hub credentials
   docker login
   ```

2. **Image pull failed**
   ```bash
   # Check if image exists locally
   docker images | grep vuhunghn/kube
   
   # Pull from registry if needed
   docker pull vuhunghn/kube:0.0.1-SNAPSHOT
   ```

3. **Pod not starting**
   ```bash
   # Check pod events
   kubectl describe pod <pod-name> -n kube-app-ns
   
   # Check logs
   kubectl logs <pod-name> -n kube-app-ns
   ```

4. **Service not accessible**
   ```bash
   # Check service configuration
   kubectl describe svc kube-app-service -n kube-app-ns
   
   # Check endpoints
   kubectl get endpoints -n kube-app-ns
   ```

## Cleanup

```bash
# Delete Kubernetes resources
kubectl delete namespace kube-app-ns

# Remove Docker images
docker rmi vuhunghn/kube:0.0.1-SNAPSHOT
docker rmi vuhunghn/kube:latest

# Clean Maven project
./mvnw clean
```

## Quick Reference

| Command | Purpose |
|---------|---------|
| `./mvnw clean package -DskipTests` | Build JAR file |
| `docker build -t vuhunghn/kube:0.0.1-SNAPSHOT .` | Build Docker image |
| `docker push vuhunghn/kube:0.0.1-SNAPSHOT` | Push to registry |
| `./scripts/deploy.sh` | Deploy to Kubernetes |
| `kubectl get all -n kube-app-ns` | Check deployment status |
| `kubectl logs -f deployment/kube-app -n kube-app-ns` | View logs |
