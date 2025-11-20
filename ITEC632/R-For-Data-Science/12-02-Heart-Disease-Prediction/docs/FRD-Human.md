(This prompted created by a Human)

# Update the report after running the code (R/Rmd)

File to update: `Project-Report.md`

Finish @Project-Report.md 

Make sure placeholders are filled with actual results 

3.4 Statistical Analysis
Correlation Analysis: [Results]
-> fill it 

Chi-Square Tests: [Results for categorical variables]
-> fill it 

T-Tests: [Results for numeric variables]
-> fill it 

4.5 Model Interpretation
Decision Tree Analysis:
[Describe the decision tree structure and key decision paths]
-> fill it 

Feature Importance:
[Discuss feature importance from Random Forest model]
-> fill it 

Variable Contribution:
[Explain how each of the top 5 variables contributes to predictions]
-> fill it 

Visualizations:

ROC curves comparison available in images/roc_curves.png
-> pls display the image in the .md file 

Feature importance plot available in images/feature_importance.png
-> pls display the image in the .md file 

Decision tree visualization available in images/decision_tree.png
-> pls display the image in the .md file 

7.1 Ethical Perspectives in Data Mining
[Discuss:

Privacy and Confidentiality
Informed Consent
Data Ownership
Bias and Fairness
Transparency and Explainability
Accountability
Beneficence and Non-maleficence]
-> pls fill it.
- discuss using the dataset and results in this project 
- discuss using general knowledge 

7.2 Ethical Issues in This Case Study
[Identify and discuss:

Data Privacy: Protection of sensitive health information
Model Bias: Potential bias against certain demographic groups
False Positives/Negatives: Impact of incorrect predictions
Model Interpretability: Need for explainable AI
Clinical Decision Support: Role of models vs. medical professionals
Data Quality: Impact on model reliability
Informed Consent: Use of patient data
Access and Equity: Ensuring fair access]
-> pls fill it.
- discuss using the dataset and results in this project 
- discuss using general knowledge 

7.3 Mitigation Strategies
[Suggest:

Methods to reduce bias
Approaches to improve transparency
Privacy-preserving techniques
Fairness evaluation methods]
-> pls fill it.
- discuss using the dataset and results in this project 
- discuss using general knowledge 




# Update the docs after running the code (R/Rmd)

Now we have run the R/Rmd scripts and we have actual data, visuals.

Pls use those data, visuals and update the docs 

@Project-Report.md 
@Project-Presentation.md 
@Project-Presentation-Manuscript.md 

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

- `README.md`: Project description and files structure
- `Heart-Disease-Prediction.R`: Main R script with complete analysis
- `Heart-Disease-Prediction.Rmd`: R Markdown file for reproducible analysis
- `Heart-Disease-Prediction.html`: Generated HTML report
- `Heart-Disease-Dataset/`: Dataset folder
- `images/`: Images folder
- `12-02-Heart-Disease-Prediction/Heart-Disease-Dataset/heart_2020_cleaned.md`: Dataset description 

Dataset: 
- `12-02-Heart-Disease-Prediction/Heart-Disease-Dataset/heart_2020_cleaned.csv`
- This dataset is cleaned and preprocessed.
- 319796 rows, 18 columns
- The target variable is `HeartDisease`
- The dataset is imbalanced.

Project instructions:

# Assessment Guide

## Purpose

The primary purpose of this project is to provide students with an opportunity to develop data mining skills for finding human-interpretable patterns that describe the data analysis skills. In this project, student will perform data cleaning/transformation, exploratory data analysis and cluster analysis on a dataset, build and evaluate predictive models, detect anomaly and find association between variables in the given datasets using a data mining tool (e.g. R). In this task students will also apply the ethical principles of data mining in the context of the case study.

Artefact: Project Report + Project Presentation + Project Code

## The Context

Heart disease is one of the leading causes of death for people of most races in the world. According to the CDC, about half of all Americans (47%) have at least 1 of 3 key risk factors for heart disease: high blood pressure, high cholesterol, and smoking. Other key indicators include diabetic status, obesity (high BMI), not getting enough physical activity or drinking too much alcohol. Detecting and preventing the factors that have the greatest impact on heart disease is very important in healthcare.


## Instructions

# Task 0 

Import the dataset into R.

# Task 1 Conduct an exploratory data analysis

Task 1 Conduct an exploratory data analysis of the data set using R to understand the characteristics of each variable and the relationship of each variable to the other variables in the data set. Summarise the findings of your exploratory data analysis in terms of describing key characteristics of each of the variables in the data set such as maximum, minimum values, average, standard deviation, most frequent values (mode), missing values and invalid values etc and relationships with other variables if relevant in a table.

Hint: Use descriptive statistical information and useful charts like Bar charts, Scatterplots etc. You might also like to look at running some correlations and chi square tests. Indicate in Task 1 Table which variables are contributing the most to determining the risk rating of heart disease.

Briefly discuss the key results of your exploratory data analysis and the justification for selecting your five top variables for predicting the risk of heart disease based on the results of your exploratory data analysis and a review of the relevant literature about assessing the risk of heart disease (About 250 words)

# Task 2 Build and evaluate two predictive models

Task 2 Build and evaluate N predictive models for determining the risk rating of heart disease using appropriate data mining models in R using two appropriate data mining methods you learned in this unit. 

We will use the following models:
- Logistic Regression
- Decision Tree
- Random Forest
- Support Vector Machine
- K-Nearest Neighbors
- Neural Network

Briefly explain your predictive model process, justify your choice of the data mining method, and discuss the results of predictive model drawing on the key outputs. This discussion should be based on the contribution of each of the top five variables to the Final Decision Tree Model and relevant supporting literature (at least 3 credible sources) on the interpretation of the selected data mining models (About 250 words).

# Task 3 Discuss and compare the accuracy of the two data mining models 

Task 3 Discuss and compare the accuracy of the two data mining models (methods). Use a table here to compare the key results of the confusion matrix (About 250 words).

Note the important outputs from your data mining analyses conducted in R should be included in your Project report to provide support for your conclusions regarding each analysis conducted. Export the important outputs from R as jpg image files and insert these screenshots in the relevant parts of your Project report.

# Task 4 Discuss ethical perspectives and issues in data mining

Task 4 Briefly discuss the ethical perspectives in data mining and identify the possible ethical issues in the context of this case study (250 words).


## Project Report

Filename: `Project-Report.md`

Structure of the Project Report:
- Introduction
- Dataset Description
- Exploratory Data Analysis
- Predictive Models
- Model Evaluation
- Model Comparison
- Ethical Issues
- References
- Appendices (if any)
- Key Findings

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

## Some Helpful Websites and Resources

ref. 
[Heart Disease Risk Factors](https://www.cdc.gov/heart-disease/risk-factors/?CDC_AAref_Val=https://www.cdc.gov/heartdisease/risk_factors.htm)


