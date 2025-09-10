# ggplot2: Data Visualization Examples
# This script demonstrates key ggplot2 functions with simple sample data

# Load required libraries
library(ggplot2)

# Set seed for reproducible results
set.seed(16)

# Create simple sample data
# Sample 1: Student performance data
students <- data.frame(
  student_id = 1:30,
  math_score = rnorm(30, mean = 75, sd = 10),
  science_score = rnorm(30, mean = 78, sd = 8),
  study_hours = rnorm(30, mean = 15, sd = 3),
  grade_level = sample(c("Freshman", "Sophomore", "Junior", "Senior"), 30, replace = TRUE)
)

# Sample 2: Monthly sales data
monthly_sales <- data.frame(
  month = month.name[1:12],
  sales = c(1200, 1350, 1100, 1400, 1600, 1800, 2000, 1900, 1700, 1500, 1300, 1400),
  profit = c(200, 250, 180, 280, 320, 380, 420, 400, 350, 300, 250, 280)
)

# Sample 3: Product categories
product_data <- data.frame(
  category = c("Electronics", "Clothing", "Books", "Home", "Sports"),
  sales = c(4500, 3200, 1800, 2800, 2100),
  profit_margin = c(0.25, 0.35, 0.20, 0.30, 0.40)
)

# 1. Basic Scatter Plot
p1 <- ggplot(students, aes(x = study_hours, y = math_score)) +
  geom_point(color = "steelblue", size = 3, alpha = 0.7) +
  geom_smooth(method = "lm", color = "red", se = TRUE) +
  labs(
    title = "Study Hours vs Math Score",
    x = "Study Hours per Week",
    y = "Math Score",
    subtitle = "Positive correlation between study time and performance"
  ) +
  theme_minimal() +
  theme(
    plot.title = element_text(size = 16, face = "bold"),
    axis.title = element_text(size = 12)
  )

# Save the plot
ggsave("images/01_scatter_plot.png", p1, width = 8, height = 6, dpi = 300, bg = "white")

# 2. Bar Chart
p2 <- ggplot(product_data, aes(x = reorder(category, sales), y = sales)) +
  geom_bar(stat = "identity", fill = "steelblue", alpha = 0.8) +
  geom_text(aes(label = sales), vjust = -0.5, size = 4) +
  labs(
    title = "Sales by Product Category",
    x = "Product Category",
    y = "Sales ($)",
    subtitle = "Electronics leads in sales volume"
  ) +
  theme_minimal() +
  theme(
    plot.title = element_text(size = 16, face = "bold"),
    axis.text.x = element_text(angle = 45, hjust = 1)
  )

# Save the plot
ggsave("images/02_bar_chart.png", p2, width = 8, height = 6, dpi = 300, bg = "white")

# 3. Line Chart
p3 <- ggplot(monthly_sales, aes(x = month, y = sales, group = 1)) +
  geom_line(color = "steelblue", linewidth = 2) +
  geom_point(color = "steelblue", size = 4) +
  geom_text(aes(label = sales), vjust = -1, size = 3) +
  labs(
    title = "Monthly Sales Trend",
    x = "Month",
    y = "Sales ($)",
    subtitle = "Peak sales during summer months"
  ) +
  theme_minimal() +
  theme(
    plot.title = element_text(size = 16, face = "bold"),
    axis.text.x = element_text(angle = 45, hjust = 1)
  )

# Save the plot
ggsave("images/03_line_chart.png", p3, width = 8, height = 6, dpi = 300, bg = "white")

# 4. Box Plot
p4 <- ggplot(students, aes(x = grade_level, y = math_score, fill = grade_level)) +
  geom_boxplot(alpha = 0.7) +
  geom_jitter(width = 0.2, alpha = 0.5) +
  labs(
    title = "Math Score Distribution by Grade Level",
    x = "Grade Level",
    y = "Math Score",
    subtitle = "Performance varies across grade levels"
  ) +
  theme_minimal() +
  theme(
    plot.title = element_text(size = 16, face = "bold"),
    legend.position = "none"
  )

# Save the plot
ggsave("images/04_box_plot.png", p4, width = 8, height = 6, dpi = 300, bg = "white")

# 5. Histogram
p5 <- ggplot(students, aes(x = math_score)) +
  geom_histogram(bins = 15, fill = "steelblue", alpha = 0.7, color = "black") +
  geom_vline(aes(xintercept = mean(math_score)), color = "red", linetype = "dashed", linewidth = 1) +
  labs(
    title = "Distribution of Math Scores",
    x = "Math Score",
    y = "Frequency",
    subtitle = paste("Mean score:", round(mean(students$math_score), 1))
  ) +
  theme_minimal() +
  theme(plot.title = element_text(size = 16, face = "bold"))

# Save the plot
ggsave("images/05_histogram.png", p5, width = 8, height = 6, dpi = 300, bg = "white")

# 6. Faceted Plot
p6 <- ggplot(students, aes(x = study_hours, y = math_score)) +
  geom_point(color = "steelblue", alpha = 0.7) +
  geom_smooth(method = "lm", color = "red", se = FALSE) +
  facet_wrap(~grade_level, scales = "free") +
  labs(
    title = "Study Hours vs Math Score by Grade Level",
    x = "Study Hours per Week",
    y = "Math Score",
    subtitle = "Relationship varies across grade levels"
  ) +
  theme_minimal() +
  theme(
    plot.title = element_text(size = 16, face = "bold"),
    strip.text = element_text(size = 12, face = "bold")
  )

# Save the plot
ggsave("images/06_faceted_plot.png", p6, width = 10, height = 8, dpi = 300, bg = "white")

# 7. Density Plot
p7 <- ggplot(students, aes(x = math_score, fill = grade_level)) +
  geom_density(alpha = 0.6) +
  labs(
    title = "Math Score Density by Grade Level",
    x = "Math Score",
    y = "Density",
    subtitle = "Distribution patterns across different grades"
  ) +
  theme_minimal() +
  theme(
    plot.title = element_text(size = 16, face = "bold"),
    legend.title = element_text(size = 12)
  )

# Save the plot
ggsave("images/07_density_plot.png", p7, width = 8, height = 6, dpi = 300, bg = "white")

# Print summary statistics
cat("=== ggplot2 Examples Summary ===\n")
cat("Created 7 different plot types:\n")
cat("1. Scatter plot with regression line\n")
cat("2. Bar chart\n")
cat("3. Line chart\n")
cat("4. Box plot\n")
cat("5. Histogram\n")
cat("6. Faceted plot\n")
cat("7. Density plot\n")
cat("\nAll plots saved to images/\n")
cat("Sample data includes:\n")
cat("- 30 students with math scores, study hours, and grade levels\n")
cat("- 12 months of sales data\n")
cat("- 5 product categories with sales and profit data\n")
