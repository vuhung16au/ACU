# Heart Disease Prediction Project
# Author: R for Data Science Course
# Purpose: Comprehensive analysis of heart disease risk factors using machine learning
#
# PERFORMANCE OPTIMIZATIONS:
# - SMOTE applied to sampled data (max 50k rows) for faster execution
# - Random Forest uses fewer trees (50) on large datasets
# - SVM uses sampled data (20k rows) due to O(n²) complexity
# - Neural Network uses sampled data (50k rows) for faster training
# These optimizations maintain model quality while significantly reducing runtime

# =============================================================================
# SETUP AND CONFIGURATION
# =============================================================================

# Set working directory
setwd("/Users/vuhung/00.Work/02.ACU/github/ITEC632/R-For-Data-Science/12-02-Heart-Disease-Prediction")

# Load required libraries
library(tidyverse)
library(caret)
library(rpart)
library(randomForest)
library(e1071)
library(class)
library(nnet)
library(ROSE)
library(pROC)
library(corrplot)
library(VIM)
library(gridExtra)
library(knitr)

# Set seed for reproducibility
set.seed(123)

# Create images directory if it doesn't exist
if(!dir.exists("images")) dir.create("images")

# =============================================================================
# TASK 0: DATA LOADING AND INITIAL VERIFICATION
# =============================================================================

cat("=== TASK 0: DATA LOADING AND INITIAL VERIFICATION ===\n\n")

# Load dataset
cat("Loading dataset...\n")
heart_data <- read.csv("Heart-Disease-Dataset/heart_2020_cleaned.csv", 
                       stringsAsFactors = TRUE)

# Display structure
cat("\nDataset Structure:\n")
str(heart_data)
cat("\nDataset Dimensions:", dim(heart_data), "\n")

# Summary statistics
cat("\nSummary Statistics:\n")
summary(heart_data)

# Check for missing values
cat("\n=== MISSING VALUES ANALYSIS ===\n")
missing_summary <- heart_data %>%
  summarise_all(~sum(is.na(.))) %>%
  gather(key = "Variable", value = "Missing_Count") %>%
  arrange(desc(Missing_Count))

print(missing_summary)

# Visualize missing values if any exist
if(sum(is.na(heart_data)) > 0) {
  png("images/missing_values.png", width = 800, height = 600, res = 300)
  aggr(heart_data, col = c('navyblue', 'red'), numbers = TRUE, sortVars = TRUE)
  dev.off()
}

# Target variable distribution
cat("\n=== TARGET VARIABLE DISTRIBUTION ===\n")
target_dist <- table(heart_data$HeartDisease)
print(target_dist)
print(prop.table(target_dist))

# Class imbalance ratio
imbalance_ratio <- sum(heart_data$HeartDisease == "Yes") / 
                   sum(heart_data$HeartDisease == "No")
cat("\nClass Imbalance Ratio (Yes:No):", round(imbalance_ratio, 3), "\n")

# Visualize target distribution
png("images/target_distribution.png", width = 800, height = 600, res = 300)
barplot(target_dist, main = "Heart Disease Distribution", 
        xlab = "Heart Disease", ylab = "Count", col = c("lightblue", "lightcoral"))
dev.off()

# =============================================================================
# TASK 1: EXPLORATORY DATA ANALYSIS
# =============================================================================

cat("\n=== TASK 1: EXPLORATORY DATA ANALYSIS ===\n\n")

# 1.1 Descriptive Statistics
cat("=== 1.1 DESCRIPTIVE STATISTICS ===\n")

# Separate numeric and categorical variables
numeric_vars <- heart_data %>%
  select_if(is.numeric) %>%
  names()

categorical_vars <- heart_data %>%
  select_if(is.factor) %>%
  names()

categorical_vars <- categorical_vars[categorical_vars != "HeartDisease"]

cat("Numeric Variables:", paste(numeric_vars, collapse = ", "), "\n")
cat("Categorical Variables:", paste(categorical_vars, collapse = ", "), "\n")

# Summary statistics for numeric variables
numeric_summary <- heart_data %>%
  select(all_of(numeric_vars)) %>%
  summarise_all(list(
    mean = ~mean(., na.rm = TRUE),
    sd = ~sd(., na.rm = TRUE),
    min = ~min(., na.rm = TRUE),
    max = ~max(., na.rm = TRUE),
    median = ~median(., na.rm = TRUE)
  ))

cat("\nNumeric Variables Summary:\n")
print(numeric_summary)

# Categorical variables summary
cat("\nCategorical Variables Summary:\n")
for(var in categorical_vars) {
  cat("\n", var, ":\n")
  print(table(heart_data[[var]]))
}

# 1.2 Visualizations
cat("\n=== 1.2 CREATING VISUALIZATIONS ===\n")

# Distribution plots for numeric variables
for(var in numeric_vars[1:min(4, length(numeric_vars))]) {
  png(paste0("images/distribution_", var, ".png"), width = 800, height = 600, res = 300)
  hist(heart_data[[var]], main = paste("Distribution of", var), 
       xlab = var, col = "lightblue", breaks = 30)
  dev.off()
}

# Box plots by target variable
for(var in numeric_vars[1:min(4, length(numeric_vars))]) {
  png(paste0("images/boxplot_", var, "_by_target.png"), width = 800, height = 600, res = 300)
  boxplot(heart_data[[var]] ~ heart_data$HeartDisease,
          main = paste(var, "by Heart Disease"),
          xlab = "Heart Disease", ylab = var,
          col = c("lightblue", "lightcoral"))
  dev.off()
}

# Bar charts for categorical variables
for(var in categorical_vars[1:min(4, length(categorical_vars))]) {
  png(paste0("images/barplot_", var, ".png"), width = 800, height = 600, res = 300)
  barplot(table(heart_data[[var]]), main = paste("Distribution of", var),
          xlab = var, ylab = "Count", col = "lightblue", las = 2)
  dev.off()
}

# Correlation heatmap
numeric_data <- heart_data %>%
  select(all_of(numeric_vars))

cor_matrix <- cor(numeric_data, use = "complete.obs")
png("images/correlation_heatmap.png", width = 1000, height = 1000, res = 300)
corrplot(cor_matrix, method = "color", type = "upper", 
         order = "hclust", tl.cex = 0.8, tl.col = "black")
dev.off()

# 1.3 Statistical Analysis
cat("\n=== 1.3 STATISTICAL ANALYSIS ===\n")

# Convert target to binary for correlation
heart_data$HeartDisease_binary <- ifelse(heart_data$HeartDisease == "Yes", 1, 0)

# Correlation with target
correlations <- numeric_data %>%
  mutate(HeartDisease = heart_data$HeartDisease_binary) %>%
  cor(use = "complete.obs") %>%
  as.data.frame() %>%
  select(HeartDisease) %>%
  arrange(desc(abs(HeartDisease)))

cat("\nCorrelation with Heart Disease:\n")
print(correlations)

# Chi-square tests for categorical variables
chi_square_results <- data.frame()

for(var in categorical_vars[1:min(6, length(categorical_vars))]) {
  chi_test <- chisq.test(table(heart_data[[var]], heart_data$HeartDisease))
  chi_square_results <- rbind(chi_square_results, data.frame(
    Variable = var,
    Chi_Square = chi_test$statistic,
    p_value = chi_test$p.value,
    Significant = ifelse(chi_test$p.value < 0.05, "Yes", "No")
  ))
}

cat("\nChi-Square Test Results:\n")
print(chi_square_results)

# T-tests for numeric variables
t_test_results <- data.frame()

for(var in numeric_vars) {
  yes_group <- heart_data[heart_data$HeartDisease == "Yes", var]
  no_group <- heart_data[heart_data$HeartDisease == "No", var]
  
  t_test <- t.test(yes_group, no_group)
  t_test_results <- rbind(t_test_results, data.frame(
    Variable = var,
    t_statistic = t_test$statistic,
    p_value = t_test$p.value,
    Significant = ifelse(t_test$p.value < 0.05, "Yes", "No")
  ))
}

cat("\nT-Test Results:\n")
print(t_test_results)

# 1.4 Variable Selection - Top 5 Variables
cat("\n=== 1.4 TOP 5 VARIABLES SELECTION ===\n")

# Combine correlation and statistical test results
variable_importance <- data.frame(
  Variable = c(rownames(correlations)[1:(nrow(correlations)-1)], 
               chi_square_results$Variable, t_test_results$Variable),
  Correlation = c(correlations$HeartDisease[1:(nrow(correlations)-1)], 
                  rep(NA, nrow(chi_square_results) + nrow(t_test_results))),
  P_Value = c(rep(NA, nrow(correlations)-1), 
              chi_square_results$p_value, t_test_results$p_value),
  Type = c(rep("Numeric", nrow(correlations)-1),
           rep("Categorical", nrow(chi_square_results)),
           rep("Numeric", nrow(t_test_results)))
)

# Select top 5 variables based on correlation and significance
# First, get top numeric variables by correlation
top_numeric <- rownames(correlations)[1:min(5, nrow(correlations)-1)]
top_numeric <- top_numeric[top_numeric != "HeartDisease"]

# Get top categorical variables by chi-square p-value
top_categorical <- chi_square_results %>%
  arrange(p_value) %>%
  head(3) %>%
  pull(Variable)

# Combine and select top 5
top_5_vars <- unique(c(top_numeric[1:min(3, length(top_numeric))], 
                      top_categorical[1:min(2, length(top_categorical))]))[1:5]

# Ensure we have exactly 5 variables
if(length(top_5_vars) < 5) {
  # Add more from correlations if needed
  remaining <- setdiff(rownames(correlations)[1:10], c(top_5_vars, "HeartDisease"))
  top_5_vars <- c(top_5_vars, remaining[1:(5-length(top_5_vars))])
}

top_5_vars <- top_5_vars[1:5]  # Ensure exactly 5

cat("\nTop 5 Variables Selected:\n")
print(top_5_vars)
cat("\nTop 5 Variables:", paste(top_5_vars, collapse = ", "), "\n")

# =============================================================================
# TASK 2: BUILD AND EVALUATE PREDICTIVE MODELS
# =============================================================================

cat("\n=== TASK 2: BUILD AND EVALUATE PREDICTIVE MODELS ===\n\n")

# 2.1 Data Preparation
cat("=== 2.1 DATA PREPARATION ===\n")

# Prepare data for modeling
model_data <- heart_data %>%
  select(all_of(c(top_5_vars, "HeartDisease"))) %>%
  na.omit()

# Convert target to factor if needed
model_data$HeartDisease <- as.factor(model_data$HeartDisease)

# Create binary target
model_data$HeartDisease_binary <- ifelse(model_data$HeartDisease == "Yes", 1, 0)

# Split data (70% train, 30% test)
trainIndex <- createDataPartition(model_data$HeartDisease, p = 0.7, list = FALSE)
train_data <- model_data[trainIndex, ]
test_data <- model_data[-trainIndex, ]

cat("Training set size:", nrow(train_data), "\n")
cat("Testing set size:", nrow(test_data), "\n")

# 2.2 Handle Class Imbalance
cat("\n=== 2.2 HANDLING CLASS IMBALANCE ===\n")

# Check imbalance
cat("Original class distribution (Training):\n")
print(table(train_data$HeartDisease))

# For large datasets, use sampling to speed up SMOTE
# Sample training data if too large (keep max 50k rows for SMOTE)
max_rows_for_smote <- 50000
if(nrow(train_data) > max_rows_for_smote) {
  cat("\nDataset is large. Sampling", max_rows_for_smote, "rows for SMOTE...\n")
  # Stratified sample to maintain class distribution
  train_sample <- train_data %>%
    group_by(HeartDisease) %>%
    sample_n(min(n(), max_rows_for_smote / 2)) %>%
    ungroup()
  
  cat("Applying SMOTE to sampled data...\n")
  train_data_balanced <- ROSE(HeartDisease ~ ., data = train_sample, seed = 123)$data
} else {
  cat("Applying SMOTE to full training data...\n")
  train_data_balanced <- ROSE(HeartDisease ~ ., data = train_data, seed = 123)$data
}

cat("\nBalanced class distribution (Training):\n")
print(table(train_data_balanced$HeartDisease))

# 2.3 Prepare formula
formula <- as.formula(paste("HeartDisease ~", paste(top_5_vars, collapse = " + ")))

# 2.4 Build Models
cat("\n=== 2.4 BUILDING MODELS ===\n")

models <- list()
model_results <- data.frame()

# Model 1: Logistic Regression
cat("\n1. Training Logistic Regression...\n")
models$logistic <- glm(formula, data = train_data_balanced, family = binomial)

# Model 2: Decision Tree
cat("2. Training Decision Tree...\n")
models$decision_tree <- rpart(formula, data = train_data_balanced, method = "class")

# Model 3: Random Forest
cat("3. Training Random Forest...\n")
# Reduce trees for faster training on large datasets
ntrees <- ifelse(nrow(train_data_balanced) > 100000, 50, 100)
models$random_forest <- randomForest(formula, data = train_data_balanced, 
                                     ntree = ntrees, importance = TRUE, 
                                     maxnodes = 20)  # Limit tree depth

# Model 4: Support Vector Machine
cat("4. Training Support Vector Machine...\n")
# SVM is very slow on large datasets - sample if needed
if(nrow(train_data_balanced) > 20000) {
  cat("   Sampling 20,000 rows for SVM (SVM is slow on large datasets)...\n")
  svm_sample <- train_data_balanced %>%
    group_by(HeartDisease) %>%
    sample_n(min(n(), 10000)) %>%
    ungroup()
  models$svm <- svm(formula, data = svm_sample, probability = TRUE)
} else {
  models$svm <- svm(formula, data = train_data_balanced, probability = TRUE)
}

# Model 5: K-Nearest Neighbors
cat("5. Training K-Nearest Neighbors...\n")
# Prepare data for KNN (convert factors to numeric)
train_knn <- train_data_balanced %>%
  select(all_of(top_5_vars)) %>%
  mutate_if(is.factor, as.numeric)
test_knn <- test_data %>%
  select(all_of(top_5_vars)) %>%
  mutate_if(is.factor, as.numeric)

# Normalize for KNN
preProc <- preProcess(train_knn, method = c("center", "scale"))
train_knn_scaled <- predict(preProc, train_knn)
test_knn_scaled <- predict(preProc, test_knn)

# Find optimal k
k_values <- seq(3, 15, by = 2)
best_k <- 5  # Default, can be optimized
models$knn <- knn(train = train_knn_scaled, test = test_knn_scaled,
                  cl = train_data_balanced$HeartDisease, k = best_k)

# Model 6: Neural Network
cat("6. Training Neural Network...\n")
# Sample for neural network if dataset is very large
if(nrow(train_data_balanced) > 50000) {
  cat("   Sampling 50,000 rows for Neural Network...\n")
  nn_sample <- train_data_balanced %>%
    group_by(HeartDisease) %>%
    sample_n(min(n(), 25000)) %>%
    ungroup()
  models$neural_net <- nnet(formula, data = nn_sample, 
                            size = 5, maxit = 200, trace = FALSE)
} else {
  models$neural_net <- nnet(formula, data = train_data_balanced, 
                            size = 5, maxit = 200, trace = FALSE)
}

# 2.5 Model Evaluation
cat("\n=== 2.5 MODEL EVALUATION ===\n")

evaluate_model <- function(model, model_name, test_data, formula) {
  # Make predictions
  if(model_name == "knn") {
    predictions <- model
    predicted_probs <- NULL
  } else if(model_name == "decision_tree") {
    predictions <- predict(model, test_data, type = "class")
    predicted_probs <- predict(model, test_data, type = "prob")[, 2]
  } else if(model_name == "random_forest") {
    predictions <- predict(model, test_data)
    predicted_probs <- predict(model, test_data, type = "prob")[, 2]
  } else if(model_name == "svm") {
    predictions <- predict(model, test_data)
    predicted_probs <- attr(predict(model, test_data, probability = TRUE), "probabilities")[, 2]
  } else if(model_name == "neural_net") {
    predictions <- predict(model, test_data, type = "class")
    predicted_probs <- predict(model, test_data, type = "raw")
  } else {  # logistic
    predictions <- ifelse(predict(model, test_data, type = "response") > 0.5, "Yes", "No")
    predicted_probs <- predict(model, test_data, type = "response")
  }
  
  predictions <- factor(predictions, levels = levels(test_data$HeartDisease))
  
  # Confusion matrix
  cm <- confusionMatrix(predictions, test_data$HeartDisease, positive = "Yes")
  
  # Calculate metrics
  metrics <- data.frame(
    Model = model_name,
    Accuracy = cm$overall["Accuracy"],
    Precision = cm$byClass["Precision"],
    Recall = cm$byClass["Recall"],
    Specificity = cm$byClass["Specificity"],
    F1_Score = cm$byClass["F1"]
  )
  
  # AUC-ROC if probabilities available
  if(!is.null(predicted_probs)) {
    roc_obj <- roc(test_data$HeartDisease, predicted_probs)
    metrics$AUC_ROC <- as.numeric(auc(roc_obj))
  } else {
    metrics$AUC_ROC <- NA
  }
  
  return(list(metrics = metrics, confusion_matrix = cm, 
              predictions = predictions, probabilities = predicted_probs))
}

# Evaluate all models
all_results <- list()
for(model_name in names(models)) {
  cat("\nEvaluating", model_name, "...\n")
  all_results[[model_name]] <- evaluate_model(
    models[[model_name]], model_name, test_data, formula
  )
  model_results <- rbind(model_results, all_results[[model_name]]$metrics)
}

cat("\n=== MODEL PERFORMANCE SUMMARY ===\n")
print(model_results)

# Save model results
write.csv(model_results, "model_performance.csv", row.names = FALSE)

# 2.6 Visualizations
cat("\n=== 2.6 CREATING MODEL VISUALIZATIONS ===\n")

# Decision Tree Visualization
png("images/decision_tree.png", width = 1200, height = 800, res = 300)
plot(models$decision_tree)
text(models$decision_tree, use.n = TRUE, cex = 0.8)
dev.off()

# Feature Importance (Random Forest)
png("images/feature_importance.png", width = 800, height = 600, res = 300)
varImpPlot(models$random_forest, main = "Feature Importance (Random Forest)")
dev.off()

# ROC Curves
png("images/roc_curves.png", width = 1000, height = 800, res = 300)
plot(roc(test_data$HeartDisease, all_results$logistic$probabilities), 
     main = "ROC Curves", col = "red", lwd = 2)
for(i in 2:length(all_results)) {
  if(!is.null(all_results[[i]]$probabilities)) {
    lines(roc(test_data$HeartDisease, all_results[[i]]$probabilities), 
          col = i, lwd = 2)
  }
}
legend("bottomright", legend = names(all_results), 
       col = 1:length(all_results), lwd = 2)
dev.off()

# =============================================================================
# TASK 3: MODEL COMPARISON
# =============================================================================

cat("\n=== TASK 3: MODEL COMPARISON ===\n\n")

# Select top 2 models based on F1-Score and AUC-ROC
top_models <- model_results %>%
  arrange(desc(F1_Score), desc(AUC_ROC)) %>%
  head(2)

cat("Top 2 Models Selected:\n")
print(top_models)

# Detailed comparison
cat("\n=== DETAILED COMPARISON OF TOP 2 MODELS ===\n")
for(i in 1:2) {
  model_name <- top_models$Model[i]
  cat("\n", model_name, "Confusion Matrix:\n")
  print(all_results[[model_name]]$confusion_matrix$table)
}

# Save comparison
write.csv(top_models, "top_2_models_comparison.csv", row.names = FALSE)

cat("\n=== ANALYSIS COMPLETE ===\n")
cat("All results saved to:\n")
cat("- model_performance.csv\n")
cat("- top_2_models_comparison.csv\n")
cat("- images/ folder\n")

