# Quadratic Discriminant Analysis (QDA) with Heart Disease Dataset
# This script demonstrates QDA for classification and simple EDA

# Helper: install missing packages quietly
install_if_missing <- function(pkgs) {
  for (p in pkgs) {
    if (!requireNamespace(p, quietly = TRUE)) {
      install.packages(p, repos = "https://cloud.r-project.org", quiet = TRUE)
    }
  }
}

install_if_missing(c("MASS", "ggplot2", "dplyr", "corrplot", "gridExtra", "pROC", "tidyr"))

library(MASS)      # For QDA
library(ggplot2)   # For plotting
library(dplyr)     # For data manipulation
library(corrplot)  # For correlation matrix visualization
library(gridExtra) # For arranging multiple plots
library(pROC)      # For ROC curve
library(tidyr)     # For pivot_longer

set.seed(123)

# Create images directory if it doesn't exist
if (!dir.exists("images")) {
  dir.create("images")
}

# -----------------------------
# 1) Sample data demonstration
# -----------------------------
# Generate two classes with different covariance matrices to show QDA benefit
generate_sample_data <- function(n_per_class = 200) {
  mu1 <- c(0, 0)
  mu2 <- c(2, 2)
  Sigma1 <- matrix(c(1.0, 0.6, 0.6, 1.0), 2, 2)
  Sigma2 <- matrix(c(1.8, -0.7, -0.7, 1.0), 2, 2)
  class1 <- MASS::mvrnorm(n = n_per_class, mu = mu1, Sigma = Sigma1)
  class2 <- MASS::mvrnorm(n = n_per_class, mu = mu2, Sigma = Sigma2)
  df <- rbind(
    data.frame(x1 = class1[,1], x2 = class1[,2], class = factor("Class A")),
    data.frame(x1 = class2[,1], x2 = class2[,2], class = factor("Class B"))
  )
  df
}

analyze_sample_qda <- function() {
  cat("=== Sample Data (QDA) ===\n")
  df <- generate_sample_data()

  # Fit QDA
  qda_fit <- qda(class ~ x1 + x2, data = df)

  # Grid for decision boundary
  x1_seq <- seq(min(df$x1) - 1, max(df$x1) + 1, length.out = 200)
  x2_seq <- seq(min(df$x2) - 1, max(df$x2) + 1, length.out = 200)
  grid <- expand.grid(x1 = x1_seq, x2 = x2_seq)
  grid$pred <- predict(qda_fit, grid)$class

  p <- ggplot() +
    geom_tile(data = grid, aes(x = x1, y = x2, fill = pred), alpha = 0.2) +
    geom_point(data = df, aes(x = x1, y = x2, color = class), size = 1.8, alpha = 0.8) +
    scale_fill_manual(values = c("Class A" = "#c6dbef", "Class B" = "#fdd0a2"), name = "QDA region") +
    scale_color_manual(values = c("Class A" = "#3182bd", "Class B" = "#e6550d")) +
    labs(title = "Sample Data: QDA Decision Regions",
         x = "x1", y = "x2") +
    theme_minimal() +
    theme(legend.position = "bottom",
          panel.background = element_rect(fill = "white"))

  ggsave("images/01_sample_data_qda.png", p, width = 8, height = 6, dpi = 300, bg = "white")
  cat("Saved: images/01_sample_data_qda.png\n")

  list(data = df, model = qda_fit)
}

# --------------------------------------
# 2) Heart disease dataset: QDA modeling
# --------------------------------------
load_heart_data <- function() {
  cat("\n=== Loading Heart Disease Dataset ===\n")
  path <- file.path("Heart-Disease-Dataset", "heart.csv")
  heart <- read.csv(path)
  cat("Rows:", nrow(heart), "Cols:", ncol(heart), "\n")
  heart
}

preprocess_heart <- function(df) {
  cat("\n=== Preprocessing ===\n")
  # Drop known faulty rows if present (per Kaggle notes)
  if ("ca" %in% names(df)) {
    bad_ca <- df$ca == 4
    if (any(bad_ca, na.rm = TRUE)) df <- df[!bad_ca, ]
  }
  if ("thal" %in% names(df)) {
    bad_thal <- df$thal == 0
    if (any(bad_thal, na.rm = TRUE)) df <- df[!bad_thal, ]
  }

  # Ensure target as factor (0/1 -> No/Yes)
  if ("target" %in% names(df)) {
    df$target <- factor(df$target, levels = c(0, 1), labels = c("NoDisease", "Disease"))
  }

  # Convert some integer-coded categoricals to factor for clarity (optional)
  cat_cols <- intersect(c("sex", "cp", "fbs", "restecg", "exang", "slope", "ca", "thal"), names(df))
  for (cc in cat_cols) {
    df[[cc]] <- factor(df[[cc]])
  }

  df
}

create_basic_eda_plots <- function(df) {
  cat("\n=== Creating EDA plots ===\n")
  # Target distribution
  p_target <- ggplot(df, aes(x = target, fill = target)) +
    geom_bar(alpha = 0.9) +
    scale_fill_manual(values = c("NoDisease" = "#6baed6", "Disease" = "#fd8d3c")) +
    labs(title = "Target Distribution", x = "Target", y = "Count") +
    theme_minimal() +
    theme(panel.background = element_rect(fill = "white"), legend.position = "none")
  ggsave("images/02_target_distribution.png", p_target, width = 6, height = 4, dpi = 300, bg = "white")

  # Numeric correlation matrix
  numeric_cols <- names(df)[sapply(df, is.numeric)]
  if (length(numeric_cols) > 1) {
    cor_mat <- suppressWarnings(cor(df[, numeric_cols], use = "pairwise.complete.obs"))
    png("images/03_correlation_matrix.png", width = 900, height = 700, res = 120, bg = "white")
    corrplot(cor_mat, method = "color", type = "upper", addCoef.col = "black",
             tl.col = "black", tl.srt = 45, mar = c(0,0,2,0),
             title = "Heart Dataset: Numeric Correlation Matrix")
    dev.off()
  }

  # Simple histograms for selected numeric features if exist
  sel <- intersect(c("age", "trestbps", "chol", "thalach", "oldpeak"), names(df))
  if (length(sel) > 0) {
    long_df <- tidyr::pivot_longer(df[, c(sel, "target")], cols = all_of(sel), names_to = "feature", values_to = "value")
    p_hist <- ggplot(long_df, aes(x = value, fill = target)) +
      geom_histogram(bins = 30, alpha = 0.6, position = "identity") +
      facet_wrap(~feature, scales = "free", ncol = 2) +
      scale_fill_manual(values = c("NoDisease" = "#6baed6", "Disease" = "#fd8d3c")) +
      labs(title = "Selected Numeric Features", x = "Value", y = "Frequency") +
      theme_minimal() +
      theme(panel.background = element_rect(fill = "white"))
    ggsave("images/04_numeric_histograms.png", p_hist, width = 10, height = 8, dpi = 300, bg = "white")
  }
}

train_qda <- function(df) {
  cat("\n=== Training QDA ===\n")
  # Use a simple formula with numeric predictors only to avoid factor handling in qda
  numeric_cols <- setdiff(names(df)[sapply(df, is.numeric)], c())
  # Exclude target if numeric (should not be after preprocess), ensure predictors exist
  numeric_cols <- setdiff(numeric_cols, c("target"))
  if (length(numeric_cols) < 2) stop("Not enough numeric predictors for QDA")

  # train-test split (70/30)
  idx <- sample(seq_len(nrow(df)), size = floor(0.7 * nrow(df)))
  train <- df[idx, ]
  test  <- df[-idx, ]

  formula_qda <- as.formula(paste("target ~", paste(numeric_cols, collapse = " + ")))

  # Fit QDA
  model <- qda(formula_qda, data = train)

  # Predict
  pred_train <- predict(model, train)
  pred_test  <- predict(model, test)

  # Metrics
  acc_train <- mean(pred_train$class == train$target)
  acc_test  <- mean(pred_test$class == test$target)

  cat(sprintf("Train Acc: %.2f%%\n", 100 * acc_train))
  cat(sprintf("Test  Acc: %.2f%%\n", 100 * acc_test))

  # Confusion matrix plot (test)
  cm <- table(Actual = test$target, Predicted = pred_test$class)
  cm_df <- as.data.frame(cm)
  p_cm <- ggplot(cm_df, aes(x = Predicted, y = Actual, fill = Freq)) +
    geom_tile() +
    scale_fill_gradient(low = "white", high = "red", name = "Count") +
    geom_text(aes(label = Freq), color = "black", size = 4) +
    labs(title = sprintf("QDA Confusion Matrix (Test) — Acc: %.1f%%", 100 * acc_test),
         x = "Predicted", y = "Actual") +
    theme_minimal() +
    theme(axis.text.x = element_text(angle = 45, hjust = 1),
          panel.background = element_rect(fill = "white"))
  ggsave("images/05_confusion_matrix.png", p_cm, width = 7, height = 5.5, dpi = 300, bg = "white")

  # ROC curve (binary)
  if (!is.null(pred_test$posterior)) {
    # Posterior probability for positive class "Disease"
    pos_prob <- pred_test$posterior[, "Disease"]
    y_true <- as.numeric(test$target == "Disease")
    roc_obj <- pROC::roc(response = y_true, predictor = pos_prob, quiet = TRUE)
    auc_val <- as.numeric(pROC::auc(roc_obj))

    roc_df <- data.frame(tpr = roc_obj$sensitivities, fpr = 1 - roc_obj$specificities)
    p_roc <- ggplot(roc_df, aes(x = fpr, y = tpr)) +
      geom_line(color = "#2c7fb8", size = 1) +
      geom_abline(slope = 1, intercept = 0, linetype = "dashed", color = "gray60") +
      coord_equal() +
      labs(title = sprintf("ROC Curve (AUC = %.3f)", auc_val), x = "False Positive Rate", y = "True Positive Rate") +
      theme_minimal() +
      theme(panel.background = element_rect(fill = "white"))
    ggsave("images/06_roc_curve.png", p_roc, width = 6, height = 5, dpi = 300, bg = "white")
  }

  list(model = model, train = train, test = test, pred_test = pred_test)
}

main <- function() {
  cat("=== Quadratic Discriminant Analysis (Heart Disease) ===\n\n")

  # 1) Sample QDA demo
  sample_res <- analyze_sample_qda()

  # 2) Heart dataset
  heart <- load_heart_data()
  heart <- preprocess_heart(heart)

  # 3) EDA plots
  create_basic_eda_plots(heart)

  # 4) Train QDA and evaluation
  qda_res <- train_qda(heart)

  cat("\nAll plots saved to images/ directory.\n")
}

if (!interactive()) {
  main()
}
