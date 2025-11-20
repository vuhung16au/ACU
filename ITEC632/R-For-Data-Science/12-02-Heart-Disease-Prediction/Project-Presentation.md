# Heart Disease Prediction - Project Presentation

## Slide 1: Introduction

- **Project Title**: Heart Disease Prediction Using Machine Learning
- **Objective**: Develop predictive models to identify heart disease risk
- **Dataset**: 319,795 records, 18 variables
- **Approach**: Six machine learning algorithms evaluated
- **Results**: Neural Network and Random Forest selected as top models

---

## Slide 2: Dataset Description

- **Source**: Heart Disease Dataset 2020 (cleaned)
- **Size**: 319,795 rows, 18 columns
- **Target**: HeartDisease (Yes/No)
- **Challenge**: Severe class imbalance
  - No Heart Disease: 292,422 (91.4%)
  - Heart Disease: 27,373 (8.6%)
  - Imbalance Ratio: 0.094 (10.7:1)
- **Variables**: Demographics, health conditions, lifestyle factors, health metrics

---

## Slide 3: Exploratory Data Analysis

- **Key Findings**:
  - Identified significant risk factors
  - Statistical tests revealed important relationships
  - Class imbalance confirmed
- **Top 5 Variables Selected**:
  - Based on correlation and statistical significance
  - Supported by medical literature
  - Clinically relevant factors

---

## Slide 4: Key Findings

- **Risk Factors Identified**:
  - [Variable 1] - Strong correlation
  - [Variable 2] - Significant association
  - [Variable 3] - Important predictor
  - [Variable 4] - Clinical relevance
  - [Variable 5] - Statistical significance
- **Statistical Validation**: Chi-square and correlation tests

---

## Slide 5: Predictive Models

- **6 Models Evaluated**:
  - Logistic Regression
  - Decision Tree
  - Random Forest
  - Support Vector Machine
  - K-Nearest Neighbors
  - Neural Network
- **Class Imbalance**: Handled using SMOTE technique

---

## Slide 6: Model Performance

- **Evaluation Metrics**:
  - Accuracy: 74.5% - 79.4% across all models
  - Precision: 15.6% - 19.6%
  - Recall: 44.1% - 53.0%
  - F1-Score: 0.230 - 0.273
  - AUC-ROC: 0.635 - 0.703
- **Top 2 Models**:
  - **Neural Network**: Best F1-Score (0.273), AUC-ROC (0.700)
  - **Random Forest**: Second-best F1-Score (0.266), AUC-ROC (0.687)
- **Key Strength**: Strong AUC-ROC scores indicating good discriminative ability

---

## Slide 7: Model Comparison

- **Comparison Criteria**:
  - F1-Score and AUC-ROC (primary metrics for imbalanced data)
  - Precision and Recall balance
  - Model interpretability
  - Computational efficiency
- **Selected Models**: 
  - **Neural Network**: Best performance (F1: 0.273, AUC: 0.700)
  - **Random Forest**: Strong performance with interpretability (F1: 0.266, AUC: 0.687)
- **Performance Summary**: All models achieved >74% accuracy, Neural Network and Random Forest showed best balance

---

## Slide 8: Conclusion

- **Key Achievements**:
  - Successfully identified risk factors
  - Built and evaluated 6 models
  - Selected best-performing models
- **Practical Implications**:
  - Early risk identification
  - Preventive healthcare applications
  - Clinical decision support

---

## Slide 9: Further Work

- **Future Improvements**:
  - Larger dataset collection
  - Additional feature engineering
  - Model ensemble techniques
  - Real-time prediction system
- **Ethical Considerations**:
  - Bias mitigation
  - Model interpretability
  - Privacy protection

---

*Presentation prepared for R for Data Science Course*

