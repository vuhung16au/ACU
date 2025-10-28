# Data Ethics and Bias Analysis - Loan Application Dataset

## Dataset Overview

The `loan_data_sim.csv` dataset is a simulated loan application dataset designed to demonstrate various forms of algorithmic bias and discrimination in machine learning models. This dataset contains both legitimate business factors and protected attributes that could lead to unfair lending practices.

## Dataset Description

**Dataset Name:** Loan Application Simulation Dataset  
**Purpose:** Demonstrate bias detection, algorithmic fairness, and mitigation techniques  
**Size:** 2,000 simulated loan applications  
**Target Variable:** Binary classification (loan approval: YES/NO)

## Column Descriptions

### Protected Attributes (Sensitive Characteristics)

| Column | Type | Description | Values | Bias Risk |
|--------|------|-------------|---------|-----------|
| `gender` | Categorical | Applicant's gender identity | Male, Female, Non-binary | **HIGH** - Direct protected attribute |
| `age_group` | Categorical | Age category of applicant | 18-25, 26-35, 36-50, 51-65, 65+ | **HIGH** - Age discrimination risk |
| `ethnicity` | Categorical | Self-reported ethnicity | Caucasian, African_American, Hispanic, Asian, Other | **HIGH** - Racial discrimination risk |
| `postcode` | Categorical | Geographic location (proxy for socioeconomic status) | Urban_Center, Suburban, Rural, Low_Income_Area | **MEDIUM** - Proxy discrimination |

### Legitimate Business Factors

| Column | Type | Description | Values | Business Justification |
|--------|------|-------------|---------|----------------------|
| `annual_income` | Numeric | Annual income in AUD | 25,000 - 150,000 | Creditworthiness indicator |
| `credit_score` | Numeric | Credit score (300-850) | 300 - 850 | Risk assessment |
| `employment_years` | Numeric | Years in current employment | 0 - 40 | Stability indicator |
| `debt_to_income_ratio` | Numeric | Monthly debt payments / monthly income | 0.1 - 0.8 | Financial burden |
| `loan_amount` | Numeric | Requested loan amount in AUD | 5,000 - 100,000 | Risk exposure |
| `loan_purpose` | Categorical | Purpose of the loan | Home, Car, Education, Business, Personal | Risk assessment |
| `previous_defaults` | Numeric | Number of previous loan defaults | 0 - 5 | Historical risk |

### Target Variable

| Column | Type | Description | Values | Notes |
|--------|------|-------------|---------|-------|
| `loan_approved` | Binary | Loan approval decision | YES, NO | **Target for prediction** |

### Proxy Variables (Hidden Bias Sources)

| Column | Type | Description | Values | Bias Mechanism |
|--------|------|-------------|---------|----------------|
| `education_level` | Categorical | Highest education level | High_School, Bachelor, Master, PhD | Correlates with ethnicity due to historical disparities |
| `marital_status` | Categorical | Marital status | Single, Married, Divorced, Widowed | Correlates with gender due to social patterns |
| `occupation_category` | Categorical | Job category | Professional, Service, Manual, Unemployed | Correlates with gender and ethnicity |

## Bias Patterns in the Dataset

### 1. Direct Discrimination
- **Gender Bias:** Female applicants have 15% lower approval rates despite similar financial profiles
- **Age Bias:** Applicants over 65 have 20% lower approval rates
- **Ethnicity Bias:** African American applicants face 25% lower approval rates

### 2. Proxy Discrimination
- **Education Bias:** Lower education levels correlate with certain ethnic groups, creating indirect bias
- **Geographic Bias:** Postcodes in low-income areas correlate with ethnicity, creating location-based discrimination
- **Occupation Bias:** Certain job categories are dominated by specific demographic groups

### 3. Intersectional Bias
- **Compound Discrimination:** Female African American applicants face compounded bias (40% lower approval rates)
- **Age-Gender Interaction:** Older female applicants face the highest rejection rates

## Ethical Considerations

### Australian Legal Framework
This dataset demonstrates violations of:
- **Privacy Act 1988** - Collection and use of sensitive personal information
- **Anti-Discrimination Acts** - Direct and indirect discrimination based on protected attributes
- **Australian Human Rights Commission Act 1986** - Unfair treatment based on personal characteristics

### Fair Lending Principles
- **Equal Treatment:** All applicants should be evaluated based on creditworthiness alone
- **Transparency:** Decision criteria should be explainable and non-discriminatory
- **Proportionality:** Any differential treatment must be justified by legitimate business needs

## Usage Guidelines

### For Educational Purposes
- This dataset is designed for educational demonstration of bias detection techniques
- It should not be used for actual lending decisions
- The bias patterns are intentionally exaggerated for learning purposes

### Analysis Objectives
1. **Bias Detection:** Identify disparities in approval rates across protected groups
2. **Model Auditing:** Evaluate machine learning models for fairness metrics
3. **Mitigation Testing:** Implement and test bias mitigation techniques
4. **Compliance Assessment:** Evaluate models against Australian legal requirements

## Data Quality Notes

- **Simulated Data:** All data is artificially generated for educational purposes
- **Realistic Patterns:** Bias patterns are based on documented real-world lending disparities
- **Balanced Design:** Dataset includes both biased and unbiased decision patterns for comparison
- **Missing Values:** Intentionally includes some missing values to test bias in data preprocessing

## Next Steps

1. Load and explore the dataset structure
2. Conduct disparity analysis across protected groups
3. Train predictive models and evaluate for bias
4. Implement bias mitigation techniques
5. Assess compliance with Australian regulations

---

*This dataset is part of the R for Data Science curriculum focusing on ethical AI and algorithmic fairness in the Australian context.*
