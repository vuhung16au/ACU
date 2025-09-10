# Linear Discriminant Analysis (LDA) with Pokemon Dataset
# This script demonstrates LDA for dimensionality reduction and classification

# Load required libraries
library(MASS)      # For LDA
library(ggplot2)   # For plotting
library(dplyr)     # For data manipulation
library(corrplot)  # For correlation matrix visualization
library(gridExtra) # For arranging multiple plots

# Set seed for reproducibility
set.seed(16)

# Create images directory if it doesn't exist
if (!dir.exists("images")) {
  dir.create("images")
}

# Function to generate sample data for demonstration
generate_sample_data <- function(n_samples = 300) {
  # Generate three classes with different means and same covariance
  class1 <- MASS::mvrnorm(n = n_samples/3, 
                          mu = c(50, 60, 70), 
                          Sigma = matrix(c(100, 20, 15, 20, 100, 25, 15, 25, 100), 3, 3))
  class2 <- MASS::mvrnorm(n = n_samples/3, 
                          mu = c(80, 40, 50), 
                          Sigma = matrix(c(100, 20, 15, 20, 100, 25, 15, 25, 100), 3, 3))
  class3 <- MASS::mvrnorm(n = n_samples/3, 
                          mu = c(30, 90, 80), 
                          Sigma = matrix(c(100, 20, 15, 20, 100, 25, 15, 25, 100), 3, 3))
  
  # Combine data
  sample_data <- rbind(
    data.frame(feature1 = class1[,1], feature2 = class1[,2], feature3 = class1[,3], class = "Class A"),
    data.frame(feature1 = class2[,1], feature2 = class2[,2], feature3 = class2[,3], class = "Class B"),
    data.frame(feature1 = class3[,1], feature2 = class3[,2], feature3 = class3[,3], class = "Class C")
  )
  
  return(sample_data)
}

# Function to perform LDA on sample data
analyze_sample_data <- function() {
  cat("=== Sample Data Analysis ===\n")
  
  # Generate sample data
  sample_data <- generate_sample_data()
  
  # Prepare data for LDA
  X_sample <- as.matrix(sample_data[, 1:3])
  y_sample <- sample_data$class
  
  # Perform LDA
  lda_sample <- lda(X_sample, y_sample)
  
  # Transform data
  X_lda_sample <- predict(lda_sample, X_sample)$x
  
  # Create visualization
  p1 <- ggplot(data.frame(
    LD1 = X_lda_sample[,1], 
    LD2 = X_lda_sample[,2], 
    class = y_sample
  ), aes(x = LD1, y = LD2, color = class)) +
    geom_point(size = 2, alpha = 0.7) +
    stat_ellipse(level = 0.95) +
    labs(title = "Sample Data: LDA Projection",
         x = "Linear Discriminant 1", 
         y = "Linear Discriminant 2") +
    theme_minimal() +
    theme(legend.position = "bottom",
          panel.background = element_rect(fill = "white"))
  
  # Save plot with white background
  ggsave("images/01_sample_data_lda.png", p1, width = 8, height = 6, dpi = 300, bg = "white")
  
  cat("Sample data LDA plot saved as: images/01_sample_data_lda.png\n")
  
  return(list(lda_model = lda_sample, data = sample_data))
}

# Function to load and preprocess Pokemon data
load_pokemon_data <- function() {
  cat("\n=== Loading Pokemon Dataset ===\n")
  
  # Read Pokemon dataset
  pokemon <- read.csv("Pokemon-Dataset/pokemon.csv")
  
  cat("Dataset loaded with", nrow(pokemon), "rows and", ncol(pokemon), "columns\n")
  cat("Columns:", paste(colnames(pokemon), collapse = ", "), "\n")
  
  return(pokemon)
}

# Function to preprocess Pokemon data for LDA
preprocess_pokemon_data <- function(pokemon) {
  cat("\n=== Preprocessing Pokemon Data ===\n")
  
  # Select only Pokemon with single type (no dual types)
  single_type_pokemon <- pokemon[is.na(pokemon$type2) | pokemon$type2 == "", ]
  
  cat("Single-type Pokemon count:", nrow(single_type_pokemon), "\n")
  
  # Select relevant features for analysis
  features <- c("hp", "attack", "defense", "sp_attack", "sp_defense", "speed", "type1")
  pokemon_subset <- single_type_pokemon[, features]
  
  # Remove rows with missing values
  pokemon_subset <- pokemon_subset[complete.cases(pokemon_subset), ]
  
  cat("Final dataset size:", nrow(pokemon_subset), "rows\n")
  
  # Show type distribution
  type_counts <- table(pokemon_subset$type1)
  cat("Type distribution:\n")
  print(type_counts)
  
  return(pokemon_subset)
}

# Function to perform LDA on Pokemon data
perform_pokemon_lda <- function(pokemon_data) {
  cat("\n=== Performing LDA on Pokemon Data ===\n")
  
  # Prepare data for LDA
  X <- as.matrix(pokemon_data[, 1:6])  # Features
  y <- pokemon_data$type1               # Target variable
  
  # Normalize data (optional but recommended)
  X_scaled <- scale(X)
  
  # Perform LDA
  lda_model <- lda(X_scaled, y)
  
  cat("LDA model fitted successfully\n")
  cat("Number of classes:", length(lda_model$lev), "\n")
  cat("Prior probabilities:\n")
  print(lda_model$prior)
  
  # Transform data using LDA
  X_lda <- predict(lda_model, X_scaled)$x
  
  return(list(lda_model = lda_model, X_lda = X_lda, X_scaled = X_scaled, y = y))
}

# Function to analyze LDA coefficients
analyze_lda_coefficients <- function(lda_result, feature_names) {
  cat("\n=== Analyzing LDA Coefficients ===\n")
  
  # Extract coefficients
  coef_matrix <- lda_result$lda_model$scaling
  
  # Create coefficient heatmap
  coef_df <- data.frame(
    feature = rep(feature_names, ncol(coef_matrix)),
    discriminant = rep(paste0("LD", 1:ncol(coef_matrix)), each = length(feature_names)),
    coefficient = as.vector(coef_matrix)
  )
  
  p2 <- ggplot(coef_df, aes(x = discriminant, y = feature, fill = coefficient)) +
    geom_tile() +
    scale_fill_gradient2(low = "blue", mid = "white", high = "red", 
                         midpoint = 0, name = "Coefficient") +
    labs(title = "LDA Coefficients Heatmap",
         x = "Linear Discriminants", 
         y = "Features") +
    theme_minimal() +
    theme(axis.text.x = element_text(angle = 45, hjust = 1),
          panel.background = element_rect(fill = "white"))
  
  # Save plot with white background
  ggsave("images/02_lda_coefficients.png", p2, width = 8, height = 6, dpi = 300, bg = "white")
  
  cat("LDA coefficients heatmap saved as: images/02_lda_coefficients.png\n")
  
  return(coef_matrix)
}

# Function to visualize LDA projections
visualize_lda_projections <- function(lda_result, pokemon_data) {
  cat("\n=== Visualizing LDA Projections ===\n")
  
  # Create data frame for plotting
  plot_data <- data.frame(
    LD1 = lda_result$X_lda[,1],
    LD2 = lda_result$X_lda[,2],
    LD3 = lda_result$X_lda[,3],
    type = lda_result$y
  )
  
  # Plot first two discriminants
  p3 <- ggplot(plot_data, aes(x = LD1, y = LD2, color = type)) +
    geom_point(alpha = 0.6, size = 2) +
    stat_ellipse(level = 0.95, alpha = 0.3) +
    labs(title = "Pokemon Types: LDA Projection (LD1 vs LD2)",
         x = "Linear Discriminant 1", 
         y = "Linear Discriminant 2") +
    theme_minimal() +
    theme(legend.position = "bottom",
          legend.text = element_text(size = 8),
          panel.background = element_rect(fill = "white"))
  
  # Save plot with white background
  ggsave("images/03_pokemon_lda_projection.png", p3, width = 10, height = 8, dpi = 300, bg = "white")
  
  # Plot first vs third discriminant
  p4 <- ggplot(plot_data, aes(x = LD1, y = LD3, color = type)) +
    geom_point(alpha = 0.6, size = 2) +
    stat_ellipse(level = 0.95, alpha = 0.3) +
    labs(title = "Pokemon Types: LDA Projection (LD1 vs LD3)",
         x = "Linear Discriminant 1", 
         y = "Linear Discriminant 3") +
    theme_minimal() +
    theme(legend.position = "bottom",
          legend.text = element_text(size = 8),
          panel.background = element_rect(fill = "white"))
  
  # Save plot with white background
  ggsave("images/04_pokemon_lda_ld1_ld3.png", p4, width = 10, height = 8, dpi = 300, bg = "white")
  
  cat("LDA projection plots saved as:\n")
  cat("  - images/03_pokemon_lda_projection.png\n")
  cat("  - images/04_pokemon_lda_ld1_ld3.png\n")
  
  return(plot_data)
}

# Function to evaluate LDA performance
evaluate_lda_performance <- function(lda_result, pokemon_data) {
  cat("\n=== Evaluating LDA Performance ===\n")
  
  # Make predictions
  predictions <- predict(lda_result$lda_model, lda_result$X_scaled)
  
  # Calculate accuracy
  accuracy <- mean(predictions$class == lda_result$y)
  cat("Overall accuracy:", round(accuracy * 100, 2), "%\n")
  
  # Create confusion matrix
  conf_matrix <- table(Actual = lda_result$y, Predicted = predictions$class)
  
  # Calculate per-class accuracy
  per_class_accuracy <- diag(conf_matrix) / rowSums(conf_matrix)
  cat("\nPer-class accuracy:\n")
  print(round(per_class_accuracy * 100, 2))
  
  # Create confusion matrix heatmap
  conf_df <- as.data.frame(conf_matrix)
  conf_df$Actual <- factor(conf_df$Actual, levels = unique(conf_df$Actual))
  conf_df$Predicted <- factor(conf_df$Predicted, levels = unique(conf_df$Predicted))
  
  p5 <- ggplot(conf_df, aes(x = Predicted, y = Actual, fill = Freq)) +
    geom_tile() +
    scale_fill_gradient(low = "white", high = "red", name = "Count") +
    geom_text(aes(label = Freq), color = "black", size = 3) +
    labs(title = "Confusion Matrix Heatmap",
         x = "Predicted Type", 
         y = "Actual Type") +
    theme_minimal() +
    theme(axis.text.x = element_text(angle = 45, hjust = 1),
          axis.text.y = element_text(size = 8),
          panel.background = element_rect(fill = "white"))
  
  # Save plot with white background
  ggsave("images/05_confusion_matrix.png", p5, width = 10, height = 8, dpi = 300, bg = "white")
  
  cat("Confusion matrix heatmap saved as: images/05_confusion_matrix.png\n")
  
  return(list(accuracy = accuracy, conf_matrix = conf_matrix, per_class_accuracy = per_class_accuracy))
}

# Function to create correlation matrix visualization
create_correlation_plot <- function(pokemon_data) {
  cat("\n=== Creating Correlation Matrix ===\n")
  
  # Calculate correlation matrix
  numeric_features <- pokemon_data[, 1:6]
  cor_matrix <- cor(numeric_features)
  
  # Create correlation plot with white background
  png("images/06_correlation_matrix.png", width = 800, height = 600, res = 100, bg = "white")
  corrplot(cor_matrix, method = "color", type = "upper", 
           addCoef.col = "black", tl.col = "black", tl.srt = 45,
           title = "Pokemon Stats Correlation Matrix",
           mar = c(0,0,2,0))
  dev.off()
  
  cat("Correlation matrix saved as: images/06_correlation_matrix.png\n")
  
  return(cor_matrix)
}

# Function to create feature importance plot
create_feature_importance_plot <- function(lda_result, feature_names) {
  cat("\n=== Creating Feature Importance Plot ===\n")
  
  # Calculate feature importance (sum of absolute coefficients across discriminants)
  coef_matrix <- lda_result$lda_model$scaling
  feature_importance <- rowSums(abs(coef_matrix))
  
  # Create feature importance plot
  importance_df <- data.frame(
    feature = feature_names,
    importance = feature_importance
  )
  
  p6 <- ggplot(importance_df, aes(x = reorder(feature, importance), y = importance)) +
    geom_bar(stat = "identity", fill = "steelblue", alpha = 0.8) +
    coord_flip() +
    labs(title = "Feature Importance in LDA",
         x = "Features", 
         y = "Importance Score") +
    theme_minimal() +
    theme(axis.text.y = element_text(size = 10),
          panel.background = element_rect(fill = "white"))
  
  # Save plot with white background
  ggsave("images/07_feature_importance.png", p6, width = 8, height = 6, dpi = 300, bg = "white")
  
  cat("Feature importance plot saved as: images/07_feature_importance.png\n")
  
  return(importance_df)
}

# Main execution function
main <- function() {
  cat("=== Linear Discriminant Analysis with Pokemon Dataset ===\n\n")
  
  # 1. Analyze sample data
  sample_results <- analyze_sample_data()
  
  # 2. Load Pokemon data
  pokemon <- load_pokemon_data()
  
  # 3. Preprocess data
  pokemon_processed <- preprocess_pokemon_data(pokemon)
  
  # 4. Perform LDA
  lda_results <- perform_pokemon_lda(pokemon_processed)
  
  # 5. Analyze coefficients
  feature_names <- c("HP", "Attack", "Defense", "Sp. Attack", "Sp. Defense", "Speed")
  coef_analysis <- analyze_lda_coefficients(lda_results, feature_names)
  
  # 6. Visualize projections
  projection_data <- visualize_lda_projections(lda_results, pokemon_processed)
  
  # 7. Evaluate performance
  performance_results <- evaluate_lda_performance(lda_results, pokemon_processed)
  
  # 8. Create correlation matrix
  correlation_matrix <- create_correlation_plot(pokemon_processed)
  
  # 9. Create feature importance plot
  feature_importance <- create_feature_importance_plot(lda_results, feature_names)
  
  # Summary
  cat("\n=== Analysis Summary ===\n")
  cat("✓ Sample data analysis completed\n")
  cat("✓ Pokemon dataset loaded and preprocessed\n")
  cat("✓ LDA model fitted successfully\n")
  cat("✓ Coefficient analysis completed\n")
  cat("✓ Projection visualizations created\n")
  cat("✓ Performance evaluation completed\n")
  cat("✓ Correlation matrix created\n")
  cat("✓ Feature importance analysis completed\n")
  cat("✓ All plots saved to images/ directory\n")
  
  cat("\nLDA Analysis completed successfully!\n")
}

# Run the main function
if (!interactive()) {
  main()
}
