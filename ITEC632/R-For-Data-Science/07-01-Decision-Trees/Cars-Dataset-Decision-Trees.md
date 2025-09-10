# Cars Dataset Description

## Dataset Overview

The Car Evaluation Database was derived from a simple hierarchical decision model originally developed for the demonstration of DEX (M. Bohanec, V. Rajkovic: Expert system for decision making. Sistemica 1(1), pp. 145-157, 1990). The model evaluates cars according to a structured concept hierarchy.

## Dataset Characteristics

- **Total Instances**: 1,728
- **Total Attributes**: 7 (6 features + 1 target variable)
- **Data Type**: All categorical variables
- **Missing Values**: None
- **File Format**: CSV (Comma Separated Values)

## Attribute Information

### Input Features (6 attributes)

#### 1. buying (Buying Price)
- **Type**: Categorical
- **Values**: vhigh, high, med, low
- **Description**: The buying price of the car
- **Distribution**: Balanced (432 instances each)

#### 2. maint (Maintenance Price)
- **Type**: Categorical  
- **Values**: vhigh, high, med, low
- **Description**: The price of the maintenance
- **Distribution**: Balanced (432 instances each)

#### 3. doors (Number of Doors)
- **Type**: Categorical
- **Values**: 2, 3, 4, 5more
- **Description**: Number of doors in the car
- **Distribution**: Balanced (432 instances each)

#### 4. persons (Person Capacity)
- **Type**: Categorical
- **Values**: 2, 4, more
- **Description**: Capacity in terms of persons to carry
- **Distribution**: Balanced (576 instances each)

#### 5. lug_boot (Luggage Boot Size)
- **Type**: Categorical
- **Values**: small, med, big
- **Description**: The size of luggage boot
- **Distribution**: Balanced (576 instances each)

#### 6. safety (Safety Level)
- **Type**: Categorical
- **Values**: low, med, high
- **Description**: Estimated safety of the car
- **Distribution**: Balanced (576 instances each)

### Target Variable (1 attribute)

#### 7. class (Car Acceptability)
- **Type**: Categorical
- **Values**: unacc, acc, good, vgood
- **Description**: Car acceptability rating
- **Distribution**: 
  - unacc: 1,210 instances (70.0%)
  - acc: 384 instances (22.2%)
  - good: 69 instances (4.0%)
  - vgood: 65 instances (3.8%)

## Data Structure

The dataset follows a hierarchical concept structure:

```
CAR (car acceptability)
├── PRICE (overall price)
│   ├── buying (buying price)
│   └── maint (maintenance price)
└── TECH (technical characteristics)
    ├── COMFORT (comfort)
    │   ├── doors (number of doors)
    │   ├── persons (capacity in terms of persons)
    │   └── lug_boot (size of luggage boot)
    └── safety (estimated safety)
```

## Class Values Explanation

### Car Acceptability Classes

1. **unacc** (Unacceptable)
   - Cars that are not acceptable for purchase
   - Most common class (70% of data)
   - Typically associated with low safety or high price

2. **acc** (Acceptable)
   - Cars that are acceptable for purchase
   - Second most common class (22.2% of data)
   - Moderate quality and price balance

3. **good** (Good)
   - Cars that are good quality
   - Less common class (4.0% of data)
   - Good balance of features and price

4. **vgood** (Very Good)
   - Cars that are very good quality
   - Least common class (3.8% of data)
   - High quality with excellent features

## Data Quality

### Strengths
- **Complete Dataset**: No missing values
- **Balanced Features**: Most features have balanced distributions
- **Clear Structure**: Well-defined categorical values
- **Real-world Relevance**: Based on practical car evaluation criteria

### Considerations
- **Class Imbalance**: Target variable is highly imbalanced
- **Categorical Nature**: All variables are categorical (no continuous features)
- **Small Dataset**: Relatively small for complex machine learning tasks

## Usage in Decision Trees Analysis

### Why This Dataset is Suitable for Decision Trees

1. **Categorical Features**: Decision trees work well with categorical data
2. **Clear Decision Boundaries**: Hierarchical structure aligns with tree-based models
3. **Interpretable Rules**: Results provide clear decision-making logic
4. **Balanced Features**: Most features have good distribution for splitting

### Expected Insights

1. **Safety Priority**: Safety is likely to be the most important feature
2. **Price Sensitivity**: Buying and maintenance costs will influence decisions
3. **Feature Interactions**: Combinations of features may create decision rules
4. **Class Patterns**: Different classes may have distinct feature combinations

## Data Preprocessing Notes

### Required Transformations
- **Factor Conversion**: All variables need to be converted to factors in R
- **Column Naming**: Original dataset has no headers
- **Data Validation**: Verify categorical value consistency

### Recommended Splits
- **Training/Test Split**: 70/30 or 80/20 for model evaluation
- **Stratified Sampling**: Maintain class distribution in splits
- **Cross-Validation**: Use k-fold CV for robust evaluation

## File Information

- **Filename**: `cars.csv`
- **Location**: `Cars-Dataset/cars.csv`
- **Encoding**: UTF-8
- **Separator**: Comma (,)
- **Header**: None (first row contains data)
- **Size**: Approximately 50KB

---

**Note**: This dataset is particularly well-suited for demonstrating Decision Tree algorithms due to its categorical nature and clear hierarchical structure that aligns with the tree-based decision-making process.
