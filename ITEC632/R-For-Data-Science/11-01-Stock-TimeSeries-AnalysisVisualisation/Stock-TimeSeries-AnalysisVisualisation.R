# Stock Time Series Analysis and Visualization in R
# Author: Data Science Project
# Date: 2024

# Load required libraries
library(ggplot2)
library(dplyr)
library(lubridate)
library(tseries)
library(zoo)
library(gridExtra)

# Set working directory and create images folder
setwd("/Users/vuhung/00.Work/02.ACU/github/ITEC632/R-For-Data-Science/11-01-Stock-TimeSeries-AnalysisVisualisation")
if (!dir.exists("images")) {
  dir.create("images")
}

# Set theme for white background plots
theme_set(theme_minimal() + 
          theme(panel.background = element_rect(fill = "white", color = NA),
                plot.background = element_rect(fill = "white", color = NA),
                panel.grid.major = element_line(color = "gray90"),
                panel.grid.minor = element_line(color = "gray95")))

# 1. Load and Explore Data
cat("Loading stock data...\n")
df <- read.csv("Stock-Dataset/stock_data.csv", stringsAsFactors = FALSE)

# Remove the unnamed first column if it exists
if (names(df)[1] == "X" || names(df)[1] == "") {
  df <- df[, -1]
}

# Convert Date column to proper date format
df$Date <- as.Date(df$Date, format = "%m/%d/%Y")

# Display first few rows
cat("First 5 rows of the dataset:\n")
print(head(df, 5))

# Basic data summary
cat("\nDataset summary:\n")
print(summary(df))

# 2. Time Series Visualization - High Price Over Time
cat("\nCreating time series plot for High price...\n")
p1 <- ggplot(df, aes(x = Date, y = High)) +
  geom_line(color = "blue", linewidth = 0.8) +
  labs(title = "Share Highest Price Over Time",
       x = "Date",
       y = "High Price") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA))

# Save plot
ggsave("images/high_price_time_series.png", p1, width = 12, height = 6, dpi = 300, bg = "white")

# 3. Monthly Resampling
cat("Creating monthly resampling plot...\n")
# Create monthly aggregated data
df_monthly <- df %>%
  mutate(YearMonth = floor_date(Date, "month")) %>%
  group_by(YearMonth) %>%
  summarise(High = mean(High, na.rm = TRUE),
            Open = mean(Open, na.rm = TRUE),
            Low = mean(Low, na.rm = TRUE),
            Close = mean(Close, na.rm = TRUE),
            Volume = mean(Volume, na.rm = TRUE),
            .groups = 'drop')

p2 <- ggplot(df_monthly, aes(x = YearMonth, y = High)) +
  geom_line(color = "blue", linewidth = 0.8) +
  labs(title = "Monthly Resampling Highest Price Over Time",
       x = "Date (Monthly)",
       y = "High Price") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA))

# Save plot
ggsave("images/monthly_high_price.png", p2, width = 12, height = 6, dpi = 300, bg = "white")

# 4. Autocorrelation Function (ACF) Plot
cat("Creating ACF plot for Volume...\n")
# Create time series object for Volume
volume_ts <- ts(df$Volume, start = c(2006, 1), frequency = 252)  # 252 trading days per year

# Create ACF plot
png("images/acf_volume.png", width = 12, height = 6, units = "in", res = 300, bg = "white")
acf_result <- acf(volume_ts, lag.max = 40, main = "Autocorrelation Function (ACF) Plot for Volume",
                  xlab = "Lag", ylab = "Autocorrelation")
dev.off()

# 5. Augmented Dickey-Fuller Test for Stationarity
cat("Performing ADF test for High price...\n")
adf_result_high <- adf.test(df$High)
cat("ADF Test Results for High Price:\n")
cat("ADF Statistic:", adf_result_high$statistic, "\n")
cat("p-value:", adf_result_high$p.value, "\n")
cat("Critical Values:\n")
print(adf_result_high$critical)

# 6. Differencing to Achieve Stationarity
cat("Creating differenced series...\n")
df$high_diff <- c(NA, diff(df$High))

# Plot original vs differenced series
p3 <- ggplot(df, aes(x = Date)) +
  geom_line(aes(y = High, color = "Original High"), linewidth = 0.8) +
  geom_line(aes(y = high_diff, color = "Differenced High"), linetype = "dashed", linewidth = 0.8) +
  scale_color_manual(values = c("Original High" = "blue", "Differenced High" = "green")) +
  labs(title = "Original vs Differenced High Price",
       x = "Date",
       y = "Price",
       color = "Series") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA),
        legend.position = "bottom")

# Save plot
ggsave("images/original_vs_differenced.png", p3, width = 12, height = 6, dpi = 300, bg = "white")

# 7. Moving Average Smoothing
cat("Creating moving average plot...\n")
window_size <- 120
df$high_smoothed <- rollmean(df$High, k = window_size, fill = NA, align = "right")

p4 <- ggplot(df, aes(x = Date)) +
  geom_line(aes(y = High, color = "Original High"), linewidth = 0.8) +
  geom_line(aes(y = high_smoothed, color = "Moving Average"), 
            linetype = "dashed", linewidth = 0.8) +
  scale_color_manual(values = c("Original High" = "blue", 
                               "Moving Average" = "orange")) +
  labs(title = "Original vs Moving Average",
       x = "Date",
       y = "High Price",
       color = "Series") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA),
        legend.position = "bottom")

# Save plot
ggsave("images/original_vs_moving_average.png", p4, width = 12, height = 6, dpi = 300, bg = "white")

# 8. ADF Test on Differenced Series
cat("Performing ADF test on differenced series...\n")
# Remove NA values for ADF test
high_diff_clean <- df$high_diff[!is.na(df$high_diff)]
adf_result_diff <- adf.test(high_diff_clean)
cat("ADF Test Results for Differenced High Price:\n")
cat("ADF Statistic:", adf_result_diff$statistic, "\n")
cat("p-value:", adf_result_diff$p.value, "\n")
cat("Critical Values:\n")
print(adf_result_diff$critical)

# 9. Multiple Price Metrics Visualization
cat("Creating multiple price metrics plot...\n")
# Reshape data for multiple metrics
df_long <- df %>%
  select(Date, Open, High, Low, Close) %>%
  tidyr::pivot_longer(cols = c(Open, High, Low, Close), 
                      names_to = "Price_Type", 
                      values_to = "Price")

p5 <- ggplot(df_long, aes(x = Date, y = Price, color = Price_Type)) +
  geom_line(linewidth = 0.8) +
  labs(title = "Stock Price Metrics Over Time",
       x = "Date",
       y = "Price",
       color = "Price Type") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA),
        legend.position = "bottom")

# Save plot
ggsave("images/multiple_price_metrics.png", p5, width = 12, height = 6, dpi = 300, bg = "white")

# 10. Volume Analysis
cat("Creating volume analysis plot...\n")
p6 <- ggplot(df, aes(x = Date, y = Volume)) +
  geom_line(color = "purple", linewidth = 0.8) +
  labs(title = "Trading Volume Over Time",
       x = "Date",
       y = "Volume") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA))

# Save plot
ggsave("images/volume_analysis.png", p6, width = 12, height = 6, dpi = 300, bg = "white")

# 11. Statistical Summary
cat("\nStatistical Summary of the Analysis:\n")
cat("=====================================\n")
cat("Dataset period:", min(df$Date), "to", max(df$Date), "\n")
cat("Total observations:", nrow(df), "\n")
cat("High price range:", min(df$High, na.rm = TRUE), "to", max(df$High, na.rm = TRUE), "\n")
cat("Volume range:", min(df$Volume, na.rm = TRUE), "to", max(df$Volume, na.rm = TRUE), "\n")

# Stationarity test summary
cat("\nStationarity Test Results:\n")
cat("Original High Price - ADF Statistic:", round(adf_result_high$statistic, 4), 
    ", p-value:", round(adf_result_high$p.value, 6), "\n")
cat("Differenced High Price - ADF Statistic:", round(adf_result_diff$statistic, 4), 
    ", p-value:", round(adf_result_diff$p.value, 6), "\n")

if (adf_result_diff$p.value < 0.05) {
  cat("✓ Differenced series is stationary (p < 0.05)\n")
} else {
  cat("✗ Differenced series may not be stationary (p >= 0.05)\n")
}

cat("\nAnalysis completed successfully!\n")
cat("All plots saved in the 'images' folder.\n")
