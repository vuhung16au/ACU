# Data Cleansing Key Concepts

## Overview

Data cleansing (also known as data cleaning or data scrubbing) is the process of detecting and correcting (or removing) corrupt, inaccurate, incomplete, or irrelevant records from a dataset. It is a critical step in the data analysis pipeline that ensures data quality and reliability for downstream analysis and machine learning applications.

## Why Data Cleansing is Important

### 1. **Data Quality Impact**
- Poor quality data leads to incorrect insights and decisions
- Garbage in, garbage out (GIGO) principle
- Data quality directly affects model performance

### 2. **Business Value**
- Reduces operational costs
- Improves decision-making accuracy
- Enhances customer satisfaction
- Ensures regulatory compliance

### 3. **Technical Benefits**
- Prevents errors in analysis
- Reduces processing time
- Improves algorithm performance
- Enables reliable automation

## Common Data Quality Issues

### 1. **Missing Values**
- **Types**: NULL, empty strings, "N/A", "Unknown"
- **Causes**: Data entry errors, system failures, optional fields
- **Impact**: Reduces dataset size, affects statistical calculations

### 2. **Inconsistent Data**
- **Case variations**: "Male" vs "male" vs "MALE"
- **Format differences**: "01/01/2020" vs "2020-01-01"
- **Spelling errors**: "Sydney" vs "Sidney"
- **Abbreviations**: "St." vs "Street"

### 3. **Duplicate Records**
- **Exact duplicates**: Identical rows
- **Near duplicates**: Similar records with minor differences
- **Causes**: Data entry errors, system integration issues

### 4. **Outliers**
- **Statistical outliers**: Values far from the mean
- **Domain outliers**: Values outside expected business ranges
- **Causes**: Data entry errors, measurement errors, rare events

### 5. **Data Type Issues**
- **Numeric as text**: "123" instead of 123
- **Date as text**: "January 1, 2020" instead of date object
- **Mixed types**: Numbers and text in same column

### 6. **Structural Issues**
- **Wide vs Long format**: Data spread across columns vs rows
- **Nested data**: JSON objects in CSV columns
- **Inconsistent schemas**: Different column structures

## Data Cleansing Process

### 1. **Data Assessment**
```r
# Load and inspect data
library(tidyverse)
library(skimr)
library(naniar)

# Basic structure
glimpse(data)
skim(data)

# Missing value analysis
miss_var_summary(data)
vis_miss(data)
```

### 2. **Data Profiling**
- **Statistical summaries**: Mean, median, mode, standard deviation
- **Data distribution**: Histograms, box plots
- **Uniqueness**: Count of unique values
- **Pattern analysis**: Regular expressions, format validation

### 3. **Data Standardization**
```r
# Clean column names
library(janitor)
data <- data %>% clean_names()

# Standardize text
data <- data %>%
  mutate(
    city = str_to_title(city),
    city = str_squish(city)
  )
```

### 4. **Missing Value Treatment**
```r
# Drop rows with missing critical data
data <- data %>% filter(!is.na(customer_id))

# Fill missing values
data <- data %>%
  mutate(
    city = ifelse(is.na(city), "Unknown", city),
    rating = ifelse(is.na(rating), median(rating, na.rm = TRUE), rating)
  )
```

### 5. **Outlier Detection and Treatment**
```r
# IQR method for outlier detection
detect_outliers <- function(x) {
  Q1 <- quantile(x, 0.25, na.rm = TRUE)
  Q3 <- quantile(x, 0.75, na.rm = TRUE)
  IQR <- Q3 - Q1
  lower <- Q1 - 1.5 * IQR
  upper <- Q3 + 1.5 * IQR
  return(x < lower | x > upper)
}

# Winsorization (capping outliers)
winsorize <- function(x, p_low = 0.01, p_high = 0.99) {
  lo <- quantile(x, p_low, na.rm = TRUE)
  hi <- quantile(x, p_high, na.rm = TRUE)
  pmin(pmax(x, lo), hi)
}
```

### 6. **Data Validation**
```r
# Range validation
validate_ranges <- function(data) {
  data %>%
    mutate(
      quantity_valid = quantity > 0 & quantity <= 100,
      price_valid = unit_price > 0 & unit_price <= 1000,
      rating_valid = rating >= 1 & rating <= 10
    )
}
```

## Data Cleansing Techniques

### 1. **Text Processing**
- **Normalization**: Convert to consistent case
- **Trimming**: Remove leading/trailing whitespace
- **Pattern matching**: Use regular expressions
- **Fuzzy matching**: Handle typos and variations

### 2. **Date and Time Processing**
```r
library(lubridate)

# Parse dates
data <- data %>%
  mutate(
    date_parsed = mdy(date),
    year = year(date_parsed),
    month = month(date_parsed, label = TRUE),
    day_of_week = wday(date_parsed, label = TRUE)
  )
```

### 3. **Numeric Processing**
```r
# Parse numbers with currency symbols
data <- data %>%
  mutate(
    unit_price = parse_number(unit_price),
    total = parse_number(total)
  )

# Handle negative values
data <- data %>%
  mutate(
    quantity = abs(quantity),
    unit_price = abs(unit_price)
  )
```

### 4. **Categorical Processing**
```r
library(forcats)

# Standardize factor levels
data <- data %>%
  mutate(
    city = as_factor(city),
    city = fct_recode(city, "Sydney" = "Sidney"),
    city = fct_relevel(city, "Sydney", "Melbourne", "Perth")
  )
```

## Data Quality Metrics

### 1. **Completeness**
- **Formula**: (Non-missing values / Total values) × 100
- **Target**: > 95% for critical fields

### 2. **Accuracy**
- **Definition**: Percentage of correct values
- **Measurement**: Manual sampling or business rules

### 3. **Consistency**
- **Definition**: Adherence to defined formats and standards
- **Measurement**: Pattern matching and validation rules

### 4. **Uniqueness**
- **Definition**: Percentage of unique records
- **Formula**: (Unique records / Total records) × 100

### 5. **Validity**
- **Definition**: Conformance to business rules
- **Examples**: Age > 0, Email format, Date ranges

## Best Practices

### 1. **Documentation**
- Document all cleaning decisions
- Maintain audit trail of changes
- Version control for cleaning scripts

### 2. **Automation**
- Create reusable cleaning functions
- Implement data quality checks
- Use configuration files for rules

### 3. **Validation**
- Test cleaning logic on sample data
- Validate results with business users
- Implement automated quality checks

### 4. **Performance**
- Process data in chunks for large datasets
- Use efficient data structures
- Optimize memory usage

### 5. **Reproducibility**
- Use seed values for random operations
- Maintain consistent environment
- Document package versions

## Tools and Libraries

### R Packages
- **tidyverse**: Data manipulation and visualization
- **janitor**: Data cleaning utilities
- **naniar**: Missing value analysis
- **lubridate**: Date/time processing
- **stringr**: String manipulation
- **forcats**: Factor handling
- **skimr**: Data summary statistics

### Python Libraries
- **pandas**: Data manipulation
- **numpy**: Numerical computing
- **scikit-learn**: Data preprocessing
- **fuzzywuzzy**: Fuzzy string matching

### Commercial Tools
- **Trifacta**: Data preparation platform
- **Talend**: Data integration and quality
- **Informatica**: Data quality management
- **IBM InfoSphere**: Data quality suite

## Common Pitfalls

### 1. **Over-cleaning**
- Removing too much data
- Losing important information
- Creating bias in the dataset

### 2. **Under-cleaning**
- Not addressing quality issues
- Accepting poor data quality
- Missing critical problems

### 3. **Inconsistent Rules**
- Different standards across datasets
- Changing rules without documentation
- Lack of validation

### 4. **Performance Issues**
- Inefficient algorithms
- Memory problems with large datasets
- Long processing times

## Conclusion

Data cleansing is an iterative process that requires domain knowledge, technical skills, and careful attention to detail. The goal is to create a clean, consistent, and reliable dataset that supports accurate analysis and decision-making. By following systematic approaches and best practices, data scientists can ensure high-quality data that drives meaningful insights.
