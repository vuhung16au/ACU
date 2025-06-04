# QUEST (Quick Unbiased Efficient Statistical Tree)

## Overview
QUEST is a binary decision tree algorithm that uses statistical tests to select splits. It's designed to be computationally efficient and unbiased in variable selection.

## Key Features
- Binary splits only
- Unbiased variable selection
- Efficient computation
- Handles both categorical and continuous variables
- Uses statistical tests for split selection

## Algorithm Steps
1. Variable Selection:
   - For each variable, compute association measure
   - Select variable with strongest association
2. Split Point Selection:
   - For continuous variables: Use linear discriminant analysis
   - For categorical variables: Use contingency tables
3. Tree Building:
   - Recursively apply selection and splitting
   - Stop when stopping criteria met

## Advantages
- Computationally efficient
- Unbiased variable selection
- Handles mixed variable types
- Statistically sound
- Good for large datasets

## Limitations
- Binary splits only
- May miss complex interactions
- Requires sufficient sample size
- Sensitive to parameter settings
- May not capture non-linear relationships

## Use Cases
- Classification problems
- Feature selection
- Data mining
- Pattern recognition
- Decision support systems

## Implementation Considerations
- Minimum node size
- Significance level
- Maximum tree depth
- Variable selection method
- Split point selection criteria 