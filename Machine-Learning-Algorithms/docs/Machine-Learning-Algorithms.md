# Machine Learning Algorithms

## Introduction

This document provides a comprehensive overview of various machine learning algorithms implemented in this repository. Each algorithm is explained with its mathematical foundations, implementation details, performance characteristics, and practical applications.

## Table of Contents

### Supervised Learning
- [Linear Regression](algorithms/linear_regression/linear_regression.md)
- [Polynomial Regression](algorithms/polynomial_regression/polynomial_regression.md)
- [Logistic Regression](algorithms/logistic_regression/logistic_regression.md)
- [Softmax Regression](algorithms/softmax_regression/softmax_regression.md)
- [Support Vector Machines](algorithms/support_vector_machines/svm.md)
- [Decision Trees](algorithms/decision_trees/decision_trees.md)
- [Random Forests](algorithms/random_forests/random_forests.md)
- [Gradient Boosting](algorithms/gradient_boosting/gradient_boosting.md)
- [XGBoost](algorithms/xgboost/xgboost.md)
- [LightGBM](algorithms/lightgbm/lightgbm.md)
- [CatBoost](algorithms/catboost/catboost.md)
- [k-Nearest Neighbors](algorithms/k_nearest_neighbors/knn.md)

### Unsupervised Learning
- [Clustering](algorithms/clustering/clustering.md)
- [Dimensionality Reduction](algorithms/dimensionality_reduction/dimensionality_reduction.md)
- [Association Rules](algorithms/association_rules/association_rules.md)
- [Anomaly Detection](algorithms/anomaly_detection/anomaly_detection.md)

### Ensemble Methods
- [AdaBoost](algorithms/adaboost/adaboost.md)
- [Ensemble Methods](algorithms/ensemble_methods/ensemble_methods.md)
- [Combining Techniques](algorithms/combining-techniques/combining_techniques.md)

### Deep Learning
- [Neural Networks](algorithms/neural_networks/neural_networks.md)
- [Deep Learning](algorithms/deep_learning/deep_learning.md)
- [Graph Neural Networks](algorithms/graph_neural_networks/graph_neural_networks.md)
- [Computer Vision](algorithms/computer_vision/computer_vision.md)

### Time Series
- [Time Series Analysis](algorithms/time_series/time_series.md)

### Bayesian Methods
- [Naive Bayes](algorithms/naive_bayes/naive_bayes.md)
- [Bayesian Classification](algorithms/bayesian_classification/bayesian_classification.md)
- [Bayesian Methods](algorithms/bayesian_methods/bayesian_methods.md)

### Other Algorithms
- [Reinforcement Learning](algorithms/reinforcement_learning/reinforcement_learning.md)
- [Optimization](algorithms/optimization/optimization.md)
- [Specialized Models](algorithms/specialized_models/specialized_models.md)

### Fuzzy Logic
Fuzzy Logic is a form of many-valued logic that deals with reasoning that is approximate rather than fixed and exact, allowing for degrees of truth.

#### Mathematical Foundation
**Membership Function:**
$$
\mu_A(x) \in [0,1]
$$
where $\mu_A(x)$ is the degree of membership of $x$ in fuzzy set $A$.

**Fuzzy Set Operations:**
- Union: $\mu_{A \cup B}(x) = \max(\mu_A(x), \mu_B(x))$
- Intersection: $\mu_{A \cap B}(x) = \min(\mu_A(x), \mu_B(x))$
- Complement: $\mu_{\bar{A}}(x) = 1 - \mu_A(x)$

**Fuzzy Rules:**
- IF-THEN rules (e.g., IF temperature is hot THEN fan speed is high)

**Defuzzification:**
- Converts fuzzy output to a crisp value (e.g., centroid method)

#### Implementation
Implemented using scikit-fuzzy or custom code.

#### Performance Analysis
- Time Complexity: O(n * m) where $m$ is number of rules
- Space Complexity: O(n)

#### Use Cases
- Control systems (e.g., washing machines, air conditioners)
- Decision making (e.g., risk assessment)
- Pattern recognition
- Expert systems
- Robotics

**Example:**
Controlling the speed of a fan based on temperature and humidity using fuzzy rules.

#### Advantages and Limitations
Advantages:
- Handles imprecise and vague data
- Can model complex systems with simple rules
- Intuitive and interpretable

Limitations:
- Requires expert knowledge to define rules and membership functions
- May be computationally intensive for many rules
- Results can be subjective

#### Relation to Other Models
- **Probabilistic Models:** Fuzzy logic models uncertainty as degrees of truth, while probability models uncertainty as likelihood.
- **Rule-Based Systems:** Fuzzy logic extends crisp rule-based systems to handle partial truth.
- **Neuro-Fuzzy Systems:** Combine neural networks and fuzzy logic for adaptive rule learning.

#### Python Implementation and Visualization
```python
import numpy as np
import skfuzzy as fuzz
from skfuzzy import control as ctrl
import matplotlib.pyplot as plt

# Create fuzzy variables
temperature = ctrl.Antecedent(np.arange(0, 41, 1), 'temperature')
humidity = ctrl.Antecedent(np.arange(0, 101, 1), 'humidity')
fan_speed = ctrl.Consequent(np.arange(0, 101, 1), 'fan_speed')

# Define membership functions
temperature['cold'] = fuzz.trimf(temperature.universe, [0, 0, 20])
temperature['warm'] = fuzz.trimf(temperature.universe, [10, 20, 30])
temperature['hot'] = fuzz.trimf(temperature.universe, [20, 40, 40])
humidity['low'] = fuzz.trimf(humidity.universe, [0, 0, 50])
humidity['high'] = fuzz.trimf(humidity.universe, [50, 100, 100])
fan_speed['low'] = fuzz.trimf(fan_speed.universe, [0, 0, 50])
fan_speed['medium'] = fuzz.trimf(fan_speed.universe, [25, 50, 75])
fan_speed['high'] = fuzz.trimf(fan_speed.universe, [50, 100, 100])

# Define fuzzy rules
rule1 = ctrl.Rule(temperature['cold'] | humidity['low'], fan_speed['low'])
rule2 = ctrl.Rule(temperature['warm'] & humidity['high'], fan_speed['medium'])
rule3 = ctrl.Rule(temperature['hot'] | humidity['high'], fan_speed['high'])

# Create control system
fan_ctrl = ctrl.ControlSystem([rule1, rule2, rule3])
fan_sim = ctrl.ControlSystemSimulation(fan_ctrl)

# Simulate for a specific input
fan_sim.input['temperature'] = 30
fan_sim.input['humidity'] = 80
fan_sim.compute()
print('Fan speed:', fan_sim.output['fan_speed'])

# Visualize membership functions
temperature.view()
humidity.view()
fan_speed.view()
plt.show()
```

## Model Introductions

### Linear Regression
Linear regression is a fundamental supervised learning algorithm used for predicting continuous values. It models the relationship between a dependent variable and one or more independent variables using a linear equation.

#### Mathematical Foundation
The basic form of linear regression is:

$$y = \beta_0 + \beta_1x_1 + \beta_2x_2 + ... + \beta_nx_n + \epsilon$$

where:
- $y$ is the dependent variable
- $x_i$ are the independent variables
- $\beta_i$ are the coefficients
- $\epsilon$ is the error term

The goal is to minimize the sum of squared errors:

$$\min_{\beta} \sum_{i=1}^{n} (y_i - \hat{y}_i)^2$$

#### Implementation
Implemented using scikit-learn's `LinearRegression` class.

#### Performance Analysis
- Time Complexity: O(n*p) for training, where n is number of samples and p is number of features
- Space Complexity: O(p) for storing coefficients

#### Use Cases
- House price prediction
- Sales forecasting
- Risk assessment
- Trend analysis

#### Advantages and Limitations
Advantages:
- Simple to understand and implement
- Computationally efficient
- Provides interpretable results

Limitations:
- Assumes linear relationship
- Sensitive to outliers
- Cannot handle non-linear patterns

#### Python Implementation
```python
from sklearn.linear_model import LinearRegression

model = LinearRegression(
    fit_intercept=True,  # Whether to calculate the intercept
    normalize=False,     # Whether to normalize features
    n_jobs=None         # Number of jobs for parallel computation
)
```

### Support Vector Machines (SVM)
SVMs are powerful supervised learning models used for classification and regression tasks. They find the optimal hyperplane that separates classes with the maximum margin.

#### Mathematical Foundation
The optimization problem for SVM is:

$$
\min_{w,b,\xi} \frac{1}{2}||w||^2 + C\sum_{i=1}^{n}\xi_i
$$

subject to:
$$
y_i(w^T\phi(x_i) + b) \geq 1 - \xi_i, \quad \xi_i \geq 0
$$

where:
- $w$ is the weight vector
- $b$ is the bias term
- $C$ is the regularization parameter (controls trade-off between maximizing margin and minimizing classification error)
- $\xi_i$ are slack variables (allow for misclassification)
- $\phi(x)$ is the kernel function (maps input to higher-dimensional space)

**Dual Formulation:**
The dual problem is often solved in practice:
$$
\max_{\alpha} \sum_{i=1}^n \alpha_i - \frac{1}{2} \sum_{i=1}^n \sum_{j=1}^n \alpha_i \alpha_j y_i y_j K(x_i, x_j)
$$
subject to $0 \leq \alpha_i \leq C$ and $\sum_{i=1}^n \alpha_i y_i = 0$

**Kernels:**
- Linear: $K(x, x') = x^T x'$
- Polynomial: $K(x, x') = (\gamma x^T x' + r)^d$
- RBF (Gaussian): $K(x, x') = \exp(-\gamma ||x - x'||^2)$

#### Implementation
Implemented using scikit-learn's `SVC` class.

#### Performance Analysis
- Time Complexity: O(n²) to O(n³) depending on kernel and implementation
- Space Complexity: O(n) for storing support vectors

#### Use Cases
- Text classification (e.g., spam detection, sentiment analysis)
- Image classification (e.g., digit recognition with MNIST)
- Bioinformatics (e.g., cancer detection from gene expression)
- Handwriting recognition
- Face detection

**Example:**
Classifying handwritten digits (MNIST) or separating two classes in a 2D dataset.

#### Advantages and Limitations
Advantages:
- Effective in high-dimensional spaces
- Versatile through kernel trick (can model non-linear boundaries)
- Robust against overfitting, especially with proper regularization

Limitations:
- Computationally intensive for large datasets
- Sensitive to parameter tuning (C, kernel, gamma)
- Memory intensive for large datasets
- Less interpretable than linear models

#### Relation to Other Models
- **Logistic Regression:** Both are linear classifiers, but SVM maximizes margin while logistic regression maximizes likelihood.
- **Kernel Methods:** SVM is a classic example of kernel methods; other algorithms (e.g., kernel PCA) use similar ideas.
- **Perceptron:** SVM can be seen as a regularized, margin-maximizing version of the perceptron.

#### Python Implementation and Visualization
```python
import numpy as np
import matplotlib.pyplot as plt
from sklearn import datasets
from sklearn.svm import SVC
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from sklearn.preprocessing import StandardScaler

# Load a simple 2D dataset (Iris, two classes)
iris = datasets.load_iris()
X = iris.data[iris.target != 2, :2]  # Only two classes, two features
y = iris.target[iris.target != 2]

# Standardize features
scaler = StandardScaler()
X = scaler.fit_transform(X)

# Train/test split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

# Train SVM with RBF kernel
model = SVC(kernel='rbf', C=1.0, gamma='auto')
model.fit(X_train, y_train)

# Predict and evaluate
y_pred = model.predict(X_test)
print('Accuracy:', accuracy_score(y_test, y_pred))

# Visualization of decision boundary
xx, yy = np.meshgrid(np.linspace(X[:,0].min()-1, X[:,0].max()+1, 200),
                     np.linspace(X[:,1].min()-1, X[:,1].max()+1, 200))
Z = model.decision_function(np.c_[xx.ravel(), yy.ravel()])
Z = Z.reshape(xx.shape)

plt.contourf(xx, yy, Z, levels=np.linspace(Z.min(), 0, 7), cmap=plt.cm.PuBu)
plt.contour(xx, yy, Z, levels=[0], linewidths=2, colors='darkred')
plt.scatter(X[:,0], X[:,1], c=y, cmap=plt.cm.Set1, edgecolors='k')
plt.title('SVM Decision Boundary (RBF Kernel)')
plt.xlabel('Feature 1')
plt.ylabel('Feature 2')
plt.show()
```

### Decision Trees
Decision trees are versatile supervised learning algorithms that can perform both classification and regression tasks. They work by recursively splitting the data into subsets based on feature values.

#### Mathematical Foundation
A decision tree splits the data at each node based on a feature that maximizes a certain criterion (e.g., information gain or Gini impurity).

**Gini Impurity:**
$$
I_G(p) = 1 - \sum_{i=1}^{c} p_i^2
$$
where $p_i$ is the proportion of class $i$ at a node.

**Entropy:**
$$
H(p) = -\sum_{i=1}^{c} p_i \log_2 p_i
$$

**Information Gain:**
$$
IG(D_p, f) = I(D_p) - \sum_{j=1}^{m} \frac{N_j}{N_p}I(D_j)
$$
where $I$ is the impurity measure (Gini or Entropy), $N_j$ is the number of samples in child node $j$, and $N_p$ is the number of samples in the parent node.

**Stopping Criteria:**
- Maximum depth
- Minimum samples per leaf
- No further impurity reduction

#### Implementation
Implemented using scikit-learn's `DecisionTreeClassifier` and `DecisionTreeRegressor` classes.

#### Performance Analysis
- Time Complexity: O(n * log(n) * p) for training
- Space Complexity: O(n) for storing the tree structure

#### Use Cases
- Credit scoring (e.g., loan approval)
- Medical diagnosis (e.g., disease prediction)
- Customer segmentation (e.g., marketing)
- Risk assessment (e.g., insurance)
- Churn prediction

**Example:**
Classifying species in the Iris dataset or predicting if a customer will churn.

#### Advantages and Limitations
Advantages:
- Easy to understand and interpret (can be visualized as a flowchart)
- Can handle both numerical and categorical data
- Requires little data preprocessing
- Can capture non-linear relationships

Limitations:
- Can overfit easily (especially with deep trees)
- Unstable (small changes in data can lead to different trees)
- Biased towards features with more levels
- Not as accurate as ensemble methods

#### Relation to Other Models
- **Random Forests:** An ensemble of decision trees to reduce overfitting and improve accuracy.
- **Gradient Boosting:** Sequentially builds trees to correct errors of previous trees.
- **Rule-Based Models:** Decision trees can be converted to a set of if-then rules.

#### Python Implementation and Visualization
```python
import numpy as np
import matplotlib.pyplot as plt
from sklearn import datasets
from sklearn.tree import DecisionTreeClassifier, plot_tree
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

# Load Iris dataset (3 classes)
iris = datasets.load_iris()
X = iris.data[:, :2]  # Use first two features for visualization
y = iris.target

# Train/test split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

# Train Decision Tree
model = DecisionTreeClassifier(max_depth=3, criterion='gini', random_state=42)
model.fit(X_train, y_train)

# Predict and evaluate
y_pred = model.predict(X_test)
print('Accuracy:', accuracy_score(y_test, y_pred))

# Visualization of decision boundaries
x_min, x_max = X[:, 0].min() - 1, X[:, 0].max() + 1
y_min, y_max = X[:, 1].min() - 1, X[:, 1].max() + 1
xx, yy = np.meshgrid(np.arange(x_min, x_max, 0.02),
                     np.arange(y_min, y_max, 0.02))
Z = model.predict(np.c_[xx.ravel(), yy.ravel()])
Z = Z.reshape(xx.shape)
plt.contourf(xx, yy, Z, alpha=0.3, cmap=plt.cm.Paired)
plt.scatter(X[:, 0], X[:, 1], c=y, edgecolor='k', cmap=plt.cm.Paired)
plt.title('Decision Tree Decision Boundaries')
plt.xlabel('Feature 1')
plt.ylabel('Feature 2')
plt.show()

# Visualize the tree structure
plt.figure(figsize=(10,6))
plot_tree(model, feature_names=iris.feature_names[:2], class_names=iris.target_names, filled=True)
plt.title('Decision Tree Structure')
plt.show()
```

### Random Forests
Random Forests are an ensemble learning method that operates by constructing multiple decision trees and outputting the class that is the mode of the classes or mean prediction of the individual trees.

#### Mathematical Foundation
A random forest consists of $T$ decision trees, each trained on a bootstrap sample of the data and a random subset of features at each split.

**Prediction (Classification):**
$$
\hat{y} = \text{mode}\{h_1(x), h_2(x), ..., h_T(x)\}
$$
where $h_t(x)$ is the prediction of tree $t$.

**Prediction (Regression):**
$$
\hat{y} = \frac{1}{T}\sum_{t=1}^{T} h_t(x)
$$

**Feature Importance:**
- Measured by the average decrease in impurity (Gini or entropy) or by permutation importance.

**Out-of-Bag (OOB) Error:**
- Each tree is trained on a bootstrap sample; OOB samples are used for unbiased error estimation.

#### Implementation
Implemented using scikit-learn's `RandomForestClassifier` and `RandomForestRegressor` classes.

#### Performance Analysis
- Time Complexity: O(T * n * log(n) * p) where $T$ is number of trees
- Space Complexity: O(T * n) for storing all trees

#### Use Cases
- Credit risk assessment (e.g., loan default prediction)
- Medical diagnosis (e.g., disease classification)
- Stock market prediction
- Customer behavior analysis
- Feature selection in high-dimensional data

**Example:**
Classifying species in the Iris dataset or predicting customer churn with many features.

#### Advantages and Limitations
Advantages:
- Reduces overfitting compared to single decision trees
- Handles high-dimensional data well
- Provides feature importance measures
- Robust to noise and outliers
- Can handle missing values

Limitations:
- Can be computationally intensive (many trees)
- Less interpretable than single decision trees
- May require more memory
- Not ideal for extrapolation in regression

#### Relation to Other Models
- **Decision Trees:** Random Forests are an ensemble of decision trees.
- **Gradient Boosting:** Both are tree ensembles, but boosting builds trees sequentially to correct errors.
- **Bagging:** Random Forests use bagging (bootstrap aggregation) with additional feature randomness.

#### Python Implementation and Visualization
```python
import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import make_classification
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

# Generate synthetic data
X, y = make_classification(n_samples=500, n_features=4, n_informative=2, n_redundant=0, random_state=42)

# Train/test split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

# Train Random Forest
model = RandomForestClassifier(n_estimators=100, max_depth=3, random_state=42)
model.fit(X_train, y_train)

# Predict and evaluate
y_pred = model.predict(X_test)
print('Accuracy:', accuracy_score(y_test, y_pred))

# Feature importance
importances = model.feature_importances_
plt.bar(range(X.shape[1]), importances)
plt.xlabel('Feature Index')
plt.ylabel('Importance')
plt.title('Random Forest Feature Importances')
plt.show()
```

### k-Nearest Neighbors (KNN)
KNN is a simple, non-parametric algorithm used for classification and regression. It makes predictions based on the k closest training examples in the feature space.

#### Mathematical Foundation
For classification, the predicted class is:
$$
\hat{y} = \text{mode}\{y_i | i \in N_k(x)\}
$$
where $N_k(x)$ is the set of $k$ nearest neighbors of $x$.

For regression:
$$
\hat{y} = \frac{1}{k}\sum_{i \in N_k(x)} y_i
$$

**Distance Metrics:**
- Euclidean: $d(x, y) = \sqrt{\sum_{i=1}^{n} (x_i - y_i)^2}$
- Manhattan: $d(x, y) = \sum_{i=1}^{n} |x_i - y_i|$
- Minkowski: Generalization of above

**Weighted KNN:**
- Neighbors can be weighted by distance (closer neighbors have more influence)

#### Implementation
Implemented using scikit-learn's `KNeighborsClassifier` and `KNeighborsRegressor` classes.

#### Performance Analysis
- Time Complexity: O(n * p) for training, O(k * log(n)) for prediction (with KDTree/BallTree)
- Space Complexity: O(n * p) for storing training data

#### Use Cases
- Pattern recognition (e.g., handwritten digit recognition)
- Image classification
- Recommendation systems
- Anomaly detection
- Medical diagnosis

**Example:**
Classifying handwritten digits (MNIST) or segmenting customers based on features.

#### Advantages and Limitations
Advantages:
- Simple to understand and implement
- No training required (instance-based)
- Works well with non-linear data
- Can adapt to multi-class problems

Limitations:
- Computationally expensive for large datasets
- Sensitive to feature scaling and irrelevant features
- Performance depends on choice of $k$ and distance metric
- Curse of dimensionality (performance degrades with many features)

#### Relation to Other Models
- **Logistic Regression/SVM:** Both are parametric, KNN is non-parametric.
- **Decision Trees:** Both can handle non-linear boundaries, but KNN is instance-based.
- **Radius Neighbors:** Generalization of KNN using a fixed radius instead of $k$.

#### Python Implementation and Visualization
```python
import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import make_moons
from sklearn.neighbors import KNeighborsClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

# Generate synthetic data (two moons)
X, y = make_moons(n_samples=300, noise=0.3, random_state=42)

# Train/test split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

# Train KNN
model = KNeighborsClassifier(n_neighbors=5, weights='distance')
model.fit(X_train, y_train)

# Predict and evaluate
y_pred = model.predict(X_test)
print('Accuracy:', accuracy_score(y_test, y_pred))

# Visualization of decision boundary
xx, yy = np.meshgrid(np.linspace(X[:,0].min()-1, X[:,0].max()+1, 200),
                     np.linspace(X[:,1].min()-1, X[:,1].max()+1, 200))
Z = model.predict(np.c_[xx.ravel(), yy.ravel()])
Z = Z.reshape(xx.shape)
plt.contourf(xx, yy, Z, alpha=0.3, cmap=plt.cm.Paired)
plt.scatter(X[:,0], X[:,1], c=y, edgecolor='k', cmap=plt.cm.Paired)
plt.title('KNN Decision Boundary (k=5, distance-weighted)')
plt.xlabel('Feature 1')
plt.ylabel('Feature 2')
plt.show()
```

### Naive Bayes
Naive Bayes is a probabilistic classifier based on Bayes' theorem with strong independence assumptions between features.

#### Mathematical Foundation
Bayes' theorem:

$$P(y|X) = \frac{P(X|y)P(y)}{P(X)}$$

For multiple features:

$$P(y|X_1,...,X_n) = \frac{P(X_1|y)...P(X_n|y)P(y)}{P(X_1,...,X_n)}$$

#### Implementation
Implemented using scikit-learn's `GaussianNB`, `MultinomialNB`, and `BernoulliNB` classes.

#### Performance Analysis
- Time Complexity: O(n * p) for training
- Space Complexity: O(c * p) where c is number of classes

#### Use Cases
- Text classification
- Spam filtering
- Sentiment analysis
- Document categorization

#### Advantages and Limitations
Advantages:
- Fast training and prediction
- Works well with high-dimensional data
- Requires little training data

Limitations:
- Assumes feature independence
- Can be outperformed by more complex models
- Sensitive to feature scaling

#### Python Implementation
```python
from sklearn.naive_bayes import GaussianNB

model = GaussianNB(
    priors=None,             # Prior probabilities of classes
    var_smoothing=1e-9       # Portion of the largest variance added to variances
)
```

### K-Means Clustering
K-Means is an unsupervised learning algorithm that partitions data into K clusters by minimizing the within-cluster sum of squares.

#### Mathematical Foundation
The objective function to minimize:
$$
J = \sum_{i=1}^{k} \sum_{x \in C_i} ||x - \mu_i||^2
$$
where:
- $C_i$ is the i-th cluster
- $\mu_i$ is the centroid of cluster $i$
- $k$ is the number of clusters

**Algorithm Steps:**
1. Initialize $k$ centroids randomly.
2. Assign each data point to the nearest centroid.
3. Update centroids as the mean of assigned points.
4. Repeat steps 2-3 until convergence (no change in assignments or centroids).

**Convergence:**
- K-Means always converges to a local minimum, not necessarily the global minimum.
- Initialization can affect the final result (use k-means++ for better initialization).

#### Implementation
Implemented using scikit-learn's `KMeans` class.

#### Performance Analysis
- Time Complexity: O(n * k * i * p) where $i$ is number of iterations
- Space Complexity: O(n * p + k * p)

#### Use Cases
- Customer segmentation (e.g., marketing)
- Image compression (color quantization)
- Document clustering (topic modeling)
- Market basket analysis
- Anomaly detection (as outliers in clusters)

**Example:**
Segmenting customers based on purchasing behavior or compressing an image by reducing the number of colors.

#### Advantages and Limitations
Advantages:
- Simple to implement and fast
- Scales well to large datasets
- Guarantees convergence
- Works well when clusters are spherical and equally sized

Limitations:
- Requires number of clusters $k$ to be specified
- Sensitive to initial centroid positions
- Assumes spherical clusters of similar size
- Not suitable for non-globular clusters or clusters of different densities

#### Relation to Other Models
- **DBSCAN:** Can find clusters of arbitrary shape and does not require $k$.
- **Hierarchical Clustering:** Builds a tree of clusters; does not require $k$ but is more computationally expensive.
- **Gaussian Mixture Models (GMM):** Probabilistic clustering, can model elliptical clusters and provides soft assignments.

#### Python Implementation and Visualization
```python
import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import make_blobs
from sklearn.cluster import KMeans

# Generate synthetic data
X, y_true = make_blobs(n_samples=300, centers=4, cluster_std=0.60, random_state=0)

# Fit K-Means
kmeans = KMeans(n_clusters=4, init='k-means++', n_init=10, random_state=0)
y_kmeans = kmeans.fit_predict(X)

# Plot clusters and centroids
plt.scatter(X[:, 0], X[:, 1], c=y_kmeans, s=50, cmap='viridis', alpha=0.6)
centers = kmeans.cluster_centers_
plt.scatter(centers[:, 0], centers[:, 1], c='red', s=200, alpha=0.75, marker='X', label='Centroids')
plt.title('K-Means Clustering Results')
plt.xlabel('Feature 1')
plt.ylabel('Feature 2')
plt.legend()
plt.show()
```

### DBSCAN
DBSCAN (Density-Based Spatial Clustering of Applications with Noise) is a density-based clustering algorithm that groups together points that are close to each other and marks points that are far from any cluster as outliers.

#### Mathematical Foundation
A point $p$ is a core point if at least `min_samples` points are within distance $\epsilon$ (including $p$ itself):
$$
N_\epsilon(p) = \{q \in D | d(p,q) \leq \epsilon\}
$$
A cluster is formed by all points density-reachable from a core point.

**Types of Points:**
- Core point: Has at least `min_samples` points within $\epsilon$.
- Border point: Not a core point, but within $\epsilon$ of a core point.
- Noise point: Neither core nor border.

**Algorithm Steps:**
1. For each point, find its $\epsilon$-neighborhood.
2. If it is a core point, form a cluster with all density-reachable points.
3. Repeat for all unvisited points.

#### Implementation
Implemented using scikit-learn's `DBSCAN` class.

#### Performance Analysis
- Time Complexity: O(n log n) with spatial index, O(n²) otherwise
- Space Complexity: O(n)

#### Use Cases
- Anomaly detection (e.g., fraud, network intrusion)
- Spatial data analysis (e.g., geographic clustering)
- Image segmentation
- Network analysis
- Clustering with noise/outliers

**Example:**
Clustering spatial data with noise, such as GPS points or customer locations.

#### Advantages and Limitations
Advantages:
- Can find clusters of arbitrary shapes
- Robust to outliers
- Doesn't require number of clusters $k$
- Works well with spatial data

Limitations:
- Sensitive to parameters ($\epsilon$, `min_samples`)
- Struggles with varying densities
- Can be slow on large datasets without spatial index

#### Relation to Other Models
- **K-Means:** Assumes spherical clusters, requires $k$; DBSCAN does not.
- **Hierarchical Clustering:** Can also find non-spherical clusters, but is more computationally expensive.
- **OPTICS:** Extension of DBSCAN for varying densities.

#### Python Implementation and Visualization
```python
import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import make_moons
from sklearn.cluster import DBSCAN

# Generate synthetic data (two moons)
X, y = make_moons(n_samples=300, noise=0.05, random_state=42)

# Fit DBSCAN
db = DBSCAN(eps=0.2, min_samples=5)
y_db = db.fit_predict(X)

# Plot clusters
plt.scatter(X[:, 0], X[:, 1], c=y_db, cmap='plasma', s=50, edgecolor='k')
plt.title('DBSCAN Clustering Results')
plt.xlabel('Feature 1')
plt.ylabel('Feature 2')
plt.show()
```

### Principal Component Analysis (PCA)
PCA is a dimensionality reduction technique that transforms data into a new coordinate system where the greatest variance lies on the first coordinate, second greatest on the second coordinate, and so on.

#### Mathematical Foundation
Given a data matrix $X$ (centered), PCA finds the directions (principal components) that maximize variance:

**Covariance Matrix:**
$$
\Sigma = \frac{1}{n-1}X^TX
$$

**Eigen Decomposition:**
- Find eigenvalues $\lambda_i$ and eigenvectors $w_i$ of $\Sigma$.
- Principal components are the top $k$ eigenvectors (directions of maximum variance).

**Projection:**
$$
X_{\text{reduced}} = XW
$$
where $W$ is the matrix of top $k$ eigenvectors.

**Explained Variance:**
- The proportion of variance explained by each component: $\lambda_i / \sum_j \lambda_j$

#### Implementation
Implemented using scikit-learn's `PCA` class.

#### Performance Analysis
- Time Complexity: O(n * p^2 + p^3)
- Space Complexity: O(p^2)

#### Use Cases
- Feature extraction (e.g., for machine learning pipelines)
- Data compression (reduce storage or computation)
- Visualization (project high-dimensional data to 2D/3D)
- Noise reduction
- Preprocessing for clustering or classification

**Example:**
Visualizing handwritten digits (MNIST) in 2D or compressing image data.

#### Advantages and Limitations
Advantages:
- Reduces dimensionality and noise
- Removes multicollinearity
- Preserves maximum variance
- Can speed up downstream algorithms

Limitations:
- Linear transformation only (cannot capture non-linear structure)
- Sensitive to scaling of features
- May lose important information if too few components are kept

#### Relation to Other Models
- **t-SNE/UMAP:** Non-linear dimensionality reduction for visualization; better for capturing complex structure.
- **LDA (Linear Discriminant Analysis):** Supervised dimensionality reduction maximizing class separability.
- **Kernel PCA:** Extension of PCA for non-linear data using kernels.

#### Python Implementation and Visualization
```python
import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import load_digits
from sklearn.decomposition import PCA

# Load digits dataset (64 features)
digits = load_digits()
X = digits.data
y = digits.target

# Fit PCA and project to 2D
pca = PCA(n_components=2)
X_pca = pca.fit_transform(X)

# Plot 2D projection
plt.figure(figsize=(8,6))
scatter = plt.scatter(X_pca[:,0], X_pca[:,1], c=y, cmap='tab10', alpha=0.7)
plt.legend(*scatter.legend_elements(), title="Digits")
plt.title('PCA Projection of Digits Dataset')
plt.xlabel('PC1')
plt.ylabel('PC2')
plt.show()
```

### t-SNE
t-SNE (t-Distributed Stochastic Neighbor Embedding) is a non-linear dimensionality reduction technique particularly well-suited for visualization of high-dimensional datasets.

#### Mathematical Foundation
t-SNE minimizes the divergence between two distributions: one that measures pairwise similarities of the input data in high-dimensional space, and one in the low-dimensional embedding.

**High-dimensional similarities:**
$$
p_{ij} = \frac{\exp(-||x_i - x_j||^2/2\sigma_i^2)}{\sum_{k \neq l} \exp(-||x_k - x_l||^2/2\sigma_k^2)}
$$

**Low-dimensional similarities:**
$$
q_{ij} = \frac{(1 + ||y_i - y_j||^2)^{-1}}{\sum_{k \neq l} (1 + ||y_k - y_l||^2)^{-1}}
$$

**Cost Function (KL Divergence):**
$$
C = \sum_{i \neq j} p_{ij} \log \frac{p_{ij}}{q_{ij}}
$$

**Perplexity:**
- Controls the balance between local and global aspects of the data (roughly, the number of effective nearest neighbors).

#### Implementation
Implemented using scikit-learn's `TSNE` class.

#### Performance Analysis
- Time Complexity: O(n^2 * p)
- Space Complexity: O(n^2)

#### Use Cases
- Data visualization (e.g., MNIST, gene expression)
- Pattern recognition
- Feature learning
- Cluster analysis
- Visualizing word embeddings

**Example:**
Visualizing high-dimensional image or text data in 2D or 3D.

#### Advantages and Limitations
Advantages:
- Preserves local structure of data
- Good for visualization
- Can reveal clusters and subgroups

Limitations:
- Computationally expensive for large datasets
- Non-deterministic (results can vary between runs)
- Sensitive to perplexity and learning rate parameters
- Not suitable for downstream machine learning tasks (only for visualization)

#### Relation to Other Models
- **PCA:** Linear, faster, but may not capture non-linear structure.
- **UMAP:** Similar to t-SNE but faster and preserves more global structure.
- **MDS (Multidimensional Scaling):** Another dimensionality reduction technique, less effective for complex manifolds.

#### Python Implementation and Visualization
```python
import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import load_digits
from sklearn.manifold import TSNE

# Load digits dataset
X, y = load_digits(return_X_y=True)

# Fit t-SNE and project to 2D
tsne = TSNE(n_components=2, perplexity=30, n_iter=1000, random_state=42)
X_tsne = tsne.fit_transform(X)

# Plot 2D projection
plt.figure(figsize=(8,6))
scatter = plt.scatter(X_tsne[:,0], X_tsne[:,1], c=y, cmap='tab10', alpha=0.7)
plt.legend(*scatter.legend_elements(), title="Digits")
plt.title('t-SNE Projection of Digits Dataset')
plt.xlabel('t-SNE 1')
plt.ylabel('t-SNE 2')
plt.show()
```

### Gradient Boosting
Gradient Boosting is an ensemble learning method that builds models sequentially, each new model correcting the errors of the previous ones.

#### Mathematical Foundation
Gradient Boosting fits a sequence of weak learners (typically decision trees) to the negative gradient of the loss function.

**Additive Model:**
$$
F_M(x) = \sum_{m=1}^{M} h_m(x)
$$
where $h_m(x)$ is the $m$-th weak learner.

**Stagewise Update:**
$$
F_m(x) = F_{m-1}(x) + \gamma_m h_m(x)
$$
where $\gamma_m$ is the step size (learning rate).

**Gradient Step:**
$$
h_m(x) = -\eta \frac{\partial L(y, F_{m-1}(x))}{\partial F_{m-1}(x)}
$$

**Loss Functions:**
- Regression: Squared error
- Classification: Log-loss (deviance)

#### Implementation
Implemented using scikit-learn's `GradientBoostingClassifier` and `GradientBoostingRegressor` classes.

#### Performance Analysis
- Time Complexity: O(n * m * p) where $m$ is number of trees
- Space Complexity: O(m * n)

#### Use Cases
- Fraud detection (e.g., credit card fraud)
- Click-through rate prediction (e.g., online ads)
- Customer churn prediction
- Risk assessment
- Kaggle competitions (often top-performing)

**Example:**
Predicting customer churn or classifying tabular data with complex interactions.

#### Advantages and Limitations
Advantages:
- High predictive accuracy
- Handles different types of data
- Robust to outliers
- Can model complex, non-linear relationships

Limitations:
- Can overfit if too many trees or high depth
- Computationally intensive
- Requires careful tuning of hyperparameters
- Less interpretable than single trees

#### Relation to Other Models
- **Random Forests:** Both are tree ensembles, but boosting builds trees sequentially to correct errors.
- **AdaBoost:** Special case of boosting with exponential loss.
- **XGBoost/LightGBM/CatBoost:** Optimized implementations of gradient boosting with additional features.

#### Python Implementation and Visualization
```python
import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import make_classification
from sklearn.ensemble import GradientBoostingClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

# Generate synthetic data
X, y = make_classification(n_samples=500, n_features=4, n_informative=2, n_redundant=0, random_state=42)

# Train/test split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

# Train Gradient Boosting
model = GradientBoostingClassifier(n_estimators=100, learning_rate=0.1, max_depth=3, random_state=42)
model.fit(X_train, y_train)

# Predict and evaluate
y_pred = model.predict(X_test)
print('Accuracy:', accuracy_score(y_test, y_pred))

# Feature importance
importances = model.feature_importances_
plt.bar(range(X.shape[1]), importances)
plt.xlabel('Feature Index')
plt.ylabel('Importance')
plt.title('Gradient Boosting Feature Importances')
plt.show()
```

### XGBoost
XGBoost (eXtreme Gradient Boosting) is an optimized implementation of gradient boosting that includes additional features like regularization and parallel processing.

#### Mathematical Foundation
The objective function:

$$L(\phi) = \sum_i l(\hat{y}_i, y_i) + \sum_k \Omega(f_k)$$

where:
- $l$ is the loss function
- $\Omega$ is the regularization term
- $f_k$ is the k-th tree

#### Implementation
Implemented using the `xgboost` package.

#### Performance Analysis
- Time Complexity: O(n * m * p)
- Space Complexity: O(m * n)

#### Use Cases
- Structured/tabular data
- Ranking problems
- Classification tasks
- Regression problems

#### Advantages and Limitations
Advantages:
- Highly efficient
- Built-in regularization
- Handles missing values

Limitations:
- Memory intensive
- Requires parameter tuning
- Can overfit

#### Python Implementation
```python
import xgboost as xgb

model = xgb.XGBClassifier(
    n_estimators=100,        # Number of boosting rounds
    max_depth=3,             # Maximum tree depth
    learning_rate=0.1,       # Step size shrinkage
    subsample=0.8,           # Subsample ratio of training instances
    colsample_bytree=0.8,    # Subsample ratio of columns
    min_child_weight=1,      # Minimum sum of instance weight
    gamma=0,                 # Minimum loss reduction
    random_state=42          # Random state for reproducibility
)
```

### Neural Networks
Neural Networks are a class of models inspired by biological neural networks, capable of learning complex patterns through multiple layers of interconnected nodes (neurons).

#### Mathematical Foundation
A single neuron computes:
$$
    z = \sum_{i=1}^{n} w_i x_i + b
$$
$$
    a = \sigma(z)
$$
where:
- $w_i$ are weights
- $x_i$ are inputs
- $b$ is bias
- $\sigma$ is the activation function (e.g., sigmoid, ReLU, tanh)

A feedforward neural network with $L$ layers:
$$
    a^{(l)} = \sigma(W^{(l)} a^{(l-1)} + b^{(l)})
$$

**Backpropagation:**
- Computes gradients of the loss function with respect to weights using the chain rule.
- Updates weights using gradient descent or its variants.

**Loss Functions:**
- Classification: Cross-entropy loss
- Regression: Mean squared error

#### Implementation
Implemented using TensorFlow/Keras or PyTorch.

#### Performance Analysis
- Time Complexity: O(n * p * l) where $l$ is number of layers
- Space Complexity: O(p * l)

#### Use Cases
- Image recognition (e.g., MNIST, CIFAR-10)
- Natural language processing (e.g., sentiment analysis, translation)
- Speech recognition
- Time series prediction
- Medical diagnosis

**Example:**
Classifying handwritten digits (MNIST) or recognizing objects in images.

#### Advantages and Limitations
Advantages:
- Can learn complex, non-linear patterns
- Works well with large datasets
- Flexible architecture (can be adapted to many tasks)

Limitations:
- Requires large amounts of data
- Computationally intensive (especially deep networks)
- Black box nature (hard to interpret)
- Prone to overfitting if not regularized

#### Relation to Other Models
- **Logistic Regression:** A single-layer neural network with sigmoid activation is equivalent to logistic regression.
- **SVM:** Both can be used for classification, but neural networks can model more complex boundaries.
- **Deep Learning:** Deep neural networks (DNNs) are neural networks with many hidden layers.
- **Convolutional/Recurrent Networks:** Specialized architectures for images and sequences, respectively.

#### Python Implementation and Visualization
```python
import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import make_moons
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
from tensorflow.keras.utils import to_categorical

# Generate synthetic data (two moons)
X, y = make_moons(n_samples=1000, noise=0.2, random_state=42)
scaler = StandardScaler()
X = scaler.fit_transform(X)
y_cat = to_categorical(y)

# Train/test split
X_train, X_test, y_train, y_test = train_test_split(X, y_cat, test_size=0.3, random_state=42)

# Build neural network
model = Sequential([
    Dense(16, activation='relu', input_shape=(2,)),
    Dense(8, activation='relu'),
    Dense(2, activation='softmax')
])
model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])
model.fit(X_train, y_train, epochs=50, batch_size=16, verbose=0)

# Evaluate
loss, acc = model.evaluate(X_test, y_test, verbose=0)
print('Test accuracy:', acc)

# Visualization of decision boundary
xx, yy = np.meshgrid(np.linspace(X[:,0].min()-1, X[:,0].max()+1, 200),
                     np.linspace(X[:,1].min()-1, X[:,1].max()+1, 200))
Z = model.predict(np.c_[xx.ravel(), yy.ravel()])
Z = np.argmax(Z, axis=1).reshape(xx.shape)
plt.contourf(xx, yy, Z, alpha=0.3, cmap=plt.cm.Paired)
plt.scatter(X[:,0], X[:,1], c=np.argmax(y_cat, axis=1), edgecolor='k', cmap=plt.cm.Paired)
plt.title('Neural Network Decision Boundary')
plt.xlabel('Feature 1')
plt.ylabel('Feature 2')
plt.show()
```

### Convolutional Neural Networks (CNN)
CNNs are specialized neural networks designed for processing grid-like data such as images, using convolutional layers to extract features.

#### Mathematical Foundation
Convolution operation:

$$(f * g)(x) = \sum_{a} f(a)g(x-a)$$

Pooling operation (max pooling):

$$y_{i,j} = \max_{m,n} x_{i+m,j+n}$$

#### Implementation
Implemented using TensorFlow/Keras.

#### Performance Analysis
- Time Complexity: O(n * k * p) where k is kernel size
- Space Complexity: O(n * p)

#### Use Cases
- Image classification
- Object detection
- Face recognition
- Medical image analysis

#### Advantages and Limitations
Advantages:
- Excellent for image data
- Parameter sharing
- Translation invariance

Limitations:
- Computationally expensive
- Requires large datasets
- Sensitive to architecture

#### Python Implementation
```python
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Conv2D, MaxPooling2D, Flatten, Dense

model = Sequential([
    Conv2D(32, (3, 3), activation='relu', input_shape=(height, width, channels)),
    MaxPooling2D((2, 2)),
    Conv2D(64, (3, 3), activation='relu'),
    MaxPooling2D((2, 2)),
    Flatten(),
    Dense(64, activation='relu'),
    Dense(num_classes, activation='softmax')
])

model.compile(
    optimizer='adam',
    loss='categorical_crossentropy',
    metrics=['accuracy']
)
```

### ARIMA
ARIMA (AutoRegressive Integrated Moving Average) is a statistical model for time series forecasting that combines autoregression, differencing, and moving average components.

#### Mathematical Foundation
ARIMA(p,d,q) model:
$$
y_t = c + \phi_1 y_{t-1} + \ldots + \phi_p y_{t-p} + \theta_1 \epsilon_{t-1} + \ldots + \theta_q \epsilon_{t-q} + \epsilon_t
$$
where:
- $y_t$ is the value at time $t$
- $c$ is a constant
- $\phi_i$ are autoregressive coefficients (AR part)
- $\theta_j$ are moving average coefficients (MA part)
- $\epsilon_t$ is white noise
- $d$ is the order of differencing (I part)

**Stationarity:**
- The series must be stationary (constant mean/variance). Differencing is used to achieve stationarity.

**Model Selection:**
- Use ACF/PACF plots and information criteria (AIC/BIC) to select $p$, $d$, $q$.

#### Implementation
Implemented using statsmodels' `ARIMA` class.

#### Performance Analysis
- Time Complexity: O(n²) for parameter estimation
- Space Complexity: O(n)

#### Use Cases
- Economic forecasting (e.g., GDP, inflation)
- Weather prediction (e.g., temperature, rainfall)
- Stock price analysis
- Demand forecasting (e.g., sales, energy consumption)

**Example:**
Forecasting monthly airline passenger numbers or daily stock prices.

#### Advantages and Limitations
Advantages:
- Handles trend and seasonality (with SARIMA extension)
- Well-established methodology
- Interpretable results

Limitations:
- Assumes linearity
- Requires stationary data
- Sensitive to parameter selection
- Not suitable for non-linear or highly volatile series

#### Relation to Other Models
- **Prophet:** Handles seasonality and holidays automatically, more robust to missing data.
- **Exponential Smoothing:** Simpler, good for short-term forecasts, but less flexible than ARIMA.
- **LSTM/Neural Networks:** Can model non-linear and long-term dependencies, but require more data and tuning.

#### Python Implementation and Visualization
```python
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from statsmodels.tsa.arima.model import ARIMA
from statsmodels.graphics.tsaplots import plot_acf, plot_pacf

# Generate synthetic time series data
np.random.seed(42)
dates = pd.date_range('2020-01-01', periods=100, freq='M')
data = pd.Series(50 + np.arange(100) * 0.5 + np.random.normal(0, 2, 100), index=dates)

# Plot the time series
data.plot(title='Synthetic Time Series')
plt.xlabel('Date')
plt.ylabel('Value')
plt.show()

# Plot ACF and PACF
plot_acf(data)
plt.show()
plot_pacf(data)
plt.show()

# Fit ARIMA model
model = ARIMA(data, order=(2,1,2))
results = model.fit()
print(results.summary())

# Forecast
forecast = results.get_forecast(steps=12)
forecast_index = pd.date_range(data.index[-1], periods=13, freq='M')[1:]
forecast_series = pd.Series(forecast.predicted_mean.values, index=forecast_index)

# Plot forecast
plt.figure(figsize=(10,5))
plt.plot(data, label='Observed')
plt.plot(forecast_series, label='Forecast', color='red')
plt.fill_between(forecast_series.index,
                 forecast.conf_int().iloc[:,0],
                 forecast.conf_int().iloc[:,1], color='pink', alpha=0.3)
plt.title('ARIMA Forecast')
plt.xlabel('Date')
plt.ylabel('Value')
plt.legend()
plt.show()
```

### Prophet
Prophet is a time series forecasting tool developed by Facebook that handles seasonality, holidays, and trend changes automatically.

#### Mathematical Foundation
Prophet decomposes the time series into trend, seasonality, and holiday effects:
$$
y(t) = g(t) + s(t) + h(t) + \epsilon_t
$$
where:
- $g(t)$ is the trend function (piecewise linear or logistic growth)
- $s(t)$ is the seasonality component (Fourier series)
- $h(t)$ is the effect of holidays
- $\epsilon_t$ is the error term

**Trend:**
- Piecewise linear or logistic growth with automatic changepoint detection.

**Seasonality:**
- Modeled using Fourier series:
$$
s(t) = \sum_{n=1}^{N} [a_n \cos(2\pi n t / P) + b_n \sin(2\pi n t / P)]
$$
where $P$ is the period (e.g., 365.25 for yearly).

**Holidays:**
- User-specified events with additional parameters.

#### Implementation
Implemented using the `prophet` package.

#### Performance Analysis
- Time Complexity: O(n)
- Space Complexity: O(n)

#### Use Cases
- Business forecasting (e.g., sales, web traffic)
- Website traffic prediction
- Energy consumption
- Inventory management
- Forecasting with strong seasonality and holidays

**Example:**
Forecasting daily website visits or retail sales with holiday effects.

#### Advantages and Limitations
Advantages:
- Handles missing data
- Automatic seasonality and changepoint detection
- Robust to outliers
- Easy to use and interpret

Limitations:
- Requires regular time series (no missing timestamps)
- May overfit with too many changepoints
- Less flexible for non-additive effects
- Computationally intensive for very large datasets

#### Relation to Other Models
- **ARIMA:** Prophet is more robust to missing data and outliers, and easier to use for non-experts.
- **Exponential Smoothing:** Prophet can model multiple seasonalities and changepoints.
- **LSTM/Neural Networks:** Can model more complex, non-linear dependencies, but require more data and tuning.

#### Python Implementation and Visualization
```python
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from prophet import Prophet

# Generate synthetic time series data
np.random.seed(42)
dates = pd.date_range('2020-01-01', periods=200, freq='D')
y = 10 + 0.05 * np.arange(200) + 2 * np.sin(2 * np.pi * np.arange(200) / 30) + np.random.normal(0, 0.5, 200)
df = pd.DataFrame({'ds': dates, 'y': y})

# Fit Prophet model
model = Prophet()
model.fit(df)

# Forecast
future = model.make_future_dataframe(periods=30)
forecast = model.predict(future)

# Plot forecast
fig = model.plot(forecast)
plt.title('Prophet Forecast')
plt.xlabel('Date')
plt.ylabel('Value')
plt.show()
```

### Isolation Forest
Isolation Forest is an anomaly detection algorithm that isolates observations by randomly selecting a feature and then randomly selecting a split value between the maximum and minimum values of the selected feature.

#### Mathematical Foundation
Isolation Forest builds an ensemble of isolation trees (iTrees). Anomalies are isolated faster (shorter path length) than normal points.

**Path Length:**
- For a point $x$, the path length $h(x)$ is the number of edges from the root to the terminating node in an iTree.
- Average path length for $n$ samples:
$$
C(n) = 2H(n-1) - \frac{2(n-1)}{n}
$$
where $H(i)$ is the $i$-th harmonic number.

**Anomaly Score:**
$$
s(x, n) = 2^{-\frac{E(h(x))}{C(n)}}
$$
where $E(h(x))$ is the average path length for $x$ over all trees.
- Scores close to 1 indicate anomalies; close to 0.5 indicate normal points.

#### Implementation
Implemented using scikit-learn's `IsolationForest` class.

#### Performance Analysis
- Time Complexity: O(n \log n)
- Space Complexity: O(n)

#### Use Cases
- Fraud detection (e.g., credit card fraud)
- Network intrusion detection
- System health monitoring
- Quality control in manufacturing
- Outlier detection in high-dimensional data

**Example:**
Detecting fraudulent transactions or abnormal sensor readings.

#### Advantages and Limitations
Advantages:
- Fast training and prediction
- Works well with high-dimensional data
- No need for feature scaling
- Can handle large datasets

Limitations:
- May not detect local anomalies
- Sensitive to contamination parameter
- Assumes independence of features
- Randomness can lead to different results on different runs

#### Relation to Other Models
- **One-Class SVM:** Also used for anomaly detection, but can model non-linear boundaries.
- **DBSCAN:** Can detect outliers as noise points, but is density-based.
- **Local Outlier Factor (LOF):** Measures local deviation of density, not isolation.

#### Python Implementation and Visualization
```python
import numpy as np
import matplotlib.pyplot as plt
from sklearn.ensemble import IsolationForest
from sklearn.datasets import make_blobs

# Generate synthetic data with outliers
X, _ = make_blobs(n_samples=300, centers=1, cluster_std=0.60, random_state=42)
X_outliers = np.random.uniform(low=-6, high=6, size=(20, 2))
X = np.vstack([X, X_outliers])

# Fit Isolation Forest
model = IsolationForest(contamination=0.05, random_state=42)
y_pred = model.fit_predict(X)

# Plot results
plt.scatter(X[:, 0], X[:, 1], c=y_pred, cmap='coolwarm', edgecolor='k')
plt.title('Isolation Forest Anomaly Detection')
plt.xlabel('Feature 1')
plt.ylabel('Feature 2')
plt.show()
```

### Genetic Algorithms
Genetic Algorithms (GAs) are optimization algorithms inspired by the process of natural selection, using techniques such as mutation, crossover, and selection to evolve solutions to problems.

#### Mathematical Foundation
A GA maintains a population of candidate solutions (individuals), each represented as a chromosome (often a vector of numbers or bits).

**Fitness Function:**
$$
f(x) = \text{objective to maximize or minimize}
$$

**Selection Probability:**
$$
P(x_i) = \frac{f(x_i)}{\sum_{j=1}^{n} f(x_j)}
$$

**Operators:**
- **Selection:** Choose individuals for reproduction based on fitness (e.g., roulette wheel, tournament).
- **Crossover:** Combine two parents to produce offspring (e.g., one-point, two-point, uniform crossover).
- **Mutation:** Randomly alter genes in offspring (e.g., bit flip, Gaussian noise).
- **Replacement:** Form new population from offspring and/or parents.

**Algorithm Steps:**
1. Initialize population randomly.
2. Evaluate fitness of each individual.
3. Select parents based on fitness.
4. Apply crossover and mutation to produce offspring.
5. Replace population with offspring.
6. Repeat steps 2-5 for a fixed number of generations or until convergence.

#### Implementation
Implemented using DEAP (Distributed Evolutionary Algorithms in Python) or custom code.

#### Performance Analysis
- Time Complexity: O(p * g * n) where $p$ is population size, $g$ is generations
- Space Complexity: O(p * n)

#### Use Cases
- Parameter optimization (e.g., hyperparameter tuning)
- Scheduling problems (e.g., job shop scheduling)
- Circuit design
- Game strategy optimization
- Feature selection in machine learning

**Example:**
Optimizing weights of a neural network or finding the shortest path in a traveling salesman problem.

#### Advantages and Limitations
Advantages:
- Can find global optima in complex, non-convex spaces
- Works with non-linear, multi-modal, and discrete problems
- No need for derivatives or gradient information

Limitations:
- Computationally expensive (many evaluations)
- May converge slowly or to suboptimal solutions
- Results may vary between runs (stochastic)

#### Relation to Other Models
- **Simulated Annealing:** Another stochastic optimization method, but uses a single solution and temperature.
- **Particle Swarm Optimization:** Population-based, but uses velocity and position updates.
- **Gradient Descent:** Deterministic, requires gradients, best for convex problems.

#### Python Implementation and Visualization
```python
import numpy as np
import matplotlib.pyplot as plt
from deap import base, creator, tools, algorithms
import random

# Objective: maximize f(x) = sin(10x) * x + cos(2x) * x, x in [0, 2]
def eval_func(individual):
    x = individual[0]
    return (np.sin(10*x) * x + np.cos(2*x) * x,),

creator.create("FitnessMax", base.Fitness, weights=(1.0,))
creator.create("Individual", list, fitness=creator.FitnessMax)
toolbox = base.Toolbox()
toolbox.register("attr_float", random.uniform, 0, 2)
toolbox.register("individual", tools.initRepeat, creator.Individual, toolbox.attr_float, n=1)
toolbox.register("population", tools.initRepeat, list, toolbox.individual)
toolbox.register("mate", tools.cxBlend, alpha=0.5)
toolbox.register("mutate", tools.mutGaussian, mu=0, sigma=0.2, indpb=0.2)
toolbox.register("select", tools.selTournament, tournsize=3)
toolbox.register("evaluate", eval_func)

pop = toolbox.population(n=30)
hof = tools.HallOfFame(1)
algorithms.eaSimple(pop, toolbox, cxpb=0.5, mutpb=0.2, ngen=20, halloffame=hof, verbose=False)

# Visualize best solution
x = np.linspace(0, 2, 100)
y = np.sin(10*x) * x + np.cos(2*x) * x
plt.plot(x, y, label='Objective Function')
plt.scatter(hof[0][0], eval_func(hof[0])[0], color='red', label='Best Solution')
plt.title('Genetic Algorithm Optimization')
plt.xlabel('x')
plt.ylabel('f(x)')
plt.legend()
plt.show()
```

### FP-Growth
FP-Growth (Frequent Pattern Growth) is an efficient algorithm for mining frequent itemsets without candidate generation, using a compact data structure called the FP-tree.

#### Mathematical Foundation
**FP-Tree Construction:**
1. Scan the database to determine the support of each item.
2. Discard infrequent items and sort frequent items in each transaction by descending support.
3. Build the FP-tree by inserting transactions; common prefixes share nodes.

**Frequent Pattern Mining:**
- Recursively mine the FP-tree by extracting conditional pattern bases and building conditional FP-trees for each frequent item.
- Frequent itemsets are found by combining items in the conditional trees.

**Support:**
$$
support(X) = \frac{|\{t \in T | X \subseteq t\}|}{|T|}
$$

#### Implementation
Implemented using mlxtend's `fpgrowth` and `association_rules` functions.

#### Performance Analysis
- Time Complexity: O(n * m) where $n$ is number of transactions, $m$ is average transaction length
- Space Complexity: O(n * m) (FP-tree size)

#### Use Cases
- Market basket analysis (e.g., retail transaction data)
- Web log analysis (e.g., frequent navigation patterns)
- DNA sequence analysis
- Network traffic analysis

**Example:**
Finding sets of products that are frequently bought together in a supermarket.

#### Advantages and Limitations
Advantages:
- Faster than Apriori for large datasets
- No candidate generation (reduces computation)
- Memory efficient for sparse data

Limitations:
- Complex implementation
- Memory usage can be high for dense datasets
- May not scale well with very large or dense datasets

#### Relation to Other Models
- **Apriori:** FP-Growth is generally faster and more memory-efficient than Apriori.
- **Eclat:** Uses vertical data format; can be faster for some data.
- **Association Rule Mining:** FP-Growth is a classic algorithm for this task.

#### Python Implementation and Visualization
```python
import pandas as pd
from mlxtend.frequent_patterns import fpgrowth, association_rules
import matplotlib.pyplot as plt

# Example transaction data (one-hot encoded)
data = {'milk': [1, 0, 1, 1, 0],
        'bread': [1, 1, 1, 0, 0],
        'butter': [0, 1, 1, 1, 1],
        'beer': [0, 1, 0, 1, 1]}
df = pd.DataFrame(data)

# Generate frequent itemsets
frequent_itemsets = fpgrowth(df, min_support=0.4, use_colnames=True)

# Generate rules
rules = association_rules(frequent_itemsets, metric="confidence", min_threshold=0.7)
print(rules[['antecedents', 'consequents', 'support', 'confidence', 'lift']])

# Visualize support vs. confidence
plt.scatter(rules['support'], rules['confidence'], alpha=0.7)
plt.xlabel('Support')
plt.ylabel('Confidence')
plt.title('FP-Growth Association Rules')
plt.show()
```

### Simulated Annealing
Simulated Annealing (SA) is a probabilistic technique for approximating the global optimum of a given function, inspired by the annealing process in metallurgy.

#### Mathematical Foundation
SA explores the solution space by accepting not only improvements but also, with a certain probability, worse solutions to escape local minima.

**Acceptance Probability:**
$$
P(accept) = \min(1, \exp(-\frac{\Delta E}{T}))
$$
where:
- $\Delta E$ is the change in cost (energy)
- $T$ is the current temperature

**Temperature Schedule:**
$$
T(t) = T_0 \alpha^t
$$
where $T_0$ is the initial temperature, $\alpha$ is the cooling rate, and $t$ is the iteration.

**Algorithm Steps:**
1. Start with an initial solution and temperature.
2. At each iteration, generate a neighbor solution.
3. Accept the new solution if it is better, or with probability $P(accept)$ if it is worse.
4. Decrease the temperature according to the schedule.
5. Repeat until stopping criterion is met (e.g., temperature is low or max iterations reached).

#### Implementation
Custom implementation using NumPy or other libraries.

#### Performance Analysis
- Time Complexity: O(n * i) where $i$ is number of iterations
- Space Complexity: O(n)

#### Use Cases
- Traveling salesman problem (TSP)
- Circuit board design
- Job scheduling
- Network design
- Hyperparameter optimization

**Example:**
Finding a near-optimal route for the TSP or optimizing a function with many local minima.

#### Advantages and Limitations
Advantages:
- Can escape local optima
- Simple to implement
- Works with discrete and continuous problems

Limitations:
- May be slow to converge
- Results depend on cooling schedule and parameters
- Not guaranteed to find global optimum

#### Relation to Other Models
- **Genetic Algorithms:** Population-based, uses crossover/mutation; SA uses a single solution and temperature.
- **Gradient Descent:** Deterministic, best for convex problems; SA is stochastic and can escape local minima.
- **Tabu Search:** Uses memory to avoid revisiting solutions; SA uses randomness.

#### Python Implementation and Visualization
```python
import numpy as np
import matplotlib.pyplot as plt

# Objective function: f(x) = x^2 + 10*sin(5x)
def objective(x):
    return x**2 + 10 * np.sin(5*x)

# Simulated Annealing implementation
def simulated_annealing(objective, bounds, n_iterations, initial_temp, alpha):
    x = np.random.uniform(*bounds)
    best = x
    best_eval = objective(x)
    curr, curr_eval = x, best_eval
    temp = initial_temp
    history = [curr_eval]
    for i in range(n_iterations):
        candidate = curr + np.random.uniform(-1, 1)
        candidate = np.clip(candidate, *bounds)
        candidate_eval = objective(candidate)
        if candidate_eval < best_eval or np.random.rand() < np.exp((curr_eval - candidate_eval) / temp):
            curr, curr_eval = candidate, candidate_eval
            if candidate_eval < best_eval:
                best, best_eval = candidate, candidate_eval
        temp *= alpha
        history.append(curr_eval)
    return best, best_eval, history

# Run SA
best, best_eval, history = simulated_annealing(objective, bounds=[-10, 10], n_iterations=200, initial_temp=10, alpha=0.98)
print('Best solution: x=%.4f, f(x)=%.4f' % (best, best_eval))

# Plot convergence
plt.plot(history)
plt.xlabel('Iteration')
plt.ylabel('Objective Value')
plt.title('Simulated Annealing Convergence')
plt.show()
```

## Conclusion

This repository provides a comprehensive collection of machine learning algorithms, covering a wide range of techniques from basic statistical methods to advanced deep learning architectures. The algorithms are organized into several categories:

### Supervised Learning
- Linear and Polynomial Regression for continuous value prediction
- Logistic Regression and Softmax Regression for classification
- Support Vector Machines for both classification and regression
- Decision Trees and Random Forests for structured data
- Gradient Boosting variants (XGBoost, LightGBM, CatBoost) for high-performance prediction

### Unsupervised Learning
- Clustering algorithms (K-Means, DBSCAN) for data grouping
- Dimensionality reduction techniques (PCA, t-SNE) for feature extraction
- Association rule mining (Apriori, FP-Growth) for pattern discovery
- Anomaly detection methods (Isolation Forest, One-Class SVM)

### Deep Learning
- Neural Networks for complex pattern recognition
- Convolutional Neural Networks for image processing
- Deep Q-Networks for reinforcement learning
- Various architectures for specialized tasks

### Time Series Analysis
- ARIMA for traditional time series forecasting
- Prophet for automated time series analysis
- Various specialized models for temporal data

### Optimization Techniques
- Gradient Descent for parameter optimization
- Genetic Algorithms for complex optimization
- Simulated Annealing for global optimization
- Fuzzy Logic for approximate reasoning

Each algorithm implementation includes:
- Detailed mathematical foundations
- Performance characteristics
- Practical use cases
- Advantages and limitations
- Python implementation with hyperparameters

The code is designed to be:
- Educational and well-documented
- Production-ready
- Easy to understand and modify
- Compatible with popular machine learning libraries

For more detailed information about each algorithm, please refer to their respective documentation files in the algorithms directory. The implementations are continuously updated and improved to incorporate the latest developments in machine learning research and best practices. 