# Dockerized Machine Learning Application

This project demonstrates how to containerize a simple machine learning application using Docker. The application uses scikit-learn to train a logistic regression model on the famous Iris dataset.

## Project Structure

```
ScikitLearn-Docker/
├── iris_classification.py    # Main ML application
├── requirements.txt          # Python dependencies
├── Dockerfile               # Docker configuration
├── .dockerignore            # Files to exclude from Docker build
└── README.md               # This file
```

## What the Application Does

The `iris_classification.py` script:
1. Loads the Iris dataset from scikit-learn
2. Splits the data into training and test sets
3. Trains a Logistic Regression model
4. Makes predictions on the test set
5. Reports accuracy and classification metrics
6. Shows example predictions

## Prerequisites

- Docker installed on your system
- Basic knowledge of Docker commands

## Quick Start

### 1. Build the Docker Image

```bash
docker build -t iris-ml-app .
```

### 2. Run the Container

```bash
docker run iris-ml-app
```

### 3. Expected Output

You should see output similar to:

```
=== Iris Classification with Docker ===
Loading iris dataset...
Dataset shape: (150, 4)
Target classes: [0 1 2]
Feature names: ['sepal length (cm)', 'sepal width (cm)', 'petal length (cm)', 'petal width (cm)']
Target names: ['setosa' 'versicolor' 'virginica']

Training set size: 120
Test set size: 30

Training Logistic Regression model...
Making predictions...

Model Accuracy: 0.9667 (96.67%)

Classification Report:
              precision    recall  f1-score   support

      setosa       1.00      1.00      1.00        10
  versicolor       0.91      1.00      0.95         9
   virginica       1.00      0.91      0.95        11

    accuracy                           0.97        30
   macro avg       0.97      0.97      0.97        30
weighted avg       0.97      0.97      0.97        30

Example Predictions:
Sample 1: True=versicolor, Predicted=versicolor
Sample 2: True=setosa, Predicted=setosa
Sample 3: True=virginica, Predicted=virginica
Sample 4: True=versicolor, Predicted=versicolor
Sample 5: True=setosa, Predicted=setosa

=== Model Training Complete ===
```

## Docker Commands Explained

### Building the Image
```bash
docker build -t iris-ml-app .
```
- `-t iris-ml-app`: Tags the image with the name "iris-ml-app"
- `.`: Uses the current directory as the build context

### Running the Container
```bash
docker run iris-ml-app
```
- Runs the container and executes the CMD specified in the Dockerfile

### Additional Useful Commands

**Run with interactive shell:**
```bash
docker run -it iris-ml-app /bin/bash
```

**Run with volume mounting (for development):**
```bash
docker run -v $(pwd):/src iris-ml-app
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
CMD ["python", "iris_classification.py"]  # Default command
```

## Benefits of Dockerizing ML Applications

1. **Reproducibility**: Same environment across different machines
2. **Isolation**: No conflicts with system Python packages
3. **Portability**: Easy to deploy on any system with Docker
4. **Version Control**: Exact package versions are specified
5. **Scalability**: Easy to run multiple instances

