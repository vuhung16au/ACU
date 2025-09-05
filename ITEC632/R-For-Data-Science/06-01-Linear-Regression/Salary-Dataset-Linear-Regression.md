# Advertising Dataset - Linear Regression Analysis

## Dataset Description

The advertising dataset is a classic dataset used for demonstrating linear regression analysis. It contains information about advertising spending across different media channels and the resulting sales performance.

## Dataset Information

- **Source**: ISLR (Introduction to Statistical Learning with R)
- **File**: `advertising.csv`
- **Observations**: 200
- **Variables**: 4
- **Missing Values**: None

## Variable Descriptions

### 1. TV (Television Advertising)
- **Type**: Continuous numerical
- **Description**: Amount spent on TV advertising (in thousands of dollars)
- **Range**: 0.7 to 296.4
- **Mean**: 147.04
- **Standard Deviation**: 85.85
- **Usage**: Primary predictor variable for sales prediction

### 2. Radio (Radio Advertising)
- **Type**: Continuous numerical
- **Description**: Amount spent on radio advertising (in thousands of dollars)
- **Range**: 0.0 to 49.6
- **Mean**: 23.26
- **Standard Deviation**: 14.85
- **Usage**: Secondary predictor variable

### 3. Newspaper (Newspaper Advertising)
- **Type**: Continuous numerical
- **Description**: Amount spent on newspaper advertising (in thousands of dollars)
- **Range**: 0.3 to 114.0
- **Mean**: 30.55
- **Standard Deviation**: 21.78
- **Usage**: Secondary predictor variable

### 4. Sales (Target Variable)
- **Type**: Continuous numerical
- **Description**: Sales performance (in thousands of units)
- **Range**: 1.6 to 27.0
- **Mean**: 15.13
- **Standard Deviation**: 5.28
- **Usage**: Target variable for prediction

## Data Quality

- **Completeness**: 100% - No missing values
- **Outliers**: Minimal outliers detected in the dataset
- **Data Types**: All variables are continuous numerical
- **Scale**: All monetary values are in thousands of dollars

## Statistical Summary

| Variable   | Count | Mean   | Std    | Min  | 25%   | 50%   | 75%   | Max   |
|------------|-------|--------|--------|------|-------|-------|-------|-------|
| TV         | 200   | 147.04 | 85.85  | 0.7  | 74.38 | 149.75| 218.83| 296.4 |
| Radio      | 200   | 23.26  | 14.85  | 0.0  | 9.98  | 22.90 | 36.53 | 49.6  |
| Newspaper  | 200   | 30.55  | 21.78  | 0.3  | 12.75 | 25.75 | 45.10 | 114.0 |
| Sales      | 200   | 15.13  | 5.28   | 1.6  | 11.00 | 16.00 | 19.05 | 27.0  |

## Business Context

This dataset represents a typical marketing scenario where a company wants to understand the relationship between advertising spending and sales performance. The analysis helps answer key business questions:

1. Which advertising channel has the strongest impact on sales?
2. How much should be invested in each advertising channel?
3. What is the expected return on advertising investment?

## Correlation Analysis

- **TV vs Sales**: Strong positive correlation (highest)
- **Radio vs Sales**: Moderate positive correlation
- **Newspaper vs Sales**: Weak positive correlation
- **TV vs Radio**: Low correlation (good for model stability)
- **TV vs Newspaper**: Low correlation (good for model stability)
- **Radio vs Newspaper**: Low correlation (good for model stability)

## Model Suitability

This dataset is well-suited for linear regression analysis because:

1. **Linear Relationship**: Clear linear relationship between TV advertising and sales
2. **Sufficient Data**: 200 observations provide adequate sample size
3. **Clean Data**: No missing values or significant outliers
4. **Business Relevance**: Real-world marketing scenario
5. **Interpretability**: Results are easily interpretable for business decisions

## Expected Outcomes

Based on the dataset characteristics, we expect:

- TV advertising to be the strongest predictor of sales
- A positive linear relationship between advertising spending and sales
- Good model performance with R-squared > 0.7
- Statistically significant coefficients
- Residuals following normal distribution
