# Heart Disease Dataset 2020 - Cleaned Version

## Dataset Overview

**File**: `heart_2020_cleaned.csv`  
**Source**: Heart Disease Dataset 2020 (cleaned and preprocessed)  
**Size**: 319,796 rows, 18 columns  
**Target Variable**: `HeartDisease` (binary: Yes/No)  
**Class Distribution**: Imbalanced dataset

## Dataset Description

This dataset contains information about heart disease risk factors from the 2020 Behavioral Risk Factor Surveillance System (BRFSS). The dataset has been cleaned and preprocessed for analysis.

## Variables Description

### Target Variable
- **HeartDisease**: Binary target variable indicating presence of heart disease
  - Values: "Yes", "No"
  - Class imbalance: Majority class is "No"

### Predictor Variables (17 features)

#### Demographics
- **BMI**: Body Mass Index (numeric)
- **Smoking**: Smoking status (Yes/No)
- **AlcoholDrinking**: Alcohol consumption status (Yes/No)
- **Stroke**: History of stroke (Yes/No)
- **PhysicalHealth**: Physical health status (numeric, days)
- **MentalHealth**: Mental health status (numeric, days)
- **DiffWalking**: Difficulty walking (Yes/No)
- **Sex**: Gender (Male/Female)
- **AgeCategory**: Age group (categorical)
- **Race**: Race/ethnicity (categorical)
- **Diabetic**: Diabetic status (Yes/No/No, borderline diabetes/Yes, during pregnancy)

#### Health Conditions
- **PhysicalActivity**: Physical activity status (Yes/No)
- **GenHealth**: General health status (Excellent/Very good/Good/Fair/Poor)
- **SleepTime**: Average hours of sleep (numeric)
- **Asthma**: Asthma status (Yes/No)
- **KidneyDisease**: Kidney disease status (Yes/No)
- **SkinCancer**: Skin cancer status (Yes/No)

## Data Quality Notes

- Dataset has been cleaned and preprocessed
- Missing values have been handled (verify in analysis)
- Categorical variables are encoded appropriately
- Numeric variables are scaled/normalized where applicable
- Class imbalance exists - requires appropriate handling techniques

## Usage Notes

- Always check for remaining missing values
- Verify data types are correct for analysis
- Address class imbalance before model training
- Consider feature engineering based on domain knowledge
- Validate data quality before proceeding with analysis

## References

- Behavioral Risk Factor Surveillance System (BRFSS) 2020
- CDC Heart Disease Data and Statistics
- American Heart Association Risk Factors

