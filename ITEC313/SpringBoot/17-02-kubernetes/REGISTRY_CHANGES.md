# Docker Registry Changes Summary

## Overview
This document summarizes all the changes made to update the Docker registry from the default `docker.io/library/` to the custom registry `vuhunghn`.

## Changes Made

### 1. Maven Configuration (`pom.xml`)
- **Added**: Spring Boot Maven plugin configuration with custom image name
- **Image Name**: Changed from default to `vuhunghn/kube:0.0.1-SNAPSHOT`
- **Builder**: Configured to use Paketo buildpacks (currently commented out due to compatibility issues)

### 2. Docker Configuration
- **Created**: `Dockerfile` for manual Docker image building
- **Base Image**: OpenJDK 21 slim variant for optimized container size
- **JVM Options**: Configured for containerized environments
- **Port**: Exposed port 8080 for the application

### 3. Kubernetes Configuration (`k8s/deployment.yaml`)
- **Image**: Updated from `kube:latest` to `vuhunghn/kube:0.0.1-SNAPSHOT`
- **Image Pull Policy**: Changed from `Never` to `Always` for production use

### 4. Build Scripts
- **Updated**: `scripts/deploy.sh` to include Docker image building
- **Created**: `scripts/build-and-push.sh` for separate image building and pushing
- **Process**: 
  1. Build JAR file with Maven
  2. Build Docker image with Dockerfile
  3. Deploy to Kubernetes

### 5. Documentation Updates

#### README.md
- **Docker Registry Section**: Added configuration information
- **Build Instructions**: Updated to use Dockerfile approach
- **Authentication**: Added Docker Hub login instructions
- **Scripts**: Updated deployment and build instructions
- **Cleanup**: Updated image removal commands

#### docs/k8s.md
- **Deployment Documentation**: Updated image references
- **Configuration Examples**: Updated to reflect new registry

### 6. Build Process Changes

#### Before (Buildpacks)
```bash
./mvnw spring-boot:build-image
```

#### After (Dockerfile)
```bash
./mvnw clean package -DskipTests
docker build -t vuhunghn/kube:0.0.1-SNAPSHOT .
```

## Benefits of Changes

### 1. Custom Registry
- **Ownership**: Full control over image naming and versioning
- **Organization**: Better organization of Docker images
- **Security**: Can implement custom security policies

### 2. Dockerfile Approach
- **Reliability**: More stable than buildpacks for this use case
- **Transparency**: Clear and explicit build process
- **Customization**: Full control over the container environment
- **Debugging**: Easier to troubleshoot build issues

### 3. Production Ready
- **Image Pull Policy**: Set to `Always` for production deployments
- **Resource Optimization**: JVM settings optimized for containers
- **Health Checks**: Ready for Kubernetes health probes

## Usage Instructions

### Building and Deploying
```bash
# Option 1: Build and deploy in one step
./scripts/deploy.sh

# Option 2: Build and push separately
./scripts/build-and-push.sh
./scripts/deploy.sh
```

### Docker Hub Authentication
```bash
# Login to Docker Hub before pushing
docker login
```

### Testing Locally
```bash
# Build and test locally
./mvnw clean package -DskipTests
docker build -t vuhunghn/kube:0.0.1-SNAPSHOT .
docker run -d -p 8080:8080 --name kube-app-test vuhunghn/kube:0.0.1-SNAPSHOT
curl http://localhost:8080/actuator/health
```

## Rollback Plan

If needed, the changes can be rolled back by:

1. **Reverting pom.xml**: Remove the Spring Boot plugin configuration
2. **Reverting deployment.yaml**: Change image back to `kube:latest`
3. **Removing Dockerfile**: Delete the custom Dockerfile
4. **Updating scripts**: Revert to original build process
5. **Moving scripts**: Move scripts back to root directory if needed

## Future Enhancements

1. **Multi-stage Dockerfile**: For even smaller production images
2. **Buildpack Integration**: Re-enable when compatibility issues are resolved
3. **CI/CD Pipeline**: Integrate with GitHub Actions or similar
4. **Image Scanning**: Add security scanning to the build process
5. **Multi-architecture**: Support for ARM64 and other architectures
