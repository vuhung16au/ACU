# =============================================================================
# K-MEANS OPTIMAL K DISCOVERY ANIMATION
# =============================================================================
# File: 40_k-Means_animation_Optimal-K-Discovery-Animation.R
# Purpose: Animate the process of finding the optimal number of clusters
# Author: Data Science Project
# Date: 2024

# =============================================================================
# SETUP AND PACKAGE LOADING
# =============================================================================

# Set random seed for reproducibility
set.seed(16)

# Load required packages
required_packages <- c("ggplot2", "dplyr", "cluster", "magick", "factoextra")

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

cat("=== K-MEANS OPTIMAL K DISCOVERY ANIMATION ===\n")
cat("Ames Housing Dataset - Finding Optimal Number of Clusters\n\n")

# =============================================================================
# DATA LOADING AND PREPARATION
# =============================================================================

cat("1. Loading and preparing data...\n")

# Load the dataset
data <- read.csv("Housing-Dataset/housing-ames.csv", stringsAsFactors = FALSE)

# Select features for clustering (same as main analysis)
clustering_features <- c("LotArea", "TotalBsmtSF", "FirstFlrSF", "SecondFlrSF", "GrLivArea")
X <- data[, clustering_features, drop = FALSE]
y <- data$SalePrice

# Remove rows with missing values
complete_cases <- complete.cases(X)
X_clean <- X[complete_cases, ]
y_clean <- y[complete_cases]

# Use a subset of data for animation (to make it manageable)
set.seed(16)
sample_indices <- sample(nrow(X_clean), min(500, nrow(X_clean)))
X_sample <- X_clean[sample_indices, ]
y_sample <- y_clean[sample_indices]

# Standardize features
X_scaled <- scale(X_sample)
X_scaled <- as.data.frame(X_scaled)

cat("Sample size for animation:", nrow(X_scaled), "observations\n")
cat("Features:", ncol(X_scaled), "\n")

# =============================================================================
# CALCULATE METRICS FOR DIFFERENT K VALUES
# =============================================================================

cat("2. Calculating metrics for different k values...\n")

# Range of k values to test
k_values <- 2:10
max_k <- max(k_values)

# Store results
wss_values <- numeric(max_k)
silhouette_values <- numeric(max_k)
kmeans_results <- list()

# Calculate metrics for each k
for (k in k_values) {
  cat("Processing k =", k, "...\n")
  
  # Perform k-means clustering
  kmeans_result <- kmeans(X_scaled, centers = k, nstart = 25, iter.max = 100)
  kmeans_results[[k]] <- kmeans_result
  
  # Calculate within-cluster sum of squares
  wss_values[k] <- kmeans_result$tot.withinss
  
  # Calculate silhouette score
  if (k > 1) {
    silhouette_result <- silhouette(kmeans_result$cluster, dist(X_scaled))
    silhouette_values[k] <- mean(silhouette_result[, 3])
  }
}

cat("Completed calculations for k =", paste(k_values, collapse = ", "), "\n")

# =============================================================================
# CREATE ANIMATION FRAMES
# =============================================================================

cat("3. Creating animation frames...\n")

# Create frames for animation
frames <- list()

for (i in seq_along(k_values)) {
  k <- k_values[i]
  kmeans_result <- kmeans_results[[k]]
  
  # Create plot for this frame
  png_file <- tempfile(fileext = ".png")
  png(png_file, width = 1200, height = 800, bg = "white")
  
  # Set up multi-panel layout
  par(mfrow = c(2, 2), mar = c(4, 4, 3, 2))
  
  # Panel 1: Current clustering result
  plot(X_sample$LotArea, X_sample$TotalBsmtSF, 
       col = kmeans_result$cluster,
       pch = 16, cex = 0.6,
       main = paste("K-Means Clustering (k =", k, ")"),
       xlab = "Lot Area (sq ft)", 
       ylab = "Total Basement SF (sq ft)")
  
  # Add centroids
  centroids <- kmeans_result$centers
  centroid_x <- centroids[, 1] * sd(X_sample$LotArea) + mean(X_sample$LotArea)
  centroid_y <- centroids[, 2] * sd(X_sample$TotalBsmtSF) + mean(X_sample$TotalBsmtSF)
  points(centroid_x, centroid_y, col = "red", pch = 17, cex = 1.5, lwd = 2)
  
  # Add cluster boundaries
  for (j in 1:k) {
    cluster_points <- X_sample[kmeans_result$cluster == j, ]
    if (nrow(cluster_points) > 0) {
      hull <- chull(cluster_points$LotArea, cluster_points$TotalBsmtSF)
      polygon(cluster_points$LotArea[hull], cluster_points$TotalBsmtSF[hull], 
              border = j, lty = 2, lwd = 1)
    }
  }
  
  # Panel 2: Elbow method plot
  plot(1:max_k, wss_values, type = "b", pch = 19, frame = FALSE,
       xlab = "Number of Clusters (k)", ylab = "Within-cluster Sum of Squares",
       main = "Elbow Method",
       col = "blue", lwd = 2)
  
  # Highlight current k
  points(k, wss_values[k], col = "red", pch = 19, cex = 2)
  text(k, wss_values[k], paste("k =", k), pos = 3, cex = 1.2, font = 2)
  
  # Add grid
  grid()
  
  # Panel 3: Silhouette method plot
  plot(2:max_k, silhouette_values[2:max_k], type = "b", pch = 19, frame = FALSE,
       xlab = "Number of Clusters (k)", ylab = "Average Silhouette Score",
       main = "Silhouette Method",
       col = "red", lwd = 2)
  
  # Highlight current k
  if (k > 1) {
    points(k, silhouette_values[k], col = "blue", pch = 19, cex = 2)
    text(k, silhouette_values[k], paste("k =", k), pos = 3, cex = 1.2, font = 2)
  }
  
  # Add grid
  grid()
  
  # Panel 4: Metrics summary
  plot(1, 1, type = "n", xlim = c(0, 1), ylim = c(0, 1), 
       axes = FALSE, xlab = "", ylab = "", main = "Current Metrics")
  
  # Display current metrics
  text(0.1, 0.9, paste("k =", k), cex = 2, font = 2)
  text(0.1, 0.8, paste("WSS =", round(wss_values[k], 2)), cex = 1.2)
  if (k > 1) {
    text(0.1, 0.7, paste("Silhouette =", round(silhouette_values[k], 3)), cex = 1.2)
  }
  text(0.1, 0.6, paste("Clusters =", k), cex = 1.2)
  text(0.1, 0.5, paste("Observations =", nrow(X_scaled)), cex = 1.2)
  
  # Add interpretation
  if (k == 2) {
    text(0.1, 0.3, "Too few clusters", cex = 1.0, col = "red")
  } else if (k == 10) {
    text(0.1, 0.3, "Optimal k (chosen)", cex = 1.0, col = "green", font = 2)
  } else if (k <= 5) {
    text(0.1, 0.3, "Under-clustering", cex = 1.0, col = "orange")
  } else {
    text(0.1, 0.3, "Good clustering", cex = 1.0, col = "blue")
  }
  
  dev.off()
  
  # Read the image
  frames[[i]] <- image_read(png_file)
  file.remove(png_file)
}

# =============================================================================
# CREATE AND SAVE ANIMATION
# =============================================================================

cat("4. Creating and saving animation...\n")

# Combine frames into animation
animation <- image_animate(image_join(frames), fps = 2)

# Save as GIF
gif_filename <- "images/40_k-Means_animation_Optimal-K-Discovery-Animation.gif"
image_write(animation, gif_filename)
cat("Saved GIF:", gif_filename, "\n")

# Convert to MP4 using ffmpeg
mp4_filename <- "images/40_k-Means_animation_Optimal-K-Discovery-Animation.mp4"
system(paste("ffmpeg -i", gif_filename, 
             "-movflags faststart -pix_fmt yuv420p",
             "-vf 'scale=trunc(iw/2)*2:trunc(ih/2)*2'",
             mp4_filename))

if (file.exists(mp4_filename)) {
  cat("Saved MP4:", mp4_filename, "\n")
} else {
  cat("MP4 conversion failed, GIF is available\n")
}

# =============================================================================
# CREATE FINAL COMPARISON PLOTS
# =============================================================================

cat("5. Creating final comparison plots...\n")

# Create final comparison plot
png("images/40_k-Means_animation_Optimal-K-Discovery-Final.png", 
    width = 1200, height = 600, bg = "white")

par(mfrow = c(1, 2), mar = c(4, 4, 3, 2))

# Elbow method final plot
plot(1:max_k, wss_values, type = "b", pch = 19, frame = FALSE,
     xlab = "Number of Clusters (k)", ylab = "Within-cluster Sum of Squares",
     main = "Elbow Method - Final Analysis",
     col = "blue", lwd = 2)

# Highlight optimal k
points(10, wss_values[10], col = "red", pch = 19, cex = 2)
text(10, wss_values[10], "k = 10\n(Chosen)", pos = 3, cex = 1.2, font = 2)

# Add elbow point
elbow_k <- which.min(diff(wss_values)) + 1
points(elbow_k, wss_values[elbow_k], col = "green", pch = 17, cex = 2)
text(elbow_k, wss_values[elbow_k], paste("Elbow\nk =", elbow_k), pos = 3, cex = 1.0)

grid()

# Silhouette method final plot
plot(2:max_k, silhouette_values[2:max_k], type = "b", pch = 19, frame = FALSE,
     xlab = "Number of Clusters (k)", ylab = "Average Silhouette Score",
     main = "Silhouette Method - Final Analysis",
     col = "red", lwd = 2)

# Highlight optimal k
points(10, silhouette_values[10], col = "blue", pch = 19, cex = 2)
text(10, silhouette_values[10], "k = 10\n(Chosen)", pos = 3, cex = 1.2, font = 2)

# Add best silhouette k
best_silhouette_k <- which.max(silhouette_values[2:max_k]) + 1
points(best_silhouette_k, silhouette_values[best_silhouette_k], col = "green", pch = 17, cex = 2)
text(best_silhouette_k, silhouette_values[best_silhouette_k], 
     paste("Best Silhouette\nk =", best_silhouette_k), pos = 3, cex = 1.0)

grid()

dev.off()
cat("Saved final comparison: images/40_k-Means_animation_Optimal-K-Discovery-Final.png\n")

# =============================================================================
# CREATE FINAL CLUSTERING RESULT
# =============================================================================

cat("6. Creating final clustering result...\n")

# Use k=10 for final result
final_k <- 10
final_kmeans <- kmeans_results[[final_k]]

# Create final clustering plot
png("images/40_k-Means_animation_Optimal-K-Discovery-Final-Clusters.png", 
    width = 800, height = 600, bg = "white")

plot(X_sample$LotArea, X_sample$TotalBsmtSF, 
     col = final_kmeans$cluster,
     pch = 16, cex = 0.6,
     main = paste("Final K-Means Clustering (k =", final_k, ")"),
     xlab = "Lot Area (sq ft)", 
     ylab = "Total Basement SF (sq ft)")

# Add centroids
centroids <- final_kmeans$centers
centroid_x <- centroids[, 1] * sd(X_sample$LotArea) + mean(X_sample$LotArea)
centroid_y <- centroids[, 2] * sd(X_sample$TotalBsmtSF) + mean(X_sample$TotalBsmtSF)
points(centroid_x, centroid_y, col = "red", pch = 17, cex = 1.5, lwd = 2)

# Add cluster boundaries
for (j in 1:final_k) {
  cluster_points <- X_sample[final_kmeans$cluster == j, ]
  if (nrow(cluster_points) > 0) {
    hull <- chull(cluster_points$LotArea, cluster_points$TotalBsmtSF)
    polygon(cluster_points$LotArea[hull], cluster_points$TotalBsmtSF[hull], 
            border = j, lty = 2, lwd = 1)
  }
}

# Add legend
legend("topright", 
       legend = paste("Cluster", 1:final_k),
       col = 1:final_k, pch = 16, cex = 0.6, ncol = 2)

dev.off()
cat("Saved final clusters: images/40_k-Means_animation_Optimal-K-Discovery-Final-Clusters.png\n")

# =============================================================================
# SUMMARY
# =============================================================================

cat("\n=== OPTIMAL K DISCOVERY ANIMATION COMPLETED ===\n")
cat("K values tested:", paste(k_values, collapse = ", "), "\n")
cat("Animation frames:", length(frames), "\n")
cat("Final k chosen:", final_k, "\n")
cat("Sample size:", nrow(X_scaled), "observations\n")
cat("Features used:", paste(clustering_features, collapse = ", "), "\n")

cat("\nOptimal k analysis:\n")
cat("- Elbow method suggests k =", which.min(diff(wss_values)) + 1, "\n")
cat("- Silhouette method suggests k =", which.max(silhouette_values[2:max_k]) + 1, "\n")
cat("- Final choice: k =", final_k, "(as specified in reference notebook)\n")

cat("\nFiles created:\n")
cat("- GIF:", gif_filename, "\n")
cat("- MP4:", mp4_filename, "\n")
cat("- Final comparison: images/40_k-Means_animation_Optimal-K-Discovery-Final.png\n")
cat("- Final clusters: images/40_k-Means_animation_Optimal-K-Discovery-Final-Clusters.png\n")

cat("\nAnimation shows:\n")
cat("1. Clustering results for k = 2 to 10\n")
cat("2. How silhouette scores change with k\n")
cat("3. Elbow point in within-cluster sum of squares\n")
cat("4. Why k = 10 was chosen as optimal\n")
cat("5. Final cluster configuration\n")
