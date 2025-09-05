# Weather Dataset - Logistic Regression Analysis

## Dataset Overview

The Australian Weather Dataset is a comprehensive collection of weather observations from multiple locations across Australia. This dataset is used for building a logistic regression model to predict whether it will rain tomorrow based on various weather features.

## Dataset Information

- **Source**: Australian Bureau of Meteorology
- **File**: `weatherAUS.csv`
- **Total Observations**: 142,193
- **Total Variables**: 24
- **Time Period**: 2007-2017
- **Target Variable**: RainTomorrow (Yes/No)

## Variable Descriptions

### 1. Date
- **Type**: Date
- **Description**: Date of the weather observation
- **Format**: YYYY-MM-DD
- **Example**: 2008-12-01
- **Note**: Converted to Year, Month, Day features during preprocessing

### 2. Location
- **Type**: Categorical
- **Description**: Weather station location
- **Unique Values**: 49 locations
- **Examples**: Albury, BadgerysCreek, Cobar, CoffsHarbour, Moree, Newcastle, etc.
- **Note**: High cardinality categorical variable, converted to dummy variables

### 3. MinTemp
- **Type**: Numerical (Float)
- **Description**: Minimum temperature in degrees Celsius
- **Range**: -8.2 to 33.9
- **Missing Values**: 637 (0.4%)
- **Note**: Imputed with median value

### 4. MaxTemp
- **Type**: Numerical (Float)
- **Description**: Maximum temperature in degrees Celsius
- **Range**: -4.8 to 48.1
- **Missing Values**: 322 (0.2%)
- **Note**: Imputed with median value

### 5. Rainfall
- **Type**: Numerical (Float)
- **Description**: Rainfall amount in millimeters
- **Range**: 0.0 to 371.0
- **Missing Values**: 1,406 (1.0%)
- **Note**: Imputed with median value, capped outliers at 3.2mm

### 6. Evaporation
- **Type**: Numerical (Float)
- **Description**: Evaporation amount in millimeters
- **Range**: 0.0 to 145.0
- **Missing Values**: 60,843 (42.8%)
- **Note**: High missing value percentage, imputed with median value

### 7. Sunshine
- **Type**: Numerical (Float)
- **Description**: Sunshine hours
- **Range**: 0.0 to 14.5
- **Missing Values**: 67,816 (47.7%)
- **Note**: High missing value percentage, imputed with median value

### 8. WindGustDir
- **Type**: Categorical
- **Description**: Direction of the strongest wind gust
- **Unique Values**: 16 directions
- **Examples**: W, WNW, WSW, NE, NNW, N, NNE, SW, ENE, SSE, S, NW, SE, ESE, E, SSW
- **Missing Values**: 9,330 (6.6%)
- **Note**: Imputed with mode value, converted to dummy variables

### 9. WindGustSpeed
- **Type**: Numerical (Float)
- **Description**: Speed of the strongest wind gust in km/h
- **Range**: 6.0 to 135.0
- **Missing Values**: 9,270 (6.5%)
- **Note**: Imputed with median value

### 10. WindDir9am
- **Type**: Categorical
- **Description**: Wind direction at 9am
- **Unique Values**: 16 directions
- **Examples**: W, NNW, SE, ENE, SW, SSE, S, NE, SSW, N, WSW, ESE, E, NW, WNW, NNE
- **Missing Values**: 10,013 (7.0%)
- **Note**: Imputed with mode value, converted to dummy variables

### 11. WindDir3pm
- **Type**: Categorical
- **Description**: Wind direction at 3pm
- **Unique Values**: 16 directions
- **Examples**: WNW, WSW, E, NW, W, SSE, ESE, ENE, NNW, SSW, SW, SE, N, S, NNE, NE
- **Missing Values**: 3,778 (2.7%)
- **Note**: Imputed with mode value, converted to dummy variables

### 12. WindSpeed9am
- **Type**: Numerical (Float)
- **Description**: Wind speed at 9am in km/h
- **Range**: 0.0 to 130.0
- **Missing Values**: 1,348 (0.9%)
- **Note**: Imputed with median value, capped outliers at 55 km/h

### 13. WindSpeed3pm
- **Type**: Numerical (Float)
- **Description**: Wind speed at 3pm in km/h
- **Range**: 0.0 to 87.0
- **Missing Values**: 2,630 (1.8%)
- **Note**: Imputed with median value, capped outliers at 57 km/h

### 14. Humidity9am
- **Type**: Numerical (Float)
- **Description**: Humidity at 9am as a percentage
- **Range**: 0.0 to 100.0
- **Missing Values**: 1,774 (1.2%)
- **Note**: Imputed with median value

### 15. Humidity3pm
- **Type**: Numerical (Float)
- **Description**: Humidity at 3pm as a percentage
- **Range**: 0.0 to 100.0
- **Missing Values**: 3,610 (2.5%)
- **Note**: Imputed with median value

### 16. Pressure9am
- **Type**: Numerical (Float)
- **Description**: Atmospheric pressure at 9am in hectopascals (hPa)
- **Range**: 980.5 to 1041.0
- **Missing Values**: 14,014 (9.9%)
- **Note**: Imputed with median value

### 17. Pressure3pm
- **Type**: Numerical (Float)
- **Description**: Atmospheric pressure at 3pm in hectopascals (hPa)
- **Range**: 977.1 to 1039.6
- **Missing Values**: 13,981 (9.8%)
- **Note**: Imputed with median value

### 18. Cloud9am
- **Type**: Numerical (Float)
- **Description**: Cloud cover at 9am in oktas (0-8 scale)
- **Range**: 0.0 to 9.0
- **Missing Values**: 53,657 (37.7%)
- **Note**: High missing value percentage, imputed with median value

### 19. Cloud3pm
- **Type**: Numerical (Float)
- **Description**: Cloud cover at 3pm in oktas (0-8 scale)
- **Range**: 0.0 to 9.0
- **Missing Values**: 57,094 (40.1%)
- **Note**: High missing value percentage, imputed with median value

### 20. Temp9am
- **Type**: Numerical (Float)
- **Description**: Temperature at 9am in degrees Celsius
- **Range**: -7.2 to 40.2
- **Missing Values**: 904 (0.6%)
- **Note**: Imputed with median value

### 21. Temp3pm
- **Type**: Numerical (Float)
- **Description**: Temperature at 3pm in degrees Celsius
- **Range**: -5.4 to 47.0
- **Missing Values**: 2,726 (1.9%)
- **Note**: Imputed with median value

### 22. RainToday
- **Type**: Categorical
- **Description**: Whether it rained today (Yes/No)
- **Unique Values**: Yes, No
- **Missing Values**: 1,406 (1.0%)
- **Note**: Imputed with mode value, converted to dummy variables

### 23. RISK_MM
- **Type**: Numerical (Float)
- **Description**: Amount of rain in millimeters (excluded from analysis)
- **Note**: Dropped from dataset as recommended in reference notebook

### 24. RainTomorrow
- **Type**: Categorical
- **Description**: Whether it will rain tomorrow (Yes/No) - **TARGET VARIABLE**
- **Unique Values**: Yes, No
- **Distribution**: 
  - No: 110,316 (77.6%)
  - Yes: 31,877 (22.4%)
- **Missing Values**: 0
- **Note**: This is the target variable for the logistic regression model

## Derived Features

During preprocessing, the following features were created:

### Date Components
- **Year**: Extracted from Date (2007-2017)
- **Month**: Extracted from Date (1-12)
- **Day**: Extracted from Date (1-31)

## Data Quality Issues

### Missing Values
The dataset has significant missing values in several variables:

1. **High Missing Values (>30%)**:
   - Sunshine: 47.7% missing
   - Cloud3pm: 40.1% missing
   - Evaporation: 42.8% missing
   - Cloud9am: 37.7% missing

2. **Moderate Missing Values (5-15%)**:
   - Pressure9am: 9.9% missing
   - Pressure3pm: 9.8% missing
   - WindGustDir: 6.6% missing
   - WindGustSpeed: 6.5% missing
   - WindDir9am: 7.0% missing

3. **Low Missing Values (<5%)**:
   - All other variables have less than 5% missing values

### Outliers
Several variables contain outliers that were capped during preprocessing:

1. **Rainfall**: Capped at 3.2mm (upper fence)
2. **Evaporation**: Capped at 21.8mm (upper fence)
3. **WindSpeed9am**: Capped at 55 km/h (upper fence)
4. **WindSpeed3pm**: Capped at 57 km/h (upper fence)

## Data Preprocessing Steps

### 1. Feature Engineering
- Extracted Year, Month, Day from Date variable
- Dropped original Date variable
- Dropped RISK_MM variable

### 2. Missing Value Imputation
- **Numerical variables**: Median imputation
- **Categorical variables**: Mode imputation

### 3. Outlier Treatment
- Applied IQR-based outlier capping for skewed variables
- Used 3×IQR rule for outlier detection

### 4. Feature Encoding
- Created dummy variables for all categorical variables
- Total features after encoding: ~118 features

### 5. Feature Scaling
- Applied standardization (z-score normalization)
- Used training set statistics for scaling

## Class Distribution

The dataset shows class imbalance:

- **No Rain Tomorrow**: 110,316 observations (77.6%)
- **Rain Tomorrow**: 31,877 observations (22.4%)

This imbalance is important to consider when evaluating model performance and may require techniques like:
- Stratified sampling
- Class weighting
- SMOTE (Synthetic Minority Oversampling Technique)
- Threshold tuning

## Geographic Coverage

The dataset covers 49 weather stations across Australia:

### Major Cities
- Sydney, Melbourne, Brisbane, Perth, Adelaide, Canberra, Hobart, Darwin

### Regional Locations
- Albury, Bendigo, Ballarat, Townsville, Cairns, Alice Springs, etc.

### Climate Zones
- Tropical (Darwin, Cairns)
- Subtropical (Brisbane, Sydney)
- Temperate (Melbourne, Adelaide)
- Mediterranean (Perth)
- Continental (Alice Springs)

## Temporal Coverage

- **Start Date**: 2007
- **End Date**: 2017
- **Duration**: 10 years
- **Seasonal Coverage**: All seasons represented
- **Data Frequency**: Daily observations

## Data Usage in Logistic Regression

### Target Variable
- **RainTomorrow**: Binary classification target (Yes/No)

### Feature Variables
- **Numerical**: Temperature, humidity, pressure, wind speed, rainfall, etc.
- **Categorical**: Location, wind direction, rain today status
- **Derived**: Date components (year, month, day)

### Model Input
- **Total Features**: ~118 (after dummy variable encoding)
- **Training Set**: 80% (113,754 observations)
- **Test Set**: 20% (28,439 observations)

## Data Quality Assessment

### Strengths
1. **Large Sample Size**: 142,193 observations provide robust statistical power
2. **Comprehensive Features**: Wide range of weather variables
3. **Geographic Diversity**: 49 locations across Australia
4. **Temporal Coverage**: 10 years of historical data
5. **Daily Frequency**: High-resolution temporal data

### Limitations
1. **Missing Values**: Significant missing data in some variables
2. **Class Imbalance**: Uneven distribution of target variable
3. **Geographic Bias**: Some locations may be overrepresented
4. **Temporal Gaps**: Potential gaps in data collection
5. **Measurement Errors**: Possible instrument errors or data entry mistakes

## Recommendations for Data Usage

1. **Handle Missing Values**: Use appropriate imputation strategies
2. **Address Class Imbalance**: Apply techniques to balance the dataset
3. **Feature Selection**: Consider dimensionality reduction techniques
4. **Cross-Validation**: Use stratified k-fold cross-validation
5. **Model Evaluation**: Use appropriate metrics for imbalanced data
6. **Regularization**: Apply L1/L2 regularization to prevent overfitting

This dataset provides a rich foundation for building a logistic regression model to predict rain tomorrow, with comprehensive weather features and sufficient historical data for robust model training and evaluation.
