# =============================================================================
# MASTER SCRIPT - DATA CLEANSING ESSENTIALS WITH R
# =============================================================================
# File: 00-master-script.R
# Purpose: Master script that sources all modular data cleansing functions
# Author: Data Science Project
# Date: 2024

# =============================================================================
# LOAD ALL MODULAR FUNCTIONS
# =============================================================================

cat("Loading Data Cleansing Modules...\n")

# Source all modular function files
source("01-setup-environment.R")
source("02-data-loading.R")
source("03-data-quality-assessment.R")
source("04-data-standardization.R")
source("05-data-type-conversion.R")
source("06-missing-value-treatment.R")
source("07-outlier-detection.R")
source("08-text-normalization.R")
source("09-data-validation.R")
source("10-duplicate-detection.R")
source("11-derived-variables.R")
source("12-data-quality-summary.R")
source("13-visualizations.R")
source("14-export-data.R")
source("15-main-execution.R")

cat("✓ All modules loaded successfully!\n\n")

# =============================================================================
# EXECUTION OPTIONS
# =============================================================================

cat("Data Cleansing Modules Ready!\n")
cat("Available functions:\n")
cat("- setup_environment()\n")
cat("- load_dataset()\n")
cat("- check_parsing_issues()\n")
cat("- display_data_overview()\n")
cat("- assess_data_quality()\n")
cat("- generate_initial_visualization()\n")
cat("- standardize_data()\n")
cat("- convert_data_types()\n")
cat("- treat_missing_values()\n")
cat("- detect_and_treat_outliers()\n")
cat("- normalize_text_fields()\n")
cat("- validate_data()\n")
cat("- detect_and_remove_duplicates()\n")
cat("- create_derived_variables()\n")
cat("- generate_quality_summary()\n")
cat("- generate_visualizations()\n")
cat("- export_cleaned_data()\n")
cat("- main()\n\n")

cat("To run the complete workflow, execute: main()\n")
cat("To run individual steps, call the specific functions as needed.\n\n")

# =============================================================================
# QUICK START EXAMPLES
# =============================================================================

cat("Quick Start Examples:\n")
cat("1. Run complete workflow: main()\n")
cat("2. Load and assess data only:\n")
cat("   sales_data <- load_dataset()\n")
cat("   assess_data_quality(sales_data)\n")
cat("3. Custom workflow:\n")
cat("   setup_environment()\n")
cat("   data <- load_dataset()\n")
cat("   clean_data <- standardize_data(data)\n")
cat("   clean_data <- convert_data_types(clean_data)\n\n")

cat("All functions are now available in your R environment!\n")
