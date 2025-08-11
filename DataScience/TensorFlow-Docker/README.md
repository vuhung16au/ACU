# Dockerized TensorFlow MNIST Classification

This project demonstrates how to containerize a TensorFlow 2 machine learning application using Docker. The application implements a Convolutional Neural Network (CNN) for MNIST digit classification using TensorFlow 2's advanced features.

## Project Structure

```
TensorFlow-Docker/
├── tensorflow_mnist.py    # Main TensorFlow MNIST application
├── requirements.txt       # Python dependencies
├── Dockerfile            # Docker configuration
├── docker-compose.yml    # Docker Compose configuration
├── build_and_run.sh      # Build and run script
├── .dockerignore         # Files to exclude from Docker build
├── advanced.ipynb        # Original Jupyter notebook
└── README.md            # This file
```

## What the Application Does

The `tensorflow_mnist.py` script:
1. Loads the MNIST dataset from TensorFlow
2. Preprocesses the data (normalization and reshaping)
3. Creates a CNN model using TensorFlow 2's Keras API
4. Implements custom training loop using `tf.GradientTape`
5. Trains the model for 5 epochs
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
- At least 4GB of available RAM (TensorFlow is memory-intensive)
- Docker Desktop configured with at least 4GB memory allocation

## Quick Start

### 1. Build and Run with Script (Recommended)

```bash
cd TensorFlow-Docker
./build_and_run.sh
```

### 2. Manual Docker Commands

**Build the Docker Image:**
```bash
docker build -t tensorflow-mnist-app .
```

**Run the Container:**
```bash
docker run --memory=4g tensorflow-mnist-app
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
=== TensorFlow 2 MNIST Classification with Docker ===
TensorFlow version: 2.17.0

Loading MNIST dataset...
Training data shape: (60000, 28, 28, 1)
Test data shape: (10000, 28, 28, 1)
Number of classes: 10

Preparing data pipelines...

Building CNN model...
Model created with 1,199,882 parameters

Starting training for 5 epochs...
Epoch 1, Loss: 0.1234, Accuracy: 96.45%, Test Loss: 0.0987, Test Accuracy: 97.12%
Epoch 2, Loss: 0.0891, Accuracy: 97.23%, Test Loss: 0.0765, Test Accuracy: 97.89%
Epoch 3, Loss: 0.0678, Accuracy: 97.89%, Test Loss: 0.0654, Test Accuracy: 98.23%
Epoch 4, Loss: 0.0543, Accuracy: 98.34%, Test Loss: 0.0587, Test Accuracy: 98.45%
Epoch 5, Loss: 0.0456, Accuracy: 98.67%, Test Loss: 0.0523, Test Accuracy: 98.67%

=== Training Complete ===
Final Test Accuracy: 98.67%
Final Test Loss: 0.0523

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
docker build -t tensorflow-mnist-app .
```
- `-t tensorflow-mnist-app`: Tags the image with the name "tensorflow-mnist-app"
- `.`: Uses the current directory as the build context

### Running the Container
```bash
docker run --memory=4g tensorflow-mnist-app
```
- Runs the container with 4GB memory allocation and executes the CMD specified in the Dockerfile

### Additional Useful Commands

**Run with interactive shell:**
```bash
docker run -it tensorflow-mnist-app /bin/bash
```

**Run with volume mounting (for development):**
```bash
docker run --memory=4g -v $(pwd):/src tensorflow-mnist-app
```

**Run with GPU support (if available):**
```bash
docker run --memory=4g --gpus all tensorflow-mnist-app
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
CMD ["python", "tensorflow_mnist.py"]  # Default command
```

## Development Mode

For development with live code changes:

```bash
# Start development container
docker-compose --profile dev up -d

# Access the container
docker exec -it tensorflow-mnist-classification-dev bash

# Run the application inside the container
python tensorflow_mnist.py
```

## Performance Considerations

1. **Memory Usage**: TensorFlow is memory-intensive. The container requires 4GB of RAM during training. The application has been optimized with:
   - Reduced batch size (16 instead of 32)
   - Smaller shuffle buffer (5000 instead of 10000)
   - Memory growth configuration for GPU
   - Prefetch optimization for data pipelines

2. **GPU Support**: For faster training, you can use GPU-enabled TensorFlow:
   ```bash
   # Use GPU-enabled base image
   FROM tensorflow/tensorflow:2.17.0-gpu
   ```

3. **Training Time**: On CPU, training takes approximately 2-5 minutes depending on your hardware.

## Benefits of Dockerizing TensorFlow Applications

1. **Reproducibility**: Same environment across different machines
2. **Isolation**: No conflicts with system Python packages
3. **Portability**: Easy to deploy on any system with Docker
4. **Version Control**: Exact TensorFlow and dependency versions
5. **Scalability**: Easy to run multiple instances
6. **GPU Support**: Consistent GPU environment across systems

## Troubleshooting

### Common Issues

1. **Out of Memory**: The container requires 4GB of memory. Make sure to:
   - Allocate at least 4GB to Docker Desktop
   - Use `--memory=4g` flag when running the container
   - Increase Docker memory limit in Docker Desktop settings if needed
2. **Slow Training**: Consider using GPU-enabled TensorFlow image
3. **Permission Denied**: Make sure the build script is executable: `chmod +x build_and_run.sh`

### Getting Help

- Check Docker logs: `docker logs <container_name>`
- Run interactively: `docker run -it tensorflow-mnist-app /bin/bash`
- Check TensorFlow installation: `python -c "import tensorflow as tf; print(tf.__version__)"`

## License

This project is based on the TensorFlow 2 quickstart tutorial and is licensed under the Apache License 2.0.
