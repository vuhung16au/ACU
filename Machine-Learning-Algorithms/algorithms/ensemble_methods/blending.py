# %% [markdown]
## Blending Ensemble Implementation using scikit-learn
# This notebook demonstrates blending (a variant of stacking) using sklearn's StackingClassifier with a holdout set.
# We use only seaborn for visualization, and the code is ready for Jupyter Notebook format.

# %% [markdown]
## 1. Import Required Libraries
import numpy as np
import seaborn as sns
from sklearn.datasets import make_classification
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn.svm import SVC
from sklearn.linear_model import LogisticRegression
from sklearn.ensemble import StackingClassifier
from sklearn.metrics import accuracy_score, confusion_matrix, classification_report, roc_curve, auc
import matplotlib.pyplot as plt  # Only for seaborn display
import os

# %% [markdown]
## 2. Set Random Seed for Reproducibility
np.random.seed(2220)  # Ensures reproducible results for all models and data splits

# %% [markdown]
## 3. Generate Sample Data
# - n_samples: Number of samples in the dataset
# - n_features: Total number of features
# - n_informative: Number of informative features
# - n_redundant: Number of redundant features
# - n_classes: Number of output classes (for multiclass classification)
# - random_state: Seed for reproducibility
X, y = make_classification(
    n_samples=1000,      # Number of samples
    n_features=20,       # Total number of features
    n_informative=15,    # Number of informative features
    n_redundant=5,       # Number of redundant features
    n_classes=3,         # Number of classes (for multiclass classification)
    random_state=2220    # Random seed for reproducibility
)

# %% [markdown]
## 4. Split Data for Blending
# - 60% for training base models
# - 20% for holdout (meta-model training)
# - 20% for testing (final evaluation)
X_train, X_temp, y_train, y_temp = train_test_split(
    X, y, test_size=0.4, random_state=2220
)
X_holdout, X_test, y_holdout, y_test = train_test_split(
    X_temp, y_temp, test_size=0.5, random_state=2220
)

# %% [markdown]
## 5. Define Base Models and Meta-Model
# - Base models should be diverse for best ensemble performance
# - All models use random_state=2220 for reproducibility
base_models = [
    # DecisionTreeClassifier: max_depth=3 (tree depth), random_state=2220 (seed)
    ("dt", DecisionTreeClassifier(max_depth=3, random_state=2220)),
    # SVC: probability=True (enable probability output), random_state=2220 (seed)
    ("svc", SVC(probability=True, random_state=2220)),
    # LogisticRegression: random_state=2220 (seed)
    ("lr", LogisticRegression(random_state=2220))
]
# Meta-model (blender): LogisticRegression with random_state=2220
meta_model = LogisticRegression(random_state=2220)

# %% [markdown]
## 6. Fit Base Models on Training Set
# Required for cv="prefit" in StackingClassifier
for _, model in base_models:
    model.fit(X_train, y_train)

# %% [markdown]
## 7. Create and Train Blending Ensemble (StackingClassifier)
# - estimators: List of (name, estimator) tuples for base models
# - final_estimator: Meta-model (blender)
# - cv="prefit": Use prefit base models and a holdout set for blending
# - stack_method="predict_proba": Use class probabilities as input to meta-model
blending = StackingClassifier(
    estimators=[(name, model) for name, model in base_models],
    final_estimator=meta_model,
    cv="prefit",                # Use prefit base models and a holdout set for blending
    stack_method="predict_proba" # Use class probabilities as input to meta-model
)
blending.fit(X_holdout, y_holdout)  # Fit meta-model on holdout set

# %% [markdown]
## 8. Make Predictions and Evaluate the Blending Ensemble
pred = blending.predict(X_test)
proba = blending.predict_proba(X_test)

# Print accuracy and classification report
print(f"Accuracy: {accuracy_score(y_test, pred):.3f}")
print(classification_report(y_test, pred))

# %% [markdown]
## 9. Visualize Results with Seaborn (Save to PNG Files)
# Output directory for saving plots
output_dir = 'algorithms/ensemble_methods/blending_outputs'
os.makedirs(output_dir, exist_ok=True)

def plot_confusion_matrix_seaborn(y_true, y_pred, title="Confusion Matrix", filename="confusion_matrix.png"):
    """
    Plot confusion matrix using seaborn heatmap and save to a PNG file.
    """
    cm = confusion_matrix(y_true, y_pred)
    plt.figure(figsize=(6, 5))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues', cbar=False)
    plt.title(title)
    plt.xlabel('Predicted')
    plt.ylabel('True')
    # Save the plot to a PNG file
    plt.savefig(os.path.join(output_dir, filename), bbox_inches='tight')
    plt.close()
    print(f"Confusion matrix saved to {os.path.join(output_dir, filename)}")

def plot_roc_curves_seaborn(y_true, y_pred_proba, title="ROC Curves", filename="roc_curves.png"):
    """
    Plot ROC curves for each class using seaborn lineplot and save to a PNG file.
    """
    plt.figure(figsize=(7, 5))
    for i in range(y_pred_proba.shape[1]):
        fpr, tpr, _ = roc_curve(y_true == i, y_pred_proba[:, i])
        auc_score = auc(fpr, tpr)
        sns.lineplot(x=fpr, y=tpr, label=f'Class {i} (AUC={auc_score:.2f})')
    plt.plot([0, 1], [0, 1], 'k--')
    plt.xlabel('False Positive Rate')
    plt.ylabel('True Positive Rate')
    plt.title(title)
    plt.legend()
    # Save the plot to a PNG file
    plt.savefig(os.path.join(output_dir, filename), bbox_inches='tight')
    plt.close()
    print(f"ROC curves saved to {os.path.join(output_dir, filename)}")

# Plot and save confusion matrix
plot_confusion_matrix_seaborn(y_test, pred, title="Blending Confusion Matrix", filename="blending_confusion_matrix.png")

# Plot and save ROC curves
plot_roc_curves_seaborn(y_test, proba, title="Blending ROC Curves", filename="blending_roc_curves.png") 