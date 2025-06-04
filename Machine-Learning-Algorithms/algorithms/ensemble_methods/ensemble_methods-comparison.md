# Ensemble Methods Comparison

| Method | Time Complexity | Space Complexity | Best For | Limitations | Advantages | Use Cases |
|--------|----------------|------------------|----------|-------------|------------|-----------|
| Bagging | O(n*p*t) where t is number of models | O(p*t) | - Reducing variance<br>- Parallel training<br>- When base models are unstable | - May not improve bias<br>- Requires more memory<br>- Can be computationally expensive | - Reduces overfitting<br>- Parallelizable<br>- Works with any base model<br>- Robust to outliers | - Random Forests<br>- Credit scoring<br>- Medical diagnosis<br>- Fraud detection |
| Boosting | O(n*p*t) where t is number of models | O(p*t) | - Reducing bias<br>- Improving weak learners<br>- When accuracy is crucial | - Sequential training<br>- Sensitive to noise<br>- Can overfit<br>- Requires careful tuning | - Often achieves best performance<br>- Can handle imbalanced data<br>- Automatic feature selection<br>- Good generalization | - XGBoost<br>- LightGBM<br>- CatBoost<br>- Adaboost |
| Stacking | O(n*p*t + n*p*m) where t is base models, m is meta-model | O(p*(t+m)) | - Combining different models<br>- When base models are complementary<br>- Complex problems | - Computationally expensive<br>- Requires more data<br>- Complex to implement<br>- Can overfit | - Leverages strengths of different models<br>- Can capture complex patterns<br>- Often better than individual models<br>- Flexible architecture | - Kaggle competitions<br>- Complex prediction tasks<br>- When accuracy is critical<br>- Multi-stage problems |

## Common Characteristics
- All combine multiple models
- All can improve over single models
- All require more computational resources
- All can handle both classification and regression
- All benefit from diverse base models

## Key Differences
1. **Training Process**:
   - Bagging: Parallel training of independent models
   - Boosting: Sequential training, each model learns from previous errors
   - Stacking: Two-stage training (base models + meta-model)

2. **Error Reduction**:
   - Bagging: Reduces variance
   - Boosting: Reduces bias
   - Stacking: Can reduce both bias and variance

3. **Model Independence**:
   - Bagging: Models are independent
   - Boosting: Models are dependent
   - Stacking: Models can be either

4. **Use Cases**:
   - Bagging: When base models are unstable
   - Boosting: When base models are weak
   - Stacking: When different models have complementary strengths

5. **Computational Requirements**:
   - Bagging: Parallelizable, moderate
   - Boosting: Sequential, high
   - Stacking: Two-stage, highest

6. **Popular Implementations**:
   - Bagging: Random Forest
   - Boosting: XGBoost, LightGBM, CatBoost
   - Stacking: Custom implementations

7. **Hyperparameter Sensitivity**:
   - Bagging: Less sensitive
   - Boosting: More sensitive
   - Stacking: Most sensitive 