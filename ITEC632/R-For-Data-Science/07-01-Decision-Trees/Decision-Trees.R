 Decision Trees Classifier — Cars Dataset (R)
# This script demonstrates Decision Tree classification for car evaluation

# Helper: install missing packages quietly
install_if_missing <- function(pkgs) {
  for (p in pkgs) {
    if (!requireNamespace(p, quietly = TRUE)) {
      install.packages(p, repos = "https://cloud.r-project.org", quiet = TRUE)
    }
  }
}

install_if_missing(c("rpart", "rpart.plot", "caret", "ggplot2", "dplyr", "corrplot", "gridExtra", "pROC", "tidyr"))

library(rpart)        # For Decision Trees
library(rpart.plot)   # For plotting decision trees
library(caret)        # For data splitting and confusion matrix
library(ggplot2)      # For plotting
library(dplyr)        # For data manipulation
library(corrplot)     # For correlation matrix visualization
library(gridExtra)    # For arranging multiple plots
library(pROC)         # For ROC curve
library(tidyr)        # For pivot_longer

set.seed(16)

# Create images directory if it doesn't exist
if (!dir.exists("images")) {
  dir.create("images")
}

# -----------------------------
# 1) Load and preprocess data
# -----------------------------
load_cars_data <- function() {
  cat("=== Loading Cars Dataset ===\n")
  path <- file.path("Cars-Dataset", "cars.csv")
  
  # Read CSV without header
  df <- read.csv(path, header = FALSE, stringsAsFactors = FALSE)
  
  # Set column names based on the dataset description
  col_names <- c('buying', 'maint', 'doors', 'persons', 'lug_boot', 'safety', 'class')
  colnames(df) <- col_names
  
  cat("Rows:", nrow(df), "Cols:", ncol(df), "\n")
  cat("Columns:", paste(col_names, collapse = ", "), "\n")
  
  # Convert all columns to factors since they are categorical
  df[] <- lapply(df, factor)
  
  df
}

# -----------------------------
# 2) Exploratory Data Analysis
# -----------------------------
create_eda_plots <- function(df) {
  cat("\n=== Creating EDA plots ===\n")
  
  # Target distribution
  p_target <- ggplot(df, aes(x = class, fill = class)) +
    geom_bar(alpha = 0.9) +
    scale_fill_manual(values = c("unacc" = "#e31a1c", "acc" = "#1f78b4", 
                                "good" = "#33a02c", "vgood" = "#ff7f00")) +
    labs(title = "Car Class Distribution", x = "Class", y = "Count") +
    theme_minimal() +
    theme(panel.background = element_rect(fill = "white"), 
          axis.text.x = element_text(angle = 45, hjust = 1),
          legend.position = "none")
  ggsave("images/01_class_distribution.png", p_target, width = 8, height = 6, dpi = 300, bg = "white")
  cat("Saved: images/01_class_distribution.png\n")
  
  # Feature distributions
  feature_cols <- c('buying', 'maint', 'doors', 'persons', 'lug_boot', 'safety')
  
  # Create bar plots for each feature
  plots <- list()
  for (i in seq_along(feature_cols)) {
    col_name <- feature_cols[i]
    plots[[i]] <- ggplot(df, aes_string(x = col_name, fill = "class")) +
      geom_bar(position = "dodge", alpha = 0.8) +
      scale_fill_manual(values = c("unacc" = "#e31a1c", "acc" = "#1f78b4", 
                                  "good" = "#33a02c", "vgood" = "#ff7f00")) +
      labs(title = paste("Distribution of", col_name), x = col_name, y = "Count") +
      theme_minimal() +
      theme(panel.background = element_rect(fill = "white"),
            axis.text.x = element_text(angle = 45, hjust = 1),
            legend.position = "none")
  }
  
  # Arrange plots in a grid
  combined_plot <- do.call(grid.arrange, c(plots, ncol = 2))
  ggsave("images/02_feature_distributions.png", combined_plot, width = 12, height = 10, dpi = 300, bg = "white")
  cat("Saved: images/02_feature_distributions.png\n")
  
  # Class distribution by safety level
  p_safety <- ggplot(df, aes(x = .data$safety, fill = .data$class)) +
    geom_bar(position = "fill", alpha = 0.8) +
    scale_fill_manual(values = c("unacc" = "#e31a1c", "acc" = "#1f78b4", 
                                "good" = "#33a02c", "vgood" = "#ff7f00")) +
    labs(title = "Class Distribution by Safety Level", x = "Safety", y = "Proportion") +
    theme_minimal() +
    theme(panel.background = element_rect(fill = "white"))
  ggsave("images/03_safety_class_distribution.png", p_safety, width = 8, height = 6, dpi = 300, bg = "white")
  cat("Saved: images/03_safety_class_distribution.png\n")
}

# -----------------------------
# 3) Train Decision Tree models
# -----------------------------
train_decision_trees <- function(df) {
  cat("\n=== Training Decision Trees ===\n")
  
  # Split data into training and test sets (70/30)
  train_idx <- createDataPartition(df$class, p = 0.7, list = FALSE)
  train_data <- df[train_idx, ]
  test_data <- df[-train_idx, ]
  
  cat("Training set size:", nrow(train_data), "\n")
  cat("Test set size:", nrow(test_data), "\n")
  
  # Train Decision Tree with Gini criterion
  cat("\n--- Training Decision Tree with Gini criterion ---\n")
  dt_gini <- rpart(class ~ ., data = train_data, method = "class", 
                   parms = list(split = "gini"))
  
  # Train Decision Tree with Information Gain (entropy)
  cat("--- Training Decision Tree with Information Gain ---\n")
  dt_entropy <- rpart(class ~ ., data = train_data, method = "class", 
                      parms = list(split = "information"))
  
  # Make predictions
  pred_gini_train <- predict(dt_gini, train_data, type = "class")
  pred_gini_test <- predict(dt_gini, test_data, type = "class")
  
  pred_entropy_train <- predict(dt_entropy, train_data, type = "class")
  pred_entropy_test <- predict(dt_entropy, test_data, type = "class")
  
  # Calculate accuracies
  acc_gini_train <- mean(pred_gini_train == train_data$class)
  acc_gini_test <- mean(pred_gini_test == test_data$class)
  acc_entropy_train <- mean(pred_entropy_train == train_data$class)
  acc_entropy_test <- mean(pred_entropy_test == test_data$class)
  
  cat(sprintf("Gini - Train Acc: %.4f, Test Acc: %.4f\n", acc_gini_train, acc_gini_test))
  cat(sprintf("Entropy - Train Acc: %.4f, Test Acc: %.4f\n", acc_entropy_train, acc_entropy_test))
  
  # Visualize decision trees
  visualize_decision_trees(dt_gini, dt_entropy)
  
  # Create confusion matrices
  create_confusion_matrices(pred_gini_test, pred_entropy_test, test_data$class)
  
  # Feature importance
  create_feature_importance_plots(dt_gini, dt_entropy)
  
  list(
    gini_model = dt_gini,
    entropy_model = dt_entropy,
    train_data = train_data,
    test_data = test_data,
    pred_gini_test = pred_gini_test,
    pred_entropy_test = pred_entropy_test
  )
}

# -----------------------------
# 4) Visualization functions
# -----------------------------
visualize_decision_trees <- function(dt_gini, dt_entropy) {
  cat("\n=== Creating Decision Tree Visualizations ===\n")
  
  # Plot Gini tree
  png("images/04_decision_tree_gini.png", width = 1200, height = 800, res = 120, bg = "white")
  rpart.plot(dt_gini, main = "Decision Tree with Gini Criterion", 
             box.palette = "RdBu", shadow.col = "gray", nn = TRUE)
  dev.off()
  cat("Saved: images/04_decision_tree_gini.png\n")
  
  # Plot Entropy tree
  png("images/05_decision_tree_entropy.png", width = 1200, height = 800, res = 120, bg = "white")
  rpart.plot(dt_entropy, main = "Decision Tree with Information Gain", 
             box.palette = "RdBu", shadow.col = "gray", nn = TRUE)
  dev.off()
  cat("Saved: images/05_decision_tree_entropy.png\n")
}

create_confusion_matrices <- function(pred_gini, pred_entropy, actual) {
  cat("\n=== Creating Confusion Matrices ===\n")
  
  # Gini confusion matrix
  cm_gini <- confusionMatrix(pred_gini, actual)
  cm_gini_df <- as.data.frame(cm_gini$table)
  
  p_gini <- ggplot(cm_gini_df, aes(x = .data$Prediction, y = .data$Reference, fill = .data$Freq)) +
    geom_tile() +
    scale_fill_gradient(low = "white", high = "red", name = "Count") +
    geom_text(aes(label = .data$Freq), color = "black", size = 4) +
    labs(title = sprintf("Confusion Matrix - Gini (Accuracy: %.3f)", cm_gini$overall['Accuracy']),
         x = "Predicted", y = "Actual") +
    theme_minimal() +
    theme(axis.text.x = element_text(angle = 45, hjust = 1),
          panel.background = element_rect(fill = "white"))
  ggsave("images/06_confusion_matrix_gini.png", p_gini, width = 8, height = 6, dpi = 300, bg = "white")
  
  # Entropy confusion matrix
  cm_entropy <- confusionMatrix(pred_entropy, actual)
  cm_entropy_df <- as.data.frame(cm_entropy$table)
  
  p_entropy <- ggplot(cm_entropy_df, aes(x = .data$Prediction, y = .data$Reference, fill = .data$Freq)) +
    geom_tile() +
    scale_fill_gradient(low = "white", high = "red", name = "Count") +
    geom_text(aes(label = .data$Freq), color = "black", size = 4) +
    labs(title = sprintf("Confusion Matrix - Entropy (Accuracy: %.3f)", cm_entropy$overall['Accuracy']),
         x = "Predicted", y = "Actual") +
    theme_minimal() +
    theme(axis.text.x = element_text(angle = 45, hjust = 1),
          panel.background = element_rect(fill = "white"))
  ggsave("images/07_confusion_matrix_entropy.png", p_entropy, width = 8, height = 6, dpi = 300, bg = "white")
  
  cat("Saved: images/06_confusion_matrix_gini.png\n")
  cat("Saved: images/07_confusion_matrix_entropy.png\n")
}

create_feature_importance_plots <- function(dt_gini, dt_entropy) {
  cat("\n=== Creating Feature Importance Plots ===\n")
  
  # Extract variable importance
  importance_gini <- dt_gini$variable.importance
  importance_entropy <- dt_entropy$variable.importance
  
  # Create data frames for plotting
  imp_gini_df <- data.frame(
    Feature = names(importance_gini),
    Importance = as.numeric(importance_gini),
    Method = "Gini"
  )
  
  imp_entropy_df <- data.frame(
    Feature = names(importance_entropy),
    Importance = as.numeric(importance_entropy),
    Method = "Entropy"
  )
  
  # Combine and plot
  imp_combined <- rbind(imp_gini_df, imp_entropy_df)
  
  p_importance <- ggplot(imp_combined, aes(x = reorder(.data$Feature, .data$Importance), y = .data$Importance, fill = .data$Method)) +
    geom_bar(stat = "identity", position = "dodge", alpha = 0.8) +
    coord_flip() +
    scale_fill_manual(values = c("Gini" = "#1f78b4", "Entropy" = "#33a02c")) +
    labs(title = "Feature Importance Comparison", x = "Features", y = "Importance") +
    theme_minimal() +
    theme(panel.background = element_rect(fill = "white"))
  ggsave("images/08_feature_importance.png", p_importance, width = 10, height = 6, dpi = 300, bg = "white")
  cat("Saved: images/08_feature_importance.png\n")
}

# -----------------------------
# 5) Model evaluation
# -----------------------------
evaluate_models <- function(results) {
  cat("\n=== Model Evaluation ===\n")
  
  # Print detailed results
  cat("\n--- Gini Model Results ---\n")
  print(summary(results$gini_model))
  
  cat("\n--- Entropy Model Results ---\n")
  print(summary(results$entropy_model))
  
  # Classification reports
  cat("\n--- Classification Report - Gini ---\n")
  print(confusionMatrix(results$pred_gini_test, results$test_data$class))
  
  cat("\n--- Classification Report - Entropy ---\n")
  print(confusionMatrix(results$pred_entropy_test, results$test_data$class))
}

# -----------------------------
# 6) Main function
# -----------------------------
main <- function() {
  cat("=== Decision Trees Classifier (Cars Dataset) ===\n\n")
  
  # Load and preprocess data
  cars_data <- load_cars_data()
  
  # Exploratory data analysis
  create_eda_plots(cars_data)
  
  # Train models
  results <- train_decision_trees(cars_data)
  
  # Evaluate models
  evaluate_models(results)
  
  cat("\nAll plots saved to images/ directory.\n")
  cat("Decision Trees analysis completed successfully!\n")
}

# Run main function if script is executed directly
if (!interactive()) {
  main()
}
