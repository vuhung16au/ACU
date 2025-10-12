# Cross-Validation in R: Iris Dataset Classification

This notebook demonstrates the implementation of cross-validation
techniques in R for classification using the famous Iris dataset. We’ll
explore how to use cross-validation to compare different machine
learning algorithms and select the best performing model.

## Setup and Data Loading

    # Load required libraries
    library(caret)
    library(e1071)
    library(randomForest)
    library(class)
    library(ggplot2)
    library(dplyr)
    library(gridExtra)
    library(knitr)
    # library(VIM)  # Not needed for this analysis

    # Set theme for consistent plotting
    theme_set(theme_minimal() + theme(plot.background = element_rect(fill = "white", color = NA)))

    # Load the iris dataset
    cat("Loading iris dataset...\n")

    ## Loading iris dataset...

    data(iris)

    # Display basic information about the dataset
    cat("Dataset Information:\n")

    ## Dataset Information:

    cat("Dataset dimensions:", dim(iris), "\n")

    ## Dataset dimensions: 150 5

    cat("Column names:", colnames(iris), "\n")

    ## Column names: Sepal.Length Sepal.Width Petal.Length Petal.Width Species

    cat("Species distribution:\n")

    ## Species distribution:

    print(table(iris$Species))

    ## 
    ##     setosa versicolor  virginica 
    ##         50         50         50

    # Display first few rows
    cat("First 6 rows of the dataset:\n")

    ## First 6 rows of the dataset:

    print(head(iris))

    ##   Sepal.Length Sepal.Width Petal.Length Petal.Width Species
    ## 1          5.1         3.5          1.4         0.2  setosa
    ## 2          4.9         3.0          1.4         0.2  setosa
    ## 3          4.7         3.2          1.3         0.2  setosa
    ## 4          4.6         3.1          1.5         0.2  setosa
    ## 5          5.0         3.6          1.4         0.2  setosa
    ## 6          5.4         3.9          1.7         0.4  setosa

    # Basic statistics
    cat("Dataset summary:\n")

    ## Dataset summary:

    print(summary(iris))

    ##   Sepal.Length    Sepal.Width     Petal.Length    Petal.Width   
    ##  Min.   :4.300   Min.   :2.000   Min.   :1.000   Min.   :0.100  
    ##  1st Qu.:5.100   1st Qu.:2.800   1st Qu.:1.600   1st Qu.:0.300  
    ##  Median :5.800   Median :3.000   Median :4.350   Median :1.300  
    ##  Mean   :5.843   Mean   :3.057   Mean   :3.758   Mean   :1.199  
    ##  3rd Qu.:6.400   3rd Qu.:3.300   3rd Qu.:5.100   3rd Qu.:1.800  
    ##  Max.   :7.900   Max.   :4.400   Max.   :6.900   Max.   :2.500  
    ##        Species  
    ##  setosa    :50  
    ##  versicolor:50  
    ##  virginica :50  
    ##                 
    ##                 
    ## 

## Data Visualization

    # Sepal measurements scatter plot
    p1 <- ggplot(iris, aes(x = Sepal.Length, y = Sepal.Width, color = Species)) +
      geom_point(size = 2, alpha = 0.7) +
      labs(title = "Sepal Length vs Sepal Width by Species",
           x = "Sepal Length (cm)",
           y = "Sepal Width (cm)") +
      theme_minimal() +
      theme(plot.background = element_rect(fill = "white", color = NA))

    print(p1)

<img src="Cross-Validation-Iris_files/figure-markdown_strict/sepal-plot-1.png" style="display: block; margin: auto;" />

    # Petal measurements scatter plot
    p2 <- ggplot(iris, aes(x = Petal.Length, y = Petal.Width, color = Species)) +
      geom_point(size = 2, alpha = 0.7) +
      labs(title = "Petal Length vs Petal Width by Species",
           x = "Petal Length (cm)",
           y = "Petal Width (cm)") +
      theme_minimal() +
      theme(plot.background = element_rect(fill = "white", color = NA))

    print(p2)

<img src="Cross-Validation-Iris_files/figure-markdown_strict/petal-plot-1.png" style="display: block; margin: auto;" />

    # Box plots for feature distributions
    p3 <- ggplot(iris, aes(x = Species, y = Sepal.Length, fill = Species)) +
      geom_boxplot(alpha = 0.7) +
      labs(title = "Sepal Length Distribution by Species",
           x = "Species",
           y = "Sepal Length (cm)") +
      theme_minimal() +
      theme(plot.background = element_rect(fill = "white", color = NA))

    p4 <- ggplot(iris, aes(x = Species, y = Petal.Length, fill = Species)) +
      geom_boxplot(alpha = 0.7) +
      labs(title = "Petal Length Distribution by Species",
           x = "Species",
           y = "Petal Length (cm)") +
      theme_minimal() +
      theme(plot.background = element_rect(fill = "white", color = NA))

    # Combine plots
    grid.arrange(p3, p4, ncol = 2)

<img src="Cross-Validation-Iris_files/figure-markdown_strict/boxplots-1.png" style="display: block; margin: auto;" />

## Cross-Validation Setup

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

    cat("Cross-validation setup:\n")

    ## Cross-validation setup:

    cat("- Method: 10-fold cross-validation\n")

    ## - Method: 10-fold cross-validation

    cat("- Features:", ncol(X), "\n")

    ## - Features: 4

    cat("- Samples:", nrow(X), "\n")

    ## - Samples: 150

    cat("- Classes:", length(unique(y)), "\n")

    ## - Classes: 3

## Model Training with Cross-Validation

    # 1. k-Nearest Neighbors (k-NN)
    cat("Training k-NN model with cross-validation...\n")

    ## Training k-NN model with cross-validation...

    knn_model <- train(
      x = X,
      y = y,
      method = "knn",
      trControl = cv_control,
      tuneGrid = expand.grid(k = c(1, 3, 5, 7, 9, 11, 13, 15))
    )

    print(knn_model)

    ## k-Nearest Neighbors 
    ## 
    ## 150 samples
    ##   4 predictor
    ##   3 classes: 'setosa', 'versicolor', 'virginica' 
    ## 
    ## No pre-processing
    ## Resampling: Cross-Validated (10 fold) 
    ## Summary of sample sizes: 135, 135, 135, 135, 135, 135, ... 
    ## Resampling results across tuning parameters:
    ## 
    ##   k   Accuracy   Kappa
    ##    1  0.9533333  0.93 
    ##    3  0.9666667  0.95 
    ##    5  0.9666667  0.95 
    ##    7  0.9666667  0.95 
    ##    9  0.9600000  0.94 
    ##   11  0.9800000  0.97 
    ##   13  0.9733333  0.96 
    ##   15  0.9800000  0.97 
    ## 
    ## Accuracy was used to select the optimal model using the largest value.
    ## The final value used for the model was k = 15.

    # 2. Support Vector Machine (SVM)
    cat("Training SVM model with cross-validation...\n")

    ## Training SVM model with cross-validation...

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

    print(svm_model)

    ## Support Vector Machines with Radial Basis Function Kernel 
    ## 
    ## 150 samples
    ##   4 predictor
    ##   3 classes: 'setosa', 'versicolor', 'virginica' 
    ## 
    ## No pre-processing
    ## Resampling: Cross-Validated (10 fold) 
    ## Summary of sample sizes: 135, 135, 135, 135, 135, 135, ... 
    ## Resampling results across tuning parameters:
    ## 
    ##   C      sigma  Accuracy   Kappa
    ##     0.1   0.1   0.9000000  0.85 
    ##     0.1   1.0   0.9333333  0.90 
    ##     0.1  10.0   0.8466667  0.77 
    ##     1.0   0.1   0.9666667  0.95 
    ##     1.0   1.0   0.9400000  0.91 
    ##     1.0  10.0   0.9000000  0.85 
    ##    10.0   0.1   0.9466667  0.92 
    ##    10.0   1.0   0.9266667  0.89 
    ##    10.0  10.0   0.9066667  0.86 
    ##   100.0   0.1   0.9533333  0.93 
    ##   100.0   1.0   0.9266667  0.89 
    ##   100.0  10.0   0.9066667  0.86 
    ## 
    ## Accuracy was used to select the optimal model using the largest value.
    ## The final values used for the model were sigma = 0.1 and C = 1.

    # 3. Random Forest
    cat("Training Random Forest model with cross-validation...\n")

    ## Training Random Forest model with cross-validation...

    rf_model <- train(
      x = X,
      y = y,
      method = "rf",
      trControl = cv_control,
      tuneGrid = expand.grid(mtry = c(1, 2, 3, 4))
    )

    print(rf_model)

    ## Random Forest 
    ## 
    ## 150 samples
    ##   4 predictor
    ##   3 classes: 'setosa', 'versicolor', 'virginica' 
    ## 
    ## No pre-processing
    ## Resampling: Cross-Validated (10 fold) 
    ## Summary of sample sizes: 135, 135, 135, 135, 135, 135, ... 
    ## Resampling results across tuning parameters:
    ## 
    ##   mtry  Accuracy   Kappa
    ##   1     0.9466667  0.92 
    ##   2     0.9600000  0.94 
    ##   3     0.9600000  0.94 
    ##   4     0.9533333  0.93 
    ## 
    ## Accuracy was used to select the optimal model using the largest value.
    ## The final value used for the model was mtry = 2.

    # 4. Naive Bayes
    cat("Training Naive Bayes model with cross-validation...\n")

    ## Training Naive Bayes model with cross-validation...

    nb_model <- train(
      x = X,
      y = y,
      method = "nb",
      trControl = cv_control
    )

    print(nb_model)

    ## Naive Bayes 
    ## 
    ## 150 samples
    ##   4 predictor
    ##   3 classes: 'setosa', 'versicolor', 'virginica' 
    ## 
    ## No pre-processing
    ## Resampling: Cross-Validated (10 fold) 
    ## Summary of sample sizes: 135, 135, 135, 135, 135, 135, ... 
    ## Resampling results across tuning parameters:
    ## 
    ##   usekernel  Accuracy   Kappa
    ##   FALSE      0.9533333  0.93 
    ##    TRUE      0.9600000  0.94 
    ## 
    ## Tuning parameter 'fL' was held constant at a value of 0
    ## Tuning
    ##  parameter 'adjust' was held constant at a value of 1
    ## Accuracy was used to select the optimal model using the largest value.
    ## The final values used for the model were fL = 0, usekernel = TRUE and adjust
    ##  = 1.

    # 5. Linear Discriminant Analysis (LDA)
    cat("Training LDA model with cross-validation...\n")

    ## Training LDA model with cross-validation...

    lda_model <- train(
      x = X,
      y = y,
      method = "lda",
      trControl = cv_control
    )

    print(lda_model)

    ## Linear Discriminant Analysis 
    ## 
    ## 150 samples
    ##   4 predictor
    ##   3 classes: 'setosa', 'versicolor', 'virginica' 
    ## 
    ## No pre-processing
    ## Resampling: Cross-Validated (10 fold) 
    ## Summary of sample sizes: 135, 135, 135, 135, 135, 135, ... 
    ## Resampling results:
    ## 
    ##   Accuracy  Kappa
    ##   0.98      0.97

## Model Comparison

    # Compare model performance
    cat("Comparing model performance...\n")

    ## Comparing model performance...

    results <- resamples(list(
      kNN = knn_model,
      SVM = svm_model,
      RandomForest = rf_model,
      NaiveBayes = nb_model,
      LDA = lda_model
    ))

    # Display results summary
    cat("Cross-validation results summary:\n")

    ## Cross-validation results summary:

    print(summary(results))

    ## 
    ## Call:
    ## summary.resamples(object = results)
    ## 
    ## Models: kNN, SVM, RandomForest, NaiveBayes, LDA 
    ## Number of resamples: 10 
    ## 
    ## Accuracy 
    ##                   Min.   1st Qu.    Median      Mean 3rd Qu. Max. NA's
    ## kNN          0.9333333 0.9500000 1.0000000 0.9800000       1    1    0
    ## SVM          0.9333333 0.9333333 0.9666667 0.9666667       1    1    0
    ## RandomForest 0.8666667 0.9333333 0.9666667 0.9600000       1    1    0
    ## NaiveBayes   0.9333333 0.9333333 0.9333333 0.9600000       1    1    0
    ## LDA          0.8666667 1.0000000 1.0000000 0.9800000       1    1    0
    ## 
    ## Kappa 
    ##              Min. 1st Qu. Median Mean 3rd Qu. Max. NA's
    ## kNN           0.9   0.925   1.00 0.97       1    1    0
    ## SVM           0.9   0.900   0.95 0.95       1    1    0
    ## RandomForest  0.8   0.900   0.95 0.94       1    1    0
    ## NaiveBayes    0.9   0.900   0.90 0.94       1    1    0
    ## LDA           0.8   1.000   1.00 0.97       1    1    0

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

    print(p5)

<img src="Cross-Validation-Iris_files/figure-markdown_strict/performance-plot-1.png" style="display: block; margin: auto;" />

## Best Model Selection

    # Best model selection
    best_accuracy <- max(accuracy_data$Accuracy)
    best_model_name <- accuracy_data$Model[which.max(accuracy_data$Accuracy)]
    cat("Best performing model:", best_model_name, "\n")

    ## Best performing model: kNN

    cat("Best accuracy:", best_accuracy, "\n")

    ## Best accuracy: 1

## Feature Importance Analysis

    # Feature importance analysis (using Random Forest)
    cat("Feature importance analysis:\n")

    ## Feature importance analysis:

    importance_scores <- varImp(rf_model)
    print(importance_scores)

    ## rf variable importance
    ## 
    ##              Overall
    ## Petal.Length  100.00
    ## Petal.Width    94.49
    ## Sepal.Length   16.71
    ## Sepal.Width     0.00

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

    print(p6)

<img src="Cross-Validation-Iris_files/figure-markdown_strict/feature-importance-plot-1.png" style="display: block; margin: auto;" />

## Confusion Matrix Analysis

    # Confusion matrix for best model
    cat("Creating confusion matrix for best model...\n")

    ## Creating confusion matrix for best model...

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

    ## Confusion Matrix and Statistics
    ## 
    ##             Reference
    ## Prediction   setosa versicolor virginica
    ##   setosa         50          0         0
    ##   versicolor      0         49         1
    ##   virginica       0          1        49
    ## 
    ## Overall Statistics
    ##                                           
    ##                Accuracy : 0.9867          
    ##                  95% CI : (0.9527, 0.9984)
    ##     No Information Rate : 0.3333          
    ##     P-Value [Acc > NIR] : < 2.2e-16       
    ##                                           
    ##                   Kappa : 0.98            
    ##                                           
    ##  Mcnemar's Test P-Value : NA              
    ## 
    ## Statistics by Class:
    ## 
    ##                      Class: setosa Class: versicolor Class: virginica
    ## Sensitivity                 1.0000            0.9800           0.9800
    ## Specificity                 1.0000            0.9900           0.9900
    ## Pos Pred Value              1.0000            0.9800           0.9800
    ## Neg Pred Value              1.0000            0.9900           0.9900
    ## Prevalence                  0.3333            0.3333           0.3333
    ## Detection Rate              0.3333            0.3267           0.3267
    ## Detection Prevalence        0.3333            0.3333           0.3333
    ## Balanced Accuracy           1.0000            0.9850           0.9850

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

    print(p7)

<img src="Cross-Validation-Iris_files/figure-markdown_strict/confusion-matrix-plot-1.png" style="display: block; margin: auto;" />

## Cross-Validation Performance by Fold

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

    print(p8)

<img src="Cross-Validation-Iris_files/figure-markdown_strict/cv-by-fold-1.png" style="display: block; margin: auto;" />

## Summary and Results

    # Summary statistics
    cat("\n=== Cross-Validation Summary ===\n")

    ## 
    ## === Cross-Validation Summary ===

    cat("Dataset: Iris (150 samples, 4 features, 3 classes)\n")

    ## Dataset: Iris (150 samples, 4 features, 3 classes)

    cat("Cross-validation: 10-fold CV\n")

    ## Cross-validation: 10-fold CV

    cat("Best model:", best_model_name, "\n")

    ## Best model: kNN

    cat("Best accuracy:", round(max(results$values$Accuracy), 4), "\n")

    ## Best accuracy: -Inf

    cat("Models tested: k-NN, SVM, Random Forest, Naive Bayes, LDA\n")

    ## Models tested: k-NN, SVM, Random Forest, Naive Bayes, LDA

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

    kable(results_summary, caption = "Cross-Validation Results Summary")

<table>
<caption>Cross-Validation Results Summary</caption>
<thead>
<tr>
<th style="text-align: left;">Model</th>
<th style="text-align: right;">Mean_Accuracy</th>
<th style="text-align: right;">SD_Accuracy</th>
</tr>
</thead>
<tbody>
<tr>
<td style="text-align: left;">kNN</td>
<td style="text-align: right;">0.9800000</td>
<td style="text-align: right;">0.0322031</td>
</tr>
<tr>
<td style="text-align: left;">SVM</td>
<td style="text-align: right;">0.9666667</td>
<td style="text-align: right;">0.0351364</td>
</tr>
<tr>
<td style="text-align: left;">RandomForest</td>
<td style="text-align: right;">0.9600000</td>
<td style="text-align: right;">0.0466137</td>
</tr>
<tr>
<td style="text-align: left;">NaiveBayes</td>
<td style="text-align: right;">0.9600000</td>
<td style="text-align: right;">0.0344265</td>
</tr>
<tr>
<td style="text-align: left;">LDA</td>
<td style="text-align: right;">0.9800000</td>
<td style="text-align: right;">0.0449966</td>
</tr>
</tbody>
</table>

## Conclusion

This analysis demonstrates the power of cross-validation in selecting
optimal machine learning models for classification tasks. The key
findings include:

1.  **Cross-validation provides robust model evaluation** by testing
    models on multiple data splits
2.  **Different algorithms perform differently** on the same dataset,
    highlighting the importance of model comparison
3.  **Feature importance analysis** reveals which measurements are most
    predictive of iris species
4.  **The process is systematic and reproducible** using R’s caret
    package

The cross-validation approach ensures that our model selection is robust
and generalizes well to unseen data, which is crucial for real-world
machine learning applications.
