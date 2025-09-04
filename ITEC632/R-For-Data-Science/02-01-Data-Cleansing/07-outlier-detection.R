# =============================================================================
# OUTLIER DETECTION AND TREATMENT
# =============================================================================
# File: 07-outlier-detection.R
# Purpose: Outlier detection, treatment, and winsorization functions

#' Detect and treat outliers in numeric columns
#' @param data Dataset to process
#' @return Dataset with outlier treatment
detect_and_treat_outliers <- function(data) {
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
  outlier_summary <- data %>%
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
  data_treated <- data %>%
    mutate(
      unit_price_winsorized = winsorize(unit_price),
      quantity_winsorized = winsorize(quantity),
      total_winsorized = winsorize(total),
      rating_winsorized = winsorize(rating)
    )
  
  # Create outlier flags
  data_treated <- data_treated %>%
    mutate(
      unit_price_outlier = outlier_detection(unit_price),
      quantity_outlier = outlier_detection(quantity),
      total_outlier = outlier_detection(total),
      rating_outlier = outlier_detection(rating)
    )
  
  return(data_treated)
}
