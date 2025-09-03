# k-NN with Triangulation Methodology using Pokemon Dataset
# - Cross-validation
# - Multiple metrics: Accuracy, AUC, Lift, Confusion Matrix
# - Simple, reproducible example

suppressPackageStartupMessages({
  library(readr)
  library(dplyr)
  library(ggplot2)
  library(class)
  library(caret)
  library(pROC)
})

set.seed(123)

# Paths
base_dir <- "05-05-Triangulation-Methodology-with-k-NN"
img_dir <- file.path(base_dir, "images")
data_path <- file.path(base_dir, "Pokemon-Dataset", "pokemon.csv")

if (!dir.exists(img_dir)) dir.create(img_dir, recursive = TRUE)

# Load dataset (fallback already created by setup)
pokemon <- suppressMessages(readr::read_csv(data_path, show_col_types = FALSE))

# Minimal preprocessing: create a binary target: Fast (speed >= median)
pokemon <- pokemon %>%
  mutate(
    Fast = as.factor(ifelse(speed >= median(speed, na.rm = TRUE), "Yes", "No"))
  ) %>%
  select(attack, defense, speed, Fast) %>%
  na.omit()

# Train/test split
set.seed(123)
train_index <- createDataPartition(pokemon$Fast, p = 0.8, list = FALSE)
train_df <- pokemon[train_index, ]
test_df  <- pokemon[-train_index, ]

# If test set is empty or has <2 classes, create a simple stratified split with at least 1 per class when possible
if (nrow(test_df) == 0 || length(unique(test_df$Fast)) < 2) {
  set.seed(123)
  test_idx <- c()
  for (lvl in levels(pokemon$Fast)) {
    idx_lvl <- which(pokemon$Fast == lvl)
    if (length(idx_lvl) > 0) {
      test_idx <- c(test_idx, sample(idx_lvl, size = 1))
    }
  }
  test_idx <- unique(test_idx)
  # Fallback: ensure at least one test row
  if (length(test_idx) == 0) test_idx <- sample(seq_len(nrow(pokemon)), size = 1)
  test_df <- pokemon[test_idx, ]
  train_df <- pokemon[-test_idx, ]
}

# Standardize features using training stats
scale_train <- scale(train_df %>% select(-Fast))
center_values <- attr(scale_train, "scaled:center")
scale_values  <- attr(scale_train, "scaled:scale")

to_scale <- function(df) {
  as.data.frame(scale(df %>% select(-Fast), center = center_values, scale = scale_values))
}

x_train <- to_scale(train_df)
x_test  <- as.data.frame(scale(test_df %>% select(-Fast), center = center_values, scale = scale_values))
y_train <- train_df$Fast
y_test  <- test_df$Fast

# Cross-validation to choose k
set.seed(123)
# Cap k by training size - 1
max_k_global <- max(1, min(25, nrow(train_df) - 1))
k_grid <- seq(1, max_k_global, by = 2)
cv_results <- data.frame(k = integer(), Accuracy = numeric(), stringsAsFactors = FALSE)

# Use up to 5 folds but not more folds than the number of rows per class
num_folds <- min(5, length(y_train))
folds <- createFolds(y_train, k = max(2, num_folds), returnTrain = TRUE)

for (k in k_grid) {
  acc_fold <- c()
  for (tr_idx in folds) {
    val_idx <- setdiff(seq_len(nrow(x_train)), tr_idx)
    x_tr <- x_train[tr_idx, , drop = FALSE]
    y_tr <- y_train[tr_idx]
    x_val <- x_train[val_idx, , drop = FALSE]
    y_val <- y_train[val_idx]

    # Cap k by available training patterns in the fold
    k_use <- min(k, max(1, nrow(x_tr) - 1))

    preds <- class::knn(train = x_tr, test = x_val, cl = y_tr, k = k_use, prob = TRUE)
    acc_fold <- c(acc_fold, mean(preds == y_val))
  }
  cv_results <- rbind(cv_results, data.frame(k = k, Accuracy = mean(acc_fold)))
}

best_k <- cv_results$k[which.max(cv_results$Accuracy)]

# Plot CV accuracy vs k
p_cv <- ggplot(cv_results, aes(x = k, y = Accuracy)) +
  geom_line(color = "steelblue", linewidth = 1) +
  geom_point(color = "steelblue", size = 2) +
  geom_vline(xintercept = best_k, color = "red", linetype = "dashed", linewidth = 1) +
  labs(title = paste0("k-NN CV Accuracy vs k (best k = ", best_k, ")"), x = "k", y = "CV Accuracy") +
  theme_minimal()

ggsave(file.path(img_dir, "01_cv_accuracy_vs_k.png"), p_cv, width = 7, height = 5, dpi = 300, bg = "white")

# Fit final model and predict on test
set.seed(123)
# Cap best_k by available training size
best_k_use <- min(best_k, max(1, nrow(x_train) - 1))

# Guard for empty test set
if (nrow(x_test) == 0) {
  preds_class <- factor(character(0), levels = levels(y_train))
  prob_yes <- numeric(0)
} else {
  preds_class <- class::knn(train = x_train, test = x_test, cl = y_train, k = best_k_use, prob = TRUE)
  attr_prob <- attr(preds_class, "prob")
  prob_yes <- ifelse(preds_class == "Yes", attr_prob, 1 - attr_prob)
}

# Metrics
# 1) Accuracy
accuracy <- if (length(y_test) > 0) mean(preds_class == y_test) else NA_real_

# 2) AUC and ROC
auc_val <- NA_real_
roc_obj <- NULL
if (length(unique(y_test)) == 2) {
  roc_obj <- try(pROC::roc(response = y_test, predictor = as.numeric(prob_yes), levels = c("No", "Yes"), quiet = TRUE), silent = TRUE)
  if (!inherits(roc_obj, "try-error") && !is.null(roc_obj)) {
    auc_val <- as.numeric(pROC::auc(roc_obj))
    # ROC curve plot
    p_roc <- ggplot(data.frame(tpr = roc_obj$sensitivities, fpr = 1 - roc_obj$specificities), aes(x = fpr, y = tpr)) +
      geom_line(color = "darkorange", linewidth = 1) +
      geom_abline(slope = 1, intercept = 0, linetype = "dashed") +
      coord_equal() +
      labs(title = sprintf("ROC Curve (AUC = %.3f)", auc_val), x = "False Positive Rate", y = "True Positive Rate") +
      theme_minimal()
    ggsave(file.path(img_dir, "02_roc_curve.png"), p_roc, width = 6, height = 6, dpi = 300, bg = "white")
  }
}

# 3) Lift chart
lift_created <- FALSE
lift_path <- file.path(img_dir, "03_lift_chart.png")
baseline <- mean(y_test == "Yes", na.rm = TRUE)
if (!is.na(baseline) && baseline > 0) {
  lift_df <- data.frame(prob_yes = as.numeric(prob_yes), actual = y_test) %>%
    mutate(decile = ntile(prob_yes, 10)) %>%
    group_by(decile) %>%
    summarise(
      avg_prob = mean(prob_yes),
      response_rate = mean(actual == "Yes"),
      n = n(), .groups = "drop"
    ) %>%
    arrange(desc(decile))

  p_lift <- ggplot(lift_df, aes(x = factor(decile, levels = rev(sort(unique(decile)))), y = response_rate / baseline)) +
    geom_col(fill = "steelblue", alpha = 0.8) +
    geom_hline(yintercept = 1, linetype = "dashed", color = "red") +
    labs(title = "Lift Chart (Deciles)", x = "Decile (High score to Low)", y = "Lift over baseline") +
    theme_minimal()

  ggsave(lift_path, p_lift, width = 7, height = 5, dpi = 300, bg = "white")
  lift_created <- TRUE
}

# 4) Confusion Matrix
if (length(y_test) > 0) {
  cm <- caret::confusionMatrix(preds_class, y_test, positive = "Yes")
  # Save confusion matrix as text
  sink(file.path(base_dir, "confusion_matrix.txt"))
  cat("Confusion Matrix (Positive = 'Yes')\n\n")
  print(cm)
  sink()

  # Save a ggplot confusion heatmap
  cm_table <- as.data.frame(cm$table)
  p_cm <- ggplot(cm_table, aes(x = Reference, y = Prediction, fill = Freq)) +
    geom_tile() +
    geom_text(aes(label = Freq), color = "white", fontface = "bold") +
    scale_fill_gradient(low = "#90caf9", high = "#0d47a1") +
    labs(title = "Confusion Matrix", x = "Actual", y = "Predicted") +
    theme_minimal()

  ggsave(file.path(img_dir, "04_confusion_matrix.png"), p_cm, width = 6, height = 5, dpi = 300, bg = "white")
}

# Write a brief metrics summary
summary_path <- file.path(base_dir, "metrics_summary.txt")
cat(
  sprintf(
    "Accuracy: %.4f\nAUC: %s\nBest k (CV): %d\nBaseline positive rate: %.4f\n",
    accuracy, ifelse(is.na(auc_val), "NA", sprintf("%.4f", auc_val)), best_k_use, baseline
  ),
  file = summary_path
)

cat("\n=== k-NN Triangulation Summary ===\n")
cat(sprintf("Best k from CV: %d (used %d)\n", best_k, best_k_use))
cat(sprintf("Test Accuracy: %.3f\n", accuracy))
cat(sprintf("Test AUC: %s\n", ifelse(is.na(auc_val), "NA (single-class test set)", sprintf("%.3f", auc_val))))
cat("Outputs saved to images/ plus confusion_matrix.txt and metrics_summary.txt\n")
