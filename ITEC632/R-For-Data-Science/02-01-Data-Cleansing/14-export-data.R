# =============================================================================
# EXPORT CLEANED DATA
# =============================================================================
# File: 14-export-data.R
# Purpose: Data export, reporting, and file saving functions

#' Export cleaned data and reports
#' @param original_data Original dataset
#' @param cleaned_data Cleaned dataset
#' @param quality_report Quality report
#' @param removed_duplicates Number of removed duplicates
#' @return None
export_cleaned_data <- function(original_data, cleaned_data, quality_report, removed_duplicates) {
  cat("\n=== EXPORTING CLEANED DATA ===\n")
  
  # Save cleaned dataset
  write_csv(cleaned_data, "Dataset/australia_sales_cleaned.csv")
  cat("✓ Cleaned dataset saved to: Dataset/australia_sales_cleaned.csv\n")
  
  # Save quality report
  write_csv(quality_report$missing_values, "Dataset/data_quality_report.csv")
  cat("✓ Quality report saved to: Dataset/data_quality_report.csv\n")
  
  # Save cleaning log
  cleaning_log <- data.frame(
    Step = c("Original Data", "Type Conversion", "Missing Value Treatment", 
             "Outlier Treatment", "Duplicate Removal", "Final Dataset"),
    Rows = c(nrow(original_data), nrow(cleaned_data), nrow(cleaned_data), 
             nrow(cleaned_data), nrow(cleaned_data), nrow(cleaned_data)),
    Columns = c(ncol(original_data), ncol(cleaned_data), ncol(cleaned_data), 
                ncol(cleaned_data), ncol(cleaned_data), ncol(cleaned_data)),
    Missing_Values = c(sum(is.na(original_data)), sum(is.na(cleaned_data)), 
                       sum(is.na(cleaned_data)), sum(is.na(cleaned_data)), 
                       sum(is.na(cleaned_data)), sum(is.na(cleaned_data))),
    stringsAsFactors = FALSE
  )
  
  write_csv(cleaning_log, "Dataset/cleaning_log.csv")
  cat("✓ Cleaning log saved to: Dataset/cleaning_log.csv\n")
}
