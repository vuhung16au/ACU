# Install R packages required for Store Sales - Time Series Forecasting
# Run once: source("install_dependencies.R")

pkgs <- c(
  "tidyverse",   # data manipulation, ggplot2
  "data.table",  # fast tables and merges
  "lubridate",   # dates
  "xgboost",     # gradient boosting
  "zoo",         # rollapply, na.fill
  "future.apply" # parallel feature computation
)

for (p in pkgs) {
  if (!requireNamespace(p, quietly = TRUE)) {
    install.packages(p, repos = "https://cloud.r-project.org/")
  }
}

message("Done. Load packages in your script with library(...) as in store_sales_forecast.R")
