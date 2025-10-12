# Cross-Validation in R

This project demonstrates the implementation of cross-validation techniques in R for machine learning model selection and hyperparameter tuning using the Ames Housing dataset.

## Overview

Cross-validation is a crucial technique in machine learning that helps ensure our models generalize well to unseen data. This project shows how to:

- Implement cross-validation for hyperparameter tuning
- Use Random Forest regression for house price prediction
- Analyze feature importance and correlations
- Visualize results and model performance

## Dataset

The project uses the **Ames Housing Dataset** which contains information about residential properties in Ames, Iowa. The dataset includes:

- **Training data**: 1,460 observations with 81 features
- **Test data**: 1,459 observations with 80 features
- **Target variable**: SalePrice (house sale price in dollars)

For detailed dataset information, see [Housing-Dataset/README.md](Housing-Dataset/README.md).

## Files Structure

```
10-01-Cross-Validation/
├── README.md                           # This file
├── Cross-Validation.R                  # R script implementation
├── Cross-Validation.Rmd                # R Markdown notebook
├── Cross-Validation.md                 # Generated markdown output
├── Dataset/                            # Dataset files
│   ├── train.csv                      # Training data
│   ├── test.csv                       # Test data
│   └── sample_submission.csv          # Sample submission format
├── Housing-Dataset/                    # Dataset documentation
│   └── README.md                       # Dataset description
└── images/                            # Generated plots and visualizations
    ├── cross_validation_results.png   # CV results plot
    ├── predictions.png                # Prediction visualization
    ├── feature_importance.png         # Feature importance plot
    ├── correlation_plot.png           # Correlation matrix
    └── cross_validation_results.csv  # Results data
```

## Key Features

### 1. Cross-Validation Implementation
- **3-fold cross-validation** for robust model evaluation
- **Parameter tuning** for Random Forest (n_estimators: 50-400)
- **MAE (Mean Absolute Error)** as the evaluation metric

### 2. Data Preprocessing
- **Numeric feature selection** (similar to Python implementation)
- **Median imputation** for missing values
- **Data quality assessment** and validation

### 3. Model Analysis
- **Feature importance ranking** using Random Forest
- **Correlation analysis** between top features
- **Prediction visualization** on test set

### 4. Visualizations
- Cross-validation results plot
- Feature importance bar chart
- Correlation matrix heatmap
- Prediction trends visualization

## Usage

### Running the R Script
```bash
cd 10-01-Cross-Validation
Rscript Cross-Validation.R
```

### Running the R Markdown
```bash
cd 10-01-Cross-Validation
Rscript -e "rmarkdown::render('Cross-Validation.Rmd')"
```

### Required R Packages
```r
install.packages(c("randomForest", "caret", "ggplot2", "dplyr", "VIM", "corrplot", "knitr", "rmarkdown"))
```

## Key Results

The cross-validation analysis reveals:

1. **Optimal number of trees** for the Random Forest model
2. **Feature importance ranking** showing which variables are most predictive
3. **Model performance metrics** using MAE as the evaluation criterion
4. **Correlation patterns** between important features

## Methodology

### Cross-Validation Process
1. **Data Preparation**: Load and preprocess the housing dataset
2. **Feature Selection**: Select numeric features for modeling
3. **Missing Value Treatment**: Impute missing values with median
4. **Parameter Tuning**: Test different n_estimators values (50-400)
5. **Cross-Validation**: Use 3-fold CV for each parameter combination
6. **Model Selection**: Choose parameters with lowest MAE
7. **Final Training**: Train model with optimal parameters
8. **Prediction**: Make predictions on test set

### Evaluation Metrics
- **MAE (Mean Absolute Error)**: Primary evaluation metric
- **Cross-validation consistency**: Ensures robust model selection
- **Feature importance**: Identifies most predictive variables

## Technical Implementation

### R Packages Used
- **randomForest**: Random Forest implementation
- **caret**: Cross-validation and model training
- **ggplot2**: Data visualization
- **dplyr**: Data manipulation
- **VIM**: Missing value analysis
- **corrplot**: Correlation matrix visualization

### Key Functions
- `get_score()`: Cross-validation scoring function
- `randomForest()`: Random Forest model training
- `train()`: Caret-based cross-validation
- `importance()`: Feature importance extraction

## Output Files

The analysis generates several output files:

1. **Images**: PNG files with white backgrounds for all plots
2. **CSV Results**: Cross-validation results in tabular format
3. **HTML/PDF Reports**: R Markdown generated reports

## Learning Objectives

This project demonstrates:

1. **Cross-validation fundamentals** in R
2. **Hyperparameter tuning** using systematic approaches
3. **Feature importance analysis** for model interpretation
4. **Data visualization** for results communication
5. **Reproducible research** using R Markdown

## References

- [Ames Housing Dataset](https://www.kaggle.com/c/home-data-for-ml-course)
- [Cross-Validation Tutorial](https://www.kaggle.com/alexisbcook/cross-validation)
- [R Caret Package Documentation](https://topepo.github.io/caret/)

## License

This project is part of the R for Data Science course materials.
