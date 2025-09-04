# =============================================================================
# DATA LOADING AND INITIAL ASSESSMENT
# =============================================================================
# File: 02-data-loading.R
# Purpose: Data loading, parsing, and initial assessment functions

#' Load dataset with proper column types
#' @param data_path Path to the CSV file
#' @return Loaded dataset as tibble
load_dataset <- function(data_path = "Dataset/australia_sales.csv") {
  cat("=== DATA CLEANSING ESSENTIALS WITH R ===\n")
  cat("Loading Australia Sales Dataset...\n\n")
  
  sales_data <- readr::read_csv(
    file = data_path,
    col_types = cols(
      `Invoice ID` = col_character(),
      Branch = col_character(),
      City = col_character(),
      `Customer type` = col_character(),
      Gender = col_character(),
      `Product line` = col_character(),
      `Unit price` = col_double(),
      Quantity = col_double(),
      `Tax 5%` = col_double(),
      Total = col_double(),
      Date = col_character(),
      Time = col_character(),
      Payment = col_character(),
      cogs = col_double(),
      `gross margin percentage` = col_double(),
      `gross income` = col_double(),
      Rating = col_double()
    ),
    locale = locale(decimal_mark = ".", grouping_mark = ",")
  )
  
  return(sales_data)
}

#' Check for data parsing issues
#' @param data Dataset to check
#' @return None
check_parsing_issues <- function(data) {
  parsing_issues <- readr::problems(data)
  if (nrow(parsing_issues) > 0) {
    cat("Warning: Found", nrow(parsing_issues), "parsing issues\n")
    print(parsing_issues)
  } else {
    cat("✓ No parsing issues found\n")
  }
}

#' Display initial data overview
#' @param data Dataset to analyze
#' @return None
display_data_overview <- function(data) {
  cat("\n=== INITIAL DATA OVERVIEW ===\n")
  cat("Dataset dimensions:", nrow(data), "rows ×", ncol(data), "columns\n")
  cat("Memory usage:", format(object.size(data), units = "MB"), "\n\n")
  
  # Display first few rows
  cat("First 5 rows:\n")
  print(head(data, 5))
  
  # Basic structure
  cat("\nData structure:\n")
  str(data)
}
