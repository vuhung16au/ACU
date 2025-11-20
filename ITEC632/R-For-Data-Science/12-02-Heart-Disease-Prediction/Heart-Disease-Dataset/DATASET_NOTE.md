# Dataset Note

## Important: Dataset File Required

The dataset file `heart_2020_cleaned.csv` needs to be placed in this directory before running the analysis.

## Dataset Requirements

- **Filename**: `heart_2020_cleaned.csv`
- **Location**: `Heart-Disease-Dataset/heart_2020_cleaned.csv`
- **Expected Size**: 319,796 rows, 18 columns
- **Format**: CSV file with headers

## Dataset Source

The dataset should be the Heart Disease Dataset 2020 (cleaned version). If you need to obtain the dataset:

1. Check the original source (BRFSS 2020 data)
2. Ensure it has been cleaned and preprocessed
3. Verify it contains the 18 expected columns
4. Confirm the target variable is named `HeartDisease`

## Expected Columns

1. HeartDisease (target variable)
2. BMI
3. Smoking
4. AlcoholDrinking
5. Stroke
6. PhysicalHealth
7. MentalHealth
8. DiffWalking
9. Sex
10. AgeCategory
11. Race
12. Diabetic
13. PhysicalActivity
14. GenHealth
15. SleepTime
16. Asthma
17. KidneyDisease
18. SkinCancer

## Verification

After placing the dataset file, you can verify it by running:

```r
data <- read.csv("Heart-Disease-Dataset/heart_2020_cleaned.csv")
dim(data)  # Should be 319796 x 18
str(data)  # Check structure
```

---

*Note: The actual dataset file is not included in the repository due to size constraints. Please obtain the dataset separately.*

