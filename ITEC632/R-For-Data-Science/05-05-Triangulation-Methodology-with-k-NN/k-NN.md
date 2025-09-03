Triangulation Methodology with k-NN on Pokemon Dataset
================

- [Overview](#overview)
- [Libraries and Data](#libraries-and-data)
- [Train/Test Split and Scaling](#traintest-split-and-scaling)
- [Cross-Validation to Select k](#cross-validation-to-select-k)
- [Fit Final Model and Evaluate](#fit-final-model-and-evaluate)
- [Conclusion](#conclusion)

## Overview

This notebook demonstrates triangulation for model evaluation using k-NN
on a simple Pokemon dataset:

- Cross-validation for hyperparameter selection (k)
- Multiple evaluation metrics:
  - Accuracy (overall performance)
  - AUC + ROC (discriminative power)
  - Lift Chart (business relevance)
  - Confusion Matrix (detailed breakdown)

## Libraries and Data

``` r
suppressPackageStartupMessages({
  library(readr)
  library(dplyr)
  library(ggplot2)
  library(class)
  library(caret)
  library(pROC)
})

base_dir <- "05-05-Triangulation-Methodology-with-k-NN"
img_dir <- file.path(base_dir, "images")
if (!dir.exists(img_dir)) dir.create(img_dir, recursive = TRUE)

# Resolve dataset path robustly regardless of working directory
local_path <- file.path("Pokemon-Dataset", "pokemon.csv")
project_path <- file.path(base_dir, "Pokemon-Dataset", "pokemon.csv")
data_path <- if (file.exists(local_path)) local_path else project_path

pokemon <- suppressMessages(readr::read_csv(data_path, show_col_types = FALSE))

pokemon <- pokemon %>%
  mutate(Fast = as.factor(ifelse(speed >= median(speed, na.rm = TRUE), "Yes", "No"))) %>%
  select(attack, defense, speed, Fast) %>%
  na.omit()

glimpse(pokemon)
```

    ## Rows: 7
    ## Columns: 4
    ## $ attack  <dbl> 55, 52, 48, 49, 55, 45, 65
    ## $ defense <dbl> 40, 43, 65, 49, 50, 20, 60
    ## $ speed   <dbl> 90, 65, 43, 45, 55, 20, 110
    ## $ Fast    <fct> Yes, Yes, No, No, Yes, No, Yes

## Train/Test Split and Scaling

``` r
set.seed(123)
train_index <- createDataPartition(pokemon$Fast, p = 0.8, list = FALSE)
train_df <- pokemon[train_index, ]
test_df  <- pokemon[-train_index, ]

if (nrow(test_df) == 0 || length(unique(test_df$Fast)) < 2) {
  set.seed(123)
  test_idx <- c()
  for (lvl in levels(pokemon$Fast)) {
    idx_lvl <- which(pokemon$Fast == lvl)
    if (length(idx_lvl) > 0) test_idx <- c(test_idx, sample(idx_lvl, size = 1))
  }
  test_idx <- unique(test_idx)
  if (length(test_idx) == 0) test_idx <- sample(seq_len(nrow(pokemon)), size = 1)
  test_df <- pokemon[test_idx, ]
  train_df <- pokemon[-test_idx, ]
}

scale_train <- scale(train_df %>% select(-Fast))
center_values <- attr(scale_train, "scaled:center")
scale_values  <- attr(scale_train, "scaled:scale")

scale_like_train <- function(df) {
  as.data.frame(scale(df %>% select(-Fast), center = center_values, scale = scale_values))
}

x_train <- scale_like_train(train_df)
x_test  <- scale_like_train(test_df)
y_train <- train_df$Fast
y_test  <- test_df$Fast

summary(y_train)
```

    ##  No Yes 
    ##   2   3

``` r
summary(y_test)
```

    ##  No Yes 
    ##   1   1

## Cross-Validation to Select k

``` r
set.seed(123)
max_k_global <- max(1, min(25, nrow(train_df) - 1))
k_grid <- seq(1, max_k_global, by = 2)
cv_results <- data.frame(k = integer(), Accuracy = numeric())

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

    k_use <- min(k, max(1, nrow(x_tr) - 1))
    preds <- class::knn(train = x_tr, test = x_val, cl = y_tr, k = k_use, prob = TRUE)
    acc_fold <- c(acc_fold, mean(preds == y_val))
  }
  cv_results <- rbind(cv_results, data.frame(k = k, Accuracy = mean(acc_fold)))
}

best_k <- cv_results$k[which.max(cv_results$Accuracy)]

library(ggplot2)
cv_plot <- ggplot(cv_results, aes(x = k, y = Accuracy)) +
  geom_line(color = "steelblue", linewidth = 1) +
  geom_point(color = "steelblue", size = 2) +
  geom_vline(xintercept = best_k, color = "red", linetype = "dashed", linewidth = 1) +
  labs(title = paste0("CV Accuracy vs k (best k = ", best_k, ")"), x = "k", y = "CV Accuracy") +
  theme_minimal()

cv_plot
```

![](05-05-Triangulation-Methodology-with-k-NN/images/unnamed-chunk-3-1.png)<!-- -->

## Fit Final Model and Evaluate

``` r
set.seed(123)
best_k_use <- min(best_k, max(1, nrow(x_train) - 1))

if (nrow(x_test) == 0) {
  preds_class <- factor(character(0), levels = levels(y_train))
  prob_yes <- numeric(0)
} else {
  preds_class <- class::knn(train = x_train, test = x_test, cl = y_train, k = best_k_use, prob = TRUE)
  attr_prob <- attr(preds_class, "prob")
  prob_yes <- ifelse(preds_class == "Yes", attr_prob, 1 - attr_prob)
}

accuracy <- if (length(y_test) > 0) mean(preds_class == y_test) else NA_real_

auc_val <- NA_real_
roc_obj <- NULL
if (length(unique(y_test)) == 2 && length(prob_yes) > 0) {
  roc_obj <- try(pROC::roc(response = y_test, predictor = as.numeric(prob_yes), levels = c("No", "Yes"), quiet = TRUE), silent = TRUE)
  if (!inherits(roc_obj, "try-error") && !is.null(roc_obj)) {
    auc_val <- as.numeric(pROC::auc(roc_obj))
  }
}

list(accuracy = accuracy, auc = auc_val, best_k = best_k, used_k = best_k_use)
```

    ## $accuracy
    ## [1] 0.5
    ## 
    ## $auc
    ## [1] 0.5
    ## 
    ## $best_k
    ## [1] 1
    ## 
    ## $used_k
    ## [1] 1

### ROC Curve

``` r
if (!is.null(roc_obj)) {
  df_roc <- data.frame(tpr = roc_obj$sensitivities, fpr = 1 - roc_obj$specificities)
  ggplot(df_roc, aes(x = fpr, y = tpr)) +
    geom_line(color = "darkorange", linewidth = 1) +
    geom_abline(slope = 1, intercept = 0, linetype = "dashed") +
    coord_equal() +
    labs(title = sprintf("ROC Curve (AUC = %.3f)", auc_val), x = "False Positive Rate", y = "True Positive Rate") +
    theme_minimal()
}
```

![](05-05-Triangulation-Methodology-with-k-NN/images/unnamed-chunk-5-1.png)<!-- -->

### Lift Chart

``` r
baseline <- mean(y_test == "Yes", na.rm = TRUE)
if (!is.na(baseline) && baseline > 0 && length(prob_yes) > 0) {
  lift_df <- data.frame(prob_yes = as.numeric(prob_yes), actual = y_test) %>%
    mutate(decile = ntile(prob_yes, 10)) %>%
    group_by(decile) %>%
    summarise(
      avg_prob = mean(prob_yes),
      response_rate = mean(actual == "Yes"),
      n = n(), .groups = "drop"
    ) %>%
    arrange(desc(decile))

  ggplot(lift_df, aes(x = factor(decile, levels = rev(sort(unique(decile)))), y = response_rate / baseline)) +
    geom_col(fill = "steelblue", alpha = 0.8) +
    geom_hline(yintercept = 1, linetype = "dashed", color = "red") +
    labs(title = "Lift Chart (Deciles)", x = "Decile (High score to Low)", y = "Lift over baseline") +
    theme_minimal()
}
```

![](05-05-Triangulation-Methodology-with-k-NN/images/unnamed-chunk-6-1.png)<!-- -->

### Confusion Matrix

``` r
if (length(y_test) > 0) {
  cm <- caret::confusionMatrix(preds_class, y_test, positive = "Yes")
  cm
}
```

    ## Confusion Matrix and Statistics
    ## 
    ##           Reference
    ## Prediction No Yes
    ##        No   0   0
    ##        Yes  1   1
    ##                                           
    ##                Accuracy : 0.5             
    ##                  95% CI : (0.0126, 0.9874)
    ##     No Information Rate : 0.5             
    ##     P-Value [Acc > NIR] : 0.75            
    ##                                           
    ##                   Kappa : 0               
    ##                                           
    ##  Mcnemar's Test P-Value : 1.00            
    ##                                           
    ##             Sensitivity : 1.0             
    ##             Specificity : 0.0             
    ##          Pos Pred Value : 0.5             
    ##          Neg Pred Value : NaN             
    ##              Prevalence : 0.5             
    ##          Detection Rate : 0.5             
    ##    Detection Prevalence : 1.0             
    ##       Balanced Accuracy : 0.5             
    ##                                           
    ##        'Positive' Class : Yes             
    ## 

## Conclusion

This simple demonstration uses triangulation—multiple complementary
metrics—to validate the k-NN model. Even with a tiny dataset,
cross-validation, ROC/AUC, Lift, and confusion matrix together provide a
robust view of model performance.
