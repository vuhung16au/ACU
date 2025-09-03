Quadratic Discriminant Analysis with Heart Disease Dataset
================
Data Science Analysis
2025-09-03

- [Introduction](#introduction)
- [Mathematical Foundation](#mathematical-foundation)
- [Sample Data Demonstration](#sample-data-demonstration)
- [Heart Disease Dataset](#heart-disease-dataset)
  - [Preprocessing](#preprocessing)
  - [EDA](#eda)
- [QDA Modeling](#qda-modeling)
- [Discussion](#discussion)
- [References](#references)

## Introduction

Quadratic Discriminant Analysis (QDA) is a supervised classification
technique that models each class with its own covariance matrix,
allowing for non-linear decision boundaries. This makes QDA more
flexible than LDA when class covariances differ.

- **Goal**: Explore QDA using a simple synthetic example and the heart
  disease dataset.
- **Outputs**: Figures and metrics summarizing QDA performance.

## Mathematical Foundation

### What is Quadratic Discriminant Analysis?

Quadratic Discriminant Analysis (QDA) is a probabilistic classification method that assumes each class follows a multivariate normal distribution with potentially different covariance matrices. Unlike Linear Discriminant Analysis (LDA), which assumes equal covariance across all classes, QDA allows for class-specific covariance structures, resulting in quadratic decision boundaries.

### Key Assumptions

1. **Multivariate Normal Distribution**: Each class \(k\) follows \(N(\mu_k, \Sigma_k)\)
2. **Different Covariance Matrices**: \(\Sigma_k \neq \Sigma_j\) for classes \(k \neq j\)
3. **Prior Probabilities**: Known or estimated class priors \(\pi_k\)

### Mathematical Formulation

#### Class-Conditional Density

For a feature vector \(\mathbf{x}\) and class \(k\), the probability density function is:

\[
f_k(\mathbf{x}) = \frac{1}{(2\pi)^{p/2} |\Sigma_k|^{1/2}} \exp\left(-\frac{1}{2}(\mathbf{x} - \mu_k)^T \Sigma_k^{-1} (\mathbf{x} - \mu_k)\right)
\]

where:
- \(p\) is the number of features
- \(\mu_k\) is the mean vector for class \(k\)
- \(\Sigma_k\) is the covariance matrix for class \(k\)

#### Posterior Probability

Using Bayes' theorem, the posterior probability of class \(k\) given \(\mathbf{x}\) is:

\[
P(Y = k | \mathbf{x}) = \frac{\pi_k f_k(\mathbf{x})}{\sum_{j=1}^{K} \pi_j f_j(\mathbf{x})}
\]

#### Decision Rule

The optimal classification rule assigns \(\mathbf{x}\) to class \(k\) that maximizes the posterior probability:

\[
\hat{y} = \arg\max_{k} P(Y = k | \mathbf{x})
\]

#### Quadratic Decision Function

Taking the logarithm and simplifying, the decision function becomes quadratic:

\[
\delta_k(\mathbf{x}) = -\frac{1}{2} \log|\Sigma_k| - \frac{1}{2}(\mathbf{x} - \mu_k)^T \Sigma_k^{-1} (\mathbf{x} - \mu_k) + \log\pi_k
\]

The quadratic term \((\mathbf{x} - \mu_k)^T \Sigma_k^{-1} (\mathbf{x} - \mu_k)\) gives QDA its name and allows for curved decision boundaries.

#### Decision Boundary

The decision boundary between classes \(k\) and \(l\) occurs when:

\[
\delta_k(\mathbf{x}) = \delta_l(\mathbf{x})
\]

This results in a quadratic equation in \(\mathbf{x}\), which can represent ellipses, parabolas, or hyperbolas depending on the covariance structures.

### Comparison with LDA

| Aspect | LDA | QDA |
|--------|-----|-----|
| Covariance | \(\Sigma_k = \Sigma\) (equal) | \(\Sigma_k \neq \Sigma_j\) (different) |
| Decision Boundary | Linear | Quadratic |
| Flexibility | Lower | Higher |
| Parameter Count | \(Kp + p(p+1)/2\) | \(Kp + Kp(p+1)/2\) |
| Overfitting Risk | Lower | Higher |

### Parameter Estimation

From training data, we estimate:

1. **Class Means**: \(\hat{\mu}_k = \frac{1}{n_k} \sum_{i: y_i = k} \mathbf{x}_i\)
2. **Class Covariances**: \(\hat{\Sigma}_k = \frac{1}{n_k-1} \sum_{i: y_i = k} (\mathbf{x}_i - \hat{\mu}_k)(\mathbf{x}_i - \hat{\mu}_k)^T\)
3. **Class Priors**: \(\hat{\pi}_k = \frac{n_k}{n}\)

where \(n_k\) is the number of samples in class \(k\).

## Sample Data Demonstration

We first illustrate QDA on two Gaussian classes with different
covariance matrices.

``` r
# Generate two classes with different covariance matrices
generate_sample_data <- function(n_per_class = 200) {
  mu1 <- c(0, 0)
  mu2 <- c(2, 2)
  Sigma1 <- matrix(c(1.0, 0.6, 0.6, 1.0), 2, 2)
  Sigma2 <- matrix(c(1.8, -0.7, -0.7, 1.0), 2, 2)
  class1 <- MASS::mvrnorm(n = n_per_class, mu = mu1, Sigma = Sigma1)
  class2 <- MASS::mvrnorm(n = n_per_class, mu = mu2, Sigma = Sigma2)
  rbind(
    data.frame(x1 = class1[,1], x2 = class1[,2], class = factor("Class A")),
    data.frame(x1 = class2[,1], x2 = class2[,2], class = factor("Class B"))
  )
}

sample_df <- generate_sample_data()
head(sample_df)
```

    ##           x1          x2   class
    ## 1 -1.4846425  0.48203322 Class A
    ## 2 -0.7928059  0.38105192 Class A
    ## 3  1.5127276  1.27557462 Class A
    ## 4 -0.1798591  0.30598839 Class A
    ## 5  0.3009369 -0.06965999 Class A
    ## 6  1.7469848  1.32101667 Class A

``` r
qda_fit <- qda(class ~ x1 + x2, data = sample_df)

x1_seq <- seq(min(sample_df$x1) - 1, max(sample_df$x1) + 1, length.out = 200)
x2_seq <- seq(min(sample_df$x2) - 1, max(sample_df$x2) + 1, length.out = 200)
grid <- expand.grid(x1 = x1_seq, x2 = x2_seq)
grid$pred <- predict(qda_fit, grid)$class

p_sample <- ggplot() +
  geom_tile(data = grid, aes(x = x1, y = x2, fill = pred), alpha = 0.2) +
  geom_point(data = sample_df, aes(x = x1, y = x2, color = class), size = 1.8, alpha = 0.8) +
  scale_fill_manual(values = c("Class A" = "#c6dbef", "Class B" = "#fdd0a2"), name = "QDA region") +
  scale_color_manual(values = c("Class A" = "#3182bd", "Class B" = "#e6550d")) +
  labs(title = "Sample Data: QDA Decision Regions",
       x = "x1", y = "x2") +
  theme_minimal() +
  theme(legend.position = "bottom",
        panel.background = element_rect(fill = "white"))

print(p_sample)
```

<img src="Quadratic-Discriminant-Analysis_files/figure-gfm/sample_qda_plot-1.png" style="display: block; margin: auto;" />

``` r
ggsave("images/01_sample_data_qda.png", p_sample, width = 8, height = 6, dpi = 300, bg = "white")
```

## Heart Disease Dataset

We now apply QDA to the heart disease dataset.

``` r
heart <- read.csv("Heart-Disease-Dataset/heart.csv")
cat("Rows:", nrow(heart), "Cols:", ncol(heart), "\n")
```

    ## Rows: 303 Cols: 14

``` r
head(heart)
```

    ##   age sex cp trestbps chol fbs restecg thalach exang oldpeak slope ca thal
    ## 1  63   1  3      145  233   1       0     150     0     2.3     0  0    1
    ## 2  37   1  2      130  250   0       1     187     0     3.5     0  0    2
    ## 3  41   0  1      130  204   0       0     172     0     1.4     2  0    2
    ## 4  56   1  1      120  236   0       1     178     0     0.8     2  0    2
    ## 5  57   0  0      120  354   0       1     163     1     0.6     2  0    2
    ## 6  57   1  0      140  192   0       1     148     0     0.4     1  0    1
    ##   target
    ## 1      1
    ## 2      1
    ## 3      1
    ## 4      1
    ## 5      1
    ## 6      1

### Preprocessing

``` r
# Drop known faulty rows if present
if ("ca" %in% names(heart)) heart <- heart[!(heart$ca == 4), ]
if ("thal" %in% names(heart)) heart <- heart[!(heart$thal == 0), ]

# Target to factor
if ("target" %in% names(heart)) {
  heart$target <- factor(heart$target, levels = c(0,1), labels = c("NoDisease", "Disease"))
}

# Integer-coded categoricals (optional)
cat_cols <- intersect(c("sex", "cp", "fbs", "restecg", "exang", "slope", "ca", "thal"), names(heart))
for (cc in cat_cols) heart[[cc]] <- factor(heart[[cc]])

# Quick check
str(heart)
```

    ## 'data.frame':    296 obs. of  14 variables:
    ##  $ age     : int  63 37 41 56 57 57 56 44 52 57 ...
    ##  $ sex     : Factor w/ 2 levels "0","1": 2 2 1 2 1 2 1 2 2 2 ...
    ##  $ cp      : Factor w/ 4 levels "0","1","2","3": 4 3 2 2 1 1 2 2 3 3 ...
    ##  $ trestbps: int  145 130 130 120 120 140 140 120 172 150 ...
    ##  $ chol    : int  233 250 204 236 354 192 294 263 199 168 ...
    ##  $ fbs     : Factor w/ 2 levels "0","1": 2 1 1 1 1 1 1 1 2 1 ...
    ##  $ restecg : Factor w/ 3 levels "0","1","2": 1 2 1 2 2 2 1 2 2 2 ...
    ##  $ thalach : int  150 187 172 178 163 148 153 173 162 174 ...
    ##  $ exang   : Factor w/ 2 levels "0","1": 1 1 1 1 2 1 1 1 1 1 ...
    ##  $ oldpeak : num  2.3 3.5 1.4 0.8 0.6 0.4 1.3 0 0.5 1.6 ...
    ##  $ slope   : Factor w/ 3 levels "0","1","2": 1 1 3 3 3 2 2 3 3 3 ...
    ##  $ ca      : Factor w/ 4 levels "0","1","2","3": 1 1 1 1 1 1 1 1 1 1 ...
    ##  $ thal    : Factor w/ 3 levels "1","2","3": 1 2 2 2 2 1 2 3 3 2 ...
    ##  $ target  : Factor w/ 2 levels "NoDisease","Disease": 2 2 2 2 2 2 2 2 2 2 ...

### EDA

``` r
# Target distribution
p_target <- ggplot(heart, aes(x = target, fill = target)) +
  geom_bar(alpha = 0.9) +
  scale_fill_manual(values = c("NoDisease" = "#6baed6", "Disease" = "#fd8d3c")) +
  labs(title = "Target Distribution", x = "Target", y = "Count") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white"), legend.position = "none")

print(p_target)
```

<img src="Quadratic-Discriminant-Analysis_files/figure-gfm/eda_plots-1.png" style="display: block; margin: auto;" />

``` r
ggsave("images/02_target_distribution.png", p_target, width = 6, height = 4, dpi = 300, bg = "white")

# Correlation plot of numeric variables
num_cols <- names(heart)[sapply(heart, is.numeric)]
if (length(num_cols) > 1) {
  cor_mat <- suppressWarnings(cor(heart[, num_cols], use = "pairwise.complete.obs"))
  corrplot(cor_mat, method = "color", type = "upper", addCoef.col = "black",
           tl.col = "black", tl.srt = 45,
           title = "Heart Dataset: Numeric Correlation Matrix",
           mar = c(0,0,2,0))
}
```

<img src="Quadratic-Discriminant-Analysis_files/figure-gfm/eda_plots-2.png" style="display: block; margin: auto;" />

``` r
sel <- intersect(c("age", "trestbps", "chol", "thalach", "oldpeak"), names(heart))
if (length(sel) > 0) {
  long_df <- tidyr::pivot_longer(heart[, c(sel, "target")], cols = all_of(sel), names_to = "feature", values_to = "value")
  p_hist <- ggplot(long_df, aes(x = value, fill = target)) +
    geom_histogram(bins = 30, alpha = 0.6, position = "identity") +
    facet_wrap(~feature, scales = "free", ncol = 2) +
    scale_fill_manual(values = c("NoDisease" = "#6baed6", "Disease" = "#fd8d3c")) +
    labs(title = "Selected Numeric Features", x = "Value", y = "Frequency") +
    theme_minimal() +
    theme(panel.background = element_rect(fill = "white"))
  print(p_hist)
  ggsave("images/04_numeric_histograms.png", p_hist, width = 10, height = 8, dpi = 300, bg = "white")
}
```

<img src="Quadratic-Discriminant-Analysis_files/figure-gfm/histograms-1.png" style="display: block; margin: auto;" />

## QDA Modeling

``` r
# Predictors: numeric only
numeric_cols <- setdiff(names(heart)[sapply(heart, is.numeric)], c("target"))
stopifnot(length(numeric_cols) >= 2)

set.seed(123)
idx <- sample(seq_len(nrow(heart)), size = floor(0.7 * nrow(heart)))
train <- heart[idx, ]
test  <- heart[-idx, ]

form <- as.formula(paste("target ~", paste(numeric_cols, collapse = " + ")))
qda_model <- qda(form, data = train)

pred_train <- predict(qda_model, train)
pred_test  <- predict(qda_model, test)

acc_train <- mean(pred_train$class == train$target)
acc_test  <- mean(pred_test$class == test$target)

cat(sprintf("Train Accuracy: %.2f%%\n", 100 * acc_train))
```

    ## Train Accuracy: 73.43%

``` r
cat(sprintf("Test  Accuracy: %.2f%%\n", 100 * acc_test))
```

    ## Test  Accuracy: 78.65%

``` r
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

print(p_cm)
```

<img src="Quadratic-Discriminant-Analysis_files/figure-gfm/confusion_matrix-1.png" style="display: block; margin: auto;" />

``` r
ggsave("images/05_confusion_matrix.png", p_cm, width = 7, height = 5.5, dpi = 300, bg = "white")
```

``` r
if (!is.null(pred_test$posterior)) {
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

  print(p_roc)
  ggsave("images/06_roc_curve.png", p_roc, width = 6, height = 5, dpi = 300, bg = "white")
}
```

<img src="Quadratic-Discriminant-Analysis_files/figure-gfm/roc_curve-1.png" style="display: block; margin: auto;" />

## Discussion

- QDA models class-specific covariance, enabling curved boundaries and
  improved performance when class covariances differ.
- For the heart dataset, key numeric predictors (e.g., age, thalach,
  oldpeak) contribute to separability, but performance depends on
  train/test split.

## References

- MASS package documentation for QDA implementation
- Heart disease dataset (Cleveland) data dictionary from Kaggle
