# =============================================================================
# K-MEANS CLUSTER FORMATION ANIMATION
# =============================================================================
# File: 40_k-Means_animation_Cluster-Formation-Animation.R
# Purpose: Animate the step-by-step formation of clusters as k-means algorithm iterates
# Author: Data Science Project
# Date: 2024

# =============================================================================
# SETUP AND PACKAGE LOADING
# =============================================================================

# Set random seed for reproducibility
set.seed(16)

# Load required packages
required_packages <- c("ggplot2", "dplyr", "cluster", "magick", "animation")

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

cat("=== K-MEANS CLUSTER FORMATION ANIMATION ===\n")
cat("Ames Housing Dataset - Step-by-Step Cluster Formation\n\n")

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
# CUSTOM K-MEANS IMPLEMENTATION FOR ANIMATION
# =============================================================================

cat("2. Implementing custom k-means for animation...\n")

# Custom k-means function that returns intermediate results
kmeans_animation <- function(data, k, max_iter = 20, tolerance = 1e-4) {
  n <- nrow(data)
  
  # Initialize centroids randomly
  set.seed(16)
  centroids <- data[sample(n, k), ]
  
  # Store animation frames
  frames <- list()
  
  for (iter in 1:max_iter) {
    # Calculate distances from each point to each centroid
    distances <- matrix(0, n, k)
    for (i in 1:n) {
      for (j in 1:k) {
        distances[i, j] <- sqrt(sum((data[i, ] - centroids[j, ])^2))
      }
    }
    
    # Assign points to nearest centroid
    clusters <- apply(distances, 1, which.min)
    
    # Calculate new centroids
    new_centroids <- centroids
    for (j in 1:k) {
      cluster_points <- data[clusters == j, , drop = FALSE]
      if (nrow(cluster_points) > 0) {
        new_centroids[j, ] <- colMeans(cluster_points)
      }
    }
    
    # Store frame data
    frame_data <- data.frame(
      x = X_sample$LotArea,
      y = X_sample$TotalBsmtSF,
      cluster = as.factor(clusters),
      iteration = iter,
      centroid_x = new_centroids[, 1] * sd(X_sample$LotArea) + mean(X_sample$LotArea),
      centroid_y = new_centroids[, 2] * sd(X_sample$TotalBsmtSF) + mean(X_sample$TotalBsmtSF)
    )
    
    frames[[iter]] <- frame_data
    
    # Check for convergence
    centroid_shift <- max(sqrt(rowSums((new_centroids - centroids)^2)))
    if (centroid_shift < tolerance) {
      cat("Converged at iteration", iter, "\n")
      break
    }
    
    centroids <- new_centroids
  }
  
  return(frames)
}

# =============================================================================
# CREATE ANIMATION FRAMES
# =============================================================================

cat("3. Creating animation frames...\n")

# Run k-means animation with k=5 for better visualization
k <- 5
animation_frames <- kmeans_animation(X_scaled, k = k, max_iter = 15)

cat("Generated", length(animation_frames), "animation frames\n")

# =============================================================================
# CREATE ANIMATION USING MAGICK
# =============================================================================

cat("4. Creating animation using magick...\n")

# Create frames for animation
frames <- list()

for (i in seq_along(animation_frames)) {
  frame_data <- animation_frames[[i]]
  
  # Create plot for this frame
  png_file <- tempfile(fileext = ".png")
  png(png_file, width = 800, height = 600, bg = "white")
  
  # Create the plot
  plot(frame_data$x, frame_data$y, 
       col = as.numeric(frame_data$cluster),
       pch = 16, cex = 0.8,
       main = paste("K-Means Cluster Formation - Iteration", i),
       xlab = "Lot Area (sq ft)", 
       ylab = "Total Basement SF (sq ft)",
       xlim = range(frame_data$x) * 1.1,
       ylim = range(frame_data$y) * 1.1)
  
  # Add centroids
  points(frame_data$centroid_x, frame_data$centroid_y, 
         col = "red", pch = 17, cex = 2, lwd = 2)
  
  # Add iteration info
  text(min(frame_data$x), max(frame_data$y) * 0.95, 
       paste("Iteration:", i), pos = 4, cex = 1.2, font = 2)
  
  # Add convergence info
  if (i > 1) {
    prev_centroids <- animation_frames[[i-1]][1:k, c("centroid_x", "centroid_y")]
    curr_centroids <- frame_data[1:k, c("centroid_x", "centroid_y")]
    centroid_shift <- mean(sqrt((prev_centroids$centroid_x - curr_centroids$centroid_x)^2 + 
                               (prev_centroids$centroid_y - curr_centroids$centroid_y)^2))
    text(min(frame_data$x), max(frame_data$y) * 0.90, 
         paste("Centroid Shift:", round(centroid_shift, 4)), pos = 4, cex = 1.0)
  }
  
  # Add legend
  legend("topright", 
         legend = paste("Cluster", 1:k),
         col = 1:k, pch = 16, cex = 0.8)
  
  dev.off()
  
  # Read the image
  frames[[i]] <- image_read(png_file)
  file.remove(png_file)
}

# =============================================================================
# CREATE AND SAVE ANIMATION
# =============================================================================

cat("5. Creating and saving animation...\n")

# Combine frames into animation
animation <- image_animate(image_join(frames), fps = 2)

# Save as GIF
gif_filename <- "images/40_k-Means_animation_Cluster-Formation-Animation.gif"
image_write(animation, gif_filename)
cat("Saved GIF:", gif_filename, "\n")

# Convert to MP4 using ffmpeg
mp4_filename <- "images/40_k-Means_animation_Cluster-Formation-Animation.mp4"
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
# CREATE FINAL STATIC PLOT
# =============================================================================

cat("6. Creating final static plot...\n")

# Get final frame data
final_frame <- animation_frames[[length(animation_frames)]]

# Create final plot
png("images/40_k-Means_animation_Cluster-Formation-Final.png", 
    width = 800, height = 600, bg = "white")

plot(final_frame$x, final_frame$y, 
     col = as.numeric(final_frame$cluster),
     pch = 16, cex = 0.8,
     main = "K-Means Clustering - Final Result",
     xlab = "Lot Area (sq ft)", 
     ylab = "Total Basement SF (sq ft)")

# Add final centroids
points(final_frame$centroid_x, final_frame$centroid_y, 
       col = "red", pch = 17, cex = 2, lwd = 2)

# Add cluster boundaries (Voronoi-like)
for (i in 1:k) {
  cluster_points <- final_frame[final_frame$cluster == i, ]
  if (nrow(cluster_points) > 0) {
    hull <- chull(cluster_points$x, cluster_points$y)
    polygon(cluster_points$x[hull], cluster_points$y[hull], 
            border = i, lty = 2, lwd = 1)
  }
}

# Add legend
legend("topright", 
       legend = paste("Cluster", 1:k),
       col = 1:k, pch = 16, cex = 0.8)

dev.off()
cat("Saved final plot: images/40_k-Means_animation_Cluster-Formation-Final.png\n")

# =============================================================================
# SUMMARY
# =============================================================================

cat("\n=== CLUSTER FORMATION ANIMATION COMPLETED ===\n")
cat("Animation frames:", length(animation_frames), "\n")
cat("Number of clusters:", k, "\n")
cat("Sample size:", nrow(X_scaled), "observations\n")
cat("Features used:", paste(clustering_features, collapse = ", "), "\n")
cat("Files created:\n")
cat("- GIF:", gif_filename, "\n")
cat("- MP4:", mp4_filename, "\n")
cat("- Final plot: images/40_k-Means_animation_Cluster-Formation-Final.png\n")
cat("\nAnimation shows:\n")
cat("1. Random initial centroids\n")
cat("2. Data points being assigned to nearest centroids\n")
cat("3. Centroid movement toward cluster centers\n")
cat("4. Convergence when centroids stop moving significantly\n")
cat("5. Final cluster boundaries\n")
