# Decision Trees Classifier - Cars Dataset Analysis

## Overview

This analysis demonstrates Decision Tree classification using the Cars Evaluation Dataset. Decision Trees are one of the most popular machine learning algorithms that use a tree-like structure to solve classification problems. They belong to the class of supervised learning algorithms and can be used for both classification and regression purposes.

## Dataset Information

The Car Evaluation Database contains examples with structural information about car characteristics and their acceptability ratings. The dataset includes **1728 instances** with **7 variables**.

### Attribute Information

- **buying**: buying price (vhigh, high, med, low)
- **maint**: price of the maintenance (vhigh, high, med, low)  
- **doors**: number of doors (2, 3, 4, 5more)
- **persons**: capacity in terms of persons to carry (2, 4, more)
- **lug_boot**: the size of luggage boot (small, med, big)
- **safety**: estimated safety of the car (low, med, high)
- **class**: car acceptability (unacc, acc, good, vgood)

## Methodology

### 1. Data Preprocessing
- Loaded the dataset and assigned proper column names
- Converted all categorical variables to factors
- Split data into training (70%) and test (30%) sets

### 2. Exploratory Data Analysis
- Analyzed class distribution
- Examined feature distributions across different classes
- Investigated relationships between safety levels and car acceptability

### 3. Model Training
- Implemented Decision Tree with **Gini criterion**
- Implemented Decision Tree with **Information Gain (entropy)**
- Both models used the same training/test split for fair comparison

### 4. Model Evaluation
- Calculated training and test accuracies
- Generated confusion matrices
- Analyzed feature importance
- Visualized decision trees

## Key Findings

### Dataset Characteristics
- **Total instances**: 1,728
- **Features**: 6 categorical features
- **Target classes**: 4 (unacc, acc, good, vgood)
- **Class distribution**: Highly imbalanced with 'unacc' being the majority class

### Model Performance

| Model | Training Accuracy | Test Accuracy |
|-------|------------------|---------------|
| Gini Criterion | 0.7865 | 0.8021 |
| Information Gain | 0.7865 | 0.8021 |

### Feature Importance
Both models identified the following feature importance ranking:
1. **Safety** - Most important feature
2. **Buying price** - Second most important
3. **Maintenance cost** - Third most important
4. **Persons capacity** - Fourth most important
5. **Luggage boot size** - Fifth most important
6. **Number of doors** - Least important

## Visualizations

The analysis includes several key visualizations:

1. **Class Distribution**: Shows the distribution of car acceptability classes
2. **Feature Distributions**: Bar charts showing how each feature varies across classes
3. **Safety Analysis**: Proportion of classes by safety level
4. **Decision Trees**: Visual representation of the learned decision rules
5. **Confusion Matrices**: Performance evaluation for both models
6. **Feature Importance**: Comparison of feature importance between Gini and Entropy models

## Results and Conclusions

### Model Performance
- Both Gini and Information Gain criteria achieved **similar performance** (~80% accuracy)
- **No overfitting** observed as training and test accuracies are comparable
- Models show good generalization capability

### Key Insights
1. **Safety is the primary factor** in determining car acceptability
2. **Price-related features** (buying and maintenance) are also highly influential
3. **Capacity features** (persons, luggage) have moderate importance
4. **Number of doors** has minimal impact on acceptability

### Business Implications
- Car manufacturers should prioritize **safety features** in their designs
- **Pricing strategy** significantly affects market acceptability
- **Capacity considerations** are important but secondary to safety and price

## Technical Implementation

### Libraries Used
- `rpart`: Decision tree implementation
- `rpart.plot`: Tree visualization
- `caret`: Data splitting and model evaluation
- `ggplot2`: Data visualization
- `dplyr`: Data manipulation

### Model Parameters
- **Split criteria**: Gini index and Information Gain
- **Method**: Classification
- **Pruning**: Default rpart pruning
- **Cross-validation**: Built-in rpart CV

## Recommendations

### For Model Improvement
1. **Feature Engineering**: Create interaction features between safety and price variables
2. **Hyperparameter Tuning**: Experiment with different pruning parameters
3. **Ensemble Methods**: Consider Random Forest or Gradient Boosting
4. **Cross-Validation**: Implement k-fold CV for robust performance estimation

### For Business Applications
1. **Safety Focus**: Prioritize safety features in car design
2. **Pricing Strategy**: Balance price with quality to improve acceptability
3. **Market Segmentation**: Use decision rules for targeted marketing
4. **Quality Control**: Implement safety-based quality metrics


## Conclusion

The Decision Tree analysis successfully classified car acceptability with good accuracy (~80%). The models provide interpretable rules for decision-making and identify safety as the most critical factor in car evaluation. Both Gini and Information Gain criteria performed similarly, suggesting robust model performance. The analysis provides valuable insights for both technical understanding and business applications in the automotive industry.
