# Spring Boot Kubernetes Demo

This is a Spring Boot application designed to run on Kubernetes. The application includes Spring Boot Actuator for health checks and monitoring, with a robust version management system for seamless deployments.

## Prerequisites

- Java 21
- Maven 3.9+
- Docker
- Kubernetes cluster (minikube, Docker Desktop, or cloud provider)
- Docker Hub account (for pushing images to `vuhunghn` registry)

## Version Management

This project includes a comprehensive version management system that automatically synchronizes versions across all components.

### Quick Version Management

```bash
# Check current version
./scripts/version.sh

# Update to new version (updates pom.xml and syncs all files)
./scripts/set-version.sh 0.0.5-SNAPSHOT

# Deploy with current version
./scripts/deploy.sh

# Build and push with current version
./scripts/build-and-push.sh
```

### How Version Management Works

- **Single Source of Truth**: Version is defined in `pom.xml`
- **Automatic Synchronization**: All files (Docker, Kubernetes, scripts) stay in sync
- **Dynamic Docker Builds**: Dockerfile uses build arguments for flexible JAR naming
- **Cross-Platform**: Works on macOS, Linux, and Windows
- **Error Prevention**: No more version mismatch errors

For detailed information, see [Version Management Documentation](docs/VERSION_MANAGEMENT.md).

## Building the Application

### Docker Registry Configuration

This project is configured to use the custom Docker registry `vuhunghn`. The Docker images are dynamically tagged based on the current version in `pom.xml`.

**Note**: Before pushing images to Docker Hub, make sure you're logged in:
```bash
docker login
```

This will prompt for your Docker Hub username and password.

### Build Scripts

The project includes enhanced build scripts in the `scripts/` folder for easy automation:

- `scripts/version.sh` - Extract and display current version from pom.xml
- `scripts/set-version.sh` - Update version in pom.xml and sync all files
- `scripts/update-k8s-version.sh` - Update Kubernetes deployment with current version
- `scripts/build-and-push.sh` - Build JAR, create Docker image, and push to registry
- `scripts/deploy.sh` - Build and deploy to Kubernetes in one step

## Building the Application

This project uses a custom Dockerfile to build optimized Docker images for the Spring Boot application.

```bash
# Build the JAR file first
./mvnw clean package -DskipTests

# Build the Docker image using Dockerfile (version is automatically detected)
docker build -t vuhunghn/kube:$(./scripts/version.sh | grep APP_VERSION | cut -d'=' -f2) .

# Test the image locally
docker run -d -p 8080:8080 --name kube-app vuhunghn/kube:$(./scripts/version.sh | grep APP_VERSION | cut -d'=' -f2)

# Test the application
curl http://localhost:8080/actuator/health

# Clean up
docker stop kube-app && docker rm kube-app
```

### How Dockerfile Build Works

The Docker build process uses a dynamic approach with build arguments:

#### 1. **Maven Build Phase**
```bash
./mvnw clean package -DskipTests
```
- Compiles Java source code
- Creates the executable JAR file with all dependencies
- The JAR is placed in `target/kube-{version}.jar`

#### 2. **Docker Build Phase**
```bash
docker build -t vuhunghn/kube:{version} --build-arg JAR_FILE=target/kube-{version}.jar .
```
- Uses OpenJDK 21 slim base image for smaller size
- Dynamically copies the JAR file using build arguments
- Sets up JVM options optimized for containers
- Exposes port 8080 for the application

#### 3. **Key Benefits**

**Optimized for Containers:**
- **Small image size**: Uses OpenJDK slim variant
- **Memory optimization**: JVM heap settings for containers
- **Security**: Minimal attack surface with slim base image
- **Performance**: Optimized for Spring Boot applications
- **Dynamic versioning**: No hardcoded versions in Dockerfile

**Production Ready:**
- **Health checks**: Built-in support for Spring Boot Actuator
- **Resource limits**: Configurable memory and CPU settings
- **Monitoring**: Ready for Kubernetes health probes
- **Scalability**: Designed for container orchestration
- **Version management**: Automatic version synchronization

## Kubernetes Deployment

### Quick Deployment

```bash
# Build and deploy to Kubernetes (includes image building and version sync)
./scripts/deploy.sh

# Or build and push image separately, then deploy
./scripts/build-and-push.sh
./scripts/deploy.sh

# Check deployment status
kubectl get all -n kube-app-ns

# Get service URL (if using minikube)
minikube service kube-app-service -n kube-app-ns
```

### Manual Deployment

```bash
# Update Kubernetes deployment with current version
./scripts/update-k8s-version.sh

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

### Using Scripts

The project includes enhanced scripts in the `scripts/` folder:

- `scripts/version.sh` - Check current version and export environment variables
- `scripts/set-version.sh` - Update version and sync all files
- `scripts/update-k8s-version.sh` - Update Kubernetes deployment version
- `scripts/deploy.sh` - Build and deploy to Kubernetes with version sync
- `scripts/build-and-push.sh` - Build and push Docker image with version sync

## Application Features

- **Spring Boot 3.5.5** with Java 21
- **Spring WebFlux** for reactive web support
- **Spring Boot Actuator** for health checks and monitoring
- **Kubernetes-ready** with proper health checks and resource limits
- **Auto-scaling** with HorizontalPodAutoscaler
- **Load balancing** with Kubernetes Service
- **Version Management** with automatic synchronization across all components

## Health Check Endpoints

- Health check: `/actuator/health`
- Application info: `/actuator/info`
- Metrics: `/actuator/metrics`

## Kubernetes Resources

The deployment includes:

- **Namespace**: `kube-app-ns`
- **Deployment**: 3 replicas with health checks and dynamic image versions
- **Service**: LoadBalancer type for external access
- **ConfigMap**: Application configuration
- **HPA**: Auto-scaling based on CPU and memory usage
- **Scripts**: Enhanced build and deployment automation with version management

## Monitoring and Scaling

The application includes:

- **Liveness Probe**: Checks if the application is running
- **Readiness Probe**: Checks if the application is ready to serve traffic
- **Resource Limits**: CPU and memory limits for each pod
- **Auto-scaling**: Scales between 3-10 replicas based on resource usage
- **Version Tracking**: All components use the same version for consistency

## Troubleshooting

### Version Mismatch Issues

**Problem**: You see a different version in the application logs than what's in your `pom.xml`.

**Example**:
```bash
# Current pom.xml shows:
<version>0.0.4-SNAPSHOT</version>

# But application logs show:
v0.0.1-SNAPSHOT
```

**Root Cause**: The running container was built from an older version of your code. The version in the logs comes from the JAR file that was built into the Docker image, not from the current `pom.xml` file.

**Why This Happens**:
1. **Container Image Version**: The container image might not exist in the registry, or Kubernetes might be using a cached version
2. **Build Process**: When you build the Docker image, it uses the version from `pom.xml` at build time
3. **Deployment**: If the target image doesn't exist, Kubernetes might fall back to an older version

**Solutions**:

1. **Rebuild and redeploy** with the current version:
   ```bash
   # Build and push new image with current version
   ./scripts/build-and-push.sh
   
   # Restart deployment to use new image
   kubectl rollout restart deployment/kube-app -n kube-app-ns
   
   # Check logs to verify new version
   kubectl logs -f deployment/kube-app -n kube-app-ns
   ```

2. **Check if the image exists** in your registry:
   ```bash
   # Try to pull the image to see if it exists
   docker pull vuhunghn/kube:0.0.4-SNAPSHOT
   
   # Check what images are available locally
   docker images | grep vuhunghn/kube
   ```

3. **Verify the deployment** is using the correct image:
   ```bash
   # Check deployment configuration
   kubectl describe deployment kube-app -n kube-app-ns
   
   # Check what image the pods are actually using
   kubectl get pods -n kube-app-ns -o jsonpath='{.items[*].spec.containers[*].image}'
   ```

4. **Force image pull** to ensure latest version:
   ```bash
   # Delete pods to force recreation with new image
   kubectl delete pods -l app=kube-app -n kube-app-ns
   
   # Or scale down and up
   kubectl scale deployment kube-app --replicas=0 -n kube-app-ns
   kubectl scale deployment kube-app --replicas=3 -n kube-app-ns
   ```

**Prevention**:
- Always run `./scripts/build-and-push.sh` after version changes
- Use `./scripts/deploy.sh` for complete build and deployment workflow
- Check image tags in your registry regularly
- Use `imagePullPolicy: Always` in deployment (already configured)

### Check Pod Status
```bash
kubectl get pods -n kube-app-ns
kubectl describe pod <pod-name> -n kube-app-ns
```

### Check Logs
```bash
kubectl logs <pod-name> -n kube-app-ns
kubectl logs -f deployment/kube-app -n kube-app-ns
```

### Check Service
```bash
kubectl get svc -n kube-app-ns
kubectl describe svc kube-app-service -n kube-app-ns
```

### Port Forward (for local testing)
```bash
kubectl port-forward svc/kube-app-service 8080:80 -n kube-app-ns
```

### Version Issues
```bash
# Check current version
./scripts/version.sh

# Verify pom.xml has correct version
cat pom.xml | grep -A 1 -B 1 "<version>"

# Sync Kubernetes deployment
./scripts/update-k8s-version.sh

# Clean and rebuild
./mvnw clean package
```

## Cleanup

```bash
# Delete all resources
kubectl delete namespace kube-app-ns

# Remove Docker images (use current version)
CURRENT_VERSION=$(./scripts/version.sh | grep APP_VERSION | cut -d'=' -f2)
docker rmi vuhunghn/kube:$CURRENT_VERSION
docker rmi vuhunghn/kube:latest
```

## Documentation

- [Version Management Guide](docs/VERSION_MANAGEMENT.md) - Detailed version management documentation
- [Kubernetes Setup](docs/k8s.md) - Kubernetes configuration details
- [Quick Start Guide](docs/QUICK-START.md) - Getting started quickly
