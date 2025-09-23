
- [Overview](#overview)
- [Packages and Setup](#packages-and-setup)
- [Data Loading](#data-loading)
- [Visual EDA](#visual-eda)
- [Correlation Heatmap](#correlation-heatmap)
- [Modeling with Keras](#modeling-with-keras)
- [Evaluation](#evaluation)

## Overview

We implement a simple feed-forward neural network using Keras
(TensorFlow backend) to predict `default.payment.next.month` on the UCI
Credit Card dataset, mirroring the structure of the provided Python
notebook.

## Packages and Setup

``` r
required_packages <- c(
  "tidyverse", "readr", "dplyr", "ggplot2", "scales", "cowplot",
  "corrplot", "reshape2", "caret", "pROC", "keras3", "tensorflow", "rmarkdown"
)
missing <- required_packages[!required_packages %in% installed.packages()[, "Package"]]
if (length(missing) > 0) install.packages(missing, repos = "https://cloud.r-project.org", quiet = TRUE)

lapply(required_packages, require, character.only = TRUE)

# TensorFlow backend (already installed in the .R script)
library(tensorflow)
tf_ok <- TRUE
```

## Data Loading

``` r
data_path <- file.path(".", "CreditCard-Dataset", "CreditCard.csv")
stopifnot(file.exists(data_path))
df <- readr::read_csv(data_path, show_col_types = FALSE)
names(df) <- make.names(names(df))
target_col <- "default.payment.next.month"
stopifnot(target_col %in% names(df))
cat(sprintf("Rows: %s, Cols: %s\n", nrow(df), ncol(df)))
```

## Visual EDA

``` r
save_plot <- function(p, filename, width = 7, height = 5) {
  ggplot2::ggsave(filename = file.path("images", filename), plot = p + theme_bw(),
                  width = width, height = height, dpi = 150, bg = "white")
}

p_limit <- ggplot(df, aes(x = LIMIT_BAL)) +
  geom_histogram(bins = 30, fill = "#cc79a7", alpha = 0.6, color = "white") +
  labs(title = "Fig.1: Credit Limit", x = "Credit Limit (NT$)", y = "Count")
save_plot(p_limit, "fig01_limit_bal.png")

p_limit_overlay <- ggplot(df, aes(x = LIMIT_BAL, fill = factor(.data[[target_col]]))) +
  geom_histogram(position = "identity", bins = 30, alpha = 0.5, color = "white") +
  scale_fill_manual(values = c("0" = "#cc79a7", "1" = "#0072b2"), name = "Default") +
  labs(title = "Fig.1b: Credit Limit (Default Overlay)", x = "Credit Limit", y = "Count")
save_plot(p_limit_overlay, "fig01b_limit_bal_overlay.png")

p_sex <- df %>%
  mutate(SEX_F = factor(SEX, levels = c(1, 2), labels = c("Male", "Female"))) %>%
  count(SEX_F, !!sym(target_col)) %>%
  ggplot(aes(x = SEX_F, y = n, fill = factor(.data[[target_col]]))) +
  geom_col(alpha = 0.7) +
  scale_fill_manual(values = c("0" = "#cc79a7", "1" = "#0072b2"), name = "Default") +
  labs(title = "Fig.2: Gender", x = NULL, y = "Count")
save_plot(p_sex, "fig02_gender.png")

p_edu <- df %>%
  mutate(EDU_F = dplyr::case_when(
    EDUCATION == 1 ~ "Grad School",
    EDUCATION == 2 ~ "University",
    EDUCATION == 3 ~ "High School",
    TRUE ~ "Other"
  )) %>%
  count(EDU_F, !!sym(target_col)) %>%
  ggplot(aes(x = EDU_F, y = n, fill = factor(.data[[target_col]]))) +
  geom_col(alpha = 0.7) +
  scale_fill_manual(values = c("0" = "#cc79a7", "1" = "#0072b2"), name = "Default") +
  labs(title = "Fig.3: Education", x = NULL, y = "Count")
save_plot(p_edu, "fig03_education.png")

p_mar <- df %>%
  mutate(MAR_F = factor(MARRIAGE, levels = c(1, 2, 3), labels = c("Married", "Single", "Other"))) %>%
  count(MAR_F, !!sym(target_col)) %>%
  ggplot(aes(x = MAR_F, y = n, fill = factor(.data[[target_col]]))) +
  geom_col(alpha = 0.7) +
  scale_fill_manual(values = c("0" = "#cc79a7", "1" = "#0072b2"), name = "Default") +
  labs(title = "Fig.4: Marriage Status", x = NULL, y = "Count")
save_plot(p_mar, "fig04_marriage.png")

p_age <- ggplot(df, aes(x = AGE, fill = factor(.data[[target_col]]))) +
  geom_histogram(position = "identity", bins = 25, alpha = 0.5, color = "white") +
  scale_fill_manual(values = c("0" = "#cc79a7", "1" = "#0072b2"), name = "Default") +
  labs(title = "Fig.5: Age", x = "Age", y = "Count")
save_plot(p_age, "fig05_age.png")
```

### Payment status and amounts

``` r
pay_cols <- c("PAY_0", "PAY_2", "PAY_3", "PAY_4", "PAY_5", "PAY_6")
bill_cols <- paste0("BILL_AMT", 1:6)
pay_amt_cols <- paste0("PAY_AMT", 1:6)

plot_delay <- function(cn) {
  vals <- -2:9
  df %>%
    mutate(val = .data[[cn]]) %>%
    count(val, !!sym(target_col), .drop = FALSE) %>%
    mutate(val = factor(val, levels = vals)) %>%
    ggplot(aes(x = val, y = n, fill = factor(.data[[target_col]]))) +
    geom_col(alpha = 0.7) +
    scale_fill_manual(values = c("0" = "#56b4e9", "1" = "#000000"), name = "Default") +
    labs(title = paste0("Payment status: ", cn), x = "Delay (months)", y = "Count")
}

library(cowplot)
save_plot(plot_grid(plotlist = lapply(pay_cols, plot_delay), ncol = 2),
          "fig06_payment_status.png", width = 12, height = 10)

plot_bill <- function(cn) {
  ggplot(df, aes(x = .data[[cn]], fill = factor(.data[[target_col]]))) +
    geom_histogram(bins = 25, alpha = 0.7, position = "identity", color = "white") +
    scale_fill_manual(values = c("0" = "#6f42c1", "1" = "#b3b3b3"), name = "Default") +
    scale_y_continuous(trans = "log10", labels = scales::comma) +
    labs(title = paste0("Amount of bill statement: ", cn), x = "Amount (NT$)", y = "Count (log)")
}
save_plot(plot_grid(plotlist = lapply(bill_cols, plot_bill), ncol = 2),
          "fig07_bill_amount.png", width = 10, height = 10)

plot_pay_amt <- function(cn) {
  ggplot(df, aes(x = .data[[cn]], fill = factor(.data[[target_col]]))) +
    geom_histogram(bins = 25, alpha = 0.85, position = "identity", color = "white") +
    scale_fill_manual(values = c("0" = "#56b4e9", "1" = "#000000"), name = "Default") +
    scale_y_continuous(trans = "log10", labels = scales::comma) +
    labs(title = paste0("Amount of previous payment: ", cn), x = "Amount (NT$)", y = "Count (log)")
}
save_plot(plot_grid(plotlist = lapply(pay_amt_cols, plot_pay_amt), ncol = 2),
          "fig08_prev_payment.png", width = 10, height = 10)
```

## Correlation Heatmap

``` r
num_df <- df %>% dplyr::select(-ID)
cor_mat <- suppressWarnings(cor(num_df, use = "pairwise.complete.obs"))
png(file.path("images", "fig09_correlation_heatmap.png"), width = 1800, height = 1800, res = 200, bg = "white")
corrplot::corrplot(cor_mat, method = "color", type = "full", tl.cex = 0.6, tl.col = "black", order = "original")
dev.off()
```

## Modeling with Keras

``` r
feature_cols <- setdiff(names(df), c("ID", target_col))
x <- as.matrix(df[, feature_cols])
scale_center <- apply(x, 2, mean, na.rm = TRUE)
scale_scale <- apply(x, 2, sd, na.rm = TRUE)
scale_scale[scale_scale == 0] <- 1
x_scaled <- scale(x, center = scale_center, scale = scale_scale)

y <- as.integer(df[[target_col]])
y_categ <- keras3::to_categorical(y)

set.seed(123)
train_index <- caret::createDataPartition(y, p = 0.7, list = FALSE)
x_train <- x_scaled[train_index, , drop = FALSE]
x_val   <- x_scaled[-train_index, , drop = FALSE]
y_train <- y_categ[train_index, , drop = FALSE]
y_val   <- y_categ[-train_index, , drop = FALSE]

ratio <- mean(y == 1)
class_weight <- list("0" = ratio, "1" = 1 - ratio)
cat(sprintf("Default ratio: %.4f\n", ratio))

keras3::set_random_seed(123)
n_cols <- ncol(x_train)
if (tf_ok) {
  model <- keras_model_sequential() %>%
    layer_dense(units = 25, activation = "relu", input_shape = n_cols) %>%
    layer_dense(units = 25, activation = "relu") %>%
    layer_dense(units = 2, activation = "softmax")

  model %>% compile(
    optimizer = "adam",
    loss = "categorical_crossentropy",
    metrics = c("accuracy")
  )

  early_stopping <- callback_early_stopping(patience = 2, restore_best_weights = TRUE)

  history <- model %>% fit(
    x = x_train, y = y_train,
    epochs = 20, batch_size = 256,
    validation_data = list(x_val, y_val),
    callbacks = list(early_stopping),
    class_weight = class_weight,
    verbose = 2
  )
}

if (tf_ok) {
  dfh <- data.frame(
    epoch = seq_along(history$metrics$loss),
    loss = history$metrics$loss,
    val_loss = history$metrics$val_loss,
    acc = history$metrics$accuracy,
    val_acc = history$metrics$val_accuracy
  )
  p1 <- ggplot(dfh, aes(epoch)) +
    geom_line(aes(y = loss, color = "Train Loss"), linewidth = 0.8) +
    geom_line(aes(y = val_loss, color = "Val Loss"), linewidth = 0.8) +
    scale_color_manual(values = c("Train Loss" = "#e69f00", "Val Loss" = "#0072b2")) +
    labs(title = "Training vs Validation Loss", y = "Loss", x = "Epoch", color = NULL)
  p2 <- ggplot(dfh, aes(epoch)) +
    geom_line(aes(y = acc, color = "Train Acc"), linewidth = 0.8) +
    geom_line(aes(y = val_acc, color = "Val Acc"), linewidth = 0.8) +
    scale_color_manual(values = c("Train Acc" = "#009e73", "Val Acc" = "#d55e00")) +
    labs(title = "Training vs Validation Accuracy", y = "Accuracy", x = "Epoch", color = NULL)
  save_plot(cowplot::plot_grid(p1, p2, ncol = 1), "fig10_training_history.png", width = 8, height = 8)
} else {
  placeholder <- ggplot() + annotate("text", x = 0, y = 0, label = "TensorFlow not available. Training skipped.") + xlim(-1, 1) + ylim(-1, 1)
  save_plot(placeholder, "fig10_training_history.png", width = 8, height = 8)
}
```

## Evaluation

``` r
if (tf_ok) {
  pred_prob <- model %>% predict(x_val)
  pred_class <- max.col(pred_prob) - 1
  truth <- y[-train_index]

  cm <- caret::confusionMatrix(factor(pred_class), factor(truth))
  roc_obj <- pROC::roc(truth, pred_prob[, 2])

  metrics_txt <- file.path("images", "metrics.txt")
  cat(
    sprintf("Confusion Matrix:\n%s\n\nAccuracy: %.4f\nKappa: %.4f\nAUC: %.4f\n",
            capture.output(cm$table) %>% paste(collapse = "\n"),
            cm$overall[["Accuracy"]], cm$overall[["Kappa"]], pROC::auc(roc_obj)),
    file = metrics_txt
  )

  roc_df <- data.frame(tpr = roc_obj$sensitivities, fpr = 1 - roc_obj$specificities)
  p_roc <- ggplot(roc_df, aes(x = fpr, y = tpr)) +
    geom_line(color = "#0072b2", linewidth = 1) +
    geom_abline(slope = 1, intercept = 0, linetype = "dashed", color = "gray50") +
    labs(title = sprintf("ROC Curve (AUC = %.3f)", as.numeric(pROC::auc(roc_obj))), x = "False Positive Rate", y = "True Positive Rate")
  save_plot(p_roc, "fig11_roc.png", width = 6, height = 5)
} else {
  writeLines("TensorFlow not available. Training and evaluation skipped.", file.path("images", "metrics.txt"))
  placeholder <- ggplot() + annotate("text", x = 0, y = 0, label = "TensorFlow not available. ROC unavailable.") + xlim(-1, 1) + ylim(-1, 1)
  save_plot(placeholder, "fig11_roc.png", width = 6, height = 5)
}
```
