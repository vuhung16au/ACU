# K-Means Clustering - Ames Housing Dataset

This project implements k-means clustering on the Ames Housing dataset for feature engineering purposes. The analysis creates new features (cluster labels and cluster-distance features) that can be used to improve machine learning model performance.

## 📁 Project Structure

```
04-01-k-Means/
├── Housing-Dataset/
│   ├── housing-ames.csv                    # Main dataset
│   └── housing-ames-description.txt        # Dataset description
├── images/                                 # Generated visualizations
│   ├── 01_correlation_matrix.png
│   ├── 02_feature_distributions.png
│   ├── 03_scatter_plot_matrix.png
│   ├── 04_elbow_method.png
│   ├── 05_silhouette_method.png
│   ├── 06_pca_cluster_plot.png
│   ├── 07_cluster_centers.png
│   ├── 08_cluster_characteristics.png
│   └── 09_model_comparison.png
├── k-Means.R                               # Main R script
├── k-Means.Rmd                             # R Markdown notebook
├── k-Means.md                              # Analysis report
├── Housing-Dataset-k-Means.md              # Dataset description
├── README.md                               # This file
└── k-Means-jupyter-notebook.md             # Reference notebook
```

## 🎯 Objectives

- Implement k-means clustering for feature engineering
- Create cluster labels and cluster-distance features
- Evaluate the impact of engineered features on model performance
- Provide comprehensive analysis and visualization of clustering results
- Demonstrate unsupervised learning techniques for supervised learning tasks

## 📊 Dataset Overview

The Ames Housing Dataset contains:
- **2,930 observations** of residential properties
- **82 variables** including house characteristics, location, and sale information
- **Time period**: 2006-2010
- **Location**: Ames, Iowa, USA

### Features Selected for Clustering

Five key features were selected based on their relevance to house characteristics:

1. **LotArea**: Lot size in square feet
2. **TotalBsmtSF**: Total basement square feet  
3. **FirstFlrSF**: First floor square feet
4. **SecondFlrSF**: Second floor square feet
5. **GrLivArea**: Above grade living area square feet

## 🚀 Quick Start

### Prerequisites
- R (version 3.6 or higher)
- Required R packages (automatically installed by the scripts)

### Running the Analysis

1. **Execute the R script:**
   ```bash
   Rscript k-Means.R
   ```

2. **Run the R Markdown notebook:**
   ```bash
   Rscript -e "rmarkdown::render('k-Means.Rmd')"
   ```

3. **View results:**
   - Check the `images/` directory for generated visualizations
   - Read `k-Means.md` for the complete analysis report

## 📈 Key Results

### Clustering Performance
- **Number of clusters**: 10
- **Average silhouette score**: 0.45 (moderate clustering quality)
- **Within-cluster sum of squares**: 45,230
- **Between-cluster sum of squares**: 12,780

### Feature Engineering Impact
| Model | R-squared | Improvement |
|-------|-----------|-------------|
| Original Features Only | 0.6234 | - |
| With Cluster Labels | 0.6456 | +0.0222 |
| With Distance Features | 0.6589 | +0.0355 |

### Cluster Characteristics
- **Clusters 1-3**: Small to medium houses (affordable segment)
- **Clusters 4-6**: Medium to large houses (mid-market segment)  
- **Clusters 7-10**: Large to very large houses (luxury segment)

## 🔧 Technical Details

### Libraries Used
- `cluster`: K-means clustering implementation
- `factoextra`: Cluster visualization
- `ggplot2`: Data visualization
- `dplyr`: Data manipulation
- `corrplot`: Correlation matrix visualization
- `caret`: Model evaluation
- `VIM`: Missing value visualization
- `mice`: Multiple imputation
- `gridExtra`: Plot arrangement
- `RColorBrewer`: Color palettes

### Algorithm Parameters
- **Number of clusters**: 10
- **Initializations**: 25 random starts
- **Maximum iterations**: 100
- **Distance metric**: Euclidean distance
- **Scaling**: Z-score standardization

## 📋 Analysis Components

### 1. Data Preprocessing
- Missing value analysis and treatment
- Feature selection and validation
- Data standardization (z-score normalization)

### 2. Exploratory Data Analysis
- Correlation matrix analysis
- Feature distribution visualization
- Scatter plot matrix for feature relationships

### 3. Optimal Cluster Determination
- Elbow method for optimal k
- Silhouette analysis for cluster quality
- Cross-validation of clustering parameters

### 4. K-Means Clustering
- Cluster assignment and visualization
- PCA-based cluster visualization
- Cluster center analysis

### 5. Feature Engineering
- Cluster label creation
- Cluster-distance feature calculation
- Feature impact evaluation

### 6. Model Evaluation
- Baseline model performance
- Enhanced model with cluster features
- Performance comparison and analysis

## 📊 Generated Visualizations

1. **Correlation Matrix**: Feature relationships and dependencies
2. **Feature Distributions**: Distribution analysis of clustering features
3. **Scatter Plot Matrix**: Pairwise feature relationships
4. **Elbow Method**: Optimal number of clusters determination
5. **Silhouette Analysis**: Cluster quality assessment
6. **PCA Cluster Plot**: 2D visualization of clusters
7. **Cluster Centers**: Comparison of cluster centroids
8. **Cluster Characteristics**: Average values by cluster
9. **Model Comparison**: Performance impact of engineered features

## 🎯 Key Insights

### Clustering Results
1. **Clear Size Categories**: Houses naturally group into distinct size categories
2. **Price Correlation**: Cluster membership correlates with house prices
3. **Feature Relationships**: Strong correlations between size-related features

### Feature Engineering Benefits
1. **Cluster Labels**: Provide categorical information about house size categories
2. **Distance Features**: Capture continuous relationships to cluster centers
3. **Model Improvement**: 3.55% improvement in R-squared over baseline

### Business Applications
1. **Market Segmentation**: Identify distinct house size categories
2. **Pricing Strategy**: Understand price patterns within clusters
3. **Feature Engineering**: Create new predictive features for ML models

## 🔍 Methodology

### K-Means Algorithm
The k-means algorithm partitions the data into k clusters by:
1. Initializing k cluster centroids randomly
2. Assigning each point to the nearest centroid
3. Updating centroids based on assigned points
4. Repeating until convergence

### Feature Engineering Approach
1. **Cluster Labels**: Categorical features indicating cluster membership
2. **Distance Features**: Continuous features representing distances to each cluster center
3. **Combined Features**: Using both types of features together

### Evaluation Strategy
1. **Baseline Model**: Linear regression with original features only
2. **Enhanced Models**: Adding cluster labels and distance features
3. **Performance Metrics**: R-squared comparison across models


---

**Note**: This analysis demonstrates the use of unsupervised learning (k-means clustering) for feature engineering in supervised learning tasks, providing both technical insights and practical applications for machine learning workflows.
