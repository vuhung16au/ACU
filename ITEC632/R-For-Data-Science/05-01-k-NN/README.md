# k-NN Classification: Pumpkin Seeds Dataset

## Project Overview

This project implements **k-Nearest Neighbors (k-NN)** classification to distinguish between two varieties of Turkish pumpkin seeds based on morphological features. The analysis demonstrates fundamental machine learning concepts including data preprocessing, feature scaling, model selection, and performance evaluation.

## 🎯 Project Goals

- Implement k-NN classification from scratch
- Demonstrate proper data preprocessing techniques
- Analyze feature importance and correlations
- Visualize classification results and model performance
- Provide comprehensive documentation and code examples

## 📊 Dataset Information

**Source**: Pumpkin Seeds Dataset  
**Classes**: 2 (Çerçevelik vs Ürgüp Sivrisi)  
**Samples**: 2,500  
**Features**: 12 morphological characteristics  
**Features Include**: Area, Perimeter, Axis Lengths, Roundness, Compactness, etc.

## 📁 Project Structure

```
05-01-k-NN/
├── Pumpkin_Seeds_Dataset/
│   ├── Pumpkin_Seeds_Dataset.csv          # Main dataset
│   ├── Pumpkin_Seeds_Dataset.xlsx         # Excel version
│   ├── Pumpkin_Seeds_Dataset.arff         # ARFF format
│   ├── README.md                          # Dataset documentation
│   └── Pumpkin_Seeds_Dataset_Citation_Request.txt
├── Pumpkin_Seeds_Dataset-kNN.R            # Main R script
├── Pumpkin_Seeds_Dataset-kNN.md           # Detailed documentation
├── README.md                              # This file
└── images/                                # Generated visualizations
    ├── 01_class_distribution.png
    ├── 02_feature_importance.png
    ├── 03_knn_accuracy_vs_k.png
    ├── 04_correlation_matrix.png
    └── 05_feature_distributions.png
```

## 🚀 Quick Start

### Prerequisites

- **R** (version 3.6.0 or higher)
- **RStudio** (recommended for better experience)

### Installation

1. **Clone or download** this project to your local machine
2. **Open R/RStudio** and set working directory to the project folder
3. **Run the main script**:

```r
# Set working directory
setwd("path/to/05-01-k-NN")

# Source the script
source("Pumpkin_Seeds_Dataset-kNN.R")
```

### Alternative: Step-by-Step Execution

```r
# Load required packages (automatically installed if missing)
library(class)      # k-NN implementation
library(caret)      # Data splitting and evaluation
library(ggplot2)    # Visualization
library(dplyr)      # Data manipulation
library(corrplot)   # Correlation plots
library(gridExtra)  # Plot arrangement

# Run the script
source("Pumpkin_Seeds_Dataset-kNN.R")
```

## 📋 What the Script Does

### 1. Data Loading & Exploration
- Loads the CSV dataset
- Displays basic statistics and structure
- Checks for missing values and data quality

### 2. Data Preprocessing
- Fixes Turkish character encoding issues
- Converts class labels to factors
- Separates features and target variables

### 3. Feature Analysis
- Creates correlation matrix
- Generates feature distribution plots
- Identifies feature relationships

### 4. Model Preparation
- Splits data into training (70%) and testing (30%) sets
- Applies Z-score standardization
- Ensures proper feature scaling

### 5. k-NN Implementation
- Tests multiple k values (1, 3, 5, ..., 21)
- Finds optimal k for maximum accuracy
- Trains final model with best parameters

### 6. Performance Evaluation
- Generates confusion matrix
- Calculates comprehensive metrics (Accuracy, Precision, Recall, F1, Kappa)
- Analyzes feature importance

### 7. Visualization
- Creates and saves multiple plots
- Generates publication-quality images
- Provides insights into model behavior

## 📈 Expected Output

### Console Output
- Dataset overview and statistics
- Preprocessing steps and data quality checks
- k-NN evaluation results for different k values
- Final model performance metrics
- Feature importance rankings

### Generated Images
- **Class Distribution**: Shows balance between pumpkin seed varieties
- **Feature Importance**: Ranks features by correlation with target
- **k-NN Accuracy vs k**: Optimization plot for parameter selection
- **Correlation Matrix**: Feature relationships heatmap
- **Feature Distributions**: Individual feature plots by class

## 🔧 Customization Options

### Modify k Values
```r
# Change the range of k values to test
k_values <- c(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21)
```

### Adjust Train/Test Split
```r
# Change the training set proportion
train_indices <- createDataPartition(target, p = 0.8, list = FALSE)  # 80% training
```

### Modify Feature Scaling
```r
# Use Min-Max scaling instead of Z-score
X_train_scaled <- scale(X_train, center = FALSE, scale = apply(X_train, 2, max) - apply(X_train, 2, min))
```

## 📚 Key Concepts Explained

### k-Nearest Neighbors
- **Distance-based classification** algorithm
- **No training phase** - stores all training data
- **Majority voting** among k nearest neighbors
- **Sensitive to feature scales** - requires standardization

### Feature Scaling
- **Z-score normalization**: \((x - \mu) / \sigma\)
- **Prevents features** with larger scales from dominating
- **Essential for distance-based** algorithms like k-NN

### Model Selection
- **Grid search** over different k values
- **Cross-validation** approach using train/test split
- **Performance-based** selection of optimal parameters

## 🎓 Learning Outcomes

After completing this project, you will understand:

- **Data preprocessing** for machine learning
- **Feature scaling** and its importance
- **k-NN algorithm** implementation and optimization
- **Model evaluation** using multiple metrics
- **Feature importance** analysis
- **Data visualization** techniques
- **R programming** for machine learning

## 🔍 Troubleshooting

### Common Issues

1. **Package Installation Errors**
   ```r
   # Install packages manually if needed
   install.packages(c("class", "caret", "ggplot2", "dplyr", "corrplot", "gridExtra"))
   ```

2. **Encoding Issues**
   - The script automatically handles Turkish character encoding
   - If problems persist, check your R locale settings

3. **Memory Issues**
   - Reduce dataset size for testing
   - Close other R sessions to free memory

4. **Plot Saving Errors**
   - Ensure the `images/` directory exists
   - Check write permissions in the project folder

### Performance Tips

- **Small datasets**: k-NN works well with < 10,000 samples
- **Feature count**: Consider dimensionality reduction for > 20 features
- **Computational time**: k-NN scales with dataset size

## 📖 Further Reading

- **k-NN Algorithm**: [Wikipedia - k-NN](https://en.wikipedia.org/wiki/K-nearest_neighbors_algorithm)
- **R for Machine Learning**: [R Documentation](https://www.r-project.org/)
- **Data Preprocessing**: [Feature Scaling Methods](https://en.wikipedia.org/wiki/Feature_scaling)
- **Model Evaluation**: [Confusion Matrix](https://en.wikipedia.org/wiki/Confusion_matrix)

## 🤝 Contributing

Feel free to:
- **Report issues** or bugs
- **Suggest improvements** to the code
- **Add new features** or visualizations
- **Improve documentation**

## 📄 License

This project is for educational purposes. The dataset and code are provided as-is for learning and research.

## 👨‍💻 Author

Data Science Project - ITEC632 Course  
**Date**: 2024  
**Course**: R for Data Science

---

**Happy Learning! 🎉**

For questions or support, please refer to the course materials or instructor.
