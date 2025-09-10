# =============================================================================
# K-MEANS CLUSTERING - AMES HOUSING DATASET
# =============================================================================
# File: k-Means.R
# Purpose: Implement k-means clustering for feature engineering using Ames Housing dataset
# Author: Data Science Project
# Date: 2024

# =============================================================================
# SETUP AND PACKAGE LOADING
# =============================================================================

# Set random seed for reproducibility
set.seed(16)

# Load required packages
required_packages <- c("ggplot2", "dplyr", "cluster", "factoextra", "gridExtra", 
                      "RColorBrewer", "corrplot", "VIM", "mice", "caret")

# Function to install and load packages
install_and_load <- function(package) {
  if (!require(package, character.only = TRUE, quietly = TRUE)) {
    cat("Installing package:", package, "\n")
    install.packages(package, repos = "https://cran.rstudio.com/")
    library(package, character.only = TRUE)
  }
}

# Install and load all required packages
sapply(required_packages, install_and_load)

cat("=== K-MEANS CLUSTERING ANALYSIS ===\n")
cat("Ames Housing Dataset - Feature Engineering\n\n")

# =============================================================================
# DATA LOADING AND PREPARATION
# =============================================================================

cat("1. Loading and preparing data...\n")

# Load the dataset
data <- read.csv("Housing-Dataset/housing-ames.csv", stringsAsFactors = FALSE)

# Display basic information about the dataset
cat("Dataset dimensions:", dim(data), "\n")
cat("Number of observations:", nrow(data), "\n")
cat("Number of variables:", ncol(data), "\n\n")

# Display first few rows
cat("First few rows of the dataset:\n")
print(head(data, 3))

# Check for missing values
missing_summary <- sapply(data, function(x) sum(is.na(x)))
missing_data <- data.frame(
  Variable = names(missing_summary),
  Missing_Count = missing_summary,
  Missing_Percentage = round(missing_summary / nrow(data) * 100, 2)
)
missing_data <- missing_data[missing_data$Missing_Count > 0, ]

if (nrow(missing_data) > 0) {
  cat("\nVariables with missing values:\n")
  print(missing_data)
} else {
  cat("\nNo missing values found in the dataset.\n")
}

# =============================================================================
# FEATURE SELECTION AND PREPROCESSING
# =============================================================================

cat("\n2. Feature selection and preprocessing...\n")

# Select features for clustering (similar to the reference notebook)
# Features: LotArea, TotalBsmtSF, FirstFlrSF, SecondFlrSF, GrLivArea
clustering_features <- c("LotArea", "TotalBsmtSF", "FirstFlrSF", "SecondFlrSF", "GrLivArea")

# Check if all features exist in the dataset
available_features <- clustering_features[clustering_features %in% colnames(data)]
missing_features <- clustering_features[!clustering_features %in% colnames(data)]

if (length(missing_features) > 0) {
  cat("Warning: Some features not found in dataset:", missing_features, "\n")
  cat("Available features:", available_features, "\n")
}

# Use available features
features_to_use <- available_features
cat("Features selected for clustering:", features_to_use, "\n")

# Extract features and target variable
X <- data[, features_to_use, drop = FALSE]
y <- data$SalePrice

# Remove rows with missing values in selected features
complete_cases <- complete.cases(X)
X_clean <- X[complete_cases, ]
y_clean <- y[complete_cases]

cat("After removing missing values:\n")
cat("Observations:", nrow(X_clean), "\n")
cat("Features:", ncol(X_clean), "\n\n")

# =============================================================================
# FEATURE SCALING
# =============================================================================

cat("3. Feature scaling...\n")

# Standardize features (z-score normalization)
X_scaled <- scale(X_clean)
X_scaled <- as.data.frame(X_scaled)

cat("Features standardized using z-score normalization.\n")
cat("Scaled data summary:\n")
print(summary(X_scaled))

# =============================================================================
# EXPLORATORY DATA ANALYSIS
# =============================================================================

cat("\n4. Exploratory data analysis...\n")

# Create correlation matrix
correlation_matrix <- cor(X_clean)

# Save correlation matrix plot
png("images/01_correlation_matrix.png", width = 800, height = 600, bg = "white")
corrplot(correlation_matrix, method = "color", type = "upper", 
         order = "hclust", tl.cex = 0.8, tl.col = "black",
         addCoef.col = "black", number.cex = 0.7,
         title = "Correlation Matrix of Clustering Features",
         mar = c(0,0,2,0))
dev.off()
cat("Saved: images/01_correlation_matrix.png\n")

# Create feature distribution plots
png("images/02_feature_distributions.png", width = 1200, height = 800, bg = "white")
par(mfrow = c(2, 3), mar = c(4, 4, 3, 2))

for (i in seq_len(ncol(X_clean))) {
  hist(X_clean[, i], main = paste("Distribution of", colnames(X_clean)[i]),
       xlab = colnames(X_clean)[i], col = "lightblue", border = "black")
}

# Add SalePrice distribution
hist(y_clean, main = "Distribution of SalePrice", 
     xlab = "SalePrice", col = "lightgreen", border = "black")

dev.off()
cat("Saved: images/02_feature_distributions.png\n")

# Create scatter plot matrix
png("images/03_scatter_plot_matrix.png", width = 1000, height = 1000, bg = "white")
pairs(X_clean, main = "Scatter Plot Matrix of Clustering Features",
      pch = 16, col = "blue", cex = 0.5)
dev.off()
cat("Saved: images/03_scatter_plot_matrix.png\n")

# =============================================================================
# DETERMINE OPTIMAL NUMBER OF CLUSTERS
# =============================================================================

cat("\n5. Determining optimal number of clusters...\n")

# Elbow method
wss <- numeric(15)
for (k in 1:15) {
  kmeans_result <- kmeans(X_scaled, centers = k, nstart = 25, iter.max = 100)
  wss[k] <- kmeans_result$tot.withinss
}

# Save elbow plot
png("images/04_elbow_method.png", width = 800, height = 600, bg = "white")
plot(1:15, wss, type = "b", pch = 19, frame = FALSE,
     xlab = "Number of Clusters (k)", ylab = "Within-cluster Sum of Squares",
     main = "Elbow Method for Optimal k",
     col = "blue", lwd = 2)
grid()
dev.off()
cat("Saved: images/04_elbow_method.png\n")

# Silhouette method
silhouette_scores <- numeric(15)
for (k in 2:15) {
  kmeans_result <- kmeans(X_scaled, centers = k, nstart = 25, iter.max = 100)
  silhouette_result <- silhouette(kmeans_result$cluster, dist(X_scaled))
  silhouette_scores[k] <- mean(silhouette_result[, 3])
}

# Save silhouette plot
png("images/05_silhouette_method.png", width = 800, height = 600, bg = "white")
plot(2:15, silhouette_scores[2:15], type = "b", pch = 19, frame = FALSE,
     xlab = "Number of Clusters (k)", ylab = "Average Silhouette Score",
     main = "Silhouette Method for Optimal k",
     col = "red", lwd = 2)
grid()
dev.off()
cat("Saved: images/05_silhouette_method.png\n")

# Find optimal k
optimal_k_elbow <- which.min(diff(wss)) + 1
optimal_k_silhouette <- which.max(silhouette_scores[2:15]) + 1

cat("Optimal k (Elbow method):", optimal_k_elbow, "\n")
cat("Optimal k (Silhouette method):", optimal_k_silhouette, "\n")

# Use k=10 as specified in the reference notebook
final_k <- 10
cat("Using k =", final_k, "as specified in reference notebook.\n")

# =============================================================================
# K-MEANS CLUSTERING
# =============================================================================

cat("\n6. Performing k-means clustering...\n")

# Perform k-means clustering
kmeans_result <- kmeans(X_scaled, centers = final_k, nstart = 25, iter.max = 100)

# Add cluster labels to the dataset
X_with_clusters <- X_clean
X_with_clusters$Cluster <- as.factor(kmeans_result$cluster)
X_with_clusters$SalePrice <- y_clean

cat("K-means clustering completed.\n")
cat("Cluster sizes:\n")
print(table(kmeans_result$cluster))

# =============================================================================
# CLUSTER ANALYSIS AND VISUALIZATION
# =============================================================================

cat("\n7. Cluster analysis and visualization...\n")

# Create cluster visualization using first two principal components
pca_result <- prcomp(X_scaled, scale. = FALSE)
pca_data <- data.frame(
  PC1 = pca_result$x[, 1],
  PC2 = pca_result$x[, 2],
  Cluster = as.factor(kmeans_result$cluster)
)

# Save PCA cluster plot
png("images/06_pca_cluster_plot.png", width = 800, height = 600, bg = "white")
ggplot(pca_data, aes(x = PC1, y = PC2, color = Cluster)) +
  geom_point(alpha = 0.7, size = 2) +
  labs(title = "K-Means Clusters (PCA Visualization)",
       x = paste("PC1 (", round(summary(pca_result)$importance[2, 1] * 100, 1), "%)"),
       y = paste("PC2 (", round(summary(pca_result)$importance[2, 2] * 100, 1), "%)")) +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white"),
        plot.background = element_rect(fill = "white"),
        legend.position = "bottom")
dev.off()
cat("Saved: images/06_pca_cluster_plot.png\n")

# Create cluster centers visualization
cluster_centers <- kmeans_result$centers
cluster_centers_df <- data.frame(
  Cluster = rep(1:final_k, each = ncol(X_scaled)),
  Feature = rep(colnames(X_scaled), final_k),
  Center_Value = as.vector(t(cluster_centers))
)

# Save cluster centers plot
png("images/07_cluster_centers.png", width = 1000, height = 600, bg = "white")
ggplot(cluster_centers_df, aes(x = Feature, y = Center_Value, fill = as.factor(Cluster))) +
  geom_bar(stat = "identity", position = "dodge") +
  labs(title = "Cluster Centers (Standardized Values)",
       x = "Features", y = "Standardized Center Value", fill = "Cluster") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white"),
        plot.background = element_rect(fill = "white"),
        axis.text.x = element_text(angle = 45, hjust = 1),
        legend.position = "bottom")
dev.off()
cat("Saved: images/07_cluster_centers.png\n")

# =============================================================================
# CLUSTER-DISTANCE FEATURES
# =============================================================================

cat("\n8. Creating cluster-distance features...\n")

# Calculate distances to cluster centers
distances_to_centers <- matrix(0, nrow = nrow(X_scaled), ncol = final_k)

for (i in seq_len(nrow(X_scaled))) {
  for (j in seq_len(final_k)) {
    distances_to_centers[i, j] <- sqrt(sum((X_scaled[i, ] - cluster_centers[j, ])^2))
  }
}

# Create distance features dataframe
distance_features <- data.frame(distances_to_centers)
colnames(distance_features) <- paste0("Centroid_", 1:final_k)

# Add distance features to the dataset
X_with_distances <- cbind(X_clean, distance_features)
X_with_distances$Cluster <- as.factor(kmeans_result$cluster)
X_with_distances$SalePrice <- y_clean

cat("Cluster-distance features created.\n")
cat("New dataset dimensions:", dim(X_with_distances), "\n")

# =============================================================================
# CLUSTER CHARACTERISTICS ANALYSIS
# =============================================================================

cat("\n9. Analyzing cluster characteristics...\n")

# Calculate cluster statistics
cluster_stats <- X_with_clusters %>%
  group_by(Cluster) %>%
  summarise(
    Count = n(),
    Avg_SalePrice = mean(SalePrice, na.rm = TRUE),
    Avg_LotArea = mean(LotArea, na.rm = TRUE),
    Avg_TotalBsmtSF = mean(TotalBsmtSF, na.rm = TRUE),
    Avg_FirstFlrSF = mean(FirstFlrSF, na.rm = TRUE),
    Avg_SecondFlrSF = mean(SecondFlrSF, na.rm = TRUE),
    Avg_GrLivArea = mean(GrLivArea, na.rm = TRUE),
    .groups = 'drop'
  )

cat("Cluster characteristics:\n")
print(cluster_stats)

# Save cluster characteristics plot
png("images/08_cluster_characteristics.png", width = 1200, height = 800, bg = "white")
par(mfrow = c(2, 3), mar = c(4, 4, 3, 2))

# Plot average SalePrice by cluster
barplot(cluster_stats$Avg_SalePrice, names.arg = cluster_stats$Cluster,
        main = "Average Sale Price by Cluster", xlab = "Cluster", ylab = "Average Sale Price",
        col = "lightblue", border = "black")

# Plot average LotArea by cluster
barplot(cluster_stats$Avg_LotArea, names.arg = cluster_stats$Cluster,
        main = "Average Lot Area by Cluster", xlab = "Cluster", ylab = "Average Lot Area",
        col = "lightgreen", border = "black")

# Plot average TotalBsmtSF by cluster
barplot(cluster_stats$Avg_TotalBsmtSF, names.arg = cluster_stats$Cluster,
        main = "Average Total Basement SF by Cluster", xlab = "Cluster", ylab = "Average Total Basement SF",
        col = "lightcoral", border = "black")

# Plot average FirstFlrSF by cluster
barplot(cluster_stats$Avg_FirstFlrSF, names.arg = cluster_stats$Cluster,
        main = "Average First Floor SF by Cluster", xlab = "Cluster", ylab = "Average First Floor SF",
        col = "lightyellow", border = "black")

# Plot average SecondFlrSF by cluster
barplot(cluster_stats$Avg_SecondFlrSF, names.arg = cluster_stats$Cluster,
        main = "Average Second Floor SF by Cluster", xlab = "Cluster", ylab = "Average Second Floor SF",
        col = "lightpink", border = "black")

# Plot average GrLivArea by cluster
barplot(cluster_stats$Avg_GrLivArea, names.arg = cluster_stats$Cluster,
        main = "Average Living Area by Cluster", xlab = "Cluster", ylab = "Average Living Area",
        col = "lightgray", border = "black")

dev.off()
cat("Saved: images/08_cluster_characteristics.png\n")

# =============================================================================
# FEATURE ENGINEERING EVALUATION
# =============================================================================

cat("\n10. Evaluating feature engineering impact...\n")

# Create a simple model to evaluate the impact of cluster features
# Split data for evaluation
set.seed(16)
train_indices <- createDataPartition(y_clean, p = 0.7, list = FALSE)
X_train <- X_with_distances[train_indices, ]
X_test <- X_with_distances[-train_indices, ]
y_train <- y_clean[train_indices]
y_test <- y_clean[-train_indices]

# Model 1: Original features only
model_original <- lm(SalePrice ~ LotArea + TotalBsmtSF + FirstFlrSF + SecondFlrSF + GrLivArea, 
                     data = X_train)

# Model 2: Original features + cluster labels
model_with_clusters <- lm(SalePrice ~ LotArea + TotalBsmtSF + FirstFlrSF + SecondFlrSF + GrLivArea + Cluster, 
                          data = X_train)

# Model 3: Original features + distance features
distance_features_only <- paste0("Centroid_", 1:final_k)
formula_distance <- as.formula(paste("SalePrice ~ LotArea + TotalBsmtSF + FirstFlrSF + SecondFlrSF + GrLivArea +", 
                                     paste(distance_features_only, collapse = " + ")))
model_with_distances <- lm(formula_distance, data = X_train)

# Calculate R-squared for each model
r2_original <- summary(model_original)$r.squared
r2_clusters <- summary(model_with_clusters)$r.squared
r2_distances <- summary(model_with_distances)$r.squared

cat("Model Performance (R-squared):\n")
cat("Original features only:", round(r2_original, 4), "\n")
cat("With cluster labels:", round(r2_clusters, 4), "\n")
cat("With distance features:", round(r2_distances, 4), "\n")

# Save model comparison plot
png("images/09_model_comparison.png", width = 800, height = 600, bg = "white")
model_names <- c("Original Features", "With Cluster Labels", "With Distance Features")
r2_values <- c(r2_original, r2_clusters, r2_distances)

barplot(r2_values, names.arg = model_names, main = "Model Performance Comparison (R-squared)",
        ylab = "R-squared", col = c("lightblue", "lightgreen", "lightcoral"),
        border = "black", ylim = c(0, max(r2_values) * 1.1))

# Add value labels on bars
text(x = seq_along(r2_values), y = r2_values + 0.01, 
     labels = round(r2_values, 4), pos = 3, cex = 0.8)
dev.off()
cat("Saved: images/09_model_comparison.png\n")

# =============================================================================
# SUMMARY AND RESULTS
# =============================================================================

cat("\n11. Summary and results...\n")

# Calculate silhouette score for final clustering
silhouette_result <- silhouette(kmeans_result$cluster, dist(X_scaled))
avg_silhouette <- mean(silhouette_result[, 3])

cat("\n=== K-MEANS CLUSTERING RESULTS ===\n")
cat("Dataset: Ames Housing Dataset\n")
cat("Features used:", paste(features_to_use, collapse = ", "), "\n")
cat("Number of clusters (k):", final_k, "\n")
cat("Number of observations:", nrow(X_scaled), "\n")
cat("Average silhouette score:", round(avg_silhouette, 4), "\n")
cat("Within-cluster sum of squares:", round(kmeans_result$tot.withinss, 2), "\n")
cat("Between-cluster sum of squares:", round(kmeans_result$betweenss, 2), "\n")
cat("Total sum of squares:", round(kmeans_result$totss, 2), "\n")

cat("\nCluster sizes:\n")
for (i in 1:final_k) {
  cat("Cluster", i, ":", sum(kmeans_result$cluster == i), "observations\n")
}

cat("\nFeature engineering impact:\n")
cat("R-squared improvement with cluster labels:", round(r2_clusters - r2_original, 4), "\n")
cat("R-squared improvement with distance features:", round(r2_distances - r2_original, 4), "\n")

cat("\n=== ANALYSIS COMPLETED ===\n")
cat("All visualizations saved to 'images/' directory.\n")
cat("Total images generated:", length(list.files("images/", pattern = "\\.png$")), "\n")
