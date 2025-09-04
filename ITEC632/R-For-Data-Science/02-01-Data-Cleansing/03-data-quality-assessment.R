# =============================================================================
# DATA QUALITY ASSESSMENT
# =============================================================================
# File: 03-data-quality-assessment.R
# Purpose: Data quality assessment and initial visualization functions

#' Perform comprehensive data quality assessment
#' @param data Dataset to assess
#' @return List containing quality assessment results
assess_data_quality <- function(data) {
  cat("\n=== DATA QUALITY ASSESSMENT ===\n")
  
  # 1. Missing values analysis
  cat("\n1. Missing Values Analysis:\n")
  missing_summary <- naniar::miss_var_summary(data)
  print(missing_summary)
  
  # 2. Data types analysis
  cat("\n2. Data Types Analysis:\n")
  data_types <- sapply(data, class)
  print(data_types)
  
  # 3. Basic statistics
  cat("\n3. Basic Statistics:\n")
  summary_stats <- skimr::skim(data)
  print(summary_stats)
  
  # Return assessment results
  return(list(
    missing_summary = missing_summary,
    data_types = data_types,
    summary_stats = summary_stats
  ))
}

#' Generate initial data overview visualization
#' @param data Dataset to visualize
#' @return None
generate_initial_visualization <- function(data) {
  # Visualize missing values
  png("images/01_data_overview.png", width = 1400, height = 900, res = 150, bg = "white")
  naniar::vis_miss(data, warn_large_data = FALSE) +
    theme_minimal() +
    theme(plot.background = element_rect(fill = "white", color = NA),
          text = element_text(size = 12),
          axis.text = element_text(size = 10),
          plot.title = element_text(size = 16, face = "bold"),
          plot.subtitle = element_text(size = 14)) +
    labs(title = "Missing Values Pattern in Original Dataset",
         subtitle = "White = Missing, Black = Present")
  dev.off()
}
