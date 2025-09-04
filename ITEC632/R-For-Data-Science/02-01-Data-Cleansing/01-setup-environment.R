# =============================================================================
# SETUP AND PACKAGE LOADING
# =============================================================================
# File: 01-setup-environment.R
# Purpose: Environment setup and package management functions

#' Setup environment and load required packages
#' @return None
setup_environment <- function() {
  # Clear workspace
  rm(list = ls())
  
  # Install packages if needed
  required_packages <- c("tidyverse", "janitor", "naniar", "lubridate", 
                         "stringr", "forcats", "skimr", "readxl", "here", "gridExtra")
  
  install_if_missing <- function(package) {
    if (!require(package, character.only = TRUE)) {
      install.packages(package, dependencies = TRUE, repos = "https://cran.rstudio.com/")
      library(package, character.only = TRUE)
    }
  }
  
  # Load all required packages
  invisible(sapply(required_packages, install_if_missing))
  
  # Use current working directory
  cat("Current working directory:", getwd(), "\n")
  
  # Create output directories
  dir.create("images", showWarnings = FALSE, recursive = TRUE)
  dir.create("Dataset", showWarnings = FALSE, recursive = TRUE)
}
