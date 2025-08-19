# Linear and Logistic Regression

## Linear Regression

### Define linear regression and explain how it is used

**Linear Regression** is a fundamental supervised learning algorithm used for predicting continuous numerical values based on one or more input features. It models the relationship between a dependent variable (target) and one or more independent variables (features) using a linear function.

**Key Concepts:**
- **Simple Linear Regression**: Uses one independent variable to predict the dependent variable
- **Multiple Linear Regression**: Uses multiple independent variables to predict the dependent variable
- **Equation**: $y = \beta_0 + \beta_1 x_1 + \beta_2 x_2 + \cdots + \beta_n x_n + \varepsilon$
  - $y$ = dependent variable (target)
  - $\beta_0$ = y-intercept (bias term)
  - $\beta_1, \beta_2, \ldots, \beta_n$ = coefficients (slopes)
  - $x_1, x_2, \ldots, x_n$ = independent variables (features)
  - $\varepsilon$ = error term (residual)

**Common Applications:**
- Predicting house prices based on square footage, number of bedrooms, location
- Forecasting sales based on advertising budget, season, economic indicators
- Estimating student performance based on study hours, attendance, previous grades
- Predicting stock prices based on market indicators

### Identify necessary format for predictive linear regression

**Data Requirements:**

1. **Target Variable (y)**:
   - Must be continuous/numerical
   - Examples: price, temperature, salary, sales volume
   - Should follow normal distribution for optimal results

2. **Feature Variables (X)**:
   - Can be numerical or categorical (with proper encoding)
   - Numerical features should be scaled/normalized
   - Categorical features need encoding (one-hot, label encoding)

3. **Data Quality**:
   - No missing values (or proper handling of missing data)
   - No multicollinearity between features
   - Sufficient sample size (rule of thumb: 10-20 observations per feature)

**Data Preprocessing Steps:**
```python
# Example preprocessing steps
import pandas as pd
from sklearn.preprocessing import StandardScaler, LabelEncoder
from sklearn.model_selection import train_test_split

# Handle missing values
df = df.dropna()  # or df.fillna(method='ffill')

# Encode categorical variables
le = LabelEncoder()
df['category_encoded'] = le.fit_transform(df['category'])

# Scale numerical features
scaler = StandardScaler()
df[['feature1', 'feature2']] = scaler.fit_transform(df[['feature1', 'feature2']])

# Split data
X = df.drop('target', axis=1)
y = df['target']
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)
```

### Interpret resulting coefficients from linear regression model

**Coefficient Interpretation:**

1. **$\beta_0$ (Intercept)**:
   - Predicted value of $y$ when all features are zero
   - May not always have practical meaning
   - Example: If $\beta_0 = 50,000$, predicted house price is $50,000 when all features are zero

2. **$\beta_1, \beta_2, \ldots, \beta_n$ (Feature Coefficients)**:
   - **Positive coefficient**: As feature increases, target increases
   - **Negative coefficient**: As feature increases, target decreases
   - **Magnitude**: Strength of the relationship
   - **Units**: Change in target per unit change in feature

**Example Interpretation:**
```python
# Model: House_Price = 50000 + 100(Square_Feet) + 5000(Bedrooms) - 2000(Age)

# Interpretation:
# - β₀ = 50,000: Base price when all features are zero
# - β₁ = 100: Each additional square foot adds $100 to price
# - β₂ = 5,000: Each additional bedroom adds $5,000 to price
# - β₃ = -2,000: Each additional year of age reduces price by $2,000
```

**Mathematical Form:**
$$\text{House\_Price} = 50000 + 100 \cdot \text{Square\_Feet} + 5000 \cdot \text{Bedrooms} - 2000 \cdot \text{Age}$$

**Model Evaluation Metrics:**
- **$R^2$ (R-squared)**: Proportion of variance explained (0 to 1, higher is better)
- **Adjusted $R^2$**: $R^2$ adjusted for number of features
- **RMSE (Root Mean Square Error)**: $\sqrt{\frac{1}{n}\sum_{i=1}^{n}(y_i - \hat{y}_i)^2}$
- **MAE (Mean Absolute Error)**: $\frac{1}{n}\sum_{i=1}^{n}|y_i - \hat{y}_i|$

## Logistic Regression

### Explain what logistic regression is, how it is used, and the benefits of using it

**Logistic Regression** is a supervised learning algorithm used for binary classification problems. Despite its name, it's a classification algorithm, not a regression algorithm. It models the probability of an event occurring using a logistic function (sigmoid function).

**Key Concepts:**
- **Binary Classification**: Predicts one of two possible outcomes (0 or 1)
- **Probability Output**: Returns probability between 0 and 1
- **Decision Boundary**: Typically 0.5 (can be adjusted)
- **Equation**: $P(y=1) = \frac{1}{1 + e^{-z}}$ where $z = \beta_0 + \beta_1 x_1 + \beta_2 x_2 + \cdots + \beta_n x_n$

**Sigmoid Function:**
$$P(y=1) = \frac{1}{1 + e^{-z}} = \sigma(z)$$
- Transforms any real number to a probability between 0 and 1
- S-shaped curve that approaches 0 and 1 asymptotically

**Common Applications:**
- Medical diagnosis (disease present/absent)
- Email spam detection (spam/not spam)
- Credit card fraud detection (fraudulent/legitimate)
- Customer churn prediction (will churn/won't churn)
- Admission prediction (admitted/rejected)

**Benefits of Logistic Regression:**
1. **Interpretability**: Coefficients have clear meaning
2. **Probability Output**: Provides confidence scores
3. **Computational Efficiency**: Fast training and prediction
4. **No Assumptions**: Doesn't require normal distribution
5. **Feature Importance**: Easy to identify important features
6. **Baseline Model**: Good starting point for classification problems

### Identify the necessary format of data required to perform logistic regression

**Data Requirements:**

1. **Target Variable (y)**:
   - Must be binary (0 or 1, True or False, Yes or No)
   - Can be encoded as integers or strings
   - Should be balanced (similar number of samples in each class)

2. **Feature Variables (X)**:
   - Can be numerical or categorical
   - Numerical features benefit from scaling
   - Categorical features need encoding
   - No perfect multicollinearity

3. **Data Quality**:
   - No missing values in target variable
   - Handle missing values in features appropriately
   - Check for class imbalance

**Data Preprocessing Example:**
```python
import pandas as pd
from sklearn.preprocessing import StandardScaler, LabelEncoder
from sklearn.model_selection import train_test_split

# Load data
df = pd.read_csv('data.csv')

# Encode target variable
le_target = LabelEncoder()
df['target_encoded'] = le_target.fit_transform(df['target'])

# Handle categorical features
categorical_cols = ['category1', 'category2']
for col in categorical_cols:
    le = LabelEncoder()
    df[col + '_encoded'] = le.fit_transform(df[col])

# Scale numerical features
numerical_cols = ['feature1', 'feature2', 'feature3']
scaler = StandardScaler()
df[numerical_cols] = scaler.fit_transform(df[numerical_cols])

# Prepare features and target
X = df.drop(['target', 'target_encoded'] + categorical_cols, axis=1)
y = df['target_encoded']

# Split data
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
```

**Handling Class Imbalance:**
```python
from imblearn.over_sampling import SMOTE
from imblearn.under_sampling import RandomUnderSampler

# Oversampling minority class
smote = SMOTE(random_state=42)
X_train_balanced, y_train_balanced = smote.fit_resample(X_train, y_train)

# Or undersampling majority class
rus = RandomUnderSampler(random_state=42)
X_train_balanced, y_train_balanced = rus.fit_resample(X_train, y_train)
```

### Interpret output of logistic regression models

**Coefficient Interpretation:**

1. **$\beta_0$ (Intercept)**:
   - Log-odds of the event when all features are zero
   - Example: $\beta_0 = -2.3$ means log-odds = -2.3 when all features are zero

2. **$\beta_1, \beta_2, \ldots, \beta_n$ (Feature Coefficients)**:
   - **Positive coefficient**: Feature increases probability of event
   - **Negative coefficient**: Feature decreases probability of event
   - **Magnitude**: Strength of the relationship
   - **Odds Ratio**: $e^{\beta}$ gives the odds ratio

**Odds Ratio Interpretation:**
```python
# Model: log(odds) = -2.3 + 0.5(Age) + 1.2(Income) - 0.8(Education)

# Interpretation:
# - β₀ = -2.3: Log-odds when all features are zero
# - β₁ = 0.5: Each year increase in age multiplies odds by e^0.5 = 1.65
# - β₂ = 1.2: Each unit increase in income multiplies odds by e^1.2 = 3.32
# - β₃ = -0.8: Each unit increase in education multiplies odds by e^(-0.8) = 0.45
```

**Mathematical Form:**
$$\log\left(\frac{P(y=1)}{1-P(y=1)}\right) = -2.3 + 0.5 \cdot \text{Age} + 1.2 \cdot \text{Income} - 0.8 \cdot \text{Education}$$

**Odds Ratios:**
- Age: $e^{0.5} = 1.65$
- Income: $e^{1.2} = 3.32$
- Education: $e^{-0.8} = 0.45$

**Model Evaluation Metrics:**

1. **Classification Metrics:**
   - **Accuracy**: $\frac{TP + TN}{TP + TN + FP + FN}$
   - **Precision**: $\frac{TP}{TP + FP}$ - How many predicted positives are actually positive
   - **Recall**: $\frac{TP}{TP + FN}$ - How many actual positives are correctly predicted
   - **F1-Score**: $2 \times \frac{\text{Precision} \times \text{Recall}}{\text{Precision} + \text{Recall}}$

2. **Probability Metrics:**
   - **AUC-ROC**: Area under ROC curve (0.5 to 1.0, higher is better)
   - **Log Loss**: $-\frac{1}{n}\sum_{i=1}^{n}[y_i \log(\hat{y}_i) + (1-y_i)\log(1-\hat{y}_i)]$
   - **Brier Score**: $\frac{1}{n}\sum_{i=1}^{n}(y_i - \hat{y}_i)^2$

**Example Model Output:**
```python
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import classification_report, confusion_matrix

# Train model
model = LogisticRegression(random_state=42)
model.fit(X_train, y_train)

# Make predictions
y_pred = model.predict(X_test)
y_pred_proba = model.predict_proba(X_test)

# Evaluate
print("Classification Report:")
print(classification_report(y_test, y_pred))

print("\nConfusion Matrix:")
print(confusion_matrix(y_test, y_pred))

print("\nCoefficients:")
for feature, coef in zip(X.columns, model.coef_[0]):
    print(f"{feature}: {coef:.4f}")

print(f"Intercept: {model.intercept_[0]:.4f}")
```

**Decision Threshold Adjustment:**
```python
# Default threshold is 0.5
y_pred_default = (y_pred_proba[:, 1] > 0.5).astype(int)

# Adjust threshold for different business needs
y_pred_conservative = (y_pred_proba[:, 1] > 0.7).astype(int)  # Higher precision
y_pred_aggressive = (y_pred_proba[:, 1] > 0.3).astype(int)    # Higher recall
```

**Feature Importance:**
```python
# Calculate feature importance based on absolute coefficient values
feature_importance = pd.DataFrame({
    'Feature': X.columns,
    'Coefficient': abs(model.coef_[0])
})
feature_importance = feature_importance.sort_values('Coefficient', ascending=False)
print("Feature Importance:")
print(feature_importance)
```

This comprehensive guide covers the essential concepts, data requirements, and interpretation methods for both linear and logistic regression, providing practical examples and code snippets for implementation.
