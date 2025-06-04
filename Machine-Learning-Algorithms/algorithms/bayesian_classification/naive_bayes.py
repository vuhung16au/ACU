## Naive Bayes Classification Implementation
# This notebook demonstrates Naive Bayes for classification using scikit-learn.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import GaussianNB
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix
import seaborn as sns
import matplotlib.pyplot as plt
import os

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory for visuals
output_dir = 'algorithms/bayesian_classification/naive_bayes'
os.makedirs(output_dir, exist_ok=True)

## 2. Data Generation
# Generate synthetic data for demonstration
n_samples = 500
n_features = 4
X = np.random.randn(n_samples, n_features)
# Create binary target with a simple rule
y = (X[:, 0] + X[:, 1] > 0).astype(int)

# Split data into train and test sets
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=2220
)

## 3. Model Training
# Hyperparameters explained:
# - var_smoothing: Portion of the largest variance of all features that is added to variances for stability
model = GaussianNB(var_smoothing=1e-9)
model.fit(X_train, y_train)

## 4. Prediction and Evaluation
y_pred = model.predict(X_test)
accuracy = accuracy_score(y_test, y_pred)
print(f"Accuracy: {accuracy:.4f}")
print("\nClassification Report:")
print(classification_report(y_test, y_pred))

## 5. Visualization
def plot_confusion_matrix(y_true, y_pred, title="Confusion Matrix"):
    """
    Plot confusion matrix using seaborn.
    """
    cm = confusion_matrix(y_true, y_pred)
    plt.figure(figsize=(6, 5))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
    plt.xlabel('Predicted')
    plt.ylabel('True')
    plt.title(title)
    plt.savefig(f'{output_dir}/naive_bayes-confusion-matrix.png')
    plt.close()

plot_confusion_matrix(y_test, y_pred, title="Naive Bayes Confusion Matrix") 