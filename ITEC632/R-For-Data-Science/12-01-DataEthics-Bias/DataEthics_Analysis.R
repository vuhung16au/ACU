# Data Ethics and Bias Analysis - Loan Application Dataset
# Author: R for Data Science Course
# Purpose: Demonstrate algorithmic bias detection and mitigation techniques
# Australian Compliance Focus: Privacy Act 1988, Anti-Discrimination Acts

# =============================================================================
# SECTION 1: DATA AUDIT & BIAS DETECTION
# =============================================================================

# Load required libraries
library(tidyverse)
library(ggplot2)
library(dplyr)
library(caret)
library(yardstick)
library(rpart)
library(gridExtra)
library(VIM)  # For missing value visualization

# Set working directory and load data
setwd("/Users/vuhung/00.Work/02.ACU/github/ITEC632/R-For-Data-Science/12-01-DataEthics-Bias")
loan_data <- read.csv("loan_data_sim.csv", stringsAsFactors = TRUE)

# Step 1: Initial Data Exploration and Structure
cat("=== DATA STRUCTURE AND OVERVIEW ===\n")
str(loan_data)
summary(loan_data)

# Check for missing values
cat("\n=== MISSING VALUES ANALYSIS ===\n")
missing_summary <- loan_data %>%
  summarise_all(~sum(is.na(.))) %>%
  gather(key = "Variable", value = "Missing_Count") %>%
  arrange(desc(Missing_Count))

print(missing_summary)

# Visualize missing values pattern
if(sum(is.na(loan_data)) > 0) {
  png("images/missing_values_pattern.png", width = 800, height = 600)
  aggr(loan_data, col = c('navyblue', 'red'), numbers = TRUE, sortVars = TRUE)
  dev.off()
}

# Step 2: Disparity Analysis - Calculate approval rates by protected groups
cat("\n=== DISPARITY ANALYSIS ===\n")

# Gender-based disparity
gender_disparity <- loan_data %>%
  group_by(gender) %>%
  summarise(
    total_applications = n(),
    approved_count = sum(loan_approved == "YES"),
    approval_rate = mean(loan_approved == "YES") * 100,
    .groups = 'drop'
  )

cat("Gender-based Approval Rates:\n")
print(gender_disparity)

# Age-based disparity
age_disparity <- loan_data %>%
  group_by(age_group) %>%
  summarise(
    total_applications = n(),
    approved_count = sum(loan_approved == "YES"),
    approval_rate = mean(loan_approved == "YES") * 100,
    .groups = 'drop'
  )

cat("\nAge-based Approval Rates:\n")
print(age_disparity)

# Ethnicity-based disparity
ethnicity_disparity <- loan_data %>%
  group_by(ethnicity) %>%
  summarise(
    total_applications = n(),
    approved_count = sum(loan_approved == "YES"),
    approval_rate = mean(loan_approved == "YES") * 100,
    .groups = 'drop'
  )

cat("\nEthnicity-based Approval Rates:\n")
print(ethnicity_disparity)

# Postcode-based disparity (proxy discrimination)
postcode_disparity <- loan_data %>%
  group_by(postcode) %>%
  summarise(
    total_applications = n(),
    approved_count = sum(loan_approved == "YES"),
    approval_rate = mean(loan_approved == "YES") * 100,
    .groups = 'drop'
  )

cat("\nPostcode-based Approval Rates (Proxy Discrimination):\n")
print(postcode_disparity)

# Step 3: Visualize Disparities
cat("\n=== CREATING DISPARITY VISUALIZATIONS ===\n")

# Gender disparity visualization
p1 <- ggplot(gender_disparity, aes(x = gender, y = approval_rate, fill = gender)) +
  geom_bar(stat = "identity", alpha = 0.7) +
  geom_text(aes(label = paste0(round(approval_rate, 1), "%")), 
            vjust = -0.5, size = 4, fontface = "bold") +
  labs(title = "Loan Approval Rates by Gender",
       subtitle = "Demonstrating Gender-based Disparity",
       x = "Gender", y = "Approval Rate (%)",
       caption = "Data shows potential gender discrimination") +
  theme_minimal() +
  theme(legend.position = "none",
        plot.title = element_text(size = 14, face = "bold"),
        plot.subtitle = element_text(size = 12, color = "red"))

# Age disparity visualization
p2 <- ggplot(age_disparity, aes(x = age_group, y = approval_rate, fill = age_group)) +
  geom_bar(stat = "identity", alpha = 0.7) +
  geom_text(aes(label = paste0(round(approval_rate, 1), "%")), 
            vjust = -0.5, size = 3.5, fontface = "bold") +
  labs(title = "Loan Approval Rates by Age Group",
       subtitle = "Demonstrating Age-based Disparity",
       x = "Age Group", y = "Approval Rate (%)",
       caption = "Data shows potential age discrimination") +
  theme_minimal() +
  theme(legend.position = "none",
        plot.title = element_text(size = 14, face = "bold"),
        plot.subtitle = element_text(size = 12, color = "red"),
        axis.text.x = element_text(angle = 45, hjust = 1))

# Ethnicity disparity visualization
p3 <- ggplot(ethnicity_disparity, aes(x = ethnicity, y = approval_rate, fill = ethnicity)) +
  geom_bar(stat = "identity", alpha = 0.7) +
  geom_text(aes(label = paste0(round(approval_rate, 1), "%")), 
            vjust = -0.5, size = 3.5, fontface = "bold") +
  labs(title = "Loan Approval Rates by Ethnicity",
       subtitle = "Demonstrating Racial/Ethnic Disparity",
       x = "Ethnicity", y = "Approval Rate (%)",
       caption = "Data shows potential racial discrimination") +
  theme_minimal() +
  theme(legend.position = "none",
        plot.title = element_text(size = 14, face = "bold"),
        plot.subtitle = element_text(size = 12, color = "red"),
        axis.text.x = element_text(angle = 45, hjust = 1))

# Save individual plots
ggsave("images/gender_disparity.png", p1, width = 8, height = 6, dpi = 300)
ggsave("images/age_disparity.png", p2, width = 8, height = 6, dpi = 300)
ggsave("images/ethnicity_disparity.png", p3, width = 8, height = 6, dpi = 300)

# Combined disparity plot
combined_disparity <- grid.arrange(p1, p2, p3, ncol = 1)
ggsave("images/combined_disparity_analysis.png", combined_disparity, 
       width = 10, height = 15, dpi = 300)

# Intersectional Analysis (Gender + Ethnicity)
cat("\n=== INTERSECTIONAL BIAS ANALYSIS ===\n")
intersectional_disparity <- loan_data %>%
  group_by(gender, ethnicity) %>%
  summarise(
    total_applications = n(),
    approved_count = sum(loan_approved == "YES"),
    approval_rate = mean(loan_approved == "YES") * 100,
    .groups = 'drop'
  ) %>%
  filter(total_applications >= 10)  # Filter for statistical significance

cat("Intersectional Analysis (Gender + Ethnicity):\n")
print(intersectional_disparity)

# Intersectional visualization
p4 <- ggplot(intersectional_disparity, 
             aes(x = ethnicity, y = approval_rate, fill = gender)) +
  geom_bar(stat = "identity", position = "dodge", alpha = 0.7) +
  geom_text(aes(label = paste0(round(approval_rate, 1), "%")), 
            position = position_dodge(width = 0.9), 
            vjust = -0.5, size = 3, fontface = "bold") +
  labs(title = "Intersectional Bias Analysis",
       subtitle = "Loan Approval Rates by Gender and Ethnicity",
       x = "Ethnicity", y = "Approval Rate (%)",
       fill = "Gender",
       caption = "Shows compounded discrimination effects") +
  theme_minimal() +
  theme(axis.text.x = element_text(angle = 45, hjust = 1),
        plot.title = element_text(size = 14, face = "bold"),
        plot.subtitle = element_text(size = 12, color = "red"))

ggsave("images/intersectional_bias.png", p4, width = 10, height = 6, dpi = 300)

# =============================================================================
# SECTION 2: ALGORITHMIC BIAS DEMONSTRATION
# =============================================================================

cat("\n=== ALGORITHMIC BIAS DEMONSTRATION ===\n")

# Prepare data for modeling
# Convert target variable to binary
loan_data$loan_approved_binary <- ifelse(loan_data$loan_approved == "YES", 1, 0)

# Create training and testing sets
set.seed(123)
trainIndex <- createDataPartition(loan_data$loan_approved_binary, p = 0.7, list = FALSE)
train_data <- loan_data[trainIndex, ]
test_data <- loan_data[-trainIndex, ]

cat("Training set size:", nrow(train_data), "\n")
cat("Testing set size:", nrow(test_data), "\n")

# Step 4: Train a biased model (including protected attributes)
cat("\n=== TRAINING BIASED MODEL ===\n")

# Model 1: Logistic Regression with protected attributes (BIASED)
biased_model <- glm(loan_approved_binary ~ 
                   gender + age_group + ethnicity + postcode +
                   annual_income + credit_score + employment_years + 
                   debt_to_income_ratio + loan_amount + loan_purpose + 
                   previous_defaults + education_level + marital_status + 
                   occupation_category,
                   data = train_data, family = binomial)

# Make predictions
biased_predictions <- predict(biased_model, newdata = test_data, type = "response")
biased_predicted_class <- ifelse(biased_predictions > 0.5, 1, 0)

# Overall model performance
cat("Biased Model Performance (Overall):\n")
confusion_matrix_biased <- confusionMatrix(factor(biased_predicted_class), 
                                         factor(test_data$loan_approved_binary))
print(confusion_matrix_biased)

# Step 5: Measure model performance by protected groups
cat("\n=== MODEL PERFORMANCE BY PROTECTED GROUPS ===\n")

# Function to calculate group-specific metrics
calculate_group_metrics <- function(predictions, actual, group_var, group_value) {
  group_mask <- test_data[[group_var]] == group_value & !is.na(test_data[[group_var]])
  if(sum(group_mask, na.rm = TRUE) == 0) return(NULL)
  
  group_pred <- predictions[group_mask]
  group_actual <- actual[group_mask]
  
  # Calculate metrics
  tp <- sum(group_pred == 1 & group_actual == 1)
  fp <- sum(group_pred == 1 & group_actual == 0)
  fn <- sum(group_pred == 0 & group_actual == 1)
  tn <- sum(group_pred == 0 & group_actual == 0)
  
  precision <- ifelse(tp + fp > 0, tp / (tp + fp), 0)
  recall <- ifelse(tp + fn > 0, tp / (tp + fn), 0)
  f1_score <- ifelse(precision + recall > 0, 2 * precision * recall / (precision + recall), 0)
  accuracy <- (tp + tn) / (tp + fp + fn + tn)
  
  return(data.frame(
    group = group_value,
    group_size = sum(group_mask),
    accuracy = accuracy,
    precision = precision,
    recall = recall,
    f1_score = f1_score,
    approval_rate = mean(group_actual)
  ))
}

# Gender-based model performance
cat("Model Performance by Gender:\n")
gender_performance <- map_dfr(unique(test_data$gender), function(g) {
  calculate_group_metrics(biased_predicted_class, test_data$loan_approved_binary, "gender", g)
})
print(gender_performance)

# Ethnicity-based model performance
cat("\nModel Performance by Ethnicity:\n")
ethnicity_performance <- map_dfr(unique(test_data$ethnicity[!is.na(test_data$ethnicity)]), function(e) {
  calculate_group_metrics(biased_predicted_class, test_data$loan_approved_binary, "ethnicity", e)
})
print(ethnicity_performance)

# Visualize performance disparities
p5 <- ggplot(gender_performance, aes(x = group, y = recall, fill = group)) +
  geom_bar(stat = "identity", alpha = 0.7) +
  geom_text(aes(label = paste0(round(recall, 3))), 
            vjust = -0.5, size = 4, fontface = "bold") +
  labs(title = "Model Recall by Gender",
       subtitle = "Shows differential performance across groups",
       x = "Gender", y = "Recall (True Positive Rate)",
       caption = "Lower recall indicates higher false negative rate") +
  theme_minimal() +
  theme(legend.position = "none",
        plot.title = element_text(size = 14, face = "bold"),
        plot.subtitle = element_text(size = 12, color = "red"))

ggsave("images/model_performance_gender.png", p5, width = 8, height = 6, dpi = 300)

# =============================================================================
# SECTION 3: MITIGATION & COMPLIANCE CHECK
# =============================================================================

cat("\n=== BIAS MITIGATION TECHNIQUES ===\n")

# Step 6: Mitigation Technique 1 - Remove Protected Attributes
cat("\n=== MITIGATION 1: REMOVING PROTECTED ATTRIBUTES ===\n")

# Train model without protected attributes
fair_model <- glm(loan_approved_binary ~ 
                 annual_income + credit_score + employment_years + 
                 debt_to_income_ratio + loan_amount + loan_purpose + 
                 previous_defaults,
                 data = train_data, family = binomial)

# Make predictions
fair_predictions <- predict(fair_model, newdata = test_data, type = "response")
fair_predicted_class <- ifelse(fair_predictions > 0.5, 1, 0)

# Compare performance
cat("Fair Model Performance (Overall):\n")
confusion_matrix_fair <- confusionMatrix(factor(fair_predicted_class), 
                                        factor(test_data$loan_approved_binary))
print(confusion_matrix_fair)

# Calculate fairness metrics
cat("\n=== FAIRNESS METRICS COMPARISON ===\n")

# Demographic Parity (Equal approval rates)
cat("Demographic Parity Analysis:\n")
demographic_parity_biased <- test_data %>%
  mutate(predicted_approved = biased_predicted_class) %>%
  group_by(gender) %>%
  summarise(approval_rate = mean(predicted_approved), .groups = 'drop')

demographic_parity_fair <- test_data %>%
  mutate(predicted_approved = fair_predicted_class) %>%
  group_by(gender) %>%
  summarise(approval_rate = mean(predicted_approved), .groups = 'drop')

cat("Biased Model - Gender Approval Rates:\n")
print(demographic_parity_biased)
cat("Fair Model - Gender Approval Rates:\n")
print(demographic_parity_fair)

# Equalized Odds (Equal true positive and false positive rates)
cat("\nEqualized Odds Analysis:\n")
equalized_odds_analysis <- test_data %>%
  mutate(
    biased_pred = biased_predicted_class,
    fair_pred = fair_predicted_class,
    actual = loan_approved_binary
  ) %>%
  group_by(gender) %>%
  summarise(
    biased_tpr = sum(biased_pred == 1 & actual == 1) / sum(actual == 1),
    biased_fpr = sum(biased_pred == 1 & actual == 0) / sum(actual == 0),
    fair_tpr = sum(fair_pred == 1 & actual == 1) / sum(actual == 1),
    fair_fpr = sum(fair_pred == 1 & actual == 0) / sum(actual == 0),
    .groups = 'drop'
  )

print(equalized_odds_analysis)

# Step 7: Australian Compliance Assessment
cat("\n=== AUSTRALIAN COMPLIANCE ASSESSMENT ===\n")

cat("LEGAL COMPLIANCE ANALYSIS:\n")
cat("==========================\n")
cat("1. Privacy Act 1988 (Australian Privacy Principles):\n")
cat("   - Collection of sensitive information (gender, ethnicity) requires explicit consent\n")
cat("   - Use of personal information must be necessary and directly related to purpose\n")
cat("   - Data minimization principle violated by collecting unnecessary protected attributes\n\n")

cat("2. Anti-Discrimination Acts (Federal and State):\n")
cat("   - Direct discrimination: Using gender/ethnicity in decision-making is prohibited\n")
cat("   - Indirect discrimination: Proxy variables creating disparate impact are unlawful\n")
cat("   - Equal opportunity requirements violated by biased approval rates\n\n")

cat("3. Australian Human Rights Commission Act 1986:\n")
cat("   - Right to equality before the law violated by differential treatment\n")
cat("   - Right to non-discrimination in employment/services violated\n\n")

cat("4. Fair Lending Guidelines:\n")
cat("   - Responsible lending requires assessment based on creditworthiness alone\n")
cat("   - Transparency requirements mandate explainable, non-discriminatory decisions\n\n")

# Generate compliance report
compliance_findings <- data.frame(
  Issue = c("Gender Discrimination", "Age Discrimination", "Racial Discrimination", 
           "Proxy Discrimination", "Lack of Transparency", "Data Minimization Violation"),
  Severity = c("HIGH", "HIGH", "HIGH", "MEDIUM", "HIGH", "MEDIUM"),
  Legal_Violation = c("Anti-Discrimination Act", "Age Discrimination Act", 
                     "Racial Discrimination Act", "Indirect Discrimination",
                     "Privacy Act APP 5", "Privacy Act APP 3"),
  Recommendation = c("Remove gender from model", "Remove age from model",
                    "Remove ethnicity from model", "Audit proxy variables",
                    "Implement explainable AI", "Collect only necessary data")
)

cat("COMPLIANCE FINDINGS SUMMARY:\n")
print(compliance_findings)

# Save compliance report
write.csv(compliance_findings, "compliance_assessment.csv", row.names = FALSE)

# =============================================================================
# SECTION 4: RECOMMENDATIONS AND NEXT STEPS
# =============================================================================

cat("\n=== RECOMMENDATIONS FOR COMPLIANCE ===\n")

cat("IMMEDIATE ACTIONS REQUIRED:\n")
cat("1. Remove all protected attributes from the model\n")
cat("2. Audit proxy variables for correlation with protected attributes\n")
cat("3. Implement regular bias testing and monitoring\n")
cat("4. Establish explainable AI framework for decision transparency\n")
cat("5. Conduct regular compliance audits\n\n")

cat("TECHNICAL MITIGATION STRATEGIES:\n")
cat("1. Use adversarial debiasing techniques\n")
cat("2. Implement demographic parity constraints\n")
cat("3. Apply equalized odds post-processing\n")
cat("4. Use synthetic data augmentation for underrepresented groups\n")
cat("5. Implement continuous monitoring of model fairness\n\n")

cat("ORGANIZATIONAL MEASURES:\n")
cat("1. Establish AI ethics committee\n")
cat("2. Implement bias impact assessments\n")
cat("3. Provide staff training on algorithmic fairness\n")
cat("4. Establish external audit processes\n")
cat("5. Create incident response procedures for bias detection\n\n")

# Save session summary
session_summary <- list(
  dataset_size = nrow(loan_data),
  protected_attributes = c("gender", "age_group", "ethnicity", "postcode"),
  bias_detected = TRUE,
  compliance_violations = nrow(compliance_findings),
  mitigation_applied = TRUE,
  recommendations_count = 15
)

saveRDS(session_summary, "analysis_summary.rds")

cat("Analysis complete. All outputs saved to respective files.\n")
cat("Review compliance_assessment.csv for detailed findings.\n")
cat("Check images/ folder for visualization outputs.\n")
