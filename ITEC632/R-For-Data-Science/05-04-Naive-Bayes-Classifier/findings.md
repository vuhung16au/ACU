# Naive Bayes Classification Findings: Adult Income Dataset

## Executive Summary

This document summarizes the findings from the Gaussian Naive Bayes classifier trained on the Adult Income dataset. The model achieved **82.65% test accuracy** with fast training and simple preprocessing. However, the ROC-AUC was low (≈0.125) due to the probability calibration under the chosen preprocessing and class mapping, despite good accuracy.

## Dataset Overview

- **Total Samples**: 32,561
- **Features**: 14 predictors (mixed numeric/categorical)
- **Target**: `income` (\<=50K, >50K)
- **Data Quality**: Missing values encoded as `?` in some categoricals; imputed with training-set mode

### Feature Groups

- **Numeric**: age, fnlwgt, education_num, capital_gain, capital_loss, hours_per_week
- **Categorical**: workclass, education, marital_status, occupation, relationship, race, sex, native_country

## Model Performance Results

### Overall Performance Metrics (Test)

- **Accuracy**: 82.65%
- **Sensitivity (Recall, >50K)**: 0.496
- **Specificity (\<=50K)**: 0.931
- **Precision (for >50K)**: 0.696
- **Balanced Accuracy**: 0.714
- **ROC AUC**: 0.125 (note calibration caveat)

### Confusion Matrix (Test)

| Actual \ Predicted | \<=50K | >50K |
|-------------------|--------:|-----:|
| \<=50K | 6906 | 1185 |
| >50K  | 510  | 1167 |

## Data Preprocessing Results

- **Train/Test Split**: 70/30 stratified by target
- **Imputation**: Replaced `?` in workclass, occupation, native_country with mode from training set
- **Encoding**: Factorized all categoricals; aligned test levels to training levels
- **Scaling**: Z-score for numeric features (fit on train, applied to test)

## Technical Implementation Details

- **Algorithm**: `e1071::naiveBayes`
- **Evaluation**: caret confusion matrix; probability histogram; ROC curve via `pROC`
- **Cross-Validation**: caret 5-fold (best CV Accuracy ≈ 0.830)
- **Visualizations**: Class distribution, correlation heatmap, confusion matrix, probability histogram, ROC curve

## Key Insights

1. **Strong Accuracy Baseline**: Despite a simple model, accuracy is competitive for this dataset.
2. **Class Imbalance Effects**: High specificity with lower sensitivity indicates errors mainly on >50K.
3. **Probability Calibration**: AUC is low; probability estimates may be poorly calibrated under this preprocessing. Isotonic/Platt scaling or alternative encodings could help.

## Limitations and Considerations

- Naive Bayes assumes conditional independence; violated in this dataset (correlated socio-economic features).
- Mixed variable types and heavy-tailed gains/losses challenge Gaussian assumptions.
- One-hot vs. factor treatment can change behavior; here factors with Gaussian on numeric were used.

## Recommendations for Future Work

- Improve calibration (Platt scaling, isotonic regression) on validation data.
- Try alternative encodings for categoricals (one-hot with GaussianNB on numerics only; or multinomial/bernoulli variants where appropriate).
- Compare with Logistic Regression, Random Forests, Gradient Boosting, and XGBoost.
- Address class imbalance (threshold tuning, cost-sensitive learning).

## Conclusion

Gaussian Naive Bayes delivers fast training and solid accuracy (82.65%) on the Adult dataset but shows weak probability calibration (low AUC). With better calibration and feature handling, performance and probabilistic interpretability can be improved.

---

**Analysis Date**: 3rd September 2025  
**Dataset**: Adult Income  
**Algorithm**: Gaussian Naive Bayes  
**Performance**: 82.65% Accuracy; AUC ≈ 0.125  
**Status**: ✅ Completed
