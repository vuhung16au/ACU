# Bias Detection Methodology

## Overview

This document outlines comprehensive methodologies for detecting algorithmic bias in machine learning systems, with specific focus on Australian compliance requirements and statistical rigor.

## 🎯 Detection Objectives

### Primary Goals
- Identify statistical disparities across protected groups
- Detect direct and indirect discrimination patterns
- Assess intersectional bias effects
- Evaluate proxy variable correlations
- Measure fairness metrics compliance

### Secondary Goals
- Monitor bias drift over time
- Assess model performance disparities
- Evaluate decision consistency
- Review data quality impacts
- Validate mitigation effectiveness

## 📊 Statistical Methods

### 1. Disparity Analysis

#### Demographic Parity Testing
```r
# Calculate approval rates by demographic groups
demographic_parity <- function(data, outcome_var, group_var) {
  data %>%
    group_by(!!sym(group_var)) %>%
    summarise(
      total = n(),
      approved = sum(!!sym(outcome_var) == "YES"),
      approval_rate = mean(!!sym(outcome_var) == "YES"),
      .groups = 'drop'
    )
}

# Statistical significance testing
test_demographic_parity <- function(data, outcome_var, group_var) {
  # Chi-square test for independence
  contingency_table <- table(data[[group_var]], data[[outcome_var]])
  chi_square_test <- chisq.test(contingency_table)
  
  # Effect size calculation (Cramér's V)
  cramers_v <- sqrt(chi_square_test$statistic / 
                    (sum(contingency_table) * (min(dim(contingency_table)) - 1)))
  
  return(list(
    chi_square = chi_square_test,
    cramers_v = cramers_v,
    interpretation = ifelse(cramers_v > 0.3, "Large effect", 
                          ifelse(cramers_v > 0.1, "Medium effect", "Small effect"))
  ))
}
```

#### Equalized Odds Assessment
```r
# Calculate true positive and false positive rates by group
equalized_odds <- function(predictions, actual, groups) {
  results <- data.frame()
  
  for(group in unique(groups)) {
    group_mask <- groups == group
    group_pred <- predictions[group_mask]
    group_actual <- actual[group_mask]
    
    tp <- sum(group_pred == 1 & group_actual == 1)
    fp <- sum(group_pred == 1 & group_actual == 0)
    fn <- sum(group_pred == 0 & group_actual == 1)
    tn <- sum(group_pred == 0 & group_actual == 0)
    
    tpr <- tp / (tp + fn)  # True Positive Rate (Sensitivity)
    fpr <- fp / (fp + tn)  # False Positive Rate (1 - Specificity)
    
    results <- rbind(results, data.frame(
      group = group,
      tpr = tpr,
      fpr = fpr,
      group_size = sum(group_mask)
    ))
  }
  
  return(results)
}

# Test for equalized odds violations
test_equalized_odds <- function(equalized_odds_results) {
  # ANOVA test for TPR differences
  tpr_test <- aov(tpr ~ group, data = equalized_odds_results)
  fpr_test <- aov(fpr ~ group, data = equalized_odds_results)
  
  return(list(
    tpr_anova = summary(tpr_test),
    fpr_anova = summary(fpr_test),
    violation = any(summary(tpr_test)[[1]][["Pr(>F)"]] < 0.05) ||
               any(summary(fpr_test)[[1]][["Pr(>F)"]] < 0.05)
  ))
}
```

### 2. Intersectional Bias Analysis

#### Multi-dimensional Disparity Assessment
```r
# Analyze bias across multiple protected attributes
intersectional_analysis <- function(data, outcome_var, group_vars) {
  # Create intersectional groups
  data$intersectional_group <- do.call(paste, data[group_vars])
  
  # Calculate approval rates for each intersectional group
  intersectional_results <- data %>%
    group_by(intersectional_group) %>%
    summarise(
      total = n(),
      approved = sum(!!sym(outcome_var) == "YES"),
      approval_rate = mean(!!sym(outcome_var) == "YES"),
      .groups = 'drop'
    ) %>%
    filter(total >= 10)  # Minimum group size for statistical validity
  
  # Identify most and least advantaged groups
  most_advantaged <- intersectional_results[which.max(intersectional_results$approval_rate), ]
  least_advantaged <- intersectional_results[which.min(intersectional_results$approval_rate), ]
  
  # Calculate disparity ratio
  disparity_ratio <- most_advantaged$approval_rate / least_advantaged$approval_rate
  
  return(list(
    results = intersectional_results,
    most_advantaged = most_advantaged,
    least_advantaged = least_advantaged,
    disparity_ratio = disparity_ratio,
    significant_disparity = disparity_ratio > 1.2  # 20% threshold
  ))
}
```

### 3. Proxy Variable Detection

#### Correlation Analysis
```r
# Detect proxy variables correlated with protected attributes
detect_proxy_variables <- function(data, protected_attrs, candidate_proxies) {
  proxy_results <- data.frame()
  
  for(proxy in candidate_proxies) {
    for(protected in protected_attrs) {
      # Calculate correlation for numeric variables
      if(is.numeric(data[[proxy]]) && is.numeric(data[[protected]])) {
        correlation <- cor(data[[proxy]], data[[protected]], use = "complete.obs")
      } else {
        # Calculate Cramér's V for categorical variables
        contingency_table <- table(data[[proxy]], data[[protected]])
        chi_square_test <- chisq.test(contingency_table)
        correlation <- sqrt(chi_square_test$statistic / 
                           (sum(contingency_table) * (min(dim(contingency_table)) - 1)))
      }
      
      proxy_results <- rbind(proxy_results, data.frame(
        proxy_variable = proxy,
        protected_attribute = protected,
        correlation = correlation,
        risk_level = ifelse(abs(correlation) > 0.3, "HIGH",
                           ifelse(abs(correlation) > 0.1, "MEDIUM", "LOW"))
      ))
    }
  }
  
  return(proxy_results)
}

# Identify high-risk proxy variables
identify_high_risk_proxies <- function(proxy_results, threshold = 0.3) {
  high_risk <- proxy_results[abs(proxy_results$correlation) > threshold, ]
  return(high_risk)
}
```

### 4. Model Performance Disparity

#### Group-Specific Performance Metrics
```r
# Calculate performance metrics by demographic group
group_performance_metrics <- function(predictions, actual, groups) {
  results <- data.frame()
  
  for(group in unique(groups)) {
    group_mask <- groups == group
    group_pred <- predictions[group_mask]
    group_actual <- actual[group_mask]
    
    # Confusion matrix components
    tp <- sum(group_pred == 1 & group_actual == 1)
    fp <- sum(group_pred == 1 & group_actual == 0)
    fn <- sum(group_pred == 0 & group_actual == 1)
    tn <- sum(group_pred == 0 & group_actual == 0)
    
    # Calculate metrics
    accuracy <- (tp + tn) / (tp + fp + fn + tn)
    precision <- ifelse(tp + fp > 0, tp / (tp + fp), 0)
    recall <- ifelse(tp + fn > 0, tp / (tp + fn), 0)
    f1_score <- ifelse(precision + recall > 0, 2 * precision * recall / (precision + recall), 0)
    specificity <- ifelse(tn + fp > 0, tn / (tn + fp), 0)
    
    results <- rbind(results, data.frame(
      group = group,
      group_size = sum(group_mask),
      accuracy = accuracy,
      precision = precision,
      recall = recall,
      f1_score = f1_score,
      specificity = specificity,
      true_positive_rate = recall,
      false_positive_rate = 1 - specificity
    ))
  }
  
  return(results)
}

# Test for performance disparities
test_performance_disparity <- function(performance_results, metric = "recall") {
  # ANOVA test for metric differences across groups
  formula <- as.formula(paste(metric, "~ group"))
  anova_test <- aov(formula, data = performance_results)
  
  # Post-hoc pairwise comparisons
  pairwise_test <- pairwise.t.test(performance_results[[metric]], 
                                  performance_results$group, 
                                  p.adjust.method = "bonferroni")
  
  return(list(
    anova = summary(anova_test),
    pairwise = pairwise_test,
    significant_disparity = any(summary(anova_test)[[1]][["Pr(>F)"]] < 0.05)
  ))
}
```

## 🔍 Detection Workflows

### 1. Comprehensive Bias Assessment

```r
# Complete bias detection workflow
comprehensive_bias_assessment <- function(data, outcome_var, protected_attrs, 
                                        candidate_proxies, model = NULL) {
  results <- list()
  
  # 1. Demographic parity analysis
  results$demographic_parity <- list()
  for(attr in protected_attrs) {
    results$demographic_parity[[attr]] <- demographic_parity(data, outcome_var, attr)
  }
  
  # 2. Statistical significance testing
  results$significance_tests <- list()
  for(attr in protected_attrs) {
    results$significance_tests[[attr]] <- test_demographic_parity(data, outcome_var, attr)
  }
  
  # 3. Intersectional analysis
  if(length(protected_attrs) > 1) {
    results$intersectional <- intersectional_analysis(data, outcome_var, protected_attrs)
  }
  
  # 4. Proxy variable detection
  results$proxy_analysis <- detect_proxy_variables(data, protected_attrs, candidate_proxies)
  results$high_risk_proxies <- identify_high_risk_proxies(results$proxy_analysis)
  
  # 5. Model performance analysis (if model provided)
  if(!is.null(model)) {
    predictions <- predict(model, data, type = "response")
    predicted_class <- ifelse(predictions > 0.5, 1, 0)
    
    results$performance_analysis <- list()
    for(attr in protected_attrs) {
      results$performance_analysis[[attr]] <- group_performance_metrics(
        predicted_class, data[[outcome_var]], data[[attr]]
      )
    }
  }
  
  return(results)
}
```

### 2. Automated Bias Monitoring

```r
# Automated bias monitoring system
automated_bias_monitor <- function(data, outcome_var, protected_attrs, 
                                  monitoring_period = "daily") {
  
  # Calculate current bias metrics
  current_bias <- comprehensive_bias_assessment(data, outcome_var, protected_attrs)
  
  # Store historical data (in production, this would be in a database)
  if(!exists("bias_history")) {
    bias_history <<- list()
  }
  
  timestamp <- Sys.time()
  bias_history[[as.character(timestamp)]] <<- current_bias
  
  # Detect bias drift
  if(length(bias_history) > 1) {
    bias_drift <- detect_bias_drift(bias_history)
    current_bias$bias_drift <- bias_drift
  }
  
  # Generate alerts for significant changes
  alerts <- generate_bias_alerts(current_bias)
  
  return(list(
    current_bias = current_bias,
    alerts = alerts,
    timestamp = timestamp
  ))
}

# Detect bias drift over time
detect_bias_drift <- function(bias_history) {
  if(length(bias_history) < 2) return(NULL)
  
  # Compare most recent with previous period
  recent <- bias_history[[length(bias_history)]]
  previous <- bias_history[[length(bias_history) - 1]]
  
  drift_analysis <- list()
  
  # Analyze demographic parity drift
  for(attr in names(recent$demographic_parity)) {
    recent_rates <- recent$demographic_parity[[attr]]$approval_rate
    previous_rates <- previous$demographic_parity[[attr]]$approval_rate
    
    drift_analysis[[attr]] <- data.frame(
      group = recent$demographic_parity[[attr]]$group,
      recent_rate = recent_rates,
      previous_rate = previous_rates,
      change = recent_rates - previous_rates,
      significant_change = abs(recent_rates - previous_rates) > 0.05
    )
  }
  
  return(drift_analysis)
}

# Generate bias alerts
generate_bias_alerts <- function(bias_assessment) {
  alerts <- list()
  
  # Check for high disparity ratios
  if(!is.null(bias_assessment$intersectional)) {
    if(bias_assessment$intersectional$disparity_ratio > 1.5) {
      alerts$high_disparity <- "CRITICAL: Disparity ratio exceeds 1.5"
    }
  }
  
  # Check for high-risk proxy variables
  if(nrow(bias_assessment$high_risk_proxies) > 0) {
    alerts$proxy_risk <- paste("WARNING:", nrow(bias_assessment$high_risk_proxies), 
                              "high-risk proxy variables detected")
  }
  
  # Check for significant performance disparities
  if(!is.null(bias_assessment$performance_analysis)) {
    for(attr in names(bias_assessment$performance_analysis)) {
      performance <- bias_assessment$performance_analysis[[attr]]
      recall_range <- range(performance$recall)
      if(recall_range[2] - recall_range[1] > 0.2) {
        alerts$performance_disparity <- paste("WARNING: Large performance disparity in", attr)
      }
    }
  }
  
  return(alerts)
}
```

## 📈 Visualization and Reporting

### 1. Bias Visualization Functions

```r
# Create comprehensive bias visualization
create_bias_dashboard <- function(bias_assessment, data, outcome_var, protected_attrs) {
  
  plots <- list()
  
  # 1. Demographic parity visualization
  for(attr in protected_attrs) {
    parity_data <- bias_assessment$demographic_parity[[attr]]
    
    plots[[paste0("parity_", attr)]] <- ggplot(parity_data, 
                                              aes_string(x = attr, y = "approval_rate", 
                                                       fill = attr)) +
      geom_bar(stat = "identity", alpha = 0.7) +
      geom_text(aes(label = paste0(round(approval_rate * 100, 1), "%")), 
                vjust = -0.5, size = 4, fontface = "bold") +
      labs(title = paste("Approval Rates by", attr),
           subtitle = "Demographic Parity Analysis",
           x = attr, y = "Approval Rate",
           caption = "Higher bars indicate potential bias") +
      theme_minimal() +
      theme(legend.position = "none")
  }
  
  # 2. Performance disparity visualization
  if(!is.null(bias_assessment$performance_analysis)) {
    for(attr in names(bias_assessment$performance_analysis)) {
      perf_data <- bias_assessment$performance_analysis[[attr]]
      
      plots[[paste0("performance_", attr)]] <- ggplot(perf_data, 
                                                      aes_string(x = "group", y = "recall", 
                                                               fill = "group")) +
        geom_bar(stat = "identity", alpha = 0.7) +
        geom_text(aes(label = paste0(round(recall, 3))), 
                  vjust = -0.5, size = 4, fontface = "bold") +
        labs(title = paste("Model Recall by", attr),
             subtitle = "Performance Disparity Analysis",
             x = attr, y = "Recall (True Positive Rate)",
             caption = "Lower recall indicates higher false negative rate") +
        theme_minimal() +
        theme(legend.position = "none")
    }
  }
  
  # 3. Proxy variable risk heatmap
  if(nrow(bias_assessment$proxy_analysis) > 0) {
    proxy_heatmap <- ggplot(bias_assessment$proxy_analysis, 
                           aes(x = protected_attribute, y = proxy_variable, 
                               fill = abs(correlation))) +
      geom_tile() +
      scale_fill_gradient(low = "white", high = "red", 
                         name = "Correlation\nStrength") +
      labs(title = "Proxy Variable Risk Assessment",
           subtitle = "Correlation with Protected Attributes",
           x = "Protected Attribute", y = "Proxy Variable") +
      theme_minimal() +
      theme(axis.text.x = element_text(angle = 45, hjust = 1))
    
    plots$proxy_heatmap <- proxy_heatmap
  }
  
  return(plots)
}
```

### 2. Automated Reporting

```r
# Generate comprehensive bias report
generate_bias_report <- function(bias_assessment, output_file = "bias_report.html") {
  
  # Create HTML report
  html_content <- paste0(
    "<!DOCTYPE html>
    <html>
    <head>
      <title>Algorithmic Bias Assessment Report</title>
      <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .header { background-color: #f0f0f0; padding: 20px; border-radius: 5px; }
        .section { margin: 20px 0; padding: 15px; border-left: 4px solid #007acc; }
        .alert { background-color: #ffebee; padding: 10px; border-radius: 3px; margin: 10px 0; }
        .success { background-color: #e8f5e8; padding: 10px; border-radius: 3px; margin: 10px 0; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
      </style>
    </head>
    <body>
      <div class='header'>
        <h1>Algorithmic Bias Assessment Report</h1>
        <p>Generated on: ", Sys.time(), "</p>
      </div>"
  )
  
  # Add demographic parity results
  html_content <- paste0(html_content, "
    <div class='section'>
      <h2>Demographic Parity Analysis</h2>")
  
  for(attr in names(bias_assessment$demographic_parity)) {
    parity_data <- bias_assessment$demographic_parity[[attr]]
    html_content <- paste0(html_content, "
      <h3>", attr, "</h3>
      <table>
        <tr><th>Group</th><th>Total</th><th>Approved</th><th>Approval Rate</th></tr>")
    
    for(i in 1:nrow(parity_data)) {
      html_content <- paste0(html_content, "
        <tr>
          <td>", parity_data$group[i], "</td>
          <td>", parity_data$total[i], "</td>
          <td>", parity_data$approved[i], "</td>
          <td>", round(parity_data$approval_rate[i] * 100, 1), "%</td>
        </tr>")
    }
    
    html_content <- paste0(html_content, "</table>")
  }
  
  html_content <- paste0(html_content, "</div>")
  
  # Add proxy variable analysis
  if(nrow(bias_assessment$high_risk_proxies) > 0) {
    html_content <- paste0(html_content, "
      <div class='section'>
        <h2>High-Risk Proxy Variables</h2>
        <div class='alert'>
          <strong>WARNING:</strong> ", nrow(bias_assessment$high_risk_proxies), 
          " high-risk proxy variables detected
        </div>
        <table>
          <tr><th>Proxy Variable</th><th>Protected Attribute</th><th>Correlation</th><th>Risk Level</th></tr>")
    
    for(i in 1:nrow(bias_assessment$high_risk_proxies)) {
      proxy <- bias_assessment$high_risk_proxies[i, ]
      html_content <- paste0(html_content, "
        <tr>
          <td>", proxy$proxy_variable, "</td>
          <td>", proxy$protected_attribute, "</td>
          <td>", round(proxy$correlation, 3), "</td>
          <td>", proxy$risk_level, "</td>
        </tr>")
    }
    
    html_content <- paste0(html_content, "</table></div>")
  }
  
  # Add recommendations
  html_content <- paste0(html_content, "
    <div class='section'>
      <h2>Recommendations</h2>
      <ul>
        <li>Remove high-risk proxy variables from model training</li>
        <li>Implement fairness constraints in model development</li>
        <li>Establish regular bias monitoring procedures</li>
        <li>Conduct comprehensive bias impact assessments</li>
        <li>Provide staff training on algorithmic fairness</li>
      </ul>
    </div>
  </body>
  </html>")
  
  # Write HTML file
  writeLines(html_content, output_file)
  
  return(output_file)
}
```

## 🚨 Alert Thresholds and Guidelines

### Critical Alerts (Immediate Action)
- Disparity ratio > 1.5 (50% difference in approval rates)
- Proxy variable correlation > 0.5
- Performance disparity > 0.3 (30% difference in recall)
- Statistical significance p < 0.001

### Warning Alerts (Investigation Required)
- Disparity ratio > 1.2 (20% difference in approval rates)
- Proxy variable correlation > 0.3
- Performance disparity > 0.2 (20% difference in recall)
- Statistical significance p < 0.05

### Monitoring Thresholds (Regular Review)
- Disparity ratio > 1.1 (10% difference in approval rates)
- Proxy variable correlation > 0.1
- Performance disparity > 0.1 (10% difference in recall)
- Any statistically significant differences

---

*This bias detection methodology provides comprehensive tools for identifying and monitoring algorithmic bias in compliance with Australian legal requirements. Regular application of these methods is essential for maintaining ethical AI systems.*
