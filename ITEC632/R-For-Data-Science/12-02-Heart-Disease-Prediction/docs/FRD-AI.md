Based on the `Prompt-Heart-Human.md` file, the following file is suggested and updated by Cursor.

# Implement Heart Disease Prediction in R

Folder Structure:
12-02-Heart-Disease-Prediction/
├── README.md
├── Heart-Disease-Prediction.R
├── Heart-Disease-Prediction.Rmd
├── Heart-Disease-Prediction.html
├── Heart-Disease-Dataset/
│   └── heart_2020_cleaned.csv
│   └── heart_2020_cleaned.md
└── images/
    └── heart_disease_prediction.png
- `Project-Report.md`: Project report
- `Project-Presentation.md`: Project presentation
- `Project-Presentation-Manuscript.md`: Project presentation manuscript
- .gitignore: Git ignore file

- `README.md`: Project description and files structure
- `Heart-Disease-Prediction.R`: Main R script with complete analysis
- `Heart-Disease-Prediction.Rmd`: R Markdown file for reproducible analysis
- `Heart-Disease-Prediction.html`: Generated HTML report
- `Heart-Disease-Dataset/`: Dataset folder
- `images/`: Images folder
- `12-02-Heart-Disease-Prediction/Heart-Disease-Dataset/heart_2020_cleaned.md`: Dataset description 

`README.md` structure:
- Project description
- Files structure
- Dataset description -> refer to `Heart-Disease-Dataset/heart_2020_cleaned.md`
- How to run the project
- Conclusion

## Dataset Information

- **File**: `12-02-Heart-Disease-Prediction/Heart-Disease-Dataset/heart_2020_cleaned.csv`
- **Source**: Heart Disease Dataset 2020 (cleaned and preprocessed version)
- **Size**: 319,796 rows, 18 columns
- **Target Variable**: `HeartDisease` (binary classification: Yes/No)
- **Class Imbalance**: The dataset is imbalanced - this must be addressed in model development
- **Preprocessing**: Dataset has been cleaned and preprocessed, but students should verify data quality and handle any remaining issues
- **Data Dictionary**: See `Heart-Disease-Dataset/heart_2020_cleaned.md` for detailed column descriptions

Project instructions:

# Assessment Guide

## Purpose

The primary purpose of this project is to provide students with an opportunity to develop data mining skills for finding human-interpretable patterns that describe the data analysis skills. In this project, student will perform data cleaning/transformation, exploratory data analysis and cluster analysis on a dataset, build and evaluate predictive models, detect anomaly and find association between variables in the given datasets using R. In this task students will also apply the ethical principles of data mining in the context of the case study.

Artefact: Project Report + Project Presentation + Project Code

## The Context

Heart disease is one of the leading causes of death for people of most races in the world. According to the CDC, about half of all Americans (47%) have at least 1 of 3 key risk factors for heart disease: high blood pressure, high cholesterol, and smoking. Other key indicators include diabetic status, obesity (high BMI), not getting enough physical activity or drinking too much alcohol. Detecting and preventing the factors that have the greatest impact on heart disease is very important in healthcare.

## Prerequisites

### Required R Packages
Students should install and load the following R packages:
- `tidyverse` - Data manipulation and visualization
- `caret` - Classification and regression training
- `rpart` - Decision trees
- `randomForest` - Random forest models
- `e1071` - Support Vector Machines
- `class` - K-Nearest Neighbors
- `nnet` or `neuralnet` - Neural networks
- `ROSE` or `DMwR` - Handling class imbalance (SMOTE)
- `pROC` - ROC curve analysis
- `corrplot` - Correlation visualization
- `VIM` - Missing value visualization
- `knitr` - Report generation
- `rmarkdown` - R Markdown processing

### Code Quality Requirements
- All code must be well-documented with comments
- Use consistent coding style and naming conventions
- Set random seeds (`set.seed()`) for reproducibility
- Include error handling where appropriate
- Code should be organized into logical sections
- All scripts must run without errors

### Reproducibility Requirements
- Set random seeds before any random operations
- Document R version and package versions used
- Ensure all paths are relative or clearly documented
- Include session information in reports

## Instructions

# Task 0: Data Loading and Initial Verification

**Objective**: Load the dataset into R and perform initial data verification.

**Requirements**:
1. Import the dataset `heart_2020_cleaned.csv` into R
2. Display the structure of the dataset (`str()`, `summary()`)
3. Check for missing values and report their distribution
4. Verify the target variable (`HeartDisease`) distribution
5. Display basic summary statistics for all variables
6. Check data types and convert if necessary
7. Report the class imbalance ratio (positive:negative cases)

**Deliverables**:
- Code for data loading and verification
- Summary table of missing values
- Target variable distribution plot
- Initial data quality assessment

# Task 1: Exploratory Data Analysis (EDA)

**Objective**: Conduct a comprehensive exploratory data analysis to understand the dataset characteristics and identify key predictors of heart disease.

## Requirements

### 1.1 Descriptive Statistics
- Calculate and summarize key characteristics for each variable:
  - **Numerical variables**: mean, median, standard deviation, minimum, maximum, quartiles, skewness
  - **Categorical variables**: frequency counts, mode, proportions
- Create a comprehensive summary table with all variables
- Identify and document any missing values or invalid entries
- Check for outliers and unusual patterns

### 1.2 Visualizations (Minimum Requirements)
Create at least the following visualizations:
- **Distribution plots**: Histograms or density plots for numerical variables
- **Bar charts**: Frequency distributions for categorical variables
- **Box plots**: Distribution comparison by target variable (HeartDisease)
- **Correlation heatmap**: Correlation matrix for numerical variables
- **Scatterplots**: Relationships between key numerical variables and target
- **Chi-square test visualizations**: Association between categorical variables and target

### 1.3 Statistical Analysis
- **Correlation analysis**: Calculate correlation coefficients between numerical variables and target
- **Chi-square tests**: Test associations between categorical variables and HeartDisease
- **T-tests or ANOVA**: Compare numerical variables across target classes
- Identify variables with correlation > 0.3 (or < -0.3) with target variable
- Document statistical significance (p < 0.05)

### 1.4 Variable Selection
- Rank variables by their contribution to predicting heart disease risk
- Select **top 5 variables** based on:
  - Statistical significance
  - Correlation strength
  - Clinical/medical relevance (supported by literature)
  - Practical importance
- Create a table indicating which variables contribute most to determining heart disease risk

### 1.5 Discussion (Approximately 250 words)
Briefly discuss:
- Key findings from the exploratory data analysis
- Justification for selecting the five top variables
- Support your variable selection with:
  - Results from statistical tests
  - Correlation analysis findings
  - Review of relevant medical/healthcare literature on heart disease risk factors
  - Clinical relevance of selected variables

**Deliverables**:
- Comprehensive EDA summary table
- All required visualizations (saved as PNG/JPG, minimum 300 DPI)
- Statistical test results
- Top 5 variables selection with justification
- Written discussion (250 words)

# Task 2: Build and Evaluate Predictive Models

**Objective**: Build and evaluate multiple predictive models for determining heart disease risk using various machine learning algorithms in R.

## Requirements

### 2.1 Data Preparation
- **Data Splitting**: Split dataset into training (70%) and testing (30%) sets
- **Stratified Sampling**: Ensure class distribution is maintained in train/test splits
- **Set Random Seed**: Use `set.seed()` for reproducibility
- **Feature Selection**: Use the top 5 variables identified in Task 1
- **Feature Engineering**: Create any necessary derived features or transformations

### 2.2 Handle Class Imbalance
Since the dataset is imbalanced, implement at least one of the following techniques:
- **SMOTE** (Synthetic Minority Oversampling Technique) using `ROSE` or `DMwR` package
- **Class Weights**: Adjust class weights in model training
- **Undersampling**: Random undersampling of majority class
- **Combined Approach**: Combination of oversampling and undersampling
- Document the imbalance ratio before and after treatment
- Justify your choice of technique

### 2.3 Build Multiple Models
Build and train **all 6** of the following models in R:

1. **Logistic Regression** (`glm()` or `caret::train()`)
2. **Decision Tree** (`rpart()` or `caret::train()`)
3. **Random Forest** (`randomForest()` or `caret::train()`)
4. **Support Vector Machine** (`svm()` from `e1071` or `caret::train()`)
5. **K-Nearest Neighbors** (`knn()` from `class` or `caret::train()`)
6. **Neural Network** (`nnet()` or `neuralnet()` or `caret::train()`)

### 2.4 Model Training Requirements
- Use appropriate hyperparameter tuning (grid search, random search, or default parameters)
- Implement cross-validation (k-fold, preferably 10-fold) for model validation
- Train each model on the training set
- Document hyperparameters used for each model
- Save trained models for later use

### 2.5 Model Evaluation
For each model, calculate and report:
- **Confusion Matrix**: True Positives, True Negatives, False Positives, False Negatives
- **Accuracy**: Overall classification accuracy
- **Precision**: Positive predictive value
- **Recall (Sensitivity)**: True positive rate
- **Specificity**: True negative rate
- **F1-Score**: Harmonic mean of precision and recall
- **AUC-ROC**: Area Under the ROC Curve (important for imbalanced data)
- **Precision-Recall Curve**: Alternative to ROC for imbalanced data
- **Feature Importance**: For tree-based models (Decision Tree, Random Forest)

### 2.6 Model Interpretation
- **Decision Tree Visualization**: Visualize the final decision tree model
- **Feature Importance Analysis**: Rank and visualize top 5 variables' contribution to the Decision Tree model
- **Variable Contribution**: Explain how each of the top 5 variables contributes to predictions
- **Model Comparison Table**: Create a table comparing all 6 models' performance metrics

### 2.7 Discussion (Approximately 250 words)
Briefly explain:
- Your predictive modeling process and methodology
- Justification for your choice of class imbalance handling technique
- Key findings from model training and evaluation
- Discussion of the top 5 variables' contribution to the Decision Tree model
- Interpretation of model results with reference to:
  - At least 3 credible academic/medical sources
  - Clinical relevance of findings
  - Model interpretability and practical implications

**Deliverables**:
- All 6 trained models (code and saved models)
- Model evaluation metrics table for all models
- Confusion matrices for all models
- ROC curves and/or Precision-Recall curves
- Decision tree visualization
- Feature importance plots
- Written discussion (250 words)

# Task 3: Model Comparison and Selection

**Objective**: Compare the performance of all predictive models and identify the best-performing models.

## Requirements

### 3.1 Comprehensive Model Comparison
- Create a **comparison table** including all 6 models with the following metrics:
  - Accuracy
  - Precision
  - Recall (Sensitivity)
  - Specificity
  - F1-Score
  - AUC-ROC
  - Training Time (optional but recommended)
  - Model Complexity/Interpretability

### 3.2 Select Top 2 Models
- Based on the comparison, select the **top 2 performing models**
- Selection criteria should consider:
  - Overall performance (F1-Score and AUC-ROC are most important for imbalanced data)
  - Balance between precision and recall
  - Model interpretability
  - Computational efficiency
  - Clinical/practical applicability

### 3.3 Detailed Comparison of Top 2 Models
- Create detailed confusion matrices for the top 2 models
- Generate side-by-side ROC curves for the top 2 models
- Compare Precision-Recall curves
- Statistical comparison (if applicable): Use McNemar's test or other appropriate tests
- Visual comparison: Create comparative visualizations

### 3.4 Discussion (Approximately 250 words)
Discuss:
- Performance comparison of all models
- Justification for selecting the top 2 models
- Detailed comparison of the top 2 models' performance
- Strengths and weaknesses of each top model
- Which model performs better and why
- Trade-offs between model accuracy, interpretability, and complexity
- Recommendations for model deployment

### 3.5 Visualization Requirements
- **Comparison Table**: Well-formatted table comparing all models
- **Confusion Matrices**: Visual confusion matrices for top 2 models
- **ROC Curves**: Overlaid ROC curves for top 2 models
- **Performance Metrics Bar Chart**: Visual comparison of key metrics
- **Model Selection Justification**: Visual summary of selection criteria

**Note**: All important outputs from R analyses should be exported as high-quality image files (PNG or JPG format, minimum 300 DPI) and included in the Project Report to support conclusions.

**Deliverables**:
- Comprehensive model comparison table (all 6 models)
- Detailed comparison of top 2 models
- All required visualizations (high-resolution images)
- Written discussion (250 words)

# Task 4: Ethical Perspectives and Issues in Data Mining

**Objective**: Discuss ethical considerations in data mining and identify potential ethical issues specific to heart disease prediction.

## Requirements

### 4.1 Ethical Frameworks
Discuss ethical perspectives in data mining, including but not limited to:
- **Privacy and Confidentiality**: Patient data protection, HIPAA/GDPR considerations
- **Informed Consent**: Use of patient data for research/prediction
- **Data Ownership**: Who owns the data and predictions
- **Bias and Fairness**: Algorithmic bias, demographic fairness
- **Transparency and Explainability**: Model interpretability for medical decisions
- **Accountability**: Responsibility for model predictions and errors
- **Beneficence and Non-maleficence**: Do good, do no harm

### 4.2 Ethical Issues in This Case Study
Identify and discuss specific ethical issues relevant to heart disease prediction:
- **Data Privacy**: Protection of sensitive health information
- **Model Bias**: Potential bias against certain demographic groups
- **False Positives/Negatives**: Impact of incorrect predictions on patient health
- **Model Interpretability**: Need for explainable AI in healthcare decisions
- **Clinical Decision Support**: Role of models vs. medical professionals
- **Data Quality**: Impact of data quality on model reliability
- **Informed Consent**: Use of patient data without explicit consent
- **Access and Equity**: Ensuring fair access to predictive healthcare tools

### 4.3 Mitigation Strategies
Briefly suggest potential mitigation strategies for identified ethical issues:
- Methods to reduce bias
- Approaches to improve transparency
- Privacy-preserving techniques
- Fairness evaluation methods

### 4.4 Discussion (Approximately 250 words)
Write a comprehensive discussion covering:
- General ethical perspectives in data mining
- Specific ethical issues in the heart disease prediction context
- Potential consequences of ethical violations
- Recommendations for ethical data mining practices in healthcare

**Deliverables**:
- Written discussion (250 words)
- Identification of at least 3-5 specific ethical issues
- Brief mitigation strategies for each identified issue


## Evaluation Criteria

### Code Quality (20%)
- Code runs without errors
- Well-documented with clear comments
- Follows consistent coding style
- Proper use of functions and modularity
- Reproducible (set.seed, version control)

### Exploratory Data Analysis (20%)
- Comprehensive descriptive statistics
- Appropriate visualizations (minimum requirements met)
- Statistical tests conducted and interpreted correctly
- Top 5 variables selected with clear justification
- Quality of written discussion

### Model Development (25%)
- All 6 models implemented correctly
- Appropriate handling of class imbalance
- Proper data splitting and cross-validation
- Hyperparameter tuning (if applicable)
- Model evaluation metrics calculated correctly

### Model Comparison (15%)
- Comprehensive comparison of all models
- Clear selection of top 2 models
- Detailed comparison with appropriate visualizations
- Quality of analysis and discussion

### Ethical Discussion (10%)
- Understanding of ethical issues demonstrated
- Relevant ethical issues identified
- Appropriate mitigation strategies suggested
- Quality of written discussion

### Report and Presentation Quality (10%)
- Professional presentation
- Clear structure and organization
- Appropriate use of visualizations
- Quality of writing and communication

## Submission Requirements

### File Structure
All files must follow the specified folder structure:
```
12-02-Heart-Disease-Prediction/
├── README.md
├── Heart-Disease-Prediction.R
├── Heart-Disease-Prediction.Rmd
├── Heart-Disease-Prediction.html
├── Project-Report.md
├── Project-Presentation.md
├── Project-Presentation-Manuscript.md
├── Heart-Disease-Dataset/
│   ├── heart_2020_cleaned.csv
│   └── heart_2020_cleaned.md
└── images/
    └── [all visualization files]
```

### Deliverables Checklist
- [ ] All R scripts run without errors
- [ ] R Markdown file renders successfully to HTML
- [ ] All visualizations are clear, labeled, and high-resolution (300+ DPI)
- [ ] Code is well-documented with comments
- [ ] Project Report is complete with all required sections
- [ ] Project Presentation is prepared
- [ ] Project Presentation Manuscript is ready
- [ ] All files follow naming conventions
- [ ] README.md includes project description and instructions

### Code Requirements
- All code must be executable and reproducible
- Include `sessionInfo()` or package version information
- Set random seeds for reproducibility
- Use relative paths or clearly document absolute paths
- Include error handling where appropriate

### Report Requirements
- Professional formatting and presentation
- All figures and tables properly labeled and referenced
- Citations and references in appropriate format
- Clear, concise writing
- All required sections included

## Project Report

Filename: `Project-Report.md`

Structure of the Project Report:
1. **Introduction**
   - Project overview and objectives
   - Context and motivation
   - Scope of the analysis

2. **Dataset Description**
   - Dataset source and characteristics
   - Variable descriptions
   - Data quality assessment
   - Class imbalance discussion

3. **Exploratory Data Analysis**
   - Descriptive statistics
   - Visualizations
   - Statistical tests
   - Top 5 variables selection and justification

4. **Predictive Models**
   - Data preparation and preprocessing
   - Class imbalance handling
   - Model training process
   - All 6 models: methodology and results
   - Model interpretation (Decision Tree focus)

5. **Model Evaluation**
   - Evaluation metrics for all models
   - Confusion matrices
   - ROC curves and Precision-Recall curves
   - Feature importance analysis

6. **Model Comparison**
   - Comprehensive comparison table
   - Top 2 models selection
   - Detailed comparison and analysis
   - Model recommendations

7. **Ethical Issues**
   - Ethical perspectives in data mining
   - Specific ethical issues in this case study
   - Mitigation strategies

8. **Key Findings**
   - Summary of main findings
   - Key insights and conclusions
   - Practical implications

9. **References**
   - At least 3 credible academic/medical sources
   - Proper citation format

10. **Appendices** (if any)
    - Additional tables or figures
    - Extended code snippets
    - Additional analysis results

## Project Presentation

Filename: `Project-Presentation.md`

Structure of the Project Presentation:

- Introduction
- Dataset Description
- Exploratory Data Analysis
- Key Findings
- Conclusion
- Further Work

Notes: Only use bullet points in the presentation. 

## Project Presentation manuscript

Filename: `Project-Presentation-Manuscript.md`

This is the manuscript for the project presentation.

Requirements: The presentation must less than 5 minutes speaking time.

## Helpful Resources

### Dataset and Medical Information
- [CDC - Heart Disease Risk Factors](https://www.cdc.gov/heart-disease/risk-factors/)
- [American Heart Association - Heart Disease](https://www.heart.org/en/health-topics/consumer-healthcare/what-is-cardiovascular-disease)
- [WHO - Cardiovascular Diseases](https://www.who.int/health-topics/cardiovascular-diseases)

### R Programming Resources
- [R Documentation](https://www.r-project.org/help.html)
- [CRAN Task View: Machine Learning](https://cran.r-project.org/web/views/MachineLearning.html)
- [caret Package Documentation](https://topepo.github.io/caret/)
- [R Markdown Guide](https://rmarkdown.rstudio.com/lesson-1.html)

### Machine Learning in Healthcare
- [Machine Learning in Healthcare - Nature](https://www.nature.com/articles/s41591-019-0641-6)
- [Ethical AI in Healthcare](https://www.who.int/publications/i/item/9789240029200)

### Data Mining Ethics
- [ACM Code of Ethics](https://www.acm.org/code-of-ethics)
- [Data Mining Ethics Guidelines](https://www.kdd.org/ethics-in-data-mining)

### Visualization Best Practices
- [R Graphics Cookbook](https://r-graphics.org/)
- [ggplot2 Documentation](https://ggplot2.tidyverse.org/)

## Important Notes

1. **Image Quality**: All exported images should be in PNG or JPG format with minimum 300 DPI resolution for clarity in reports. Images has WHITE BACKGROUND.
2. **Reproducibility**: Always set random seeds before random operations to ensure reproducible results
3. **Code Documentation**: Comment your code thoroughly to explain your methodology
4. **Model Selection**: Focus on F1-Score and AUC-ROC for imbalanced datasets rather than accuracy alone
5. **Ethical Considerations**: Consider the real-world implications of your models in healthcare settings

