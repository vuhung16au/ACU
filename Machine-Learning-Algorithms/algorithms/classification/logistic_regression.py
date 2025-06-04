## Logistic Regression with scikit-learn
#
# This notebook demonstrates how to use scikit-learn's LogisticRegression in a functional style.
# It includes fitting, predicting, and visualizing results using seaborn.

# --- Imports ---
import numpy as np  # For numerical operations
import seaborn as sns  # For visualization
from sklearn.linear_model import LogisticRegression  # Logistic Regression model
from sklearn.utils.validation import check_X_y, check_array  # Input validation
from sklearn.datasets import make_classification  # For generating a toy dataset
from sklearn.model_selection import train_test_split  # For splitting data
from sklearn.metrics import accuracy_score, confusion_matrix  # For evaluation

## Set random seed for reproducibility
np.random.seed(2220)

# Store the model as a global variable (for demonstration purposes)
_logreg_model = None

# --- Logistic Regression Functional API ---

def fit_logistic_regression(X, y, fit_intercept=True, max_iter=1000, solver='lbfgs', random_state=None):
    """
    ## Fit a logistic regression model using scikit-learn's LogisticRegression.
    #
    # Hyperparameters:
    # - fit_intercept: Whether to add an intercept term to the model (default: True)
    # - max_iter: Maximum number of iterations for the solver to converge (default: 1000)
    # - solver: Algorithm to use in the optimization problem (default: 'lbfgs')
    # - random_state: Seed for the random number generator (default: None)
    """
    global _logreg_model
    X, y = check_X_y(X, y)
    model = LogisticRegression(
        fit_intercept=fit_intercept,  # Whether to calculate the intercept for this model
        max_iter=max_iter,            # Maximum number of iterations taken for the solvers to converge
        solver=solver,                # Algorithm to use in the optimization problem
        random_state=random_state     # Seed of the pseudo random number generator
    )
    model.fit(X, y)
    _logreg_model = model
    return model


def predict_logistic_regression(X, model=None):
    """
    ## Predict class labels using a fitted logistic regression model.
    If model is None, uses the last fitted model.
    """
    if model is None:
        model = _logreg_model
    X = check_array(X)
    return model.predict(X)


def predict_proba_logistic_regression(X, model=None):
    """
    ## Predict class probabilities using a fitted logistic regression model.
    If model is None, uses the last fitted model.
    """
    if model is None:
        model = _logreg_model
    X = check_array(X)
    return model.predict_proba(X)

# --- Demonstration: Logistic Regression on a Toy Dataset ---

# ## Generate a synthetic binary classification dataset
# - n_samples: Number of samples
# - n_features: Number of features
# - n_informative: Number of informative features
# - n_redundant: Number of redundant features
# - n_classes: Number of classes
# - random_state: Seed for reproducibility
X, y = make_classification(
    n_samples=300,         # Number of samples
    n_features=2,          # Number of features
    n_informative=2,       # Number of informative features
    n_redundant=0,         # Number of redundant features
    n_classes=2,           # Number of classes
    random_state=2220      # Seed for reproducibility
)

# ## Split the dataset into training and testing sets
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.3, random_state=2220
)

# ## Fit the logistic regression model
model = fit_logistic_regression(
    X_train, y_train,
    fit_intercept=True,    # Whether to calculate the intercept for this model
    max_iter=1000,         # Maximum number of iterations
    solver='lbfgs',        # Optimization algorithm
    random_state=2220      # Seed for reproducibility
)

# ## Predict on the test set
y_pred = predict_logistic_regression(X_test, model)

# ## Calculate accuracy
accuracy = accuracy_score(y_test, y_pred)
print(f"Test Accuracy: {accuracy:.2f}")

# ## Confusion matrix
cm = confusion_matrix(y_test, y_pred)

# ## Visualize the confusion matrix using seaborn
sns.set(style="whitegrid")
ax = sns.heatmap(cm, annot=True, fmt="d", cmap="Blues", cbar=False)
ax.set_xlabel('Predicted label')
ax.set_ylabel('True label')
ax.set_title('Confusion Matrix')

# ## Save the confusion matrix plot to a PNG file
import matplotlib.pyplot as plt
plt.savefig('algorithms/classification/logistic_regression_confusion_matrix.png')
plt.close()

# ## Visualize the decision boundary (for 2D data)
def plot_decision_boundary(X, y, model, filename):
    """
    ## Plot the decision boundary for a 2D dataset and save to file
    """
    # Create a mesh grid
    x_min, x_max = X[:, 0].min() - 1, X[:, 0].max() + 1
    y_min, y_max = X[:, 1].min() - 1, X[:, 1].max() + 1
    xx, yy = np.meshgrid(
        np.linspace(x_min, x_max, 200),
        np.linspace(y_min, y_max, 200)
    )
    grid = np.c_[xx.ravel(), yy.ravel()]
    probs = predict_proba_logistic_regression(grid, model)[:, 1].reshape(xx.shape)
    
    # Plot
    plt.figure(figsize=(8, 6))
    contour = plt.contourf(xx, yy, probs, 25, cmap="Blues", alpha=0.8)
    sns.scatterplot(x=X[:, 0], y=X[:, 1], hue=y, palette="Set1", edgecolor="k")
    plt.title('Logistic Regression Decision Boundary')
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    plt.legend(title='Class')
    plt.savefig(filename)
    plt.close()

# ## Plot and save the decision boundary
plot_decision_boundary(X_test, y_test, model, 'algorithms/classification/logistic_regression_decision_boundary.png')