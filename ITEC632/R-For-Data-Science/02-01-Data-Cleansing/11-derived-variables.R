# =============================================================================
# DERIVED VARIABLES
# =============================================================================
# File: 11-derived-variables.R
# Purpose: Creation of new variables and features from existing data

#' Create additional useful variables
#' @param data Dataset to enhance
#' @return Dataset with derived variables
create_derived_variables <- function(data) {
  cat("\n=== CREATING DERIVED VARIABLES ===\n")
  
  # Create additional useful variables
  data_enhanced <- data %>%
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
  
  return(data_enhanced)
}
