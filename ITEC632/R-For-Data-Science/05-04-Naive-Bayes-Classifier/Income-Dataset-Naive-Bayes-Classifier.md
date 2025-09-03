# Adult Income Dataset — Column Descriptions

This dataset (Census Adult) is used to predict whether income exceeds 50K/yr based on census data. Each row represents an individual.

- age: Age in years (numeric)
- workclass: Type of employer (factor; e.g., Private, Self-emp, State-gov, Federal-gov, Local-gov, Without-pay, Never-worked)
- fnlwgt: Final sampling weight (numeric)
- education: Highest education level (factor; e.g., Bachelors, HS-grad, Masters)
- education_num: Education level as integer years (numeric)
- marital_status: Marital status (factor; e.g., Married-civ-spouse, Never-married, Divorced)
- occupation: Occupation category (factor; e.g., Exec-managerial, Adm-clerical, Sales)
- relationship: Family relationship (factor; e.g., Husband, Wife, Not-in-family)
- race: Race (factor)
- sex: Sex (factor; Male/Female)
- capital_gain: Capital gains (numeric)
- capital_loss: Capital losses (numeric)
- hours_per_week: Hours worked per week (numeric)
- native_country: Country of origin (factor; e.g., United-States)
- income: Target label (factor; "<=50K" or ">50K")

Notes:
- Missing values are encoded as `?` in some categorical columns (`workclass`, `occupation`, `native_country`).
- The dataset has no header in the provided CSV; columns are assigned programmatically.
