#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
CatBoost Implementation
This module provides both custom and official implementations of CatBoost.
"""

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from catboost import CatBoostClassifier
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc, mean_squared_error
)
from sklearn.preprocessing import StandardScaler
import seaborn as sns
import os

## CatBoost Classification Implementation
# This notebook demonstrates CatBoost for classification using the official CatBoost library.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
from catboost import CatBoostClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix
import seaborn as sns
import matplotlib.pyplot as plt

# Set random seed for reproducibility
np.random.seed(2220)

## 2. Data Generation
# Generate synthetic data for demonstration
n_samples = 500
n_features = 6
X = np.random.randn(n_samples, n_features)
# Create a categorical feature
cat_feature = np.random.choice(['A', 'B', 'C'], size=n_samples)
X = np.column_stack([X, cat_feature])
# Create binary target with a simple rule
y = (X[:, 0].astype(float) + (X[:, 1].astype(float) > 0) + (X[:, -1] == 'A')) > 1
y = y.astype(int)

# Convert to DataFrame for CatBoost
feature_names = [f'feature_{i}' for i in range(n_features)] + ['cat_feature']
df = pd.DataFrame(X, columns=feature_names)
df[feature_names[:-1]] = df[feature_names[:-1]].astype(float)

# Encode categorical feature as string (CatBoost requirement)
df['cat_feature'] = df['cat_feature'].astype(str)

# Split data into train and test sets
X_train, X_test, y_train, y_test = train_test_split(
    df, y, test_size=0.2, random_state=2220
)

## 3. Model Training
# Hyperparameters explained:
# - iterations: Number of boosting rounds (trees)
# - learning_rate: Step size shrinkage
# - depth: Tree depth
# - l2_leaf_reg: L2 regularization term
# - random_strength: Random strength for scoring
# - border_count: Number of borders for feature discretization
# - cat_features: List of categorical feature names
model = CatBoostClassifier(
    iterations=100,
    learning_rate=0.1,
    depth=6,
    l2_leaf_reg=3,
    random_strength=1,
    border_count=254,
    cat_features=['cat_feature'],
    verbose=0,
    random_seed=2220
)
model.fit(X_train, y_train)

## 4. Prediction and Evaluation
y_pred = model.predict(X_test)
accuracy = accuracy_score(y_test, y_pred)
print(f"Accuracy: {accuracy:.4f}")
print("\nClassification Report:")
print(classification_report(y_test, y_pred))

## 5. Visualization
def plot_feature_importance(model, feature_names=None, title="Feature Importances"):
    """
    Plot feature importances using seaborn and save to PNG file.
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/catboost/catboost', exist_ok=True)
    
    importances = model.get_feature_importance()
    if feature_names is None:
        feature_names = [f'feature_{i}' for i in range(len(importances))]
    plt.figure(figsize=(10, 6))
    sns.barplot(x=importances, y=feature_names)
    plt.title(title)
    plt.xlabel('Importance')
    plt.ylabel('Feature')
    
    # Save plot
    filename = f'algorithms/catboost/catboost/catboost-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_confusion_matrix(y_true, y_pred, title="Confusion Matrix"):
    """
    Plot confusion matrix using seaborn and save to PNG file.
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/catboost/catboost', exist_ok=True)
    
    cm = confusion_matrix(y_true, y_pred)
    plt.figure(figsize=(6, 5))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
    plt.xlabel('Predicted')
    plt.ylabel('True')
    plt.title(title)
    
    # Save plot
    filename = f'algorithms/catboost/catboost/catboost-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

plot_feature_importance(model, feature_names=feature_names)
plot_confusion_matrix(y_test, y_pred, title="CatBoost Confusion Matrix")

def generate_data():
    """
    Generate sample data for classification with categorical features.
    
    Returns:
        tuple: (X, y, cat_features) features, target, and categorical feature indices
    """
    np.random.seed(42)
    n_samples = 1000
    n_features = 10
    n_cat_features = 3
    
    # Generate numerical features
    X_num = np.random.randn(n_samples, n_features - n_cat_features)
    
    # Generate categorical features
    X_cat = np.random.randint(0, 5, size=(n_samples, n_cat_features))
    
    # Combine features
    X = np.column_stack((X_num, X_cat))
    
    # Generate target with non-linear relationship
    y = np.zeros(n_samples)
    y[X[:, 0] + X[:, 1]**2 + np.sin(X[:, 2]) + X[:, -1] > 0] = 1
    
    # Add some noise
    y += np.random.normal(0, 0.1, n_samples)
    y = (y > 0.5).astype(int)
    
    # Define categorical feature indices
    cat_features = list(range(n_features - n_cat_features, n_features))
    
    return X, y, cat_features

# Generate sample data
X, y, cat_features = generate_data()

# --- Remove the NumPy array approach and use DataFrame for CatBoost ---

def generate_data_df():
    """
    Generate sample data for classification with categorical features as DataFrame.
    Returns:
        tuple: (df, y, cat_feature_names)
    """
    np.random.seed(42)
    n_samples = 1000
    n_features = 10
    n_cat_features = 3
    # Generate numerical features
    X_num = np.random.randn(n_samples, n_features - n_cat_features)
    # Generate categorical features as strings
    X_cat = np.random.randint(0, 5, size=(n_samples, n_cat_features)).astype(str)
    # Combine features
    X = np.column_stack((X_num, X_cat))
    # Create DataFrame
    feature_names = [f'feature_{i}' for i in range(n_features - n_cat_features)] + [f'cat_feature_{i}' for i in range(n_cat_features)]
    df = pd.DataFrame(X, columns=feature_names)
    for col in feature_names[:n_features - n_cat_features]:
        df[col] = df[col].astype(float)
    for col in feature_names[-n_cat_features:]:
        df[col] = df[col].astype(str)
    # Generate target
    y = np.zeros(n_samples)
    y[df['feature_0'] + df['feature_1']**2 + np.sin(df['feature_2']) + df['cat_feature_2'].astype(int) > 0] = 1
    y += np.random.normal(0, 0.1, n_samples)
    y = (y > 0.5).astype(int)
    cat_feature_names = feature_names[-n_cat_features:]
    return df, y, cat_feature_names

# Generate sample data as DataFrame
new_df, new_y, new_cat_features = generate_data_df()

# Split data
X_train, X_test, y_train, y_test = train_test_split(
    new_df, new_y, test_size=0.2, random_state=42
)

# Scale only numerical columns
num_cols = [col for col in new_df.columns if col not in new_cat_features]
scaler = StandardScaler()
X_train[num_cols] = scaler.fit_transform(X_train[num_cols])
X_test[num_cols] = scaler.transform(X_test[num_cols])

# Official CatBoost implementation
cat_model = CatBoostClassifier(
    n_estimators=100,
    learning_rate=0.1,
    depth=6,
    l2_leaf_reg=3,
    random_strength=1,
    border_count=254,
    feature_border_type='UniformAndQuantiles',
    leaf_estimation_method='Newton',
    random_state=42,
    verbose=False
)
cat_model.fit(X_train, y_train, cat_features=new_cat_features)
cat_predictions = cat_model.predict(X_test)

print("\nOfficial CatBoost Implementation Results:")
print(f"Accuracy: {accuracy_score(y_test, cat_predictions):.4f}")
print("\nClassification Report:")
print(classification_report(y_test, cat_predictions))

plot_feature_importance(cat_model, feature_names=new_df.columns, title="Official CatBoost Feature Importances")
plot_confusion_matrix(y_test, cat_predictions, "Official CatBoost Implementation Confusion Matrix")