# =============================================================================
# DATA VALIDATION
# =============================================================================
# File: 09-data-validation.R
# Purpose: Business rule validation and data integrity check functions

#' Validate data against business rules
#' @param data Dataset to validate
#' @return Validation results summary
validate_data <- function(data) {
  cat("\n=== DATA VALIDATION ===\n")
  
  # Business rule validations
  validation_results <- data %>%
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
  return(validation_summary)
}
