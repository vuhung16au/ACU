# =============================================================================
# MAIN EXECUTION FUNCTION
# =============================================================================
# File: 15-main-execution.R
# Purpose: Main orchestration function and execution logic

#' Main function to execute the complete data cleansing workflow
#' @return None
main <- function() {
  # 1. Setup environment
  setup_environment()
  
  # 2. Load and assess data
  sales_data <- load_dataset()
  check_parsing_issues(sales_data)
  display_data_overview(sales_data)
  
  # 3. Assess data quality
  quality_assessment <- assess_data_quality(sales_data)
  generate_initial_visualization(sales_data)
  
  # 4. Standardize data
  sales_clean <- standardize_data(sales_data)
  
  # 5. Convert data types
  sales_clean <- convert_data_types(sales_clean)
  
  # 6. Treat missing values
  sales_clean <- treat_missing_values(sales_clean)
  
  # 7. Detect and treat outliers
  sales_clean <- detect_and_treat_outliers(sales_clean)
  
  # 8. Normalize text fields
  sales_clean <- normalize_text_fields(sales_clean)
  
  # 9. Validate data
  validation_results <- validate_data(sales_clean)
  
  # 10. Detect and remove duplicates
  duplicate_results <- detect_and_remove_duplicates(sales_clean)
  sales_clean <- duplicate_results$data
  removed_duplicates <- duplicate_results$removed_duplicates
  
  # 11. Create derived variables
  sales_clean <- create_derived_variables(sales_clean)
  
  # 12. Generate quality summary
  quality_report <- generate_quality_summary(sales_data, sales_clean)
  
  # 13. Generate visualizations
  generate_visualizations(sales_data, sales_clean, removed_duplicates)
  
  # 14. Export cleaned data
  export_cleaned_data(sales_data, sales_clean, quality_report, removed_duplicates)
  
  # 15. Final summary
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
}

# Execute the main function if script is run directly
if (!interactive()) {
  main()
}
