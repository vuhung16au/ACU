# =============================================================================
# DATA QUALITY SUMMARY
# =============================================================================
# File: 12-data-quality-summary.R
# Purpose: Comprehensive data quality reporting and summary functions

#' Generate comprehensive data quality summary
#' @param original_data Original dataset
#' @param cleaned_data Cleaned dataset
#' @return Quality report list
generate_quality_summary <- function(original_data, cleaned_data) {
  cat("\n=== FINAL DATA QUALITY SUMMARY ===\n")
  
  # Create comprehensive quality report
  quality_report <- list(
    dataset_info = list(
      original_rows = nrow(original_data),
      final_rows = nrow(cleaned_data),
      columns = ncol(cleaned_data),
      memory_mb = round(object.size(cleaned_data) / 1024^2, 2)
    ),
    
    missing_values = naniar::miss_var_summary(cleaned_data),
    
    data_types = sapply(cleaned_data, class),
    
    categorical_summary = cleaned_data %>%
      select_if(is.factor) %>%
      summarise_all(list(
        levels = ~length(levels(.)),
        missing = ~sum(is.na(.))
      )),
    
    numeric_summary = cleaned_data %>%
      select_if(is.numeric) %>%
      summarise_all(list(
        mean = ~round(mean(., na.rm = TRUE), 2),
        median = ~round(median(., na.rm = TRUE), 2),
        sd = ~round(sd(., na.rm = TRUE), 2),
        min = ~min(., na.rm = TRUE),
        max = ~max(., na.rm = TRUE),
        missing = ~sum(is.na(.))
      ))
  )
  
  # Print summary
  cat("Dataset Information:\n")
  print(quality_report$dataset_info)
  
  cat("\nMissing Values Summary:\n")
  print(quality_report$missing_values)
  
  return(quality_report)
}
