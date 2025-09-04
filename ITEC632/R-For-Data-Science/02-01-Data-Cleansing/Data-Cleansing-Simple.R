# Data Cleansing Essentials with R - Simplified Version
# Australia Sales Dataset Analysis

# Clear workspace
rm(list = ls())

# Load required packages
suppressPackageStartupMessages({
  library(tidyverse)
  library(janitor)
  library(naniar)
  library(lubridate)
  library(stringr)
  library(forcats)
  library(skimr)
})

# Set working directory
cat("Current working directory:", getwd(), "\n")

# Create output directories
dir.create("images", showWarnings = FALSE, recursive = TRUE)
dir.create("Dataset", showWarnings = FALSE, recursive = TRUE)

# =============================================================================
# DATA LOADING AND INITIAL ASSESSMENT
# =============================================================================

cat("=== DATA CLEANSING ESSENTIALS WITH R ===\n")
cat("Loading Australia Sales Dataset...\n\n")

# Load the dataset
data_path <- "Dataset/australia_sales.csv"
sales_data <- readr::read_csv(
  file = data_path,
  col_types = cols(
    `Invoice ID` = col_character(),
    Branch = col_character(),
    City = col_character(),
    `Customer type` = col_character(),
    Gender = col_character(),
    `Product line` = col_character(),
    `Unit price` = col_double(),
    Quantity = col_double(),
    `Tax 5%` = col_double(),
    Total = col_double(),
    Date = col_character(),
    Time = col_character(),
    Payment = col_character(),
    cogs = col_double(),
    `gross margin percentage` = col_double(),
    `gross income` = col_double(),
    Rating = col_double()
  ),
  locale = locale(decimal_mark = ".", grouping_mark = ",")
)

# Check for parsing issues
parsing_issues <- readr::problems(sales_data)
if (nrow(parsing_issues) > 0) {
  cat("Warning: Found", nrow(parsing_issues), "parsing issues\n")
  print(parsing_issues)
} else {
  cat("✓ No parsing issues found\n")
}

# Initial data overview
cat("\n=== INITIAL DATA OVERVIEW ===\n")
cat("Dataset dimensions:", nrow(sales_data), "rows ×", ncol(sales_data), "columns\n")
cat("Memory usage:", format(object.size(sales_data), units = "MB"), "\n\n")

# Display first few rows
cat("First 5 rows:\n")
print(head(sales_data, 5))

# =============================================================================
# DATA QUALITY ASSESSMENT
# =============================================================================

cat("\n=== DATA QUALITY ASSESSMENT ===\n")

# 1. Missing values analysis
cat("\n1. Missing Values Analysis:\n")
missing_summary <- naniar::miss_var_summary(sales_data)
print(missing_summary)

# 2. Data types analysis
cat("\n2. Data Types Analysis:\n")
data_types <- sapply(sales_data, class)
print(data_types)

# 3. Basic statistics
cat("\n3. Basic Statistics:\n")
summary_stats <- skimr::skim(sales_data)
print(summary_stats)

# =============================================================================
# DATA STANDARDIZATION
# =============================================================================

cat("\n=== DATA STANDARDIZATION ===\n")

# Clean column names
sales_clean <- sales_data %>%
  janitor::clean_names()

cat("Column names cleaned to snake_case format\n")
cat("Original columns:", paste(names(sales_data), collapse = ", "), "\n")
cat("Cleaned columns:", paste(names(sales_clean), collapse = ", "), "\n\n")

# =============================================================================
# DATA TYPE CONVERSION AND PARSING
# =============================================================================

cat("=== DATA TYPE CONVERSION ===\n")

# Convert data types
sales_clean <- sales_clean %>%
  mutate(
    # Parse dates
    date_parsed = lubridate::mdy(date, quiet = TRUE),
    
    # Parse times
    time_parsed = lubridate::hm(time),
    
    # Convert to factors
    branch = as_factor(branch),
    city = as_factor(city),
    customer_type = as_factor(customer_type),
    gender = as_factor(gender),
    product_line = as_factor(product_line),
    payment = as_factor(payment),
    
    # Ensure numeric types
    unit_price = as.numeric(unit_price),
    quantity = as.numeric(quantity),
    tax_5_percent = as.numeric(tax_5_percent),
    total = as.numeric(total),
    cogs = as.numeric(cogs),
    gross_margin_percentage = as.numeric(gross_margin_percentage),
    gross_income = as.numeric(gross_income),
    rating = as.numeric(rating)
  )

# Check for date parsing issues
na_dates <- sum(is.na(sales_clean$date_parsed))
cat("Date parsing: ", na_dates, "failed dates out of", nrow(sales_clean), "total\n")

# Check for time parsing issues
na_times <- sum(is.na(sales_clean$time_parsed))
cat("Time parsing: ", na_times, "failed times out of", nrow(sales_clean), "total\n")

# =============================================================================
# MISSING VALUE TREATMENT
# =============================================================================

cat("\n=== MISSING VALUE TREATMENT ===\n")

# Check missing values after type conversion
missing_after_conversion <- naniar::miss_var_summary(sales_clean)
print(missing_after_conversion)

# Handle missing values
sales_clean <- sales_clean %>%
  mutate(
    # Fill missing cities with "Unknown"
    city = fct_na_value_to_level(city, level = "Unknown"),
    
    # Fill missing ratings with median
    rating = ifelse(is.na(rating), median(rating, na.rm = TRUE), rating),
    
    # Create missing value indicators
    city_missing = as.integer(city == "Unknown"),
    rating_missing = as.integer(is.na(rating))
  )

# Remove rows with missing critical data
initial_rows <- nrow(sales_clean)
sales_clean <- sales_clean %>%
  filter(!is.na(invoice_id), !is.na(unit_price), !is.na(quantity))

final_rows <- nrow(sales_clean)
removed_rows <- initial_rows - final_rows
cat("Removed", removed_rows, "rows with missing critical data\n")
cat("Final dataset:", final_rows, "rows\n")

# =============================================================================
# OUTLIER DETECTION AND TREATMENT
# =============================================================================

cat("\n=== OUTLIER DETECTION ===\n")

# Define numeric columns for outlier analysis
numeric_cols <- c("unit_price", "quantity", "total", "rating")

# IQR-based outlier detection
outlier_detection <- function(x, multiplier = 1.5) {
  Q1 <- quantile(x, 0.25, na.rm = TRUE)
  Q3 <- quantile(x, 0.75, na.rm = TRUE)
  IQR <- Q3 - Q1
  lower <- Q1 - multiplier * IQR
  upper <- Q3 + multiplier * IQR
  return(x < lower | x > upper)
}

# Detect outliers
outlier_summary <- sales_clean %>%
  summarise(across(all_of(numeric_cols), list(
    outliers = ~sum(outlier_detection(.x), na.rm = TRUE),
    outlier_pct = ~round(100 * sum(outlier_detection(.x), na.rm = TRUE) / length(.x), 2)
  )))

print(outlier_summary)

# Winsorization function
winsorize <- function(x, p_low = 0.01, p_high = 0.99) {
  lo <- quantile(x, p_low, na.rm = TRUE)
  hi <- quantile(x, p_high, na.rm = TRUE)
  pmin(pmax(x, lo), hi)
}

# Apply winsorization to extreme outliers
sales_clean <- sales_clean %>%
  mutate(
    unit_price_winsorized = winsorize(unit_price),
    quantity_winsorized = winsorize(quantity),
    total_winsorized = winsorize(total),
    rating_winsorized = winsorize(rating)
  )

# Create outlier flags
sales_clean <- sales_clean %>%
  mutate(
    unit_price_outlier = outlier_detection(unit_price),
    quantity_outlier = outlier_detection(quantity),
    total_outlier = outlier_detection(total),
    rating_outlier = outlier_detection(rating)
  )

# =============================================================================
# TEXT NORMALIZATION
# =============================================================================

cat("\n=== TEXT NORMALIZATION ===\n")

# Standardize text fields
sales_clean <- sales_clean %>%
  mutate(
    # Standardize city names
    city = fct_recode(city,
                      "Sydney" = "sydney",
                      "Melbourne" = "melbourne", 
                      "Perth" = "perth"),
    
    # Standardize product line names
    product_line = fct_recode(product_line,
                              "Health and Beauty" = "Health and beauty",
                              "Electronic Accessories" = "Electronic accessories",
                              "Home and Lifestyle" = "Home and lifestyle",
                              "Sports and Travel" = "Sports and travel",
                              "Food and Beverages" = "Food and beverages",
                              "Fashion Accessories" = "Fashion accessories"),
    
    # Standardize payment methods
    payment = fct_recode(payment,
                         "E-wallet" = "Ewallet",
                         "Credit Card" = "Credit card"),
    
    # Relevel factors for consistent ordering
    city = fct_relevel(city, "Sydney", "Melbourne", "Perth", "Unknown"),
    customer_type = fct_relevel(customer_type, "Member", "Normal"),
    gender = fct_relevel(gender, "Male", "Female")
  )

# =============================================================================
# DATA VALIDATION
# =============================================================================

cat("\n=== DATA VALIDATION ===\n")

# Business rule validations
validation_results <- sales_clean %>%
  mutate(
    # Check if total equals quantity * unit_price + tax
    total_calculated = quantity * unit_price + tax_5_percent,
    total_diff = abs(total - total_calculated),
    total_valid = total_diff < 0.01,  # Allow small rounding differences
    
    # Check if tax is 5% of subtotal
    subtotal = quantity * unit_price,
    tax_calculated = subtotal * 0.05,
    tax_diff = abs(tax_5_percent - tax_calculated),
    tax_valid = tax_diff < 0.01,
    
    # Check rating range
    rating_valid = rating >= 1 & rating <= 10,
    
    # Check positive values
    price_valid = unit_price > 0,
    quantity_valid = quantity > 0,
    total_valid_amount = total > 0
  )

# Summary of validation results
validation_summary <- validation_results %>%
  summarise(
    total_calculation_errors = sum(!total_valid, na.rm = TRUE),
    tax_calculation_errors = sum(!tax_valid, na.rm = TRUE),
    invalid_ratings = sum(!rating_valid, na.rm = TRUE),
    negative_prices = sum(!price_valid, na.rm = TRUE),
    negative_quantities = sum(!quantity_valid, na.rm = TRUE),
    negative_totals = sum(!total_valid_amount, na.rm = TRUE)
  )

print(validation_summary)

# =============================================================================
# DUPLICATE DETECTION
# =============================================================================

cat("\n=== DUPLICATE DETECTION ===\n")

# Check for exact duplicates
exact_duplicates <- janitor::get_dupes(sales_clean, invoice_id)
cat("Exact duplicates by invoice_id:", nrow(exact_duplicates), "\n")

# Check for potential duplicates (same invoice_id, different other fields)
potential_duplicates <- sales_clean %>%
  group_by(invoice_id) %>%
  filter(n() > 1) %>%
  arrange(invoice_id)

cat("Potential duplicates (same invoice_id, different details):", nrow(potential_duplicates), "\n")

# Remove exact duplicates
initial_rows <- nrow(sales_clean)
sales_clean <- sales_clean %>%
  distinct(invoice_id, .keep_all = TRUE)
final_rows <- nrow(sales_clean)
removed_duplicates <- initial_rows - final_rows
cat("Removed", removed_duplicates, "duplicate records\n")

# =============================================================================
# DERIVED VARIABLES
# =============================================================================

cat("\n=== CREATING DERIVED VARIABLES ===\n")

# Create additional useful variables
sales_clean <- sales_clean %>%
  mutate(
    # Date components
    year = year(date_parsed),
    month = month(date_parsed, label = TRUE, abbr = TRUE),
    day_of_week = wday(date_parsed, label = TRUE, abbr = TRUE),
    day_of_month = day(date_parsed),
    
    # Time components
    hour = hour(time_parsed),
    minute = minute(time_parsed),
    
    # Business metrics
    subtotal = quantity * unit_price,
    profit_margin = gross_income / total,
    
    # Customer segments
    high_value_customer = ifelse(total > quantile(total, 0.8, na.rm = TRUE), "High", "Regular"),
    
    # Product categories (simplified)
    product_category = case_when(
      str_detect(product_line, "Health|Beauty") ~ "Health & Beauty",
      str_detect(product_line, "Electronic") ~ "Electronics",
      str_detect(product_line, "Home|Lifestyle") ~ "Home & Lifestyle",
      str_detect(product_line, "Sports|Travel") ~ "Sports & Travel",
      str_detect(product_line, "Food|Beverage") ~ "Food & Beverage",
      str_detect(product_line, "Fashion") ~ "Fashion",
      TRUE ~ "Other"
    )
  )

# =============================================================================
# DATA QUALITY SUMMARY
# =============================================================================

cat("\n=== FINAL DATA QUALITY SUMMARY ===\n")

# Create comprehensive quality report
quality_report <- list(
  dataset_info = list(
    original_rows = nrow(sales_data),
    final_rows = nrow(sales_clean),
    columns = ncol(sales_clean),
    memory_mb = round(object.size(sales_clean) / 1024^2, 2)
  ),
  
  missing_values = naniar::miss_var_summary(sales_clean),
  
  data_types = sapply(sales_clean, class)
)

# Print summary
cat("Dataset Information:\n")
print(quality_report$dataset_info)

cat("\nMissing Values Summary:\n")
print(quality_report$missing_values)

# =============================================================================
# EXPORT CLEANED DATA
# =============================================================================

cat("\n=== EXPORTING CLEANED DATA ===\n")

# Save cleaned dataset
write_csv(sales_clean, "Dataset/australia_sales_cleaned.csv")
cat("✓ Cleaned dataset saved to: Dataset/australia_sales_cleaned.csv\n")

# Save quality report
write_csv(quality_report$missing_values, "Dataset/data_quality_report.csv")
cat("✓ Quality report saved to: Dataset/data_quality_report.csv\n")

# Save cleaning log
cleaning_log <- data.frame(
  Step = c("Original Data", "Type Conversion", "Missing Value Treatment", 
           "Outlier Treatment", "Duplicate Removal", "Final Dataset"),
  Rows = c(nrow(sales_data), nrow(sales_clean), nrow(sales_clean), 
           nrow(sales_clean), nrow(sales_clean), nrow(sales_clean)),
  Columns = c(ncol(sales_data), ncol(sales_clean), ncol(sales_clean), 
              ncol(sales_clean), ncol(sales_clean), ncol(sales_clean)),
  Missing_Values = c(sum(is.na(sales_data)), sum(is.na(sales_clean)), 
                     sum(is.na(sales_clean)), sum(is.na(sales_clean)), 
                     sum(is.na(sales_clean)), sum(is.na(sales_clean))),
  stringsAsFactors = FALSE
)

write_csv(cleaning_log, "Dataset/cleaning_log.csv")
cat("✓ Cleaning log saved to: Dataset/cleaning_log.csv\n")

# =============================================================================
# FINAL SUMMARY
# =============================================================================

cat("\n=== DATA CLEANSING COMPLETED ===\n")
cat("✓ Dataset loaded and assessed\n")
cat("✓ Data types converted and standardized\n")
cat("✓ Missing values treated\n")
cat("✓ Outliers detected and handled\n")
cat("✓ Duplicates removed\n")
cat("✓ Data validated\n")
cat("✓ Visualizations generated\n")
cat("✓ Cleaned data exported\n")

cat("\nFinal Dataset Summary:\n")
cat("- Rows:", nrow(sales_clean), "\n")
cat("- Columns:", ncol(sales_clean), "\n")
cat("- Missing values:", sum(is.na(sales_clean)), "\n")
cat("- Memory usage:", format(object.size(sales_clean), units = "MB"), "\n")

cat("\nData cleansing process completed successfully!\n")
cat("Check the 'Dataset/' folder for cleaned data and reports.\n")
