# =============================================================================
# MISSING VALUE TREATMENT
# =============================================================================
# File: 06-missing-value-treatment.R
# Purpose: Missing value detection, treatment, and indicator creation functions

#' Handle missing values in the dataset
#' @param data Dataset to process
#' @return Dataset with treated missing values
treat_missing_values <- function(data) {
  cat("\n=== MISSING VALUE TREATMENT ===\n")
  
  # Check missing values after type conversion
  missing_after_conversion <- naniar::miss_var_summary(data)
  print(missing_after_conversion)
  
  # Handle missing values
  data_treated <- data %>%
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
  initial_rows <- nrow(data_treated)
  data_treated <- data_treated %>%
    filter(!is.na(invoice_id), !is.na(unit_price), !is.na(quantity))
  
  final_rows <- nrow(data_treated)
  removed_rows <- initial_rows - final_rows
  cat("Removed", removed_rows, "rows with missing critical data\n")
  cat("Final dataset:", final_rows, "rows\n")
  
  return(data_treated)
}
