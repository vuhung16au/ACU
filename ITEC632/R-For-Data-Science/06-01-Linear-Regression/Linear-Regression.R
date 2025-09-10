# Linear Regression Analysis - Sales Prediction
# Based on Advertising Dataset

# Load required libraries
library(ggplot2)
library(dplyr)
library(corrplot)
library(gridExtra)

# Set working directory and create images folder
if (!dir.exists("images")) {
  dir.create("images")
}

# Set theme for white background plots
theme_set(theme_minimal() + 
          theme(panel.background = element_rect(fill = "white", color = NA),
                plot.background = element_rect(fill = "white", color = NA)))

cat("=== Linear Regression Analysis: Sales Prediction ===\n")
cat("Loading and understanding the data...\n")

# Load the dataset
advertising <- read.csv("Salary-Dataset/salary.csv")

# Display basic information about the dataset
cat("\nDataset Information:\n")
cat("Shape:", nrow(advertising), "rows,", ncol(advertising), "columns\n")
cat("Column names:", paste(names(advertising), collapse = ", "), "\n")

# Display first few rows
cat("\nFirst 5 rows of the dataset:\n")
print(head(advertising, 5))

# Data inspection
cat("\nData Summary:\n")
print(summary(advertising))

# Check for missing values
cat("\nMissing values check:\n")
missing_values <- colSums(is.na(advertising))
print(missing_values)

# Data cleaning - check for outliers using boxplots
cat("\nCreating outlier analysis plots...\n")

# Create boxplots for outlier analysis
p1 <- ggplot(advertising, aes(y = TV)) + 
  geom_boxplot(fill = "lightblue", color = "black") +
  labs(title = "TV Advertising - Outlier Analysis", y = "TV Advertising (thousands $)") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA))

p2 <- ggplot(advertising, aes(y = Radio)) + 
  geom_boxplot(fill = "lightgreen", color = "black") +
  labs(title = "Radio Advertising - Outlier Analysis", y = "Radio Advertising (thousands $)") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA))

p3 <- ggplot(advertising, aes(y = Newspaper)) + 
  geom_boxplot(fill = "lightcoral", color = "black") +
  labs(title = "Newspaper Advertising - Outlier Analysis", y = "Newspaper Advertising (thousands $)") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA))

# Save outlier analysis plot
png("images/01_outlier_analysis.png", width = 800, height = 600, bg = "white")
grid.arrange(p1, p2, p3, ncol = 1)
dev.off()

cat("Outlier analysis plot saved as: images/01_outlier_analysis.png\n")

# Exploratory Data Analysis
cat("\n=== Exploratory Data Analysis ===\n")

# Univariate Analysis - Sales (Target Variable)
cat("Creating sales distribution plot...\n")
p_sales <- ggplot(advertising, aes(y = Sales)) + 
  geom_boxplot(fill = "gold", color = "black") +
  labs(title = "Sales Distribution - Target Variable", y = "Sales (thousands of units)") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA))

png("images/02_sales_distribution.png", width = 600, height = 400, bg = "white")
print(p_sales)
dev.off()

cat("Sales distribution plot saved as: images/02_sales_distribution.png\n")

# Scatter plots showing relationship between advertising channels and sales
cat("Creating scatter plots for advertising channels vs sales...\n")

p_tv <- ggplot(advertising, aes(x = TV, y = Sales)) + 
  geom_point(color = "blue", alpha = 0.6) +
  geom_smooth(method = "lm", color = "red", se = TRUE) +
  labs(title = "TV Advertising vs Sales", 
       x = "TV Advertising (thousands $)", 
       y = "Sales (thousands of units)") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA))

p_radio <- ggplot(advertising, aes(x = Radio, y = Sales)) + 
  geom_point(color = "green", alpha = 0.6) +
  geom_smooth(method = "lm", color = "red", se = TRUE) +
  labs(title = "Radio Advertising vs Sales", 
       x = "Radio Advertising (thousands $)", 
       y = "Sales (thousands of units)") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA))

p_newspaper <- ggplot(advertising, aes(x = Newspaper, y = Sales)) + 
  geom_point(color = "orange", alpha = 0.6) +
  geom_smooth(method = "lm", color = "red", se = TRUE) +
  labs(title = "Newspaper Advertising vs Sales", 
       x = "Newspaper Advertising (thousands $)", 
       y = "Sales (thousands of units)") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA))

# Save scatter plots
png("images/03_scatter_plots.png", width = 1200, height = 400, bg = "white")
grid.arrange(p_tv, p_radio, p_newspaper, ncol = 3)
dev.off()

cat("Scatter plots saved as: images/03_scatter_plots.png\n")

# Correlation analysis
cat("Creating correlation heatmap...\n")
correlation_matrix <- cor(advertising)
print("Correlation Matrix:")
print(round(correlation_matrix, 3))

# Create correlation heatmap
png("images/04_correlation_heatmap.png", width = 600, height = 600, bg = "white")
corrplot(correlation_matrix, method = "color", type = "upper", 
         addCoef.col = "black", tl.col = "black", tl.srt = 45,
         title = "Correlation Matrix - Advertising Dataset")
dev.off()

cat("Correlation heatmap saved as: images/04_correlation_heatmap.png\n")

# Model Building
cat("\n=== Model Building ===\n")

# Based on correlation analysis, TV shows the strongest correlation with Sales
# Let's perform simple linear regression using TV as our feature variable

# Prepare data for modeling
X <- advertising$TV
y <- advertising$Sales

# Train-Test Split (70% train, 30% test)
set.seed(16)  # For reproducibility
train_indices <- sample(seq_len(nrow(advertising)), 0.7 * nrow(advertising))
X_train <- X[train_indices]
X_test <- X[-train_indices]
y_train <- y[train_indices]
y_test <- y[-train_indices]

cat("Train set size:", length(X_train), "observations\n")
cat("Test set size:", length(X_test), "observations\n")

# Display training data
cat("\nFirst 5 training observations:\n")
train_data <- data.frame(TV = X_train, Sales = y_train)
print(head(train_data, 5))

# Building Linear Model using lm() function
cat("\nBuilding linear regression model...\n")
model <- lm(Sales ~ TV, data = train_data)

# Display model summary
cat("\nModel Summary:\n")
print(summary(model))

# Extract model coefficients
intercept <- coef(model)[1]
slope <- coef(model)[2]

cat("\nModel Coefficients:\n")
cat("Intercept:", round(intercept, 4), "\n")
cat("Slope (TV coefficient):", round(slope, 4), "\n")

# Model equation
cat("\nLinear Regression Equation:\n")
cat("Sales =", round(intercept, 4), "+", round(slope, 4), "× TV\n")

# Model Evaluation
cat("\n=== Model Evaluation ===\n")

# R-squared
r_squared <- summary(model)$r.squared
cat("R-squared:", round(r_squared, 4), "\n")
cat("This means", round(r_squared * 100, 1), "% of the variance in Sales is explained by TV advertising\n")

# F-statistic and p-value
f_stat <- summary(model)$fstatistic[1]
f_pvalue <- pf(f_stat, summary(model)$fstatistic[2], summary(model)$fstatistic[3], lower.tail = FALSE)
cat("F-statistic:", round(f_stat, 2), "\n")
cat("F-statistic p-value:", format(f_pvalue, scientific = TRUE), "\n")

# Visualize model fit on training data
cat("\nCreating model fit visualization...\n")
p_fit <- ggplot(train_data, aes(x = TV, y = Sales)) + 
  geom_point(color = "blue", alpha = 0.6) +
  geom_abline(intercept = intercept, slope = slope, color = "red", linewidth = 1) +
  labs(title = "Linear Regression Model Fit (Training Data)", 
       subtitle = paste("Sales =", round(intercept, 2), "+", round(slope, 3), "× TV"),
       x = "TV Advertising (thousands $)", 
       y = "Sales (thousands of units)") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA))

png("images/05_model_fit_training.png", width = 800, height = 600, bg = "white")
print(p_fit)
dev.off()

cat("Model fit plot saved as: images/05_model_fit_training.png\n")

# Residual Analysis
cat("\n=== Residual Analysis ===\n")

# Calculate predictions and residuals
y_train_pred <- predict(model)
residuals <- y_train - y_train_pred

# Distribution of residuals
cat("Creating residual distribution plot...\n")
p_residuals <- ggplot(data.frame(residuals = residuals), aes(x = residuals)) + 
  geom_histogram(bins = 15, fill = "lightblue", color = "black", alpha = 0.7) +
  geom_vline(xintercept = 0, color = "red", linetype = "dashed") +
  labs(title = "Distribution of Residuals", 
       x = "Residuals (y_train - y_train_pred)", 
       y = "Frequency") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA))

png("images/06_residual_distribution.png", width = 600, height = 400, bg = "white")
print(p_residuals)
dev.off()

cat("Residual distribution plot saved as: images/06_residual_distribution.png\n")

# Residuals vs Fitted values
cat("Creating residuals vs fitted values plot...\n")
p_residuals_fitted <- ggplot(data.frame(fitted = y_train_pred, residuals = residuals), 
                            aes(x = fitted, y = residuals)) + 
  geom_point(color = "blue", alpha = 0.6) +
  geom_hline(yintercept = 0, color = "red", linetype = "dashed") +
  labs(title = "Residuals vs Fitted Values", 
       x = "Fitted Values", 
       y = "Residuals") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA))

png("images/07_residuals_vs_fitted.png", width = 600, height = 400, bg = "white")
print(p_residuals_fitted)
dev.off()

cat("Residuals vs fitted values plot saved as: images/07_residuals_vs_fitted.png\n")

# Predictions on Test Set
cat("\n=== Predictions on Test Set ===\n")

# Create test data frame
test_data <- data.frame(TV = X_test)

# Make predictions
y_pred <- predict(model, newdata = test_data)

cat("First 5 test predictions:\n")
test_results <- data.frame(TV = X_test, Actual_Sales = y_test, Predicted_Sales = y_pred)
print(head(test_results, 5))

# Calculate performance metrics
# RMSE (Root Mean Square Error)
rmse <- sqrt(mean((y_test - y_pred)^2))
cat("\nRoot Mean Square Error (RMSE):", round(rmse, 4), "\n")

# R-squared on test set
ss_res <- sum((y_test - y_pred)^2)
ss_tot <- sum((y_test - mean(y_test))^2)
r_squared_test <- 1 - (ss_res / ss_tot)
cat("R-squared on test set:", round(r_squared_test, 4), "\n")

# Mean Absolute Error
mae <- mean(abs(y_test - y_pred))
cat("Mean Absolute Error (MAE):", round(mae, 4), "\n")

# Visualize predictions on test set
cat("\nCreating test set predictions visualization...\n")
p_test <- ggplot(test_results, aes(x = TV, y = Actual_Sales)) + 
  geom_point(color = "blue", alpha = 0.6, size = 2) +
  geom_abline(intercept = intercept, slope = slope, color = "red", linewidth = 1) +
  labs(title = "Linear Regression Model - Test Set Predictions", 
       subtitle = paste("RMSE:", round(rmse, 3), "| R²:", round(r_squared_test, 3)),
       x = "TV Advertising (thousands $)", 
       y = "Sales (thousands of units)") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA))

png("images/08_test_predictions.png", width = 800, height = 600, bg = "white")
print(p_test)
dev.off()

cat("Test predictions plot saved as: images/08_test_predictions.png\n")

# Actual vs Predicted scatter plot
cat("Creating actual vs predicted scatter plot...\n")
p_actual_pred <- ggplot(test_results, aes(x = Actual_Sales, y = Predicted_Sales)) + 
  geom_point(color = "blue", alpha = 0.6, size = 2) +
  geom_abline(intercept = 0, slope = 1, color = "red", linetype = "dashed", linewidth = 1) +
  labs(title = "Actual vs Predicted Sales", 
       subtitle = paste("Perfect prediction would lie on the red line"),
       x = "Actual Sales (thousands of units)", 
       y = "Predicted Sales (thousands of units)") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA))

png("images/09_actual_vs_predicted.png", width = 600, height = 600, bg = "white")
print(p_actual_pred)
dev.off()

cat("Actual vs predicted plot saved as: images/09_actual_vs_predicted.png\n")

# Summary of Results
cat("\n=== SUMMARY OF RESULTS ===\n")
cat("Model Performance:\n")
cat("- Training R-squared:", round(r_squared, 4), "\n")
cat("- Test R-squared:", round(r_squared_test, 4), "\n")
cat("- RMSE:", round(rmse, 4), "\n")
cat("- MAE:", round(mae, 4), "\n")
cat("\nModel Equation:\n")
cat("Sales =", round(intercept, 4), "+", round(slope, 4), "× TV\n")
cat("\nInterpretation:\n")
cat("- For every $1000 increase in TV advertising, sales increase by", round(slope, 2), "thousand units\n")
cat("- The model explains", round(r_squared * 100, 1), "% of the variance in sales\n")
cat("- The model has good predictive power with low RMSE and high R-squared\n")

cat("\nAll plots have been saved in the 'images' directory with white backgrounds.\n")
cat("Analysis completed successfully!\n")
