# Cross-Validation in R: Housing Price Prediction

This notebook demonstrates the implementation of cross-validation
techniques in R for predicting housing prices using the Ames Housing
dataset. We’ll explore how to use cross-validation to select optimal
hyperparameters for a Random Forest model.

## Setup and Data Loading

    # Load required libraries
    library(randomForest)
    library(caret)
    library(ggplot2)
    library(dplyr)
    # library(VIM)  # Not needed for this analysis
    library(corrplot)
    library(knitr)

    # Set theme for consistent plotting
    theme_set(theme_minimal() + theme(plot.background = element_rect(fill = "white", color = NA)))

    # Load the dataset
    cat("Loading housing dataset...\n")

    ## Loading housing dataset...

    train_data <- read.csv("Dataset/train.csv", stringsAsFactors = FALSE)
    test_data <- read.csv("Dataset/test.csv", stringsAsFactors = FALSE)

    # Display basic information about the dataset
    cat("Dataset Information:\n")

    ## Dataset Information:

    cat("Training data dimensions:", dim(train_data), "\n")

    ## Training data dimensions: 1460 81

    cat("Test data dimensions:", dim(test_data), "\n")

    ## Test data dimensions: 1459 80

## Data Preprocessing

    # Remove rows with missing target, separate target from predictors
    train_data <- train_data[!is.na(train_data$SalePrice), ]
    y <- train_data$SalePrice
    train_data$SalePrice <- NULL

    # Select numeric columns only (similar to Python version)
    numeric_cols <- sapply(train_data, function(x) is.numeric(x))
    X <- train_data[, numeric_cols, drop = FALSE]
    X_test <- test_data[, numeric_cols, drop = FALSE]

    cat("Numeric features selected:", ncol(X), "\n")

    ## Numeric features selected: 37

    cat("Sample of numeric features:\n")

    ## Sample of numeric features:

    print(head(X[, 1:5]))

    ##   Id MSSubClass LotFrontage LotArea OverallQual
    ## 1  1         60          65    8450           7
    ## 2  2         20          80    9600           6
    ## 3  3         60          68   11250           7
    ## 4  4         70          60    9550           7
    ## 5  5         60          84   14260           8
    ## 6  6         50          85   14115           5

    # Handle missing values with median imputation
    cat("Handling missing values...\n")

    ## Handling missing values...

    X_imputed <- X
    X_test_imputed <- X_test

    # Impute missing values with median
    for (col in names(X_imputed)) {
      if (any(is.na(X_imputed[[col]]))) {
        X_imputed[[col]][is.na(X_imputed[[col]])] <- median(X_imputed[[col]], na.rm = TRUE)
      }
    }

    for (col in names(X_test_imputed)) {
      if (any(is.na(X_test_imputed[[col]]))) {
        X_test_imputed[[col]][is.na(X_test_imputed[[col]])] <- median(X_test_imputed[[col]], na.rm = TRUE)
      }
    }

    cat("Missing values in training data after imputation:", sum(is.na(X_imputed)), "\n")

    ## Missing values in training data after imputation: 0

    cat("Missing values in test data after imputation:", sum(is.na(X_test_imputed)), "\n")

    ## Missing values in test data after imputation: 0

## Cross-Validation Function

    # Function to get cross-validation score (equivalent to Python get_score function)
    get_score <- function(n_estimators, cv_folds = 3) {
      # Create random forest model
      model <- randomForest(x = X_imputed, y = y, ntree = n_estimators, random_state = 0)
      
      # Perform cross-validation
      cv_results <- train(
        x = X_imputed, 
        y = y,
        method = "rf",
        trControl = trainControl(method = "cv", number = cv_folds),
        tuneGrid = data.frame(mtry = floor(sqrt(ncol(X_imputed)))),
        ntree = n_estimators
      )
      
      # Return MAE (Mean Absolute Error)
      return(cv_results$results$MAE)
    }

## Parameter Tuning with Cross-Validation

    # Test different parameter values (equivalent to Python results dictionary)
    cat("Testing different n_estimators values...\n")

    ## Testing different n_estimators values...

    n_estimators_values <- c(50, 100, 150, 200, 250, 300, 350, 400)
    results <- list()

    for (n_est in n_estimators_values) {
      cat("Testing n_estimators =", n_est, "\n")
      mae_score <- get_score(n_est)
      results[[as.character(n_est)]] <- mae_score
      cat("MAE for", n_est, "trees:", mae_score, "\n")
    }

    ## Testing n_estimators = 50 
    ## MAE for 50 trees: 18444.42 
    ## Testing n_estimators = 100 
    ## MAE for 100 trees: 17765.01 
    ## Testing n_estimators = 150 
    ## MAE for 150 trees: 18050.55 
    ## Testing n_estimators = 200 
    ## MAE for 200 trees: 17616.7 
    ## Testing n_estimators = 250 
    ## MAE for 250 trees: 17786.76 
    ## Testing n_estimators = 300 
    ## MAE for 300 trees: 17494.46 
    ## Testing n_estimators = 350 
    ## MAE for 350 trees: 17480.76 
    ## Testing n_estimators = 400 
    ## MAE for 400 trees: 17579.54

    # Convert results to data frame for plotting
    results_df <- data.frame(
      n_estimators = as.numeric(names(results)),
      mae = unlist(results)
    )

    print(results_df)

    ##     n_estimators      mae
    ## 50            50 18444.42
    ## 100          100 17765.01
    ## 150          150 18050.55
    ## 200          200 17616.70
    ## 250          250 17786.76
    ## 300          300 17494.46
    ## 350          350 17480.76
    ## 400          400 17579.54

## Visualization of Cross-Validation Results

    # Create visualization
    p1 <- ggplot(results_df, aes(x = n_estimators, y = mae)) +
      geom_line(color = "blue", linewidth = 1) +
      geom_point(color = "red", size = 2) +
      labs(
        title = "Cross-Validation Results: MAE vs Number of Trees",
        x = "Number of Trees (n_estimators)",
        y = "Mean Absolute Error (MAE)"
      ) +
      theme_minimal() +
      theme(plot.background = element_rect(fill = "white", color = NA))

    print(p1)

<img src="Cross-Validation_files/figure-markdown_strict/cv-visualization-1.png" style="display: block; margin: auto;" />

## Best Parameter Selection

    # Find the best parameter value
    best_n_estimators <- results_df$n_estimators[which.min(results_df$mae)]
    best_mae <- min(results_df$mae)

    cat("Best n_estimators:", best_n_estimators, "\n")

    ## Best n_estimators: 350

    cat("Best MAE:", best_mae, "\n")

    ## Best MAE: 17480.76

## Final Model Training

    # Train final model with best parameters
    cat("Training final model with best parameters...\n")

    ## Training final model with best parameters...

    final_model <- randomForest(x = X_imputed, y = y, ntree = best_n_estimators, random_state = 0)

    # Make predictions on test set
    cat("Making predictions on test set...\n")

    ## Making predictions on test set...

    predictions <- predict(final_model, X_test_imputed)

## Prediction Visualization

    # Create prediction visualization
    pred_df <- data.frame(
      index = 1:length(predictions),
      prediction = predictions
    )

    p2 <- ggplot(pred_df, aes(x = index, y = prediction)) +
      geom_line(color = "blue", alpha = 0.7) +
      geom_point(color = "red", size = 0.5) +
      labs(
        title = "Predicted House Prices on Test Set",
        x = "Test Sample Index",
        y = "Predicted Sale Price ($)"
      ) +
      theme_minimal() +
      theme(plot.background = element_rect(fill = "white", color = NA))

    print(p2)

<img src="Cross-Validation_files/figure-markdown_strict/prediction-viz-1.png" style="display: block; margin: auto;" />

## Feature Importance Analysis

    # Feature importance plot
    importance_scores <- importance(final_model)
    importance_df <- data.frame(
      feature = rownames(importance_scores),
      importance = importance_scores[, 1]
    ) %>%
      arrange(desc(importance)) %>%
      head(15)  # Top 15 features

    p3 <- ggplot(importance_df, aes(x = reorder(feature, importance), y = importance)) +
      geom_col(fill = "steelblue", alpha = 0.7) +
      coord_flip() +
      labs(
        title = "Top 15 Most Important Features",
        x = "Features",
        y = "Importance Score"
      ) +
      theme_minimal() +
      theme(plot.background = element_rect(fill = "white", color = NA))

    print(p3)

<img src="Cross-Validation_files/figure-markdown_strict/feature-importance-1.png" style="display: block; margin: auto;" />

## Correlation Analysis

    # Create correlation plot for top features
    top_features <- importance_df$feature[1:10]
    correlation_data <- X_imputed[, top_features]
    correlation_matrix <- cor(correlation_data, use = "complete.obs")

    corrplot(correlation_matrix, method = "color", type = "upper", 
             order = "hclust", tl.cex = 0.8, tl.col = "black")

<img src="Cross-Validation_files/figure-markdown_strict/correlation-plot-1.png" style="display: block; margin: auto;" />

## Summary and Results

    # Summary statistics
    cat("\n=== Cross-Validation Summary ===\n")

    ## 
    ## === Cross-Validation Summary ===

    cat("Best number of trees:", best_n_estimators, "\n")

    ## Best number of trees: 350

    cat("Best MAE score:", round(best_mae, 2), "\n")

    ## Best MAE score: 17480.76

    cat("Total features used:", ncol(X_imputed), "\n")

    ## Total features used: 37

    cat("Training samples:", nrow(X_imputed), "\n")

    ## Training samples: 1460

    cat("Test samples:", nrow(X_test_imputed), "\n")

    ## Test samples: 1459

    # Save results to file
    results_summary <- data.frame(
      n_estimators = n_estimators_values,
      mae = sapply(n_estimators_values, function(x) results[[as.character(x)]]),
      stringsAsFactors = FALSE
    )

    kable(results_summary, caption = "Cross-Validation Results Summary")

<table>
<caption>Cross-Validation Results Summary</caption>
<thead>
<tr>
<th style="text-align: right;">n_estimators</th>
<th style="text-align: right;">mae</th>
</tr>
</thead>
<tbody>
<tr>
<td style="text-align: right;">50</td>
<td style="text-align: right;">18444.42</td>
</tr>
<tr>
<td style="text-align: right;">100</td>
<td style="text-align: right;">17765.01</td>
</tr>
<tr>
<td style="text-align: right;">150</td>
<td style="text-align: right;">18050.55</td>
</tr>
<tr>
<td style="text-align: right;">200</td>
<td style="text-align: right;">17616.70</td>
</tr>
<tr>
<td style="text-align: right;">250</td>
<td style="text-align: right;">17786.76</td>
</tr>
<tr>
<td style="text-align: right;">300</td>
<td style="text-align: right;">17494.46</td>
</tr>
<tr>
<td style="text-align: right;">350</td>
<td style="text-align: right;">17480.76</td>
</tr>
<tr>
<td style="text-align: right;">400</td>
<td style="text-align: right;">17579.54</td>
</tr>
</tbody>
</table>

## Conclusion

This analysis demonstrates the power of cross-validation in selecting
optimal hyperparameters for machine learning models. The key findings
include:

1.  **Cross-validation helps identify the optimal number of trees** for
    the Random Forest model
2.  **Feature importance analysis** reveals which variables are most
    predictive of house prices
3.  **Correlation analysis** helps understand relationships between
    important features
4.  **The process is systematic and reproducible** using R’s caret
    package

The cross-validation approach ensures that our model selection is robust
and generalizes well to unseen data, which is crucial for real-world
machine learning applications.
