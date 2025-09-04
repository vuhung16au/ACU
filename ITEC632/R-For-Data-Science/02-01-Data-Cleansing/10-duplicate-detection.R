# =============================================================================
# DUPLICATE DETECTION
# =============================================================================
# File: 10-duplicate-detection.R
# Purpose: Duplicate detection, analysis, and removal functions

#' Detect and remove duplicate records
#' @param data Dataset to check
#' @return List with cleaned dataset and duplicate info
detect_and_remove_duplicates <- function(data) {
  cat("\n=== DUPLICATE DETECTION ===\n")
  
  # Check for exact duplicates
  exact_duplicates <- janitor::get_dupes(data, invoice_id)
  cat("Exact duplicates by invoice_id:", nrow(exact_duplicates), "\n")
  
  # Check for potential duplicates (same invoice_id, different other fields)
  potential_duplicates <- data %>%
    group_by(invoice_id) %>%
    filter(n() > 1) %>%
    arrange(invoice_id)
  
  cat("Potential duplicates (same invoice_id, different details):", nrow(potential_duplicates), "\n")
  
  # Remove exact duplicates
  initial_rows <- nrow(data)
  data_cleaned <- data %>%
    distinct(invoice_id, .keep_all = TRUE)
  final_rows <- nrow(data_cleaned)
  removed_duplicates <- initial_rows - final_rows
  cat("Removed", removed_duplicates, "duplicate records\n")
  
  return(list(
    data = data_cleaned,
    removed_duplicates = removed_duplicates
  ))
}
