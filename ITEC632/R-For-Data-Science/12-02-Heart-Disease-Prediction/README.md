# Heart Disease Prediction Project

## Project Description

This project implements a comprehensive machine learning analysis to predict heart disease risk based on various health and lifestyle factors. The project uses R programming language to perform exploratory data analysis, build multiple predictive models, and evaluate their performance.

## Objectives

- Conduct comprehensive exploratory data analysis (EDA)
- Identify key risk factors for heart disease
- Build and evaluate 6 different machine learning models
- Compare model performance and select best models
- Discuss ethical considerations in healthcare data mining

## Files Structure

```
12-02-Heart-Disease-Prediction/
├── README.md                          # This file
├── Heart-Disease-Prediction.R          # Main R script with complete analysis
├── Heart-Disease-Prediction.Rmd       # R Markdown file for reproducible analysis
├── Heart-Disease-Prediction.html      # Generated HTML report
├── Project-Report.md                  # Project report
├── Project-Presentation.md            # Project presentation
├── Project-Presentation-Manuscript.md # Presentation manuscript
├── .gitignore                         # Git ignore file
├── Heart-Disease-Dataset/
│   ├── heart_2020_cleaned.csv         # Dataset file
│   └── heart_2020_cleaned.md         # Dataset description
└── images/                            # Generated visualizations
    ├── target_distribution.png
    ├── correlation_heatmap.png
    ├── decision_tree.png
    ├── feature_importance.png
    └── roc_curves.png
```

## Dataset Description

The dataset used in this project is the Heart Disease Dataset 2020 (cleaned version). For detailed information about the dataset, please refer to `Heart-Disease-Dataset/heart_2020_cleaned.md`.

### Dataset Characteristics
- **Size**: 319,796 rows, 18 columns
- **Target Variable**: `HeartDisease` (Yes/No)
- **Class Distribution**: Imbalanced dataset
- **Preprocessing**: Dataset has been cleaned and preprocessed

### Key Variables
- Demographics: Age, Sex, Race, BMI
- Health Conditions: Diabetes, Stroke, Kidney Disease, Asthma
- Lifestyle Factors: Smoking, Alcohol Drinking, Physical Activity
- Health Metrics: Physical Health, Mental Health, Sleep Time

## How to Run the Project

### Prerequisites

Install required R packages:

```r
install.packages(c("tidyverse", "caret", "rpart", "randomForest", 
                   "e1071", "class", "nnet", "ROSE", "pROC", 
                   "corrplot", "VIM", "gridExtra", "knitr", "rmarkdown"))
```

### Running the Analysis

#### Option 1: Run R Script
```r
source("Heart-Disease-Prediction.R")
```

#### Option 2: Generate HTML Report
```r
rmarkdown::render("Heart-Disease-Prediction.Rmd")
```

### Project Tasks

1. **Task 0**: Data Loading and Initial Verification
2. **Task 1**: Exploratory Data Analysis
3. **Task 2**: Build and Evaluate 6 Predictive Models
4. **Task 3**: Model Comparison and Selection
5. **Task 4**: Ethical Considerations Discussion

## Project Components

### Main Analysis Files

- **Heart-Disease-Prediction.R**: Complete R script implementing all tasks
- **Heart-Disease-Prediction.Rmd**: R Markdown file for reproducible analysis
- **Heart-Disease-Prediction.html**: Generated HTML report

### Documentation Files

- **Project-Report.md**: Comprehensive project report
- **Project-Presentation.md**: Presentation slides
- **Project-Presentation-Manuscript.md**: Presentation manuscript

## Key Features

- **Comprehensive EDA**: Statistical analysis and visualizations
- **Multiple Models**: 6 different machine learning algorithms
- **Class Imbalance Handling**: SMOTE technique implementation
- **Model Evaluation**: Multiple metrics (Accuracy, Precision, Recall, F1, AUC-ROC)
- **Model Comparison**: Detailed comparison of all models
- **Ethical Analysis**: Discussion of ethical issues in healthcare data mining

## Results

The analysis identifies key risk factors for heart disease and evaluates multiple predictive models. The top-performing models are selected based on F1-Score and AUC-ROC metrics, with Random Forest and Logistic Regression showing the best performance.

## Ethical Considerations

This project addresses important ethical considerations in healthcare data mining:
- Patient data privacy and confidentiality
- Algorithmic bias and fairness
- Model interpretability for medical decisions
- Accountability for model predictions

## References

- CDC Heart Disease Risk Factors
- American Heart Association
- Behavioral Risk Factor Surveillance System (BRFSS) 2020

## Conclusion

This project demonstrates the application of machine learning techniques to healthcare prediction problems. The analysis provides insights into heart disease risk factors and evaluates multiple models for predictive performance. Ethical considerations are crucial for responsible deployment of such models in healthcare settings.

---

*This project is part of the R for Data Science curriculum focusing on machine learning applications in healthcare.*

