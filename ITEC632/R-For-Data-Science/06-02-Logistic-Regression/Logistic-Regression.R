# Logistic Regression Analysis - Weather Prediction
# Based on Australian Weather Dataset

# Load required libraries
library(ggplot2)
library(dplyr)
library(corrplot)
library(gridExtra)
library(caret)
library(pROC)
library(VIM)

# Set working directory and create images folder
if (!dir.exists("images")) {
  dir.create("images")
}

# Set theme for white background plots
theme_set(theme_minimal() + 
          theme(panel.background = element_rect(fill = "white", color = NA),
                plot.background = element_rect(fill = "white", color = NA)))

cat("=== Logistic Regression Analysis: Weather Prediction ===\n")
cat("Loading and understanding the data...\n")

# Load the dataset
weather_data <- read.csv("Weather-Dataset/weatherAUS.csv")

# Display basic information about the dataset
cat("\nDataset Information:\n")
cat("Shape:", nrow(weather_data), "rows,", ncol(weather_data), "columns\n")
cat("Column names:", paste(names(weather_data), collapse = ", "), "\n")

# Display first few rows
cat("\nFirst 5 rows of the dataset:\n")
print(head(weather_data, 5))

# Data inspection
cat("\nData Summary:\n")
print(summary(weather_data))

# Check for missing values
cat("\nMissing values check:\n")
missing_values <- colSums(is.na(weather_data))
print(missing_values)

# Drop RISK_MM variable as mentioned in the reference notebook
weather_data <- weather_data[, !names(weather_data) %in% c("RISK_MM")]

cat("\nAfter dropping RISK_MM variable:\n")
cat("Shape:", nrow(weather_data), "rows,", ncol(weather_data), "columns\n")

# Feature Engineering - Extract date components
weather_data$Date <- as.Date(weather_data$Date)
weather_data$Year <- as.numeric(format(weather_data$Date, "%Y"))
weather_data$Month <- as.numeric(format(weather_data$Date, "%m"))
weather_data$Day <- as.numeric(format(weather_data$Date, "%d"))

# Drop the original Date variable
weather_data <- weather_data[, !names(weather_data) %in% c("Date")]

cat("\nAfter feature engineering:\n")
cat("Shape:", nrow(weather_data), "rows,", ncol(weather_data), "columns\n")

# Identify categorical and numerical variables
categorical_vars <- names(weather_data)[sapply(weather_data, is.character)]
numerical_vars <- names(weather_data)[sapply(weather_data, is.numeric)]

cat("\nCategorical variables:", paste(categorical_vars, collapse = ", "), "\n")
cat("Numerical variables:", paste(numerical_vars, collapse = ", "), "\n")

# Exploratory Data Analysis
cat("\n=== Exploratory Data Analysis ===\n")

# Target variable analysis
cat("Target variable (RainTomorrow) distribution:\n")
target_dist <- table(weather_data$RainTomorrow)
print(target_dist)
cat("Proportions:\n")
print(prop.table(target_dist))

# Create target variable distribution plot
p_target <- ggplot(weather_data, aes(x = RainTomorrow, fill = RainTomorrow)) + 
  geom_bar(alpha = 0.7) +
  scale_fill_manual(values = c("No" = "lightblue", "Yes" = "lightcoral")) +
  labs(title = "Distribution of RainTomorrow (Target Variable)", 
       x = "Rain Tomorrow", 
       y = "Count") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA),
        legend.position = "none")

png("images/01_target_distribution.png", width = 600, height = 400, bg = "white")
print(p_target)
dev.off()

cat("Target distribution plot saved as: images/01_target_distribution.png\n")

# Missing values analysis
cat("\nCreating missing values analysis plot...\n")
missing_data <- weather_data[, numerical_vars]
missing_summary <- colSums(is.na(missing_data))
missing_df <- data.frame(Variable = names(missing_summary), Missing_Count = missing_summary)
missing_df$Missing_Percentage <- (missing_df$Missing_Count / nrow(weather_data)) * 100

p_missing <- ggplot(missing_df, aes(x = reorder(Variable, Missing_Percentage), y = Missing_Percentage)) + 
  geom_bar(stat = "identity", fill = "lightcoral", alpha = 0.7) +
  coord_flip() +
  labs(title = "Missing Values Analysis", 
       x = "Variables", 
       y = "Missing Percentage (%)") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA))

png("images/02_missing_values_analysis.png", width = 800, height = 600, bg = "white")
print(p_missing)
dev.off()

cat("Missing values analysis plot saved as: images/02_missing_values_analysis.png\n")

# Correlation analysis for numerical variables
cat("\nCreating correlation analysis...\n")
numerical_data <- weather_data[, numerical_vars]
correlation_matrix <- cor(numerical_data, use = "complete.obs")

png("images/03_correlation_heatmap.png", width = 800, height = 800, bg = "white")
corrplot(correlation_matrix, method = "color", type = "upper", 
         addCoef.col = "black", tl.col = "black", tl.srt = 45,
         title = "Correlation Matrix - Weather Dataset")
dev.off()

cat("Correlation heatmap saved as: images/03_correlation_heatmap.png\n")

# Data Preprocessing
cat("\n=== Data Preprocessing ===\n")

# Handle missing values in numerical variables (use median imputation)
for (var in numerical_vars) {
  if (sum(is.na(weather_data[[var]])) > 0) {
    median_val <- median(weather_data[[var]], na.rm = TRUE)
    weather_data[[var]][is.na(weather_data[[var]])] <- median_val
    cat("Imputed missing values in", var, "with median:", median_val, "\n")
  }
}

# Handle missing values in categorical variables (use mode)
for (var in categorical_vars) {
  if (sum(is.na(weather_data[[var]])) > 0) {
    mode_val <- names(sort(table(weather_data[[var]]), decreasing = TRUE))[1]
    weather_data[[var]][is.na(weather_data[[var]])] <- mode_val
    cat("Imputed missing values in", var, "with mode:", mode_val, "\n")
  }
}

# Check if all missing values are handled
cat("\nMissing values after imputation:\n")
missing_after <- colSums(is.na(weather_data))
print(missing_after)

# Feature Engineering - Create dummy variables for categorical variables
cat("\nCreating dummy variables for categorical features...\n")

# Convert categorical variables to factors
for (var in categorical_vars) {
  weather_data[[var]] <- as.factor(weather_data[[var]])
}

# Create dummy variables for Location (high cardinality)
location_dummies <- model.matrix(~ Location - 1, data = weather_data)
location_dummies <- as.data.frame(location_dummies)

# Create dummy variables for wind directions
wind_gust_dummies <- model.matrix(~ WindGustDir - 1, data = weather_data)
wind_gust_dummies <- as.data.frame(wind_gust_dummies)

wind_dir9am_dummies <- model.matrix(~ WindDir9am - 1, data = weather_data)
wind_dir9am_dummies <- as.data.frame(wind_dir9am_dummies)

wind_dir3pm_dummies <- model.matrix(~ WindDir3pm - 1, data = weather_data)
wind_dir3pm_dummies <- as.data.frame(wind_dir3pm_dummies)

# Create dummy variables for RainToday
rain_today_dummies <- model.matrix(~ RainToday - 1, data = weather_data)
rain_today_dummies <- as.data.frame(rain_today_dummies)

# Combine all features
weather_processed <- cbind(
  weather_data[, numerical_vars],
  location_dummies,
  wind_gust_dummies,
  wind_dir9am_dummies,
  wind_dir3pm_dummies,
  rain_today_dummies
)

# Add target variable
weather_processed$RainTomorrow <- weather_data$RainTomorrow

cat("Processed dataset shape:", nrow(weather_processed), "rows,", ncol(weather_processed), "columns\n")

# Train-Test Split
cat("\n=== Train-Test Split ===\n")
set.seed(100)  # For reproducibility

# Prepare features and target
X <- weather_processed[, !names(weather_processed) %in% c("RainTomorrow")]
y <- weather_processed$RainTomorrow

# Split the data
train_indices <- createDataPartition(y, p = 0.8, list = FALSE)
X_train <- X[train_indices, ]
X_test <- X[-train_indices, ]
y_train <- y[train_indices]
y_test <- y[-train_indices]

cat("Train set size:", nrow(X_train), "observations\n")
cat("Test set size:", nrow(X_test), "observations\n")

# Check class distribution in train and test sets
cat("\nClass distribution in training set:\n")
print(table(y_train))
cat("Class distribution in test set:\n")
print(table(y_test))

# Feature Scaling
cat("\n=== Feature Scaling ===\n")
# Scale numerical features
preprocess_params <- preProcess(X_train, method = c("center", "scale"))
X_train_scaled <- predict(preprocess_params, X_train)
X_test_scaled <- predict(preprocess_params, X_test)

cat("Features scaled using standardization\n")

# Model Building
cat("\n=== Model Building ===\n")

# Build Logistic Regression Model
cat("Building logistic regression model...\n")
train_data <- data.frame(X_train_scaled, RainTomorrow = y_train)

# Fit logistic regression model
logistic_model <- glm(RainTomorrow ~ ., data = train_data, family = binomial)

# Display model summary
cat("\nModel Summary:\n")
print(summary(logistic_model))

# Model Evaluation
cat("\n=== Model Evaluation ===\n")

# Predictions on training set
y_train_pred_prob <- predict(logistic_model, newdata = X_train_scaled, type = "response")
y_train_pred <- ifelse(y_train_pred_prob > 0.5, "Yes", "No")

# Predictions on test set
y_test_pred_prob <- predict(logistic_model, newdata = X_test_scaled, type = "response")
y_test_pred <- ifelse(y_test_pred_prob > 0.5, "Yes", "No")

# Calculate accuracy
train_accuracy <- mean(y_train_pred == y_train)
test_accuracy <- mean(y_test_pred == y_test)

cat("Training Accuracy:", round(train_accuracy, 4), "\n")
cat("Test Accuracy:", round(test_accuracy, 4), "\n")

# Confusion Matrix
cat("\nConfusion Matrix (Test Set):\n")
confusion_matrix <- table(Actual = y_test, Predicted = y_test_pred)
print(confusion_matrix)

# Calculate additional metrics
TP <- confusion_matrix[2, 2]  # True Positives
TN <- confusion_matrix[1, 1]  # True Negatives
FP <- confusion_matrix[1, 2]  # False Positives
FN <- confusion_matrix[2, 1]  # False Negatives

precision <- TP / (TP + FP)
recall <- TP / (TP + FN)
f1_score <- 2 * (precision * recall) / (precision + recall)

cat("\nPerformance Metrics:\n")
cat("Precision:", round(precision, 4), "\n")
cat("Recall (Sensitivity):", round(recall, 4), "\n")
cat("F1-Score:", round(f1_score, 4), "\n")

# ROC Curve
cat("\nCreating ROC curve...\n")
roc_obj <- roc(y_test, y_test_pred_prob)
auc_score <- auc(roc_obj)

cat("AUC Score:", round(auc_score, 4), "\n")

# Plot ROC Curve
png("images/04_roc_curve.png", width = 600, height = 600, bg = "white")
plot(roc_obj, main = "ROC Curve - Logistic Regression", 
     col = "blue", lwd = 2)
abline(a = 0, b = 1, col = "red", lty = 2)
text(0.6, 0.2, paste("AUC =", round(auc_score, 3)), cex = 1.2)
dev.off()

cat("ROC curve saved as: images/04_roc_curve.png\n")

# Feature Importance
cat("\n=== Feature Importance ===\n")
# Get coefficients
coefficients <- coef(logistic_model)
coefficients <- coefficients[!is.na(coefficients)]
coefficients <- coefficients[abs(coefficients) > 0.1]  # Filter significant coefficients

# Create feature importance plot
if (length(coefficients) > 0) {
  importance_df <- data.frame(
    Feature = names(coefficients),
    Coefficient = abs(coefficients)
  )
  importance_df <- importance_df[order(importance_df$Coefficient, decreasing = TRUE), ]
  importance_df <- head(importance_df, 15)  # Top 15 features
  
  p_importance <- ggplot(importance_df, aes(x = reorder(Feature, Coefficient), y = Coefficient)) + 
    geom_bar(stat = "identity", fill = "lightgreen", alpha = 0.7) +
    coord_flip() +
    labs(title = "Top 15 Feature Importance (Logistic Regression)", 
         x = "Features", 
         y = "Absolute Coefficient Value") +
    theme_minimal() +
    theme(panel.background = element_rect(fill = "white", color = NA),
          plot.background = element_rect(fill = "white", color = NA))
  
  png("images/05_feature_importance.png", width = 800, height = 600, bg = "white")
  print(p_importance)
  dev.off()
  
  cat("Feature importance plot saved as: images/05_feature_importance.png\n")
}

# Prediction Probability Distribution
cat("\nCreating prediction probability distribution plot...\n")
prob_df <- data.frame(Probability = y_test_pred_prob, Actual = y_test)

p_prob_dist <- ggplot(prob_df, aes(x = Probability, fill = Actual)) + 
  geom_histogram(bins = 30, alpha = 0.7, position = "identity") +
  scale_fill_manual(values = c("No" = "lightblue", "Yes" = "lightcoral")) +
  labs(title = "Distribution of Prediction Probabilities", 
       x = "Predicted Probability of Rain", 
       y = "Frequency") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA))

png("images/06_probability_distribution.png", width = 800, height = 600, bg = "white")
print(p_prob_dist)
dev.off()

cat("Probability distribution plot saved as: images/06_probability_distribution.png\n")

# Cross-Validation
cat("\n=== Cross-Validation ===\n")
# Perform 5-fold cross-validation
cv_results <- train(
  RainTomorrow ~ ., 
  data = train_data, 
  method = "glm", 
  family = "binomial",
  trControl = trainControl(method = "cv", number = 5)
)

cat("Cross-validation results:\n")
print(cv_results)

# Summary of Results
cat("\n=== SUMMARY OF RESULTS ===\n")
cat("Model Performance:\n")
cat("- Training Accuracy:", round(train_accuracy, 4), "\n")
cat("- Test Accuracy:", round(test_accuracy, 4), "\n")
cat("- Precision:", round(precision, 4), "\n")
cat("- Recall:", round(recall, 4), "\n")
cat("- F1-Score:", round(f1_score, 4), "\n")
cat("- AUC Score:", round(auc_score, 4), "\n")
cat("- Cross-validation Accuracy:", round(cv_results$results$Accuracy, 4), "\n")

cat("\nModel Interpretation:\n")
cat("- The logistic regression model achieved", round(test_accuracy * 100, 1), "% accuracy on the test set\n")
cat("- AUC score of", round(auc_score, 3), "indicates", 
    ifelse(auc_score > 0.8, "excellent", ifelse(auc_score > 0.7, "good", "fair")), "discriminative ability\n")
cat("- The model can predict rain tomorrow with reasonable accuracy\n")

cat("\nAll plots have been saved in the 'images' directory with white backgrounds.\n")
cat("Analysis completed successfully!\n")
