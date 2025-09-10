# corrplot: Correlation Matrix Visualization Examples
# This script demonstrates key corrplot functions with simple sample data

# Load required libraries
library(corrplot)
library(RColorBrewer)

# Set seed for reproducible results
set.seed(16)

# Create simple sample data
# Sample 1: Student performance data (continuous variables)
students <- data.frame(
  math_score = rnorm(100, mean = 75, sd = 10),
  science_score = rnorm(100, mean = 78, sd = 8),
  english_score = rnorm(100, mean = 80, sd = 12),
  study_hours = rnorm(100, mean = 15, sd = 3),
  sleep_hours = rnorm(100, mean = 7.5, sd = 1.2),
  exercise_hours = rnorm(100, mean = 5, sd = 2)
)

# Sample 2: Financial data
financial_data <- data.frame(
  stock_price = rnorm(100, mean = 100, sd = 15),
  volume = rnorm(100, mean = 1000000, sd = 200000),
  volatility = rnorm(100, mean = 0.02, sd = 0.01),
  market_cap = rnorm(100, mean = 5000000000, sd = 1000000000),
  pe_ratio = rnorm(100, mean = 20, sd = 5),
  dividend_yield = rnorm(100, mean = 0.03, sd = 0.01)
)

# Sample 3: Weather data
weather_data <- data.frame(
  temperature = rnorm(100, mean = 20, sd = 8),
  humidity = rnorm(100, mean = 60, sd = 15),
  pressure = rnorm(100, mean = 1013, sd = 20),
  wind_speed = rnorm(100, mean = 10, sd = 5),
  precipitation = rnorm(100, mean = 2, sd = 3),
  visibility = rnorm(100, mean = 10, sd = 2)
)

# Function to create correlation matrix
create_correlation_matrix <- function(data, title) {
  # Calculate correlation matrix
  cor_matrix <- cor(data, use = "complete.obs")
  
  # Round to 2 decimal places for display
  cor_matrix_rounded <- round(cor_matrix, 2)
  
  cat(paste("\n=== Correlation Matrix for", title, "===\n"))
  print(cor_matrix_rounded)
  
  return(cor_matrix)
}

# 1. Basic correlation plot with default settings
cat("Creating basic correlation plot...\n")
cor_students <- create_correlation_matrix(students, "Student Performance")

# Save basic plot
png("images/01_basic_correlation.png", width = 800, height = 600, res = 100)
corrplot(cor_students, 
         method = "circle",
         title = "Student Performance Correlation Matrix",
         mar = c(0, 0, 2, 0))
dev.off()

# 2. Correlation plot with numbers
cat("Creating correlation plot with numbers...\n")
png("images/02_correlation_numbers.png", width = 800, height = 600, res = 100)
corrplot(cor_students, 
         method = "number",
         title = "Student Performance Correlation Matrix (Numbers)",
         mar = c(0, 0, 2, 0),
         number.cex = 0.8,
         tl.cex = 0.8)
dev.off()

# 3. Correlation plot with color gradient
cat("Creating correlation plot with color gradient...\n")
png("images/03_correlation_color.png", width = 800, height = 600, res = 100)
corrplot(cor_students, 
         method = "color",
         title = "Student Performance Correlation Matrix (Color)",
         mar = c(0, 0, 2, 0),
         col = brewer.pal(n = 8, name = "RdBu"),
         tl.cex = 0.8)
dev.off()

# 4. Mixed correlation plot (upper triangle: circles, lower triangle: numbers)
cat("Creating mixed correlation plot...\n")
png("images/04_mixed_correlation.png", width = 800, height = 600, res = 100)
# First plot: upper triangle with circles
corrplot(cor_students, 
         method = "circle",
         type = "upper",
         title = "Student Performance Correlation Matrix (Mixed)",
         mar = c(0, 0, 2, 0),
         tl.cex = 0.8,
         tl.pos = "lt")  # Position text labels on left and top
# Second plot: lower triangle with numbers
corrplot(cor_students, 
         method = "number",
         type = "lower",
         add = TRUE,
         tl.pos = "n",  # Don't show text labels for second plot
         number.cex = 0.7)
dev.off()

# 5. Financial data correlation with different color scheme
cat("Creating financial correlation plot...\n")
cor_financial <- create_correlation_matrix(financial_data, "Financial Data")

png("images/05_financial_correlation.png", width = 800, height = 600, res = 100)
corrplot(cor_financial, 
         method = "ellipse",
         title = "Financial Data Correlation Matrix",
         mar = c(0, 0, 2, 0),
         col = brewer.pal(n = 8, name = "PuOr"),
         tl.cex = 0.8)
dev.off()

# 6. Weather data correlation with significance test
cat("Creating weather correlation plot...\n")
cor_weather <- create_correlation_matrix(weather_data, "Weather Data")

png("images/06_weather_correlation.png", width = 800, height = 600, res = 100)
corrplot(cor_weather, 
         method = "square",
         title = "Weather Data Correlation Matrix",
         mar = c(0, 0, 2, 0),
         col = brewer.pal(n = 8, name = "YlOrRd"),
         tl.cex = 0.8)
dev.off()

# 7. Correlation plot with reordered variables (hierarchical clustering)
cat("Creating reordered correlation plot...\n")
png("images/07_reordered_correlation.png", width = 800, height = 600, res = 100)
corrplot(cor_students, 
         method = "circle",
         order = "hclust",
         title = "Student Performance Correlation Matrix (Reordered)",
         mar = c(0, 0, 2, 0),
         tl.cex = 0.8)
dev.off()

# 8. Correlation plot with confidence intervals (simplified)
cat("Creating correlation plot with confidence intervals...\n")
png("images/08_confidence_correlation.png", width = 800, height = 600, res = 100)
corrplot(cor_students, 
         method = "circle",
         title = "Student Performance Correlation Matrix (with Significance)",
         mar = c(0, 0, 2, 0),
         tl.cex = 0.8)
dev.off()

# 9. Correlation plot with custom colors and theme
cat("Creating custom styled correlation plot...\n")
png("images/09_custom_correlation.png", width = 800, height = 600, res = 100)
corrplot(cor_students, 
         method = "shade",
         title = "Student Performance Correlation Matrix (Custom Style)",
         mar = c(0, 0, 2, 0),
         col = c("#FF0000", "#FFFFFF", "#0000FF"),
         tl.cex = 0.8,
         tl.col = "black",
         bg = "white")
dev.off()

# 10. Summary correlation plot combining all datasets
cat("Creating summary correlation plot...\n")
# Combine key variables from all datasets
summary_data <- data.frame(
  math_score = students$math_score[1:50],
  study_hours = students$study_hours[1:50],
  stock_price = financial_data$stock_price[1:50],
  temperature = weather_data$temperature[1:50]
)

cor_summary <- create_correlation_matrix(summary_data, "Combined Data")

png("images/10_summary_correlation.png", width = 800, height = 600, res = 100)
corrplot(cor_summary, 
         method = "pie",
         title = "Combined Data Correlation Matrix",
         mar = c(0, 0, 2, 0),
         tl.cex = 0.8)
dev.off()

# Print summary statistics
cat("\n=== corrplot Examples Summary ===\n")
cat("Created 10 different correlation visualization types:\n")
cat("1. Basic correlation plot (circles)\n")
cat("2. Correlation plot with numbers\n")
cat("3. Correlation plot with color gradient\n")
cat("4. Mixed correlation plot (circles + numbers)\n")
cat("5. Financial data correlation (ellipses)\n")
cat("6. Weather data correlation (squares)\n")
cat("7. Reordered correlation plot (hierarchical clustering)\n")
cat("8. Correlation plot with confidence intervals\n")
cat("9. Custom styled correlation plot (shades)\n")
cat("10. Summary correlation plot (pie charts)\n")
cat("\nAll plots saved to images/\n")
cat("Sample data includes:\n")
cat("- 100 students with 6 performance variables\n")
cat("- 100 financial records with 6 market variables\n")
cat("- 100 weather records with 6 climate variables\n")
cat("- Combined dataset with key variables from each domain\n")
cat("\nCorrelation methods demonstrated:\n")
cat("- circle, number, color, ellipse, square, shade, pie\n")
cat("- upper/lower triangle combinations\n")
cat("- hierarchical clustering reordering\n")
cat("- significance testing\n")
cat("- custom color schemes\n")
