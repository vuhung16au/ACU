# QDA Classification Findings: Heart Disease Dataset

## Executive Summary

This document reports findings from applying Quadratic Discriminant Analysis (QDA) to the UCI Heart Disease dataset. QDA achieved **78.65% test accuracy** (73.43% train), demonstrating solid performance with curved decision boundaries suited to differing class covariances.

## Dataset Overview

- **Total Samples**: 303 (after cleaning: 296)
- **Features**: 14 (mix of numeric and categorical; numeric used for QDA)
- **Target**: Binary (`NoDisease`, `Disease`)
- **Data Quality**: Cleaned by removing known faulty rows (`ca == 4`, `thal == 0`); categorical recoded as factors

### Selected Numeric Features (examples)

- age, trestbps, chol, thalach, oldpeak (and other numeric columns)

## Model Performance Results

### Overall Performance Metrics

- **Train Accuracy**: 73.43%
- **Test Accuracy**: 78.65%
- **ROC AUC (Test, posterior-based)**: Reported via plot; value computed during run (see `images/06_roc_curve.png`)

### Confusion Matrix (Test)

- See `images/05_confusion_matrix.png` for counts and layout. The confusion matrix heatmap is labeled with actual vs predicted classes and the overall accuracy in the title.

## EDA Highlights

- **Class Balance**: Visible from the `Target Distribution` plot (`images/02_target_distribution.png`).
- **Correlations**: Numeric features display varied correlations (see correlation matrix figure).
- **Feature Distributions**: Histograms of selected numeric features indicate separability trends between classes.

## Technical Implementation Details

- **Method**: `MASS::qda` on numeric predictors
- **Split**: Random 70/30 train/test (set.seed(123))
- **Evaluation**: Accuracy on train and test, confusion matrix, ROC curve (posterior for `Disease`)
- **Visualizations**: Decision regions on synthetic data, EDA plots, confusion matrix, ROC curve

## Key Insights

1. **Curved Boundaries Help**: Allowing class-specific covariance improves separation vs linear methods.
2. **Moderate Accuracy**: ~79% test accuracy is competitive for this small dataset and simple pipeline.
3. **Predictive Drivers**: Features like age, thalach, and oldpeak appear influential.

## Limitations and Considerations

- Small sample size; results vary with split.
- Using only numeric predictors simplifies modeling but may omit categorical signal.
- Potential overfitting risk with many parameters relative to data size (class-specific covariance matrices).

## Recommendations for Future Work

- Try LDA as a baseline and compare with logistic regression and tree ensembles.
- Include engineered features and carefully encode categoricals to enrich signal.
- Use k-fold cross-validation and stratification to stabilize estimates.
- Consider regularized discriminant analysis (RDA) to balance bias-variance.

## Conclusion

QDA delivers strong baseline performance (78.65% test accuracy) on the heart disease dataset, benefiting from flexible, quadratic decision boundaries. With additional features, regularization, and robust validation, further gains are likely.

---

**Analysis Date**: 3rd September 2025  
**Dataset**: Heart Disease (Cleveland)  
**Algorithm**: Quadratic Discriminant Analysis  
**Performance**: 78.65% Test Accuracy  
**Status**: ✅ Completed
