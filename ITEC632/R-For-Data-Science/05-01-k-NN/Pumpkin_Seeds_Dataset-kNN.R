# =============================================================================
# k-NN Classification using the Pumpkin Seeds Dataset
# =============================================================================
# 
# This script demonstrates k-Nearest Neighbors classification on the Pumpkin Seeds
# Dataset, which contains morphological features of pumpkin seeds from two classes:
# - Çerçevelik (Turkish variety)
# - Ürgüp Sivrisi (Turkish variety)
#
# Features: Area, Perimeter, Major_Axis_Length, Minor_Axis_Length, Convex_Area,
#           Equiv_Diameter, Eccentricity, Solidity, Extent, Roundness, 
#           Aspect_Ration, Compactness
#
# Author: Data Science Project
# Date: 2024
# =============================================================================

# Clear workspace and set seed for reproducibility
rm(list = ls())
set.seed(16)

# =============================================================================
# 1. LOAD AND EXPLORE DATA
# =============================================================================

# Load required libraries (only essential ones)
cat("Loading required packages...\n")
if (!require("class", quietly = TRUE)) {
  install.packages("class")
  library(class)
}

# Load the dataset with proper encoding
cat("\nLoading Pumpkin Seeds Dataset...\n")
pumpkin_data <- read.csv("Pumpkin_Seeds_Dataset/Pumpkin_Seeds_Dataset.csv", 
                         fileEncoding = "latin1", stringsAsFactors = FALSE)

# Fix encoding issues in class names
cat("\nFixing Turkish character encoding issues...\n")
cat("Raw class values before fixing:\n")
print(unique(pumpkin_data$Class))

# Fix the encoding issues systematically
pumpkin_data$Class <- gsub(".*velik", "Çerçevelik", pumpkin_data$Class)
pumpkin_data$Class <- gsub(".*Sivrisi", "Ürgüp Sivrisi", pumpkin_data$Class)

cat("Class values after fixing:\n")
print(unique(pumpkin_data$Class))

# Display basic information
cat("\nDataset Overview:\n")
cat("Dimensions:", dim(pumpkin_data), "\n")
cat("Column names:", paste(names(pumpkin_data), collapse = ", "), "\n")

# Check for missing values
cat("\nMissing values:\n")
print(colSums(is.na(pumpkin_data)))

# Display first few rows
cat("\nFirst 6 rows:\n")
print(head(pumpkin_data))

# Display summary statistics
cat("\nSummary statistics:\n")
print(summary(pumpkin_data))

# =============================================================================
# 2. DATA PREPROCESSING
# =============================================================================

cat("\n", paste(rep("=", 50), collapse = ""), "\n")
cat("DATA PREPROCESSING\n")
cat(paste(rep("=", 50), collapse = ""), "\n")

# Check class distribution
cat("\nClass distribution:\n")
class_counts <- table(pumpkin_data$Class)
print(class_counts)
print(prop.table(class_counts))

# Convert class to factor
pumpkin_data$Class <- as.factor(pumpkin_data$Class)

# Separate features and target
features <- pumpkin_data[, -ncol(pumpkin_data)]
target <- pumpkin_data$Class

cat("\nFeature names:", paste(names(features), collapse = ", "), "\n")
cat("Target variable:", names(pumpkin_data)[ncol(pumpkin_data)], "\n")

# Check feature scales
cat("\nFeature scales (first few rows):\n")
print(head(features))

# =============================================================================
# 3. FEATURE ANALYSIS
# =============================================================================

cat("\n", paste(rep("=", 50), collapse = ""), "\n")
cat("FEATURE ANALYSIS\n")
cat(paste(rep("=", 50), collapse = ""), "\n")

# Create correlation matrix
cor_matrix <- cor(features)
cat("\nCorrelation matrix dimensions:", dim(cor_matrix), "\n")

# =============================================================================
# 4. DATA SPLITTING AND SCALING
# =============================================================================

cat("\n", paste(rep("=", 50), collapse = ""), "\n")
cat("DATA SPLITTING AND SCALING\n")
cat(paste(rep("=", 50), collapse = ""), "\n")

# Simple train/test split function
createDataPartition <- function(y, p = 0.7, list = FALSE) {
  n <- length(y)
  train_size <- round(n * p)
  train_indices <- sample(1:n, train_size)
  if (list) {
    return(list(train_indices))
  } else {
    return(train_indices)
  }
}

# Split data into training and testing sets (70-30 split)
train_indices <- createDataPartition(target, p = 0.7, list = FALSE)
X_train <- features[train_indices, ]
X_test <- features[-train_indices, ]
y_train <- target[train_indices]
y_test <- target[-train_indices]

cat("\nTraining set size:", nrow(X_train), "\n")
cat("Testing set size:", nrow(X_test), "\n")

# Feature scaling (Standardization: Z-score normalization)
# Calculate mean and standard deviation from training data
train_means <- colMeans(X_train)
train_sds <- apply(X_train, 2, sd)

# Scale training data
X_train_scaled <- scale(X_train, center = train_means, scale = train_sds)

# Scale testing data using training statistics
X_test_scaled <- scale(X_test, center = train_means, scale = train_sds)

cat("\nScaling applied using training set statistics\n")
cat("Training set scaled dimensions:", dim(X_train_scaled), "\n")
cat("Testing set scaled dimensions:", dim(X_test_scaled), "\n")

# =============================================================================
# 5. k-NN MODEL TRAINING AND EVALUATION
# =============================================================================

cat("\n", paste(rep("=", 50), collapse = ""), "\n")
cat("k-NN MODEL TRAINING AND EVALUATION\n")
cat(paste(rep("=", 50), collapse = ""), "\n")

# Function to evaluate k-NN with different k values
evaluate_knn <- function(k_values, X_train, y_train, X_test, y_test) {
  results <- data.frame(k = k_values, accuracy = numeric(length(k_values)))
  
  for(i in seq_along(k_values)) {
    k <- k_values[i]
    cat("Testing k =", k, "... ")
    
    # Train k-NN model
    knn_pred <- knn(train = X_train, test = X_test, cl = y_train, k = k)
    
    # Calculate accuracy
    accuracy <- mean(knn_pred == y_test)
    results$accuracy[i] <- accuracy
    
    cat("Accuracy:", round(accuracy, 4), "\n")
  }
  
  return(results)
}

# Test different k values
k_values <- c(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21)
cat("\nTesting k-NN with different k values:\n")
knn_results <- evaluate_knn(k_values, X_train_scaled, y_train, X_test_scaled, y_test)

# Find optimal k
optimal_k <- k_values[which.max(knn_results$accuracy)]
cat("\nOptimal k value:", optimal_k, "with accuracy:", max(knn_results$accuracy), "\n")

# =============================================================================
# 6. FINAL MODEL AND PREDICTIONS
# =============================================================================

cat("\n", paste(rep("=", 50), collapse = ""), "\n")
cat("FINAL MODEL AND PREDICTIONS\n")
cat(paste(rep("=", 50), collapse = ""), "\n")

# Train final model with optimal k
cat("\nTraining final k-NN model with k =", optimal_k, "\n")
final_predictions <- knn(train = X_train_scaled, test = X_test_scaled, 
                         cl = y_train, k = optimal_k)

# Create confusion matrix
conf_matrix <- table(y_test, final_predictions)
cat("\nConfusion Matrix:\n")
print(conf_matrix)

# Calculate performance metrics manually
tp <- conf_matrix[2, 2]  # True Positives
tn <- conf_matrix[1, 1]  # True Negatives
fp <- conf_matrix[1, 2]  # False Positives
fn <- conf_matrix[2, 1]  # False Negatives

accuracy <- (tp + tn) / (tp + tn + fp + fn)
sensitivity <- tp / (tp + fn)  # Recall
specificity <- tn / (tn + fp)
precision <- tp / (tp + fp)
f1 <- 2 * (precision * sensitivity) / (precision + sensitivity)

# Display detailed metrics
cat("\nDetailed Performance Metrics:\n")
cat("Overall Accuracy:", round(accuracy, 4), "\n")
cat("Sensitivity (Recall):", round(sensitivity, 4), "\n")
cat("Specificity:", round(specificity, 4), "\n")
cat("Precision:", round(precision, 4), "\n")
cat("F1 Score:", round(f1, 4), "\n")

# =============================================================================
# 7. FEATURE IMPORTANCE ANALYSIS
# =============================================================================

cat("\n", paste(rep("=", 50), collapse = ""), "\n")
cat("FEATURE IMPORTANCE ANALYSIS\n")
cat(paste(rep("=", 50), collapse = ""), "\n")

# Simple feature importance using correlation with target
# Convert target to numeric for correlation calculation
target_numeric <- as.numeric(y_test) - 1  # 0 and 1

# Calculate correlation between each feature and target
feature_importance <- data.frame(
  Feature = colnames(X_test_scaled),
  Correlation = sapply(1:ncol(X_test_scaled), function(i) {
    cor(X_test_scaled[, i], target_numeric)
  })
)

# Sort by absolute correlation
feature_importance$Abs_Correlation <- abs(feature_importance$Correlation)
feature_importance <- feature_importance[order(-feature_importance$Abs_Correlation), ]

cat("\nFeature Importance (correlation with target):\n")
print(feature_importance)

# =============================================================================
# 8. VISUALIZATION OF RESULTS
# =============================================================================

cat("\n", paste(rep("=", 50), collapse = ""), "\n")
cat("VISUALIZATION OF RESULTS\n")
cat(paste(rep("=", 50), collapse = ""), "\n")

# Create images directory if it doesn't exist
if (!dir.exists("images")) {
  dir.create("images")
}

# Save plots using base R graphics
cat("\nSaving plots to images/ directory...\n")

# Class distribution
png("images/01_class_distribution.png", width = 8, height = 6, units = "in", res = 300)
barplot(class_counts, main = "Class Distribution", xlab = "Pumpkin Seed Variety", 
        ylab = "Count", col = c("steelblue", "orange"), 
        names.arg = c("Çerçevelik", "Ürgüp Sivrisi"))
dev.off()

# Feature importance
png("images/02_feature_importance.png", width = 10, height = 8, units = "in", res = 300)
barplot(feature_importance$Abs_Correlation, names.arg = feature_importance$Feature, 
        main = "Feature Importance (Absolute Correlation with Target)",
        xlab = "Features", ylab = "|Correlation|", col = "steelblue", 
        las = 2, cex.names = 0.8)
dev.off()

# k-NN accuracy vs k
png("images/03_knn_accuracy_vs_k.png", width = 10, height = 6, units = "in", res = 300)
plot(knn_results$k, knn_results$accuracy, type = "l", col = "blue", lwd = 2,
     main = "k-NN Accuracy vs k Value", xlab = "k (Number of Neighbors)", 
     ylab = "Accuracy")
points(knn_results$k, knn_results$accuracy, col = "red", pch = 19, cex = 1.5)
abline(v = optimal_k, lty = 2, col = "green", lwd = 2)
text(optimal_k, max(knn_results$accuracy), paste("Optimal k =", optimal_k), 
     pos = 4, col = "green", cex = 1.2)
dev.off()

# Correlation matrix
png("images/04_correlation_matrix.png", width = 10, height = 8, units = "in", res = 300)
heatmap(cor_matrix, main = "Feature Correlation Matrix", 
        col = colorRampPalette(c("blue", "white", "red"))(100))
dev.off()

cat("All plots saved successfully!\n")

# =============================================================================
# 9. SUMMARY AND CONCLUSIONS
# =============================================================================

cat("\n", paste(rep("=", 50), collapse = ""), "\n")
cat("SUMMARY AND CONCLUSIONS\n")
cat(paste(rep("=", 50), collapse = ""), "\n")

cat("\nProject Summary:\n")
cat("- Dataset: Pumpkin Seeds Dataset with", nrow(pumpkin_data), "samples and", ncol(features), "features\n")
cat("- Classes:", paste(levels(target), collapse = " vs "), "\n")
cat("- Training set size:", nrow(X_train), "samples\n")
cat("- Testing set size:", nrow(X_test), "samples\n")
cat("- Optimal k value:", optimal_k, "\n")
cat("- Final accuracy:", round(accuracy, 4), "\n")

cat("\nKey Findings:\n")
cat("- Best performing k value:", optimal_k, "\n")
cat("- Model performance:", round(accuracy * 100, 1), "% accuracy\n")
cat("- Most important features:", paste(head(feature_importance$Feature, 3), collapse = ", "), "\n")

cat("\nRecommendations:\n")
cat("- Consider feature engineering for better performance\n")
cat("- Try ensemble methods (Random Forest, SVM) for comparison\n")
cat("- Collect more data if possible for robust model validation\n")

cat("\nScript completed successfully!\n")
cat("Check the 'images/' directory for generated visualizations.\n")
