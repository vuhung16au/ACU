# Cross-Validation in R: Iris Dataset

This project demonstrates the implementation of cross-validation techniques in R for classification using the famous Iris dataset. The project shows how to use cross-validation to compare different machine learning algorithms and select the best performing model.

## Overview

Cross-validation is a crucial technique in machine learning that helps ensure our models generalize well to unseen data. This project shows how to:

- Implement cross-validation for classification tasks
- Compare multiple machine learning algorithms
- Analyze feature importance and model performance
- Visualize results and model comparisons

## Dataset

The project uses the **Iris Dataset** which contains measurements of iris flowers:

- **Size**: 150 observations with 4 features and 1 target variable
- **Features**: Sepal length, sepal width, petal length, petal width (all in cm)
- **Target**: Species classification (setosa, versicolor, virginica)
- **Quality**: Complete dataset with no missing values

For detailed dataset information, see [iris-dataset/README.md](iris-dataset/README.md).

## Files Structure

```
10-02-Cross-Validation-Iris/
├── README.md                           # This file
├── Cross-Validation-Iris.R              # R script implementation
├── Cross-Validation-Iris.Rmd            # R Markdown notebook
├── Cross-Validation-Iris.md             # Generated markdown output
├── iris-dataset/                        # Dataset documentation
│   └── README.md                        # Dataset description
├── iris-dataset-Cross-Validation-Iris.md # Dataset and analysis description
└── images/                             # Generated plots and visualizations
    ├── sepal_plot.png                  # Sepal measurements scatter plot
    ├── petal_plot.png                  # Petal measurements scatter plot
    ├── sepal_length_boxplot.png        # Sepal length distribution
    ├── petal_length_boxplot.png        # Petal length distribution
    ├── model_comparison.png            # Model performance comparison
    ├── feature_importance.png          # Feature importance analysis
    ├── confusion_matrix.png            # Best model confusion matrix
    ├── cv_by_fold.png                  # Cross-validation performance by fold
    └── cross_validation_results.csv    # Results data
```

## Key Features

### 1. Cross-Validation Implementation
- **10-fold cross-validation** for robust model evaluation
- **Multiple algorithms** compared: k-NN, SVM, Random Forest, Naive Bayes, LDA
- **Stratified sampling** to maintain class balance

### 2. Data Visualization
- **Scatter plots** showing feature relationships and class separation
- **Box plots** displaying feature distributions by species
- **Performance comparisons** across different algorithms

### 3. Model Analysis
- **Feature importance ranking** using Random Forest
- **Confusion matrix analysis** for best performing model
- **Cross-validation performance** by fold visualization

### 4. Algorithm Comparison
- **k-Nearest Neighbors (k-NN)**: Simple and effective
- **Support Vector Machine (SVM)**: Good for linear and non-linear boundaries
- **Random Forest**: Ensemble method with feature importance
- **Naive Bayes**: Probabilistic classifier
- **Linear Discriminant Analysis (LDA)**: Linear classifier

## Usage

### Running the R Script
```bash
cd 10-02-Cross-Validation-Iris
Rscript Cross-Validation-Iris.R
```

### Running the R Markdown
```bash
cd 10-02-Cross-Validation-Iris
Rscript -e "rmarkdown::render('Cross-Validation-Iris.Rmd')"
```

### Required R Packages
```r
install.packages(c("caret", "e1071", "randomForest", "class", "ggplot2", "dplyr", "gridExtra"))
```

## Key Results

The cross-validation analysis reveals:

1. **Best performing algorithm** for iris classification
2. **Feature importance ranking** showing which measurements matter most
3. **Model performance metrics** using accuracy as the evaluation criterion
4. **Cross-validation consistency** across different folds

## Methodology

### Cross-Validation Process
1. **Data Preparation**: Load and explore the iris dataset
2. **Feature Analysis**: Visualize feature distributions and relationships
3. **Model Training**: Train 5 different algorithms with 10-fold CV
4. **Performance Comparison**: Compare models using accuracy metrics
5. **Best Model Selection**: Choose the algorithm with highest accuracy
6. **Feature Importance**: Analyze which features are most predictive
7. **Confusion Matrix**: Evaluate best model's classification performance

### Evaluation Metrics
- **Accuracy**: Primary evaluation metric
- **Cross-validation consistency**: Ensures robust model selection
- **Feature importance**: Identifies most predictive variables

## Technical Implementation

### R Packages Used
- **caret**: Cross-validation and model training
- **e1071**: Support Vector Machine implementation
- **randomForest**: Random Forest algorithm
- **class**: k-Nearest Neighbors
- **ggplot2**: Data visualization
- **dplyr**: Data manipulation
- **gridExtra**: Plot arrangement

### Key Functions
- `train()`: Caret-based model training with cross-validation
- `trainControl()`: Cross-validation control parameters
- `resamples()`: Model comparison
- `confusionMatrix()`: Performance evaluation
- `varImp()`: Feature importance analysis

## Output Files

The analysis generates several output files:

1. **Images**: PNG files with white backgrounds for all plots
2. **CSV Results**: Cross-validation results in tabular format
3. **HTML/PDF Reports**: R Markdown generated reports

## Learning Objectives

This project demonstrates:

1. **Cross-validation fundamentals** in R for classification
2. **Algorithm comparison** using systematic approaches
3. **Feature importance analysis** for model interpretation
4. **Data visualization** for results communication
5. **Reproducible research** using R Markdown

## Iris Dataset Characteristics

### Perfect for Cross-Validation
- **Small and manageable**: 150 samples make it easy to understand concepts
- **Balanced classes**: Equal representation of all three species
- **Clear patterns**: Well-separated classes make it easy to see performance
- **No missing values**: No preprocessing required
- **Educational value**: Classic example in machine learning education

### Feature Information
- **Sepal Length**: 4.3 - 7.9 cm (mean ≈ 5.8 cm)
- **Sepal Width**: 2.0 - 4.4 cm (mean ≈ 3.1 cm)
- **Petal Length**: 1.0 - 6.9 cm (mean ≈ 3.8 cm)
- **Petal Width**: 0.1 - 2.5 cm (mean ≈ 1.2 cm)

## References

- [Iris Dataset - Wikipedia](https://en.wikipedia.org/wiki/Iris_flower_data_set)
- [Fisher's Original Paper](https://onlinelibrary.wiley.com/doi/abs/10.1111/j.1469-1809.1936.tb02137.x)
- [R Caret Package Documentation](https://topepo.github.io/caret/)
- [Cross-Validation Tutorial](https://rpubs.com/muxicheng/1004550)

## License

This project is part of the R for Data Science course materials.
