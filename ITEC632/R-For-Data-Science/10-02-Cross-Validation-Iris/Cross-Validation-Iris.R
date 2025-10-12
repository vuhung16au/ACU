# Cross-Validation in R with Iris Dataset
# Classification and Cross-Validation Analysis

# Load required libraries
library(caret)
library(e1071)
library(randomForest)
library(class)
library(ggplot2)
library(dplyr)
library(gridExtra)
# library(VIM)  # Not needed for this analysis

# Set working directory and create images directory
setwd("/Users/vuhung/00.Work/02.ACU/github/ITEC632/R-For-Data-Science/10-02-Cross-Validation-Iris")
if (!dir.exists("images")) {
  dir.create("images")
}

# Set theme for white background plots
theme_set(theme_minimal() + theme(plot.background = element_rect(fill = "white", color = NA)))

# Load the iris dataset
cat("Loading iris dataset...\n")
data(iris)

# Display basic information about the dataset
cat("Dataset Information:\n")
cat("Dataset dimensions:", dim(iris), "\n")
cat("Column names:", colnames(iris), "\n")
cat("Species distribution:\n")
print(table(iris$Species))

# Display first few rows
cat("First 6 rows of the dataset:\n")
print(head(iris))

# Basic statistics
cat("Dataset summary:\n")
print(summary(iris))

# Create data visualization
cat("Creating data visualizations...\n")

# 1. Pair plot of all features
p1 <- ggplot(iris, aes(x = Sepal.Length, y = Sepal.Width, color = Species)) +
  geom_point(size = 2, alpha = 0.7) +
  labs(title = "Sepal Length vs Sepal Width by Species",
       x = "Sepal Length (cm)",
       y = "Sepal Width (cm)") +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA))

ggsave("images/sepal_plot.png", p1, width = 8, height = 6, dpi = 300, bg = "white")

# 2. Petal measurements plot
p2 <- ggplot(iris, aes(x = Petal.Length, y = Petal.Width, color = Species)) +
  geom_point(size = 2, alpha = 0.7) +
  labs(title = "Petal Length vs Petal Width by Species",
       x = "Petal Length (cm)",
       y = "Petal Width (cm)") +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA))

ggsave("images/petal_plot.png", p2, width = 8, height = 6, dpi = 300, bg = "white")

# 3. Box plots for each feature
p3 <- ggplot(iris, aes(x = Species, y = Sepal.Length, fill = Species)) +
  geom_boxplot(alpha = 0.7) +
  labs(title = "Sepal Length Distribution by Species",
       x = "Species",
       y = "Sepal Length (cm)") +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA))

ggsave("images/sepal_length_boxplot.png", p3, width = 8, height = 6, dpi = 300, bg = "white")

# 4. Petal length box plot
p4 <- ggplot(iris, aes(x = Species, y = Petal.Length, fill = Species)) +
  geom_boxplot(alpha = 0.7) +
  labs(title = "Petal Length Distribution by Species",
       x = "Species",
       y = "Petal Length (cm)") +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA))

ggsave("images/petal_length_boxplot.png", p4, width = 8, height = 6, dpi = 300, bg = "white")

# Cross-Validation Analysis
cat("Starting cross-validation analysis...\n")

# Define cross-validation control
cv_control <- trainControl(
  method = "cv",           # k-fold cross-validation
  number = 10,             # 10-fold CV
  classProbs = TRUE,       # Enable class probabilities
  summaryFunction = defaultSummary  # Default summary function
)

# Prepare data for modeling
X <- iris[, 1:4]  # Features
y <- iris$Species  # Target variable

# 1. k-Nearest Neighbors (k-NN)
cat("Training k-NN model with cross-validation...\n")
knn_model <- train(
  x = X,
  y = y,
  method = "knn",
  trControl = cv_control,
  tuneGrid = expand.grid(k = c(1, 3, 5, 7, 9, 11, 13, 15))
)

# 2. Support Vector Machine (SVM)
cat("Training SVM model with cross-validation...\n")
svm_model <- train(
  x = X,
  y = y,
  method = "svmRadial",
  trControl = cv_control,
  tuneGrid = expand.grid(
    C = c(0.1, 1, 10, 100),
    sigma = c(0.1, 1, 10)
  )
)

# 3. Random Forest
cat("Training Random Forest model with cross-validation...\n")
rf_model <- train(
  x = X,
  y = y,
  method = "rf",
  trControl = cv_control,
  tuneGrid = expand.grid(mtry = c(1, 2, 3, 4))
)

# 4. Naive Bayes
cat("Training Naive Bayes model with cross-validation...\n")
nb_model <- train(
  x = X,
  y = y,
  method = "nb",
  trControl = cv_control
)

# 5. Linear Discriminant Analysis (LDA)
cat("Training LDA model with cross-validation...\n")
lda_model <- train(
  x = X,
  y = y,
  method = "lda",
  trControl = cv_control
)

# Compare model performance
cat("Comparing model performance...\n")
results <- resamples(list(
  kNN = knn_model,
  SVM = svm_model,
  RandomForest = rf_model,
  NaiveBayes = nb_model,
  LDA = lda_model
))

# Display results summary
cat("Cross-validation results summary:\n")
print(summary(results))

# Create performance comparison plot
# Extract accuracy values for plotting
accuracy_data <- data.frame(
  Model = rep(c("kNN", "SVM", "RandomForest", "NaiveBayes", "LDA"), each = 10),
  Accuracy = c(
    knn_model$resample$Accuracy,
    svm_model$resample$Accuracy,
    rf_model$resample$Accuracy,
    nb_model$resample$Accuracy,
    lda_model$resample$Accuracy
  )
)

p5 <- ggplot(accuracy_data, aes(x = Model, y = Accuracy, fill = Model)) +
  geom_boxplot(alpha = 0.7) +
  labs(title = "Model Performance Comparison (10-Fold CV)",
       x = "Model",
       y = "Accuracy") +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA),
        axis.text.x = element_text(angle = 45, hjust = 1))

ggsave("images/model_comparison.png", p5, width = 10, height = 6, dpi = 300, bg = "white")

# Best model selection
best_accuracy <- max(accuracy_data$Accuracy)
best_model_name <- accuracy_data$Model[which.max(accuracy_data$Accuracy)]
cat("Best performing model:", best_model_name, "\n")
cat("Best accuracy:", best_accuracy, "\n")

# Feature importance analysis (using Random Forest)
cat("Feature importance analysis:\n")
importance_scores <- varImp(rf_model)
print(importance_scores)

# Create feature importance plot
importance_df <- data.frame(
  feature = rownames(importance_scores$importance),
  importance = importance_scores$importance$Overall
) %>%
  arrange(desc(importance))

p6 <- ggplot(importance_df, aes(x = reorder(feature, importance), y = importance)) +
  geom_col(fill = "steelblue", alpha = 0.7) +
  coord_flip() +
  labs(title = "Feature Importance (Random Forest)",
       x = "Features",
       y = "Importance Score") +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA))

ggsave("images/feature_importance.png", p6, width = 8, height = 6, dpi = 300, bg = "white")

# Confusion matrix for best model
cat("Creating confusion matrix for best model...\n")
if (best_model_name == "kNN") {
  final_model <- knn_model
} else if (best_model_name == "SVM") {
  final_model <- svm_model
} else if (best_model_name == "RandomForest") {
  final_model <- rf_model
} else if (best_model_name == "NaiveBayes") {
  final_model <- nb_model
} else {
  final_model <- lda_model
}

# Make predictions on the full dataset
predictions <- predict(final_model, X)
confusion_matrix <- confusionMatrix(predictions, y)
print(confusion_matrix)

# Create confusion matrix visualization
cm_data <- as.data.frame(confusion_matrix$table)
p7 <- ggplot(cm_data, aes(x = Reference, y = Prediction, fill = Freq)) +
  geom_tile() +
  geom_text(aes(label = Freq), color = "white", size = 4) +
  scale_fill_gradient(low = "lightblue", high = "darkblue") +
  labs(title = paste("Confusion Matrix -", best_model_name),
       x = "Actual",
       y = "Predicted") +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA))

ggsave("images/confusion_matrix.png", p7, width = 8, height = 6, dpi = 300, bg = "white")

# Cross-validation results by fold
cv_results <- data.frame(
  Model = rep(c("kNN", "SVM", "RandomForest", "NaiveBayes", "LDA"), each = 10),
  Fold = rep(1:10, 5),
  Accuracy = c(
    knn_model$resample$Accuracy,
    svm_model$resample$Accuracy,
    rf_model$resample$Accuracy,
    nb_model$resample$Accuracy,
    lda_model$resample$Accuracy
  )
)

p8 <- ggplot(cv_results, aes(x = Fold, y = Accuracy, color = Model)) +
  geom_line(linewidth = 1) +
  geom_point(size = 2) +
  labs(title = "Cross-Validation Performance by Fold",
       x = "Fold",
       y = "Accuracy") +
  theme_minimal() +
  theme(plot.background = element_rect(fill = "white", color = NA))

ggsave("images/cv_by_fold.png", p8, width = 10, height = 6, dpi = 300, bg = "white")

# Summary statistics
cat("\n=== Cross-Validation Summary ===\n")
cat("Dataset: Iris (150 samples, 4 features, 3 classes)\n")
cat("Cross-validation: 10-fold CV\n")
cat("Best model:", best_model_name, "\n")
cat("Best accuracy:", round(max(results$values$Accuracy), 4), "\n")
cat("Models tested: k-NN, SVM, Random Forest, Naive Bayes, LDA\n")

# Save results to CSV
results_summary <- data.frame(
  Model = c("kNN", "SVM", "RandomForest", "NaiveBayes", "LDA"),
  Mean_Accuracy = c(
    mean(knn_model$resample$Accuracy),
    mean(svm_model$resample$Accuracy),
    mean(rf_model$resample$Accuracy),
    mean(nb_model$resample$Accuracy),
    mean(lda_model$resample$Accuracy)
  ),
  SD_Accuracy = c(
    sd(knn_model$resample$Accuracy),
    sd(svm_model$resample$Accuracy),
    sd(rf_model$resample$Accuracy),
    sd(nb_model$resample$Accuracy),
    sd(lda_model$resample$Accuracy)
  )
)

write.csv(results_summary, "images/cross_validation_results.csv", row.names = FALSE)

cat("Cross-validation analysis completed successfully!\n")
cat("Results saved to images/ directory\n")
cat("Generated visualizations:\n")
cat("- sepal_plot.png: Sepal measurements scatter plot\n")
cat("- petal_plot.png: Petal measurements scatter plot\n")
cat("- sepal_length_boxplot.png: Sepal length distribution\n")
cat("- petal_length_boxplot.png: Petal length distribution\n")
cat("- model_comparison.png: Model performance comparison\n")
cat("- feature_importance.png: Feature importance analysis\n")
cat("- confusion_matrix.png: Best model confusion matrix\n")
cat("- cv_by_fold.png: Cross-validation performance by fold\n")
