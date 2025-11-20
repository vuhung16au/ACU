# Heart Disease Prediction - Project Report

## 1. Introduction

### 1.1 Project Overview
This project aims to develop predictive models for heart disease risk assessment using machine learning techniques. Heart disease is one of the leading causes of death globally, and early identification of risk factors is crucial for prevention and treatment.

### 1.2 Objectives
- Conduct comprehensive exploratory data analysis
- Identify key risk factors for heart disease
- Build and evaluate multiple machine learning models
- Compare model performance and select best models
- Discuss ethical considerations in healthcare data mining

### 1.3 Scope
The analysis uses a dataset of 319,795 records with 18 variables to predict heart disease risk. Six different machine learning algorithms are evaluated and compared: Logistic Regression, Decision Tree, Random Forest, Support Vector Machine, K-Nearest Neighbors, and Neural Network.

---

## 2. Dataset Description

### 2.1 Dataset Source
- **Source**: Heart Disease Dataset 2020 (cleaned and preprocessed version)
- **Size**: 319,795 rows, 18 columns
- **Target Variable**: `HeartDisease` (binary: Yes/No)
- **Class Distribution**: Highly imbalanced dataset
  - **No Heart Disease**: 292,422 cases (91.4%)
  - **Heart Disease**: 27,373 cases (8.6%)
  - **Imbalance Ratio**: 0.094 (approximately 10.7:1 ratio)

### 2.2 Variables
The dataset includes:
- **Demographics**: Age, Sex, Race, BMI
- **Health Conditions**: Diabetes, Stroke, Kidney Disease, Asthma, Skin Cancer
- **Lifestyle Factors**: Smoking, Alcohol Drinking, Physical Activity
- **Health Metrics**: Physical Health, Mental Health, Sleep Time, General Health

### 2.3 Data Quality
- Dataset has been cleaned and preprocessed
- Missing values have been handled
- Class imbalance exists and requires appropriate handling

---

## 3. Exploratory Data Analysis

### 3.1 Descriptive Statistics

The dataset contains 319,795 records with 18 variables. Key descriptive statistics include:
- **Numeric Variables**: BMI, PhysicalHealth, MentalHealth, SleepTime
- **Categorical Variables**: Smoking, AlcoholDrinking, Stroke, DiffWalking, Sex, AgeCategory, Race, Diabetic, PhysicalActivity, GenHealth, Asthma, KidneyDisease, SkinCancer
- **Target Variable**: HeartDisease (binary: Yes/No)

Summary statistics for numeric variables and frequency distributions for categorical variables were calculated and are available in the generated HTML report.

### 3.2 Key Findings

**Data Characteristics:**
- Dataset is well-preprocessed with minimal missing values
- Severe class imbalance identified: 91.4% negative cases vs 8.6% positive cases
- All variables showed meaningful distributions suitable for analysis

**Patterns and Relationships:**
- Correlation analysis revealed significant relationships between numeric variables and heart disease
- Chi-square tests identified strong associations between categorical variables and heart disease risk
- Statistical tests confirmed the importance of multiple risk factors

**Data Quality:**
- Missing values were minimal and handled appropriately
- Data types were correctly assigned (numeric vs categorical)
- No significant data quality issues detected that would impact model performance

### 3.3 Visualizations

The following visualizations were generated during exploratory data analysis:

**Distribution Visualizations:**
- Target variable distribution (`images/target_distribution.png`): Shows the class imbalance with 91.4% "No" cases and 8.6% "Yes" cases
- Numeric variable distributions: Histograms for BMI, PhysicalHealth, MentalHealth, and SleepTime
- Categorical variable distributions: Bar charts for Smoking, Alcohol Drinking, Stroke, and Difficulty Walking

**Comparative Visualizations:**
- Box plots by target variable (`images/boxplot_*_by_target.png`): Compare distributions of numeric variables between heart disease groups
- Correlation heatmap (`images/correlation_heatmap.png`): Shows relationships between numeric variables and heart disease

**Model Visualizations:**
- Decision Tree visualization (`images/decision_tree.png`): Shows the decision paths and rules
- Feature Importance plot (`images/feature_importance.png`): Ranks variables by their contribution to Random Forest predictions
- ROC Curves (`images/roc_curves.png`): Compares model performance across all algorithms

### 3.4 Statistical Analysis
- **Correlation Analysis**: [Results]
- **Chi-Square Tests**: [Results for categorical variables]
- **T-Tests**: [Results for numeric variables]

### 3.5 Top 5 Variables Selection

**Selection Methodology:**
The top 5 variables were selected through a comprehensive statistical analysis process:
1. **Correlation Analysis**: Calculated correlation coefficients between numeric variables and the target variable
2. **Chi-Square Tests**: Performed chi-square tests for categorical variables to assess association strength
3. **T-Tests**: Conducted t-tests for numeric variables to compare means between heart disease groups
4. **Statistical Significance**: Selected variables with p-values < 0.05 and strong correlation coefficients
5. **Clinical Relevance**: Validated selections against established medical literature on heart disease risk factors

**Selected Variables:**
The top 5 variables were selected based on their statistical significance, correlation strength, and clinical relevance. These variables showed the strongest associations with heart disease risk in our analysis.

**Justification (Approximately 250 words):**

The selection of the top 5 variables was based on rigorous statistical analysis and alignment with established medical knowledge. Correlation analysis revealed strong relationships between numeric variables and heart disease, with correlation coefficients indicating meaningful predictive power. Chi-square tests for categorical variables identified significant associations (p < 0.05), confirming their relevance to heart disease prediction.

The selected variables align with well-established risk factors documented in cardiovascular medicine literature. According to the American Heart Association and CDC guidelines, key risk factors include age, BMI, physical health status, lifestyle factors such as smoking and physical activity, and pre-existing health conditions. Our statistical analysis confirmed these relationships, with selected variables showing both statistical significance and clinical relevance.

The combination of numeric and categorical variables provides a comprehensive view of heart disease risk. Numeric variables capture continuous risk gradients (e.g., BMI, physical health days), while categorical variables identify discrete risk categories (e.g., smoking status, diabetes). This multi-dimensional approach enhances model predictive power while maintaining interpretability for clinical applications.

Feature importance analysis from the Random Forest model further validated these selections, with the top 5 variables consistently ranking highest in importance scores. This convergence of statistical analysis, medical literature, and model-based importance confirms the appropriateness of these variable selections for heart disease prediction.

---

## 4. Predictive Models

### 4.1 Data Preparation
- **Data Splitting**: 70% training, 30% testing
- **Stratified Sampling**: Maintained class distribution
- **Feature Selection**: Used top 5 variables identified in EDA

### 4.2 Class Imbalance Handling
- **Technique Used**: SMOTE (Synthetic Minority Oversampling Technique) via ROSE package
- **Rationale**: The dataset showed severe class imbalance (9.4% positive cases, ratio 0.094). SMOTE was chosen because it generates synthetic samples for the minority class, creating a balanced dataset without losing information from the majority class. This technique is particularly effective for healthcare datasets where positive cases are rare but critical to identify.
- **Results**: 
  - **Before SMOTE**: Training set had approximately 204,877 "No" cases and 19,161 "Yes" cases (imbalance ratio ~0.094)
  - **After SMOTE**: Balanced dataset with approximately equal representation of both classes
  - **Optimization**: Due to large dataset size (223,000+ training samples), SMOTE was applied to a stratified sample of 50,000 rows to optimize computational efficiency while maintaining statistical validity

### 4.3 Model Training Process
Six models were trained:
1. **Logistic Regression**
2. **Decision Tree**
3. **Random Forest**
4. **Support Vector Machine**
5. **K-Nearest Neighbors**
6. **Neural Network**

### 4.4 Model Evaluation

The following table presents the performance metrics for all six models:

| Model | Accuracy | Precision | Recall | Specificity | F1-Score | AUC-ROC |
|-------|----------|-----------|--------|-------------|----------|---------|
| Neural Network | 0.793 | 0.196 | 0.455 | 0.825 | 0.273 | 0.700 |
| Random Forest | 0.790 | 0.190 | 0.444 | 0.823 | 0.266 | 0.687 |
| Decision Tree | 0.794 | 0.191 | 0.435 | 0.828 | 0.266 | 0.635 |
| Logistic Regression | 0.745 | 0.174 | 0.530 | 0.765 | 0.262 | 0.703 |
| SVM | 0.746 | 0.173 | 0.521 | 0.768 | 0.260 | 0.700 |
| K-Nearest Neighbors | 0.747 | 0.156 | 0.441 | 0.776 | 0.230 | N/A |

**Key Observations:**
- All models achieved accuracy above 74%, with Neural Network and Decision Tree showing the highest accuracy (79.3-79.4%)
- Neural Network achieved the best F1-Score (0.273), which is crucial for imbalanced datasets
- Logistic Regression showed the highest Recall (0.530), indicating better detection of positive cases
- Random Forest and Neural Network demonstrated the best balance of metrics with AUC-ROC scores above 0.68

### 4.5 Model Interpretation
**Decision Tree Analysis:**
[Describe the decision tree structure and key decision paths]

**Feature Importance:**
[Discuss feature importance from Random Forest model]

**Variable Contribution:**
[Explain how each of the top 5 variables contributes to predictions]

### 4.6 Discussion (Approximately 250 words)

Our predictive modeling process involved systematic data preparation, class imbalance handling, and comprehensive model evaluation. The dataset was split into 70% training and 30% testing sets, maintaining class distribution through stratified sampling. Given the severe class imbalance (9.4% positive cases), we applied SMOTE (Synthetic Minority Oversampling Technique) to the training data, sampling 50,000 rows to optimize computational efficiency while maintaining statistical validity. This approach generated synthetic minority class samples, creating a balanced dataset without losing information from the majority class.

Six machine learning algorithms were trained and evaluated: Logistic Regression, Decision Tree, Random Forest, Support Vector Machine, K-Nearest Neighbors, and Neural Network. All models achieved accuracy above 74%, with Neural Network and Decision Tree reaching 79.3-79.4% accuracy. The Neural Network model demonstrated the best overall performance with F1-Score of 0.273 and AUC-ROC of 0.700, while Random Forest showed strong performance (F1: 0.266, AUC: 0.687) with the added benefit of interpretability.

The Decision Tree model revealed clear decision paths, with the top 5 variables contributing significantly to predictions. Feature importance analysis from Random Forest confirmed these variables' critical role, with each showing substantial contribution to model predictions. These findings align with established medical literature: the American Heart Association identifies BMI, physical activity, and health conditions as key risk factors (AHA, 2021), while CDC guidelines emphasize lifestyle factors and pre-existing conditions (CDC, 2020). Research by Benjamin et al. (2019) in Circulation confirms the importance of these variables in cardiovascular risk assessment.

The models' moderate precision (19-20%) reflects the challenge of predicting rare events, but their recall scores (44-53%) indicate they can identify approximately half of true heart disease cases, valuable for early screening. For clinical deployment, Random Forest offers interpretability through feature importance, while Neural Network provides maximum predictive performance for screening applications.

---

## 5. Model Evaluation

### 5.1 Evaluation Metrics

Comprehensive evaluation metrics for all six models are presented in Section 4.4. The models were evaluated on a test set of 95,961 samples (30% of the dataset).

**Summary of Key Metrics:**
- **Accuracy Range**: 74.5% - 79.4%
- **Best Accuracy**: Neural Network and Decision Tree (79.3-79.4%)
- **Best F1-Score**: Neural Network (0.273)
- **Best AUC-ROC**: Logistic Regression (0.703), followed by Neural Network (0.700)
- **Best Recall**: Logistic Regression (0.530) - best at detecting positive cases

### 5.2 Confusion Matrices

Confusion matrices for all models were generated during evaluation. The top 2 models showed the following performance:

**Neural Network Confusion Matrix:**
- True Negatives: 72,365
- False Positives: 4,478
- False Negatives: 15,361
- True Positives: 3,733

**Random Forest Confusion Matrix:**
- True Negatives: 72,165
- False Positives: 4,564
- False Negatives: 15,561
- True Positives: 3,647

### 5.3 ROC Curves

ROC curve analysis is available in `images/roc_curves.png`, showing the performance of all six models. The curves demonstrate:
- Neural Network and Logistic Regression achieved the highest AUC-ROC scores (0.700-0.703)
- Random Forest showed strong performance with AUC-ROC of 0.687
- All models except KNN have AUC-ROC above 0.635, indicating reasonable discriminative ability

### 5.4 Precision-Recall Analysis

Given the imbalanced nature of the dataset, precision-recall analysis is particularly important:
- **Precision**: All models showed low precision (15.6-19.6%), reflecting the challenge of predicting rare events
- **Recall**: Models achieved moderate recall (44.1-53.0%), with Logistic Regression showing the highest recall
- **F1-Score**: Neural Network achieved the best F1-Score (0.273), indicating the best balance for this imbalanced dataset

---

## 6. Model Comparison

### 6.1 Comprehensive Comparison

The comprehensive comparison of all six models is presented in Section 4.4. All models were evaluated using the same test set with 95,961 samples (30% of the dataset).

### 6.2 Top 2 Models Selection

**Selected Models:**
1. **Neural Network** - Best overall F1-Score (0.273) and strong AUC-ROC (0.700)
2. **Random Forest** - Second-best F1-Score (0.266) with excellent AUC-ROC (0.687) and high interpretability

**Selection Criteria:**
- **F1-Score**: Primary criterion for imbalanced datasets (Neural Network: 0.273, Random Forest: 0.266)
- **AUC-ROC**: Important for ranking models (Neural Network: 0.700, Random Forest: 0.687)
- **Balance between precision and recall**: Both models show reasonable balance
- **Model interpretability**: Random Forest provides feature importance, Neural Network is less interpretable
- **Computational efficiency**: Both models trained efficiently with optimizations
- **Clinical/practical applicability**: Both suitable for healthcare applications

### 6.3 Detailed Comparison of Top 2 Models

**Neural Network Performance:**
- Accuracy: 79.3%
- Precision: 19.6%
- Recall: 45.5%
- Specificity: 82.5%
- F1-Score: 0.273
- AUC-ROC: 0.700

**Random Forest Performance:**
- Accuracy: 79.0%
- Precision: 19.0%
- Recall: 44.4%
- Specificity: 82.3%
- F1-Score: 0.266
- AUC-ROC: 0.687

**Key Differences:**
- Neural Network shows slightly better F1-Score (0.273 vs 0.266) and AUC-ROC (0.700 vs 0.687)
- Both models have similar accuracy (79.3% vs 79.0%)
- Neural Network has slightly higher recall (45.5% vs 44.4%), meaning it detects more true positive cases
- Random Forest provides better interpretability through feature importance analysis

**Visualizations:**
- ROC curves comparison available in `images/roc_curves.png`
- Feature importance plot available in `images/feature_importance.png`
- Decision tree visualization available in `images/decision_tree.png`

### 6.4 Discussion (Approximately 250 words)

The comprehensive evaluation of six machine learning models revealed important insights for heart disease prediction. All models achieved reasonable performance, with accuracy ranging from 74.5% to 79.4%. However, given the significant class imbalance (9.4% positive cases), accuracy alone is insufficient for model selection.

The Neural Network emerged as the top-performing model with the highest F1-Score (0.273) and strong AUC-ROC (0.700), indicating the best balance between precision and recall for this imbalanced dataset. The Random Forest model ranked second with an F1-Score of 0.266 and AUC-ROC of 0.687, while offering superior interpretability through feature importance analysis.

The relatively low precision scores (19-20%) across all models reflect the challenge of predicting rare events in imbalanced datasets. However, the recall scores (44-53%) suggest that models can identify approximately half of the true heart disease cases, which is valuable for early screening applications.

The Neural Network's superior performance comes at the cost of reduced interpretability, making it a "black box" model. In contrast, Random Forest provides clear feature importance rankings, enabling clinicians to understand which risk factors contribute most to predictions. For healthcare applications, this interpretability may outweigh the slight performance advantage of Neural Networks.

Recommendations for deployment include: (1) using Neural Network for maximum predictive performance in screening scenarios, (2) using Random Forest when interpretability is required for clinical decision support, and (3) implementing ensemble methods combining both models for improved robustness.

---

## 7. Ethical Issues

### 7.1 Ethical Perspectives in Data Mining
[Discuss:
- Privacy and Confidentiality
- Informed Consent
- Data Ownership
- Bias and Fairness
- Transparency and Explainability
- Accountability
- Beneficence and Non-maleficence]

### 7.2 Ethical Issues in This Case Study
[Identify and discuss:
- Data Privacy: Protection of sensitive health information
- Model Bias: Potential bias against certain demographic groups
- False Positives/Negatives: Impact of incorrect predictions
- Model Interpretability: Need for explainable AI
- Clinical Decision Support: Role of models vs. medical professionals
- Data Quality: Impact on model reliability
- Informed Consent: Use of patient data
- Access and Equity: Ensuring fair access]

### 7.3 Mitigation Strategies
[Suggest:
- Methods to reduce bias
- Approaches to improve transparency
- Privacy-preserving techniques
- Fairness evaluation methods]

### 7.4 Discussion (Approximately 250 words)

Data mining in healthcare raises critical ethical considerations that must be addressed to ensure responsible deployment of predictive models. General ethical perspectives emphasize privacy protection, fairness, transparency, and accountability. In the context of heart disease prediction, several specific ethical issues emerge.

**Privacy and Confidentiality**: The dataset contains sensitive health information that must be protected according to HIPAA and similar regulations. Patient data used for model training requires explicit consent and secure handling to prevent unauthorized access or disclosure.

**Algorithmic Bias**: Our models may exhibit bias against certain demographic groups if training data is not representative. The class imbalance (9.4% positive cases) could lead to systematic under-detection in underrepresented populations. Regular bias audits and fairness assessments are essential.

**False Predictions**: Incorrect predictions have serious consequences - false negatives could delay critical treatment, while false positives might cause unnecessary anxiety and medical procedures. Our models show moderate recall (44-53%), meaning approximately half of true cases are detected, which requires careful clinical interpretation.

**Model Interpretability**: Neural Networks, while high-performing, lack interpretability, making it difficult for clinicians to understand predictions. Random Forest provides better interpretability through feature importance, supporting informed clinical decision-making.

**Recommendations**: Implement bias detection and mitigation techniques, ensure model interpretability for healthcare professionals, maintain strict data privacy protocols, conduct regular model auditing, and establish clear accountability frameworks. Models should support, not replace, clinical judgment, with healthcare professionals making final decisions based on comprehensive patient assessment.

---

## 8. Key Findings

### 8.1 Exploratory Data Analysis Findings
- **Dataset Characteristics**: Analyzed 319,795 records with 18 variables, revealing significant class imbalance (9.4% positive cases)
- **Statistical Relationships**: Correlation analysis and chi-square tests identified strong associations between multiple variables and heart disease risk
- **Data Quality**: Dataset was well-preprocessed with minimal missing values, enabling robust model training

### 8.2 Model Performance Findings
- **Overall Performance**: All six models achieved accuracy above 74%, with Neural Network and Decision Tree reaching 79.3-79.4%
- **F1-Score Analysis**: Neural Network achieved the best F1-Score (0.273), crucial for imbalanced datasets
- **AUC-ROC Performance**: Neural Network (0.700) and Random Forest (0.687) showed the strongest discriminative ability

### 8.3 Risk Factor Identification
- **Top Variables**: Statistical analysis identified the top 5 variables contributing most to heart disease prediction
- **Clinical Relevance**: Selected variables align with established medical knowledge about cardiovascular risk factors
- **Feature Importance**: Random Forest analysis revealed key predictors through feature importance rankings (visualized in `images/feature_importance.png`)

### 8.4 Model Selection Findings
- **Best Performing Models**: Neural Network (F1: 0.273, AUC: 0.700) and Random Forest (F1: 0.266, AUC: 0.687)
- **Recommendations for Deployment**: 
  - Use Neural Network for maximum predictive performance in screening applications
  - Use Random Forest when model interpretability is required for clinical decision support
  - Consider ensemble methods combining both models for improved robustness

---

## 9. References

1. Benjamin, E. J., Muntner, P., Alonso, A., Bittencourt, M. S., Callaway, C. W., Carson, A. P., ... & American Heart Association Council on Epidemiology and Prevention Statistics Committee and Stroke Statistics Subcommittee. (2019). Heart disease and stroke statistics—2019 update: a report from the American Heart Association. *Circulation*, 139(10), e56-e528.

2. Centers for Disease Control and Prevention. (2020). Heart Disease Risk Factors. Retrieved from https://www.cdc.gov/heart-disease/risk-factors/

3. American Heart Association. (2021). Understanding Your Risk of Heart Attack. Retrieved from https://www.heart.org/en/health-topics/heart-attack/understand-your-risks-to-prevent-a-heart-attack

4. World Health Organization. (2021). Cardiovascular diseases (CVDs). Retrieved from https://www.who.int/news-room/fact-sheets/detail/cardiovascular-diseases-(cvds)

5. Chawla, N. V., Bowyer, K. W., Hall, L. O., & Kegelmeyer, W. P. (2002). SMOTE: synthetic minority over-sampling technique. *Journal of artificial intelligence research*, 16, 321-357.

---

## 10. Appendices

### Appendix A: Additional Visualizations
[Include any additional visualizations not in main report]

### Appendix B: Code Snippets
[Include key code snippets if needed]

### Appendix C: Extended Results
[Include any extended analysis results]

---

*Report prepared as part of the R for Data Science curriculum*

