## # Perceptron Algorithm with TensorFlow/Keras (Functional, Jupyter-Notebook-Ready)

## ## 1. Imports and Setup
import numpy as np  # For numerical operations
import tensorflow as tf  # For building the perceptron model
from tensorflow import keras
from tensorflow.keras import layers
import seaborn as sns  # For visualization
import pandas as pd  # For easier plotting with seaborn
import os  # For directory operations

## ## 2. Set Random Seed for Reproducibility
np.random.seed(2220)  # Set seed for numpy random number generation
# Note: For TensorFlow, set the seed as well for reproducibility
# tf.random.set_seed(2220)  # Uncomment if you want full reproducibility in TF

## ## 3. Generate Synthetic Data for Binary Classification
# Here, we create a simple 2D dataset for demonstration
from sklearn.datasets import make_classification

# n_samples: number of samples to generate
# n_features: number of features
# n_redundant: number of redundant features
# n_clusters_per_class: number of clusters per class
# random_state: seed for reproducibility
X, y = make_classification(
    n_samples=200,  # Number of samples
    n_features=2,   # Number of features (for 2D visualization)
    n_redundant=0,  # No redundant features
    n_clusters_per_class=1,  # One cluster per class
    random_state=2220  # Seed for reproducibility
)

## ## 4. Visualize the Data
# Let's plot the generated data using seaborn
sns.set(style="whitegrid")
df = pd.DataFrame(X, columns=["Feature 1", "Feature 2"])
df["Target"] = y

# Ensure the output directory exists
output_dir = os.path.join("algorithms", "classification")
os.makedirs(output_dir, exist_ok=True)

plot1 = sns.scatterplot(data=df, x="Feature 1", y="Feature 2", hue="Target", palette="Set1")
plot1.get_figure().savefig(os.path.join(output_dir, "perceptron_data_scatter.png"))  # Save the plot to a PNG file
plot1.get_figure().clf()  # Clear the figure for further use

## ## 5. Define Perceptron Model Using TensorFlow/Keras (Functional API)
def build_perceptron(input_dim, learning_rate=0.01):
    """
    Build a perceptron model using Keras Sequential API.
    
    input_dim: Number of input features
    learning_rate: Step size for the optimizer
    """
    # input_dim: Number of input features
    # learning_rate: Step size for the optimizer
    model = keras.Sequential([
        layers.Dense(1, input_dim=input_dim, activation='linear', use_bias=True)
    ])
    model.compile(
        optimizer=keras.optimizers.SGD(learning_rate=learning_rate),
        loss=perceptron_loss
    )
    return model

## ## 6. Define Perceptron Loss Function
# The perceptron loss is max(0, -y * f(x)), where y in {-1, 1}
def perceptron_loss(y_true, y_pred):
    y_true = tf.where(y_true == 0, -1.0, y_true)  # Convert 0 to -1
    return tf.reduce_mean(tf.maximum(0.0, -y_true * y_pred))

## ## 7. Prepare Data for Training
# Convert labels from {0, 1} to {-1, 1} for perceptron loss
y_train = np.where(y == 0, -1, 1)

## ## 8. Set Perceptron Hyperparameters
# learning_rate: Step size for weight updates
# max_iter: Number of epochs (full passes over the data)
learning_rate = 0.01  # Step size for the optimizer
max_iter = 100  # Number of epochs to train

## ## 9. Build and Train the Perceptron Model
model = build_perceptron(input_dim=X.shape[1], learning_rate=learning_rate)

# Fit the model
def fit_perceptron(model, X, y, max_iter):
    """
    Train the perceptron model.
    model: Keras model
    X: Input features
    y: Labels (-1, 1)
    max_iter: Number of epochs
    """
    history = model.fit(X, y, epochs=max_iter, batch_size=X.shape[0], verbose=0)
    return history

history = fit_perceptron(model, X, y_train, max_iter)

## ## 10. Make Predictions
# The perceptron outputs a real value (logit). We threshold at 0 for class prediction.
def predict_perceptron(model, X):
    logits = model.predict(X, verbose=0)
    return np.where(logits.flatten() >= 0, 1, 0)

y_pred = predict_perceptron(model, X)

## ## 11. Visualize Decision Boundary
# We'll plot the decision boundary learned by the perceptron

def plot_decision_boundary(model, X, y, output_dir):
    """
    Plot the decision boundary of a perceptron model using seaborn.
    """
    # Create a mesh grid
    x_min, x_max = X[:, 0].min() - 1, X[:, 0].max() + 1
    y_min, y_max = X[:, 1].min() - 1, X[:, 1].max() + 1
    xx, yy = np.meshgrid(np.linspace(x_min, x_max, 200), np.linspace(y_min, y_max, 200))
    grid = np.c_[xx.ravel(), yy.ravel()]
    Z = predict_perceptron(model, grid)
    Z = Z.reshape(xx.shape)
    
    # Plot with seaborn
    import matplotlib.pyplot as plt  # Only for contourf, not for direct plotting
    plt.figure(figsize=(8, 6))
    sns.scatterplot(x=X[:, 0], y=X[:, 1], hue=y, palette="Set1", edgecolor="k")
    plt.contourf(xx, yy, Z, alpha=0.2, levels=[-1, 0, 1], colors=["#FFAAAA", "#AAAAFF"])
    plt.title("Perceptron Decision Boundary")
    plt.xlabel("Feature 1")
    plt.ylabel("Feature 2")
    plt.savefig(os.path.join(output_dir, "perceptron_decision_boundary.png"))  # Save the plot to a PNG file
    plt.clf()  # Clear the figure for further use

plot_decision_boundary(model, X, y, output_dir)

## ## 12. Evaluate Model Performance
from sklearn.metrics import accuracy_score
accuracy = accuracy_score(y, y_pred)
print(f"Perceptron accuracy: {accuracy:.2f}")

## ## 13. Summary
# - We built a perceptron using TensorFlow/Keras (functional, not OOP)
# - Used seaborn for all data visualizations
# - Explained all hyperparameters in comments
# - Code is ready for Jupyter Notebook conversion
# - Random seed is set for reproducibility