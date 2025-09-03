# k-NN Classification using the Pumpkin Seeds Dataset

## Overview

This project demonstrates **k-Nearest Neighbors (k-NN)** classification on the Pumpkin Seeds Dataset, which contains morphological features of pumpkin seeds from two Turkish varieties: **Çerçevelik** and **Ürgüp Sivrisi**.

## Dataset Description

The dataset contains **2,500 samples** with **12 morphological features**:

| Feature | Description | Unit |
|---------|-------------|------|
| Area | Total area of the seed | pixels² |
| Perimeter | Perimeter length | pixels |
| Major_Axis_Length | Length of major axis | pixels |
| Minor_Axis_Length | Length of minor axis | pixels |
| Convex_Area | Area of convex hull | pixels² |
| Equiv_Diameter | Equivalent diameter | pixels |
| Eccentricity | Measure of elongation | ratio |
| Solidity | Ratio of areas | ratio |
| Extent | Ratio of areas | ratio |
| Roundness | Measure of roundness | ratio |
| Aspect_Ration | Major/Minor axis ratio | ratio |
| Compactness | Compactness measure | ratio |

## Mathematical Foundation

### k-Nearest Neighbors Algorithm

The k-NN algorithm classifies a new data point based on the majority class of its \(k\) nearest neighbors in the feature space.

#### Distance Metrics

For two points \(\mathbf{x}_1\) and \(\mathbf{x}_2\) in \(n\)-dimensional space, the **Euclidean distance** is:

\[
d(\mathbf{x}_1, \mathbf{x}_2) = \sqrt{\sum_{i=1}^{n} (x_{1i} - x_{2i})^2}
\]

#### Classification Rule

Given a test point \(\mathbf{x}_{\text{test}}\), the algorithm:

1. Finds the \(k\) nearest training points: \(\{\mathbf{x}_1, \mathbf{x}_2, \ldots, \mathbf{x}_k\}\)
2. Assigns the class label:
   \[
   \text{class}(\mathbf{x}_{\text{test}}) = \arg\max_{c} \sum_{i=1}^{k} \mathbb{I}(y_i = c)
   \]
   where \(\mathbb{I}(\cdot)\) is the indicator function and \(y_i\) is the class of neighbor \(i\).

### Feature Scaling

Since k-NN is distance-based, **standardization** is crucial:

\[
x'_{ij} = \frac{x_{ij} - \mu_j}{\sigma_j}
\]

where:
- \(x_{ij}\) is the \(i\)-th observation of feature \(j\)
- \(\mu_j\) is the mean of feature \(j\) (calculated from training data)
- \(\sigma_j\) is the standard deviation of feature \(j\) (calculated from training data)

## Implementation Details

### 1. Data Preprocessing

- **Class Encoding**: Fixed Turkish character encoding issues
- **Data Splitting**: 70% training, 30% testing using stratified sampling
- **Feature Scaling**: Z-score normalization using training set statistics

### 2. Model Selection

The optimal \(k\) value is determined by testing multiple values:
\[
k \in \{1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21\}
\]

### 3. Performance Metrics

#### Confusion Matrix
For binary classification:

| | Predicted Negative | Predicted Positive |
|---|---|---|
| **Actual Negative** | True Negative (TN) | False Positive (FP) |
| **Actual Positive** | False Negative (FN) | True Positive (TP) |

#### Key Metrics

- **Accuracy**: \(\frac{TP + TN}{TP + TN + FP + FN}\)
- **Precision**: \(\frac{TP}{TP + FP}\)
- **Recall (Sensitivity)**: \(\frac{TP}{TP + FN}\)
- **Specificity**: \(\frac{TN}{TN + FP}\)
- **F1-Score**: \(2 \times \frac{\text{Precision} \times \text{Recall}}{\text{Precision} + \text{Recall}}\)
- **Kappa**: \(\frac{P_o - P_e}{1 - P_e}\) where \(P_o\) is observed accuracy and \(P_e\) is expected accuracy

### 4. Feature Importance

Feature importance is calculated using **correlation analysis**:

\[
\rho_i = \text{corr}(X_i, y)
\]

where \(X_i\) is the \(i\)-th feature and \(y\) is the target variable.

## Results and Analysis

### Optimal k Selection

The algorithm evaluates multiple \(k\) values to find the optimal one that maximizes classification accuracy on the test set.

### Model Performance

The final model achieves high accuracy through:
- Proper feature scaling
- Optimal \(k\) selection
- Comprehensive feature analysis

### Feature Insights

Key morphological features that distinguish pumpkin seed varieties:
- **Geometric properties**: Area, perimeter, axis lengths
- **Shape characteristics**: Roundness, compactness, aspect ratio
- **Structural features**: Solidity, extent, eccentricity

## Code Structure

```r
# Main sections:
1. Data Loading and Exploration
2. Data Preprocessing
3. Feature Analysis and Visualization
4. Data Splitting and Scaling
5. k-NN Model Training and Evaluation
6. Final Model and Predictions
7. Feature Importance Analysis
8. Visualization of Results
9. Summary and Conclusions
```

## Key Functions Used

- **`knn()`**: Core k-NN classification from `class` package
- **`createDataPartition()`**: Stratified data splitting from `caret`
- **`confusionMatrix()`**: Performance evaluation from `caret`
- **`ggplot2`**: Data visualization
- **`corrplot`**: Correlation matrix visualization

## Output Files

The script generates several visualizations saved to the `images/` directory:

1. **`01_class_distribution.png`**: Class balance visualization
2. **`02_feature_importance.png`**: Feature importance ranking
3. **`03_knn_accuracy_vs_k.png`**: k-value optimization plot
4. **`04_correlation_matrix.png`**: Feature correlation heatmap
5. **`05_feature_distributions.png`**: Feature distributions by class

## Usage Instructions

1. **Install Required Packages**: The script automatically installs missing packages
2. **Run the Script**: Execute `Pumpkin_Seeds_Dataset-kNN.R`
3. **Review Output**: Check console output and generated images
4. **Interpret Results**: Analyze performance metrics and feature importance

## Advantages of k-NN

- **Simple and intuitive**: Easy to understand and implement
- **No training phase**: Lazy learning approach
- **Non-parametric**: No assumptions about data distribution
- **Effective for small datasets**: Works well with limited data

## Limitations and Considerations

- **Computational cost**: Scales poorly with large datasets
- **Feature scaling**: Sensitive to feature scales
- **Curse of dimensionality**: Performance degrades with many features
- **Memory requirements**: Stores entire training dataset

## Future Improvements

1. **Feature Engineering**: Create new features from existing ones
2. **Cross-validation**: Implement k-fold cross-validation
3. **Hyperparameter Tuning**: Use grid search for optimal \(k\)
4. **Ensemble Methods**: Combine k-NN with other algorithms
5. **Dimensionality Reduction**: Apply PCA or feature selection

## Conclusion

This project successfully demonstrates k-NN classification on the Pumpkin Seeds Dataset, achieving high accuracy through proper preprocessing, feature scaling, and optimal parameter selection. The analysis provides valuable insights into morphological features that distinguish pumpkin seed varieties.

The implementation serves as a solid foundation for understanding distance-based classification algorithms and can be extended for more complex classification tasks in agricultural and biological applications.
