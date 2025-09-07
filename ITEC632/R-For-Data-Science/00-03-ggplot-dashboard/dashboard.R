# Sales Dashboard - R Implementation
# Comprehensive sales data visualization and analysis

# Load required libraries
library(ggplot2)
library(dplyr)
library(lubridate)
library(scales)
library(gridExtra)
library(grid)

# Set working directory and create images folder
if (!dir.exists("images")) {
  dir.create("images")
}

# Set theme for white background plots
theme_set(theme_minimal() + 
          theme(panel.background = element_rect(fill = "white", color = NA),
                plot.background = element_rect(fill = "white", color = NA)))

cat("=== Sales Dashboard Analysis ===\n")
cat("Loading and processing sales data...\n")

# Load the dataset
sales_data <- read.csv("sales-dataset/sales.csv")

# Display basic information about the dataset
cat("\nDataset Information:\n")
cat("Shape:", nrow(sales_data), "rows,", ncol(sales_data), "columns\n")
cat("Column names:", paste(names(sales_data), collapse = ", "), "\n")

# Display first few rows
cat("\nFirst 5 rows of the dataset:\n")
print(head(sales_data, 5))

# Data preprocessing
cat("\n=== Data Preprocessing ===\n")

# Convert ORDERDATE to proper date format
sales_data$ORDERDATE <- as.Date(sales_data$ORDERDATE, format = "%m/%d/%Y")

# Extract additional date components
sales_data$Year <- year(sales_data$ORDERDATE)
sales_data$Month <- month(sales_data$ORDERDATE)
sales_data$Month_Name <- month.name[sales_data$Month]

# Ensure numeric columns are properly formatted
sales_data$SALES <- as.numeric(sales_data$SALES)
sales_data$QUANTITYORDERED <- as.numeric(sales_data$QUANTITYORDERED)
sales_data$PRICEEACH <- as.numeric(sales_data$PRICEEACH)

# Data summary
cat("\nData Summary:\n")
print(summary(sales_data))

# Check for missing values
cat("\nMissing values check:\n")
missing_values <- colSums(is.na(sales_data))
print(missing_values)

cat("\n=== Creating Dashboard Visualizations ===\n")

# 1. Total Sales
cat("\n1. Creating Total Sales visualization...\n")
total_sales <- sum(sales_data$SALES, na.rm = TRUE)

p1 <- ggplot(data.frame(metric = "Total Sales", value = total_sales), 
             aes(x = metric, y = value)) +
  geom_bar(stat = "identity", fill = "steelblue", alpha = 0.7, width = 0.5) +
  geom_text(aes(label = dollar_format()(value)), 
            vjust = -0.5, size = 4, fontface = "bold") +
  labs(title = "Total Sales Overview", 
       x = "", 
       y = "Total Sales ($)") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA),
        axis.text.x = element_blank(),
        axis.ticks.x = element_blank()) +
  scale_y_continuous(labels = dollar_format())

png("images/01_total_sales.png", width = 800, height = 600, bg = "white")
print(p1)
dev.off()

cat("Total Sales:", dollar_format()(total_sales), "\n")
cat("Total Sales plot saved as: images/01_total_sales.png\n")

# 2. Total Sales by Years
cat("\n2. Creating Total Sales by Years visualization...\n")
sales_by_year <- sales_data %>%
  group_by(Year) %>%
  summarise(Total_Sales = sum(SALES, na.rm = TRUE), .groups = 'drop')

p2 <- ggplot(sales_by_year, aes(x = Year, y = Total_Sales)) +
  geom_line(color = "steelblue", linewidth = 2) +
  geom_point(color = "darkblue", size = 3) +
  geom_text(aes(label = dollar_format()(Total_Sales)), 
            vjust = -1, size = 3.5) +
  labs(title = "Total Sales by Years", 
       x = "Year", 
       y = "Total Sales ($)") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA)) +
  scale_y_continuous(labels = dollar_format()) +
  scale_x_continuous(breaks = unique(sales_by_year$Year))

png("images/02_sales_by_years.png", width = 800, height = 600, bg = "white")
print(p2)
dev.off()

cat("Sales by Years plot saved as: images/02_sales_by_years.png\n")
print(sales_by_year)

# 3. Total Orders by Years (renumbered from 4)
cat("\n3. Creating Total Orders by Years visualization...\n")
orders_by_year <- sales_data %>%
  group_by(Year) %>%
  summarise(Total_Orders = n_distinct(ORDERNUMBER), .groups = 'drop')

p3 <- ggplot(orders_by_year, aes(x = Year, y = Total_Orders)) +
  geom_line(color = "darkgreen", linewidth = 2) +
  geom_point(color = "darkolivegreen", size = 3) +
  geom_text(aes(label = comma_format()(Total_Orders)), 
            vjust = -1, size = 3.5) +
  labs(title = "Total Orders by Years", 
       x = "Year", 
       y = "Total Orders") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA)) +
  scale_y_continuous(labels = comma_format()) +
  scale_x_continuous(breaks = unique(orders_by_year$Year))

png("images/03_orders_by_years.png", width = 800, height = 600, bg = "white")
print(p3)
dev.off()

cat("Orders by Years plot saved as: images/03_orders_by_years.png\n")
print(orders_by_year)

# 4. Total Quantity
cat("\n4. Creating Total Quantity visualization...\n")
total_quantity <- sum(sales_data$QUANTITYORDERED, na.rm = TRUE)

p4 <- ggplot(data.frame(metric = "Total Quantity", value = total_quantity), 
             aes(x = metric, y = value)) +
  geom_bar(stat = "identity", fill = "darkorange", alpha = 0.7, width = 0.5) +
  geom_text(aes(label = comma_format()(value)), 
            vjust = -0.5, size = 4, fontface = "bold") +
  labs(title = "Total Quantity Overview", 
       x = "", 
       y = "Total Quantity") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA),
        axis.text.x = element_blank(),
        axis.ticks.x = element_blank()) +
  scale_y_continuous(labels = comma_format())

png("images/04_total_quantity.png", width = 800, height = 600, bg = "white")
print(p4)
dev.off()

cat("Total Quantity:", total_quantity, "\n")
cat("Total Quantity plot saved as: images/04_total_quantity.png\n")

# 5. Total Quantity by Years
cat("\n5. Creating Total Quantity by Years visualization...\n")
quantity_by_year <- sales_data %>%
  group_by(Year) %>%
  summarise(Total_Quantity = sum(QUANTITYORDERED, na.rm = TRUE), .groups = 'drop')

p5 <- ggplot(quantity_by_year, aes(x = Year, y = Total_Quantity)) +
  geom_line(color = "darkorange", linewidth = 2) +
  geom_point(color = "orange", size = 3) +
  geom_text(aes(label = comma_format()(Total_Quantity)), 
            vjust = -1, size = 3.5) +
  labs(title = "Total Quantity by Years", 
       x = "Year", 
       y = "Total Quantity") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA)) +
  scale_y_continuous(labels = comma_format()) +
  scale_x_continuous(breaks = unique(quantity_by_year$Year))

png("images/05_quantity_by_years.png", width = 800, height = 600, bg = "white")
print(p5)
dev.off()

cat("Quantity by Years plot saved as: images/05_quantity_by_years.png\n")
print(quantity_by_year)

# 6. Total Quantity by Months
cat("\n6. Creating Total Quantity by Months visualization...\n")
quantity_by_month <- sales_data %>%
  group_by(Month_Name) %>%
  summarise(Total_Quantity = sum(QUANTITYORDERED, na.rm = TRUE), .groups = 'drop') %>%
  mutate(Month_Name = factor(Month_Name, levels = month.name))

p6 <- ggplot(quantity_by_month, aes(x = Month_Name, y = Total_Quantity)) +
  geom_bar(stat = "identity", fill = "purple", alpha = 0.7) +
  geom_text(aes(label = comma_format()(Total_Quantity)), 
            vjust = -0.5, size = 3, angle = 45) +
  labs(title = "Total Quantity by Months", 
       x = "Month", 
       y = "Total Quantity") +
  theme_minimal() +
  theme(panel.background = element_rect(fill = "white", color = NA),
        plot.background = element_rect(fill = "white", color = NA),
        axis.text.x = element_text(angle = 45, hjust = 1)) +
  scale_y_continuous(labels = comma_format())

png("images/06_quantity_by_months.png", width = 800, height = 600, bg = "white")
print(p6)
dev.off()

cat("Quantity by Months plot saved as: images/06_quantity_by_months.png\n")
print(quantity_by_month)

# Summary Statistics
cat("\n=== SUMMARY OF RESULTS ===\n")
cat("Dashboard Metrics:\n")
cat("- Total Sales:", dollar_format()(total_sales), "\n")
cat("- Total Quantity:", total_quantity, "\n")

cat("\nYear-over-Year Analysis:\n")
for (i in 1:nrow(sales_by_year)) {
  year <- sales_by_year$Year[i]
  sales <- sales_by_year$Total_Sales[i]
  orders <- orders_by_year$Total_Orders[i]
  quantity <- quantity_by_year$Total_Quantity[i]
  
  cat("Year", year, ":\n")
  cat("  - Sales:", dollar_format()(sales), "\n")
  cat("  - Orders:", orders, "\n")
  cat("  - Quantity:", quantity, "\n")
}

cat("\nMonthly Quantity Analysis:\n")
for (i in 1:nrow(quantity_by_month)) {
  month <- quantity_by_month$Month_Name[i]
  quantity <- quantity_by_month$Total_Quantity[i]
  cat("-", month, ":", quantity, "units\n")
}

cat("\nAll visualizations have been saved in the 'images' directory with white backgrounds.\n")

# Create Combined Dashboard
cat("\n=== Creating Combined Dashboard ===\n")
cat("Combining all visualizations into a single dashboard image...\n")

# Create a combined dashboard layout
combined_dashboard <- grid.arrange(
  p1, p2, p3,  # Top row: Total Sales, Sales by Years, Orders by Years
  p4, p5, p6,  # Bottom row: Total Quantity, Quantity by Years, Quantity by Months
  ncol = 3,
  nrow = 2,
  top = textGrob("Sales Dashboard - Comprehensive Analysis", 
                 gp = gpar(fontsize = 20, fontface = "bold")),
  bottom = textGrob("Data Source: Sales Dataset | Generated with R & ggplot2", 
                    gp = gpar(fontsize = 10, col = "gray50"))
)

# Save the combined dashboard
png("images/00_combined_dashboard.png", width = 1600, height = 800, bg = "white")
grid.arrange(
  p1, p2, p3,  # Top row: Total Sales, Sales by Years, Orders by Years
  p4, p5, p6,  # Bottom row: Total Quantity, Quantity by Years, Quantity by Months
  ncol = 3,
  nrow = 2,
  top = textGrob("Sales Dashboard - Comprehensive Analysis", 
                 gp = gpar(fontsize = 20, fontface = "bold")),
  bottom = textGrob("Data Source: Sales Dataset | Generated with R & ggplot2", 
                    gp = gpar(fontsize = 10, col = "gray50"))
)
dev.off()

cat("Combined dashboard saved as: images/00_combined_dashboard.png\n")
cat("Dashboard analysis completed successfully!\n")
