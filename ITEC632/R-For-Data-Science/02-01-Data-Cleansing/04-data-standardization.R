# =============================================================================
# DATA STANDARDIZATION
# =============================================================================
# File: 04-data-standardization.R
# Purpose: Data standardization and column name cleaning functions

#' Standardize column names and data structure
#' @param data Dataset to standardize
#' @return Standardized dataset
standardize_data <- function(data) {
  cat("\n=== DATA STANDARDIZATION ===\n")
  
  # Clean column names
  sales_clean <- data %>%
    janitor::clean_names()
  
  cat("Column names cleaned to snake_case format\n")
  cat("Original columns:", paste(names(data), collapse = ", "), "\n")
  cat("Cleaned columns:", paste(names(sales_clean), collapse = ", "), "\n\n")
  
  return(sales_clean)
}
