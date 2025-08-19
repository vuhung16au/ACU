# Supervised Classification

## What is Supervised Learning?

Supervised learning is a machine learning paradigm where an algorithm learns from labeled training data to make predictions or classifications on new, unseen data. The key characteristic is that the training data includes both input features and their corresponding correct outputs (labels or target variables).

**Key Components:**
- **Training Data**: A dataset with known input-output pairs
- **Features**: Input variables (X) that describe the data
- **Labels**: Output variables (y) that we want to predict
- **Model**: The algorithm that learns the mapping from features to labels

**Types of Supervised Learning:**
1. **Classification**: Predicting categorical/discrete outcomes
2. **Regression**: Predicting continuous numerical outcomes

**Example**: Predicting whether an email is spam (1) or not spam (0) based on features like word frequency, sender information, etc.

## What is Classification?

Classification is a supervised learning task where the goal is to predict the category or class of an object based on its features. The output is a discrete label from a predefined set of possible classes.

**Key Characteristics:**
- **Discrete Output**: Predicts one of several possible categories
- **Categorical Labels**: Classes can be binary (2 classes) or multi-class (3+ classes)
- **Decision Boundary**: The model learns boundaries that separate different classes

**Common Classification Applications:**
- **Binary Classification**: Spam detection, disease diagnosis, fraud detection
- **Multi-class Classification**: Image recognition, document categorization, sentiment analysis

**Example**: Classifying iris flowers into three species (setosa, versicolor, virginica) based on petal length, petal width, sepal length, and sepal width.

## Classification Algorithms

### 1. Discriminant Analysis

**What it is:**
Discriminant analysis is a statistical method that finds linear combinations of features that best separate different classes. It assumes that the data follows a multivariate normal distribution within each class.

**Types:**
- **Linear Discriminant Analysis (LDA)**: Assumes equal covariance matrices across classes
- **Quadratic Discriminant Analysis (QDA)**: Allows different covariance matrices for each class

**How it works:**
1. Calculates the mean vector for each class
2. Computes the within-class and between-class scatter matrices
3. Finds the optimal projection that maximizes between-class variance while minimizing within-class variance
4. Projects new data points onto this discriminant function to classify them

**Benefits:**
- **Probabilistic Output**: Provides class probabilities, not just predictions
- **Feature Reduction**: Can reduce dimensionality while preserving class separation
- **Interpretable**: The discriminant function coefficients show feature importance
- **Robust**: Works well with small sample sizes relative to feature count
- **No Hyperparameters**: Few parameters to tune

**Use Cases:**
- Medical diagnosis
- Credit scoring
- Market segmentation
- Face recognition

### 2. k-Nearest Neighbors (k-NN)

**What it is:**
k-NN is a non-parametric, instance-based learning algorithm that classifies objects based on the majority class among their k nearest neighbors in the feature space.

**How it works:**
1. Stores all training data points
2. For a new data point, calculates distances to all training points
3. Identifies the k nearest neighbors
4. Assigns the class that appears most frequently among these k neighbors
5. For ties, can use distance-weighted voting

**Key Parameters:**
- **k**: Number of neighbors to consider
- **Distance Metric**: Euclidean, Manhattan, Minkowski, etc.
- **Weighting**: Uniform or distance-weighted voting

**Benefits:**
- **Simple**: Easy to understand and implement
- **No Training**: No model training phase required
- **Non-linear**: Can capture complex decision boundaries
- **Adaptive**: Naturally adapts to local structure in data
- **No Assumptions**: Makes no assumptions about data distribution

**Limitations:**
- **Computationally Expensive**: O(n) for each prediction
- **Curse of Dimensionality**: Performance degrades with high-dimensional data
- **Sensitive to Irrelevant Features**: All features are treated equally
- **Memory Intensive**: Stores entire training dataset

**Use Cases:**
- Recommendation systems
- Pattern recognition
- Medical diagnosis
- Image classification

### 3. Naïve Bayes

**What it is:**
Naïve Bayes is a probabilistic classifier based on Bayes' theorem with an assumption of conditional independence between features given the class.

**How it works:**
1. Calculates prior probabilities P(C) for each class
2. Calculates likelihood P(X|C) for each feature given each class
3. Applies Bayes' theorem: P(C|X) ∝ P(X|C) × P(C)
4. Assumes feature independence: P(X|C) = ∏P(xi|C)
5. Predicts the class with highest posterior probability

**Types:**
- **Gaussian Naïve Bayes**: Assumes features follow normal distribution
- **Multinomial Naïve Bayes**: For discrete count data (e.g., text)
- **Bernoulli Naïve Bayes**: For binary features

**Benefits:**
- **Fast**: Very quick training and prediction
- **Probabilistic**: Provides class probabilities
- **Handles Missing Data**: Can work with incomplete datasets
- **Small Training Data**: Works well with limited training examples
- **Interpretable**: Easy to understand the decision process

**Limitations:**
- **Independence Assumption**: Features are rarely truly independent
- **Feature Quality**: Sensitive to irrelevant or redundant features
- **Zero Probability Problem**: Can occur with unseen feature combinations

**Use Cases:**
- Text classification (spam detection, sentiment analysis)
- Medical diagnosis
- Document categorization
- Email filtering

## Data Format Requirements

### General Requirements for All Algorithms

**Data Structure:**
- **Features (X)**: Numerical or categorical variables
- **Target (y)**: Categorical labels
- **No Missing Values**: Handle missing data before training
- **Balanced Classes**: Imbalanced data may require special handling

**Data Preprocessing:**
- **Feature Scaling**: Normalize/standardize numerical features
- **Encoding**: Convert categorical variables to numerical
- **Feature Selection**: Remove irrelevant or redundant features

### Specific Requirements

#### Discriminant Analysis
- **Numerical Features**: All features must be continuous
- **Normal Distribution**: Features should be approximately normally distributed
- **Homoscedasticity**: Equal variance across classes (for LDA)
- **No Multicollinearity**: Features should not be highly correlated
- **Sample Size**: n > p (number of samples > number of features)

#### k-Nearest Neighbors
- **Feature Scaling**: Essential due to distance-based calculations
- **Numerical Features**: Works best with numerical data
- **Categorical Features**: Must be encoded (one-hot, label encoding)
- **No Missing Values**: Cannot handle missing values
- **Feature Selection**: Important to remove irrelevant features

#### Naïve Bayes
- **Feature Independence**: Assumes features are conditionally independent
- **Categorical Features**: Natural fit for discrete data
- **Numerical Features**: Can use Gaussian NB for continuous data
- **No Feature Scaling**: Not required (except for Gaussian NB)
- **Sparse Data**: Handles sparse feature matrices well

## Interpreting Model Outputs

### Classification Metrics

**Accuracy**: Overall correct predictions / Total predictions
- **Pros**: Simple to understand
- **Cons**: Misleading with imbalanced classes

**Precision**: True Positives / (True Positives + False Positives)
- **Use**: When false positives are costly

**Recall (Sensitivity)**: True Positives / (True Positives + False Negatives)
- **Use**: When false negatives are costly

**F1-Score**: 2 × (Precision × Recall) / (Precision + Recall)
- **Use**: Balanced measure between precision and recall

**ROC-AUC**: Area under the Receiver Operating Characteristic curve
- **Use**: Overall model performance across different thresholds

### Algorithm-Specific Interpretations

#### Discriminant Analysis
- **Discriminant Function Coefficients**: Show feature importance
- **Eigenvalues**: Indicate the strength of each discriminant function
- **Canonical Correlations**: Measure the relationship between features and classes
- **Classification Results**: Confusion matrix and classification report

#### k-Nearest Neighbors
- **Distance Values**: Show how similar new points are to training data
- **Neighbor Distribution**: Reveal local data density
- **Voting Patterns**: Indicate confidence in predictions
- **No Model Parameters**: Interpretation focuses on data characteristics

#### Naïve Bayes
- **Prior Probabilities**: Show class distribution in training data
- **Likelihood Probabilities**: Indicate feature importance for each class
- **Posterior Probabilities**: Provide prediction confidence
- **Feature Contributions**: Show how each feature affects the prediction

### Practical Interpretation Tips

1. **Start with Confusion Matrix**: Understand where the model makes mistakes
2. **Check Class Balance**: Ensure metrics aren't misleading due to imbalanced data
3. **Feature Importance**: Identify which features drive predictions
4. **Probability Thresholds**: Adjust decision boundaries based on business needs
5. **Cross-Validation**: Ensure performance generalizes to new data
6. **Domain Knowledge**: Validate results against business understanding

### Example Interpretation

For a spam detection model:
- **High Precision**: Few legitimate emails marked as spam
- **High Recall**: Most spam emails are caught
- **Feature Analysis**: Words like "free", "money", "urgent" have high likelihood for spam class
- **Threshold Tuning**: Adjust based on cost of false positives vs. false negatives
