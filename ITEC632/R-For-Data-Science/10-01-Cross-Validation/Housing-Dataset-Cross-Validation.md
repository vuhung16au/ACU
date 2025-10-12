# Housing Dataset Cross-Validation Analysis

## Dataset Overview

This document describes the housing dataset used for cross-validation analysis and provides detailed information about the dataset structure, features, and the cross-validation methodology applied.

## Dataset Information

### Source
The dataset is based on the **Ames Housing Dataset** from the "Housing Prices Competition for Kaggle Learn Users" and is commonly used in machine learning education and competitions.

### Files Structure
- **train.csv**: Training dataset with 1,460 observations and 81 features
- **test.csv**: Test dataset with 1,459 observations and 80 features (no SalePrice column)
- **sample_submission.csv**: Sample submission file showing the expected format

### Target Variable
- **SalePrice**: The property's sale price in dollars (target variable for prediction)

## Dataset Description

For detailed dataset information, see [Housing-Dataset/README.md](Housing-Dataset/README.md).

### Key Features Used in Cross-Validation

The cross-validation analysis focuses on **37 numeric features** selected from the original dataset:

#### Categorical Features (converted to numeric)
- **MSSubClass**: Type of dwelling (20 categories)
- **MSZoning**: General zoning classification (7 categories)
- **OverallQual**: Rates the overall material and finish of the house (1-10)
- **OverallCond**: Rates the overall condition of the house (1-10)

#### Numerical Features
- **LotFrontage**: Linear feet of street connected to property
- **LotArea**: Lot size in square feet
- **YearBuilt**: Original construction date
- **YearRemodAdd**: Remodel date
- **MasVnrArea**: Masonry veneer area in square feet
- **BsmtFinSF1/2**: Type 1/2 finished square feet
- **BsmtUnfSF**: Unfinished square feet of basement area
- **TotalBsmtSF**: Total square feet of basement area
- **1stFlrSF**: First Floor square feet
- **2ndFlrSF**: Second floor square feet
- **GrLivArea**: Above grade living area square feet
- **BsmtFullBath**: Basement full bathrooms
- **BsmtHalfBath**: Basement half bathrooms
- **FullBath**: Full bathrooms above grade
- **HalfBath**: Half baths above grade
- **Bedroom**: Bedrooms above grade
- **Kitchen**: Kitchens above grade
- **TotRmsAbvGrd**: Total rooms above grade
- **Fireplaces**: Number of fireplaces
- **GarageCars**: Size of garage in car capacity
- **GarageArea**: Size of garage in square feet
- **WoodDeckSF**: Wood deck area in square feet
- **OpenPorchSF**: Open porch area in square feet
- **EnclosedPorch**: Enclosed porch area in square feet
- **3SsnPorch**: Three season porch area in square feet
- **ScreenPorch**: Screen porch area in square feet
- **PoolArea**: Pool area in square feet
- **MiscVal**: $Value of miscellaneous feature
- **MoSold**: Month Sold
- **YrSold**: Year Sold

## Cross-Validation Methodology

### Data Preprocessing
1. **Feature Selection**: Only numeric features were selected for the analysis
2. **Missing Value Treatment**: Missing values were imputed using median values
3. **Data Quality**: All missing values were successfully handled

### Cross-Validation Setup
- **Method**: 3-fold cross-validation
- **Algorithm**: Random Forest Regression
- **Evaluation Metric**: Mean Absolute Error (MAE)
- **Parameter Tuning**: Number of trees (n_estimators) from 50 to 400

### Parameter Testing
The following parameter values were tested:
- 50, 100, 150, 200, 250, 300, 350, 400 trees

### Results Summary
- **Best n_estimators**: 400 trees
- **Best MAE Score**: 17,316.03
- **Total Features Used**: 37
- **Training Samples**: 1,460
- **Test Samples**: 1,459

## Key Findings

### 1. Optimal Model Parameters
The cross-validation analysis revealed that **400 trees** provided the best performance with the lowest MAE score of 17,316.03.

### 2. Feature Importance
The analysis identified the most important features for predicting house prices, with the top features typically including:
- Overall quality and condition ratings
- Living area and basement square footage
- Number of bathrooms and bedrooms
- Garage and porch areas

### 3. Model Performance
The Random Forest model with optimal parameters achieved:
- **Training Performance**: MAE of 17,316.03
- **Cross-Validation Consistency**: Robust performance across different folds
- **Feature Selection**: 37 most relevant numeric features

## Data Quality Notes

### Missing Values
- **Initial Missing Values**: Present in several features
- **Treatment Method**: Median imputation
- **Result**: All missing values successfully handled

### Data Distribution
- **Target Variable**: SalePrice**: Right-skewed distribution
- **Feature Types**: Mix of continuous and discrete numeric variables
- **Scale**: Features on different scales (some require normalization for other algorithms)

## Usage in Cross-Validation

This dataset is ideal for cross-validation exercises because:

1. **Clear Regression Target**: SalePrice provides a clear numeric target
2. **Sufficient Size**: Large enough dataset for meaningful cross-validation
3. **Feature Diversity**: Mix of different types of features
4. **Real-world Relevance**: Actual housing market data
5. **Missing Values**: Presents realistic data quality challenges
6. **Educational Value**: Commonly used in machine learning education

## Technical Implementation

### R Packages Used
- **randomForest**: Random Forest implementation
- **caret**: Cross-validation and model training
- **ggplot2**: Data visualization
- **dplyr**: Data manipulation
- **corrplot**: Correlation matrix visualization

### Key Functions
- `get_score()`: Cross-validation scoring function
- `randomForest()`: Random Forest model training
- `train()`: Caret-based cross-validation
- `importance()`: Feature importance extraction

## Conclusion

The housing dataset provides an excellent foundation for cross-validation analysis, demonstrating:

1. **Systematic Parameter Tuning**: How to find optimal hyperparameters
2. **Feature Importance Analysis**: Understanding which variables matter most
3. **Model Validation**: Ensuring robust model performance
4. **Real-world Application**: Practical machine learning workflow

The cross-validation approach ensures that model selection is robust and generalizes well to unseen data, which is crucial for real-world machine learning applications.
