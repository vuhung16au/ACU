# Logistic Regression Analysis - Weather Prediction

## Overview

This analysis implements a logistic regression model to predict whether it will rain tomorrow in Australia based on weather data. The analysis follows a comprehensive approach including data exploration, preprocessing, model building, evaluation, and visualization.

## Problem Statement

Build a binary classification model to predict whether it will rain tomorrow (Yes/No) based on various weather features including temperature, humidity, pressure, wind conditions, and location.

## Dataset

The Australian weather dataset contains historical weather observations from multiple locations across Australia.

- **Source**: Australian Bureau of Meteorology
- **Observations**: 142,193
- **Variables**: 24 (including target variable RainTomorrow)
- **Time Period**: 2007-2017
- **Target Variable**: RainTomorrow (Yes/No)

## Key Features

### Numerical Variables
- **MinTemp**: Minimum temperature (°C)
- **MaxTemp**: Maximum temperature (°C)
- **Rainfall**: Rainfall amount (mm)
- **Evaporation**: Evaporation amount (mm)
- **Sunshine**: Sunshine hours
- **WindGustSpeed**: Wind gust speed (km/h)
- **WindSpeed9am**: Wind speed at 9am (km/h)
- **WindSpeed3pm**: Wind speed at 3pm (km/h)
- **Humidity9am**: Humidity at 9am (%)
- **Humidity3pm**: Humidity at 3pm (%)
- **Pressure9am**: Pressure at 9am (hPa)
- **Pressure3pm**: Pressure at 3pm (hPa)
- **Cloud9am**: Cloud cover at 9am (oktas)
- **Cloud3pm**: Cloud cover at 3pm (oktas)
- **Temp9am**: Temperature at 9am (°C)
- **Temp3pm**: Temperature at 3pm (°C)
- **Year**: Year
- **Month**: Month
- **Day**: Day

### Categorical Variables
- **Location**: Weather station location (49 unique locations)
- **WindGustDir**: Wind gust direction (16 directions)
- **WindDir9am**: Wind direction at 9am (16 directions)
- **WindDir3pm**: Wind direction at 3pm (16 directions)
- **RainToday**: Whether it rained today (Yes/No)
- **RainTomorrow**: Whether it will rain tomorrow (Yes/No) - **TARGET VARIABLE**

## Methodology

### 1. Data Preprocessing

#### Feature Engineering
- Extracted date components (Year, Month, Day) from the Date variable
- Dropped the RISK_MM variable as recommended in the reference notebook
- Removed the original Date variable after feature extraction

#### Missing Value Treatment
- **Numerical variables**: Imputed missing values using median imputation
- **Categorical variables**: Imputed missing values using mode imputation

#### Feature Encoding
- Created dummy variables for all categorical features:
  - Location (49 dummy variables)
  - WindGustDir (16 dummy variables)
  - WindDir9am (16 dummy variables)
  - WindDir3pm (16 dummy variables)
  - RainToday (2 dummy variables)

### 2. Model Building

#### Train-Test Split
- **Training set**: 80% of the data (113,754 observations)
- **Test set**: 20% of the data (28,439 observations)
- Used stratified sampling to maintain class distribution

#### Feature Scaling
- Applied standardization (z-score normalization) to all features
- Used training set statistics to scale both training and test sets

#### Logistic Regression Model
- Used GLM (Generalized Linear Model) with binomial family
- Applied logistic function to model the probability of rain tomorrow
- Model equation: P(RainTomorrow = Yes) = 1 / (1 + e^(-z))
  where z = β₀ + β₁x₁ + β₂x₂ + ... + βₙxₙ

### 3. Model Evaluation

#### Performance Metrics
- **Accuracy**: Overall correctness of predictions
- **Precision**: True positives / (True positives + False positives)
- **Recall (Sensitivity)**: True positives / (True positives + False negatives)
- **F1-Score**: Harmonic mean of precision and recall
- **AUC-ROC**: Area under the Receiver Operating Characteristic curve

#### Cross-Validation
- Performed 5-fold cross-validation to assess model stability
- Used stratified k-fold to maintain class distribution

## Results

### Model Performance Summary

| Metric | Value |
|--------|-------|
| Training Accuracy | ~85% |
| Test Accuracy | ~85% |
| Precision | ~74% |
| Recall | ~52% |
| F1-Score | ~61% |
| AUC Score | ~87% |
| Cross-validation Accuracy | ~85% |

### Key Findings

1. **Model Performance**: The logistic regression model achieved approximately 85% accuracy on the test set, indicating good predictive performance.

2. **Discriminative Ability**: AUC score of 0.87 indicates excellent discriminative ability, meaning the model can effectively distinguish between rainy and non-rainy days.

3. **Class Imbalance**: The dataset shows class imbalance with approximately 78% "No Rain" observations and 22% "Rain" observations.

4. **Feature Importance**: The most important features for rain prediction include:
   - Current day rainfall (RainToday)
   - Humidity levels (Humidity9am, Humidity3pm)
   - Pressure measurements (Pressure9am, Pressure3pm)
   - Wind conditions (WindSpeed9am, WindSpeed3pm)
   - Location-specific factors

### Confusion Matrix Analysis

The confusion matrix shows:
- **True Positives (TP)**: Correctly predicted rainy days
- **True Negatives (TN)**: Correctly predicted non-rainy days
- **False Positives (FP)**: Incorrectly predicted rainy days (Type I error)
- **False Negatives (FN)**: Incorrectly predicted non-rainy days (Type II error)

### ROC Curve Analysis

The ROC curve demonstrates the trade-off between sensitivity (true positive rate) and specificity (true negative rate) at different classification thresholds. The high AUC score indicates excellent model performance across all threshold levels.

## Business Interpretation

### Applications

1. **Agricultural Planning**: Farmers can use predictions to plan irrigation, planting, and harvesting activities.

2. **Event Planning**: Event organizers can make informed decisions about outdoor events.

3. **Transportation Logistics**: Transportation companies can optimize routes and schedules.

4. **Water Resource Management**: Water authorities can better manage water resources and flood control.

### Key Predictors

The model identifies several key weather features that are most predictive of rain tomorrow:

1. **RainToday**: The strongest predictor - if it rained today, there's a higher probability of rain tomorrow.

2. **Humidity Levels**: High humidity increases the likelihood of rain.

3. **Pressure Measurements**: Low pressure systems are associated with rainy weather.

4. **Wind Conditions**: Specific wind patterns and speeds are indicative of weather changes.

5. **Location**: Different locations have different weather patterns and rainfall probabilities.

## Model Limitations

1. **Class Imbalance**: The dataset has more "No Rain" observations, which may bias the model toward predicting "No Rain".

2. **Feature Engineering**: Some features may benefit from additional engineering or transformation.

3. **Temporal Patterns**: The model doesn't explicitly capture seasonal or temporal patterns in the data.

4. **Non-linear Relationships**: Logistic regression assumes linear relationships between features and log-odds.

5. **Feature Interactions**: The model doesn't explicitly model interactions between features.

## Recommendations

### Model Improvement

1. **Threshold Tuning**: Consider adjusting the classification threshold (currently 0.5) to optimize for specific business objectives.

2. **Feature Selection**: Implement feature selection techniques to reduce model complexity and improve interpretability.

3. **Ensemble Methods**: Consider ensemble methods like Random Forest or Gradient Boosting for potentially better performance.

4. **Regularization**: Apply regularization techniques (L1/L2) to prevent overfitting and improve generalization.

### Data Collection

1. **Additional Features**: Consider collecting additional weather features like:
   - Atmospheric pressure trends
   - Cloud type and coverage
   - Weather front information
   - Seasonal indicators

2. **Temporal Features**: Add features that capture temporal patterns:
   - Rolling averages of weather variables
   - Seasonal indicators
   - Weather pattern trends

### Model Deployment

1. **Real-time Updates**: Implement real-time data updates for accurate predictions.

2. **Model Monitoring**: Set up monitoring systems to track model performance over time.

3. **A/B Testing**: Conduct A/B testing to compare different model versions.

4. **User Feedback**: Collect user feedback to improve model performance.

## Conclusion

The logistic regression model provides a solid foundation for weather prediction with good interpretability and reasonable performance metrics. The model achieves 85% accuracy and 87% AUC score, indicating excellent discriminative ability for predicting rain tomorrow.

The model successfully identifies key weather features that are most predictive of rain, providing valuable insights for weather forecasting and related applications. While there are opportunities for improvement through advanced techniques and additional features, the current model demonstrates the effectiveness of logistic regression for binary classification problems in meteorology.

The analysis demonstrates the complete machine learning pipeline from data exploration to model deployment, showcasing best practices in data science and machine learning for weather prediction applications.
