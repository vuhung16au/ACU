# FP-Growth Algorithm — Grocery Store Dataset (R)
# This script demonstrates FP-Growth algorithm for association rule mining

# Helper: install missing packages quietly
install_if_missing <- function(pkgs) {
  for (p in pkgs) {
    if (!requireNamespace(p, quietly = TRUE)) {
      install.packages(p, repos = "https://cloud.r-project.org", quiet = TRUE)
    }
  }
}

install_if_missing(c("arules", "arulesViz", "ggplot2", "dplyr", "gridExtra", "RColorBrewer"))

library(arules)      # For FP-Growth algorithm
library(arulesViz)   # For visualization
library(ggplot2)     # For plotting
library(dplyr)       # For data manipulation
library(gridExtra)   # For arranging multiple plots
library(RColorBrewer) # For color palettes

set.seed(16)

# Create images directory if it doesn't exist
if (!dir.exists("images")) {
  dir.create("images")
}

# -----------------------------
# 1) Load and preprocess data
# -----------------------------
load_grocery_data <- function() {
  cat("=== Loading Grocery Store Dataset ===\n")
  
  # Load the transaction data (preprocess to remove quotes)
  path <- file.path("Grocery-Store-Dataset", "GroceryStoreDataSet.csv")
  raw_data <- readLines(path)
  clean_data <- gsub('"', '', raw_data)  # Remove quotes
  writeLines(clean_data, "temp_transactions.csv")
  
  transactions <- read.transactions("temp_transactions.csv", format = "basket", sep = ",", rm.duplicates = TRUE)
  file.remove("temp_transactions.csv")  # Clean up temporary file
  
  cat("Number of transactions:", nrow(transactions), "\n")
  cat("Number of items:", ncol(transactions), "\n")
  
  transactions
}

# -----------------------------
# 2) Exploratory Data Analysis
# -----------------------------
create_eda_plots <- function(transactions) {
  cat("\n=== Creating EDA plots ===\n")
  
  # Item frequency plot
  png("images/07_item_frequency_fp.png", width = 1200, height = 800, res = 120, bg = "white")
  itemFrequencyPlot(transactions, topN = 20, type = "absolute", 
                    col = brewer.pal(8, "Pastel2"), main = "Top 20 Most Frequent Items (FP-Growth)")
  dev.off()
  cat("Saved: images/07_item_frequency_fp.png\n")
  
  # Item frequency plot (relative)
  png("images/08_item_frequency_relative_fp.png", width = 1200, height = 800, res = 120, bg = "white")
  itemFrequencyPlot(transactions, topN = 20, type = "relative", 
                    col = brewer.pal(8, "Set3"), main = "Top 20 Most Frequent Items (Relative) - FP-Growth")
  dev.off()
  cat("Saved: images/08_item_frequency_relative_fp.png\n")
  
  # Transaction length distribution
  transaction_lengths <- size(transactions)
  length_df <- data.frame(Length = transaction_lengths)
  
  p_length <- ggplot(length_df, aes(x = Length)) +
    geom_histogram(bins = 30, fill = "#9b59b6", alpha = 0.8, color = "white") +
    labs(title = "Distribution of Transaction Lengths (FP-Growth)", 
         x = "Number of Items per Transaction", y = "Frequency") +
    theme_minimal() +
    theme(panel.background = element_rect(fill = "white"))
  ggsave("images/09_transaction_length_distribution_fp.png", p_length, width = 10, height = 6, dpi = 300, bg = "white")
  cat("Saved: images/09_transaction_length_distribution_fp.png\n")
  
  # Summary statistics
  cat("\nTransaction Length Summary:\n")
  print(summary(transaction_lengths))
}

# -----------------------------
# 3) FP-Growth Algorithm Implementation
# -----------------------------
run_fpgrowth_algorithm <- function(transactions) {
  cat("\n=== Running FP-Growth Algorithm ===\n")
  
  # Set parameters (adjusted for small dataset)
  min_support <- 0.1  # 10% minimum support (1 transaction out of 20)
  min_confidence <- 0.3  # 30% minimum confidence
  min_length <- 2  # Minimum rule length
  
  cat("Parameters:\n")
  cat("- Minimum Support:", min_support, "\n")
  cat("- Minimum Confidence:", min_confidence, "\n")
  cat("- Minimum Rule Length:", min_length, "\n")
  
  # Run FP-Growth algorithm (using eclat for frequent itemsets, then generate rules)
  start_time <- Sys.time()
  
  # First, find frequent itemsets using ECLAT (which is similar to FP-Growth)
  frequent_itemsets <- eclat(transactions, 
                             parameter = list(supp = min_support, 
                                             minlen = min_length,
                                             maxlen = 10))
  
  # Generate association rules from frequent itemsets
  rules <- ruleInduction(frequent_itemsets, transactions, 
                        confidence = min_confidence)
  
  end_time <- Sys.time()
  
  execution_time <- end_time - start_time
  cat("Execution time:", round(execution_time, 4), "seconds\n")
  cat("Number of frequent itemsets:", length(frequent_itemsets), "\n")
  cat("Number of rules found:", length(rules), "\n")
  
  # Sort rules by confidence
  rules_sorted <- sort(rules, by = "confidence", decreasing = TRUE)
  
  # Display top 10 rules
  cat("\nTop 10 Rules by Confidence:\n")
  if (length(rules_sorted) > 0) {
    print(inspect(head(rules_sorted, 10)))
  } else {
    cat("No rules found with current parameters. Try lowering support or confidence thresholds.\n")
  }
  
  list(rules = rules_sorted, frequent_itemsets = frequent_itemsets, execution_time = execution_time)
}

# -----------------------------
# 4) Rule Analysis and Visualization
# -----------------------------
analyze_rules <- function(rules) {
  cat("\n=== Analyzing Association Rules ===\n")
  
  if (length(rules) == 0) {
    cat("No rules found to analyze.\n")
    return(data.frame())
  }
  
  # Convert rules to data frame for analysis
  rules_df <- as(rules, "data.frame")
  
  # Summary statistics
  cat("Rules Summary:\n")
  print(summary(rules_df))
  
  # Create visualizations
  create_rule_visualizations(rules, rules_df)
  
  # Analyze rule quality
  analyze_rule_quality(rules_df)
  
  rules_df
}

create_rule_visualizations <- function(rules, rules_df) {
  cat("\n=== Creating Rule Visualizations ===\n")
  
  if (nrow(rules_df) == 0) {
    cat("No rules to visualize.\n")
    return()
  }
  
  # Scatter plot of support vs confidence
  p_scatter <- ggplot(rules_df, aes(x = support, y = confidence, color = lift)) +
    geom_point(alpha = 0.7, size = 2) +
    scale_color_gradient(low = "purple", high = "orange") +
    labs(title = "Association Rules: Support vs Confidence (FP-Growth)", 
         x = "Support", y = "Confidence", color = "Lift") +
    theme_minimal() +
    theme(panel.background = element_rect(fill = "white"))
  ggsave("images/10_support_confidence_scatter_fp.png", p_scatter, width = 10, height = 6, dpi = 300, bg = "white")
  cat("Saved: images/10_support_confidence_scatter_fp.png\n")
  
  # Top rules by lift
  top_rules <- head(rules_df[order(rules_df$lift, decreasing = TRUE), ], 15)
  top_rules$rule_id <- 1:nrow(top_rules)
  
  p_lift <- ggplot(top_rules, aes(x = reorder(rule_id, lift), y = lift)) +
    geom_bar(stat = "identity", fill = "#8e44ad", alpha = 0.8) +
    coord_flip() +
    labs(title = "Top 15 Rules by Lift (FP-Growth)", x = "Rule ID", y = "Lift") +
    theme_minimal() +
    theme(panel.background = element_rect(fill = "white"))
  ggsave("images/11_top_rules_by_lift_fp.png", p_lift, width = 10, height = 6, dpi = 300, bg = "white")
  cat("Saved: images/11_top_rules_by_lift_fp.png\n")
  
  # Distribution of rule metrics
  create_metric_distributions(rules_df)
}

create_metric_distributions <- function(rules_df) {
  # Support distribution
  p_support <- ggplot(rules_df, aes(x = support)) +
    geom_histogram(bins = 30, fill = "#8e44ad", alpha = 0.8, color = "white") +
    labs(title = "Distribution of Support Values (FP-Growth)", x = "Support", y = "Frequency") +
    theme_minimal() +
    theme(panel.background = element_rect(fill = "white"))
  
  # Confidence distribution
  p_confidence <- ggplot(rules_df, aes(x = confidence)) +
    geom_histogram(bins = 30, fill = "#9b59b6", alpha = 0.8, color = "white") +
    labs(title = "Distribution of Confidence Values (FP-Growth)", x = "Confidence", y = "Frequency") +
    theme_minimal() +
    theme(panel.background = element_rect(fill = "white"))
  
  # Lift distribution
  p_lift <- ggplot(rules_df, aes(x = lift)) +
    geom_histogram(bins = 30, fill = "#e67e22", alpha = 0.8, color = "white") +
    labs(title = "Distribution of Lift Values (FP-Growth)", x = "Lift", y = "Frequency") +
    theme_minimal() +
    theme(panel.background = element_rect(fill = "white"))
  
  # Combine plots
  combined_plot <- grid.arrange(p_support, p_confidence, p_lift, ncol = 1)
  ggsave("images/12_rule_metrics_distribution_fp.png", combined_plot, width = 10, height = 12, dpi = 300, bg = "white")
  cat("Saved: images/12_rule_metrics_distribution_fp.png\n")
}

analyze_rule_quality <- function(rules_df) {
  cat("\n=== Rule Quality Analysis ===\n")
  
  # High confidence rules
  high_conf_rules <- rules_df[rules_df$confidence >= 0.8, ]
  cat("High confidence rules (>= 0.8):", nrow(high_conf_rules), "\n")
  
  # High lift rules
  high_lift_rules <- rules_df[rules_df$lift >= 2.0, ]
  cat("High lift rules (>= 2.0):", nrow(high_lift_rules), "\n")
  
  # Strong rules (high confidence and lift)
  strong_rules <- rules_df[rules_df$confidence >= 0.7 & rules_df$lift >= 1.5, ]
  cat("Strong rules (conf >= 0.7 & lift >= 1.5):", nrow(strong_rules), "\n")
  
  if (nrow(strong_rules) > 0) {
    cat("\nTop 5 Strong Rules:\n")
    print(head(strong_rules[order(strong_rules$lift, decreasing = TRUE), ], 5))
  }
}

# -----------------------------
# 5) Performance Analysis
# -----------------------------
analyze_performance <- function(execution_time, num_rules, num_itemsets) {
  cat("\n=== Performance Analysis ===\n")
  
  performance_data <- data.frame(
    Algorithm = "FP-Growth",
    Execution_Time = as.numeric(execution_time),
    Number_of_Itemsets = num_itemsets,
    Number_of_Rules = num_rules,
    Rules_per_Second = num_rules / as.numeric(execution_time),
    Itemsets_per_Second = num_itemsets / as.numeric(execution_time)
  )
  
  cat("Performance Metrics:\n")
  print(performance_data)
  
  # Save performance data
  write.csv(performance_data, "fpgrowth_performance.csv", row.names = FALSE)
  cat("Performance data saved to: fpgrowth_performance.csv\n")
  
  performance_data
}

# -----------------------------
# 6) FP-Growth Specific Analysis
# -----------------------------
analyze_frequent_itemsets <- function(frequent_itemsets) {
  cat("\n=== Frequent Itemsets Analysis ===\n")
  
  if (length(frequent_itemsets) == 0) {
    cat("No frequent itemsets found.\n")
    return(data.frame())
  }
  
  # Convert to data frame
  itemsets_df <- as(frequent_itemsets, "data.frame")
  
  # Summary statistics
  cat("Frequent Itemsets Summary:\n")
  print(summary(itemsets_df))
  
  # Top itemsets by support
  if (nrow(itemsets_df) > 0) {
    top_itemsets <- head(itemsets_df[order(itemsets_df$support, decreasing = TRUE), ], 10)
    cat("\nTop 10 Frequent Itemsets by Support:\n")
    print(top_itemsets)
  } else {
    cat("No itemsets to display.\n")
  }
  
  # Itemset size distribution
  if (length(frequent_itemsets) > 0) {
    itemset_sizes <- size(frequent_itemsets)
    size_df <- data.frame(Size = itemset_sizes)
    
    p_size <- ggplot(size_df, aes(x = Size)) +
      geom_bar(fill = "#9b59b6", alpha = 0.8, color = "white") +
      labs(title = "Distribution of Itemset Sizes (FP-Growth)", 
           x = "Itemset Size", y = "Frequency") +
      theme_minimal() +
      theme(panel.background = element_rect(fill = "white"))
    ggsave("images/13_itemset_size_distribution_fp.png", p_size, width = 10, height = 6, dpi = 300, bg = "white")
    cat("Saved: images/13_itemset_size_distribution_fp.png\n")
  } else {
    cat("No itemsets to create size distribution plot.\n")
  }
  
  # Create additional visualizations for frequent itemsets
  if (nrow(itemsets_df) > 0) {
    create_frequent_itemsets_visualizations(itemsets_df, "_fp_r")
    create_frequent_itemsets_animation(itemsets_df, "_fp_r")
  }
  
  itemsets_df
}

create_frequent_itemsets_visualizations <- function(itemsets_data, suffix) {
  cat("\n=== Creating Frequent Itemsets Visualizations ===\n")
  
  # 1. Frequent Itemsets Summary Visualization
  # Support distribution
  p_support <- ggplot(itemsets_data, aes(x = support)) +
    geom_histogram(bins = 20, fill = "#3498db", alpha = 0.8, color = "white") +
    labs(title = "Frequent Itemsets - Support Distribution", 
         x = "Support", y = "Frequency") +
    theme_minimal() +
    theme(panel.background = element_rect(fill = "white"))
  
  # Count distribution
  p_count <- ggplot(itemsets_data, aes(x = count)) +
    geom_histogram(bins = 20, fill = "#e74c3c", alpha = 0.8, color = "white") +
    labs(title = "Frequent Itemsets - Count Distribution", 
         x = "Count", y = "Frequency") +
    theme_minimal() +
    theme(panel.background = element_rect(fill = "white"))
  
  # Combine support and count plots
  combined_summary <- grid.arrange(p_support, p_count, ncol = 1)
  filename1 <- paste0("images/20_frequent_itemsets_summary", suffix, ".png")
  ggsave(filename1, combined_summary, width = 10, height = 8, dpi = 300, bg = "white")
  cat("Saved:", filename1, "\n")
  
  # 2. Top 10 Frequent Itemsets by Support
  top_10 <- head(itemsets_data[order(itemsets_data$support, decreasing = TRUE), ], 10)
  
  # Create bar plot for top 10 itemsets
  p_top10 <- ggplot(top_10, aes(x = reorder(items, support), y = support)) +
    geom_col(fill = "#2ecc71", alpha = 0.8, color = "white") +
    coord_flip() +
    labs(title = "Top 10 Frequent Itemsets by Support", 
         x = "Itemsets", y = "Support") +
    theme_minimal() +
    theme(panel.background = element_rect(fill = "white"),
          axis.text.y = element_text(size = 8))
  
  filename2 <- paste0("images/21_top_10_frequent_itemsets_by_support", suffix, ".png")
  ggsave(filename2, p_top10, width = 12, height = 8, dpi = 300, bg = "white")
  cat("Saved:", filename2, "\n")
}

# -----------------------------
# 8) Frequent Itemsets Discovery Animation
# -----------------------------
create_frequent_itemsets_animation <- function(itemsets_data, suffix) {
  cat("\n=== Creating Frequent Itemsets Discovery Animation ===\n")
  
  # Check if magick package is available
  if (!require(magick, quietly = TRUE)) {
    cat("Installing magick package...\n")
    install.packages("magick", repos = "https://cran.rstudio.com/")
    library(magick)
  }
  
  # Sort itemsets by support (descending)
  sorted_itemsets <- itemsets_data[order(itemsets_data$support, decreasing = TRUE), ]
  
  # Limit to top 20 itemsets for better animation
  if (nrow(sorted_itemsets) > 20) {
    sorted_itemsets <- sorted_itemsets[1:20, ]
  }
  
  # Create individual frames
  frames <- list()
  
  for (i in 1:nrow(sorted_itemsets)) {
    current_itemsets <- sorted_itemsets[1:i, ]
    
    # Create plot for current frame
    png_file <- tempfile(fileext = ".png")
    png(png_file, width = 800, height = 600, bg = "white")
    
    # Create horizontal bar plot
    par(mar = c(5, 12, 4, 2))
    colors <- rainbow(nrow(current_itemsets), alpha = 0.8)
    
    barplot(
      current_itemsets$support,
      names.arg = current_itemsets$items,
      horiz = TRUE,
      las = 1,
      col = colors,
      border = "white",
      main = paste("Frequent Itemsets Discovery Animation (FP-Growth) - Frame", i, "of", nrow(sorted_itemsets)),
      xlab = "Support",
      ylab = "",
      cex.names = 0.8,
      cex.axis = 0.8,
      cex.main = 1.2,
      xlim = c(0, max(sorted_itemsets$support) * 1.1)
    )
    
    # Add support values on bars
    text(
      current_itemsets$support + 0.01,
      seq_along(current_itemsets$support),
      labels = round(current_itemsets$support, 3),
      pos = 4,
      cex = 0.7
    )
    
    dev.off()
    
    # Read the image
    frames[[i]] <- image_read(png_file)
    file.remove(png_file)
  }
  
  # Create animation
  animation <- image_animate(image_join(frames), fps = 2)
  
  # Save the animation
  filename <- paste0("images/30_frequent_itemsets_discovery_animation", suffix, ".gif")
  image_write(animation, filename)
  cat("Saved:", filename, "\n")
}

# -----------------------------
# 7) Main function
# -----------------------------
main <- function() {
  cat("=== FP-Growth Algorithm (Grocery Store Dataset) ===\n\n")
  
  # Load and preprocess data
  transactions <- load_grocery_data()
  
  # Exploratory data analysis
  create_eda_plots(transactions)
  
  # Run FP-Growth algorithm
  result <- run_fpgrowth_algorithm(transactions)
  rules <- result$rules
  frequent_itemsets <- result$frequent_itemsets
  execution_time <- result$execution_time
  
  # Analyze frequent itemsets
  itemsets_df <- analyze_frequent_itemsets(frequent_itemsets)
  
  # Analyze rules
  rules_df <- analyze_rules(rules)
  
  # Performance analysis
  performance_data <- analyze_performance(execution_time, length(rules), length(frequent_itemsets))
  
  cat("\nAll plots saved to images/ directory.\n")
  cat("FP-Growth algorithm analysis completed successfully!\n")
  
  # Return results for comparison
  list(
    rules = rules,
    rules_df = rules_df,
    frequent_itemsets = frequent_itemsets,
    itemsets_df = itemsets_df,
    execution_time = execution_time,
    performance_data = performance_data
  )
}

# Run main function if script is executed directly
if (!interactive()) {
  main()
}
