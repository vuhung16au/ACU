# Multivariate Normal: simulation, density/probability, and conditional distributions
# Reproducible, self-contained example using MASS::mvrnorm and mvtnorm

set.seed(632)

# -------- Utilities --------
ensure_package <- function(pkg_name) {
  if (!requireNamespace(pkg_name, quietly = TRUE)) {
    install.packages(pkg_name, repos = "https://cloud.r-project.org")
  }
  suppressPackageStartupMessages(library(pkg_name, character.only = TRUE))
}

# Ensure required packages are available
ensure_package("MASS")     # for mvrnorm
ensure_package("mvtnorm")  # for dmvnorm, pmvnorm

# Output directory (assumes running from repo root). Adjust if needed.
output_dir <- "00.MultivariateNormal"
if (!dir.exists(output_dir)) dir.create(output_dir, recursive = TRUE)

# -------- Parameters --------
num_samples <- 5000
mean_vector <- c(0, 0)
# Positive-definite covariance matrix capturing correlation
covariance_matrix <- matrix(c(1.0, 0.7,
                              0.7, 2.0), nrow = 2, byrow = TRUE)

# -------- Simulation with MASS::mvrnorm --------
samples <- MASS::mvrnorm(n = num_samples, mu = mean_vector, Sigma = covariance_matrix)
colnames(samples) <- c("X1", "X2")

# Summary statistics
sample_mean <- colMeans(samples)
sample_covariance <- stats::cov(samples)

cat("=== Simulation Summary ===\n")
cat("Target mean:", paste(mean_vector, collapse = ", "), "\n")
cat("Sample mean:", paste(round(sample_mean, 3), collapse = ", "), "\n\n")
cat("Target covariance matrix:\n")
print(covariance_matrix)
cat("\nSample covariance matrix:\n")
print(round(sample_covariance, 3))
cat("\n")

# -------- Density over a grid with mvtnorm::dmvnorm --------
# Create grid
x1_seq <- seq(min(samples[, 1]) - 1, max(samples[, 1]) + 1, length.out = 100)
x2_seq <- seq(min(samples[, 2]) - 1, max(samples[, 2]) + 1, length.out = 100)
grid <- expand.grid(X1 = x1_seq, X2 = x2_seq)

# Compute joint density on grid
z_density <- mvtnorm::dmvnorm(grid, mean = mean_vector, sigma = covariance_matrix)
Z <- matrix(z_density, nrow = length(x1_seq), ncol = length(x2_seq))

# Contour plot of the theoretical density
png(file.path(output_dir, "bivariate_density_contour.png"), width = 900, height = 700)
par(mar = c(4.5, 4.5, 2, 1))
contour(x1_seq, x2_seq, Z,
        nlevels = 12,
        xlab = "X1",
        ylab = "X2",
        main = "Bivariate Normal: Joint Density Contours")
points(samples[sample(1:nrow(samples), 1000), ], pch = 16, cex = 0.4, col = rgb(0, 0, 0, 0.2))
dev.off()

# Perspective surface plot of the theoretical density
png(file.path(output_dir, "bivariate_density_surface.png"), width = 900, height = 700)
par(mar = c(2.5, 2.5, 2, 1))
persp(x1_seq, x2_seq, Z,
      theta = 35, phi = 25,
      col = "lightblue",
      shade = 0.5,
      ticktype = "detailed",
      xlab = "X1", ylab = "X2", zlab = "Density",
      main = "Bivariate Normal: Joint Density Surface")
dev.off()

# -------- Probability with mvtnorm::pmvnorm --------
# Example: P(a1 <= X1 <= b1, a2 <= X2 <= b2)
a1 <- -0.5; b1 <- 0.5
a2 <- -1.0; b2 <- 1.0

rect_prob <- mvtnorm::pmvnorm(lower = c(a1, a2),
                              upper = c(b1, b2),
                              mean  = mean_vector,
                              sigma = covariance_matrix)

cat("=== Probability over rectangle ===\n")
cat(sprintf("P(%0.1f<=X1<=%0.1f, %0.1f<=X2<=%0.1f) = %0.4f\n\n", a1, b1, a2, b2, rect_prob[1]))

# -------- Conditional Distribution: X1 | X2 = x2_obs --------
# For a partitioned MVN: X1|X2=x ~ Normal(mu1 + Sigma12 Sigma22^{-1} (x - mu2), Sigma11 - Sigma12 Sigma22^{-1} Sigma21)
mu1 <- mean_vector[1]
mu2 <- mean_vector[2]
Sigma11 <- covariance_matrix[1, 1, drop = FALSE]
Sigma22 <- covariance_matrix[2, 2, drop = FALSE]
Sigma12 <- covariance_matrix[1, 2, drop = FALSE]
Sigma21 <- covariance_matrix[2, 1, drop = FALSE]

x2_obs <- 0.5
Sigma22_inv <- 1 / Sigma22
cond_mean <- as.numeric(mu1 + Sigma12 %*% Sigma22_inv %*% (x2_obs - mu2))
cond_var  <- as.numeric(Sigma11 - Sigma12 %*% Sigma22_inv %*% Sigma21)

cat("=== Conditional Distribution X1 | X2 =", x2_obs, "===\n")
cat("Conditional mean:", round(cond_mean, 3), "\n")
cat("Conditional variance:", round(cond_var, 3), "\n\n")

# Visualize conditional slices for several X2 values
x2_vals <- c(-1, 0, 1)
x1_line <- seq(min(samples[, 1]) - 1, max(samples[, 1]) + 1, length.out = 300)

png(file.path(output_dir, "conditional_densities_X1_given_X2.png"), width = 900, height = 700)
par(mar = c(4.5, 4.5, 2, 1))
plot(NA, xlim = range(x1_line), ylim = c(0, 1.5),
     xlab = "x1",
     ylab = "Density",
     main = "Conditional Densities: X1 | X2 = x2")
cols <- c("#1b9e77", "#d95f02", "#7570b3")
for (i in seq_along(x2_vals)) {
  x2_val <- x2_vals[i]
  cm <- as.numeric(mu1 + Sigma12 %*% Sigma22_inv %*% (x2_val - mu2))
  cv <- as.numeric(Sigma11 - Sigma12 %*% Sigma22_inv %*% Sigma21)
  lines(x1_line, dnorm(x1_line, mean = cm, sd = sqrt(cv)), lwd = 2, col = cols[i])
}
legend("topright", legend = paste0("X2 = ", x2_vals), lwd = 2, col = cols, bty = "n")
dev.off()

# -------- Scatter plot with correlation --------
png(file.path(output_dir, "samples_scatter.png"), width = 900, height = 700)
par(mar = c(4.5, 4.5, 2, 1))
plot(samples, pch = 16, cex = 0.6, col = rgb(0, 0, 0, 0.35),
     xlab = "X1", ylab = "X2", main = "Samples from Bivariate Normal (MASS::mvrnorm)")
# Add sample mean
points(t(sample_mean), pch = 4, lwd = 3, col = "red", cex = 1.5)
legend("topleft", legend = c("Samples", "Sample mean"),
       pch = c(16, 4), col = c(rgb(0, 0, 0, 0.35), "red"), pt.cex = c(0.6, 1.5), bty = "n")
dev.off()

cat("Figures saved to:", normalizePath(output_dir), "\n")
cat("- bivariate_density_contour.png\n")
cat("- bivariate_density_surface.png\n")
cat("- conditional_densities_X1_given_X2.png\n")
cat("- samples_scatter.png\n")
