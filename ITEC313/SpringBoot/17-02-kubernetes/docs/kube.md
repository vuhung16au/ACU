# Kubernetes (K8s) Documentation

## What is Kubernetes?

Kubernetes (K8s) is an open-source container orchestration platform that automates the deployment, scaling, and management of containerized applications. It was originally developed by Google and is now maintained by the Cloud Native Computing Foundation (CNCF).

### Key Features:
- **Container Orchestration**: Manages containerized applications across multiple hosts
- **Auto-scaling**: Automatically scales applications based on demand
- **Self-healing**: Restarts failed containers and replaces unhealthy ones
- **Load Balancing**: Distributes network traffic across containers
- **Service Discovery**: Enables containers to find and communicate with each other
- **Rolling Updates**: Updates applications without downtime

### Core Concepts:
- **Pods**: Smallest deployable units (can contain one or more containers)
- **Services**: Abstract way to expose applications running on pods
- **Deployments**: Manage the desired state for pods and replicas
- **Namespaces**: Provide a mechanism for isolating groups of resources
- **ConfigMaps & Secrets**: Store configuration data and sensitive information

## Minikube

Minikube is a tool that runs a single-node Kubernetes cluster inside a virtual machine on your local machine. It's perfect for learning Kubernetes, daily development work, and testing.

### Features:
- **Local Development**: Run Kubernetes locally without complex setup
- **Multiple Drivers**: Support for Docker, VirtualBox, Hyper-V, and others
- **Add-ons**: Easy installation of additional Kubernetes components
- **Cross-platform**: Works on Windows, macOS, and Linux

### Common Commands:
```bash
# Start minikube
minikube start

# Check status
minikube status

# Stop minikube
minikube stop

# Delete cluster
minikube delete

# Access Kubernetes dashboard
minikube dashboard

# Get cluster IP
minikube ip
```

## kubectl

kubectl is the command-line tool for interacting with Kubernetes clusters. It's the primary way to communicate with the Kubernetes API server.

### Key Commands:
```bash
# Get cluster information
kubectl cluster-info

# List nodes
kubectl get nodes

# List pods
kubectl get pods

# List services
kubectl get services

# Describe a resource
kubectl describe pod <pod-name>

# Apply a configuration file
kubectl apply -f <filename>

# Delete a resource
kubectl delete <resource-type> <name>

# Execute command in a pod
kubectl exec -it <pod-name> -- /bin/bash

# View logs
kubectl logs <pod-name>

# Port forward
kubectl port-forward <pod-name> <local-port>:<pod-port>
```

### Configuration:
- kubectl uses kubeconfig files to find cluster information
- Default location: `~/.kube/config`
- Can manage multiple clusters and contexts

## Installation on macOS

### Prerequisites:
- macOS 10.15 or later
- Homebrew (recommended package manager)

### Step 1: Install Homebrew (if not installed)
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

### Step 2: Install kubectl
```bash
brew install kubectl
```

Verify installation:
```bash
kubectl version --client
```

### Step 3: Install Minikube
```bash
brew install minikube
```

Verify installation:
```bash
minikube version
```

### Step 4: Install Docker Desktop
```bash
brew install --cask docker
```

Alternative: Download from [Docker Desktop for Mac](https://www.docker.com/products/docker-desktop)

### Step 5: Start Minikube
```bash
# Start with Docker driver (recommended)
minikube start --driver=docker

# Or start with default driver
minikube start
```

### Step 6: Verify Installation
```bash
# Check cluster status
kubectl cluster-info

# List nodes
kubectl get nodes

# Check minikube status
minikube status
```

## Alternative Installation Methods

### Using curl (kubectl):
```bash
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/darwin/arm64/kubectl"
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl
```

### Using curl (minikube):
```bash
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-darwin-amd64
sudo install minikube-darwin-amd64 /usr/local/bin/minikube
```

## Useful Tips

### Enable Minikube Add-ons:
```bash
# Enable dashboard
minikube addons enable dashboard

# Enable ingress
minikube addons enable ingress

# Enable metrics server
minikube addons enable metrics-server
```

### Access Services:
```bash
# Get service URL
minikube service <service-name>

# Open service in browser
minikube service <service-name> --url
```

### Troubleshooting:
```bash
# Reset minikube
minikube delete
minikube start

# Check logs
minikube logs

# SSH into minikube
minikube ssh
```

## Next Steps

After installation, you can:
1. Deploy your first application
2. Explore the Kubernetes dashboard
3. Learn about pods, services, and deployments
4. Practice with sample applications
5. Set up persistent storage
6. Configure networking and ingress

## Cloud Native Buildpacks

Cloud Native Buildpacks (CNB) is a specification and set of tools that transform your application source code into container images without requiring a Dockerfile. They provide a higher-level abstraction for building container images and are designed to be secure, reproducible, and maintainable.

### What are Cloud Native Buildpacks?

Cloud Native Buildpacks are:
- **Language-aware**: Automatically detect your application's language and framework
- **Dockerfile-free**: No need to write or maintain Dockerfiles
- **Secure by default**: Include security patches and best practices
- **Reproducible**: Consistent builds across different environments
- **Optimized**: Create efficient, layered container images

### How Buildpacks Work

1. **Detection**: Analyzes your source code to determine the language and framework
2. **Build**: Downloads dependencies, compiles code, and prepares the runtime
3. **Export**: Creates a container image with the application and runtime

### Key Components

- **Buildpacks**: Language-specific build scripts and runtime configurations
- **Builder**: Combines multiple buildpacks to create a complete build environment
- **Lifecycle**: Orchestrates the build process (detect, analyze, restore, build, export)

### Popular Buildpack Implementations

#### 1. **Paketo Buildpacks**
- Cloud Foundry Foundation's implementation
- Excellent Spring Boot support
- Comprehensive language support
- Active community and regular updates

#### 2. **Google Cloud Buildpacks**
- Google's implementation
- Optimized for Google Cloud Platform
- Good integration with Cloud Run and GKE

#### 3. **Heroku Buildpacks**
- Heroku's implementation
- Mature and battle-tested
- Good for web applications

### Using Buildpacks with Spring Boot

#### Prerequisites
```bash
# Install pack CLI
# macOS
brew install buildpacks/tap/pack

# Linux
curl -sL "https://github.com/buildpacks/pack/releases/download/v0.32.1/pack-v0.32.1-linux.tgz" | tar -xz
sudo mv pack /usr/local/bin/
```

#### Basic Usage
```bash
# Build an image from source code
pack build my-app --builder paketobuildpacks/builder:base

# Build with specific builder
pack build my-app --builder paketobuildpacks/builder:tiny

# Build and push to registry
pack build my-app --builder paketobuildpacks/builder:base --publish

# Build with environment variables
pack build my-app --builder paketobuildpacks/builder:base --env SPRING_PROFILES_ACTIVE=prod
```

#### Spring Boot Specific Features

**Automatic Detection**: Buildpacks automatically detect Spring Boot applications and:
- Use appropriate JVM version
- Configure memory settings
- Set up health checks
- Optimize for containerized environments

**Environment Configuration**:
```bash
# Set JVM options
pack build my-app --env BPE_JAVA_TOOL_OPTIONS="-Xmx512m -Xms256m"

# Set Spring profiles
pack build my-app --env SPRING_PROFILES_ACTIVE=kubernetes

# Configure application properties
pack build my-app --env SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/mydb
```

### Buildpack vs Dockerfile

| Aspect | Buildpacks | Dockerfile |
|--------|------------|------------|
| **Ease of Use** | High-level, language-aware | Low-level, manual configuration |
| **Security** | Automatic security updates | Manual security management |
| **Maintenance** | Minimal maintenance required | Regular updates needed |
| **Optimization** | Built-in optimizations | Manual optimization required |
| **Flexibility** | Limited customization | Full customization |
| **Learning Curve** | Shallow | Steep |

### Integration with Kubernetes

#### Building Images for Kubernetes
```bash
# Build image for local Kubernetes
pack build my-app:latest --builder paketobuildpacks/builder:base

# Build and push to registry
pack build my-app:latest --builder paketobuildpacks/builder:base --publish

# Use in Kubernetes deployment
kubectl apply -f k8s/deployment.yaml
```

#### Multi-stage Builds
```bash
# Build with different builders for different stages
pack build my-app:dev --builder paketobuildpacks/builder:base
pack build my-app:prod --builder paketobuildpacks/builder:tiny
```

### Advanced Features

#### Custom Buildpacks
```bash
# Create custom buildpack
pack buildpack package my-custom-buildpack --path ./my-buildpack

# Use custom buildpack
pack build my-app --builder my-custom-builder
```

#### Buildpack Configuration
```bash
# Use project.toml for configuration
cat > project.toml << EOF
[build]
  [[build.env]]
    name = "BP_JVM_VERSION"
    value = "17"
  
  [[build.env]]
    name = "BPE_JAVA_TOOL_OPTIONS"
    value = "-Xmx1g -Xms512m"
EOF
```

#### Buildpack Registry
```bash
# List available builders
pack builder suggest

# Inspect builder contents
pack builder inspect paketobuildpacks/builder:base
```

### Best Practices

1. **Choose the Right Builder**:
   - Use `base` for development and testing
   - Use `tiny` for production (smaller image size)
   - Use `full` for applications requiring additional system packages

2. **Optimize JVM Settings**:
   ```bash
   # Set appropriate memory limits
   pack build my-app --env BPE_JAVA_TOOL_OPTIONS="-Xmx512m -Xms256m"
   ```

3. **Use Environment Variables**:
   - Configure application properties at build time
   - Use runtime environment variables for dynamic configuration

4. **Security Considerations**:
   - Regularly update builders to get security patches
   - Use minimal builders when possible
   - Scan images for vulnerabilities

5. **CI/CD Integration**:
   ```yaml
   # GitHub Actions example
   - name: Build with Pack
     run: |
       pack build my-app:${{ github.sha }} \
         --builder paketobuildpacks/builder:base \
         --publish
   ```

### Troubleshooting

#### Common Issues
```bash
# Check build logs
pack build my-app --builder paketobuildpacks/builder:base --verbose

# Inspect builder
pack builder inspect paketobuildpacks/builder:base

# Validate buildpack
pack buildpack validate ./my-buildpack
```

#### Debug Mode
```bash
# Enable debug logging
pack build my-app --builder paketobuildpacks/builder:base --verbose --log-level debug
```

## Resources

- [Kubernetes Official Documentation](https://kubernetes.io/docs/)
- [Minikube Documentation](https://minikube.sigs.k8s.io/docs/)
- [kubectl Reference](https://kubernetes.io/docs/reference/kubectl/)
- [Spring on Kubernetes Guide](https://spring.io/guides/topicals/spring-on-kubernetes)
- [Cloud Native Buildpacks Documentation](https://buildpacks.io/docs/)
- [Paketo Buildpacks](https://paketo.io/)
- [Pack CLI Documentation](https://buildpacks.io/docs/tools/pack/)
