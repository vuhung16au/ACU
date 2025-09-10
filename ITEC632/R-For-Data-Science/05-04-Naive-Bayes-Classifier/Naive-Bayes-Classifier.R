# Naive Bayes Classifier — Adult Income Dataset (R)

# Package management -------------------------------------------------------
ensure_packages <- function(packages) {
  installed <- rownames(installed.packages())
  to_install <- setdiff(packages, installed)
  if (length(to_install) > 0) {
    install.packages(to_install, repos = "https://cloud.r-project.org", quiet = TRUE)
  }
  invisible(lapply(packages, require, character.only = TRUE))
}

ensure_packages(c(
  "e1071",      # naiveBayes
  "caret",      # data split + confusionMatrix
  "ggplot2",    # plots
  "dplyr",      # data wrangling
  "pROC",       # ROC / AUC
  "tidyr"       # gather/spread
))

# Paths --------------------------------------------------------------------
module_dir <- "05-04-Naive-Bayes-Classifier"
data_path <- file.path(module_dir, "Income-Dataset", "adult.csv")
images_dir <- file.path(module_dir, "images")
if (!dir.exists(images_dir)) dir.create(images_dir, recursive = TRUE)

# Load data ----------------------------------------------------------------
# The CSV has no header, fields separated by commas with possible spaces.
col_names <- c(
  "age", "workclass", "fnlwgt", "education", "education_num", "marital_status",
  "occupation", "relationship", "race", "sex", "capital_gain", "capital_loss",
  "hours_per_week", "native_country", "income"
)

adult <- read.csv(
  data_path,
  header = FALSE,
  strip.white = TRUE,
  stringsAsFactors = FALSE
)
colnames(adult) <- col_names

# Basic cleaning -----------------------------------------------------------
# Replace '?' with NA in selected categorical columns
adult$workclass[adult$workclass == "?"] <- NA
adult$occupation[adult$occupation == "?"] <- NA
adult$native_country[adult$native_country == "?"] <- NA

# Trim whitespace
char_cols <- sapply(adult, is.character)
adult[char_cols] <- lapply(adult[char_cols], trimws)

# Target variable as factor with explicit level order
adult$income <- factor(adult$income, levels = c("<=50K", ">50K"))

# Train-test split ---------------------------------------------------------
set.seed(16)
train_idx <- caret::createDataPartition(adult$income, p = 0.7, list = FALSE)
adult_train <- adult[train_idx, ]
adult_test  <- adult[-train_idx, ]

# Impute missing values in categorical columns with training-set mode ------
mode_value <- function(x) {
  ux <- stats::na.omit(x)
  if (length(ux) == 0) return(NA)
  tab <- sort(table(ux), decreasing = TRUE)
  names(tab)[1]
}

cat_cols <- c("workclass", "occupation", "native_country")
for (col in cat_cols) {
  m <- mode_value(adult_train[[col]])
  adult_train[[col]][is.na(adult_train[[col]])] <- m
  adult_test[[col]][is.na(adult_test[[col]])]  <- m
}

# Ensure factor types for categoricals -------------------------------------
categorical_cols <- c(
  "workclass", "education", "marital_status", "occupation", "relationship",
  "race", "sex", "native_country", "income"
)

for (col in categorical_cols) {
  adult_train[[col]] <- as.factor(adult_train[[col]])
  adult_test[[col]] <- factor(adult_test[[col]], levels = levels(adult_train[[col]]))
}

# Feature scaling for numeric columns (fit on train, apply to test) --------
numeric_cols <- c("age", "fnlwgt", "education_num", "capital_gain", "capital_loss", "hours_per_week")

scale_params <- lapply(adult_train[numeric_cols], function(x) list(center = mean(x), scale = stats::sd(x)))
for (col in numeric_cols) {
  p <- scale_params[[col]]
  adult_train[[col]] <- if (is.na(p$scale) || p$scale == 0) adult_train[[col]] - p$center else (adult_train[[col]] - p$center) / p$scale
  adult_test[[col]]  <- if (is.na(p$scale) || p$scale == 0) adult_test[[col]] - p$center else (adult_test[[col]] - p$center) / p$scale
}

# EDA: target distribution -------------------------------------------------
plot_target_dist <- ggplot(adult, aes(x = income)) +
  geom_bar(fill = "#2c7fb8") +
  labs(title = "Income Class Distribution", x = "Income", y = "Count") +
  theme_minimal(base_size = 12)

ggplot2::ggsave(filename = file.path(images_dir, "01_target_distribution.png"), plot = plot_target_dist, width = 7, height = 4, dpi = 150, bg = "white")

# Correlation matrix of numeric features ----------------------------------
corr_df <- stats::cor(adult[numeric_cols])

corr_long <- as.data.frame(as.table(corr_df))
colnames(corr_long) <- c("Feature1", "Feature2", "Correlation")

plot_corr <- ggplot(corr_long, aes(Feature1, Feature2, fill = Correlation)) +
  geom_tile() +
  scale_fill_gradient2(low = "#d7191c", mid = "#ffffbf", high = "#1a9641", midpoint = 0) +
  theme_minimal(base_size = 11) +
  theme(axis.text.x = element_text(angle = 45, hjust = 1)) +
  labs(title = "Correlation Matrix (Numeric Features)")

ggplot2::ggsave(filename = file.path(images_dir, "02_correlation_matrix.png"), plot = plot_corr, width = 6.5, height = 5.5, dpi = 150, bg = "white")

# Model training -----------------------------------------------------------
formula_nbc <- stats::as.formula("income ~ .")
nb_model <- e1071::naiveBayes(formula_nbc, data = adult_train)

# Predictions --------------------------------------------------------------
y_pred <- predict(nb_model, newdata = adult_test, type = "class")
y_prob <- predict(nb_model, newdata = adult_test, type = "raw")

# Metrics ------------------------------------------------------------------
cm <- caret::confusionMatrix(y_pred, adult_test$income, positive = ">50K")

accuracy <- cm$overall["Accuracy"]
by_class <- as.list(cm$byClass)

cat(sprintf("Model accuracy: %.4f\n", as.numeric(accuracy)))
cat("Confusion Matrix:\n")
print(cm$table)
cat("\nBy-class metrics (positive='>50K'):\n")
print(by_class[c("Sensitivity", "Specificity", "Precision", "Recall", "F1")])

# Confusion matrix heatmap -------------------------------------------------
cm_df <- as.data.frame(cm$table)
colnames(cm_df) <- c("Reference", "Prediction", "Freq")

plot_cm <- ggplot(cm_df, aes(x = Reference, y = Prediction, fill = Freq)) +
  geom_tile() +
  geom_text(aes(label = Freq), color = "white", fontface = "bold") +
  scale_fill_gradient(low = "#74add1", high = "#313695") +
  theme_minimal(base_size = 12) +
  labs(title = "Confusion Matrix", x = "Actual", y = "Predicted")

ggplot2::ggsave(filename = file.path(images_dir, "03_confusion_matrix.png"), plot = plot_cm, width = 6, height = 5, dpi = 150, bg = "white")

# Histogram of predicted probabilities (>50K) ------------------------------
prob_pos <- y_prob[, ">50K"]
plot_hist <- ggplot(data.frame(prob_pos = prob_pos), aes(x = prob_pos)) +
  geom_histogram(bins = 10, fill = "#1b9e77", color = "white") +
  theme_minimal(base_size = 12) +
  labs(title = "Histogram of predicted probabilities of salaries >50K",
       x = "Predicted probabilities of salaries >50K", y = "Frequency") +
  xlim(0, 1)

ggplot2::ggsave(filename = file.path(images_dir, "04_histogram_predicted_probabilities.png"), plot = plot_hist, width = 7, height = 4, dpi = 150, bg = "white")

# ROC / AUC ----------------------------------------------------------------
roc_obj <- pROC::roc(response = adult_test$income, predictor = prob_pos, levels = c("<=50K", ">50K"), direction = ">")
auc_val <- pROC::auc(roc_obj)

plot_roc <- ggplot(data.frame(fpr = 1 - roc_obj$specificities, tpr = roc_obj$sensitivities),
                   aes(x = fpr, y = tpr)) +
  geom_line(color = "#d73027", size = 1.1) +
  geom_abline(slope = 1, intercept = 0, linetype = "dashed", color = "gray50") +
  theme_minimal(base_size = 12) +
  labs(title = sprintf("ROC Curve (Naive Bayes) — AUC = %.4f", as.numeric(auc_val)),
       x = "False Positive Rate (1 - Specificity)", y = "True Positive Rate (Sensitivity)")

ggplot2::ggsave(filename = file.path(images_dir, "05_roc_curve.png"), plot = plot_roc, width = 6.5, height = 5, dpi = 150, bg = "white")

# Cross-validation (5-fold) ------------------------------------------------
set.seed(16)
train_ctrl <- caret::trainControl(method = "cv", number = 5)
cv_model <- caret::train(
  x = adult_train %>% dplyr::select(-income),
  y = adult_train$income,
  method = "naive_bayes",
  trControl = train_ctrl
)
cat(sprintf("Cross-validated Accuracy (5-fold): %.4f\n", max(cv_model$results$Accuracy)))

cat("\nSummary:\n")
cat(sprintf("Accuracy (test): %.4f\n", as.numeric(accuracy)))
cat(sprintf("AUC (test): %.4f\n", as.numeric(auc_val)))
