# LDA Classification Findings: Pokemon Dataset

## Executive Summary

This document summarizes the findings from the Linear Discriminant Analysis (LDA) performed on the Pokemon dataset. LDA provides interpretable projections and a baseline classifier. On the Pokemon types task, overall test accuracy is approximately 30–40%, reflecting moderate-to-poor linear separability across 18 classes.

## Dataset Overview

- **Total Samples**: 800+ (single-type Pokemon)
- **Features**: 6 base stats (HP, Attack, Defense, Sp. Attack, Sp. Defense, Speed)
- **Classes**: 18 Pokemon types (imbalanced)
- **Data Quality**: Cleaned; only single-type Pokemon used

### Feature Description

| Feature | Description |
|---------|-------------|
| HP | Hit Points (survivability) |
| Attack | Physical attack stat |
| Defense | Physical defense stat |
| Sp. Attack | Special attack stat |
| Sp. Defense | Special defense stat |
| Speed | Speed stat |

## Model Performance Results

### Overall Performance Metrics (Test)

- **Accuracy**: ~30–40%
- **Class Separability**: Moderate to poor (substantial class overlap)
- **Most Informative Features**: Speed, Attack

### Confusion Matrix (Qualitative)

- Clear confusion among many types; some types (with distinctive stat profiles) perform better than others.

## Feature Importance Analysis

- Coefficient heatmaps indicate that Speed and Attack contribute the most across discriminants.
- Defensive stats contribute variably; importance depends on discriminant and type pairings.

## Data Preprocessing Results

- **Filtering**: Kept only single-type Pokemon
- **Normalization**: Z-score scaling across the 6 stats
- **Class Balance**: Imbalanced across 18 types

## Technical Implementation Details

- **Method**: `MASS::lda`
- **Outputs**: Discriminant directions, class priors, projected coordinates
- **Visualizations**: Projection plots (LD1-LD2, LD1-LD3), coefficient heatmaps, confusion matrix

## Key Insights

1. **Linear Boundaries Are Limiting**: Many types are not linearly separable in stat space.
2. **Speed & Attack Stand Out**: These stats drive the most discrimination.
3. **Dimensionality Reduction Value**: LDA projections aid visualization and downstream modeling.

## Model Limitations and Considerations

- Linear decision boundaries underperform for inherently non-linear class structure.
- Only 6 stats used; important non-stat features (moves, abilities, evolution) are excluded.
- Class imbalance reduces per-class performance stability.

## Recommendations for Future Work

- Evaluate non-linear models (SVM/RBF, Random Forests, Gradient Boosting, Neural Networks).
- Engineer additional features (ratios, normalized stats by base totals; meta-features from abilities/moves).
- Address class imbalance (resampling, class weights).
- Use cross-validation with stratification across types.

## Conclusion

LDA provides useful interpretability and visualization, but accuracy (~30–40%) indicates Pokemon types cannot be reliably predicted from base stats alone with linear methods. The analysis highlights key discriminative stats (Speed, Attack) and motivates non-linear, richer-feature approaches.

---

**Analysis Date**: 3rd September 2025  
**Dataset**: Pokemon (single types)  
**Algorithm**: Linear Discriminant Analysis  
**Performance**: ~30–40% Accuracy  
**Status**: ✅ Completed
