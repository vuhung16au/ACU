# =============================================================================
# DATA TYPE CONVERSION AND PARSING
# =============================================================================
# File: 05-data-type-conversion.R
# Purpose: Data type conversion, date/time parsing, and factor conversion functions

#' Convert data types and parse dates/times
#' @param data Dataset to convert
#' @return Dataset with converted data types
convert_data_types <- function(data) {
  cat("=== DATA TYPE CONVERSION ===\n")
  
  # Convert data types
  data_converted <- data %>%
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
  na_dates <- sum(is.na(data_converted$date_parsed))
  cat("Date parsing: ", na_dates, "failed dates out of", nrow(data_converted), "total\n")
  
  # Check for time parsing issues
  na_times <- sum(is.na(data_converted$time_parsed))
  cat("Time parsing: ", na_times, "failed times out of", nrow(data_converted), "total\n")
  
  return(data_converted)
}
