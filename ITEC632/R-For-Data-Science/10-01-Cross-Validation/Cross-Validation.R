# Cross-Validation in R
# Housing Price Prediction with Cross-Validation

# Load required libraries
library(randomForest)
library(caret)
library(ggplot2)
library(dplyr)
# library(VIM)  # Not needed for this analysis
library(corrplot)

# Set working directory and create images directory
setwd("/Users/vuhung/00.Work/02.ACU/github/ITEC632/R-For-Data-Science/10-01-Cross-Validation")
if (!dir.exists("images")) {
  dir.create("images")
}

# Set theme for white background plots
theme_set(theme_minimal() + theme(plot.background = element_rect(fill = "white", color = NA)))

# Load the dataset
cat("Loading housing dataset...\n")
train_data <- read.csv("Dataset/train.csv", stringsAsFactors = FALSE)
test_data <- read.csv("Dataset/test.csv", stringsAsFactors = FALSE)

# Display basic information about the dataset
cat("Dataset Information:\n")
cat("Training data dimensions:", dim(train_data), "\n")
cat("Test data dimensions:", dim(test_data), "\n")

# Remove rows with missing target, separate target from predictors
train_data <- train_data[!is.na(train_data$SalePrice), ]
y <- train_data$SalePrice
train_data$SalePrice <- NULL

# Select numeric columns only (similar to Python version)
numeric_cols <- sapply(train_data, function(x) is.numeric(x))
X <- train_data[, numeric_cols, drop = FALSE]
X_test <- test_data[, numeric_cols, drop = FALSE]

cat("Numeric features selected:", ncol(X), "\n")
cat("Sample of numeric features:\n")
print(head(X[, 1:5]))

# Handle missing values with median imputation
cat("Handling missing values...\n")
X_imputed <- X
X_test_imputed <- X_test

# Impute missing values with median
for (col in names(X_imputed)) {
  if (any(is.na(X_imputed[[col]]))) {
    X_imputed[[col]][is.na(X_imputed[[col]])] <- median(X_imputed[[col]], na.rm = TRUE)
  }
}

for (col in names(X_test_imputed)) {
  if (any(is.na(X_test_imputed[[col]]))) {
    X_test_imputed[[col]][is.na(X_test_imputed[[col]])] <- median(X_test_imputed[[col]], na.rm = TRUE)
  }
}

cat("Missing values in training data after imputation:", sum(is.na(X_imputed)), "\n")
cat("Missing values in test data after imputation:", sum(is.na(X_test_imputed)), "\n")

# Function to get cross-validation score (equivalent to Python get_score function)
get_score <- function(n_estimators, cv_folds = 3) {
  # Create random forest model
  model <- randomForest(x = X_imputed, y = y, ntree = n_estimators, random_state = 0)
  
  # Perform cross-validation
  cv_results <- train(
    x = X_imputed, 
    y = y,
    method = "rf",
    trControl = trainControl(method = "cv", number = cv_folds),
    tuneGrid = data.frame(mtry = floor(sqrt(ncol(X_imputed)))),
    ntree = n_estimators
  )
  
  # Return MAE (Mean Absolute Error)
  return(cv_results$results$MAE)
}

# Test different parameter values (equivalent to Python results dictionary)
cat("Testing different n_estimators values...\n")
n_estimators_values <- c(50, 100, 150, 200, 250, 300, 350, 400)
results <- list()

for (n_est in n_estimators_values) {
  cat("Testing n_estimators =", n_est, "\n")
  mae_score <- get_score(n_est)
  results[[as.character(n_est)]] <- mae_score
  cat("MAE for", n_est, "trees:", mae_score, "\n")
}

# Convert results to data frame for plotting
results_df <- data.frame(
  n_estimators = as.numeric(names(results)),
  mae = unlist(results)
)

# Create visualization
cat("Creating visualization...\n")
p1 <- ggplot(results_df, aes(x = n_estimators, y = mae)) +
  geom_line(color = "blue", linewidth = 1) +
  geom_point(color = "red", size = 2) +
  labs(
    title = "Cross-Validation Results: MAE vs Number of Trees",
    x = "Number of Trees (n_estimators)",
    y = "Mean Absolute Error (MAE)"
  ) +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA))

# Save the plot
ggsave("images/cross_validation_results.png", p1, width = 10, height = 6, dpi = 300, bg = "white")

# Find the best parameter value
best_n_estimators <- results_df$n_estimators[which.min(results_df$mae)]
best_mae <- min(results_df$mae)

cat("Best n_estimators:", best_n_estimators, "\n")
cat("Best MAE:", best_mae, "\n")

# Train final model with best parameters
cat("Training final model with best parameters...\n")
final_model <- randomForest(x = X_imputed, y = y, ntree = best_n_estimators, random_state = 0)

# Make predictions on test set
cat("Making predictions on test set...\n")
predictions <- predict(final_model, X_test_imputed)

# Create prediction visualization
pred_df <- data.frame(
  index = 1:length(predictions),
  prediction = predictions
)

p2 <- ggplot(pred_df, aes(x = index, y = prediction)) +
  geom_line(color = "blue", alpha = 0.7) +
  geom_point(color = "red", size = 0.5) +
  labs(
    title = "Predicted House Prices on Test Set",
    x = "Test Sample Index",
    y = "Predicted Sale Price ($)"
  ) +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA))

ggsave("images/predictions.png", p2, width = 10, height = 6, dpi = 300, bg = "white")

# Feature importance plot
importance_scores <- importance(final_model)
importance_df <- data.frame(
  feature = rownames(importance_scores),
  importance = importance_scores[, 1]
) %>%
  arrange(desc(importance)) %>%
  head(15)  # Top 15 features

p3 <- ggplot(importance_df, aes(x = reorder(feature, importance), y = importance)) +
  geom_col(fill = "steelblue", alpha = 0.7) +
  coord_flip() +
  labs(
    title = "Top 15 Most Important Features",
    x = "Features",
    y = "Importance Score"
  ) +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA))

ggsave("images/feature_importance.png", p3, width = 10, height = 8, dpi = 300, bg = "white")

# Create correlation plot for top features
top_features <- importance_df$feature[1:10]
correlation_data <- X_imputed[, top_features]
correlation_matrix <- cor(correlation_data, use = "complete.obs")

png("images/correlation_plot.png", width = 800, height = 800, bg = "white")
corrplot(correlation_matrix, method = "color", type = "upper", 
         order = "hclust", tl.cex = 0.8, tl.col = "black")
dev.off()

# Summary statistics
cat("\n=== Cross-Validation Summary ===\n")
cat("Best number of trees:", best_n_estimators, "\n")
cat("Best MAE score:", round(best_mae, 2), "\n")
cat("Total features used:", ncol(X_imputed), "\n")
cat("Training samples:", nrow(X_imputed), "\n")
cat("Test samples:", nrow(X_test_imputed), "\n")

# Save results to file
results_summary <- data.frame(
  n_estimators = n_estimators_values,
  mae = sapply(n_estimators_values, function(x) results[[as.character(x)]]),
  stringsAsFactors = FALSE
)

write.csv(results_summary, "images/cross_validation_results.csv", row.names = FALSE)

cat("Cross-validation analysis completed successfully!\n")
cat("Results saved to images/ directory\n")
