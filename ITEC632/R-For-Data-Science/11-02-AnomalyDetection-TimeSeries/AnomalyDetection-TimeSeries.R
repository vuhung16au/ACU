# Anomaly Detection in Time Series Data
# System Failure Dataset Analysis
# Author: R For Data Science Project
# Date: 2024

# Load required libraries
library(tidyverse)
library(lubridate)
library(anomalize)
library(ggplot2)
library(gridExtra)
library(plotly)
library(zoo)

# Set working directory and create images directory
setwd("/Users/vuhung/00.Work/02.ACU/github/ITEC632/R-For-Data-Science/11-02-AnomalyDetection-TimeSeries")
if (!dir.exists("images")) {
  dir.create("images")
}

# Set theme for white background plots
theme_set(theme_minimal() + 
  theme(plot.background = element_rect(fill = "white", color = NA),
        panel.background = element_rect(fill = "white", color = NA)))

cat("=== Anomaly Detection in Time Series Data ===\n")
cat("Loading System Failure Dataset...\n")

# Load the dataset
data <- read_csv("SystemFailure-Dataset/ambient_temperature_system_failure.csv", 
                 show_col_types = FALSE)

# Display basic information about the dataset
cat("\nDataset Information:\n")
cat("Number of observations:", nrow(data), "\n")
cat("Number of variables:", ncol(data), "\n")

# Convert timestamp to datetime format and handle parsing errors
data <- data %>%
  mutate(timestamp = parse_date_time(timestamp, orders = c("ymd HMS", "ymd HM", "ymd H"), 
                                   truncated = 2, quiet = TRUE)) %>%
  filter(!is.na(timestamp)) %>%
  arrange(timestamp)

cat("Date range:", min(data$timestamp), "to", max(data$timestamp), "\n")

# Display basic statistics
cat("\nBasic Statistics:\n")
print(summary(data$value))

# Create time series plot
cat("\nCreating time series visualization...\n")
p1 <- ggplot(data, aes(x = timestamp, y = value)) +
  geom_line(color = "blue", alpha = 0.7) +
  labs(title = "Ambient Temperature Over Time",
       subtitle = "System Failure Dataset",
       x = "Timestamp",
       y = "Temperature (°F)") +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA),
        panel.background = element_rect(fill = "white", color = NA))

# Save the plot
ggsave("images/01_time_series_plot.png", p1, width = 12, height = 6, dpi = 300, bg = "white")

# Method 1: Statistical Anomaly Detection (Z-Score)
cat("\n=== Method 1: Statistical Anomaly Detection (Z-Score) ===\n")

# Calculate z-scores
data$z_score <- abs(scale(data$value)[,1])
data$is_anomaly_zscore <- data$z_score > 3

# Count anomalies
n_anomalies_zscore <- sum(data$is_anomaly_zscore)
cat("Number of anomalies detected (Z-Score, threshold=3):", n_anomalies_zscore, "\n")
cat("Percentage of anomalies:", round(n_anomalies_zscore/nrow(data)*100, 2), "%\n")

# Create visualization for Z-Score method
p2 <- ggplot(data, aes(x = timestamp, y = value)) +
  geom_line(color = "blue", alpha = 0.7) +
  geom_point(data = data[data$is_anomaly_zscore,], 
             aes(x = timestamp, y = value), 
             color = "red", size = 2) +
  labs(title = "Anomaly Detection - Z-Score Method",
       subtitle = paste("Detected", n_anomalies_zscore, "anomalies"),
       x = "Timestamp",
       y = "Temperature (°F)") +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA),
        panel.background = element_rect(fill = "white", color = NA))

ggsave("images/02_zscore_anomalies.png", p2, width = 12, height = 6, dpi = 300, bg = "white")

# Method 2: IQR-based Anomaly Detection
cat("\n=== Method 2: IQR-based Anomaly Detection ===\n")

# Calculate IQR bounds
Q1 <- quantile(data$value, 0.25)
Q3 <- quantile(data$value, 0.75)
IQR <- Q3 - Q1
lower_bound <- Q1 - 1.5 * IQR
upper_bound <- Q3 + 1.5 * IQR

data$is_anomaly_iqr <- data$value < lower_bound | data$value > upper_bound

n_anomalies_iqr <- sum(data$is_anomaly_iqr)
cat("Number of anomalies detected (IQR method):", n_anomalies_iqr, "\n")
cat("Percentage of anomalies:", round(n_anomalies_iqr/nrow(data)*100, 2), "%\n")
cat("IQR bounds: [", round(lower_bound, 2), ",", round(upper_bound, 2), "]\n")

# Create visualization for IQR method
p3 <- ggplot(data, aes(x = timestamp, y = value)) +
  geom_line(color = "blue", alpha = 0.7) +
  geom_hline(yintercept = lower_bound, color = "orange", linetype = "dashed") +
  geom_hline(yintercept = upper_bound, color = "orange", linetype = "dashed") +
  geom_point(data = data[data$is_anomaly_iqr,], 
             aes(x = timestamp, y = value), 
             color = "red", size = 2) +
  labs(title = "Anomaly Detection - IQR Method",
       subtitle = paste("Detected", n_anomalies_iqr, "anomalies"),
       x = "Timestamp",
       y = "Temperature (°F)") +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA),
        panel.background = element_rect(fill = "white", color = NA))

ggsave("images/03_iqr_anomalies.png", p3, width = 12, height = 6, dpi = 300, bg = "white")

# Method 3: Time Series Decomposition with Anomaly Detection
cat("\n=== Method 3: Time Series Decomposition with Anomaly Detection ===\n")

# Prepare data for anomalize package
data_ts <- data %>%
  select(timestamp, value) %>%
  rename(date = timestamp)

# Try time series decomposition with error handling
tryCatch({
  # Perform time series decomposition and anomaly detection
  anomaly_results <- data_ts %>%
    time_decompose(value, method = "stl", frequency = "1 day", trend = "auto") %>%
    anomalize(remainder, method = "iqr", alpha = 0.05) %>%
    time_recompose()
  
  # Extract anomaly information
  anomaly_results$is_anomaly_decomp <- anomaly_results$anomaly == "Yes"
  n_anomalies_decomp <- sum(anomaly_results$is_anomaly_decomp)
  cat("Number of anomalies detected (Decomposition method):", n_anomalies_decomp, "\n")
  cat("Percentage of anomalies:", round(n_anomalies_decomp/nrow(anomaly_results)*100, 2), "%\n")
  
  # Create visualization for decomposition method
  p4 <- ggplot(anomaly_results, aes(x = date, y = observed)) +
    geom_line(color = "blue", alpha = 0.7) +
    geom_point(data = anomaly_results[anomaly_results$is_anomaly_decomp,], 
               aes(x = date, y = observed), 
               color = "red", size = 2) +
    labs(title = "Anomaly Detection - Time Series Decomposition",
         subtitle = paste("Detected", n_anomalies_decomp, "anomalies"),
         x = "Timestamp",
         y = "Temperature (°F)") +
    theme_minimal() +
    theme(plot.background = element_rect(fill = "white", color = NA),
          panel.background = element_rect(fill = "white", color = NA))
  
  ggsave("images/04_decomposition_anomalies.png", p4, width = 12, height = 6, dpi = 300, bg = "white")
  
}, error = function(e) {
  cat("Error in time series decomposition:", e$message, "\n")
  cat("Using alternative method: Moving average with threshold\n")
  
  # Alternative method: Moving average with threshold
  window_size <- 24  # 24 hours
  data$ma <- zoo::rollmean(data$value, k = window_size, fill = NA, align = "center")
  data$ma_diff <- abs(data$value - data$ma)
  threshold <- quantile(data$ma_diff, 0.95, na.rm = TRUE)
  data$is_anomaly_decomp <- data$ma_diff > threshold & !is.na(data$ma_diff)
  
  n_anomalies_decomp <- sum(data$is_anomaly_decomp, na.rm = TRUE)
  cat("Number of anomalies detected (Alternative method):", n_anomalies_decomp, "\n")
  cat("Percentage of anomalies:", round(n_anomalies_decomp/nrow(data)*100, 2), "%\n")
  
  # Create visualization for alternative method
  p4 <- ggplot(data, aes(x = timestamp, y = value)) +
    geom_line(color = "blue", alpha = 0.7) +
    geom_point(data = data[data$is_anomaly_decomp,], 
               aes(x = timestamp, y = value), 
               color = "red", size = 2) +
    labs(title = "Anomaly Detection - Alternative Method",
         subtitle = paste("Detected", n_anomalies_decomp, "anomalies"),
         x = "Timestamp",
         y = "Temperature (°F)") +
    theme_minimal() +
    theme(plot.background = element_rect(fill = "white", color = NA),
          panel.background = element_rect(fill = "white", color = NA))
  
  ggsave("images/04_decomposition_anomalies.png", p4, width = 12, height = 6, dpi = 300, bg = "white")
})

# Method 4: Isolation Forest (if available)
cat("\n=== Method 4: Isolation Forest Anomaly Detection ===\n")

# Try to use isolation forest if available
if (require(isotree, quietly = TRUE)) {
  # Prepare data for isolation forest
  iso_data <- data.frame(value = data$value)
  
  # Fit isolation forest
  iso_model <- isolation.forest(iso_data, ntrees = 100, sample_size = 256)
  
  # Predict anomalies
  iso_predictions <- predict(iso_model, iso_data)
  data$is_anomaly_iso <- iso_predictions > 0.5
  
  n_anomalies_iso <- sum(data$is_anomaly_iso)
  cat("Number of anomalies detected (Isolation Forest):", n_anomalies_iso, "\n")
  cat("Percentage of anomalies:", round(n_anomalies_iso/nrow(data)*100, 2), "%\n")
  
  # Create visualization for isolation forest
  p5 <- ggplot(data, aes(x = timestamp, y = value)) +
    geom_line(color = "blue", alpha = 0.7) +
    geom_point(data = data[data$is_anomaly_iso,], 
               aes(x = timestamp, y = value), 
               color = "red", size = 2) +
    labs(title = "Anomaly Detection - Isolation Forest",
         subtitle = paste("Detected", n_anomalies_iso, "anomalies"),
         x = "Timestamp",
         y = "Temperature (°F)") +
    theme_minimal() +
    theme(plot.background = element_rect(fill = "white", color = NA),
          panel.background = element_rect(fill = "white", color = NA))
  
  ggsave("images/05_isolation_forest_anomalies.png", p5, width = 12, height = 6, dpi = 300, bg = "white")
} else {
  cat("Isolation Forest package not available. Skipping this method.\n")
  data$is_anomaly_iso <- FALSE
  n_anomalies_iso <- 0
}

# Comparison of Methods
cat("\n=== Comparison of Anomaly Detection Methods ===\n")
comparison_df <- data.frame(
  Method = c("Z-Score", "IQR", "Decomposition", "Isolation Forest"),
  Anomalies_Detected = c(n_anomalies_zscore, n_anomalies_iqr, n_anomalies_decomp, n_anomalies_iso),
  Percentage = c(round(n_anomalies_zscore/nrow(data)*100, 2),
                 round(n_anomalies_iqr/nrow(data)*100, 2),
                 round(n_anomalies_decomp/nrow(data)*100, 2),
                 round(n_anomalies_iso/nrow(data)*100, 2))
)

print(comparison_df)

# Create comparison plot
p6 <- ggplot(comparison_df, aes(x = Method, y = Anomalies_Detected, fill = Method)) +
  geom_bar(stat = "identity") +
  geom_text(aes(label = paste(Anomalies_Detected, "(", Percentage, "%)")), 
            vjust = -0.5, size = 3) +
  labs(title = "Comparison of Anomaly Detection Methods",
       subtitle = "Number of anomalies detected by each method",
       x = "Detection Method",
       y = "Number of Anomalies") +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA),
        panel.background = element_rect(fill = "white", color = NA),
        legend.position = "none")

ggsave("images/06_method_comparison.png", p6, width = 10, height = 6, dpi = 300, bg = "white")

# Create comprehensive visualization
cat("\nCreating comprehensive visualization...\n")

# Combine all methods in one plot
data_combined <- data %>%
  select(timestamp, value, is_anomaly_zscore, is_anomaly_iqr) %>%
  mutate(anomaly_any = is_anomaly_zscore | is_anomaly_iqr)

p7 <- ggplot(data_combined, aes(x = timestamp, y = value)) +
  geom_line(color = "blue", alpha = 0.7) +
  geom_point(data = data_combined[data_combined$is_anomaly_zscore,], 
             aes(x = timestamp, y = value), 
             color = "red", size = 1.5, alpha = 0.7) +
  geom_point(data = data_combined[data_combined$is_anomaly_iqr,], 
             aes(x = timestamp, y = value), 
             color = "orange", size = 1.5, alpha = 0.7) +
  labs(title = "Comprehensive Anomaly Detection Results",
       subtitle = "Red: Z-Score anomalies, Orange: IQR anomalies",
       x = "Timestamp",
       y = "Temperature (°F)") +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA),
        panel.background = element_rect(fill = "white", color = NA))

ggsave("images/07_comprehensive_anomalies.png", p7, width = 14, height = 8, dpi = 300, bg = "white")

# Statistical Summary
cat("\n=== Statistical Summary ===\n")
cat("Dataset size:", nrow(data), "observations\n")
cat("Time period:", min(data$timestamp), "to", max(data$timestamp), "\n")
cat("Temperature range:", round(min(data$value), 2), "to", round(max(data$value), 2), "°F\n")
cat("Mean temperature:", round(mean(data$value), 2), "°F\n")
cat("Standard deviation:", round(sd(data$value), 2), "°F\n")

# Performance metrics for each method
cat("\n=== Performance Metrics ===\n")
cat("Z-Score method: Detected", n_anomalies_zscore, "anomalies (", 
    round(n_anomalies_zscore/nrow(data)*100, 2), "%)\n")
cat("IQR method: Detected", n_anomalies_iqr, "anomalies (", 
    round(n_anomalies_iqr/nrow(data)*100, 2), "%)\n")
cat("Decomposition method: Detected", n_anomalies_decomp, "anomalies (", 
    round(n_anomalies_decomp/nrow(data)*100, 2), "%)\n")
if (n_anomalies_iso > 0) {
  cat("Isolation Forest: Detected", n_anomalies_iso, "anomalies (", 
      round(n_anomalies_iso/nrow(data)*100, 2), "%)\n")
}

cat("\n=== Analysis Complete ===\n")
cat("All plots saved to 'images/' directory\n")
cat("Generated files:\n")
cat("- 01_time_series_plot.png\n")
cat("- 02_zscore_anomalies.png\n")
cat("- 03_iqr_anomalies.png\n")
cat("- 04_decomposition_anomalies.png\n")
if (n_anomalies_iso > 0) {
  cat("- 05_isolation_forest_anomalies.png\n")
}
cat("- 06_method_comparison.png\n")
cat("- 07_comprehensive_anomalies.png\n")
