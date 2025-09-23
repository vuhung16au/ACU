# ML Containerization: Dockerizing Machine Learning Applications 🐳🤖

This repository demonstrates how to containerize machine learning applications using Docker across three popular ML frameworks: **Scikit-learn**, **PyTorch**, and **TensorFlow**. Each project showcases best practices for ML containerization, from simple traditional ML to deep learning applications.

## 🚀 Quick Overview

- This showcases a simple way to solve your "It runs on my machine" problem
- For a quick start, you can run all the projects with `./build_and_run.sh`

Note: This repo is not about building a model. It's about containerizing a machine learning application.

## 📁 Project Structure

```
ML-Containerization/
├── ScikitLearn-Docker/     # Traditional ML with Iris classification
├── PyTorch-Docker/         # Deep learning with PyTorch CNN
├── TensorFlow-Docker/      # Deep learning with TensorFlow 2 CNN
└── README.md              # This file
```

## 🎯 What You'll Learn

- **Docker fundamentals** for ML applications
- **Framework-specific optimizations** for each ML library
- **Production-ready** containerization patterns
- **DevOps to ML Engineering** transition techniques

## 🛠️ Prerequisites

- Docker installed on your system
- Basic knowledge of Docker commands
- At least 4GB of available RAM (for TensorFlow)
- Docker Desktop configured with appropriate memory allocation

## 🚀 Quick Start

### Option 1: Run All Projects (Recommended)

```bash
# Clone the repository
git clone <repository-url>
cd ML-Containerization

# Run Scikit-learn project
cd ScikitLearn-Docker && ./build_and_run.sh

# Run PyTorch project  
cd ../PyTorch-Docker && ./build_and_run.sh

# Run TensorFlow project
cd ../TensorFlow-Docker && ./build_and_run.sh
```

### Option 2: Individual Project Setup

Each project can be run independently. Navigate to any project folder and follow the specific README instructions.

---

**Ready to containerize your ML applications? Start with any project and build your way up to production-ready ML systems!** 🚀
