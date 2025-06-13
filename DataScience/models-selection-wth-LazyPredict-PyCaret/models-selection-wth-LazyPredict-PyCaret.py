"""
Machine Learning Model Selection Workflow using LazyPredict and PyCaret
-----------------------------------------------------------------------
This script demonstrates an automated machine learning workflow for model selection 
and comparison using LazyPredict and PyCaret libraries. The California Housing dataset
is used to showcase various classification models and their performance.
"""

# Import necessary libraries
# pandas: For data manipulation and analysis
import pandas as pd
# sklearn: For splitting the dataset into training and testing sets
from sklearn.model_selection import train_test_split
# LazyPredict: For automated model selection and comparison
from lazypredict.Supervised import LazyClassifier
# PyCaret: For automated machine learning workflow - importing specific functions instead of wildcard
from pycaret.classification import setup, compare_models
# matplotlib: For data visualization
import matplotlib.pyplot as plt


# --------- DATA LOADING AND PREPARATION ---------

# Load dataset from GitHub repository
# The California Housing dataset is used for demonstration purposes
url = "https://raw.githubusercontent.com/vuhung16au/ACU/refs/heads/main/ITEC203/Week05/CaliforniaHousing.csv"

# Load the dataset into a pandas DataFrame
# header=1 specifies that the column names are in the second row (index 1)
df = pd.read_csv(url, header=1)

# Feature extraction
# X: All columns except the last one (features/predictors)
X = df.iloc[:, :-1]
# y: Only the last column (target variable)
y = df.iloc[:, -1]

# --------- DATA SPLITTING ---------

# Split the dataset into training and testing sets
# test_size=0.2: 20% of the data will be used for testing, 80% for training
# random_state=78: Set seed for reproducibility of results
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=78)


# --------- MODEL SELECTION USING LAZYPREDICT ---------

# Initialize LazyClassifier
# verbose=0: Suppress detailed output during model training
# ignore_warnings=True: Ignore warning messages
clf = LazyClassifier(verbose=0, ignore_warnings=True)

# Train multiple models at once and get predictions
# LazyPredict automatically trains and evaluates dozens of classification models
models, predictions = clf.fit(X_train, X_test, y_train, y_test)

# Display the top 5 performing models based on accuracy
print(models.head(5))

# --------- VISUALIZATION OF TOP MODELS ---------

# Select the top 10 models sorted by accuracy (descending order)
top_models = models.sort_values("Accuracy", ascending=False).head(10)

# Create a horizontal bar chart to visualize model performance
plt.figure(figsize=(10, 6))  # Set figure size: width=10, height=6 inches
top_models["Accuracy"].plot(kind="barh", color="skyblue")  # Horizontal bar plot
plt.xlabel("Accuracy")  # Label for x-axis
plt.title("Top 10 Models by Accuracy (LazyPredict)")  # Title of the plot
plt.gca().invert_yaxis()  # Invert y-axis to have highest accuracy at the top
plt.tight_layout()  # Adjust layout to make room for labels

# Save the visualization to a PNG file for later reference
plt.savefig("top_models_accuracy.png")

# Display the plot on screen
plt.show()


# --------- MODEL SELECTION USING PYCARET ---------

# Initialize PyCaret setup with the dataset
# data: The entire dataset
# target: The name of the target column (last column in this case)
clf = setup(data=df, target=df.columns[-1])

# Compare and evaluate multiple models using PyCaret
# This creates and evaluates multiple models, returning the best one
best_model = compare_models()

# Note: PyCaret provides a more comprehensive ML workflow than LazyPredict,
# including preprocessing, feature engineering, hyperparameter tuning,
# and model interpretation capabilities.

# You can now continue to explore the best_model object for further analysis