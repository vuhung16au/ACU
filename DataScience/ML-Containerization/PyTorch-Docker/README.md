# Dockerized PyTorch MNIST Classification

This project demonstrates how to containerize a PyTorch machine learning application using Docker. The application implements a Convolutional Neural Network (CNN) for MNIST digit classification using PyTorch's native APIs.

## Project Structure

```
PyTorch-Docker/
├── pytorch_mnist.py    # Main PyTorch MNIST application
├── requirements.txt    # Python dependencies
├── Dockerfile         # Docker configuration
├── docker-compose.yml # Docker Compose configuration
├── build_and_run.sh   # Build and run script
├── test_local.py      # Local testing script
├── .dockerignore      # Files to exclude from Docker build
├── advanced.ipynb     # Original Jupyter notebook
└── README.md         # This file
```

## What the Application Does

The `pytorch_mnist.py` script:
1. Loads the MNIST dataset from torchvision
2. Preprocesses the data (normalization and tensor conversion)
3. Creates a CNN model using PyTorch's nn.Module
4. Implements custom training loop using PyTorch's autograd
5. Trains the model for 2 epochs
6. Evaluates the model on test data
7. Shows example predictions with confidence scores

## Model Architecture

The CNN model consists of:
- **Conv2D Layer**: 32 filters, 3x3 kernel, ReLU activation
- **Flatten Layer**: Converts 2D features to 1D
- **Dense Layer 1**: 128 neurons with ReLU activation
- **Dense Layer 2**: 10 neurons (output layer for 10 digit classes)

## Prerequisites

- Docker installed on your system
- Basic knowledge of Docker commands
- At least 2GB of available RAM (PyTorch is more memory-efficient than TensorFlow)
- Docker Desktop configured with at least 2GB memory allocation

## Quick Start

### 1. Build and Run with Script (Recommended)

```bash
cd PyTorch-Docker
./build_and_run.sh
```

### 2. Manual Docker Commands

**Build the Docker Image:**
```bash
docker build -t pytorch-mnist-app .
```

**Run the Container:**
```bash
docker run --memory=2g pytorch-mnist-app
```

### 3. Using Docker Compose

```bash
# Build and run
docker-compose up --build

# Run in background
docker-compose up -d --build

# Stop the container
docker-compose down
```

## Expected Output

You should see output similar to:

```
=== PyTorch MNIST Classification with Docker ===
PyTorch version: 2.1.0
Device: cpu

Loading MNIST dataset...
Training data size: 60000
Test data size: 10000
Number of classes: 10

Building CNN model...
Model created with 1,199,882 parameters

Starting training for 2 epochs...
Epoch 1, Loss: 0.1234, Accuracy: 96.45%, Test Loss: 0.0987, Test Accuracy: 97.12%
Epoch 2, Loss: 0.0891, Accuracy: 97.23%, Test Loss: 0.0765, Test Accuracy: 97.89%

=== Training Complete ===
Final Test Accuracy: 97.89%
Final Test Loss: 0.0765

Example Predictions:
Sample 1: True=7, Predicted=7, Confidence=0.987
Sample 2: True=2, Predicted=2, Confidence=0.945
Sample 3: True=1, Predicted=1, Confidence=0.998
Sample 4: True=0, Predicted=0, Confidence=0.992
Sample 5: True=4, Predicted=4, Confidence=0.976

=== Model Training Complete ===
```

## Docker Commands Explained

### Building the Image
```bash
docker build -t pytorch-mnist-app .
```
- `-t pytorch-mnist-app`: Tags the image with the name "pytorch-mnist-app"
- `.`: Uses the current directory as the build context

### Running the Container
```bash
docker run --memory=2g pytorch-mnist-app
```
- Runs the container with 2GB memory allocation and executes the CMD specified in the Dockerfile

### Additional Useful Commands

**Run with interactive shell:**
```bash
docker run -it pytorch-mnist-app /bin/bash
```

**Run with volume mounting (for development):**
```bash
docker run --memory=2g -v $(pwd):/src pytorch-mnist-app
```

**Run with GPU support (if available):**
```bash
docker run --memory=2g --gpus all pytorch-mnist-app
```

**Remove old containers and images:**
```bash
docker system prune -a
```

## Dockerfile Breakdown

```dockerfile
FROM python:3.9-slim          # Base image with Python 3.9
WORKDIR /src                  # Set working directory
COPY requirements.txt .       # Copy dependencies first (for caching)
RUN pip install --no-cache-dir -r requirements.txt  # Install packages
COPY . .                      # Copy application code
CMD ["python", "pytorch_mnist.py"]  # Default command
```

## Development Mode

For development with live code changes:

```bash
# Start development container
docker-compose --profile dev up -d

# Access the container
docker exec -it pytorch-mnist-classification-dev bash

# Run the application inside the container
python pytorch_mnist.py
```

## Performance Considerations

1. **Memory Usage**: PyTorch is more memory-efficient than TensorFlow. The container requires 2GB of RAM during training. The application has been optimized with:
   - Reduced batch size (16)
   - Efficient data loading with DataLoader
   - Proper device management (CPU/GPU)
   - Memory-efficient model architecture

2. **GPU Support**: For faster training, you can use GPU-enabled PyTorch:
   ```bash
   # Use GPU-enabled base image
   FROM pytorch/pytorch:latest
   ```

3. **Training Time**: On CPU, training takes approximately 1-3 minutes depending on your hardware.

## Benefits of Dockerizing PyTorch Applications

1. **Reproducibility**: Same environment across different machines
2. **Isolation**: No conflicts with system Python packages
3. **Portability**: Easy to deploy on any system with Docker
4. **Version Control**: Exact PyTorch and dependency versions
5. **Scalability**: Easy to run multiple instances
6. **GPU Support**: Consistent GPU environment across systems
7. **Memory Efficiency**: PyTorch is more memory-efficient than TensorFlow

## Local Testing

Before running the full application, you can test your PyTorch installation:

```bash
python test_local.py
```

This will verify:
- PyTorch and torchvision installation
- MNIST dataset loading
- Model creation
- Training components
- DataLoader functionality

