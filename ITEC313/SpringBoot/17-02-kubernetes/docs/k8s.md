# Kubernetes (K8s) Documentation

## What is Kubernetes?

**Kubernetes (K8s)** is an open-source container orchestration platform that automates the deployment, scaling, and management of containerized applications. It was originally developed by Google and is now maintained by the Cloud Native Computing Foundation (CNCF).

### What does Kubernetes do?

Kubernetes provides a framework to run distributed systems resiliently by:

- **Container Orchestration**: Manages thousands of containers across multiple hosts
- **Service Discovery & Load Balancing**: Automatically routes traffic to appropriate containers
- **Storage Orchestration**: Mounts storage systems of your choice
- **Automated Rollouts & Rollbacks**: Deploys and updates applications without downtime
- **Self-healing**: Restarts failed containers, replaces and reschedules containers when nodes die
- **Secret & Configuration Management**: Deploys and updates secrets and application configuration without rebuilding container images
- **Horizontal Scaling**: Scales applications up or down manually or automatically

### What is Kubernetes used for?

Kubernetes is used for:

- **Microservices Architecture**: Managing complex applications composed of multiple services
- **Cloud-Native Applications**: Building applications designed for cloud environments
- **DevOps & CI/CD**: Automating deployment pipelines
- **High Availability**: Ensuring applications remain available even when infrastructure fails
- **Resource Optimization**: Efficiently utilizing computing resources across clusters
- **Multi-Cloud & Hybrid Cloud**: Running applications across different cloud providers

## How to Run Kubernetes

### Prerequisites

1. **Install kubectl** (Kubernetes command-line tool):
   ```bash
   # macOS
   brew install kubectl
   
   # Linux
   curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
   sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
   ```

2. **Choose a Kubernetes Environment**:
   - **Minikube**: For local development
   - **Docker Desktop**: Built-in Kubernetes
   - **Kind**: Kubernetes in Docker
   - **Cloud Providers**: GKE, EKS, AKS

### Running with Minikube (Local Development)

```bash
# Install Minikube
brew install minikube

# Start Minikube
minikube start

# Verify installation
kubectl cluster-info
```

### Running with Docker Desktop

1. Enable Kubernetes in Docker Desktop settings
2. Wait for Kubernetes to start
3. Verify with `kubectl cluster-info`

## How to Configure Kubernetes

### Basic Configuration

1. **Set Context**: Switch between different clusters
   ```bash
   kubectl config get-contexts
   kubectl config use-context <context-name>
   ```

2. **Configure Namespaces**: Organize resources
   ```bash
   kubectl create namespace my-namespace
   kubectl config set-context --current --namespace=my-namespace
   ```

3. **Resource Limits**: Define CPU and memory limits
   ```yaml
   resources:
     requests:
       memory: "256Mi"
       cpu: "250m"
     limits:
       memory: "512Mi"
       cpu: "500m"
   ```

### Advanced Configuration

1. **ConfigMaps**: Store configuration data
2. **Secrets**: Store sensitive data
3. **Persistent Volumes**: Manage storage
4. **Network Policies**: Control traffic flow
5. **RBAC**: Manage access control

## Kubernetes YAML Files Explanation

This project contains the following Kubernetes configuration files in the `/k8s/` directory:

### 1. `namespace.yaml`
```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: kube-app-ns
  labels:
    name: kube-app-ns
```

**Purpose**: Creates a logical isolation boundary for the application resources. All other resources (deployments, services, etc.) will be created within this namespace.

### 2. `deployment.yaml`
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: kube-app
  namespace: kube-app-ns
spec:
  replicas: 3
  # ... container configuration
```

**Purpose**: Defines how the application should be deployed and run:
- **Replicas**: Runs 3 instances of the application for high availability
- **Container**: Specifies the Docker image (`vuhunghn/kube:0.0.1-SNAPSHOT`)
- **Health Checks**: Liveness and readiness probes using Spring Boot Actuator
- **Resource Limits**: CPU and memory constraints
- **Volume Mounts**: Mounts configuration from ConfigMap

### 3. `service.yaml`
```yaml
apiVersion: v1
kind: Service
metadata:
  name: kube-app-service
spec:
  type: LoadBalancer
  ports:
  - port: 80
    targetPort: 8080
```

**Purpose**: Exposes the application to external traffic:
- **LoadBalancer**: Creates external IP for accessing the application
- **Port Mapping**: Routes external port 80 to container port 8080
- **Load Balancing**: Distributes traffic across all replicas

### 4. `configmap.yaml`
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: kube-app-config
data:
  application.yml: |
    server:
      port: 8080
    management:
      endpoints:
        web:
          exposure:
            include: health,info,metrics
```

**Purpose**: Stores application configuration:
- **Spring Boot Config**: Server port and actuator endpoints configuration
- **Externalized Configuration**: Separates configuration from application code
- **Runtime Updates**: Can be updated without rebuilding the application

### 5. `hpa.yaml` (Horizontal Pod Autoscaler)
```yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: kube-app-hpa
spec:
  minReplicas: 3
  maxReplicas: 10
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 70
```

**Purpose**: Automatically scales the application based on resource usage:
- **CPU Scaling**: Scales up when CPU usage exceeds 70%
- **Memory Scaling**: Scales up when memory usage exceeds 80%
- **Min/Max Replicas**: Maintains between 3 and 10 instances

## Deployment Process

The project includes a `deploy.sh` script that automates the deployment:

```bash
# Make script executable
chmod +x deploy.sh

# Run deployment
./deploy.sh
```

The script applies all Kubernetes resources in the correct order:
1. Namespace
2. ConfigMap
3. Deployment
4. Service
5. Horizontal Pod Autoscaler

## Useful Commands

```bash
# Check deployment status
kubectl get all -n kube-app-ns

# View logs
kubectl logs -f deployment/kube-app -n kube-app-ns

# Access application
kubectl get service kube-app-service -n kube-app-ns

# Scale deployment
kubectl scale deployment kube-app --replicas=5 -n kube-app-ns

# Delete deployment
kubectl delete -f k8s/ -n kube-app-ns
```

## Best Practices

1. **Resource Limits**: Always set CPU and memory limits
2. **Health Checks**: Implement liveness and readiness probes
3. **Namespaces**: Use namespaces for resource organization
4. **Labels**: Apply meaningful labels for resource management
5. **Configuration**: Use ConfigMaps and Secrets for configuration
6. **Monitoring**: Implement proper logging and monitoring
7. **Security**: Follow the principle of least privilege
